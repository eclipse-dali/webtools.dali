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
package org.eclipse.jpt.core.tests.internal.context.orm;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmOneToManyMapping;
import org.eclipse.jpt.core.internal.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlOneToManyMappingTests extends ContextModelTestCase
{
	public XmlOneToManyMappingTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertEquals("oneToManyMapping", xmlOneToManyMapping.getName());
		assertEquals("oneToManyMapping", oneToMany.getName());
				
		//set name in the resource model, verify context model updated
		oneToMany.setName("newName");
		assertEquals("newName", xmlOneToManyMapping.getName());
		assertEquals("newName", oneToMany.getName());
	
		//set name to null in the resource model
		oneToMany.setName(null);
		assertNull(xmlOneToManyMapping.getName());
		assertNull(oneToMany.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertEquals("oneToManyMapping", xmlOneToManyMapping.getName());
		assertEquals("oneToManyMapping", oneToMany.getName());
				
		//set name in the context model, verify resource model updated
		xmlOneToManyMapping.setName("newName");
		assertEquals("newName", xmlOneToManyMapping.getName());
		assertEquals("newName", oneToMany.getName());
	
		//set name to null in the context model
		xmlOneToManyMapping.setName(null);
		assertNull(xmlOneToManyMapping.getName());
		assertNull(oneToMany.getName());
	}
	
	public void testUpdateTargetEntity() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		ormResource().save(null);
		
		assertNull(xmlOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		oneToMany.setTargetEntity("newTargetEntity");
		ormResource().save(null);
		assertEquals("newTargetEntity", xmlOneToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToMany.getTargetEntity());
	
		//set target entity to null in the resource model
		oneToMany.setTargetEntity(null);
		ormResource().save(null);
		assertNull(xmlOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
	}
	
	public void testModifyTargetEntity() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		xmlOneToManyMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", xmlOneToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToMany.getTargetEntity());
	
		//set target entity to null in the context model
		xmlOneToManyMapping.setSpecifiedTargetEntity(null);
		assertNull(xmlOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToManyResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToManyResource.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, xmlOneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, oneToManyResource.getFetch());
	
		oneToManyResource.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, xmlOneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, oneToManyResource.getFetch());

		//set fetch to null in the resource model
		oneToManyResource.setFetch(null);
		assertNull(xmlOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToManyResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		xmlOneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, oneToManyResource.getFetch());
		assertEquals(FetchType.EAGER, xmlOneToManyMapping.getSpecifiedFetch());
	
		xmlOneToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, oneToManyResource.getFetch());
		assertEquals(FetchType.LAZY, xmlOneToManyMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		xmlOneToManyMapping.setSpecifiedFetch(null);
		assertNull(oneToManyResource.getFetch());
		assertNull(xmlOneToManyMapping.getSpecifiedFetch());
	}
	
	public void testUpdateMappedBy() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getMappedBy());
		assertNull(oneToMany.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		oneToMany.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", xmlOneToManyMapping.getMappedBy());
		assertEquals("newMappedBy", oneToMany.getMappedBy());
	
		//setmappedBy to null in the resource model
		oneToMany.setMappedBy(null);
		assertNull(xmlOneToManyMapping.getMappedBy());
		assertNull(oneToMany.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getMappedBy());
		assertNull(oneToMany.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		xmlOneToManyMapping.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", xmlOneToManyMapping.getMappedBy());
		assertEquals("newMappedBy", oneToMany.getMappedBy());
	
		//set mappedBy to null in the context model
		xmlOneToManyMapping.setMappedBy(null);
		assertNull(xmlOneToManyMapping.getMappedBy());
		assertNull(oneToMany.getMappedBy());
	}
	
	
	public void testUpdateMapKey() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getMapKey());
		assertNull(oneToMany.getMapKey());
		
		//set mapKey in the resource model, verify context model does not change
		oneToMany.setMapKey(OrmFactory.eINSTANCE.createMapKeyImpl());
		assertNull(xmlOneToManyMapping.getMapKey());
		assertNotNull(oneToMany.getMapKey());
				
		//set mapKey name in the resource model, verify context model updated
		oneToMany.getMapKey().setName("myMapKey");
		assertEquals("myMapKey", xmlOneToManyMapping.getMapKey());
		assertEquals("myMapKey", oneToMany.getMapKey().getName());
		
		//set mapKey name to null in the resource model
		oneToMany.getMapKey().setName(null);
		assertNull(xmlOneToManyMapping.getMapKey());
		assertNull(oneToMany.getMapKey().getName());
		
		oneToMany.getMapKey().setName("myMapKey");
		oneToMany.setMapKey(null);
		assertNull(xmlOneToManyMapping.getMapKey());
		assertNull(oneToMany.getMapKey());
	}
	
	public void testModifyMapKey() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getMapKey());
		assertNull(oneToMany.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		xmlOneToManyMapping.setMapKey("myMapKey");
		assertEquals("myMapKey", xmlOneToManyMapping.getMapKey());
		assertEquals("myMapKey", oneToMany.getMapKey().getName());
	
		//set mapKey to null in the context model
		xmlOneToManyMapping.setMapKey(null);
		assertNull(xmlOneToManyMapping.getMapKey());
		assertNull(oneToMany.getMapKey());
	}

	public void testUpdateOrderBy() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getOrderBy());
		assertNull(oneToMany.getOrderBy());
				
		//set orderBy in the resource model, verify context model updated
		oneToMany.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", xmlOneToManyMapping.getOrderBy());
		assertEquals("newOrderBy", oneToMany.getOrderBy());
	
		//set orderBy to null in the resource model
		oneToMany.setOrderBy(null);
		assertNull(xmlOneToManyMapping.getOrderBy());
		assertNull(oneToMany.getOrderBy());
	}
	
	public void testModifyOrderBy() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getOrderBy());
		assertNull(oneToMany.getOrderBy());
				
		//set mappedBy in the context model, verify resource model updated
		xmlOneToManyMapping.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", xmlOneToManyMapping.getOrderBy());
		assertEquals("newOrderBy", oneToMany.getOrderBy());
	
		//set mappedBy to null in the context model
		xmlOneToManyMapping.setOrderBy(null);
		assertNull(xmlOneToManyMapping.getOrderBy());
		assertNull(oneToMany.getOrderBy());
	}
	
	public void testIsNoOrdering() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		
		assertTrue(xmlOneToManyMapping.isNoOrdering());

		xmlOneToManyMapping.setOrderBy("foo");
		assertFalse(xmlOneToManyMapping.isNoOrdering());
		
		xmlOneToManyMapping.setOrderBy(null);
		assertTrue(xmlOneToManyMapping.isNoOrdering());
	}
	
	public void testSetNoOrdering() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		
		assertTrue(xmlOneToManyMapping.isNoOrdering());

		xmlOneToManyMapping.setOrderBy("foo");
		assertFalse(xmlOneToManyMapping.isNoOrdering());
		
		xmlOneToManyMapping.setNoOrdering(true);
		assertTrue(xmlOneToManyMapping.isNoOrdering());
		assertNull(xmlOneToManyMapping.getOrderBy());
	}
//TODO
//	public boolean isOrderByPk() {
//		return "".equals(getOrderBy());
//	}
//
//	public void setOrderByPk() {
//		setOrderBy("");
//	}

	public void testIsCustomOrdering() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		GenericOrmOneToManyMapping xmlOneToManyMapping = (GenericOrmOneToManyMapping) xmlPersistentAttribute.getMapping();
		
		assertFalse(xmlOneToManyMapping.isCustomOrdering());

		xmlOneToManyMapping.setOrderBy("foo");
		assertTrue(xmlOneToManyMapping.isCustomOrdering());
		
		xmlOneToManyMapping.setOrderBy(null);
		assertFalse(xmlOneToManyMapping.isCustomOrdering());
	}
	
	public void testOneToManyMorphToIdMapping() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToVersionMapping() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToTransientMapping() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToEmbeddedMapping() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToEmbeddedIdMapping() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToOneToOneMapping() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((OneToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((OneToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((OneToOneMapping) xmlPersistentAttribute.getMapping()).getMappedBy());
		assertTrue(((OneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((OneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((OneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((OneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((OneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToManyMorphToManyToManyMapping() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((ManyToManyMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((ManyToManyMapping) xmlPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((ManyToManyMapping) xmlPersistentAttribute.getMapping()).getMappedBy());
		assertTrue(((ManyToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((ManyToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((ManyToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((ManyToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((ManyToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isRemove());
		JoinTable joinTable = ((ManyToManyMapping) xmlPersistentAttribute.getMapping()).getJoinTable();
		assertEquals("MY_JOIN_TABLE", joinTable.getName());
		assertEquals("name", joinTable.joinColumns().next().getSpecifiedName());
		assertEquals("referenceName", joinTable.joinColumns().next().getSpecifiedReferencedColumnName());
		assertEquals("inverseName", joinTable.inverseJoinColumns().next().getSpecifiedName());
		assertEquals("inverseReferenceName", joinTable.inverseJoinColumns().next().getSpecifiedReferencedColumnName());
	}
	
	public void testOneToManyMorphToManyToOneMapping() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((ManyToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((ManyToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertTrue(((ManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((ManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((ManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((ManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((ManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToManyMorphToBasicMapping() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute xmlPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
}