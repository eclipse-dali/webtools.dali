/********************************************************************************
 * Copyright (c) 2006, 2009 Versant. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Versant and Others. - initial API and implementation
 ********************************************************************************/
package org.eclipse.jpt.ui.internal.views;

import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.selection.JpaSelection;
import org.eclipse.jpt.ui.internal.selection.JpaSelectionManager;
import org.eclipse.jpt.ui.internal.selection.SelectionManagerFactory;
import org.eclipse.jpt.ui.internal.widgets.FormWidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.PropertySheetWidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This is the abstract implementation of the JPA view. The selection is changed
 * by receiving a <code>IJpaSelection</code>.
 *
 * @see JpaSelection
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

	private ScrolledForm scrolledForm;
	
	
	/**
	 * The factory used to create the various widgets.
	 */
	private WidgetFactory widgetFactory;

	/**
	 * Creates a new <code>AbstractJpaView</code>.
	 *
	 * @param defaultLabel
	 */
	public AbstractJpaView(String defaultLabel) {
		super();
		this.defaultLabel = defaultLabel;
		this.initialize();
	}

	private Composite buildDefaultComposite() {
		Composite composite = widgetFactory.createComposite(pageBook);
		composite.setLayout(new FillLayout(SWT.VERTICAL));
		getWidgetFactory().createLabel(composite, defaultLabel);
		return composite;
	}

	@Override
	public final void createPartControl(Composite parent) {
		this.scrolledForm = getFormWidgetFactory().createScrolledForm(parent);
		JptUiPlugin.instance().controlAffectsJavaSource(this.scrolledForm);
		this.scrolledForm.getBody().setLayout(new GridLayout());

		this.pageBook = new PageBook(this.scrolledForm.getBody(), SWT.NONE);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		this.pageBook.setLayoutData(gridData);
		this.scrolledForm.setContent(this.pageBook);

		this.defaultComposite = buildDefaultComposite();
		this.pageBook.showPage(this.defaultComposite);

		subcreatePartControl(parent);

		JpaSelectionManager selectionManager =
			SelectionManagerFactory.getSelectionManager(getViewSite().getWorkbenchWindow());

		selectionManager.register(this);
		select(selectionManager.getCurrentSelection());
	}

	protected final PageBook getPageBook() {
		return this.pageBook;
	}

	public final WidgetFactory getWidgetFactory() {
		return this.widgetFactory;
	}

	/**
	 * Initializes this JPA view.
	 */
	protected void initialize() {
		this.widgetFactory = new PropertySheetWidgetFactory(
			new TabbedPropertySheetWidgetFactory()
		);
	}

	private FormToolkit getFormWidgetFactory() {
		return ((FormWidgetFactory) widgetFactory).getWidgetFactory();
	}

	/**
	 * The selection has changed, update the current page by using the given
	 * selection state.
	 *
	 * @param jpaSelection The new selection used to update this JPA view
	 */
	public abstract void select(JpaSelection jpaSelection);

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
	protected final void showPage(Control page) {
		page.setParent(this.pageBook);
		this.pageBook.showPage(page);
		this.scrolledForm.reflow(true);
	}

	protected void subcreatePartControl(@SuppressWarnings("unused") Composite parent) {
		// no op - for subclasses to override if wished
	}

}
