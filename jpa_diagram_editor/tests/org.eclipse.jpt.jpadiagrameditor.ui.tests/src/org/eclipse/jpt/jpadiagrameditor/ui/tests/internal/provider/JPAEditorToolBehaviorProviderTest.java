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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.provider;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IDoubleClickContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.tb.IDecorator;
import org.eclipse.graphiti.tb.IToolBehaviorProvider;
import org.eclipse.graphiti.tb.ImageDecorator;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.ui.JPAEditorMatchingStrategy;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorToolBehaviorProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IEclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IStaticIDE;
import org.eclipse.ui.PartInitException;
import org.junit.Before;
import org.junit.Test;

public class JPAEditorToolBehaviorProviderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetRenderingDecoratorsPictogramElementError() throws Exception {
		PictogramElement pe = replayPictogramElement(100, 100);
		IMarker marker = replayMarker(IMarker.SEVERITY_ERROR, "Error message");
		JavaPersistentType jpt = replayJptWithMarker(new IMarker[] { marker });
		IDiagramTypeProvider dtp = replayDiagramProvider(pe, jpt);

		IToolBehaviorProvider provider = new JPAEditorToolBehaviorProvider(dtp);
		IDecorator[] decorators = provider.getDecorators(pe);
		assertEquals(1, decorators.length);
		assertEquals("Error message", decorators[0].getMessage());
	}

	@Test
	public void testGetRenderingDecoratorsPictogramElementWarning() throws Exception {
		PictogramElement pe = replayPictogramElement(100, 100);

		IMarker marker = replayMarker(IMarker.SEVERITY_WARNING, "Warning message");
		JavaPersistentType jpt = replayJptWithMarker(new IMarker[] { marker });
		IDiagramTypeProvider dtp = replayDiagramProvider(pe, jpt);

		IToolBehaviorProvider provider = new JPAEditorToolBehaviorProvider(dtp);
		IDecorator[] decorators = provider.getDecorators(pe);
		assertEquals(1, decorators.length);
		assertEquals("Warning message", decorators[0].getMessage());
	}

	@Test
	public void testGetRenderingDecoratorsPictogramElementInfo() throws Exception {
		PictogramElement pe = replayPictogramElement(100, 100);
		IMarker marker = replayMarker(IMarker.SEVERITY_INFO, "Info message");
		JavaPersistentType jpt = replayJptWithMarker(new IMarker[] { marker });
		IDiagramTypeProvider dtp = replayDiagramProvider(pe, jpt);

		IToolBehaviorProvider provider = new JPAEditorToolBehaviorProvider(dtp);
		IDecorator[] decorators = provider.getDecorators(pe);
		assertEquals(1, decorators.length);
		assertEquals("Info message", decorators[0].getMessage());
	}

	@Test
	public void testGetRenderingDecoratorsPictogramElementNoMarkers() throws Exception {
		PictogramElement pe = replayPictogramElement(100, 100);
		JavaPersistentType jpt = replayJptWithMarker(new IMarker[0]);
		IDiagramTypeProvider dtp = replayDiagramProvider(pe, jpt);

		IToolBehaviorProvider provider = new JPAEditorToolBehaviorProvider(dtp);
		IDecorator[] decorators = provider.getDecorators(pe);
		assertEquals(0, decorators.length);
	}

	@Test
	public void testGetRenderingDecoratorsPictogramElementTwoMarkers() throws Exception {
		PictogramElement pe = replayPictogramElement(100, 100);
		IMarker warningMarker = replayMarker(IMarker.SEVERITY_WARNING, "warning");
		IMarker errorMarker = replayMarker(IMarker.SEVERITY_ERROR, "error");
		JavaPersistentType jpt = replayJptWithMarker(new IMarker[] { errorMarker, warningMarker });
		IDiagramTypeProvider dtp = replayDiagramProvider(pe, jpt);
		IToolBehaviorProvider provider = new JPAEditorToolBehaviorProvider(dtp);
		IDecorator[] decorators = provider.getDecorators(pe);
		assertEquals(1, decorators.length);
		assertEquals("error", decorators[0].getMessage());
	}
	
	@Test
	public void testGetRenderingDecoratorsPictogramElementLocation() throws Exception {
		PictogramElement pe = replayPictogramElement(100, 100);
		IMarker marker = replayMarker(IMarker.SEVERITY_ERROR, "Error message");
		JavaPersistentType jpt = replayJptWithMarker(new IMarker[] { marker });
		IDiagramTypeProvider dtp = replayDiagramProvider(pe, jpt);

		IToolBehaviorProvider provider = new JPAEditorToolBehaviorProvider(dtp);
		IDecorator[] decorators = provider.getDecorators(pe);
		assertEquals(1, decorators.length);
		assertEquals(80, ((ImageDecorator) decorators[0]).getX());
		assertEquals(80, ((ImageDecorator) decorators[0]).getY());
	}
	
	@Test
	public void testGetDoubleClickOnJptFeature() throws PartInitException, CoreException {
		PictogramElement pe = replayPictogramElement(100, 100);
		IFile file = replayResource();
		JavaPersistentType jpt = replayJpt(pe, file);
		IDiagramTypeProvider dtp = replayDiagramProvider(pe, jpt);
		
		IEclipseFacade facade = EasyMock.createMock(IEclipseFacade.class);
		IStaticIDE ide = EasyMock.createMock(IStaticIDE.class);
		expect(facade.getIDE()).andStubReturn(ide);
		ide.openEditor(file);
		replay(facade, ide);
		IToolBehaviorProvider provider = new JPAEditorToolBehaviorProvider(dtp, facade);
      
		IDoubleClickContext context = replayDoubleClickContext(pe);
		provider.getDoubleClickFeature(context);
		verify(ide);
	}
	
	@Test
	public void testGetDoubleClickOnJpaFeature() throws Exception {
		PictogramElement pe = replayPictogramElement(100, 100);
		IFile file = replayResource();
		JavaPersistentAttribute jpa = replayJpa(pe, file);
		IDiagramTypeProvider dtp = replayDiagramProviderForJpa(pe, jpa);
		
		IEclipseFacade eclipseFacade = EasyMock.createMock(IEclipseFacade.class);
		IStaticIDE ide = EasyMock.createMock(IStaticIDE.class);
		expect(eclipseFacade.getIDE()).andStubReturn(ide);
		ide.openEditor(file);
		replay(eclipseFacade, ide);
		IToolBehaviorProvider provider = new JPAEditorToolBehaviorProvider(dtp, eclipseFacade);
		
		IDoubleClickContext context = replayDoubleClickContext(pe);
		provider.getDoubleClickFeature(context);
		verify(ide);
	}
	
	private IDiagramTypeProvider replayDiagramProvider(PictogramElement pe, JavaPersistentType jpt) {
		IFeatureProvider featureProvider = EasyMock.createMock(IFeatureProvider.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(pe)).andStubReturn(jpt);
		IDiagramTypeProvider dtp = EasyMock.createMock(IDiagramTypeProvider.class);
		expect(dtp.getFeatureProvider()).andStubReturn(featureProvider);
		replay(dtp, featureProvider);
		return dtp;
	}

	private IMarker replayMarker(int severity, String message) throws CoreException {
		IMarker marker = EasyMock.createMock(IMarker.class);
		expect(marker.getAttribute(IMarker.SEVERITY)).andStubReturn(severity);
		expect(marker.getAttribute(IMarker.MESSAGE)).andStubReturn(message);
		replay(marker);
		return marker;
	}

	private JavaPersistentType replayJptWithMarker(IMarker[] markers) throws CoreException {
		JavaPersistentType jpt = EasyMock.createMock(JavaPersistentType.class);
		IFile file = EasyMock.createMock(IFile.class);
		expect(file.exists()).andReturn(true);
		expect(file.findMarkers(null, true, IResource.DEPTH_INFINITE)).andStubReturn(markers);
		expect(jpt.getResource()).andStubReturn(file);
		replay(file, jpt);
		return jpt;
	}
	
	private IFile replayResource() throws CoreException {
		IFile file = EasyMock.createMock(IFile.class);
		file.setSessionProperty(new QualifiedName(null, JPAEditorMatchingStrategy.DOUBLE_CLICK), "true");
		EasyMock.expectLastCall().asStub();
		expect(file.getType()).andStubReturn(IResource.FILE);
		replay(file);
		return file;
	}
	
	private IDoubleClickContext replayDoubleClickContext(PictogramElement pe){
		IDoubleClickContext context = EasyMock.createMock(IDoubleClickContext.class);
		expect(context.getPictogramElements()).andStubReturn(new PictogramElement[] {pe});
		replay(context);
		return context;
	}
	
	private JavaPersistentType replayJpt(PictogramElement pe, IFile file){
		IFeatureProvider featureProvider = EasyMock.createMock(IFeatureProvider.class);
		JavaPersistentType jpt = EasyMock.createMock(JavaPersistentType.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(pe)).andStubReturn(jpt);
		expect(jpt.getResource()).andReturn(file);
		replay(jpt, featureProvider);
		return jpt;
	}
	
	private JavaPersistentAttribute replayJpa(PictogramElement pe, IFile file){
		IFeatureProvider featureProvider = EasyMock.createMock(IFeatureProvider.class);
		JavaPersistentAttribute jpa = EasyMock.createMock(JavaPersistentAttribute.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(pe)).andStubReturn(jpa);
		expect(jpa.getResource()).andReturn(file);
		replay(jpa, featureProvider);
		return jpa;
	}
	
	private IDiagramTypeProvider replayDiagramProviderForJpa(PictogramElement pe, JavaPersistentAttribute jpa) {
		IFeatureProvider featureProvider = EasyMock.createMock(IFeatureProvider.class);
		expect(featureProvider.getBusinessObjectForPictogramElement(pe)).andStubReturn(jpa);
		IDiagramTypeProvider dtp = EasyMock.createMock(IDiagramTypeProvider.class);
		expect(dtp.getFeatureProvider()).andStubReturn(featureProvider);
		replay(dtp, featureProvider);
		return dtp;
	}

	private PictogramElement replayPictogramElement(int width, int height) {
		PictogramElement pe = EasyMock.createMock(PictogramElement.class);
		GraphicsAlgorithm algo = EasyMock.createMock(GraphicsAlgorithm.class);
		expect(algo.getWidth()).andStubReturn(width);
		expect(algo.getHeight()).andStubReturn(height);
		expect(pe.getGraphicsAlgorithm()).andStubReturn(algo);
		replay(pe, algo);
		return pe;
	}
	
}
