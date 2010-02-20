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
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
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
	
	protected OrmXml getOrmXml() {
		return (OrmXml) getPersistenceUnit().mappingFileRefs().next().getMappingFile();
	}
	
	public void testUpdateAddEntityMappings() throws Exception {
		assertEquals(2, getJpaProject().jpaFilesSize());
		JpaXmlResource ormResource = getOrmXmlResource();
		ormResource.getContents().clear();
		ormResource.save(null);
		
		//the ContentType of the orm.xml file is no longer orm, so the jpa file is removed
		assertNull(getOrmXml());
		assertEquals(1, getJpaProject().jpaFilesSize()); //should only be the persistence.xml file
		
		XmlEntityMappings xmlEntityMappings = OrmFactory.eINSTANCE.createXmlEntityMappings();
		xmlEntityMappings.setVersion("1.0");
		ormResource.getContents().add(xmlEntityMappings);
		ormResource.save(null);
		
		assertNotNull(getOrmXml().getEntityMappings());
		assertEquals(2, getJpaProject().jpaFilesSize());
	}
	
	public void testUpdateRemoveEntityMappings() throws Exception {
		JpaXmlResource ormResource = getOrmXmlResource();
		
		assertNotNull(getOrmXml().getRoot());
		
		ormResource.getContents().clear();
		assertNull(getOrmXml());
	}
}
