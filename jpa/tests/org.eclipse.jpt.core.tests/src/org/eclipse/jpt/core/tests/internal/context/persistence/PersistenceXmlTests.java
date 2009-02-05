/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class PersistenceXmlTests extends ContextModelTestCase
{
	public PersistenceXmlTests(String name) {
		super(name);
	}
	
	protected PersistenceXml getPersistenceXml() {
		return getRootContextNode().getPersistenceXml();
	}
	
	public void testUpdateAddPersistence() throws Exception {
		assertEquals(2, getJpaProject().jpaFilesSize());
		JpaXmlResource prm = getPersistenceXmlResource();
		prm.getContents().clear();
		prm.save(null);
		
		//the ContentType of the persistence.xml file is no longer persistence, so the jpa file is removed
		assertNull(getPersistenceXml());
		assertEquals(1, getJpaProject().jpaFilesSize()); //should only be the orm.xml file
		
		prm.getContents().add(PersistenceFactory.eINSTANCE.createXmlPersistence());
		prm.save(null);
		
		assertNotNull(getPersistenceXml().getPersistence());
		assertEquals(2, getJpaProject().jpaFilesSize());	
	}
	
	public void testModifyAddPersistence() {
		JpaXmlResource prm = getPersistenceXmlResource();
		prm.getContents().remove(getXmlPersistence());
		assertNull(getXmlPersistence());
		
		PersistenceXml persistenceXml = getPersistenceXml();
		
		persistenceXml.addPersistence();
		
		assertNotNull(persistenceXml.getPersistence());
		
		boolean exceptionThrown = false;
		try {
			persistenceXml.addPersistence();
		}
		catch (IllegalStateException ise) {
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}
	
	public void testUpdateRemovePersistence() throws Exception {
		JpaXmlResource prm = getPersistenceXmlResource();
		
		assertNotNull(getPersistenceXml().getPersistence());
		
		prm.getContents().clear();
		
		assertNull(getPersistenceXml().getPersistence());
	}
	
	public void testModifyRemovePersistence() {
		PersistenceXml persistenceXml = getPersistenceXml();
		
		assertNotNull(persistenceXml.getPersistence());
		
		persistenceXml.removePersistence();
		
		assertNull(persistenceXml.getPersistence());
		
		boolean exceptionThrown = false;
		try {
			persistenceXml.removePersistence();
		}
		catch (IllegalStateException ise) {
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}
}
