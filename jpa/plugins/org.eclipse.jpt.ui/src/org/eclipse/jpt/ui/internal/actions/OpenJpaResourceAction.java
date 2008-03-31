/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.selection.DefaultJpaSelection;
import org.eclipse.jpt.ui.internal.selection.JpaSelectionManager;
import org.eclipse.jpt.ui.internal.selection.SelectionManagerFactory;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

public class OpenJpaResourceAction extends BaseSelectionListenerAction
{
	private JpaContextNode selectedNode;
	
	
	public OpenJpaResourceAction() {
		super("Open");   //$NON-NLS-1$
	}
	
	
	public boolean updateSelection(IStructuredSelection s) {
		selectedNode = null;
		
		if (! super.updateSelection(s)) {
			return false;
		}
		
		if (s.size() != 1) {
			return false;
		}
		
		selectedNode = (JpaContextNode) s.getFirstElement();

		return true;
	}
	
	@Override
	public void run() {
		if (! isEnabled()) {
			return;
		}
		
		IResource resource = selectedNode.getResource();
		
		if (resource != null && resource.exists() && resource.getType() == IResource.FILE) {
			openEditor((IFile) resource);
				
			
			if (selectedNode instanceof JpaStructureNode) {
				JpaSelectionManager selectionManager =
					SelectionManagerFactory.getSelectionManager(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
				selectionManager.select(new DefaultJpaSelection((JpaStructureNode) selectedNode));
			}
		}
	}
	
	protected void openEditor(IFile file) {
		IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
		IContentType contentType = IDE.getContentType(file);
		IEditorDescriptor editorDescriptor = registry.getDefaultEditor(file.getName(), contentType);
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		try {
			page.openEditor(new FileEditorInput(file), editorDescriptor.getId());
		} 
		catch (Exception e) {
			MessageDialog.openError(page.getWorkbenchWindow().getShell(), JptUiMessages.Error_openingEditor, e.getMessage());
		}
	}
}
