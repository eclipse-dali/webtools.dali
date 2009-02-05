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

import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.resource.common.JpaXmlResource;
import org.eclipse.jpt.core.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.tests.internal.context.ContextModelTestCase;

public class RootContextNodeTests extends ContextModelTestCase
{
	public RootContextNodeTests(String name) {
		super(name);
	}
	
	public void testUpdateAddPersistenceXml() throws Exception {
		JpaXmlResource pr = getPersistenceXmlResource();
		deleteResource(pr);
		
		assertFalse(pr.fileExists());
		
		JpaRootContextNode baseJpaContent = getJavaProject().getJpaProject().getRootContextNode();
		assertNull(baseJpaContent.getPersistenceXml());
		
		pr.getContents().add(PersistenceFactory.eINSTANCE.createXmlPersistence());
		pr.save(null);
		assertNotNull(baseJpaContent.getPersistenceXml());
	}
	
	public void testUpdateRemovePersistenceXml() throws Exception {
		JpaXmlResource pr = getPersistenceXmlResource();
		JpaRootContextNode baseJpaContent = getJavaProject().getJpaProject().getRootContextNode();
		
		assertNotNull(baseJpaContent.getPersistenceXml());
		
		deleteResource(pr);
		
		assertNull(baseJpaContent.getPersistenceXml());
	}
}
