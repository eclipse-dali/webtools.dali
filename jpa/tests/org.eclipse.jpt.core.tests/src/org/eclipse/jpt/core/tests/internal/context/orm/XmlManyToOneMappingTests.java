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
import org.eclipse.jpt.core.internal.context.orm.XmlManyToOneMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.ManyToOne;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlManyToOneMappingTests extends ContextModelTestCase
{
	public XmlManyToOneMappingTests(String name) {
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
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertEquals("manyToOneMapping", xmlManyToOneMapping.getName());
		assertEquals("manyToOneMapping", manyToOne.getName());
				
		//set name in the resource model, verify context model updated
		manyToOne.setName("newName");
		assertEquals("newName", xmlManyToOneMapping.getName());
		assertEquals("newName", manyToOne.getName());
	
		//set name to null in the resource model
		manyToOne.setName(null);
		assertNull(xmlManyToOneMapping.getName());
		assertNull(manyToOne.getName());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertEquals("manyToOneMapping", xmlManyToOneMapping.getName());
		assertEquals("manyToOneMapping", manyToOne.getName());
				
		//set name in the context model, verify resource model updated
		xmlManyToOneMapping.setName("newName");
		assertEquals("newName", xmlManyToOneMapping.getName());
		assertEquals("newName", manyToOne.getName());
	
		//set name to null in the context model
		xmlManyToOneMapping.setName(null);
		assertNull(xmlManyToOneMapping.getName());
		assertNull(manyToOne.getName());
	}
	
	public void testUpdateTargetEntity() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
				
		//set target entity in the resource model, verify context model updated
		manyToOne.setTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToOne.getTargetEntity());
	
		//set target entity to null in the resource model
		manyToOne.setTargetEntity(null);
		assertNull(xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
	}
	
	public void testModifyTargetEntity() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOne = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
				
		//set target entity in the context model, verify resource model updated
		xmlManyToOneMapping.setSpecifiedTargetEntity("newTargetEntity");
		assertEquals("newTargetEntity", xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertEquals("newTargetEntity", manyToOne.getTargetEntity());
	
		//set target entity to null in the context model
		xmlManyToOneMapping.setSpecifiedTargetEntity(null);
		assertNull(xmlManyToOneMapping.getSpecifiedTargetEntity());
		assertNull(manyToOne.getTargetEntity());
	}
	
	public void testUpdateSpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(xmlManyToOneMapping.getSpecifiedFetch());
		assertNull(manyToOneResource.getFetch());
				
		//set fetch in the resource model, verify context model updated
		manyToOneResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER);
		assertEquals(FetchType.EAGER, xmlManyToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, manyToOneResource.getFetch());
	
		manyToOneResource.setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY);
		assertEquals(FetchType.LAZY, xmlManyToOneMapping.getSpecifiedFetch());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, manyToOneResource.getFetch());

		//set fetch to null in the resource model
		manyToOneResource.setFetch(null);
		assertNull(xmlManyToOneMapping.getSpecifiedFetch());
		assertNull(manyToOneResource.getFetch());
	}
	
	public void testModifySpecifiedFetch() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		XmlPersistentAttribute xmlPersistentAttribute = xmlPersistentType.addSpecifiedPersistentAttribute(IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY, "manyToOneMapping");
		XmlManyToOneMapping xmlManyToOneMapping = (XmlManyToOneMapping) xmlPersistentAttribute.getMapping();
		ManyToOne manyToOneResource = ormResource().getEntityMappings().getEntities().get(0).getAttributes().getManyToOnes().get(0);
		
		assertNull(xmlManyToOneMapping.getSpecifiedFetch());
		assertNull(manyToOneResource.getFetch());
				
		//set fetch in the context model, verify resource model updated
		xmlManyToOneMapping.setSpecifiedFetch(FetchType.EAGER);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.EAGER, manyToOneResource.getFetch());
		assertEquals(FetchType.EAGER, xmlManyToOneMapping.getSpecifiedFetch());
	
		xmlManyToOneMapping.setSpecifiedFetch(FetchType.LAZY);
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.FetchType.LAZY, manyToOneResource.getFetch());
		assertEquals(FetchType.LAZY, xmlManyToOneMapping.getSpecifiedFetch());

		//set fetch to null in the context model
		xmlManyToOneMapping.setSpecifiedFetch(null);
		assertNull(manyToOneResource.getFetch());
		assertNull(xmlManyToOneMapping.getSpecifiedFetch());
	}

}