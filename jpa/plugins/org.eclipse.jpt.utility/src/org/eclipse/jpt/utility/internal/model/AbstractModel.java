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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.StringTools;
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
 * Convenience implementation of Model protocol.
 */
public abstract class AbstractModel implements Model, Serializable {
	/**
	 * Delegate state/property/collection/list/tree change support to this
	 * helper object. The change support object is "lazy-initialized".
	 */
	private ChangeSupport changeSupport;


	// ********** constructors/initialization **********

	/**
	 * Default constructor.
	 * This will call #initialize() on the newly-created instance.
	 */
	protected AbstractModel() {
		super();
		this.initialize();
	}

	protected void initialize() {
		// do nothing by default
	}

	/**
	 * This accessor will build the change support when required.
	 */
	private synchronized ChangeSupport changeSupport() {
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


	// ********** state change support **********

	public synchronized void addStateChangeListener(StateChangeListener listener) {
		this.changeSupport().addStateChangeListener(listener);
	}

	public synchronized void removeStateChangeListener(StateChangeListener listener) {
		this.changeSupport().removeStateChangeListener(listener);
	}

	protected final void fireStateChanged() {
		this.changeSupport().fireStateChanged();
	}

	protected final void fireStateChanged(StateChangeEvent event) {
		this.changeSupport().fireStateChanged(event);
	}


	// ********** property change support **********

	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		this.changeSupport().addPropertyChangeListener(listener);
	}

	public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.changeSupport().addPropertyChangeListener(propertyName, listener);
	}

	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		this.changeSupport().removePropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.changeSupport().removePropertyChangeListener(propertyName, listener);
	}

	protected final void firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
		this.changeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	protected final void firePropertyChanged(String propertyName, int oldValue, int newValue) {
		this.changeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	protected final void firePropertyChanged(String propertyName, boolean oldValue, boolean newValue) {
		this.changeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	protected final void firePropertyChanged(String propertyName, Object newValue) {
		this.changeSupport().firePropertyChanged(propertyName, null, newValue);
	}

	protected final void firePropertyChanged(PropertyChangeEvent event) {
		this.changeSupport().firePropertyChanged(event);
	}


	// ********** collection change support **********

	public synchronized void addCollectionChangeListener(CollectionChangeListener listener) {
		this.changeSupport().addCollectionChangeListener(listener);
	}

	public synchronized void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.changeSupport().addCollectionChangeListener(collectionName, listener);
	}

	public synchronized void removeCollectionChangeListener(CollectionChangeListener listener) {
		this.changeSupport().removeCollectionChangeListener(listener);
	}

	public synchronized void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.changeSupport().removeCollectionChangeListener(collectionName, listener);
	}

	protected final void fireItemAdded(String collectionName, Object addedItem) {
		this.changeSupport().fireItemAdded(collectionName, addedItem);
	}

	protected final void fireItemsAdded(String collectionName, Collection<?> addedItems) {
		this.changeSupport().fireItemsAdded(collectionName, addedItems);
	}

	protected final void fireItemsAdded(CollectionChangeEvent event) {
		this.changeSupport().fireItemsAdded(event);
	}

	protected final void fireItemRemoved(String collectionName, Object removedItem) {
		this.changeSupport().fireItemRemoved(collectionName, removedItem);
	}

	protected final void fireItemsRemoved(String collectionName, Collection<?> removedItems) {
		this.changeSupport().fireItemsRemoved(collectionName, removedItems);
	}

	protected final void fireItemsRemoved(CollectionChangeEvent event) {
		this.changeSupport().fireItemsRemoved(event);
	}

	protected final void fireCollectionCleared(String collectionName) {
		this.changeSupport().fireCollectionCleared(collectionName);
	}

	protected final void fireCollectionCleared(CollectionChangeEvent event) {
		this.changeSupport().fireCollectionCleared(event);
	}

	protected final void fireCollectionChanged(String collectionName) {
		this.changeSupport().fireCollectionChanged(collectionName);
	}

	protected final void fireCollectionChanged(CollectionChangeEvent event) {
		this.changeSupport().fireCollectionChanged(event);
	}

	/**
	 * Convenience method.
	 * Add the specified item to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 */
	protected <E> boolean addItemToCollection(E item, Collection<E> collection, String collectionName) {
		if (collection.add(item)) {
			this.fireItemAdded(collectionName, item);
			return true;
		}
		return false;
	}

	/**
	 * Convenience method.
	 * Add the specified items to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether collection changed.
	 */
	protected <E> boolean addItemsToCollection(Iterable<? extends E> items, Collection<E> collection, String collectionName) {
		return this.addItemsToCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Convenience method.
	 * Add the specified items to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether collection changed.
	 */
	protected <E> boolean addItemsToCollection(Iterator<? extends E> items, Collection<E> collection, String collectionName) {
		Collection<E> addedItems = null;
		while (items.hasNext()) {
			E item = items.next();
			if (collection.add(item)) {
				if (addedItems == null) {
					addedItems = new ArrayList<E>();
				}
				addedItems.add(item);
			}
		}
		if (addedItems != null) {
			this.fireItemsAdded(collectionName, addedItems);
			return true;
		}
		return false;
	}

	/**
	 * Convenience method.
	 * Remove the specified item from the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#remove(Object)
	 */
	protected boolean removeItemFromCollection(Object item, Collection<?> collection, String collectionName) {
		if (collection.remove(item)) {
			this.fireItemRemoved(collectionName, item);
			return true;
		}
		return false;
	}

	/**
	 * Convenience method.
	 * Remove the specified items from the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#removeAll(Collection)
	 */
	protected boolean removeItemsFromCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.removeItemsFromCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Convenience method.
	 * Remove the specified items from the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#removeAll(Collection)
	 */
	protected boolean removeItemsFromCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		Collection<?> items2 = CollectionTools.collection(items);
		items2.retainAll(collection);
		boolean changed = collection.removeAll(items2);

		if ( ! items2.isEmpty()) {
			this.fireItemsRemoved(collectionName, items2);
		}
		return changed;
	}

	/**
	 * Convenience method.
	 * Retain the specified items in the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#retainAll(Collection)
	 */
	protected boolean retainItemsInCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.retainItemsInCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Convenience method.
	 * Retain the specified items in the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#retainAll(Collection)
	 */
	protected boolean retainItemsInCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		Collection<?> items2 = CollectionTools.collection(items);
		Collection<?> removedItems = CollectionTools.collection(collection);
		removedItems.removeAll(items2);
		boolean changed = collection.retainAll(items2);

		if ( ! removedItems.isEmpty()) {
			this.fireItemsRemoved(collectionName, removedItems);
		}
		return changed;
	}

	/**
	 * Convenience method.
	 * Clear the entire collection
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 */
	protected boolean clearCollection(Collection<?> collection, String collectionName) {
		if (collection.isEmpty()) {
			return false;
		}
		collection.clear();
		this.fireCollectionCleared(collectionName);
		return true;
	}

	/**
	 * Convenience method.
	 * Synchronize the collection with the specified new collection,
	 * making a minimum number of removes and adds.
	 * Return whether the collection changed.
	 */
	protected <E> boolean synchronizeCollection(Collection<E> newCollection, Collection<E> collection, String collectionName) {
		if (newCollection.isEmpty()) {
			return this.clearCollection(collection, collectionName);
		}

		if (collection.isEmpty()) {
			return this.addItemsToCollection(newCollection, collection, collectionName);
		}

		boolean changed = false;
		Collection<E> removeItems = new HashBag<E>(collection);
		removeItems.removeAll(newCollection);
		changed |= this.removeItemsFromCollection(removeItems, collection, collectionName);

		Collection<E> addItems = new HashBag<E>(newCollection);
		addItems.removeAll(collection);
		changed |= this.addItemsToCollection(addItems, collection, collectionName);

		return changed;
	}

	/**
	 * Convenience method.
	 * Synchronize the collection with the specified new collection,
	 * making a minimum number of removes and adds.
	 * Return whether the collection changed.
	 */
	protected <E> boolean synchronizeCollection(Iterator<E> newItems, Collection<E> collection, String collectionName) {
		return this.synchronizeCollection(CollectionTools.collection(newItems), collection, collectionName);
	}


	// ********** list change support **********

	public synchronized void addListChangeListener(ListChangeListener listener) {
		this.changeSupport().addListChangeListener(listener);
	}

	public synchronized void addListChangeListener(String listName, ListChangeListener listener) {
		this.changeSupport().addListChangeListener(listName, listener);
	}

	public synchronized void removeListChangeListener(ListChangeListener listener) {
		this.changeSupport().removeListChangeListener(listener);
	}

	public synchronized void removeListChangeListener(String listName, ListChangeListener listener) {
		this.changeSupport().removeListChangeListener(listName, listener);
	}

	protected final void fireItemAdded(String listName, int index, Object addedItem) {
		this.changeSupport().fireItemAdded(listName, index, addedItem);
	}

	protected final void fireItemsAdded(String listName, int index, List<?> addedItems) {
		this.changeSupport().fireItemsAdded(listName, index, addedItems);
	}

	protected final void fireItemsAdded(ListChangeEvent event) {
		this.changeSupport().fireItemsAdded(event);
	}

	protected final void fireItemRemoved(String listName, int index, Object removedItem) {
		this.changeSupport().fireItemRemoved(listName, index, removedItem);
	}

	protected final void fireItemsRemoved(String listName, int index, List<?> removedItems) {
		this.changeSupport().fireItemsRemoved(listName, index, removedItems);
	}

	protected final void fireItemsRemoved(ListChangeEvent event) {
		this.changeSupport().fireItemsRemoved(event);
	}

	protected final void fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
		this.changeSupport().fireItemReplaced(listName, index, newItem, replacedItem);
	}

	protected final <E> void fireItemsReplaced(String listName, int index, List<? extends E> newItems, List<E> replacedItems) {
		this.changeSupport().fireItemsReplaced(listName, index, newItems, replacedItems);
	}

	protected final void fireItemsReplaced(ListChangeEvent event) {
		this.changeSupport().fireItemsReplaced(event);
	}

	protected final void fireItemMoved(String listName, int targetIndex, int sourceIndex) {
		this.changeSupport().fireItemMoved(listName, targetIndex, sourceIndex);
	}

	protected final <E> void fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
		this.changeSupport().fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	protected final void fireItemsMoved(ListChangeEvent event) {
		this.changeSupport().fireItemsMoved(event);
	}

	protected final void fireListCleared(String listName) {
		this.changeSupport().fireListCleared(listName);
	}

	protected final void fireListCleared(ListChangeEvent event) {
		this.changeSupport().fireListCleared(event);
	}

	protected final void fireListChanged(String listName) {
		this.changeSupport().fireListChanged(listName);
	}

	protected final void fireListChanged(ListChangeEvent event) {
		this.changeSupport().fireListChanged(event);
	}

	/**
	 * Convenience method.
	 * Add the specified item to the specified bound list
	 * and fire the appropriate event if necessary.
	 */
	protected <E> void addItemToList(int index, E item, List<E> list, String listName) {
		list.add(index, item);
		this.fireItemAdded(listName, index, item);
	}

	/**
	 * Convenience method.
	 * Add the specified item to the end of the specified bound list
	 * and fire the appropriate event if necessary.
	 */
	protected <E> void addItemToList(E item, List<E> list, String listName) {
		this.addItemToList(list.size(), item, list, listName);
	}

	/**
	 * Convenience method.
	 * Add the specified items to the specified bound list
	 * and fire the appropriate event if necessary.
	 */
	protected <E> void addItemsToList(int index, List<? extends E> items, List<E> list, String listName) {
		list.addAll(index, items);
		this.fireItemsAdded(listName, index, items);
	}

	/**
	 * Convenience method.
	 * Add the specified items to the end of to the specified bound list
	 * and fire the appropriate event if necessary.
	 */
	protected <E> void addItemsToList(List<? extends E> items, List<E> list, String listName) {
		this.addItemsToList(list.size(), items, list, listName);
	}

	/**
	 * Convenience method.
	 * Remove the specified item from the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the removed item.
	 */
	protected Object removeItemFromList(int index, List<?> list, String listName) {
		Object item = list.remove(index);
		this.fireItemRemoved(listName, index, item);
		return item;
	}

	/**
	 * Convenience method.
	 * Remove the specified item from the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the removed item.
	 */
	protected Object removeItemFromList(Object item, List<?> list, String listName) {
		return this.removeItemFromList(list.indexOf(item), list, listName);
	}

	/**
	 * Convenience method.
	 * Remove the specified items from the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the removed items.
	 */
	protected <E> List<E> removeItemsFromList(int index, int length, List<E> list, String listName) {
		List<E> subList = list.subList(index, index + length);
		List<E> removedItems = new ArrayList<E>(subList);
		subList.clear();
		this.fireItemsRemoved(listName, index, removedItems);
		return removedItems;
	}

	/**
	 * Convenience method.
	 * Set the specified item in the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the replaced item.
	 */
	protected <E> E setItemInList(int index, E item, List<E> list, String listName) {
		E replacedItem = list.set(index, item);
		this.fireItemReplaced(listName, index, item, replacedItem);
		return replacedItem;
	}

	/**
	 * Convenience method.
	 * Replace the specified item in the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the replaced item.
	 */
	protected <E> E replaceItemInList(E oldItem, E newItem, List<E> list, String listName) {
		return this.setItemInList(list.indexOf(oldItem), newItem, list, listName);
	}

	/**
	 * Convenience method.
	 * Set the specified items in the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the replaced items.
	 */
	protected <E> List<E> setItemsInList(int index, List<? extends E> items, List<E> list, String listName) {
		List<E> subList = list.subList(index, index + items.size());
		List<E> replacedItems = new ArrayList<E>(subList);
		for (int i = 0; i < items.size(); i++) {
			subList.set(i, items.get(i));
		}
		this.fireItemsReplaced(listName, index, items, replacedItems);
		return replacedItems;
	}

	/**
	 * Convenience method.
	 * Move items in the specified list from the specified source index to the
	 * specified target index for the specified length.
	 */
	protected <E> void moveItemsInList(int targetIndex, int sourceIndex, int length, List<E> list, String listName) {
		CollectionTools.move(list, targetIndex, sourceIndex, length);
		this.fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	/**
	 * Convenience method.
	 * Move an item in the specified list from the specified source index to the
	 * specified target index.
	 */
	protected <E> void moveItemInList(int targetIndex, int sourceIndex, List<E> list, String listName) {
		CollectionTools.move(list, targetIndex, sourceIndex);
		this.fireItemMoved(listName, targetIndex, sourceIndex);
	}

	/**
	 * Convenience method.
	 * Clear the entire list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 */
	protected boolean clearList(List<?> list, String listName) {
		if (list.isEmpty()) {
			return false;
		}
		list.clear();
		this.fireListCleared(listName);
		return true;
	}


	// ********** tree change support **********

	public synchronized void addTreeChangeListener(TreeChangeListener listener) {
		this.changeSupport().addTreeChangeListener(listener);
	}

	public synchronized void addTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.changeSupport().addTreeChangeListener(treeName, listener);
	}

	public synchronized void removeTreeChangeListener(TreeChangeListener listener) {
		this.changeSupport().removeTreeChangeListener(listener);
	}

	public synchronized void removeTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.changeSupport().removeTreeChangeListener(treeName, listener);
	}

	protected final void fireNodeAdded(String treeName, Object[] path) {
		this.changeSupport().fireNodeAdded(treeName, path);
	}

	protected final void fireNodeAdded(TreeChangeEvent event) {
		this.changeSupport().fireNodeAdded(event);
	}

	protected final void fireNodeRemoved(String treeName, Object[] path) {
		this.changeSupport().fireNodeRemoved(treeName, path);
	}

	protected final void fireNodeRemoved(TreeChangeEvent event) {
		this.changeSupport().fireNodeRemoved(event);
	}

	protected final void fireTreeCleared(String treeName) {
		this.changeSupport().fireTreeCleared(treeName);
	}

	protected final void fireTreeCleared(TreeChangeEvent event) {
		this.changeSupport().fireTreeCleared(event);
	}

	protected final void fireTreeChanged(String treeName) {
		this.changeSupport().fireTreeChanged(treeName);
	}

	protected final void fireTreeChanged(String treeName, Object[] path) {
		this.changeSupport().fireTreeChanged(treeName, path);
	}

	protected final void fireTreeChanged(TreeChangeEvent event) {
		this.changeSupport().fireTreeChanged(event);
	}


	// ********** queries **********

	/**
	 * Return whether there are any state change listeners.
	 */
	public boolean hasAnyStateChangeListeners() {
		return this.changeSupport().hasAnyStateChangeListeners();
	}

	/**
	 * Return whether there are no state change listeners.
	 */
	public boolean hasNoStateChangeListeners() {
		return ! this.hasAnyStateChangeListeners();
	}

	/**
	 * Return whether there are any property change listeners for a specific property.
	 */
	public boolean hasAnyPropertyChangeListeners(String propertyName) {
		return this.changeSupport().hasAnyPropertyChangeListeners(propertyName);
	}

	/**
	 * Return whether there are any property change listeners for a specific property.
	 */
	public boolean hasNoPropertyChangeListeners(String propertyName) {
		return ! this.hasAnyPropertyChangeListeners(propertyName);
	}

	/**
	 * Return whether there are any collection change listeners for a specific collection.
	 */
	public boolean hasAnyCollectionChangeListeners(String collectionName) {
		return this.changeSupport().hasAnyCollectionChangeListeners(collectionName);
	}

	/**
	 * Return whether there are any collection change listeners for a specific collection.
	 */
	public boolean hasNoCollectionChangeListeners(String collectionName) {
		return ! this.hasAnyCollectionChangeListeners(collectionName);
	}

	/**
	 * Return whether there are any list change listeners for a specific list.
	 */
	public boolean hasAnyListChangeListeners(String listName) {
		return this.changeSupport().hasAnyListChangeListeners(listName);
	}

	/**
	 * Return whether there are any list change listeners for a specific list.
	 */
	public boolean hasNoListChangeListeners(String listName) {
		return ! this.hasAnyListChangeListeners(listName);
	}

	/**
	 * Return whether there are any tree change listeners for a specific tree.
	 */
	public boolean hasAnyTreeChangeListeners(String treeName) {
		return this.changeSupport().hasAnyTreeChangeListeners(treeName);
	}

	/**
	 * Return whether there are any tree change listeners for a specific tree.
	 */
	public boolean hasNoTreeChangeListeners(String treeName) {
		return ! this.hasAnyTreeChangeListeners(treeName);
	}


	// ********** convenience methods **********

	/**
	 * Return whether the values are equal, with the appropriate null checks.
	 * Convenience method for checking whether an attribute value has changed.
	 * 
	 * DO NOT use this to determine whether to fire a change notification,
	 * ChangeSupport already does that.
	 */
	protected final boolean valuesAreEqual(Object value1, Object value2) {
		return this.changeSupport().valuesAreEqual(value1, value2);
	}
	protected final boolean attributeValueHasNotChanged(Object oldValue, Object newValue) {
		return this.valuesAreEqual(oldValue, newValue);
	}


	/**
	 * Return whether the values are different, with the appropriate null checks.
	 * Convenience method for checking whether an attribute value has changed.
	 * 
	 * DO NOT use this to determine whether to fire a change notification,
	 * ChangeSupport already does that.
	 * 
	 * For example, after firing the change notification, you can use this method
	 * to decide if some other, related, piece of state needs to be synchronized
	 * with the state that just changed.
	 */
	protected final boolean valuesAreDifferent(Object value1, Object value2) {
		return this.changeSupport().valuesAreDifferent(value1, value2);
	}
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
		clone.postClone();
		return clone;
	}

	/**
	 * Perform any post-clone processing necessary to
	 * successfully disconnect the clone from the original.
	 * When this method is called on the clone, the clone
	 * is a "shallow" copy of the original (i.e. the clone
	 * shares all its instance variables with the original).
	 */
	protected void postClone() {
		// clear out change support - models do not share listeners
		this.changeSupport = null;
	// when you override this method, don't forget to include:
	//	super.postClone();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringTools.buildSimpleToStringOn(this, sb);
		sb.append(" (");
		this.toString(sb);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * make this public so one model can call a nested model's
	 * #toString(StringBuilder)
	 */
	public void toString(StringBuilder sb) {
		// subclasses should override this to do something a bit more helpful
	}

}
