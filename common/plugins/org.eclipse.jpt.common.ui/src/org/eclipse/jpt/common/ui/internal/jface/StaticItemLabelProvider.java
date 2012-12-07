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
	 * and text.
	 */
	public StaticItemLabelProvider(ImageDescriptor imageDescriptor, String text, ItemLabelProvider.Manager manager) {
		super(imageDescriptor, text, manager);
	}
}
