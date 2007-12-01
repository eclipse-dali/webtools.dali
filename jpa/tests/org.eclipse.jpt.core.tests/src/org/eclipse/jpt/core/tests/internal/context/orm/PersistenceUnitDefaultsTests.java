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
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class PersistenceUnitDefaultsTests extends ContextModelTestCase
{
	public PersistenceUnitDefaultsTests(String name) {
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
	
	protected PersistenceUnitDefaults persistenceUnitDefaults() {
		return entityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
	
//	public void testUpdateSchema() throws Exception {
//		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
//		assertNull(persistenceUnitDefaults.getSchema());
//		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
//		
//		//set schema in the resource model, verify context model updated
//		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
//		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
//		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("MY_SCHEMA");
//		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
//		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
//	
//		//set schema to null in the resource model
//		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema(null);
//		assertNull(persistenceUnitDefaults.getSchema());
//		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
//	}
//	
//	public void testModifySchema() throws Exception {		
//		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
//		assertNull(persistenceUnitDefaults.getSchema());
//		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
//		
//		//set schema in the context model, verify resource model modified
//		persistenceUnitDefaults.setSchema("MY_SCHEMA");
//		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
//		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
//		
//		//set schema to null in the context model
//		persistenceUnitDefaults.setSchema(null);
//		assertNull(persistenceUnitDefaults.getSchema());
//		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
//	}
//	
//	public void testModifySchema2() throws Exception {
//		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
//		assertNull(persistenceUnitDefaults.getSchema());
//		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
//		
//		//set schema in the context model, verify resource model modified
//		persistenceUnitDefaults.setSchema("MY_SCHEMA");
//		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
//		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
//		
//		//set another element on the persistence-unit-defaults element so it doesn't get removed
//		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("MY_CATALOG");
//		//set schema to null in the context model
//		persistenceUnitDefaults.setSchema(null);
//		assertNull(persistenceUnitDefaults.getSchema());
//		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
//	}
}