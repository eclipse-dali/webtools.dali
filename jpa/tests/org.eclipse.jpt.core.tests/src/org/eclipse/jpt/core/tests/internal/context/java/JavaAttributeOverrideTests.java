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
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject.SourceWriter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaAttributeOverrideTests extends ContextModelTestCase
{
	private static final String ATTRIBUTE_OVERRIDE_NAME = "MY_ATTRIBUTE_OVERRIDE_NAME";
	private static final String ATTRIBUTE_OVERRIDE_COLUMN_NAME = "MY_ATTRIBUTE_OVERRIDE_COLUMN_NAME";

	private void createEntityAnnotation() throws Exception{
		this.createAnnotationAndMembers("Entity", "String name() default \"\";");		
	}
	
	private void createMappedSuperclassAnnotation() throws Exception{
		this.createAnnotationAndMembers("MappedSuperclass", "");		
	}
	
	private void createColumnAnnotation() throws Exception {
		this.createAnnotationAndMembers("Column", 
			"String name() default \"\";" +
			"boolean unique() default false;" +
			"boolean nullable() default true;" +
			"boolean insertable() default true;" +
			"boolean updatable() default true;" +
			"String columnDefinition() default \"\";" +
			"String table() default \"\";" +
			"int length() default 255;" +
			"int precision() default 0;" +
			"int scale() default 0;");		
	}
	
	private void createAttributeOverrideAnnotation() throws Exception {
		createColumnAnnotation();
		this.createAnnotationAndMembers("AttributeOverride", 
			"String name();" +
			"Column column();");		
	}
		
	private IType createTestMappedSuperclass() throws Exception {
		createMappedSuperclassAnnotation();
		
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

	private IType createTestEntityWithAttributeOverride() throws Exception {
		createEntityAnnotation();
		createAttributeOverrideAnnotation();
	
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
	
	private IType createTestEntity() throws Exception {
		createEntityAnnotation();
	
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

	private IType createTestSubType() throws Exception {
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
		return this.javaProject.createType(PACKAGE_NAME, "AnnotationTestTypeChild.java", sourceWriter);
	}


		
	public JavaAttributeOverrideTests(String name) {
		super(name);
	}
	
	public void testGetName() throws Exception {
		createTestEntityWithAttributeOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		AttributeOverride specifiedAttributeOverride = javaEntity().specifiedAttributeOverrides().next();
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, specifiedAttributeOverride.getName());
		
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) typeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
		
		attributeOverrideResource.setName("FOO");
		specifiedAttributeOverride = javaEntity().specifiedAttributeOverrides().next();
		assertEquals("FOO", specifiedAttributeOverride.getName());
	}
	
	public void testSetName() throws Exception {
		createTestEntityWithAttributeOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		AttributeOverride specifiedAttributeOverride = javaEntity().specifiedAttributeOverrides().next();
		assertEquals(ATTRIBUTE_OVERRIDE_NAME, specifiedAttributeOverride.getName());
		
		specifiedAttributeOverride.setName("FOO");
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) typeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
		
		assertEquals("FOO", attributeOverrideResource.getName());
	}

	public void testColumnGetName() throws Exception {
		createTestEntityWithAttributeOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		AttributeOverride specifiedAttributeOverride = javaEntity().specifiedAttributeOverrides().next();
		Column column = specifiedAttributeOverride.getColumn();
		assertEquals(ATTRIBUTE_OVERRIDE_COLUMN_NAME, column.getName());
		
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) typeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
		ColumnAnnotation columnResource = attributeOverrideResource.getColumn();
		columnResource.setName("FOO");
		
		
		column = javaEntity().specifiedAttributeOverrides().next().getColumn();
		assertEquals("FOO", column.getName());
	}
	
	public void testColumnSetName() throws Exception {
		createTestEntityWithAttributeOverride();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		AttributeOverride specifiedAttributeOverride = javaEntity().specifiedAttributeOverrides().next();
		Column column = specifiedAttributeOverride.getColumn();
		assertEquals(ATTRIBUTE_OVERRIDE_COLUMN_NAME, column.getName());
		
		column.setSpecifiedName("FOO");
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) typeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
		ColumnAnnotation columnResource = attributeOverrideResource.getColumn();

		assertEquals("FOO", columnResource.getName());
		
		column.setSpecifiedName(null);
		
		attributeOverrideResource = (AttributeOverrideAnnotation) typeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
		assertNull(attributeOverrideResource.getColumn());
		assertNotNull(specifiedAttributeOverride.getColumn());
	}
	
	public void testColumnDefaultName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		Entity entity = javaEntity();
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(2, entity.virtualAttributeOverridesSize());
		
		AttributeOverride attributeOverride = entity.virtualAttributeOverrides().next();
		assertEquals("id", attributeOverride.getColumn().getDefaultName());
		
		
		JavaPersistentType mappedSuperclass = CollectionTools.list(persistenceUnit().specifiedClassRefs()).get(1).getJavaPersistentType();
		BasicMapping basicMapping = (BasicMapping) mappedSuperclass.attributeNamed("id").getMapping();
		basicMapping.getColumn().setSpecifiedName("FOO");
	
		attributeOverride = entity.virtualAttributeOverrides().next();
		assertEquals("FOO", attributeOverride.getColumn().getDefaultName());
	}
	
	public void testColumnDefaultTableName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		Entity entity = javaEntity();
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(2, entity.virtualAttributeOverridesSize());
		
		AttributeOverride attributeOverride = entity.virtualAttributeOverrides().next();
		assertEquals("AnnotationTestTypeChild", attributeOverride.getColumn().getDefaultTable());

		
		JavaPersistentType mappedSuperclass = CollectionTools.list(persistenceUnit().specifiedClassRefs()).get(1).getJavaPersistentType();
		BasicMapping basicMapping = (BasicMapping) mappedSuperclass.attributeNamed("id").getMapping();
		basicMapping.getColumn().setSpecifiedTable("BAR");
	
		attributeOverride = entity.virtualAttributeOverrides().next();
		assertEquals("BAR", attributeOverride.getColumn().getDefaultTable());
	}
	
	public void testDefaultName() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = javaEntity();	
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(2, entity.virtualAttributeOverridesSize());
		
		AttributeOverride attributeOverride = entity.virtualAttributeOverrides().next();
		assertEquals("id", attributeOverride.getName());
	}
	
	public void testIsVirtual() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = javaEntity();	
		assertEquals("AnnotationTestTypeChild", entity.getName());
		assertEquals(2, entity.virtualAttributeOverridesSize());
		
		AttributeOverride attributeOverride = entity.virtualAttributeOverrides().next();
		assertTrue(attributeOverride.isVirtual());
	}
	
	public void testSetColumn() throws Exception {
		createTestMappedSuperclass();
		createTestSubType();
		addXmlClassRef(PACKAGE_NAME + ".AnnotationTestTypeChild");
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		Entity entity = javaEntity();	
		AttributeOverride attributeOverride = entity.virtualAttributeOverrides().next();
		attributeOverride = attributeOverride.setVirtual(false);
		attributeOverride.getColumn().setSpecifiedName("FOO");
		
		
		JavaResourcePersistentType typeResource = jpaProject().javaPersistentTypeResource(PACKAGE_NAME + ".AnnotationTestTypeChild");
		AttributeOverrideAnnotation attributeOverrideResource = (AttributeOverrideAnnotation) typeResource.annotation(JPA.ATTRIBUTE_OVERRIDE);
	
		assertEquals("FOO", attributeOverrideResource.getColumn().getName());
		assertEquals("FOO", entity.specifiedAttributeOverrides().next().getColumn().getSpecifiedName());
	}
}
