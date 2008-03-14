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
package org.eclipse.jpt.core.tests.internal.context.persistence;

import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class RootContextTests extends ContextModelTestCase
{
	public RootContextTests(String name) {
		super(name);
	}
	
	public void testModifyAddPersistenceXml() throws Exception {
		PersistenceResource pr = persistenceResource();
		deleteResource(pr);
		
		assertFalse(pr.exists());
		
		JpaRootContextNode baseJpaContent = getJavaProject().getJpaProject().rootContext();
		assertNull(baseJpaContent.persistenceXml());
		
		baseJpaContent.addPersistenceXml();
		
		assertNotNull(baseJpaContent.persistenceXml());
		
		boolean exceptionThrown = false;
		try {
			baseJpaContent.addPersistenceXml();
		}
		catch (IllegalStateException ise) {
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}
	
	public void testModifyRemovePersistenceXml() throws Exception {
		JpaRootContextNode baseJpaContent = getJavaProject().getJpaProject().rootContext();
		
		assertNotNull(baseJpaContent.persistenceXml());
		
		baseJpaContent.removePersistenceXml();
		waitForWorkspaceJobs();
		
		assertNull(baseJpaContent.persistenceXml());
		
		boolean exceptionThrown = false;
		try {
			baseJpaContent.removePersistenceXml();
		}
		catch (IllegalStateException ise) {
			exceptionThrown = true;
		}
		
		assertTrue(exceptionThrown);
	}
	
	public void testUpdateAddPersistenceXml() throws Exception {
		PersistenceResource pr = persistenceResource();
		deleteResource(pr);
		
		assertFalse(pr.exists());
		
		JpaRootContextNode baseJpaContent = getJavaProject().getJpaProject().rootContext();
		assertNull(baseJpaContent.persistenceXml());
		
		pr.getContents().add(PersistenceFactory.eINSTANCE.createXmlPersistence());
		pr.save(null);
		assertNotNull(baseJpaContent.persistenceXml());
	}
	
	public void testUpdateRemovePersistenceXml() throws Exception {
		PersistenceResource pr = persistenceResource();
		JpaRootContextNode baseJpaContent = getJavaProject().getJpaProject().rootContext();
		
		assertNotNull(baseJpaContent.persistenceXml());
		
		deleteResource(pr);
		
		assertNull(baseJpaContent.persistenceXml());
	}
}
