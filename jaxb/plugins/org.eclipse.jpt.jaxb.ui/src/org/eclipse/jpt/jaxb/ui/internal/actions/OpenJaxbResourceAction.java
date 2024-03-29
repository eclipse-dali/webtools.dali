/*******************************************************************************
 *  Copyright (c) 2011, 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
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
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.JaxbContextRoot;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiMessages;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
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
		IEditorDescriptor editorDescriptor = WorkbenchTools.getDefaultEditor(file);
		if (editorDescriptor == null) {
			return;  // no editor associated...
		}
		
		IWorkbenchPage page = WorkbenchTools.getActivePage();
		
		try {
			if (page != null) {
				page.openEditor(new FileEditorInput(file), editorDescriptor.getId());
			}
		} 
		catch (Exception e) {
			MessageDialog.openError(page.getWorkbenchWindow().getShell(), JptJaxbUiMessages.ERROR_OPENING_EDITOR, e.getMessage());
		}
	}
}
