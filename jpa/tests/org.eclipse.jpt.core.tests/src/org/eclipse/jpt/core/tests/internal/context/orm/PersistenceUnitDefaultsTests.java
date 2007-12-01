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
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata;
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
	
	public void testIsAllFeaturesUnset() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		PersistenceUnitMetadata persistenceUnitMetadata = OrmFactory.eINSTANCE.createPersistenceUnitMetadata();
		ormResource().getEntityMappings().setPersistenceUnitMetadata(persistenceUnitMetadata);
		assertTrue(persistenceUnitMetadata.isAllFeaturesUnset());
		
		org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults persistenceUnitDefaultsResource = OrmFactory.eINSTANCE.createPersistenceUnitDefaults();
		persistenceUnitMetadata.setPersistenceUnitDefaults(persistenceUnitDefaultsResource);
		assertTrue(persistenceUnitDefaultsResource.isAllFeaturesUnset());
		
		persistenceUnitDefaultsResource.setCascadePersist(true);
		assertFalse(persistenceUnitDefaultsResource.isAllFeaturesUnset());
		
		persistenceUnitDefaultsResource.setCascadePersist(false);
		assertTrue(persistenceUnitDefaultsResource.isAllFeaturesUnset());
		
		persistenceUnitDefaultsResource.setSchema("asdf");
		assertFalse(persistenceUnitDefaultsResource.isAllFeaturesUnset());
		
		persistenceUnitDefaultsResource.setSchema(null);
		assertTrue(persistenceUnitDefaultsResource.isAllFeaturesUnset());
		
		persistenceUnitDefaultsResource.setCatalog("asdf");
		assertFalse(persistenceUnitDefaultsResource.isAllFeaturesUnset());
		
		persistenceUnitDefaultsResource.setCatalog(null);
		assertTrue(persistenceUnitDefaultsResource.isAllFeaturesUnset());
		
		
		persistenceUnitDefaultsResource.setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY);
		assertFalse(persistenceUnitDefaultsResource.isAllFeaturesUnset());
		
		persistenceUnitDefaultsResource.setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD);
		assertFalse(persistenceUnitDefaultsResource.isAllFeaturesUnset());
		
		persistenceUnitDefaultsResource.setAccess(null);
		assertTrue(persistenceUnitDefaultsResource.isAllFeaturesUnset());
	}

	public void testUpdateSchema() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set schema in the resource model, verify context model updated
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
	
		//set schema to null in the resource model
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema(null);
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
	}
	
	public void testModifySchema() throws Exception {		
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set schema in the context model, verify resource model modified
		persistenceUnitDefaults.setSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
		
		//set schema to null in the context model
		persistenceUnitDefaults.setSchema(null);
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifySchema2() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set schema in the context model, verify resource model modified
		persistenceUnitDefaults.setSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
		
		//set another element on the persistence-unit-defaults element so it doesn't get removed
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("MY_CATALOG");
		//set schema to null in the context model
		persistenceUnitDefaults.setSchema(null);
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
	}
	
	public void testModifySchema3() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		//set another element on the persistence-unit-metadata element so only persistence-unit-defaults element gets removed
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		
		//set schema in the context model, verify resource model modified
		persistenceUnitDefaults.setSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
		assertEquals("MY_SCHEMA", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
		
		//set schema to null in the context model
		persistenceUnitDefaults.setSchema(null);
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults());
	}
	
	public void testUpdateCatalog() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set catalog in the resource model, verify context model updated
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", persistenceUnitDefaults.getCatalog());
		assertEquals("MY_CATALOG", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
	
		//set catalog to null in the resource model
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog(null);
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
	}
	
	public void testModifyCatalog() throws Exception {		
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set catalog in the context model, verify resource model modified
		persistenceUnitDefaults.setCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", persistenceUnitDefaults.getCatalog());
		assertEquals("MY_CATALOG", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
		
		//set catalog to null in the context model
		persistenceUnitDefaults.setCatalog(null);
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifyCatalog2() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set catalog in the context model, verify resource model modified
		persistenceUnitDefaults.setCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", persistenceUnitDefaults.getCatalog());
		assertEquals("MY_CATALOG", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
		
		//set another element on the persistence-unit-defaults element so it doesn't get removed
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("MY_SCHEMA");
		//set catalog to null in the context model
		persistenceUnitDefaults.setCatalog(null);
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
	}
	
	public void testModifyCatalog3() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		//set another element on the persistence-unit-metadata element so only persistence-unit-defaults element gets removed
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		
		//set catalog in the context model, verify resource model modified
		persistenceUnitDefaults.setCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", persistenceUnitDefaults.getCatalog());
		assertEquals("MY_CATALOG", ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
		
		//set catalog to null in the context model
		persistenceUnitDefaults.setCatalog(null);
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults());
	}
	
	
	public void testUpdateCascadePersist() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set cascadePersist in the resource model, verify context model updated
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCascadePersist(true);
		assertTrue(persistenceUnitDefaults.isCascadePersist());
		assertTrue(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
	
		//set cascadePersist to null in the resource model, persistence-unit-defaults tag not removed
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCascadePersist(false);
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertFalse(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
	}
	
	public void testModifyCascadePersist() throws Exception {		
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set cascadePersist in the context model, verify resource model modified
		persistenceUnitDefaults.setCascadePersist(true);
		assertTrue(persistenceUnitDefaults.isCascadePersist());
		assertTrue(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
		
		//set cascadePersist to null in the context model
		persistenceUnitDefaults.setCascadePersist(false);
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		
	}
	
	public void testModifyCascadePersist2() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set cascadePersist in the context model, verify resource model modified
		persistenceUnitDefaults.setCascadePersist(true);
		ormResource().save(null);
		assertTrue(persistenceUnitDefaults.isCascadePersist());
		assertTrue(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
		
		//set another element on the persistence-unit-defaults element so it doesn't get removed
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("MY_CATALOG");
		ormResource().save(null);
		//set cascadePersist to null in the context model
		persistenceUnitDefaults.setCascadePersist(false);
		ormResource().save(null);
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertFalse(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
	}
	
	public void testModifyCascadePersist3() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		//set another element on the persistence-unit-metadata element so only persistence-unit-defaults element gets removed
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		
		//set cascadePersist in the context model, verify resource model modified
		persistenceUnitDefaults.setCascadePersist(true);
		assertTrue(persistenceUnitDefaults.isCascadePersist());
		assertTrue(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
		
		//set cascadePersist to null in the context model
		persistenceUnitDefaults.setCascadePersist(false);
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults());
	}
	
	
	public void testUpdateAccess() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set access in the resource model, verify context model updated
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
		ormResource().save(null);
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD);
		ormResource().save(null);
		assertEquals(AccessType.FIELD, persistenceUnitDefaults.getAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD, ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
	
		//set access to null in the resource model
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		ormResource().save(null);
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
	}
	
	public void testModifyAccess() throws Exception {		
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setAccess(AccessType.PROPERTY);
		ormResource().save(null);
		assertEquals(AccessType.PROPERTY, persistenceUnitDefaults.getAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.PROPERTY, ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set access to null in the context model
		persistenceUnitDefaults.setAccess(null);
		ormResource().save(null);
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifyAccess2() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setAccess(AccessType.FIELD);
		ormResource().save(null);
		assertEquals(AccessType.FIELD, persistenceUnitDefaults.getAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD, ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set another element on the persistence-unit-defaults element so it doesn't get removed
		ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCascadePersist(true);
		ormResource().save(null);
		//set access to null in the context model
		persistenceUnitDefaults.setAccess(null);
		ormResource().save(null);
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
	}
	
	public void testModifyAccess3() throws Exception {
		PersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		//set another element on the persistence-unit-metadata element so only persistence-unit-defaults element gets removed
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		ormResource().save(null);
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setAccess(AccessType.FIELD);
		ormResource().save(null);
		assertEquals(AccessType.FIELD, persistenceUnitDefaults.getAccess());
		assertEquals(org.eclipse.jpt.core.internal.resource.orm.AccessType.FIELD, ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set access to null in the context model
		persistenceUnitDefaults.setAccess(null);
		ormResource().save(null);
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults());
	}

}