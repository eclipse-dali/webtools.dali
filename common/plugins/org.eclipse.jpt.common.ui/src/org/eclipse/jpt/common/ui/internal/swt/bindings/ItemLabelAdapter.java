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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Item;

/**
 * Adapt an item to the <em>label</em> interface.
 * @see Item
 */
final class ItemLabelAdapter
	implements WidgetLabelAdapter
{
	private final Item item;

	public ItemLabelAdapter(Item item) {
		super();
		if (item == null) {
			throw new NullPointerException();
		}
		this.item = item;
	}

	public void setImage(Image image) {
		this.item.setImage(image);
	}

	public void setText(String text) {
		this.item.setText(text);
	}

	public Item getWidget() {
		return this.item;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.item);
	}
}
