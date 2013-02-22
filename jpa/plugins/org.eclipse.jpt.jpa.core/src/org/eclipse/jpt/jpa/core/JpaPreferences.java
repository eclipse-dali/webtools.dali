/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.core.resources.IProject;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

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

	public static boolean getWorkspaceValidationPreferencesOverridden(IProject project) {
		return getPlugin().getBooleanPreference(project, WORKSPACE_PREFERENCES_OVERRIDDEN);
	}

	public static void setWorkspaceValidationPreferencesOverridden(IProject project, boolean value) {
		getPlugin().setBooleanPreference(project, WORKSPACE_PREFERENCES_OVERRIDDEN, value);
	}

	// unfortunate legacy typo :-(
	private static final String WORKSPACE_PREFERENCES_OVERRIDDEN = "workspace_preferences_overriden"; //$NON-NLS-1$

	/**
	 * Project-level validation message preference.
	 * Return <code>-1</code> if the specified message is to be ignored.
	 * @see org.eclipse.wst.validation.internal.provisional.core.IMessage#getSeverity()
	 */
	public static int getValidationMessageSeverity(IProject project, String messageID, int defaultSeverity) {
		String prefSeverity = getProblemSeverity(project, messageID);
		return (prefSeverity == null) ? defaultSeverity : convertPreferenceValueToMessageSeverity(prefSeverity);
	}

	private static int convertPreferenceValueToMessageSeverity(String prefSeverity) {
		for (PreferenceSeverityMapping mapping : PREFERENCE_SEVERITY_MAPPINGS) {
			if (prefSeverity.equals(mapping.preferenceValue)) {
				return mapping.validationSeverity;
			}
		}
		throw new IllegalArgumentException("unknown preference severity: " + prefSeverity); //$NON-NLS-1$
	}

	/**
	 * Convert the specified validation message severity to the corresponding
	 * problem severity preference value.
	 * @see #getProblemSeverity(String)
	 * @see #getProblemSeverity(IProject, String)
	 * @see IMessage#getSeverity()
	 * @see org.eclipse.jpt.common.core.utility.ValidationMessage#getDefaultSeverity()
	 */
	public static String convertMessageSeverityToPreferenceValue(int severity) {
		for (PreferenceSeverityMapping mapping : PREFERENCE_SEVERITY_MAPPINGS) {
			if (severity == mapping.validationSeverity) {
				return mapping.preferenceValue;
			}
		}
		throw new IllegalArgumentException("unknown severity: " + severity); //$NON-NLS-1$
	}

	/**
	 * Project-level problem preference.
	 * @see #PROBLEM_ERROR
	 * @see #PROBLEM_WARNING
	 * @see #PROBLEM_INFO
	 * @see #PROBLEM_IGNORE
	 */
	public static String getProblemSeverity(IProject project, String messageID) {
		return getPlugin().getPreference(project, PROBLEM_ + messageID);
	}

	/**
	 * Project-level problem preference.
	 * @see #PROBLEM_ERROR
	 * @see #PROBLEM_WARNING
	 * @see #PROBLEM_INFO
	 * @see #PROBLEM_IGNORE
	 */
	public static void setProblemSeverity(IProject project, String messageID, String value) {
		getPlugin().setPreference(project, PROBLEM_ + messageID, value);
	}

	/**
	 * Workspace-level problem preference.
	 * @see #PROBLEM_ERROR
	 * @see #PROBLEM_WARNING
	 * @see #PROBLEM_INFO
	 * @see #PROBLEM_IGNORE
	 */
	public static String getProblemSeverity(String messageID) {
		return getPlugin().getPreference(PROBLEM_ + messageID);
	}

	/**
	 * Workspace-level problem preference.
	 * @see #PROBLEM_ERROR
	 * @see #PROBLEM_WARNING
	 * @see #PROBLEM_INFO
	 * @see #PROBLEM_IGNORE
	 */
	public static void setProblemSeverity(String messageID, String value) {
		getPlugin().setPreference(PROBLEM_ + messageID, value);
	}

	private static final String PROBLEM = "problem"; //$NON-NLS-1$
	private static final String PROBLEM_ = PROBLEM + '.';

	public static final String PROBLEM_ERROR = "error"; //$NON-NLS-1$
	public static final String PROBLEM_WARNING = "warning"; //$NON-NLS-1$
	public static final String PROBLEM_INFO = "info"; //$NON-NLS-1$
	public static final String PROBLEM_IGNORE = "ignore"; //$NON-NLS-1$


	/**
	 * Map the problem severity preference values to their corresponding
	 * validation message severities, and vice-versa.
	 * 
	 * @see IMessage#getSeverity()
	 */
	private static final PreferenceSeverityMapping[] PREFERENCE_SEVERITY_MAPPINGS = new PreferenceSeverityMapping[] {
		new PreferenceSeverityMapping(PROBLEM_ERROR, IMessage.HIGH_SEVERITY),
		new PreferenceSeverityMapping(PROBLEM_WARNING, IMessage.NORMAL_SEVERITY),
		new PreferenceSeverityMapping(PROBLEM_INFO, IMessage.LOW_SEVERITY),
		new PreferenceSeverityMapping(PROBLEM_IGNORE, JpaProject.VALIDATION_IGNORE_SEVERITY)
	};

	private static class PreferenceSeverityMapping {
		final String preferenceValue;
		final int validationSeverity;
		PreferenceSeverityMapping(String preferenceValue, int validationSeverity) {
			super();
			this.preferenceValue = preferenceValue;
			this.validationSeverity = validationSeverity;
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, this.preferenceValue);
		}
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

	private static JptJpaCorePlugin getPlugin() {
		return JptJpaCorePlugin.instance();
	}

	private JpaPreferences() {
		throw new UnsupportedOperationException();
	}
}
