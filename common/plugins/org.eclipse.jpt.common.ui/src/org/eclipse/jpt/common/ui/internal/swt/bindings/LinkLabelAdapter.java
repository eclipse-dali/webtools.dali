/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Link;

/**
 * Adapt a link to the <em>label</em> interface (text-only support).
 * @see Link
 */
final class LinkLabelAdapter
	extends ControlLabelAdapter<Link>
{
	public LinkLabelAdapter(Link link) {
		super(link);
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
