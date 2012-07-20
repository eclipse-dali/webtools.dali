/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.orm.OrmEntity;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class OrmQueryHintTests extends ContextModelTestCase
{
	public OrmQueryHintTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	public void testUpdateName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmQueryHint ormQueryHint = ormEntity.getQueryContainer().addNamedQuery(0).addHint(0);
		
		XmlQueryHint queryHintResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0).getHints().get(0);
		
		assertNull(ormQueryHint.getName());
		assertNull(queryHintResource.getName());
				
		//set name in the resource model, verify context model updated
		queryHintResource.setName("newName");
		assertEquals("newName", ormQueryHint.getName());
		assertEquals("newName", queryHintResource.getName());
	
		//set name to null in the resource model
		queryHintResource.setName(null);
		assertNull(ormQueryHint.getName());
		assertNull(queryHintResource.getName());
	}
	
	public void testModifyName() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmQueryHint ormQueryHint = ormEntity.getQueryContainer().addNamedQuery(0).addHint(0);
		
		XmlQueryHint queryHintResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0).getHints().get(0);
		
		assertNull(ormQueryHint.getName());
		assertNull(queryHintResource.getName());
				
		//set name in the context model, verify resource model updated
		ormQueryHint.setName("newName");
		assertEquals("newName", ormQueryHint.getName());
		assertEquals("newName", queryHintResource.getName());
	
		//set name to null in the context model
		ormQueryHint.setName(null);
		assertNull(ormQueryHint.getName());
		assertNull(queryHintResource.getName());
	}
	
	public void testUpdateValue() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmQueryHint ormQueryHint = ormEntity.getQueryContainer().addNamedQuery(0).addHint(0);
		
		XmlQueryHint queryHintResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0).getHints().get(0);
		
		assertNull(ormQueryHint.getValue());
		assertNull(queryHintResource.getValue());
				
		//set name in the resource model, verify context model updated
		queryHintResource.setValue("newName");
		assertEquals("newName", ormQueryHint.getValue());
		assertEquals("newName", queryHintResource.getValue());
	
		//set name to null in the resource model
		queryHintResource.setValue(null);
		assertNull(ormQueryHint.getValue());
		assertNull(queryHintResource.getValue());
	}
	
	public void testModifyValue() throws Exception {
		OrmPersistentType ormPersistentType = getEntityMappings().addPersistentType(MappingKeys.ENTITY_TYPE_MAPPING_KEY, "model.Foo");
		OrmEntity ormEntity = (OrmEntity) ormPersistentType.getMapping();
		OrmQueryHint ormQueryHint = ormEntity.getQueryContainer().addNamedQuery(0).addHint(0);
		
		XmlQueryHint queryHintResource = getXmlEntityMappings().getEntities().get(0).getNamedQueries().get(0).getHints().get(0);
		
		assertNull(ormQueryHint.getValue());
		assertNull(queryHintResource.getValue());
				
		//set name in the context model, verify resource model updated
		ormQueryHint.setValue("newName");
		assertEquals("newName", ormQueryHint.getValue());
		assertEquals("newName", queryHintResource.getValue());
	
		//set name to null in the context model
		ormQueryHint.setValue(null);
		assertNull(ormQueryHint.getValue());
		assertNull(queryHintResource.getValue());
	}
}