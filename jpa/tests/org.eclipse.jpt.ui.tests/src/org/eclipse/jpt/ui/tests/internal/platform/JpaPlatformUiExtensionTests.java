package org.eclipse.jpt.ui.tests.internal.platform;

import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.tests.extension.resource.ExtensionTestPlugin;
import org.eclipse.jpt.core.tests.extension.resource.TestJpaPlatform;
import org.eclipse.jpt.core.tests.extension.resource.TestJpaPlatformUi;
import org.eclipse.jpt.core.tests.internal.ProjectUtility;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;

import junit.framework.TestCase;

public class JpaPlatformUiExtensionTests extends TestCase
{
	protected TestJpaProject testProject;

	protected static final String PROJECT_NAME = "ExtensionTestProject";
	protected static final String PACKAGE_NAME = "extension.test";
	
	public static final String TEST_PLUGIN_CLASS = ExtensionTestPlugin.class.getName();
	public static final String TEST_PLUGIN_ID = ExtensionTestPlugin.PLUGIN_ID;

	public static final String TEST_PLATFORM_ID = TestJpaPlatform.ID;
	public static final String TEST_PLATFORM_CLASS = TestJpaPlatform.class.getName();
	public static final String TEST_PLATFORM_LABEL = "Test Jpa Platform";

	public static final String TEST_UI_PLATFORM_ID = TEST_PLATFORM_ID;
	public static final String TEST_UI_PLATFORM_CLASS = TestJpaPlatformUi.class.getName();
	
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
		//testProject.dispose();
		ProjectUtility.deleteAllProjects();
		this.testProject = null;
		super.tearDown();
	}
	
	protected IJpaProject jpaProject() {
		return this.testProject.getJpaProject();
	}
	
	public void testJpaPlatform() {
		assertNotNull(PlatformRegistry.instance().jpaPlatform(jpaProject().jpaPlatform().getId()));
	}
	
}
