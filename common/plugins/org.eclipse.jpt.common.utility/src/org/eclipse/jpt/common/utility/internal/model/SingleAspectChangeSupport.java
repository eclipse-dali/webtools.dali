/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model;

import java.util.Collection;
import java.util.EventListener;
import java.util.List;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;
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

/**
 * This change support class changes the behavior of the standard
 * change support in several ways:<ul>
 * <li>All events fired by the source must specify the single aspect.
 * <li>Listeners are required to be either "general purpose" listeners or
 * 	    listeners of the single aspect.
 * </ul>
 */
public class SingleAspectChangeSupport
	extends ChangeSupport
{
	protected final Class<? extends EventListener> validListenerClass;
	protected final String validAspectName;


	// TODO remove
	public SingleAspectChangeSupport(Model source, Class<? extends EventListener> validListenerClass, String validAspectName) {
		this(source, validListenerClass, validAspectName, DefaultExceptionHandler.instance());
	}

	public SingleAspectChangeSupport(Model source, Class<? extends EventListener> validListenerClass, String validAspectName, ExceptionHandler exceptionHandler) {
		super(source, exceptionHandler);
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

	protected void check(Class<? extends EventListener> listenerClass, String aspectName) {
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
	public boolean firePropertyChanged(PropertyChangeEvent event) {
		this.check(PROPERTY_CHANGE_LISTENER_CLASS, event.getPropertyName());
		return super.firePropertyChanged(event);
	}

	@Override
	public boolean firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
		this.check(PROPERTY_CHANGE_LISTENER_CLASS, propertyName);
		return super.firePropertyChanged(propertyName, oldValue, newValue);
	}

	@Override
	public boolean firePropertyChanged(String propertyName, int oldValue, int newValue) {
		this.check(PROPERTY_CHANGE_LISTENER_CLASS, propertyName);
		return super.firePropertyChanged(propertyName, oldValue, newValue);
	}

	@Override
	public boolean firePropertyChanged(String propertyName, boolean oldValue, boolean newValue) {
		this.check(PROPERTY_CHANGE_LISTENER_CLASS, propertyName);
		return super.firePropertyChanged(propertyName, oldValue, newValue);
	}


	// ********** collection change support **********

	@Override
	public boolean fireItemsAdded(CollectionAddEvent event) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, event.getCollectionName());
		return super.fireItemsAdded(event);
	}

	@Override
	public boolean fireItemsAdded(String collectionName, Collection<?> addedItems) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
		return super.fireItemsAdded(collectionName, addedItems);
	}

	@Override
	public void fireItemAdded(String collectionName, Object addedItem) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
		super.fireItemAdded(collectionName, addedItem);
	}

	@Override
	public boolean fireItemsRemoved(CollectionRemoveEvent event) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, event.getCollectionName());
		return super.fireItemsRemoved(event);
	}

	@Override
	public boolean fireItemsRemoved(String collectionName, Collection<?> removedItems) {
		this.check(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
		return super.fireItemsRemoved(collectionName, removedItems);
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
	public boolean fireItemsAdded(ListAddEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		return super.fireItemsAdded(event);
	}

	@Override
	public boolean fireItemsAdded(String listName, int index, List<?> addedItems) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		return super.fireItemsAdded(listName, index, addedItems);
	}

	@Override
	public void fireItemAdded(String listName, int index, Object addedItem) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireItemAdded(listName, index, addedItem);
	}

	@Override
	public boolean fireItemsRemoved(ListRemoveEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		return super.fireItemsRemoved(event);
	}

	@Override
	public boolean fireItemsRemoved(String listName, int index, List<?> removedItems) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		return super.fireItemsRemoved(listName, index, removedItems);
	}

	@Override
	public void fireItemRemoved(String listName, int index, Object removedItem) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		super.fireItemRemoved(listName, index, removedItem);
	}

	@Override
	public boolean fireItemsReplaced(ListReplaceEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		return super.fireItemsReplaced(event);
	}

	@Override
	public boolean fireItemsReplaced(String listName, int index, List<?> newItems, List<?> replacedItems) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		return super.fireItemsReplaced(listName, index, newItems, replacedItems);
	}

	@Override
	public boolean fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		return super.fireItemReplaced(listName, index, newItem, replacedItem);
	}

	@Override
	public boolean fireItemsMoved(ListMoveEvent event) {
		this.check(LIST_CHANGE_LISTENER_CLASS, event.getListName());
		return super.fireItemsMoved(event);
	}

	@Override
	public boolean fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
		this.check(LIST_CHANGE_LISTENER_CLASS, listName);
		return super.fireItemsMoved(listName, targetIndex, sourceIndex, length);
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
}
