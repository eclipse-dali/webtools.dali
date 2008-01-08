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
		manyToMany.setMapKey(OrmFactory.eINSTANCE.createMapKey());
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
		
		xmlManyToManyMapping.setNoOrdering();
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
}