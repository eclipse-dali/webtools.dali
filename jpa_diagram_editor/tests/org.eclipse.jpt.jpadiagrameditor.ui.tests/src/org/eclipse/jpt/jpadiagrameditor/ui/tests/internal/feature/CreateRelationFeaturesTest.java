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
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateManyToManyBiDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateManyToManyUniDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateManyToOneBiDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateManyToOneUniDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateOneToManyUniDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateOneToOneBiDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.CreateOneToOneUniDirRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelDir;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToOneBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.ManyToOneUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToManyUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToOneBiDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.OneToOneUniDirRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IEclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("restriction")
public class CreateRelationFeaturesTest {
	final String TEST_PROJECT = "Test";
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	IEclipseFacade eclipseFacade = null;
	
	@Before
	public void setUp() throws Exception {
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		Thread.sleep(2000);
	}
	
	@Test
	public void testCreateOneToOneUniDirRelationFeature() throws Exception {
		IJPAEditorFeatureProvider featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		IFile addressFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		assertTrue(addressFile.exists());
		JavaResourcePersistentType addressType = jpaProject.getJavaResourcePersistentType("com.test.Address");
		assertNotNull(addressType);
		
		JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		JavaPersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		}

		
		CreateOneToOneUniDirRelationFeature ft = new CreateOneToOneUniDirRelationFeature(featureProvider);
		ICreateConnectionContext ctx = EasyMock.createMock(ICreateConnectionContext.class);
		
		ContainerShape cs1 = EasyMock.createMock(ContainerShape.class);
		ContainerShape cs2 = EasyMock.createMock(ContainerShape.class);
		
		expect(featureProvider.getBusinessObjectForPictogramElement(cs1)).andStubReturn(t1);
		expect(featureProvider.getBusinessObjectForPictogramElement(cs2)).andStubReturn(t2);
		expect(featureProvider.getPictogramElementForBusinessObject(t1)).andStubReturn(cs1);
		expect(featureProvider.getPictogramElementForBusinessObject(t2)).andStubReturn(cs2);
		
		Anchor a1 = EasyMock.createMock(Anchor.class);
		Anchor a2 = EasyMock.createMock(Anchor.class);
		expect(a1.getParent()).andStubReturn(cs1);
		expect(a2.getParent()).andStubReturn(cs2);
		expect(ctx.getSourceAnchor()).andStubReturn(a1);
		expect(ctx.getTargetAnchor()).andStubReturn(a2);

		ICompilationUnit cu1 = createCompilationUnitFrom(customerFile);
		cu1.becomeWorkingCopy(new NullProgressMonitor());
		ICompilationUnit cu2 = createCompilationUnitFrom(addressFile);
		cu2.becomeWorkingCopy(new NullProgressMonitor());
		
		expect(featureProvider.getCompilationUnit(t1)).andStubReturn(cu1);
		expect(featureProvider.getCompilationUnit(t2)).andStubReturn(cu2);
		
		Connection conn = EasyMock.createMock(Connection.class);
		expect(featureProvider.addIfPossible((IAddContext)EasyMock.anyObject())).andStubReturn(conn);
		replay(featureProvider, a1, a2, cs1, cs2, ctx);
		ft.create(ctx);
		OneToOneUniDirRelation rel = ft.createRelation(featureProvider, cs1, cs2);
		assertNotNull(rel);
		assertNotNull(rel.getId());
		assertEquals(RelType.ONE_TO_ONE, rel.getRelType());
		assertEquals(RelDir.UNI, rel.getRelDir());		
		assertSame(rel.getOwner(), t1);
		assertSame(rel.getInverse(), t2);
		assertNotNull(t1.getAttributeNamed(rel.getOwnerAttributeName()));
		assertTrue(JpaArtifactFactory.instance().isMethodAnnotated(t1));
		JavaPersistentAttribute jpa = t1.getAttributeNamed("id");
		assertFalse(JpaArtifactFactory.instance().isRelationAnnotated(jpa));
		jpa = t1.getAttributeNamed(rel.getOwnerAttributeName());
		assertTrue(JpaArtifactFactory.instance().isRelationAnnotated(jpa));
	}
	
	@Test
	public void testCreateOneToOneBiDirRelationFeature() throws Exception {
		IJPAEditorFeatureProvider featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		IFile addressFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		assertTrue(addressFile.exists());
		JavaResourcePersistentType addressType = jpaProject.getJavaResourcePersistentType("com.test.Address");
		assertNotNull(addressType);
		
		JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		JavaPersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		}

		
		CreateOneToOneBiDirRelationFeature ft = new CreateOneToOneBiDirRelationFeature(featureProvider);
		ICreateConnectionContext ctx = EasyMock.createMock(ICreateConnectionContext.class);
		
		ContainerShape cs1 = EasyMock.createMock(ContainerShape.class);
		ContainerShape cs2 = EasyMock.createMock(ContainerShape.class);
		
		expect(featureProvider.getBusinessObjectForPictogramElement(cs1)).andStubReturn(t1);
		expect(featureProvider.getBusinessObjectForPictogramElement(cs2)).andStubReturn(t2);
		expect(featureProvider.getPictogramElementForBusinessObject(t1)).andStubReturn(cs1);
		expect(featureProvider.getPictogramElementForBusinessObject(t2)).andStubReturn(cs2);
		
		Anchor a1 = EasyMock.createMock(Anchor.class);
		Anchor a2 = EasyMock.createMock(Anchor.class);
		expect(a1.getParent()).andStubReturn(cs1);
		expect(a2.getParent()).andStubReturn(cs2);
		expect(ctx.getSourceAnchor()).andStubReturn(a1);
		expect(ctx.getTargetAnchor()).andStubReturn(a2);

		ICompilationUnit cu1 = createCompilationUnitFrom(customerFile);
		ICompilationUnit cu2 = createCompilationUnitFrom(addressFile);
		
		expect(featureProvider.getCompilationUnit(t1)).andStubReturn(cu1);
		expect(featureProvider.getCompilationUnit(t2)).andStubReturn(cu2);
		
		Connection conn = EasyMock.createMock(Connection.class);
		expect(featureProvider.addIfPossible((IAddContext)EasyMock.anyObject())).andStubReturn(conn);
		replay(featureProvider, a1, a2, cs1, cs2, ctx);
		ft.create(ctx);
		OneToOneBiDirRelation rel = ft.createRelation(featureProvider, cs1, cs2);
		assertNotNull(rel);
		assertNotNull(rel.getId());
		assertEquals(RelType.ONE_TO_ONE, rel.getRelType());
		assertEquals(RelDir.BI, rel.getRelDir());
		assertNotNull(t1.getAttributeNamed(rel.getOwnerAttributeName()));
		assertNotNull(t2.getAttributeNamed(rel.getInverseAttributeName()));		
	}
	
	
	@Test
	public void testCreateManyToOneUniDirRelationFeature() throws Exception {
		IJPAEditorFeatureProvider featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		IFile addressFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		assertTrue(addressFile.exists());
		JavaResourcePersistentType addressType = jpaProject.getJavaResourcePersistentType("com.test.Address");
		assertNotNull(addressType);
		
		JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		JavaPersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		}

		
		CreateManyToOneUniDirRelationFeature ft = new CreateManyToOneUniDirRelationFeature(featureProvider);
		ICreateConnectionContext ctx = EasyMock.createMock(ICreateConnectionContext.class);
		
		ContainerShape cs1 = EasyMock.createMock(ContainerShape.class);
		ContainerShape cs2 = EasyMock.createMock(ContainerShape.class);
		
		expect(featureProvider.getBusinessObjectForPictogramElement(cs1)).andStubReturn(t1);
		expect(featureProvider.getBusinessObjectForPictogramElement(cs2)).andStubReturn(t2);
		expect(featureProvider.getPictogramElementForBusinessObject(t1)).andStubReturn(cs1);
		expect(featureProvider.getPictogramElementForBusinessObject(t2)).andStubReturn(cs2);
		
		Anchor a1 = EasyMock.createMock(Anchor.class);
		Anchor a2 = EasyMock.createMock(Anchor.class);
		expect(a1.getParent()).andStubReturn(cs1);
		expect(a2.getParent()).andStubReturn(cs2);
		expect(ctx.getSourceAnchor()).andStubReturn(a1);
		expect(ctx.getTargetAnchor()).andStubReturn(a2);

		ICompilationUnit cu1 = createCompilationUnitFrom(customerFile);
		ICompilationUnit cu2 = createCompilationUnitFrom(addressFile);
		
		expect(featureProvider.getCompilationUnit(t1)).andStubReturn(cu1);
		expect(featureProvider.getCompilationUnit(t2)).andStubReturn(cu2);
		
		Connection conn = EasyMock.createMock(Connection.class);
		expect(featureProvider.addIfPossible((IAddContext)EasyMock.anyObject())).andStubReturn(conn);
		replay(featureProvider, a1, a2, cs1, cs2, ctx);
		ft.create(ctx);
		ManyToOneUniDirRelation rel = ft.createRelation(featureProvider, cs1, cs2);
		assertNotNull(rel);
		assertNotNull(rel.getId());
		assertEquals(RelType.MANY_TO_ONE, rel.getRelType());
		assertEquals(RelDir.UNI, rel.getRelDir());
		assertNotNull(t1.getAttributeNamed(rel.getOwnerAttributeName()));
	}
	
	
	@Test
	public void testCreateManyToOneBiDirRelationFeature() throws Exception {
		IJPAEditorFeatureProvider featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		IFile addressFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		assertTrue(addressFile.exists());
		JavaResourcePersistentType addressType = jpaProject.getJavaResourcePersistentType("com.test.Address");
		assertNotNull(addressType);
		
		JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		JavaPersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		}

		
		CreateManyToOneBiDirRelationFeature ft = new CreateManyToOneBiDirRelationFeature(featureProvider);
		ICreateConnectionContext ctx = EasyMock.createMock(ICreateConnectionContext.class);
		
		ContainerShape cs1 = EasyMock.createMock(ContainerShape.class);
		ContainerShape cs2 = EasyMock.createMock(ContainerShape.class);
		
		expect(featureProvider.getBusinessObjectForPictogramElement(cs1)).andStubReturn(t1);
		expect(featureProvider.getBusinessObjectForPictogramElement(cs2)).andStubReturn(t2);
		expect(featureProvider.getPictogramElementForBusinessObject(t1)).andStubReturn(cs1);
		expect(featureProvider.getPictogramElementForBusinessObject(t2)).andStubReturn(cs2);
		
		Anchor a1 = EasyMock.createMock(Anchor.class);
		Anchor a2 = EasyMock.createMock(Anchor.class);
		expect(a1.getParent()).andStubReturn(cs1);
		expect(a2.getParent()).andStubReturn(cs2);
		expect(ctx.getSourceAnchor()).andStubReturn(a1);
		expect(ctx.getTargetAnchor()).andStubReturn(a2);

		ICompilationUnit cu1 = createCompilationUnitFrom(customerFile);
		ICompilationUnit cu2 = createCompilationUnitFrom(addressFile);
		
		expect(featureProvider.getCompilationUnit(t1)).andStubReturn(cu1);
		expect(featureProvider.getCompilationUnit(t2)).andStubReturn(cu2);
		
		Connection conn = EasyMock.createMock(Connection.class);
		expect(featureProvider.addIfPossible((IAddContext)EasyMock.anyObject())).andStubReturn(conn);
		replay(featureProvider, a1, a2, cs1, cs2, ctx);
		ft.create(ctx);
		ManyToOneBiDirRelation rel = ft.createRelation(featureProvider, cs1, cs2);
		assertNotNull(rel);
		assertNotNull(rel.getId());
		assertEquals(RelType.MANY_TO_ONE, rel.getRelType());
		assertEquals(RelDir.BI, rel.getRelDir());
		assertNotNull(t1.getAttributeNamed(rel.getOwnerAttributeName()));
		assertNotNull(t2.getAttributeNamed(rel.getInverseAttributeName()));		
	}
	
	@Test
	public void testCreateOneToManyUniDirRelationFeature() throws Exception {
		IJPAEditorFeatureProvider featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		IFile addressFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		assertTrue(addressFile.exists());
		JavaResourcePersistentType addressType = jpaProject.getJavaResourcePersistentType("com.test.Address");
		assertNotNull(addressType);
		
		JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		JavaPersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		}

		
		CreateOneToManyUniDirRelationFeature ft = new CreateOneToManyUniDirRelationFeature(featureProvider);
		ICreateConnectionContext ctx = EasyMock.createMock(ICreateConnectionContext.class);
		
		ContainerShape cs1 = EasyMock.createMock(ContainerShape.class);
		ContainerShape cs2 = EasyMock.createMock(ContainerShape.class);
		
		expect(featureProvider.getBusinessObjectForPictogramElement(cs1)).andStubReturn(t1);
		expect(featureProvider.getBusinessObjectForPictogramElement(cs2)).andStubReturn(t2);
		expect(featureProvider.getPictogramElementForBusinessObject(t1)).andStubReturn(cs1);
		expect(featureProvider.getPictogramElementForBusinessObject(t2)).andStubReturn(cs2);
		
		Anchor a1 = EasyMock.createMock(Anchor.class);
		Anchor a2 = EasyMock.createMock(Anchor.class);
		expect(a1.getParent()).andStubReturn(cs1);
		expect(a2.getParent()).andStubReturn(cs2);
		expect(ctx.getSourceAnchor()).andStubReturn(a1);
		expect(ctx.getTargetAnchor()).andStubReturn(a2);

		ICompilationUnit cu1 = createCompilationUnitFrom(customerFile);
		ICompilationUnit cu2 = createCompilationUnitFrom(addressFile);
		
		expect(featureProvider.getCompilationUnit(t1)).andStubReturn(cu1);
		expect(featureProvider.getCompilationUnit(t2)).andStubReturn(cu2);
		
		Connection conn = EasyMock.createMock(Connection.class);
		expect(featureProvider.addIfPossible((IAddContext)EasyMock.anyObject())).andStubReturn(conn);
		replay(featureProvider, a1, a2, cs1, cs2, ctx);
		ft.create(ctx);
		OneToManyUniDirRelation rel = ft.createRelation(featureProvider, cs1, cs2);
		assertNotNull(rel);
		assertNotNull(rel.getId());
		assertEquals(RelType.ONE_TO_MANY, rel.getRelType());
		assertEquals(RelDir.UNI, rel.getRelDir());	
		assertNotNull(t1.getAttributeNamed(rel.getOwnerAttributeName()));
	}
	
	@Test
	public void testCreateManyToManyUniDirRelationFeature() throws Exception {
		IJPAEditorFeatureProvider featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		IFile addressFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		assertTrue(addressFile.exists());
		JavaResourcePersistentType addressType = jpaProject.getJavaResourcePersistentType("com.test.Address");
		assertNotNull(addressType);
		
		JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		JavaPersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		}

		
		CreateManyToManyUniDirRelationFeature ft = new CreateManyToManyUniDirRelationFeature(featureProvider);
		ICreateConnectionContext ctx = EasyMock.createMock(ICreateConnectionContext.class);
		
		ContainerShape cs1 = EasyMock.createMock(ContainerShape.class);
		ContainerShape cs2 = EasyMock.createMock(ContainerShape.class);
		
		expect(featureProvider.getBusinessObjectForPictogramElement(cs1)).andStubReturn(t1);
		expect(featureProvider.getBusinessObjectForPictogramElement(cs2)).andStubReturn(t2);
		expect(featureProvider.getPictogramElementForBusinessObject(t1)).andStubReturn(cs1);
		expect(featureProvider.getPictogramElementForBusinessObject(t2)).andStubReturn(cs2);
		
		Anchor a1 = EasyMock.createMock(Anchor.class);
		Anchor a2 = EasyMock.createMock(Anchor.class);
		expect(a1.getParent()).andStubReturn(cs1);
		expect(a2.getParent()).andStubReturn(cs2);
		expect(ctx.getSourceAnchor()).andStubReturn(a1);
		expect(ctx.getTargetAnchor()).andStubReturn(a2);

		ICompilationUnit cu1 = createCompilationUnitFrom(customerFile);
		ICompilationUnit cu2 = createCompilationUnitFrom(addressFile);
		
		expect(featureProvider.getCompilationUnit(t1)).andStubReturn(cu1);
		expect(featureProvider.getCompilationUnit(t2)).andStubReturn(cu2);
		
		Connection conn = EasyMock.createMock(Connection.class);
		expect(featureProvider.addIfPossible((IAddContext)EasyMock.anyObject())).andStubReturn(conn);
		replay(featureProvider, a1, a2, cs1, cs2, ctx);
		ft.create(ctx);
		ManyToManyUniDirRelation rel = ft.createRelation(featureProvider, cs1, cs2);
		assertNotNull(rel);
		assertNotNull(rel.getId());
		assertEquals(RelType.MANY_TO_MANY, rel.getRelType());
		assertEquals(RelDir.UNI, rel.getRelDir());	
		assertNotNull(t1.getAttributeNamed(rel.getOwnerAttributeName()));
	}
	
	@Test
	public void testCreateManyToManyBiDirRelationFeature() throws Exception {
		IJPAEditorFeatureProvider featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		IFile addressFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Address");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		assertTrue(addressFile.exists());
		JavaResourcePersistentType addressType = jpaProject.getJavaResourcePersistentType("com.test.Address");
		assertNotNull(addressType);
		
		JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}
		JavaPersistentType t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		while (t2 == null) {
			Thread.sleep(200);
			t2 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, addressType.getQualifiedName());
		}

		
		CreateManyToManyBiDirRelationFeature ft = new CreateManyToManyBiDirRelationFeature(featureProvider);
		ICreateConnectionContext ctx = EasyMock.createMock(ICreateConnectionContext.class);
		
		ContainerShape cs1 = EasyMock.createMock(ContainerShape.class);
		ContainerShape cs2 = EasyMock.createMock(ContainerShape.class);
		
		expect(featureProvider.getBusinessObjectForPictogramElement(cs1)).andStubReturn(t1);
		expect(featureProvider.getBusinessObjectForPictogramElement(cs2)).andStubReturn(t2);
		expect(featureProvider.getPictogramElementForBusinessObject(t1)).andStubReturn(cs1);
		expect(featureProvider.getPictogramElementForBusinessObject(t2)).andStubReturn(cs2);
		
		Anchor a1 = EasyMock.createMock(Anchor.class);
		Anchor a2 = EasyMock.createMock(Anchor.class);
		expect(a1.getParent()).andStubReturn(cs1);
		expect(a2.getParent()).andStubReturn(cs2);
		expect(ctx.getSourceAnchor()).andStubReturn(a1);
		expect(ctx.getTargetAnchor()).andStubReturn(a2);

		ICompilationUnit cu1 = createCompilationUnitFrom(customerFile);
		ICompilationUnit cu2 = createCompilationUnitFrom(addressFile);
		
		expect(featureProvider.getCompilationUnit(t1)).andStubReturn(cu1);
		expect(featureProvider.getCompilationUnit(t2)).andStubReturn(cu2);
		
		Connection conn = EasyMock.createMock(Connection.class);
		expect(featureProvider.addIfPossible((IAddContext)EasyMock.anyObject())).andStubReturn(conn);
		replay(featureProvider, a1, a2, cs1, cs2, ctx);
		ft.create(ctx);
		ManyToManyBiDirRelation rel = ft.createRelation(featureProvider, cs1, cs2);
		assertNotNull(rel);
		assertNotNull(rel.getId());
		assertEquals(RelType.MANY_TO_MANY, rel.getRelType());
		assertEquals(RelDir.BI, rel.getRelDir());	
		assertNotNull(t1.getAttributeNamed(rel.getOwnerAttributeName()));
		assertNotNull(t2.getAttributeNamed(rel.getInverseAttributeName()));		
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

}
