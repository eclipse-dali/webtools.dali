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
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.provider.JPAEditorDiagramTypeProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class ResourceChangeListener implements IResourceChangeListener {

	public void resourceChanged(IResourceChangeEvent event) {
		IMarkerDelta[] markerDeltas = event.findMarkerDeltas(null, true);
		IProject pr = null;
		for (IMarkerDelta delta : markerDeltas) {
			int resType = delta.getResource().getType();
			if (resType == IResource.PROJECT) {
				pr = (IProject)delta.getResource();
				continue;
			}
			if (resType != IResource.FILE)
				continue;
			final IFile file = (IFile) delta.getResource();
			pr = file.getProject();
			if (file.exists())
				continue;
			if (delta.getKind() != IResourceDelta.REMOVED) 
				continue;
			final IProject project = file.getProject();
			pr = project;
			final String name = project.getName();
			if (file.getFullPath().equals(ModelIntegrationUtil.getDiagramXMLFullPath(name))) {
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					public void run() {
						IWorkbenchPage workbenchPage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IEditorReference[] editorRefs = workbenchPage.getEditorReferences();
						for (IEditorReference editorRef : editorRefs) {
							if(!JPADiagramEditorPlugin.PLUGIN_ID.equals(editorRef.getId())) 
								continue;
							if (name.equals(editorRef.getName())) 
								continue;							
							IEditorPart editor = editorRef.getEditor(false);
							if (editor == null)
								continue;
							workbenchPage.closeEditor(editor, false);
							break;
						}
					}
				});
				
			}
		}	
		
		if (pr == null)
			return;
		rearrangeIsARelations(pr);

	}
	
	private void rearrangeIsARelations(IProject pr) {
		if (pr == null) return;
		final Diagram d = ModelIntegrationUtil.getDiagramByProject(pr);
		if (d == null) return;
		final JPAEditorDiagramTypeProvider provider = ModelIntegrationUtil.getProviderByDiagram(d.getName());
		if (provider == null)
			return;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				JpaArtifactFactory.instance().rearrangeIsARelationsInTransaction(provider.getFeatureProvider());
			}
		});
		
	}
		
}