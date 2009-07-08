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
 * Reasonable implementation of the <code>Model</code> protocol
 * with numerous convenience methods.
 */
public abstract class AbstractModel
	implements Model, Serializable
{
	/**
	 * Delegate state/property/collection/list/tree change support to this
	 * helper object. The change support object is "lazily-initialized";
	 * so it may be null. The method #getChangeSupport() will initialize this
	 * field if it is null.
	 * 
	 * NB: We instantiate this when we fire events, even when we do not have
	 * any listeners (which would be implied if 'changeSupport' were null).
	 * This allows the change support to have behavior tied to events even when
	 * we have no listeners.
	 * @see ChangeSupport#aspectChanged(String)
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

	public void addChangeListener(ChangeListener listener) {
		this.getChangeSupport().addChangeListener(listener);
	}

	public void removeChangeListener(ChangeListener listener) {
		this.getChangeSupport().removeChangeListener(listener);
	}

	/**
	 * Return whether the model has any change listeners.
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

	public void addStateChangeListener(StateChangeListener listener) {
		this.getChangeSupport().addStateChangeListener(listener);
	}

	public void removeStateChangeListener(StateChangeListener listener) {
		this.getChangeSupport().removeStateChangeListener(listener);
	}

	/**
	 * Return whether the model has any state change listeners.
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

	protected final void fireStateChanged(StateChangeEvent event) {
		this.getChangeSupport().fireStateChanged(event);
	}

	protected final void fireStateChanged() {
		this.getChangeSupport().fireStateChanged();
	}


	// ********** property change support **********

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.getChangeSupport().addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.getChangeSupport().removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Return whether the model has any property change listeners that will
	 * be notified when the specified property has changed.
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

	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.getChangeSupport().addCollectionChangeListener(collectionName, listener);
	}

	public void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.getChangeSupport().removeCollectionChangeListener(collectionName, listener);
	}

	/**
	 * Return whether the model has any collection change listeners that will
	 * be notified when the specified collection has changed.
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

	protected final void fireItemsAdded(CollectionAddEvent event) {
		this.getChangeSupport().fireItemsAdded(event);
	}

	protected final void fireItemsAdded(String collectionName, Collection<?> addedItems) {
		this.getChangeSupport().fireItemsAdded(collectionName, addedItems);
	}

	protected final void fireItemAdded(String collectionName, Object addedItem) {
		this.getChangeSupport().fireItemAdded(collectionName, addedItem);
	}

	protected final void fireItemsRemoved(CollectionRemoveEvent event) {
		this.getChangeSupport().fireItemsRemoved(event);
	}

	protected final void fireItemsRemoved(String collectionName, Collection<?> removedItems) {
		this.getChangeSupport().fireItemsRemoved(collectionName, removedItems);
	}

	protected final void fireItemRemoved(String collectionName, Object removedItem) {
		this.getChangeSupport().fireItemRemoved(collectionName, removedItem);
	}

	protected final void fireCollectionCleared(CollectionClearEvent event) {
		this.getChangeSupport().fireCollectionCleared(event);
	}

	protected final void fireCollectionCleared(String collectionName) {
		this.getChangeSupport().fireCollectionCleared(collectionName);
	}

	protected final void fireCollectionChanged(CollectionChangeEvent event) {
		this.getChangeSupport().fireCollectionChanged(event);
	}

	protected final void fireCollectionChanged(String collectionName, Collection<?> collection) {
		this.getChangeSupport().fireCollectionChanged(collectionName, collection);
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

	public void addListChangeListener(String listName, ListChangeListener listener) {
		this.getChangeSupport().addListChangeListener(listName, listener);
	}

	public void removeListChangeListener(String listName, ListChangeListener listener) {
		this.getChangeSupport().removeListChangeListener(listName, listener);
	}

	/**
	 * Return whether the model has any list change listeners that will
	 * be notified when the specified list has changed.
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

	protected final void fireItemsAdded(ListAddEvent event) {
		this.getChangeSupport().fireItemsAdded(event);
	}

	protected final void fireItemsAdded(String listName, int index, List<?> addedItems) {
		this.getChangeSupport().fireItemsAdded(listName, index, addedItems);
	}

	protected final void fireItemAdded(String listName, int index, Object addedItem) {
		this.getChangeSupport().fireItemAdded(listName, index, addedItem);
	}

	protected final void fireItemsRemoved(ListRemoveEvent event) {
		this.getChangeSupport().fireItemsRemoved(event);
	}

	protected final void fireItemsRemoved(String listName, int index, List<?> removedItems) {
		this.getChangeSupport().fireItemsRemoved(listName, index, removedItems);
	}

	protected final void fireItemRemoved(String listName, int index, Object removedItem) {
		this.getChangeSupport().fireItemRemoved(listName, index, removedItem);
	}

	protected final void fireItemsReplaced(ListReplaceEvent event) {
		this.getChangeSupport().fireItemsReplaced(event);
	}

	protected final <E> void fireItemsReplaced(String listName, int index, List<? extends E> newItems, List<E> replacedItems) {
		this.getChangeSupport().fireItemsReplaced(listName, index, newItems, replacedItems);
	}

	protected final void fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
		this.getChangeSupport().fireItemReplaced(listName, index, newItem, replacedItem);
	}

	protected final void fireItemsMoved(ListMoveEvent event) {
		this.getChangeSupport().fireItemsMoved(event);
	}

	protected final <E> void fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
		this.getChangeSupport().fireItemsMoved(listName, targetIndex, sourceIndex, length);
	}

	protected final void fireItemMoved(String listName, int targetIndex, int sourceIndex) {
		this.getChangeSupport().fireItemMoved(listName, targetIndex, sourceIndex);
	}

	protected final void fireListCleared(ListClearEvent event) {
		this.getChangeSupport().fireListCleared(event);
	}

	protected final void fireListCleared(String listName) {
		this.getChangeSupport().fireListCleared(listName);
	}

	protected final void fireListChanged(ListChangeEvent event) {
		this.getChangeSupport().fireListChanged(event);
	}

	protected final void fireListChanged(String listName, List<?> list) {
		this.getChangeSupport().fireListChanged(listName, list);
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

	public void addTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.getChangeSupport().addTreeChangeListener(treeName, listener);
	}

	public void removeTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.getChangeSupport().removeTreeChangeListener(treeName, listener);
	}

	/**
	 * Return whether the model has any tree change listeners that will
	 * be notified when the specified tree has changed.
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

	protected final void fireNodeAdded(TreeAddEvent event) {
		this.getChangeSupport().fireNodeAdded(event);
	}

	protected final void fireNodeAdded(String treeName, List<?> path) {
		this.getChangeSupport().fireNodeAdded(treeName, path);
	}

	protected final void fireNodeRemoved(TreeRemoveEvent event) {
		this.getChangeSupport().fireNodeRemoved(event);
	}

	protected final void fireNodeRemoved(String treeName, List<?> path) {
		this.getChangeSupport().fireNodeRemoved(treeName, path);
	}

	protected final void fireTreeCleared(TreeClearEvent event) {
		this.getChangeSupport().fireTreeCleared(event);
	}

	protected final void fireTreeCleared(String treeName) {
		this.getChangeSupport().fireTreeCleared(treeName);
	}

	protected final void fireTreeChanged(TreeChangeEvent event) {
		this.getChangeSupport().fireTreeChanged(event);
	}

	protected final void fireTreeChanged(String treeName, Collection<?> nodes) {
		this.getChangeSupport().fireTreeChanged(treeName, nodes);
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
		// clear out change support - models do not share listeners
		clone.changeSupport = null;
		return clone;
	}

	/**
	 * "ClassName[00F3EE42](add'l info)"
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
	 * make this public so one model can call a nested model's
	 * #toString(StringBuilder)
	 */
	public void toString(@SuppressWarnings("unused") StringBuilder sb) {
		// subclasses should override this to do something a bit more helpful
	}

}
