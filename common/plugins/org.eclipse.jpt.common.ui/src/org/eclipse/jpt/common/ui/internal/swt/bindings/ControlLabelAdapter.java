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
import org.eclipse.swt.widgets.Control;

/**
 * Adapt a control to the <em>label</em> interface (text-only support).
 * @param <C> the type of control to be adapted
 * @see Control
 */
public abstract class ControlLabelAdapter<C extends Control>
	implements WidgetLabelAdapter
{
	protected final C control;

	protected ControlLabelAdapter(C control) {
		super();
		if (control == null) {
			throw new NullPointerException();
		}
		this.control = control;
	}

	public final void setImage(Image image) {
		this.setImage_(image);
		this.control.getParent().layout(true);
	}

	protected abstract void setImage_(Image image);

	public final void setText(String text) {
		this.setText_(text);
		this.control.getParent().layout(true);
	}

	protected abstract void setText_(String text);

	public C getWidget() {
		return this.control;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.control);
	}
}
