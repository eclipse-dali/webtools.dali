/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model;

import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;

/**
 * Interface to be implemented by models that notify listeners of
 * changes to <em>bound</em> properties, collections, and/or lists.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see ChangeListener
 * @see StateChangeListener
 * @see PropertyChangeListener
 * @see CollectionChangeListener
 * @see ListChangeListener
 * @see org.eclipse.jpt.common.utility.internal.model.AbstractModel
 */
public interface Model {

	// ********** change **********

	/**
	 * Add a listener that listens to all change events.
	 * Throw an exception if the same listener is added more than once.
	 * The listener cannot be <code>null</code>.
	 */
	void addChangeListener(ChangeListener listener);

	/**
	 * Remove the specified change listener.
	 * Throw an exception if the listener is <code>null</code> or if the listener was never added.
	 */
	void removeChangeListener(ChangeListener listener);


	// ********** state change **********

	/**
	 * Add a listener that listens to all state change events.
	 * Throw an exception if the same listener is added more than once.
	 * The listener cannot be <code>null</code>.
	 */
	void addStateChangeListener(StateChangeListener listener);

	/**
	 * Remove the specified state change listener.
	 * Throw an exception if the listener is <code>null</code> or if the listener was never added.
	 */
	void removeStateChangeListener(StateChangeListener listener);


	// ********** property change **********

	/**
	 * Add a listener that listens to all property change events with
	 * the specified property name.
	 * Throw an exception if the same listener is added more than once.
	 * The listener cannot be <code>null</code>.
	 */
	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	/**
	 * Remove a listener that listens to all property change events,
	 * with the specified property name.
	 * Throw an exception if the listener is <code>null</code> or if the listener was never added.
	 */
	void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);


	// ********** collection change **********

	/**
	 * Add a listener that listens to all collection change events with
	 * the specified collection name.
	 * Throw an exception if the same listener is added more than once.
	 * The listener cannot be <code>null</code>.
	 */
	void addCollectionChangeListener(String collectionName, CollectionChangeListener listener);

	/**
	 * Remove a listener that listens to all collection change events,
	 * with the specified collection name.
	 * Throw an exception if the listener is <code>null</code> or if the listener was never added.
	 */
	void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener);


	// ********** list change **********

	/**
	 * Add a listener that listens to all list change events with
	 * the specified list name.
	 * Throw an exception if the same listener is added more than once.
	 * The listener cannot be <code>null</code>.
	 */
	void addListChangeListener(String listName, ListChangeListener listener);

	/**
	 * Remove a listener that listens to all list change events,
	 * with the specified list name.
	 * Throw an exception if the listener is <code>null</code> or if the listener was never added.
	 */
	void removeListChangeListener(String listName, ListChangeListener listener);
}
