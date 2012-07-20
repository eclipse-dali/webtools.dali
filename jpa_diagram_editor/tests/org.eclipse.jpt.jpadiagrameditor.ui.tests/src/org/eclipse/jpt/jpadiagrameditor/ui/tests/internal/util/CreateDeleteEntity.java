/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2011 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util;

import static org.junit.Assert.*;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Before;
import org.junit.Test;


public class CreateDeleteEntity {
	
	private static final String TEST_PROJECT = "Test";
	private JPACreateFactory factory = JPACreateFactory.instance();
	private JpaProject jpaProject = null;

	@Before
	public void setUp() throws Exception {
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		Thread.sleep(2000);
	}

	@Test
	public void testCreateAndDeleteEntity() throws Exception {	
		assertNotNull(jpaProject);
//		SynchronousJpaProjectUpdater updater = new SynchronousJpaProjectUpdater(jpaProject);
//		updater.start();
		JpaRootContextNode jpaProjectContent = jpaProject.getRootContextNode();
		assertNotNull(jpaProjectContent);		
//		if(jpaProjectContent.getPersistenceXml() == null) {		
//			updater = new SynchronousJpaProjectUpdater(jpaProject);
//			updater.start();			
//		}
		Thread.sleep(2000);		
		PersistenceXml persistenceXml = jpaProjectContent.getPersistenceXml();
		assertNotNull(persistenceXml);
		Persistence p = persistenceXml.getRoot();
		int cnt = 0;
		while ((p == null) && (cnt < 25)) {
			Thread.sleep(250);
			p = persistenceXml.getRoot();
			cnt++;
		}
		assertTrue("Can't obtain persistence object", p != null);
		assertTrue(persistenceXml.getRoot().getPersistenceUnitsSize() == 1);
		//java.lang.UnsupportedOperationException: No PersistenceUnit in this context
		//PersistenceUnit persistenceUnit = persistenceXml.getPersistence().getPersistenceUnit();
		PersistenceUnit persistenceUnit = persistenceXml.getRoot().getPersistenceUnits().iterator().next();
		assertNotNull(persistenceUnit);
		assertTrue(persistenceUnit.getClassRefsSize() == 0);
		IFile entity1 = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com"}, "Entity1");		
		assertTrue(entity1.exists());
//		if(jpaProject.getJavaResourceType("com.Entity1") == null) {			
//			updater = new SynchronousJpaProjectUpdater(jpaProject);
//			updater.start();
//		}
		JavaResourceAbstractType persistenceType = jpaProject.getJavaResourceType("com.Entity1");
		int c = 0;
		while ((persistenceType == null) && (c < 100)) {
			Thread.sleep(500);
			jpaProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
			persistenceType = jpaProject.getJavaResourceType("com.Entity1");
			c++;
		}
		assertNotNull(persistenceType);
		PersistentType t = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, persistenceType.getTypeBinding().getQualifiedName());
		c = 0;
		while ((t == null) && (c < 100)) {
			Thread.sleep(500);
			t = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, persistenceType.getTypeBinding().getQualifiedName());
			c++;
		}
		JpaArtifactFactory.instance().deletePersistenceTypeResource(t);
		assertFalse(entity1.exists());
//		updater = new SynchronousJpaProjectUpdater(jpaProject);
//		updater.start();
		jpaProject.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
		persistenceType = jpaProject.getJavaResourceType("com.Entity1");
		c = 0;
		while ((persistenceType != null) && (c < 250)) {
			Thread.sleep(500);
			persistenceType = jpaProject.getJavaResourceType("com.Entity1");
			c++;
		}
		assertNull(persistenceType);		
		assertNotNull(jpaProject);
	}	
}
