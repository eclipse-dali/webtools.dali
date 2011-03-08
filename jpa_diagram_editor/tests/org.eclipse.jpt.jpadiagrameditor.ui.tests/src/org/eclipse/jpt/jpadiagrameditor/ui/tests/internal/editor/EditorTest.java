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
package org.eclipse.jpt.jpadiagrameditor.ui.tests.internal.editor;

import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.eclipse.gef.EditPart;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.internal.selection.JpaSelection;
import org.eclipse.jpt.jpa.ui.internal.selection.JpaSelectionManager;
import org.eclipse.jpt.jpa.ui.internal.selection.JpaSelectionParticipant;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditor;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.IJPAEditorFeatureProvider;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleListener;

@SuppressWarnings("restriction")
public class EditorTest {

	@Test
	public void testJPADiagramEditorCreation() {
		JPADiagramEditor ed = new JPADiagramEditor();
		assertNotNull(ed);
	}
	
	
	@Test
	public void testJPADiagramEditorPlugin() {
		JPADiagramEditorPlugin plugin = new JPADiagramEditorPlugin();
		BundleContext bc = EasyMock.createMock(BundleContext.class);
		Bundle bundle = EasyMock.createMock(Bundle.class);
		EasyMock.expect(bundle.getSymbolicName()).andStubReturn("");
		EasyMock.expect(bc.getBundle()).andStubReturn(bundle);
		bc.addBundleListener(EasyMock.isA(BundleListener.class));
		bc.removeBundleListener(EasyMock.isA(BundleListener.class));
		EasyMock.replay(bc, bundle);
		try {
			plugin.start(bc);
			plugin.stop(bc);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		EasyMock.verify(bc);
	}	
	
	@Test
	public void testSelectionChanged() {
		final IJPAEditorFeatureProvider provider = EasyMock
				.createMock(IJPAEditorFeatureProvider.class);

		IWorkbenchPart part = replayPart();

		PictogramElement pe = EasyMock.createMock(PictogramElement.class);
		replay(pe);
		EditPart editPart = EasyMock.createMock(EditPart.class);
		expect(editPart.getModel()).andReturn(pe);
		replay(editPart);

		JpaStructureNode jpaNodeToBeSelected = EasyMock
				.createMock(JpaStructureNode.class);
		expect(provider.getBusinessObjectForPictogramElement(pe)).andReturn(
				jpaNodeToBeSelected);
		replay(provider);

		// test
		JpaSelectionManager manager = EasyMock
				.createMock(JpaSelectionManager.class);
		manager.select(isA(JpaSelection.class),
				(JpaSelectionParticipant) eq(null));
		replay(manager);

		JPADiagramEditor.ISelectionManagerFactory factory = EasyMock
				.createMock(JPADiagramEditor.ISelectionManagerFactory.class);
		expect(factory.getSelectionManager(isA(IWorkbenchWindow.class)))
				.andReturn(manager);
		replay(factory);
		JPADiagramEditor editor = createEditor(provider, factory);
		// test
		editor.selectionChanged(part, new StructuredSelection(editPart));
		verify(manager);
	}

	private JPADiagramEditor createEditor(final IJPAEditorFeatureProvider provider,
			JPADiagramEditor.ISelectionManagerFactory factory) {
		IWorkbenchPage page = EasyMock.createMock(IWorkbenchPage.class);
		expect(page.isPartVisible(isA(IWorkbenchPart.class)))
				.andReturn(false);
		expect(page.getActiveEditor()).andStubReturn(null);
		replay(page);
		final IWorkbenchPartSite site = EasyMock
				.createMock(IWorkbenchPartSite.class);
		expect(site.getPage()).andStubReturn(page);
		replay(site);

		JPADiagramEditor editor = new JPADiagramEditor(factory) {
			@Override
			public IJPAEditorFeatureProvider getFeatureProvider() {
				return provider;
			}

			public IWorkbenchPartSite getSite() {
				return site;
			}
		};
		return editor;
	}

	private IWorkbenchPart replayPart() {
		IWorkbenchWindow window = EasyMock.createMock(IWorkbenchWindow.class);
		replay(window);
		IWorkbenchPage page = EasyMock.createMock(IWorkbenchPage.class);
		IWorkbenchPartSite site = EasyMock.createMock(IWorkbenchPartSite.class);
		expect(site.getWorkbenchWindow()).andReturn(window);
		expect(site.getPage()).andStubReturn(page);
		replay(site);
		IWorkbenchPart part = EasyMock.createMock(IWorkbenchPart.class);
		expect(part.getSite()).andStubReturn(site);
		expect(page.isPartVisible(isA(IWorkbenchPart.class)))
				.andReturn(false);
		expect(page.getActiveEditor()).andReturn(null);
		replay(page);
		replay(part);
		return part;
	}

	@Test
	public void testSelectionChangedTextSelection() {
		JPADiagramEditor editor = createEditor(null, null);
		editor.selectionChanged(null, new ISelection() {
			public boolean isEmpty() {
				return false;
			}
		});
	}

	@Test
	public void testSelectionChangedNotEditPart() {
		JPADiagramEditor editor = createEditor(null, null);
		editor.selectionChanged(null, new StructuredSelection(new Object()));
	}

}
