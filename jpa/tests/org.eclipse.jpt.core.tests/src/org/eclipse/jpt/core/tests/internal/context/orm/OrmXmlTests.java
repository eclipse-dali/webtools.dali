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
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.OrmResource;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
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
	
	protected PersistenceXml persistenceXml() {
		return getRootContextNode().getPersistenceXml();
	}
	
	protected OrmXml ormXml() {
		return (OrmXml) persistenceUnit().mappingFileRefs().next().getMappingFile();
	}
	
	public void testUpdateAddEntityMappings() throws Exception {
		OrmResource ormResource = ormResource();
		ormResource.getContents().clear();
		ormResource.save(null);
		
		// removing root node now results in reducing content type to simple xml
		assertNull(ormXml());
		
		ormResource.getContents().add(OrmFactory.eINSTANCE.createXmlEntityMappings());
		ormResource.save(null);
		
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
