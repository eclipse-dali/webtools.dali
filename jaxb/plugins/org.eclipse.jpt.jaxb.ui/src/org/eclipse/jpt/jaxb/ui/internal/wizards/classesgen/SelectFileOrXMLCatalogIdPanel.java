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
package org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jpt.jaxb.ui.JptJaxbUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalog;
import org.eclipse.wst.xml.core.internal.catalog.provisional.ICatalogEntry;


public class SelectFileOrXMLCatalogIdPanel extends Composite implements SelectionListener {

	public interface PanelListener {
		void completionStateChanged();
	}

	protected PanelListener listener;
	protected PageBook pageBook;

	protected Button[] radioButton;
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

		radioButton = new Button[2];
		radioButton[0] = new Button(this, SWT.RADIO);
		radioButton[0].setText("Select file from Workspace");
		radioButton[0].setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		radioButton[0].setSelection(true);
		radioButton[0].addSelectionListener(this);

		radioButton[1] = new Button(this, SWT.RADIO);
		radioButton[1].setText("Select XML Catalog entry");
		radioButton[1].setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		radioButton[1].addSelectionListener(this);

		pageBook = new PageBook(this, SWT.NONE);
		pageBook.setLayoutData(new GridData(GridData.FILL_BOTH));

		selectFilePanel = new SelectFilePanel(pageBook, selection);
		this.setSingleFileViewDefaultSelection(selection);
		
		// Catalog
		ICatalog xmlCatalog = JptJaxbUiPlugin.instance().getDefaultXMLCatalog();
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
		IFile result = null;
		if (radioButton[0].getSelection()) {
			result = selectFilePanel.getFile();
		}
		return result;
	}

	public ICatalogEntry getXMLCatalogEntry() {
		ICatalogEntry result = null;
		if (radioButton[1].getSelection()) {
			result = selectXMLCatalogIdPanel.getXMLCatalogEntry();
		}
		return result;
	}

	public String getXMLCatalogId() {
		String result = null;
		if (radioButton[1].getSelection()) {
			result = selectXMLCatalogIdPanel.getId();
		}
		return result;
	}

	public String getXMLCatalogURI() {
		String result = null;
		if (radioButton[1].getSelection()) {
			result = selectXMLCatalogIdPanel.getURI();
		}
		return result;
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
		if (e.widget == radioButton[0]) {
			pageBook.showPage(selectFilePanel.getControl());
		}
		else {
			pageBook.showPage(selectXMLCatalogIdPanel);
		}
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
