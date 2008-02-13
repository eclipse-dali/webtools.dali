/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.util;

import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;

/**
 * This controller is responsible to switch the active page based on a value. A
 * <code>Transformer</code> is used to transformed that value into a
 * <code>Control</code>.
 *
 * @version 2.0
 * @since 2.0
 */
public final class ControlSwitcher
{
	/**
	 * The widget that is used to show the active <code>Control</code>.
	 */
	private PageBook pageBook;

	/**
	 * The <code>Transformer</code> used to transform the value into a
	 * <code>Control</code>.
	 */
	private Transformer<?, Control> paneTransformer;

	/**
	 * Creates a new <code>ControlSwitcher</code>.
	 *
	 * @param switchHolder The holder of the value that will be used to retrieve
	 * the right <code>Control</code> when passed to the given transformer
	 * @param paneTransformer The <code>Transformer</code> used to transform the value into a
	 * <code>Control</code>
	 * @param pageBook The <code>Transformer</code> used to transform the value
	 * into a <code>Control</code>
	 */
	public <T> ControlSwitcher(PropertyValueModel<? extends T> switchHolder,
	                           Transformer<T, Control> paneTransformer,
	                           PageBook pageBook)
	{
		super();
		initialize(switchHolder, paneTransformer, pageBook);
	}

	private PropertyChangeListener buildPropertyChangeListener() {
		return new SWTPropertyChangeListenerWrapper(
			buildPropertyChangeListener_()
		);
	}

	private PropertyChangeListener buildPropertyChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				switchPanes(e.newValue());
			}
		};
	}

	/**
	 * Initializes this <code>ControlSwitcher</code>.
	 *
	 * @param switchHolder The holder of the value that will be used to retrieve
	 * the right <code>Control</code> when passed to the given transformer
	 * @param paneTransformer The <code>Transformer</code> used to transform the value into a
	 * <code>Control</code>
	 * @param pageBook The <code>Transformer</code> used to transform the value
	 * into a <code>Control</code>
	 */
	private void initialize(PropertyValueModel<?> switchHolder,
	                        Transformer<?, Control> paneTransformer,
	                        PageBook pageBook)
	{
		this.pageBook        = pageBook;
		this.paneTransformer = paneTransformer;

		switchHolder.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			buildPropertyChangeListener()
		);

		switchPanes(switchHolder.value());
	}

	/**
	 * Switches the active page by transforming the given value into its
	 * corresponding pane.
	 *
	 * @param value The state passed to the transformer in order to retrieve the
	 * new pane
	 */
	private void switchPanes(Object value) {

		if (pageBook.isDisposed()) {
			return;
		}

		// Retrieve the Control for the new value
		Control pane = transform(value);
		boolean visible = (pane != null);

		// Show the new page
		if (visible) {
			pageBook.showPage(pane);
		}
		else {
			// Note: We can't null due to a bug in PageBook
			pageBook.showPage(new Label(pageBook, SWT.SEPARATOR | SWT.HORIZONTAL));
		}

		if (pageBook.isVisible() != visible) {
			pageBook.setVisible(visible);
		}

		// Revalidate the parents in order to update the layout
		SWTUtil.reflow(pageBook);
	}

	@SuppressWarnings("unchecked")
	private Control transform(Object value) {
		return ((Transformer<Object, Control>) paneTransformer).transform(value);
	}
}