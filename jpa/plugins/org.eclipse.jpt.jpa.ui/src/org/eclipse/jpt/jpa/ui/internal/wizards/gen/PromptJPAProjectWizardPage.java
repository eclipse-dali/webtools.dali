/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.gen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
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
import org.eclipse.ui.PlatformUI;

public class PromptJPAProjectWizardPage extends WizardPage {

	private static String SELECT_PROJECT_PAGE_NAME = "SelectJPAProject"; //$NON-NLS-1$
	private Table projTable;
	private TableViewer projTableViewer;
	private static int PROJECT_NAME_COLUMN_INDEX = 0;
	private final String helpContextId;
	
	protected PromptJPAProjectWizardPage( final String helpContextId ) {
		super(SELECT_PROJECT_PAGE_NAME);
		setTitle( JptUiEntityGenMessages.GenerateEntitiesWizard_selectJPAProject );
		setMessage( JptUiEntityGenMessages.GenerateEntitiesWizard_selectJPAProject_msg );
		this.helpContextId = helpContextId;
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 1;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);			
		Label label = new Label( composite, SWT.NONE );
		label.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_selectJPAProject );
		
		projTableViewer = new TableViewer(composite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.VIRTUAL);		
		projTable = projTableViewer.getTable();
		GridData gd = new GridData( SWT.FILL, SWT.FILL, true, true );
		projTable.setLayoutData(gd);
				
		projTable.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleJpaProjectSelection();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
			
		});
		
		projTableViewer = new TableViewer(projTable);
		projTableViewer.setLabelProvider(new ProjectTableLabelProvider());
		projTableViewer.setContentProvider(new ProjectTableContentProvider());
		fillJpaProjectList();
		setControl( composite );
		validate();
	}
	
	private void handleJpaProjectSelection() {
		if (projTable.getSelectionIndex() != -1) {
			TableItem item =  projTable.getItem(projTable.getSelectionIndex());
			String projName = item.getText(0);
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projName);
			JpaProject jpaProj = JptJpaCorePlugin.getJpaProject( project );
			((GenerateEntitiesFromSchemaWizard)getWizard()).setJpaProject(jpaProj);
			validate();
		}
	}
	
	private void fillJpaProjectList() {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		List<String> projNames = new ArrayList<String>();
		for ( IProject project : projects )
		{
			JpaProject jpaProj = JptJpaCorePlugin.getJpaProject( project );
			if ( jpaProj != null ) {
				projNames.add(project.getName());
			}
		}
		projTableViewer.setInput(projNames);
	}
	
	private void validate() {
		if (projTable.getSelectionIndex() != -1)
			setPageComplete(true);
		else
			setPageComplete(false);
	}
	
	@Override
	public final void performHelp() 
	{
	    PlatformUI.getWorkbench().getHelpSystem().displayHelp( this.helpContextId );
	}

	// inner classes
	private final class ProjectTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex)
		{
			if (columnIndex == PROJECT_NAME_COLUMN_INDEX)
				return PlatformUI.getWorkbench().getSharedImages().getImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT);
			return null;
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
		public Object[] getElements(Object inputElement){
			return ((Collection<?>) inputElement).toArray();
		}

		public void dispose(){}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput){}
		
	}
}	
