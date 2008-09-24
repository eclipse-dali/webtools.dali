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

public class RootContextNodeTests extends ContextModelTestCase
{
	public RootContextNodeTests(String name) {
		super(name);
	}
	
	public void testModifyAddPersistenceXml() throws Exception {
		PersistenceResource pr = persistenceResource();
		deleteResource(pr);
		
		assertFalse(pr.exists());
		
		JpaRootContextNode baseJpaContent = getJavaProject().getJpaProject().getRootContextNode();
		assertNull(baseJpaContent.getPersistenceXml());
		
		baseJpaContent.addPersistenceXml();
		
		assertNotNull(baseJpaContent.getPersistenceXml());
		
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
		JpaRootContextNode baseJpaContent = getJavaProject().getJpaProject().getRootContextNode();
		
		assertNotNull(baseJpaContent.getPersistenceXml());
		
		baseJpaContent.removePersistenceXml();
		waitForWorkspaceJobs();
		
		assertNull(baseJpaContent.getPersistenceXml());
		
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
		
		JpaRootContextNode baseJpaContent = getJavaProject().getJpaProject().getRootContextNode();
		assertNull(baseJpaContent.getPersistenceXml());
		
		pr.getContents().add(PersistenceFactory.eINSTANCE.createXmlPersistence());
		pr.save(null);
		assertNotNull(baseJpaContent.getPersistenceXml());
	}
	
	public void testUpdateRemovePersistenceXml() throws Exception {
		PersistenceResource pr = persistenceResource();
		JpaRootContextNode baseJpaContent = getJavaProject().getJpaProject().getRootContextNode();
		
		assertNotNull(baseJpaContent.getPersistenceXml());
		
		deleteResource(pr);
		
		assertNull(baseJpaContent.getPersistenceXml());
	}
}
