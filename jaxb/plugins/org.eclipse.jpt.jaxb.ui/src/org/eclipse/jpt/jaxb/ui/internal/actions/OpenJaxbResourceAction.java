/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;


public class OpenJaxbResourceAction
		extends BaseSelectionListenerAction {
	
	private JaxbContextNode selectedNode;
	
	
	public OpenJaxbResourceAction() {
		super("Open");   //$NON-NLS-1$
	}
	
	
	@Override
	public boolean updateSelection(IStructuredSelection s) {
		this.selectedNode = null;
		
		if (! super.updateSelection(s)) {
			return false;
		}
		
		if (s.size() != 1) {
			return false;
		}
		
		if (s.getFirstElement() instanceof JaxbContextRoot) {
			return false;
		}
		
		selectedNode = (JaxbContextNode) s.getFirstElement();
		
		return true;
	}
	
	@Override
	public void run() {
		if (! isEnabled()) {
			return;
		}
		
		IResource resource = this.selectedNode.getResource();
		
		if (resource != null && resource.exists() && resource.getType() == IResource.FILE) {
			openEditor((IFile) resource);
		}
	}
	
	protected void openEditor(IFile file) {
		IEditorRegistry registry = PlatformUI.getWorkbench().getEditorRegistry();
		IContentType contentType = IDE.getContentType(file);
		IEditorDescriptor editorDescriptor = registry.getDefaultEditor(file.getName(), contentType);
		if (editorDescriptor == null) {
			return;  // no editor associated...
		}
		
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		
		try {
			page.openEditor(new FileEditorInput(file), editorDescriptor.getId());
		} 
		catch (Exception e) {
			MessageDialog.openError(page.getWorkbenchWindow().getShell(), JptJaxbUiMessages.Error_openingEditor, e.getMessage());
		}
	}
}
