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
 * Null implementation of ChangeEventDispatcher interface: Do nothing.
 */
public class NullChangeEventDispatcher
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
			INSTANCE = new NullChangeEventDispatcher();
		}
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NullChangeEventDispatcher() {
		super();
	}

	public void stateChanged(StateChangeListener listener, StateChangeEvent event) {
		// do nothing
	}

	public void propertyChanged(PropertyChangeListener listener, PropertyChangeEvent event) {
		// do nothing
	}

	public void itemsAdded(CollectionChangeListener listener, CollectionChangeEvent event) {
		// do nothing
	}

	public void itemsRemoved(CollectionChangeListener listener, CollectionChangeEvent event) {
		// do nothing
	}

	public void collectionCleared(CollectionChangeListener listener, CollectionChangeEvent event) {
		// do nothing
	}

	public void collectionChanged(CollectionChangeListener listener, CollectionChangeEvent event) {
		// do nothing
	}

	public void itemsAdded(ListChangeListener listener, ListChangeEvent event) {
		// do nothing
	}

	public void itemsRemoved(ListChangeListener listener, ListChangeEvent event) {
		// do nothing
	}

	public void itemsReplaced(ListChangeListener listener, ListChangeEvent event) {
		// do nothing
	}

	public void itemsMoved(ListChangeListener listener, ListChangeEvent event) {
		// do nothing
	}

	public void listCleared(ListChangeListener listener, ListChangeEvent event) {
		// do nothing
	}

	public void listChanged(ListChangeListener listener, ListChangeEvent event) {
		// do nothing
	}

	public void nodeAdded(TreeChangeListener listener, TreeChangeEvent event) {
		// do nothing
	}

	public void nodeRemoved(TreeChangeListener listener, TreeChangeEvent event) {
		// do nothing
	}

	public void treeCleared(TreeChangeListener listener, TreeChangeEvent event) {
		// do nothing
	}

	public void treeChanged(TreeChangeListener listener, TreeChangeEvent event) {
		// do nothing
	}

	/**
	 * Serializable singleton support
	 */
	private Object readResolve() {
		return instance();
	}

}
