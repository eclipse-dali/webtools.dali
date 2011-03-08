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

import java.util.Date;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.RemoveAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Before;
import org.junit.Test;

public class RemoveAttributeFeatureTest {
	
	private IJPAEditorFeatureProvider featureProvider;
	private IRemoveContext context;
	final String TEST_PROJECT = "Test"+(new Date()).getTime();
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	private IFile entity;
	
	@Before
	public void setUp() throws Exception{
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		entity = factory.createEntity(jpaProject, "org.eclipse.Ent");
		Thread.sleep(2000);
		factory.addAttributes(entity, "att", "java.lang.String", "", "att", false);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRemoveAttributeFeature() {
		GraphicsAlgorithm algorithm = EasyMock.createNiceMock(GraphicsAlgorithm.class);
		replay(algorithm);
		
		EList<Anchor> list = EasyMock.createMock(EList.class);
		Shape shape = EasyMock.createNiceMock(Shape.class);
		
		expect(shape.getGraphicsAlgorithm()).andReturn(algorithm);
		expect(shape.getAnchors()).andReturn(list);
		replay(shape);
		
		//EList<Shape> listShape = new BasicInternalEList<Shape>(Shape.class);
		
		ContainerShape pictogramElement = EasyMock.createMock(ContainerShape.class);
		Resource res = EasyMock.createMock(Resource.class);
		expect(pictogramElement.eResource()).andStubReturn(res);
		
		ResourceSet rs = EasyMock.createMock(ResourceSet.class);
		expect(res.getResourceSet()).andStubReturn(rs);
		EList<Adapter> ead = new BasicInternalEList<Adapter>(Adapter.class); 
		expect(rs.eAdapters()).andStubReturn(ead);

		replay(pictogramElement, res, rs);
		
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(pictogramElement)).andReturn(JPACreateFactory.getPersistentAttribute(entity, "att"));
		expect(featureProvider.decreaseAttribsNum(pictogramElement)).andReturn(0);
		expect(featureProvider.getKeyForBusinessObject(isA(Object.class))).andReturn("");
		expect(featureProvider.remove("")).andReturn(null);
		expect(featureProvider.getRelationRelatedToAttribute(isA(JavaPersistentAttribute.class))).andReturn(null);
		replay(featureProvider);
		
		context = EasyMock.createMock(IRemoveContext.class);
		expect(context.getPictogramElement()).andReturn(pictogramElement);
		replay(context);
		
		ICustomFeature graphicalRemove = EasyMock.createMock(ICustomFeature.class);
		graphicalRemove.execute(isA(ICustomContext.class));
		replay(graphicalRemove);
		
		RemoveAttributeFeature feature = new RemoveAttributeFeature(featureProvider, graphicalRemove);
		feature.preRemove(context);
	}
}
