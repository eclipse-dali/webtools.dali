/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2010 SAP AG.
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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateDeleteOnlyAttributeTest {

	private IJPAEditorFeatureProvider featureProvider;
	final String TEST_PROJECT = "Test";
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	JavaPersistentType jpt = null;
	
	@Before
	public void setUp() throws Exception{
		JptJpaCorePlugin.getJpaProjectManager();
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		IFile entity = factory.createEntity(jpaProject, "org.eclipse.Entity1");
		jpt = (JavaPersistentType)JPACreateFactory.getPersistentType(entity);
		int c = 0;
		while ((jpt == null) && (c < 100)) {
			try {
				Thread.sleep(250);
			} catch (Exception e) {}
			jpt = (JavaPersistentType)JPACreateFactory.getPersistentType(entity);
			c++;
		}
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(isA(ContainerShape.class))).andStubReturn(jpt);		
		expect(featureProvider.getBusinessObjectForPictogramElement(null)).andReturn(JPACreateFactory.getPersistentType(entity));
		expect(featureProvider.getCompilationUnit(isA(JavaPersistentType.class))).andReturn(JavaCore.createCompilationUnitFrom(entity)).anyTimes();
		expect(featureProvider.addIfPossible(isA(IAddContext.class))).andStubReturn(null);
		expect(featureProvider.getPictogramElementForBusinessObject(jpt)).andStubReturn(isA(ContainerShape.class));
		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(entity);
		expect(featureProvider.getCompilationUnit(jpt)).andStubReturn(cu);
		replay(featureProvider);
	}
	
	
	@Test
	public void testCreatePropertyAnnotatedAttribute() throws Exception {
		EntityAttributesChangeTestListener lsnr = new EntityAttributesChangeTestListener(0, 1);
		jpt.addListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
		deleteAttribute("id");
		assertTrue(lsnr.waitForEvents());
		jpt.removeListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
		lsnr = new EntityAttributesChangeTestListener(2, 0);
		jpt.addListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
		JpaArtifactFactory.instance().makeNewAttribute(featureProvider, jpt, featureProvider.getCompilationUnit(jpt), "aaa", "java.lang.String", null, "aaa", null, false, true);
		assertFalse(lsnr.waitForEvents());
		assertEquals(1, lsnr.incrementCounter);
		assertEquals(0, lsnr.decrementCounter);		
	}
	
	
	
	@Test
	public void testCreateFieldAnnotatedAttribute() throws Exception {
		EntityAttributesChangeTestListener lsnr = new EntityAttributesChangeTestListener(0, 1);
		jpt.addListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
		deleteAttribute("id");
		assertTrue(lsnr.waitForEvents());
		jpt.removeListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
		lsnr = new EntityAttributesChangeTestListener(2, 0);
		jpt.addListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
		JpaArtifactFactory.instance().makeNewAttribute(featureProvider, jpt, featureProvider.getCompilationUnit(jpt), "aaa", "java.lang.String", null, "aaa", null, false, false);
		assertFalse(lsnr.waitForEvents());
		assertEquals(1, lsnr.incrementCounter);
		assertEquals(0, lsnr.decrementCounter);		
	}	
	
	
	@Test
	public void testDeletePropertyAnnotatedAttribute() throws Exception {
		EntityAttributesChangeTestListener lsnr = new EntityAttributesChangeTestListener(0, 2);
		jpt.addListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
		deleteAttribute("id");
		assertFalse(lsnr.waitForEvents());
		assertEquals(0, lsnr.incrementCounter);
		assertEquals(1, lsnr.decrementCounter);		
	}	
	

	@Test
	public void testDeleteFieldAnnotatedAttribute() throws Exception  {
		EntityAttributesChangeTestListener lsnr = new EntityAttributesChangeTestListener(1, 1);
		jpt.addListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
		deleteAttribute("id");
		JpaArtifactFactory.instance().makeNewAttribute(featureProvider, jpt, featureProvider.getCompilationUnit(jpt), "aaa", "java.lang.String", null, "aaa", null, false, false);
		assertTrue(lsnr.waitForEvents());
		jpt.removeListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
		lsnr = new EntityAttributesChangeTestListener(0, 2);
		jpt.addListChangeListener(JavaPersistentType.ATTRIBUTES_LIST, lsnr);
		deleteAttribute("aaa");
		assertFalse(lsnr.waitForEvents());
		assertEquals(0, lsnr.incrementCounter);
		assertEquals(1, lsnr.decrementCounter);		
	}
	
	
	
	private void deleteAttribute(String attrName) {
		JpaArtifactFactory.instance().deleteAttribute(jpt, attrName, featureProvider);
	}	
	
	
	public static class EntityAttributesChangeTestListener implements ListChangeListener {
		
		private Semaphore sem = new Semaphore(1);
		
		private int toBeAdded;
		private int toBeRemove;

		public EntityAttributesChangeTestListener(int add, int remove) {
			this.toBeAdded = add;
			this.toBeRemove = remove;
			try {
				sem.acquire(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public boolean waitForEvents() throws InterruptedException {
			return sem.tryAcquire(5, TimeUnit.SECONDS);
		}
		
		public int incrementCounter = 0;
		public int decrementCounter = 0;
		
		public void listChanged(ListChangeEvent event) {
		}

		public void itemsAdded(ListAddEvent arg0) {
			incrementCounter++;
			if(this.incrementCounter >= toBeAdded && this.decrementCounter>= toBeRemove ) {
				sem.release();
			}
		}

		public void itemsMoved(ListMoveEvent arg0) {
		}

		public void itemsRemoved(ListRemoveEvent arg0) {
			decrementCounter++;
			if(this.incrementCounter >= toBeAdded && this.decrementCounter>= toBeRemove ) {
				sem.release();
			}
		}

		public void itemsReplaced(ListReplaceEvent arg0) {
		}

		public void listCleared(ListClearEvent arg0) {
		}
	};
	
	@After	
	public void tearDown() throws Exception {		
		deleteAllProjects();
	}
	
	private void deleteAllProjects() throws Exception {
		IProgressMonitor monitor= new NullProgressMonitor();
		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IProject project = jpaProject.getProject();
			    project.close(monitor);	    	
			    project.close(monitor);
			    project.delete(true, true, monitor);				
			}
		} , monitor);
	}	
	
}
