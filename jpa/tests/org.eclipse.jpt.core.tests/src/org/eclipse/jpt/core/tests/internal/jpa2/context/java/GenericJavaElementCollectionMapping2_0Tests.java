/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.java;

import java.util.Iterator;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.ElementCollection2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.core.resource.java.BasicAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedAnnotation;
import org.eclipse.jpt.core.resource.java.EmbeddedIdAnnotation;
import org.eclipse.jpt.core.resource.java.IdAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.java.ManyToManyAnnotation;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.core.resource.java.TransientAnnotation;
import org.eclipse.jpt.core.resource.java.VersionAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericJavaElementCollectionMapping2_0Tests extends Generic2_0ContextModelTestCase
{

	private ICompilationUnit createTestEntityWithElementCollectionMapping() throws Exception {
		return this.createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA2_0.ELEMENT_COLLECTION);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append("@ElementCollection").append(CR);
			}
		});
	}

	public GenericJavaElementCollectionMapping2_0Tests(String name) {
		super(name);
	}
	
	public void testMorphToBasicMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof BasicMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(BasicAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToDefault() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY);
		assertNull(persistentAttribute.getSpecifiedMapping());
		assertTrue(persistentAttribute.getMapping().isDefault());
	
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
	}
	
	public void testMorphToVersionMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof VersionMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(VersionAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToIdMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof IdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(IdAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(EmbeddedAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToEmbeddedIdMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(EmbeddedIdAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToTransientMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof TransientMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(TransientAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToOneMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToOneToManyMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof OneToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(OneToManyAnnotation.ANNOTATION_NAME));
	}
	
	public void testMorphToManyToManyMapping() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		assertFalse(elementCollectionMapping.isDefault());
		
		persistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertTrue(persistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertFalse(persistentAttribute.getMapping().isDefault());
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		assertNull(attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME));
		assertNotNull(attributeResource.getAnnotation(ManyToManyAnnotation.ANNOTATION_NAME));
	}

//	
//	public void testUpdateSpecifiedTargetEntity() throws Exception {
//		createTestEntityWithOneToOneMapping();
//		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
//		
//		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
//		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
//		
//		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
//		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
//		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
//		
//		assertNull(oneToOneMapping.getSpecifiedTargetEntity());
//		assertNull(oneToOne.getTargetEntity());
//				
//		//set target entity in the resource model, verify context model updated
//		oneToOne.setTargetEntity("newTargetEntity");
//		assertEquals("newTargetEntity", oneToOneMapping.getSpecifiedTargetEntity());
//		assertEquals("newTargetEntity", oneToOne.getTargetEntity());
//	
//		//set target entity to null in the resource model
//		oneToOne.setTargetEntity(null);
//		assertNull(oneToOneMapping.getSpecifiedTargetEntity());
//		assertNull(oneToOne.getTargetEntity());
//	}
//	
//	public void testModifySpecifiedTargetEntity() throws Exception {
//		createTestEntityWithOneToOneMapping();
//		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
//		
//		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
//		OneToOneMapping oneToOneMapping = (OneToOneMapping) persistentAttribute.getMapping();
//		
//		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
//		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
//		OneToOneAnnotation oneToOne = (OneToOneAnnotation) attributeResource.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME);
//		
//		assertNull(oneToOneMapping.getSpecifiedTargetEntity());
//		assertNull(oneToOne.getTargetEntity());
//				
//		//set target entity in the context model, verify resource model updated
//		oneToOneMapping.setSpecifiedTargetEntity("newTargetEntity");
//		assertEquals("newTargetEntity", oneToOneMapping.getSpecifiedTargetEntity());
//		assertEquals("newTargetEntity", oneToOne.getTargetEntity());
//	
//		//set target entity to null in the context model
//		oneToOneMapping.setSpecifiedTargetEntity(null);
//		assertNull(oneToOneMapping.getSpecifiedTargetEntity());
//		assertNull(oneToOne.getTargetEntity());
//	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ElementCollection2_0Annotation elementCollection = (ElementCollection2_0Annotation) attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME);
		
		assertNull(elementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
				
		//set fetch in the resource model, verify context model updated
		elementCollection.setFetch(org.eclipse.jpt.core.resource.java.FetchType.EAGER);
		assertEquals(FetchType.EAGER, elementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.EAGER, elementCollection.getFetch());
		
		elementCollection.setFetch(org.eclipse.jpt.core.resource.java.FetchType.LAZY);
		assertEquals(FetchType.LAZY, elementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, elementCollection.getFetch());
		
		//set fetch to null in the resource model
		elementCollection.setFetch(null);
		assertNull(elementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		createTestEntityWithElementCollectionMapping();
		addXmlClassRef(FULLY_QUALIFIED_TYPE_NAME);
		
		PersistentAttribute persistentAttribute = getJavaPersistentType().attributes().next();
		ElementCollectionMapping2_0 elementCollectionMapping = (ElementCollectionMapping2_0) persistentAttribute.getMapping();
		
		JavaResourcePersistentType typeResource = getJpaProject().getJavaResourcePersistentType(FULLY_QUALIFIED_TYPE_NAME);
		JavaResourcePersistentAttribute attributeResource = typeResource.persistableAttributes().next();
		ElementCollection2_0Annotation elementCollection = (ElementCollection2_0Annotation) attributeResource.getAnnotation(ElementCollection2_0Annotation.ANNOTATION_NAME);
		
		assertNull(elementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
				
		//set fetch in the context model, verify resource model updated
		elementCollectionMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(FetchType.EAGER, elementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.EAGER, elementCollection.getFetch());
	
		elementCollectionMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(FetchType.LAZY, elementCollectionMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.java.FetchType.LAZY, elementCollection.getFetch());
		
		//set fetch to null in the context model
		elementCollectionMapping.setSpecifiedFetch(null);
		assertNull(elementCollectionMapping.getSpecifiedFetch());
		assertNull(elementCollection.getFetch());
	}
}
