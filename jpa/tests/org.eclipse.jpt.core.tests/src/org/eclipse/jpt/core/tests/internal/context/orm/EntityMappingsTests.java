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

import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class EntityMappingsTests extends ContextModelTestCase
{
	public EntityMappingsTests(String name) {
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
		
	protected XmlPersistence xmlPersistence() {
		return persistenceResource().getPersistence();
	}
	
	protected EntityMappings entityMappings() {
		return persistenceUnit().mappingFileRefs().next().getOrmXml().getEntityMappings();
	}
	
	public void testGetVersion() throws Exception {
		assertEquals("1.0", entityMappings().getVersion());
	}
	
	public void testUpdateDescription() throws Exception {
		assertNull(entityMappings().getDescription());
		assertNull(ormResource().getEntityMappings().getDescription());
		
		//set description in the resource model, verify context model updated
		ormResource().getEntityMappings().setDescription("newDescription");
		assertEquals("newDescription", entityMappings().getDescription());
		assertEquals("newDescription", ormResource().getEntityMappings().getDescription());
	
		//set description to null in the resource model
		ormResource().getEntityMappings().setDescription(null);
		assertNull(entityMappings().getDescription());
		assertNull(ormResource().getEntityMappings().getDescription());
	}
	
	public void testModifyDescription() throws Exception {
		assertNull(entityMappings().getDescription());
		assertNull(ormResource().getEntityMappings().getDescription());
		
		//set description in the context model, verify resource model modified
		entityMappings().setDescription("newDescription");
		assertEquals("newDescription", entityMappings().getDescription());
		assertEquals("newDescription", ormResource().getEntityMappings().getDescription());
		//TODO should we verify the tag is correct in the orm.xml??
		
		//set description to null in the context model
		entityMappings().setDescription(null);
		assertNull(entityMappings().getDescription());
		assertNull(ormResource().getEntityMappings().getDescription());
	}
	
	public void testUpdatePackage() throws Exception {
		assertNull(entityMappings().getPackage());
		assertNull(ormResource().getEntityMappings().getPackage());
		
		//set package in the resource model, verify context model updated
		ormResource().getEntityMappings().setPackage("foo.model");
		assertEquals("foo.model", entityMappings().getPackage());
		assertEquals("foo.model", ormResource().getEntityMappings().getPackage());
		
		//set package to null in the resource model
		ormResource().getEntityMappings().setPackage(null);
		assertNull(entityMappings().getPackage());
		assertNull(ormResource().getEntityMappings().getPackage());
	}
	
	public void testModifyPackage() throws Exception {
		assertNull(entityMappings().getPackage());
		assertNull(ormResource().getEntityMappings().getPackage());
		
		//set package in the context model, verify resource model modified
		entityMappings().setPackage("foo.model");
		assertEquals("foo.model", entityMappings().getPackage());
		assertEquals("foo.model", ormResource().getEntityMappings().getPackage());

		//set package to null in the context model
		entityMappings().setPackage(null);
		assertNull(entityMappings().getPackage());
		assertNull(ormResource().getEntityMappings().getPackage());
	}

	public void testUpdateSpecifiedSchema() throws Exception {
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
		
		//set schema in the resource model, verify context model updated
		ormResource().getEntityMappings().setSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", entityMappings().getSpecifiedSchema());
		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getSchema());

		//set schema to null in the resource model
		ormResource().getEntityMappings().setSchema(null);
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
	}
	
	//TODO test schema
	//TODO test default schema
	public void testModifySpecifiedSchema() throws Exception {
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
		
		//set schema in the context model, verify resource model modified
		entityMappings().setSpecifiedSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", entityMappings().getSpecifiedSchema());
		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getSchema());

		//set schema to null in the context model
		entityMappings().setSpecifiedSchema(null);
		assertNull(entityMappings().getSpecifiedSchema());
		assertNull(ormResource().getEntityMappings().getSchema());
	}
	
	public void testUpdateSpecifiedCatalog() throws Exception {
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
		
		//set catalog in the resource model, verify context model updated
		ormResource().getEntityMappings().setCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", entityMappings().getSpecifiedCatalog());
		assertEquals("MY_CATALOG", ormResource().getEntityMappings().getCatalog());

		//set catalog to null in the resource model
		ormResource().getEntityMappings().setCatalog(null);
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
	}
	
	public void testModifySpecifiedCatalog() throws Exception {
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
		
		//set catalog in the context model, verify resource model modified
		entityMappings().setSpecifiedCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", entityMappings().getSpecifiedCatalog());
		assertEquals("MY_CATALOG", ormResource().getEntityMappings().getCatalog());
		
		//set catalog to null in the context model
		entityMappings().setSpecifiedCatalog(null);
		assertNull(entityMappings().getSpecifiedCatalog());
		assertNull(ormResource().getEntityMappings().getCatalog());
	}
	//TODO test catalog
	//TODO test default catalog

	public void testUpdateSpecifiedAccess() throws Exception {
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
		
		//set access in the resource model, verify context model updated
		ormResource().getEntityMappings().setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, entityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, ormResource().getEntityMappings().getAccess());
		
		//set access to null in the resource model
		ormResource().getEntityMappings().setAccess(null);
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
	}
	
	public void testModifySpecifiedAccess() throws Exception {
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
		
		//set access in the context model, verify resource model modified
		entityMappings().setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, entityMappings().getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, ormResource().getEntityMappings().getAccess());
		
		//set access to null in the context model
		entityMappings().setSpecifiedAccess(null);
		assertNull(entityMappings().getSpecifiedAccess());
		assertNull(ormResource().getEntityMappings().getAccess());
	}
}