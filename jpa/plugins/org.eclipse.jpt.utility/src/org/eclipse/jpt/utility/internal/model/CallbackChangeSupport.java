/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model;

import java.util.Collection;
import java.util.EventListener;
import java.util.List;

import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.utility.model.event.TreeRemoveEvent;

/**
 * This change support class will notify listeners whenever one of the source's
 * aspects has changed. Only the aspect name is passed to the listener; no
 * event is generated. This allows the listeners to delegate to the change
 * support object verification that an aspect as actually changed. This is
 * useful for simple things like setting dirty flags, blanket validation, and
 * blanket sychronization; i.e. things that might be interested in the <em>name</em>
 * of the aspect that changed but not so much <em>how</em> the aspect changed.
 */
public class CallbackChangeSupport
	extends ChangeSupport
{
	protected final ListenerList<Listener> listenerList = new ListenerList<Listener>(Listener.class);
	private static final long serialVersionUID = 1L;


	public CallbackChangeSupport(Model source, Listener listener) {
		this(source);
		this.addListener(listener);
	}

	public CallbackChangeSupport(Model source) {
		super(source);
	}

	protected void aspectChanged(String aspectName) {
		for (Listener listener : this.listenerList.getListeners()) {
			listener.aspectChanged(aspectName);
		}
	}

	public void addListener(Listener listener) {
		this.listenerList.add(listener);
	}

	public void removeListener(Listener listener) {
		this.listenerList.remove(listener);
	}


	// ********** listener interface **********

	/**
	 * Listener that will be notified of any aspect changes.
	 */
	public interface Listener extends EventListener {

		/**
		 * The specified aspect changed.
		 */
		void aspectChanged(String aspectName);

	}


	// ********** state change support **********

	@Override
	public void fireStateChanged(StateChangeEvent event) {
		super.fireStateChanged(event);
		this.aspectChanged(null);
	}

	@Override
	public void fireStateChanged() {
		super.fireStateChanged();
		this.aspectChanged(null);
	}


	// ********** property change support **********

	@Override
	protected void firePropertyChanged_(PropertyChangeEvent event) {
		super.firePropertyChanged_(event);
		this.aspectChanged(event.getPropertyName());
	}

	@Override
	protected void firePropertyChanged_(String propertyName, Object oldValue, Object newValue) {
		super.firePropertyChanged_(propertyName, oldValue, newValue);
		this.aspectChanged(propertyName);
	}

	@Override
	protected void firePropertyChanged_(String propertyName, int oldValue, int newValue) {
		super.firePropertyChanged_(propertyName, oldValue, newValue);
		this.aspectChanged(propertyName);
	}

	@Override
	protected void firePropertyChanged_(String propertyName, boolean oldValue, boolean newValue) {
		super.firePropertyChanged_(propertyName, oldValue, newValue);
		this.aspectChanged(propertyName);
	}


	// ********** collection change support **********

	@Override
	protected void fireItemsAdded_(CollectionAddEvent event) {
		super.fireItemsAdded_(event);
		this.aspectChanged(event.getCollectionName());
	}

	@Override
	protected void fireItemsAdded_(String collectionName, Collection<?> addedItems) {
		super.fireItemsAdded_(collectionName, addedItems);
		this.aspectChanged(collectionName);
	}

	@Override
	public void fireItemAdded(String collectionName, Object addedItem) {
		super.fireItemAdded(collectionName, addedItem);
		this.aspectChanged(collectionName);
	}

	@Override
	protected void fireItemsRemoved_(CollectionRemoveEvent event) {
		super.fireItemsRemoved_(event);
		this.aspectChanged(event.getCollectionName());
	}

	@Override
	protected void fireItemsRemoved_(String collectionName, Collection<?> removedItems) {
		super.fireItemsRemoved_(collectionName, removedItems);
		this.aspectChanged(collectionName);
	}

	@Override
	public void fireItemRemoved(String collectionName, Object removedItem) {
		super.fireItemRemoved(collectionName, removedItem);
		this.aspectChanged(collectionName);
	}

	@Override
	public void fireCollectionCleared(CollectionClearEvent event) {
		super.fireCollectionCleared(event);
		this.aspectChanged(event.getCollectionName());
	}

	@Override
	public void fireCollectionCleared(String collectionName) {
		super.fireCollectionCleared(collectionName);
		this.aspectChanged(collectionName);
	}

	@Override
	public void fireCollectionChanged(CollectionChangeEvent event) {
		super.fireCollectionChanged(event);
		this.aspectChanged(event.getCollectionName());
	}

	@Override
	public void fireCollectionChanged(String collectionName, Collection<?> collection) {
		super.fireCollectionChanged(collectionName, collection);
		this.aspectChanged(collectionName);
	}


	// ********** list change support **********

	@Override
	protected void fireItemsAdded_(ListAddEvent event) {
		super.fireItemsAdded_(event);
		this.aspectChanged(event.getListName());
	}

	@Override
	protected void fireItemsAdded_(String listName, int index, List<?> addedItems) {
		super.fireItemsAdded_(listName, index, addedItems);
		this.aspectChanged(listName);
	}

	@Override
	public void fireItemAdded(String listName, int index, Object addedItem) {
		super.fireItemAdded(listName, index, addedItem);
		this.aspectChanged(listName);
	}

	@Override
	protected void fireItemsRemoved_(ListRemoveEvent event) {
		super.fireItemsRemoved_(event);
		this.aspectChanged(event.getListName());
	}

	@Override
	protected void fireItemsRemoved_(String listName, int index, List<?> removedItems) {
		super.fireItemRemoved(listName, index, removedItems);
		this.aspectChanged(listName);
	}

	@Override
	public void fireItemRemoved(String listName, int index, Object removedItem) {
		super.fireItemRemoved(listName, index, removedItem);
		this.aspectChanged(listName);
	}

	@Override
	protected void fireItemsReplaced_(ListReplaceEvent event) {
		super.fireItemsReplaced_(event);
		this.aspectChanged(event.getListName());
	}

	@Override
	protected void fireItemsReplaced_(String listName, int index, List<?> newItems, List<?> replacedItems) {
		super.fireItemsReplaced_(listName, index, newItems, replacedItems);
		this.aspectChanged(listName);
	}

	@Override
	protected void fireItemReplaced_(String listName, int index, Object newItem, Object replacedItem) {
		super.fireItemReplaced_(listName, index, newItem, replacedItem);
		this.aspectChanged(listName);
	}

	@Override
	protected void fireItemsMoved_(ListMoveEvent event) {
		super.fireItemsMoved_(event);
		this.aspectChanged(event.getListName());
	}

	@Override
	protected void fireItemsMoved_(String listName, int targetIndex, int sourceIndex, int length) {
		super.fireItemsMoved_(listName, targetIndex, sourceIndex, length);
		this.aspectChanged(listName);
	}

	@Override
	public void fireListCleared(ListClearEvent event) {
		super.fireListCleared(event);
		this.aspectChanged(event.getListName());
	}

	@Override
	public void fireListCleared(String listName) {
		super.fireListCleared(listName);
		this.aspectChanged(listName);
	}

	@Override
	public void fireListChanged(ListChangeEvent event) {
		super.fireListChanged(event);
		this.aspectChanged(event.getListName());
	}

	@Override
	public void fireListChanged(String listName, List<?> list) {
		super.fireListChanged(listName, list);
		this.aspectChanged(listName);
	}


	// ********** tree change support **********

	@Override
	public void fireNodeAdded(TreeAddEvent event) {
		super.fireNodeAdded(event);
		this.aspectChanged(event.getTreeName());
	}

	@Override
	public void fireNodeAdded(String treeName, List<?> path) {
		super.fireNodeAdded(treeName, path);
		this.aspectChanged(treeName);
	}

	@Override
	public void fireNodeRemoved(TreeRemoveEvent event) {
		super.fireNodeRemoved(event);
		this.aspectChanged(event.getTreeName());
	}

	@Override
	public void fireNodeRemoved(String treeName, List<?> path) {
		super.fireNodeRemoved(treeName, path);
		this.aspectChanged(treeName);
	}

	@Override
	public void fireTreeCleared(TreeClearEvent event) {
		super.fireTreeCleared(event);
		this.aspectChanged(event.getTreeName());
	}

	@Override
	public void fireTreeCleared(String treeName) {
		super.fireTreeCleared(treeName);
		this.aspectChanged(treeName);
	}

	@Override
	public void fireTreeChanged(TreeChangeEvent event) {
		super.fireTreeChanged(event);
		this.aspectChanged(event.getTreeName());
	}

	@Override
	public void fireTreeChanged(String treeName, Collection<?> nodes) {
		super.fireTreeChanged(treeName, nodes);
		this.aspectChanged(treeName);
	}

}
