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

/**
 * Marker interface used in conjunction with DelegatingContentProvider to return 
 * content information for a particular item.
 * @see DelegatingContentProvider
 * @see ItemContentProviderFactory
 */
public interface ItemContentProvider
{
	/**
	 * Return the elements of the represented item.
	 * Note that when this is called, the represented item is an input element.
	 */
	Object[] getElements();
	
	/**
	 * Dispose of this content provider, cleaning up all references, listeners, etc.
	 */
	void dispose();
}
