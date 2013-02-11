/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali JPA UI.
 */
public class JptJpaUiMessages {

	private static final String BUNDLE_NAME = "jpt_jpa_ui"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptJpaUiMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	public static String AccessTypeComposite_access;
	public static String AddToEarComposite_earMemberShip;
	public static String AddToEarComposite_addToEarLabel;
	public static String AddToEarComposite_earProjectLabel;
	public static String AddToEarComposite_newButtonLabel;
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
	public static String convertToJpa_convertingProject;
	public static String convertToJpa_detectingTechnologies;
	public static String convertToJpa_installingJpaFacet;
	public static String DatabaseSchemaWizardPage_title;
	public static String DatabaseSchemaWizardPage_desc;
	public static String DatabaseSchemaWizardPage_schemaSettings;
	public static String DatabaseSchemaWizardPage_addConnectionToProject;
	public static String DatabaseSchemaWizardPage_connectLink;
	public static String DatabaseSchemaWizardPage_schema;
	public static String DatabaseSchemaWizardPage_connectionInfo;
	public static String DatabaseSchemaWizardPage_schemaInfo;
	public static String OpenJpaResourceAction_open;
	public static String OpenJpaResourceAction_error;
	public static String General_browse;
	public static String General_revert;
	public static String General_deselectAll;
	public static String General_selectAll;
	public static String General_refresh;
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
	public static String JpaFacetWizardPage_connectedText;
	public static String JpaFacetWizardPage_createOrmXmlButton;
	public static String JpaFacetWizardPage_defaultCatalogLabel;
	public static String JpaFacetWizardPage_defaultSchemaLabel;
	public static String JpaFacetWizardPage_description;
	public static String JpaFacetWizardPage_discoverClassesButton;
	public static String JpaFacetWizardPage_driverLibraryLabel;
	public static String JpaFacetWizardPage_facetsPageLink;
	public static String JpaFacetWizardPage_metamodelSourceFolderLink;
	public static String JpaFacetWizardPage_jpaImplementationLabel;
	public static String JpaFacetWizardPage_jpaPrefsLink;
	public static String JpaFacetWizardPage_listClassesButton;
	public static String JpaFacetWizardPage_metamodelLabel;
	public static String JpaFacetWizardPage_none;
	public static String JpaFacetWizardPage_overrideDefaultCatalogLabel;
	public static String JpaFacetWizardPage_overrideDefaultSchemaLabel;
	public static String JpaFacetWizardPage_persistentClassManagementLabel;
	public static String JpaFacetWizardPage_platformLabel;
	public static String JpaFacetWizardPage_specifyLibLabel;
	public static String JpaFacetWizardPage_title;
	public static String JpaFacetWizardPage_userLibsLink;
	public static String JpaFacetWizardPage_userServerLibLabel;
	public static String JpaJpqlHyperlinkBuilder_OpenDeclaration;
	public static String JpaJpqlHyperlinkBuilder_OpenDeclaredType;
	public static String JpaJpqlJavaCompletionProposalComputer_Error;
	public static String JpaJpqlSseCompletionProposalComputer_Error;
	public static String JpaLibraryProviderInstallPanel_includeLibraries;
	public static String JpaMakePersistentWizardPage_title;
	public static String JpaMakePersistentWizardPage_message;
	public static String JpaMakePersistentWizardPage_annotateInJavaRadioButton;
	public static String JpaMakePersistentWizardPage_mappingFileRadioButton;
	public static String JpaMakePersistentWizardPage_mappingFileLink;
	public static String JpaMakePersistentWizardPage_mappingFileBrowseButton;
	public static String JpaMakePersistentWizardPage_typeTableColumn;
	public static String JpaMakePersistentWizardPage_mappingTableColumn;
	public static String JpaMakePersistentWizardPage_listInPersistenceXmlCheckBox;
	public static String JpaMakePersistentWizardPage_selectedTypesPersistentError;
	public static String JpaMakePersistentWizardPage_mappingFileDoesNotExistError;
	public static String JpaMakePersistentWizardPage_mappingFileNotListedInPersistenceXmlError;
	public static String JpaStructureView_linkWithEditorDesc;
	public static String JpaStructureView_linkWithEditorText;
	public static String JpaStructureView_linkWithEditorTooltip;
	public static String JpaStructureView_structureNotAvailable;
	public static String JpaStructureView_structureProviderNotAvailable;
	public static String JpaStructureView_numItemsSelected;
	public static String JpqlContentProposalProvider_Description;
	public static String MappingFileWizard_title;
	public static String MappingFileWizardPage_newFile_title;
	public static String MappingFileWizardPage_newFile_desc;
	public static String MappingFileWizardPage_options_title;
	public static String MappingFileWizardPage_options_desc;
	public static String MappingFileWizardPage_projectLabel;
	public static String MappingFileWizardPage_sourceFolderLabel;
	public static String MappingFileWizardPage_filePathLabel;
	public static String MappingFileWizardPage_accessLabel;
	public static String MappingFileWizardPage_addToPersistenceUnitButton;
	public static String MappingFileWizardPage_persistenceUnitLabel;
	public static String MappingFileWizardPage_incorrectSourceFolderError;
	public static String MappingFileWizardPage_accessLabel_sourceFolderDialogTitle;
	public static String MappingFileWizardPage_accessLabel_sourceFolderDialogDesc;
	public static String JpaProjectWizard_title;
	public static String NewJpaProjectWizard_firstPage_description;
	public static String NewJpaProjectWizard_firstPage_title;
	public static String OrmItemLabelProviderFactory_entityMappingsLabel;
	public static String OverwriteConfirmerDialog_text;
	public static String OverwriteConfirmerDialog_title;
	public static String PersistenceItemLabelProviderFactory_persistenceLabel;
	public static String EntitiesGenerator_jobName;
	public static String JptPreferencesPage_DoNotShowDialogs;
	public static String JptPreferencesPage_DoNotShowText;
	public static String JptPreferencesPage_ClearButtonText;
	public static String JpaPreferencesPage_description;
	public static String JpaPreferencesPage_entityGen;
	public static String JpaPreferencesPage_entityGen_defaultPackageLabel;
	public static String JpaPreferencesPage_jpqlEditor;
	public static String JpaPreferencesPage_jpqlEditor_description;
	public static String JpaPreferencesPage_jpqlEditor_lowerCaseRadioButton;
	public static String JpaPreferencesPage_jpqlEditor_matchFirstCharacterCaseRadioButton;
	public static String JpaPreferencesPage_jpqlEditor_textAreaNumberOfLines;
	public static String JpaPreferencesPage_jpqlEditor_upperCaseRadioButton;
	public static String JpaProblemSeveritiesPage_Description;
	public static String JpaProblemSeveritiesPage_Error;
	public static String JpaProblemSeveritiesPage_Ignore;
	public static String JpaProblemSeveritiesPage_Info;
	public static String JpaProblemSeveritiesPage_Warning;
	public static String JpaEntityGenPreferencePage_Description;
	public static String JpaEntityGenPreferencePage_generalGroup_title;
	public static String JpaEntityGenPreferencePage_defaultPackageLabel;

	public static String SelectJpaProjectWizardPage_title;
	public static String SelectJpaProjectWizardPage_msg;

	public static String SelectMappingFileDialog_title;
	public static String SelectMappingFileDialog_message;
	public static String SelectMappingFileDialog_newButton;
	public static String SelectMappingFileDialog_newButtonToolTip;

	public static String SynchronizingClasses_TaskName;

	public static String JavaMetadataConversionWizard_title;
	public static String JAVA_METADATA_CONVERSION_warning;
	public static String JAVA_METADATA_CONVERSION_mappingFileDoesNotExist;
	public static String JAVA_METADATA_CONVERSION_mappingFileVersionIsInvalid;
	public static String JAVA_METADATA_CONVERSION_mappingFileNotListedInPersistenceXml;
	public static String JAVA_METADATA_CONVERSION_noGeneratorsToConvert;
	public static String JAVA_METADATA_CONVERSION_noQueriesToConvert;
	public static String JavaMetadataConversionWizardPage_mappingFileBrowseButton;
	public static String JavaMetadataConversionWizardPage_mappingFileBrowseButtonToolTip;
	public static String JavaMetadataConversionWizardPage_label;
	public static String JavaMetadataConversionWizardPage_newMappingFileLink;
	public static String JavaMetadataConversionWizardPage_newMappingFileLinkToolTip;
	public static String JavaGeneratorConversionWizardPage_title;
	public static String JavaGeneratorConversionWizardPage_description;
	public static String JavaQueryConversionWizardPage_title;
	public static String JavaQueryConversionWizardPage_description;

	public static String SetJpaSelection_jobName;

	public static String JpaXmlCompletionProposalComputer_SpecialNameMsg;

	public static String JpaPlatformUi_missingJpaPlatform;

	public static String JpaXmlEditor_page_help;
	public static String JpaXmlEditor_sourcePage;

	private JptJpaUiMessages() {
		throw new UnsupportedOperationException();
	}
}
