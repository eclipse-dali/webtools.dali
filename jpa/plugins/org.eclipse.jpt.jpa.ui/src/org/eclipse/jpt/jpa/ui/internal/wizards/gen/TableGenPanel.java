/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.gen;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.jpa.gen.internal.ORMGenTable;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.wizards.gen.JptJpaUiWizardsEntityGenMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The UI panel for setting the default and specific 
 * table entity generation properties.
 * 
 */
public class TableGenPanel
{
	WizardPage wizardPage ; 
	
	private Text classNameField; // used in setting individual table/entity generation only
	
	private Combo idGeneratorCombo;
	private Text sequenceNameField;
	
	private Button entityAccessField;
	private Button entityAccessProperty;
	private Button associationFetchDefault;
	private Button associationFetchEager;
	private Button associationFetchLazy;
	
	private Button collectionTypeSet;
	private Button collectionTypeList;
	
	private Button generateOptionalAnnotations;
	
	private Label sequenceNameNoteLabel; 
	
	private boolean isUpdatingControls;
	
	private ORMGenTable mTable;
	
	protected boolean isDefaultTable = false;
	
	public TableGenPanel(Composite parent, int columns , boolean isDefaultTable, WizardPage wizardPage   ){
		super();
		this.wizardPage = wizardPage;
		this.isDefaultTable = isDefaultTable;
		createTableMappingPropertiesGroup(parent, columns);
		SWTUtil.createLabel(parent, 4, ""); //$NON-NLS-1$
	}
	
	protected void createTableMappingPropertiesGroup(Composite composite, int columns) {
		Group parent = new Group(composite, SWT.NONE );
		parent.setText( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_TABLE_MAPPING);
		parent.setLayout(new GridLayout(columns, false));
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = columns;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = false;
		parent.setLayoutData(layoutData);
		
		createClassNameControl(parent, columns);
		
		createIdGeneratorControls(parent, columns);
		createEntityAccessControls(parent, columns);
		
		//AssociationFetch and CollectionType only available for default table generation
		if ( isDefaultTable ) {
			createAssociationFetchControls(parent, columns);
			createCollectionTypeControls(parent, columns);
			createGenerateOptionalAnnotationControls(parent, columns);
		}
	}
	
	private void createGenerateOptionalAnnotationControls(Group parent, int columns) {
		generateOptionalAnnotations = new Button(parent, SWT.CHECK );
		generateOptionalAnnotations.setText( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_GEN_OPTIONAL_ANNOTATIONS);
		generateOptionalAnnotations.setToolTipText(JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_GEN_OPTIONAL_ANNOTATIONS_DESC);

		GridData gd = new GridData();
		gd.horizontalSpan = columns;
		generateOptionalAnnotations.setLayoutData(gd);
		generateOptionalAnnotations.addSelectionListener(new SelectionListener(){
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				boolean selected = generateOptionalAnnotations.getSelection();
				mTable.setGenerateDDLAnnotations(selected);
			}
		});
	}

	protected void createClassNameControl(Composite parent, int columns) {
		//Customize class name for specific table only
		if ( !isDefaultTable ) {
			SWTUtil.createLabel( parent, 1 , JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_PANEL_CLASS_NAME );
			
			classNameField = new Text(parent, SWT.SINGLE | SWT.BORDER );
			//mPackageNameField.setEditable(false);
			SWTUtil.fillColumns(classNameField,3);
			classNameField.addModifyListener(new ModifyListener(){
				@SuppressWarnings({  "deprecation" })
				public void modifyText(ModifyEvent e) {
					if (e.getSource() == null || !isUpdatingControls) {
						String className = classNameField.getText();
						IStatus status = JavaConventions.validateJavaTypeName( className );
						if( !status.matches(IStatus.ERROR) ){
							mTable.setClassName(  className );
							wizardPage.setErrorMessage(null);
						}else{
							wizardPage.setErrorMessage(status.getMessage());
						}
					}
				}			
			});
		}
	}
	
	class AssociationFetchListener implements SelectionListener{
		public void widgetDefaultSelected(SelectionEvent e) {}
		public void widgetSelected(SelectionEvent e) {
			if (!isUpdatingControls) {
				Button radioBtn = (Button)e.getSource();
				mTable.setDefaultFetch( radioBtn.getData().toString());
			}
		}
	}

	protected void createAssociationFetchControls(Composite composite, int columns) {
		SWTUtil.createLabel(composite, 1, JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_FETCH );
		
		Composite parent = new Composite( composite, SWT.NONE);
		parent.setLayout(new RowLayout());
		SWTUtil.fillColumns( parent , 3);
		associationFetchDefault	= new Button( parent, SWT.RADIO );
		associationFetchDefault.setText( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_FETCH_DEFAULT);
		associationFetchDefault.setData( ORMGenTable.DEFAULT_FETCH );	
		
		associationFetchEager = new Button( parent, SWT.RADIO );
		associationFetchEager.setText( "&Eager"); //$NON-NLS-1$
		associationFetchEager.setData( ORMGenTable.EAGER_FETCH );

		associationFetchLazy = new Button( parent, SWT.RADIO );
		associationFetchLazy.setText( "La&zy"); //$NON-NLS-1$
		associationFetchLazy.setData( ORMGenTable.LAZY_FETCH );
		
		AssociationFetchListener associationFetchListener = new AssociationFetchListener();
		associationFetchDefault.addSelectionListener( associationFetchListener );
		associationFetchLazy.addSelectionListener( associationFetchListener );
		associationFetchEager.addSelectionListener( associationFetchListener );
	}

	class CollectionTypeListener implements SelectionListener {
		public void widgetDefaultSelected(SelectionEvent e) {}
		public void widgetSelected(SelectionEvent e) {
			if (!isUpdatingControls) {
				Button radioBtn = (Button)e.getSource();
				mTable.setDefaultCollectionType( radioBtn.getData().toString());
			}
		}
	}	
	
	protected void createCollectionTypeControls(Composite composite, int columns) {
		SWTUtil.createLabel(composite, 1, JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_COLLECTION_TYPE );
		
		Composite parent = new Composite( composite, SWT.NONE);
		parent.setLayout(new RowLayout());
		SWTUtil.fillColumns( parent , 3);
		
		this.collectionTypeSet = new Button( parent, SWT.RADIO);
		this.collectionTypeSet.setText( "java.util.Se&t");
		this.collectionTypeSet.setData( ORMGenTable.SET_COLLECTION_TYPE );
		this.collectionTypeList = new Button( parent, SWT.RADIO);
		this.collectionTypeList.setText("java.util.&List");
		this.collectionTypeList.setData(ORMGenTable.LIST_COLLECTION_TYPE); 
		
		CollectionTypeListener collectionTypeListener = new CollectionTypeListener();
		collectionTypeList.addSelectionListener( collectionTypeListener );
		collectionTypeSet.addSelectionListener( collectionTypeListener );
	}


	public void setORMGenTable(ORMGenTable table) {
		this.mTable = table;
	}
	
	//should be called after setting the ORMGenTable
	public void updateControls(){
		
		isUpdatingControls = true;
				
		try {
			updateClassNameField();
			udpateGeneratorControls();
			updateAccessControls();
			updateFetchControls();
			
			//DefaultTable only
			updateCollectionTypeControls();
			updateGenerateOptionalAnnotationControls();
			
			
		} catch (Exception e) {
			JptJpaUiPlugin.instance().logError(e);
		}
		
		isUpdatingControls = false;
	}

	private void updateCollectionTypeControls() {
		if (collectionTypeList != null) {
			String cType = this.mTable.getDefaultCollectionType();
			if ( ORMGenTable.LIST_COLLECTION_TYPE.equals( cType ) ) {
				this.collectionTypeList.setSelection( true );
				this.collectionTypeSet.setSelection( false );
			} else {
				this.collectionTypeSet.setSelection( true );
				this.collectionTypeList.setSelection( false );
			}
			

		}
	}
	
	protected void updateGenerateOptionalAnnotationControls(){
		if (generateOptionalAnnotations != null){
			this.generateOptionalAnnotations.setSelection( this.mTable.isGenerateDDLAnnotations());
		}
	}

	private void updateFetchControls() {
		if (associationFetchLazy != null && associationFetchEager != null ) {
			String defaultFetch = this.mTable.getDefaultFetch();
			//reset all three buttons
			associationFetchDefault.setSelection(false);
			associationFetchEager.setSelection(false);
			associationFetchLazy.setSelection(false);
			if( ORMGenTable.DEFAULT_FETCH.equals( defaultFetch ) )
				associationFetchDefault.setSelection(true);
			else if( ORMGenTable.EAGER_FETCH.equals( defaultFetch ) )
				associationFetchEager.setSelection(true);
			else
				associationFetchLazy.setSelection(true);
		}
	}

	protected void updateAccessControls() {
		String access = this.mTable.getAccess() ;
		if (  ORMGenTable.FIELD_ACCESS.equals( access ) ) {
			this.entityAccessField.setSelection( true );
			this.entityAccessProperty.setSelection( false );
		} else {
			this.entityAccessProperty.setSelection( true );
			this.entityAccessField.setSelection( false );
		}
	}

	private void udpateGeneratorControls() {
		final List<String> schemes = this.mTable.getCustomizer().getAllIdGenerators();
		String[] values = new String[schemes.size()];
		schemes.toArray(values);
		idGeneratorCombo.setItems( values );
		String idGenerator = this.mTable.getIdGenerator();
		idGeneratorCombo.setText( idGenerator);
		
		boolean isSequence = this.mTable.getCustomizer().getSequenceIdGenerators().contains(idGenerator);		
		String sequenceName = this.mTable.isDefaultsTable() ? this.mTable.getSequence() : this.mTable.getFormattedSequence();
		sequenceName  = ( sequenceName == null ? "" : sequenceName ); 
		sequenceNameField.setText( sequenceName );
		if ( isSequence ) {
			sequenceNameField.setEnabled(true);
			sequenceNameNoteLabel.setEnabled(true);
		} else {
			sequenceNameField.setEnabled(false);
			sequenceNameNoteLabel.setEnabled(false);
		}
	}

	private void updateClassNameField() {
		//ClassNameField is not available for default table
		if(classNameField!= null )
			classNameField.setText( this.mTable.getClassName() );
	}

	protected void createIdGeneratorControls(Composite parent, int columns) {
		SWTUtil.createLabel(parent, 1, JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_KEY_GEN );

		idGeneratorCombo = new Combo(parent,SWT.SINGLE | SWT.READ_ONLY);
		SWTUtil.fillColumns(idGeneratorCombo, 3);
		
		idGeneratorCombo.addSelectionListener( new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				if (isUpdatingControls) {
					return;
				}
				
				idGenChanged();
			}});

		SWTUtil.createLabel(parent, 1, JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_SEQUENCE );
		sequenceNameField = new Text( parent, SWT.SINGLE | SWT.BORDER );
		
		SWTUtil.fillColumns(sequenceNameField, 3);
		sequenceNameField.addModifyListener(new ModifyListener(){
			public void modifyText(ModifyEvent e) {
				if (e.getSource() == null || !isUpdatingControls) {

					if ( idGeneratorCombo.getText().equals("sequence")) { //$NON-NLS-1$
						String sequenceName = sequenceNameField.getText();
						if ( sequenceName.toLowerCase().indexOf("$table") >= 0 ||  //$NON-NLS-1$
								sequenceName.toLowerCase().indexOf("$pk") >= 0 ) { //$NON-NLS-1$
							sequenceName = convertVarToLowerCase("$table", sequenceName); //$NON-NLS-1$
							sequenceName = convertVarToLowerCase("$pk", sequenceName); //$NON-NLS-1$
						}
						if ( sequenceName.trim().length() != 0 ) {
							mTable.setSequence( sequenceName );
						} else{
							mTable.setSequence( "" ); //$NON-NLS-1$
						}
					}
				}
			}

			private String convertVarToLowerCase(String var, String sequenceName) {
				int n = sequenceName.toLowerCase().indexOf( var );
				if( n==0 ) {
					return var + sequenceName.substring( var.length());
				} else if( n >0 ) {
					return sequenceName.substring(0,n) + var + sequenceName.substring( n + var.length());
				}
				return sequenceName;
			}			
		});
		
		SWTUtil.newLabel(parent, "");//$NON-NLS-1$
		sequenceNameNoteLabel = new Label(parent, SWT.NONE);
		String text =String.format( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_SEQUENCE_NOTE, 
				ORMGenTable.TABLE_SEQ_PATTERN, ORMGenTable.PK_SEQ_PATTERN);
		sequenceNameNoteLabel.setText( text ) ;
		sequenceNameNoteLabel.setEnabled(false);
		SWTUtil.fillColumns( sequenceNameNoteLabel, 3);
	}

	private void idGenChanged() {
		String scheme = idGeneratorCombo.getText();
		mTable.setIdGenerator(scheme);
		
		boolean isSequence = this.mTable.getCustomizer().getSequenceIdGenerators().contains(scheme);		
		if (!isSequence) {
			sequenceNameField.setText("");
			sequenceNameField.setEnabled(false);
			mTable.setSequence(null);
			sequenceNameNoteLabel.setEnabled(false);
		} else {
			sequenceNameField.setEnabled(true);
			Color NOTE_LABEL_COLOR = new Color( sequenceNameField.getDisplay(), 102,102,102);
			sequenceNameNoteLabel.setForeground( NOTE_LABEL_COLOR );
			NOTE_LABEL_COLOR.dispose();
			sequenceNameNoteLabel.setEnabled(true);
			if ( sequenceNameField.getText().length()==0 ) {
				String newMessage = "Please specify a sequence name";
				this.wizardPage.setMessage(newMessage);
				this.wizardPage.setPageComplete(true);
			} else {
				this.wizardPage.setErrorMessage(null);
				this.wizardPage.setPageComplete(true);
			}
		}
	}
	
	class EntityAccessFetchListener implements SelectionListener{
		public void widgetDefaultSelected(SelectionEvent e) {}
		public void widgetSelected(SelectionEvent e) {
			if (!isUpdatingControls) {
				Button radioBtn = (Button)e.getSource();
				mTable.setAccess( radioBtn.getData().toString() );
			}
		}
	}
	
	private void createEntityAccessControls(Composite composite, int columns) {
		SWTUtil.createLabel(composite, 1, JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_ACCESS );
		
		Composite parent = new Composite( composite, SWT.NONE);
		SWTUtil.fillColumns( parent , 3);
		parent.setLayout(new RowLayout());

		entityAccessField = new Button( parent, SWT.RADIO );
		entityAccessField.setText( "&Field" ); //$NON-NLS1$
		entityAccessField.setData( ORMGenTable.FIELD_ACCESS);

		entityAccessProperty = new Button( parent, SWT.RADIO );
		entityAccessProperty.setText(  "&Property" );//$NON-NLS1$
		entityAccessProperty.setData( ORMGenTable.PROPERTY_ACCESS );
		
		EntityAccessFetchListener entityAccessFetchListener = new EntityAccessFetchListener();
		entityAccessField.addSelectionListener( entityAccessFetchListener );
		entityAccessProperty.addSelectionListener( entityAccessFetchListener );
	}
	
}
