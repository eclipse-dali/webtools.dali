/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards.schemagen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.jaxb.core.internal.SchemaGenerator;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiIcons;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.FileTools;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class SchemaGeneratorWizard extends Wizard implements IExportWizard {

	protected IStructuredSelection selection;

	private ProjectWizardPage javaProjectWizardPage;
	protected SchemaGeneratorWizardPage schemaGenWizardPage;

	public static final String XSD_EXTENSION = ".xsd";   //$NON-NLS-1$
	
	// ********** constructor **********
	
	public SchemaGeneratorWizard() {
		super();
	}

	// ********** IWorkbenchWizard implementation  **********
	
	public void init(IWorkbench workbench, IStructuredSelection selection) {

		this.selection = this.getValidSelection();
		
		this.setWindowTitle(JptJaxbUiMessages.SchemaGeneratorWizard_title);
		this.setDefaultPageImageDescriptor(JptJaxbUiPlugin.getImageDescriptor(JptJaxbUiIcons.SCHEMA_GEN_WIZ_BANNER));
		this.setNeedsProgressMonitor(true);
	}

	// ********** IWizard implementation  **********

	@Override
	public void addPages() {
		super.addPages();
		if(this.selection.isEmpty()) {
			this.javaProjectWizardPage = new ProjectWizardPage();
			this.addPage(this.javaProjectWizardPage);
		}

		this.schemaGenWizardPage = new SchemaGeneratorWizardPage(this.selection);
		this.addPage(this.schemaGenWizardPage);
	}
	
	@Override
	public boolean performFinish() {

		IJavaProject javaProject = this.getJavaProject();
		
		String[] sourceClassNames = this.buildSourceClassNames(this.getAllCheckedItems());
		
		WorkspaceJob genEntitiesJob = new GenerateSchemaJob( 
						javaProject.getProject(), 
						sourceClassNames, 
						this.getTargetSchema(), 
						this.usesMoxy());
		genEntitiesJob.schedule();

		return true;
	}

	// ********** internal methods **********
	
	private String getTargetSchema() {
		String targetSchema = this.schemaGenWizardPage.getSchemaPath();
		if ( ! FileTools.extension(targetSchema).equalsIgnoreCase(XSD_EXTENSION)) {
			targetSchema += XSD_EXTENSION;
		}
		return targetSchema;
	}
	
	private IJavaProject getJavaProject() {
		return this.schemaGenWizardPage.getJavaProject();
	}
	
	private boolean usesMoxy() {
		return this.schemaGenWizardPage.usesMoxy();
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
			classNames.add(packageName + '.' + className);
		}
		
		return ArrayTools.array(classNames, new String[0]);
	}
	
	/**
	 * Gets the current workspace page selection and converts it to a valid
	 * selection for this wizard.
	 * Valid selections: 
	 *      - Java projects
	 *      - Source package fragments
	 *  all other input elements are ignored
	 *
	 * @return a valid structured selection based on the current selection
	 */
	private IStructuredSelection getValidSelection() {
		ISelection currentSelection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService().getSelection();
		if(currentSelection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) currentSelection;
			List<IAdaptable> selectedElements = new ArrayList<IAdaptable>(structuredSelection.size());
			Iterator<?> i = structuredSelection.iterator();
			while(i.hasNext()) {
				Object selectedElement = i.next();
				if(selectedElement instanceof IProject) {
					this.addProject(selectedElements, (IProject)selectedElement);
				}
				else if(selectedElement instanceof IJavaElement) {
					this.addJavaElement(selectedElements, (IJavaElement)selectedElement);
				}
			}
			return new StructuredSelection(selectedElements);
		} 
		return StructuredSelection.EMPTY;
	}

	private void addJavaElement(List<IAdaptable> selectedElements, IJavaElement javaElement) {
		if(javaElement.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
			if( ! isInArchiveOrExternal(javaElement))
				selectedElements.add(javaElement);
		} 
	}

	private void addProject(List<IAdaptable> selectedElements, IProject project) {
		try {
			if(project.hasNature(JavaCore.NATURE_ID))
				selectedElements.add(JavaCore.create(project));
		} 
		catch(CoreException ex) {
			// ignore selected element
		}
	}

	private static boolean isInArchiveOrExternal(IJavaElement element) {
		IPackageFragmentRoot root = (IPackageFragmentRoot) element.getAncestor(IJavaElement.PACKAGE_FRAGMENT_ROOT);
		return root != null && (root.isArchive() || root.isExternal());
	}
	
	// ********** generate schema job **********

	static class GenerateSchemaJob extends WorkspaceJob {
		private final IProject project;
		private final String[] sourceClassNames;
		private final String targetSchema;
		private final boolean useMoxy;
		
		GenerateSchemaJob(IProject project, String[] sourceClassNames, String targetSchema, boolean useMoxy) {
			super(JptJaxbUiMessages.SchemaGeneratorWizard_generatingSchema);
			
			this.project = project ;
			this.sourceClassNames = sourceClassNames;
			this.targetSchema = targetSchema;
			this.useMoxy = useMoxy;

			IResourceRuleFactory ruleFactory = ResourcesPlugin.getWorkspace().getRuleFactory();
			this.setRule(ruleFactory.modifyRule(project));
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
			try{
				SchemaGenerator.generate(project, this.targetSchema, this.sourceClassNames, this.useMoxy, monitor);
			}
			catch(OperationCanceledException e) {
				//user canceled generation
			}
			return Status.OK_STATUS;
		}
	}
}