/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.jface;

/**
 * Implementations of this interface can be used to maintain the elements
 * of a specific <em>input element</em>. The implementation will monitor
 * the <em>input element</em> for any changes that affect the elements and
 * forward them appropriately to the {@link Manager}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jface.viewers.IContentProvider
 * @see org.eclipse.jface.viewers.IStructuredContentProvider
 */
public interface ItemStructuredContentProvider
	extends ItemContentProvider
{
	/**
	 * Return the <em>input element</em>'s elements.
	 * <strong>NB:</strong>
	 * When this method is called, the item is the view's <em>input element</em>.
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, Object, Object)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(Object)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(Object)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(Object)
	 */
	Object[] getElements();

	/**
	 * Dispose the item content provider.
	 * Stop listening to the provider's item, as appropriate.
	 */
	void dispose();


	/**
	 * An item structured content provider's manager is notified whenever the
	 * <em>input element</em>'s list of elements has changed.
	 */
	interface Manager
		extends ItemContentProvider.Manager
	{
		/**
		 * The content provider's <em>input element</em>'s list of elements has
		 * changed. The specified elements were added and removed. Add providers
		 * for the added elements, dispose the providers for the removed
		 * elements, and refresh the view. This method must be called from the
		 * UI event thread.
		 * 
		 * @see org.eclipse.jface.viewers.Viewer#refresh()
		 */
		void elementsChanged(Object input, Iterable<?> addedElements, Iterable<?> removedElements);
	}


	/**
	 * Factory interface for constructing an item structured content provider
	 * for a specified item.
	 */
	public interface Factory {
		/**
		 * Build a structured content provider for the specified
		 * <em>input element</em>.
		 * Do not return <code>null</code>.
		 * 
		 * @see org.eclipse.jpt.common.ui.internal.jface.NullItemStructuredContentProvider
		 */
		ItemStructuredContentProvider buildProvider(Object input, Manager manager);
	}
}
