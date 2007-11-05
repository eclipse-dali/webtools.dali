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

import java.util.Collection;
import java.util.List;

import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.internal.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ChangeListener;

/**
 * This support class changes the behavior of the standard
 * ChangeSupport in several ways:
 * 	- All events fired by the source must specify the expected aspect name.
 * 	- Listeners are required to be either "generic" listeners or
 * 	    listeners of the aspect name.
 * 	- The "aspect-specific" listeners are stored alongside the "generic"
 *     listeners, improving performance a bit (in terms of both time and space)
 */
public class SingleAspectChangeSupport extends ChangeSupport {
	private final String aspectName;
	private static final long serialVersionUID = 1L;

	public SingleAspectChangeSupport(Model source, String aspectName) {
		super(source);
		this.aspectName = aspectName;
	}


	// ******************** internal behavior ********************

	private UnsupportedOperationException unsupportedOperationException() {
		return new UnsupportedOperationException("This Model supports only changes for the aspect \"" + this.aspectName + "\"");
	}

	private void checkAspectName(String aName) {
		if (aName != this.aspectName) {
			throw new IllegalArgumentException("This Model supports only changes for the aspect \"" + this.aspectName + "\" : \"" + aName + "\"");
		}
	}

	@Override
	protected <T extends ChangeListener> void addListener(String aName, Class<T> listenerClass, T listener) {
		this.checkAspectName(aName);
		// redirect to "generic" listeners collection
		this.addListener(listenerClass, listener);
	}

	@Override
	protected <T extends ChangeListener> void removeListener(String aName, Class<T> listenerClass, T listener) {
		this.checkAspectName(aName);
		// redirect to "generic" listeners collection
		this.removeListener(listenerClass, listener);
	}


	// ******************** internal queries ********************

	@Override
	protected boolean hasAnyListeners(Class<? extends ChangeListener> listenerClass, String aName) {
		this.checkAspectName(aName);
		// redirect to "generic" listeners collection
		return this.hasAnyListeners(listenerClass);
	}


	// ******************** state change support ********************

	@Override
	public void fireStateChanged(StateChangeEvent event) {
		throw this.unsupportedOperationException();
	}

	@Override
	public void fireStateChanged() {
		throw this.unsupportedOperationException();
	}


	// ******************** property change support ********************

	@Override
	public void firePropertyChanged(PropertyChangeEvent event) {
		this.checkAspectName(event.propertyName());
		super.firePropertyChanged(event);
	}

	@Override
	public void firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
		this.checkAspectName(propertyName);
		super.firePropertyChanged(propertyName, oldValue, newValue);
	}

	@Override
	public void firePropertyChanged(String propertyName, int oldValue, int newValue) {
		this.checkAspectName(propertyName);
		super.firePropertyChanged(propertyName, oldValue, newValue);
	}

	@Override
	public void firePropertyChanged(String propertyName, boolean oldValue, boolean newValue) {
		this.checkAspectName(propertyName);
		super.firePropertyChanged(propertyName, oldValue, newValue);
	}


	// ******************** collection change support ********************

	@Override
	public void fireItemsAdded(CollectionChangeEvent event) {
		this.checkAspectName(event.collectionName());
		super.fireItemsAdded(event);
	}

	@Override
	public void fireItemsAdded(String collectionName, Collection<?> addedItems) {
		this.checkAspectName(collectionName);
		super.fireItemsAdded(collectionName, addedItems);
	}

	@Override
	public void fireItemAdded(String collectionName, Object addedItem) {
		this.checkAspectName(collectionName);
		super.fireItemAdded(collectionName, addedItem);
	}

	@Override
	public void fireItemsRemoved(CollectionChangeEvent event) {
		this.checkAspectName(event.collectionName());
		super.fireItemsRemoved(event);
	}

	@Override
	public void fireItemsRemoved(String collectionName, Collection<?> removedItems) {
		this.checkAspectName(collectionName);
		super.fireItemsRemoved(collectionName, removedItems);
	}

	@Override
	public void fireItemRemoved(String collectionName, Object removedItem) {
		this.checkAspectName(collectionName);
		super.fireItemRemoved(collectionName, removedItem);
	}

	@Override
	public void fireCollectionCleared(CollectionChangeEvent event) {
		this.checkAspectName(event.collectionName());
		super.fireCollectionCleared(event);
	}

	@Override
	public void fireCollectionCleared(String collectionName) {
		this.checkAspectName(collectionName);
		super.fireCollectionCleared(collectionName);
	}

	@Override
	public void fireCollectionChanged(CollectionChangeEvent event) {
		this.checkAspectName(event.collectionName());
		super.fireCollectionChanged(event);
	}

	@Override
	public void fireCollectionChanged(String collectionName) {
		this.checkAspectName(collectionName);
		super.fireCollectionChanged(collectionName);
	}


	// ******************** list change support ********************

	@Override
	public void fireItemsAdded(ListChangeEvent event) {
		this.checkAspectName(event.listName());
		super.fireItemsAdded(event);
	}

	@Override
	public void fireItemsAdded(String listName, int index, List<?> addedItems) {
		this.checkAspectName(listName);
		super.fireItemsAdded(listName, index, addedItems);
	}

	@Override
	public void fireItemAdded(String listName, int index, Object addedItem) {
		this.checkAspectName(listName);
		super.fireItemAdded(listName, index, addedItem);
	}

	@Override
	public void fireItemsRemoved(ListChangeEvent event) {
		this.checkAspectName(event.listName());
		super.fireItemsRemoved(event);
	}

	@Override
	public void fireItemsRemoved(String listName, int index, List<?> removedItems) {
		this.checkAspectName(listName);
		super.fireItemsRemoved(listName, index, removedItems);
	}

	@Override
	public void fireItemRemoved(String listName, int index, Object removedItem) {
		this.checkAspectName(listName);
		super.fireItemRemoved(listName, index, removedItem);
	}

	@Override
	public void fireItemsReplaced(ListChangeEvent event) {
		this.checkAspectName(event.listName());
		super.fireItemsReplaced(event);
	}

	@Override
	public void fireItemsReplaced(String listName, int index, List<?> newItems, List<?> replacedItems) {
		this.checkAspectName(listName);
		super.fireItemsReplaced(listName, index, newItems, replacedItems);
	}

	@Override
	public void fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
		this.checkAspectName(listName);
		super.fireItemReplaced(listName, index, newItem, replacedItem);
	}

	@Override
	public void fireItemsMoved(ListChangeEvent event) {
		this.checkAspectName(event.listName());
		super.fireItemsMoved(event);
	}

	@Override
	public void fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
		this.checkAspectName(listName);
		super.fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	@Override
	public void fireListCleared(ListChangeEvent event) {
		this.checkAspectName(event.listName());
		super.fireListCleared(event);
	}

	@Override
	public void fireListCleared(String listName) {
		this.checkAspectName(listName);
		super.fireListCleared(listName);
	}

	@Override
	public void fireListChanged(ListChangeEvent event) {
		this.checkAspectName(event.listName());
		super.fireListChanged(event);
	}

	@Override
	public void fireListChanged(String listName) {
		this.checkAspectName(listName);
		super.fireListChanged(listName);
	}


	// ******************** tree change support ********************

	@Override
	public void fireNodeAdded(TreeChangeEvent event) {
		this.checkAspectName(event.treeName());
		super.fireNodeAdded(event);
	}

	@Override
	public void fireNodeAdded(String treeName, Object[] path) {
		this.checkAspectName(treeName);
		super.fireNodeAdded(treeName, path);
	}

	@Override
	public void fireNodeRemoved(TreeChangeEvent event) {
		this.checkAspectName(event.treeName());
		super.fireNodeRemoved(event);
	}

	@Override
	public void fireNodeRemoved(String treeName, Object[] path) {
		this.checkAspectName(treeName);
		super.fireNodeRemoved(treeName, path);
	}

	@Override
	public void fireTreeCleared(TreeChangeEvent event) {
		this.checkAspectName(event.treeName());
		super.fireTreeCleared(event);
	}

	@Override
	public void fireTreeCleared(String treeName, Object[] path) {
		this.checkAspectName(treeName);
		super.fireTreeCleared(treeName, path);
	}

	@Override
	public void fireTreeChanged(TreeChangeEvent event) {
		this.checkAspectName(event.treeName());
		super.fireTreeChanged(event);
	}

	@Override
	public void fireTreeChanged(String treeName, Object[] path) {
		this.checkAspectName(treeName);
		super.fireTreeChanged(treeName, path);
	}

}
