/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model;

import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;

/**
 * Interface to be implemented by models that notify listeners of
 * changes to bound properties, collections, lists, and/or trees.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Model {

	// ********** state change **********

	/**
	 * Add a listener that listens to all state change events.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addStateChangeListener(StateChangeListener listener);

	/**
	 * Remove the specified state change listener. If the listener
	 * was added more than once, it will be notified one less time
	 * after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeStateChangeListener(StateChangeListener listener);


	// ********** property change **********

	/**
	 * Add a listener that listens to all property change events,
	 * regardless of the property name associated with that event.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Add a listener that listens to all property change events with
	 * the specified property name.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	/**
	 * Remove a listener that listens to all property change events,
	 * regardless of the property name associated with that event.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Remove a listener that listens to all property change events,
	 * with the specified property name.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);


	// ********** collection change **********

	/**
	 * Add a listener that listens to all collection change events,
	 * regardless of the collection name associated with that event.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addCollectionChangeListener(CollectionChangeListener listener);

	/**
	 * Add a listener that listens to all collection change events with
	 * the specified collection name.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addCollectionChangeListener(String collectionName, CollectionChangeListener listener);

	/**
	 * Remove a listener that listens to all collection change events,
	 * regardless of the collection name associated with that event.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeCollectionChangeListener(CollectionChangeListener listener);

	/**
	 * Remove a listener that listens to all collection change events,
	 * with the specified collection name.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener);


	// ********** list change **********

	/**
	 * Add a listener that listens to all list change events,
	 * regardless of the list name associated with that event.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addListChangeListener(ListChangeListener listener);

	/**
	 * Add a listener that listens to all list change events with
	 * the specified list name.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addListChangeListener(String listName, ListChangeListener listener);

	/**
	 * Remove a listener that listens to all list change events,
	 * regardless of the list name associated with that event.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeListChangeListener(ListChangeListener listener);

	/**
	 * Remove a listener that listens to all list change events,
	 * with the specified list name.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeListChangeListener(String listName, ListChangeListener listener);


	// ********** tree change **********

	/**
	 * Add a listener that listens to all tree change events,
	 * regardless of the tree name associated with that event.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addTreeChangeListener(TreeChangeListener listener);

	/**
	 * Add a listener that listens to all tree change events with
	 * the specified tree name.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addTreeChangeListener(String treeName, TreeChangeListener listener);

	/**
	 * Remove a listener that listens to all tree change events,
	 * regardless of the tree name associated with that event.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeTreeChangeListener(TreeChangeListener listener);

	/**
	 * Remove a listener that listens to all tree change events,
	 * with the specified tree name.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeTreeChangeListener(String treeName, TreeChangeListener listener);

}
