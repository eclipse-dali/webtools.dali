/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal;

import org.eclipse.jpt.common.core.tests.PreferencesTests;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;

/**
 * <strong>NB:</strong> These tests are to test for backward-compatibility!
 * That is, there are testing to make sure preferences are stored in their
 * <em>original</em> location from release to release. Thus the hard-coded
 * file names, preference keys, etc.
 */
@SuppressWarnings("nls")
public class JpaPreferencesTests
	extends PreferencesTests
{
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String WORKSPACE_PREFS_FILE_NAME = "org.eclipse.jpt.core.prefs";

	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String PROJECT_PREFS_FILE_NAME = "org.eclipse.jpt.core.prefs";


	public JpaPreferencesTests(String name) {
		super(name);
	}


	// ********** overrides **********

	@Override
	protected String getWorkspacePrefsFileName() {
		return WORKSPACE_PREFS_FILE_NAME;
	}

	@Override
	protected String getProjectPrefsFileName() {
		return PROJECT_PREFS_FILE_NAME;
	}

	@Override
	protected Class<?> getPreferencesClass() {
		return JpaPreferences.class;
	}


	// ********** tests **********

	public void testJpaPlatformID() throws Exception {
		String value = "FOO";
		JpaPreferences.setJpaPlatformID(this.getProject(), value);
		this.flushProjectPrefs();
		assertEquals(value, JpaPreferences.getJpaPlatformID(this.getProject()));
		assertEquals(value, this.readProjectPrefs().getProperty(JPA_PLATFORM));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String JPA_PLATFORM = "org.eclipse.jpt.core.platform";

	public void testDiscoverAnnotatedClasses() throws Exception {
		JpaPreferences.setDiscoverAnnotatedClasses(this.getProject(), true);
		this.flushProjectPrefs();
		assertTrue(JpaPreferences.getDiscoverAnnotatedClasses(this.getProject()));
		assertTrue(Boolean.parseBoolean(this.readProjectPrefs().getProperty(DISCOVER_ANNOTATED_CLASSES)));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String DISCOVER_ANNOTATED_CLASSES = "org.eclipse.jpt.jpa.core.discoverAnnotatedClasses";

	public void testMetamodelSourceFolderName() throws Exception {
		String value = "gen";
		JpaPreferences.setMetamodelSourceFolderName(this.getProject(), value);
		this.flushProjectPrefs();
		assertEquals(value, JpaPreferences.getMetamodelSourceFolderName(this.getProject()));
		assertEquals(value, this.readProjectPrefs().getProperty(METAMODEL_SOURCE_FOLDER_NAME));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String METAMODEL_SOURCE_FOLDER_NAME = "org.eclipse.jpt.jpa.core.metamodelSourceFolderName";

	public void testEntityGenDefaultPackageName_Project() throws Exception {
		String value = "entitygen";
		JpaPreferences.setEntityGenDefaultPackageName(this.getProject(), value);
		this.flushProjectPrefs();
		assertEquals(value, JpaPreferences.getEntityGenDefaultPackageName(this.getProject()));
		assertEquals(value, this.readProjectPrefs().getProperty(ENTITY_GEN_DEFAULT_PACKAGE_NAME));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String ENTITY_GEN_DEFAULT_PACKAGE_NAME = "entitygen.DEFAULT_PACKAGE";

	public void testEntityGenDefaultPackageName_Workspace() throws Exception {
		String value = "wsentitygen";
		JpaPreferences.setEntityGenDefaultPackageName(value);
		this.flushWorkspacePrefs();
		// verify workspace pref affects project-level pref
		assertEquals(value, JpaPreferences.getEntityGenDefaultPackageName(this.getProject()));
		assertEquals(value, JpaPreferences.getEntityGenDefaultPackageName());
		assertEquals(value, this.readWorkspacePrefs().getProperty(ENTITY_GEN_DEFAULT_PACKAGE_NAME));
	}

	public void testJpqlIdentifierLowercase() throws Exception {
		JpaPreferences.setJpqlIdentifierLowercase(false);
		this.flushWorkspacePrefs();
		assertFalse(JpaPreferences.getJpqlIdentifierLowercase());
		assertEquals("uppercase", this.readWorkspacePrefs().getProperty(JPQL_IDENTIFIER_CASE));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String JPQL_IDENTIFIER_CASE = "jpqlIdentifier.CASE";

	public void testJpqlIdentifierMatchFirstCharacterCase() throws Exception {
		JpaPreferences.setJpqlIdentifierMatchFirstCharacterCase(false);
		this.flushWorkspacePrefs();
		assertFalse(JpaPreferences.getJpqlIdentifierMatchFirstCharacterCase());
		assertFalse(Boolean.parseBoolean(this.readWorkspacePrefs().getProperty(JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE)));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String JPQL_IDENTIFIER_MATCH_FIRST_CHARACTER_CASE = "jpqlIdentifier.MATCH_FIRST_CHARACTER_CASE";

	public void testWorkspaceValidationPreferencesOverridden() throws Exception {
		JpaPreferences.setWorkspaceValidationPreferencesOverridden(this.getProject(), true);
		this.flushProjectPrefs();
		assertTrue(JpaPreferences.getWorkspaceValidationPreferencesOverridden(this.getProject()));
		assertTrue(Boolean.parseBoolean(this.readProjectPrefs().getProperty(WORKSPACE_PREFERENCES_OVERRIDDEN)));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String WORKSPACE_PREFERENCES_OVERRIDDEN = "workspace_preferences_overriden";

	public void testProblemSeverity_Project() throws Exception {
		String value = JpaPreferences.PROBLEM_INFO;
		JpaPreferences.setProblemSeverity(this.getProject(), JptJpaCoreValidationMessages.NO_JPA_PROJECT.getID(), value);
		this.flushProjectPrefs();
		assertEquals(value, JpaPreferences.getProblemSeverity(this.getProject(), JptJpaCoreValidationMessages.NO_JPA_PROJECT.getID()));
		assertEquals(value, this.readProjectPrefs().getProperty(PROBLEM_NO_JPA_PROJECT));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String PROBLEM_NO_JPA_PROJECT = "problem.NO_JPA_PROJECT";

	public void testProblemSeverity_Workspace() throws Exception {
		String value = JpaPreferences.PROBLEM_WARNING;
		JpaPreferences.setProblemSeverity(JptJpaCoreValidationMessages.NO_JPA_PROJECT.getID(), value);
		this.flushWorkspacePrefs();
		// verify workspace pref affects project-level pref
		assertEquals(value, JpaPreferences.getProblemSeverity(this.getProject(), JptJpaCoreValidationMessages.NO_JPA_PROJECT.getID()));
		assertEquals(value, JpaPreferences.getProblemSeverity(JptJpaCoreValidationMessages.NO_JPA_PROJECT.getID()));
		assertEquals(value, this.readWorkspacePrefs().getProperty(PROBLEM_NO_JPA_PROJECT));
	}
}
