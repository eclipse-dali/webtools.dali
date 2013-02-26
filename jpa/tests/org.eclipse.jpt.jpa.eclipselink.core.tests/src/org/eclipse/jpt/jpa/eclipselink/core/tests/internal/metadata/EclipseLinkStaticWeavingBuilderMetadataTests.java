/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.metadata;

import junit.framework.Assert;

import org.eclipse.jpt.jpa.core.tests.internal.metadata.JpaMetadataTests;
import org.eclipse.jpt.jpa.eclipselink.core.builder.EclipseLinkStaticWeavingBuilderConfigurator;

/**
 *  JpaStaticWeavingBuilderMetadataTests
 */
public class EclipseLinkStaticWeavingBuilderMetadataTests extends JpaMetadataTests {

	public static final String SOURCE_PREF_KEY = "staticweave.SOURCE"; //$NON-NLS-1$
	public static final String LOG_LEVEL_PREF_KEY = "staticweave.LOG_LEVEL"; //$NON-NLS-1$
	
	public static final String SOURCE_TEST_VALUE = "bin"; //$NON-NLS-1$
	public static final String LOG_LEVEL_TEST_VALUE = "ALL"; //$NON-NLS-1$

	protected EclipseLinkStaticWeavingBuilderConfigurator projectPrefsManager;
	
	// ********** constructor **********

	public EclipseLinkStaticWeavingBuilderMetadataTests(String name) {
		super(name);
	}

	// ********** overrides **********

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.projectPrefsManager = new EclipseLinkStaticWeavingBuilderConfigurator(this.getJavaProject().getProject());
		Assert.assertNotNull(this.projectPrefsManager);
	}

	// ********** tests **********

	public void testSourceLocationProjectMetadata() throws Exception {

		this.projectPrefsManager.setSourceLocationPreference(SOURCE_TEST_VALUE);
		this.projectPrefsManager.getLegacyProjectPreferences().flush();
		
		String value = this.getProjectPrefs().get(SOURCE_PREF_KEY);
		Assert.assertNotNull(value);
		Assert.assertTrue(SOURCE_TEST_VALUE.equals(value));
	}

	public void testLogLevelProjectMetadata() throws Exception {

		this.projectPrefsManager.setLogLevelPreference(LOG_LEVEL_TEST_VALUE);
		this.projectPrefsManager.getLegacyProjectPreferences().flush();
		
		String value = this.getProjectPrefs().get(LOG_LEVEL_PREF_KEY);
		Assert.assertNotNull(value);
		Assert.assertTrue(LOG_LEVEL_TEST_VALUE.equals(value));
	}
}