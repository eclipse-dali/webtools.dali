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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.modelintegration.ui;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.platform.IDiagramEditor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.ui.JPAEditorMatchingStrategy;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.IModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IEclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPADiagramEditorInput;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("restriction")
public class ModelIntegrationTest {
	
	private static final String CODE_GENERATED = "CODE_GENERATED";
	private IJPAEditorFeatureProvider featureProvider;
	final String TEST_PROJECT = "Test";
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	IEclipseFacade eclipseFacade = null;
	private String testProjectName = TEST_PROJECT + "_" + System.currentTimeMillis();
	IFile entityFile = null;
	
	@Before
	public void setUp() throws Exception {
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(testProjectName);
		assertNotNull(jpaProject);
		entityFile = factory.createEntity(jpaProject, "org.eclipse.Entity1");
		Thread.sleep(2000);
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(null)).andReturn(getPersistentType(entityFile));
		expect(featureProvider.getCompilationUnit(isA(JavaPersistentType.class))).andReturn(JavaCore.createCompilationUnitFrom(entityFile)).anyTimes();
	}
	
	@Test
	public void testJPAEditorMatchingStrategyWithEntityFileEditorInputTrue() {
		IEditorReference editorRef = EasyMock.createMock(IEditorReference.class);
		IFileEditorInput input = EasyMock.createMock(IFileEditorInput.class);
		expect(input.getFile()).andStubReturn(entityFile);
		expect(input.getName()).andStubReturn(CODE_GENERATED);
		ICompilationUnit cu = createCompilationUnitFrom(entityFile); 
		
		IJPAEditorUtil ut = EasyMock.createMock(IJPAEditorUtil.class);
		JavaPersistentType inputJptType = getPersistentType(entityFile);
		expect(ut.getJPType(cu)).andStubReturn(inputJptType);
		expect(featureProvider.getJPAEditorUtil()).andStubReturn(ut);
		Diagram d = EasyMock.createMock(Diagram.class);
		IJPADiagramEditorInput dEdInput = EasyMock.createMock(IJPADiagramEditorInput.class);
		try {
			expect(editorRef.getEditorInput()).andStubReturn(dEdInput);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		expect(dEdInput.getDiagram()).andStubReturn(d);
		IDiagramTypeProvider dtp = EasyMock.createMock(IDiagramTypeProvider.class);
		expect(featureProvider.getDiagramTypeProvider()).andStubReturn(dtp);
		IModelIntegrationUtil moinIntUtil = EasyMock.createMock(IModelIntegrationUtil.class); 
		expect(featureProvider.getMoinIntegrationUtil()).andStubReturn(moinIntUtil);
		expect(moinIntUtil.getProjectByDiagram(d)).andStubReturn(jpaProject);
		PictogramElement pe = EasyMock.createMock(PictogramElement.class);
		expect(featureProvider.getPictogramElementForBusinessObject(inputJptType)).andStubReturn(pe);
		IDiagramEditor dEd = EasyMock.createMock(IDiagramEditor.class);
		expect(dtp.getDiagramEditor()).andStubReturn(dEd);
		dEd.setPictogramElementForSelection(pe);

		JPAEditorMatchingStrategy str = new JPAEditorMatchingStrategy(featureProvider);
		
		EasyMock.replay(editorRef, d, dtp, pe, dEd, moinIntUtil, dEdInput, ut, input, featureProvider);
		assertTrue(str.matches(editorRef, input));
	}
	
	@Test
	public void testJPAEditorMatchingStrategyWithEntityFileEditorInputFalse() {		
		IEditorReference editorRef = EasyMock.createMock(IEditorReference.class);
		IFileEditorInput input = EasyMock.createMock(IFileEditorInput.class);
		expect(input.getFile()).andStubReturn(entityFile);
		expect(input.getName()).andStubReturn(CODE_GENERATED);
		ICompilationUnit cu = createCompilationUnitFrom(entityFile); 
		
		IJPAEditorUtil ut = EasyMock.createMock(IJPAEditorUtil.class);
		JavaPersistentType inputJptType = getPersistentType(entityFile);
		expect(ut.getJPType(cu)).andStubReturn(inputJptType);
		expect(featureProvider.getJPAEditorUtil()).andStubReturn(ut);
		Diagram d = EasyMock.createMock(Diagram.class);
		IJPADiagramEditorInput dEdInput = EasyMock.createMock(IJPADiagramEditorInput.class);
		try {
			expect(editorRef.getEditorInput()).andStubReturn(dEdInput);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		expect(dEdInput.getDiagram()).andStubReturn(d);
		IDiagramTypeProvider dtp = EasyMock.createMock(IDiagramTypeProvider.class);
		expect(featureProvider.getDiagramTypeProvider()).andStubReturn(dtp);
		IModelIntegrationUtil moinIntUtil = EasyMock.createMock(IModelIntegrationUtil.class); 
		expect(featureProvider.getMoinIntegrationUtil()).andStubReturn(moinIntUtil);
		JpaProject anotherJpaProject = EasyMock.createMock(JpaProject.class);
		expect(moinIntUtil.getProjectByDiagram(d)).andStubReturn(anotherJpaProject);
		PictogramElement pe = EasyMock.createMock(PictogramElement.class);
		expect(featureProvider.getPictogramElementForBusinessObject(inputJptType)).andStubReturn(pe);
		IDiagramEditor dEd = EasyMock.createMock(IDiagramEditor.class);
		expect(dtp.getDiagramEditor()).andStubReturn(dEd);
		dEd.setPictogramElementForSelection(pe);

		JPAEditorMatchingStrategy str = new JPAEditorMatchingStrategy(featureProvider);
		
		EasyMock.replay(editorRef, d, dtp, pe, dEd, moinIntUtil, anotherJpaProject, dEdInput, ut, input, featureProvider);
		assertFalse(str.matches(editorRef, input));
	}	
	
	@Test
	public void testJPAEditorMatchingStrategyWithJPADiagramEditorInputTrue() {		
		IEditorReference editorRef = EasyMock.createMock(IEditorReference.class);
		expect(editorRef.getPartName()).andStubReturn("ProjectName");
		IJPADiagramEditorInput input = EasyMock.createMock(IJPADiagramEditorInput.class);
		expect(input.getProjectName()).andStubReturn("ProjectName");
		JPAEditorMatchingStrategy str = new JPAEditorMatchingStrategy(featureProvider);		
		EasyMock.replay(editorRef, input, featureProvider);
		assertTrue(str.matches(editorRef, input));
	}

	@Test
	public void testJPAEditorMatchingStrategyWithJPADiagramEditorInputFalse() {		
		IEditorReference editorRef = EasyMock.createMock(IEditorReference.class);
		expect(editorRef.getPartName()).andStubReturn("ProjectName");
		IJPADiagramEditorInput input = EasyMock.createMock(IJPADiagramEditorInput.class);
		expect(input.getProjectName()).andStubReturn("DifferentProjectName");
		JPAEditorMatchingStrategy str = new JPAEditorMatchingStrategy(featureProvider);		
		EasyMock.replay(editorRef, input, featureProvider);
		assertFalse(str.matches(editorRef, input));
	}	
	
	
//	@Test
//	public void testJPAEditorMatchingStrategyWithFileEditorInput() {
//		IEditorReference editorRef = EasyMock.createMock(IEditorReference.class);
//		IFileEditorInput input = EasyMock.createMock(IFileEditorInput.class);
//		IJPAEditorUtil ut = EasyMock.createMock(IJPAEditorUtil.class);
//		expect(featureProvider.getJPAEditorUtil()).andStubReturn(ut);
//		JavaPersistentType jpt = EasyMock.createMock(JavaPersistentType.class);
//		ICompilationUnit cu = JavaCore.createCompilationUnitFrom(entityFile);
//		expect(ut.getJPType(cu)).andStubReturn(jpt);
//		PersistenceUnit pu = EasyMock.createMock(PersistenceUnit.class);
//		expect(jpt.getPersistenceUnit()).andStubReturn(pu);
//		expect(pu.getJpaProject()).andStubReturn(jpaProject);
//		expect(input.getFile()).andStubReturn(entityFile);
//		expect(input.getName()).andStubReturn(entityFile.getName());
//		JPAEditorMatchingStrategy str = new JPAEditorMatchingStrategy(featureProvider);		
//		EasyMock.replay(editorRef, input, ut, jpt, pu, featureProvider);
//		assertFalse(str.matches(editorRef, input));
//	}
	
	
	
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
