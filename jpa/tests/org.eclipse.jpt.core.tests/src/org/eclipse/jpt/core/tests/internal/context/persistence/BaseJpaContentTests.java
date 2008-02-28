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

import org.eclipse.jpt.core.context.BaseJpaContent;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.resource.persistence.PersistenceResource;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class BaseJpaContentTests extends ContextModelTestCase
{
	public BaseJpaContentTests(String name) {
		super(name);
	}
	
	public void testModifyAddPersistenceXml() throws Exception {
		PersistenceResource pr = persistenceResource();
		deleteResource(pr);
		
		assertFalse(pr.exists());
		
		BaseJpaContent baseJpaContent = (BaseJpaContent) getJavaProject().getJpaProject().contextModel();
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
		BaseJpaContent baseJpaContent = (BaseJpaContent) getJavaProject().getJpaProject().contextModel();
		
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
	
	@SuppressWarnings("unchecked")
	public void testUpdateAddPersistenceXml() throws Exception {
		PersistenceResource pr = persistenceResource();
		deleteResource(pr);
		
		assertFalse(pr.exists());
		
		BaseJpaContent baseJpaContent = (BaseJpaContent) getJavaProject().getJpaProject().contextModel();
		assertNull(baseJpaContent.getPersistenceXml());
		
		pr.getContents().add(PersistenceFactory.eINSTANCE.createXmlPersistence());
		pr.save(null);
		assertNotNull(baseJpaContent.getPersistenceXml());
	}
	
	public void testUpdateRemovePersistenceXml() throws Exception {
		PersistenceResource pr = persistenceResource();
		BaseJpaContent baseJpaContent = (BaseJpaContent) getJavaProject().getJpaProject().contextModel();
		
		assertNotNull(baseJpaContent.getPersistenceXml());
		
		deleteResource(pr);
		
		assertNull(baseJpaContent.getPersistenceXml());
	}
}
