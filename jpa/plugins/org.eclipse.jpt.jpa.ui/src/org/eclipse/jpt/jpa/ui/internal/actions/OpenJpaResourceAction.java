/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaElementReference;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The selection will be a JPA context node.
 * @see org.eclipse.jpt.jpa.ui.internal.navigator.JpaNavigatorActionProvider
 */
public class OpenJpaResourceAction
	extends BaseSelectionListenerAction
{
	private JpaContextNode selectedNode;


	public OpenJpaResourceAction() {
		super(JptJpaUiMessages.OpenJpaResourceAction_open);
	}

	@Override
	public boolean updateSelection(IStructuredSelection s) {
		this.selectedNode = null;
		return super.updateSelection(s) && this.updateSelection_(s);
	}

	private boolean updateSelection_(IStructuredSelection s) {
		if (s.size() == 1) {
			this.selectedNode = (JpaContextNode) s.getFirstElement();
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		if (this.isEnabled()) {
			this.run_();
		}
	}

	private void run_() {
		IResource resource = this.selectedNode.getResource();
		if ((resource != null) && resource.exists() && (resource.getType() == IResource.FILE)) {
			this.openEditor((IFile) resource);
			if (this.selectedNode instanceof JpaStructureNode) {
				JpaSelectionManager selectionManager = PlatformTools.getAdapter(PlatformUI.getWorkbench(), JpaSelectionManager.class);
				selectionManager.setSelection((JpaStructureNode) this.selectedNode);
			}
		}
		// handle persistent types and attributes in jar files
		else if (this.selectedNode instanceof JavaElementReference) {
			this.openJavaEditor(((JavaElementReference) this.selectedNode).getJavaElement());
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
			MessageDialog.openError(page.getWorkbenchWindow().getShell(), JptJpaUiMessages.OpenJpaResourceAction_error, e.getMessage());
		}
	}

	private void openJavaEditor(IJavaElement element) {
		if (element != null) {
			try {
				JavaUI.openInEditor(element, true, true);
			}
			catch (PartInitException e) {
				JptJpaUiPlugin.instance().logError(e);
			}
			catch (JavaModelException e) {
				JptJpaUiPlugin.instance().logError(e);
			}
		}
	}
}
