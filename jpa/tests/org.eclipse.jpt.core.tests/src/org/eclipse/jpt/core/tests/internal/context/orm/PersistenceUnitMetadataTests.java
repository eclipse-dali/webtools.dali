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
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
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
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceResource().save(null);
	}

	protected PersistenceUnitMetadata persistenceUnitMetadata() {
		return getEntityMappings().getPersistenceUnitMetadata();
	}
	
	public void testIsAllFeaturesUnset() throws Exception {
		org.eclipse.jpt.core.resource.orm.XmlPersistenceUnitMetadata persistenceUnitMetadata = OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
		getOrmResource().getEntityMappings().setPersistenceUnitMetadata(persistenceUnitMetadata);
		assertTrue(persistenceUnitMetadata.isAllFeaturesUnset());
		
		persistenceUnitMetadata.setXmlMappingMetadataComplete(true);
		assertFalse(persistenceUnitMetadata.isAllFeaturesUnset());
		
		persistenceUnitMetadata.setXmlMappingMetadataComplete(false);
		assertTrue(persistenceUnitMetadata.isAllFeaturesUnset());
		
		persistenceUnitMetadata.setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		assertFalse(persistenceUnitMetadata.isAllFeaturesUnset());		
	}
	
	public void testUpdateXmlMappingMetadataComplete() throws Exception {
		PersistenceUnitMetadata persistenceUnitMetadata = getEntityMappings().getPersistenceUnitMetadata();
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(getOrmResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set xmlMappingMetadataComplete in the resource model, verify context model updated
		getOrmResource().getEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getOrmResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);		
		assertTrue(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertTrue(getOrmResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());

		//set xmlMappingMetadataComplete to null in the resource model
		getOrmResource().getEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertFalse(getOrmResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
	}
	
	public void testModifyXmlMappingMetadataComplete() throws Exception {		
		PersistenceUnitMetadata persistenceUnitMetadata = getEntityMappings().getPersistenceUnitMetadata();
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(getOrmResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set xmlMappingMetadataComplete in the context model, verify resource model modified
		persistenceUnitMetadata.setXmlMappingMetadataComplete(true);
		assertTrue(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertTrue(getOrmResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
		
		//set xmlMappingMetadataComplete to null in the context model
		persistenceUnitMetadata.setXmlMappingMetadataComplete(false);
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(getOrmResource().getEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifyXmlMappingMetadataComplete2() throws Exception {
		PersistenceUnitMetadata persistenceUnitMetadata = getEntityMappings().getPersistenceUnitMetadata();
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(getOrmResource().getEntityMappings().getPersistenceUnitMetadata());
		
		//set xmlMappingMetadataComplete in the context model, verify resource model modified
		persistenceUnitMetadata.setXmlMappingMetadataComplete(true);
		assertTrue(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertTrue(getOrmResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
		
		//set xmlMappingMetadataComplete to null in the context model
		//set another element on the persistence-unit-metadata element so it doesn't get removed
		getOrmResource().getEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		persistenceUnitMetadata.setXmlMappingMetadataComplete(false);
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertFalse(getOrmResource().getEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
	}
}