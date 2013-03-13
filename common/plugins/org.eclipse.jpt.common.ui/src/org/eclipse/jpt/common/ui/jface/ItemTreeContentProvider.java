/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.jface;

/**
 * Implementations of this interface can be used to maintain the content of a
 * specific tree element. The implementation will monitor the element for any
 * changes that affect the element's children and forward them
 * appropriately to the {@link Manager}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jface.viewers.IContentProvider
 * @see org.eclipse.jface.viewers.IStructuredContentProvider
 * @see org.eclipse.jface.viewers.ITreeContentProvider
 */
public interface ItemTreeContentProvider
	extends ItemStructuredContentProvider
{
	/**
	 * Return the item's parent.
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(Object)
	 */
	Object getParent();

	/**
	 * Return whether the item has children.
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(Object)
	 */
	boolean hasChildren();

	/**
	 * Return the item's children.
	 * 
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(Object)
	 */
	Object[] getChildren();


	/**
	 * An item tree content provider's manager is notified whenever the
	 * item's children have changed.
	 */
	interface Manager
		extends ItemStructuredContentProvider.Manager
	{
		/**
		 * The children for the specified element have changed.
		 * Update appropriately.
		 */
		void updateChildren(Object element);
	}
}
