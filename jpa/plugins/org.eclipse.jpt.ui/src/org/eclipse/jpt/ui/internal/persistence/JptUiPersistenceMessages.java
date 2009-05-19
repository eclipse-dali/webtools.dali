/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.persistence;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages used by Dali persistence editor.
 *
 * @version 2.0
 * @since 2.0
 */
public class JptUiPersistenceMessages {

	public static String Boolean_False;
	public static String Boolean_True;
	
	public static String ArchiveFileSelectionDialog_jarPathHelpLabel;
	public static String ArchiveFileSelectionDialog_jarPathLabel;

	public static String PersistenceEditor_page_help;
	public static String PersistenceEditor_sourceTab;

	public static String PersistenceUnitClassesComposite_description;
	public static String PersistenceUnitClassesComposite_excludeUnlistedMappedClasses;
	public static String PersistenceUnitClassesComposite_excludeUnlistedMappedClassesWithDefault;
	public static String PersistenceUnitClassesComposite_mappedClassesNoName;
	public static String PersistenceUnitClassesComposite_open;

	public static String PersistenceUnitConnectionComposite_connection;
	public static String PersistenceUnitConnectionComposite_database;
	public static String PersistenceUnitConnectionComposite_general;

	public static String PersistenceUnitConnectionDatabaseComposite_jtaDatasourceName;
	public static String PersistenceUnitConnectionDatabaseComposite_nonJtaDatasourceName;

	public static String PersistenceUnitConnectionGeneralComposite_default;
	public static String PersistenceUnitConnectionGeneralComposite_jta;
	public static String PersistenceUnitConnectionGeneralComposite_resource_local;
	public static String PersistenceUnitConnectionGeneralComposite_transactionType;

	public static String PersistenceUnitGeneralComposite_general;
	public static String PersistenceUnitGeneralComposite_jarFiles;
	public static String PersistenceUnitGeneralComposite_jpaMappingDescriptors;
	public static String PersistenceUnitGeneralComposite_jpaMappingDescriptors_description;
	public static String PersistenceUnitGeneralComposite_mappedClasses;
	public static String PersistenceUnitGeneralComposite_name;
	public static String PersistenceUnitGeneralComposite_persistenceProvider;
	public static String PersistenceUnitGeneralComposite_description;
	
	public static String PersistenceUnitJarFilesComposite_noFileName;
	public static String PersistenceUnitMappingFilesComposite_jarFileDialog_title;
	public static String PersistenceUnitMappingFilesComposite_jarFileDialog_message;
	
	public static String PersistenceUnitMappingFilesComposite_mappingFileDialog_message;
	public static String PersistenceUnitMappingFilesComposite_mappingFileDialog_title;
	public static String PersistenceUnitMappingFilesComposite_ormNoName;

	public static String PersistenceUnitPropertiesComposite_nameColumn;
	public static String PersistenceUnitPropertiesComposite_properties;
	public static String PersistenceUnitPropertiesComposite_properties_description;
	public static String PersistenceUnitPropertiesComposite_valueColumn;

	private static final String BUNDLE_NAME = "jpt_ui_persistence"; //$NON-NLS-1$
	private static final Class<?> BUNDLE_CLASS = JptUiPersistenceMessages.class;
	static {
		NLS.initializeMessages(BUNDLE_NAME, BUNDLE_CLASS);
	}

	private JptUiPersistenceMessages() {
		throw new UnsupportedOperationException();
	}

}
