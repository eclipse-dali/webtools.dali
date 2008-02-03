/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.util;

import org.eclipse.swt.graphics.Image;

/**
 * This <code>LabeledControl</code> is used to encapsulate a widget and update
 * its properties (icon and text).
 *
 * @see LabeledButton
 * @see LabeledLabel
 *
 * @version 2.0
 * @since 2.0
 */
public interface LabeledControl {
	/**
	 * Passes the image so the wrapped component can receive it.
	 *
	 * @param image The new <code>Image</code>
	 */
	void setIcon(Image image);

	/**
	 * Passes the text so the wrapped component can receive it.
	 *
	 * @param text The new text
	 */
	void setText(String text);
}
