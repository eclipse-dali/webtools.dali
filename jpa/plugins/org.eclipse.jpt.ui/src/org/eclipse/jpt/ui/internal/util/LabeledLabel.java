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
import org.eclipse.swt.widgets.Label;

/**
 * A default implementation of <code>LabeledControl</code> that updates an
 * <code>Label</code> when required.
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public final class LabeledLabel implements LabeledControl
{
	/**
	 * The label to be updated with a different icon and text.
	 */
	private final Label label;

	/**
	 * Creates a new <code>LabeledButton</code>.
	 *
	 * @param label The label that will have its text and icon updated when
	 * required
	 */
	public LabeledLabel(Label label) {
		super();
		Assert.isNotNull(label, "The label cannot be null");
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 */
	public void setIcon(Image image) {
		this.label.setImage(image);
	}

	/*
	 * (non-Javadoc)
	 */
	public void setText(String text) {
		this.label.setText(text);
	}
}
