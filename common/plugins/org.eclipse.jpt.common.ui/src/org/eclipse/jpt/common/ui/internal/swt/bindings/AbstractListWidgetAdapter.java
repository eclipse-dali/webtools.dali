/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;

/**
 * All the "list widgets" are subclasses of {@link Widget}; so we can provide
 * a smidgen of common behavior here.
 * @param <W> the type of the widget to be adapted
 */
abstract class AbstractListWidgetAdapter<E, W extends Widget>
	implements ListWidgetModelBinding.ListWidget<E>
{
	final W widget;

	AbstractListWidgetAdapter(W widget) {
		super();
		if (widget == null) {
			throw new NullPointerException();
		}
		this.widget = widget;
	}

	public Display getDisplay() {
		return this.widget.getDisplay();
	}

	public boolean isDisposed() {
		return this.widget.isDisposed();
	}

	public void addDisposeListener(DisposeListener listener) {
		this.widget.addDisposeListener(listener);
	}

	public void removeDisposeListener(DisposeListener listener) {
		this.widget.removeDisposeListener(listener);
	}
}

