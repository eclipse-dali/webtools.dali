/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import java.io.File;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;

public class SelectJpaOrmMappingFileDialog extends ElementTreeSelectionDialog
{
	private static final String META_INF = "META-INF";//$NON-NLS-1$
	private static final String EMPTY = "";//$NON-NLS-1$
	private static final char SLASH = '/';

	private String xmlName = EMPTY;


	public SelectJpaOrmMappingFileDialog(Shell parent, ILabelProvider labelProvider, ITreeContentProvider contentProvider) {
		super(parent, labelProvider, contentProvider);			
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
			updateStatus(new Status(IStatus.OK, JptUiPlugin.PLUGIN_ID, ""));
		}
		else {
			updateStatus(new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, ""));
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
}