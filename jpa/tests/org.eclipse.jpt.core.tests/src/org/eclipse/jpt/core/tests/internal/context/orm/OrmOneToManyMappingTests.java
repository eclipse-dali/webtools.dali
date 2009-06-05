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
import org.eclipse.jpt.core.context.orm.OrmMappedByJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmOneToManyMappingTests extends ContextModelTestCase
{
	public OrmOneToManyMappingTests(String name) {
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertEquals("oneToManyMapping", ormOneToManyMapping.getName());
		assertEquals("oneToManyMapping", oneToMany.getName());
				
		//set name in the resource model, verify context model updated
		oneToMany.setName("newName");
		assertEquals("newName", ormOneToManyMapping.getName());
		assertEquals("newName", oneToMany.getName());
	
		//set name to null in the resource model
		oneToMany.setName(null);
		assertNull(ormOneToManyMapping.getName());
		assertNull(oneToMany.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertEquals("oneToManyMapping", ormOneToManyMapping.getName());
		assertEquals("oneToManyMapping", oneToMany.getName());
				
		//set name in the context model, verify resource model updated
		ormOneToManyMapping.setName("newName");
		assertEquals("newName", ormOneToManyMapping.getName());
		assertEquals("newName", oneToMany.getName());
	
		//set name to null in the context model
		ormOneToManyMapping.setName(null);
		assertNull(ormOneToManyMapping.getName());
		assertNull(oneToMany.getName());
	}
	
	public void testUpdateTargetEntity() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		oneToMany.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormOneToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToMany.getTargetEntity());
	
		//set target entity to null in the resource model
		oneToMany.setTargetEntity(null);
		assertNull(ormOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
	}
	
	public void testModifyTargetEntity() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		ormOneToManyMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", ormOneToManyMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToMany.getTargetEntity());
	
		//set target entity to null in the context model
		ormOneToManyMapping.setSpecifiedTargetEntity(null);
		assertNull(ormOneToManyMapping.getSpecifiedTargetEntity());
		assertNull(oneToMany.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToManyResource.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, ormOneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, oneToManyResource.getFetch());
	
		oneToManyResource.setFetch(org.eclipse.jpt.core.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, ormOneToManyMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, oneToManyResource.getFetch());

		//set fetch to null in the resource model
		oneToManyResource.setFetch(null);
		assertNull(ormOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToManyResource = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getSpecifiedFetch());
		assertNull(oneToManyResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		ormOneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.EAGER, oneToManyResource.getFetch());
		assertEquals(FetchType.EAGER, ormOneToManyMapping.getSpecifiedFetch());
	
		ormOneToManyMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.resource.orm.FetchType.LAZY, oneToManyResource.getFetch());
		assertEquals(FetchType.LAZY, ormOneToManyMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		ormOneToManyMapping.setSpecifiedFetch(null);
		assertNull(oneToManyResource.getFetch());
		assertNull(ormOneToManyMapping.getSpecifiedFetch());
	}
	
	public void testUpdateMappedBy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		OrmMappedByJoiningStrategy strategy = ormOneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToMany.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		oneToMany.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", strategy.getMappedByAttribute());
		assertEquals("newMappedBy", oneToMany.getMappedBy());
	
		//setmappedBy to null in the resource model
		oneToMany.setMappedBy(null);
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToMany.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		OrmMappedByJoiningStrategy strategy = ormOneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToMany.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		strategy.setMappedByAttribute("newMappedBy");
		assertEquals("newMappedBy", strategy.getMappedByAttribute());
		assertEquals("newMappedBy", oneToMany.getMappedBy());
	
		//set mappedBy to null in the context model
		strategy.setMappedByAttribute(null);
		assertNull(strategy.getMappedByAttribute());
		assertNull(oneToMany.getMappedBy());
	}
	
	
	public void testUpdateMapKey() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getMapKey());
		assertNull(oneToMany.getMapKey());
		
		//set mapKey in the resource model, verify context model does not change
		oneToMany.setMapKey(OrmFactory.eINSTANCE.createMapKey());
		assertNull(ormOneToManyMapping.getMapKey());
		assertNotNull(oneToMany.getMapKey());
				
		//set mapKey name in the resource model, verify context model updated
		oneToMany.getMapKey().setName("myMapKey");
		assertEquals("myMapKey", ormOneToManyMapping.getMapKey());
		assertEquals("myMapKey", oneToMany.getMapKey().getName());
		
		//set mapKey name to null in the resource model
		oneToMany.getMapKey().setName(null);
		assertNull(ormOneToManyMapping.getMapKey());
		assertNull(oneToMany.getMapKey().getName());
		
		oneToMany.getMapKey().setName("myMapKey");
		oneToMany.setMapKey(null);
		assertNull(ormOneToManyMapping.getMapKey());
		assertNull(oneToMany.getMapKey());
	}
	
	public void testModifyMapKey() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getMapKey());
		assertNull(oneToMany.getMapKey());
					
		//set mapKey  in the context model, verify resource model updated
		ormOneToManyMapping.setMapKey("myMapKey");
		assertEquals("myMapKey", ormOneToManyMapping.getMapKey());
		assertEquals("myMapKey", oneToMany.getMapKey().getName());
	
		//set mapKey to null in the context model
		ormOneToManyMapping.setMapKey(null);
		assertNull(ormOneToManyMapping.getMapKey());
		assertNull(oneToMany.getMapKey());
	}

	public void testUpdateOrderBy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getOrderBy());
		assertNull(oneToMany.getOrderBy());
				
		//set orderBy in the resource model, verify context model updated
		oneToMany.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", ormOneToManyMapping.getOrderBy());
		assertEquals("newOrderBy", oneToMany.getOrderBy());
	
		//set orderBy to null in the resource model
		oneToMany.setOrderBy(null);
		assertNull(ormOneToManyMapping.getOrderBy());
		assertNull(oneToMany.getOrderBy());
	}
	
	public void testModifyOrderBy() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		XmlOneToMany oneToMany = getXmlEntityMappings().getEntities().get(0).getAttributes().getOneToManys().get(0);
		
		assertNull(ormOneToManyMapping.getOrderBy());
		assertNull(oneToMany.getOrderBy());
				
		//set mappedBy in the context model, verify resource model updated
		ormOneToManyMapping.setOrderBy("newOrderBy");
		assertEquals("newOrderBy", ormOneToManyMapping.getOrderBy());
		assertEquals("newOrderBy", oneToMany.getOrderBy());
	
		//set mappedBy to null in the context model
		ormOneToManyMapping.setOrderBy(null);
		assertNull(ormOneToManyMapping.getOrderBy());
		assertNull(oneToMany.getOrderBy());
	}
	
	public void testIsNoOrdering() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		
		assertTrue(ormOneToManyMapping.isNoOrdering());

		ormOneToManyMapping.setOrderBy("foo");
		assertFalse(ormOneToManyMapping.isNoOrdering());
		
		ormOneToManyMapping.setOrderBy(null);
		assertTrue(ormOneToManyMapping.isNoOrdering());
	}
	
	public void testSetNoOrdering() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		
		assertTrue(ormOneToManyMapping.isNoOrdering());

		ormOneToManyMapping.setOrderBy("foo");
		assertFalse(ormOneToManyMapping.isNoOrdering());
		
		ormOneToManyMapping.setNoOrdering(true);
		assertTrue(ormOneToManyMapping.isNoOrdering());
		assertNull(ormOneToManyMapping.getOrderBy());
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
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToManyMapping");
		OrmOneToManyMapping ormOneToManyMapping = (OrmOneToManyMapping) ormPersistentAttribute.getMapping();
		
		assertFalse(ormOneToManyMapping.isCustomOrdering());

		ormOneToManyMapping.setOrderBy("foo");
		assertTrue(ormOneToManyMapping.isCustomOrdering());
		
		ormOneToManyMapping.setOrderBy(null);
		assertFalse(ormOneToManyMapping.isCustomOrdering());
	}
	
	public void testOneToManyMorphToIdMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("mappedBy");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof IdMapping);
		assertEquals("oneToMany", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToVersionMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof VersionMapping);
		assertEquals("oneToMany", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToTransientMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("mappedBy");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof TransientMapping);
		assertEquals("oneToMany", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToEmbeddedMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedMapping);
		assertEquals("oneToMany", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToEmbeddedIdMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("mappedBy");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof EmbeddedIdMapping);
		assertEquals("oneToMany", ormPersistentAttribute.getMapping().getName());
	}
	
	public void testOneToManyMorphToOneToOneMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("mappedBy");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof OneToOneMapping);
		assertEquals("oneToMany", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((OneToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((OneToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertEquals("mappedBy", ((OneToOneMapping) ormPersistentAttribute.getMapping()).getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((OneToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToManyMorphToManyToManyMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		oneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().setSpecifiedName("MY_JOIN_TABLE");
		JoinColumn joinColumn = oneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().addSpecifiedJoinColumn(0);
		joinColumn.setSpecifiedName("name");
		joinColumn.setSpecifiedReferencedColumnName("referenceName");
		JoinColumn inverseJoinColumn = oneToManyMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable().addSpecifiedInverseJoinColumn(0);
		inverseJoinColumn.setSpecifiedName("inverseName");
		inverseJoinColumn.setSpecifiedReferencedColumnName("inverseReferenceName");
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_MANY_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToManyMapping);
		assertEquals("oneToMany", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((ManyToManyMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
		JoinTable joinTable = ((ManyToManyMapping) ormPersistentAttribute.getMapping()).getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable();
		assertEquals("MY_JOIN_TABLE", joinTable.getName());
		assertEquals("name", joinTable.joinColumns().next().getSpecifiedName());
		assertEquals("referenceName", joinTable.joinColumns().next().getSpecifiedReferencedColumnName());
		assertEquals("inverseName", joinTable.inverseJoinColumns().next().getSpecifiedName());
		assertEquals("inverseReferenceName", joinTable.inverseJoinColumns().next().getSpecifiedReferencedColumnName());
	}
	
	public void testOneToManyMorphToManyToOneMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("mappedBy");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof ManyToOneMapping);
		assertEquals("oneToMany", ormPersistentAttribute.getMapping().getName());
		assertEquals(FetchType.EAGER, ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
		assertEquals("TargetEntity", ((ManyToOneMapping) ormPersistentAttribute.getMapping()).getSpecifiedTargetEntity());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isAll());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isMerge());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isPersist());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRefresh());
		assertTrue(((ManyToOneMapping) ormPersistentAttribute.getMapping()).getCascade().isRemove());
	}
	
	public void testOneToManyMorphToBasicMapping() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, FULLY_QUALIFIED_TYPE_NAME);
		OrmPersistentAttribute ormPersistentAttribute = ormPersistentType.addSpecifiedPersistentAttribute(MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY, "oneToMany");
		
		OneToManyMapping oneToManyMapping = (OneToManyMapping) ormPersistentAttribute.getMapping();
		assertFalse(oneToManyMapping.isDefault());
		oneToManyMapping.setSpecifiedFetch(FetchType.EAGER);
		oneToManyMapping.setSpecifiedTargetEntity("TargetEntity");
		oneToManyMapping.setOrderBy("customOrder");
		oneToManyMapping.setMapKey("mapKey");
		oneToManyMapping.getRelationshipReference().getMappedByJoiningStrategy().setMappedByAttribute("mappedBy");
		oneToManyMapping.getCascade().setAll(true);
		oneToManyMapping.getCascade().setMerge(true);
		oneToManyMapping.getCascade().setPersist(true);
		oneToManyMapping.getCascade().setRefresh(true);
		oneToManyMapping.getCascade().setRemove(true);
		assertFalse(oneToManyMapping.isDefault());	
		
		ormPersistentAttribute.setSpecifiedMappingKey(MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY);
		assertEquals(1, ormPersistentType.specifiedAttributesSize());
		assertEquals(ormPersistentAttribute, ormPersistentType.specifiedAttributes().next());
		assertTrue(ormPersistentAttribute.getMapping() instanceof BasicMapping);
		assertEquals("oneToMany", ormPersistentAttribute.getMapping().getName());
//TODO	assertEquals(FetchType.EAGER, ((IBasicMapping) ormPersistentAttribute.getMapping()).getSpecifiedFetch());
	}
}