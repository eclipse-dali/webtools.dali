/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringBuilderTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.collection.HashBag;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
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
 * 
 * @see Model
 * @see AbstractModel
 */
public class ChangeSupport {
	/** The object to be provided as the "source" for any generated events. */
	protected final Model source;

	/** Associate aspect names to class-specific listener lists. */
	private AspectListenerListPair<?>[] aspectListenerListPairs = EMPTY_ASPECT_LISTENER_LIST_PAIR_ARRAY;
		private static final AspectListenerListPair<?>[] EMPTY_ASPECT_LISTENER_LIST_PAIR_ARRAY = new AspectListenerListPair[0];

	private final ExceptionHandler exceptionHandler;


	// ********** constructor **********

	/**
	 * Construct support for the specified source of change events.
	 * The source cannot be <code>null</code>.
	 */
	public ChangeSupport(Model source) {
		this(source, DefaultExceptionHandler.instance());
	}

	/**
	 * Construct support for the specified source of change events.
	 * Neither the source nor the exception handler can be <code>null</code>.
	 */
	public ChangeSupport(Model source, ExceptionHandler exceptionHandler) {
		super();
		if (source == null) {
			throw new NullPointerException();
		}
		if (exceptionHandler == null) {
			throw new NullPointerException();
		}
		this.source = source;
		this.exceptionHandler = exceptionHandler;
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

	private Iterable<ChangeListener> getChangeListeners() {
		return this.getListenerList(CHANGE_LISTENER_CLASS);
	}

	private boolean hasChangeListener(ChangeListener listener) {
		return IterableTools.contains(this.getChangeListeners(), listener);
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

	private Iterable<StateChangeListener> getStateChangeListeners() {
		return this.getListenerList(STATE_CHANGE_LISTENER_CLASS);
	}

	private boolean hasStateChangeListener(StateChangeListener listener) {
		return IterableTools.contains(this.getStateChangeListeners(), listener);
	}

	/**
	 * Fire the specified state change event to any registered listeners.
	 */
	public void fireStateChanged(StateChangeEvent event) {
		Iterable<StateChangeListener> listeners = this.getStateChangeListeners();
		if (listeners != null) {
			for (StateChangeListener listener : listeners) {
				if (this.hasStateChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.stateChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.stateChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.stateChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new StateChangeEvent(this.source);
					}
					try {
						listener.stateChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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

	private Iterable<PropertyChangeListener> getPropertyChangeListeners(String propertyName) {
		return this.getListenerList(PROPERTY_CHANGE_LISTENER_CLASS, propertyName);
	}

	private boolean hasPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		return IterableTools.contains(this.getPropertyChangeListeners(propertyName), listener);
	}

	/**
	 * Fire the specified property change event to any registered listeners.
	 * No event is fired if the specified event's old and new values are the same;
	 * this includes when both values are null. Use a state change event
	 * for general purpose notification of changes.
	 * Return whether the old and new values are different.
	 */
	public boolean firePropertyChanged(PropertyChangeEvent event) {
		if (ObjectTools.notEquals(event.getOldValue(), event.getNewValue())) {
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
					try {
						listener.propertyChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.propertyChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
		if (ObjectTools.notEquals(oldValue, newValue)) {
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
					try {
						listener.propertyChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new PropertyChangeEvent(this.source, propertyName, oldValue, newValue);
					}
					try {
						listener.propertyChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.propertyChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new PropertyChangeEvent(this.source, propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
					}
					try {
						listener.propertyChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.propertyChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new PropertyChangeEvent(this.source, propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
					}
					try {
						listener.propertyChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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

	private Iterable<CollectionChangeListener> getCollectionChangeListeners(String collectionName) {
		return this.getListenerList(COLLECTION_CHANGE_LISTENER_CLASS, collectionName);
	}

	private boolean hasCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		return IterableTools.contains(this.getCollectionChangeListeners(collectionName), listener);
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
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionAddEvent(this.source, collectionName, addedItems);
					}
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionAddEvent(this.source, collectionName, addedItem);
					}
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionRemoveEvent(this.source, collectionName, removedItems);
					}
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionRemoveEvent(this.source, collectionName, removedItem);
					}
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.collectionCleared(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.collectionCleared(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.collectionCleared(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionClearEvent(this.source, collectionName);
					}
					try {
						listener.collectionCleared(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.collectionChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.collectionChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.collectionChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new CollectionChangeEvent(this.source, collectionName, collection);
					}
					try {
						listener.collectionChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
				&& this.addItemsToCollection_(IteratorTools.iterator(items), collection, collectionName);
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
				&& this.removeItemsFromCollection_(IteratorTools.iterator(items), collection, collectionName);
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
		HashBag<?> removedItems = CollectionTools.hashBag(items);
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
		return this.retainItemsInCollection_(IteratorTools.iterator(items), collection, collectionName);
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
		HashBag<?> retainedItems = CollectionTools.hashBag(items);
		HashBag<?> removedItems = CollectionTools.hashBag(collection);
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

		return this.synchronizeCollection_(CollectionTools.hashBag(newCollection), collection, collectionName);
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

	private Iterable<ListChangeListener> getListChangeListeners(String listName) {
		return this.getListenerList(LIST_CHANGE_LISTENER_CLASS, listName);
	}

	private boolean hasListChangeListener(String listName, ListChangeListener listener) {
		return IterableTools.contains(this.getListChangeListeners(listName), listener);
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
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListAddEvent(this.source, listName, index, addedItems);
					}
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListAddEvent(this.source, listName, index, addedItem);
					}
					try {
						listener.itemsAdded(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListRemoveEvent(this.source, listName, index, removedItems);
					}
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListRemoveEvent(this.source, listName, index, removedItem);
					}
					try {
						listener.itemsRemoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}
	}

	/**
	 * Report a bound list update to any registered listeners.
	 * Return whether there are any replaced items.
	 */
	public boolean fireItemsReplaced(ListReplaceEvent event) {
		if ((event.getItemsSize() != 0) && IterableTools.elementsAreDifferent(event.getNewItems(), event.getOldItems())) {
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
					try {
						listener.itemsReplaced(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.itemsReplaced(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
		if (( ! newItems.isEmpty()) && IterableTools.elementsAreDifferent(newItems, oldItems)) {
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
					try {
						listener.itemsReplaced(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListReplaceEvent(this.source, listName, index, newItems, oldItems);
					}
					try {
						listener.itemsReplaced(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
		if (ObjectTools.notEquals(newItem, oldItem)) {
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
					try {
						listener.itemsReplaced(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListReplaceEvent(this.source, listName, index, newItem, oldItem);
					}
					try {
						listener.itemsReplaced(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsMoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.itemsMoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.itemsMoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListMoveEvent(this.source, listName, targetIndex, sourceIndex, length);
					}
					try {
						listener.itemsMoved(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.listCleared(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.listCleared(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.listCleared(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListClearEvent(this.source, listName);
					}
					try {
						listener.listCleared(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.listChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					try {
						listener.listChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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
					try {
						listener.listChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
				}
			}
		}

		Iterable<ChangeListener> changeListeners = this.getChangeListeners();
		if (changeListeners != null) {
			for (ChangeListener listener : changeListeners) {
				if (this.hasChangeListener(listener)) {  // verify listener is still listening
					if (event == null) {
						event = new ListChangeEvent(this.source, listName, list);
					}
					try {
						listener.listChanged(event);
					} catch (Throwable t) {
						this.exceptionHandler.handleException(t);
					}
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

		ArrayList<E> addedItems = ListTools.arrayList(items);
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
		ArrayList<E> addedItems = ListTools.arrayList(items);
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
				&& this.removeItemsFromList_(IteratorTools.iterator(items), list, listName);
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
		return this.retainItemsInList_(IteratorTools.iterator(items), list, listName);
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
		HashBag<?> retainedItems = CollectionTools.hashBag(items);
		HashBag<?> removedItems = CollectionTools.hashBag(list);
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
		if ((index != -1) && ObjectTools.notEquals(oldItem, newItem)) {
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
		ListTools.move(list, targetIndex, sourceIndex, length);
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
		if (ObjectTools.equals(list.get(targetIndex), list.get(sourceIndex))) {
			return false;
		}
		ListTools.move(list, targetIndex, sourceIndex);
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
		return this.synchronizeList_(ListTools.arrayList(newList), list, listName);
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
			if (ObjectTools.notEquals(newItem, oldItem)) {
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


	// ********** misc **********

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
	 * This method is public so one delegate can call a nested abstract delegate's
	 * {@link #toString(StringBuilder)}.
	 */
	public void toString(StringBuilder sb) {
		sb.append(this.source);
		sb.append(" : "); //$NON-NLS-1$
		StringBuilderTools.append(sb, this.aspectListenerListPairs);
	}


	// ********** member classes **********

	/**
	 * Pair a possibly <code>null</code> aspect name with its associated
	 * listeners.
	 */
	static abstract class AspectListenerListPair<L extends EventListener> {
		private final Class<? extends EventListener> listenerClass;
		final ListenerList<L> listenerList;

		AspectListenerListPair(Class<L> listenerClass, L listener) {
			super();
			this.listenerClass = listenerClass;
			this.listenerList = ModelTools.listenerList(listener);
		}

		abstract String getAspectName();

		boolean matches(Class<? extends EventListener> listenerType, @SuppressWarnings("unused") String aspectName) {
			return this.listenerClass == listenerType;
		}

		boolean matches(Class<? extends EventListener> listenerType) {
			return this.matches(listenerType, null);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(this.getAspectName());
			sb.append(" => "); //$NON-NLS-1$
			this.listenerList.toString(sb);
			return sb.toString();
		}
	}

	/**
	 * Pair a non-<code>null</code> aspect name with its associated listeners.
	 */
	static class SimpleAspectListenerListPair<L extends EventListener>
		extends AspectListenerListPair<L>
	{
		final String aspectName;

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
