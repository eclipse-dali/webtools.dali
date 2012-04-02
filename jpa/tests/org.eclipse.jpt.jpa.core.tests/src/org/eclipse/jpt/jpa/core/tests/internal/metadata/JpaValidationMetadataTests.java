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

import org.eclipse.jpt.jpa.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.jpa.core.prefs.JpaValidationPreferencesManager;

/**
 *  JpaValidationMetadataTests
 */
public class JpaValidationMetadataTests extends JpaMetadataTests {

	public static final String WORKSPACE_OVERRIDEN_PREF_KEY = "workspace_preferences_overriden"; 
	public static final String PERSISTENCE_NO_PERSISTENCE_UNIT_PREF_KEY = "problem." + JpaValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT; 
	
	public static final boolean WORKSPACE_OVERRIDEN_TEST_VALUE = true;
	public static final String PERSISTENCE_NO_PERSISTENCE_UNIT_TEST_VALUE = JpaValidationPreferencesManager.INFO; 

	protected JpaValidationPreferencesManager projectPrefsManager;
	
	// ********** constructor **********

	public JpaValidationMetadataTests(String name) {
		super(name);
	}

	// ********** overrides **********

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.projectPrefsManager = new JpaValidationPreferencesManager(this.getJavaProject().getProject());
		Assert.assertNotNull(this.projectPrefsManager);
	}

	// ********** tests **********
	
	public void testWorkspaceLevelProblemMetadata() throws Exception {
		JpaValidationPreferencesManager.setWorkspaceLevelProblemPreference(
								JpaValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT,
								PERSISTENCE_NO_PERSISTENCE_UNIT_TEST_VALUE);
		JpaValidationPreferencesManager.getLegacyWorkspacePreferences().flush();
		
		String value = this.getWorkspacePrefs().get(PERSISTENCE_NO_PERSISTENCE_UNIT_PREF_KEY);
		Assert.assertNotNull(value);
		Assert.assertTrue(PERSISTENCE_NO_PERSISTENCE_UNIT_TEST_VALUE.equals(value));
		
		JpaValidationPreferencesManager.removeLegacyWorkspacePreference(PERSISTENCE_NO_PERSISTENCE_UNIT_PREF_KEY);
	}

	public void testProjectHasSpecificOptionsMetadata() throws Exception {

		this.projectPrefsManager.setProjectHasSpecificOptions(WORKSPACE_OVERRIDEN_TEST_VALUE);
		this.projectPrefsManager.getLegacyProjectPreferences().flush();

		String stringValue = this.getProjectPrefs().get(WORKSPACE_OVERRIDEN_PREF_KEY);
		Assert.assertNotNull(stringValue);
		boolean value = Boolean.valueOf(stringValue);
		Assert.assertTrue(value == WORKSPACE_OVERRIDEN_TEST_VALUE);
	}

	public void testProjectLevelProblemMetadata() throws Exception {

		this.projectPrefsManager.setProjectLevelProblemPreference(
								JpaValidationMessages.PERSISTENCE_NO_PERSISTENCE_UNIT,
								PERSISTENCE_NO_PERSISTENCE_UNIT_TEST_VALUE);
		this.projectPrefsManager.getLegacyProjectPreferences().flush();

		String value = this.getProjectPrefs().get(PERSISTENCE_NO_PERSISTENCE_UNIT_PREF_KEY);
		Assert.assertNotNull(value);
		Assert.assertTrue(PERSISTENCE_NO_PERSISTENCE_UNIT_TEST_VALUE.equals(value));
	}
}