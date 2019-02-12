/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal;

import org.eclipse.jpt.common.core.tests.PreferencesTests;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaPreferences;

/**
 * <strong>NB:</strong> These tests are to test for backward-compatibility!
 * That is, there are testing to make sure preferences are stored in their
 * <em>original</em> location from release to release. Thus the hard-coded
 * file names, preference keys, etc.
 */
@SuppressWarnings("nls")
public class EclipseLinkJpaPreferencesTests
	extends PreferencesTests
{
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String WORKSPACE_PREFS_FILE_NAME = "org.eclipse.jpt.jpa.eclipselink.core.prefs";

	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String PROJECT_PREFS_FILE_NAME = "org.eclipse.jpt.jpa.eclipselink.core.prefs";


	public EclipseLinkJpaPreferencesTests(String name) {
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
		return EclipseLinkJpaPreferences.class;
	}


	// ********** tests **********

	public void testStaticWeavingSourceLocation() throws Exception {
		String value = "FOO";
		EclipseLinkJpaPreferences.setStaticWeavingSourceLocation(this.getProject(), value);
		this.flushProjectPrefs();
		assertEquals(value, this.readProjectPrefs().getProperty(STATIC_WEAVE_SOURCE_LOCATION));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String STATIC_WEAVE_SOURCE_LOCATION = "staticWeaving.SOURCE";

	public void testStaticWeavingTargetLocation() throws Exception {
		String value = "FOO";
		EclipseLinkJpaPreferences.setStaticWeavingTargetLocation(this.getProject(), value);
		this.flushProjectPrefs();
		assertEquals(value, this.readProjectPrefs().getProperty(STATIC_WEAVING_TARGET_LOCATION));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String STATIC_WEAVING_TARGET_LOCATION = "staticWeaving.TARGET";

	public void testStaticWeavingLogLevel() throws Exception {
		String value = "SEVERE";
		EclipseLinkJpaPreferences.setStaticWeavingLogLevel(this.getProject(), value);
		this.flushProjectPrefs();
		assertEquals(value, this.readProjectPrefs().getProperty(STATIC_WEAVING_LOG_LEVEL));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String STATIC_WEAVING_LOG_LEVEL = "staticWeaving.LOG_LEVEL";

	public void testStaticWeavingPersistenceInfo() throws Exception {
		String value = "BLAH";
		EclipseLinkJpaPreferences.setStaticWeavingPersistenceInfo(this.getProject(), value);
		this.flushProjectPrefs();
		assertEquals(value, this.readProjectPrefs().getProperty(STATIC_WEAVING_PERSISTENCE_INFO));
	}
	// DO NOT CHANGE THIS CONSTANT - as it is testing backward-compatibility
	private static final String STATIC_WEAVING_PERSISTENCE_INFO = "staticWeaving.PERSISTENCE_INFO";
}
