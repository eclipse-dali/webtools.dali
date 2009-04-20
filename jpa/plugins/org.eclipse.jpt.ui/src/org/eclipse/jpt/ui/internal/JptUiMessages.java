/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali UI.
 *
 * @version 2.0
 * @since 1.0
 */
public class JptUiMessages {

	public static String ChooserPane_browseButton;
	public static String AccessTypeComposite_access;
	public static String AccessTypeComposite_field;
	public static String AccessTypeComposite_property;
	public static String AddPersistentAttributeDialog_attributeLabel;
	public static String AddPersistentAttributeDialog_mappingLabel;
	public static String AddPersistentAttributeDialog_noMappingKeyError;
	public static String AddPersistentAttributeDialog_title;
	public static String AddPersistentClassDialog_classDialog_message;
	public static String AddPersistentClassDialog_classDialog_title;
	public static String AddPersistentClassDialog_classLabel;
	public static String AddPersistentClassDialog_classNotFoundWarning;
	public static String AddPersistentClassDialog_duplicateClassWarning;
	public static String AddPersistentClassDialog_mappingLabel;
	public static String AddPersistentClassDialog_noClassError;
	public static String AddPersistentClassDialog_noMappingKeyError;
	public static String AddPersistentClassDialog_title;
	public static String AddRemovePane_AddButtonText;
	public static String AddRemovePane_RemoveButtonText;
	public static String ClassChooserPane_dialogMessage;
	public static String ClassChooserPane_dialogTitle;
	public static String DatabaseSchemaWizardPage_title;
	public static String DatabaseSchemaWizardPage_desc;
	public static String DatabaseSchemaWizardPage_schemaSettings;
	public static String DatabaseSchemaWizardPage_addConnectionToProject;
	public static String DatabaseSchemaWizardPage_connectLink;
	public static String DatabaseSchemaWizardPage_schema;
	public static String DatabaseSchemaWizardPage_connectionInfo;
	public static String DatabaseSchemaWizardPage_schemaInfo;
	public static String EnumComboViewer_default;
	public static String EnumComboViewer_defaultWithDefault;
	public static String Error_openingEditor;
	public static String General_browse;
	public static String General_revert;
	public static String General_deselectAll;
	public static String General_selectAll;
	public static String GenerateDDLWizard_title;
	public static String GenerateEntitiesWizard_generateEntities;
	public static String GenerateEntitiesWizardPage_chooseEntityTable;
	public static String GenerateEntitiesWizardPage_entityNameColumn;
	public static String GenerateEntitiesWizardPage_generateEntities;
	public static String GenerateEntitiesWizardPage_synchronizeClasses;
	public static String GenerateEntitiesWizardPage_tableColumn;
	public static String GenerateEntitiesWizardPage_tables;
	public static String GenericPlatformUiDialog_notSupportedMessageText;
	public static String GenericPlatformUiDialog_notSupportedMessageTitle;
	public static String JpaContent_label;
	public static String JpaDetailsView_viewNotAvailable;
	public static String JpaFacetWizardPage_addDriverLibraryLabel;
	public static String JpaFacetWizardPage_connectionLabel;
	public static String JpaFacetWizardPage_connectionLink;
	public static String JpaFacetWizardPage_connectLink;
	public static String JpaFacetWizardPage_createOrmXmlButton;
	public static String JpaFacetWizardPage_defaultCatalogLabel;
	public static String JpaFacetWizardPage_defaultSchemaLabel;
	public static String JpaFacetWizardPage_description;
	public static String JpaFacetWizardPage_discoverClassesButton;
	public static String JpaFacetWizardPage_driverLibraryLabel;
	public static String JpaFacetWizardPage_jpaImplementationLabel;
	public static String JpaFacetWizardPage_jpaPrefsLink;
	public static String JpaFacetWizardPage_listClassesButton;
	public static String JpaFacetWizardPage_none;
	public static String JpaFacetWizardPage_overrideDefaultCatalogLabel;
	public static String JpaFacetWizardPage_overrideDefaultSchemaLabel;
	public static String JpaFacetWizardPage_persistentClassManagementLabel;
	public static String JpaFacetWizardPage_platformLabel;
	public static String JpaFacetWizardPage_specifyLibLabel;
	public static String JpaFacetWizardPage_title;
	public static String JpaFacetWizardPage_userLibsLink;
	public static String JpaFacetWizardPage_userServerLibLabel;
	public static String JpaStructureView_linkWithEditorDesc;
	public static String JpaStructureView_linkWithEditorText;
	public static String JpaStructureView_linkWithEditorTooltip;
	public static String JpaStructureView_structureNotAvailable;
	public static String JpaStructureView_numItemsSelected;
	public static String MappingFileWizard_title;
	public static String MappingFileWizardPage_title;
	public static String MappingFileWizardPage_desc;
	public static String MappingFileWizardPage_projectLabel;
	public static String MappingFileWizardPage_sourceFolderLabel;
	public static String MappingFileWizardPage_filePathLabel;
	public static String MappingFileWizardPage_accessLabel;
	public static String MappingFileWizardPage_addToPersistenceUnitButton;
	public static String MappingFileWizardPage_persistenceUnitLabel;
	public static String MappingFileWizardPage_incorrectSourceFolderError;
	public static String MappingFileWizardPage_accessLabel_sourceFolderDialogTitle;
	public static String MappingFileWizardPage_accessLabel_sourceFolderDialogDesc;
	public static String NewJpaProjectWizard_firstPage_description;
	public static String NewJpaProjectWizard_firstPage_title;
	public static String NewJpaProjectWizard_title;
	public static String OrmItemLabelProviderFactory_entityMappingsLabel;
	public static String OverwriteConfirmerDialog_text;
	public static String OverwriteConfirmerDialog_title;
	public static String PackageChooserPane_dialogMessage;
	public static String PackageChooserPane_dialogTitle;
	public static String PersistenceItemLabelProviderFactory_persistenceLabel;
	public static String PersistentAttributePage_mapAs;
	public static String PersistentTypePage_mapAs;
	public static String EntitiesGenerator_jobName;

	private static final String BUNDLE_NAME = "jpt_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptUiMessages() {
		throw new UnsupportedOperationException();
	}

}
