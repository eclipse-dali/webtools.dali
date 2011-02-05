/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.schemagen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.FileTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.internal.gen.SchemaGenerator;
import org.eclipse.jpt.jaxb.core.internal.operations.SchemaFileCreationDataModelProvider;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiIcons;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
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
		setWindowTitle(JptJaxbUiMessages.SchemaGeneratorWizard_title);
		setDefaultPageImageDescriptor(JptJaxbUiPlugin.getImageDescriptor(JptJaxbUiIcons.SCHEMA_GEN_WIZ_BANNER));
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
		
		WorkspaceJob genSchemaJob = new GenerateSchemaJob( 
						this.targetProject, 
						sourceClassNames, 
						this.getTargetSchema(), 
						this.usesMoxy());
		genSchemaJob.schedule();

		return true;
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
		IJavaProject javaProject = (IJavaProject)((IJavaElement)((IAdaptable)project).getAdapter(IJavaElement.class));
		if(javaProject == null) {
			throw new RuntimeException("Not a Java Project");  //$NON-NLS-1$
		}
		return javaProject;
    }
	
	private String getTargetSchema() {
		
		IPath filePath = this.newSchemaFileWizardPage.getFilePath();
		String fileName = this.newSchemaFileWizardPage.getFileName();
		String targetSchema = filePath.toOSString() + File.separator + fileName;
		if ( ! FileTools.extension(targetSchema).equalsIgnoreCase(XSD_EXTENSION)) {
			targetSchema += XSD_EXTENSION;
		}
		
		return this.makeRelativeToProjectPath(targetSchema);
	}
	
	private String makeRelativeToProjectPath(String filePath) {
		Path path = new Path(filePath);
		IPath relativePath = path.makeRelativeTo(this.targetProject.getProject().getLocation());
		return relativePath.removeFirstSegments(1).toOSString();
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
			if(StringTools.stringIsEmpty(packageName)) {
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
				JptJaxbUiMessages.SchemaGeneratorProjectWizardPage_title, 
				JptJaxbUiMessages.SchemaGeneratorProjectWizardPage_desc);
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
		try {
			if(project != null && project.hasNature(JavaCore.NATURE_ID))
				selectedElements.add(JavaCore.create(project));
		} 
		catch(CoreException ex) {
			// ignore selected element
		}
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

	static class GenerateSchemaJob extends WorkspaceJob {
		private final IJavaProject javaProject;
		private final String[] sourceClassNames;
		private final String targetSchema;
		private final boolean useMoxy;
		
		GenerateSchemaJob(IJavaProject project, String[] sourceClassNames, String targetSchema, boolean useMoxy) {
			super(JptJaxbUiMessages.SchemaGeneratorWizard_generatingSchema);
			
			this.javaProject = project ;
			this.sourceClassNames = sourceClassNames;
			this.targetSchema = targetSchema;
			this.useMoxy = useMoxy;

			this.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(this.javaProject.getProject()));
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			SubMonitor sm = SubMonitor.convert(monitor, NLS.bind(JptJaxbUiMessages.SchemaGeneratorWizard_generateSchemaTask, this.targetSchema), 1);
			try {
				SchemaGenerator.generate(this.javaProject, this.targetSchema, this.sourceClassNames, this.useMoxy, sm.newChild(1));
			}
			catch (OperationCanceledException e) {
				return Status.CANCEL_STATUS;
			}
			return Status.OK_STATUS;
		}
	}
}