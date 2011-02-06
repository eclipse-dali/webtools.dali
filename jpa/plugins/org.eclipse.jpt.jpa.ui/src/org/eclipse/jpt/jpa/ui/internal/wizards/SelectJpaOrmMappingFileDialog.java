/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
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
	private static final String META_INF = "META-INF";//$NON-NLS-1$
	private static final String EMPTY = "";//$NON-NLS-1$
	private static final char SLASH = '/';

	private String xmlName = EMPTY;

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
     * @return the name of the alternative mapping XML
     */
    public String getChosenName() {
    	String result = EMPTY;
		Object element = getFirstResult();
		if (element instanceof IContainer) {
			IContainer container = (IContainer) element;
			result = container.getFullPath().toString() + File.separatorChar + this.xmlName;					
		} else {
			IFile f = (IFile) element;
			result = f.getFullPath().toOSString();
		}
		result = removeRedundantSegmentFromName(result);
		return result;
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
	
	/** 
	 * This method is for internal purposes only
	 * @param input non formated path to the mapping XML
	 * @return the formated path to the mapping XML
	 */
	private String removeRedundantSegmentFromName(String input) {
		String output = input.substring(input.indexOf(META_INF));			 
		output = output.replace(File.separatorChar, SLASH);
		return output;
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