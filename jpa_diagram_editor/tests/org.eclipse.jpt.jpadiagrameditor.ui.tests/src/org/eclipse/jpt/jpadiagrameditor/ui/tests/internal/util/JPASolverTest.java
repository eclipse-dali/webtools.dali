/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2013 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.util;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.net.URI;

import org.easymock.EasyMock;
import org.easymock.IArgumentMatcher;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.platform.IDiagramBehavior;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IEclipseFacade;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IJPAEditorUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.JPASolver;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings({"unused", "nls"})
public class JPASolverTest {

	private IEclipseFacade eclipseFacade;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		eclipseFacade = createEclipseFacade();
	}

	@Test
	public void testResourceListenerRegistered() {
		IWorkspace workspace = EasyMock.createMock(IWorkspace.class);
		workspace.addResourceChangeListener(isA(IResourceChangeListener.class), eq(IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE | IResourceChangeEvent.POST_BUILD));
		replay(workspace);
		IEclipseFacade facade = EasyMock.createMock(IEclipseFacade.class);
		expect(facade.getWorkspace()).andStubReturn(workspace);
		replay(facade);
		new JPASolver(facade, null);
		verify(workspace, facade);
	}

	private IFile replayFile() {
		IFile file = EasyMock.createMock(IFile.class);
		URI uri = URI.create("file://project//aaa");
		expect(file.getLocationURI()).andStubReturn(uri);
		expect(file.exists()).andStubReturn(true);
		IProject proj = EasyMock.createMock(IProject.class);
		expect(proj.getType()).andStubReturn(IResource.PROJECT);
		expect(file.getType()).andStubReturn(IResource.FILE);
		expect(file.getFullPath()).andStubReturn(new Path("C:\\project\\aaa"));
		IFile clsPath = EasyMock.createMock(IFile.class); 
		expect(proj.getFile(".classpath")).andStubReturn(clsPath);
		try {
			expect(proj.hasNature("org.eclipse.jdt.core.javanature")).andStubReturn(true);
		} catch (CoreException e) {}
		expect(file.getProject()).andStubReturn(proj);
		replay(file, proj);
		return file;
	}

	private PersistentType createJptForResource(IFile file, String name) {
		PersistentType jpt = EasyMock.createNiceMock(PersistentType.class);
		JpaProject jpaProject = EasyMock.createNiceMock(JpaProject.class);
		TypeMapping m = EasyMock.createNiceMock(TypeMapping.class);
		expect(jpt.getResource()).andStubReturn(file);
		expect(jpt.getJpaProject()).andStubReturn(jpaProject);
		expect(jpt.getMapping()).andStubReturn(m);
		if (name != null)
			expect(jpt.getName()).andStubReturn(name);
		replay(jpt, jpaProject);
		return jpt;
	}

	private IEclipseFacade createEclipseFacade() {
		IEclipseFacade facade = EasyMock.createMock(IEclipseFacade.class);
		return facade;
	}

	private void configureForWorkspace(IEclipseFacade facade) {
		IWorkspace workspace = EasyMock.createMock(IWorkspace.class);
		workspace.addResourceChangeListener(isA(IResourceChangeListener.class), eq(IResourceChangeEvent.POST_BUILD));
		replay(workspace);
		expect(facade.getWorkspace()).andStubReturn(workspace);
	}

	private IJPAEditorFeatureProvider configureRefreshEditorProvider() {
		IDiagramBehavior editor = EasyMock.createMock(IDiagramBehavior.class);
		editor.refresh();
		replay(editor);
		IDiagramTypeProvider diagramProvider = EasyMock.createMock(IDiagramTypeProvider.class);
		expect(diagramProvider.getDiagramBehavior()).andStubReturn(editor);
		replay(diagramProvider);
		IJPAEditorFeatureProvider provider = EasyMock.createMock(IJPAEditorFeatureProvider.class);
		expect(provider.getDiagramTypeProvider()).andStubReturn(diagramProvider);
		return provider;
	}

	private IMarkerDelta replayDelta(IResource resource) {
		IMarkerDelta delta = EasyMock.createMock(IMarkerDelta.class);
		expect(delta.getResource()).andStubReturn(resource);
		replay(delta);
		return delta;
	}

	private JPASolver createSolver(IEclipseFacade facade, IJPAEditorUtil util) {
		return new JPASolver(facade, util);
	}

	public static IResourceChangeListener eqResourceListener(IArgumentMatcher matcher) {
		EasyMock.reportMatcher(matcher);
		return null;
	}

	private IResourceChangeEvent replayEvent(IResource resource) {
		IResourceChangeEvent event = EasyMock.createMock(IResourceChangeEvent.class);
		expect(event.findMarkerDeltas(null, true)).andStubReturn(new IMarkerDelta[] { replayDelta(resource) });
		replay(event);
		return event;
	}

	private IResourceChangeEvent replayEmptyEvent() {
		IResourceChangeEvent event = EasyMock.createMock(IResourceChangeEvent.class);
		expect(event.findMarkerDeltas(null, true)).andStubReturn(new IMarkerDelta[] {});
		replay(event);
		return event;
	}

}
