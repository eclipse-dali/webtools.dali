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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
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
 * Support object that can be used by implementors of the Model interface.
 * It provides for state, property, collection, list, and tree change notifications to
 * listeners.
 * 
 * NB1: There is lots of copy-n-paste code in this class. Nearly all of this duplication
 * is an effort to prevent the unnecessary creation of new objects (typically event
 * objects). Since many events are fired when there are no listeners, we postpone
 * the creation of event objects until we know we have interested listeners.
 * Most methods have the "non-duplicated" version of the method body commented
 * out at the top of the current method body.
 * The hope was that this class would prove to be fairly static and the duplicated
 * code would not prove onerous; but that has not proven to be
 * the case, as we have added support for "state" changes, "dirty" notification,
 * and custom "notifiers", with more to come, I'm sure....  ~bjv
 * 
 * NB2: This class will check to see if, during the firing of events, a listener
 * on the original, cloned, list of listeners has been removed from the master
 * list of listeners *before* it is notified. If the listener has been removed
 * "concurrently" it will *not* be notified. (See the code that uses the
 * 'stillListening' local boolean flag.)
 * 
 * NB3: Any listener that is added during the firing of events will *not* be
 * also notified. This is a bit inconsistent with NB2....
 * 
 * NB4: This class is serializable, but it will only write out listeners that
 * are also serializable while silently leaving behind listeners that are not.
 * 
 * TODO fire a state change event with *every* change?
 * TODO use objects (IDs?) instead of strings to identify aspects?
 */
public class ChangeSupport
	implements Serializable
{
	/** The object to be provided as the "source" for any generated events. */
	protected final Model source;

	/** Associate a listener class to a collection of "generic" listeners for that class. */
	private transient GenericListenerList[] genericListenerLists = EMPTY_GENERIC_LISTENER_LIST_ARRAY;
		private static final GenericListenerList[] EMPTY_GENERIC_LISTENER_LIST_ARRAY = new GenericListenerList[0];

	/** Associate aspect names to child change support objects. */
	private AspectChild[] aspectChildren = EMPTY_ASPECT_CHILD_ARRAY;
		private static final AspectChild[] EMPTY_ASPECT_CHILD_ARRAY = new AspectChild[0];

	private static final long serialVersionUID = 1L;


	// ********** constructor **********

	/**
	 * Construct support for the specified source of change events.
	 * The source cannot be null.
	 */
	public ChangeSupport(Model source) {
		super();
		if (source == null) {
			throw new NullPointerException();
		}
		this.source = source;
	}


	// ********** internal behavior **********

	/**
	 * Add a "generic" listener that listens to all events appropriate to that
	 * listener, regardless of the aspect name associated with that event.
	 * The listener cannot be null.
	 */
	protected synchronized <T extends ChangeListener> void addListener(Class<T> listenerClass, T listener) {
		if (listener == null) {
			throw new NullPointerException();  // better sooner than later
		}
		GenericListenerList gll = this.getGenericListenerList(listenerClass);
		if (gll == null) {
			this.addGenericListenerList(listenerClass, listener);
		} else {
			gll.addListener(listener);
		}
	}

	/**
	 * Return the "generic" listener list for the specified listener class.
	 * Return null if the list is not present.
	 */
	// FindBugs: this method does not need to be synchronized because the
	// *contents* of 'genericListenerLists' never changes; the array is simply
	// replaced whenever an entry is added
	protected GenericListenerList getGenericListenerList(Class<? extends ChangeListener> listenerClass) {
		for (GenericListenerList gll : this.genericListenerLists) {
			if (gll.listenerClass == listenerClass) {
				return gll;
			}
		}
		return null;
	}

	/**
	 * Add the "generic" listener list for the specified listener class.
	 * Return the newly-built generic listener list.
	 */
	protected <T extends ChangeListener> GenericListenerList addGenericListenerList(Class<T> listenerClass, T listener) {
		GenericListenerList gll = new GenericListenerList(listenerClass, listener);
		this.genericListenerLists = CollectionTools.add(this.genericListenerLists, gll);
		return gll;
	}

	/**
	 * Adds a listener that listens to all events appropriate to that listener,
	 * and only to those events carrying the aspect name specified.
	 * The aspect name cannot be null and the listener cannot be null.
	 */
	protected synchronized <T extends ChangeListener> void addListener(String aspectName, Class<T> listenerClass, T listener) {
		ChangeSupport child = this.getChild(aspectName);
		if (child == null) {
			child = this.addChild(aspectName);
		}
		child.addListener(listenerClass, listener);
	}

	/**
	 * Return the child change support for the specified aspect name.
	 * Return null if the aspect name is null or the child is not present.
	 */
	protected ChangeSupport getChild(String aspectName) {
		// put in a null check to simplify calling code
		if (aspectName == null) {
			throw new NullPointerException();
		}
		for (AspectChild aspectChild : this.aspectChildren) {
			if (aspectChild.aspectName.equals(aspectName)) {
				return aspectChild.child;
			}
		}
		return null;
	}

	/**
	 * Add the child change support for the specified aspect name.
	 * Return the newly-built child change support.
	 */
	protected ChangeSupport addChild(String aspectName) {
		ChangeSupport child = this.buildChild();
		this.aspectChildren = CollectionTools.add(this.aspectChildren, new AspectChild(aspectName, child));
		return child;
	}

	/**
	 * Build and return a child change support to hold aspect-specific listeners.
	 */
	protected ChangeSupport buildChild() {
		return new Child(this.source);
	}

	/**
	 * Removes a "generic" listener that has been registered for all events
	 * appropriate to that listener.
	 */
	protected synchronized <T extends ChangeListener> void removeListener(Class<T> listenerClass, T listener) {
		GenericListenerList gll = this.getGenericListenerList(listenerClass);
		if (gll == null) {
			this.handleUnregisteredListener(listener);
		} else {
			if ( ! gll.removeListener(listener)) {  // leave the GLL, even if it is empty?
				this.handleUnregisteredListener(listener);
			}
		}
	}

	/**
	 * Removes a listener that has been registered for appropriate
	 * events carrying the specified aspect name.
	 */
	// synchronize this method if we ever decide to remove the "child"
	// change support when it is empty
	protected <T extends ChangeListener> void removeListener(String aspectName, Class<T> listenerClass, T listener) {
		ChangeSupport child = this.getChild(aspectName);
		if (child == null) {
			this.handleUnregisteredListener(listener);
		} else {
			child.removeListener(listenerClass, listener);  // leave the child, even if it is empty?
		}
	}

	protected <T extends ChangeListener> void handleUnregisteredListener(T listener) {
		throw new IllegalArgumentException("listener not registered: " + listener); //$NON-NLS-1$
	}


	// ********** internal queries **********

	/**
	 * Return the "generic" listeners for the specified listener class.
	 * Return null if there are no listeners.
	 */
	protected ChangeListener[] getListeners(Class<? extends ChangeListener> listenerClass) {
		GenericListenerList gll = this.getGenericListenerList(listenerClass);
		return (gll == null) ? null : gll.listeners;
	}

	/**
	 * Return whether there are any "generic" listeners for the specified
	 * listener class.
	 */
	protected <T extends ChangeListener> boolean hasAnyListeners(Class<T> listenerClass) {
		GenericListenerList gll = this.getGenericListenerList(listenerClass);
		return (gll != null) && gll.hasListeners();
	}

	/**
	 * Return whether there are no "generic" listeners for the specified
	 * listener class.
	 */
	protected <T extends ChangeListener> boolean hasNoListeners(Class<T> listenerClass) {
		return ! this.hasAnyListeners(listenerClass);
	}

	/**
	 * Return whether there are any listeners for the specified
	 * listener class and aspect name.
	 */
	protected boolean hasAnyListeners(Class<? extends ChangeListener> listenerClass, String aspectName) {
		ChangeSupport child = this.getChild(aspectName);
		if ((child != null) && child.hasAnyListeners(listenerClass)) {
			return true;
		}
		return this.hasAnyListeners(listenerClass);  // check for a "generic" listener
	}

	/**
	 * Return whether there are no "generic" listeners for the specified
	 * listener class and aspect name.
	 */
	protected <T extends ChangeListener> boolean hasNoListeners(Class<T> listenerClass, String aspectName) {
		return ! this.hasAnyListeners(listenerClass, aspectName);
	}


	// ********** behavior **********

	/**
	 * The specified aspect of the source has changed;
	 * override this method to perform things like setting a
	 * dirty flag or validating the source's state.
	 * The aspect ID will be null if a "state change" occurred.
	 */
	protected void aspectChanged(@SuppressWarnings("unused") String aspectName) {
		// the default is to do nothing
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

	private StateChangeListener[] getStateChangeListeners() {
		return (StateChangeListener[]) this.getListeners(STATE_CHANGE_LISTENER_CLASS);
	}

	private boolean hasStateChangeListener(StateChangeListener listener) {
		return CollectionTools.contains(this.getStateChangeListeners(), listener);
	}

	/**
	 * Fire the specified state change event to any registered listeners.
	 */
	public void fireStateChanged(StateChangeEvent event) {
		StateChangeListener[] listeners = this.getStateChangeListeners();
		if (listeners != null) {
			for (StateChangeListener listener : listeners) {
				if (this.hasStateChangeListener(listener)) {  // verify listener is still listening
					listener.stateChanged(event);
				}
			}
		}

		this.aspectChanged(null);
	}

	/**
	 * Report a generic state change event to any registered state change
	 * listeners.
	 */
	public void fireStateChanged() {
//		this.fireStateChange(new StateChangeEvent(this.source));
		StateChangeListener[] listeners = this.getStateChangeListeners();
		if (listeners != null) {
			StateChangeEvent event = null;
			for (StateChangeListener listener : listeners) {
				if (this.hasStateChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new StateChangeEvent(this.source);
					}
					listener.stateChanged(event);
				}
			}
		}

		this.aspectChanged(null);
	}


	// ********** property change support **********

	protected static final Class<PropertyChangeListener> PROPERTY_CHANGE_LISTENER_CLASS = PropertyChangeListener.class;

	/**
	 * Add a property change listener that is registered for all properties.
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		this.addListener(PROPERTY_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Add a property change listener for the specified property. The listener
	 * will be notified only for changes to the specified property.
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.addListener(propertyName, PROPERTY_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Remove a property change listener that was registered for all properties.
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.removeListener(PROPERTY_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Remove a property change listener that was registered for a specific property.
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		this.removeListener(propertyName, PROPERTY_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Return whether there are any property change listeners that will
	 * be notified when the specified property has changed.
	 */
	public boolean hasAnyPropertyChangeListeners(String propertyName) {
		return this.hasAnyListeners(PROPERTY_CHANGE_LISTENER_CLASS, propertyName);
	}

	/**
	 * Return whether there are any property change listeners that will
	 * be notified when any property has changed.
	 */
	public boolean hasAnyPropertyChangeListeners() {
		return this.hasAnyListeners(PROPERTY_CHANGE_LISTENER_CLASS);
	}

	private PropertyChangeListener[] getPropertyChangeListeners() {
		return (PropertyChangeListener[]) this.getListeners(PROPERTY_CHANGE_LISTENER_CLASS);
	}

	private boolean hasPropertyChangeListener(PropertyChangeListener listener) {
		return CollectionTools.contains(this.getPropertyChangeListeners(), listener);
	}

	/**
	 * Fire the specified property change event to any registered listeners.
	 * No event is fired if the given event's old and new values are the same;
	 * this includes when both values are null. Use a state change event
	 * for general purpose notification of changes.
	 */
	public void firePropertyChanged(PropertyChangeEvent event) {
		if (this.valuesAreEqual(event.getOldValue(), event.getNewValue())) {
			return; 
		}

		PropertyChangeListener[] listeners = this.getPropertyChangeListeners();
		if (listeners != null) {
			for (PropertyChangeListener listener : listeners) {
				if (this.hasPropertyChangeListener(listener)) {  // verify listener is still listening
					listener.propertyChanged(event);
				}
			}
		}

		String propertyName = event.getPropertyName();
		ChangeSupport child = this.getChild(propertyName);
		if (child != null) {
			child.firePropertyChanged(event);
		}

		this.aspectChanged(propertyName);
	}

	/**
	 * Report a bound property update to any registered property change listeners.
	 * No event is fired if the given old and new values are the same;
	 * this includes when both values are null. Use a state change event
	 * for general purpose notification of changes.
	 */
	public void firePropertyChanged(String propertyName, Object oldValue, Object newValue) {
//		this.firePropertyChanged(new PropertyChangeEvent(this.source, propertyName, oldValue, newValue));
		if (this.valuesAreEqual(oldValue, newValue)) {
			return;
		}

		PropertyChangeEvent event = null;
		PropertyChangeListener[] listeners = this.getPropertyChangeListeners();
		if (listeners != null) {
			for (PropertyChangeListener listener : listeners) {
				if (this.hasPropertyChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new PropertyChangeEvent(this.source, propertyName, oldValue, newValue);
					}
					listener.propertyChanged(event);
				}
			}
		}

		ChangeSupport child = this.getChild(propertyName);
		if (child != null) {
			if (event == null) {
				child.firePropertyChanged(propertyName, oldValue, newValue);
			} else {
				child.firePropertyChanged(event);
			}
		}

		this.aspectChanged(propertyName);
	}

	/**
	 * Report an int bound property update to any registered listeners.
	 * No event is fired if old and new are equal.
	 * <p>
	 * This is merely a convenience wrapper around the more general
	 * firePropertyChange method that takes Object values.
	 */
	public void firePropertyChanged(String propertyName, int oldValue, int newValue) {
//		this.firePropertyChanged(propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
		if (oldValue == newValue) {
			return;
		}

		PropertyChangeEvent event = null;
		PropertyChangeListener[] listeners = this.getPropertyChangeListeners();
		if (listeners != null) {
			for (PropertyChangeListener listener : listeners) {
				if (this.hasPropertyChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new PropertyChangeEvent(this.source, propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
					}
					listener.propertyChanged(event);
				}
			}
		}

		ChangeSupport child = this.getChild(propertyName);
		if (child != null) {
			if (event == null) {
				child.firePropertyChanged(propertyName, oldValue, newValue);
			} else {
				child.firePropertyChanged(event);
			}
		}

		this.aspectChanged(propertyName);
	}

	/**
	 * Report a boolean bound property update to any registered listeners.
	 * No event is fired if old and new are equal.
	 * <p>
	 * This is merely a convenience wrapper around the more general
	 * firePropertyChange method that takes Object values.
	 */
	public void firePropertyChanged(String propertyName, boolean oldValue, boolean newValue) {
//		this.firePropertyChanged(propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
		if (oldValue == newValue) {
			return;
		}

		PropertyChangeEvent event = null;
		PropertyChangeListener[] listeners = this.getPropertyChangeListeners();
		if (listeners != null) {
			for (PropertyChangeListener listener : listeners) {
				if (this.hasPropertyChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new PropertyChangeEvent(this.source, propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
					}
					listener.propertyChanged(event);
				}
			}
		}

		ChangeSupport child = this.getChild(propertyName);
		if (child != null) {
			if (event == null) {
				child.firePropertyChanged(propertyName, oldValue, newValue);
			} else {
				child.firePropertyChanged(event);
			}
		}

		this.aspectChanged(propertyName);
	}


	// ********** collection change support **********

	protected static final Class<CollectionChangeListener> COLLECTION_CHANGE_LISTENER_CLASS = CollectionChangeListener.class;

	/**
	 * Add a collection change listener that is registered for all collections.
	 */
	public void addCollectionChangeListener(CollectionChangeListener listener) {
		this.addListener(COLLECTION_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Add a collection change listener for the specified collection. The listener
	 * will be notified only for changes to the specified collection.
	 */
	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.addListener(collectionName, COLLECTION_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Remove a collection change listener that was registered for all collections.
	 */
	public void removeCollectionChangeListener(CollectionChangeListener listener) {
		this.removeListener(COLLECTION_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Remove a collection change listener that was registered for a specific collection.
	 */
	public void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		this.removeListener(collectionName, COLLECTION_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Return whether there are any collection change listeners that will
	 * be notified when the specified collection has changed.
	 */
	public boolean hasAnyCollectionChangeListeners(String collectionName) {
		return this.hasAnyListeners(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
	}

	/**
	 * Return whether there are any collection change listeners that will
	 * be notified when any collection has changed.
	 */
	public boolean hasAnyCollectionChangeListeners() {
		return this.hasAnyListeners(COLLECTION_CHANGE_LISTENER_CLASS);
	}

	private CollectionChangeListener[] getCollectionChangeListeners() {
		return (CollectionChangeListener[]) this.getListeners(COLLECTION_CHANGE_LISTENER_CLASS);
	}

	private boolean hasCollectionChangeListener(CollectionChangeListener listener) {
		return CollectionTools.contains(this.getCollectionChangeListeners(), listener);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemsAdded(CollectionAddEvent event) {
		if (event.getItemsSize() == 0) {
			return;
		}

		CollectionChangeListener[] listeners = this.getCollectionChangeListeners();
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(listener)) {  // verify listener is still listening
					listener.itemsAdded(event);
				}
			}
		}

		String collectionName = event.getCollectionName();
		ChangeSupport child = this.getChild(collectionName);
		if (child != null) {
			child.fireItemsAdded(event);
		}

		this.aspectChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemsAdded(String collectionName, Collection<?> addedItems) {
//		this.fireItemsAdded(new CollectionAddEvent(this.source, collectionName, addedItems));
		if (addedItems.isEmpty()) {
			return;
		}

		CollectionAddEvent event = null;
		CollectionChangeListener[] listeners = this.getCollectionChangeListeners();
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionAddEvent(this.source, collectionName, addedItems);
					}
					listener.itemsAdded(event);
				}
			}
		}

		ChangeSupport child = this.getChild(collectionName);
		if (child != null) {
			if (event == null) {
				child.fireItemsAdded(collectionName, addedItems);
			} else {
				child.fireItemsAdded(event);
			}
		}

		this.aspectChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemAdded(String collectionName, Object addedItem) {
//		this.fireItemsAdded(collectionName, Collections.singleton(addedItem));

		CollectionAddEvent event = null;
		CollectionChangeListener[] listeners = this.getCollectionChangeListeners();
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionAddEvent(this.source, collectionName, Collections.singleton(addedItem));
					}
					listener.itemsAdded(event);
				}
			}
		}

		ChangeSupport child = this.getChild(collectionName);
		if (child != null) {
			if (event == null) {
				child.fireItemAdded(collectionName, addedItem);
			} else {
				child.fireItemsAdded(event);
			}
		}

		this.aspectChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemsRemoved(CollectionRemoveEvent event) {
		if (event.getItemsSize() == 0) {
			return;
		}

		CollectionChangeListener[] listeners = this.getCollectionChangeListeners();
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(listener)) {  // verify listener is still listening
					listener.itemsRemoved(event);
				}
			}
		}

		String collectionName = event.getCollectionName();
		ChangeSupport child = this.getChild(collectionName);
		if (child != null) {
			child.fireItemsRemoved(event);
		}

		this.aspectChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemsRemoved(String collectionName, Collection<?> removedItems) {
//		this.fireItemsRemoved(new CollectionRemoveEvent(this.source, collectionName, removedItems));
		if (removedItems.isEmpty()) {
			return;
		}

		CollectionRemoveEvent event = null;
		CollectionChangeListener[] listeners = this.getCollectionChangeListeners();
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionRemoveEvent(this.source, collectionName, removedItems);
					}
					listener.itemsRemoved(event);
				}
			}
		}

		ChangeSupport child = this.getChild(collectionName);
		if (child != null) {
			if (event == null) {
				child.fireItemsRemoved(collectionName, removedItems);
			} else {
				child.fireItemsRemoved(event);
			}
		}

		this.aspectChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemRemoved(String collectionName, Object removedItem) {
//		this.fireItemsRemoved(collectionName, Collections.singleton(removedItem));

		CollectionRemoveEvent event = null;
		CollectionChangeListener[] listeners = this.getCollectionChangeListeners();
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionRemoveEvent(this.source, collectionName, Collections.singleton(removedItem));
					}
					listener.itemsRemoved(event);
				}
			}
		}

		ChangeSupport child = this.getChild(collectionName);
		if (child != null) {
			if (event == null) {
				child.fireItemRemoved(collectionName, removedItem);
			} else {
				child.fireItemsRemoved(event);
			}
		}

		this.aspectChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionCleared(CollectionClearEvent event) {
		CollectionChangeListener[] listeners = this.getCollectionChangeListeners();
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(listener)) {  // verify listener is still listening
					listener.collectionCleared(event);
				}
			}
		}

		String collectionName = event.getCollectionName();
		ChangeSupport child = this.getChild(collectionName);
		if (child != null) {
			child.fireCollectionCleared(event);
		}

		this.aspectChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionCleared(String collectionName) {
//		this.fireCollectionCleared(new CollectionClearEvent(this.source, collectionName));

		CollectionClearEvent event = null;
		CollectionChangeListener[] listeners = this.getCollectionChangeListeners();
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionClearEvent(this.source, collectionName);
					}
					listener.collectionCleared(event);
				}
			}
		}

		ChangeSupport child = this.getChild(collectionName);
		if (child != null) {
			if (event == null) {
				child.fireCollectionCleared(collectionName);
			} else {
				child.fireCollectionCleared(event);
			}
		}

		this.aspectChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionChanged(CollectionChangeEvent event) {
		CollectionChangeListener[] listeners = this.getCollectionChangeListeners();
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(listener)) {  // verify listener is still listening
					listener.collectionChanged(event);
				}
			}
		}

		String collectionName = event.getCollectionName();
		ChangeSupport child = this.getChild(collectionName);
		if (child != null) {
			child.fireCollectionChanged(event);
		}

		this.aspectChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionChanged(String collectionName, Collection<?> collection) {
//		this.fireCollectionChanged(new CollectionChangeEvent(this.source, collectionName, collection));

		CollectionChangeEvent event = null;
		CollectionChangeListener[] listeners = this.getCollectionChangeListeners();
		if (listeners != null) {
			for (CollectionChangeListener listener : listeners) {
				if (this.hasCollectionChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionChangeEvent(this.source, collectionName, collection);
					}
					listener.collectionChanged(event);
				}
			}
		}

		ChangeSupport child = this.getChild(collectionName);
		if (child != null) {
			if (event == null) {
				child.fireCollectionChanged(collectionName, collection);
			} else {
				child.fireCollectionChanged(event);
			}
		}

		this.aspectChanged(collectionName);
	}

	/**
	 * Add the specified item to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#add(Object)
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
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public <E> boolean addItemsToCollection(E[] items, Collection<E> collection, String collectionName) {
		return (items.length != 0)
				&& this.addItemsToCollection_(new ArrayIterator<E>(items), collection, collectionName);
	}

	/**
	 * Add the specified items to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether collection changed.
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public <E> boolean addItemsToCollection(Collection<? extends E> items, Collection<E> collection, String collectionName) {
		return ( ! items.isEmpty())
				&& this.addItemsToCollection_(items.iterator(), collection, collectionName);
	}

	/**
	 * Add the specified items to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether collection changed.
	 * @see java.util.Collection#addAll(java.util.Collection)
	 */
	public <E> boolean addItemsToCollection(Iterable<? extends E> items, Collection<E> collection, String collectionName) {
		return this.addItemsToCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Add the specified items to the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether collection changed.
	 * @see java.util.Collection#addAll(java.util.Collection)
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
			this.fireItemsAdded(collectionName, addedItems);
			return true;
		}
		return false;
	}

	/**
	 * Remove the specified item from the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#remove(Object)
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
	 * @see java.util.Collection#removeAll(java.util.Collection)
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
	 * @see java.util.Collection#removeAll(java.util.Collection)
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
	 * @see java.util.Collection#removeAll(java.util.Collection)
	 */
	public boolean removeItemsFromCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.removeItemsFromCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Remove the specified items from the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#removeAll(java.util.Collection)
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
			this.fireItemsRemoved(collectionName, removedItems);
		}
		return changed;
	}

	/**
	 * Retain the specified items in the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#retainAll(java.util.Collection)
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
	 * @see java.util.Collection#retainAll(java.util.Collection)
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
	 * @see java.util.Collection#retainAll(java.util.Collection)
	 */
	public boolean retainItemsInCollection(Iterable<?> items, Collection<?> collection, String collectionName) {
		return this.retainItemsInCollection(items.iterator(), collection, collectionName);
	}

	/**
	 * Retain the specified items in the specified bound collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#retainAll(java.util.Collection)
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
			this.fireItemsRemoved(collectionName, removedItems);
		}
		return changed;
	}

	/**
	 * Clear the entire collection
	 * and fire the appropriate event if necessary.
	 * Return whether the collection changed.
	 * @see java.util.Collection#clear()
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
	 * Add a list change listener that is registered for all lists.
	 */
	public void addListChangeListener(ListChangeListener listener) {
		this.addListener(LIST_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Add a list change listener for the specified list. The listener
	 * will be notified only for changes to the specified list.
	 */
	public void addListChangeListener(String listName, ListChangeListener listener) {
		this.addListener(listName, LIST_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Remove a list change listener that was registered for all lists.
	 */
	public void removeListChangeListener(ListChangeListener listener) {
		this.removeListener(LIST_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Remove a list change listener that was registered for a specific list.
	 */
	public void removeListChangeListener(String listName, ListChangeListener listener) {
		this.removeListener(listName, LIST_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Return whether there are any list change listeners that will
	 * be notified when the specified list has changed.
	 */
	public boolean hasAnyListChangeListeners(String listName) {
		return this.hasAnyListeners(LIST_CHANGE_LISTENER_CLASS, listName);
	}

	/**
	 * Return whether there are any list change listeners that will
	 * be notified when any list has changed.
	 */
	public boolean hasAnyListChangeListeners() {
		return this.hasAnyListeners(LIST_CHANGE_LISTENER_CLASS);
	}

	private ListChangeListener[] getListChangeListeners() {
		return (ListChangeListener[]) this.getListeners(LIST_CHANGE_LISTENER_CLASS);
	}

	private boolean hasListChangeListener(ListChangeListener listener) {
		return CollectionTools.contains(this.getListChangeListeners(), listener);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsAdded(ListAddEvent event) {
		if (event.getItemsSize() == 0) {
			return;
		}

		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					listener.itemsAdded(event);
				}
			}
		}

		String listName = event.getListName();
		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			child.fireItemsAdded(event);
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsAdded(String listName, int index, List<?> addedItems) {
//		this.fireItemsAdded(new ListAddEvent(this.source, listName, index, addedItems));
		if (addedItems.isEmpty()) {
			return;
		}

		ListAddEvent event = null;
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListAddEvent(this.source, listName, index, addedItems);
					}
					listener.itemsAdded(event);
				}
			}
		}

		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			if (event == null) {
				child.fireItemsAdded(listName, index, addedItems);
			} else {
				child.fireItemsAdded(event);
			}
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemAdded(String listName, int index, Object addedItem) {
//		this.fireItemsAdded(listName, index, Collections.singletonList(addedItem));

		ListAddEvent event = null;
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListAddEvent(this.source, listName, index, Collections.singletonList(addedItem));
					}
					listener.itemsAdded(event);
				}
			}
		}

		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			if (event == null) {
				child.fireItemAdded(listName, index, addedItem);
			} else {
				child.fireItemsAdded(event);
			}
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsRemoved(ListRemoveEvent event) {
		if (event.getItemsSize() == 0) {
			return;
		}

		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					listener.itemsRemoved(event);
				}
			}
		}

		String listName = event.getListName();
		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			child.fireItemsRemoved(event);
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsRemoved(String listName, int index, List<?> removedItems) {
//		this.fireItemsRemoved(new ListRemoveEvent(this.source, listName, index, removedItems));
		if (removedItems.isEmpty()) {
			return;
		}

		ListRemoveEvent event = null;
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListRemoveEvent(this.source, listName, index, removedItems);
					}
					listener.itemsRemoved(event);
				}
			}
		}

		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			if (event == null) {
				child.fireItemsRemoved(listName, index, removedItems);
			} else {
				child.fireItemsRemoved(event);
			}
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemRemoved(String listName, int index, Object removedItem) {
//		this.fireItemsRemoved(listName, index, Collections.singletonList(removedItem));

		ListRemoveEvent event = null;
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListRemoveEvent(this.source, listName, index, Collections.singletonList(removedItem));
					}
					listener.itemsRemoved(event);
				}
			}
		}

		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			if (event == null) {
				child.fireItemRemoved(listName, index, removedItem);
			} else {
				child.fireItemsRemoved(event);
			}
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsReplaced(ListReplaceEvent event) {
		if (event.getItemsSize() == 0) {
			return;
		}
		// TODO check that the items are actually different... ?
//		if (this.elementsAreEqual(event.items(), event.replacedItems())) {
//			return;
//		}

		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					listener.itemsReplaced(event);
				}
			}
		}

		String listName = event.getListName();
		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			child.fireItemsReplaced(event);
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsReplaced(String listName, int index, List<?> newItems, List<?> replacedItems) {
//		this.fireItemsReplaced(new ListReplaceEvent(this.source, listName, index, newItems, replacedItems));
		if (newItems.isEmpty()) {
			return;
		}
		// TODO check that the items are actually different... ?
//		if (newItems.equals(replacedItems)) {
//			return;
//		}

		ListReplaceEvent event = null;
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListReplaceEvent(this.source, listName, index, newItems, replacedItems);
					}
					listener.itemsReplaced(event);
				}
			}
		}

		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			if (event == null) {
				child.fireItemsReplaced(listName, index, newItems, replacedItems);
			} else {
				child.fireItemsReplaced(event);
			}
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
//		this.fireItemsReplaced(listName, index, Collections.singletonList(newItem), Collections.singletonList(replacedItem));
		// TODO check that the item is actually different... ?
//		if (this.valuesAreEqual(newItem, replacedItem)) {
//			return;
//		}

		ListReplaceEvent event = null;
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListReplaceEvent(this.source, listName, index, Collections.singletonList(newItem), Collections.singletonList(replacedItem));
					}
					listener.itemsReplaced(event);
				}
			}
		}

		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			if (event == null) {
				child.fireItemReplaced(listName, index, newItem, replacedItem);
			} else {
				child.fireItemsReplaced(event);
			}
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsMoved(ListMoveEvent event) {
		if (event.getTargetIndex() == event.getSourceIndex()) {
			return;
		}
		// it's unlikely but possible the list is unchanged by the move... (e.g. any moves within ["foo", "foo", "foo"]...)

		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					listener.itemsMoved(event);
				}
			}
		}

		String listName = event.getListName();
		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			child.fireItemsMoved(event);
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
//		this.fireItemsMoved(new ListMoveEvent(this.source, listName, targetIndex, sourceIndex, length));
		if (targetIndex == sourceIndex) {
			return;
		}
		// it's unlikely but possible the list is unchanged by the move... (e.g. any moves within ["foo", "foo", "foo"]...)

		ListMoveEvent event = null;
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListMoveEvent(this.source, listName, targetIndex, sourceIndex, length);
					}
					listener.itemsMoved(event);
				}
			}
		}

		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			if (event == null) {
				child.fireItemsMoved(listName, targetIndex, sourceIndex, length);
			} else {
				child.fireItemsMoved(event);
			}
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemMoved(String listName, int targetIndex, int sourceIndex) {
		this.fireItemsMoved(listName, targetIndex, sourceIndex, 1);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListCleared(ListClearEvent event) {
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					listener.listCleared(event);
				}
			}
		}

		String listName = event.getListName();
		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			child.fireListCleared(event);
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListCleared(String listName) {
//		this.fireListCleared(new ListClearEvent(this.source, listName));

		ListClearEvent event = null;
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListClearEvent(this.source, listName);
					}
					listener.listCleared(event);
				}
			}
		}

		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			if (event == null) {
				child.fireListCleared(listName);
			} else {
				child.fireListCleared(event);
			}
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListChanged(ListChangeEvent event) {
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					listener.listChanged(event);
				}
			}
		}

		String listName = event.getListName();
		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			child.fireListChanged(event);
		}

		this.aspectChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListChanged(String listName, List<?> list) {
//		this.fireListChanged(new ListChangeEvent(this.source, listName));

		ListChangeEvent event = null;
		ListChangeListener[] listeners = this.getListChangeListeners();
		if (listeners != null) {
			for (ListChangeListener listener : listeners) {
				if (this.hasListChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListChangeEvent(this.source, listName, list);
					}
					listener.listChanged(event);
				}
			}
		}

		ChangeSupport child = this.getChild(listName);
		if (child != null) {
			if (event == null) {
				child.fireListChanged(listName, list);
			} else {
				child.fireListChanged(event);
			}
		}

		this.aspectChanged(listName);
	}

	/**
	 * Add the specified item to the specified bound list at the specified index
	 * and fire the appropriate event.
	 * @see java.util.List#add(int, Object)
	 */
	public <E> void addItemToList(int index, E item, List<E> list, String listName) {
		list.add(index, item);
		this.fireItemAdded(listName, index, item);
	}

	/**
	 * Add the specified item to the end of the specified bound list
	 * and fire the appropriate event.
	 * Return whether the list changed (i.e. 'true').
	 * @see java.util.List#add(Object)
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
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public <E> boolean addItemsToList(int index, E[] items, List<E> list, String listName) {
		return (items.length != 0)
				&& this.addItemsToList_(index, Arrays.asList(items), list, listName);
	}

	/**
	 * Add the specified items to the specified bound list at the specified index
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see java.util.List#addAll(int, java.util.Collection)
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
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public <E> boolean addItemsToList(int index, Iterable<? extends E> items, List<E> list, String listName) {
		return this.addItemsToList(index, items.iterator(), list, listName);
	}

	/**
	 * Add the specified items to the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see java.util.List#addAll(int, java.util.Collection)
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
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public <E> boolean addItemsToList(E[] items, List<E> list, String listName) {
		return (items.length != 0)
				&& this.addItemsToList_(Arrays.asList(items), list, listName);
	}

	/**
	 * Add the specified items to the end of the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see java.util.List#addAll(int, java.util.Collection)
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
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public <E> boolean addItemsToList(Iterable<? extends E> items, List<E> list, String listName) {
		return this.addItemsToList(items.iterator(), list, listName);
	}

	/**
	 * Add the specified items to the end of to the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see java.util.List#addAll(java.util.Collection)
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
	 * @see java.util.List#remove(int)
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
	 * @see java.util.List#remove(Object)
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
	 * Remove the specified items from the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the removed items.
	 * @see java.util.List#remove(int)
	 */
	public <E> List<E> removeItemsFromList(int index, int length, List<E> list, String listName) {
		if (length == 0) {
			return Collections.emptyList();
		}
		return this.removeItemsFromList_(index, length, list, listName);
	}

	/**
	 * no empty check
	 */
	protected <E> List<E> removeItemsFromList_(int index, int length, List<E> list, String listName) {
		List<E> subList = list.subList(index, index + length);
		List<E> removedItems = new ArrayList<E>(subList);
		subList.clear();
		this.fireItemsRemoved(listName, index, removedItems);
		return removedItems;
	}

	/**
	 * Remove the specified items from the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see java.util.List#removeAll(java.util.Collection)
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
	 * @see java.util.List#removeAll(java.util.Collection)
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
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeItemsFromList(Iterable<?> items, List<?> list, String listName) {
		return this.removeItemsFromList(items.iterator(), list, listName);
	}

	/**
	 * Remove the specified items from the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see java.util.List#removeAll(java.util.Collection)
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
	 * @see java.util.List#retainAll(java.util.Collection)
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
	 * @see java.util.List#retainAll(java.util.Collection)
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
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainItemsInList(Iterable<?> items, List<?> list, String listName) {
		return this.retainItemsInList(items.iterator(), list, listName);
	}

	/**
	 * Retain the specified items in the specified bound list
	 * and fire the appropriate event(s) if necessary.
	 * Return whether the list changed.
	 * @see java.util.List#retainAll(java.util.Collection)
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
	 * @see java.util.List#set(int, Object)
	 */
	public <E> E setItemInList(int index, E item, List<E> list, String listName) {
		E replacedItem = list.set(index, item);
		if (this.valuesAreDifferent(item, replacedItem)) {
			this.fireItemReplaced(listName, index, item, replacedItem);
		}
		return replacedItem;
	}

	/**
	 * Replace the first occurrence of the specified item
	 * in the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the index of the replaced item.
	 * Return -1 if the item was not found in the list.
	 * @see java.util.List#set(int, Object)
	 */
	public <E> int replaceItemInList(E oldItem, E newItem, List<E> list, String listName) {
		if (list.isEmpty()) {
			return -1;
		}

		int index = list.indexOf(oldItem);
		if ((index != -1) && this.valuesAreDifferent(oldItem, newItem)) {
			list.set(index, newItem);
			this.fireItemReplaced(listName, index, newItem, oldItem);
		}
		return index;
	}

	/**
	 * Set the specified items in the specified bound list
	 * and fire the appropriate event if necessary.
	 * Return the replaced items.
	 * @see java.util.List#set(int, Object)
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
	 * @see java.util.List#set(int, Object)
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
		List<E> replacedItems = new ArrayList<E>(subList);
		for (int i = 0; i < items.size(); i++) {
			E newItem = items.get(i);
			E oldItem = subList.set(i, newItem);
			if (this.valuesAreDifferent(oldItem, newItem)) {
				this.fireItemReplaced(listName, index + i, newItem, oldItem);
			}
		}
		return replacedItems;
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
	 * Move an item in the specified list from the specified source index to the
	 * specified target index.
	 * Return whether the list changed.
	 */
	public <E> boolean moveItemInList(int targetIndex, int sourceIndex, List<E> list, String listName) {
		if (targetIndex == sourceIndex) {
			return false;
		}
		// it's unlikely but possible the list is unchanged by the move... (e.g. any moves within ["foo", "foo", "foo"]...)
		CollectionTools.move(list, targetIndex, sourceIndex);
		this.fireItemMoved(listName, targetIndex, sourceIndex);
		return true;
	}

	/**
	 * Clear the entire list
	 * and fire the appropriate event if necessary.
	 * Return whether the list changed.
	 * @see java.util.List#clear()
	 */
	public boolean clearList(List<?> list, String listName) {
		if (list.isEmpty()) {
			return false;
		}
		return this.clearList_(list, listName);
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
	public <E> boolean synchronizeList(Iterator<E> newList, List<E> list, String listName) {
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
	protected <E> boolean synchronizeList_(List<E> newList, List<E> oldList, String listName) {
		int newSize = newList.size();
		int oldSize = oldList.size();

		boolean changed = false;
		int min = Math.min(newSize, oldSize);
		for (int i = 0; i < min; i++) {
			E newItem = newList.get(i);
			E oldItem = oldList.set(i, newItem);
			if (this.valuesAreDifferent(oldItem, newItem)) {
				changed = true;
				this.fireItemReplaced(listName, i, newItem, oldItem);
			}
		}

		if (newSize == oldSize) {
			return changed;
		}

		if (newSize < oldSize) {
			this.removeItemsFromList_(newSize, oldSize - newSize, oldList, listName);
			return true;
		}

		// newSize > oldSize
		this.addItemsToList_(newList.subList(oldSize, newSize), oldList, listName);
		return true;
	}


	// ********** tree change support **********

	protected static final Class<TreeChangeListener> TREE_CHANGE_LISTENER_CLASS = TreeChangeListener.class;

	/**
	 * Add a tree change listener that is registered for all trees.
	 */
	public void addTreeChangeListener(TreeChangeListener listener) {
		this.addListener(TREE_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Add a tree change listener for the specified tree. The listener
	 * will be notified only for changes to the specified tree.
	 */
	public void addTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.addListener(treeName, TREE_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Remove a tree change listener that was registered for all tree.
	 */
	public void removeTreeChangeListener(TreeChangeListener listener) {
		this.removeListener(TREE_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Remove a tree change listener that was registered for a specific tree.
	 */
	public void removeTreeChangeListener(String treeName, TreeChangeListener listener) {
		this.removeListener(treeName, TREE_CHANGE_LISTENER_CLASS, listener);
	}

	/**
	 * Return whether there are any tree change listeners that will
	 * be notified when the specified tree has changed.
	 */
	public boolean hasAnyTreeChangeListeners(String treeName) {
		return this.hasAnyListeners(TREE_CHANGE_LISTENER_CLASS, treeName);
	}

	/**
	 * Return whether there are any tree change listeners that will
	 * be notified when any tree has changed.
	 */
	public boolean hasAnyTreeChangeListeners() {
		return this.hasAnyListeners(TREE_CHANGE_LISTENER_CLASS);
	}

	private TreeChangeListener[] getTreeChangeListeners() {
		return (TreeChangeListener[]) this.getListeners(TREE_CHANGE_LISTENER_CLASS);
	}

	private boolean hasTreeChangeListener(TreeChangeListener listener) {
		return CollectionTools.contains(this.getTreeChangeListeners(), listener);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeAdded(TreeAddEvent event) {
		TreeChangeListener[] listeners = this.getTreeChangeListeners();
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(listener)) {  // verify listener is still listening
					listener.nodeAdded(event);
				}
			}
		}

		String treeName = event.getTreeName();
		ChangeSupport child = this.getChild(treeName);
		if (child != null) {
			child.fireNodeAdded(event);
		}

		this.aspectChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeAdded(String treeName, List<?> path) {
//		this.fireNodeAdded(new TreeAddEvent(this.source, treeName, path));
		TreeAddEvent event = null;
		TreeChangeListener[] listeners = this.getTreeChangeListeners();
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new TreeAddEvent(this.source, treeName, path);
					}
					listener.nodeAdded(event);
				}
			}
		}

		ChangeSupport child = this.getChild(treeName);
		if (child != null) {
			if (event == null) {
				child.fireNodeAdded(treeName, path);
			} else {
				child.fireNodeAdded(event);
			}
		}

		this.aspectChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeRemoved(TreeRemoveEvent event) {
		TreeChangeListener[] listeners = this.getTreeChangeListeners();
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(listener)) {  // verify listener is still listening
					listener.nodeRemoved(event);
				}
			}
		}

		String treeName = event.getTreeName();
		ChangeSupport child = this.getChild(treeName);
		if (child != null) {
			child.fireNodeRemoved(event);
		}

		this.aspectChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeRemoved(String treeName, List<?> path) {
//		this.fireNodeRemoved(new TreeRemoveEvent(this.source, treeName, path));

		TreeRemoveEvent event = null;
		TreeChangeListener[] listeners = this.getTreeChangeListeners();
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new TreeRemoveEvent(this.source, treeName, path);
					}
					listener.nodeRemoved(event);
				}
			}
		}

		ChangeSupport child = this.getChild(treeName);
		if (child != null) {
			if (event == null) {
				child.fireNodeRemoved(treeName, path);
			} else {
				child.fireNodeRemoved(event);
			}
		}

		this.aspectChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeCleared(TreeClearEvent event) {
		TreeChangeListener[] listeners = this.getTreeChangeListeners();
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(listener)) {  // verify listener is still listening
					listener.treeCleared(event);
				}
			}
		}

		String treeName = event.getTreeName();
		ChangeSupport child = this.getChild(treeName);
		if (child != null) {
			child.fireTreeCleared(event);
		}

		this.aspectChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeCleared(String treeName) {
//		this.fireTreeCleared(new TreeClearEvent(this.source, treeName));

		TreeClearEvent event = null;
		TreeChangeListener[] listeners = this.getTreeChangeListeners();
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new TreeClearEvent(this.source, treeName);
					}
					listener.treeCleared(event);
				}
			}
		}

		ChangeSupport child = this.getChild(treeName);
		if (child != null) {
			if (event == null) {
				child.fireTreeCleared(treeName);
			} else {
				child.fireTreeCleared(event);
			}
		}

		this.aspectChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeChanged(TreeChangeEvent event) {
		TreeChangeListener[] listeners = this.getTreeChangeListeners();
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(listener)) {  // verify listener is still listening
					listener.treeChanged(event);
				}
			}
		}

		String treeName = event.getTreeName();
		ChangeSupport child = this.getChild(treeName);
		if (child != null) {
			child.fireTreeChanged(event);
		}

		this.aspectChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeChanged(String treeName, Collection<?> nodes) {
//		this.fireTreeChanged(new TreeChangeEvent(this.source, treeName, nodes));

		TreeChangeEvent event = null;
		TreeChangeListener[] listeners = this.getTreeChangeListeners();
		if (listeners != null) {
			for (TreeChangeListener listener : listeners) {
				if (this.hasTreeChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new TreeChangeEvent(this.source, treeName, nodes);
					}
					listener.treeChanged(event);
				}
			}
		}

		ChangeSupport child = this.getChild(treeName);
		if (child != null) {
			if (event == null) {
				child.fireTreeChanged(treeName, nodes);
			} else {
				child.fireTreeChanged(event);
			}
		}

		this.aspectChanged(treeName);
	}


	// ********** convenience methods **********

	/**
	 * Return whether the specified values are equal, with the appropriate null checks.
	 * Convenience method for checking whether an attribute value has changed.
	 */
	public boolean valuesAreEqual(Object value1, Object value2) {
		if ((value1 == null) && (value2 == null)) {
			return true;	// both are null
		}
		if ((value1 == null) || (value2 == null)) {
			return false;	// one is null but the other is not
		}
		return value1.equals(value2);
	}

	/**
	 * Return whether the specified values are different, with the appropriate null checks.
	 * Convenience method for checking whether an attribute value has changed.
	 */
	public boolean valuesAreDifferent(Object value1, Object value2) {
		return ! this.valuesAreEqual(value1, value2);
	}

	/**
	 * Return whether the specified iterators return the same elements
	 * in the same order.
	 */
	public boolean elementsAreEqual(Iterator<?> iterator1, Iterator<?> iterator2) {
		while (iterator1.hasNext() && iterator2.hasNext()) {
			if (this.valuesAreDifferent(iterator1.next(), iterator2.next())) {
				return false;
			}
		}
		return ( ! iterator1.hasNext()) && ( ! iterator2.hasNext());
	}

	/**
	 * Return whether the specified iterators do not return the same elements
	 * in the same order.
	 */
	public boolean elementsAreDifferent(Iterator<?> iterator1, Iterator<?> iterator2) {
		return ! this.elementsAreEqual(iterator1, iterator2);
	}


	// ********** standard methods **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.source);
	}


	// ********** serialization **********

	private synchronized void writeObject(ObjectOutputStream s) throws IOException {
		// write out the source, children, and any hidden stuff
		s.defaultWriteObject();

		// only write out Serializable listeners
		int len = this.genericListenerLists.length;
		for (int i = 0; i < len; i++) {
			this.writeObject(s, this.genericListenerLists[i]);
		}
		s.writeObject(null);
    }

	private void writeObject(ObjectOutputStream s, GenericListenerList gll) throws IOException {
		boolean first = true;
		int len = gll.listeners.length;
		for (int i = 0; i < len; i++) {
			ChangeListener listener = gll.listeners[i];
			if (listener instanceof Serializable) {
				if (first) {
					first = false;
					s.writeObject(gll.listenerClass);
				}
				s.writeObject(listener);
			}
		}
		if ( ! first) {
			s.writeObject(null);
		}
	}

	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		// read in the source, children, and any hidden stuff
		s.defaultReadObject();

		// read in generic listener lists
		this.genericListenerLists = EMPTY_GENERIC_LISTENER_LIST_ARRAY;
		Object o;
		while (null != (o = s.readObject())) {
			@SuppressWarnings("unchecked")
			Class<? extends ChangeListener> listenerClass = (Class<? extends ChangeListener>) o;
			GenericListenerList gll = null;
			while (null != (o = s.readObject())) {
				if (gll == null) {
					gll = this.addGenericListenerList_(listenerClass, (ChangeListener) o);
				} else {
					gll.addListener((ChangeListener) o);
				}
			}
		}
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private <T extends ChangeListener> GenericListenerList addGenericListenerList_(Class<T> listenerClass, ChangeListener listener) {
		return this.addGenericListenerList(listenerClass, (T) listener);
	}


	// ********** member classes **********

	/**
	 * Pair a listener class with its "generic" listeners.
	 */
	private static class GenericListenerList {
		final Class<? extends ChangeListener> listenerClass;
		ChangeListener[] listeners;

		<T extends ChangeListener> GenericListenerList(Class<T> listenerClass, T listener) {
			super();
			this.listenerClass = listenerClass;
			this.listeners = (ChangeListener[]) Array.newInstance(listenerClass, 1);
			this.listeners[0] = listener;
		}

		/**
		 * pre-condition: synchronized
		 */
		void addListener(ChangeListener listener) {
			if (CollectionTools.contains(this.listeners, listener)) {
				throw new IllegalArgumentException("duplicate listener: " + listener); //$NON-NLS-1$
			}
			this.listeners = CollectionTools.add(this.listeners, listener);
		}

		/**
		 * pre-condition: synchronized
		 */
		boolean removeListener(ChangeListener listener) {
			int len = this.listeners.length;
			if (len == 0) {
				return false;
			}
			try {
				this.listeners = CollectionTools.remove(this.listeners, listener);
			} catch (ArrayIndexOutOfBoundsException ex) {
				return false;  // listener not in the list
			}
			return (this.listeners.length + 1) == len;
		}

		boolean hasListeners() {
			return this.listeners.length > 0;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, ClassTools.shortNameFor(this.listenerClass));
		}

	}


	/**
	 * Pair an aspect name with the change support holding its associated
	 * listeners.
	 */
	private static class AspectChild implements Serializable {
		final String aspectName;
		final ChangeSupport child;
		private static final long serialVersionUID = 1L;

		AspectChild(String aspectName, ChangeSupport child) {
			super();
			this.aspectName = aspectName;
			this.child = child;
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this, this.aspectName);
		}

	}


	/**
	 * The aspect-specific change support class does not need to
	 * build "grandchildren" change support objects.
	 */
	protected static class Child extends ChangeSupport {
		private static final long serialVersionUID = 1L;

		public Child(Model source) {
			super(source);
		}

		@Override
		protected ChangeSupport buildChild() {
			// there should be no grandchildren
			throw new UnsupportedOperationException();
		}

	}

}
