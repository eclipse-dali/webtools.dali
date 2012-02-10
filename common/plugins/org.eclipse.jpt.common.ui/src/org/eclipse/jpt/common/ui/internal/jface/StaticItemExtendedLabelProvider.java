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

import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Item label provider that contains unchanging image, text, and description.
 */
public class StaticItemExtendedLabelProvider
	extends StaticItemLabelProvider
	implements ItemExtendedLabelProvider
{
	protected final String description;

	/**
	 * Construct an item label provider that returns the specified text
	 * but no image. The provider will also return the specified text as the
	 * description.
	 */
	public StaticItemExtendedLabelProvider(String text) {
		this(null, text);
	}

	/**
	 * Construct an item label provider that returns the specified image
	 * and text. The provider will also return the specified text as the
	 * description.
	 */
	public StaticItemExtendedLabelProvider(Image image, String text) {
		this(image, text, text);
	}

	/**
	 * Construct an item label provider that returns the specified image, text,
	 * and description.
	 */
	public StaticItemExtendedLabelProvider(Image image, String text, String description) {
		super(image, text);
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
