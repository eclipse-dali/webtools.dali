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
import java.util.ListIterator;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.IEmbeddable;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IMappedSuperclass;
import org.eclipse.jpt.core.internal.context.base.INamedQuery;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMapping;
import org.eclipse.jpt.core.internal.resource.java.JPA;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentAttributeResource;
import org.eclipse.jpt.core.internal.resource.java.JavaPersistentTypeResource;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclass;
import org.eclipse.jpt.core.internal.resource.java.NamedQueries;
import org.eclipse.jpt.core.internal.resource.java.NamedQuery;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
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

	public void testAddNamedQuery() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
		
		INamedQuery namedQuery = mappedSuperclass.addNamedQuery(0);
		namedQuery.setName("FOO");
		
		ListIterator<NamedQuery> javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("FOO", javaNamedQueries.next().getName());
		
		INamedQuery namedQuery2 = mappedSuperclass.addNamedQuery(0);
		namedQuery2.setName("BAR");
		
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());
		
		INamedQuery namedQuery3 = mappedSuperclass.addNamedQuery(1);
		namedQuery3.setName("BAZ");
		
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("BAZ", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());
		
		ListIterator<INamedQuery> namedQueries = mappedSuperclass.namedQueries();
		assertEquals(namedQuery2, namedQueries.next());
		assertEquals(namedQuery3, namedQueries.next());
		assertEquals(namedQuery, namedQueries.next());
		
		namedQueries = mappedSuperclass.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
	}
	
	public void testRemoveNamedQuery() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		mappedSuperclass.addNamedQuery(0).setName("FOO");
		mappedSuperclass.addNamedQuery(1).setName("BAR");
		mappedSuperclass.addNamedQuery(2).setName("BAZ");
		
		ListIterator<NamedQuery> javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		mappedSuperclass.removeNamedQuery(0);
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals(2, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("BAZ", javaNamedQueries.next().getName());

		mappedSuperclass.removeNamedQuery(0);
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals(1, CollectionTools.size(javaNamedQueries));
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAZ", javaNamedQueries.next().getName());
		
		mappedSuperclass.removeNamedQuery(0);
		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals(0, CollectionTools.size(javaNamedQueries));
	}
	
	public void testMoveNamedQuery() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);

		mappedSuperclass.addNamedQuery(0).setName("FOO");
		mappedSuperclass.addNamedQuery(1).setName("BAR");
		mappedSuperclass.addNamedQuery(2).setName("BAZ");
		
		ListIterator<NamedQuery> javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals(3, CollectionTools.size(javaNamedQueries));
		
		
		mappedSuperclass.moveNamedQuery(2, 0);
		ListIterator<INamedQuery> namedQueries = mappedSuperclass.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("BAZ", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());


		mappedSuperclass.moveNamedQuery(0, 1);
		namedQueries = mappedSuperclass.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());

		javaNamedQueries = typeResource.annotations(NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		assertEquals("BAZ", javaNamedQueries.next().getName());
		assertEquals("BAR", javaNamedQueries.next().getName());
		assertEquals("FOO", javaNamedQueries.next().getName());
	}
	
	public void testUpdateNamedQueries() throws Exception {
		createTestMappedSuperclass();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		IMappedSuperclass mappedSuperclass = (IMappedSuperclass) javaPersistentType().getMapping();
		JavaPersistentTypeResource typeResource = jpaProject().javaPersistentTypeResource(FULLY_QUALIFIED_TYPE_NAME);
	
		((NamedQuery) typeResource.addAnnotation(0, NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME)).setName("FOO");
		((NamedQuery) typeResource.addAnnotation(1, NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME)).setName("BAR");
		((NamedQuery) typeResource.addAnnotation(2, NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME)).setName("BAZ");
			
		ListIterator<INamedQuery> namedQueries = mappedSuperclass.namedQueries();
		assertEquals("FOO", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.move(2, 0, NamedQueries.ANNOTATION_NAME);
		namedQueries = mappedSuperclass.namedQueries();
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
	
		typeResource.move(0, 1, NamedQueries.ANNOTATION_NAME);
		namedQueries = mappedSuperclass.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("BAR", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
	
		typeResource.removeAnnotation(1,  NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		namedQueries = mappedSuperclass.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertEquals("FOO", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
	
		typeResource.removeAnnotation(1,  NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		namedQueries = mappedSuperclass.namedQueries();
		assertEquals("BAZ", namedQueries.next().getName());
		assertFalse(namedQueries.hasNext());
		
		typeResource.removeAnnotation(0,  NamedQuery.ANNOTATION_NAME, NamedQueries.ANNOTATION_NAME);
		namedQueries = mappedSuperclass.namedQueries();
		assertFalse(namedQueries.hasNext());
	}

}
