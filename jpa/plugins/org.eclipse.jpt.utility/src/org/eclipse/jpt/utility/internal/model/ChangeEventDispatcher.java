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

import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.TreeChangeListener;

/**
 * Add yet another level of indirection to change support to allow
 * clients to change how and when listener notification occurs.
 * The primary use would be to dispatch the notification to the
 * AWT event queue, so the UI will be updated in a single thread.
 */
public interface ChangeEventDispatcher {

	/**
	 * Notify the specified listener that the source's state changed,
	 * as described in the specified event.
	 */
	void stateChanged(StateChangeListener listener, StateChangeEvent event);

	/**
	 * Notify the specified listener that a bound property changed,
	 * as described in the specified event.
	 */
	void propertyChanged(PropertyChangeListener listener, PropertyChangeEvent event);

	/**
	 * Notify the specified listener that a bound collection changed,
	 * as described in the specified event.
	 */
	void itemsAdded(CollectionChangeListener listener, CollectionChangeEvent event);

	/**
	 * Notify the specified listener that a bound collection changed,
	 * as described in the specified event.
	 */
	void itemsRemoved(CollectionChangeListener listener, CollectionChangeEvent event);

	/**
	 * Notify the specified listener that a bound collection changed,
	 * as described in the specified event.
	 */
	void collectionCleared(CollectionChangeListener listener, CollectionChangeEvent event);

	/**
	 * Notify the specified listener that a bound collection changed,
	 * as described in the specified event.
	 */
	void collectionChanged(CollectionChangeListener listener, CollectionChangeEvent event);

	/**
	 * Notify the specified listener that a bound list changed,
	 * as described in the specified event.
	 */
	void itemsAdded(ListChangeListener listener, ListChangeEvent event);

	/**
	 * Notify the specified listener that a bound list changed,
	 * as described in the specified event.
	 */
	void itemsRemoved(ListChangeListener listener, ListChangeEvent event);

	/**
	 * Notify the specified listener that a bound list changed,
	 * as described in the specified event.
	 */
	void itemsReplaced(ListChangeListener listener, ListChangeEvent event);

	/**
	 * Notify the specified listener that a bound list changed,
	 * as described in the specified event.
	 */
	void itemsMoved(ListChangeListener listener, ListChangeEvent event);

	/**
	 * Notify the specified listener that a bound list changed,
	 * as described in the specified event.
	 */
	void listCleared(ListChangeListener listener, ListChangeEvent event);

	/**
	 * Notify the specified listener that a bound list changed,
	 * as described in the specified event.
	 */
	void listChanged(ListChangeListener listener, ListChangeEvent event);

	/**
	 * Notify the specified listener that a bound tree changed,
	 * as described in the specified event.
	 */
	void nodeAdded(TreeChangeListener listener, TreeChangeEvent event);

	/**
	 * Notify the specified listener that a bound tree changed,
	 * as described in the specified event.
	 */
	void nodeRemoved(TreeChangeListener listener, TreeChangeEvent event);

	/**
	 * Notify the specified listener that a bound tree changed,
	 * as described in the specified event.
	 */
	void treeCleared(TreeChangeListener listener, TreeChangeEvent event);

	/**
	 * Notify the specified listener that a bound tree changed,
	 * as described in the specified event.
	 */
	void treeChanged(TreeChangeListener listener, TreeChangeEvent event);

}
