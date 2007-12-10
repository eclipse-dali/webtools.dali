/********************************************************************************
 * Copyright (c) 2006, 2007 Versant. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Versant and Others. - initial API and implementation
 ********************************************************************************/
package org.eclipse.jpt.ui.internal.views;

import org.eclipse.jpt.ui.internal.selection.IJpaSelection;
import org.eclipse.jpt.ui.internal.selection.IJpaSelectionManager;
import org.eclipse.jpt.ui.internal.selection.SelectionManagerFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public abstract class AbstractJpaView extends ViewPart 
{	
	protected PageBook pageBook;
	
	protected Composite defaultComposite;
	
	
	/**
	 * The string to display when there is no view content
	 */
	private String defaultLabel;
	
	private TabbedPropertySheetWidgetFactory widgetFactory;
	
	
	public AbstractJpaView(String aDefaultLabel) {
		super();
		defaultLabel = aDefaultLabel;
		this.widgetFactory = new TabbedPropertySheetWidgetFactory();
	}
	
	/* @see IWorkbenchPart#createPartControl(Composite) */
	public final void createPartControl(Composite parent) {
		pageBook = new PageBook(parent, SWT.NONE);
		defaultComposite = buildDefaultComposite();
		pageBook.showPage(defaultComposite);
		
		subcreatePartControl(parent);
		
		IJpaSelectionManager selectionManager = 
			SelectionManagerFactory.getSelectionManager(getViewSite().getWorkbenchWindow());
		selectionManager.register(this);
		select(selectionManager.getCurrentSelection());
	}
	
	protected void subcreatePartControl(Composite parent) {
		// no op - for subclasses to override if wished
	}
	
	private Composite buildDefaultComposite() {
		Composite composite = getWidgetFactory().createComposite(pageBook, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		getWidgetFactory().createLabel(composite, defaultLabel);
		return composite;
	}
	
	public abstract void select(IJpaSelection aSelection);
	
	protected void showDefaultPage() {
		pageBook.showPage(defaultComposite);
	}
	
	/* @see IWorkbenchPart#setFocus() */
	public void setFocus() {
		pageBook.setFocus();
	}
	
	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return this.widgetFactory;
	}
}
