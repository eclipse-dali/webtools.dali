/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 *  ProjectWizardPage
 */
public class ProjectWizardPage extends WizardPage
{
	private IJavaProject javaProject;
	private ProjectGroup projectGroup;

	// ********** static methods **********

    public static IJavaProject getJavaProjectFromSelection(IStructuredSelection selection) {
    	if(selection == null) {
    		return null;
    	}
		Object firstElement = selection.getFirstElement();
		if(firstElement instanceof IJavaProject) {
			return (IJavaProject)firstElement;
		}
		else if(firstElement instanceof IResource) {
			IProject project = ((IResource) firstElement).getProject();
			return getJavaProjectFrom(project);
		}
		else if(firstElement instanceof IJavaElement) {
			return ((IJavaElement)firstElement).getJavaProject();
		}
		return null;
    }
    
    public static IJavaProject getJavaProjectFrom(IProject project) {
    	return (IJavaProject)((IJavaElement)((IAdaptable)project).getAdapter(IJavaElement.class));
    }

	// ********** constructor **********
    
	public ProjectWizardPage() {
		super("Java Project"); //$NON-NLS-1$

		this.setDescription(JptJaxbUiMessages.ClassesGeneratorProjectWizardPage_desc);
	}
	
	public ProjectWizardPage(IJavaProject javaProject) {
		this();

		this.javaProject = javaProject;
	}
	
	// ********** IDialogPage implementation  **********

	public void createControl(Composite parent) {
		this.setPageComplete(false);
		this.setControl(this.buildTopLevelControl(parent));
	}

	// ********** intra-wizard methods **********

	public IJavaProject getJavaProject() {
		return this.javaProject;
	}
	
	// ********** internal methods **********

	private Control buildTopLevelControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new FillLayout());
		this.projectGroup = new ProjectGroup(composite);
		Dialog.applyDialogFont(parent);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, HELP_CONTEXT_ID);
		return composite;
	}
	
	private void setJavaProject(IJavaProject project) {
		this.javaProject = project;
	}

	private void projectChanged() {
		this.setPageComplete(false);
		String projectName = this.projectGroup.getProjectName();
		if( ! StringTools.stringIsEmpty(projectName)) {

			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			this.setJavaProject(JavaCore.create(project));
			this.setPageComplete(true);
		}
	}

	// ********** project group **********

	class ProjectGroup {

		private Combo projectCombo;


		// ********** constructor **********

		private ProjectGroup(Composite parent) {
			super();
			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout(2, false));

			// Project
			this.buildLabel(composite, JptJaxbUiMessages.JavaProjectWizardPage_project);
			this.projectCombo = this.buildProjectCombo(composite, this.buildProjectComboSelectionListener());
			this.updateProjectCombo();

			setPageComplete( ! StringTools.stringIsEmpty(this.getProjectName()));
		}

		// ********** listeners **********

		private SelectionListener buildProjectComboSelectionListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					// nothing special for "default" (double-click?)
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					ProjectGroup.this.selectedProjectChanged();
				}
				@Override
				public String toString() {
					return "JavaProjectWizardPage project combo-box selection listener"; //$NON-NLS-1$
				}
			};
		}
		
		// ********** listener callbacks **********

		void selectedProjectChanged() {
			projectChanged();
		}

		// ********** intra-wizard methods **********

		protected String getProjectName() {
			return this.projectCombo.getText();
		}

		// ********** internal methods **********
		
		protected void updateProjectCombo() {
			
			this.projectCombo.removeAll();
			
			for (String name : this.getSortedJavaProjectsNames()) {
				this.projectCombo.add(name);
			}
			if(javaProject != null) {
				this.projectCombo.select(this.projectCombo.indexOf(javaProject.getProject().getName()));
			}
		}

		private String[] getSortedJavaProjectsNames() {
			return ArrayTools.sort(this.getJavaProjectsNames());
		}

		private String[] getJavaProjectsNames() {
			return ArrayTools.array(
				new TransformationIterable<IProject, String>(this.getJavaProjects()) {
					@Override
					protected String transform(IProject project) {
						return project.getName();
					}
				},
				new String[0]);
		}
		
		private Iterable<IProject> getJavaProjects() {
		   return new FilteringIterable<IProject>(CollectionTools.collection(this.getProjects())) {
		      @Override
		      protected boolean accept(IProject next) {
					try {
						return next.hasNature(JavaCore.NATURE_ID);
					}
					catch (CoreException e) {
						return false;
					}
		      }
		   };
		}

		private Iterator<IProject> getProjects() {
			return new ArrayIterator<IProject>(ResourcesPlugin.getWorkspace().getRoot().getProjects());
		}

		// ********** UI components **********

		private Label buildLabel(Composite parent, String text) {
			Label label = new Label(parent, SWT.LEFT);
			label.setLayoutData(new GridData());
			label.setText(text);
			return label;
		}

		private Combo buildProjectCombo(Composite parent, SelectionListener listener) {
			Combo projectCombo = new Combo(parent, SWT.READ_ONLY);
			GridData gridData = new GridData();
			gridData.horizontalAlignment = SWT.FILL;
			gridData.grabExcessHorizontalSpace = true;
			projectCombo.setLayoutData(gridData);
			projectCombo.addSelectionListener(listener);
			return projectCombo;
		}
	}
}
