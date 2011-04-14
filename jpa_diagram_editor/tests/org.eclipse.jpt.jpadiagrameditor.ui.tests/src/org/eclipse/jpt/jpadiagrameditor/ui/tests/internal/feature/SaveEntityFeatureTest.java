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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.SaveEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JpaArtifactFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Before;
import org.junit.Test;

public class SaveEntityFeatureTest {
	final String TEST_PROJECT = "Test";
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;	
	
	@Before
	public void setUp() throws Exception{
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		Thread.sleep(2000);
	}
	
	@Test
	public void testExecute() throws Exception {
		assertNotNull(jpaProject);	
		IFile customerFile = factory.createEntityInProject(jpaProject.getProject(), new String[]{"com","test"}, "Customer");
		
		IJPAEditorFeatureProvider featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		PictogramElement pe = EasyMock.createMock(PictogramElement.class);

		
		jpaProject.getProject().build(IncrementalProjectBuilder.CLEAN_BUILD, new NullProgressMonitor());

		assertTrue(customerFile.exists());
		JavaResourcePersistentType customerType = jpaProject.getJavaResourcePersistentType("com.test.Customer");
		assertNotNull(customerType);
		
		
		JavaPersistentType t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
		int cnt = 0;
		while ((cnt < 25) && (t1 == null)) {
			Thread.sleep(200);
			t1 = JpaArtifactFactory.instance().getContextPersistentType(jpaProject, customerType.getQualifiedName());
			cnt++;
		}
		if (t1 == null)
			return;
		expect(featureProvider.getBusinessObjectForPictogramElement(pe)).andStubReturn(t1);
		expect(featureProvider.getCompilationUnit(isA(JavaPersistentType.class))).andReturn(createCompilationUnitFrom(customerFile)).anyTimes();

		
		SaveEntityFeature ft = new SaveEntityFeature(featureProvider);
		ICustomContext ctx = EasyMock.createMock(ICustomContext.class);
		
		PictogramElement[] pes = new PictogramElement[1];
		pes[0] = pe;
		expect(ctx.getPictogramElements()).andStubReturn(pes);
		
		replay(featureProvider, ctx, pe);
		ft.execute(ctx);
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
