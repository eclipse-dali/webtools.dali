package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.JPADiagramEditorPlugin;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.modelintegration.util.ModelIntegrationUtil;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

public class ResourceChangeListener implements IResourceChangeListener {

	public void resourceChanged(IResourceChangeEvent event) {
		IMarkerDelta[] markerDeltas = event.findMarkerDeltas(null, true);

		for (IMarkerDelta delta : markerDeltas) {
			if (delta.getResource().getType() != IResource.FILE)
				continue;
			if (delta.getKind() != IResourceDelta.REMOVED) 
				continue;
			final IFile file = (IFile) delta.getResource();
			if (file.exists())
				continue;
			final IProject project = file.getProject();
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
	}
		
}