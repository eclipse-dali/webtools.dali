/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.java;

import java.util.Iterator;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Embeddable;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.resource.java.IdClassAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaMappedSuperclassTests extends ContextModelTestCase
{
	
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


	public JavaMappedSuperclassTests(String name) {
		super(name);
	}
	
	public void testMorphToEntity() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		mappedSuperclass.setIdClass("myIdClass");
		
		javaPersistentType().setMappingKey(MappingKeys.ENTITY_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof Entity);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.getMappingAnnotation(MappedSuperclassAnnotation.ANNOTATION_NAME));
		assertNotNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddable() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		mappedSuperclass.setIdClass("myIdClass");

		javaPersistentType().setMappingKey(MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof Embeddable);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.getMappingAnnotation(MappedSuperclassAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToNull() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		mappedSuperclass.setIdClass("myIdClass");

		javaPersistentType().setMappingKey(MappingKeys.NULL_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof JavaNullTypeMapping);
		
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.getMappingAnnotation(MappedSuperclassAnnotation.ANNOTATION_NAME));
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
	}

	
	public void testMappedSuperclass() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertTrue(javaPersistentType().getMapping() instanceof MappedSuperclass);
	}
	
	public void testOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		Iterator<String> overridableAttributeNames = mappedSuperclass.overridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	public void testOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		Iterator<String> overridableAssociationNames = mappedSuperclass.overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testTableNameIsInvalid() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();

		assertFalse(mappedSuperclass.tableNameIsInvalid(FULLY_QUALIFIED_TYPE_NAME));
		assertFalse(mappedSuperclass.tableNameIsInvalid("FOO"));
	}

	public void testAssociatedTables() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();

		assertFalse(mappedSuperclass.associatedTables().hasNext());
	}

	public void testAssociatedTablesIncludingInherited() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();

		assertFalse(mappedSuperclass.associatedTablesIncludingInherited().hasNext());
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();

		assertFalse(mappedSuperclass.associatedTableNamesIncludingInherited().hasNext());
	}
	
	public void testAllOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		Iterator<String> overridableAttributeNames = mappedSuperclass.overridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	//TODO need to create a subclass mappedSuperclass and test this
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
		Iterator<String> overridableAssociationNames = mappedSuperclass.overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();
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
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();

		assertNull(mappedSuperclass.getIdClass());
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		
		IdClassAnnotation idClass = (IdClassAnnotation) typeResource.addAnnotation(IdClassAnnotation.ANNOTATION_NAME);	
		assertNull(mappedSuperclass.getIdClass());
		assertNotNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		
		idClass.setValue("model.Foo");
		assertEquals("model.Foo", mappedSuperclass.getIdClass());
		assertEquals("model.Foo", ((IdClassAnnotation) typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		
		//test setting  @IdClass value to null, IdClass annotation is removed
		idClass.setValue(null);
		assertNull(mappedSuperclass.getIdClass());
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
		
		//reset @IdClass value and then remove @IdClass
		idClass = (IdClassAnnotation) typeResource.addAnnotation(IdClassAnnotation.ANNOTATION_NAME);	
		idClass.setValue("model.Foo");
		typeResource.removeAnnotation(IdClassAnnotation.ANNOTATION_NAME);
		
		assertNull(mappedSuperclass.getIdClass());
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));		
	}
	
	public void testModifyIdClass() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		JavaResourcePersistentType typeResource = jpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		MappedSuperclass mappedSuperclass = (MappedSuperclass) javaPersistentType().getMapping();

		assertNull(mappedSuperclass.getIdClass());
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
			
		mappedSuperclass.setIdClass("model.Foo");
		assertEquals("model.Foo", ((IdClassAnnotation) typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME)).getValue());
		assertEquals("model.Foo", mappedSuperclass.getIdClass());
		
		mappedSuperclass.setIdClass(null);
		assertNull(mappedSuperclass.getIdClass());
		assertNull(typeResource.getAnnotation(IdClassAnnotation.ANNOTATION_NAME));
	}

}
