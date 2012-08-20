/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.context.persistence;

import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.jpa.core.tests.internal.context.ContextModelTestCase;

@SuppressWarnings("nls")
public class PersistenceXmlTests extends ContextModelTestCase
{
	public PersistenceXmlTests(String name) {
		super(name);
	}
	
	protected PersistenceXml getPersistenceXml() {
		return getRootContextNode().getPersistenceXml();
	}
	
	public void testUpdateAddPersistence() throws Exception {
		assertEquals(2, getJpaProject().getJpaFilesSize());
		JptXmlResource prm = getPersistenceXmlResource();
		prm.getContents().clear();
		prm.save(null);
		
		//the ContentType of the persistence.xml file is no longer persistence, so the jpa file is removed
		assertNull(getPersistenceXml());
		assertEquals(1, getJpaProject().getJpaFilesSize()); //should only be the orm.xml file
		
		XmlPersistence xmlPersistence = PersistenceFactory.eINSTANCE.createXmlPersistence();
		xmlPersistence.setVersion("1.0");
		prm.getContents().add(xmlPersistence);
		prm.save(null);
		
		assertNotNull(getPersistenceXml().getRoot());
		assertEquals(2, getJpaProject().getJpaFilesSize());	
	}
	
	public void testUpdateRemovePersistence() throws Exception {
		JptXmlResource prm = getPersistenceXmlResource();
		
		assertNotNull(getPersistenceXml().getRoot());
		
		prm.getContents().clear();
		assertNull(getPersistenceXml());
	}
}
