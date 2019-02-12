/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jpt.common.ui.internal.swt.bindings.WidgetLabelAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

/**
 * Adapt a JFace viewer cell to the <em>label</em> interface.
 * @see ViewerCell
 */
public final class ViewerCellLabelAdapter
	implements WidgetLabelAdapter
{
	private final ViewerCell viewerCell;

	public ViewerCellLabelAdapter(ViewerCell viewerCell) {
		super();
		if (viewerCell == null) {
			throw new NullPointerException();
		}
		this.viewerCell = viewerCell;
	}

	public void setImage(Image image) {
		this.viewerCell.setImage(image);
	}

	public void setText(String text) {
		this.viewerCell.setText(text);
	}

	public Control getWidget() {
		return this.viewerCell.getControl();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.viewerCell);
	}
}
