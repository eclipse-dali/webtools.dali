/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jface;

/**
 * Interface used in conjunction with DelegatingTreeContentProvider to return 
 * tree information for a particular node.
 * @see DelegatingTreeContentProvider
 * @see ITreeItemContentProviderFactory
 */
public interface ITreeItemContentProvider
{
	/**
	 * Return the parent of the represented node
	 */
	Object getParent();
	
	/**
	 * Return whether the represented node has children
	 */
	boolean hasChildren();
	
	/**
	 * Return the children of the represented node
	 */
	Object[] getChildren();
	
	/**
	 * Dispose of this content provider, cleaning up all references, listeners, etc.
	 */
	void dispose();
}
