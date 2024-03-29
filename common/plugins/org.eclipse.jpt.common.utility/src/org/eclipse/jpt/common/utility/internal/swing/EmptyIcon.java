/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.swing;

import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 * Implement the {@link Icon} interface with an icon that has a size but
 * does not paint anything on the graphics context.
 */
public class EmptyIcon
	implements Icon
{
	private final int width;
	private final int height;

	public static final EmptyIcon NULL_INSTANCE = new EmptyIcon(0);


	public EmptyIcon(int size) {
		this(size, size);
	}

	public EmptyIcon(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		// don't paint anything for an empty icon
	}

	public int getIconWidth() { 
		return this.width;
	}

	public int getIconHeight() {
		return this.height;
	}
}
