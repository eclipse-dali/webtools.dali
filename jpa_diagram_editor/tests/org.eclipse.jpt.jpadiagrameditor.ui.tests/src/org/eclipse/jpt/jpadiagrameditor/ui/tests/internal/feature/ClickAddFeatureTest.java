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

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.ClickAddAttributeButtonFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util.IEditor;
import org.eclipse.ui.IWorkbenchPartSite;
import org.junit.Before;
import org.junit.Test;

public class ClickAddFeatureTest {
	
	private IJPAEditorFeatureProvider featureProvider;
	private ICreateContext context;
	final String TEST_PROJECT = "Test";
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	JavaPersistentType jpt = null;
	ICompilationUnit cu = null;
	
	@Before
	public void setUp() throws Exception {
		System.setProperty(JPACreateFactory.JPA_JAR_NAME_SYSTEM_PROPERTY, "C:\\lib\\persistence-api-1.0.jar");
		JptJpaCorePlugin.getJpaProjectManager();
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		IFile entity = factory.createEntity(jpaProject, "org.eclipse.Entity1");
		ContainerShape cs = EasyMock.createMock(ContainerShape.class);
		context = EasyMock.createMock(ICreateContext.class);
		expect(context.getTargetContainer()).andStubReturn(cs);
		expect(context.getX()).andStubReturn(0);
		expect(context.getY()).andStubReturn(0);
		expect(context.getWidth()).andStubReturn(100);
		expect(context.getHeight()).andStubReturn(100);
		expect(context.getTargetConnection()).andStubReturn(null);
		IDirectEditingInfo dei = EasyMock.createMock(IDirectEditingInfo.class);
		dei.setActive(true);
		Thread.sleep(2000);
		jpt = (JavaPersistentType)JPACreateFactory.getPersistentType(entity);
		int cnt = 0;
		while ((cnt < 50) && (jpt == null)) {
			Thread.sleep(200);
			jpt = (JavaPersistentType)JPACreateFactory.getPersistentType(entity);
			cnt++;
		}
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(cs)).andStubReturn(jpt);		
		expect(featureProvider.getPictogramElementForBusinessObject(jpt)).andStubReturn(null);
		cu = JavaCore.createCompilationUnitFrom(entity);
		cnt = 0;
		while ((cnt < 50) && (cu == null)) {
			Thread.sleep(200);
			cu = JavaCore.createCompilationUnitFrom(entity);
			cnt++;
		}		
		expect(featureProvider.getCompilationUnit((JavaPersistentType) EasyMock.anyObject())).andReturn(cu).anyTimes();
		expect(featureProvider.addIfPossible(isA(IAddContext.class))).andStubReturn(null);
		expect(featureProvider.getDirectEditingInfo()).andStubReturn(dei);
		
		IJPAEditorUtil ut = EasyMock.createMock(IJPAEditorUtil.class);
		expect(featureProvider.getJPAEditorUtil()).andStubReturn(ut);
		
		IDiagramTypeProvider diagramTypeProvider = EasyMock.createMock(IDiagramTypeProvider.class);
		expect(featureProvider.getDiagramTypeProvider()).andStubReturn(diagramTypeProvider);
		IEditor ed = EasyMock.createMock(IEditor.class);
		expect(diagramTypeProvider.getDiagramEditor()).andStubReturn(ed);
		IWorkbenchPartSite ws = EasyMock.createMock(IWorkbenchPartSite.class);
		expect(ed.getSite()).andStubReturn(ws);
		ut.formatCode((ICompilationUnit)EasyMock.anyObject(), (IWorkbenchPartSite)EasyMock.anyObject());
		
		featureProvider.addAddIgnore(jpt, "attribute1");
		
		replay(featureProvider, cs, context, dei, diagramTypeProvider, ed, ws, ut);
	}
	
	@Test
	public void testClickAddAttributeButtonFeature(){ 
		if ((jpt == null) || (cu == null))
			return;		// The test wasn't setup properly
		ClickAddAttributeButtonFeature feature = new ClickAddAttributeButtonFeature(featureProvider);
		Object[] created = feature.create(context);
		assertNotNull(created[0]);
	}
	
}
