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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IEclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.junit.Before;
import org.junit.Test;

public class JpaArtifactFactoryTest {
	
	String TEST_PROJECT = "Test";
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	IEclipseFacade eclipseFacade = null;
	public static int cnt = 0;
	
	@Before
	public void setUp() throws Exception {
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		Thread.sleep(2000);
	}
	
	/*
	@Test
	public void testJpaArtifactoryAttributeRelatedMethods() throws Exception {
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
		OneToOneBiDirRelation rel = ft.createRelation(cs1, cs2);
		assertNotNull(rel);
		assertNotNull(rel.getId());
		assertEquals(RelType.ONE_TO_ONE, rel.getRelType());
		assertEquals(RelDir.BI, rel.getRelDir());
		assertNotNull(t1.getAttributeNamed(rel.getOwnerAttributeName()));
		assertNotNull(t2.getAttributeNamed(rel.getInverseAttributeName()));		
		
		JavaPersistentAttribute jpa = t1.getAttributeNamed(rel.getOwnerAttributeName());
		assertTrue(JpaArtifactFactory.instance().hasEntityAnnotation(t1));
		
		//JavaPersistentAttribute jpa1 =  JpaArtifactFactory.instance().getRelatedAttribute(t1, t2);
		//assertNotNull(jpa1);
		//assertSame(jpa1, t2.getAttributeNamed(rel.getInverseAttributeName()));
		
		List<String> ans = JpaArtifactFactory.instance().getAnnotationStrings(jpa);
		assertNotNull(ans);
		assertTrue(ans.size() > 0);		
		assertTrue(ans.contains("@OneToOne"));		
		JpaArtifactFactory.instance().deleteAttribute(t1, rel.getOwnerAttributeName(), featureProvider);
		jpa = t1.getAttributeNamed(rel.getOwnerAttributeName());
		assertNull(jpa);
	}
	*/
	
	@Test 
	public void testEntityListener() throws Exception {
		IJPAEditorFeatureProvider featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());
		
		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
				
		JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		while (t1 == null) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		}		
		ICompilationUnit cu1 = createCompilationUnitFrom(customerFile);		
		expect(featureProvider.getCompilationUnit(t1)).andStubReturn(cu1);
		replay(featureProvider);		

//		Catches problem with multiple events - Eclipse bugzilla bug #259103
		
//		cnt = 0;
//		t1.addListChangeListener(new EntityAttributesChangeListener());
//		JpaArtifactFactory.instance().deleteAttribute(t1, "id", featureProvider);
//		Thread.sleep(10000);
//		assertTrue("The listener is being triggered " + cnt + " times", cnt == 1);
		
	}
	
	public ICompilationUnit createCompilationUnitFrom(IFile file) {
		return JavaCore.createCompilationUnitFrom(file);
	}

	
	
	public class EntityAttributesChangeListener implements ListChangeListener {
		
		public void listChanged(ListChangeEvent event) {
		}

		public void itemsAdded(ListAddEvent arg0) {
		}

		public void itemsMoved(ListMoveEvent arg0) {
		}

		public void itemsRemoved(ListRemoveEvent arg0) {
			cnt++;
		}

		public void itemsReplaced(ListReplaceEvent arg0) {
		}

		public void listCleared(ListClearEvent arg0) {
		}
	};

}
