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
}