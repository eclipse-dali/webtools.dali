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
import org.eclipse.jpt.core.internal.context.orm.OrmXml;
import org.eclipse.jpt.core.internal.context.persistence.IPersistenceXml;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.OrmResource;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class OrmXmlTests extends ContextModelTestCase
{
	public OrmXmlTests(String name) {
		super(name);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		xmlPersistenceUnit().setName("foo");
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		xmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		persistenceResource().save(null);
	}
	
	protected IPersistenceXml persistenceXml() {
		return jpaContent().getPersistenceXml();
	}
	
	protected OrmXml ormXml() {
		return persistenceUnit().mappingFileRefs().next().getOrmXml();
	}
	
	public void testUpdateAddEntityMappings() throws Exception {
		OrmResource ormResource = ormResource();
		ormResource.getContents().clear();
		ormResource.save(null);
		
		assertNull(ormXml().getEntityMappings());
		
		ormResource.getContents().add(OrmFactory.eINSTANCE.createEntityMappings());
		
		assertNotNull(ormXml().getEntityMappings());
		
	}
	
	public void testModifyAddEntityMappings() {
		OrmResource ormResource = ormResource();
		ormResource.getContents().remove(ormResource.getEntityMappings());
		assertNull(ormResource.getEntityMappings());
		
		OrmXml ormXml = ormXml();
		assertNull(ormXml.getEntityMappings());
		
		ormXml.addEntityMappings();
		
		assertNotNull(ormXml.getEntityMappings());
		
		boolean exceptionThrown = false;
		try {
			ormXml.addEntityMappings();
		}
		catch (IllegalStateException ise) {
			exceptionThrown = true;
		}
		
		assertTrue("IllegalStateException was not thrown", exceptionThrown);
	}
	
	public void testUpdateRemoveEntityMappings() throws Exception {
		OrmResource ormResource = ormResource();
		
		assertNotNull(ormXml().getEntityMappings());
		
		ormResource.getContents().clear();
		
		assertNull(ormXml().getEntityMappings());
	}
	
	public void testModifyRemoveEntityMappings() {
		OrmXml ormXml = ormXml();
		
		assertNotNull(ormXml.getEntityMappings());
		
		ormXml.removeEntityMappings();
		
		assertNull(ormXml.getEntityMappings());
		
		boolean exceptionThrown = false;
		try {
			ormXml.removeEntityMappings();
		}
		catch (IllegalStateException ise) {
			exceptionThrown = true;
		}
		
		assertTrue("IllegalStateException was not thrown", exceptionThrown);
	}
}
