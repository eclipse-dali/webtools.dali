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
package org.eclipse.jpt.core.tests.internal.context;

import org.eclipse.jpt.core.internal.context.base.IBaseJpaContent;
import org.eclipse.jpt.core.internal.context.base.IPersistenceXml;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResource;

public class PersistenceXmlTests extends ContextModelTestCase
{
	public PersistenceXmlTests(String name) {
		super(name);
	}
	
	public void testAddPersistence() {
		PersistenceResource prm = persistenceResource();
		prm.getContents().remove(prm.getPersistence());
		assertNull(prm.getPersistence());
		
		IPersistenceXml persistenceXml = persistenceXml();
		
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
	
	public void testRemovePersistence() {
		IPersistenceXml persistenceXml = persistenceXml();
		
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
	
	public void testUpdateAddPersistence() throws Exception {
		PersistenceResource prm = persistenceResource();
		prm.getContents().clear();
		prm.save(null);
		
		assertNull(persistenceXml().getPersistence());
		
		prm.getContents().add(PersistenceFactory.eINSTANCE.createXmlPersistence());
		
		assertNotNull(persistenceXml().getPersistence());
		
	}
	
	public void testUpdateRemovePersistence() throws Exception {
		PersistenceResource prm = persistenceResource();
		
		assertNotNull(persistenceXml().getPersistence());
		
		prm.getContents().clear();
		
		assertNull(persistenceXml().getPersistence());
	}
	
	protected IBaseJpaContent baseJpaContent() {
		return (IBaseJpaContent) getJavaProject().getJpaProject().contextModel();
	}
	
	protected IPersistenceXml persistenceXml() {
		return baseJpaContent().getPersistenceXml();
	}
}
