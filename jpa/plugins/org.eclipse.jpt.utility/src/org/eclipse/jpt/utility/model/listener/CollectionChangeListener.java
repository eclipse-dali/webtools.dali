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

import java.util.EventListener;

import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;

/**
 * A "collection change" event gets fired whenever a model changes a "bound"
 * collection. You can register a CollectionChangeListener with a source
 * model so as to be notified of any bound collection updates.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface CollectionChangeListener extends EventListener {

	/**
	 * This method gets called when items are added to a bound collection.
	 * 
	 * @param event A CollectionAddEvent describing the event source,
	 * the collection that changed, and the items that were added.
	 */
	void itemsAdded(CollectionAddEvent event);

	/**
	 * This method gets called when items are removed from a bound collection.
	 * 
	 * @param event A CollectionRemoveEvent describing the event source,
	 * the collection that changed, and the items that were removed.
	 */
	void itemsRemoved(CollectionRemoveEvent event);

	/**
	 * This method gets called when a bound collection is cleared.
	 * 
	 * @param event A CollectionClearEvent describing the event source 
	 * and the collection that changed.
	 */
	void collectionCleared(CollectionClearEvent event);

	/**
	 * This method gets called when a bound collection is changed in a manner
	 * that is not easily characterized by the other methods in this interface.
	 * 
	 * @param event A CollectionChangeEvent describing the event source 
	 * and the collection that changed.
	 */
	void collectionChanged(CollectionChangeEvent event);

	/**
	 * This method gets called when the items in a bound collection are changed.
	 * The collection itself has not changed, but some significant aspect(s) of the
	 * objects contained by the collection has changed.
	 * 
	 * @param event A CollectionItemChangeEvent describing the event source,
	 * the collection whose items have changed, and the items that changed.
	 */
//	void itemsChanged(CollectionChangeEvent event);
//	void itemsReplaced(CollectionChangeEvent event);???

}
