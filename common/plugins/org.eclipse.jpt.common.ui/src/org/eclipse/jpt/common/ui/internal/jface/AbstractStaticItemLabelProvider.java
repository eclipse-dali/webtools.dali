/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
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
	private final ImageDescriptor imageDescriptor;
	private Image image;
	private boolean imageBuilt = false;  // image can be null
	private final String text;
	/* private-protected */ final M manager;

	/**
	 * Construct an item label provider that returns the specified text but
	 * no image.
	 */
	/* private-protected */ AbstractStaticItemLabelProvider(String text) {
		this(null, text, null);
	}

	/**
	 * Construct an item label provider that returns the specified image
	 * and text. The manager need only be specified if the specified image
	 * descriptor is not <code>null</code>.
	 */
	/* private-protected */ AbstractStaticItemLabelProvider(ImageDescriptor imageDescriptor, String text, M manager) {
		super();
		this.imageDescriptor = imageDescriptor;
		this.text = text;
		if ((imageDescriptor != null) && (manager == null)) {
			throw new NullPointerException();
		}
		this.manager = manager;
	}

	/**
	 * Return the image (lazy-initialized).
	 */
	public Image getImage() {
		if ( ! this.imageBuilt) {
			this.imageBuilt = true;
			this.image = this.buildImage();
		}
		return this.image;
	}

	private Image buildImage() {
		return (this.imageDescriptor == null) ? null : this.manager.getResourceManager().createImage(this.imageDescriptor);
	}

	public String getText() {
		return this.text;
	}

	public boolean isLabelProperty(String property) {
		return false;  // the label does not change
	}

	public void dispose() {
		if (this.image != null) {
			this.manager.getResourceManager().destroyImage(this.imageDescriptor);
			this.image = null;
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.text);
	}
}
