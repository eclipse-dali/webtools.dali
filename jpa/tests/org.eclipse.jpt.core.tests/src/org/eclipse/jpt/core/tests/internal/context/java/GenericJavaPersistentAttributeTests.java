/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericJavaPersistentAttributeTests extends ContextModelTestCase
{

	private ICompilationUnit createTestEntityAnnotatedField() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

	private ICompilationUnit createTestEntityAnnotatedMethod() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
			}
			
			@Override
			public void appendGetIdMethodAnnotationTo(StringBuilder sb) {
				sb.append("@Id");
			}
		});
	}

		
	public GenericJavaPersistentAttributeTests(String name) {
		super(name);
	}
		
	public void testGetName() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		
		assertEquals("id", persistentAttribute.getName());
	}
	
	public void testGetMapping() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		assertTrue(persistentAttribute.getMapping() instanceof JavaIdMapping);

		persistentAttribute.setSpecifiedMappingKey(null);
		assertTrue(persistentAttribute.getMapping() instanceof JavaBasicMapping);
	}
	
	public void testGetSpecifiedMapping() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		assertTrue(persistentAttribute.getSpecifiedMapping() instanceof JavaIdMapping);

		persistentAttribute.setSpecifiedMappingKey(null);
		assertNull(persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetSpecifiedMappingNull() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();

		assertNull(persistentAttribute.getSpecifiedMapping());
		assertNotNull(persistentAttribute.getMapping());
	}
	
	public void testMappingKey() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();

		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		
		persistentAttribute.setSpecifiedMappingKey(null);
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
	}
	
	public void testDefaultMappingKey() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);

		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();

		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getDefaultMappingKey());
	}
	
	public void testSetSpecifiedMappingKey() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		assertNull(persistentAttribute.getSpecifiedMapping());

		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		assertTrue(persistentAttribute.getSpecifiedMapping() instanceof JavaEmbeddedMapping);
	}
	
	public void testSetSpecifiedMappingKey2() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());

		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNotNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
		
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		assertTrue(persistentAttribute.getSpecifiedMapping() instanceof JavaEmbeddedMapping);
	}

	public void testSetSpecifiedMappingKeyNull() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());

		persistentAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
		
		assertNull(persistentAttribute.getSpecifiedMapping());
	}
	
	public void testGetMappingKeyMappingChangeInResourceModel() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		assertEquals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.setPrimaryAnnotation(EmbeddedAnnotation.ANNOTATION_NAME, EmptyIterable.<String>instance());
		
		assertEquals(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getMappingKey());
	}
	
	public void testGetMappingKeyMappingChangeInResourceModel2() throws Exception {
		createTestType();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		assertNull(persistentAttribute.getSpecifiedMapping());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		attributeResource.setPrimaryAnnotation(BasicAnnotation.ANNOTATION_NAME, EmptyIterable.<String>instance());
				
		assertEquals(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY, persistentAttribute.getSpecifiedMapping().getKey());
	}
	
	public void testGetAccessField() throws Exception {
		createTestEntityAnnotatedField();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		assertEquals(AccessType.FIELD, persistentAttribute.getAccess());
		assertEquals(AccessType.FIELD, persistentAttribute.getDefaultAccess());
		assertEquals(null, persistentAttribute.getSpecifiedAccess());
	}
	
	public void testGetAccessProperty() throws Exception {
		createTestEntityAnnotatedMethod();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		assertEquals(AccessType.PROPERTY, persistentAttribute.getAccess());
		assertEquals(AccessType.PROPERTY, persistentAttribute.getDefaultAccess());
		assertEquals(null, persistentAttribute.getSpecifiedAccess());
	}
}
