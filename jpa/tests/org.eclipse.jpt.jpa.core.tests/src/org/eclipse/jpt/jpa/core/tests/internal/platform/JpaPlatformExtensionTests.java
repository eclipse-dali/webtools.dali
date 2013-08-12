/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.platform;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.RegistryFactory;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.tests.extension.resource.TestExtensionPlugin;
import org.eclipse.jpt.jpa.core.tests.extension.resource.TestJpaPlatformProvider;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.jpa.core.tests.internal.projects.JpaProjectTestHarness;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IActionConfigFactory;

@SuppressWarnings("nls")
public class JpaPlatformExtensionTests
	extends ContextModelTestCase
{
	public static final String TEST_PLATFORM_ID = TestJpaPlatformProvider.ID;
	public static final String TEST_PLATFORM_LABEL = "Test Jpa Platform";
	
	protected JpaProjectTestHarness testProject;

	public JpaPlatformExtensionTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		verifyExtensionTestProjectExists();
	}
	
	@Override
	protected JavaProjectTestHarness buildJavaProjectTestHarness(boolean autoBuild) throws Exception {
		return super.buildJpaProject(PROJECT_NAME, autoBuild, this.buildConfig());
	}

	protected IDataModel buildConfig() throws Exception {
		IActionConfigFactory configFactory = new JpaFacetInstallDataModelProvider();
		IDataModel config = (IDataModel) configFactory.create();
		config.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, JpaProject.FACET_VERSION_STRING);
		config.setProperty(JpaFacetDataModelProperties.PLATFORM, this.getJpaPlatformManager().getJpaPlatformConfig(TEST_PLATFORM_ID));
		return config;
	}

	public static void verifyExtensionTestProjectExists() {
		IExtensionRegistry registry = RegistryFactory.getRegistry();
		IExtensionPoint extensionPoint = 
			registry.getExtensionPoint(JptJpaCorePlugin.instance().getPluginID(), "jpaPlatform");
		IExtension[] extensions = extensionPoint.getExtensions();
		boolean extensionFound = false;
		for (IExtension extension : extensions) {
			if (extension.getContributor().getName().equals(TestExtensionPlugin.instance().getPluginID())) {
				extensionFound = true;
			}
		}
		if (!extensionFound) {
			throw new RuntimeException("Missing Extension " + TEST_PLATFORM_ID + ". The ExtensionTestProject plugin must be in your testing workspace.");
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testAllJpaPlatformIds() {
		assertTrue(IterableTools.size(getJpaPlatformManager().getJpaPlatformConfigs()) >= 2);
	}
	
	public void testJpaPlatformLabel() {
		assertEquals(TEST_PLATFORM_LABEL, getJpaPlatformManager().getJpaPlatformConfig(TEST_PLATFORM_ID).getLabel());	
	}
	
	public void testJpaPlatform() {
		assertNotNull(this.getJpaPlatform());		
	}

	protected JpaPlatform getJpaPlatform() {
		return this.getJpaPlatformManager().getJpaPlatform(JpaPreferences.getJpaPlatformID(this.testProject.getProject()));
	}
}
