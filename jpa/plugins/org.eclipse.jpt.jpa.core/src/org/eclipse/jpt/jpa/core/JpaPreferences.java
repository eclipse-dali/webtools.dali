/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.core.internal.utility.JptPlugin;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;

/**
 * Public access to the Dali JPA preferences.
 * <p>
 * Preferences are a cross between public, model-related state and private,
 * plug-in-related state; thus this public facade to state that is
 * (traditionally) scoped by the source code's plug-in location.
 * Another complication is that preferences must be available even when a
 * model is not (yet) present (e.g. for a "creation" wizard).
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 3.2
 */
public final class JpaPreferences {

	// ********** project JPA platform ID **********

	public static String getJpaPlatformID(IProject project) {
		return getPlugin().getPreference(project, JPA_PLATFORM);
	}

	public static void setJpaPlatformID(IProject project, String id) {
		getPlugin().setPreference(project, JPA_PLATFORM, id);
	}

	private static final String ORIGINAL_PLUGIN_ID = getPlugin().getOriginalPluginID();
	private static final String ORIGINAL_PLUGIN_ID_ = ORIGINAL_PLUGIN_ID + '.';
	// not sure why this is qualified... (to be consistent JDT?)
	private static final String JPA_PLATFORM = ORIGINAL_PLUGIN_ID_ + "platform"; //$NON-NLS-1$


	// ********** project discover annotated classes **********

	public static boolean getDiscoverAnnotatedClasses(IProject project) {
		return getPlugin().getBooleanPreference(project, DISCOVER_ANNOTATED_CLASSES);
	}

	public static void setDiscoverAnnotatedClasses(IProject project, boolean value) {
		getPlugin().setBooleanPreference(project, DISCOVER_ANNOTATED_CLASSES, value);
	}

	private static final String PLUGIN_ID = getPlugin().getPluginID();
	private static final String PLUGIN_ID_ = PLUGIN_ID + '.';
	// we mistakenly changed this qualifier...
	private static final String DISCOVER_ANNOTATED_CLASSES = PLUGIN_ID_ + "discoverAnnotatedClasses"; //$NON-NLS-1$


	// ********** project metamodel source folder name **********

	public static String getMetamodelSourceFolderName(IProject project) {
		return getPlugin().getPreference(project, METAMODEL_SOURCE_FOLDER);
	}

	public static void setMetamodelSourceFolderName(IProject project, String name) {
		getPlugin().setPreference(project, METAMODEL_SOURCE_FOLDER, name);
	}

	// we mistakenly used the new qualifier...
	private static final String METAMODEL_SOURCE_FOLDER = PLUGIN_ID_ + "metamodelSourceFolderName"; //$NON-NLS-1$


	// ********** project entity gen default package name **********

	public static String getEntityGenDefaultPackageName(IProject project) {
		return getPlugin().getPreference(project, ENTITY_GEN_DEFAULT_PACKAGE);
	}

	public static void setEntityGenDefaultPackageName(IProject project, String name) {
		getPlugin().setPreference(project, ENTITY_GEN_DEFAULT_PACKAGE, name);
	}

	public static String getEntityGenDefaultPackageName() {
		return getPlugin().getPreference(ENTITY_GEN_DEFAULT_PACKAGE);
	}

	public static void setEntityGenDefaultPackageName(String name) {
		getPlugin().setPreference(ENTITY_GEN_DEFAULT_PACKAGE, name);
	}

	private static final String ENTITY_GEN = "entitygen"; //$NON-NLS-1$
	private static final String ENTITY_GEN_ = ENTITY_GEN + '.';
	private static final String ENTITY_GEN_DEFAULT_PACKAGE = ENTITY_GEN_ + "DEFAULT_PACKAGE"; //$NON-NLS-1$
	private static final String ENTITY_GEN_DEFAULT_PACKAGE_DEFAULT = "model"; //$NON-NLS-1$


	// ********** workspace/project user override default catalog **********

	/**
	 * This preference is set in the workspace's metadata, not in the project's
	 * metadata.
	 */
	public static String getUserOverrideDefaultCatalog(IProject project) {
		return getPlugin().getPersistentProperty(project, USER_OVERRIDE_DEFAULT_CATALOG);
	}

	/**
	 * This preference is set in the workspace's metadata, not in the project's
	 * metadata.
	 */
	public static void setUserOverrideDefaultCatalog(IProject project, String catalog) {
		getPlugin().setPersistentProperty(project, USER_OVERRIDE_DEFAULT_CATALOG, catalog);
	}

	private static final String USER_OVERRIDE_DEFAULT_CATALOG = "userOverrideDefaultCatalogName";  //$NON-NLS-1$


	// ********** workspace/project user override default schema **********

	/**
	 * This preference is set in the workspace's metadata, not in the project's
	 * metadata.
	 */
	public static String getUserOverrideDefaultSchema(IProject project) {
		return getPlugin().getPersistentProperty(project, USER_OVERRIDE_DEFAULT_SCHEMA);
	}

	/**
	 * This preference is set in the workspace's metadata, not in the project's
	 * metadata.
	 */
	public static void setUserOverrideDefaultSchema(IProject project, String schema) {
		getPlugin().setPersistentProperty(project, USER_OVERRIDE_DEFAULT_SCHEMA, schema);
	}

	private static final String USER_OVERRIDE_DEFAULT_SCHEMA = "userOverrideDefaultSchemaName";  //$NON-NLS-1$


	// ********** workspace/project connection profile **********

	/**
	 * This preference is set in the workspace's metadata, not in the project's
	 * metadata.
	 */
	public static String getConnectionProfileName(IProject project) {
		return getPlugin().getPersistentProperty(project, CONNECTION_PROFILE_NAME);
	}

	/**
	 * This preference is set in the workspace's metadata, not in the project's
	 * metadata.
	 */
	public static void setConnectionProfileName(IProject project, String name) {
		getPlugin().setPersistentProperty(project, CONNECTION_PROFILE_NAME, name);
	}

	private static final String CONNECTION_PROFILE_NAME = "dataSource.connectionProfileName";  //$NON-NLS-1$


	// ********** JPQL identifiers lowercase **********

	public static boolean getJpqlIdentifierLowercase() {
		return getJpqlIdentifierLowercase(getPlugin().getPreference(JPQL_IDENTIFIER_CASE));
	}

	public static boolean getJpqlIdentifierLowercaseDefault() {
		return getJpqlIdentifierLowercase(JPQL_IDENTIFIER_CASE_DEFAULT);
	}

	private static boolean getJpqlIdentifierLowercase(String value) {
		return ObjectTools.equals(value, JPQL_IDENTIFIER_CASE_VALUE_LOWERCASE);
	}

	public static void setJpqlIdentifierLowercase(boolean value) {
		getPlugin().setPreference(JPQL_IDENTIFIER_CASE, value ? JPQL_IDENTIFIER_CASE_VALUE_LOWERCASE : JPQL_IDENTIFIER_CASE_VALUE_UPPERCASE);
	}

	private static final String JPQL_IDENTIFIER = "jpqlIdentifier"; //$NON-NLS-1$
	private static final String JPQL_IDENTIFIER_ = JPQL_IDENTIFIER + '.';
	private static final String JPQL_IDENTIFIER_CASE = JPQL_IDENTIFIER_ + "CASE"; //$NON-NLS-1$
	private static final String JPQL_IDENTIFIER_CASE_VALUE_LOWERCASE = "lowercase"; //$NON-NLS-1$
	private static final String JPQL_IDENTIFIER_CASE_VALUE_UPPERCASE = "uppercase"; //$NON-NLS-1$
	private static final String JPQL_IDENTIFIER_CASE_DEFAULT = JPQL_IDENTIFIER_CASE_VALUE_LOWERCASE;


	// ********** JPQL match first character case **********

	public static boolean getJpqlIdentifierMatchFirstCharacterCase() {
		return getPlugin().getBooleanPreference(JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE);
	}

	public static boolean getJpqlIdentifierMatchFirstCharacterCaseDefault() {
		return JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_DEFAULT;
	}

	public static void setJpqlIdentifierMatchFirstCharacterCase(boolean value) {
		getPlugin().setBooleanPreference(JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE, value);
	}

	private static final String JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE = JPQL_IDENTIFIER_ + "MATCH_FIRST_CHARACTER_CASE"; //$NON-NLS-1$
	private static final boolean JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_DEFAULT = true;


	// ********** JPQL query text area **********

	private static final int JPQL_QUERY_TEXT_AREA_NUMBER_OF_LINES_DEFAULT = 5;
	private static final String JPQL_QUERY_TEXT_AREA_NUMBER_OF_LINES = "jpqlQueryEditorNumberOfLines"; //$NON-NLS-1$

	public static int getJpqlQueryTextAreaNumberOfLinesDefault() {
		return JPQL_QUERY_TEXT_AREA_NUMBER_OF_LINES_DEFAULT;
	}

	public static int getJpqlQueryTextAreaNumberOfLines() {
		return getPlugin().getIntPreference(JPQL_QUERY_TEXT_AREA_NUMBER_OF_LINES, JPQL_QUERY_TEXT_AREA_NUMBER_OF_LINES_DEFAULT);
	}

	public static void setJpqlQueryTextAreaNumberOfLines(int value) {
		getPlugin().setIntPreference(JPQL_QUERY_TEXT_AREA_NUMBER_OF_LINES, value);
	}


	// ********** project validation messages **********

	/**
	 * @see JptPlugin#getWorkspaceValidationPreferencesOverridden(IProject)
	 */
	public static boolean getWorkspaceValidationOverridden(IProject project) {
		return getPlugin().getWorkspaceValidationPreferencesOverridden(project);
	}

	/**
	 * @see #getWorkspaceValidationOverridden(IProject)
	 */
	public static void setWorkspaceValidationOverridden(IProject project, boolean value) {
		getPlugin().setWorkspaceValidationPreferencesOverridden(project, value);
	}

	/**
	 * @see JptPlugin#getValidationMessageSeverity(IProject, String, int)
	 */
	public static int getValidationMessageSeverity(IProject project, String messageID, int defaultSeverity) {
		return getPlugin().getValidationMessageSeverity(project, messageID, defaultSeverity);
	}

	/**
	 * @see JptPlugin#getValidationMessageSeverityPreference(IProject, String)
	 */
	public static int getValidationMessageSeverity(IProject project, String messageID) {
		return getPlugin().getValidationMessageSeverityPreference(project, messageID);
	}

	/**
	 * @see #getValidationMessageSeverity(IProject, String)
	 */
	public static void setValidationMessageSeverity(IProject project, String messageID, int value) {
		getPlugin().setValidationMessageSeverityPreference(project, messageID, value);
	}

	/**
	 * @see JptPlugin#getValidationMessageSeverityPreference(String)
	 */
	public static int getValidationMessageSeverity(String messageID) {
		return getPlugin().getValidationMessageSeverityPreference(messageID);
	}

	/**
	 * @see #getValidationMessageSeverity(String)
	 */
	public static void setValidationMessageSeverity(String messageID, int value) {
		getPlugin().setValidationMessageSeverityPreference(messageID, value);
	}


	// ********** misc **********

	/**
	 * Internal: Called <em>only</em> by the
	 * {@link org.eclipse.jpt.jpa.core.internal.JpaPreferenceInitializer#initializeDefaultPreferences()
	 * JPA preferences initializer}.
	 */
	public static void initializeDefaultPreferences() {
		getPlugin().setDefaultPreference(ENTITY_GEN_DEFAULT_PACKAGE, ENTITY_GEN_DEFAULT_PACKAGE_DEFAULT);
		getPlugin().setDefaultPreference(JPQL_IDENTIFIER_CASE, JPQL_IDENTIFIER_CASE_DEFAULT);
		getPlugin().setBooleanDefaultPreference(JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE, JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE_DEFAULT);
	}

	/**
	 * Remove both the project's settings and the workspace settings
	 * related to the project.
	 */
	public static void removePreferences(IProject project) {
		getPlugin().removePreferences(project);
		getPlugin().removePersistentProperties(project);
	}

	public static void removePreferences() {
		getPlugin().removePreferences();
	}

	private static JptPlugin getPlugin() {
		return JptJpaCorePlugin.instance();
	}

	private JpaPreferences() {
		throw new UnsupportedOperationException();
	}
}
