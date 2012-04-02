/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.metadata;

import junit.framework.Assert;

import org.eclipse.jpt.jpa.core.prefs.JpaEntityGenPreferencesManager;
import org.eclipse.jpt.jpa.core.prefs.JpaJpqlPreferencesManager;

/**
 *  JpaJpqlMetadataTests
 */
public class JpaJpqlMetadataTests extends JpaMetadataTests {

	public static final String CASE_PREF_KEY = "jpqlIdentifier.CASE"; 
	public static final String MATCH_FIRST_CHARACTER_CASE_PREF_KEY = "jpqlIdentifier.MATCH_FIRST_CHARACTER_CASE"; 
	
	public static final String CASE_TEST_VALUE = JpaJpqlPreferencesManager.JPQL_IDENTIFIER_UPPERCASE_PREF_VALUE; 
	public static final boolean MATCH_FIRST_CHARACTER_CASE_TEST_VALUE = ! JpaJpqlPreferencesManager.DEFAULT_MATCH_FIRST_CHARACTER_CASE;

	// ********** constructor **********

	public JpaJpqlMetadataTests(String name) {
		super(name);
	}

	// ********** tests **********
	
	public void testIdentifiersCaseWorkspaceMetadata() throws Exception {
		JpaJpqlPreferencesManager.setIdentifiersCaseWorkspacePreference(CASE_TEST_VALUE);
		JpaJpqlPreferencesManager.getLegacyWorkspacePreferences().flush();

		String value = this.getWorkspacePrefs().get(CASE_PREF_KEY);
		Assert.assertNotNull(value);
		Assert.assertTrue(CASE_TEST_VALUE.equals(value));
		
		JpaEntityGenPreferencesManager.removeLegacyWorkspacePreference(CASE_PREF_KEY);
	}

	public void testMatchFirstCharacterCaseWorkspaceMetadata() throws Exception {
		JpaJpqlPreferencesManager.setMatchFirstCharacterCaseWorkspacePreference(MATCH_FIRST_CHARACTER_CASE_TEST_VALUE);
		JpaJpqlPreferencesManager.getLegacyWorkspacePreferences().flush();

		String stringValue = this.getWorkspacePrefs().get(MATCH_FIRST_CHARACTER_CASE_PREF_KEY);
		Assert.assertNotNull(stringValue);
		boolean value = Boolean.valueOf(stringValue);
		Assert.assertTrue(value == MATCH_FIRST_CHARACTER_CASE_TEST_VALUE);
		
		JpaEntityGenPreferencesManager.removeLegacyWorkspacePreference(MATCH_FIRST_CHARACTER_CASE_PREF_KEY);
	}
	
}
