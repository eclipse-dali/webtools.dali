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

import static org.eclipse.jpt.jpa.ui.internal.wizards.gen.SWTUtil.createButton;
import static org.eclipse.jpt.jpa.ui.internal.wizards.gen.SWTUtil.createLabel;
import static org.eclipse.jpt.jpa.ui.internal.wizards.gen.SWTUtil.createText;
import java.util.ArrayList;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.gen.internal.Association;
import org.eclipse.jpt.jpa.gen.internal.ORMGenCustomizer;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.wizards.gen.JptJpaUiWizardsEntityGenMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;



public class AssociationTablesPage extends NewAssociationWizardPage {

	private Button simpleAssoBtn;
	private Button mtmAssoBtn; 
	private Text table1TextField ;
	private Text table2TextField ;
	private Text joinTableTextField; 
	private Button joinTableBrowse;
	
	protected final ResourceManager resourceManager;
	
	public AssociationTablesPage(ORMGenCustomizer customizer, ResourceManager resourceManager) {
		super(customizer,  "AssociationTablesPage");
		this.resourceManager = resourceManager;
		setTitle( JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_title);
		setDescription(JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_desc);
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
		WorkbenchTools.setHelp(composite, JpaHelpContextIds.GENERATE_ENTITIES_WIZARD_ASSOCIATION_TABLES);
		assocKindGroup.setText( JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_assocKind);

		simpleAssoBtn = createButton(assocKindGroup, 3, JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_simpleAssoc, SWT.RADIO);
		mtmAssoBtn = createButton(assocKindGroup, 3, JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_m2mAssoc, SWT.RADIO);

		
		Group assocTablesGroup = new Group(parent, SWT.NULL);
		nColumns= 3 ;
		layout = new GridLayout();
		layout.numColumns = nColumns;
		assocTablesGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		assocTablesGroup.setLayout(layout);
		
		
		assocTablesGroup.setText( JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_assocTables );
		
		createLabel(assocTablesGroup, 1, JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_table1);
		table1TextField = createText(assocTablesGroup, 1);
		table1TextField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updatePageComplete();
			}
		});
		
		Button browser1 = createButton(assocTablesGroup, 1, "", SWT.NONE);
		browser1.setImage(this.resourceManager.createImage(JptCommonUiImages.BROWSE_BUTTON));

		browser1.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				SelectTableDialog dlg = new SelectTableDialog(SWTUtil.getShell(), resourceManager, customizer.getTableNames());
				if( dlg.open() ==Dialog.OK ){
					table1TextField.setText( dlg.getSelectedTable() );
					getWizardDataModel().put( NewAssociationWizard.ASSOCIATION_REFERRER_TABLE, table1TextField.getText());
					getWizard().getContainer().updateButtons();
					((NewAssociationWizard)getWizard()).updateTableNames();
				}
				updatePageComplete();
			}
		});

		createLabel(assocTablesGroup, 1, JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_table2);
		table2TextField = createText(assocTablesGroup, 1);
		table2TextField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updatePageComplete();
			}
		});
		
		Button browser2 = createButton(assocTablesGroup, 1, "", SWT.NONE);
		browser2.setImage(this.resourceManager.createImage(JptCommonUiImages.BROWSE_BUTTON));

		browser2.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				SelectTableDialog dlg = new SelectTableDialog( SWTUtil.getShell(), resourceManager, customizer.getTableNames());
				if( dlg.open() == Dialog.OK){
					table2TextField.setText( dlg.getSelectedTable() );
					getWizardDataModel().put( NewAssociationWizard.ASSOCIATION_REFERENCED_TABLE, table2TextField.getText());
					((NewAssociationWizard)getWizard()).updateTableNames();
				}
				updatePageComplete();
			}
		});
		
		createLabel(assocTablesGroup, 1, JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_intermediateTable );
		joinTableTextField = createText(assocTablesGroup, 1);
		joinTableTextField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updatePageComplete();
			}
		});
		joinTableTextField.setEnabled(false);

		joinTableBrowse = createButton(assocTablesGroup, 1, "", SWT.NONE);
		joinTableBrowse.setImage(this.resourceManager.createImage(JptCommonUiImages.BROWSE_BUTTON));
		joinTableBrowse.setEnabled(false);
		
		joinTableBrowse.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				SelectTableDialog dlg = new SelectTableDialog( SWTUtil.getShell(), resourceManager, customizer.getSchema() );
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
	}

	@Override
	public boolean canFlipToNextPage() {
		return isPageComplete();
	}
	
	public void updatePageComplete() {
		if( this.table1TextField.getText().length() <= 0 ||
			this.table2TextField.getText().length() <= 0 ) {
			this.setErrorMessage(null);
			setPageComplete(false);
			return;
		}

		String errorMessage = this.buildTableErrorMessage();
		if( errorMessage == null && this.mtmAssoBtn.getSelection() ) {
				if( this.joinTableTextField.getText().length() <= 0 ) {
					this.setErrorMessage(null);
					setPageComplete(false);
					return;
				} else {
					errorMessage = this.buildJoinTableErrorMessage();
				}
		}

		this.setErrorMessage(errorMessage);
		this.setPageComplete(errorMessage == null);
	}

	private String buildTableErrorMessage() {
		if ( !IterableTools.contains(this.customizer.getTableNames(), this.table1TextField.getText()) ) {
			return NLS.bind(JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_nonexsistent_table, this.table1TextField.getText());
		} else if ( !IterableTools.contains(this.customizer.getTableNames(), this.table2TextField.getText()) ) {
			return NLS.bind(JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_nonexsistent_table, this.table2TextField.getText());
		}
		return null;
	}
	
	private String buildJoinTableErrorMessage() {
		if (!IterableTools.contains(this.getAllTableNames(this.customizer.getSchema()), this.joinTableTextField.getText())) {
			return NLS.bind(JptJpaUiWizardsEntityGenMessages.GenerateEntitiesWizard_newAssoc_tablesPage_nonexsistent_join_table, this.joinTableTextField.getText());
		}
		return null;
	}
	
	protected ArrayList<String> getAllTableNames(Schema schema) {
		ArrayList<String> list = new ArrayList<String>();
		for (Table table : schema.getTables()) {
			list.add(table.getName());
		}
		return list;
	}
}
