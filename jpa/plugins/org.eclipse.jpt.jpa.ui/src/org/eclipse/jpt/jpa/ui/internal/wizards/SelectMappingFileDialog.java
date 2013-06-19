/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jpt.common.core.resource.ProjectResourceLocator;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.orm.EmbeddedMappingFileWizard;
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
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class SelectMappingFileDialog
	extends ElementTreeSelectionDialog
{
	protected final IProject project;
	
	protected Label messageLabel;
	protected Tree treeWidget;


	public SelectMappingFileDialog(Shell parent, IProject project) {
		this(parent, project, new WorkbenchLabelProvider(), new WorkbenchContentProvider());
	}

	public SelectMappingFileDialog(Shell parent, IProject project, ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
		super(parent, labelProvider, contentProvider);
		setAllowMultiple(false);
		this.project = project;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		Button newButton = new Button(composite, SWT.PUSH);
		newButton.setText(JptJpaUiMessages.SELECT_MAPPING_FILE_DIALOG_NEW_BUTTON);
		GridData browseButtonData = new GridData(GridData.HORIZONTAL_ALIGN_END);
		newButton.setLayoutData(browseButtonData);		
		newButton.setToolTipText(JptJpaUiMessages.SELECT_MAPPING_FILE_DIALOG_NEW_BUTTON_TOOL_TIP);
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
   
	public String getChosenName() {
		IPath resourcePath = null;
		Object element = getFirstResult();
		if(element instanceof IContainer) {
			resourcePath = ((IContainer) element).getFullPath();
		} else {
			resourcePath = ((IFile) element).getFullPath();
		}
		return this.getProjectResourceLocator().getRuntimePath(resourcePath).toString();
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
			updateStatus(JptJpaUiPlugin.instance().buildOKStatus());
		}
		else {
			updateStatus(JptJpaUiPlugin.instance().buildErrorStatus());
		}
	}
	
	protected void openNewMappingFileWizard() {
		IPath path = EmbeddedMappingFileWizard.createNewMappingFile(new StructuredSelection(this.project));
		updateDialog(path);
	}

	protected void updateDialog(IPath path) {
		if (path != null) {
			//these are disabled if the tree is empty when the dialog is created.
			this.messageLabel.setEnabled(true);
			this.treeWidget.setEnabled(true);
			IFile file = this.getProjectResourceLocator().getPlatformFile(path);
			getTreeViewer().refresh();
			getTreeViewer().setSelection(new StructuredSelection(file), true);
		}
	}
	
	

	protected ProjectResourceLocator getProjectResourceLocator() {
		return (ProjectResourceLocator) this.project.getAdapter(ProjectResourceLocator.class);
	}
}
