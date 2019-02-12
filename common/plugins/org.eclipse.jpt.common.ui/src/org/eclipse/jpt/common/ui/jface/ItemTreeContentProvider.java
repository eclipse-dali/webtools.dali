/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.jface;

/**
 * Implementations of this interface can be used to maintain the content of a
 * specific (non-root) tree item. The implementation will monitor the item for
 * any changes that affect the item's list of children and forward them
 * appropriately to the {@link Manager}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jface.viewers.IContentProvider
 * @see org.eclipse.jface.viewers.ITreeContentProvider
 */
public interface ItemTreeContentProvider
	extends ItemContentProvider
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
	 * Dispose the item content provider.
	 * Stop listening to the provider's item, as appropriate.
	 */
	void dispose();


	/**
	 * An item tree content provider's manager is notified whenever the
	 * item's list of children has changed.
	 */
	interface Manager
		extends ItemContentProvider.Manager
	{
		/**
		 * The list of children for the specified item has changed.
		 * The specified elements were added and removed. Add providers
		 * for the added elements, dispose the providers for the removed
		 * elements, and refresh the view. This method must be called from the
		 * UI event thread.
		 * 
		 * @see org.eclipse.jface.viewers.StructuredViewer#refresh(Object, boolean)
		 */
		void childrenChanged(Object item, Iterable<?> addedChildren, Iterable<?> removedChildren);
	}


	/**
	 * Factory interface for constructing item tree content providers
	 * for both root and branch/leaf items.
	 * Typically used by {@link ItemTreeContentProvider.Manager item tree content
	 * provider managers}.
	 * <p>
	 * <strong>NB:</strong> This interface extends the
	 * {@link ItemStructuredContentProvider.Factory item structured content
	 * factory interface}, defining different factory methods for:<ul>
	 * <li>the tree root item
	 * <li>tree branch/leaf items
	 * </ul>
	 */
	interface Factory
		extends ItemStructuredContentProvider.Factory
	{
		/**
		 * Build a tree content provider for the specified item.
		 * Do not return <code>null</code>.
		 * 
		 * @see org.eclipse.jpt.common.ui.internal.jface.NullItemTreeContentProvider
		 */
		ItemTreeContentProvider buildProvider(Object item, Object parent, Manager manager);
	}
}
