/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.orm;

import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistenceUnitMetadata;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

public class PersistenceUnitMetadataTests extends ContextModelTestCase
{
	public PersistenceUnitMetadataTests(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptJpaCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}

	protected OrmPersistenceUnitMetadata persistenceUnitMetadata() {
		return getEntityMappings().getPersistenceUnitMetadata();
	}
	
	public void testIsAllFeaturesUnset() throws Exception {
		org.eclipse.jpt.jpa.core.resource.orm.XmlPersistenceUnitMetadata persistenceUnitMetadata = OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata();
		getXmlEntityMappings().setPersistenceUnitMetadata(persistenceUnitMetadata);
		assertTrue(persistenceUnitMetadata.isUnset());
		
		persistenceUnitMetadata.setXmlMappingMetadataComplete(true);
		assertFalse(persistenceUnitMetadata.isUnset());
		
		persistenceUnitMetadata.setXmlMappingMetadataComplete(false);
		assertTrue(persistenceUnitMetadata.isUnset());
		
		persistenceUnitMetadata.setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		assertFalse(persistenceUnitMetadata.isUnset());		
	}
	
	public void testUpdateXmlMappingMetadataComplete() throws Exception {
		OrmPersistenceUnitMetadata persistenceUnitMetadata = getEntityMappings().getPersistenceUnitMetadata();
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set xmlMappingMetadataComplete in the resource model, verify context model updated
		getXmlEntityMappings().setPersistenceUnitMetadata(OrmFactory.eINSTANCE.createXmlPersistenceUnitMetadata());
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(true);		
		assertTrue(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertTrue(getXmlEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());

		//set xmlMappingMetadataComplete to null in the resource model
		getXmlEntityMappings().getPersistenceUnitMetadata().setXmlMappingMetadataComplete(false);
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertFalse(getXmlEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
	}
	
	public void testModifyXmlMappingMetadataComplete() throws Exception {		
		OrmPersistenceUnitMetadata persistenceUnitMetadata = getEntityMappings().getPersistenceUnitMetadata();
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set xmlMappingMetadataComplete in the context model, verify resource model modified
		persistenceUnitMetadata.setXmlMappingMetadataComplete(true);
		assertTrue(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertTrue(getXmlEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
		
		//set xmlMappingMetadataComplete to null in the context model
		persistenceUnitMetadata.setXmlMappingMetadataComplete(false);
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
	}
	
	public void testModifyXmlMappingMetadataComplete2() throws Exception {
		OrmPersistenceUnitMetadata persistenceUnitMetadata = getEntityMappings().getPersistenceUnitMetadata();
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertNull(getXmlEntityMappings().getPersistenceUnitMetadata());
		
		//set xmlMappingMetadataComplete in the context model, verify resource model modified
		persistenceUnitMetadata.setXmlMappingMetadataComplete(true);
		assertTrue(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertTrue(getXmlEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
		
		//set xmlMappingMetadataComplete to null in the context model
		//set another element on the persistence-unit-metadata element so it doesn't get removed
		getXmlEntityMappings().getPersistenceUnitMetadata().setPersistenceUnitDefaults(OrmFactory.eINSTANCE.createXmlPersistenceUnitDefaults());
		persistenceUnitMetadata.setXmlMappingMetadataComplete(false);
		assertFalse(persistenceUnitMetadata.isXmlMappingMetadataComplete());
		assertFalse(getXmlEntityMappings().getPersistenceUnitMetadata().isXmlMappingMetadataComplete());
	}
}