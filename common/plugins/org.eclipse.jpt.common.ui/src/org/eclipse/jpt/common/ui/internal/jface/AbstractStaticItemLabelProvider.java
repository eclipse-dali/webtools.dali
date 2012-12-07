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
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;

/**
 * Item label provider that contains unchanging image and text.
 */
public abstract class AbstractStaticItemLabelProvider<M extends ItemLabelProvider.Manager>
	implements ItemLabelProvider
{
	protected final ImageDescriptor imageDescriptor;
	protected final String text;
	protected final M manager;

	/**
	 * Construct an item label provider that returns the specified text but
	 * no image.
	 */
	protected AbstractStaticItemLabelProvider(String text) {
		this(null, text, null);
	}

	/**
	 * Construct an item label provider that returns the specified image
	 * and text. The manager need only be specified if the specified image
	 * descriptor is not <code>null</code>.
	 */
	protected AbstractStaticItemLabelProvider(ImageDescriptor imageDescriptor, String text, M manager) {
		super();
		this.imageDescriptor = imageDescriptor;
		this.text = text;
		if ((imageDescriptor != null) && (manager == null)) {
			throw new NullPointerException();
		}
		this.manager = manager;
	}

	public Image getImage() {
		return (this.imageDescriptor == null) ? null : this.getImage_();
	}

	/**
	 * Pre-condition: the image descriptor is not <code>null</code>.
	 */
	protected Image getImage_() {
		return this.manager.getResourceManager().createImage(this.imageDescriptor);
	}

	public String getText() {
		return this.text;
	}

	public void dispose() {
		if (this.imageDescriptor != null) {
			this.manager.getResourceManager().destroyImage(this.imageDescriptor);
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.text);
	}
}
