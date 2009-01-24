/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.core.context.EmbeddedMapping;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.ManyToManyMapping;
import org.eclipse.jpt.core.context.ManyToOneMapping;
import org.eclipse.jpt.core.context.OneToManyMapping;
import org.eclipse.jpt.core.context.OneToOneMapping;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class OrmManyToManyMappingTests extends ContextModelTestCase
{
	public OrmManyToManyMappingTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertEquals("manyToManyMapping", ormManyToManyMapping.getName());
		assertEquals("manyToManyMapping", manyToMany.getName());
				
		//set name in the resource model, verify context model updated
		manyToMany.setName("newName");
		assertEquals("newName", ormManyToManyMapping.getName());
		assertEquals("newName", manyToMany.getName());
	
		//set name to null in the resource model
		manyToMany.setName(null);
		assertNull(ormManyToManyMapping.getName());
		assertNull(manyToMany.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertEquals("manyToManyMapping", ormManyToManyMapping.getName());
		assertEquals("manyToManyMapping", manyToMany.getName());
				
		//set name in the context model, verify resource model updated
		ormManyToManyMapping.setName("newName");
		assertEquals("newName", ormManyToManyMapping.getName());
		assertEquals("newName", manyToMany.getName());
	
		//set name to null in the context model
		ormManyToManyMapping.setName(null);
		assertNull(ormManyToManyMapping.getName());
		assertNull(manyToMany.getName());
	}
	
	public void testUpdateTargetEntity() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		manyToMany.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormManyToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToMany.getTargetEntity());
	
		//set target entity to null in the resource model
		manyToMany.setTargetEntity(null);
		assertNull(ormManyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
	}
	
	public void testModifyTargetEntity() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		ormManyToManyMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormManyToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToMany.getTargetEntity());
	
		//set target entity to null in the context model
		ormManyToManyMapping.setSpecifiedTargetEntity(null);
		assertNull(ormManyToManyMapping.getSpecifiedTargetEntity());
		assertNull(manyToMany.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToManyResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getSpecifiedFetch());
		assertNull(manyToManyResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		manyToManyResource.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, ormManyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, manyToManyResource.getFetch());
	
		manyToManyResource.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, ormManyToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, manyToManyResource.getFetch());

		//set fetch to null in the resource model
		manyToManyResource.setFetch(null);
		assertNull(ormManyToManyMapping.getSpecifiedFetch());
		assertNull(manyToManyResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToManyResource = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getSpecifiedFetch());
		assertNull(manyToManyResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		ormManyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, manyToManyResource.getFetch());
		assertEquals(FetchType.EAGER, ormManyToManyMapping.getSpecifiedFetch());
	
		ormManyToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, manyToManyResource.getFetch());
		assertEquals(FetchType.LAZY, ormManyToManyMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		ormManyToManyMapping.setSpecifiedFetch(null);
		assertNull(manyToManyResource.getFetch());
		assertNull(ormManyToManyMapping.getSpecifiedFetch());
	}
		
	public void testUpdateMappedBy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		manyToMany.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", ormManyToManyMapping.getMappedBy());
		assertEquals("newMappedBy", manyToMany.getMappedBy());
	
		//setmappedBy to null in the resource model
		manyToMany.setMappedBy(null);
		assertNull(ormManyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		ormManyToManyMapping.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", ormManyToManyMapping.getMappedBy());
		assertEquals("newMappedBy", manyToMany.getMappedBy());
	
		//set mappedBy to null in the context model
		ormManyToManyMapping.setMappedBy(null);
		assertNull(ormManyToManyMapping.getMappedBy());
		assertNull(manyToMany.getMappedBy());
	}
	
	public void testUpdateMapKey() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getMapKey());
		assertNull(manyToMany.getMapKey());
		
		//set mapKey in the resource model, verify context model does not change
		manyToMany.setMapKey(OrmFactory.eINSTANCE.createMapKeyImpl());
		assertNull(ormManyToManyMapping.getMapKey());
		assertNotNull(manyToMany.getMapKey());
				
		//set mapKey name in the resource model, verify context model updated
		manyToMany.getMapKey().setName("myMapKey");
		assertEquals("myMapKey", ormManyToManyMapping.getMapKey());
		assertEquals("myMapKey", manyToMany.getMapKey().getName());
		
		//set mapKey name to null in the resource model
		manyToMany.getMapKey().setName(null);
		assertNull(ormManyToManyMapping.getMapKey());
		assertNull(manyToMany.getMapKey().getName());
		
		manyToMany.getMapKey().setName("myMapKey");
		manyToMany.setMapKey(null);
		assertNull(ormManyToManyMapping.getMapKey());
		assertNull(manyToMany.getMapKey());
	}
	
	public void testModifyMapKey() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getMapKey());
		assertNull(manyToMany.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		ormManyToManyMapping.setMapKey("myMapKey");
		assertEquals("myMapKey", ormManyToManyMapping.getMapKey());
		assertEquals("myMapKey", manyToMany.getMapKey().getName());
	
		//set mapKey to null in the context model
		ormManyToManyMapping.setMapKey(null);
		assertNull(ormManyToManyMapping.getMapKey());
		assertNull(manyToMany.getMapKey());
	}
	
	public void testUpdateOrderBy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getOrderBy());
		assertNull(manyToMany.getOrderBy());
				
		//set orderBy in the resource model, verify context model updated
		manyToMany.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", ormManyToManyMapping.getOrderBy());
		assertEquals("newOrderBy", manyToMany.getOrderBy());
	
		//set orderBy to null in the resource model
		manyToMany.setOrderBy(null);
		assertNull(ormManyToManyMapping.getOrderBy());
		assertNull(manyToMany.getOrderBy());
	}
	
	public void testModifyOrderBy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		XmlManyToMany manyToMany = getOrmXmlResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToManys().get(0);
		
		assertNull(ormManyToManyMapping.getOrderBy());
		assertNull(manyToMany.getOrderBy());
				
		//set mappedBy in the context model, verify resource model updated
		ormManyToManyMapping.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", ormManyToManyMapping.getOrderBy());
		assertEquals("newOrderBy", manyToMany.getOrderBy());
	
		//set mappedBy to null in the context model
		ormManyToManyMapping.setOrderBy(null);
		assertNull(ormManyToManyMapping.getOrderBy());
		assertNull(manyToMany.getOrderBy());
	}
	
	public void testIsNoOrdering() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		
		assertTrue(ormManyToManyMapping.isNoOrdering());

		ormManyToManyMapping.setOrderBy("foo");
		assertFalse(ormManyToManyMapping.isNoOrdering());
		
		ormManyToManyMapping.setOrderBy(null);
		assertTrue(ormManyToManyMapping.isNoOrdering());
	}
	
	public void testSetNoOrdering() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		
		assertTrue(ormManyToManyMapping.isNoOrdering());

		ormManyToManyMapping.setOrderBy("foo");
		assertFalse(ormManyToManyMapping.isNoOrdering());
		
		ormManyToManyMapping.setNoOrdering(true);
		assertTrue(ormManyToManyMapping.isNoOrdering());
		assertNull(ormManyToManyMapping.getOrderBy());
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
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToManyMapping");
		OrmManyToManyMapping ormManyToManyMapping = (OrmManyToManyMapping) ormPersistentAttribute.getMapping();
		
		assertFalse(ormManyToManyMapping.isCustomOrdering());

		ormManyToManyMapping.setOrderBy("foo");
		assertTrue(ormManyToManyMapping.isCustomOrdering());
		
		ormManyToManyMapping.setOrderBy(null);
		assertFalse(ormManyToManyMapping.isCustomOrdering());
	}
	
	public void testManyToManyMorphToIdMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("manyToMany", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testManyToManyMorphToVersionMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("manyToMany", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testManyToManyMorphToTransientMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("manyToMany", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testManyToManyMorphToEmbeddedMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("manyToMany", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testManyToManyMorphToEmbeddedIdMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(manyToManyMapping.isDefault());
		manyToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		manyToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		manyToManyMapping.setOrderBy("customOrder");
		manyToManyMapping.setMapKey("mapKey");
		manyToManyMapping.setMappedBy("mappedBy");
		manyToManyMapping.getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("manyToMany", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testManyToManyMorphToOneToOneMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) ormPersistentAttribute.getMapping();
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
		JoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("manyToMany", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((OneToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((OneToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((OneToOneMapping) ormPersistentAttribute.getMapping()).getMappedBy());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testManyToManyMorphToOneToManyMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) ormPersistentAttribute.getMapping();
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
		JoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToManyMapping);
		assertEquals("manyToMany", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((OneToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((OneToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((OneToManyMapping) ormPersistentAttribute.getMapping()).getMappedBy());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((OneToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
		JoinTable joinTable = ((OneToManyMapping) ormPersistentAttribute.getMapping()).getJoinTable();
		assertEquals("MY_JOIN_TABLE", joinTable.getName());
		assertEquals("name", joinTable.joinColumns().next().getSpecifiedName());
		assertEquals("referenceName", joinTable.joinColumns().next().getSpecifiedReferencedColumnName());
		assertEquals("inverseName", joinTable.inverseJoinColumns().next().getSpecifiedName());
		assertEquals("inverseReferenceName", joinTable.inverseJoinColumns().next().getSpecifiedReferencedColumnName());
	}
	
	public void testManyToManyMorphToManyToOneMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) ormPersistentAttribute.getMapping();
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
		JoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("manyToMany", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testManyToManyMorphToBasicMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY, "manyToMany");
		
		ManyToManyMapping manyToManyMapping = (ManyToManyMapping) ormPersistentAttribute.getMapping();
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
		JoinColumn joinColumn = manyToManyMapping.getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = manyToManyMapping.getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(manyToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("manyToMany", ormPersistentAttribute.getMapping().getName());
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
}