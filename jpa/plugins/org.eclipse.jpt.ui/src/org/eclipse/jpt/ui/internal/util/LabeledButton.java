/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.util;

import org.eclipse.core.runtime.Assert;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;

/**
 * A default implementation of <code>LabeledControl</code> that updates a
 * <code>Button</code> when required.
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class LabeledButton implements LabeledControl
{
	/**
	 * The button to be updated with a different icon and text.
	 */
	private final Button button;

	/**
	 * Creates a new <code>LabeledButton</code>.
	 *
	 * @param button The button that will have its text and icon updated when
	 * required
	 * @exception AssertionFailedException If the given <code>Button</code> is
	 * <code>null</code>
	 */
	public LabeledButton(Button button) {
		super();

		Assert.isNotNull(button, "The button cannot be null");
		this.button = button;
	}

	/*
	 * (non-Javadoc)
	 */
	public void setImage(Image image) {
		if (!this.button.isDisposed()) {
			this.button.setImage(image);
			this.button.getParent().layout(true);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	public void setText(String text) {
		if (!this.button.isDisposed()) {
			this.button.setText(text);
			this.button.getParent().layout(true);
		}
	}
}
