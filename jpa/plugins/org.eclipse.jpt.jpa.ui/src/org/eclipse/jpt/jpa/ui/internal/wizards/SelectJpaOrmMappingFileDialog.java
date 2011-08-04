/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards;

import java.io.File;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.orm.MappingFileWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class SelectJpaOrmMappingFileDialog extends ElementTreeSelectionDialog
{
	private final IProject project;
	
	private Label messageLabel;
	private Tree treeWidget;

	public SelectJpaOrmMappingFileDialog(Shell parent, IProject project, ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
		super(parent, labelProvider, contentProvider);
		setAllowMultiple(false);
		this.project = project;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		Button newButton = new Button(composite, SWT.PUSH);
		newButton.setText(JptUiMessages.SelectJpaOrmMappingFileDialog_newButton);
		GridData browseButtonData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		newButton.setLayoutData(browseButtonData);		
		newButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				openNewMappingFileWizard();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		return composite;
	}

	@Override
	protected Label createMessageArea(Composite composite) {
		this.messageLabel = super.createMessageArea(composite);
		return this.messageLabel;
	}

	@Override
	protected TreeViewer createTreeViewer(Composite parent) {
		TreeViewer treeViewer = super.createTreeViewer(parent);
        this.treeWidget = treeViewer.getTree();
        return treeViewer;
	}
    
	/**
	 * @param project
	 * @return the runtime path of the chosen element
	 */
	public String getChosenName() {
		IPath resourcePath = null;
		Object element = getFirstResult();
		if(element instanceof IContainer) {
			resourcePath = ((IContainer) element).getFullPath();
		} else {
			resourcePath = ((IFile) element).getFullPath();
		}
		String runtimePath = JptCommonCorePlugin.getResourceLocator(project).getRuntimePath(project, resourcePath).toOSString();
		return runtimePath.replace(File.separatorChar, '/');
	}

	@Override
    /*
     * @see ElementTreeSelectionDialog#updateOKStatus(Composite)
     */
	protected void updateOKStatus() {
		super.updateOKStatus();
		TreeSelection selection = (TreeSelection)getTreeViewer().getSelection();
		IResource selectedResource = (IResource) selection.getFirstElement();
		if (selectedResource instanceof IFile) {
			updateStatus(new Status(IStatus.OK, JptJpaUiPlugin.PLUGIN_ID, ""));
		}
		else {
			updateStatus(new Status(IStatus.ERROR, JptJpaUiPlugin.PLUGIN_ID, ""));
		}
	}
	
	private void openNewMappingFileWizard() {
		IPath path = MappingFileWizard.createNewMappingFile(new StructuredSelection(this.project), null);
		if (path != null) {
			//these are disabled if the tree is empty when the dialog is created.
			this.messageLabel.setEnabled(true);
			this.treeWidget.setEnabled(true);
			IFile file = JptCommonCorePlugin.getPlatformFile(this.project, path);
			getTreeViewer().setSelection(new StructuredSelection(file), true);
		}
	}
}