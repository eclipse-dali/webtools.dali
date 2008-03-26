/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model;

import java.util.Collection;
import java.util.List;

import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;

/**
 * This change support class changes the behavior of the standard
 * ChangeSupport in several ways:
 * 	- All events fired by the source must specify the single aspect.
 * 	- Listeners are required to be either "generic" listeners or
 * 	    listeners of the single aspect.
 */
public class SingleAspectChangeSupport
	extends ChangeSupport
{
	protected final Class<? extends ChangeListener> listenerClass;
	protected final String aspectName;

	private static final long serialVersionUID = 1L;


	// ********** constructor **********

	public SingleAspectChangeSupport(Model source, Class<? extends ChangeListener> listenerClass, String aspectName) {
		super(source);
		this.listenerClass = listenerClass;
		this.aspectName = aspectName;
	}


	// ********** internal behavior **********

	private UnsupportedOperationException unsupportedOperationException() {
		return new UnsupportedOperationException("This Model supports only changes for the listener type \"" + this.listenerClass.getName()
				+ "\" and the aspect \"" + this.aspectName + "\"");
	}

	private void check(Class<? extends ChangeListener> lClass, String aName) {
		if (lClass != this.listenerClass) {
			throw new IllegalArgumentException("This Model supports only changes for the listener type \"" + this.listenerClass.getName() + "\" : \"" + lClass.getName() + "\"");
		}
		if (aName != this.aspectName) {
			throw new IllegalArgumentException("This Model supports only changes for the aspect \"" + this.aspectName + "\" : \"" + aName + "\"");
		}
	}

	@Override
	protected <T extends ChangeListener> void addListener(String aName, Class<T> lClass, T listener) {
		this.check(lClass, aName);
		super.addListener(aName, lClass, listener);
	}

	@Override
	protected <T extends ChangeListener> void removeListener(String aName, Class<T> lClass, T listener) {
		this.check(lClass, aName);
		super.removeListener(aName, lClass, listener);
	}


	// ********** internal queries **********

	@Override
	protected boolean hasAnyListeners(Class<? extends ChangeListener> lClass, String aName) {
		this.check(lClass, aName);
		return super.hasAnyListeners(lClass, aName);
	}


	// ********** state change support **********

	@Override
	public void fireStateChanged(StateChangeEvent event) {
		throw this.unsupportedOperationException();
	}

	@Override
	public void fireStateChanged() {
		throw this.unsupportedOperationException();
	}


	// ********** property change support **********

	@Override
	public void firePropertyChanged(PropertyChangeEvent event) {
		this.check(PROPERTY_CHANGE_LISTENER_CLASS, event.getPropertyName());
		super.firePropertyChanged(event);
	}

	@Override
	public void firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
		this.check(PROPERTY_CHANGE_LISTENER_CLASS, propertyName);
		super.firePropertyChanged(propertyName, oldValue, newValue);
	}

	@Override
	public void firePropertyChanged(String propertyName, int oldValue, int newValue) {
		this.check(PROPERTY_CHANGE_LISTENER_CLASS, propertyName);
		super.firePropertyChanged(propertyName, oldValue, newValue);
	}

	@Override
	public void firePropertyChanged(String propertyName, boolean oldValue, boolean newValue) {
		this.check(PROPERTY_CHANGE_LISTENER_CLASS, propertyName);
		super.firePropertyChanged(propertyName, oldValue, newValue);
	}


	// ********** collection change support **********

	@Override
	public void fireItemsAdded(CollectionChangeEvent event) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, event.getCollectionName());
		super.fireItemsAdded(event);
	}

	@Override
	public void fireItemsAdded(String collectionName, Collection<?> addedItems) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
		super.fireItemsAdded(collectionName, addedItems);
	}

	@Override
	public void fireItemAdded(String collectionName, Object addedItem) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
		super.fireItemAdded(collectionName, addedItem);
	}

	@Override
	public void fireItemsRemoved(CollectionChangeEvent event) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, event.getCollectionName());
		super.fireItemsRemoved(event);
	}

	@Override
	public void fireItemsRemoved(String collectionName, Collection<?> removedItems) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
		super.fireItemsRemoved(collectionName, removedItems);
	}

	@Override
	public void fireItemRemoved(String collectionName, Object removedItem) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
		super.fireItemRemoved(collectionName, removedItem);
	}

	@Override
	public void fireCollectionCleared(CollectionChangeEvent event) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, event.getCollectionName());
		super.fireCollectionCleared(event);
	}

	@Override
	public void fireCollectionCleared(String collectionName) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
		super.fireCollectionCleared(collectionName);
	}

	@Override
	public void fireCollectionChanged(CollectionChangeEvent event) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, event.getCollectionName());
		super.fireCollectionChanged(event);
	}

	@Override
	public void fireCollectionChanged(String collectionName) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
		super.fireCollectionChanged(collectionName);
	}


	// ********** list change support **********

	@Override
	public void fireItemsAdded(ListChangeEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		super.fireItemsAdded(event);
	}

	@Override
	public void fireItemsAdded(String listName, int index, List<?> addedItems) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireItemsAdded(listName, index, addedItems);
	}

	@Override
	public void fireItemAdded(String listName, int index, Object addedItem) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireItemAdded(listName, index, addedItem);
	}

	@Override
	public void fireItemsRemoved(ListChangeEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		super.fireItemsRemoved(event);
	}

	@Override
	public void fireItemsRemoved(String listName, int index, List<?> removedItems) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireItemsRemoved(listName, index, removedItems);
	}

	@Override
	public void fireItemRemoved(String listName, int index, Object removedItem) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireItemRemoved(listName, index, removedItem);
	}

	@Override
	public void fireItemsReplaced(ListChangeEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		super.fireItemsReplaced(event);
	}

	@Override
	public void fireItemsReplaced(String listName, int index, List<?> newItems, List<?> replacedItems) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireItemsReplaced(listName, index, newItems, replacedItems);
	}

	@Override
	public void fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireItemReplaced(listName, index, newItem, replacedItem);
	}

	@Override
	public void fireItemsMoved(ListChangeEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		super.fireItemsMoved(event);
	}

	@Override
	public void fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	@Override
	public void fireListCleared(ListChangeEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		super.fireListCleared(event);
	}

	@Override
	public void fireListCleared(String listName) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireListCleared(listName);
	}

	@Override
	public void fireListChanged(ListChangeEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		super.fireListChanged(event);
	}

	@Override
	public void fireListChanged(String listName) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireListChanged(listName);
	}


	// ********** tree change support **********

	@Override
	public void fireNodeAdded(TreeChangeEvent event) {
		this.check(TREE_CHANGE_LISTENER_CLASS, event.getTreeName());
		super.fireNodeAdded(event);
	}

	@Override
	public void fireNodeAdded(String treeName, Object[] path) {
		this.check(TREE_CHANGE_LISTENER_CLASS, treeName);
		super.fireNodeAdded(treeName, path);
	}

	@Override
	public void fireNodeRemoved(TreeChangeEvent event) {
		this.check(TREE_CHANGE_LISTENER_CLASS, event.getTreeName());
		super.fireNodeRemoved(event);
	}

	@Override
	public void fireNodeRemoved(String treeName, Object[] path) {
		this.check(TREE_CHANGE_LISTENER_CLASS, treeName);
		super.fireNodeRemoved(treeName, path);
	}

	@Override
	public void fireTreeCleared(TreeChangeEvent event) {
		this.check(TREE_CHANGE_LISTENER_CLASS, event.getTreeName());
		super.fireTreeCleared(event);
	}

	@Override
	public void fireTreeCleared(String treeName, Object[] path) {
		this.check(TREE_CHANGE_LISTENER_CLASS, treeName);
		super.fireTreeCleared(treeName, path);
	}

	@Override
	public void fireTreeChanged(TreeChangeEvent event) {
		this.check(TREE_CHANGE_LISTENER_CLASS, event.getTreeName());
		super.fireTreeChanged(event);
	}

	@Override
	public void fireTreeChanged(String treeName, Object[] path) {
		this.check(TREE_CHANGE_LISTENER_CLASS, treeName);
		super.fireTreeChanged(treeName, path);
	}

}
