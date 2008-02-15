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

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.core.internal.context.base.IManyToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IManyToOneMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToManyMapping;
import org.eclipse.jpt.core.internal.context.base.IOneToOneMapping;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.context.base.IVersionMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlOneToManyMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.OneToMany;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToManyResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToManyResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, xmlOneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, oneToManyResource.getFetch());
	
		oneToManyResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, xmlOneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, oneToManyResource.getFetch());

		//set fetch to null in the resource model
		oneToManyResource.setFetch(null);
		assertNull(xmlOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToManyResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(xmlOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		xmlOneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, oneToManyResource.getFetch());
		assertEquals(FetchType.EAGER, xmlOneToManyMapping.getSpecifiedFetch());
	
		xmlOneToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, oneToManyResource.getFetch());
		assertEquals(FetchType.LAZY, xmlOneToManyMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		xmlOneToManyMapping.setSpecifiedFetch(null);
		assertNull(oneToManyResource.getFetch());
		assertNull(xmlOneToManyMapping.getSpecifiedFetch());
	}
	
	public void testUpdateMappedBy() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		OneToMany oneToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		
		assertTrue(xmlOneToManyMapping.isNoOrdering());

		xmlOneToManyMapping.setOrderBy("foo");
		assertFalse(xmlOneToManyMapping.isNoOrdering());
		
		xmlOneToManyMapping.setOrderBy(null);
		assertTrue(xmlOneToManyMapping.isNoOrdering());
	}
	
	public void testSetNoOrdering() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		XmlOneToManyMapping xmlOneToManyMapping = (XmlOneToManyMapping) xmlPersistentAttribute.getMapping();
		
		assertFalse(xmlOneToManyMapping.isCustomOrdering());

		xmlOneToManyMapping.setOrderBy("foo");
		assertTrue(xmlOneToManyMapping.isCustomOrdering());
		
		xmlOneToManyMapping.setOrderBy(null);
		assertFalse(xmlOneToManyMapping.isCustomOrdering());
	}
	
	public void testOneToManyMorphToIdMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IIdMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToVersionMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IVersionMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToTransientMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ITransientMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToEmbeddedMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IEmbeddedMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToEmbeddedIdMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.setMappedBy("mappedBy");
		oneToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IEmbeddedIdMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToOneToOneMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) xmlPersistentAttribute.getMapping();
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
		IJoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IOneToOneMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getMappedBy());
		assertTrue(((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToManyMorphToManyToManyMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) xmlPersistentAttribute.getMapping();
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
		IJoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IManyToManyMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((IManyToManyMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((IManyToManyMapping) xmlPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((IManyToManyMapping) xmlPersistentAttribute.getMapping()).getMappedBy());
		assertTrue(((IManyToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((IManyToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((IManyToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((IManyToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((IManyToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isRemove());
		IJoinTable joinTable = ((IManyToManyMapping) xmlPersistentAttribute.getMapping()).getJoinTable();
		assertEquals("MY_JOIN_TABLE", joinTable.getName());
		assertEquals("name", joinTable.joinColumns().next().getSpecifiedName());
		assertEquals("referenceName", joinTable.joinColumns().next().getSpecifiedReferencedColumnName());
		assertEquals("inverseName", joinTable.inverseJoinColumns().next().getSpecifiedName());
		assertEquals("inverseReferenceName", joinTable.inverseJoinColumns().next().getSpecifiedReferencedColumnName());
	}
	
	public void testOneToManyMorphToManyToOneMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) xmlPersistentAttribute.getMapping();
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
		IJoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IManyToOneMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertTrue(((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToManyMorphToBasicMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		IOneToManyMapping oneToManyMapping = (IOneToManyMapping) xmlPersistentAttribute.getMapping();
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
		IJoinColumn joinColumn = oneToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = oneToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IBasicMapping);
		assertEquals("oneToMany", xmlPersistentAttribute.getMapping().getName());
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
}