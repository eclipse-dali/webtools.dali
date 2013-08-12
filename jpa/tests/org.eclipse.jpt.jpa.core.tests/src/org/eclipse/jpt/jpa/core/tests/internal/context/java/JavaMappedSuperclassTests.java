/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement.AstNodeType;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness.SourceWriter;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.IdClassReference;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.jpa.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class JavaMappedSuperclassTests extends ContextModelTestCase
{
	public JavaMappedSuperclassTests(String name) {
		super(name);
	}
	
	
	private ICompilationUnit createTestMappedSuperclass() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return IteratorTools.iterator(JPA.MAPPED_SUPERCLASS);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@MappedSuperclass");
			}
		});
	}
	
	private void createTestIdClass() throws Exception {
		SourceWriter sourceWriter = new SourceWriter() {
			public void appendSourceTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("public class ").append("TestTypeId").append(" ");
				sb.append("{}").append(CR);
			}
		};
		this.javaProjectTestHarness.createCompilationUnit(PACKAGE_NAME, "TestTypeId.java", sourceWriter);
	}
	
	public void testMorphToEntity() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		mappedSuperclass.getIdClassReference().setSpecifiedIdClassName("myIdClass");
		
		getJavaPersistentType().setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof Entity);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		assertNull(resourceType.getAnnotation(MappedSuperclassAnnotation.ANNOTATION_NAME));
		assertNotNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddable() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		mappedSuperclass.getIdClassReference().setSpecifiedIdClassName("myIdClass");

		getJavaPersistentType().setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof Embeddable);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		assertNull(resourceType.getAnnotation(MappedSuperclassAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToNull() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		mappedSuperclass.getIdClassReference().setSpecifiedIdClassName("myIdClass");

		getJavaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertTrue(getJavaPersistentType().getMapping() instanceof JavaNullTypeMapping);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		assertNull(resourceType.getAnnotation(MappedSuperclassAnnotation.ANNOTATION_NAME));
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
	}
	
	public void testMappedSuperclass() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertTrue(getJavaPersistentType().getMapping() instanceof MappedSuperclass);
	}
	
	public void testOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		Iterator<String> overridableAttributeNames = mappedSuperclass.getOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	public void testOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		Iterator<String> overridableAssociationNames = mappedSuperclass.getOverridableAssociationNames().iterator();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testTableNameIsInvalid() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();

		assertFalse(mappedSuperclass.tableNameIsInvalid(FULLY_QUALIFIED_TYPE_NAME));
		assertFalse(mappedSuperclass.tableNameIsInvalid("FOO"));
	}

	public void testAssociatedTables() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();

		assertFalse(mappedSuperclass.getAssociatedTables().iterator().hasNext());
	}

	public void testAssociatedTablesIncludingInherited() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();

		assertFalse(mappedSuperclass.getAllAssociatedTables().iterator().hasNext());
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();

		assertFalse(mappedSuperclass.getAllAssociatedTableNames().iterator().hasNext());
	}
	
	public void testAllOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		Iterator<String> overridableAttributeNames = mappedSuperclass.getOverridableAttributeNames().iterator();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	//TODO need to create a subclass mappedSuperclass and test this
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		Iterator<String> overridableAssociationNames = mappedSuperclass.getOverridableAssociationNames().iterator();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY));
	}

	public void testUpdateIdClass() throws Exception {
		createTestMappedSuperclass();
		createTestIdClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		IdClassReference idClassRef = mappedSuperclass.getIdClassReference();
		
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		IdClassAnnotation idClassAnnotation = (IdClassAnnotation) resourceType.addAnnotation(IdClassAnnotation.ANNOTATION_NAME);	
		this.getJpaProject().synchronizeContextModel();
		assertNotNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		// test setting id class name to nonexistent class.  test class name is set, but class is null
		String nonExistentIdClassName = PACKAGE_NAME + ".Foo";
		idClassAnnotation.setValue(nonExistentIdClassName);
		this.getJpaProject().synchronizeContextModel();
		assertEquals(nonExistentIdClassName, ((IdClassAnnotation) resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertEquals(nonExistentIdClassName, idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		// test setting id class name to existent class.  test class name is set and class is not null
		String existentIdClassName = PACKAGE_NAME + ".TestTypeId";
		idClassAnnotation.setValue(existentIdClassName);
		this.getJpaProject().synchronizeContextModel();
		assertEquals(existentIdClassName, ((IdClassAnnotation) resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertEquals(existentIdClassName, idClassRef.getSpecifiedIdClassName());
		assertNotNull(idClassRef.getIdClass());
		
		//test setting  @IdClass value to null, IdClass annotation is removed
		idClassRef.setSpecifiedIdClassName(null);
		this.getJpaProject().synchronizeContextModel();
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		//reset @IdClass value and then remove @IdClass
		idClassAnnotation = (IdClassAnnotation) resourceType.addAnnotation(IdClassAnnotation.ANNOTATION_NAME);	
		idClassAnnotation.setValue(existentIdClassName);
		resourceType.removeAnnotation(IdClassAnnotation.ANNOTATION_NAME);
		getJpaProject().synchronizeContextModel();
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());		
	}
	
	public void testModifyIdClass() throws Exception {
		createTestMappedSuperclass();
		createTestIdClass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		JavaResourceType resourceType = (JavaResourceType) getJpaProject().getJavaResourceType(FULLY_QUALIFIED_TYPE_NAME, AstNodeType.TYPE);
		MappedSuperclass mappedSuperclass = (MappedSuperclass) getJavaPersistentType().getMapping();
		IdClassReference idClassRef = mappedSuperclass.getIdClassReference();
		
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		String nonExistentIdClassName = PACKAGE_NAME + ".Foo";
		idClassRef.setSpecifiedIdClassName(nonExistentIdClassName);
		assertEquals(nonExistentIdClassName, ((IdClassAnnotation) resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertEquals(nonExistentIdClassName, idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
		
		String existentIdClassName = PACKAGE_NAME + ".TestTypeId";
		idClassRef.setSpecifiedIdClassName(existentIdClassName);
		assertEquals(existentIdClassName, ((IdClassAnnotation) resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertEquals(existentIdClassName, idClassRef.getSpecifiedIdClassName());
		assertNotNull(idClassRef.getIdClass());
		
		idClassRef.setSpecifiedIdClassName(null);
		assertNull(resourceType.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		assertNull(idClassRef.getSpecifiedIdClassName());
		assertNull(idClassRef.getIdClass());
	}
}
