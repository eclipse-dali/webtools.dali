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
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmQueryHint;
import org.eclipse.jpt.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class XmlQueryHintTests extends ContextModelTestCase
{
	public XmlQueryHintTests(String name) {
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
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmQueryHint xmlQueryHint = xmlEntity.addNamedQuery(0).addHint(0);
		
		XmlQueryHint queryHintResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0).getHints().get(0);
		
		assertNull(xmlQueryHint.getName());
		assertNull(queryHintResource.getName());
				
		//set name in the resource model, verify context model updated
		queryHintResource.setName("newName");
		assertEquals("newName", xmlQueryHint.getName());
		assertEquals("newName", queryHintResource.getName());
	
		//set name to null in the resource model
		queryHintResource.setName(null);
		assertNull(xmlQueryHint.getName());
		assertNull(queryHintResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmQueryHint xmlQueryHint = xmlEntity.addNamedQuery(0).addHint(0);
		
		XmlQueryHint queryHintResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0).getHints().get(0);
		
		assertNull(xmlQueryHint.getName());
		assertNull(queryHintResource.getName());
				
		//set name in the context model, verify resource model updated
		xmlQueryHint.setName("newName");
		assertEquals("newName", xmlQueryHint.getName());
		assertEquals("newName", queryHintResource.getName());
	
		//set name to null in the context model
		xmlQueryHint.setName(null);
		assertNull(xmlQueryHint.getName());
		assertNull(queryHintResource.getName());
	}
	
	public void testUpdateValue() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmQueryHint xmlQueryHint = xmlEntity.addNamedQuery(0).addHint(0);
		
		XmlQueryHint queryHintResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0).getHints().get(0);
		
		assertNull(xmlQueryHint.getValue());
		assertNull(queryHintResource.getValue());
				
		//set name in the resource model, verify context model updated
		queryHintResource.setValue("newName");
		assertEquals("newName", xmlQueryHint.getValue());
		assertEquals("newName", queryHintResource.getValue());
	
		//set name to null in the resource model
		queryHintResource.setValue(null);
		assertNull(xmlQueryHint.getValue());
		assertNull(queryHintResource.getValue());
	}
	
	public void testModifyValue() throws Exception {
		OrmPersistentType ormPersistentType = entityMappings().addOrmPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		GenericOrmEntity xmlEntity = (GenericOrmEntity) ormPersistentType.getMapping();
		GenericOrmQueryHint xmlQueryHint = xmlEntity.addNamedQuery(0).addHint(0);
		
		XmlQueryHint queryHintResource = ormResource().getEntityMappings().getEntities().get(0).getNamedQueries().get(0).getHints().get(0);
		
		assertNull(xmlQueryHint.getValue());
		assertNull(queryHintResource.getValue());
				
		//set name in the context model, verify resource model updated
		xmlQueryHint.setValue("newName");
		assertEquals("newName", xmlQueryHint.getValue());
		assertEquals("newName", queryHintResource.getValue());
	
		//set name to null in the context model
		xmlQueryHint.setValue(null);
		assertNull(xmlQueryHint.getValue());
		assertNull(queryHintResource.getValue());
	}
}