/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.orm;

import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.OrmXmlResource;
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
		getXmlPersistenceUnit().setName("foo");
		XmlMappingFileRef mappingFileRef = PersistenceFactory.eINSTANCE.createXmlMappingFileRef();
		mappingFileRef.setFileName(JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH);
		getXmlPersistenceUnit().getMappingFiles().add(mappingFileRef);
		getPersistenceXmlResource().save(null);
	}
	
	protected PersistenceXml persistenceXml() {
		return getRootContextNode().getPersistenceXml();
	}
	
	protected OrmXml ormXml() {
		return (OrmXml) getPersistenceUnit().mappingFileRefs().next().getMappingFile();
	}
	
	public void testUpdateAddEntityMappings() throws Exception {
		OrmXmlResource ormResource = getOrmXmlResource();
		ormResource.getContents().clear();
		ormResource.save(null);
		
		// removing root node now results in reducing content type to simple xml
		assertNull(ormXml());
		
		ormResource.getContents().add(OrmFactory.eINSTANCE.createXmlEntityMappings());
		ormResource.save(null);
		
		assertNotNull(ormXml().getRoot());
	}
	
	public void testModifyAddEntityMappings() {
		OrmXmlResource ormResource = getOrmXmlResource();
		ormResource.getContents().remove(ormResource.getEntityMappings());
		assertNull(ormResource.getEntityMappings());
		
		OrmXml ormXml = ormXml();
		assertNull(ormXml.getRoot());
		
		ormXml.addEntityMappings();
		
		assertNotNull(ormXml.getRoot());
		
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
		OrmXmlResource ormResource = getOrmXmlResource();
		
		assertNotNull(ormXml().getRoot());
		
		ormResource.getContents().clear();
		
		assertNull(ormXml().getRoot());
	}
	
	public void testModifyRemoveEntityMappings() {
		OrmXml ormXml = ormXml();
		
		assertNotNull(ormXml.getRoot());
		
		ormXml.removeEntityMappings();
		
		assertNull(ormXml.getRoot());
		
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
