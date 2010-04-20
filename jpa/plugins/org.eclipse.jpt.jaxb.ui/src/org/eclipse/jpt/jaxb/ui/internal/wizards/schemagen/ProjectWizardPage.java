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

import java.util.Comparator;
import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.Dialog;
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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

import com.ibm.icu.text.Collator;

/**
 *  ProjectWizardPage
 */
public class ProjectWizardPage extends WizardPage
{
	private IJavaProject javaProject;
	private ProjectGroup projectGroup;

	protected ProjectWizardPage() {
		super("Java Project"); //$NON-NLS-1$
		
		this.setTitle(JptJaxbUiMessages.SchemaGeneratorWizardPage_title);
		this.setDescription(JptJaxbUiMessages.ProjectWizardPage_desc);
	}

	public void createControl(Composite parent) {
		this.setPageComplete(false);
		this.setControl(this.buildTopLevelControl(parent));
	}

	private Control buildTopLevelControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		this.projectGroup = new ProjectGroup(composite);
		Dialog.applyDialogFont(parent);
//		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, HELP_CONTEXT_ID);
		return composite;
	}

	// ********** intra-wizard methods **********

	protected IJavaProject getProject() {
		return this.javaProject;
	}
	
	
	// ********** internal methods **********

	private void setProject(IJavaProject project) {
		this.javaProject = project;
	}

	private void projectChanged() {
		this.setPageComplete(false);
		String projectName = this.projectGroup.getProjectName();
		if( ! StringTools.stringIsEmpty(projectName)) {

			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			this.setProject(JavaCore.create(project));
			setPageComplete(true);
		}
	}

	// ********** project group **********

	class ProjectGroup {

		private Combo projectCombo;


		// ********** constructor **********

		private ProjectGroup(Composite parent) {
			super();

			Composite composite = new Composite(parent, SWT.NULL);
			composite.setLayout(new GridLayout(3, false));
			composite.setLayoutData(
				new GridData(GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
			
			// Project
			this.buildLabel(composite, 1, JptJaxbUiMessages.ProjectWizardPage_project);
			this.projectCombo = this.buildProjectCombo(composite, 2, this.buildProjectComboSelectionListener());
			this.updateProjectCombo();
			
			setPageComplete(false);
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
			for (String name : this.getJavaProjectsNames()) {
				this.projectCombo.add(name);
			}
			if(javaProject != null) {
				this.projectCombo.select(this.projectCombo.indexOf(javaProject.getProject().getName()));
			}
		}

		private Iterable<String> getJavaProjectsNames() {
			return new TransformationIterable<IProject, String>(this.getJavaProjects()) {
				@Override
				protected String transform(IProject project) {
					return project.getName();
				}
			};
		}
		
		private Iterable<IProject> getJavaProjects() {
		   return new FilteringIterable<IProject>(CollectionTools.collection(this.getProjects())) {
		      protected boolean accept(IProject next) {
					try {
						return ((IProject)next).hasNature(JavaCore.NATURE_ID);
					}
					catch (CoreException e) {
						return false;
					}
		      }
		   };
		}

		private Iterator<IProject> getProjects() {
			return new ArrayIterator<IProject>(this.sortedProject());
		}
		
		private IProject[] sortedProject() {
			
			return ArrayTools.sort(ResourcesPlugin.getWorkspace().getRoot().getProjects(), this.projectNameComparator());
		}

		private Comparator<IProject> projectNameComparator() {
			return new Comparator<IProject>() {
				public int compare(IProject project1, IProject project2) {
					return Collator.getInstance().compare(project1.getName(), project2.getName());
				}
			};
		}
		
		// ********** UI components **********

		private Label buildLabel(Composite parent, int horizontalSpan, String text) {
			Label label = new Label(parent, SWT.LEFT);
			label.setText(text);
			GridData gridData = new GridData();
			gridData.horizontalSpan = horizontalSpan;
			label.setLayoutData(gridData);
			return label;
		}
			
		private Combo buildProjectCombo(Composite parent, int horizontalSpan, SelectionListener listener) {

			Combo projectCombo = new Combo(parent, SWT.BORDER | SWT.READ_ONLY);
			projectCombo.addSelectionListener(listener);
			
			GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.horizontalSpan = horizontalSpan;
			gridData.widthHint = 300;
			projectCombo.setLayoutData(gridData);

			this.buildFillerColumn(parent);
			return projectCombo;
		}
		
		private Label buildFillerColumn(Composite parent) {
			return new Label(parent, SWT.NONE);
		}
	}
}
