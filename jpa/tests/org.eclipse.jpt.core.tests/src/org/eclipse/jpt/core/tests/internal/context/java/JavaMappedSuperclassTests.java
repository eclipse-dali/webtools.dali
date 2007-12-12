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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IMappedSuperclass;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclass;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaMappedSuperclassTests extends ContextModelTestCase
{

	private void createMappedSuperclassAnnotation() throws Exception {
		this.createAnnotationAndMembers("MappedSuperclass", "");		
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


	public JavaMappedSuperclassTests(String name) {
		super(name);
	}
	
	public void testMorphToEntity() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		javaPersistentType().setMappingKey(IMappingKeys.ENTITY_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof IEntity);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(MappedSuperclass.ANNOTATION_NAME));
	}

	public void testMorphToEmbeddable() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		javaPersistentType().setMappingKey(IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof IEmbeddable);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		JavaPersistentAttributeResource attributeResource = typeResource.attributes().next();
		assertNull(attributeResource.mappingAnnotation(MappedSuperclass.ANNOTATION_NAME));
	}
	
	public void testMorphToNull() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
				
		javaPersistentType().setMappingKey(IMappingKeys.NULL_TYPE_MAPPING_KEY);
		assertTrue(javaPersistentType().getMapping() instanceof JavaNullTypeMapping);
		
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		assertNull(typeResource.mappingAnnotation(MappedSuperclass.ANNOTATION_NAME));
	}

	
	public void testMappedSuperclass() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		assertTrue(javaPersistentType().getMapping() instanceof IMappedSuperclass);
	}
	
	public void testOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
		Iterator<String> overridableAttributeNames = mappedSuperclass.overridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	public void testOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
		Iterator<String> overridableAssociationNames = mappedSuperclass.overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testTableNameIsInvalid() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();

		assertFalse(mappedSuperclass.tableNameIsInvalid(FULLY_QUALIFIED_TYPE_NAME));
		assertFalse(mappedSuperclass.tableNameIsInvalid("FOO"));
	}

	public void testAssociatedTables() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();

		assertFalse(mappedSuperclass.associatedTables().hasNext());
	}

	public void testAssociatedTablesIncludingInherited() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();

		assertFalse(mappedSuperclass.associatedTablesIncludingInherited().hasNext());
	}
	
	public void testAssociatedTableNamesIncludingInherited() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();

		assertFalse(mappedSuperclass.associatedTableNamesIncludingInherited().hasNext());
	}
	
	public void testAllOverridableAttributeNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
	
		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
		Iterator<String> overridableAttributeNames = mappedSuperclass.overridableAttributeNames();
		assertEquals("id", overridableAttributeNames.next());
		assertEquals("name", overridableAttributeNames.next());
		assertFalse(overridableAttributeNames.hasNext());
	}
	
	//TODO need to create a subclass mappedSuperclass and test this
	public void testAllOverridableAssociationNames() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
		Iterator<String> overridableAssociationNames = mappedSuperclass.overridableAssociationNames();
		assertFalse(overridableAssociationNames.hasNext());
	}
	
	public void testAttributeMappingKeyAllowed() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY));
		assertTrue(mappedSuperclass.attributeMappingKeyAllowed(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY));
	}


}
