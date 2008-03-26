/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
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
 * NB: There is lots of copy-n-paste code in this class. Nearly all of this duplication
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
 * also notified.
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
	transient private GenericListenerList[] genericListeners = EMPTY_GENERIC_LISTENERS;
		private static final GenericListenerList[] EMPTY_GENERIC_LISTENERS = new GenericListenerList[0];

	/** Associate aspect names to child change support objects. */
	private AspectChild[] aspectChildren = EMPTY_ASPECT_CHILDREN;
		private static final AspectChild[] EMPTY_ASPECT_CHILDREN = new AspectChild[0];

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
	protected <T extends ChangeListener> void addListener(Class<T> listenerClass, T listener) {
		if (listener == null) {
			throw new NullPointerException();		// better sooner than later
		}
		synchronized (this) {
			GenericListenerList gll = this.genericListenerList(listenerClass);
			if (gll == null) {
				this.addGenericListenerList(listenerClass, listener);
			} else {
				gll.addListener(listener);
			}
		}
	}

	/**
	 * Return the "generic" listener list for the specified listener class.
	 * Return null if the list is not present.
	 */
	protected GenericListenerList genericListenerList(Class<? extends ChangeListener> listenerClass) {
		for (GenericListenerList gll : this.genericListeners) {
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
		this.genericListeners = CollectionTools.add(this.genericListeners, gll);
		return gll;
	}

	/**
	 * Adds a listener that listens to all events appropriate to that listener,
	 * and only to those events carrying the aspect name specified.
	 * The aspect name cannot be null and the listener cannot be null.
	 */
	protected <T extends ChangeListener> void addListener(String aspectName, Class<T> listenerClass, T listener) {
		if ((aspectName == null) || (listener == null)) {
			throw new NullPointerException();		// better sooner than later
		}
		synchronized (this) {
			ChangeSupport child = this.child(aspectName);
			if (child == null) {
				child = this.addChild(aspectName);
			}
			child.addListener(listenerClass, listener);
		}
	}

	/**
	 * Return the child change support for the specified aspect name.
	 * Return null if the aspect name is null or the child is not present.
	 */
	protected ChangeSupport child(String aspectName) {
		// put in a null check to simplify calling code
		if (aspectName == null) {
			return null;
		}
		for (AspectChild aspectChild : this.aspectChildren) {
			if (aspectChild.aspectName == aspectName) {
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
		ChangeSupport child = this.buildChildChangeSupport();
		this.aspectChildren = CollectionTools.add(this.aspectChildren, new AspectChild(aspectName, child));
		return child;
	}

	/**
	 * Build and return a child change support to hold aspect-specific listeners.
	 */
	protected ChangeSupport buildChildChangeSupport() {
		return new ChangeSupport(this.source);
	}

	/**
	 * Removes a "generic" listener that has been registered for all events
	 * appropriate to that listener.
	 */
	protected <T extends ChangeListener> void removeListener(Class<T> listenerClass, T listener) {
		synchronized (this) {
			GenericListenerList gll = this.genericListenerList(listenerClass);
			if (gll == null) {
				throw new IllegalArgumentException("listener not registered");
			}
			if ( ! gll.removeListener(listener)) {  // leave the GLL, even if it is empty?
				throw new IllegalArgumentException("listener not registered");
			}
		}
	}

	/**
	 * Removes a listener that has been registered for appropriate
	 * events carrying the specified aspect name.
	 */
	protected <T extends ChangeListener> void removeListener(String aspectName, Class<T> listenerClass, T listener) {
		synchronized (this) {
			ChangeSupport child = this.child(aspectName);
			if (child == null) {
				throw new IllegalArgumentException("listener not registered");
			}
			child.removeListener(listenerClass, listener);  // leave the child, even if it is empty?
		}
	}


	// ********** internal queries **********

	/**
	 * Return the "generic" listeners for the specified listener class.
	 * Return null if there are no listeners.
	 */
	protected ChangeListener[] listeners(Class<? extends ChangeListener> listenerClass) {
		GenericListenerList gll = this.genericListenerList(listenerClass);
		return (gll == null) ? null : gll.listeners;
	}

	/**
	 * Return whether there are any "generic" listeners for the specified
	 * listener class.
	 */
	protected synchronized <T extends ChangeListener> boolean hasAnyListeners(Class<T> listenerClass) {
		GenericListenerList gll = this.genericListenerList(listenerClass);
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
	protected synchronized boolean hasAnyListeners(Class<? extends ChangeListener> listenerClass, String aspectName) {
		if (this.hasAnyListeners(listenerClass)) {
			return true;		// there's a "generic" listener
		}
		ChangeSupport child = this.child(aspectName);
		return (child != null) &&
			child.hasAnyListeners(listenerClass);
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
	protected void sourceChanged(String aspectName) {
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

	private StateChangeListener[] stateChangeListeners() {
		return (StateChangeListener[]) this.listeners(STATE_CHANGE_LISTENER_CLASS);
	}

	/**
	 * Fire the specified state change event to any registered listeners.
	 */
	public void fireStateChanged(StateChangeEvent event) {

		StateChangeListener[] targets = null;

		synchronized (this) {
			StateChangeListener[] stateChangeListeners = this.stateChangeListeners();
			if (stateChangeListeners != null) {
				targets = stateChangeListeners.clone();
			}
		}

		if (targets != null) {
			for (StateChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.stateChangeListeners(), target);
				}
				if (stillListening) {
					target.stateChanged(event);
				}
			}
		}

		this.sourceChanged(null);
	}

	/**
	 * Report a generic state change event to any registered state change
	 * listeners.
	 */
	public void fireStateChanged() {
//		this.fireStateChange(new StateChangeEvent(this.source));

		StateChangeListener[] targets = null;

		synchronized (this) {
			StateChangeListener[] stateChangeListeners = this.stateChangeListeners();
			if (stateChangeListeners != null) {
				targets = stateChangeListeners.clone();
			}
		}

		if (targets != null) {
			StateChangeEvent event = null;
			for (StateChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.stateChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new StateChangeEvent(this.source);
					}
					target.stateChanged(event);
				}
			}
		}

		this.sourceChanged(null);
	}


	// ********** property change support **********

	protected static final Class<PropertyChangeListener> PROPERTY_CHANGE_LISTENER_CLASS = PropertyChangeListener.class;

	/**
	 * Return whether the values are equal, with the appropriate null checks.
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
	 * Return whether the values are different, with the appropriate null checks.
	 * Convenience method for checking whether an attribute value has changed.
	 */
	public boolean valuesAreDifferent(Object value1, Object value2) {
		return ! this.valuesAreEqual(value1, value2);
	}

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

	private PropertyChangeListener[] propertyChangeListeners() {
		return (PropertyChangeListener[]) this.listeners(PROPERTY_CHANGE_LISTENER_CLASS);
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

		String propertyName = event.getPropertyName();

		PropertyChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			PropertyChangeListener[] propertyChangeListeners = this.propertyChangeListeners();
			if (propertyChangeListeners != null) {
				targets = propertyChangeListeners.clone();
			}
			child = this.child(propertyName);
		}

		if (targets != null) {
			for (PropertyChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.propertyChangeListeners(), target);
				}
				if (stillListening) {
					target.propertyChanged(event);
				}
			}
		}
		if (child != null) {
			child.firePropertyChanged(event);
		}

		this.sourceChanged(propertyName);
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

		PropertyChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			PropertyChangeListener[] propertyChangeListeners = this.propertyChangeListeners();
			if (propertyChangeListeners != null) {
				targets = propertyChangeListeners.clone();
			}
			child = this.child(propertyName);
		}

		PropertyChangeEvent event = null;

		if (targets != null) {
			for (PropertyChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.propertyChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new PropertyChangeEvent(this.source, propertyName, oldValue, newValue);
					}
					target.propertyChanged(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.firePropertyChanged(propertyName, oldValue, newValue);
			} else {
				child.firePropertyChanged(event);
			}
		}

		this.sourceChanged(propertyName);
	}

	/**
	 * Report an int bound property update to any registered listeners.
	 * No event is fired if old and new are equal.
	 * <p>
	 * This is merely a convenience wrapper around the more general
	 * firePropertyChange method that takes Object values.
	 */
	public void firePropertyChanged(String propertyName, int oldValue, int newValue) {
//		this.firePropertyChanged(propertyName, new Integer(oldValue), new Integer(newValue));
		if (oldValue == newValue) {
			return;
		}

		PropertyChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			PropertyChangeListener[] propertyChangeListeners = this.propertyChangeListeners();
			if (propertyChangeListeners != null) {
				targets = propertyChangeListeners.clone();
			}
			child = this.child(propertyName);
		}

		PropertyChangeEvent event = null;

		if (targets != null) {
			for (PropertyChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.propertyChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new PropertyChangeEvent(this.source, propertyName, new Integer(oldValue), new Integer(newValue));
					}
					target.propertyChanged(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.firePropertyChanged(propertyName, oldValue, newValue);
			} else {
				child.firePropertyChanged(event);
			}
		}

		this.sourceChanged(propertyName);
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

		PropertyChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			PropertyChangeListener[] propertyChangeListeners = this.propertyChangeListeners();
			if (propertyChangeListeners != null) {
				targets = propertyChangeListeners.clone();
			}
			child = this.child(propertyName);
		}

		PropertyChangeEvent event = null;

		if (targets != null) {
			for (PropertyChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.propertyChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new PropertyChangeEvent(this.source, propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
					}
					target.propertyChanged(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.firePropertyChanged(propertyName, oldValue, newValue);
			} else {
				child.firePropertyChanged(event);
			}
		}

		this.sourceChanged(propertyName);
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

	private CollectionChangeListener[] collectionChangeListeners() {
		return (CollectionChangeListener[]) this.listeners(COLLECTION_CHANGE_LISTENER_CLASS);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemsAdded(CollectionChangeEvent event) {
		if (event.itemsSize() == 0) {
			return;
		}

		String collectionName = event.getCollectionName();

		CollectionChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			CollectionChangeListener[] collectionChangeListeners = this.collectionChangeListeners();
			if (collectionChangeListeners != null) {
				targets = collectionChangeListeners.clone();
			}
			child = this.child(collectionName);
		}

		if (targets != null) {
			for (CollectionChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.collectionChangeListeners(), target);
				}
				if (stillListening) {
					target.itemsAdded(event);
				}
			}
		}
		if (child != null) {
			child.fireItemsAdded(event);
		}

		this.sourceChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemsAdded(String collectionName, Collection<?> addedItems) {
//		this.fireItemsAdded(new CollectionChangeEvent(this.source, collectionName, addedItems));
		if (addedItems.size() == 0) {
			return;
		}

		CollectionChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			CollectionChangeListener[] collectionChangeListeners = this.collectionChangeListeners();
			if (collectionChangeListeners != null) {
				targets = collectionChangeListeners.clone();
			}
			child = this.child(collectionName);
		}

		CollectionChangeEvent event = null;

		if (targets != null) {
			for (CollectionChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.collectionChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionChangeEvent(this.source, collectionName, addedItems);
					}
					target.itemsAdded(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemsAdded(collectionName, addedItems);
			} else {
				child.fireItemsAdded(event);
			}
		}

		this.sourceChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemAdded(String collectionName, Object addedItem) {
//		this.fireItemsAdded(collectionName, Collections.singleton(addedItem));

		CollectionChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			CollectionChangeListener[] collectionChangeListeners = this.collectionChangeListeners();
			if (collectionChangeListeners != null) {
				targets = collectionChangeListeners.clone();
			}
			child = this.child(collectionName);
		}

		CollectionChangeEvent event = null;

		if (targets != null) {
			for (CollectionChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.collectionChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionChangeEvent(this.source, collectionName, Collections.singleton(addedItem));
					}
					target.itemsAdded(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemAdded(collectionName, addedItem);
			} else {
				child.fireItemsAdded(event);
			}
		}

		this.sourceChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemsRemoved(CollectionChangeEvent event) {
		if (event.itemsSize() == 0) {
			return;
		}

		String collectionName = event.getCollectionName();

		CollectionChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			CollectionChangeListener[] collectionChangeListeners = this.collectionChangeListeners();
			if (collectionChangeListeners != null) {
				targets = collectionChangeListeners.clone();
			}
			child = this.child(collectionName);
		}

		if (targets != null) {
			for (CollectionChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.collectionChangeListeners(), target);
				}
				if (stillListening) {
					target.itemsRemoved(event);
				}
			}
		}
		if (child != null) {
			child.fireItemsRemoved(event);
		}

		this.sourceChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemsRemoved(String collectionName, Collection<?> removedItems) {
//		this.fireItemsRemoved(new CollectionChangeEvent(this.source, collectionName, removedItems));
		if (removedItems.size() == 0) {
			return;
		}

		CollectionChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			CollectionChangeListener[] collectionChangeListeners = this.collectionChangeListeners();
			if (collectionChangeListeners != null) {
				targets = collectionChangeListeners.clone();
			}
			child = this.child(collectionName);
		}

		CollectionChangeEvent event = null;

		if (targets != null) {
			for (CollectionChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.collectionChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionChangeEvent(this.source, collectionName, removedItems);
					}
					target.itemsRemoved(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemsRemoved(collectionName, removedItems);
			} else {
				child.fireItemsRemoved(event);
			}
		}

		this.sourceChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireItemRemoved(String collectionName, Object removedItem) {
//		this.fireItemsRemoved(collectionName, Collections.singleton(removedItem));

		CollectionChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			CollectionChangeListener[] collectionChangeListeners = this.collectionChangeListeners();
			if (collectionChangeListeners != null) {
				targets = collectionChangeListeners.clone();
			}
			child = this.child(collectionName);
		}

		CollectionChangeEvent event = null;

		if (targets != null) {
			for (CollectionChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.collectionChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionChangeEvent(this.source, collectionName, Collections.singleton(removedItem));
					}
					target.itemsRemoved(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemRemoved(collectionName, removedItem);
			} else {
				child.fireItemsRemoved(event);
			}
		}

		this.sourceChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionCleared(CollectionChangeEvent event) {
		String collectionName = event.getCollectionName();

		CollectionChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			CollectionChangeListener[] collectionChangeListeners = this.collectionChangeListeners();
			if (collectionChangeListeners != null) {
				targets = collectionChangeListeners.clone();
			}
			child = this.child(collectionName);
		}

		if (targets != null) {
			for (CollectionChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.collectionChangeListeners(), target);
				}
				if (stillListening) {
					target.collectionCleared(event);
				}
			}
		}
		if (child != null) {
			child.fireCollectionCleared(event);
		}

		this.sourceChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionCleared(String collectionName) {
//		this.fireCollectionCleared(new CollectionChangeEvent(this.source, collectionName));

		CollectionChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			CollectionChangeListener[] collectionChangeListeners = this.collectionChangeListeners();
			if (collectionChangeListeners != null) {
				targets = collectionChangeListeners.clone();
			}
			child = this.child(collectionName);
		}

		CollectionChangeEvent event = null;

		if (targets != null) {
			for (CollectionChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.collectionChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionChangeEvent(this.source, collectionName);
					}
					target.collectionCleared(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireCollectionCleared(collectionName);
			} else {
				child.fireCollectionCleared(event);
			}
		}

		this.sourceChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionChanged(CollectionChangeEvent event) {
		String collectionName = event.getCollectionName();

		CollectionChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			CollectionChangeListener[] collectionChangeListeners = this.collectionChangeListeners();
			if (collectionChangeListeners != null) {
				targets = collectionChangeListeners.clone();
			}
			child = this.child(collectionName);
		}

		if (targets != null) {
			for (CollectionChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.collectionChangeListeners(), target);
				}
				if (stillListening) {
					target.collectionChanged(event);
				}
			}
		}
		if (child != null) {
			child.fireCollectionChanged(event);
		}

		this.sourceChanged(collectionName);
	}

	/**
	 * Report a bound collection update to any registered listeners.
	 */
	public void fireCollectionChanged(String collectionName) {
//		this.fireCollectionChanged(new CollectionChangeEvent(this.source, collectionName));

		CollectionChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			CollectionChangeListener[] collectionChangeListeners = this.collectionChangeListeners();
			if (collectionChangeListeners != null) {
				targets = collectionChangeListeners.clone();
			}
			child = this.child(collectionName);
		}

		CollectionChangeEvent event = null;

		if (targets != null) {
			for (CollectionChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.collectionChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new CollectionChangeEvent(this.source, collectionName);
					}
					target.collectionChanged(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireCollectionChanged(collectionName);
			} else {
				child.fireCollectionChanged(event);
			}
		}

		this.sourceChanged(collectionName);
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

	private ListChangeListener[] listChangeListeners() {
		return (ListChangeListener[]) this.listeners(LIST_CHANGE_LISTENER_CLASS);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsAdded(ListChangeEvent event) {
		if (event.itemsSize() == 0) {
			return;
		}

		String listName = event.getListName();

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					target.itemsAdded(event);
				}
			}
		}
		if (child != null) {
			child.fireItemsAdded(event);
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsAdded(String listName, int index, List<?> addedItems) {
//		this.fireItemsAdded(new ListChangeEvent(this.source, listName, index, addedItems));
		if (addedItems.size() == 0) {
			return;
		}

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		ListChangeEvent event = null;

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListChangeEvent(this.source, listName, index, addedItems);
					}
					target.itemsAdded(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemsAdded(listName, index, addedItems);
			} else {
				child.fireItemsAdded(event);
			}
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemAdded(String listName, int index, Object addedItem) {
//		this.fireItemsAdded(listName, index, Collections.singletonList(addedItem));

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		ListChangeEvent event = null;

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListChangeEvent(this.source, listName, index, Collections.singletonList(addedItem));
					}
					target.itemsAdded(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemAdded(listName, index, addedItem);
			} else {
				child.fireItemsAdded(event);
			}
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsRemoved(ListChangeEvent event) {
		if (event.itemsSize() == 0) {
			return;
		}

		String listName = event.getListName();

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					target.itemsRemoved(event);
				}
			}
		}
		if (child != null) {
			child.fireItemsRemoved(event);
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsRemoved(String listName, int index, List<?> removedItems) {
//		this.fireItemsRemoved(new ListChangeEvent(this.source, listName, index, removedItems));
		if (removedItems.size() == 0) {
			return;
		}

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		ListChangeEvent event = null;

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListChangeEvent(this.source, listName, index, removedItems);
					}
					target.itemsRemoved(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemsRemoved(listName, index, removedItems);
			} else {
				child.fireItemsRemoved(event);
			}
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemRemoved(String listName, int index, Object removedItem) {
//		this.fireItemsRemoved(listName, index, Collections.singletonList(removedItem));

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		ListChangeEvent event = null;

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListChangeEvent(this.source, listName, index, Collections.singletonList(removedItem));
					}
					target.itemsRemoved(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemRemoved(listName, index, removedItem);
			} else {
				child.fireItemsRemoved(event);
			}
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsReplaced(ListChangeEvent event) {
		if (event.itemsSize() == 0) {
			return;
		}

		String listName = event.getListName();

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					target.itemsReplaced(event);
				}
			}
		}
		if (child != null) {
			child.fireItemsReplaced(event);
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsReplaced(String listName, int index, List<?> newItems, List<?> replacedItems) {
//		this.fireItemsReplaced(new ListChangeEvent(this.source, listName, index, newItems, replacedItems));
		if (newItems.size() == 0) {
			return;
		}

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		ListChangeEvent event = null;

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListChangeEvent(this.source, listName, index, newItems, replacedItems);
					}
					target.itemsReplaced(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemsReplaced(listName, index, newItems, replacedItems);
			} else {
				child.fireItemsReplaced(event);
			}
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemReplaced(String listName, int index, Object newItem, Object replacedItem) {
//		this.fireItemsReplaced(listName, index, Collections.singletonList(newItem), Collections.singletonList(replacedItem));

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		ListChangeEvent event = null;

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListChangeEvent(this.source, listName, index, Collections.singletonList(newItem), Collections.singletonList(replacedItem));
					}
					target.itemsReplaced(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemReplaced(listName, index, newItem, replacedItem);
			} else {
				child.fireItemsReplaced(event);
			}
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsMoved(ListChangeEvent event) {
		if (event.getTargetIndex() == event.getSourceIndex()) {
			return;
		}

		String listName = event.getListName();

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					target.itemsMoved(event);
				}
			}
		}
		if (child != null) {
			child.fireItemsMoved(event);
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireItemsMoved(String listName, int targetIndex, int sourceIndex, int length) {
//		this.fireItemsMoved(new ListChangeEvent(this.source, listName, targetIndex, sourceIndex, length));
		if (targetIndex == sourceIndex) {
			return;
		}

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		ListChangeEvent event = null;

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListChangeEvent(this.source, listName, targetIndex, sourceIndex, length);
					}
					target.itemsMoved(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireItemsMoved(listName, targetIndex, sourceIndex, length);
			} else {
				child.fireItemsMoved(event);
			}
		}

		this.sourceChanged(listName);
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
	public void fireListCleared(ListChangeEvent event) {
		String listName = event.getListName();

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					target.listCleared(event);
				}
			}
		}
		if (child != null) {
			child.fireListCleared(event);
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListCleared(String listName) {
//		this.fireListCleared(new ListChangeEvent(this.source, listName));

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		ListChangeEvent event = null;

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListChangeEvent(this.source, listName);
					}
					target.listCleared(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireListCleared(listName);
			} else {
				child.fireListCleared(event);
			}
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListChanged(ListChangeEvent event) {
		String listName = event.getListName();

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					target.listChanged(event);
				}
			}
		}
		if (child != null) {
			child.fireListChanged(event);
		}

		this.sourceChanged(listName);
	}

	/**
	 * Report a bound list update to any registered listeners.
	 */
	public void fireListChanged(String listName) {
//		this.fireListChanged(new ListChangeEvent(this.source, listName));

		ListChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			ListChangeListener[] listChangeListeners = this.listChangeListeners();
			if (listChangeListeners != null) {
				targets = listChangeListeners.clone();
			}
			child = this.child(listName);
		}

		ListChangeEvent event = null;

		if (targets != null) {
			for (ListChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.listChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new ListChangeEvent(this.source, listName);
					}
					target.listChanged(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireListChanged(listName);
			} else {
				child.fireListChanged(event);
			}
		}

		this.sourceChanged(listName);
	}


	// ********** tree change support **********

	protected static final Class<TreeChangeListener> TREE_CHANGE_LISTENER_CLASS = TreeChangeListener.class;
	private static final Object[] EMPTY_TREE_PATH = new Object[0];

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

	private TreeChangeListener[] treeChangeListeners() {
		return (TreeChangeListener[]) this.listeners(TREE_CHANGE_LISTENER_CLASS);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeAdded(TreeChangeEvent event) {
		String treeName = event.getTreeName();

		TreeChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			TreeChangeListener[] treeChangeListeners = this.treeChangeListeners();
			if (treeChangeListeners != null) {
				targets = treeChangeListeners.clone();
			}
			child = this.child(treeName);
		}

		if (targets != null) {
			for (TreeChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.treeChangeListeners(), target);
				}
				if (stillListening) {
					target.nodeAdded(event);
				}
			}
		}
		if (child != null) {
			child.fireNodeAdded(event);
		}

		this.sourceChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeAdded(String treeName, Object[] path) {
//		this.fireNodeAdded(new TreeChangeEvent(this.source, treeName, path));

		TreeChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			TreeChangeListener[] treeChangeListeners = this.treeChangeListeners();
			if (treeChangeListeners != null) {
				targets = treeChangeListeners.clone();
			}
			child = this.child(treeName);
		}

		TreeChangeEvent event = null;

		if (targets != null) {
			for (TreeChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.treeChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new TreeChangeEvent(this.source, treeName, path);
					}
					target.nodeAdded(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireNodeAdded(treeName, path);
			} else {
				child.fireNodeAdded(event);
			}
		}

		this.sourceChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeRemoved(TreeChangeEvent event) {
		String treeName = event.getTreeName();

		TreeChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			TreeChangeListener[] treeChangeListeners = this.treeChangeListeners();
			if (treeChangeListeners != null) {
				targets = treeChangeListeners.clone();
			}
			child = this.child(treeName);
		}

		if (targets != null) {
			for (TreeChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.treeChangeListeners(), target);
				}
				if (stillListening) {
					target.nodeRemoved(event);
				}
			}
		}
		if (child != null) {
			child.fireNodeRemoved(event);
		}

		this.sourceChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireNodeRemoved(String treeName, Object[] path) {
//		this.fireNodeRemoved(new TreeChangeEvent(this.source, treeName, path));

		TreeChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			TreeChangeListener[] treeChangeListeners = this.treeChangeListeners();
			if (treeChangeListeners != null) {
				targets = treeChangeListeners.clone();
			}
			child = this.child(treeName);
		}

		TreeChangeEvent event = null;

		if (targets != null) {
			for (TreeChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.treeChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new TreeChangeEvent(this.source, treeName, path);
					}
					target.nodeRemoved(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireNodeRemoved(treeName, path);
			} else {
				child.fireNodeRemoved(event);
			}
		}

		this.sourceChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeCleared(TreeChangeEvent event) {
		String treeName = event.getTreeName();

		TreeChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			TreeChangeListener[] treeChangeListeners = this.treeChangeListeners();
			if (treeChangeListeners != null) {
				targets = treeChangeListeners.clone();
			}
			child = this.child(treeName);
		}

		if (targets != null) {
			for (TreeChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.treeChangeListeners(), target);
				}
				if (stillListening) {
					target.treeCleared(event);
				}
			}
		}
		if (child != null) {
			child.fireTreeCleared(event);
		}

		this.sourceChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeCleared(String treeName, Object[] path) {
//		this.fireTreeCleared(new TreeChangeEvent(this.source, treeName, path));

		TreeChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			TreeChangeListener[] treeChangeListeners = this.treeChangeListeners();
			if (treeChangeListeners != null) {
				targets = treeChangeListeners.clone();
			}
			child = this.child(treeName);
		}

		TreeChangeEvent event = null;

		if (targets != null) {
			for (TreeChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.treeChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new TreeChangeEvent(this.source, treeName, path);
					}
					target.treeCleared(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireTreeCleared(treeName, path);
			} else {
				child.fireTreeCleared(event);
			}
		}

		this.sourceChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeCleared(String treeName) {
		this.fireTreeCleared(treeName, EMPTY_TREE_PATH);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeChanged(TreeChangeEvent event) {
		String treeName = event.getTreeName();

		TreeChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			TreeChangeListener[] treeChangeListeners = this.treeChangeListeners();
			if (treeChangeListeners != null) {
				targets = treeChangeListeners.clone();
			}
			child = this.child(treeName);
		}

		if (targets != null) {
			for (TreeChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.treeChangeListeners(), target);
				}
				if (stillListening) {
					target.treeChanged(event);
				}
			}
		}
		if (child != null) {
			child.fireTreeChanged(event);
		}

		this.sourceChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeChanged(String treeName, Object[] path) {
//		this.fireTreeChanged(new TreeChangeEvent(this.source, treeName, path));

		TreeChangeListener[] targets = null;
		ChangeSupport child = null;

		synchronized (this) {
			TreeChangeListener[] treeChangeListeners = this.treeChangeListeners();
			if (treeChangeListeners != null) {
				targets = treeChangeListeners.clone();
			}
			child = this.child(treeName);
		}

		TreeChangeEvent event = null;

		if (targets != null) {
			for (TreeChangeListener target : targets) {
				boolean stillListening;
				synchronized (this) {
					stillListening = CollectionTools.contains(this.treeChangeListeners(), target);
				}
				if (stillListening) {
					if (event == null) {
						// here's the reason for the duplicate code...
						event = new TreeChangeEvent(this.source, treeName, path);
					}
					target.treeChanged(event);
				}
			}
		}
		if (child != null) {
			if (event == null) {
				child.fireTreeChanged(treeName, path);
			} else {
				child.fireTreeChanged(event);
			}
		}

		this.sourceChanged(treeName);
	}

	/**
	 * Report a bound tree update to any registered listeners.
	 */
	public void fireTreeChanged(String treeName) {
		this.fireTreeChanged(treeName, EMPTY_TREE_PATH);
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
		int len1 = this.genericListeners.length;
		for (int i = 0; i < len1; i++) {
			this.writeObject(s, this.genericListeners[i]);
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

	private synchronized void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		// read in the source, children, and any hidden stuff
		s.defaultReadObject();

		// read in generic listener lists
		this.genericListeners = EMPTY_GENERIC_LISTENERS;
		Object o;
		while (null != (o = s.readObject())) {
			@SuppressWarnings("unchecked")
			Class<? extends ChangeListener> listenerClass = (Class<? extends ChangeListener>) o;
			GenericListenerList gll = null;
			while (null != (o = s.readObject())) {
				if (gll == null) {
					gll = this.addGenericListenerListInternal(listenerClass, (ChangeListener) o);
				} else {
					gll.addListener((ChangeListener) o);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends ChangeListener> GenericListenerList addGenericListenerListInternal(Class<T> listenerClass, ChangeListener listener) {
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

		void addListener(ChangeListener listener) {
			this.listeners = CollectionTools.add(this.listeners, listener);
		}

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

}
