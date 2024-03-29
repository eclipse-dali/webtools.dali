/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.propertypage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.resource.ResourceMappingFile;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectMappingFileDialog;
import org.eclipse.jpt.jpa.ui.wizards.entity.JptJpaUiWizardsEntityMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.preferences.JPAEditorPreferenceInitializer;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.preferences.JPAEditorPreferencesPage;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

@SuppressWarnings("restriction")
public class JPADiagramPropertyPage extends PropertyPage {

	public static final String PROJECT_SETTINGS_PATH = "/.settings/org.eclipse.jpt.jpadiagrameditor.ui";	//$NON-NLS-1$;
	protected IProject project = null;
	protected boolean isJPA10Project = false;

	private Properties editorProps;

	private Text txtDefaultFolderField;
	private Text txtDefaultEntityPackageField;
	private Text txtDefaultTableNamePrefix;
	private Button checkDirectEditAffectsClass;
	private Group groupAccessType;
	private Group groupCollectionType;
	private Button btnFieldBasedAccess;
	private Button btnPropertyBasedAccess;
	private Button btnCollectionType;
	private Button btnListType;
	private Button btnSetType;
	private Button btnMapType;
	private Button checkOneToManyOldStyle;
	
	
	private Label displayNameLabel;
	private Button xmlSupportButton;	
	private Text ormXmlName;
	private Button browseButton;

	private boolean propsModified = false;

	static private final String QUALIFIER 								= "org.eclipse.jpt.jpadiagrameditor.ui";						//$NON-NLS-1$
	static public final QualifiedName PROP_DEFAULT_DIAGRAM_FOLDER		= new QualifiedName(QUALIFIER, "diagramFolder");			//$NON-NLS-1$;
	static public final QualifiedName PROP_DEFAULT_PACKAGE 				= new QualifiedName(QUALIFIER, "defaultPackage");			//$NON-NLS-1$
	static public final QualifiedName PROP_DEFAULT_TABLE_NAME_PREFIX 	= new QualifiedName(QUALIFIER, "defaultTableNamePrefix");	//$NON-NLS-1$;
	static public final QualifiedName PROP_DIRECT_EDIT_AFFECTS_CLASS 	= new QualifiedName(QUALIFIER, "directEditAffectsClass");	//$NON-NLS-1$;
	static public final QualifiedName PROP_ACCESS_TYPE 					= new QualifiedName(QUALIFIER, "accessType");				//$NON-NLS-1$;
	static public final QualifiedName PROP_COLLECTION_TYPE 				= new QualifiedName(QUALIFIER, "collectionType");			//$NON-NLS-1$;
	static public final QualifiedName PROP_ONE_TO_MANY_OLD_STYLE        = new QualifiedName(QUALIFIER, "oneToManyOldStyle");		//$NON-NLS-1$;
	static public final QualifiedName PROP_ORM_XML_FILE_NAME            = new QualifiedName(QUALIFIER, "ormXmlFileName");		//$NON-NLS-1$;
	static public final QualifiedName PROP_SUPPORT_ORM_XML              = new QualifiedName(QUALIFIER, "supportOrmXml");		//$NON-NLS-1$;

	private IPreferenceStore store = JPADiagramEditorPlugin.getDefault().getPreferenceStore();

	@Override
	protected Control createContents(Composite parent) {
		final Composite composite = createCompositeContainer(parent);
		project = (IProject)getElement().getAdapter(IProject.class);
		isJPA10Project = JPAEditorUtil.checkJPAFacetVersion(project, JPAEditorUtil.JPA_PROJECT_FACET_10);
		loadProperties();
		createDefaultFolderControl(composite);
		createDefaultPackageControl(composite);
		createDefaultTableNamePrefixControl(composite);
		createDirectEditAffectsClassControl(composite);
		createAccessTypeControl(composite);
		createDefaultCollectionTypeControl(composite);
		if (!isJPA10Project)
			createOneToManyOldStyleControl(composite);
		
	    createXMLstorageControl(composite);
	    
		Dialog.applyDialogFont(composite);
		validatePage();
		return composite;
	}

	private Composite createCompositeContainer(Composite parent) {
		final Composite composite= new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		composite.setLayout(gl);
		return composite;
	}

	private void createDefaultFolderControl(Composite composite) {
		Label lblDefaultFolder = new Label(composite, SWT.FILL);
		lblDefaultFolder.setText(JPAEditorMessages.JPAEditorPreferencesPage_defaultFolderControlLabel);
		lblDefaultFolder.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_defaultFolderControlTooltip);
		GridData gd = new GridData();
		lblDefaultFolder.setLayoutData(gd);
		txtDefaultFolderField = new Text(composite, SWT.FLAT | SWT.BORDER | SWT.FILL);
		lblDefaultFolder.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_defaultFolderControlTooltip);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.grabExcessHorizontalSpace = true;
		txtDefaultFolderField.setLayoutData(gd);

	    String defaultFolder = editorProps.getProperty(PROP_DEFAULT_DIAGRAM_FOLDER.getLocalName());
	    txtDefaultFolderField.setText(defaultFolder);
	    txtDefaultFolderField.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_defaultFolderControlTooltip);
	    txtDefaultFolderField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
				propsModified = true;
			}
		});
	}


	private void createDefaultPackageControl(Composite composite) {
		Label lblDefaultPackage = new Label(composite, SWT.FILL);
		lblDefaultPackage.setText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultPackageToUse);
		lblDefaultPackage.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultPackageMsg);
		GridData gd = new GridData();
		lblDefaultPackage.setLayoutData(gd);
		txtDefaultEntityPackageField = new Text(composite, SWT.FLAT | SWT.BORDER | SWT.FILL);
		lblDefaultPackage.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultPackageMsg);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.grabExcessHorizontalSpace = true;
		txtDefaultEntityPackageField.setLayoutData(gd);

	    String defaultPackageName = editorProps.getProperty(PROP_DEFAULT_PACKAGE.getLocalName());
		txtDefaultEntityPackageField.setText(defaultPackageName);
		txtDefaultEntityPackageField.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultPackageMsg);
		txtDefaultEntityPackageField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
				propsModified = true;
			}
		});
	}

	private void createDefaultTableNamePrefixControl(Composite composite) {
		Label lblDefaultTableNamePrfix = new Label(composite, SWT.FILL);
		lblDefaultTableNamePrfix.setText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultTableNameLabel);
		lblDefaultTableNamePrfix.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultTableNamePrefixMsg);
		GridData gd = new GridData();
		lblDefaultTableNamePrfix.setLayoutData(gd);
		txtDefaultTableNamePrefix = new Text(composite, SWT.FLAT | SWT.BORDER);
		lblDefaultTableNamePrfix.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultTableNamePrefixMsg);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.grabExcessHorizontalSpace = true;
		txtDefaultTableNamePrefix.setLayoutData(gd);

	    String defaultTableNamePrefix =  editorProps.getProperty(PROP_DEFAULT_TABLE_NAME_PREFIX.getLocalName());
		txtDefaultTableNamePrefix.setText(defaultTableNamePrefix);
		txtDefaultTableNamePrefix.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultTableNamePrefixMsg);
		txtDefaultTableNamePrefix.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
				propsModified = true;
			}
		});
	}

	private void createDirectEditAffectsClassControl(Composite composite) {
		checkDirectEditAffectsClass = new Button(composite, SWT.FLAT | SWT.CHECK);
		checkDirectEditAffectsClass.setText(JPAEditorMessages.JPAEditorPreferencesPage_directEditAffectsClass);
		checkDirectEditAffectsClass.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_directEditAffectsClassTooltip);
		checkDirectEditAffectsClass.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1));

		boolean defaultDirectEditAffectsClass = Boolean.parseBoolean(editorProps.getProperty(PROP_DIRECT_EDIT_AFFECTS_CLASS.getLocalName()));
		checkDirectEditAffectsClass.setSelection(defaultDirectEditAffectsClass);
		checkDirectEditAffectsClass.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propsModified = true;
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	private void createOneToManyOldStyleControl(Composite composite) {
		checkOneToManyOldStyle = new Button(composite, SWT.FLAT | SWT.CHECK);
		checkOneToManyOldStyle.setText(JPAEditorMessages.JPAEditorPropertyPage_oneToManyOldStyle);
		checkOneToManyOldStyle.setToolTipText(JPAEditorMessages.JPAEditorPropertyPage_oneToManyOldStyleTooltip);
		checkOneToManyOldStyle.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 2, 1));

		boolean defaultDirectEditAffectsClass = Boolean.parseBoolean(editorProps.getProperty(PROP_ONE_TO_MANY_OLD_STYLE.getLocalName()));
		checkOneToManyOldStyle.setSelection(defaultDirectEditAffectsClass);
		checkOneToManyOldStyle.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propsModified = true;
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}



	private void createDefaultCollectionTypeControl(Composite composite) {
		groupCollectionType = new Group(composite, 0);
		groupCollectionType.setText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultCollectionTypeSectionTittle);
		groupCollectionType.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultCollectionTypeSectionDescription);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		groupCollectionType.setLayoutData(gd);

		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		groupCollectionType.setLayout(gl);

		String defaultCollectionType = editorProps.getProperty(PROP_COLLECTION_TYPE.getLocalName());

		createCollectionTypeButton(composite, gd, defaultCollectionType);
		createListTypeButton(composite, gd, defaultCollectionType);
		createSetTypeButton(composite, gd, defaultCollectionType);
		createMapTypeButton(composite, gd, defaultCollectionType);

	}

	private void createAccessTypeControl(Composite composite) {
		groupAccessType = new Group(composite, 0);
		groupAccessType.setText(JPAEditorMessages.JPAEditorPreferencesPage_entityAccessTypeButtonGroupLabel);
		groupAccessType.setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_entityAccessTypeButtonGroupTooltip);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 2;
		groupAccessType.setLayoutData(gd);

		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		groupAccessType.setLayout(gl);

		String defaultAccessType = editorProps.getProperty(PROP_ACCESS_TYPE.getLocalName());

		createFieldAccessButton(composite, gd, defaultAccessType);
		createPropertyAccessButton(composite, gd, defaultAccessType);
	}

	private void createCollectionTypeButton(Composite composite, GridData gd, String defaultCollectionType) {
		btnCollectionType = new Button(groupCollectionType, SWT.RADIO | SWT.FLAT);
		btnCollectionType.setText("java.util.&Collection"); //$NON-NLS-1$
		gd = new GridData();
		btnCollectionType.setLayoutData(gd);
		btnCollectionType.setSelection(defaultCollectionType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_COLLECTION_TYPE));
		btnCollectionType.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propsModified = true;
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	private void createListTypeButton(Composite composite, GridData gd, String defaultCollectionType) {
		btnListType = new Button(groupCollectionType, SWT.RADIO | SWT.FLAT);
		btnListType.setText("java.util.&List"); //$NON-NLS-1$
		gd = new GridData();
		btnListType.setLayoutData(gd);
		btnListType.setSelection(defaultCollectionType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_LIST_TYPE));
		btnListType.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propsModified = true;
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	private void createSetTypeButton(Composite composite, GridData gd, String defaultCollectionType) {
		btnSetType = new Button(groupCollectionType, SWT.RADIO | SWT.FLAT);
		btnSetType.setText("java.util.&Set"); //$NON-NLS-1$
		gd = new GridData();
		btnSetType.setLayoutData(gd);
		btnSetType.setSelection(defaultCollectionType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_SET_TYPE));
		btnSetType.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propsModified = true;
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	private void createMapTypeButton(Composite composite, GridData gd, String defaultCollectionType) {
		btnMapType = new Button(groupCollectionType, SWT.RADIO | SWT.FLAT);
		btnMapType.setText("java.util.&Map"); //$NON-NLS-1$
		gd = new GridData();
		btnMapType.setLayoutData(gd);
		btnMapType.setSelection(defaultCollectionType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_MAP_TYPE));
		btnMapType.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propsModified = true;
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	private void createFieldAccessButton(Composite composite, GridData gd, String defaultAccessType) {
		btnFieldBasedAccess = new Button(groupAccessType, SWT.RADIO | SWT.FLAT);
		btnFieldBasedAccess.setText(JPAEditorMessages.JPAEditorPreferencesPage_entityFieldBasedAccessButtonLabel);
		gd = new GridData();
		btnFieldBasedAccess.setLayoutData(gd);
		btnFieldBasedAccess.setSelection(defaultAccessType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_ACCESS_FIELD_BASED));
		btnFieldBasedAccess.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propsModified = true;
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

	private void createPropertyAccessButton(Composite composite, GridData gd, String defaultAccessType) {
		btnPropertyBasedAccess = new Button(groupAccessType, SWT.RADIO | SWT.FLAT);
		btnPropertyBasedAccess.setText(JPAEditorMessages.JPAEditorPreferencesPage_entityPropertyBasedAccessButtonLabel);
		gd = new GridData();
		btnPropertyBasedAccess.setLayoutData(gd);
		btnPropertyBasedAccess.setSelection(defaultAccessType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_ACCESS_PROPERTY_BASED));
		btnPropertyBasedAccess.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				propsModified = true;
			}
			public void widgetDefaultSelected(SelectionEvent e) {}
		});
	}

    @Override
	protected void performDefaults() {
	    String defaultFolder = store.getString(JPAEditorPreferenceInitializer.PROPERTY_DIAGRAM_FOLDER);
		txtDefaultFolderField.setText(defaultFolder);
	    String defaultPackageName = store.getString(JPAEditorPreferenceInitializer.PROPERTY_ENTITY_PACKAGE);
		txtDefaultEntityPackageField.setText(defaultPackageName);
	    String defaultTableNamePrefix =  store.getString(JPAEditorPreferenceInitializer.PROPERTY_TABLE_NAME_PREFIX);
		txtDefaultTableNamePrefix.setText(defaultTableNamePrefix);
	    boolean defaultDirectEditAffectsClass = store.getBoolean(JPAEditorPreferenceInitializer.PROPERTY_DIRECT_EDIT_CLASS_NAME);
		checkDirectEditAffectsClass.setSelection(defaultDirectEditAffectsClass);
		String defaultAccessType = store.getString(JPAEditorPreferenceInitializer.PROPERTY_ENTITY_ACCESS_TYPE);
		btnFieldBasedAccess.setSelection(defaultAccessType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_ACCESS_FIELD_BASED));
		btnPropertyBasedAccess.setSelection(defaultAccessType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_ACCESS_PROPERTY_BASED));
		String defaultCollectionType = store.getString(JPAEditorPreferenceInitializer.PROPERTY_DEFAULT_COLLECTION_TYPE);
		btnCollectionType.setSelection(defaultCollectionType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_COLLECTION_TYPE));
		btnListType.setSelection(defaultCollectionType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_LIST_TYPE));
		btnSetType.setSelection(defaultCollectionType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_SET_TYPE));
		btnMapType.setSelection(defaultCollectionType.equals(JPAEditorPreferenceInitializer.PROPERTY_VAL_MAP_TYPE));
	    boolean defaultOneToManyOldStyle = store.getBoolean(JPAEditorPreferenceInitializer.PROPERTY_ONE_TO_MANY_OLD_STYLE);
	    if (!isJPA10Project)
	    	checkOneToManyOldStyle.setSelection(defaultOneToManyOldStyle);
	    
	    
	    boolean xmlSupport = store.getBoolean(JPAEditorPreferenceInitializer.PROPERTY_DEFAULT_SUPPORT_ORM_XML);
	    if(xmlSupport){
	    	String ormXml = store.getString(JPAEditorPreferenceInitializer.PROPERTY_DEFAULT_ORM_XML_FILE_NAME);
	    	ormXmlName.setText(ormXml);
	    }

	    
		super.performDefaults();
    }

    synchronized protected void validatePage() {
    	IStatus statFolder = JPAEditorPreferencesPage.validateDefaultFolder(txtDefaultFolderField.getText().trim(),
    																		project.getName());
    	IStatus statPack = JPAEditorPreferencesPage.validateDefaultPackage(txtDefaultEntityPackageField.getText().trim());
    	IStatus statPref = JPAEditorPreferencesPage.validateTableNamePrefix(txtDefaultTableNamePrefix.getText().trim());
    	
    	IStatus statOrmXml = validateXmlName(ormXmlName.getText().trim());
    	
    	if (statFolder.getSeverity() == IStatus.ERROR) {
    		setErrorMessage(statFolder.getMessage());
    		setValid(false);
    		return;
    	}
    	if (statPack.getSeverity() == IStatus.ERROR) {
    		setErrorMessage(statPack.getMessage());
    		setValid(false);
    		return;
    	}
    	if (statPref.getSeverity() == IStatus.ERROR) {
    		setErrorMessage(statPref.getMessage());
    		setValid(false);
    		return;
    	}
    	if(statOrmXml.getSeverity() == IStatus.ERROR){
    		setErrorMessage(statOrmXml.getMessage());
    		setValid(false);
    		return;
    	}
    	setErrorMessage(null);
    	setValid(true);
    	if (statPack.getSeverity() == IStatus.WARNING) {
    		setMessage(statPack.getMessage(), IMessageProvider.WARNING);
    		return;
    	}
    	if (statPref.getSeverity() == IStatus.WARNING) {
    		setMessage(statPref.getMessage(), IMessageProvider.WARNING);
    		return;
    	}
    	setMessage(null, IMessageProvider.NONE);
    }

    @Override
	protected void performApply() {
    	saveSettingsIfModified();
    }

    @Override
	public boolean performOk() {
    	return saveSettingsIfModified();
    }

    private boolean saveSettingsIfModified() {
    	if (propsModified) {
    		boolean saved = saveSettings();
    		propsModified = !saved;
    		return saved;
    	}
    	return true;
    }

    private boolean saveSettings() {
    	putNewValsInProps();
		IProject project = (IProject)getElement().getAdapter(IProject.class);
		Path path = new Path(PROJECT_SETTINGS_PATH);
		IFile quartzDataXMLFile = project.getFile(path);
		File file = new File(quartzDataXMLFile.getLocation().toOSString());
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			editorProps.store(os, "This file contains JPA Diagram Editor settings");	//$NON-NLS-1$;
			os.close();
			return true;
		} catch (IOException e) {
			MessageDialog.openError(Display.getCurrent().getActiveShell(),
									JPAEditorMessages.JPADiagramPropertyPage_errSavePropsMsgTitle,
									JPAEditorMessages.JPADiagramPropertyPage_errSavePropsMsgText);
			return false;
		}
	}

	private void putNewValsInProps() {
		editorProps.put(PROP_DEFAULT_DIAGRAM_FOLDER.getLocalName(), txtDefaultFolderField.getText().trim());
		editorProps.put(PROP_DEFAULT_PACKAGE.getLocalName(), txtDefaultEntityPackageField.getText().trim());
		editorProps.put(PROP_DEFAULT_TABLE_NAME_PREFIX.getLocalName(), txtDefaultTableNamePrefix.getText().trim());
		editorProps.put(PROP_DIRECT_EDIT_AFFECTS_CLASS.getLocalName(), "" + checkDirectEditAffectsClass.getSelection());	//$NON-NLS-1$;
		editorProps.put(PROP_ACCESS_TYPE.getLocalName(), btnFieldBasedAccess.getSelection() ? JPAEditorPreferenceInitializer.PROPERTY_VAL_ACCESS_FIELD_BASED : JPAEditorPreferenceInitializer.PROPERTY_VAL_ACCESS_PROPERTY_BASED);
		editorProps.put(PROP_COLLECTION_TYPE.getLocalName(), getCollectionProperty());

		if (!isJPA10Project)
			editorProps.put(PROP_ONE_TO_MANY_OLD_STYLE.getLocalName(), "" + checkOneToManyOldStyle.getSelection());	//$NON-NLS-1$;
		
		editorProps.put(PROP_SUPPORT_ORM_XML.getLocalName(), "" + xmlSupportButton.getSelection()); //$NON-NLS-1$
		editorProps.put(PROP_ORM_XML_FILE_NAME.getLocalName(), ormXmlName.getText().trim());
	}


	private String getCollectionProperty(){
		if (btnCollectionType.getSelection())
			return JPAEditorPreferenceInitializer.PROPERTY_VAL_COLLECTION_TYPE;
		else if (btnListType.getSelection())
			return JPAEditorPreferenceInitializer.PROPERTY_VAL_LIST_TYPE;
		else if (btnSetType.getSelection())
			return JPAEditorPreferenceInitializer.PROPERTY_VAL_SET_TYPE;
		else if (btnMapType.getSelection())
			return JPAEditorPreferenceInitializer.PROPERTY_VAL_MAP_TYPE;
		return null;
	}

	private Properties createDefaultProps() {
		return createDefaultProps(store);
	}

	static private Properties createDefaultProps(IPreferenceStore store) {
		Properties editorDefaultProps = new Properties();
		editorDefaultProps.setProperty(PROP_DEFAULT_DIAGRAM_FOLDER.getLocalName(), store.getString(JPAEditorPreferenceInitializer.PROPERTY_DIAGRAM_FOLDER));
		editorDefaultProps.setProperty(PROP_DEFAULT_PACKAGE.getLocalName(), store.getString(JPAEditorPreferenceInitializer.PROPERTY_ENTITY_PACKAGE));
		editorDefaultProps.setProperty(PROP_DEFAULT_TABLE_NAME_PREFIX.getLocalName(), store.getString(JPAEditorPreferenceInitializer.PROPERTY_TABLE_NAME_PREFIX));
		editorDefaultProps.setProperty(PROP_DIRECT_EDIT_AFFECTS_CLASS.getLocalName(), "" + store.getBoolean(JPAEditorPreferenceInitializer.PROPERTY_DIRECT_EDIT_CLASS_NAME));	//$NON-NLS-1$;
		editorDefaultProps.setProperty(PROP_ACCESS_TYPE.getLocalName(), store.getString(JPAEditorPreferenceInitializer.PROPERTY_ENTITY_ACCESS_TYPE));
		editorDefaultProps.setProperty(PROP_COLLECTION_TYPE.getLocalName(), store.getString(JPAEditorPreferenceInitializer.PROPERTY_DEFAULT_COLLECTION_TYPE));
		editorDefaultProps.setProperty(PROP_ONE_TO_MANY_OLD_STYLE.getLocalName(), "" + store.getBoolean(JPAEditorPreferenceInitializer.PROPERTY_ONE_TO_MANY_OLD_STYLE));	//$NON-NLS-1$;
		
		editorDefaultProps.setProperty(PROP_SUPPORT_ORM_XML.getLocalName(), "" + store.getBoolean(JPAEditorPreferenceInitializer.PROPERTY_DEFAULT_SUPPORT_ORM_XML));	//$NON-NLS-1$;
		editorDefaultProps.setProperty(PROP_ORM_XML_FILE_NAME.getLocalName(), store.getString(JPAEditorPreferenceInitializer.PROPERTY_DEFAULT_ORM_XML_FILE_NAME));


		return editorDefaultProps;
	}


	private void loadProperties() {
		Properties defaultProps = createDefaultProps();
		editorProps = new Properties(defaultProps);

		IProject project = (IProject)getElement().getAdapter(IProject.class);
		Path path = new Path(PROJECT_SETTINGS_PATH);
		IFile editorProjectProperties = project.getFile(path);

		File propertiesFile = new File(editorProjectProperties.getLocation().toOSString());
		if (propertiesFile.exists()) {
			InputStream is = null;
			try {
				is = new FileInputStream(propertiesFile);
				editorProps.load(is);
				is.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	static public Properties loadProperties(IProject project) {
		IPreferenceStore store = JPADiagramEditorPlugin.getDefault().getPreferenceStore();
		Properties defaultProps = createDefaultProps(store);
		Properties editorProps = new Properties(defaultProps);

		Path path = new Path(PROJECT_SETTINGS_PATH);
		IFile editorProjectProperties = project.getFile(path);

		if (editorProjectProperties.getLocation() != null) {
			File propertiesFile = new File(editorProjectProperties.getLocation().toOSString());
			if (propertiesFile.exists()) {
				InputStream is = null;
				try {
					is = new FileInputStream(propertiesFile);
					editorProps.load(is);
					is.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
		return editorProps;
	}

	public static String getDefaultFolder(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return getDefaultFolder(project, props);
	}

	public static String getDefaultPackage(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return getDefaultPackage(project, props);
	}

	public static String getDefaultTablePrefixName(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return getDefaultTablePrefixName(project, props);
	}

	public static boolean doesDirecteEditingAffectClassNameByDefault(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return doesDirecteEditingAffectClassNameByDefault(project, props);
	}

	public static boolean isAccessFieldBased(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return isAccessFieldBased(project, props);
	}

	public static boolean isCollectionType(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return isCollectionType(project, props);
	}

	public static boolean isListType(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return isListType(project, props);
	}

	public static boolean isSetType(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return isSetType(project, props);
	}

	public static boolean isMapType(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return isMapType(project, props);
	}

	public static boolean shouldOneToManyUnidirBeOldStyle(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return shouldOneToManyUnidirBeOldStyle(project, props);
	}
	
	public static String getOrmXmlFileName(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return getOrmXmlFileName(project, props);
	}

	public static boolean doesSupportOrmXml(IProject project) {
		Properties props = JPADiagramPropertyPage.loadProperties(project);
		return doesSupportOrmXml(project, props);
	}
//---------------------------------------------------------------------------------------------------

	public static String getDefaultFolder(IProject project, Properties props) {
		return props.getProperty(PROP_DEFAULT_DIAGRAM_FOLDER.getLocalName());
	}

	public static String getDefaultPackage(IProject project, Properties props) {
		return props.getProperty(PROP_DEFAULT_PACKAGE.getLocalName());
	}

	public static String getDefaultTablePrefixName(IProject project, Properties props) {
		return props.getProperty(PROP_DEFAULT_TABLE_NAME_PREFIX.getLocalName());
	}

	public static boolean doesDirecteEditingAffectClassNameByDefault(IProject project, Properties props) {
		return Boolean.parseBoolean(props.getProperty(PROP_DIRECT_EDIT_AFFECTS_CLASS.getLocalName()));
	}

	public static boolean isAccessFieldBased(IProject project, Properties props) {
		String accessType = props.getProperty(PROP_ACCESS_TYPE.getLocalName());
		return accessType.equals("field");	//$NON-NLS-1$;
	}

	public static boolean isCollectionType(IProject project, Properties props) {
		String accessType = props.getProperty(PROP_COLLECTION_TYPE.getLocalName());
		return accessType.equals("collection");	//$NON-NLS-1$;
	}

	public static boolean isListType(IProject project, Properties props) {
		String accessType = props.getProperty(PROP_COLLECTION_TYPE.getLocalName());
		return accessType.equals("list");	//$NON-NLS-1$;
	}

	public static boolean isSetType(IProject project, Properties props) {
		String accessType = props.getProperty(PROP_COLLECTION_TYPE.getLocalName());
		return accessType.equals("set");	//$NON-NLS-1$;
	}

	public static boolean isMapType(IProject project, Properties props) {
		String accessType = props.getProperty(PROP_COLLECTION_TYPE.getLocalName());
		return accessType.equals("map");	//$NON-NLS-1$;
	}

	public static boolean shouldOneToManyUnidirBeOldStyle(IProject project, Properties props) {
		return Boolean.parseBoolean(props.getProperty(PROP_ONE_TO_MANY_OLD_STYLE.getLocalName()));
	}
	
	public static String getOrmXmlFileName(IProject project, Properties props) {
		return props.getProperty(PROP_ORM_XML_FILE_NAME.getLocalName());
	}
	
	public static boolean doesSupportOrmXml(IProject project, Properties props) {
		return Boolean.parseBoolean(props.getProperty(PROP_SUPPORT_ORM_XML.getLocalName()));
	}
	
	/**
	 * Create the group, which manage entity mapping registration
	 * @param parent the main composite
	 */
	private void createXMLstorageControl(Composite parent) {
		Group group = createGroup(parent, JptJpaUiWizardsEntityMessages.XML_STORAGE_GROUP);
		this.xmlSupportButton = createCheckButton(group, JptJpaUiWizardsEntityMessages.XML_SUPPORT);				
		createBrowseGroup(group, JPAEditorMessages.JPADiagramPropertyPage_ChooseXMLLabel);
		this.xmlSupportButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean isChecked = xmlSupportButton.getSelection();
				enableMappingXMLChooseGroup(isChecked);
				validatePage();
			    propsModified = true;
			}
		});
	}
	
	/**
	 * @param parent the main composite
	 * @param text the name/title of the group
	 * @return the created group
	 */
	private Group createGroup(Composite parent, String text) {
		Group group = new Group(parent, SWT.NONE);		
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);
		groupGridData.horizontalSpan = 3;
		group.setLayoutData(groupGridData);
		group.setLayout(new GridLayout(3, false));
		group.setText(text);	
		group.setToolTipText(JPAEditorMessages.JPADiagramPropertyPage_ChooseXMLGroupTooltip);
		return group;
	}
	
	/**
	 * Create check button
	 * @param parent the main composite - inheritance group
	 * @param text the label of the button
	 * @return the created button
	 */
	private Button createCheckButton(Composite parent, String text) {
		final Button button = new Button(parent, SWT.CHECK);		
		GridData groupGridData = new GridData(GridData.FILL_HORIZONTAL);
		groupGridData.horizontalSpan = 3;
		button.setLayoutData(groupGridData);
		button.setText(text);
		button.setToolTipText(JPAEditorMessages.JPADiagramPropertyPage_ChooseXMLCheckboxTooltip);
		button.setSelection(doesSupportOrmXml(project));
		return button;
	}

	/**
	 * Create XML group
	 * @param parent the main composite
	 * @param label the name of the group
	 * @param property the related property to which this group will be synchronized
	 * @return the created group
	 */
	private void createBrowseGroup(Composite parent, String label) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout(3, false));
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));	

		this.displayNameLabel = new Label(composite, SWT.LEFT);
		this.displayNameLabel.setText(label);
		this.displayNameLabel.setToolTipText(JPAEditorMessages.JPADiagramPropertyPage_ChooseXMLTooltip);
		this.displayNameLabel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));

		this.ormXmlName = new Text(composite, SWT.SINGLE | SWT.BORDER);
		this.ormXmlName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		String ormXmlFileName = editorProps.getProperty(PROP_ORM_XML_FILE_NAME.getLocalName());
		this.ormXmlName.setText(ormXmlFileName);
		this.ormXmlName.setToolTipText(JPAEditorMessages.JPADiagramPropertyPage_ChooseXMLTooltip);
		    
		this.browseButton = new Button(composite, SWT.PUSH);
		this.browseButton.setText(JPAEditorMessages.JPADiagramPropertyPage_ChooseXmlBrowseButton);
		this.browseButton.setToolTipText(JPAEditorMessages.JPADiagramPropertyPage_ChooseXMLTooltip);
		GridData browseButtonData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		browseButtonData.horizontalSpan = 1;
		this.browseButton.setLayoutData(browseButtonData);		
		this.browseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				handleChooseXmlButtonPressed();
				propsModified = true;
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		this.ormXmlName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
				propsModified = true;
			}
		});
		
		enableMappingXMLChooseGroup(xmlSupportButton.getSelection());
	}

	/**
	 * Process browsing when the Browse... button have been pressed. Allow choosing of 
	 * XML for entity mapping registration
	 *  
	 */
	private void handleChooseXmlButtonPressed() {		
		if (project == null) {
			return;
		}
		JpaProject jpaProject = this.getJpaProject(project);
		if (jpaProject == null) {
			return;
		}
		ViewerFilter filter = getDialogViewerFilter(jpaProject);
		ITreeContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new WorkbenchLabelProvider();
		SelectMappingFileDialog dialog = new SelectMappingFileDialog(getShell(), project, labelProvider, contentProvider);
		dialog.setTitle(JptJpaUiWizardsEntityMessages.MAPPING_XML_TITLE);
		dialog.setMessage(JptJpaUiWizardsEntityMessages.CHOOSE_MAPPING_XML_MESSAGE);
		dialog.addFilter(filter);
			
		String ormFileName = this.ormXmlName.getText();
		JptXmlResource resource = jpaProject.getMappingFileXmlResource(new Path(ormFileName));
		IFile initialSelection = (resource != null) ? resource.getFile() : null;
		dialog.setInput(project);

		if (initialSelection != null) {
			dialog.setInitialSelection(initialSelection);
		}
		if (dialog.open() == Window.OK) {
			boolean noNameChange = false;
			if (ormXmlName.getText().equals(dialog.getChosenName())) {
				noNameChange = true;
			}
			ormXmlName.setText(dialog.getChosenName());
			if (noNameChange) {
				this.validatePage();
			}
		}
	}	
	
	
	/**
	 * This method create filter for the browse/add alternative mapping XML 
	 * @return new instance of viewer filter for the SelectMappingXMLDialog
	 */
	protected ViewerFilter getDialogViewerFilter(JpaProject jpaProject) {
		return new XmlMappingFileViewerFilter(jpaProject, ResourceMappingFile.Root.CONTENT_TYPE);
	}
	
	private void enableMappingXMLChooseGroup(boolean enabled) {
		this.displayNameLabel.setEnabled(enabled);
		this.ormXmlName.setEnabled(enabled);
		this.browseButton.setEnabled(enabled);
	}
	
	protected JpaProject getJpaProject(IProject project) {
		return (JpaProject) project.getAdapter(JpaProject.class);
	}
	
	/**
	 * This method will be used to validate the correctness of xml file location. 
	 * This method will accept a null parameter. 
	 */
	private IStatus validateXmlName(String xmlName) {
		if (xmlSupportButton.getSelection()) {
			JptXmlResource ormXmlResource = StringTools.isBlank(xmlName) ? null : getOrmXmlResource(xmlName);
			if (ormXmlResource == null) {
				
				return new Status(IStatus.ERROR, 
						  JPADiagramEditorPlugin.PLUGIN_ID, 
						  JPAEditorMessages.JPADiagramPropertyPage_NotExistsXmlErrorMsg);
			}
			JpaProject jpaProject = this.getJpaProject(project);
			if ((jpaProject == null) || jpaProject.getJpaFile(ormXmlResource.getFile()).getRootStructureNodesSize() == 0) {
				return new Status(IStatus.ERROR, 
						  JPADiagramEditorPlugin.PLUGIN_ID, 
						  JPAEditorMessages.JPADiagramPropertyPage_NotAddedXMLErrorMsg);
			}
		}
		return Status.OK_STATUS;
	}

	private JptXmlResource getOrmXmlResource(String xmlName) {
		JpaProject jpaProject = this.getJpaProject(project);
		return (jpaProject == null) ? null : jpaProject.getMappingFileXmlResource(new Path(xmlName));
	}
}
