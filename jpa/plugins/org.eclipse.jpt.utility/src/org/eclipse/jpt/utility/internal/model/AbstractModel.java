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

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;

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
	}

	/**
	 * This accessor will build the change support when required.
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


	// ********** state change support **********

	public synchronized void addStateChangeListener(StateChangeListener listener) {
		this.getChangeSupport().addStateChangeListener(listener);
	}

	public synchronized void removeStateChangeListener(StateChangeListener listener) {
		this.getChangeSupport().removeStateChangeListener(listener);
	}

	/**
	 * Return whether there are any state change listeners.
	 */
	public boolean hasAnyStateChangeListeners() {
		return this.getChangeSupport().hasAnyStateChangeListeners();
	}

	/**
	 * Return whether there are no state change listeners.
	 */
	public boolean hasNoStateChangeListeners() {
		return ! this.hasAnyStateChangeListeners();
	}

	protected final void fireStateChanged(StateChangeEvent event) {
		this.getChangeSupport().fireStateChanged(event);
	}

	protected final void fireStateChanged() {
		this.getChangeSupport().fireStateChanged();
	}


	// ********** property change support **********

	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
		this.getChangeSupport().addPropertyChangeListener(listener);
	}

	public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.getChangeSupport().addPropertyChangeListener(propertyName, listener);
	}

	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
		this.getChangeSupport().removePropertyChangeListener(listener);
	}

	public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.getChangeSupport().removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Return whether there are any property change listeners for a specific property.
	 */
	public boolean hasAnyPropertyChangeListeners(String propertyName) {
		return this.getChangeSupport().hasAnyPropertyChangeListeners(propertyName);
	}

	/**
	 * Return whether there are any property change listeners for a specific property.
	 */
	public boolean hasNoPropertyChangeListeners(String propertyName) {
		return ! this.hasAnyPropertyChangeListeners(propertyName);
	}

	/**
	 * Return whether there are any property change listeners.
	 */
	public boolean hasAnyPropertyChangeListeners() {
		return this.getChangeSupport().hasAnyPropertyChangeListeners();
	}

	/**
	 * Return whether there are any property change listeners.
	 */
	public boolean hasNoPropertyChangeListeners() {
		return ! this.hasAnyPropertyChangeListeners();
	}

	protected final void firePropertyChanged(PropertyChangeEvent event) {
		this.getChangeSupport().firePropertyChanged(event);
	}

	protected final void firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
		this.getChangeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	protected final void firePropertyChanged(String propertyName, int oldValue, int newValue) {
		this.getChangeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	protected final void firePropertyChanged(String propertyName, boolean oldValue, boolean newValue) {
		this.getChangeSupport().firePropertyChanged(propertyName, oldValue, newValue);
	}

	/**
	 * implied 'null' old value
	 */
	protected final void firePropertyChanged(String propertyName, Object newValue) {
		this.firePropertyChanged(propertyName, null, newValue);
	}


	// ********** collection change support **********

	public synchronized void addCollectionChangeListener(CollectionChangeListener listener) {
		this.getChangeSupport().addCollectionChangeListener(listener);
	}

	public synchronized void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.getChangeSupport().addCollectionChangeListener(collectionName, listener);
	}

	public synchronized void removeCollectionChangeListener(CollectionChangeListener listener) {
		this.getChangeSupport().removeCollectionChangeListener(listener);
	}

	public synchronized void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.getChangeSupport().removeCollectionChangeListener(collectionName, listener);
	}

	/**
	 * Return whether there are any collection change listeners for a specific collection.
	 */
	public boolean hasAnyCollectionChangeListeners(String collectionName) {
		return this.getChangeSupport().hasAnyCollectionChangeListeners(collectionName);
	}

	/**
	 * Return whether there are any collection change listeners for a specific collection.
	 */
	public boolean hasNoCollectionChangeListeners(String collectionName) {
		return ! this.hasAnyCollectionChangeListeners(collectionName);
	}

	/**
	 * Return whether there are any collection change listeners.
	 */
	public boolean hasAnyCollectionChangeListeners() {
		return this.getChangeSupport().hasAnyCollectionChangeListeners();
	}

	/**
	 * Return whether there are any collection change listeners.
	 */
	public boolean hasNoCollectionChangeListeners() {
		return ! this.hasAnyCollectionChangeListeners();
	}

	protected final void fireItemsAdded(CollectionChangeEvent event) {
		this.getChangeSupport().fireItemsAdded(event);
	}

	protected final void fireItemsAdded(String collectionName, Collection<?> addedItems) {
		this.getChangeSupport().fireItemsAdded(collectionName, addedItems);
	}

	protected final void fireItemAdded(String collectionName, Object addedItem) {
		this.getChangeSupport().fireItemAdded(collectionName, addedItem);
	}

	protected final void fireItemsRemoved(CollectionChangeEvent event) {
		this.getChangeSupport().fireItemsRemoved(event);
	}

	protected final void fireItemsRemoved(String collectionName, Collection<?> removedItems) {
		this.getChangeSupport().fireItemsRemoved(collectionName, removedItems);
	}

	protected final void fireItemRemoved(String collectionName, Object removedItem) {
		this.getChangeSupport().fireItemRemoved(collectionName, removedItem);
	}

	protected final void fireCollectionCleared(CollectionChangeEvent event) {
		this.getChangeSupport().fireCollectionCleared(event);
	}

	protected final void fireCollectionCleared(String collectionName) {
		this.getChangeSupport().fireCollectionCleared(collectionName);
	}

	protected final void fireCollectionChanged(CollectionChangeEvent event) {
		this.getChangeSupport().fireCollectionChanged(event);
	}

	protected final void fireCollectionChanged(String collectionName) {
		this.getChangeSupport().fireCollectionChanged(collectionName);
	}

	protected <E> boolean addItemToCollection(E item, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().addItemToCollection(item, collection, collectionName);
	}

	protected <E> boolean addItemsToCollection(E[] items, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().addItemsToCollection(items, collection, collectionName);
	}

	protected <E> boolean addItemsToCollection(Collection<? extends E> items, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().addItemsToCollection(items, collection, collectionName);
	}

	protected <E> boolean addItemsToCollection(Iterable<? extends E> items, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().addItemsToCollection(items, collection, collectionName);
	}

	protected <E> boolean addItemsToCollection(Iterator<? extends E> items, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().addItemsToCollection(items, collection, collectionName);
	}

	protected boolean removeItemFromCollection(Object item, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().removeItemFromCollection(item, collection, collectionName);
	}

	protected boolean removeItemsFromCollection(Object[] items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().removeItemsFromCollection(items, collection, collectionName);
	}

	protected boolean removeItemsFromCollection(Collection<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().removeItemsFromCollection(items, collection, collectionName);
	}

	protected boolean removeItemsFromCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().removeItemsFromCollection(items, collection, collectionName);
	}

	protected boolean removeItemsFromCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().removeItemsFromCollection(items, collection, collectionName);
	}

	protected boolean retainItemsInCollection(Object[] items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().retainItemsInCollection(items, collection, collectionName);
	}

	protected boolean retainItemsInCollection(Collection<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().retainItemsInCollection(items, collection, collectionName);
	}

	protected boolean retainItemsInCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().retainItemsInCollection(items, collection, collectionName);
	}

	protected boolean retainItemsInCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		return this.getChangeSupport().retainItemsInCollection(items, collection, collectionName);
	}

	protected boolean clearCollection(Collection<?> collection, String collectionName) {
		return this.getChangeSupport().clearCollection(collection, collectionName);
	}

	protected <E> boolean synchronizeCollection(Collection<E> newCollection, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().synchronizeCollection(newCollection, collection, collectionName);
	}

	protected <E> boolean synchronizeCollection(Iterator<E> newCollection, Collection<E> collection, String collectionName) {
		return this.getChangeSupport().synchronizeCollection(newCollection, collection, collectionName);
	}


	// ********** list change support **********

	public synchronized void addListChangeListener(ListChangeListener listener) {
		this.getChangeSupport().addListChangeListener(listener);
	}

	public synchronized void addListChangeListener(String listName, ListChangeListener listener) {
		this.getChangeSupport().addListChangeListener(listName, listener);
	}

	public synchronized void removeListChangeListener(ListChangeListener listener) {
		this.getChangeSupport().removeListChangeListener(listener);
	}

	public synchronized void removeListChangeListener(String listName, ListChangeListener listener) {
		this.getChangeSupport().removeListChangeListener(listName, listener);
	}

	/**
	 * Return whether there are any list change listeners for a specific list.
	 */
	public boolean hasAnyListChangeListeners(String listName) {
		return this.getChangeSupport().hasAnyListChangeListeners(listName);
	}

	/**
	 * Return whether there are any list change listeners for a specific list.
	 */
	public boolean hasNoListChangeListeners(String listName) {
		return ! this.hasAnyListChangeListeners(listName);
	}

	/**
	 * Return whether there are any list change listeners.
	 */
	public boolean hasAnyListChangeListeners() {
		return this.getChangeSupport().hasAnyListChangeListeners();
	}

	/**
	 * Return whether there are any list change listeners.
	 */
	public boolean hasNoListChangeListeners() {
		return ! this.hasAnyListChangeListeners();
	}

	protected final void fireItemsAdded(ListChangeEvent event) {
		this.getChangeSupport().fireItemsAdded(event);
	}

	protected final void fireItemsAdded(String listName, int index, List<?> addedItems) {
		this.getChangeSupport().fireItemsAdded(listName, index, addedItems);
	}

	protected final void fireItemAdded(String listName, int index, Object addedItem) {
		this.getChangeSupport().fireItemAdded(listName, index, addedItem);
	}

	protected final void fireItemsRemoved(ListChangeEvent event) {
		this.getChangeSupport().fireItemsRemoved(event);
	}

	protected final void fireItemsRemoved(String listName, int index, List<?> removedItems) {
		this.getChangeSupport().fireItemsRemoved(listName, index, removedItems);
	}

	protected final void fireItemRemoved(String listName, int index, Object removedItem) {
		this.getChangeSupport().fireItemRemoved(listName, index, removedItem);
	}

	protected final void fireItemsReplaced(ListChangeEvent event) {
		this.getChangeSupport().fireItemsReplaced(event);
	}

	protected final <E> void fireItemsReplaced(String listName, int index, List<? extends E> newItems, List<E> replacedItems) {
		this.getChangeSupport().fireItemsReplaced(listName, index, newItems, replacedItems);
	}

	protected final void fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
		this.getChangeSupport().fireItemReplaced(listName, index, newItem, replacedItem);
	}

	protected final void fireItemsMoved(ListChangeEvent event) {
		this.getChangeSupport().fireItemsMoved(event);
	}

	protected final <E> void fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
		this.getChangeSupport().fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	protected final void fireItemMoved(String listName, int targetIndex, int sourceIndex) {
		this.getChangeSupport().fireItemMoved(listName, targetIndex, sourceIndex);
	}

	protected final void fireListCleared(ListChangeEvent event) {
		this.getChangeSupport().fireListCleared(event);
	}

	protected final void fireListCleared(String listName) {
		this.getChangeSupport().fireListCleared(listName);
	}

	protected final void fireListChanged(ListChangeEvent event) {
		this.getChangeSupport().fireListChanged(event);
	}

	protected final void fireListChanged(String listName) {
		this.getChangeSupport().fireListChanged(listName);
	}

	protected <E> void addItemToList(int index, E item, List<E> list, String listName) {
		this.getChangeSupport().addItemToList(index, item, list, listName);
	}

	protected <E> boolean addItemToList(E item, List<E> list, String listName) {
		return this.getChangeSupport().addItemToList(item, list, listName);
	}

	protected <E> boolean addItemsToList(int index, E[] items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(index, items, list, listName);
	}

	protected <E> boolean addItemsToList(int index, Collection<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(index, items, list, listName);
	}

	protected <E> boolean addItemsToList(int index, Iterable<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(index, items, list, listName);
	}

	protected <E> boolean addItemsToList(int index, Iterator<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(index, items, list, listName);
	}

	protected <E> boolean addItemsToList(E[] items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(items, list, listName);
	}

	protected <E> boolean addItemsToList(Collection<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(items, list, listName);
	}

	protected <E> boolean addItemsToList(Iterable<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(items, list, listName);
	}

	protected <E> boolean addItemsToList(Iterator<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().addItemsToList(items, list, listName);
	}

	protected <E> E removeItemFromList(int index, List<E> list, String listName) {
		return this.getChangeSupport().removeItemFromList(index, list, listName);
	}

	protected boolean removeItemFromList(Object item, List<?> list, String listName) {
		return this.getChangeSupport().removeItemFromList(item, list, listName);
	}

	protected <E> List<E> removeItemsFromList(int index, int length, List<E> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(index, length, list, listName);
	}

	protected boolean removeItemsFromList(Object[] items, List<?> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(items, list, listName);
	}

	protected boolean removeItemsFromList(Collection<?> items, List<?> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(items, list, listName);
	}

	protected boolean removeItemsFromList(Iterable<?> items, List<?> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(items, list, listName);
	}

	protected boolean removeItemsFromList(Iterator<?> items, List<?> list, String listName) {
		return this.getChangeSupport().removeItemsFromList(items, list, listName);
	}

	protected boolean retainItemsInList(Object[] items, List<?> list, String listName) {
		return this.getChangeSupport().retainItemsInList(items, list, listName);
	}

	protected boolean retainItemsInList(Collection<?> items, List<?> list, String listName) {
		return this.getChangeSupport().retainItemsInList(items, list, listName);
	}

	protected boolean retainItemsInList(Iterable<?> items, List<?> list, String listName) {
		return this.getChangeSupport().retainItemsInList(items, list, listName);
	}

	protected boolean retainItemsInList(Iterator<?> items, List<?> list, String listName) {
		return this.getChangeSupport().retainItemsInList(items, list, listName);
	}

	protected <E> E setItemInList(int index, E item, List<E> list, String listName) {
		return this.getChangeSupport().setItemInList(index, item, list, listName);
	}

	protected <E> int replaceItemInList(E oldItem, E newItem, List<E> list, String listName) {
		return this.getChangeSupport().replaceItemInList(oldItem, newItem, list, listName);
	}

	protected <E> List<E> setItemsInList(int index, E[] items, List<E> list, String listName) {
		return this.getChangeSupport().setItemsInList(index, items, list, listName);
	}

	protected <E> List<E> setItemsInList(int index, List<? extends E> items, List<E> list, String listName) {
		return this.getChangeSupport().setItemsInList(index, items, list, listName);
	}

	protected <E> void moveItemsInList(int targetIndex, int sourceIndex, int length, List<E> list, String listName) {
		this.getChangeSupport().moveItemsInList(targetIndex, sourceIndex, length, list, listName);
	}

	protected <E> void moveItemInList(int targetIndex, int sourceIndex, List<E> list, String listName) {
		this.getChangeSupport().moveItemInList(targetIndex, sourceIndex, list, listName);
	}

	protected boolean clearList(List<?> list, String listName) {
		return this.getChangeSupport().clearList(list, listName);
	}

	protected <E> boolean synchronizeList(List<E> newList, List<E> list, String listName) {
		return this.getChangeSupport().synchronizeList(newList, list, listName);
	}

	protected <E> boolean synchronizeList(Iterator<E> newList, List<E> list, String listName) {
		return this.getChangeSupport().synchronizeList(newList, list, listName);
	}


	// ********** tree change support **********

	public synchronized void addTreeChangeListener(TreeChangeListener listener) {
		this.getChangeSupport().addTreeChangeListener(listener);
	}

	public synchronized void addTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.getChangeSupport().addTreeChangeListener(treeName, listener);
	}

	public synchronized void removeTreeChangeListener(TreeChangeListener listener) {
		this.getChangeSupport().removeTreeChangeListener(listener);
	}

	public synchronized void removeTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.getChangeSupport().removeTreeChangeListener(treeName, listener);
	}

	/**
	 * Return whether there are any tree change listeners for a specific tree.
	 */
	public boolean hasAnyTreeChangeListeners(String treeName) {
		return this.getChangeSupport().hasAnyTreeChangeListeners(treeName);
	}

	/**
	 * Return whether there are any tree change listeners for a specific tree.
	 */
	public boolean hasNoTreeChangeListeners(String treeName) {
		return ! this.hasAnyTreeChangeListeners(treeName);
	}

	/**
	 * Return whether there are any tree change listeners.
	 */
	public boolean hasAnyTreeChangeListeners() {
		return this.getChangeSupport().hasAnyTreeChangeListeners();
	}

	/**
	 * Return whether there are any tree change listeners.
	 */
	public boolean hasNoTreeChangeListeners() {
		return ! this.hasAnyTreeChangeListeners();
	}

	protected final void fireNodeAdded(TreeChangeEvent event) {
		this.getChangeSupport().fireNodeAdded(event);
	}

	protected final void fireNodeAdded(String treeName, Object[] path) {
		this.getChangeSupport().fireNodeAdded(treeName, path);
	}

	protected final void fireNodeRemoved(TreeChangeEvent event) {
		this.getChangeSupport().fireNodeRemoved(event);
	}

	protected final void fireNodeRemoved(String treeName, Object[] path) {
		this.getChangeSupport().fireNodeRemoved(treeName, path);
	}

	protected final void fireTreeCleared(TreeChangeEvent event) {
		this.getChangeSupport().fireTreeCleared(event);
	}

	protected final void fireTreeCleared(String treeName, Object[] path) {
		this.getChangeSupport().fireTreeCleared(treeName, path);
	}

	protected final void fireTreeCleared(String treeName) {
		this.getChangeSupport().fireTreeCleared(treeName);
	}

	protected final void fireTreeChanged(TreeChangeEvent event) {
		this.getChangeSupport().fireTreeChanged(event);
	}

	protected final void fireTreeChanged(String treeName, Object[] path) {
		this.getChangeSupport().fireTreeChanged(treeName, path);
	}

	protected final void fireTreeChanged(String treeName) {
		this.getChangeSupport().fireTreeChanged(treeName);
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
		return this.getChangeSupport().valuesAreEqual(value1, value2);
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
		return this.getChangeSupport().valuesAreDifferent(value1, value2);
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
		sb.append(" ("); //$NON-NLS-1$
		this.toString(sb);
		sb.append(')');
		return sb.toString();
	}

	/**
	 * make this public so one model can call a nested model's
	 * #toString(StringBuilder)
	 */
	public void toString(@SuppressWarnings("unused") StringBuilder sb) {
		// subclasses should override this to do something a bit more helpful
	}

}
