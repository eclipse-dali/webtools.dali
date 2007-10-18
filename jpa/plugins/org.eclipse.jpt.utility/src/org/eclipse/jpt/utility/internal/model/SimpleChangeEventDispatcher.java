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

import java.io.Serializable;

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
 * Straightforward implementation of ChangeEventDispatcher interface:
 * Just forward the change notification directly to the listener.
 */
public class SimpleChangeEventDispatcher
	implements ChangeEventDispatcher, Serializable
{
	// singleton
	private static ChangeEventDispatcher INSTANCE;

	private static final long serialVersionUID = 1L;


	/**
	 * Return the singleton.
	 */
	public synchronized static ChangeEventDispatcher instance() {
		if (INSTANCE == null) {
			INSTANCE = new SimpleChangeEventDispatcher();
		}
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private SimpleChangeEventDispatcher() {
		super();
	}

	public void stateChanged(StateChangeListener listener, StateChangeEvent event) {
		listener.stateChanged(event);
	}

	public void propertyChanged(PropertyChangeListener listener, PropertyChangeEvent event) {
		listener.propertyChanged(event);
	}

	public void itemsAdded(CollectionChangeListener listener, CollectionChangeEvent event) {
		listener.itemsAdded(event);
	}

	public void itemsRemoved(CollectionChangeListener listener, CollectionChangeEvent event) {
		listener.itemsRemoved(event);
	}

	public void collectionCleared(CollectionChangeListener listener, CollectionChangeEvent event) {
		listener.collectionCleared(event);
	}

	public void collectionChanged(CollectionChangeListener listener, CollectionChangeEvent event) {
		listener.collectionChanged(event);
	}

	public void itemsAdded(ListChangeListener listener, ListChangeEvent event) {
		listener.itemsAdded(event);
	}

	public void itemsRemoved(ListChangeListener listener, ListChangeEvent event) {
		listener.itemsRemoved(event);
	}

	public void itemsReplaced(ListChangeListener listener, ListChangeEvent event) {
		listener.itemsReplaced(event);
	}

	public void itemsMoved(ListChangeListener listener, ListChangeEvent event) {
		listener.itemsMoved(event);
	}

	public void listCleared(ListChangeListener listener, ListChangeEvent event) {
		listener.listCleared(event);
	}

	public void listChanged(ListChangeListener listener, ListChangeEvent event) {
		listener.listChanged(event);
	}

	public void nodeAdded(TreeChangeListener listener, TreeChangeEvent event) {
		listener.nodeAdded(event);
	}

	public void nodeRemoved(TreeChangeListener listener, TreeChangeEvent event) {
		listener.nodeRemoved(event);
	}

	public void treeCleared(TreeChangeListener listener, TreeChangeEvent event) {
		listener.treeCleared(event);
	}

	public void treeChanged(TreeChangeListener listener, TreeChangeEvent event) {
		listener.treeChanged(event);
	}

	/**
	 * Serializable singleton support
	 */
	private Object readResolve() {
		return instance();
	}

}
