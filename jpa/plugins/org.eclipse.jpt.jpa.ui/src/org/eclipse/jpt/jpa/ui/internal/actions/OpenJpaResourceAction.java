/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaElementReference;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaSelectionManager;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.part.FileEditorInput;

/**
 * The selection will be a JPA context node.
 * @see org.eclipse.jpt.jpa.ui.internal.navigator.OpenJpaResourceActionProvider
 */
public class OpenJpaResourceAction
	extends BaseSelectionListenerAction
{
	private JpaContextModel selectedNode;


	public OpenJpaResourceAction() {
		super(JptJpaUiMessages.OPEN_JPA_RESOURCE_ACTION_OPEN);
	}

	@Override
	public boolean updateSelection(IStructuredSelection s) {
		this.selectedNode = null;
		return super.updateSelection(s) && this.updateSelection_(s);
	}

	private boolean updateSelection_(IStructuredSelection s) {
		if (s.size() == 1) {
			this.selectedNode = (JpaContextModel) s.getFirstElement();
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
				JpaSelectionManager selectionManager = WorkbenchTools.getAdapter(JpaSelectionManager.class);
				selectionManager.setSelection((JpaStructureNode) this.selectedNode);
			}
		}
		// handle persistent types and attributes in jar files
		else if (this.selectedNode instanceof JavaElementReference) {
			this.openJavaEditor(((JavaElementReference) this.selectedNode).getJavaElement());
		}
	}

	protected void openEditor(IFile file) {
		IEditorDescriptor editorDescriptor = WorkbenchTools.getDefaultEditor(file);
		if (editorDescriptor == null) {
			return;  // no editor associated...
		}

		IWorkbenchPage page = WorkbenchTools.getActivePage();
		if (page == null) {
			return;
		}

		try {
			page.openEditor(new FileEditorInput(file), editorDescriptor.getId());
		} catch (Exception ex) {
			MessageDialog.openError(page.getWorkbenchWindow().getShell(), JptJpaUiMessages.OPEN_JPA_RESOURCE_ACTION_ERROR, ex.getMessage());
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
