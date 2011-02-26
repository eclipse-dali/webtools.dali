/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 * Code originate from org.eclipse.wst.xml.ui.internal.dialogs
 *******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.wizards.gen;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jpt.dbws.eclipselink.ui.JptDbwsUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalog;


public class SelectFileOrXMLCatalogIdPanel extends Composite implements SelectionListener {

	public interface PanelListener {
		void completionStateChanged();
	}

	protected PanelListener listener;
	protected PageBook pageBook;

	protected SelectFilePanel selectFilePanel;
	protected SelectXMLCatalogIdPanel selectXMLCatalogIdPanel;

	// ********** constructor **********

	public SelectFileOrXMLCatalogIdPanel(Composite parent, IStructuredSelection selection) {
		super(parent, SWT.NONE);

		// container group
		setLayout(new GridLayout());
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.heightHint = 400;
		gd.widthHint = 400;
		setLayoutData(gd);

		pageBook = new PageBook(this, SWT.NONE);
		pageBook.setLayoutData(new GridData(GridData.FILL_BOTH));

		selectFilePanel = new SelectFilePanel(pageBook, selection);
		this.setSingleFileViewDefaultSelection(selection);
		
		// Catalog
		ICatalog xmlCatalog = JptDbwsUiPlugin.instance().getDefaultXMLCatalog();
		selectXMLCatalogIdPanel = new SelectXMLCatalogIdPanel(pageBook, xmlCatalog);
		selectXMLCatalogIdPanel.getTableViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateCompletionStateChange();
			}
		});
		Dialog.applyDialogFont(parent);
		pageBook.showPage(selectFilePanel.getControl());
	}
	
	public void setSingleFileViewDefaultSelection(ISelection selection) {
		this.selectFilePanel.setDefaultSelection(selection);
	}

	public IFile getFile() {
		return selectFilePanel.getFile();
	}

	public String getXMLCatalogURI() {
		return null;		// XMLCatalog not supported
	}

	public void setCatalogEntryType(int catalogEntryType) {
		selectXMLCatalogIdPanel.setCatalogEntryType(catalogEntryType);
	}

	public void setFilterExtensions(String[] filterExtensions) {
		selectFilePanel.resetFilters();
		selectFilePanel.addFilterExtensions(filterExtensions);

		selectXMLCatalogIdPanel.getTableViewer().setFilterExtensions(filterExtensions);
	}

	public void setListener(PanelListener listener) {
		this.listener = listener;
	}

	public void setVisibleHelper(boolean isVisible) {
		selectFilePanel.setVisibleHelper(isVisible);
	}

	public void updateCompletionStateChange() {
		if (listener != null) {
			listener.completionStateChanged();
		}
	}

	public void widgetDefaultSelected(SelectionEvent e) {
	}

	public void widgetSelected(SelectionEvent e) {
		pageBook.showPage(selectFilePanel.getControl());

		updateCompletionStateChange();
	}

	// ********** inner class **********
	
	protected class SelectFilePanel extends SelectSingleFileViewFacade implements SelectSingleFileViewFacade.Listener {
		protected Control control;

		public SelectFilePanel(Composite parent, IStructuredSelection selection) {
			super(selection, true);
			// String[] ext = {".dtd"};
			// addFilterExtensions(ext);
			control = createControl(parent);
			control.setLayoutData(new GridData(GridData.FILL_BOTH));
			SelectFilePanel.this.setListener(this);
		}

		public Control getControl() {
			return control;
		}

		public void setControlComplete(boolean isComplete) {
			updateCompletionStateChange();
		}
	}
}
