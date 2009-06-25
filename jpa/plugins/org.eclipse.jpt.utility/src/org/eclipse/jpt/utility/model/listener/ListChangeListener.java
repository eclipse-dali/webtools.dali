/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.listener;

import org.eclipse.jpt.utility.model.event.ListChangeEvent;

/**
 * A "list change" event gets fired whenever a model changes a "bound"
 * list. You can register a ListChangeListener with a source
 * model so as to be notified of any bound list updates.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ListChangeListener extends ChangeListener {

	/**
	 * This method gets called when items are added to a bound list.
	 * 
	 * @param event A ListChangeEvent describing the event source,
	 * the list that changed, the items that were added, and the index
	 * at which the items were added.
	 * 
	 * @see ListChangeEvent#getIndex()
	 * @see ListChangeEvent#items()
	 * @see ListChangeEvent#itemsSize()
	 */
	void itemsAdded(ListChangeEvent event);

	/**
	 * This method gets called when items are removed from a bound list.
	 * 
	 * @param event A ListChangeEvent describing the event source,
	 * the list that changed, the items that were removed, and the index
	 * at which the items were removed.
	 * 
	 * @see ListChangeEvent#getIndex()
	 * @see ListChangeEvent#items()
	 * @see ListChangeEvent#itemsSize()
	 */
	void itemsRemoved(ListChangeEvent event);

	/**
	 * This method gets called when items in a bound list are replaced.
	 * 
	 * @param event A ListChangeEvent describing the event source,
	 * the list that changed, the items that were added, the items that were
	 * replaced, and the index at which the items were replaced.
	 * 
	 * @see ListChangeEvent#getIndex()
	 * @see ListChangeEvent#items()
	 * @see ListChangeEvent#itemsSize()
	 * @see ListChangeEvent#replacedItems()
	 * @see ListChangeEvent#replacedItemsSize()
	 */
	void itemsReplaced(ListChangeEvent event);

	/**
	 * This method gets called when items in a bound list are moved.
	 * 
	 * @param event A ListChangeEvent describing the event source,
	 * the list that changed, and the indices of where items were moved
	 * from and to.
	 * 
	 * @see ListChangeEvent#getSourceIndex()
	 * @see ListChangeEvent#getTargetIndex()
	 * @see ListChangeEvent#getMoveLength()
	 */
	void itemsMoved(ListChangeEvent event);

	/**
	 * This method gets called when a bound list is cleared.
	 * 
	 * @param event A ListChangeEvent object describing the event source 
	 * and the list that changed.
	 */
	void listCleared(ListChangeEvent event);

	/**
	 * This method gets called when a bound list is changed in a manner
	 * that is not easily characterized by the other methods in this interface.
	 * 
	 * @param event A ListChangeEvent object describing the event source 
	 * and the list that changed.
	 */
	void listChanged(ListChangeEvent event);

	/**
	 * This method gets called when the items in a bound list are changed.
	 * The list itself has not changed, but some significant aspect(s) of the
	 * objects contained by the list has changed.
	 * 
	 * @param event A ListChangeEvent describing the event source,
	 * the list whose items have changed, the items that changed, and the index
	 * at which the items were located.
	 * 
	 * @see ListChangeEvent#getIndex()
	 * @see ListChangeEvent#items()
	 * @see ListChangeEvent#itemsSize()
	 */
//	void itemsChanged(ListChangeEvent event);

}
