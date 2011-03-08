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

import org.easymock.EasyMock;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.LayoutJPAEntityFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IGraphicsUpdater;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IPeServiceUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPAEditorConstants;
import org.junit.Test;

public class LayoutEntityFeatureTest {

	@Test
	public void testIfNewlyAddedEntityShapeIsExpanded() {
		IJPAEditorFeatureProvider fp = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		ILayoutContext ctx = EasyMock.createMock(ILayoutContext.class);
		ContainerShape cs = EasyMock.createMock(ContainerShape.class);
		GraphicsAlgorithm ga = EasyMock.createMock(GraphicsAlgorithm.class);
		EasyMock.expect(cs.getGraphicsAlgorithm()).andStubReturn(ga);
		EasyMock.expect(ctx.getPictogramElement()).andStubReturn(cs);
		EasyMock.expect(ga.getHeight()).andStubReturn(80);
		EasyMock.expect(ga.getWidth()).andStubReturn(100);
		//EasyMock.expect(cs.is___Alive()).andStubReturn(true);
		EList<Shape> shList = new BasicInternalEList<Shape>(Shape.class); 
		EasyMock.expect(cs.getChildren()).andStubReturn(shList);
		IPeServiceUtil peUtil = EasyMock.createMock(IPeServiceUtil.class);
		EasyMock.expect(fp.getPeUtil()).andStubReturn(peUtil);
		EasyMock.expect(peUtil.getProperty(cs, JPAEditorConstants.COLLAPSE_FEATURES)).andStubReturn(null);
		EasyMock.expect(peUtil.removeProperty(cs, JPAEditorConstants.COLLAPSE_FEATURES)).andStubReturn(false);
		IGraphicsUpdater gu = EasyMock.createMock(IGraphicsUpdater.class);
		EasyMock.expect(fp.getGraphicsUpdater()).andStubReturn(gu);
		gu.updateEntityHeigth(cs);
		
		EasyMock.replay(fp, ctx, cs, ga, peUtil, gu);		
		LayoutJPAEntityFeature ft = new LayoutJPAEntityFeature(fp);		
		ft.layout(ctx);
	}
	
}
