/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.platform;


import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.core.internal.facet.JpaFacetDataModelProvider;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.tests.extension.resource.ExtensionTestPlugin;
import org.eclipse.jpt.core.tests.extension.resource.JavaTestAttributeMapping;
import org.eclipse.jpt.core.tests.extension.resource.JavaTestAttributeMappingProvider;
import org.eclipse.jpt.core.tests.extension.resource.JavaTestTypeMapping;
import org.eclipse.jpt.core.tests.extension.resource.JavaTestTypeMappingProvider;
import org.eclipse.jpt.core.tests.extension.resource.TestJavaBasicMapping;
import org.eclipse.jpt.core.tests.extension.resource.TestJavaEntity;
import org.eclipse.jpt.core.tests.extension.resource.TestJpaFactory;
import org.eclipse.jpt.core.tests.extension.resource.TestJpaPlatform;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IActionConfigFactory;

public class JpaPlatformTests extends ContextModelTestCase
{
	protected TestJpaProject testProject;

	protected static final String PROJECT_NAME = "ExtensionTestProject";
	protected static final String PACKAGE_NAME = "extension.test";
	
	public static final String TEST_PLUGIN_CLASS = ExtensionTestPlugin.class.getName();
	public static final String TEST_PLUGIN_ID = "org.eclipse.jpt.core.tests.extension.resource";

	public static final String TEST_PLATFORM_CLASS_NAME = TestJpaPlatform.class.getName();
	public static final String TEST_PLATFORM_LABEL = "Test Jpa Platform";
	public static final String TEST_JPA_FACTORY = TestJpaFactory.class.getName();
	public static final String TEST_TYPE_MAPPING_PROVIDER_CLASS = JavaTestTypeMappingProvider.class.getName();
	public static final String TEST_ATTRIBUTE_MAPPING_PROVIDER_CLASS = JavaTestAttributeMappingProvider.class.getName();
	
	public JpaPlatformTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		JpaPlatformExtensionTests.verifyExtensionTestProjectExists();
	}

	@Override
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return super.buildJpaProject(PROJECT_NAME, autoBuild, this.buildConfig());
	}

	protected IDataModel buildConfig() throws Exception {
		IActionConfigFactory configFactory = new JpaFacetDataModelProvider();
		IDataModel config = (IDataModel) configFactory.create();
		config.setProperty(JpaFacetDataModelProperties.PLATFORM_ID, TestJpaPlatform.ID);
		return config;
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
		});
	}

	
	protected JpaPlatform jpaPlatform() {
		return this.getJpaProject().getJpaPlatform();
	}

	public void testJpaFactory() {
		assertTrue(jpaPlatform().getJpaFactory().getClass().getName().equals(TEST_JPA_FACTORY));
	}
	
	public void testBuildJavaTypeMappingFromMappingKey() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaTypeMapping javaTypeMapping = getJpaProject().getJpaPlatform().buildJavaTypeMappingFromMappingKey(JavaTestTypeMapping.TEST_TYPE_MAPPING_KEY, getJavaPersistentType());
		assertTrue(javaTypeMapping instanceof JavaTestTypeMapping);
		
		javaTypeMapping = jpaPlatform().buildJavaTypeMappingFromMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY, getJavaPersistentType());
		assertTrue(javaTypeMapping instanceof TestJavaEntity);	
	}
	
	public void testBuildJavaAttributeMappingFromMappingKey() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		JavaAttributeMapping javaAttributeMapping = getJpaProject().getJpaPlatform().buildJavaAttributeMappingFromMappingKey(JavaTestAttributeMapping.TEST_ATTRIBUTE_MAPPING_KEY, getJavaPersistentType().getAttributeNamed("name"));	
		assertTrue(javaAttributeMapping instanceof JavaTestAttributeMapping);
		
		javaAttributeMapping = jpaPlatform().buildJavaAttributeMappingFromMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, getJavaPersistentType().getAttributeNamed("name"));
		assertTrue(javaAttributeMapping instanceof TestJavaBasicMapping);
	}
	
}
