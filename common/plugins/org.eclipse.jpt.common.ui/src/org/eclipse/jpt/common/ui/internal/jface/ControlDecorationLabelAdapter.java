/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jpt.common.ui.internal.swt.bind.WidgetLabelAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

/**
 * Adapt a JFace control decoration to the <em>label</em> interface.
 * @see ControlDecoration
 */
public final class ControlDecorationLabelAdapter
	implements WidgetLabelAdapter
{
	private final ControlDecoration controlDecoration;

	public ControlDecorationLabelAdapter(ControlDecoration controlDecoration) {
		super();
		if (controlDecoration == null) {
			throw new NullPointerException();
		}
		this.controlDecoration = controlDecoration;
	}

	public void setImage(Image image) {
		this.controlDecoration.setImage(image);
	}

	public void setText(String text) {
		this.controlDecoration.setDescriptionText(text);
	}

	public Control getWidget() {
		return this.controlDecoration.getControl();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.controlDecoration);
	}
}
