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
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.feature;

import static org.easymock.EasyMock.aryEq;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IAddBendpointFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.context.IAddBendpointContext;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.feature.AddRelationFeature;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorImageCreator;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelDir;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.relations.IRelation.RelType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IPeServiceUtil;
import org.eclipse.swt.graphics.Point;
import org.junit.Before;
import org.junit.Test;

public class AddRelationFeatureTest {

	private IJPAEditorFeatureProvider featureProvider;

	private IJPAEditorImageCreator imageCreator;
	
	private IJPAEditorUtil jpaEditorUtil;

	private IDiagramTypeProvider diagramProvider;

	private Diagram diagram;

	private IAddConnectionContext context;

	private IPeServiceUtil peUtil;
	
	private IGaService gaUtil;
	
	private IAddBendpointFeature ft;
	
	private Resource resource;
	
	private static String OWNER_AT_NAME = "ownerAttr";		//$NON-NLS-1$
	
	private static String INVERSE_AT_NAME = "inverseAttr";	//$NON-NLS-1$
	
	/**
	 * @throws java.lang.Exception
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		featureProvider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		imageCreator = EasyMock.createMock(IJPAEditorImageCreator.class);
		jpaEditorUtil = EasyMock.createMock(IJPAEditorUtil.class);
		diagramProvider = EasyMock.createMock(IDiagramTypeProvider.class);
		diagram = EasyMock.createMock(Diagram.class);
		context = EasyMock.createMock(IAddConnectionContext.class);
		peUtil = EasyMock.createMock(IPeServiceUtil.class);
		gaUtil = EasyMock.createMock(IGaService.class);
		ft = EasyMock.createMock(IAddBendpointFeature.class);
		
		
		TransactionalEditingDomain defaultTransEditDomain = EasyMock.createMock(TransactionalEditingDomain.class);
		ResourceSet resourceSet = EasyMock.createMock(ResourceSet.class); 
		expect(defaultTransEditDomain.getResourceSet()).andStubReturn(resourceSet);
		EList<Adapter> eAdapters = EasyMock.createMock(EList.class);
		expect(resourceSet.eAdapters()).andStubReturn(eAdapters);
		//IFile diagramFile = project.getFile(diagramFileName.removeFirstSegments(1));
		//IPath diagramFilePath = diagramFile.getFullPath();

		//String pathName = diagramFilePath.toString();
		//URI resourceURI = URI.createPlatformResourceURI(pathName, true);
		resource = EasyMock.createMock(Resource.class); 
		expect(resourceSet.getResource(isA(URI.class), EasyMock.anyBoolean())).andStubReturn(resource);
		
		expect(resource.getResourceSet()).andStubReturn(resourceSet);
		replay(defaultTransEditDomain, resourceSet, resource);
		
	}

	@Test
	public void testCanAddNotAddConnectionContext() {
		IAddContext context = EasyMock.createMock(IAddContext.class);
		verifyCanAdd(context, false);
	}

	@Test
	public void testCanAddNoRelation() throws Exception {
		IAddConnectionContext context = EasyMock.createMock(IAddConnectionContext.class);
		Object notARelation = new Object();
		expect(context.getNewObject()).andReturn(notARelation);
		verifyCanAdd(context, false);
	}

	@Test
	public void testCanAdd() throws Exception {
		IAddConnectionContext context = EasyMock.createMock(IAddConnectionContext.class);
		IRelation relation = EasyMock.createMock(IRelation.class);
		replay(relation);
		expect(context.getNewObject()).andReturn(relation);
		verifyCanAdd(context, true);
	}

	@Test
	public void testGetFeatureProvider() {
		IAddFeature fixture = createFeature();
		assertSame(featureProvider, fixture.getFeatureProvider());
	}

	@SuppressWarnings("unused")
	private void expectManyEndDecorator(double location) {
		ConnectionDecorator d = EasyMock.createMock(ConnectionDecorator.class);
		Polyline pl = EasyMock.createMock(Polyline.class);
		expect(pl.getX()).andReturn(0);
		expect(pl.getY()).andReturn(0);		
		expect(imageCreator.createManyEndDecorator(isA(Connection.class), eq(location))).andReturn(d);
		expect(d.getGraphicsAlgorithm()).andReturn(pl);
		replay(d, pl);		
	}
	
	@SuppressWarnings("unused")
	private void expectManyStartDecorator(double location) {
		ConnectionDecorator d = EasyMock.createMock(ConnectionDecorator.class);
		Polyline pl = EasyMock.createMock(Polyline.class);
		expect(pl.getX()).andReturn(0);
		expect(pl.getY()).andReturn(0);		
		expect(imageCreator.createManyStartDecorator(isA(Connection.class), eq(location))).andReturn(d);
		expect(d.getGraphicsAlgorithm()).andReturn(pl);
		replay(d, pl);		
	}	

	@SuppressWarnings("unused")
	private void expectManyEndWithArrow(final double location) {
		ConnectionDecorator d = EasyMock.createMock(ConnectionDecorator.class);
		Polyline pl = EasyMock.createMock(Polyline.class);
		expect(pl.getX()).andReturn(0);
		expect(pl.getY()).andReturn(0);		
		expect(imageCreator.createManyEndWithArrowDecorator(isA(Connection.class), eq(location))).andReturn(d);
		expect(d.getGraphicsAlgorithm()).andReturn(pl);
		replay(d, pl);		
	}

	@SuppressWarnings("unused")
	private void expectArrow(final double location) {
		ConnectionDecorator d = EasyMock.createMock(ConnectionDecorator.class);
		Polyline pl = EasyMock.createMock(Polyline.class);
		expect(pl.getX()).andReturn(0);
		expect(pl.getY()).andReturn(0);
		expect(imageCreator.createArrowConnectionDecorator(isA(Connection.class), eq(location))).andReturn(d);
		expect(d.getGraphicsAlgorithm()).andReturn(pl);
		replay(d, pl);
	}

	@SuppressWarnings("unused")
	private void expectTextConnectionDecorator(String text, double location) {
		expect(imageCreator.createCardinalityConnectionDecorator(diagram, isA(Connection.class), eq(text), eq(location))).andReturn(
				null);
	}

	@SuppressWarnings("unused")
	private void configureRelation(IRelation relation, final RelDir direction, final RelType type) {
		expect(relation.getOwnerAttributeName()).andStubReturn(OWNER_AT_NAME);
		expect(relation.getInverseAttributeName()).andStubReturn(INVERSE_AT_NAME);
		expect(relation.getRelDir()).andStubReturn(direction);
		expect(relation.getRelType()).andStubReturn(type);
	}

	private void configureProvidersForAdd(IRelation relation) {
		expect(diagramProvider.getDiagram()).andReturn(diagram);
		expect(featureProvider.getDiagramTypeProvider()).andReturn(diagramProvider);
		expect(featureProvider.getPeUtil()).andReturn(peUtil);
		expect(featureProvider.getAddBendpointFeature(isA(IAddBendpointContext.class))).andStubReturn(ft);
		expect(context.getNewObject()).andReturn(relation);

	}

	@SuppressWarnings("unused")
	private void verifyAdd(IRelation relation, boolean expectId) {
		FreeFormConnection connection = configureConnection(relation, expectId);
		replay(connection);
		PictogramElement result = callAdd();
		assertSame(connection, result);
		verify(context);
		verify(featureProvider);
		verify(imageCreator);
		verify(jpaEditorUtil);
		verify(diagram);
		verify(diagramProvider);
		verify(relation);
	}

	private PictogramElement callAdd() {
		IAddFeature fixture = createFeature();
		PictogramElement result = fixture.add(context);
		return result;
	}

	private FreeFormConnection configureConnection(IRelation relation, boolean expectId) {
		final String relationId = "someId"; //$NON-NLS-1$
		if (expectId)
			expect(relation.getId()).andStubReturn(relationId);
		replay(relation);
		configureProvidersForAdd(relation);
		Anchor startAnchor = replayAnchor();
		Anchor endAnchor = replayAnchor();
		expect(context.getSourceAnchor()).andReturn(startAnchor);
		expect(context.getTargetAnchor()).andReturn(endAnchor);
		
		gaUtil.setLocation(isA(Polyline.class), EasyMock.anyInt() , EasyMock.anyInt());
		gaUtil.setLocation(isA(Polyline.class), EasyMock.anyInt() , EasyMock.anyInt());

		FreeFormConnection connection = createConnection(startAnchor, endAnchor);
		expect(peUtil.createFreeFormConnection(diagram)).andReturn(connection);
		expect(imageCreator.createConnectionLine(diagram, connection)).andReturn(null);
		ft.addBendpoint(isA(IAddBendpointContext.class));
		
		List<Point> pts = new LinkedList<Point>();
		Point pt1 = new Point(100, 100);
		//Point pt2 = new Point(200, 200);
		pts.add(pt1); 
		//pts.add(pt2);
		expect(jpaEditorUtil.createBendPointList(connection, false)).andReturn(pts);
		featureProvider.putKeyToBusinessObject(relationId, relation);
		featureProvider.link(eq(connection), aryEq(new IRelation[] { relation }));
		expect(featureProvider.layoutIfPossible(isA(ILayoutContext.class))).andReturn(null);
		return connection;
	}

	private Anchor replayAnchor() {
		Anchor startAnchor = EasyMock.createMock(Anchor.class);
		replay(startAnchor);
		return startAnchor;
	}

	private FreeFormConnection createConnection(Anchor startAnchor, Anchor endAnchor) {
		FreeFormConnection connection = EasyMock.createMock(FreeFormConnection.class);
		connection.setStart(startAnchor);
		connection.setEnd(endAnchor);
		connection.setVisible(true);
		connection.setActive(true);
		
		org.eclipse.graphiti.mm.algorithms.styles.Point pt = EasyMock.createMock(org.eclipse.graphiti.mm.algorithms.styles.Point.class);
		expect(pt.getX()).andStubReturn(100);
		expect(pt.getY()).andStubReturn(100);
		
		org.eclipse.graphiti.mm.algorithms.styles.Point pt1 = EasyMock.createMock(org.eclipse.graphiti.mm.algorithms.styles.Point.class);
		expect(pt1.getX()).andStubReturn(200);
		expect(pt1.getY()).andStubReturn(200);
		
		
		EList<org.eclipse.graphiti.mm.algorithms.styles.Point> pts1 = new BasicInternalEList<org.eclipse.graphiti.mm.algorithms.styles.Point>(org.eclipse.graphiti.mm.algorithms.styles.Point.class);
		pts1.add(pt);	
		pts1.add(pt1);
		expect(connection.getBendpoints()).andStubReturn(pts1);
		replay(pt, pt1);
		return connection;
	}

	private void verifyCanAdd(IAddContext context, final boolean expected) {
		replay(context);
		boolean result = callCanAdd(context);
		assertEquals(expected, result);
		verify(context);
	}

	private boolean callCanAdd(IAddContext context) {
		IAddFeature fixture = createFeature();
		return fixture.canAdd(context);
	}

	private IAddFeature createFeature() {
		replay(featureProvider);
		replay(imageCreator);
		replay(jpaEditorUtil);
		replay(diagramProvider);
		replay(diagram);
		replay(context);
		replay(peUtil);
		replay(gaUtil);
		replay(ft);
		return new AddRelationFeature(featureProvider, imageCreator, jpaEditorUtil);
	}

}
