/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import static org.eclipse.jpt.ui.internal.wizards.gen.SWTUtil.createButton;
import static org.eclipse.jpt.ui.internal.wizards.gen.SWTUtil.createLabel;
import static org.eclipse.jpt.ui.internal.wizards.gen.SWTUtil.createText;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jpt.gen.internal2.Association;
import org.eclipse.jpt.gen.internal2.ORMGenCustomizer;
import org.eclipse.jpt.ui.CommonImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;



public class AssociationTablesPage extends NewAssociationWizardPage {

	private Button simpleAssoBtn;
	private Button mtmAssoBtn; 
	private Text table1TextField ;
	private Text table2TextField ;
	private Text joinTableTextField; 
	private Button joinTableBrowse;
	
	public AssociationTablesPage(ORMGenCustomizer customizer) {
		super(customizer,  "AssociationTablesPage");
		setTitle( JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_title);
		setDescription(JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_desc);
	}

	public void createControl(Composite composite) {
		initializeDialogUnits(composite);
		Composite parent = new Composite(composite, SWT.NONE);
		parent.setLayout(new GridLayout(1, true));
		
		Group assocKindGroup = new Group(parent, SWT.NULL);
		int nColumns= 3 ;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		assocKindGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		assocKindGroup.setLayout(layout);
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, JpaHelpContextIds.DIALOG_GENERATE_ENTITIES);
		assocKindGroup.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_assocKind);

		simpleAssoBtn = createButton(assocKindGroup, 3, JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_simpleAssoc, SWT.RADIO);
		mtmAssoBtn = createButton(assocKindGroup, 3, JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_m2mAssoc, SWT.RADIO);

		
		Group assocTablesGroup = new Group(parent, SWT.NULL);
		nColumns= 3 ;
		layout = new GridLayout();
		layout.numColumns = nColumns;
		assocTablesGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		assocTablesGroup.setLayout(layout);
		
		
		assocTablesGroup.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_assocTables );
		
		createLabel(assocTablesGroup, 1, JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_table1);
		table1TextField = createText(assocTablesGroup, 1);
		
		Button browser1 = createButton(assocTablesGroup, 1, "", SWT.NONE);
		browser1.setImage( CommonImages.createImage( CommonImages.DESC_BUTTON_BROWSE )); 

		browser1.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				SelectTableDialog dlg = new SelectTableDialog( Display.getDefault().getActiveShell(), customizer.getTableNames() );
				if( dlg.open() ==Dialog.OK ){
					table1TextField.setText( dlg.getSelectedTable() );
					getWizardDataModel().put( NewAssociationWizard.ASSOCIATION_REFERRER_TABLE, table1TextField.getText());
					getWizard().getContainer().updateButtons();
					((NewAssociationWizard)getWizard()).updateTableNames();
				}
			}
		});

		createLabel(assocTablesGroup, 1, JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_table2);
		table2TextField = createText(assocTablesGroup, 1);
		
		Button browser2 = createButton(assocTablesGroup, 1, "", SWT.NONE);
		browser2.setImage( CommonImages.createImage( CommonImages.DESC_BUTTON_BROWSE )); 

		browser2.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				SelectTableDialog dlg = new SelectTableDialog( Display.getDefault().getActiveShell(), customizer.getSchema() );
				if( dlg.open() == Dialog.OK){
					table2TextField.setText( dlg.getSelectedTable() );
					getWizardDataModel().put( NewAssociationWizard.ASSOCIATION_REFERENCED_TABLE, table2TextField.getText());
					((NewAssociationWizard)getWizard()).updateTableNames();
				}
				updatePageComplete();
			}
		});
		
		createLabel(assocTablesGroup, 1, JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_intermediateTable );
		joinTableTextField = createText(assocTablesGroup, 1);
		joinTableTextField.setEnabled(false);

		joinTableBrowse = createButton(assocTablesGroup, 1, "", SWT.NONE);
		joinTableBrowse.setImage( CommonImages.createImage( CommonImages.DESC_BUTTON_BROWSE )); 
		joinTableBrowse.setEnabled(false);
		
		joinTableBrowse.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				SelectTableDialog dlg = new SelectTableDialog( Display.getDefault().getActiveShell(), customizer.getSchema() );
				if( dlg.open() == Dialog.OK){
					joinTableTextField.setText( dlg.getSelectedTable() );
					getWizardDataModel().put( NewAssociationWizard.ASSOCIATION_JOIN_TABLE, joinTableTextField.getText() );
					((NewAssociationWizard)getWizard()).updateTableNames();
					getWizard().getContainer().updateButtons();
				}
				updatePageComplete();
			}
		});
		
		setControl(parent);

		simpleAssoBtn.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				joinTableTextField.setEnabled(false);
				joinTableTextField.clearSelection();
				joinTableTextField.setText("");
				joinTableBrowse.setEnabled(false);
				getWizardDataModel().put( NewAssociationWizard.ASSOCIATION_CADINALITY, Association.MANY_TO_ONE);
				getWizardDataModel().remove( NewAssociationWizard.ASSOCIATION_JOIN_TABLE );
				((NewAssociationWizard)getWizard()).updateTableNames();
				updatePageComplete();
			}
			
		});

		mtmAssoBtn.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				joinTableTextField.setEnabled(true);
				joinTableBrowse.setEnabled(true);
				getWizardDataModel().put( NewAssociationWizard.ASSOCIATION_CADINALITY, Association.MANY_TO_MANY);
				((NewAssociationWizard)getWizard()).updateTableNames();
				updatePageComplete();
			}
		});		
		
		this.setPageComplete( false);
		table1TextField.setFocus(); 
	}

	public boolean canFlipToNextPage() {
		return isPageComplete();
	}
	
	public void updatePageComplete() {
		if( this.table1TextField.getText().length() <= 0){
			setPageComplete(false);
			return;
		}
		if( mtmAssoBtn.getSelection() ){
			if( this.joinTableTextField.getText().length() <= 0 ){
				setPageComplete(false);
				return;
			}
		}
		setPageComplete(true);
	}
	
}
