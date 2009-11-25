/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.jpa2.context.orm.OrmManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneMapping2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlDerivedId_2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlMapsId_2_0;
import org.eclipse.jpt.core.tests.internal.jpa2.context.Generic2_0ContextModelTestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

@SuppressWarnings("nls")
public class GenericOrmManyToOneMapping2_0Tests
	extends Generic2_0ContextModelTestCase
{
	public GenericOrmManyToOneMapping2_0Tests(String name) {
		super(name);
	}
	
	
	private void createTestEntity() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE);
			}
			
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity");
				sb.append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne");
				sb.append(CR);
				sb.append("    private Address address;");
				sb.append(CR);
				sb.append(CR);
				sb.append("    @Id");			
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.makeSpecified();
		}
	}
	
	private void createTestEntityWithDerivedId() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA.ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne @Id").append(CR);				
				sb.append("    private " + TYPE_NAME + " manyToOne;").append(CR);
				sb.append(CR);				
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.makeSpecified();
		}
	}
	
	public void testUpdateDerivedId() throws Exception {
		createTestEntityWithDerivedId();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		OrmManyToOneMapping2_0 contextMapping = (OrmManyToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlDerivedId_2_0 resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedId().getValue());
		
		resourceMapping.setId(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceMapping.getId());
		assertFalse(contextMapping.getDerivedId().getValue());
		
		resourceMapping.setId(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMapping.getId());
		assertTrue(contextMapping.getDerivedId().getValue());
		
		resourceMapping.setId(null);
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedId().getValue());
	}
	
	public void testSetDerivedId() throws Exception {
		createTestEntityWithDerivedId();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		OrmManyToOneMapping2_0 contextMapping = (OrmManyToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlDerivedId_2_0 resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedId().getValue());
		
		contextMapping.getDerivedId().setValue(true);
		assertEquals(Boolean.TRUE, resourceMapping.getId());
		assertTrue(contextMapping.getDerivedId().getValue());
		
		contextMapping.getDerivedId().setValue(false);
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedId().getValue());
	}
	
	public void testUpdateMapsId() throws Exception {
		createTestEntity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("address");
		OrmManyToOneMapping2_0 contextMapping = (OrmManyToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlMapsId_2_0 resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getMapsId().getValue());
		
		resourceMapping.setMapsId("foo");
		assertEquals("foo", resourceMapping.getMapsId());
		assertEquals("foo", contextMapping.getMapsId().getValue());
		
		resourceMapping.setMapsId("bar");
		assertEquals("bar", resourceMapping.getMapsId());
		assertEquals("bar", contextMapping.getMapsId().getValue());
		
		resourceMapping.setMapsId(null);
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getMapsId().getValue());
	}
	
	public void testSetMapsId() throws Exception {
		createTestEntity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("address");
		OrmManyToOneMapping2_0 contextMapping = (OrmManyToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlMapsId_2_0 resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getMapsId().getValue());
		
		contextMapping.getMapsId().setValue("foo");
		assertEquals("foo", resourceMapping.getMapsId());
		assertEquals("foo", contextMapping.getMapsId().getValue());
		
		contextMapping.getMapsId().setValue("bar");
		assertEquals("bar", resourceMapping.getMapsId());
		assertEquals("bar", contextMapping.getMapsId().getValue());
		
		contextMapping.getMapsId().setValue(null);
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getMapsId().getValue());
	}
	
	public void testMorphMapping() throws Exception {
		createTestEntityWithDerivedId();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		
		XmlManyToOne resourceManyToOne = resourceEntity.getAttributes().getManyToOnes().get(0);
		resourceManyToOne.setId(Boolean.TRUE);
		resourceManyToOne.setMapsId("foo");
		assertEquals(Boolean.TRUE, resourceManyToOne.getId());
		assertEquals("foo", resourceManyToOne.getMapsId());
		assertTrue(((OrmManyToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		XmlOneToOne resourceOneToOne = resourceEntity.getAttributes().getOneToOnes().get(0);
		assertEquals(Boolean.TRUE, resourceOneToOne.getId());
		assertEquals("foo", resourceOneToOne.getMapsId());
		assertTrue(((OrmOneToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		resourceManyToOne = resourceEntity.getAttributes().getManyToOnes().get(0);
		assertEquals(Boolean.TRUE, resourceManyToOne.getId());
		assertEquals("foo", resourceManyToOne.getMapsId());
		assertTrue(((OrmManyToOneMapping2_0) contextAttribute.getMapping()).getDerivedId().getValue());
	}
}
