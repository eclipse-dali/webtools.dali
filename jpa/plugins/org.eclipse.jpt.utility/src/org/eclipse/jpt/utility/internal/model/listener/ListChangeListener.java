/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.listener;

import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;

/**
 * A "list change" event gets fired whenever a model changes a "bound"
 * list. You can register a ListChangeListener with a source
 * model so as to be notified of any bound list updates.
 */
public interface ListChangeListener extends ChangeListener {

	/**
	 * This method gets called when items are added to a bound list.
	 * 
	 * @param event A ListChangeEvent describing the event source,
	 * the list that changed, the items that were added, and the index
	 * at which the items were added.
	 */
	void itemsAdded(ListChangeEvent event);

	/**
	 * This method gets called when items are removed from a bound list.
	 * 
	 * @param event A ListChangeEvent describing the event source,
	 * the list that changed, the items that were removed, and the index
	 * at which the items were removed.
	 */
	void itemsRemoved(ListChangeEvent event);

	/**
	 * This method gets called when items in a bound list are replaced.
	 * 
	 * @param event A ListChangeEvent describing the event source,
	 * the list that changed, the items that were added, the items that were
	 * replaced, and the index at which the items were replaced.
	 */
	void itemsReplaced(ListChangeEvent event);

	/**
	 * This method gets called when items in a bound list are moved.
	 * 
	 * @param event A ListChangeEvent describing the event source,
	 * the list that changed, and the indices of where items were moved
	 * from and to.
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

}
