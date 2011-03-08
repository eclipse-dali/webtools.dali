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

import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.Iterator;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.UpdateAttributeFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.JPACreateFactory;
import org.junit.Before;
import org.junit.Test;

public class UpdateAttributeFeatureTest {
	
	private IJPAEditorFeatureProvider featureProvider;
	private ICustomContext context;
	final String TEST_PROJECT = "Test"+(new Date()).getTime();
	private JpaProject jpaProject = null;
	private JPACreateFactory factory = null;
	private IFile entity;
	
	@Before
	public void setUp() throws Exception {
		factory = JPACreateFactory.instance();
		jpaProject = factory.createJPAProject(TEST_PROJECT + "_" + System.currentTimeMillis());
		assertNotNull(jpaProject);
		entity = factory.createEntity(jpaProject, "org.eclipse.Ent");
		Thread.sleep(2000);
		factory.addAttributes(entity, "att", "java.lang.String", "", "att", false);
	}
	
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void testUpdateAttributeFeature(){
		Text text = EasyMock.createMock(Text.class);
		text.setValue(EasyMock.isA(java.lang.String.class));
		EasyMock.replay(text);
		
		EList<GraphicsAlgorithm> listChildren = new BasicInternalEList<GraphicsAlgorithm>(GraphicsAlgorithm.class);
		
		
		EasyMock.expect(listChildren.get(0)).andReturn(text);
		EasyMock.replay(listChildren);
		
		
		Rectangle rectangle = EasyMock.createMock(Rectangle.class);
		EasyMock.expect(rectangle.getGraphicsAlgorithmChildren()).andReturn(listChildren);
		EasyMock.expect(rectangle.getY()).andReturn(0).times(2);
		EasyMock.replay(rectangle);
		
		Iterator<Shape> iteratorShape = EasyMock.createNiceMock(Iterator.class);
		EasyMock.expect(iteratorShape.hasNext()).andReturn(false);
		EasyMock.replay(iteratorShape);
		
		EList<Shape> listShape = new BasicInternalEList<Shape>(Shape.class);
		
		ContainerShape containerShape = EasyMock.createMock(ContainerShape.class);
		EasyMock.expect(containerShape.getChildren()).andReturn(listShape);
		EasyMock.replay(containerShape);
		
		Shape shape = EasyMock.createMock(Shape.class);
		Resource res = EasyMock.createMock(Resource.class);
		ResourceSet rs = EasyMock.createMock(ResourceSet.class);
		EasyMock.expect(res.getResourceSet()).andStubReturn(rs);
		EList<Adapter> ads = new BasicInternalEList<Adapter>(Adapter.class);
		EasyMock.expect(rs.eAdapters()).andStubReturn(ads);
		EasyMock.expect(shape.eResource()).andStubReturn(res);
		

		
		EasyMock.expect(shape.getGraphicsAlgorithm()).andReturn(rectangle).times(3);
		EasyMock.expect(shape.getContainer()).andReturn(containerShape);
		
		
		EasyMock.replay(shape, res, rs);
		
		context = EasyMock.createMock(ICustomContext.class);
		EasyMock.expect(context.getInnerPictogramElement()).andReturn(shape);
		EasyMock.replay(context);
		
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		EasyMock.expect(featureProvider.getBusinessObjectForPictogramElement(shape)).andReturn(null);
		EasyMock.replay(featureProvider);
		
		UpdateAttributeFeature feature = new UpdateAttributeFeature(featureProvider);
		feature.execute(context);
	}
	
}
