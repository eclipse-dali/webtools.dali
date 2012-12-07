/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.jface;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.graphics.Image;

/**
 * Implementations of this interface can be used to maintain the label (image,
 * and text) of a specific item. The implementation will monitor
 * the item for any changes that affect the label and forward them appropriately
 * to the {@link Manager}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jface.viewers.ILabelProvider
 */
public interface ItemLabelProvider {
	/**
	 * Return the image for the provider's item.
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(Object)
	 */
	Image getImage();
	
	/**
	 * Return the text for the provider's item.
	 * 
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(Object)
	 */
	public String getText();
	
	/**
	 * Dispose the item label provider.
	 * Remove any item listeners as appropriate.
	 */
	void dispose();


	/**
	 * An item label provider's manager is notified whenever the item's
	 * label has changed.
	 */
	interface Manager {
		/**
		 * Return a resource manager that can be used by the item label
		 * provider to retrieve {@link Image}s. The provider manager will
		 * dispose the resource manager;
		 * but, to minimize resource usage, the item label provider should
		 * dispose its own image(s) as appropriate.
		 */
		ResourceManager getResourceManager();

		/**
		 * The label (image and/or text) for the specified item has changed.
		 * Update appropriately.
		 */
		void updateLabel(Object item);
	}
}
