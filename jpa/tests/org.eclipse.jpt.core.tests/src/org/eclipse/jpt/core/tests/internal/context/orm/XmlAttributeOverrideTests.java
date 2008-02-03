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
import org.eclipse.jpt.core.internal.context.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.internal.context.orm.XmlEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.resource.orm.AttributeOverride;
import org.eclipse.jpt.core.internal.resource.orm.Entity;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlAttributeOverrideTests extends ContextModelTestCase
{
	public XmlAttributeOverrideTests(String name) {
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
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlAttributeOverride xmlAttributeOverride = xmlEntity.addSpecifiedAttributeOverride(0);
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		AttributeOverride attributeOverrideResource = entityResource.getAttributeOverrides().get(0);
		
		assertNull(xmlAttributeOverride.getName());
		assertNull(attributeOverrideResource.getName());
		assertTrue(xmlEntity.attributeOverrides().hasNext());
		assertFalse(entityResource.getAttributeOverrides().isEmpty());
		
		//set name in the resource model, verify context model updated
		attributeOverrideResource.setName("FOO");
		ormResource().save(null);
		assertEquals("FOO", xmlAttributeOverride.getName());
		assertEquals("FOO", attributeOverrideResource.getName());
	
		//set name to null in the resource model
		attributeOverrideResource.setName(null);
		ormResource().save(null);
		assertNull(xmlAttributeOverride.getName());
		assertNull(attributeOverrideResource.getName());
		
		attributeOverrideResource.setName("FOO");
		assertEquals("FOO", xmlAttributeOverride.getName());
		assertEquals("FOO", attributeOverrideResource.getName());

		entityResource.getAttributeOverrides().remove(0);
		ormResource().save(null);
		assertFalse(xmlEntity.attributeOverrides().hasNext());
		assertTrue(entityResource.getAttributeOverrides().isEmpty());
	}
	
	public void testModifyName() throws Exception {
		XmlPersistentType xmlPersistentType = entityMappings().addXmlPersistentType(IMappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		XmlEntity xmlEntity = (XmlEntity) xmlPersistentType.getMapping();
		XmlAttributeOverride xmlAttributeOverride = xmlEntity.addSpecifiedAttributeOverride(0);
		
		Entity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		AttributeOverride attributeOverrideResource = entityResource.getAttributeOverrides().get(0);

		assertNull(xmlAttributeOverride.getName());
		assertNull(attributeOverrideResource.getName());
		
		//set name in the context model, verify resource model modified
		xmlAttributeOverride.setName("foo");
		assertEquals("foo", xmlAttributeOverride.getName());
		assertEquals("foo", attributeOverrideResource.getName());
		
		//set name to null in the context model
		xmlAttributeOverride.setName(null);
		assertNull(xmlAttributeOverride.getName());
		assertNull(entityResource.getAttributeOverrides().get(0).getName());
	}

}