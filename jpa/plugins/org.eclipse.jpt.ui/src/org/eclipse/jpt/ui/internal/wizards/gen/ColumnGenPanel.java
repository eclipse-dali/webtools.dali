/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.gen.internal.ORMGenColumn;
import org.eclipse.jpt.gen.internal.ORMGenCustomizer;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

/**
 * The panel used in the <code>TablesAndColumnsPage</code> wizard page 
 * to edit the column generation properties.
 * An instance of this class is created by the <code>ORMGenWizard</code> 
 * implementation.

 */
public class ColumnGenPanel 
{
	WizardPage wizardPage ; 
	Composite parent;	//parent control with grid layout
	int columns; 		//total columns in the parent composite
	
	ORMGenCustomizer customizer;
	
	private ORMGenColumn mColumn;
	private boolean mPanelInited;
	private boolean mIsUpdatingControls;
	
	private Group columnMappingGroup;
	private Button mGeneratedCheckbox;
	private Text mPropNameField;
	private Combo mMappingKindCombo;
	private Combo mPropTypeCombo;
	private Button mUpdateableCheckBox;
	private Button mInsertableCheckBox;
	
	private Group domainClassGroup ;
	private ScopePanel mPropGetScopePanel;
	private ScopePanel mPropSetScopePanel;
	
	public ColumnGenPanel(Composite parent, int columns, ORMGenCustomizer customizer, WizardPage wizardPage ) {
		this.wizardPage = wizardPage;
		this.customizer = customizer;
		this.parent =parent;
		this.columns = columns;
		
		initPanel();
	}
	/**
	 * Changes the table edited by the panel.
	 * This is supposed to update the panel editing controls 
	 * using the column values.
	 */
	public void setColumn(ORMGenColumn column) {
		mColumn = column;
		
		/*lazy init panel because it uses mColumn*/
		if (!mPanelInited) {
			initPanel();
			mPanelInited = true;
		}
		
		updateControls();
	}
	private void updateControls() {
		if (mIsUpdatingControls) {
			return;
		}
		
		mIsUpdatingControls = true;
		boolean isGenerated = mColumn.isGenerated();
		mGeneratedCheckbox.setSelection( isGenerated);
		mGeneratedCheckbox.setEnabled(true);
		enableControls(isGenerated);
		try {
			mPropNameField.setText(mColumn.getPropertyName());

			mPropTypeCombo.setText( mColumn.getPropertyType());
			
			mMappingKindCombo.setText( mColumn.getMappingKind());
			
			mUpdateableCheckBox.setSelection( mColumn.isUpdateable());
			
			mInsertableCheckBox.setSelection(mColumn.isInsertable());
			
			mPropGetScopePanel.enableComponents(isGenerated);
			mPropGetScopePanel.setScope(mColumn.getPropertyGetScope());
			
			mPropSetScopePanel.enableComponents( isGenerated );
			mPropSetScopePanel.setScope(mColumn.getPropertySetScope());

			if( mColumn.isPartOfCompositePrimaryKey()){
				enableControls(false);
				mPropNameField.setEnabled(true);
				mGeneratedCheckbox.setEnabled(false);
			}
			
		} catch (Exception e) {
			JptUiPlugin.log(e);
		}

		mIsUpdatingControls = false;
	}
	private void enableControls(boolean isGenerated) {
		Control[] controls = this.domainClassGroup.getChildren();
		for( Control c: controls){
			c.setEnabled( isGenerated );
		}

		controls = this.columnMappingGroup.getChildren();
		for( Control c: controls){
			c.setEnabled( isGenerated );
		}
	}
	/**
	 * Initializes the panel by adding the editing controls.
	 * @param columns 
	 * @param parent 
	 */
	protected void initPanel() {
		createControls(parent, columns);
		this.mPanelInited = true;
	}
	
	//-------------------------------------------
	//----- ScopePanel class --------------------
	//-------------------------------------------
	/**
	 * A panel containing 3 radios (public, protected, private)
	 */
	private class ScopePanel 
	{
		private Button mPublicRadio;
		private Button mProtectedRadio;
		private Button mPrivateRadio;
		
		public ScopePanel(Composite comp, SelectionListener listener) {
			//super(3, 20/*hspacing*/, 0/*vspacing*/);
			
			Composite radioGroup = new Composite( comp, SWT.NONE);
			radioGroup.setLayout(new GridLayout(3, true));
			GridData gd = new GridData();
			gd.horizontalSpan = 3;
			radioGroup.setLayoutData(gd);
			
			/*string not localized intentionally, they are used as the actual 
			 * scope value (see getText() usage)*/
			mPublicRadio = new Button( radioGroup, SWT.RADIO );
			mPublicRadio.setText( "public");
			mPublicRadio.setLayoutData(new GridData());
			mProtectedRadio = new Button( radioGroup, SWT.RADIO );
			mProtectedRadio.setText("protected");
			mProtectedRadio.setLayoutData(new GridData());
			mPrivateRadio = new Button(radioGroup, SWT.RADIO );
			mPrivateRadio.setText( "private");
			mPrivateRadio.setLayoutData(new GridData());
			
			mPublicRadio.addSelectionListener(listener);
			mProtectedRadio.addSelectionListener(listener);
			mPrivateRadio.addSelectionListener(listener);
				
		}
		public void enableComponents(boolean b) {
			mPublicRadio.setEnabled(b);
			mProtectedRadio.setEnabled(b);
			mPrivateRadio.setEnabled(b);
		}
		/**
		 * Returns the currently selected scope.
		 */
		public String getScope() {
			Button radio = null;
			if (mPublicRadio.getSelection()) {
				radio = mPublicRadio;
			} else if (mProtectedRadio.getSelection() ) {
				radio = mProtectedRadio;
			} else if (mPrivateRadio.getSelection() ) {
				radio = mPrivateRadio;
			}
			return radio != null ? radio.getText() : null;
		}
		public void setScope(String scope) {
			mPublicRadio.setSelection(false);
			mProtectedRadio.setSelection(false);
			mPrivateRadio.setSelection(false);
			if( scope == null )
				return;
			if (scope.equals(ORMGenColumn.PUBLIC_SCOPE)) {
				mPublicRadio.setSelection(true);
			} else if (scope.equals(ORMGenColumn.PROTECTED_SCOPE)) {
				mProtectedRadio.setSelection(true);
			} else if (scope.equals(ORMGenColumn.PRIVATE_SCOPE)) {
				mPrivateRadio.setSelection(true);
			}
		}
	}
	
	//-------------------------------------------
	//----- private methods ---------------------
	//-------------------------------------------
	private void createControls(Composite composite, int columns) {
		mGeneratedCheckbox = new Button(composite, SWT.CHECK);
		mGeneratedCheckbox.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_colPanel_genProp);
		mGeneratedCheckbox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if (!mIsUpdatingControls) {
					mColumn.setGenerated(mGeneratedCheckbox.getSelection() );
					updateControls();
				}
			}});
		SWTUtil.fillColumns(mGeneratedCheckbox, columns);
		
		columnMappingGroup = new Group( composite, SWT.NONE);
		columnMappingGroup.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_colPanel_colMapping);
		columnMappingGroup.setLayout(new GridLayout(columns, false));
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = columns;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = false;
		layoutData.horizontalIndent = 20 ;
		columnMappingGroup.setLayoutData(layoutData);
		
		SWTUtil.createLabel(columnMappingGroup, 1, JptUiEntityGenMessages.GenerateEntitiesWizard_colPanel_propName );
		mPropNameField = new Text(columnMappingGroup, SWT.BORDER | SWT.SINGLE );
		mPropNameField.addModifyListener(new ModifyListener(){
			@SuppressWarnings("restriction")
			public void modifyText(ModifyEvent e) {
				if (!mIsUpdatingControls) {
					String fldName = mPropNameField.getText();
					IStatus status = JavaConventions.validateIdentifier( fldName, 
						JavaCore.VERSION_1_3, JavaCore.VERSION_1_3 );
					if( !status.matches(IStatus.ERROR)){
						mColumn.setPropertyName(fldName);
						wizardPage.setErrorMessage(null);
					}else{
						wizardPage.setErrorMessage(status.getMessage());
					}
				}
			}			
		});
		SWTUtil.fillColumns(mPropNameField ,3);
		
		SWTUtil.createLabel(columnMappingGroup, 1, JptUiEntityGenMessages.GenerateEntitiesWizard_colPanel_propType );
		mPropTypeCombo = new Combo(columnMappingGroup, SWT.SINGLE | SWT.READ_ONLY);
		mPropTypeCombo.setItems( this.customizer.getAllPropertyTypes());
		mPropTypeCombo.addModifyListener( new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				if (!mIsUpdatingControls) {
					mColumn.setPropertyType(mPropTypeCombo.getText());
				}
			}
		});
		SWTUtil.fillColumns(mPropTypeCombo,3);
		
		SWTUtil.createLabel(columnMappingGroup, 1, JptUiEntityGenMessages.GenerateEntitiesWizard_colPanel_mapKind );
		mMappingKindCombo = new Combo(columnMappingGroup, SWT.SINGLE | SWT.READ_ONLY);
		mMappingKindCombo.setItems( this.customizer.getAllMappingKinds());
		mMappingKindCombo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if (!mIsUpdatingControls) {
					mColumn.setMappingKind((String)mMappingKindCombo.getText());
				}
				
			}});
		SWTUtil.fillColumns(mMappingKindCombo ,3);
		
		mUpdateableCheckBox = new Button(columnMappingGroup, SWT.CHECK);
		mUpdateableCheckBox.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_colPanel_colUpdateable);
		mUpdateableCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if (!mIsUpdatingControls) {
					mColumn.setUpdateable(mUpdateableCheckBox.getSelection() );
				}
			}});
		SWTUtil.fillColumns(mUpdateableCheckBox ,4);
		
		mInsertableCheckBox = new Button(columnMappingGroup, SWT.CHECK);
		mInsertableCheckBox.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_colPanel_colInsertable);
		mInsertableCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if (!mIsUpdatingControls) {
					mColumn.setInsertable(mInsertableCheckBox.getSelection());
				}
			}});
		SWTUtil.fillColumns(mInsertableCheckBox ,4);
		
		SWTUtil.createLabel(composite, 4,""); 
		
		createJavaBeanPropertyControls(composite, columns);
	}
	
	void createJavaBeanPropertyControls(Composite composite, int columns){
		//Java class generation properties 
		domainClassGroup = new Group(composite, SWT.NONE);
		domainClassGroup.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_colPanel_beanProp );
		domainClassGroup.setLayout(new GridLayout(columns, false));
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = columns;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = false;
		layoutData.horizontalIndent = 20;
		domainClassGroup.setLayoutData(layoutData);

		SWTUtil.createLabel(domainClassGroup, 1, JptUiEntityGenMessages.GenerateEntitiesWizard_colPanel_getterScope );
		mPropGetScopePanel = new ScopePanel(domainClassGroup, new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				if (!mIsUpdatingControls) {
					if( ((Button)e.getSource()).getSelection() )
						mColumn.setPropertyGetScope(mPropGetScopePanel.getScope());
				}
				
			}});
		
		SWTUtil.createLabel(domainClassGroup, 1, JptUiEntityGenMessages.GenerateEntitiesWizard_colPanel_setterScope );
		mPropSetScopePanel = new ScopePanel(domainClassGroup, new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				if (!mIsUpdatingControls) {
					if( ((Button)e.getSource()).getSelection() )
						mColumn.setPropertySetScope(mPropSetScopePanel.getScope());
				}
			}});
		
	}
}
