/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.AccessType;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlPersistenceUnitDefaults;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.EclipseLinkContextModelTestCase;

@SuppressWarnings("nls")
public class EclipseLinkPersistenceUnitDefaultsTests
	extends EclipseLinkContextModelTestCase
{
	public EclipseLinkPersistenceUnitDefaultsTests(String name) {
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
	
	protected OrmPersistenceUnitDefaults persistenceUnitDefaults() {
		return getEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults();
	}
	
	public void testIsAllFeaturesUnset() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		XmlPersistenceUnitMetadata persistenceUnitMetadata = EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
		getXmlEntityMappings().setPersistenceUnitMetadata(persistenceUnitMetadata);
		assertTrue(persistenceUnitMetadata.isUnset());
		
		XmlPersistenceUnitDefaults persistenceUnitDefaultsResource = EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults();
		persistenceUnitMetadata.setPersistenceUnitDefaults(persistenceUnitDefaultsResource);
		assertTrue(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setCascadePersist(true);
		assertFalse(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setCascadePersist(false);
		assertTrue(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setSchema("asdf");
		assertFalse(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setSchema(null);
		assertTrue(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setCatalog("asdf");
		assertFalse(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setCatalog(null);
		assertTrue(persistenceUnitDefaultsResource.isUnset());
		
		
		persistenceUnitDefaultsResource.setAccess(org.eclipse.jpt.jpa.core.resource.orm.AccessType.PROPERTY);
		assertFalse(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setAccess(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD);
		assertFalse(persistenceUnitDefaultsResource.isUnset());
		
		persistenceUnitDefaultsResource.setAccess(null);
		assertTrue(persistenceUnitDefaultsResource.isUnset());
	}

	public void testUpdateSchema() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set schema in the resource model, verify context model updated
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
		assertEquals("MY_SCHEMA", getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
	
		//set schema to null in the resource model
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema(null);
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
	}
	
	public void testModifySchema() throws Exception {		
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set schema in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
		assertEquals("MY_SCHEMA", getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
		
		//set schema to null in the context model
		persistenceUnitDefaults.setSpecifiedSchema(null);
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifySchema2() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set schema in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
		assertEquals("MY_SCHEMA", getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
		
		//set another element on the persistence-unit-defaults element so it doesn't get removed
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("MY_CATALOG");
		//set schema to null in the context model
		persistenceUnitDefaults.setSpecifiedSchema(null);
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
	}
	
	public void testModifySchema3() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		//set another element on the persistence-unit-metadata element so only persistence-unit-defaults element gets removed
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		
		//set schema in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedSchema("MY_SCHEMA");
		assertEquals("MY_SCHEMA", persistenceUnitDefaults.getSchema());
		assertEquals("MY_SCHEMA", getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getSchema());
		
		//set schema to null in the context model
		persistenceUnitDefaults.setSpecifiedSchema(null);
		assertNull(persistenceUnitDefaults.getSchema());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults());
	}
	
	public void testUpdateCatalog() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set catalog in the resource model, verify context model updated
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", persistenceUnitDefaults.getCatalog());
		assertEquals("MY_CATALOG", getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
	
		//set catalog to null in the resource model
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog(null);
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
	}
	
	public void testModifyCatalog() throws Exception {		
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set catalog in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", persistenceUnitDefaults.getCatalog());
		assertEquals("MY_CATALOG", getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
		
		//set catalog to null in the context model
		persistenceUnitDefaults.setSpecifiedCatalog(null);
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifyCatalog2() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set catalog in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", persistenceUnitDefaults.getCatalog());
		assertEquals("MY_CATALOG", getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
		
		//set another element on the persistence-unit-defaults element so it doesn't get removed
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setSchema("MY_SCHEMA");
		//set catalog to null in the context model
		persistenceUnitDefaults.setSpecifiedCatalog(null);
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
	}
	
	public void testModifyCatalog3() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		//set another element on the persistence-unit-metadata element so only persistence-unit-defaults element gets removed
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		
		//set catalog in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedCatalog("MY_CATALOG");
		assertEquals("MY_CATALOG", persistenceUnitDefaults.getCatalog());
		assertEquals("MY_CATALOG", getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getCatalog());
		
		//set catalog to null in the context model
		persistenceUnitDefaults.setSpecifiedCatalog(null);
		assertNull(persistenceUnitDefaults.getCatalog());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults());
	}
	
	public void testUpdateCascadePersist() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set cascadePersist in the resource model, verify context model updated
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCascadePersist(true);
		assertTrue(persistenceUnitDefaults.isCascadePersist());
		assertTrue(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
	
		//set cascadePersist to null in the resource model, persistence-unit-defaults tag not removed
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCascadePersist(false);
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertFalse(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
	}
	
	public void testModifyCascadePersist() throws Exception {		
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set cascadePersist in the context model, verify resource model modified
		persistenceUnitDefaults.setCascadePersist(true);
		assertTrue(persistenceUnitDefaults.isCascadePersist());
		assertTrue(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
		
		//set cascadePersist to null in the context model
		persistenceUnitDefaults.setCascadePersist(false);
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifyCascadePersist2() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set cascadePersist in the context model, verify resource model modified
		persistenceUnitDefaults.setCascadePersist(true);
		assertTrue(persistenceUnitDefaults.isCascadePersist());
		assertTrue(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
		
		//set another element on the persistence-unit-defaults element so it doesn't get removed
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCatalog("MY_CATALOG");
		//set cascadePersist to null in the context model
		persistenceUnitDefaults.setCascadePersist(false);
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertFalse(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
	}
	
	public void testModifyCascadePersist3() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		//set another element on the persistence-unit-metadata element so only persistence-unit-defaults element gets removed
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		
		//set cascadePersist in the context model, verify resource model modified
		persistenceUnitDefaults.setCascadePersist(true);
		assertTrue(persistenceUnitDefaults.isCascadePersist());
		assertTrue(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().isCascadePersist());
		
		//set cascadePersist to null in the context model
		persistenceUnitDefaults.setCascadePersist(false);
		assertFalse(persistenceUnitDefaults.isCascadePersist());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults());
	}
	
	
	public void testUpdateAccess() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set access in the resource model, verify context model updated
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD);
		assertEquals(AccessType.FIELD, persistenceUnitDefaults.getAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
	
		//set access to null in the resource model
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setAccess(null);
		assertNull(persistenceUnitDefaults.getAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
	}
	
	public void testModifyAccess() throws Exception {		
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedAccess(AccessType.PROPERTY);
		assertEquals(AccessType.PROPERTY, persistenceUnitDefaults.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.PROPERTY, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set access to null in the context model
		persistenceUnitDefaults.setSpecifiedAccess(null);
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifyAccess2() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, persistenceUnitDefaults.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set another element on the persistence-unit-defaults element so it doesn't get removed
		getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().setCascadePersist(true);
		//set access to null in the context model
		persistenceUnitDefaults.setSpecifiedAccess(null);
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
	}
	
	public void testModifyAccess3() throws Exception {
		OrmPersistenceUnitDefaults persistenceUnitDefaults = persistenceUnitDefaults();
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		//set another element on the persistence-unit-metadata element so only persistence-unit-defaults element gets removed
		getXmlEntityMappings().setPersistenceUnitMetadata(EclipseLinkOrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);
		
		//set access in the context model, verify resource model modified
		persistenceUnitDefaults.setSpecifiedAccess(AccessType.FIELD);
		assertEquals(AccessType.FIELD, persistenceUnitDefaults.getSpecifiedAccess());
		assertEquals(org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD, getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults().getAccess());
		
		//set access to null in the context model
		persistenceUnitDefaults.setSpecifiedAccess(null);
		assertNull(persistenceUnitDefaults.getSpecifiedAccess());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata().getPersistenceUnitDefaults());
	}

}