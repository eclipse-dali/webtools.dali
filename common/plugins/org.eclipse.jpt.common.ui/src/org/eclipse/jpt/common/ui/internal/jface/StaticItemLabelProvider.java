/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;

/**
 * Item label provider that contains unchanging image and text.
 */
public class StaticItemLabelProvider
	implements ItemLabelProvider
{
	protected final Image image;
	protected final String text;

	/**
	 * Construct an item label provider that returns the specified text but
	 * no image.
	 */
	public StaticItemLabelProvider(String text) {
		this(null, text);
	}

	/**
	 * Construct an item label provider that returns the specified image
	 * and text.
	 */
	public StaticItemLabelProvider(Image image, String text) {
		super();
		this.image = image;
		this.text = text;
	}

	public Image getImage() {
		return this.image;
	}

	public String getText() {
		return this.text;
	}

	public void dispose() {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.text);
	}
}
