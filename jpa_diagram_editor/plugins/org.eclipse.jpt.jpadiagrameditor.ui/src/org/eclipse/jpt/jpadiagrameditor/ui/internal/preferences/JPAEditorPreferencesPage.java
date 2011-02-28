/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.preferences;

import java.text.MessageFormat;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.JavaConventions;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditor;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.i18n.JPAEditorMessages;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

@SuppressWarnings("restriction")
public class JPAEditorPreferencesPage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {

	private static final String 	FICTIVE_PROJECT_NAME = "PROJECT NAME";		//$NON-NLS-1$ 
	private static final String 	COLLECTIION_TYPE = "java.util.Collection"; //$NON-NLS-1$
	private static final String 	LIST_TYPE = "java.util.List"; //$NON-NLS-1$
	private static final String 	SET_TYPE = "java.util.Set"; //$NON-NLS-1$
	
	private StringFieldEditor 		fDefaultDiagramFolderField;
	private StringFieldEditor 		fDefaultEntityPackageField;
	private StringFieldEditor 		fDefaultTableNamePrefix;
	private JPABooleanFieldEditor 	directEditAffectsClass;
	private RadioGroupFieldEditor 	entityAccessTypeChooser;
	private RadioGroupFieldEditor   defaultCollectionTypeChooser;
	private JPABooleanFieldEditor 	oneToManyOldStyle;

	public JPAEditorPreferencesPage() {
		super(GRID);
		setPreferenceStore(JPADiagramEditorPlugin.getDefault()
				.getPreferenceStore());
		setDescription(JPAEditorMessages.JPAEditorPreferencesPage_pageDescription);
	}

	public void init(IWorkbench workbench) {
	}
		
	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		createFolderField(parent);
		createEntityPackageField(parent);
		createTableNameField(parent);
		
		directEditAffectsClass = new JPABooleanFieldEditor("",	//$NON-NLS-1$ 
				JPAEditorMessages.JPAEditorPreferencesPage_directEditAffectsClass, 
				BooleanFieldEditor.DEFAULT, parent);
		directEditAffectsClass.setPreferenceName(JPAEditorPreferenceInitializer.PROPERTY_DIRECT_EDIT_CLASS_NAME);
		directEditAffectsClass.getCheckBox(parent).setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_directEditAffectsClassTooltip);
		addField(directEditAffectsClass);		
		entityAccessTypeChooser = new RadioGroupFieldEditor(
				JPAEditorPreferenceInitializer.PROPERTY_ENTITY_ACCESS_TYPE,
				JPAEditorMessages.JPAEditorPreferencesPage_entityAccessTypeButtonGroupLabel,
				1,
			    new String[][] {
					{JPAEditorMessages.JPAEditorPreferencesPage_entityFieldBasedAccessButtonLabel, JPAEditorPreferenceInitializer.PROPERTY_VAL_ACCESS_FIELD_BASED},
					{JPAEditorMessages.JPAEditorPreferencesPage_entityPropertyBasedAccessButtonLabel, JPAEditorPreferenceInitializer.PROPERTY_VAL_ACCESS_PROPERTY_BASED}},				
					parent, true);
		entityAccessTypeChooser.setPreferenceStore(getPreferenceStore());
		entityAccessTypeChooser.getRadioBoxControl(parent).setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_entityAccessTypeButtonGroupTooltip);
		addField(entityAccessTypeChooser);
		
		
		
		defaultCollectionTypeChooser = new RadioGroupFieldEditor(
				JPAEditorPreferenceInitializer.PROPERTY_DEFAULT_COLLECTION_TYPE,
				JPAEditorMessages.JPAEditorPreferencesPage_DefaultCollectionTypeSectionTittle,
				1,
			    new String[][] {
					{COLLECTIION_TYPE, JPAEditorPreferenceInitializer.PROPERTY_VAL_COLLECTION_TYPE},
					{LIST_TYPE, JPAEditorPreferenceInitializer.PROPERTY_VAL_LIST_TYPE},		
					{SET_TYPE, JPAEditorPreferenceInitializer.PROPERTY_VAL_SET_TYPE}},
					parent, true);
		defaultCollectionTypeChooser.setPreferenceStore(getPreferenceStore());
		defaultCollectionTypeChooser.getRadioBoxControl(parent).setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultCollectionTypeSectionDescription);
		addField(defaultCollectionTypeChooser);
		
		oneToManyOldStyle = new JPABooleanFieldEditor("",	//$NON-NLS-1$ 
				JPAEditorMessages.JPAEditorPreferencesPage_oneToManyOldStyle, 
				BooleanFieldEditor.DEFAULT, parent);
		oneToManyOldStyle.setPreferenceName(JPAEditorPreferenceInitializer.PROPERTY_ONE_TO_MANY_OLD_STYLE);
		oneToManyOldStyle.getCheckBox(parent).setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_oneToManyOldStyleTooltip);
		addField(oneToManyOldStyle);		
	}
	
	protected void createFolderField(Composite parent) {
		fDefaultDiagramFolderField = new CustomStringFieldEditor(
				JPAEditorPreferenceInitializer.PROPERTY_DIAGRAM_FOLDER,
				JPAEditorMessages.JPAEditorPreferencesPage_defaultFolderControlLabel, getFieldEditorParent()) {
					protected IStatus validateValue() {
						return validateDefaultFolder(IPath.SEPARATOR + FICTIVE_PROJECT_NAME + IPath.SEPARATOR
													 + fDefaultDiagramFolderField.getStringValue().trim(), FICTIVE_PROJECT_NAME);
					}
				};
				
		fDefaultDiagramFolderField.getTextControl(parent).addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
		
		fDefaultDiagramFolderField.getLabelControl(parent).
		setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_defaultFolderControlTooltip);
		addField(fDefaultDiagramFolderField);
		
		
		fDefaultEntityPackageField = new CustomStringFieldEditor(
				JPAEditorPreferenceInitializer.PROPERTY_ENTITY_PACKAGE,
				JPAEditorMessages.JPAEditorPreferencesPage_DefaultPackageToUse, getFieldEditorParent()) {
					protected IStatus validateValue() {
						return validateDefaultPackage(fDefaultEntityPackageField.getStringValue().trim());
					}	
				};
	}
	
	protected void createEntityPackageField(Composite parent) {
		fDefaultEntityPackageField.getTextControl(parent).addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
			
		fDefaultEntityPackageField.getLabelControl(parent).
				setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultPackageMsg);
		addField(fDefaultEntityPackageField);		
	}
	
	protected void createTableNameField(Composite parent) {
		fDefaultTableNamePrefix = new CustomStringFieldEditor(
				JPAEditorPreferenceInitializer.PROPERTY_TABLE_NAME_PREFIX,
				JPAEditorMessages.JPAEditorPreferencesPage_DefaultTableNameLabel, getFieldEditorParent()) {
					protected IStatus validateValue() {
						return validateTableNamePrefix(fDefaultTableNamePrefix.getStringValue().trim());
					}
				};
				fDefaultTableNamePrefix.getTextControl(parent).addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validatePage();
			}
		});
				
		fDefaultTableNamePrefix.getLabelControl(parent).
				setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultTableNamePrefixMsg);
		addField(fDefaultTableNamePrefix);
		
		
		
		fDefaultTableNamePrefix.getTextControl(parent).
				setToolTipText(JPAEditorMessages.JPAEditorPreferencesPage_DefaultTableNamePrefixMsg);		
	}
	
	
	
	
	public static IStatus validateDefaultFolder(String defaultFolder, String projectName) {
		defaultFolder = defaultFolder.trim();
		if (StringTools.stringIsEmpty(defaultFolder)) 
			return new Status(IStatus.ERROR, 
					  JPADiagramEditorPlugin.PLUGIN_ID, 
					  JPAEditorMessages.JPAEditorPreferencesPage_emptyFolder);
		defaultFolder = IPath.SEPARATOR + projectName + IPath.SEPARATOR + defaultFolder; 
		if (!defaultFolder.startsWith(IPath.SEPARATOR + projectName + IPath.SEPARATOR))
			return new Status(IStatus.ERROR, 
							  JPADiagramEditorPlugin.PLUGIN_ID, 
							  MessageFormat.format(JPAEditorMessages.JPAEditorPreferencesPage_invalidFolder, 
									  			   IPath.SEPARATOR + projectName + IPath.SEPARATOR));
		IStatus res = ResourcesPlugin.getWorkspace().validatePath(defaultFolder, IResource.FOLDER);
		return res;
	}
		
	public static IStatus validateDefaultPackage(String defaultPackage) {
		IStatus validateDefaultPackageStatus = JavaConventions
				.validatePackageName(defaultPackage, JavaCore.VERSION_1_5,
						JavaCore.VERSION_1_5);
		if (validateDefaultPackageStatus.getSeverity() != IStatus.OK) 
			return validateDefaultPackageStatus; 
		return new Status(IStatus.OK, JPADiagramEditor.ID, null);
	}
	
	public static IStatus validateTableNamePrefix(String tableNamePrefix) {
		String s = tableNamePrefix;
		if (StringTools.stringIsEmpty(s))
			return new Status(IStatus.OK, JPADiagramEditor.ID, null);
		if (Character.isDigit(s.charAt(0))) {
			String message = MessageFormat.format(JPAEditorMessages.JPAEditorPreferencesPage_invalidTableNamePrefix, 
					tableNamePrefix);
			return new Status(IStatus.ERROR, JPADiagramEditor.ID, message);						
		}		
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (!Character.isLetterOrDigit(ch) && (ch != '_')) {
				String message = MessageFormat.format(JPAEditorMessages.JPAEditorPreferencesPage_invalidTableNamePrefix, 
						tableNamePrefix);
				return new Status(IStatus.ERROR, JPADiagramEditor.ID, message);						
			}	
		}
		return new Status(IStatus.OK, JPADiagramEditor.ID, null);	
	}	
	
	public abstract class CustomStringFieldEditor extends StringFieldEditor {
		public CustomStringFieldEditor(String propertyName,
									   String labelName,
									   Composite fieldEditorParent) {
			
			super(propertyName, labelName, fieldEditorParent);
		}
		
		String msg;
		String prefixMsg;
		
		protected boolean checkState() {
			IStatus stat = validateValue();
			if (stat.getSeverity() == IStatus.ERROR) {
				return false;
			}
			if (stat.getSeverity() == IStatus.WARNING) {
				return true;
			}
			return true;			
		}
		
		protected boolean doCheckState() {
			return true;
		}	
				
		abstract protected IStatus validateValue();
	}
	
	
	public class JPABooleanFieldEditor extends BooleanFieldEditor {
		public JPABooleanFieldEditor(String name, String labelText, int style, Composite parent) {
			super(name, labelText, style, parent);
		}
				
		public Button getCheckBox(Composite parent) {
			return getChangeControl(parent);
		}		
	}
	
    synchronized protected void validatePage() {
    	
    	IStatus statFolder = JPAEditorPreferencesPage.
    							validateDefaultFolder(fDefaultDiagramFolderField.getTextControl(getFieldEditorParent()).getText().trim(), 
    							FICTIVE_PROJECT_NAME);
    	IStatus statPack = JPAEditorPreferencesPage.
    							validateDefaultPackage(fDefaultEntityPackageField.getTextControl(getFieldEditorParent()).getText().trim());
    	
    	IStatus statPref = JPAEditorPreferencesPage.
    							validateTableNamePrefix(fDefaultTableNamePrefix.getTextControl(getFieldEditorParent()).getText().trim());
    	
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
	
}
