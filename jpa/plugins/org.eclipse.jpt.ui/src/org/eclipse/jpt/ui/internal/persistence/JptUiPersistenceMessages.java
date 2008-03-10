/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.persistence;

import org.eclipse.osgi.util.NLS;

/**
 * The localized messages used by the persistence editor.
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class JptUiPersistenceMessages
{
	private static final String BUNDLE_NAME = "jpt_ui_persistence";

	public static String Boolean_False;
	public static String Boolean_True;

	public static String PersistenceEditor_Page_help;

	public static String PersistenceUnitComposite_connection;
	public static String PersistenceUnitComposite_general;

	public static String PersistenceUnitConnectionComposite_database;
	public static String PersistenceUnitConnectionComposite_general;

	public static String PersistenceUnitConnectionDatabaseComposite_description;
	public static String PersistenceUnitConnectionDatabaseComposite_jtaDatasourceName;
	public static String PersistenceUnitConnectionDatabaseComposite_nonJtaDatasourceName;

	public static String PersistenceUnitConnectionGeneralComposite_default;
	public static String PersistenceUnitConnectionGeneralComposite_description;
	public static String PersistenceUnitConnectionGeneralComposite_jta;
	public static String PersistenceUnitConnectionGeneralComposite_serverProvider;
	public static String PersistenceUnitConnectionGeneralComposite_resource_local;
	public static String PersistenceUnitConnectionGeneralComposite_transactionType;

	public static String PersistenceUnitGeneralComposite_general;
	public static String PersistenceUnitGeneralComposite_javaArchives;
	public static String PersistenceUnitGeneralComposite_jpaMappingDescriptors;
	public static String PersistenceUnitGeneralComposite_mappedClasses;
	public static String PersistenceUnitGeneralComposite_persistenceProvider;

	public static String PersistenceUnitJPAMappingDescriptorsComposite_description;
	public static String PersistenceUnitJPAMappingDescriptorsComposite_ormNoName;

	public static String PersistenceUnitMappedClassesComposite_description;
	public static String PersistenceUnitMappedClassesComposite_excludeUnlistedMappedClasses;
	public static String PersistenceUnitMappedClassesComposite_excludeUnlistedMappedClassesWithDefault;
	public static String PersistenceUnitMappedClassesComposite_mappedClassesNoName;

	static {
		NLS.initializeMessages(BUNDLE_NAME, JptUiPersistenceMessages.class);
	}

	private JptUiPersistenceMessages() {
		throw new UnsupportedOperationException();
	}
}
