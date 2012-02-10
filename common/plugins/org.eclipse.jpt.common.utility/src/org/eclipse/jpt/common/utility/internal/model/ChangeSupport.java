/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.HashBag;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterators.ArrayIterator;
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
import org.eclipse.jpt.common.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.common.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.common.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.common.utility.model.event.TreeRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.listener.TreeChangeListener;

/**
 * Support object that can be used by implementors of the {@link Model} interface.
 * It provides for state, property, collection, list, and tree change notifications to
 * listeners.
 * <p>
 * <strong>NB1:</strong> There is lots of copy-n-paste code in this class. Nearly all of this duplication
 * is an effort to prevent the unnecessary creation of new objects (typically event
 * objects). Since many events are fired when there are no listeners, we postpone
 * the creation of event objects until we know we have interested listeners.
 * Most methods have the "non-duplicated" version of the method body commented
 * out at the top of the current method body.
 * The hope was that this class would prove to be fairly static and the duplicated
 * code would not prove onerous; but that has not proven to be
 * the case, as we have added support for "state" changes, "dirty" notification,
 * and custom "notifiers", with more to come, I'm sure....  ~bjv
 * <p>
 * <strong>NB2:</strong> This class will check to see if, during the firing of events, a listener
 * on the original, cloned, list of listeners has been removed from the master
 * list of listeners <em>before</em> it is notified. If the listener has been removed
 * "concurrently" it will <em>not</em> be notified.
 * <p>
 * <strong>NB3:</strong> Any listener that is added during the firing of events will <em>not</em> be
 * also notified. This is a bit inconsistent with NB2, but seems reasonable
 * since any added listener should already be in sync with the model.
 * <p>
 * <strong>NB4:</strong> This class is serializable, but it will only write out listeners that
 * are also serializable while silently leaving behind listeners that are not.
 * 
 * @see Model
 * @see AbstractModel
 */
public class ChangeSupport
	implements Serializable
{
	/** The object to be provided as the "source" for any generated events. */
	protected final Model source;

	/** Associate aspect names to class-specific listener lists. */
	private AspectListenerListPair<?>[] aspectListenerListPairs = EMPTY_ASPECT_LISTENER_LIST_PAIR_ARRAY;
		private static final AspectListenerListPair<?>[] EMPTY_ASPECT_LISTENER_LIST_PAIR_ARRAY = new AspectListenerListPair[0];

	private static final long serialVersionUID = 1L;


	// ********** constructor **********

	/**
	 * Construct support for the specified source of change events.
	 * The source cannot be <code>null</code>.
	 */
	public ChangeSupport(Model source) {
		super();
		if (source == null) {
			throw new NullPointerException();
		}
		this.source = source;
	}


	// ********** internal implementation **********

	/**
	 * Add a listener that listens to all the events of the specified type and
	 * carrying the specified aspect name.
	 * Neither the aspect name nor the listener can be <code>null</code>.
	 */
	protected synchronized <L extends EventListener> void addListener(Class<L> listenerClass, String aspectName, L listener) {
		ListenerList<L> aspectListenerList = this.getListenerList(listenerClass, aspectName);
		if (aspectListenerList == null) {
			this.aspectListenerListPairs = ArrayTools.add(this.aspectListenerListPairs, new SimpleAspectListenerListPair<L>(listenerClass, aspectName, listener));
		} else {
			aspectListenerList.add(listener);
		}
	}

	/**
	 * Add a listener that listens to all the events of the specified type.
	 * The listener cannot be <code>null</code>.
	 */
	protected synchronized <L extends EventListener> void addListener(Class<L> listenerClass, L listener) {
		ListenerList<L> listenerList = this.getListenerList(listenerClass);
		if (listenerList == null) {
			this.aspectListenerListPairs = ArrayTools.add(this.aspectListenerListPairs, new NullAspectListenerListPair<L>(listenerClass, listener));
		} else {
			listenerList.add(listener);
		}
	}

	/**
	 * Remove a listener that has been registered for all the
	 * events of the specified type and carrying the specified aspect name.
	 * Neither the aspect name nor the listener can be <code>null</code>.
	 */
	protected synchronized <L extends EventListener> void removeListener(Class<L> listenerClass, String aspectName, L listener) {
		ListenerList<L> aspectListenerList = this.getListenerList(listenerClass, aspectName);
		if (aspectListenerList == null) {
			throw new IllegalArgumentException("unregistered listener: " + listener); //$NON-NLS-1$
		}
		aspectListenerList.remove(listener);  // leave the pair, even if the listener list is empty?
	}

	/**
	 * Remove a listener that has been registered for all the events of the specified type.
	 * The listener cannot be <code>null</code>.
	 */
	protected synchronized <L extends EventListener> void removeListener(Class<L> listenerClass, L listener) {
		ListenerList<L> listenerList = this.getListenerList(listenerClass);
		if (listenerList == null) {
			throw new IllegalArgumentException("unregistered listener: " + listener); //$NON-NLS-1$
		}
		listenerList.remove(listener);  // leave the pair, even if the listener list is empty?
	}

	/**
	 * Return the listener list for the specified listener class and aspect name.
	 * Return <code>null</code> if the listener list is not present.
	 * The aspect name cannot be <code>null</code>.
	 */
	protected <L extends EventListener> ListenerList<L> getListenerList(Class<L> listenerClass, String aspectName) {
		// put in a null check to simplify calling code
		if (aspectName == null) {
			throw new NullPointerException();
		}
		return this.getListenerList_(listenerClass, aspectName);
	}

	/**
	 * Return the listener list for the specified listener class.
	 * Return <code>null</code> if the listener list is not present.
	 */
	protected <L extends EventListener> ListenerList<L> getListenerList(Class<L> listenerClass) {
		return this.getListenerList_(listenerClass, null);
	}

	/**
	 * Return the listener list for the specified listener class and aspect name.
	 * Return <code>null</code> if the listener list is not present.
	 */
	protected synchronized <L extends EventListener> ListenerList<L> getListenerList_(Class<L> listenerClass, String aspectName) {
		for (AspectListenerListPair<?> pair : this.aspectListenerListPairs) {
			if (pair.matches(listenerClass, aspectName)) {
				@SuppressWarnings("unchecked") ListenerList<L> aspectListenerList = (ListenerList<L>) pair.listenerList;
				return aspectListenerList;
			}
		}
		return null;
	}

	/**
	 * Return whether there are any listeners for the specified listener class
	 * and aspect name.
	 */
	protected <L extends EventListener> boolean hasAnyListeners(Class<L> listenerClass, String aspectName) {
		ListenerList<L> aspectListenerList = this.getListenerList(listenerClass, aspectName);
		if ((aspectListenerList != null) && ! aspectListenerList.isEmpty()) {
			return true;
		}
		return this.hasAnyChangeListeners();  // check for any general purpose listeners
	}

	/**
	 * Return whether there are no listeners for the specified listener class
	 * and aspect name.
	 */
	protected <L extends EventListener> boolean hasNoListeners(Class<L> listenerClass, String aspectName) {
		return ! this.hasAnyListeners(listenerClass, aspectName);
	}

	/**
	 * Return whether there are any listeners for the specified listener class.
	 */
	protected <L extends EventListener> boolean hasAnyListeners(Class<L> listenerClass) {
		ListenerList<L> aspectListenerList = this.getListenerList(listenerClass);
		if ((aspectListenerList != null) && ! aspectListenerList.isEmpty()) {
			return true;
		}
		// check for any general purpose listeners (unless that's what we're already doing)
		return (listenerClass == this.getChangeListenerClass()) ? false : this.hasAnyChangeListeners();
	}

	/**
	 * Return whether there are no listeners for the specified listener class.
	 */
	protected <L extends EventListener> boolean hasNoListeners(Class<L> listenerClass) {
		return ! this.hasAnyListeners(listenerClass);
	}


	// ********** general purpose change support **********

	/**
	 * Subclasses that add other types of listeners should override this method
	 * to return the extension to ChangeListener that also extends whatever new
	 * listener types are supported.
	 */
	@SuppressWarnings("unchecked")
	protected <L extends ChangeListener> Class<L> getChangeListenerClass() {
		// not sure why I need to cast here...
		return (Class<L>) CHANGE_LISTENER_CLASS;
	}

	protected static final Class<ChangeListener> CHANGE_LISTENER_CLASS = ChangeListener.class;

	/**
	 * Add a general purpose listener that listens to all events,
	 * regardless of the aspect name associated with that event.
	 * The listener cannot be null.
	 */
	public void addChangeListener(ChangeListener listener) {
		this.addListener(this.getChangeListenerClass(), listener);
	}

	/**
	 * Remove a general purpose listener.
	 * The listener cannot be null.
	 */
	public void removeChangeListener(ChangeListener listener) {
		this.removeListener(this.getChangeListenerClass(), listener);
	}

	/**
	 * Return whether there are any general purpose listeners that will be
	 * notified of any changes.
	 */
	public boolean hasAnyChangeListeners() {
		return this.hasAnyListeners(this.getChangeListenerClass());
	}

	private ListenerList<ChangeListener> getChangeListenerList() {
		return this.getListenerList(CHANGE_LISTENER_CLASS);
	}

	private Iterable<ChangeListener> getChangeListeners() {
		ListenerList<ChangeListener> listenerList = this.getChangeListenerList();
		return (listenerList == null) ? null : listenerList.getListeners();
	}

	private boolean hasChangeListener(ChangeListener listener) {
		return CollectionTools.contains(this.getChangeListeners(), listener);
	}


	// ********** state change support **********

	protected static final Class<StateChangeListener> STATE_CHANGE_LISTENER_CLASS = StateChangeListener.class;

	/**
	 * Add a state change listener.
	 */
	public void addStateChangeListener(StateChangeListener listener) {
		this.addListener(STATE_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Remove a state change listener.
	 */
	public void removeStateChangeListener(StateChangeListener listener) {
		this.removeListener(STATE_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Return whether there are any state change listeners.
	 */
	public boolean hasAnyStateChangeListeners() {
		return this.hasAnyListeners(STATE_CHANGE_LISTENER_CLASS);
	}

	private ListenerList<StateChangeListener> getStateChangeListenerList() {
		return this.getListenerList(STATE_CHANGE_LISTENER_CLASS);
	}

	private Iterable<StateChangeListener> getStateChangeListeners() {
		ListenerList<StateChangeListener> listenerList = this.getStateChangeListenerList();
		return (listenerList == null) ? null : listenerList.getListeners();
	}

	private boolean hasStateChangeListener(StateChangeListener listener) {
		return CollectionTools.contains(this.getStateChangeListeners(), listener);
	}

	/**
	 * Fire the specified state change event to any registered listeners.
	 */
	public void fireStateChanged(StateChangeEvent event) {
		Iterable<StateChangeListener> listeners = this.getStateChangeListeners();
		if (listeners != null) {
			for (StateChangeListener listener : listeners) {
				if (this.hasStateChangeListener(listener)) {  // verify listener is still listening
					listener.stateChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.stateChanged(event);
				}
			}
		}
	}

	/**
	 * Report a generic state change event to any registered state change
	 * listeners.
	 */
	public void fireStateChanged() {
//		this.fireStateChanged(new StateChangeEvent(this.source));
		StateChangeEvent event = null;
		Iterable<StateChangeListener> listeners = this.getStateChangeListeners();
		if (listeners != null) {
			for (StateChangeListener listener : listeners) {
				if (this.hasStateChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new StateChangeEvent(this.source);
					}
					listener.stateChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new StateChangeEvent(this.source);
					}
					changeListener.stateChanged(event);
				}
			}
		}
	}


	// ********** property change support **********

	protected static final Class<PropertyChangeListener> PROPERTY_CHANGE_LISTENER_CLASS = PropertyChangeListener.class;

	/**
	 * Add a property change listener for the specified property. The listener
	 * will be notified only for changes to the specified property.
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.addListener(PROPERTY_CHANGE_LISTENER_CLASS, propertyName, listener);
	}

	/**
	 * Remove a property change listener that was registered for a specific property.
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.removeListener(PROPERTY_CHANGE_LISTENER_CLASS, propertyName, listener);
	}

	/**
	 * Return whether there are any property change listeners that will
	 * be notified when the specified property has changed.
	 */
	public boolean hasAnyPropertyChangeListeners(String propertyName) {
		return this.hasAnyListeners(PROPERTY_CHANGE_LISTENER_CLASS, propertyName);
	}

	private ListenerList<PropertyChangeListener> getPropertyChangeListenerList(String propertyName) {
		return this.getListenerList(PROPERTY_CHANGE_LISTENER_CLASS, propertyName);
	}

	private Iterable<PropertyChangeListener> getPropertyChangeListeners(String propertyName) {
		ListenerList<PropertyChangeListener> listenerList = this.getPropertyChangeListenerList(propertyName);
		return (listenerList == null) ? null : listenerList.getListeners();
	}

	private boolean hasPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		return CollectionTools.contains(this.getPropertyChangeListeners(propertyName), listener);
	}

	/**
	 * Fire the specified property change event to any registered listeners.
	 * No event is fired if the specified event's old and new values are the same;
	 * this includes when both values are null. Use a state change event
	 * for general purpose notification of changes.
	 * Return whether the old and new values are different.
	 */
	public boolean firePropertyChanged(PropertyChangeEvent event) {
		if (this.valuesAreDifferent(event.getOldValue(), event.getNewValue())) {
			this.firePropertyChanged_(event);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified event's old and new values are different
	 */
	protected void firePropertyChanged_(PropertyChangeEvent event) {
		String propertyName = event.getPropertyName();
		Iterable<PropertyChangeListener> listeners = this.getPropertyChangeListeners(propertyName);
		if (listeners != null) {
			for (PropertyChangeListener listener : listeners) {
				if (this.hasPropertyChangeListener(propertyName, listener)) {  // verify listener is still listening
					listener.propertyChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.propertyChanged(event);
				}
			}
		}
	}

	/**
	 * Report a bound property update to any registered property change listeners.
	 * No event is fired if the specified old and new values are the same;
	 * this includes when both values are null. Use a state change event
	 * for general purpose notification of changes.
	 * Return whether the old and new values are different.
	 */
	public boolean firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
//		return this.firePropertyChanged(new PropertyChangeEvent(this.source, propertyName, oldValue, newValue));
		if (this.valuesAreDifferent(oldValue, newValue)) {
			this.firePropertyChanged_(propertyName, oldValue, newValue);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified old and new values are different
	 */
	protected void firePropertyChanged_(String propertyName, Object oldValue, Object newValue) {
		PropertyChangeEvent event = null;
		Iterable<PropertyChangeListener> listeners = this.getPropertyChangeListeners(propertyName);
		if (listeners != null) {
			for (PropertyChangeListener listener : listeners) {
				if (this.hasPropertyChangeListener(propertyName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new PropertyChangeEvent(this.source, propertyName, oldValue, newValue);
					}
					listener.propertyChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new PropertyChangeEvent(this.source, propertyName, oldValue, newValue);
					}
					changeListener.propertyChanged(event);
				}
			}
		}
	}

	/**
	 * Report an <code>int</code> bound property update to any registered listeners.
	 * No event is fired if the specified old and new values are equal.
	 * Return whether the old and new values are different.
	 * <p>
	 * This is merely a convenience wrapper around the more general method
	 * {@link #firePropertyChanged(String, Object, Object)}.
	 */
	public boolean firePropertyChanged(String propertyName, int oldValue, int newValue) {
//		return this.firePropertyChanged(propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
		if (oldValue != newValue) {
			this.firePropertyChanged_(propertyName, oldValue, newValue);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified old and new values are different
	 */
	protected void firePropertyChanged_(String propertyName, int oldValue, int newValue) {
		PropertyChangeEvent event = null;
		Iterable<PropertyChangeListener> listeners = this.getPropertyChangeListeners(propertyName);
		if (listeners != null) {
			for (PropertyChangeListener listener : listeners) {
				if (this.hasPropertyChangeListener(propertyName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new PropertyChangeEvent(this.source, propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
					}
					listener.propertyChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new PropertyChangeEvent(this.source, propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
					}
					changeListener.propertyChanged(event);
				}
			}
		}
	}

	/**
	 * Report a <code>boolean</code> bound property update to any registered listeners.
	 * No event is fired if the specified old and new values are equal.
	 * Return whether the old and new values are different.
	 * <p>
	 * This is merely a convenience wrapper around the more general method
	 * {@link #firePropertyChanged(String, Object, Object)}.
	 */
	public boolean firePropertyChanged(String propertyName, boolean oldValue, boolean newValue) {
//		return this.firePropertyChanged(propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
		if (oldValue != newValue) {
			this.firePropertyChanged_(propertyName, oldValue, newValue);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified old and new values are different
	 */
	protected void firePropertyChanged_(String propertyName, boolean oldValue, boolean newValue) {
		PropertyChangeEvent event = null;
		Iterable<PropertyChangeListener> listeners = this.getPropertyChangeListeners(propertyName);
		if (listeners != null) {
			for (PropertyChangeListener listener : listeners) {
				if (this.hasPropertyChangeListener(propertyName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new PropertyChangeEvent(this.source, propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
					}
					listener.propertyChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new PropertyChangeEvent(this.source, propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
					}
					changeListener.propertyChanged(event);
				}
			}
		}
	}


	// ********** collection change support **********

	protected static final Class<CollectionChangeListener> COLLECTION_CHANGE_LISTENER_CLASS = CollectionChangeListener.class;

	/**
	 * Add a collection change listener for the specified collection. The listener
	 * will be notified only for changes to the specified collection.
	 */
	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.addListener(COLLECTION_CHANGE_LISTENER_CLASS, collectionName, listener);
	}

	/**
	 * Remove a collection change listener that was registered for a specific collection.
	 */
	public void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.removeListener(COLLECTION_CHANGE_LISTENER_CLASS, collectionName, listener);
	}

	/**
	 * Return whether there are any collection change listeners that will
	 * be notified when the specified collection has changed.
	 */
	public boolean hasAnyCollectionChangeListeners(String collectionName) {
		return this.hasAnyListeners(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
	}

	private ListenerList<CollectionChangeListener> getCollectionChangeListenerList(String collectionName) {
		return this.getListenerList(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
	}

	private Iterable<CollectionChangeListener> getCollectionChangeListeners(String collectionName) {
		ListenerList<CollectionChangeListener> listenerList = this.getCollectionChangeListenerList(collectionName);
		return (listenerList == null) ? null : listenerList.getListeners();
	}

	private boolean hasCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		return CollectionTools.contains(this.getCollectionChangeListeners(collectionName), listener);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 * Return whether the event has any items.
	 */
	public boolean fireItemsAdded(CollectionAddEvent event) {
		if (event.getItemsSize() != 0) {
			this.fireItemsAdded_(event);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified event contains items
	 */
	protected void fireItemsAdded_(CollectionAddEvent event) {
		String collectionName = event.getCollectionName();
		Iterable<CollectionChangeListener> listeners = this.getCollectionChangeListeners(collectionName);
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(collectionName, listener)) {  // verify listener is still listening
					listener.itemsAdded(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.itemsAdded(event);
				}
			}
		}
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 * Return whether there are any added items.
	 */
	public boolean fireItemsAdded(String collectionName, Collection<?> addedItems) {
//		return this.fireItemsAdded(new CollectionAddEvent(this.source, collectionName, addedItems));
		if ( ! addedItems.isEmpty()) {
			this.fireItemsAdded_(collectionName, addedItems);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: 'addedItems' is not empty
	 */
	protected void fireItemsAdded_(String collectionName, Collection<?> addedItems) {
		CollectionAddEvent event = null;
		Iterable<CollectionChangeListener> listeners = this.getCollectionChangeListeners(collectionName);
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(collectionName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionAddEvent(this.source, collectionName, addedItems);
					}
					listener.itemsAdded(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionAddEvent(this.source, collectionName, addedItems);
					}
					changeListener.itemsAdded(event);
				}
			}
		}
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemAdded(String collectionName, Object addedItem) {
//		this.fireItemsAdded(collectionName, Collections.singleton(addedItem));

		CollectionAddEvent event = null;
		Iterable<CollectionChangeListener> listeners = this.getCollectionChangeListeners(collectionName);
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(collectionName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionAddEvent(this.source, collectionName, addedItem);
					}
					listener.itemsAdded(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionAddEvent(this.source, collectionName, addedItem);
					}
					changeListener.itemsAdded(event);
				}
			}
		}
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 * Return whether the event has any items.
	 */
	public boolean fireItemsRemoved(CollectionRemoveEvent event) {
		if (event.getItemsSize() != 0) {
			this.fireItemsRemoved_(event);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified event contains items
	 */
	protected void fireItemsRemoved_(CollectionRemoveEvent event) {
		String collectionName = event.getCollectionName();
		Iterable<CollectionChangeListener> listeners = this.getCollectionChangeListeners(collectionName);
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(collectionName, listener)) {  // verify listener is still listening
					listener.itemsRemoved(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.itemsRemoved(event);
				}
			}
		}
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 * Return whether there are any removed items.
	 */
	public boolean fireItemsRemoved(String collectionName, Collection<?> removedItems) {
//		return this.fireItemsRemoved(new CollectionRemoveEvent(this.source, collectionName, removedItems));
		if ( ! removedItems.isEmpty()) {
			this.fireItemsRemoved_(collectionName, removedItems);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: 'removedItems' is not empty
	 */
	protected void fireItemsRemoved_(String collectionName, Collection<?> removedItems) {
		CollectionRemoveEvent event = null;
		Iterable<CollectionChangeListener> listeners = this.getCollectionChangeListeners(collectionName);
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(collectionName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionRemoveEvent(this.source, collectionName, removedItems);
					}
					listener.itemsRemoved(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionRemoveEvent(this.source, collectionName, removedItems);
					}
					changeListener.itemsRemoved(event);
				}
			}
		}
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemRemoved(String collectionName, Object removedItem) {
//		this.fireItemsRemoved(collectionName, Collections.singleton(removedItem));

		CollectionRemoveEvent event = null;
		Iterable<CollectionChangeListener> listeners = this.getCollectionChangeListeners(collectionName);
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(collectionName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionRemoveEvent(this.source, collectionName, removedItem);
					}
					listener.itemsRemoved(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionRemoveEvent(this.source, collectionName, removedItem);
					}
					changeListener.itemsRemoved(event);
				}
			}
		}
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionCleared(CollectionClearEvent event) {
		String collectionName = event.getCollectionName();
		Iterable<CollectionChangeListener> listeners = this.getCollectionChangeListeners(collectionName);
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(collectionName, listener)) {  // verify listener is still listening
					listener.collectionCleared(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.collectionCleared(event);
				}
			}
		}
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionCleared(String collectionName) {
//		this.fireCollectionCleared(new CollectionClearEvent(this.source, collectionName));

		CollectionClearEvent event = null;
		Iterable<CollectionChangeListener> listeners = this.getCollectionChangeListeners(collectionName);
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(collectionName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionClearEvent(this.source, collectionName);
					}
					listener.collectionCleared(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionClearEvent(this.source, collectionName);
					}
					changeListener.collectionCleared(event);
				}
			}
		}
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionChanged(CollectionChangeEvent event) {
		String collectionName = event.getCollectionName();
		Iterable<CollectionChangeListener> listeners = this.getCollectionChangeListeners(collectionName);
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(collectionName, listener)) {  // verify listener is still listening
					listener.collectionChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.collectionChanged(event);
				}
			}
		}
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionChanged(String collectionName, Collection<?> collection) {
//		this.fireCollectionChanged(new CollectionChangeEvent(this.source, collectionName, collection));

		CollectionChangeEvent event = null;
		Iterable<CollectionChangeListener> listeners = this.getCollectionChangeListeners(collectionName);
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(collectionName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionChangeEvent(this.source, collectionName, collection);
					}
					listener.collectionChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionChangeEvent(this.source, collectionName, collection);
					}
					changeListener.collectionChanged(event);
				}
			}
		}
	}

	/**
	 * Add the specified item to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#add(Object)
	 */
	public <E> boolean addItemToCollection(E item, Collection<E> collection, String collectionName) {
		if (collection.add(item)) {
			this.fireItemAdded(collectionName, item);
			return true;
		}
		return false;
	}

	/**
	 * Add the specified items to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether collection changed.
	 * @see Collection#addAll(Collection)
	 */
	public <E> boolean addItemsToCollection(E[] items, Collection<E> collection, String collectionName) {
		return (items.length != 0)
				&& this.addItemsToCollection_(new ArrayIterator<E>(items), collection, collectionName);
	}

	/**
	 * Add the specified items to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether collection changed.
	 * @see Collection#addAll(Collection)
	 */
	public <E> boolean addItemsToCollection(Collection<? extends E> items, Collection<E> collection, String collectionName) {
		return ( ! items.isEmpty())
				&& this.addItemsToCollection_(items.iterator(), collection, collectionName);
	}

	/**
	 * Add the specified items to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether collection changed.
	 * @see Collection#addAll(Collection)
	 */
	public <E> boolean addItemsToCollection(Iterable<? extends E> items, Collection<E> collection, String collectionName) {
		return this.addItemsToCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Add the specified items to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether collection changed.
	 * @see Collection#addAll(Collection)
	 */
	public <E> boolean addItemsToCollection(Iterator<? extends E> items, Collection<E> collection, String collectionName) {
		return items.hasNext()
				&& this.addItemsToCollection_(items, collection, collectionName);
	}

	/**
	 * no empty check
	 */
	protected <E> boolean addItemsToCollection_(Iterator<? extends E> items, Collection<E> collection, String collectionName) {
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
			this.fireItemsAdded_(collectionName, addedItems);
			return true;
		}
		return false;
	}

	/**
	 * Remove the specified item from the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#remove(Object)
	 */
	public boolean removeItemFromCollection(Object item, Collection<?> collection, String collectionName) {
		if (collection.remove(item)) {
			this.fireItemRemoved(collectionName, item);
			return true;
		}
		return false;
	}

	/**
	 * Remove the specified items from the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#removeAll(Collection)
	 */
	public boolean removeItemsFromCollection(Object[] items, Collection<?> collection, String collectionName) {
		return (items.length != 0)
				&& ( ! collection.isEmpty())
				&& this.removeItemsFromCollection_(new ArrayIterator<Object>(items), collection, collectionName);
	}

	/**
	 * Remove the specified items from the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#removeAll(Collection)
	 */
	public boolean removeItemsFromCollection(Collection<?> items, Collection<?> collection, String collectionName) {
		return ( ! items.isEmpty())
				&& ( ! collection.isEmpty())
				&& this.removeItemsFromCollection_(items.iterator(), collection, collectionName);
	}

	/**
	 * Remove the specified items from the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#removeAll(Collection)
	 */
	public boolean removeItemsFromCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.removeItemsFromCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Remove the specified items from the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#removeAll(Collection)
	 */
	public boolean removeItemsFromCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		return items.hasNext()
				&& ( ! collection.isEmpty())
				&& this.removeItemsFromCollection_(items, collection, collectionName);
	}

	/**
	 * no empty checks
	 */
	protected boolean removeItemsFromCollection_(Iterator<?> items, Collection<?> collection, String collectionName) {
		HashBag<?> removedItems = CollectionTools.collection(items);
		removedItems.retainAll(collection);
		boolean changed = collection.removeAll(removedItems);

		if ( ! removedItems.isEmpty()) {
			this.fireItemsRemoved_(collectionName, removedItems);
		}
		return changed;
	}

	/**
	 * Retain the specified items in the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#retainAll(Collection)
	 */
	public boolean retainItemsInCollection(Object[] items, Collection<?> collection, String collectionName) {
		if (collection.isEmpty()) {
			return false;
		}
		if (items.length == 0) {
			return this.clearCollection_(collection, collectionName);
		}
		return this.retainItemsInCollection_(new ArrayIterator<Object>(items), collection, collectionName);
	}

	/**
	 * Retain the specified items in the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#retainAll(Collection)
	 */
	public boolean retainItemsInCollection(Collection<?> items, Collection<?> collection, String collectionName) {
		if (collection.isEmpty()) {
			return false;
		}
		if (items.isEmpty()) {
			return this.clearCollection_(collection, collectionName);
		}
		return this.retainItemsInCollection_(items.iterator(), collection, collectionName);
	}

	/**
	 * Retain the specified items in the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#retainAll(Collection)
	 */
	public boolean retainItemsInCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.retainItemsInCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Retain the specified items in the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#retainAll(Collection)
	 */
	public boolean retainItemsInCollection(Iterator<?> items, Collection<?> collection, String collectionName) {
		if (collection.isEmpty()) {
			return false;
		}
		if ( ! items.hasNext()) {
			return this.clearCollection_(collection, collectionName);
		}
		return this.retainItemsInCollection_(items, collection, collectionName);
	}

	/**
	 * no empty checks
	 */
	protected boolean retainItemsInCollection_(Iterator<?> items, Collection<?> collection, String collectionName) {
		HashBag<?> retainedItems = CollectionTools.collection(items);
		HashBag<?> removedItems = CollectionTools.collection(collection);
		removedItems.removeAll(retainedItems);
		boolean changed = collection.retainAll(retainedItems);

		if ( ! removedItems.isEmpty()) {
			this.fireItemsRemoved_(collectionName, removedItems);
		}
		return changed;
	}

	/**
	 * Clear the entire collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see Collection#clear()
	 */
	public boolean clearCollection(Collection<?> collection, String collectionName) {
		if (collection.isEmpty()) {
			return false;
		}
		return this.clearCollection_(collection, collectionName);
	}

	/**
	 * no empty check
	 */
	protected boolean clearCollection_(Collection<?> collection, String collectionName) {
		collection.clear();
		this.fireCollectionCleared(collectionName);
		return true;
	}

	/**
	 * Synchronize the collection with the specified new collection,
	 * making a minimum number of removes and adds.
	 * Return whether the collection changed.
	 */
	public <E> boolean synchronizeCollection(Collection<E> newCollection, Collection<E> collection, String collectionName) {
		if (newCollection.isEmpty()) {
			return this.clearCollection(collection, collectionName);
		}

		if (collection.isEmpty()) {
			return this.addItemsToCollection_(newCollection.iterator(), collection, collectionName);
		}

		return this.synchronizeCollection_(newCollection, collection, collectionName);
	}

	/**
	 * Synchronize the collection with the specified new collection,
	 * making a minimum number of removes and adds.
	 * Return whether the collection changed.
	 */
	public <E> boolean synchronizeCollection(Iterable<E> newCollection, Collection<E> collection, String collectionName) {
		return this.synchronizeCollection(newCollection.iterator(), collection, collectionName);
	}

	/**
	 * Synchronize the collection with the specified new collection,
	 * making a minimum number of removes and adds.
	 * Return whether the collection changed.
	 */
	public <E> boolean synchronizeCollection(Iterator<E> newCollection, Collection<E> collection, String collectionName) {
		if ( ! newCollection.hasNext()) {
			return this.clearCollection(collection, collectionName);
		}

		if (collection.isEmpty()) {
			return this.addItemsToCollection_(newCollection, collection, collectionName);
		}

		return this.synchronizeCollection_(CollectionTools.collection(newCollection), collection, collectionName);
	}

	/**
	 * no empty checks
	 */
	protected <E> boolean synchronizeCollection_(Collection<E> newCollection, Collection<E> collection, String collectionName) {
		boolean changed = false;
		Collection<E> removeItems = new HashBag<E>(collection);
		removeItems.removeAll(newCollection);
		changed |= this.removeItemsFromCollection(removeItems, collection, collectionName);

		Collection<E> addItems = new HashBag<E>(newCollection);
		addItems.removeAll(collection);
		changed |= this.addItemsToCollection(addItems, collection, collectionName);

		return changed;
	}


	// ********** list change support **********

	protected static final Class<ListChangeListener> LIST_CHANGE_LISTENER_CLASS = ListChangeListener.class;

	/**
	 * Add a list change listener for the specified list. The listener
	 * will be notified only for changes to the specified list.
	 */
	public void addListChangeListener(String listName, ListChangeListener listener) {
		this.addListener(LIST_CHANGE_LISTENER_CLASS, listName, listener);
	}

	/**
	 * Remove a list change listener that was registered for a specific list.
	 */
	public void removeListChangeListener(String listName, ListChangeListener listener) {
		this.removeListener(LIST_CHANGE_LISTENER_CLASS, listName, listener);
	}

	/**
	 * Return whether there are any list change listeners that will
	 * be notified when the specified list has changed.
	 */
	public boolean hasAnyListChangeListeners(String listName) {
		return this.hasAnyListeners(LIST_CHANGE_LISTENER_CLASS, listName);
	}

	private ListenerList<ListChangeListener> getListChangeListenerList(String listName) {
		return this.getListenerList(LIST_CHANGE_LISTENER_CLASS, listName);
	}

	private Iterable<ListChangeListener> getListChangeListeners(String listName) {
		ListenerList<ListChangeListener> listenerList = this.getListChangeListenerList(listName);
		return (listenerList == null) ? null : listenerList.getListeners();
	}

	private boolean hasListChangeListener(String listName, ListChangeListener listener) {
		return CollectionTools.contains(this.getListChangeListeners(listName), listener);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether there are any added items.
	 */
	public boolean fireItemsAdded(ListAddEvent event) {
		if (event.getItemsSize() != 0) {
			this.fireItemsAdded_(event);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified event contains items
	 */
	protected void fireItemsAdded_(ListAddEvent event) {
		String listName = event.getListName();
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					listener.itemsAdded(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.itemsAdded(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether there are any added items.
	 */
	public boolean fireItemsAdded(String listName, int index, List<?> addedItems) {
//		return this.fireItemsAdded(new ListAddEvent(this.source, listName, index, addedItems));
		if ( ! addedItems.isEmpty()) {
			this.fireItemsAdded_(listName, index, addedItems);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: 'addedItems' is not empty
	 */
	protected void fireItemsAdded_(String listName, int index, List<?> addedItems) {
		ListAddEvent event = null;
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListAddEvent(this.source, listName, index, addedItems);
					}
					listener.itemsAdded(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListAddEvent(this.source, listName, index, addedItems);
					}
					changeListener.itemsAdded(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemAdded(String listName, int index, Object addedItem) {
//		this.fireItemsAdded(listName, index, Collections.singletonList(addedItem));

		ListAddEvent event = null;
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListAddEvent(this.source, listName, index, addedItem);
					}
					listener.itemsAdded(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListAddEvent(this.source, listName, index, addedItem);
					}
					changeListener.itemsAdded(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether there are any removed items.
	 */
	public boolean fireItemsRemoved(ListRemoveEvent event) {
		if (event.getItemsSize() != 0) {
			this.fireItemsRemoved_(event);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified event contains items
	 */
	protected void fireItemsRemoved_(ListRemoveEvent event) {
		String listName = event.getListName();
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					listener.itemsRemoved(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.itemsRemoved(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether there are any removed items.
	 */
	public boolean fireItemsRemoved(String listName, int index, List<?> removedItems) {
//		return this.fireItemsRemoved(new ListRemoveEvent(this.source, listName, index, removedItems));
		if ( ! removedItems.isEmpty()) {
			this.fireItemsRemoved_(listName, index, removedItems);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: 'removedItems' is not empty
	 */
	protected void fireItemsRemoved_(String listName, int index, List<?> removedItems) {
		ListRemoveEvent event = null;
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListRemoveEvent(this.source, listName, index, removedItems);
					}
					listener.itemsRemoved(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListRemoveEvent(this.source, listName, index, removedItems);
					}
					changeListener.itemsRemoved(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemRemoved(String listName, int index, Object removedItem) {
//		this.fireItemsRemoved(listName, index, Collections.singletonList(removedItem));

		ListRemoveEvent event = null;
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListRemoveEvent(this.source, listName, index, removedItem);
					}
					listener.itemsRemoved(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListRemoveEvent(this.source, listName, index, removedItem);
					}
					changeListener.itemsRemoved(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether there are any replaced items.
	 */
	public boolean fireItemsReplaced(ListReplaceEvent event) {
		if ((event.getItemsSize() != 0) && this.elementsAreDifferent(event.getNewItems(), event.getOldItems())) {
			this.fireItemsReplaced_(event);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified event contains new items
	 */
	protected void fireItemsReplaced_(ListReplaceEvent event) {
		String listName = event.getListName();
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					listener.itemsReplaced(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.itemsReplaced(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether there are any replaced items.
	 */
	public boolean fireItemsReplaced(String listName, int index, List<?> newItems, List<?> oldItems) {
//		return this.fireItemsReplaced(new ListReplaceEvent(this.source, listName, index, newItems, oldItems));
		if (( ! newItems.isEmpty()) && this.elementsAreDifferent(newItems, oldItems)) {
			this.fireItemsReplaced_(listName, index, newItems, oldItems);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: 'newItems' is not empty and unequal to 'oldItems'
	 */
	protected void fireItemsReplaced_(String listName, int index, List<?> newItems, List<?> oldItems) {
		ListReplaceEvent event = null;
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListReplaceEvent(this.source, listName, index, newItems, oldItems);
					}
					listener.itemsReplaced(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListReplaceEvent(this.source, listName, index, newItems, oldItems);
					}
					changeListener.itemsReplaced(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether the item changed.
	 */
	public boolean fireItemReplaced(String listName, int index, Object newItem, Object oldItem) {
//		return this.fireItemsReplaced(listName, index, Collections.singletonList(newItem), Collections.singletonList(oldItem));
		if (this.valuesAreDifferent(newItem, oldItem)) {
			this.fireItemReplaced_(listName, index, newItem, oldItem);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified old and new items are different
	 */
	protected void fireItemReplaced_(String listName, int index, Object newItem, Object oldItem) {
		ListReplaceEvent event = null;
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListReplaceEvent(this.source, listName, index, newItem, oldItem);
					}
					listener.itemsReplaced(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListReplaceEvent(this.source, listName, index, newItem, oldItem);
					}
					changeListener.itemsReplaced(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether there are any moved items.
	 */
	// it's unlikely but possible the list is unchanged by the move...
	// e.g. any moves within ["foo", "foo", "foo"]
	public boolean fireItemsMoved(ListMoveEvent event) {
		if (event.getTargetIndex() != event.getSourceIndex()) {
			this.fireItemsMoved_(event);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified event indicates a move
	 */
	protected void fireItemsMoved_(ListMoveEvent event) {
		String listName = event.getListName();
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					listener.itemsMoved(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.itemsMoved(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether there are any moved items.
	 */
	// it's unlikely but possible the list is unchanged by the move...
	// e.g. any moves within ["foo", "foo", "foo"]
	public boolean fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
//		return this.fireItemsMoved(new ListMoveEvent(this.source, listName, targetIndex, sourceIndex, length));
		if (targetIndex != sourceIndex) {
			this.fireItemsMoved_(listName, targetIndex, sourceIndex, length);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified indices indicate a move
	 */
	protected void fireItemsMoved_(String listName, int targetIndex, int sourceIndex, int length) {
		ListMoveEvent event = null;
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListMoveEvent(this.source, listName, targetIndex, sourceIndex, length);
					}
					listener.itemsMoved(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListMoveEvent(this.source, listName, targetIndex, sourceIndex, length);
					}
					changeListener.itemsMoved(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether there are any moved items.
	 */
	// it's unlikely but possible the list is unchanged by the move...
	// e.g. any moves within ["foo", "foo", "foo"]
	public boolean fireItemMoved(String listName, int targetIndex, int sourceIndex) {
		if (targetIndex != sourceIndex) {
			this.fireItemMoved_(listName, targetIndex, sourceIndex);
			return true;
		}
		return false;
	}

	/**
	 * pre-condition: the specified indices indicate a move
	 */
	protected void fireItemMoved_(String listName, int targetIndex, int sourceIndex) {
		this.fireItemsMoved_(listName, targetIndex, sourceIndex, 1);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListCleared(ListClearEvent event) {
		String listName = event.getListName();
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					listener.listCleared(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.listCleared(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListCleared(String listName) {
//		this.fireListCleared(new ListClearEvent(this.source, listName));

		ListClearEvent event = null;
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListClearEvent(this.source, listName);
					}
					listener.listCleared(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListClearEvent(this.source, listName);
					}
					changeListener.listCleared(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListChanged(ListChangeEvent event) {
		String listName = event.getListName();
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					listener.listChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.listChanged(event);
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListChanged(String listName, List<?> list) {
//		this.fireListChanged(new ListChangeEvent(this.source, listName));

		ListChangeEvent event = null;
		Iterable<ListChangeListener> listeners = this.getListChangeListeners(listName);
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListChangeEvent(this.source, listName, list);
					}
					listener.listChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListChangeEvent(this.source, listName, list);
					}
					changeListener.listChanged(event);
				}
			}
		}
	}

	/**
	 * Add the specified item to the specified bound list at the specified index
	 * and fire the appropriate event.
	 * @see List#add(int, Object)
	 */
	public <E> void addItemToList(int index, E item, List<E> list, String listName) {
		list.add(index, item);
		this.fireItemAdded(listName, index, item);
	}

	/**
	 * Add the specified item to the end of the specified bound list
	 * and fire the appropriate event.
	 * Return whether the list changed (i.e. 'true').
	 * @see List#add(Object)
	 */
	public <E> boolean addItemToList(E item, List<E> list, String listName) {
		if (list.add(item)) {
			this.fireItemAdded(listName, list.size() - 1, item);
			return true;
		}
		return false;  // List#add(Object) should always return 'true', so we should never get here...
	}

	/**
	 * Add the specified items to the specified bound list at the specified index
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see List#addAll(int, Collection)
	 */
	public <E> boolean addItemsToList(int index, E[] items, List<E> list, String listName) {
		return (items.length != 0)
				&& this.addItemsToList_(index, Arrays.asList(items), list, listName);
	}

	/**
	 * Add the specified items to the specified bound list at the specified index
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see List#addAll(int, Collection)
	 */
	public <E> boolean addItemsToList(int index, Collection<? extends E> items, List<E> list, String listName) {
		return ( ! items.isEmpty())
				&& this.addItemsToList_(index, this.convertToList(items), list, listName);
	}

	/**
	 * no empty check
	 */
	protected <E> boolean addItemsToList_(int index, List<? extends E> items, List<E> list, String listName) {
		if (list.addAll(index, items)) {
			this.fireItemsAdded(listName, index, items);
			return true;
		}
		return false;  //  'items' should not be empty, so we should never get here...
	}

	/**
	 * Add the specified items to the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see List#addAll(int, Collection)
	 */
	public <E> boolean addItemsToList(int index, Iterable<? extends E> items, List<E> list, String listName) {
		return this.addItemsToList(index, items.iterator(), list, listName);
	}

	/**
	 * Add the specified items to the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see List#addAll(int, Collection)
	 */
	public <E> boolean addItemsToList(int index, Iterator<? extends E> items, List<E> list, String listName) {
		if ( ! items.hasNext()) {
			return false;
		}

		ArrayList<E> addedItems = CollectionTools.list(items);
		if (list.addAll(index, addedItems)) {
			this.fireItemsAdded(listName, index, addedItems);
			return true;
		}
		return false;  //  'items' should not be empty, so we should never get here...
	}

	/**
	 * Add the specified items to the end of to the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see List#addAll(Collection)
	 */
	public <E> boolean addItemsToList(E[] items, List<E> list, String listName) {
		return (items.length != 0)
				&& this.addItemsToList_(Arrays.asList(items), list, listName);
	}

	/**
	 * Add the specified items to the end of the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see List#addAll(int, Collection)
	 */
	public <E> boolean addItemsToList(Collection<? extends E> items, List<E> list, String listName) {
		return ( ! items.isEmpty())
				&& this.addItemsToList_(this.convertToList(items), list, listName);
	}

	protected <E> List<? extends E> convertToList(Collection<? extends E> collection) {
		return (collection instanceof List<?>) ? (List<? extends E>) collection : new ArrayList<E>(collection);
	}

	/**
	 * no empty check
	 */
	protected <E> boolean addItemsToList_(List<? extends E> items, List<E> list, String listName) {
		int index = list.size();
		if (list.addAll(items)) {
			this.fireItemsAdded(listName, index, items);
			return true;
		}
		return false;  //  'items' should not be empty, so we should never get here...
	}

	/**
	 * Add the specified items to the end of to the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see List#addAll(Collection)
	 */
	public <E> boolean addItemsToList(Iterable<? extends E> items, List<E> list, String listName) {
		return this.addItemsToList(items.iterator(), list, listName);
	}

	/**
	 * Add the specified items to the end of to the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see List#addAll(Collection)
	 */
	public <E> boolean addItemsToList(Iterator<? extends E> items, List<E> list, String listName) {
		if ( ! items.hasNext()) {
			return false;
		}
		return this.addItemsToList_(items, list, listName);
	}

	/**
	 * no empty check
	 */
	protected <E> boolean addItemsToList_(Iterator<? extends E> items, List<E> list, String listName) {
		ArrayList<E> addedItems = CollectionTools.list(items);
		int index = list.size();
		if (list.addAll(addedItems)) {
			this.fireItemsAdded(listName, index, addedItems);
			return true;
		}
		return false;  //  'items' should not be empty, so we should never get here...
	}

	/**
	 * Remove the specified item from the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the removed item.
	 * @see List#remove(int)
	 */
	public <E> E removeItemFromList(int index, List<E> list, String listName) {
		E item = list.remove(index);
		this.fireItemRemoved(listName, index, item);
		return item;
	}

	/**
	 * Remove the specified item from the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see List#remove(Object)
	 */
	public boolean removeItemFromList(Object item, List<?> list, String listName) {
		int index = list.indexOf(item);
		if (index == -1) {
			return false;
		}
		list.remove(index);
		this.fireItemRemoved(listName, index, item);
		return true;
	}

	/**
	 * Remove the items from the specified index to the end of the list
	 * from the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the removed items.
	 * @see List#remove(int)
	 */
	public <E> List<E> removeItemsFromList(int index, List<E> list, String listName) {
		return this.removeRangeFromList(index, list.size(), list, listName);
	}

	/**
	 * Remove the specified items from the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the removed items.
	 * @see List#remove(int)
	 */
	public <E> List<E> removeItemsFromList(int index, int length, List<E> list, String listName) {
		return this.removeRangeFromList(index, index + length, list, listName);
	}

	/**
	 * Remove the specified items from the specified bound list
	 * and fire the appropriate event if necessary. The begin index
	 * is inclusive, while the end index is exclusive.
	 * Return the removed items.
	 * @see List#remove(int)
	 * @see List#subList(int, int)
	 */
	public <E> List<E> removeRangeFromList(int beginIndex, int endIndex, List<E> list, String listName) {
		if (beginIndex == endIndex) {
			return Collections.emptyList();
		}
		return this.removeRangeFromList_(beginIndex, endIndex, list, listName);
	}

	/**
	 * no empty check
	 */
	protected <E> List<E> removeRangeFromList_(int beginIndex, int endIndex, List<E> list, String listName) {
		List<E> subList = list.subList(beginIndex, endIndex);
		List<E> removedItems = new ArrayList<E>(subList);
		subList.clear();
		this.fireItemsRemoved(listName, beginIndex, removedItems);
		return removedItems;
	}

	/**
	 * Remove the specified items from the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see List#removeAll(Collection)
	 */
	public boolean removeItemsFromList(Object[] items, List<?> list, String listName) {
		return (items.length != 0)
				&& ( ! list.isEmpty())
				&& this.removeItemsFromList_(new ArrayIterator<Object>(items), list, listName);
	}

	/**
	 * Remove the specified items from the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see List#removeAll(Collection)
	 */
	public boolean removeItemsFromList(Collection<?> items, List<?> list, String listName) {
		return ( ! items.isEmpty())
				&& ( ! list.isEmpty())
				&& this.removeItemsFromList_(items.iterator(), list, listName);
	}

	/**
	 * Remove the specified items from the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see List#removeAll(Collection)
	 */
	public boolean removeItemsFromList(Iterable<?> items, List<?> list, String listName) {
		return this.removeItemsFromList(items.iterator(), list, listName);
	}

	/**
	 * Remove the specified items from the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see List#removeAll(Collection)
	 */
	public boolean removeItemsFromList(Iterator<?> items, List<?> list, String listName) {
		return (items.hasNext())
				&& ( ! list.isEmpty())
				&& this.removeItemsFromList_(items, list, listName);
	}

	/**
	 * no empty checks
	 */
	protected boolean removeItemsFromList_(Iterator<?> items, List<?> list, String listName) {
		boolean changed = false;
		while (items.hasNext()) {
			changed |= this.removeItemFromList(items.next(), list, listName);
		}
		return changed;
	}

	/**
	 * Retain the specified items in the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see List#retainAll(Collection)
	 */
	public boolean retainItemsInList(Object[] items, List<?> list, String listName) {
		if (list.isEmpty()) {
			return false;
		}
		if (items.length == 0) {
			return this.clearList_(list, listName);
		}
		return this.retainItemsInList_(new ArrayIterator<Object>(items), list, listName);
	}

	/**
	 * Retain the specified items in the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see List#retainAll(Collection)
	 */
	public boolean retainItemsInList(Collection<?> items, List<?> list, String listName) {
		if (list.isEmpty()) {
			return false;
		}
		if (items.isEmpty()) {
			return this.clearList_(list, listName);
		}
		return this.retainItemsInList_(items.iterator(), list, listName);
	}

	/**
	 * Retain the specified items in the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see List#retainAll(Collection)
	 */
	public boolean retainItemsInList(Iterable<?> items, List<?> list, String listName) {
		return this.retainItemsInList(items.iterator(), list, listName);
	}

	/**
	 * Retain the specified items in the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see List#retainAll(Collection)
	 */
	public boolean retainItemsInList(Iterator<?> items, List<?> list, String listName) {
		if (list.isEmpty()) {
			return false;
		}
		if ( ! items.hasNext()) {
			return this.clearList_(list, listName);
		}
		return this.retainItemsInList_(items, list, listName);
	}

	/**
	 * no empty checks
	 */
	protected boolean retainItemsInList_(Iterator<?> items, List<?> list, String listName) {
		HashBag<?> retainedItems = CollectionTools.collection(items);
		HashBag<?> removedItems = CollectionTools.collection(list);
		removedItems.removeAll(retainedItems);
		return this.removeItemsFromList(removedItems, list, listName);
	}

	/**
	 * Set the specified item in the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the replaced item.
	 * @see List#set(int, Object)
	 */
	public <E> E setItemInList(int index, E item, List<E> list, String listName) {
		E oldItem = list.set(index, item);
		this.fireItemReplaced(listName, index, item, oldItem);
		return oldItem;
	}

	/**
	 * Replace the first occurrence of the specified item
	 * in the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the index of the replaced item.
	 * Return -1 if the item was not found in the list.
	 * @see List#set(int, Object)
	 */
	public <E> int replaceItemInList(E oldItem, E newItem, List<E> list, String listName) {
		if (list.isEmpty()) {
			return -1;
		}

		int index = list.indexOf(oldItem);
		if ((index != -1) && this.valuesAreDifferent(oldItem, newItem)) {
			list.set(index, newItem);
			this.fireItemReplaced_(listName, index, newItem, oldItem);
		}
		return index;
	}

	/**
	 * Set the specified items in the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the replaced items.
	 * @see List#set(int, Object)
	 */
	public <E> List<E> setItemsInList(int index, E[] items, List<E> list, String listName) {
		if (items.length == 0) {
			return Collections.emptyList();
		}
		return this.setItemsInList_(index, Arrays.asList(items), list, listName);
	}

	/**
	 * Set the specified items in the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the replaced items.
	 * @see List#set(int, Object)
	 */
	public <E> List<E> setItemsInList(int index, List<? extends E> items, List<E> list, String listName) {
		if (items.isEmpty()) {
			return Collections.emptyList();
		}
		return this.setItemsInList_(index, items, list, listName);
	}

	/**
	 * no empty check
	 */
	protected <E> List<E> setItemsInList_(int index, List<? extends E> items, List<E> list, String listName) {
		List<E> subList = list.subList(index, index + items.size());
		List<E> oldItems = new ArrayList<E>(subList);
		for (int i = 0; i < items.size(); i++) {
			E newItem = items.get(i);
			E oldItem = subList.set(i, newItem);
			this.fireItemReplaced(listName, index + i, newItem, oldItem);
		}
		return oldItems;
	}

	/**
	 * Move items in the specified list from the specified source index to the
	 * specified target index for the specified length.
	 * Return whether the list changed.
	 */
	public <E> boolean moveItemsInList(int targetIndex, int sourceIndex, int length, List<E> list, String listName) {
		if ((targetIndex == sourceIndex) || (length == 0)) {
			return false;
		}
		// it's unlikely but possible the list is unchanged by the move... (e.g. any moves within ["foo", "foo", "foo"]...)
		CollectionTools.move(list, targetIndex, sourceIndex, length);
		this.fireItemsMoved(listName, targetIndex, sourceIndex, length);
		return true;
	}

	/**
	 * Move the specified item in the specified list to the
	 * specified target index.
	 * Return whether the list changed.
	 */
	public <E> boolean moveItemInList(int targetIndex, E item, List<E> list, String listName) {
		return this.moveItemInList(targetIndex, list.indexOf(item), list, listName);
	}

	/**
	 * Move an item in the specified list from the specified source index to the
	 * specified target index.
	 * Return whether the list changed.
	 */
	public <E> boolean moveItemInList(int targetIndex, int sourceIndex, List<E> list, String listName) {
		if (targetIndex == sourceIndex) {
			return false;
		}
		if (this.valuesAreEqual(list.get(targetIndex), list.get(sourceIndex))) {
			return false;
		}
		CollectionTools.move(list, targetIndex, sourceIndex);
		this.fireItemMoved_(listName, targetIndex, sourceIndex);
		return true;
	}

	/**
	 * Clear the entire list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see List#clear()
	 */
	public boolean clearList(List<?> list, String listName) {
		return ( ! list.isEmpty()) && this.clearList_(list, listName);
	}

	/**
	 * no empty check
	 */
	protected boolean clearList_(List<?> list, String listName) {
		list.clear();
		this.fireListCleared(listName);
		return true;
	}

	/**
	 * Synchronize the list with the specified new list,
	 * making a minimum number of sets, removes, and/or adds.
	 * Return whether the list changed.
	 */
	public <E> boolean synchronizeList(List<E> newList, List<E> list, String listName) {
		if (newList.isEmpty()) {
			return this.clearList(list, listName);
		}
		if (list.isEmpty()) {
			return this.addItemsToList_(newList, list, listName);
		}
		return this.synchronizeList_(newList, list, listName);
	}

	/**
	 * Synchronize the list with the specified new list,
	 * making a minimum number of sets, removes, and/or adds.
	 * Return whether the list changed.
	 */
	public <E> boolean synchronizeList(Iterable<? extends E> newList, List<E> list, String listName) {
		return this.synchronizeList(newList.iterator(), list, listName);
	}

	/**
	 * Synchronize the list with the specified new list,
	 * making a minimum number of sets, removes, and/or adds.
	 * Return whether the list changed.
	 */
	public <E> boolean synchronizeList(Iterator<? extends E> newList, List<E> list, String listName) {
		if ( ! newList.hasNext()) {
			return this.clearList(list, listName);
		}
		if (list.isEmpty()) {
			return this.addItemsToList_(newList, list, listName);
		}
		return this.synchronizeList_(CollectionTools.list(newList), list, listName);
	}

	/**
	 * no empty checks
	 */
	protected <E> boolean synchronizeList_(List<? extends E> newList, List<E> oldList, String listName) {
		int newSize = newList.size();
		int oldSize = oldList.size();

		boolean changed = false;
		// TODO check for RandomAccess
		int min = Math.min(newSize, oldSize);
		for (int i = 0; i < min; i++) {
			E newItem = newList.get(i);
			E oldItem = oldList.set(i, newItem);
			if (this.valuesAreDifferent(newItem, oldItem)) {
				changed = true;
				this.fireItemReplaced_(listName, i, newItem, oldItem);
			}
		}

		if (newSize == oldSize) {
			return changed;
		}

		if (newSize < oldSize) {
			this.removeRangeFromList_(newSize, oldSize, oldList, listName);
			return true;
		}

		// newSize > oldSize
		this.addItemsToList_(newList.subList(oldSize, newSize), oldList, listName);
		return true;
	}


	// ********** tree change support **********

	protected static final Class<TreeChangeListener> TREE_CHANGE_LISTENER_CLASS = TreeChangeListener.class;

	/**
	 * Add a tree change listener for the specified tree. The listener
	 * will be notified only for changes to the specified tree.
	 */
	public void addTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.addListener(TREE_CHANGE_LISTENER_CLASS, treeName, listener);
	}

	/**
	 * Remove a tree change listener that was registered for a specific tree.
	 */
	public void removeTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.removeListener(TREE_CHANGE_LISTENER_CLASS, treeName, listener);
	}

	/**
	 * Return whether there are any tree change listeners that will
	 * be notified when the specified tree has changed.
	 */
	public boolean hasAnyTreeChangeListeners(String treeName) {
		return this.hasAnyListeners(TREE_CHANGE_LISTENER_CLASS, treeName);
	}

	private ListenerList<TreeChangeListener> getTreeChangeListenerList(String treeName) {
		return this.getListenerList(TREE_CHANGE_LISTENER_CLASS, treeName);
	}

	private Iterable<TreeChangeListener> getTreeChangeListeners(String treeName) {
		ListenerList<TreeChangeListener> listenerList = this.getTreeChangeListenerList(treeName);
		return (listenerList == null) ? null : listenerList.getListeners();
	}

	private boolean hasTreeChangeListener(String treeName, TreeChangeListener listener) {
		return CollectionTools.contains(this.getTreeChangeListeners(treeName), listener);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeAdded(TreeAddEvent event) {
		String treeName = event.getTreeName();
		Iterable<TreeChangeListener> listeners = this.getTreeChangeListeners(treeName);
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(treeName, listener)) {  // verify listener is still listening
					listener.nodeAdded(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.nodeAdded(event);
				}
			}
		}
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeAdded(String treeName, List<?> path) {
//		this.fireNodeAdded(new TreeAddEvent(this.source, treeName, path));
		TreeAddEvent event = null;
		Iterable<TreeChangeListener> listeners = this.getTreeChangeListeners(treeName);
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(treeName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new TreeAddEvent(this.source, treeName, path);
					}
					listener.nodeAdded(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new TreeAddEvent(this.source, treeName, path);
					}
					changeListener.nodeAdded(event);
				}
			}
		}
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeRemoved(TreeRemoveEvent event) {
		String treeName = event.getTreeName();
		Iterable<TreeChangeListener> listeners = this.getTreeChangeListeners(treeName);
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(treeName, listener)) {  // verify listener is still listening
					listener.nodeRemoved(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.nodeRemoved(event);
				}
			}
		}
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeRemoved(String treeName, List<?> path) {
//		this.fireNodeRemoved(new TreeRemoveEvent(this.source, treeName, path));

		TreeRemoveEvent event = null;
		Iterable<TreeChangeListener> listeners = this.getTreeChangeListeners(treeName);
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(treeName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new TreeRemoveEvent(this.source, treeName, path);
					}
					listener.nodeRemoved(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new TreeRemoveEvent(this.source, treeName, path);
					}
					changeListener.nodeRemoved(event);
				}
			}
		}
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeCleared(TreeClearEvent event) {
		String treeName = event.getTreeName();
		Iterable<TreeChangeListener> listeners = this.getTreeChangeListeners(treeName);
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(treeName, listener)) {  // verify listener is still listening
					listener.treeCleared(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.treeCleared(event);
				}
			}
		}
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeCleared(String treeName) {
//		this.fireTreeCleared(new TreeClearEvent(this.source, treeName));

		TreeClearEvent event = null;
		Iterable<TreeChangeListener> listeners = this.getTreeChangeListeners(treeName);
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(treeName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new TreeClearEvent(this.source, treeName);
					}
					listener.treeCleared(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new TreeClearEvent(this.source, treeName);
					}
					changeListener.treeCleared(event);
				}
			}
		}
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeChanged(TreeChangeEvent event) {
		String treeName = event.getTreeName();
		Iterable<TreeChangeListener> listeners = this.getTreeChangeListeners(treeName);
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(treeName, listener)) {  // verify listener is still listening
					listener.treeChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					changeListener.treeChanged(event);
				}
			}
		}
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeChanged(String treeName, Collection<?> nodes) {
//		this.fireTreeChanged(new TreeChangeEvent(this.source, treeName, nodes));

		TreeChangeEvent event = null;
		Iterable<TreeChangeListener> listeners = this.getTreeChangeListeners(treeName);
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(treeName, listener)) {  // verify listener is still listening
					if (event == null) {
						event = new TreeChangeEvent(this.source, treeName, nodes);
					}
					listener.treeChanged(event);
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener changeListener : changeListeners) {
				if (this.hasChangeListener(changeListener)) {  // verify listener is still listening
					if (event == null) {
						event = new TreeChangeEvent(this.source, treeName, nodes);
					}
					changeListener.treeChanged(event);
				}
			}
		}
	}


	// ********** misc **********

	/**
	 * Convenience method for checking whether an attribute value has changed.
	 * @see Tools#valuesAreEqual(Object, Object)
	 */
	public boolean valuesAreEqual(Object value1, Object value2) {
		return Tools.valuesAreEqual(value1, value2);
	}

	/**
	 * Convenience method for checking whether an attribute value has changed.
	 * @see Tools#valuesAreDifferent(Object, Object)
	 */
	public boolean valuesAreDifferent(Object value1, Object value2) {
		return Tools.valuesAreDifferent(value1, value2);
	}

	/**
	 * @see CollectionTools#elementsAreEqual(Iterable, Iterable)
	 */
	public boolean elementsAreEqual(Iterable<?> iterable1, Iterable<?> iterable2) {
		return CollectionTools.elementsAreEqual(iterable1, iterable2);
	}

	/**
	 * @see CollectionTools#elementsAreDifferent(Iterable, Iterable)
	 */
	public boolean elementsAreDifferent(Iterable<?> iterable1, Iterable<?> iterable2) {
		return CollectionTools.elementsAreDifferent(iterable1, iterable2);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.source);
	}


	// ********** member classes **********

	/**
	 * Pair a possibly <code>null</code> aspect name with its associated
	 * listeners.
	 */
	static abstract class AspectListenerListPair<L extends EventListener>
		implements Serializable
	{
		final ListenerList<L> listenerList;

		private static final long serialVersionUID = 1L;

		AspectListenerListPair(Class<L> listenerClass, L listener) {
			super();
			this.listenerList = new ListenerList<L>(listenerClass, listener);
		}

		boolean matches(Class<? extends EventListener> listenerClass, @SuppressWarnings("unused") String aspectName) {
			return this.listenerList.getListenerType() == listenerClass;
		}

		boolean matches(Class<? extends EventListener> listenerClass) {
			return this.matches(listenerClass, null);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.getAspectName());
		}

		abstract String getAspectName();
	}

	/**
	 * Pair a non-<code>null</code> aspect name with its associated listeners.
	 */
	static class SimpleAspectListenerListPair<L extends EventListener>
		extends AspectListenerListPair<L>
	{
		final String aspectName;

		private static final long serialVersionUID = 1L;

		SimpleAspectListenerListPair(Class<L> listenerClass, String aspectName, L listener) {
			super(listenerClass, listener);
			if (aspectName == null) {
				throw new NullPointerException();
			}
			this.aspectName = aspectName;
		}

		@Override
		boolean matches(Class<? extends EventListener> listenerClass, @SuppressWarnings("hiding") String aspectName) {
			return this.aspectName.equals(aspectName)
					&& super.matches(listenerClass, aspectName);
		}

		@Override
		String getAspectName() {
			return this.aspectName;
		}
	}

	/**
	 * Pair a <code>null</code> aspect name with its associated listeners.
	 */
	static class NullAspectListenerListPair<L extends EventListener>
		extends AspectListenerListPair<L>
	{
		private static final long serialVersionUID = 1L;

		NullAspectListenerListPair(Class<L> listenerClass, L listener) {
			super(listenerClass, listener);
		}

		@Override
		boolean matches(Class<? extends EventListener> listenerClass, String aspectName) {
			return (aspectName == null)
					&& super.matches(listenerClass, null);
		}

		@Override
		String getAspectName() {
			return null;
		}
	}
}
