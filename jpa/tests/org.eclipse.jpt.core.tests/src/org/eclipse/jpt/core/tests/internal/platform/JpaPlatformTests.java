/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.platform;

import junit.framework.TestCase;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JpaPlatformRegistry;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaBasicProvider;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEntityProvider;
import org.eclipse.jpt.core.internal.platform.generic.GenericJpaFactory;
import org.eclipse.jpt.core.internal.platform.generic.GenericPlatform;
import org.eclipse.jpt.core.tests.internal.ProjectUtility;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class JpaPlatformTests extends TestCase
{
	protected TestJpaProject testProject;

	protected static final String PROJECT_NAME = "ExtensionTestProject";
	protected static final String PACKAGE_NAME = "extension.test";
	
	public static final String TEST_PLUGIN_CLASS = "org.eclipse.tests.TestPlugin";
	public static final String TEST_PLUGIN_ID = "testPlugin";
	public static final String TEST_PLUGIN_NAME = "TestPlugin";

	public static final String TEST_PLATFORM_ID = "core.testJpaPlatform";
	public static final String TEST_PLATFORM_CLASS = "org.eclipse.tests.TestJpaPlatform";
	public static final String TEST_PLATFORM_LABEL = "Test Jpa Platform";
	public static final String TEST_JPA_FACTORY = "org.eclipse.tests.TestJpaFactory";
	public static final String TEST_TYPE_MAPPING_PROVIDER_CLASS = "org.eclipse.tests.TestTypeMappingProvider";
	public static final String TEST_ATTRIBUTE_MAPPING_PROVIDER_CLASS = "org.eclipse.tests.TestAttributeMappingProvider";
	
	public JpaPlatformTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		JpaPlatformExtensionTests.verifyExtensionTestProjectExists();
		ProjectUtility.deleteAllProjects();
		testProject = this.buildJpaProject(TestJpaProject.uniqueProjectName(PROJECT_NAME), false);  // false = no auto-build
	}

	protected TestJpaProject buildJpaProject(String projectName, boolean autoBuild) throws Exception {
		return new TestJpaProject(projectName, autoBuild);  // false = no auto-build
	}

	@Override
	protected void tearDown() throws Exception {
		testProject = null;
		ProjectUtility.deleteAllProjects();
//		testProject.dispose();
		super.tearDown();
	}
	
	protected IJpaProject jpaProject() {
		return this.testProject.getJpaProject();
	}
	
	public void testSetPlatform() {
		assertTrue(jpaPlatform() instanceof GenericPlatform);
		
		jpaProject().setPlatform(TEST_PLATFORM_ID);

		assertTrue(jpaPlatform().getClass().getName().equals(TEST_PLATFORM_CLASS));
	}
	
	public void testGetJpaFactory() {
		assertTrue(jpaPlatform().getJpaFactory() instanceof GenericJpaFactory);
		jpaProject().setPlatform(TEST_PLATFORM_ID);
		
		assertTrue(jpaPlatform().getJpaFactory().getClass().getName().equals(TEST_JPA_FACTORY));
	}
	
	public void testJavaTypeMappingProvider() {
		IJavaTypeMappingProvider provider = jpaProject().getPlatform().javaTypeMappingProvider(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		assertTrue(provider instanceof JavaEntityProvider);
		boolean exceptionCaught = false;
		try {
			provider = jpaProject().getPlatform().javaTypeMappingProvider("test");
		}
		catch (IllegalArgumentException e) {
			exceptionCaught = true;
		}
		assertTrue(exceptionCaught);
		
		jpaProject().setPlatform(TEST_PLATFORM_ID);
		
		provider = jpaProject().getPlatform().javaTypeMappingProvider(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		assertTrue(provider instanceof JavaEntityProvider);

		provider = jpaProject().getPlatform().javaTypeMappingProvider("test");
		assertTrue(provider.getClass().getName().equals(TEST_TYPE_MAPPING_PROVIDER_CLASS));
	}
	
	public void testJavaAttributeMappingProvider() {
		IJavaAttributeMappingProvider provider = jpaProject().getPlatform().javaAttributeMappingProvider(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(provider instanceof JavaBasicProvider);
		boolean exceptionCaught = false;
		try {
			provider = jpaProject().getPlatform().javaAttributeMappingProvider("test");
		}
		catch (IllegalArgumentException e) {
			exceptionCaught = true;
		}
		assertTrue(exceptionCaught);
		
		jpaProject().setPlatform(TEST_PLATFORM_ID);
		
		provider = jpaProject().getPlatform().javaAttributeMappingProvider(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(provider instanceof JavaBasicProvider);

		provider = jpaProject().getPlatform().javaAttributeMappingProvider("test");
		assertTrue(provider.getClass().getName().equals(TEST_ATTRIBUTE_MAPPING_PROVIDER_CLASS));
	}
	
	private IJpaPlatform jpaPlatform() {
		return jpaProject().jpaPlatform();
	}
}
