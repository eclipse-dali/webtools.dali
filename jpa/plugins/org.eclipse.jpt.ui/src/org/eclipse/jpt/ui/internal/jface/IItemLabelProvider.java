/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jface;

import org.eclipse.swt.graphics.Image;

/**
 * Interface used in conjunction with DelegatingLabelProvider to return 
 * label information for a particular item.
 * @see DelegatingLabelProvider
 * @see IItemLabelProviderFactory
 */
public interface IItemLabelProvider
{
	/**
	 * Return the image for the item
	 */
	Image image();
	
	/**
	 * Return the text for the item
	 */
	public String text();
	
	/**
	 * Dispose of this label provider, cleaning up all references, listeners, etc.
	 */
	void dispose();
}
