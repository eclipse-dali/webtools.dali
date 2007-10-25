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
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;

public class PersistenceXmlTests extends ContextModelTestCase
{
	public PersistenceXmlTests(String name) {
		super(name);
	}
	
	public void testAddPersistence() throws Exception {
		PersistenceResourceModel prm = persistenceResourceModel();
		IBaseJpaContent baseJpaContent = (IBaseJpaContent) jpaProject.getJpaProject().contextModel();
		IPersistenceXml persistenceXml = baseJpaContent.getPersistenceXml();
		prm.getContents().clear();
		prm.save(null);
		waitForProjectUpdate();
		
		assertNull(persistenceXml.getPersistence());
		
		prm.getContents().add(PersistenceFactory.eINSTANCE.createXmlPersistence());
		waitForProjectUpdate();
		
		assertNotNull(persistenceXml.getPersistence());
		
	}
	
	public void testRemovePersistence() throws Exception {
		PersistenceResourceModel prm = persistenceResourceModel();
		IBaseJpaContent baseJpaContent = (IBaseJpaContent) jpaProject.getJpaProject().contextModel();
		IPersistenceXml persistenceXml = baseJpaContent.getPersistenceXml();
		
		assertNotNull(persistenceXml.getPersistence());
		
		prm.getContents().clear();
		
		assertNull(persistenceXml.getPersistence());
	}
}
