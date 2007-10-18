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

import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.core.internal.context.base.BaseJpaContent;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceArtifactEdit;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceFactory;
import org.eclipse.jpt.core.internal.resource.persistence.PersistenceResourceModel;
import org.eclipse.wst.common.internal.emfworkbench.WorkbenchResourceHelper;

public class BaseJpaContentTests extends ContextModelTestCase
{
	public BaseJpaContentTests(String name) {
		super(name);
	}
	
	public void testAddPersistenceXml() throws Exception {
		String persistenceXmlUri = JptCorePlugin.persistenceXmlDeploymentURI(jpaProject.getProject());
		PersistenceArtifactEdit pae = 
				PersistenceArtifactEdit.getArtifactEditForWrite(jpaProject.getProject(), persistenceXmlUri);
		PersistenceResourceModel prm = pae.getPersistenceResource();
		WorkbenchResourceHelper.deleteResource(prm); // throws CoreException, possibly
		waitForUpdate();
		
		assertTrue(! jpaProject.getProject().getFile(persistenceXmlUri).exists());
		
		BaseJpaContent baseJpaContent = (BaseJpaContent) jpaProject.getJpaProject().contextModel();
		assertNull(baseJpaContent.persistenceXml());
		
		prm.getContents().add(PersistenceFactory.eINSTANCE.createPersistence());
		prm.save(null);
		waitForUpdate();
		assertNotNull(baseJpaContent.persistenceXml());
	}
}
