/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model;

import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.TreeChangeListener;

/**
 * Interface to be implemented by models that notify listeners of
 * changes to bound properties, collections, lists, and/or trees.
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
	 * regardless of the property ID associated with that event.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Add a listener that listens to all property change events with
	 * the specified property ID.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	/**
	 * Remove a listener that listens to all property change events,
	 * regardless of the property ID associated with that event.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Remove a listener that listens to all property change events,
	 * with the specified property ID.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);


	// ********** collection change **********

	/**
	 * Add a listener that listens to all collection change events,
	 * regardless of the collection ID associated with that event.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addCollectionChangeListener(CollectionChangeListener listener);

	/**
	 * Add a listener that listens to all collection change events with
	 * the specified collection ID.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addCollectionChangeListener(String collectionName, CollectionChangeListener listener);

	/**
	 * Remove a listener that listens to all collection change events,
	 * regardless of the collection ID associated with that event.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeCollectionChangeListener(CollectionChangeListener listener);

	/**
	 * Remove a listener that listens to all collection change events,
	 * with the specified collection ID.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener);


	// ********** list change **********

	/**
	 * Add a listener that listens to all list change events,
	 * regardless of the list ID associated with that event.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addListChangeListener(ListChangeListener listener);

	/**
	 * Add a listener that listens to all list change events with
	 * the specified list ID.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addListChangeListener(String listName, ListChangeListener listener);

	/**
	 * Remove a listener that listens to all list change events,
	 * regardless of the list ID associated with that event.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeListChangeListener(ListChangeListener listener);

	/**
	 * Remove a listener that listens to all list change events,
	 * with the specified list ID.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeListChangeListener(String listName, ListChangeListener listener);


	// ********** tree change **********

	/**
	 * Add a listener that listens to all tree change events,
	 * regardless of the tree ID associated with that event.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addTreeChangeListener(TreeChangeListener listener);

	/**
	 * Add a listener that listens to all tree change events with
	 * the specified tree ID.
	 * The same listener may be added more than once and will be called
	 * as many times as it is added. The listener cannot be null.
	 */
	void addTreeChangeListener(String treeName, TreeChangeListener listener);

	/**
	 * Remove a listener that listens to all tree change events,
	 * regardless of the tree ID associated with that event.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeTreeChangeListener(TreeChangeListener listener);

	/**
	 * Remove a listener that listens to all tree change events,
	 * with the specified tree ID.
	 * If the listener was added more than once, it will be notified one less
	 * time after being removed. An exception will be thrown if the
	 * listener is null or if the listener was never added.
	 */
	void removeTreeChangeListener(String treeName, TreeChangeListener listener);

}
