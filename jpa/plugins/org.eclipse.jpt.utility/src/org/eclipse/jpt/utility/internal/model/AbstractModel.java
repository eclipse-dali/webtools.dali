/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.utility.internal.StringTools;
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
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;

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
	 * Delegate state/property/collection/list/tree change support to this
	 * helper object. The change support object is <em>lazily-initialized</em>;
	 * so it may be <code>null</code>. The method {@link #getChangeSupport()}
	 * will initialize this field if it is <code>null</code>.
	 * <p>
	 * <strong>NB:</strong> We instantiate this when we fire events, even when
	 * we do not have any listeners (which is be implied if this is <code>null</code>).
	 * This allows the change support to have behavior tied to events even when
	 * we have no listeners.
	 * 
	 * @see AspectChangeSupport#aspectChanged(String)
	 */
	protected ChangeSupport changeSupport;


	// ********** constructors/initialization **********

	/**
	 * Default constructor.
	 */
	protected AbstractModel() {
		super();
	}

	/**
	 * This accessor will build the change support when required.
	 * This only helps reduce the footprint of a model that neither has any
	 * listeners added to it nor ever changes (fires any events).
	 */
	protected synchronized ChangeSupport getChangeSupport() {
		if (this.changeSupport == null) {
			this.changeSupport = this.buildChangeSupport();
		}
		return this.changeSupport;
	}

	/**
	 * Allow subclasses to tweak the change support used.
	 */
	protected ChangeSupport buildChangeSupport() {
		return new ChangeSupport(this);
	}


	// ********** change support **********

	/**
	 * @see ChangeSupport#addChangeListener(ChangeListener)
	 */
	public void addChangeListener(ChangeListener listener) {
		this.getChangeSupport().addChangeListener(listener);
	}

	/**
	 * @see ChangeSupport#removeChangeListener(ChangeListener)
	 */
	public void removeChangeListener(ChangeListener listener) {
		this.getChangeSupport().removeChangeListener(listener);
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


	// ********** state change support **********

	/**
	 * @see ChangeSupport#addStateChangeListener(StateChangeListener)
	 */
	public void addStateChangeListener(StateChangeListener listener) {
		this.getChangeSupport().addStateChangeListener(listener);
	}

	/**
	 * @see ChangeSupport#removeStateChangeListener(StateChangeListener)
	 */
	public void removeStateChangeListener(StateChangeListener listener) {
		this.getChangeSupport().removeStateChangeListener(listener);
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
		this.getChangeSupport().fireStateChanged(event);
	}

	/**
	 * @see ChangeSupport#fireStateChanged()
	 */
	protected final void fireStateChanged() {
		this.getChangeSupport().fireStateChanged();
	}


	// ********** property change support **********

	/**
	 * @see ChangeSupport#addPropertyChangeListener(String, PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.getChangeSupport().addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * @see ChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.getChangeSupport().removePropertyChangeListener(propertyName, listener);
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
		return this.getChangeSupport().firePropertyChanged(event);
	}

	/**
	 * @see ChangeSupport#firePropertyChanged(String, Object, Object)
	 */
	protected final boolean firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
		return this.getChangeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	/**
	 * @see ChangeSupport#firePropertyChanged(String, int, int)
	 */
	protected final boolean firePropertyChanged(String propertyName, int oldValue, int newValue) {
		return this.getChangeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	/**
	 * @see ChangeSupport#firePropertyChanged(String, boolean, boolean)
	 */
	protected final boolean firePropertyChanged(String propertyName, boolean oldValue, boolean newValue) {
		return this.getChangeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	/**
	 * Implied <code>null</code> "old" value.
	 * @see #firePropertyChanged(String, Object, Object)
	 */
	protected final boolean firePropertyChanged(String propertyName, Object newValue) {
		return this.firePropertyChanged(propertyName, null, newValue);
	}


	// ********** collection change support **********

	/**
	 * @see ChangeSupport#addCollectionChangeListener(String, CollectionChangeListener)
	 */
	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.getChangeSupport().addCollectionChangeListener(collectionName, listener);
	}

	/**
	 * @see ChangeSupport#removeCollectionChangeListener(String, CollectionChangeListener)
	 */
	public void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.getChangeSupport().removeCollectionChangeListener(collectionName, listener);
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
		return this.getChangeSupport().fireItemsAdded(event);
	}

	/**
	 * @see ChangeSupport#fireItemsAdded(String, Collection)
	 */
	protected final boolean fireItemsAdded(String collectionName, Collection<?> addedItems) {
		return this.getChangeSupport().fireItemsAdded(collectionName, addedItems);
	}

	/**
	 * @see ChangeSupport#fireItemAdded(String, Object)
	 */
	protected final void fireItemAdded(String collectionName, Object addedItem) {
		this.getChangeSupport().fireItemAdded(collectionName, addedItem);
	}

	/**
	 * @see ChangeSupport#fireItemsRemoved(CollectionRemoveEvent)
	 */
	protected final boolean fireItemsRemoved(CollectionRemoveEvent event) {
		return this.getChangeSupport().fireItemsRemoved(event);
	}

	/**
	 * @see ChangeSupport#fireItemsRemoved(String, Collection)
	 */
	protected final boolean fireItemsRemoved(String collectionName, Collection<?> removedItems) {
		return this.getChangeSupport().fireItemsRemoved(collectionName, removedItems);
	}

	/**
	 * @see ChangeSupport#fireItemRemoved(String, Object)
	 */
	protected final void fireItemRemoved(String collectionName, Object removedItem) {
		this.getChangeSupport().fireItemRemoved(collectionName, removedItem);
	}

	/**
	 * @see ChangeSupport#fireCollectionCleared(CollectionClearEvent)
	 */
	protected final void fireCollectionCleared(CollectionClearEvent event) {
		this.getChangeSupport().fireCollectionCleared(event);
	}

	/**
	 * @see ChangeSupport#fireCollectionCleared(String)
	 */
	protected final void fireCollectionCleared(String collectionName) {
		this.getChangeSupport().fireCollectionCleared(collectionName);
	}

	protected final void fireCollectionChanged(CollectionChangeEvent event) {
		this.getChangeSupport().fireCollectionChanged(event);
	}

	protected final void fireCollectionChanged(String collectionName, Collection<?> collection) {
		this.getChangeSupport().fireCollectionChanged(collectionName, collection);
	}

	/**
	 * @see ChangeSupport#addItemToCollection(Object, Collection, String)
	 */
	protected <E> boolean addItemToCollection(E item, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().addItemToCollection(item, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#addItemsToCollection(Object[], Collection, String)
	 */
	protected <E> boolean addItemsToCollection(E[] items, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().addItemsToCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#addItemsToCollection(Collection, Collection, String)
	 */
	protected <E> boolean addItemsToCollection(Collection<? extends E> items, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().addItemsToCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#addItemsToCollection(Iterable, Collection, String)
	 */
	protected <E> boolean addItemsToCollection(Iterable<? extends E> items, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().addItemsToCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#addItemsToCollection(Iterator, Collection, String)
	 */
	protected <E> boolean addItemsToCollection(Iterator<? extends E> items, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().addItemsToCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#removeItemFromCollection(Object, Collection, String)
	 */
	protected boolean removeItemFromCollection(Object item, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().removeItemFromCollection(item, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromCollection(Object[], Collection, String)
	 */
	protected boolean removeItemsFromCollection(Object[] items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().removeItemsFromCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromCollection(Collection, Collection, String)
	 */
	protected boolean removeItemsFromCollection(Collection<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().removeItemsFromCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromCollection(Iterable, Collection, String)
	 */
	protected boolean removeItemsFromCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().removeItemsFromCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromCollection(Iterator, Collection, String)
	 */
	protected boolean removeItemsFromCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().removeItemsFromCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#retainItemsInCollection(Object[], Collection, String)
	 */
	protected boolean retainItemsInCollection(Object[] items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().retainItemsInCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#retainItemsInCollection(Collection, Collection, String)
	 */
	protected boolean retainItemsInCollection(Collection<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().retainItemsInCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#retainItemsInCollection(Iterable, Collection, String)
	 */
	protected boolean retainItemsInCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().retainItemsInCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#retainItemsInCollection(Iterator, Collection, String)
	 */
	protected boolean retainItemsInCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().retainItemsInCollection(items, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#clearCollection(Collection, String)
	 */
	protected boolean clearCollection(Collection<?> collection, String collectionName) {
		return this.getChangeSupport().clearCollection(collection, collectionName);
	}

	/**
	 * @see ChangeSupport#synchronizeCollection(Collection, Collection, String)
	 */
	protected <E> boolean synchronizeCollection(Collection<E> newCollection, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().synchronizeCollection(newCollection, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#synchronizeCollection(Iterable, Collection, String)
	 */
	protected <E> boolean synchronizeCollection(Iterable<E> newCollection, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().synchronizeCollection(newCollection, collection, collectionName);
	}

	/**
	 * @see ChangeSupport#synchronizeCollection(Iterator, Collection, String)
	 */
	protected <E> boolean synchronizeCollection(Iterator<E> newCollection, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().synchronizeCollection(newCollection, collection, collectionName);
	}


	// ********** list change support **********

	/**
	 * @see ChangeSupport#addListChangeListener(String, ListChangeListener)
	 */
	public void addListChangeListener(String listName, ListChangeListener listener) {
		this.getChangeSupport().addListChangeListener(listName, listener);
	}

	/**
	 * @see ChangeSupport#removeListChangeListener(String, ListChangeListener)
	 */
	public void removeListChangeListener(String listName, ListChangeListener listener) {
		this.getChangeSupport().removeListChangeListener(listName, listener);
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
		return this.getChangeSupport().fireItemsAdded(event);
	}

	/**
	 * @see ChangeSupport#fireItemsAdded(String, int, List)
	 */
	protected final boolean fireItemsAdded(String listName, int index, List<?> addedItems) {
		return this.getChangeSupport().fireItemsAdded(listName, index, addedItems);
	}

	/**
	 * @see ChangeSupport#fireItemAdded(String, int, Object)
	 */
	protected final void fireItemAdded(String listName, int index, Object addedItem) {
		this.getChangeSupport().fireItemAdded(listName, index, addedItem);
	}

	/**
	 * @see ChangeSupport#fireItemsRemoved(ListRemoveEvent)
	 */
	protected final boolean fireItemsRemoved(ListRemoveEvent event) {
		return this.getChangeSupport().fireItemsRemoved(event);
	}

	/**
	 * @see ChangeSupport#fireItemsRemoved(String, int, List)
	 */
	protected final boolean fireItemsRemoved(String listName, int index, List<?> removedItems) {
		return this.getChangeSupport().fireItemsRemoved(listName, index, removedItems);
	}

	/**
	 * @see ChangeSupport#fireItemRemoved(String, int, Object)
	 */
	protected final void fireItemRemoved(String listName, int index, Object removedItem) {
		this.getChangeSupport().fireItemRemoved(listName, index, removedItem);
	}

	/**
	 * @see ChangeSupport#fireItemsReplaced(ListReplaceEvent)
	 */
	protected final boolean fireItemsReplaced(ListReplaceEvent event) {
		return this.getChangeSupport().fireItemsReplaced(event);
	}

	/**
	 * @see ChangeSupport#fireItemsReplaced(String, int, List, List)
	 */
	protected final <E> boolean fireItemsReplaced(String listName, int index, List<? extends E> newItems, List<E> replacedItems) {
		return this.getChangeSupport().fireItemsReplaced(listName, index, newItems, replacedItems);
	}

	/**
	 * @see ChangeSupport#fireItemReplaced(String, int, Object, Object)
	 */
	protected final boolean fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
		return this.getChangeSupport().fireItemReplaced(listName, index, newItem, replacedItem);
	}

	/**
	 * @see ChangeSupport#fireItemsMoved(ListMoveEvent)
	 */
	protected final boolean fireItemsMoved(ListMoveEvent event) {
		return this.getChangeSupport().fireItemsMoved(event);
	}

	/**
	 * @see ChangeSupport#fireItemsMoved(String, int, int, int)
	 */
	protected final <E> boolean fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
		return this.getChangeSupport().fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	/**
	 * @see ChangeSupport#fireItemMoved(String, int, int)
	 */
	protected final boolean fireItemMoved(String listName, int targetIndex, int sourceIndex) {
		return this.getChangeSupport().fireItemMoved(listName, targetIndex, sourceIndex);
	}

	/**
	 * @see ChangeSupport#fireListCleared(ListClearEvent)
	 */
	protected final void fireListCleared(ListClearEvent event) {
		this.getChangeSupport().fireListCleared(event);
	}

	/**
	 * @see ChangeSupport#fireListCleared(String)
	 */
	protected final void fireListCleared(String listName) {
		this.getChangeSupport().fireListCleared(listName);
	}

	protected final void fireListChanged(ListChangeEvent event) {
		this.getChangeSupport().fireListChanged(event);
	}

	protected final void fireListChanged(String listName, List<?> list) {
		this.getChangeSupport().fireListChanged(listName, list);
	}

	/**
	 * @see ChangeSupport#addItemToList(int, Object, List, String)
	 */
	protected <E> void addItemToList(int index, E item, List<E> list, String listName) {
		this.getChangeSupport().addItemToList(index, item, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemToList(Object, List, String)
	 */
	protected <E> boolean addItemToList(E item, List<E> list, String listName) {
		return this.getChangeSupport().addItemToList(item, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(int, Object[], List, String)
	 */
	protected <E> boolean addItemsToList(int index, E[] items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(int, Collection, List, String)
	 */
	protected <E> boolean addItemsToList(int index, Collection<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(int, Iterable, List, String)
	 */
	protected <E> boolean addItemsToList(int index, Iterable<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(int, Iterator, List, String)
	 */
	protected <E> boolean addItemsToList(int index, Iterator<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(Object[], List, String)
	 */
	protected <E> boolean addItemsToList(E[] items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(Collection, List, String)
	 */
	protected <E> boolean addItemsToList(Collection<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(Iterable, List, String)
	 */
	protected <E> boolean addItemsToList(Iterable<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#addItemsToList(Iterator, List, String)
	 */
	protected <E> boolean addItemsToList(Iterator<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemFromList(int, List, String)
	 */
	protected <E> E removeItemFromList(int index, List<E> list, String listName) {
		return this.getChangeSupport().removeItemFromList(index, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemFromList(Object, List, String)
	 */
	protected boolean removeItemFromList(Object item, List<?> list, String listName) {
		return this.getChangeSupport().removeItemFromList(item, list, listName);
	}

	/**
	 * @see ChangeSupport#removeRangeFromList(int, int, List, String)
	 */
	protected <E> List<E> removeRangeFromList(int beginIndex, int endIndex, List<E> list, String listName) {
		return this.getChangeSupport().removeRangeFromList(beginIndex, endIndex, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(int, List, String)
	 */
	protected <E> List<E> removeItemsFromList(int index, List<E> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(index, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(int, int, List, String)
	 */
	protected <E> List<E> removeItemsFromList(int index, int length, List<E> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(index, length, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(Object[], List, String)
	 */
	protected boolean removeItemsFromList(Object[] items, List<?> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(Collection, List, String)
	 */
	protected boolean removeItemsFromList(Collection<?> items, List<?> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(Iterable, List, String)
	 */
	protected boolean removeItemsFromList(Iterable<?> items, List<?> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#removeItemsFromList(Iterator, List, String)
	 */
	protected boolean removeItemsFromList(Iterator<?> items, List<?> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#retainItemsInList(Object[], List, String)
	 */
	protected boolean retainItemsInList(Object[] items, List<?> list, String listName) {
		return this.getChangeSupport().retainItemsInList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#retainItemsInList(Collection, List, String)
	 */
	protected boolean retainItemsInList(Collection<?> items, List<?> list, String listName) {
		return this.getChangeSupport().retainItemsInList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#retainItemsInList(Iterable, List, String)
	 */
	protected boolean retainItemsInList(Iterable<?> items, List<?> list, String listName) {
		return this.getChangeSupport().retainItemsInList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#retainItemsInList(Iterator, List, String)
	 */
	protected boolean retainItemsInList(Iterator<?> items, List<?> list, String listName) {
		return this.getChangeSupport().retainItemsInList(items, list, listName);
	}

	/**
	 * @see ChangeSupport#setItemInList(int, Object, List, String)
	 */
	protected <E> E setItemInList(int index, E item, List<E> list, String listName) {
		return this.getChangeSupport().setItemInList(index, item, list, listName);
	}

	/**
	 * @see ChangeSupport#replaceItemInList(Object, Object, List, String)
	 */
	protected <E> int replaceItemInList(E oldItem, E newItem, List<E> list, String listName) {
		return this.getChangeSupport().replaceItemInList(oldItem, newItem, list, listName);
	}

	/**
	 * @see ChangeSupport#setItemsInList(int, Object[], List, String)
	 */
	protected <E> List<E> setItemsInList(int index, E[] items, List<E> list, String listName) {
		return this.getChangeSupport().setItemsInList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#setItemsInList(int, List, List, String)
	 */
	protected <E> List<E> setItemsInList(int index, List<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().setItemsInList(index, items, list, listName);
	}

	/**
	 * @see ChangeSupport#moveItemsInList(int, int, int, List, String)
	 */
	protected <E> void moveItemsInList(int targetIndex, int sourceIndex, int length, List<E> list, String listName) {
		this.getChangeSupport().moveItemsInList(targetIndex, sourceIndex, length, list, listName);
	}

	/**
	 * @see ChangeSupport#moveItemInList(int, int, List, String)
	 */
	protected <E> void moveItemInList(int targetIndex, int sourceIndex, List<E> list, String listName) {
		this.getChangeSupport().moveItemInList(targetIndex, sourceIndex, list, listName);
	}

	/**
	 * @see ChangeSupport#moveItemInList(int, Object, List, String)
	 */
	protected <E> void moveItemInList(int targetIndex, E item, List<E> list, String listName) {
		this.getChangeSupport().moveItemInList(targetIndex, item, list, listName);
	}

	/**
	 * @see ChangeSupport#clearList(List, String)
	 */
	protected boolean clearList(List<?> list, String listName) {
		return this.getChangeSupport().clearList(list, listName);
	}

	/**
	 * @see ChangeSupport#synchronizeList(List, List, String)
	 */
	protected <E> boolean synchronizeList(List<? extends E> newList, List<E> list, String listName) {
		return this.getChangeSupport().synchronizeList(newList, list, listName);
	}

	/**
	 * @see ChangeSupport#synchronizeList(Iterable, List, String)
	 */
	protected <E> boolean synchronizeList(Iterable<? extends E> newList, List<E> list, String listName) {
		return this.getChangeSupport().synchronizeList(newList, list, listName);
	}

	/**
	 * @see ChangeSupport#synchronizeList(Iterator, List, String)
	 */
	protected <E> boolean synchronizeList(Iterator<? extends E> newList, List<E> list, String listName) {
		return this.getChangeSupport().synchronizeList(newList, list, listName);
	}


	// ********** tree change support **********

	/**
	 * @see ChangeSupport#addTreeChangeListener(String, TreeChangeListener)
	 */
	public void addTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.getChangeSupport().addTreeChangeListener(treeName, listener);
	}

	/**
	 * @see ChangeSupport#removeTreeChangeListener(String, TreeChangeListener)
	 */
	public void removeTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.getChangeSupport().removeTreeChangeListener(treeName, listener);
	}

	/**
	 * @see ChangeSupport#hasAnyTreeChangeListeners(String)
	 */
	public boolean hasAnyTreeChangeListeners(String treeName) {
		return (this.changeSupport != null) && this.changeSupport.hasAnyTreeChangeListeners(treeName);
	}

	/**
	 * Return whether the model has no tree change listeners that will
	 * be notified when the specified tree has changed.
	 */
	public boolean hasNoTreeChangeListeners(String treeName) {
		return ! this.hasAnyTreeChangeListeners(treeName);
	}

	/**
	 * @see ChangeSupport#fireNodeAdded(TreeAddEvent)
	 */
	protected final void fireNodeAdded(TreeAddEvent event) {
		this.getChangeSupport().fireNodeAdded(event);
	}

	/**
	 * @see ChangeSupport#fireNodeAdded(String, List)
	 */
	protected final void fireNodeAdded(String treeName, List<?> path) {
		this.getChangeSupport().fireNodeAdded(treeName, path);
	}

	/**
	 * @see ChangeSupport#fireNodeRemoved(TreeRemoveEvent)
	 */
	protected final void fireNodeRemoved(TreeRemoveEvent event) {
		this.getChangeSupport().fireNodeRemoved(event);
	}

	/**
	 * @see ChangeSupport#fireNodeRemoved(String, List)
	 */
	protected final void fireNodeRemoved(String treeName, List<?> path) {
		this.getChangeSupport().fireNodeRemoved(treeName, path);
	}

	/**
	 * @see ChangeSupport#fireTreeCleared(TreeClearEvent)
	 */
	protected final void fireTreeCleared(TreeClearEvent event) {
		this.getChangeSupport().fireTreeCleared(event);
	}

	/**
	 * @see ChangeSupport#fireTreeCleared(String)
	 */
	protected final void fireTreeCleared(String treeName) {
		this.getChangeSupport().fireTreeCleared(treeName);
	}

	/**
	 * @see ChangeSupport#fireTreeChanged(TreeChangeEvent)
	 */
	protected final void fireTreeChanged(TreeChangeEvent event) {
		this.getChangeSupport().fireTreeChanged(event);
	}

	/**
	 * @see ChangeSupport#fireTreeChanged(String, Collection)
	 */
	protected final void fireTreeChanged(String treeName, Collection<?> nodes) {
		this.getChangeSupport().fireTreeChanged(treeName, nodes);
	}


	// ********** convenience methods **********

	/**
	 * Return whether the specified values are equal, with the appropriate <code>null</code> checks.
	 * Convenience method for checking whether an attribute value has changed.
	 * <p>
	 * <em>Do not</em> use this to determine whether to fire a change notification,
	 * {@link ChangeSupport} already does that.
	 */
	protected final boolean valuesAreEqual(Object value1, Object value2) {
		return this.getChangeSupport().valuesAreEqual(value1, value2);
	}

	/**
	 * @see #valuesAreEqual(Object, Object)
	 */
	protected final boolean attributeValueHasNotChanged(Object oldValue, Object newValue) {
		return this.valuesAreEqual(oldValue, newValue);
	}


	/**
	 * <em>Do not</em> use this to determine whether to fire a change notification,
	 * {@link ChangeSupport} already does that.
	 * <p>
	 * For example, after firing the change notification, you can use this method
	 * to decide if some other, related, piece of state needs to be synchronized
	 * with the state that just changed.
	 * 
	 * @see ChangeSupport#valuesAreDifferent(Object, Object)
	 */
	protected final boolean valuesAreDifferent(Object value1, Object value2) {
		return this.getChangeSupport().valuesAreDifferent(value1, value2);
	}

	/**
	 * @see #valuesAreDifferent(Object, Object)
	 */
	protected final boolean attributeValueHasChanged(Object oldValue, Object newValue) {
		return this.valuesAreDifferent(oldValue, newValue);
	}


	// ********** Object overrides **********

	/**
	 * Although cloning models is usually not a Good Idea,
	 * we should at least support it properly.
	 */
	@Override
	protected AbstractModel clone() throws CloneNotSupportedException {
		AbstractModel clone = (AbstractModel) super.clone();
		// clear out change support - models do not share listeners
		clone.changeSupport = null;
		return clone;
	}

	/**
	 * e.g. <code>"ClassName[00-F3-EE-42](add'l info)"</code>
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringTools.buildSimpleToStringOn(this, sb);
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
	 * <code>#toString(StringBuilder)</code>.
	 */
	public void toString(@SuppressWarnings("unused") StringBuilder sb) {
		// subclasses should override this to do something a bit more helpful
	}
}
