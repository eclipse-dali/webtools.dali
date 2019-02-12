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
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;

public class StaticItemLabelProvider
	extends AbstractStaticItemLabelProvider<ItemLabelProvider.Manager>
{
	/**
	 * Construct an item label provider that returns the specified text but
	 * no image.
	 */
	public StaticItemLabelProvider(String text) {
		super(text);
	}

	/**
	 * Construct an item label provider that returns the specified image
	 * and text. The manager need only be specified if the specified image
	 * descriptor is not <code>null</code>.
	 */
	public StaticItemLabelProvider(ImageDescriptor imageDescriptor, String text, ItemLabelProvider.Manager manager) {
		super(imageDescriptor, text, manager);
	}
}
