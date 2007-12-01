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
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.internal.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class PersistenceUnitMetadataTests extends ContextModelTestCase
{
	public PersistenceUnitMetadataTests(String name) {
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
	
	public void testUpdateXmlMappingMetadataComplete() throws Exception {
		PersistenceUnitMetadata persistenceUnitMetadata = entityMappings().getPersistenceUnitMetadata();
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set xmlMappingMetadataComplete in the resource model, verify context model updated
		ormResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createPersistenceUnitMetadata());
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);		
		assertTrue(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertTrue(ormResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
	
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertFalse(ormResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());

		//set xmlMappingMetadataComplete to null in the resource model
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertFalse(ormResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
	}
	
	public void testModifyXmlMappingMetadataComplete() throws Exception {		
		PersistenceUnitMetadata persistenceUnitMetadata = entityMappings().getPersistenceUnitMetadata();
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set xmlMappingMetadataComplete in the context model, verify resource model modified
		persistenceUnitMetadata.setXmlMappingMetadataComplete(true);
		ormResource().save(null);
		assertTrue(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertTrue(ormResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
		
		//set xmlMappingMetadataComplete to null in the context model
		persistenceUnitMetadata.setXmlMappingMetadataComplete(false);
		ormResource().save(null);
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifyXmlMappingMetadataComplete2() throws Exception {
		PersistenceUnitMetadata persistenceUnitMetadata = entityMappings().getPersistenceUnitMetadata();
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(ormResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set xmlMappingMetadataComplete in the context model, verify resource model modified
		persistenceUnitMetadata.setXmlMappingMetadataComplete(true);
		ormResource().save(null);
		assertTrue(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertTrue(ormResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
		
		//set xmlMappingMetadataComplete to null in the context model
		//set another element on the persistence-unit-metadata element so it doesn't get removed
		ormResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createPersistenceUnitDefaults());
		persistenceUnitMetadata.setXmlMappingMetadataComplete(false);
		ormResource().save(null);
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertFalse(ormResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
	}
}