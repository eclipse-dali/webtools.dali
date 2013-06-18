/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.gen;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
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
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.ui.wizards.gen.JptJpaUiWizardsEntityGenMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class PromptJPAProjectWizardPage extends WizardPage {

	protected static String SELECT_PROJECT_PAGE_NAME = "SelectJPAProject"; //$NON-NLS-1$
	protected Table projTable;
	private TableViewer projTableViewer;
	private static int PROJECT_NAME_COLUMN_INDEX = 0;
	private final String helpContextId;
	
	public PromptJPAProjectWizardPage( final String helpContextId ) {
		super(SELECT_PROJECT_PAGE_NAME);
		setTitle( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_SELECT_JPA_PROJECT );
		setMessage( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_SELECT_JPA_PROJECT_MSG );
		this.helpContextId = helpContextId;
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 1;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);			
		Label label = new Label( composite, SWT.NONE );
		label.setText( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_SELECT_JPA_PROJECT );
		
		this.projTableViewer = new TableViewer(composite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.VIRTUAL);		
		this.projTable = this.projTableViewer.getTable();
		GridData gd = new GridData( SWT.FILL, SWT.FILL, true, true );
		this.projTable.setLayoutData(gd);
				
		this.projTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleJpaProjectSelection();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			
		});
		
		this.projTableViewer = new TableViewer(this.projTable);
		this.projTableViewer.setLabelProvider(new ProjectTableLabelProvider());
		this.projTableViewer.setContentProvider(new ProjectTableContentProvider());
		this.fillJpaProjectList();
		this.setControl( composite );
		this.validate();
	}
	
	protected void handleJpaProjectSelection() {
		if (this.projTable.getSelectionIndex() != -1) {
			TableItem item =  this.projTable.getItem(this.projTable.getSelectionIndex());
			String projName = item.getText(0);
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
			JpaProject jpaProj = this.getJpaProject(project);
			this.getWizard().setJpaProject(jpaProj);
			this.validate();
		}
	}
	
	@Override
	public GenerateEntitiesFromSchemaWizard getWizard() {
		return (GenerateEntitiesFromSchemaWizard) super.getWizard();
	}

	private void fillJpaProjectList() {
		this.projTableViewer.setInput(this.getSortedJpaProjectsNames());
	}

	private String[] getSortedJpaProjectsNames() {
		return ArrayTools.sort(this.getJpaProjectsNames());
	}

	private String[] getJpaProjectsNames() {
		return ArrayTools.array(IterableTools.transform(this.getJpaProjects(), ProjectTools.NAME_TRANSFORMER), StringTools.EMPTY_STRING_ARRAY);
	}

	private Iterable<IProject> getJpaProjects() {
		return IterableTools.filter(this.getProjects(), new ProjectIsValidSelection());
	}

	protected class ProjectIsValidSelection
		extends PredicateAdapter<IProject>
	{
		@Override
		public boolean evaluate(IProject project) {
			return projectIsValidSelection(project);
		}
	}
	
	protected boolean projectIsValidSelection(IProject project) {
		return this.getJpaProject(project) != null;
	}
	
	protected boolean projectIsValidSelection(JpaProject jpaProject) {
		return jpaProject != null;
	}
	
	private Iterable<IProject> getProjects() {
		return IterableTools.iterable(ResourcesPlugin.getWorkspace().getRoot().getProjects());
	}
	
	private JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}

	protected void validate() {
		if (projTable.getSelectionIndex() != -1)
			setPageComplete(true);
		else
			setPageComplete(false);
	}
	
	@Override
	public final void performHelp() 
	{
	    WorkbenchTools.displayHelp( this.helpContextId );
	}

	// inner classes
	private final class ProjectTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex)
		{
			return (columnIndex == PROJECT_NAME_COLUMN_INDEX) ? WorkbenchTools.getSharedImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT) : null;
		}

		public String getColumnText(Object element, int columnIndex)
		{
			assert element instanceof String;
			String projectName = (String)element;
			if (columnIndex == PROJECT_NAME_COLUMN_INDEX)
				return projectName;
			return null;
		}		
	}
	
	private final class ProjectTableContentProvider implements IStructuredContentProvider
	{
		public Object[] getElements(Object inputElement) {
			return ((String[])inputElement);
		}

		public void dispose() {}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
		
	}
}	
