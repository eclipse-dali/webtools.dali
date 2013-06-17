/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.draw2d;

import org.eclipse.draw2d.widgets.MultiLineLabel;
import org.eclipse.jpt.common.ui.internal.swt.bindings.ControlLabelAdapter;
import org.eclipse.swt.graphics.Image;

/**
 * Adapt a button to the <em>label</em> interface.
 * @see MultiLineLabel
 */
public final class MultiLineLabelLabelAdapter
	extends ControlLabelAdapter<MultiLineLabel>
{
	public MultiLineLabelLabelAdapter(MultiLineLabel label) {
		super(label);
	}

	@Override
	protected void setImage_(Image image) {
		this.control.setImage(image);
	}

	@Override
	protected void setText_(String text) {
		this.control.setText(text);
	}
}
