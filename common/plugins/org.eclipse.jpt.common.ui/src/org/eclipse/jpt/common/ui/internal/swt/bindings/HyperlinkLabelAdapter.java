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

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 * Adapt a hyperlink to the <em>label</em> interface (text-only support).
 * @see Hyperlink
 */
final class HyperlinkLabelAdapter
	extends ControlLabelAdapter<Hyperlink>
{
	public HyperlinkLabelAdapter(Hyperlink hyperlink) {
		super(hyperlink);
	}

	@Override
	protected void setImage_(Image image) {
		// NOP
	}

	@Override
	protected void setText_(String text) {
		this.control.setText(text);
	}
}
