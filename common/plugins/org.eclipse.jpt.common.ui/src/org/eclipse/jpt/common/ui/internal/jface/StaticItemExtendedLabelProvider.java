/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;

/**
 * Item label provider that contains unchanging image, text, and description.
 */
public class StaticItemExtendedLabelProvider
	extends AbstractStaticItemLabelProvider<ItemExtendedLabelProvider.Manager>
	implements ItemExtendedLabelProvider
{
	private final String description;

	/**
	 * Construct an item label provider that returns the specified text
	 * but no image. The provider will also return the specified text as the
	 * description.
	 */
	public StaticItemExtendedLabelProvider(String text) {
		this(null, text, null);
	}

	/**
	 * Construct an item label provider that returns the specified image
	 * and text. The provider will also return the specified text as the
	 * description. The manager need only be specified if the specified image
	 * descriptor is not <code>null</code>.
	 */
	public StaticItemExtendedLabelProvider(ImageDescriptor imageDescriptor, String text, ItemExtendedLabelProvider.Manager manager) {
		this(imageDescriptor, text, text, manager);
	}

	/**
	 * Construct an item label provider that returns the specified image, text,
	 * and description. The manager need only be specified if the specified image
	 * descriptor is not <code>null</code>.
	 */
	public StaticItemExtendedLabelProvider(ImageDescriptor imageDescriptor, String text, String description, ItemExtendedLabelProvider.Manager manager) {
		super(imageDescriptor, text, manager);
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}
}
