/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.platform;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetDataModelProperties;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.tests.extension.resource.JavaTestAttributeMapping;
import org.eclipse.jpt.jpa.core.tests.extension.resource.JavaTestAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.tests.extension.resource.JavaTestTypeMapping;
import org.eclipse.jpt.jpa.core.tests.extension.resource.JavaTestTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.tests.extension.resource.TestExtensionPlugin;
import org.eclipse.jpt.jpa.core.tests.extension.resource.TestJavaBasicMapping;
import org.eclipse.jpt.jpa.core.tests.extension.resource.TestJavaEntity;
import org.eclipse.jpt.jpa.core.tests.extension.resource.TestJpaFactory;
import org.eclipse.jpt.jpa.core.tests.extension.resource.TestJpaPlatformProvider;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.jpa.core.tests.internal.projects.TestJpaProject;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IActionConfigFactory;

@SuppressWarnings("nls")
public class JpaPlatformTests
	extends ContextModelTestCase
{
	protected TestJpaProject testProject;
	
	public static final String TEST_PLUGIN_CLASS = TestExtensionPlugin.class.getName();
	public static final String TEST_PLUGIN_ID = "org.eclipse.jpt.jpa.core.tests.extension.resource";

	public static final String TEST_PLATFORM_CLASS_NAME = TestJpaPlatformProvider.class.getName();
	public static final String TEST_PLATFORM_LABEL = "Test Jpa Platform";
	public static final String TEST_JPA_FACTORY = TestJpaFactory.class.getName();
	public static final String TEST_TYPE_MAPPING_PROVIDER_CLASS = JavaTestTypeMappingDefinition.class.getName();
	public static final String TEST_ATTRIBUTE_MAPPING_PROVIDER_CLASS = JavaTestAttributeMappingDefinition.class.getName();
	
	public JpaPlatformTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		JpaPlatformExtensionTests.verifyExtensionTestProjectExists();
	}

	@Override
	protected JavaProjectTestHarness buildJavaProject(boolean autoBuild) throws Exception {
		return super.buildJpaProject(PROJECT_NAME, autoBuild, this.buildConfig());
	}

	protected IDataModel buildConfig() throws Exception {
		IActionConfigFactory configFactory = new JpaFacetInstallDataModelProvider();
		IDataModel config = (IDataModel) configFactory.create();
		config.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, JpaProject.FACET_VERSION_STRING);
		config.setProperty(
					JpaFacetDataModelProperties.PLATFORM, 
					this.getJpaPlatformManager().getJpaPlatform(TestJpaPlatformProvider.ID));
		return config;
	}

	private ICompilationUnit createTestEntity() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.ENTITY);
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
		
		this.getJavaPersistentType().setMappingKey(JavaTestTypeMapping.TEST_TYPE_MAPPING_KEY);
		JavaTypeMapping mapping = this.getJavaPersistentType().getMapping();
		assertTrue(mapping instanceof JavaTestTypeMapping);
		
		this.getJavaPersistentType().setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		mapping = this.getJavaPersistentType().getMapping();
		assertTrue(mapping instanceof TestJavaEntity);	
	}
	
	public void testBuildJavaAttributeMappingFromMappingKey() throws Exception {
		createTestEntity();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaSpecifiedPersistentAttribute javaAttribute = this.getJavaPersistentType().getAttributeNamed("name");
		javaAttribute.setMappingKey(JavaTestAttributeMapping.TEST_ATTRIBUTE_MAPPING_KEY);
		JavaAttributeMapping mapping = javaAttribute.getMapping();
		assertTrue(mapping instanceof JavaTestAttributeMapping);
		
		javaAttribute.setMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		mapping = javaAttribute.getMapping();
		assertTrue(mapping instanceof TestJavaBasicMapping);
	}
}
