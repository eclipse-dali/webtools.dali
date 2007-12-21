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
import org.eclipse.jpt.core.internal.context.orm.XmlOneToOneMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.OneToOne;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlOneToOneMappingTests extends ContextModelTestCase
{
	public XmlOneToOneMappingTests(String name) {
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
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		XmlOneToOneMapping xmlOneToOneMapping = (XmlOneToOneMapping) xmlPersistentAttribute.getMapping();
		OneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertEquals("oneToOneMapping", xmlOneToOneMapping.getName());
		assertEquals("oneToOneMapping", oneToOne.getName());
				
		//set name in the resource model, verify context model updated
		oneToOne.setName("newName");
		assertEquals("newName", xmlOneToOneMapping.getName());
		assertEquals("newName", oneToOne.getName());
	
		//set name to null in the resource model
		oneToOne.setName(null);
		assertNull(xmlOneToOneMapping.getName());
		assertNull(oneToOne.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		XmlOneToOneMapping xmlOneToOneMapping = (XmlOneToOneMapping) xmlPersistentAttribute.getMapping();
		OneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertEquals("oneToOneMapping", xmlOneToOneMapping.getName());
		assertEquals("oneToOneMapping", oneToOne.getName());
				
		//set name in the context model, verify resource model updated
		xmlOneToOneMapping.setName("newName");
		assertEquals("newName", xmlOneToOneMapping.getName());
		assertEquals("newName", oneToOne.getName());
	
		//set name to null in the context model
		xmlOneToOneMapping.setName(null);
		assertNull(xmlOneToOneMapping.getName());
		assertNull(oneToOne.getName());
	}
	
	public void testUpdateTargetEntity() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		XmlOneToOneMapping xmlOneToOneMapping = (XmlOneToOneMapping) xmlPersistentAttribute.getMapping();
		OneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(xmlOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		oneToOne.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", xmlOneToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToOne.getTargetEntity());
	
		//set target entity to null in the resource model
		oneToOne.setTargetEntity(null);
		assertNull(xmlOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
	}
	
	public void testModifyTargetEntity() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		XmlOneToOneMapping xmlOneToOneMapping = (XmlOneToOneMapping) xmlPersistentAttribute.getMapping();
		OneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(xmlOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		xmlOneToOneMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", xmlOneToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", oneToOne.getTargetEntity());
	
		//set target entity to null in the context model
		xmlOneToOneMapping.setSpecifiedTargetEntity(null);
		assertNull(xmlOneToOneMapping.getSpecifiedTargetEntity());
		assertNull(oneToOne.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		XmlOneToOneMapping xmlOneToOneMapping = (XmlOneToOneMapping) xmlPersistentAttribute.getMapping();
		OneToOne oneToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(xmlOneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOneResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		oneToOneResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, xmlOneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, oneToOneResource.getFetch());
	
		oneToOneResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, xmlOneToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, oneToOneResource.getFetch());

		//set fetch to null in the resource model
		oneToOneResource.setFetch(null);
		assertNull(xmlOneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOneResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		XmlOneToOneMapping xmlOneToOneMapping = (XmlOneToOneMapping) xmlPersistentAttribute.getMapping();
		OneToOne oneToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(xmlOneToOneMapping.getSpecifiedFetch());
		assertNull(oneToOneResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		xmlOneToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, oneToOneResource.getFetch());
		assertEquals(FetchType.EAGER, xmlOneToOneMapping.getSpecifiedFetch());
	
		xmlOneToOneMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, oneToOneResource.getFetch());
		assertEquals(FetchType.LAZY, xmlOneToOneMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		xmlOneToOneMapping.setSpecifiedFetch(null);
		assertNull(oneToOneResource.getFetch());
		assertNull(xmlOneToOneMapping.getSpecifiedFetch());
	}

	public void testUpdateMappedBy() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		XmlOneToOneMapping xmlOneToOneMapping = (XmlOneToOneMapping) xmlPersistentAttribute.getMapping();
		OneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(xmlOneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
				
		//set mappedBy in the resource model, verify context model updated
		oneToOne.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", xmlOneToOneMapping.getMappedBy());
		assertEquals("newMappedBy", oneToOne.getMappedBy());
	
		//set mappedBy to null in the resource model
		oneToOne.setMappedBy(null);
		assertNull(xmlOneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
	}
	
	public void testModifyMappedBy() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		XmlOneToOneMapping xmlOneToOneMapping = (XmlOneToOneMapping) xmlPersistentAttribute.getMapping();
		OneToOne oneToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(xmlOneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
				
		//set mappedBy in the context model, verify resource model updated
		xmlOneToOneMapping.setMappedBy("newMappedBy");
		assertEquals("newMappedBy", xmlOneToOneMapping.getMappedBy());
		assertEquals("newMappedBy", oneToOne.getMappedBy());
	
		//set mappedBy to null in the context model
		xmlOneToOneMapping.setMappedBy(null);
		assertNull(xmlOneToOneMapping.getMappedBy());
		assertNull(oneToOne.getMappedBy());
	}
	
	
	public void testUpdateSpecifiedOptional() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		XmlOneToOneMapping xmlOneToOneMapping = (XmlOneToOneMapping) xmlPersistentAttribute.getMapping();
		OneToOne oneToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(xmlOneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOneResource.getOptional());
				
		//set optional in the resource model, verify context model updated
		oneToOneResource.setOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, xmlOneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.TRUE, oneToOneResource.getOptional());
	
		oneToOneResource.setOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, xmlOneToOneMapping.getSpecifiedOptional());
		assertEquals(Boolean.FALSE, oneToOneResource.getOptional());

		//set optional to null in the resource model
		oneToOneResource.setOptional(null);
		assertNull(xmlOneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOneResource.getOptional());
	}
	
	public void testModifySpecifiedOptional() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY, "oneToOneMapping");
		XmlOneToOneMapping xmlOneToOneMapping = (XmlOneToOneMapping) xmlPersistentAttribute.getMapping();
		OneToOne oneToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getOneToOnes().get(0);
		
		assertNull(xmlOneToOneMapping.getSpecifiedOptional());
		assertNull(oneToOneResource.getOptional());
				
		//set optional in the context model, verify resource model updated
		xmlOneToOneMapping.setSpecifiedOptional(Boolean.TRUE);
		assertEquals(Boolean.TRUE, oneToOneResource.getOptional());
		assertEquals(Boolean.TRUE, xmlOneToOneMapping.getSpecifiedOptional());
	
		xmlOneToOneMapping.setSpecifiedOptional(Boolean.FALSE);
		assertEquals(Boolean.FALSE, oneToOneResource.getOptional());
		assertEquals(Boolean.FALSE, xmlOneToOneMapping.getSpecifiedOptional());

		//set optional to null in the context model
		xmlOneToOneMapping.setSpecifiedOptional(null);
		assertNull(oneToOneResource.getOptional());
		assertNull(xmlOneToOneMapping.getSpecifiedOptional());
	}
}