/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.Kind;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaAttributeOverrideTests extends ContextModelTestCase
{
	private static final String ATTRIBUTE_OVERRIDE_NAME = "MY_ATTRIBUTE_OVERRIDE_NAME";
	private static final String ATTRIBUTE_OVERRIDE_COLUMN_NAME = "MY_ATTRIBUTE_OVERRIDE_COLUMN_NAME";
		
	private ICompilationUnit createTestMappedSuperclass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
		});
	}

	private ICompilationUnit createTestEntityWithAttributeOverride() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ATTRIBUTE_OVERRIDE, JPA.COLUMN);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
				sb.append("@AttributeOverride(name=\"" + ATTRIBUTE_OVERRIDE_NAME + "\", column=@Column(name=\"" + ATTRIBUTE_OVERRIDE_COLUMN_NAME + "\"))");
			}
		});
	}

	private void createTestSubType() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
					sb.append("import ");
					sb.append(JPA.ENTITY);
					sb.append(";");
					sb.append(CR);
				sb.append("@Entity");
				sb.append(CR);
				sb.append("public class ").append("AnnotationTestTypeChild").append(" ");
				sb.append("extends " + TYPE_NAME + " ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProject.createCompilationUnit(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}


		
	public JavaAttributeOverrideTests(String name) {
		super(name);
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithAttributeOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		AttributeOverride specifiedAttributeOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, specifiedAttributeOverride.getName());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) resourceType.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		
		attributeOverrideResource.setName("FOO");
		getJpaProject().synchronizeContextModel();
		specifiedAttributeOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertEquals("FOO", specifiedAttributeOverride.getName());
	}
	
	public void testSetName() throws Exception {
		createTestEntityWithAttributeOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		AttributeOverride specifiedAttributeOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, specifiedAttributeOverride.getName());
		
		specifiedAttributeOverride.setName("FOO");
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) resourceType.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		
		assertEquals("FOO", attributeOverrideResource.getName());
	}

	public void testColumnGetName() throws Exception {
		createTestEntityWithAttributeOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		AttributeOverride specifiedAttributeOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		Column column = specifiedAttributeOverride.getColumn();
		assertEquals(ATTRIBUTE_OVERRIDE_COLUMN_NAME, column.getName());
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) resourceType.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		ColumnAnnotation columnResource = attributeOverrideResource.getColumn();
		columnResource.setName("FOO");
		getJpaProject().synchronizeContextModel();
		
		
		column = overrideContainer.getSpecifiedOverrides().iterator().next().getColumn();
		assertEquals("FOO", column.getName());
	}
	
	public void testColumnSetName() throws Exception {
		createTestEntityWithAttributeOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		AttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		AttributeOverride specifiedAttributeOverride = overrideContainer.getSpecifiedOverrides().iterator().next();
		Column column = specifiedAttributeOverride.getColumn();
		assertEquals(ATTRIBUTE_OVERRIDE_COLUMN_NAME, column.getName());
		
		column.setSpecifiedName("FOO");
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, Kind.TYPE);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) resourceType.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		ColumnAnnotation columnResource = attributeOverrideResource.getColumn();

		assertEquals("FOO", columnResource.getName());
		
		column.setSpecifiedName(null);
		
		attributeOverrideResource = (AttributeOverrideAnnotation) resourceType.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
		assertNull(attributeOverrideResource.getColumn());
		assertNotNull(specifiedAttributeOverride.getColumn());
	}
	
	public void testColumnDefaultName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		Entity entity = getJavaEntity();
		AttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		
		ReadOnlyAttributeOverride attributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", attributeOverride.getColumn().getDefaultName());
		
		
		JavaPersistentType mappedSuperclass = CollectionTools.list(getPersistenceUnit().getSpecifiedClassRefs()).get(1).getJavaPersistentType();
		BasicMapping basicMapping = (BasicMapping) mappedSuperclass.getAttributeNamed("id").getMapping();
		basicMapping.getColumn().setSpecifiedName("FOO");
	
		attributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("FOO", attributeOverride.getColumn().getDefaultName());
	}
	
	public void testColumnDefaultTableName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		Entity entity = getJavaEntity();
		AttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		
		ReadOnlyAttributeOverride attributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("AnnotationTestTypeChild", attributeOverride.getColumn().getDefaultTable());

		
		JavaPersistentType mappedSuperclass = CollectionTools.list(getPersistenceUnit().getSpecifiedClassRefs()).get(1).getJavaPersistentType();
		BasicMapping basicMapping = (BasicMapping) mappedSuperclass.getAttributeNamed("id").getMapping();
		basicMapping.getColumn().setSpecifiedTable("BAR");
	
		attributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("BAR", attributeOverride.getColumn().getDefaultTable());
	}
	
	public void testDefaultName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();	
		AttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		
		ReadOnlyAttributeOverride attributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertEquals("id", attributeOverride.getName());
	}
	
	public void testIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = getJavaEntity();	
		AttributeOverrideContainer overrideContainer = entity.getAttributeOverrideContainer();
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(2, overrideContainer.getVirtualOverridesSize());
		
		ReadOnlyAttributeOverride attributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		assertTrue(attributeOverride.isVirtual());
	}
	
	public void testSetColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaAttributeOverrideContainer overrideContainer = getJavaEntity().getAttributeOverrideContainer();
		JavaVirtualAttributeOverride attributeOverride = overrideContainer.getVirtualOverrides().iterator().next();
		attributeOverride.convertToSpecified().getColumn().setSpecifiedName("FOO");
		
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(PACKAGE_NAME + ".AnnotationTestTypeChild", Kind.TYPE);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) resourceType.getAnnotation(0, JPA.ATTRIBUTE_OVERRIDE);
	
		assertEquals("FOO", attributeOverrideResource.getColumn().getName());
		assertEquals("FOO", overrideContainer.getSpecifiedOverrides().iterator().next().getColumn().getSpecifiedName());
	}
}
