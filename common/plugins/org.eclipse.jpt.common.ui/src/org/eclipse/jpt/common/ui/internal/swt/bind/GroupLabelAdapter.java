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

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Group;

/**
 * Adapt a group to the <em>label</em> interface (text-only support).
 * @see Group
 */
final class GroupLabelAdapter
	extends ControlLabelAdapter<Group>
{
	public GroupLabelAdapter(Group group) {
		super(group);
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
