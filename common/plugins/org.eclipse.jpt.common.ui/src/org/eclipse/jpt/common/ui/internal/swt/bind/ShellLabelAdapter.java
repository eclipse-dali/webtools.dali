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
import org.eclipse.swt.widgets.Shell;

/**
 * Adapt a shell (window) to the <em>label</em> interface.
 * @see Shell
 */
final class ShellLabelAdapter
	implements WidgetLabelAdapter
{
	private final Shell shell;

	public ShellLabelAdapter(Shell shell) {
		super();
		if (shell == null) {
			throw new NullPointerException();
		}
		this.shell = shell;
	}

	public void setImage(Image image) {
		this.shell.setImage(image);
	}

	public void setText(String text) {
		this.shell.setText(text);
	}

	public Shell getWidget() {
		return this.shell;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.shell);
	}
}
