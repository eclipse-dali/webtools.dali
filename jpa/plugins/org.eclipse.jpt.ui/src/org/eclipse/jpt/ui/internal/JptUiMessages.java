/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
 * The resource strings used by the JPT UI classes.
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class JptUiMessages extends NLS
{
	private static final String BUNDLE_NAME = "jpt_ui";

	public static String AbstractChooserPane_browseButton;
	public static String ClassChooserPane_dialogMessage;
	public static String ClassChooserPane_dialogTitle;
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
	public static String DatabaseReconnectWizardPage_addConnectionLink;
	public static String DatabaseReconnectWizardPage_connection;
	public static String DatabaseReconnectWizardPage_database;
	public static String DatabaseReconnectWizardPage_databaseConnection;
	public static String DatabaseReconnectWizardPage_reconnectLink;
	public static String DatabaseReconnectWizardPage_reconnectToDatabase;
	public static String DatabaseReconnectWizardPage_schema;
	public static String DatabaseReconnectWizardPage_schemaInfo;
	public static String EnumComboViewer_default;
	public static String EnumComboViewer_defaultWithDefault;
	public static String General_browse;
	public static String General_deselectAll;
	public static String General_selectAll;
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
	public static String JpaFacetWizardPage_connectionLabel;
	public static String JpaFacetWizardPage_connectionLink;
	public static String JpaFacetWizardPage_createOrmXmlButton;
	public static String JpaFacetWizardPage_description;
	public static String JpaFacetWizardPage_discoverClassesButton;
	public static String JpaFacetWizardPage_jpaImplementationLabel;
	public static String JpaFacetWizardPage_jpaPrefsLink;
	public static String JpaFacetWizardPage_listClassesButton;
	public static String JpaFacetWizardPage_persistentClassManagementLabel;
	public static String JpaFacetWizardPage_platformLabel;
	public static String JpaFacetWizardPage_specifyLibLabel;
	public static String JpaFacetWizardPage_title;
	public static String JpaFacetWizardPage_userLibsLink;
	public static String JpaFacetWizardPage_userServerLibLabel;
	public static String JpaPreferencePage_defaultJpaLib;
	public static String JpaPreferencePage_invalidJpaLib;
	public static String JpaPreferencePage_userLibsLink;
	public static String JpaStructureView_linkWithEditorDesc;
	public static String JpaStructureView_linkWithEditorText;
	public static String JpaStructureView_linkWithEditorTooltip;
	public static String JpaStructureView_structureNotAvailable;
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
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, JptUiMessages.class);
	}

	private JptUiMessages() {
		throw new UnsupportedOperationException();
	}
}