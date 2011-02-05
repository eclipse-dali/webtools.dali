/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.util;

import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;

/**
 * This controller is responsible to switch the active page based on a value. A
 * <code>Transformer</code> is used to transformed that value into a
 * <code>Control</code>.
 *
 * @version 2.3
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

	private Label emptyLabel;

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

	private void initialize(PropertyValueModel<?> switchHolder,
	                        Transformer<?, Control> paneTransformer,
	                        PageBook pageBook)
	{
		this.pageBook        = pageBook;
		this.paneTransformer = paneTransformer;

		this.emptyLabel = this.buildEmptyLabel();

		switchHolder.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			buildPropertyChangeListener()
		);

		switchPages(switchHolder.getValue());
	}

	//Build an empty label to display in the page book when the paneTransformer returns null.
	//SWT.SHADOW_NONE makes the line separator not visible
	//This is the best we can come up with for an empty page
	private Label buildEmptyLabel() {
		return new Label(this.pageBook, SWT.SEPARATOR | SWT.SHADOW_NONE | SWT.HORIZONTAL);
	}

	private PropertyChangeListener buildPropertyChangeListener() {
		return new SWTPropertyChangeListenerWrapper(
			buildPropertyChangeListener_()
		);
	}

	private PropertyChangeListener buildPropertyChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				switchPages(e.getNewValue());
			}
		};
	}

	/**
	 * Switches the active page by transforming the given value into its
	 * corresponding pane.
	 *
	 * @param value The state passed to the transformer in order to retrieve the
	 * new pane
	 */
	private void switchPages(Object value) {
		if (this.pageBook.isDisposed()) {
			return;
		}

		// Retrieve the Control for the new value
		Control page = transform(value);

		if (page == null) {
			//Note: We can't pass in null due to a bug in PageBook
			page = this.emptyLabel;
		}
		this.pageBook.showPage(page);

		// Revalidate the parents in order to update the layout
		SWTUtil.reflow(this.pageBook);
	}

	@SuppressWarnings("unchecked")
	private Control transform(Object value) {
		return ((Transformer<Object, Control>) this.paneTransformer).transform(value);
	}
}