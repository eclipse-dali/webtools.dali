/*******************************************************************************
* Copyright (c) 2010, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.wizards.gen;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;

/**
 *  SelectSingleFileView
 *  
 *  Facade class to change accessibility of SelectSingleFileView.
 */
@SuppressWarnings("restriction")
public class SelectSingleFileViewFacade extends org.eclipse.wst.common.ui.internal.viewers.SelectSingleFileView {

	public static interface Listener extends org.eclipse.wst.common.ui.internal.viewers.SelectSingleFileView.Listener
	{}

	public SelectSingleFileViewFacade(IStructuredSelection selection, boolean isFileMandatory) {
		super(selection, isFileMandatory);
	}

	public void addFilterExtensions(String[] filterExtensions) {
		super.addFilterExtensions(filterExtensions);
	}

	public Composite createControl(Composite parent) {
		return super.createControl(parent);
	}

	public IFile getFile() {
		return super.getFile();
	}

	public void resetFilters() {
		super.resetFilters();
	}
	
	public void setVisibleHelper(boolean isVisible) {
		super.setVisibleHelper(isVisible);
	}
	
	public void setDefaultSelection(ISelection selection) {
		super.setDefaultSelection(selection);
	}

	public void setListener(Listener listener) {
		super.setListener(listener);
	}
}
