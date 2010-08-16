/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.platform;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.jpt.core.tests.extension.resource.ExtensionTestPlugin;
import org.eclipse.jpt.core.tests.extension.resource.TestJpaPlatformProvider;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IActionConfigFactory;

@SuppressWarnings("nls")
public class JpaPlatformExtensionTests extends ContextModelTestCase
{
	public static final String TEST_PLATFORM_ID = TestJpaPlatformProvider.ID;
	public static final String TEST_PLATFORM_LABEL = "Test Jpa Platform";
	
	protected TestJpaProject testProject;

	public JpaPlatformExtensionTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		verifyExtensionTestProjectExists();
	}
	
	@Override
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return super.buildJpaProject(PROJECT_NAME, autoBuild, this.buildConfig());
	}

	protected IDataModel buildConfig() throws Exception {
		IActionConfigFactory configFactory = new JpaFacetInstallDataModelProvider();
		IDataModel config = (IDataModel) configFactory.create();
		config.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, JpaFacet.VERSION_1_0.getVersionString());
		config.setProperty(JpaFacetDataModelProperties.PLATFORM_ID, TEST_PLATFORM_ID);
		return config;
	}

	public static void verifyExtensionTestProjectExists() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = 
			registry.getExtensionPoint(JptCorePlugin.PLUGIN_ID, "jpaPlatform");
		IExtension[] extensions = extensionPoint.getExtensions();
		boolean extensionFound = false;
		for (IExtension extension : extensions) {
			if (extension.getContributor().getName().equals(ExtensionTestPlugin.PLUGIN_ID)) {
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
		assertTrue(CollectionTools.size(JptCorePlugin.getJpaPlatformManager().getJpaPlatforms()) >= 2);
	}
	
	public void testJpaPlatformLabel() {
		assertEquals(TEST_PLATFORM_LABEL, JptCorePlugin.getJpaPlatformManager().getJpaPlatform(TEST_PLATFORM_ID).getLabel());	
	}
	
	public void testJpaPlatform() {
		assertNotNull(JptCorePlugin.getJpaPlatformManager().buildJpaPlatformImplementation(this.testProject.getProject()));		
	}
}
