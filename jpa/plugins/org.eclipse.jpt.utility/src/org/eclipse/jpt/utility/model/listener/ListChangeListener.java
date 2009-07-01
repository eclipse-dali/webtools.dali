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

import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;

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
	 * @param event A ListAddEvent describing the event source,
	 * the list that changed, the items that were added, and the index
	 * at which the items were added.
	 */
	void itemsAdded(ListAddEvent event);

	/**
	 * This method gets called when items are removed from a bound list.
	 * 
	 * @param event A ListRemoveEvent describing the event source,
	 * the list that changed, the items that were removed, and the index
	 * at which the items were removed.
	 */
	void itemsRemoved(ListRemoveEvent event);

	/**
	 * This method gets called when items in a bound list are replaced.
	 * 
	 * @param event A ListReplaceEvent describing the event source,
	 * the list that changed, the items that were added, the items that were
	 * replaced, and the index at which the items were replaced.
	 */
	void itemsReplaced(ListReplaceEvent event);

	/**
	 * This method gets called when items in a bound list are moved.
	 * 
	 * @param event A ListMoveEvent describing the event source,
	 * the list that changed, and the indices of where items were moved
	 * from and to.
	 */
	void itemsMoved(ListMoveEvent event);

	/**
	 * This method gets called when a bound list is cleared.
	 * 
	 * @param event A ListClearEvent object describing the event source 
	 * and the list that changed.
	 */
	void listCleared(ListClearEvent event);

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
	 */
//	void itemsChanged(ListChangeEvent event);

}
