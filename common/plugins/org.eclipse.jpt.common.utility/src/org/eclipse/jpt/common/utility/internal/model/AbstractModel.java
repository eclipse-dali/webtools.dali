/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;

/**
 * Reasonable implementation of the {@link Model} protocol
 * with numerous convenience methods.
 * 
 * @see ChangeSupport
 */
public abstract class AbstractModel
	implements Model
{
	/**
	 * Delegate state/property/collection/list change support to this
	 * helper object.
	 */
	protected final ChangeSupport changeSupport;


	// ********** constructors/initialization **********

	/**
	 * Construct a model that uses the specified exception handler to handle
	 * any exceptions thrown by listeners. The exception handler cannot be
	 * <code>null</code>.
	 */
	protected AbstractModel() {
		super();
		this.changeSupport = this.buildChangeSupport();
	}

	/**
	 * Allow subclasses to tweak the change support used.
	 */
	protected ChangeSupport buildChangeSupport() {
		return new ChangeSupport(this);
	}


	// ********** change **********

	/**
	 * @see ChangeSupport#addChangeListener(ChangeListener)
	 */
	public void addChangeListener(ChangeListener listener) {
		this.changeSupport.addChangeListener(listener);
	}

	/**
	 * @see ChangeSupport#removeChangeListener(ChangeListener)
	 */
	public void removeChangeListener(ChangeListener listener) {
		this.changeSupport.removeChangeListener(listener);
	}

	/**
	 * @see ChangeSupport#hasAnyChangeListeners()
	 */
	public boolean hasAnyChangeListeners() {
		return (this.changeSupport != null) && this.changeSupport.hasAnyChangeListeners();
	}

	/**
	 * Return whether the model has no change listeners.
	 */
	public boolean hasNoChangeListeners() {
		return ! this.hasAnyChangeListeners();
	}


	// ********** state change **********

	/**
	 * @see ChangeSupport#addStateChangeListener(StateChangeListener)
	 */
	public void addStateChangeListener(StateChangeListener listener) {
		this.changeSupport.addStateChangeListener(listener);
	}

	/**
	 * @see ChangeSupport#removeStateChangeListener(StateChangeListener)
	 */
	public void removeStateChangeListener(StateChangeListener listener) {
		this.changeSupport.removeStateChangeListener(listener);
	}

	/**
	 * @see ChangeSupport#hasAnyStateChangeListeners()
	 */
	public boolean hasAnyStateChangeListeners() {
		return (this.changeSupport != null) && this.changeSupport.hasAnyStateChangeListeners();
	}

	/**
	 * Return whether the model has no state change listeners.
	 */
	public boolean hasNoStateChangeListeners() {
		return ! this.hasAnyStateChangeListeners();
	}

	/**
	 * @see ChangeSupport#fireStateChanged(StateChangeEvent)
	 */
	protected final void fireStateChanged(StateChangeEvent event) {
		this.changeSupport.fireStateChanged(event);
	}

	/**
	 * @see ChangeSupport#fireStateChanged()
	 */
	protected final void fireStateChanged() {
		this.changeSupport.fireStateChanged();
	}


	// ********** property change **********

	/**
	 * @see ChangeSupport#addPropertyChangeListener(String, PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @see ChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * @see ChangeSupport#hasAnyPropertyChangeListeners(String)
	 */
	public boolean hasAnyPropertyChangeListeners(String propertyName) {
		return (this.changeSupport != null) && this.changeSupport.hasAnyPropertyChangeListeners(propertyName);
	}

	/**
	 * Return whether the model has no property change listeners that will
	 * be notified when the specified property has changed.
	 */
	public boolean hasNoPropertyChangeListeners(String propertyName) {
		return ! this.hasAnyPropertyChangeListeners(propertyName);
	}

	/**
	 * @see ChangeSupport#firePropertyChanged(PropertyChangeEvent)
	 */
	protected final boolean firePropertyChanged(PropertyChangeEvent event) {
		return this.changeSupport.firePropertyChanged(event);
	}

	/**
	 * @see ChangeSupport#firePropertyChanged(String, Object, Object)
	 */
	protected final boolean firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
		return this.changeSupport.firePropertyChanged(propertyName, oldValue, newValue);
	}

	/**
	 * @see ChangeSupport#firePropertyChanged(String, int, int)
	 */
	protected final boolean firePropertyChanged(String propertyName, int oldValue, int newValue) {
		return this.changeSupport.firePropertyChanged(propertyName, oldValue, newValue);
	}

	/**
	 * @see ChangeSupport#firePropertyChanged(String, boolean, boolean)
	 */
	protected final boolean firePropertyChanged(String propertyName, boolean oldValue, boolean newValue) {
		return this.changeSupport.firePropertyChanged(propertyName, oldValue, newValue);
	}

	/**
	 * Implied <code>null</code> "old" value.
	 * @see #firePropertyChanged(String, Object, Object)
	 */
	protected final boolean firePropertyChanged(String propertyName, Object newValue) {
		return this.firePropertyChanged(propertyName, null, newValue);
	}


	// ********** collection change **********

	/**
	 * @see ChangeSupport#addCollectionChangeListener(String, CollectionChangeListener)
	 */
	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.changeSupport.addCollectionChangeListener(collectionName, listener);
	}

	/**
	 * @see ChangeSupport#removeCollectionChangeListener(String, CollectionChangeListener)
	 */
	public void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.changeSupport.removeCollectionChangeListener(collectionName, listener);
	}

	/**
	 * @see ChangeSupport#hasAnyCollectionChangeListeners(String)
	 */
	public boolean hasAnyCollectionChangeListeners(String collectionName) {
		return (this.changeSupport != null) && this.changeSupport.hasAnyCollectionChangeListeners(collectionName);
	}

	/**
	 * Return whether the model has no collection change listeners that will
	 * be notified when the specified collection has changed.
	 */
	public boolean hasNoCollectionChangeListeners(String collectionName) {
		return ! this.hasAnyCollectionChangeListeners(collectionName);
	}

	/**
	 * @see ChangeSupport#fireItemsAdded(CollectionAddEvent)
	 */
	protected final boolean fireItemsAdded(CollectionAddEvent event) {
		return this.changeSupport.fireItemsAdded(event);
	}

	/**
	 * @see ChangeSupport#fireItemsAdded(String, Collection)
	 */
	protected final boolean fireItemsAdded(String collectionName, Collection<?> addedItems) {
		return this.changeSupport.fireItemsAdded(collectionName, addedItems);
	}

	/**
	 * @see ChangeSupport#fireItemAdded(String, Object)
	 */
	protected final void fireItemAdded(String collectionName, Object addedItem) {
		this.changeSupport.fireItemAdded(collectionName, addedItem);
	}

	/**
	 * @see ChangeSupport#fireItemsRemoved(CollectionRemoveEvent)
	 */
	protected final boolean fireItemsRemoved(CollectionRemoveEvent event) {
		return this.changeSupport.fireItemsRemoved(event);
	}

	/**
	 * @see ChangeSupport#fireItemsRemoved(String, Collection)
	 */
	protected final boolean fireItemsRemoved(String collectionName, Collection<?> removedItems) {
		return this.changeSupport.fireItemsRemoved(collectionName, removedItems);
	}

	/**
	 * @see ChangeSupport#fireItemRemoved(String, Object)
	 */
	protected final void fireItemRemoved(String collectionName, Object removedItem) {
		this.changeSupport.fireItemRemoved(collectionName, removedItem);
	}

	/**
	 * @see ChangeSupport#fireCollectionCleared(CollectionClearEvent)
	 */
	protected final void fireCollectionCleared(CollectionClearEvent event) {
		this.changeSupport.fireCollectionCleared(event);
	}

	/**
	 * @see ChangeSupport#fireCollectionCleared(String)
	 */
	protected final void fireCollectionCleared(String collectionName) {
		this.changeSupport.fireCollectionCleared(collectionName);
	}

	protected final void fireCollectionChanged(CollectionChangeEvent event) {
		this.changeSupport.fireCollectionChanged(event);
	}

	protected final void fireCollectionChanged(String collectionName, Collection<?> collection) {
		this.changeSupport.fireCollectionChanged(collectionName, collection);
	}

	/**
	 * @see ChangeSupport#addItemToCollection(Object, Collection, String)
	 */
	protected <E> boolean addItemToCollection(E item, Collection<E> collection, String collectionName) {
		return this.changeSupport.addItemToCollection(item, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#addItemsToCollection(Object[], Collection, String)
	 */
	protected <E> boolean addItemsToCollection(E[] items, Collection<E> collection, String collectionName) {
		return this.changeSupport.addItemsToCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#addItemsToCollection(Collection, Collection, String)
	 */
	protected <E> boolean addItemsToCollection(Collection<? extends E> items, Collection<E> collection, String collectionName) {
		return this.changeSupport.addItemsToCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#addItemsToCollection(Iterable, Collection, String)
	 */
	protected <E> boolean addItemsToCollection(Iterable<? extends E> items, Collection<E> collection, String collectionName) {
		return this.changeSupport.addItemsToCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#addItemsToCollection(Iterator, Collection, String)
	 */
	protected <E> boolean addItemsToCollection(Iterator<? extends E> items, Collection<E> collection, String collectionName) {
		return this.changeSupport.addItemsToCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#removeItemFromCollection(Object, Collection, String)
	 */
	protected boolean removeItemFromCollection(Object item, Collection<?> collection, String collectionName) {
		return this.changeSupport.removeItemFromCollection(item, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromCollection(Object[], Collection, String)
	 */
	protected boolean removeItemsFromCollection(Object[] items, Collection<?> collection, String collectionName) {
		return this.changeSupport.removeItemsFromCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromCollection(Collection, Collection, String)
	 */
	protected boolean removeItemsFromCollection(Collection<?> items, Collection<?> collection, String collectionName) {
		return this.changeSupport.removeItemsFromCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromCollection(Iterable, Collection, String)
	 */
	protected boolean removeItemsFromCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.changeSupport.removeItemsFromCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromCollection(Iterator, Collection, String)
	 */
	protected boolean removeItemsFromCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		return this.changeSupport.removeItemsFromCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#retainItemsInCollection(Object[], Collection, String)
	 */
	protected boolean retainItemsInCollection(Object[] items, Collection<?> collection, String collectionName) {
		return this.changeSupport.retainItemsInCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#retainItemsInCollection(Collection, Collection, String)
	 */
	protected boolean retainItemsInCollection(Collection<?> items, Collection<?> collection, String collectionName) {
		return this.changeSupport.retainItemsInCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#retainItemsInCollection(Iterable, Collection, String)
	 */
	protected boolean retainItemsInCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.changeSupport.retainItemsInCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#retainItemsInCollection(Iterator, Collection, String)
	 */
	protected boolean retainItemsInCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		return this.changeSupport.retainItemsInCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#clearCollection(Collection, String)
	 */
	protected boolean clearCollection(Collection<?> collection, String collectionName) {
		return this.changeSupport.clearCollection(collection, collectionName);
	}

	/**
	 * @see ChangeSupport#synchronizeCollection(Collection, Collection, String)
	 */
	protected <E> boolean synchronizeCollection(Collection<E> newCollection, Collection<E> collection, String collectionName) {
		return this.changeSupport.synchronizeCollection(newCollection, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#synchronizeCollection(Iterable, Collection, String)
	 */
	protected <E> boolean synchronizeCollection(Iterable<E> newCollection, Collection<E> collection, String collectionName) {
		return this.changeSupport.synchronizeCollection(newCollection, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#synchronizeCollection(Iterator, Collection, String)
	 */
	protected <E> boolean synchronizeCollection(Iterator<E> newCollection, Collection<E> collection, String collectionName) {
		return this.changeSupport.synchronizeCollection(newCollection, collection, collectionName);
	}


	// ********** list change **********

	/**
	 * @see ChangeSupport#addListChangeListener(String, ListChangeListener)
	 */
	public void addListChangeListener(String listName, ListChangeListener listener) {
		this.changeSupport.addListChangeListener(listName, listener);
	}

	/**
	 * @see ChangeSupport#removeListChangeListener(String, ListChangeListener)
	 */
	public void removeListChangeListener(String listName, ListChangeListener listener) {
		this.changeSupport.removeListChangeListener(listName, listener);
	}

	/**
	 * @see ChangeSupport#hasAnyListChangeListeners(String)
	 */
	public boolean hasAnyListChangeListeners(String listName) {
		return (this.changeSupport != null) && this.changeSupport.hasAnyListChangeListeners(listName);
	}

	/**
	 * Return whether the model has no list change listeners that will
	 * be notified when the specified list has changed.
	 */
	public boolean hasNoListChangeListeners(String listName) {
		return ! this.hasAnyListChangeListeners(listName);
	}

	/**
	 * @see ChangeSupport#fireItemsAdded(ListAddEvent)
	 */
	protected final boolean fireItemsAdded(ListAddEvent event) {
		return this.changeSupport.fireItemsAdded(event);
	}

	/**
	 * @see ChangeSupport#fireItemsAdded(String, int, List)
	 */
	protected final boolean fireItemsAdded(String listName, int index, List<?> addedItems) {
		return this.changeSupport.fireItemsAdded(listName, index, addedItems);
	}

	/**
	 * @see ChangeSupport#fireItemAdded(String, int, Object)
	 */
	protected final void fireItemAdded(String listName, int index, Object addedItem) {
		this.changeSupport.fireItemAdded(listName, index, addedItem);
	}

	/**
	 * @see ChangeSupport#fireItemsRemoved(ListRemoveEvent)
	 */
	protected final boolean fireItemsRemoved(ListRemoveEvent event) {
		return this.changeSupport.fireItemsRemoved(event);
	}

	/**
	 * @see ChangeSupport#fireItemsRemoved(String, int, List)
	 */
	protected final boolean fireItemsRemoved(String listName, int index, List<?> removedItems) {
		return this.changeSupport.fireItemsRemoved(listName, index, removedItems);
	}

	/**
	 * @see ChangeSupport#fireItemRemoved(String, int, Object)
	 */
	protected final void fireItemRemoved(String listName, int index, Object removedItem) {
		this.changeSupport.fireItemRemoved(listName, index, removedItem);
	}

	/**
	 * @see ChangeSupport#fireItemsReplaced(ListReplaceEvent)
	 */
	protected final boolean fireItemsReplaced(ListReplaceEvent event) {
		return this.changeSupport.fireItemsReplaced(event);
	}

	/**
	 * @see ChangeSupport#fireItemsReplaced(String, int, List, List)
	 */
	protected final <E> boolean fireItemsReplaced(String listName, int index, List<? extends E> newItems, List<E> replacedItems) {
		return this.changeSupport.fireItemsReplaced(listName, index, newItems, replacedItems);
	}

	/**
	 * @see ChangeSupport#fireItemReplaced(String, int, Object, Object)
	 */
	protected final boolean fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
		return this.changeSupport.fireItemReplaced(listName, index, newItem, replacedItem);
	}

	/**
	 * @see ChangeSupport#fireItemsMoved(ListMoveEvent)
	 */
	protected final boolean fireItemsMoved(ListMoveEvent event) {
		return this.changeSupport.fireItemsMoved(event);
	}

	/**
	 * @see ChangeSupport#fireItemsMoved(String, int, int, int)
	 */
	protected final <E> boolean fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
		return this.changeSupport.fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	/**
	 * @see ChangeSupport#fireItemMoved(String, int, int)
	 */
	protected final boolean fireItemMoved(String listName, int targetIndex, int sourceIndex) {
		return this.changeSupport.fireItemMoved(listName, targetIndex, sourceIndex);
	}

	/**
	 * @see ChangeSupport#fireListCleared(ListClearEvent)
	 */
	protected final void fireListCleared(ListClearEvent event) {
		this.changeSupport.fireListCleared(event);
	}

	/**
	 * @see ChangeSupport#fireListCleared(String)
	 */
	protected final void fireListCleared(String listName) {
		this.changeSupport.fireListCleared(listName);
	}

	protected final void fireListChanged(ListChangeEvent event) {
		this.changeSupport.fireListChanged(event);
	}

	protected final void fireListChanged(String listName, List<?> list) {
		this.changeSupport.fireListChanged(listName, list);
	}

	/**
	 * @see ChangeSupport#addItemToList(int, Object, List, String)
	 */
	protected <E> void addItemToList(int index, E item, List<E> list, String listName) {
		this.changeSupport.addItemToList(index, item, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemToList(Object, List, String)
	 */
	protected <E> boolean addItemToList(E item, List<E> list, String listName) {
		return this.changeSupport.addItemToList(item, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(int, Object[], List, String)
	 */
	protected <E> boolean addItemsToList(int index, E[] items, List<E> list, String listName) {
		return this.changeSupport.addItemsToList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(int, Collection, List, String)
	 */
	protected <E> boolean addItemsToList(int index, Collection<? extends E> items, List<E> list, String listName) {
		return this.changeSupport.addItemsToList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(int, Iterable, List, String)
	 */
	protected <E> boolean addItemsToList(int index, Iterable<? extends E> items, List<E> list, String listName) {
		return this.changeSupport.addItemsToList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(int, Iterator, List, String)
	 */
	protected <E> boolean addItemsToList(int index, Iterator<? extends E> items, List<E> list, String listName) {
		return this.changeSupport.addItemsToList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(Object[], List, String)
	 */
	protected <E> boolean addItemsToList(E[] items, List<E> list, String listName) {
		return this.changeSupport.addItemsToList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(Collection, List, String)
	 */
	protected <E> boolean addItemsToList(Collection<? extends E> items, List<E> list, String listName) {
		return this.changeSupport.addItemsToList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(Iterable, List, String)
	 */
	protected <E> boolean addItemsToList(Iterable<? extends E> items, List<E> list, String listName) {
		return this.changeSupport.addItemsToList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(Iterator, List, String)
	 */
	protected <E> boolean addItemsToList(Iterator<? extends E> items, List<E> list, String listName) {
		return this.changeSupport.addItemsToList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemFromList(int, List, String)
	 */
	protected <E> E removeItemFromList(int index, List<E> list, String listName) {
		return this.changeSupport.removeItemFromList(index, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemFromList(Object, List, String)
	 */
	protected boolean removeItemFromList(Object item, List<?> list, String listName) {
		return this.changeSupport.removeItemFromList(item, list, listName);
	}

	/**
	 * @see ChangeSupport#removeRangeFromList(int, int, List, String)
	 */
	protected <E> List<E> removeRangeFromList(int beginIndex, int endIndex, List<E> list, String listName) {
		return this.changeSupport.removeRangeFromList(beginIndex, endIndex, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(int, List, String)
	 */
	protected <E> List<E> removeItemsFromList(int index, List<E> list, String listName) {
		return this.changeSupport.removeItemsFromList(index, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(int, int, List, String)
	 */
	protected <E> List<E> removeItemsFromList(int index, int length, List<E> list, String listName) {
		return this.changeSupport.removeItemsFromList(index, length, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(Object[], List, String)
	 */
	protected boolean removeItemsFromList(Object[] items, List<?> list, String listName) {
		return this.changeSupport.removeItemsFromList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(Collection, List, String)
	 */
	protected boolean removeItemsFromList(Collection<?> items, List<?> list, String listName) {
		return this.changeSupport.removeItemsFromList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(Iterable, List, String)
	 */
	protected boolean removeItemsFromList(Iterable<?> items, List<?> list, String listName) {
		return this.changeSupport.removeItemsFromList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(Iterator, List, String)
	 */
	protected boolean removeItemsFromList(Iterator<?> items, List<?> list, String listName) {
		return this.changeSupport.removeItemsFromList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#retainItemsInList(Object[], List, String)
	 */
	protected boolean retainItemsInList(Object[] items, List<?> list, String listName) {
		return this.changeSupport.retainItemsInList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#retainItemsInList(Collection, List, String)
	 */
	protected boolean retainItemsInList(Collection<?> items, List<?> list, String listName) {
		return this.changeSupport.retainItemsInList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#retainItemsInList(Iterable, List, String)
	 */
	protected boolean retainItemsInList(Iterable<?> items, List<?> list, String listName) {
		return this.changeSupport.retainItemsInList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#retainItemsInList(Iterator, List, String)
	 */
	protected boolean retainItemsInList(Iterator<?> items, List<?> list, String listName) {
		return this.changeSupport.retainItemsInList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#setItemInList(int, Object, List, String)
	 */
	protected <E> E setItemInList(int index, E item, List<E> list, String listName) {
		return this.changeSupport.setItemInList(index, item, list, listName);
	}

	/**
	 * @see ChangeSupport#replaceItemInList(Object, Object, List, String)
	 */
	protected <E> int replaceItemInList(E oldItem, E newItem, List<E> list, String listName) {
		return this.changeSupport.replaceItemInList(oldItem, newItem, list, listName);
	}

	/**
	 * @see ChangeSupport#setItemsInList(int, Object[], List, String)
	 */
	protected <E> List<E> setItemsInList(int index, E[] items, List<E> list, String listName) {
		return this.changeSupport.setItemsInList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#setItemsInList(int, List, List, String)
	 */
	protected <E> List<E> setItemsInList(int index, List<? extends E> items, List<E> list, String listName) {
		return this.changeSupport.setItemsInList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#moveItemsInList(int, int, int, List, String)
	 */
	protected <E> void moveItemsInList(int targetIndex, int sourceIndex, int length, List<E> list, String listName) {
		this.changeSupport.moveItemsInList(targetIndex, sourceIndex, length, list, listName);
	}

	/**
	 * @see ChangeSupport#moveItemInList(int, int, List, String)
	 */
	protected <E> void moveItemInList(int targetIndex, int sourceIndex, List<E> list, String listName) {
		this.changeSupport.moveItemInList(targetIndex, sourceIndex, list, listName);
	}

	/**
	 * @see ChangeSupport#moveItemInList(int, Object, List, String)
	 */
	protected <E> void moveItemInList(int targetIndex, E item, List<E> list, String listName) {
		this.changeSupport.moveItemInList(targetIndex, item, list, listName);
	}

	/**
	 * @see ChangeSupport#clearList(List, String)
	 */
	protected boolean clearList(List<?> list, String listName) {
		return this.changeSupport.clearList(list, listName);
	}

	/**
	 * @see ChangeSupport#synchronizeList(List, List, String)
	 */
	protected <E> boolean synchronizeList(List<? extends E> newList, List<E> list, String listName) {
		return this.changeSupport.synchronizeList(newList, list, listName);
	}

	/**
	 * @see ChangeSupport#synchronizeList(Iterable, List, String)
	 */
	protected <E> boolean synchronizeList(Iterable<? extends E> newList, List<E> list, String listName) {
		return this.changeSupport.synchronizeList(newList, list, listName);
	}

	/**
	 * @see ChangeSupport#synchronizeList(Iterator, List, String)
	 */
	protected <E> boolean synchronizeList(Iterator<? extends E> newList, List<E> list, String listName) {
		return this.changeSupport.synchronizeList(newList, list, listName);
	}


	// ********** misc **********

	/**
	 * e.g. <code>"ClassName[00-F3-EE-42](add'l info)"</code>
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringBuilderTools.appendHashCodeToString(sb, this);
		sb.append('(');
		int len = sb.length();
		this.toString(sb);
		if (sb.length() == len) {
			sb.deleteCharAt(len - 1);
		} else {
			sb.append(')');
		}
		return sb.toString();
	}

	/**
	 * This method is public so one model can call a nested abstract model's
	 * {@link #toString(StringBuilder)}.
	 */
	public void toString(@SuppressWarnings("unused") StringBuilder sb) {
		// subclasses should override this to do something a bit more helpful
	}
}
