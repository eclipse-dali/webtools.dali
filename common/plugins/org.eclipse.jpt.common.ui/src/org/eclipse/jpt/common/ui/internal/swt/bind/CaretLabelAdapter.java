/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bind;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Caret;

/**
 * Adapt a caret to the <em>label</em> interface (image-only support).
 * @see Caret
 */
final class CaretLabelAdapter
	implements WidgetLabelAdapter
{
	private final Caret caret;

	public CaretLabelAdapter(Caret caret) {
		super();
		if (caret == null) {
			throw new NullPointerException();
		}
		this.caret = caret;
	}

	public void setImage(Image image) {
		this.caret.setImage(image);
	}

	public void setText(String text) {
		// NOP
	}

	public Caret getWidget() {
		return this.caret;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.caret);
	}
}
