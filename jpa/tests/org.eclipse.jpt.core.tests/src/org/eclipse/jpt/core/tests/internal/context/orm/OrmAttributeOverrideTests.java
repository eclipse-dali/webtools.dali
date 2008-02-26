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
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlEntity;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class OrmAttributeOverrideTests extends ContextModelTestCase
{
	public OrmAttributeOverrideTests(String name) {
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverride ormAttributeOverride = xmlEntity.addSpecifiedAttributeOverride(0);
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		XmlAttributeOverride attributeOverrideResource = entityResource.getAttributeOverrides().get(0);
		
		assertNull(ormAttributeOverride.getName());
		assertNull(attributeOverrideResource.getName());
		assertTrue(xmlEntity.attributeOverrides().hasNext());
		assertFalse(entityResource.getAttributeOverrides().isEmpty());
		
		//set name in the resource model, verify context model updated
		attributeOverrideResource.setName("FOO");
		ormResource().save(null);
		assertEquals("FOO", ormAttributeOverride.getName());
		assertEquals("FOO", attributeOverrideResource.getName());
	
		//set name to null in the resource model
		attributeOverrideResource.setName(null);
		ormResource().save(null);
		assertNull(ormAttributeOverride.getName());
		assertNull(attributeOverrideResource.getName());
		
		attributeOverrideResource.setName("FOO");
		assertEquals("FOO", ormAttributeOverride.getName());
		assertEquals("FOO", attributeOverrideResource.getName());

		entityResource.getAttributeOverrides().remove(0);
		ormResource().save(null);
		assertFalse(xmlEntity.attributeOverrides().hasNext());
		assertTrue(entityResource.getAttributeOverrides().isEmpty());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		OrmAttributeOverride ormAttributeOverride = xmlEntity.addSpecifiedAttributeOverride(0);
		
		XmlEntity entityResource = ormResource().getEntityMappings().getEntities().get(0);
		XmlAttributeOverride attributeOverrideResource = entityResource.getAttributeOverrides().get(0);

		assertNull(ormAttributeOverride.getName());
		assertNull(attributeOverrideResource.getName());
		
		//set name in the context model, verify resource model modified
		ormAttributeOverride.setName("foo");
		assertEquals("foo", ormAttributeOverride.getName());
		assertEquals("foo", attributeOverrideResource.getName());
		
		//set name to null in the context model
		ormAttributeOverride.setName(null);
		assertNull(ormAttributeOverride.getName());
		assertNull(entityResource.getAttributeOverrides().get(0).getName());
	}

}