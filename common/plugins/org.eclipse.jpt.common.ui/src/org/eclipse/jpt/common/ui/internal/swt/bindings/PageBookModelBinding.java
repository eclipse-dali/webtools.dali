/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import org.eclipse.jpt.common.ui.internal.swt.events.DisposeAdapter;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.ControlTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;

/**
 * This binding can be used to keep a page book's current page synchronized
 * with a model.
 * @see PropertyValueModel
 * @see PageBook
 */
final class PageBookModelBinding<T> {
	// ***** model
	/**
	 * The value model that determines the page to be displayed.
	 */
	private final PropertyValueModel<T> valueModel;

	/**
	 * A listener that allows us to synchronize the page book's current page
	 * with the value model.
	 */
	private final PropertyChangeListener valueListener;

	// ***** transformer
	/**
	 * The transformer that converts the model's value to the control
	 * to be displayed by the page book. If the transformer returns
	 * <code>null</code>, the page book will display the <em>null</em> page.
	 */
	private final Transformer<? super T, Control> transformer;

	// ***** UI
	/**
	 * The page book whose current page is determined by the value model.
	 */
	private final PageBook pageBook;

	/**
	 * A listener that allows us to stop listening to stuff when the page book
	 * is disposed. (Critical for preventing memory leaks.)
	 */
	private final DisposeListener pageBookDisposeListener;

	/**
	 * The control to be displayed in the page book if the control transformer
	 * returns <code>null</code>.
	 */
	private final Control defaultPage;


	// ********** construction **********

	/**
	 * Constructor - the model, transformer, and page book are required.
	 */
	PageBookModelBinding(PropertyValueModel<T> valueModel, Transformer<? super T, Control> transformer, PageBook pageBook, Control defaultPage) {
		super();
		if ((valueModel == null) || (transformer == null) || (pageBook == null)) {
			throw new NullPointerException();
		}
		this.valueModel = valueModel;
		this.transformer = transformer;
		this.pageBook = pageBook;

		this.defaultPage = (defaultPage != null) ? defaultPage : this.buildDefaultDefaultPage();
		if (this.defaultPage.getParent() != this.pageBook) {
			throw new IllegalArgumentException("The null page's parent must be the page book: " + defaultPage); //$NON-NLS-1$
		}

		this.valueListener = this.buildValueListener();
		this.valueModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.valueListener);

		this.pageBookDisposeListener = this.buildPageBookDisposeListener();
		this.pageBook.addDisposeListener(this.pageBookDisposeListener);

		this.showPage(this.valueModel.getValue());
	}

	private Control buildDefaultDefaultPage() {
		// SWT.SHADOW_NONE makes the line separator invisible
		return new Label(this.pageBook, SWT.SEPARATOR | SWT.SHADOW_NONE | SWT.HORIZONTAL);
	}

	private PropertyChangeListener buildValueListener() {
		return SWTListenerTools.wrap(new ValueListener(), this.pageBook);
	}

	/* CU private */ class ValueListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			PageBookModelBinding.this.valueChanged(event);
		}
	}

	private DisposeListener buildPageBookDisposeListener() {
		return new PageBookDisposeListener();
	}

	/* CU private */ class PageBookDisposeListener
		extends DisposeAdapter
	{
		@Override
		public void widgetDisposed(DisposeEvent event) {
			PageBookModelBinding.this.pageBookDisposed();
		}
	}


	// ********** model events **********

	/* CU private */ void valueChanged(PropertyChangeEvent event) {
		if ( ! this.pageBook.isDisposed()) {
			@SuppressWarnings("unchecked")
			T value = (T) event.getNewValue();
			this.showPage(value);
		}
	}

	/**
	 * Show the page corresponding to the specified model value.
	 */
	private void showPage(T value) {
		Control page = this.transformer.transform(value);
		this.pageBook.showPage((page != null) ? page : this.defaultPage);
		ControlTools.reflow(this.pageBook);
	}


	// ********** UI events **********

	/* CU private */ void pageBookDisposed() {
		// the page book is not yet "disposed" when we receive this event
		// so we can still remove our listener
		this.pageBook.removeDisposeListener(this.pageBookDisposeListener);
		this.valueModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.valueListener);
	}


	// ********** misc **********

    @Override
	public String toString() {
		return ObjectTools.toString(this, this.valueModel);
	}
}
