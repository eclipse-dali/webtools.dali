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
import org.eclipse.jpt.core.internal.context.orm.XmlManyToManyMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.ManyToMany;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlManyToManyMappingTests extends ContextModelTestCase
{
	public XmlManyToManyMappingTests(String name) {
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
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertEquals("manyToManyMapping", xmlManyToManyMapping.getName());
		assertEquals("manyToManyMapping", manyToMany.getName());
				
		//set name in the resource model, verify context model updated
		manyToMany.setName("newName");
		assertEquals("newName", xmlManyToManyMapping.getName());
		assertEquals("newName", manyToMany.getName());
	
		//set name to null in the resource model
		manyToMany.setName(null);
		assertNull(xmlManyToManyMapping.getName());
		assertNull(manyToMany.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertEquals("manyToManyMapping", xmlManyToManyMapping.getName());
		assertEquals("manyToManyMapping", manyToMany.getName());
				
		//set name in the context model, verify resource model updated
		xmlManyToManyMapping.setName("newName");
		assertEquals("newName", xmlManyToManyMapping.getName());
		assertEquals("newName", manyToMany.getName());
	
		//set name to null in the context model
		xmlManyToManyMapping.setName(null);
		assertNull(xmlManyToManyMapping.getName());
		assertNull(manyToMany.getName());
	}
	
	public void testUpdateTargetEntity() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(xmlManyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		manyToMany.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", xmlManyToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToMany.getTargetEntity());
	
		//set target entity to null in the resource model
		manyToMany.setTargetEntity(null);
		assertNull(xmlManyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
	}
	
	public void testModifyTargetEntity() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(xmlManyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		xmlManyToManyMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", xmlManyToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToMany.getTargetEntity());
	
		//set target entity to null in the context model
		xmlManyToManyMapping.setSpecifiedTargetEntity(null);
		assertNull(xmlManyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToManyResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(xmlManyToManyMapping.getSpecifiedFetch());
		assertNull(manyToManyResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		manyToManyResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, xmlManyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, manyToManyResource.getFetch());
	
		manyToManyResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, xmlManyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, manyToManyResource.getFetch());

		//set fetch to null in the resource model
		manyToManyResource.setFetch(null);
		assertNull(xmlManyToManyMapping.getSpecifiedFetch());
		assertNull(manyToManyResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToManyResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(xmlManyToManyMapping.getSpecifiedFetch());
		assertNull(manyToManyResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		xmlManyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, manyToManyResource.getFetch());
		assertEquals(FetchType.EAGER, xmlManyToManyMapping.getSpecifiedFetch());
	
		xmlManyToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, manyToManyResource.getFetch());
		assertEquals(FetchType.LAZY, xmlManyToManyMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		xmlManyToManyMapping.setSpecifiedFetch(null);
		assertNull(manyToManyResource.getFetch());
		assertNull(xmlManyToManyMapping.getSpecifiedFetch());
	}
		
	public void testUpdateMappedBy() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(xmlManyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		manyToMany.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", xmlManyToManyMapping.getMappedBy());
		assertEquals("newMappedBy", manyToMany.getMappedBy());
	
		//setmappedBy to null in the resource model
		manyToMany.setMappedBy(null);
		assertNull(xmlManyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(xmlManyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		xmlManyToManyMapping.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", xmlManyToManyMapping.getMappedBy());
		assertEquals("newMappedBy", manyToMany.getMappedBy());
	
		//set mappedBy to null in the context model
		xmlManyToManyMapping.setMappedBy(null);
		assertNull(xmlManyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
	}
	
	public void testUpdateMapKey() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(xmlManyToManyMapping.getMapKey());
		assertNull(manyToMany.getMapKey());
		
		//set mapKey in the resource model, verify context model does not change
		manyToMany.setMapKey(OrmFactory.eINSTANCE.createMapKeyImpl());
		assertNull(xmlManyToManyMapping.getMapKey());
		assertNotNull(manyToMany.getMapKey());
				
		//set mapKey name in the resource model, verify context model updated
		manyToMany.getMapKey().setName("myMapKey");
		assertEquals("myMapKey", xmlManyToManyMapping.getMapKey());
		assertEquals("myMapKey", manyToMany.getMapKey().getName());
		
		//set mapKey name to null in the resource model
		manyToMany.getMapKey().setName(null);
		assertNull(xmlManyToManyMapping.getMapKey());
		assertNull(manyToMany.getMapKey().getName());
		
		manyToMany.getMapKey().setName("myMapKey");
		manyToMany.setMapKey(null);
		assertNull(xmlManyToManyMapping.getMapKey());
		assertNull(manyToMany.getMapKey());
	}
	
	public void testModifyMapKey() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(xmlManyToManyMapping.getMapKey());
		assertNull(manyToMany.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		xmlManyToManyMapping.setMapKey("myMapKey");
		assertEquals("myMapKey", xmlManyToManyMapping.getMapKey());
		assertEquals("myMapKey", manyToMany.getMapKey().getName());
	
		//set mapKey to null in the context model
		xmlManyToManyMapping.setMapKey(null);
		assertNull(xmlManyToManyMapping.getMapKey());
		assertNull(manyToMany.getMapKey());
	}
	
	public void testUpdateOrderBy() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(xmlManyToManyMapping.getOrderBy());
		assertNull(manyToMany.getOrderBy());
				
		//set orderBy in the resource model, verify context model updated
		manyToMany.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", xmlManyToManyMapping.getOrderBy());
		assertEquals("newOrderBy", manyToMany.getOrderBy());
	
		//set orderBy to null in the resource model
		manyToMany.setOrderBy(null);
		assertNull(xmlManyToManyMapping.getOrderBy());
		assertNull(manyToMany.getOrderBy());
	}
	
	public void testModifyOrderBy() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		ManyToMany manyToMany = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(xmlManyToManyMapping.getOrderBy());
		assertNull(manyToMany.getOrderBy());
				
		//set mappedBy in the context model, verify resource model updated
		xmlManyToManyMapping.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", xmlManyToManyMapping.getOrderBy());
		assertEquals("newOrderBy", manyToMany.getOrderBy());
	
		//set mappedBy to null in the context model
		xmlManyToManyMapping.setOrderBy(null);
		assertNull(xmlManyToManyMapping.getOrderBy());
		assertNull(manyToMany.getOrderBy());
	}
	
	public void testIsNoOrdering() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		
		assertTrue(xmlManyToManyMapping.isNoOrdering());

		xmlManyToManyMapping.setOrderBy("foo");
		assertFalse(xmlManyToManyMapping.isNoOrdering());
		
		xmlManyToManyMapping.setOrderBy(null);
		assertTrue(xmlManyToManyMapping.isNoOrdering());
	}
	
	public void testSetNoOrdering() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		
		assertTrue(xmlManyToManyMapping.isNoOrdering());

		xmlManyToManyMapping.setOrderBy("foo");
		assertFalse(xmlManyToManyMapping.isNoOrdering());
		
		xmlManyToManyMapping.setNoOrdering(true);
		assertTrue(xmlManyToManyMapping.isNoOrdering());
		assertNull(xmlManyToManyMapping.getOrderBy());
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
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		XmlManyToManyMapping xmlManyToManyMapping = (XmlManyToManyMapping) xmlPersistentAttribute.getMapping();
		
		assertFalse(xmlManyToManyMapping.isCustomOrdering());

		xmlManyToManyMapping.setOrderBy("foo");
		assertTrue(xmlManyToManyMapping.isCustomOrdering());
		
		xmlManyToManyMapping.setOrderBy(null);
		assertFalse(xmlManyToManyMapping.isCustomOrdering());
	}
	
	public void testManyToManyMorphToIdMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IIdMapping);
		assertEquals("manyToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testManyToManyMorphToVersionMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IVersionMapping);
		assertEquals("manyToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testManyToManyMorphToTransientMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof ITransientMapping);
		assertEquals("manyToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testManyToManyMorphToEmbeddedMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IEmbeddedMapping);
		assertEquals("manyToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testManyToManyMorphToEmbeddedIdMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IEmbeddedIdMapping);
		assertEquals("manyToMany", xmlPersistentAttribute.getMapping().getName());
	}
	
	public void testManyToManyMorphToOneToOneMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getCascade().setAll(true);
		manyToManyMapping.getCascade().setMerge(true);
		manyToManyMapping.getCascade().setPersist(true);
		manyToManyMapping.getCascade().setRefresh(true);
		manyToManyMapping.getCascade().setRemove(true);
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IOneToOneMapping);
		assertEquals("manyToMany", xmlPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getMappedBy());
		assertTrue(((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((IOneToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testManyToManyMorphToOneToManyMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getCascade().setAll(true);
		manyToManyMapping.getCascade().setMerge(true);
		manyToManyMapping.getCascade().setPersist(true);
		manyToManyMapping.getCascade().setRefresh(true);
		manyToManyMapping.getCascade().setRemove(true);
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IOneToManyMapping);
		assertEquals("manyToMany", xmlPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((IOneToManyMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((IOneToManyMapping) xmlPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((IOneToManyMapping) xmlPersistentAttribute.getMapping()).getMappedBy());
		assertTrue(((IOneToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((IOneToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((IOneToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((IOneToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((IOneToManyMapping) xmlPersistentAttribute.getMapping()).getCascade().isRemove());
		IJoinTable joinTable = ((IOneToManyMapping) xmlPersistentAttribute.getMapping()).getJoinTable();
		assertEquals("MY_JOIN_TABLE", joinTable.getName());
		assertEquals("name", joinTable.joinColumns().next().getSpecifiedName());
		assertEquals("referenceName", joinTable.joinColumns().next().getSpecifiedReferencedColumnName());
		assertEquals("inverseName", joinTable.inverseJoinColumns().next().getSpecifiedName());
		assertEquals("inverseReferenceName", joinTable.inverseJoinColumns().next().getSpecifiedReferencedColumnName());
	}
	
	public void testManyToManyMorphToManyToOneMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getCascade().setAll(true);
		manyToManyMapping.getCascade().setMerge(true);
		manyToManyMapping.getCascade().setPersist(true);
		manyToManyMapping.getCascade().setRefresh(true);
		manyToManyMapping.getCascade().setRemove(true);
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IManyToOneMapping);
		assertEquals("manyToMany", xmlPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertTrue(((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((IManyToOneMapping) xmlPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testManyToManyMorphToBasicMapping() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		IManyToManyMapping manyToManyMapping = (IManyToManyMapping) xmlPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getCascade().setAll(true);
		manyToManyMapping.getCascade().setMerge(true);
		manyToManyMapping.getCascade().setPersist(true);
		manyToManyMapping.getCascade().setRefresh(true);
		manyToManyMapping.getCascade().setRemove(true);
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		IJoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		IJoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		xmlPersistentAttribute.setSpecifiedMappingKey(IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, xmlPersistentType.specifiedAttributesSize());
		assertEquals(xmlPersistentAttribute, xmlPersistentType.specifiedAttributes().next());
		assertTrue(xmlPersistentAttribute.getMapping() instanceof IBasicMapping);
		assertEquals("manyToMany", xmlPersistentAttribute.getMapping().getName());
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) xmlPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
}