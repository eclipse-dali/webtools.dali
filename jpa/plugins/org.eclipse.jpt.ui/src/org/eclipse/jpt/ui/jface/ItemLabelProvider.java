/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.jface;

import org.eclipse.swt.graphics.Image;

/**
 * Interface used in conjunction with DelegatingLabelProvider to return 
 * label information for a particular item.
 * @see DelegatingLabelProvider
 * @see ItemLabelProviderFactory
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ItemLabelProvider
{
	/**
	 * Return the image for the item
	 */
	Image getImage();
	
	/**
	 * Return the text for the item
	 */
	public String getText();
	
	/**
	 * Return the description for the item
	 */
	public String getDescription();
	
	/**
	 * Dispose of this label provider, cleaning up all references, listeners, etc.
	 */
	void dispose();
}
