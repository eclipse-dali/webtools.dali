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

/**
 * This is the abstract implementation of the JPA view. The selection is changed
 * by receiving a <code>IJpaSelection</code>.
 *
 * @see IJpaSelection
 *
 * @version 2.0
 * @since 1.0
 */
public abstract class AbstractJpaView extends ViewPart
{
	/**
	 * The default page used when nothing can be shown.
	 */
	private Composite defaultComposite;

	/**
	 * The string to display when there is no view content
	 */
	private String defaultLabel;

	/**
	 * The container of the current page.
	 */
	private PageBook pageBook;

	/**
	 *
	 */
	private TabbedPropertySheetWidgetFactory widgetFactory;

	/**
	 * Creates a new <code>AbstractJpaView</code>.
	 *
	 * @param defaultLabel
	 */
	public AbstractJpaView(String defaultLabel) {
		super();
		this.defaultLabel = defaultLabel;
		initialize();
	}

	private Composite buildDefaultComposite() {
		Composite composite = getWidgetFactory().createComposite(pageBook, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		getWidgetFactory().createLabel(composite, defaultLabel);
		return composite;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
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

	protected final PageBook getPageBook() {
		return pageBook;
	}

	public final TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return this.widgetFactory;
	}

	/**
	 * Initializes this JPA view.
	 */
	protected void initialize() {
		this.widgetFactory = new TabbedPropertySheetWidgetFactory();
	}

	/**
	 * The selection has changed, update the current page by using the given
	 * selection state.
	 *
	 * @param jpaSelection The new selection used to update this JPA view
	 */
	public abstract void select(IJpaSelection jpaSelection);

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void setFocus() {
		pageBook.setFocus();
	}

	/**
	 * Changes the current page and show the default one.
	 */
	protected void showDefaultPage() {
		showPage(defaultComposite);
	}

	/**
	 * Changes the current page and show the given one.
	 *
	 * @param page The new page to show, <code>null</code> can't be passed
	 */
	protected final void showPage(Composite page) {
		pageBook.showPage(page);
	}

	protected void subcreatePartControl(Composite parent) {
		// no op - for subclasses to override if wished
	}
}