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
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedIdentity2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmManyToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneMapping2_0;
import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;
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
	
	
	private void createTestEntityWithIdDerivedIdentity() throws Exception {
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
	
	private void createTestEntityWithMapsIdDerivedIdentity() throws Exception {
		createTestType(new DefaultAnnotationWriter() {
			@Override
			public Iterator<String> imports() {
				return new ArrayIterator<String>(JPA.ENTITY, JPA.MANY_TO_ONE, JPA2_0.MAPS_ID);
			}
			@Override
			public void appendTypeAnnotationTo(StringBuilder sb) {
				sb.append("@Entity").append(CR);
			}
			
			@Override
			public void appendIdFieldAnnotationTo(StringBuilder sb) {
				sb.append(CR);
				sb.append("    @ManyToOne @MapsId").append(CR);				
				sb.append("    private " + TYPE_NAME + " manyToOne;").append(CR);
				sb.append(CR);				
			}
		});
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		for (OrmPersistentAttribute each : CollectionTools.iterable(ormPersistentType.attributes())) {
			each.makeSpecified();
		}
	}
	
	public void testUpdateId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		OrmManyToOneMapping2_0 contextMapping = (OrmManyToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlDerivedId_2_0 resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		resourceMapping.setId(Boolean.TRUE);
		assertEquals(Boolean.TRUE, resourceMapping.getId());
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		resourceMapping.setId(Boolean.FALSE);
		assertEquals(Boolean.FALSE, resourceMapping.getId());
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		resourceMapping.setId(null);
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
	}
	
	public void testSetDerivedId() throws Exception {
		createTestEntityWithIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		OrmManyToOneMapping2_0 contextMapping = (OrmManyToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlDerivedId_2_0 resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		
		contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().setValue(true);
		assertEquals(Boolean.TRUE, resourceMapping.getId());
		assertTrue(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
	
		contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().setValue(false);
		assertNull(resourceMapping.getId());
		assertFalse(contextMapping.getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
	}
	
	public void testUpdateMapsId() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		OrmManyToOneMapping2_0 contextMapping = (OrmManyToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlMapsId_2_0 resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		resourceMapping.setMapsId("foo");
		assertEquals("foo", resourceMapping.getMapsId());
		assertEquals("foo", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		resourceMapping.setMapsId("bar");
		assertEquals("bar", resourceMapping.getMapsId());
		assertEquals("bar", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		resourceMapping.setMapsId("");
		assertEquals("", resourceMapping.getMapsId());
		assertEquals("", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		resourceMapping.setMapsId(null);
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
	}
	
	public void testSetMapsId() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		OrmManyToOneMapping2_0 contextMapping = (OrmManyToOneMapping2_0) contextAttribute.getMapping();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlMapsId_2_0 resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedValue("foo");
		assertEquals("foo", resourceMapping.getMapsId());
		assertEquals("foo", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedValue("bar");
		assertEquals("bar", resourceMapping.getMapsId());
		assertEquals("bar", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedValue("");
		assertEquals("", resourceMapping.getMapsId());
		assertEquals("", contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().setSpecifiedValue(null);
		assertNull(resourceMapping.getMapsId());
		assertNull(contextMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
	}
	
	public void testUpdatePredominantDerivedIdentityStrategy() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		OrmManyToOneMapping2_0 contextMapping = (OrmManyToOneMapping2_0) contextAttribute.getMapping();
		OrmDerivedIdentity2_0 derivedIdentity = contextMapping.getDerivedIdentity();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlManyToOne resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertNull(resourceMapping.getMapsId());
		assertNull(resourceMapping.getId());
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceMapping.setMapsId("foo");
		assertNotNull(resourceMapping.getMapsId());
		assertNull(resourceMapping.getId());
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceMapping.setId(Boolean.TRUE);
		assertNotNull(resourceMapping.getMapsId());
		assertNotNull(resourceMapping.getId());
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceMapping.setMapsId(null);
		assertNull(resourceMapping.getMapsId());
		assertNotNull(resourceMapping.getId());
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		resourceMapping.setId(null);
		assertNull(resourceMapping.getMapsId());
		assertNull(resourceMapping.getId());
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesNullDerivedIdentityStrategy());
	}
	
	public void testSetPredominantDerivedIdentityStrategy() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		OrmManyToOneMapping2_0 contextMapping = (OrmManyToOneMapping2_0) contextAttribute.getMapping();
		OrmDerivedIdentity2_0 derivedIdentity = contextMapping.getDerivedIdentity();
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		XmlManyToOne resourceMapping = resourceEntity.getAttributes().getManyToOnes().get(0);
		
		assertNull(resourceMapping.getMapsId());
		assertNull(resourceMapping.getId());
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setMapsIdDerivedIdentityStrategy();
		assertNotNull(resourceMapping.getMapsId());
		assertNull(resourceMapping.getId());
		assertTrue(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setIdDerivedIdentityStrategy();
		assertNull(resourceMapping.getMapsId());
		assertNotNull(resourceMapping.getId());
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesNullDerivedIdentityStrategy());
		
		derivedIdentity.setNullDerivedIdentityStrategy();
		assertNull(resourceMapping.getMapsId());
		assertNull(resourceMapping.getId());
		assertFalse(derivedIdentity.usesMapsIdDerivedIdentityStrategy());
		assertFalse(derivedIdentity.usesIdDerivedIdentityStrategy());
		assertTrue(derivedIdentity.usesNullDerivedIdentityStrategy());
	}
	
	public void testMorphMapping() throws Exception {
		createTestEntityWithMapsIdDerivedIdentity();
		OrmPersistentType contextType = getEntityMappings().getPersistentType(FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute contextAttribute = contextType.getAttributeNamed("manyToOne");
		XmlEntity resourceEntity = (XmlEntity) contextType.getMapping().getResourceTypeMapping();
		
		XmlManyToOne resourceManyToOne = resourceEntity.getAttributes().getManyToOnes().get(0);
		resourceManyToOne.setMapsId("foo");
		assertNull(resourceManyToOne.getId());
		assertFalse(((OrmManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertEquals("foo", resourceManyToOne.getMapsId());
		assertEquals("foo", ((OrmManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		XmlOneToOne resourceOneToOne = resourceEntity.getAttributes().getOneToOnes().get(0);
		assertNull(resourceOneToOne.getId());
		assertFalse(((OrmOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertEquals("foo", resourceOneToOne.getMapsId());
		assertEquals("foo", ((OrmOneToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
		
		contextAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		resourceManyToOne = resourceEntity.getAttributes().getManyToOnes().get(0);
		assertNull(resourceManyToOne.getId());
		assertFalse(((OrmManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getIdDerivedIdentityStrategy().getValue());
		assertEquals("foo", resourceManyToOne.getMapsId());
		assertEquals("foo", ((OrmManyToOneMapping2_0) contextAttribute.getMapping()).
				getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getSpecifiedValue());
	}
}
