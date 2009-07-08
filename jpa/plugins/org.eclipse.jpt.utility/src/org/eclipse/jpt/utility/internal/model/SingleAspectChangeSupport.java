/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
 * This change support class changes the behavior of the standard
 * change support in several ways:
 * 	- All events fired by the source must specify the single aspect.
 * 	- Listeners are required to be either "general purpose" listeners or
 * 	    listeners of the single aspect.
 */
public class SingleAspectChangeSupport
	extends ChangeSupport
{
	protected final Class<? extends EventListener> validListenerClass;
	protected final String validAspectName;

	private static final long serialVersionUID = 1L;


	// ********** constructor **********

	public SingleAspectChangeSupport(Model source, Class<? extends EventListener> validListenerClass, String validAspectName) {
		super(source);
		if ( ! validListenerClass.isAssignableFrom(this.getChangeListenerClass())) {
			throw new IllegalArgumentException("The change support's change listener class (" + this.getChangeListenerClass().getName() + //$NON-NLS-1$
					") does not extend the valid listener class: " + validListenerClass.getName()); //$NON-NLS-1$
		}
		this.validListenerClass = validListenerClass;
		this.validAspectName = validAspectName;
	}


	// ********** internal implementation **********

	private UnsupportedOperationException buildUnsupportedOperationException() {
		return new UnsupportedOperationException(
				"This Model supports only changes for the listener type \"" + this.validListenerClass.getName() //$NON-NLS-1$
				+ "\" and the aspect \"" + this.validAspectName + '"' //$NON-NLS-1$
			);
	}

	/**
	 * The listener can be either an instance of the valid listener class or
	 * the "general-purpose" change listener class (which should extend the
	 * the valid listener class).
	 */
	private void check(Class<? extends EventListener> listenerClass) {
		if ((listenerClass != this.getChangeListenerClass()) && (listenerClass != this.validListenerClass)) {
			throw new IllegalArgumentException(
					"This Model supports only changes for the listener type \"" + this.validListenerClass.getName() //$NON-NLS-1$
					+ "\" : \"" + listenerClass.getName() + '"' //$NON-NLS-1$
				);
		}
	}

	private void check(Class<? extends EventListener> listenerClass, String aspectName) {
		this.check(listenerClass);
		if ( ! aspectName.equals(this.validAspectName)) {
			throw new IllegalArgumentException(
					"This Model supports only changes for the aspect \"" + this.validAspectName //$NON-NLS-1$
					+ "\" : \"" + aspectName + '"' //$NON-NLS-1$
				);
		}
	}

	@Override
	protected synchronized <L extends EventListener> void addListener(Class<L> listenerClass, String aspectName, L listener) {
		this.check(listenerClass, aspectName);
		super.addListener(listenerClass, aspectName, listener);
	}

	@Override
	protected synchronized <L extends EventListener> void addListener(Class<L> listenerClass, L listener) {
		this.check(listenerClass);
		super.addListener(listenerClass, listener);
	}

	@Override
	protected synchronized <L extends EventListener> void removeListener(Class<L> listenerClass, String aspectName, L listener) {
		this.check(listenerClass, aspectName);
		super.removeListener(listenerClass, aspectName, listener);
	}

	@Override
	protected synchronized <L extends EventListener> void removeListener(Class<L> listenerClass, L listener) {
		this.check(listenerClass);
		super.removeListener(listenerClass, listener);
	}

	@Override
	protected <L extends EventListener> boolean hasAnyListeners(Class<L> listenerClass, String aspectName) {
		this.check(listenerClass, aspectName);
		return super.hasAnyListeners(listenerClass, aspectName);
	}

	@Override
	protected <L extends EventListener> boolean hasAnyListeners(Class<L> listenerClass) {
		this.check(listenerClass);
		return super.hasAnyListeners(listenerClass);
	}


	// ********** state change support **********

	@Override
	public void fireStateChanged(StateChangeEvent event) {
		throw this.buildUnsupportedOperationException();
	}

	@Override
	public void fireStateChanged() {
		throw this.buildUnsupportedOperationException();
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
	public void fireItemsAdded(CollectionAddEvent event) {
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
	public void fireItemsRemoved(CollectionRemoveEvent event) {
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
	public void fireCollectionCleared(CollectionClearEvent event) {
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
	public void fireCollectionChanged(String collectionName, Collection<?> collection) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
		super.fireCollectionChanged(collectionName, collection);
	}


	// ********** list change support **********

	@Override
	public void fireItemsAdded(ListAddEvent event) {
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
	public void fireItemsRemoved(ListRemoveEvent event) {
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
	public void fireItemsReplaced(ListReplaceEvent event) {
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
	public void fireItemsMoved(ListMoveEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		super.fireItemsMoved(event);
	}

	@Override
	public void fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	@Override
	public void fireListCleared(ListClearEvent event) {
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
	public void fireListChanged(String listName, List<?> list) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireListChanged(listName, list);
	}


	// ********** tree change support **********

	@Override
	public void fireNodeAdded(TreeAddEvent event) {
		this.check(TREE_CHANGE_LISTENER_CLASS, event.getTreeName());
		super.fireNodeAdded(event);
	}

	@Override
	public void fireNodeAdded(String treeName, List<?> path) {
		this.check(TREE_CHANGE_LISTENER_CLASS, treeName);
		super.fireNodeAdded(treeName, path);
	}

	@Override
	public void fireNodeRemoved(TreeRemoveEvent event) {
		this.check(TREE_CHANGE_LISTENER_CLASS, event.getTreeName());
		super.fireNodeRemoved(event);
	}

	@Override
	public void fireNodeRemoved(String treeName, List<?> path) {
		this.check(TREE_CHANGE_LISTENER_CLASS, treeName);
		super.fireNodeRemoved(treeName, path);
	}

	@Override
	public void fireTreeCleared(TreeClearEvent event) {
		this.check(TREE_CHANGE_LISTENER_CLASS, event.getTreeName());
		super.fireTreeCleared(event);
	}

	@Override
	public void fireTreeCleared(String treeName) {
		this.check(TREE_CHANGE_LISTENER_CLASS, treeName);
		super.fireTreeCleared(treeName);
	}

	@Override
	public void fireTreeChanged(TreeChangeEvent event) {
		this.check(TREE_CHANGE_LISTENER_CLASS, event.getTreeName());
		super.fireTreeChanged(event);
	}

	@Override
	public void fireTreeChanged(String treeName, Collection<?> nodes) {
		this.check(TREE_CHANGE_LISTENER_CLASS, treeName);
		super.fireTreeChanged(treeName, nodes);
	}

}
