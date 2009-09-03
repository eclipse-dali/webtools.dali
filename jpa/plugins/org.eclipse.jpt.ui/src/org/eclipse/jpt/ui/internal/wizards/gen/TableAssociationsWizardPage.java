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


import static org.eclipse.jpt.ui.internal.wizards.gen.SWTUtil.fillColumns;
import static org.eclipse.jpt.ui.internal.wizards.gen.SWTUtil.newLabel;
import static org.eclipse.jpt.ui.internal.wizards.gen.SWTUtil.newLabelWithIndent;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.ui.dialogs.StatusUtil;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.DialogField;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.IStringButtonAdapter;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.StringButtonDialogField;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.gen.internal.Association;
import org.eclipse.jpt.gen.internal.AssociationRole;
import org.eclipse.jpt.gen.internal.ORMGenColumn;
import org.eclipse.jpt.gen.internal.ORMGenCustomizer;
import org.eclipse.jpt.gen.internal.util.DTPUtil;
import org.eclipse.jpt.ui.internal.ImageRepository;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

public class TableAssociationsWizardPage extends WizardPage {

	private JpaProject jpaProject;
	private ORMGenCustomizer customizer ;
	
	private AssociationsListComposite associationList; 
	private Association selectedAssociation;
	private Button deleteAssociationLink ;
	private Button createAssociationLink ;
	
	//Controls in Association Edit Panel
	private Composite associationsEditPanel ;
	private Button generateAssociationCheckBox; 
	private Label cardinalityLabel ;
	private Combo cardinalityCombo ;
	/*whether to generate the referrer-->referenced role.*/
	private Button referrerRoleCheckBox; 
	/*the name of the property in the referrer-->referenced role.*/
	private Label referrerRolePropertyLabel;
	private Text referrerRolePropertyField ;
	/*the cascade in the referrer-->referenced role.*/
	@SuppressWarnings("restriction")
	private StringButtonDialogField referrerRoleCascadeField;
	/*whether to generate the referenced->referrer role.*/
	private Button referencedRoleCheckBox;
	/*the name of the property in the referenced->referrer role.*/
	private Label referencedRolePropertyLabel;
	private Text referencedRolePropertyField ;
	/*the cascade in the referenced->referrer role.*/
	@SuppressWarnings("restriction")
	private StringButtonDialogField referencedRoleCascadeField;
	private Label joinConditionLabel;
	private Text joinConditionText; 
	
	private Composite detailPanel ;
	private StackLayout detailPanelStatckLayout ;
	private Composite emptyPanel ;
	
	protected TableAssociationsWizardPage(JpaProject jpaProject ) {
		super("Table Associations"); //$NON-NLS-1$
		this.jpaProject = jpaProject;
		setTitle(JptUiEntityGenMessages.GenerateEntitiesWizard_assocPage_title);
		setMessage(JptUiEntityGenMessages.GenerateEntitiesWizard_assocPage_desc);
		
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 2 ;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		this.getHelpSystem().setHelp(composite, JpaHelpContextIds.GENERATE_ENTITIES_WIZARD_TABLE_ASSOCIATIONS);

		Label label = new Label(composite, SWT.NONE);
		label.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_assocPage_label );
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		label.setLayoutData( gd );
		
		createAssociationsListPanel(composite);
		createAddDeleteButtons(composite, nColumns);
		SWTUtil.createSeparator(composite, nColumns);
		
		createDetailPanel(composite);
		setControl(composite);
		
		composite.layout(true);
		this.setPageComplete( true);
		
	}

	private void createAddDeleteButtons(Composite composite, int columns) {
		
		Composite c = new Composite( composite, SWT.NONE);
		fillColumns(c, 1);
		c.setLayout( new GridLayout(1,true) );
		
		createAssociationLink = new Button(c, SWT.NONE);
		createAssociationLink.setToolTipText( JptUiEntityGenMessages.GenerateEntitiesWizard_assocPage_newAssoc );
		createAssociationLink.setImage( ImageRepository.getAddButtonImage() );
		createAssociationLink.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}	
			public void widgetSelected(SelectionEvent e) {
				launchNewAssociationsWizard();
			}	
			
		});
		
		deleteAssociationLink = new Button(c, SWT.NONE);
		deleteAssociationLink.setForeground( new Color(Display.getDefault(), 0,0,255));
		deleteAssociationLink.setImage( ImageRepository.getDeleteButtonImage()  );
		deleteAssociationLink.setToolTipText( JptUiEntityGenMessages.GenerateEntitiesWizard_assocPage_delAssoc );
		deleteAssociationLink.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}	
			public void widgetSelected(SelectionEvent e) {
				Association association = associationList.getSelectedAssociation();
				if( association != null ){
					ORMGenCustomizer customizer = getCustomizer();
					customizer.deleteAssociation(association);
					List<Association> associations = customizer.getAssociations();
					associationList.updateAssociations(associations);
					if( associations.size()==0 ){
						hideAssociationDetail();
					}
				}
			}	
		});
		deleteAssociationLink.setEnabled(false);
		
	}

	private void createGenerateAssociationCheckbox(Composite composite,  int columns) {
		generateAssociationCheckBox = new Button(composite, SWT.CHECK);
		generateAssociationCheckBox.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_genAssoc);
		generateAssociationCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
		
			public void widgetSelected(SelectionEvent e) {
				Association association = associationList.getSelectedAssociation();
				if( association == null )
					return;
				association.setGenerated(generateAssociationCheckBox.getSelection());
				
				updateAssociationEditPanel(association);
				associationList.updateSelectedAssociation();
			}

		});

		fillColumns(generateAssociationCheckBox, columns-1);
		newLabel(composite, "");
	}

	@SuppressWarnings("restriction")
	private void createGenerateReferrerRoleControls(Composite parent,  int columns) {
		referrerRoleCheckBox = new Button(parent, SWT.CHECK);
		referrerRoleCheckBox.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_entityRef );
		referrerRoleCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
		
			public void widgetSelected(SelectionEvent e) {
				boolean generate = referrerRoleCheckBox.getSelection();
				//referrerRolePropertyField.setEditable( generate );
				referrerRolePropertyLabel.setEnabled( generate );
				referrerRolePropertyField.setEnabled( generate );
				referrerRoleCascadeField.setEnabled(generate);
				
				//If both referencedRoleCheckBox and referencedRoleCheckBox unchecked, 
				//the association itself shouldn't be generated 
				if( !generate && !referencedRoleCheckBox.getSelection()){
					generateAssociationCheckBox.setSelection(false);
					cardinalityLabel.setEnabled( false );
					cardinalityCombo.setEnabled(false);
					referrerRoleCheckBox.setEnabled(false);
					referencedRoleCheckBox.setEnabled(false);
				}
				
				directionalityCheckBoxChanged( );			
				
				if( generate ){
					AssociationRole role = selectedAssociation.getReferrerRole();
					referrerRolePropertyField.setText( role.getPropertyName() );
				}
			}

		});

		SWTUtil.fillColumnsWithIndent(referrerRoleCheckBox , columns-1, 20 );
		newLabel(parent, "");//$NON-NLS-1$
		
		referrerRolePropertyLabel = newLabelWithIndent( parent,  JptUiEntityGenMessages.property, 40 ); //$NON-NLS-1$
		referrerRolePropertyField = new Text( parent, SWT.BORDER);
		fillColumns(referrerRolePropertyField, 2);
		referrerRolePropertyField.addModifyListener(new ModifyListener(){
			@SuppressWarnings("deprecation")
			public void modifyText(ModifyEvent e) {
				if( selectedAssociation.getReferrerRole() == null )
					return;
				String fieldName = referrerRolePropertyField.getText();
				IStatus status = JavaConventions.validateFieldName( fieldName );
				if( !status.matches(IStatus.ERROR) ){
					selectedAssociation.getReferrerRole().setPropertyName(fieldName);
				}
				updateStatus(status);
			}			
		});

		Label label = new Label( parent, SWT.NONE);
		label.setText( "" );//$NON-NLS-1$
		label.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));
		
		referrerRoleCascadeField = new StringButtonDialogField( new IStringButtonAdapter(){
			public void changeControlPressed(DialogField field) {
				if( editCascade( selectedAssociation.getReferrerRole() )){
					referrerRoleCascadeField.setText(selectedAssociation.getReferrerRole().getCascade());
				}
			}	
		}) ;
		referrerRoleCascadeField.setLabelText( JptUiEntityGenMessages.cascade ); //$NON-NLS-1$
		referrerRoleCascadeField.setButtonLabel(""); //$NON-NLS-1$
		referrerRoleCascadeField.doFillIntoGrid(parent, 3);
		referrerRoleCascadeField.getTextControl(parent).setEditable(false);
		int maxFieldWidth = convertWidthInCharsToPixels(40);
		LayoutUtil.setWidthHint(referrerRoleCascadeField.getTextControl(null), maxFieldWidth );
		Button btn = referrerRoleCascadeField.getChangeControl(null);
		GridData data = (GridData)btn.getLayoutData();
		btn.setImage( ImageRepository.getBrowseButtonImage() );
		data.horizontalAlignment = SWT.BEGINNING;
		data.widthHint = 30;
		btn.setLayoutData(data);
		
		Label labelCtrl  = referrerRoleCascadeField.getLabelControl(parent);
		data = (GridData)labelCtrl.getLayoutData();
		data.horizontalIndent = 40 ;
		labelCtrl.setLayoutData(data);
		
		
		label = new Label( parent, SWT.NONE);
		label.setText( "" );//$NON-NLS-1$
		label.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));
		
	}	
	
	
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if( visible ){
			hideAssociationDetail();
			updateAssociationsListPanel();
		}
	}

	private void hideAssociationDetail( ){
		this.detailPanelStatckLayout.topControl = emptyPanel;
		this.detailPanel.layout();
		this.detailPanel.getParent().layout();
	}
	
	/**
	 * Updates the status line and the OK button according to the given status
	 * 
	 * @param status status to apply
	 */
	@SuppressWarnings("restriction")
	protected void updateStatus(IStatus status) {
		setPageComplete(!status.matches(IStatus.ERROR));
		
		StatusUtil.applyToStatusLine(this, status);
		if( status.getCode() == Status.OK ){
			setMessage(JptUiEntityGenMessages.GenerateEntitiesWizard_assocPage_desc);
		}
	}
	
	@SuppressWarnings("restriction")
	private void createGenerateReferencedRoleControls(Composite parent,  int columns) {
		referencedRoleCheckBox = new Button(parent, SWT.CHECK);
		referencedRoleCheckBox.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_setRef );
		referencedRoleCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
		
			public void widgetSelected(SelectionEvent e) {
				boolean generate = referencedRoleCheckBox.getSelection();
				referencedRolePropertyLabel.setEnabled( generate);
				referencedRolePropertyField.setEnabled( generate);
				referencedRoleCascadeField.setEnabled(generate);
				
				if( !generate && !referrerRoleCheckBox.getSelection()){
					generateAssociationCheckBox.setSelection(false);
					cardinalityCombo.setEnabled(false);
					referrerRoleCheckBox.setEnabled(false);
					referencedRoleCheckBox.setEnabled(false);
				}
				directionalityCheckBoxChanged();
				if( generate ){
					AssociationRole role = selectedAssociation.getReferencedRole();
					referencedRolePropertyField.setText( role.getPropertyName() );
				}
			}

		});

		SWTUtil.fillColumnsWithIndent( referencedRoleCheckBox , columns-1, 20 );
		newLabel(parent, "");//$NON-NLS-1$
		
		referencedRolePropertyLabel = SWTUtil.newLabelWithIndent(parent,  JptUiEntityGenMessages.property, 40 ); //$NON-NLS-1$
		
		referencedRolePropertyField = new Text( parent, SWT.BORDER);
		fillColumns(referencedRolePropertyField, 2);
		referencedRolePropertyField.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				if( selectedAssociation.getReferencedRole() == null )
					return ;
				
				String fieldName = referencedRolePropertyField.getText();
				IStatus status = JavaConventions.validateIdentifier(fieldName,
						JavaCore.VERSION_1_3, JavaCore.VERSION_1_3);
				if( !status.matches(IStatus.ERROR) ){
					if( !fieldName.equals(selectedAssociation.getReferencedRole().getPropertyName()) )
						selectedAssociation.getReferencedRole().setPropertyName(fieldName);
				}
				updateStatus(status);
			}			
		});
		

		Label label = new Label( parent, SWT.NONE);
		label.setText( "" );//$NON-NLS-1$
		label.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));
		
		
		referencedRoleCascadeField = new StringButtonDialogField( new IStringButtonAdapter(){
			public void changeControlPressed(DialogField field) {
				if( editCascade( selectedAssociation.getReferencedRole() ) ){
					referencedRoleCascadeField.setText( selectedAssociation.getReferencedRole().getCascade() );
				}
			}	
		}) ;
		referencedRoleCascadeField.setLabelText( JptUiEntityGenMessages.cascade ); //$NON-NLS-1$
		referencedRoleCascadeField.setButtonLabel(""); //$NON-NLS-1$
		referencedRoleCascadeField.doFillIntoGrid(parent, 3);
		referencedRoleCascadeField.getTextControl(parent).setEditable( false);
		int maxFieldWidth = convertWidthInCharsToPixels(40);
		LayoutUtil.setWidthHint(referencedRoleCascadeField.getTextControl(null), maxFieldWidth );
		Button btn = referencedRoleCascadeField.getChangeControl(null);
		btn.setImage( ImageRepository.getBrowseButtonImage() );
		GridData data = (GridData)btn.getLayoutData();
		data.horizontalAlignment = SWT.BEGINNING;
		data.widthHint = 30;
		btn.setLayoutData(data);
		
		Label labelCtrl  = referencedRoleCascadeField.getLabelControl(parent);
		data = (GridData)labelCtrl.getLayoutData();
		data.horizontalIndent = 40 ;
		labelCtrl.setLayoutData(data);
		
		label = new Label( parent, SWT.NONE);
		label.setText( "" );//$NON-NLS-1$
		label.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL));
		
	}	
	
	private void createDetailPanel(Composite composite ) {
		
		this.detailPanel = new Composite( composite, SWT.NONE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.grabExcessVerticalSpace = false;
		this.detailPanel.setLayoutData(gd);
		this.detailPanelStatckLayout = new StackLayout();
		this.detailPanel.setLayout( this.detailPanelStatckLayout );
		
		emptyPanel = new Composite( detailPanel, SWT.NONE);
		emptyPanel.setLayoutData(new GridData()); 
		detailPanelStatckLayout.topControl = emptyPanel;
		detailPanel.layout();
		
		composite.layout();
	}
	
	
	private Composite createAssociationsEditPanel(Composite composite, int columns) {
		Composite parent = new Composite( composite, SWT.NONE);
		fillColumns(parent, 4);

		createGenerateAssociationCheckbox(parent,columns);
		int nColumns= 4 ;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		parent.setLayout(layout);

		//Cardinality
		cardinalityLabel = new Label(parent, SWT.NONE);
		cardinalityLabel.setText( JptUiEntityGenMessages.cardinality);
		GridData gd = new GridData();
		gd.horizontalIndent = 20;
		cardinalityLabel.setLayoutData( gd );
		
		cardinalityCombo = new Combo(parent, SWT.SINGLE | SWT.READ_ONLY );
		
		fillColumns(cardinalityCombo, 1);
		
		cardinalityCombo.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				selectedAssociation.setCardinality( cardinalityCombo.getText());
				associationList.updateSelectedAssociation();
			}
		});
		
		//Adding a filler column
		Label label = new Label( parent, SWT.NONE);
		label.setText( "");//$NON-NLS-1$
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = 2;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		label.setLayoutData(layoutData);
		
		//Table join condition
		joinConditionLabel = newLabelWithIndent(parent, JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_tableJoin, 20 );
		
		joinConditionText = new Text( parent, SWT.MULTI | SWT.BORDER );
		joinConditionText.setEditable(false);
		joinConditionText.setText(JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_joinedWhen);
		layoutData = new GridData();
		layoutData.horizontalSpan = 2;
		layoutData.verticalAlignment = SWT.TOP;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = false;
		layoutData.heightHint = 50;
		joinConditionText.setLayoutData(layoutData);
		newLabel(parent, "");//$NON-NLS-1$

		//Generate UI controls for ReferrerRole
		createGenerateReferrerRoleControls(parent, columns);

		//Generate UI controls for ReferencedRole
		createGenerateReferencedRoleControls(parent, columns);
		
		
		return parent;
	}

	public boolean editCascade(AssociationRole role) {
		CascadeDialog dlg = CascadeDialog.create(role);
		if (dlg.open() == Window.CANCEL ) {
			return false;
		}
		
		return true;
	}	

	private void createAssociationsListPanel(Composite parent) {
		Composite composite = new Composite( parent, SWT.NULL );
		composite.setLayout( new FillLayout());
		composite.setBackground(new Color(Display.getDefault(),255, 0,0) );
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = 1;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = false;
		layoutData.grabExcessVerticalSpace = true;
		layoutData.widthHint = 400;
		layoutData.heightHint = 400;
		composite.setLayoutData(layoutData);
		
		associationList = new AssociationsListComposite(composite, this);
	}
	
	private void launchNewAssociationsWizard() {
		ORMGenCustomizer customizer = getCustomizer();
		NewAssociationWizard wizard = new NewAssociationWizard(this.jpaProject, customizer );
		
		WizardDialog dialog = new WizardDialog( this.getShell(), wizard);
		dialog.create();
		int returnCode = dialog.open();
		if (returnCode == Window.OK) {
			Association association = wizard.getNewAssociation();
			if( association !=null ){
				customizer.addAssociation(association);
				updateForeignKeyColumnGenProperty(association);
				updateAssociationsListPanel();
			}
		}		
	}
	/**
	 * For user created association:
	 * If association is to be generated, no need to generate the getter/setter for the column itself
	 */
	private void updateForeignKeyColumnGenProperty(Association association) {
		//Need to process MANY_TO_ONE only since the the associations list are optimized to have MANY_TO_ONE
		if( association.isCustom() && association.getCardinality().equals( Association.MANY_TO_ONE ) ){
			boolean generateColumn =  !association.isGenerated();
			//The "MANY" side DB table
			//ORMGenTable table1 = association.getReferrerTable();
			//The "ONE" side DB table
			//ORMGenTable table2 = association.getReferencedTable();
			//The list of foreign key columns in the MANY side, should not be generated
			//The list of primary keys in the ONE side, will be generated
			//List<ORMGenColumn> list2 = association.getReferencedColumns();	
			List<ORMGenColumn> list1 = association.getReferrerColumns();	
			for(ORMGenColumn c : list1 ){
				if( c.isGenerated() != generateColumn){
					if( !generateColumn && c.getDbColumn().isPartOfPrimaryKey() ){
						continue;
					}
					c.setGenerated(generateColumn);
					c.setInsertable(generateColumn);
					c.setUpdateable(generateColumn);
				}
			}
		}
	}

	private void updateAssociationsListPanel() {
			ORMGenCustomizer customizer = getCustomizer();
			//If user changed the connection or schema
			if( this.customizer != customizer ){
				this.customizer = customizer; 
			}
			List<Association> associations = customizer.getAssociations();
			this.associationList.updateAssociations( associations );
	}

	@SuppressWarnings("restriction")
	public void updateAssociationEditPanel(Association association) {
		this.selectedAssociation = association;

		boolean enabled = association.isCustom();
		this.deleteAssociationLink.setEnabled(enabled);
		
		//Create and display the associationsEditPanel if it was hidden before
		if( associationsEditPanel == null ){
			associationsEditPanel = this.createAssociationsEditPanel(this.detailPanel, 4);
		}
		this.detailPanelStatckLayout.topControl = associationsEditPanel;
		this.detailPanel.layout();
		this.detailPanel.getParent().layout();

		//Update the UI controls from the model
		String table1Name = association.getReferrerTableName();
		String table2Name = association.getReferencedTableName();
		String joinTableName = association.getJoinTableName();
		
		boolean isGenerated = association.isGenerated();
		this.generateAssociationCheckBox.setSelection(isGenerated);
		this.referrerRolePropertyLabel.setEnabled( isGenerated );
		this.referrerRolePropertyField.setEnabled( isGenerated );
		this.referrerRoleCheckBox.setEnabled( isGenerated );
		this.referencedRolePropertyLabel.setEnabled( isGenerated );
		this.referencedRolePropertyField.setEnabled( isGenerated );
		this.referencedRoleCheckBox.setEnabled( isGenerated );
		this.cardinalityLabel.setEnabled(isGenerated);
		this.cardinalityCombo.setEnabled(isGenerated);
		this.joinConditionLabel.setEnabled( isGenerated );
		this.joinConditionText.setEnabled( isGenerated );
		
		String cardinality = association.getCardinality();
		if( Association.MANY_TO_MANY.equals( cardinality ) ){
			cardinalityCombo.removeAll();
			cardinalityCombo.add( Association.MANY_TO_MANY);
		}else{
			cardinalityCombo.removeAll();
			cardinalityCombo.add( Association.MANY_TO_ONE);
			cardinalityCombo.add( Association.ONE_TO_ONE);
		}
		
		cardinalityCombo.setText(cardinality);
		cardinalityCombo.setEnabled(enabled);
		
		String text = null;
		//if MTM
		if( Association.MANY_TO_MANY.equals( cardinality ) ){
			text = String.format( JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_setRef, table2Name, table1Name);
		}else{
			text = String.format( JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_entityRef, table2Name, table1Name);
		}
		this.referrerRoleCheckBox.setText(text);

		//if OTO
		if( Association.ONE_TO_ONE.equals( cardinality ) ){
			text = String.format( JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_entityRef, table1Name, table2Name);
		}else{
			text = String.format( JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_setRef, table1Name, table2Name);
		}
		this.referencedRoleCheckBox.setText(text);

		//AssociationRole properties
		AssociationRole referrerRole = association.getReferrerRole();
		if( referrerRole != null){
			this.referrerRoleCheckBox.setSelection( true );
			this.referrerRolePropertyField.setEditable(true);
			this.referrerRolePropertyField.setText(referrerRole.getPropertyName());
			this.referrerRoleCascadeField.setEnabled(true);
			String cascade = referrerRole.getCascade();
			if( cascade!=null ) 
				this.referrerRoleCascadeField.setText( cascade );
			
			//if MTO: 
			if( Association.MANY_TO_ONE.equals( cardinality ) ){
				this.referrerRoleCheckBox.setEnabled( false );
			}
		}else{
			this.referrerRoleCheckBox.setSelection( false );
			this.referrerRolePropertyLabel.setEnabled(false);
			this.referrerRolePropertyField.setEditable(false);
			this.referrerRolePropertyField.setText("");
			this.referrerRoleCascadeField.setEnabled(false);
		}
		
		AssociationRole referencedRole = association.getReferencedRole();
		if( referencedRole != null){
			this.referencedRoleCheckBox.setSelection( true );
			this.referencedRolePropertyLabel.setEnabled(true);
			this.referencedRolePropertyField.setEditable(true);
			this.referencedRolePropertyField.setText(referencedRole.getPropertyName());
			this.referencedRoleCascadeField.setEnabled(true);
			String cascade = referencedRole.getCascade();
			if( cascade!=null ) 
				this.referencedRoleCascadeField.setText(cascade);
		}else{
			this.referencedRoleCheckBox.setSelection( false );
			this.referencedRolePropertyLabel.setEnabled(false);
			this.referencedRolePropertyField.setEditable(false);
			this.referencedRolePropertyField.setText("");
			this.referencedRoleCascadeField.setEnabled(false);
		}
		
		//Join conditions
		updateJoinConditions(association, table1Name, table2Name, joinTableName);
	}

	private void updateJoinConditions(Association association,
			String table1Name, String table2Name, String joinTableName) {
		String text = "%s";
		if( joinTableName == null ){
			StringBuilder strText = new StringBuilder();
			//text = JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_joinedWhen;
			List<String> columnList1 = association.getReferrerColumnNames() ;
			List<String> columnList2 = association.getReferencedColumnNames();
			for( int i=0; i < columnList1.size(); i++){
				strText.append( table1Name + "." + columnList1.get(i) );//$NON-NLS-1$
				strText.append( "=" );//$NON-NLS-1$
				strText.append( table2Name + "." + columnList2.get(i) );//$NON-NLS-1$
				if( i < columnList1.size()-1 )
					strText.append( "\n AND " );//$NON-NLS-1$
			}
			joinConditionText.setText( String.format( text , strText.toString()) ); 
		}else{
			StringBuilder strText = new StringBuilder();
			//text = JptUiEntityGenMessages.GenerateEntitiesWizard_assocEditor_joinedWhen;
			List<String> columnList1 = association.getReferrerColumnNames() ;
			List<String> joinColumnList1 = association.getReferrerJoinColumnNames()  ;
			for( int i=0; i < columnList1.size(); i++){
				strText.append( table1Name + "." + columnList1.get(i) );//$NON-NLS-1$
				strText.append( "=" );//$NON-NLS-1$
				strText.append( joinTableName + "." + joinColumnList1.get(i) );
				strText.append( "\n AND " );//$NON-NLS-1$
			}
			
			List<String> joinTableColumnList2 = association.getReferencedJoinColumnNames();
			List<String> columnList2 = association.getReferencedColumnNames();
			for( int i=0; i < joinTableColumnList2.size(); i++){
				strText.append( joinTableName + "." + joinTableColumnList2.get(i) );
				strText.append( "=" );//$NON-NLS-1$
				strText.append( table2Name + "." + columnList2.get(i) );//$NON-NLS-1$
				if( i < joinTableColumnList2.size()-1 )
					strText.append( "\n AND " );//$NON-NLS-1$
			}
			
			joinConditionText.setText( String.format( text , strText.toString()) ); 
			
		}
	}

	/**
	 * Called when one of referrerRoleCheckBox or referencedRoleCheckBox 
	 * changes value. 
	 *
	 */
	private void directionalityCheckBoxChanged() {
		String dir;
		if (referrerRoleCheckBox.getSelection()) {
			dir = referencedRoleCheckBox.getSelection() ? Association.BI_DI : Association.NORMAL_DI;
		} else {
			if (referencedRoleCheckBox.getSelection()) {
				dir = Association.OPPOSITE_DI;
			} else {
				dir = null;
			}
		}
		if (dir != null) {
			selectedAssociation.setDirectionality(dir);
		} else {
			selectedAssociation.setGenerated(false);
		}
		
		this.associationList.updateSelectedAssociation();
	}	
	
	private ORMGenCustomizer getCustomizer(){
		GenerateEntitiesFromSchemaWizard wizard = (GenerateEntitiesFromSchemaWizard) this.getWizard();
		return wizard.getCustomizer();
	}
	
	
    @Override
    public final void performHelp() 
    {
        this.getHelpSystem().displayHelp( GenerateEntitiesFromSchemaWizard.HELP_CONTEXT_ID );
    }
    
	protected final IWorkbenchHelpSystem getHelpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}
}
