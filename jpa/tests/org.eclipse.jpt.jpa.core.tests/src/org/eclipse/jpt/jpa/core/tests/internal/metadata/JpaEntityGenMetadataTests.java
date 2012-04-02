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

/**
 *  JpaEntityGenMetadataTests
 */
public class JpaEntityGenMetadataTests extends JpaMetadataTests {

	public static final String DEFAULT_PACKAGE_PREF_KEY = "entitygen.DEFAULT_PACKAGE"; //$NON-NLS-1$
	
	public static final String DEFAULT_PACKAGE_TEST_VALUE = "test.model"; //$NON-NLS-1$

	protected JpaEntityGenPreferencesManager projectPrefsManager;
	
	// ********** constructor **********

	public JpaEntityGenMetadataTests(String name) {
		super(name);
	}

	// ********** overrides **********

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.projectPrefsManager = new JpaEntityGenPreferencesManager(this.getJavaProject().getProject());
		Assert.assertNotNull(this.projectPrefsManager);
	}

	// ********** tests **********
	
	public void testDefaultPackageWorkspaceMetadata() throws Exception {
		JpaEntityGenPreferencesManager.setDefaultPackageWorkspacePreference(DEFAULT_PACKAGE_TEST_VALUE);
		JpaEntityGenPreferencesManager.getLegacyWorkspacePreferences().flush();

		String value = this.getWorkspacePrefs().get(DEFAULT_PACKAGE_PREF_KEY);
		Assert.assertNotNull(value);
		Assert.assertTrue(DEFAULT_PACKAGE_TEST_VALUE.equals(value));
		
		JpaEntityGenPreferencesManager.removeLegacyWorkspacePreference(DEFAULT_PACKAGE_PREF_KEY);
	}
	
	public void testDefaultPackageProjectMetadata() throws Exception {

		this.projectPrefsManager.setDefaultPackagePreference(DEFAULT_PACKAGE_TEST_VALUE);
		this.projectPrefsManager.getLegacyProjectPreferences().flush();
		
		String value = this.getProjectPrefs().get(DEFAULT_PACKAGE_PREF_KEY);
		Assert.assertNotNull(value);
		Assert.assertTrue(DEFAULT_PACKAGE_TEST_VALUE.equals(value));
	}
	
}
