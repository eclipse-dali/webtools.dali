/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.tests.internal.platform;

import junit.framework.TestCase;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.jpt.jpa.core.tests.extension.resource.TestExtensionPlugin;
import org.eclipse.jpt.jpa.core.tests.extension.resource.TestJpaPlatformProvider;
import org.eclipse.jpt.jpa.core.tests.internal.projects.JpaProjectTestHarness;

@SuppressWarnings("nls")
public class JpaPlatformUiExtensionTests extends TestCase
{
	protected JpaProjectTestHarness jpaProjectTestHarness;

	protected static final String PROJECT_NAME = "ExtensionTestProject";
	protected static final String PACKAGE_NAME = "extension.test";

	public static final String TEST_PLUGIN_CLASS = TestExtensionPlugin.class.getName();
	public static final String TEST_PLUGIN_ID = TestExtensionPlugin.instance().getPluginID();

	public static final String TEST_PLATFORM_ID = TestJpaPlatformProvider.ID;
	public static final String TEST_PLATFORM_CLASS = TestJpaPlatformProvider.class.getName();
	public static final String TEST_PLATFORM_LABEL = "Test Jpa Platform";

	public static final String TEST_UI_PLATFORM_ID = TEST_PLATFORM_ID;

	public JpaPlatformUiExtensionTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.jpaProjectTestHarness = this.buildJpaProjectTestHarness(PROJECT_NAME, false);  // false = no auto-build
	}

	protected JpaProjectTestHarness buildJpaProjectTestHarness(String projectName, boolean autoBuild) throws Exception {
		return new JpaProjectTestHarness(projectName, autoBuild);  // false = no auto-build
	}

	@Override
	protected void tearDown() throws Exception {
		this.jpaProjectTestHarness.getProject().delete(true, true, null);
		this.jpaProjectTestHarness = null;
		super.tearDown();
	}

	protected JpaProject jpaProject() {
		return this.jpaProjectTestHarness.getJpaProject();
	}

	public void testJpaPlatform() {
		assertNotNull(this.getJpaPlatform());		
	}

	protected JpaPlatform getJpaPlatform() {
		return this.getJpaPlatformManager().getJpaPlatform(this.getJpaPlatformID());
	}

	protected String getJpaPlatformID() {
		return JpaPreferences.getJpaPlatformID(this.jpaProjectTestHarness.getProject());
	}

	protected JpaPlatformManager getJpaPlatformManager() {
		return this.getJpaWorkspace().getJpaPlatformManager();
	}

	protected JpaWorkspace getJpaWorkspace() {
		return (JpaWorkspace) ResourcesPlugin.getWorkspace().getAdapter(JpaWorkspace.class);
	}
}
