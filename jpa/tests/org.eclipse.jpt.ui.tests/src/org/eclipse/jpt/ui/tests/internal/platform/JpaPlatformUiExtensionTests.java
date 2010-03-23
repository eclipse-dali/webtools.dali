/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.tests.internal.platform;

import junit.framework.TestCase;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.internal.JpaPlatformRegistry;
import org.eclipse.jpt.core.tests.extension.resource.ExtensionTestPlugin;
import org.eclipse.jpt.core.tests.extension.resource.TestJpaPlatformProvider;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;

@SuppressWarnings("nls")
public class JpaPlatformUiExtensionTests extends TestCase
{
	protected TestJpaProject testProject;

	protected static final String PROJECT_NAME = "ExtensionTestProject";
	protected static final String PACKAGE_NAME = "extension.test";

	public static final String TEST_PLUGIN_CLASS = ExtensionTestPlugin.class.getName();
	public static final String TEST_PLUGIN_ID = ExtensionTestPlugin.PLUGIN_ID;

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
		this.testProject = this.buildJpaProject(PROJECT_NAME, false);  // false = no auto-build
	}

	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild) throws Exception {
		return new TestJpaProject(projectName, autoBuild);  // false = no auto-build
	}

	@Override
	protected void tearDown() throws Exception {
		this.testProject.getProject().delete(true, true, null);
		this.testProject = null;
		super.tearDown();
	}

	protected JpaProject jpaProject() {
		return this.testProject.getJpaProject();
	}

	public void testJpaPlatform() {
		assertNotNull(JpaPlatformRegistry.instance().getJpaPlatform(this.testProject.getProject()));
	}

}
