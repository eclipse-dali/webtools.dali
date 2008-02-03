/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.platform;

//import org.eclipse.jpt.core.internal.IJpaPlatform;
//import org.eclipse.jpt.core.internal.IJpaProject;
//import org.eclipse.jpt.core.internal.IMappingKeys;
//import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMappingProvider;
//import org.eclipse.jpt.core.internal.content.java.IJavaTypeMappingProvider;
//import org.eclipse.jpt.core.internal.content.java.mappings.JavaBasicProvider;
//import org.eclipse.jpt.core.internal.content.java.mappings.JavaEntityProvider;
//import org.eclipse.jpt.core.internal.facet.IJpaFacetDataModelProperties;
//import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProvider;
//import org.eclipse.jpt.core.tests.extension.resource.ExtensionTestPlugin;
//import org.eclipse.jpt.core.tests.extension.resource.TestAttributeMappingProvider;
//import org.eclipse.jpt.core.tests.extension.resource.TestJpaFactory;
//import org.eclipse.jpt.core.tests.extension.resource.TestJpaPlatform;
//import org.eclipse.jpt.core.tests.extension.resource.TestTypeMappingProvider;
//import org.eclipse.jpt.core.tests.internal.ProjectUtility;
//import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
//import org.eclipse.jpt.core.tests.internal.projects.TestPlatformProject;
//import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
//import org.eclipse.wst.common.project.facet.core.IActionConfigFactory;

import junit.framework.TestCase;

public class JpaPlatformTests extends TestCase
{
//	protected TestJpaProject testProject;
//
//	protected static final String PROJECT_NAME = "ExtensionTestProject";
//	protected static final String PACKAGE_NAME = "extension.test";
//	
//	public static final String TEST_PLUGIN_CLASS = ExtensionTestPlugin.class.getName();
//	public static final String TEST_PLUGIN_ID = "org.eclipse.jpt.core.tests.extension.resource";
//
//	public static final String TEST_PLATFORM_CLASS_NAME = TestJpaPlatform.class.getName();
//	public static final String TEST_PLATFORM_LABEL = "Test Jpa Platform";
//	public static final String TEST_JPA_FACTORY = TestJpaFactory.class.getName();
//	public static final String TEST_TYPE_MAPPING_PROVIDER_CLASS = TestTypeMappingProvider.class.getName();
//	public static final String TEST_ATTRIBUTE_MAPPING_PROVIDER_CLASS = TestAttributeMappingProvider.class.getName();
//	
//	public JpaPlatformTests(String name) {
//		super(name);
//	}
//
//	@Override
//	protected void setUp() throws Exception {
//		super.setUp();
//		JpaPlatformExtensionTests.verifyExtensionTestProjectExists();
//		ProjectUtility.deleteAllProjects();
//		this.testProject = this.buildJpaProject(TestPlatformProject.uniqueProjectName(PROJECT_NAME), false);  // false = no auto-build
//	}
//
//	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild) throws Exception {
//		return new TestJpaProject(projectName, autoBuild, this.buildConfig());
//	}
//
//	protected IDataModel buildConfig() throws Exception {
//		IActionConfigFactory configFactory = new JpaFacetDataModelProvider();
//		IDataModel config = (IDataModel) configFactory.create();
//		config.setProperty(IJpaFacetDataModelProperties.PLATFORM_ID, TestJpaPlatform.ID);
//		return config;
//	}
//
//	@Override
//	protected void tearDown() throws Exception {
//		this.testProject = null;
//		ProjectUtility.deleteAllProjects();
////		this.testProject.dispose();
//		super.tearDown();
//	}
//	
//	protected IJpaProject jpaProject() {
//		return this.testProject.getJpaProject();
//	}
//	
//	protected IJpaPlatform jpaPlatform() {
//		return this.jpaProject().jpaPlatform();
//	}
//
//	public void testJpaFactory() {
//		assertTrue(jpaPlatform().getJpaFactory().getClass().getName().equals(TEST_JPA_FACTORY));
//	}
//	
//	public void testJavaTypeMappingProvider() {
//		IJavaTypeMappingProvider provider = jpaProject().jpaPlatform().javaTypeMappingProvider(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
//		assertTrue(provider instanceof JavaEntityProvider);
//
//		provider = jpaProject().jpaPlatform().javaTypeMappingProvider("test");
//		assertTrue(provider.getClass().getName().equals(TEST_TYPE_MAPPING_PROVIDER_CLASS));
//	}
//	
//	public void testJavaAttributeMappingProvider() {
//		IJavaAttributeMappingProvider provider = jpaProject().jpaPlatform().javaAttributeMappingProvider(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
//		assertTrue(provider instanceof JavaBasicProvider);
//
//		provider = jpaProject().jpaPlatform().javaAttributeMappingProvider("test");
//		assertTrue(provider.getClass().getName().equals(TEST_ATTRIBUTE_MAPPING_PROVIDER_CLASS));
//	}
//	
}
