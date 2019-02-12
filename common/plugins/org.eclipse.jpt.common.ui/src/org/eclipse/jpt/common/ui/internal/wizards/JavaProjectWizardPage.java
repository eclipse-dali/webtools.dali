/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.core.internal.utility.ProjectTools;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 *  JavaProjectWizardPage
 */
public class JavaProjectWizardPage extends WizardPage {

	private IJavaProject javaProject;
	private String destinationLabel;
	private Table projectTable;
	private TableViewer projectTableViewer;
	
	private static String SELECT_PROJECT_PAGE_NAME = "SelectJavaProject"; //$NON-NLS-1$
	private static int PROJECT_NAME_COLUMN_INDEX = 0;

	// ********** constructor **********
	
	public JavaProjectWizardPage(IJavaProject javaProject) {
		super(SELECT_PROJECT_PAGE_NAME);

		this.javaProject = javaProject;
	}
	
	// ********** IDialogPage implementation  **********

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);

		this.buildLabel(composite, this.destinationLabel);
		
		this.projectTable = this.buildProjectTable(composite, this.buildProjectTableSelectionListener());
		
		this.projectTableViewer = this.buildProjectTableViewer(
												this.projectTable, 
												this.buildProjectTableLabelProvider(), 
												this.buildProjectTableContentProvider());
		this.fillProjectList();
		this.setControl(composite);
		this.setTableSelection(this.javaProject);
		this.validate();
	}

	// ********** listeners **********

	private SelectionListener buildProjectTableSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectedProjectChanged();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			
			@Override
			public String toString() {
				return "PromptProjectWizardPage project table selection listener"; //$NON-NLS-1$
			}
		};
	}

	// ********** listener callbacks **********

	protected void selectedProjectChanged() {
		if(this.projectTable.getSelectionIndex() != -1) {
			TableItem item =  this.projectTable.getItem(this.projectTable.getSelectionIndex());
			String projectName = item.getText(0);
			if( ! StringTools.isBlank(projectName)) {

				IProject project = this.javaProject.getProject().getWorkspace().getRoot().getProject(projectName);
				this.setJavaProject(JavaCore.create(project));
				this.validate();
			}
		}
	}
	
	// ********** intra-wizard methods **********

	public IJavaProject getJavaProject() {
		return this.javaProject;
	}

	public void setDestinationLabel(String destinationLabel) {
		this.destinationLabel = destinationLabel;
	}

	// ********** protected methods **********

	protected void setTableSelection(IJavaProject javaProject) {
		if(javaProject != null) {
			String projectName = javaProject.getProject().getName();
			for(TableItem item: this.projectTable.getItems()) {
				if(item.getText(0).equals(projectName)) {
					this.projectTable.setSelection(item);
				}
			}
		}
	}
	
	protected void fillProjectList() {
		this.projectTableViewer.setInput(this.getSortedJavaProjectsNames());
	}

	// ********** internal methods **********

	private void validate() {
		this.setPageComplete(this.projectTable.getSelectionIndex() != -1);
	}
	
	private void setJavaProject(IJavaProject project) {
		this.javaProject = project;
	}

	private String[] getSortedJavaProjectsNames() {
		return ArrayTools.sort(this.getJavaProjectsNames());
	}

	private String[] getJavaProjectsNames() {
		return ArrayTools.array(
			IterableTools.transform(this.getJavaProjects(), ProjectTools.NAME_TRANSFORMER),
			StringTools.EMPTY_STRING_ARRAY);
	}
	
	protected Iterable<IProject> getJavaProjects() {
	   return IterableTools.filter(this.getProjects(), ProjectTools.IS_JAVA_PROJECT);
	}

	protected Iterable<IProject> getProjects() {
		return IterableTools.iterable(this.javaProject.getProject().getWorkspace().getRoot().getProjects());
	}

	// ********** inner classes **********

	private final class ProjectTableLabelProvider extends LabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return (columnIndex == PROJECT_NAME_COLUMN_INDEX) ? WorkbenchTools.getSharedImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT) : null;
		}

		public String getColumnText(Object element, int columnIndex) {
			assert element instanceof String;
			String projectName = (String)element;
			if(columnIndex == PROJECT_NAME_COLUMN_INDEX)
				return projectName;
			return null;
		}		
	}

	private final class ProjectTableContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			return ((String[])inputElement);
		}

		public void dispose() {}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
	}

	// ********** UI components **********
	
	private ITableLabelProvider buildProjectTableLabelProvider() {
		return new ProjectTableLabelProvider();
	}

	private IStructuredContentProvider buildProjectTableContentProvider() {
		return new ProjectTableContentProvider();
	}

	private Label buildLabel(Composite parent, String text) {
		Label label = new Label( parent, SWT.NONE );
		label.setText(text);
		return label;
	}

	private Table buildProjectTable(Composite parent, SelectionListener listener) {
		TableViewer tableViewer = new TableViewer(parent, 
												SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.VIRTUAL);		

		Table table = tableViewer.getTable();
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		table.addSelectionListener(listener);
		return table;
	}

	private TableViewer buildProjectTableViewer(Table parent, ITableLabelProvider labelProvider, IStructuredContentProvider contentProvider) {

		TableViewer tableViewer = new TableViewer(parent);
		tableViewer.setLabelProvider(labelProvider);
		tableViewer.setContentProvider(contentProvider);
		return tableViewer;
	}
}	
