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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.listener;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IEclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPASolver;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


@SuppressWarnings("restriction")
public class JPAProjectListenerTest {
	private IJPAEditorFeatureProvider featureProvider;
	final String TEST_PROJECT = "Test";
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	IEclipseFacade eclipseFacade = null;
	
	@Before
	public void setUp() throws Exception {
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		IFile entity = factory.createEntity(jpaProject, "org.eclipse.Entity1");
		Thread.sleep(2000);
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(null)).andReturn(getPersistentType(entity));
		expect(featureProvider.getCompilationUnit(isA(JavaPersistentType.class))).andReturn(JavaCore.createCompilationUnitFrom(entity)).anyTimes();
	}

	public static JavaPersistentType getPersistentType(IFile file){
		JpaFile jpaFile = JptJpaCorePlugin.getJpaFile(file);
		for (JpaStructureNode node : getRootNodes(jpaFile)) {
			JavaPersistentType entity = (JavaPersistentType) node;
			return entity;
		}
		return null;
	}	
	
	private static Iterable<JpaStructureNode> getRootNodes(JpaFile jpaFile) {
		if(jpaFile == null){
			return EmptyIterable.instance();
		}
		return jpaFile.getRootStructureNodes();
	}
	
	public ICompilationUnit createCompilationUnitFrom(IFile file) {
		return JavaCore.createCompilationUnitFrom(file);
	}
	
	@Test
	public void testJPAProjectListener() {
		JPASolver slv = new JPASolver(); 
		slv.setFeatureProvider(featureProvider);
		jpaProject.addCollectionChangeListener("mark", slv.new JPAProjectListener());
		PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
		JavaPersistentType jpt = JpaArtifactFactory.instance().getJPT("org.eclipse.Entity1", pu);
		ContainerShape cs = EasyMock.createMock(ContainerShape.class);
		expect(featureProvider.getPictogramElementForBusinessObject(jpt)).andStubReturn(cs);
		IRemoveFeature ft = EasyMock.createMock(IRemoveFeature.class);
		expect(featureProvider.getRemoveFeature(isA(IRemoveContext.class))).andReturn(ft);
		// The remove method should be invoked exactly once
		ft.remove(isA(IRemoveContext.class));
		replay(ft, cs, featureProvider);
		JpaArtifactFactory.instance().deleteEntityClass(jpt, featureProvider);
	}
	
	@Test
	public void testJPAProjectListenerNoRemove() {
		JPASolver slv = new JPASolver(); 
		slv.setFeatureProvider(featureProvider);
		jpaProject.addCollectionChangeListener("mark", slv.new JPAProjectListener());
		PersistenceUnit pu = JpaArtifactFactory.instance().getPersistenceUnit(jpaProject);
		JavaPersistentType jpt = JpaArtifactFactory.instance().getJPT("org.eclipse.Entity1", pu);
		expect(featureProvider.getPictogramElementForBusinessObject(jpt)).andStubReturn(null);
		IRemoveFeature ft = EasyMock.createMock(IRemoveFeature.class);
		expect(featureProvider.getRemoveFeature(isA(IRemoveContext.class))).andReturn(ft);
		replay(ft, featureProvider);
		// if the container shape is null the remove method of the feature should not be invoked
		JpaArtifactFactory.instance().deleteEntityClass(jpt, featureProvider);
	}
	
	
	@After	
	public void tearDown() throws Exception {		
		deleteAllProjects();
	}
	
	private void deleteAllProjects() throws Exception {
		IProgressMonitor monitor= new NullProgressMonitor();
		ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				try {
					IProject project = jpaProject.getProject();
				    project.close(monitor);	    	
				    project.close(monitor);
				    project.delete(true, true, monitor);
				} catch (Exception e) {
					//ignore
				}
			}
		} , monitor);
	}	
	
	
}
