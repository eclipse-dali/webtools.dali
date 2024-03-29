/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.schemagen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.common.core.gen.JptGenerator;
import org.eclipse.jpt.common.core.internal.utility.ProjectTools;
import org.eclipse.jpt.common.ui.gen.AbstractJptGenerateJob;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.io.FileTools;
import org.eclipse.jpt.jaxb.core.internal.gen.SchemaGenerator;
import org.eclipse.jpt.jaxb.core.internal.operations.SchemaFileCreationDataModelProvider;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiImages;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;

/**
 *  SchemaGeneratorWizard
 */
public class SchemaGeneratorWizard extends Wizard implements INewWizard 
{
	protected IStructuredSelection initialSelection;
	private IJavaProject targetProject;
	
	protected IDataModel dataModel;	
	
	private NewSchemaFileWizardPage newSchemaFileWizardPage;

	private SchemaGeneratorWizardPage schemaGenWizardPage;

	public static final String XSD_EXTENSION = ".xsd";   //$NON-NLS-1$

	// ********** constructor **********
	
	public SchemaGeneratorWizard() {
		super();
		setWindowTitle(JptJaxbUiMessages.SCHEMA_GENERATOR_WIZARD_TITLE);
		setDefaultPageImageDescriptor(JptJaxbUiImages.SCHEMA_GEN_BANNER);
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.initialSelection = selection;
		
		if (selection == null || selection.isEmpty()) {	
			return;
		}
		this.targetProject = this.getProjectFromInitialSelection();
		this.dataModel = null;
	}

	// ********** IWizard implementation  **********

	@Override
	public void addPages() {
		super.addPages();
		
		this.newSchemaFileWizardPage = this.buildNewSchemaFileWizardPage(this.initialSelection);
		this.addPage(this.newSchemaFileWizardPage);

		this.schemaGenWizardPage = this.buildSchemaGeneratorWizardPage(this.buildSelection(this.getProject()));
		this.addPage(this.schemaGenWizardPage);
	}
	
	@Override
	public boolean performFinish() {

		this.targetProject = this.getJavaProject();
		
		String[] sourceClassNames = this.buildSourceClassNames(this.getAllCheckedItems());

		this.scheduleGenerateSchemaJob(sourceClassNames);
		return true;
	}

	protected void scheduleGenerateSchemaJob(String[] sourceClassNames) {

		WorkspaceJob genSchemaJob = new GenerateSchemaJob( 
									this.targetProject, 
									sourceClassNames, 
									this.getTargetSchema(),
									this.getTargetLocation(), 
									this.usesMoxy());
		genSchemaJob.schedule();
		
	}

	// ********** intra-wizard methods **********
	
	public IJavaProject getJavaProject() {
		return this.getJavaProjectFrom(this.getProject());
	}
    
	// ********** internal methods **********

	private IProject getProject() {
		return this.newSchemaFileWizardPage.getProject();
	}

    private IJavaProject getProjectFromInitialSelection() {
    	IJavaProject project = null;

		Object firstElement = this.initialSelection.getFirstElement();
		if(firstElement instanceof IJavaElement) {
			IJavaElement javaElement = (IJavaElement)firstElement;
			int type = javaElement.getElementType();
			if(type == IJavaElement.JAVA_PROJECT) {
				project = (IJavaProject)javaElement;
			}
			else if(type == IJavaElement.PACKAGE_FRAGMENT) {
				project = ((IPackageFragment)javaElement).getJavaProject();
			}
		}
		return project;
    }

    private IJavaProject getJavaProjectFrom(IProject project) {
		IJavaProject javaProject = ((IJavaElement) project.getAdapter(IJavaElement.class)).getJavaProject();
		if(javaProject == null) {
			throw new RuntimeException("Not a Java Project");  //$NON-NLS-1$
		}
		return javaProject;
    }

	private String getTargetSchema() {

		IPath filePath = this.newSchemaFileWizardPage.getFileRelativePath();
		String fileName = this.newSchemaFileWizardPage.getFileName();
		String targetSchema = (filePath.isEmpty()) ?
			fileName :
			filePath.toOSString() + File.separator + fileName;

		return this.addXsdExtension(targetSchema);
	}

	private IPath getTargetLocation() {
		return this.newSchemaFileWizardPage.getContainerFullPath();
	}

	private String addXsdExtension(String fileName) {

		return (FileTools.extension(fileName).equalsIgnoreCase(XSD_EXTENSION)) ?
			fileName :
			fileName + XSD_EXTENSION;
	}

	private Object[] getAllCheckedItems() {
		return this.schemaGenWizardPage.getAllCheckedItems();
	}
	
	private String[] buildSourceClassNames(Object[] checkedElements) {

		ArrayList<String> classNames = new ArrayList<String>();
		
		for(Object element: checkedElements) {
			IJavaElement javaElement = (IJavaElement)element;
			String packageName = javaElement.getParent().getElementName();
			String elementName = javaElement.getElementName();
			String className = FileTools.stripExtension(elementName);
			if(StringTools.isBlank(packageName)) {
				classNames.add(className);
			}
			else {
				classNames.add(packageName + '.' + className);
			}
		}
		
		return ArrayTools.array(classNames, new String[0]);
	}
	
	private boolean usesMoxy() {
		return this.schemaGenWizardPage.usesMoxy();
	}
	
	protected NewSchemaFileWizardPage buildNewSchemaFileWizardPage(IStructuredSelection selection) {
		return new NewSchemaFileWizardPage(
				"Page_1", selection, this.getDataModel(),	   //$NON-NLS-1$
				JptJaxbUiMessages.SCHEMA_GENERATOR_PROJECT_WIZARD_PAGE_TITLE, 
				JptJaxbUiMessages.SCHEMA_GENERATOR_PROJECT_WIZARD_DESC);
	}
	
	protected SchemaGeneratorWizardPage buildSchemaGeneratorWizardPage(IStructuredSelection selection) {
		return new SchemaGeneratorWizardPage(selection);
	}

	private IStructuredSelection buildSelection(IProject project) {

		List<IAdaptable> selectedElements = new ArrayList<IAdaptable>(1);
		this.addProjectTo(selectedElements, project);
		return new StructuredSelection(selectedElements);
	}
	
	private void addProjectTo(List<IAdaptable> selectedElements, IProject project) {
		if(project != null && ProjectTools.isJavaProject(project))
			selectedElements.add(JavaCore.create(project));
	}

	protected IDataModel getDataModel() {
		if (this.dataModel == null) {
			this.dataModel = DataModelFactory.createDataModel(getDefaultProvider());
		}
		return this.dataModel;
	}

	protected IDataModelProvider getDefaultProvider() {
		return new SchemaFileCreationDataModelProvider();
	}
	
	// ********** generate schema job **********

	private class GenerateSchemaJob extends AbstractJptGenerateJob {
		private final String[] sourceClassNames;
		private final String targetSchema;
		private final IPath targetLocation;
		private final boolean useMoxy;
		private SchemaGenerator generator;

		// ********** constructor **********

		protected GenerateSchemaJob(IJavaProject javaProject, String[] sourceClassNames, String targetSchema, 
										IPath targetLocation, boolean useMoxy) {
			
			super(JptJaxbUiMessages.SCHEMA_GENERATOR_WIZARD_GENERATING_SCHEMA, javaProject);
			
			this.sourceClassNames = sourceClassNames;
			this.targetSchema = targetSchema;
			this.targetLocation = targetLocation;
			this.useMoxy = useMoxy;
		}

		// ********** overwrite AbstractJptGenerateJob **********

		@Override
		protected JptGenerator buildGenerator() {
			this.generator = new SchemaGenerator(this.getJavaProject(), this.targetSchema, this.sourceClassNames, this.useMoxy);
			return this.generator;
		}

		@Override
		protected void postGenerate() {
			this.refreshProject();
			Iterable<String> schemaNames = this.generator.getGeneratedNames();
			this.openGeneratedSchemaFiles(schemaNames);
		}

		@Override
		protected String getJobName() {
			return NLS.bind(JptJaxbUiMessages.SCHEMA_GENERATOR_WIZARD_GENERATE_SCHEMA_TASK, this.targetSchema);
		}

		@Override
		protected void jptPluginLogException(Exception exception) {
			JptJaxbUiPlugin.instance().logError(exception);
		}

		// ********** internal methods **********

		private void openGeneratedSchemaFiles(Iterable<String> names) {
			for(String name : names) {
				IContainer container = (IContainer)ResourcesPlugin.getWorkspace().getRoot().findMember(this.targetLocation);
				IPath schemaPath = new Path(SchemaGeneratorWizard.this.addXsdExtension(name));
				IFile schemaFile = container.getFile(schemaPath);
	
				this.openEditor(schemaFile);
			}
		}
	}
}
