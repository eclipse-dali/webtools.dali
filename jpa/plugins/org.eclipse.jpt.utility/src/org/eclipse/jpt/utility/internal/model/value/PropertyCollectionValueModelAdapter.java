/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Iterator;

import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.internal.model.SingleAspectChangeSupport;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;

/**
 * An adapter that allows us to make a PropertyValueModel behave like
 * a read-only, single-element CollectionValueModel, sorta.
 * 
 * If the property's value is null, an empty iterator is returned
 * (i.e. you can't have a collection with a null element).
 */
public class PropertyCollectionValueModelAdapter<E>
	extends AbstractModel
	implements CollectionValueModel<E>
{
	/** The wrapped property value model. */
	protected final PropertyValueModel<E> valueHolder;

	/** A listener that forwards any events fired by the value holder. */
	protected final PropertyChangeListener propertyChangeListener;

	/** Cache the value. */
	protected E value;


	// ********** constructors/initialization **********

	/**
	 * Wrap the specified ListValueModel.
	 */
	public PropertyCollectionValueModelAdapter(PropertyValueModel<E> valueHolder) {
		super();
		if (valueHolder == null) {
			throw new NullPointerException();
		}
		this.valueHolder = valueHolder;
		this.propertyChangeListener = this.buildPropertyChangeListener();
		// postpone building the value and listening to the underlying value
		// until we have listeners ourselves...
	}

	@Override
	protected ChangeSupport buildChangeSupport() {
		return new SingleAspectChangeSupport(this, CollectionChangeListener.class, VALUES);
	}

	/**
	 * The wrapped value has changed, forward an equivalent
	 * collection change event to our listeners.
	 */
	protected PropertyChangeListener buildPropertyChangeListener() {
		return new PropertyChangeListener() {
			@SuppressWarnings("unchecked")
			public void propertyChanged(PropertyChangeEvent e) {
				PropertyCollectionValueModelAdapter.this.valueChanged((E) e.newValue());
			}
			@Override
			public String toString() {
				return "property change listener";
			}
		};
	}


	// ********** CollectionValueModel implementation **********

	public Iterator<E> iterator() {
		return (this.value == null) ?
					EmptyIterator.<E>instance()
				:
					new SingleElementIterator<E>(this.value);
	}

	public int size() {
		return (this.value == null) ? 0 : 1;
	}


	// ********** extend change support **********

	/**
	 * Override to start listening to the value holder if necessary.
	 */
	@Override
	public void addCollectionChangeListener(CollectionChangeListener listener) {
		if (this.hasNoListeners()) {
			this.engageModel();
		}
		super.addCollectionChangeListener(listener);
	}

	/**
	 * Override to start listening to the value holder if necessary.
	 */
	@Override
	public void addCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		if (collectionName == VALUES && this.hasNoListeners()) {
			this.engageModel();
		}
		super.addCollectionChangeListener(collectionName, listener);
	}

	/**
	 * Override to stop listening to the value holder if appropriate.
	 */
	@Override
	public void removeCollectionChangeListener(CollectionChangeListener listener) {
		super.removeCollectionChangeListener(listener);
		if (this.hasNoListeners()) {
			this.disengageModel();
		}
	}

	/**
	 * Override to stop listening to the value holder if appropriate.
	 */
	@Override
	public void removeCollectionChangeListener(String collectionName, CollectionChangeListener listener) {
		super.removeCollectionChangeListener(collectionName, listener);
		if (collectionName == VALUES && this.hasNoListeners()) {
			this.disengageModel();
		}
	}


	// ********** queries **********

	protected boolean hasListeners() {
		return this.hasAnyCollectionChangeListeners(VALUES);
	}

	protected boolean hasNoListeners() {
		return ! this.hasListeners();
	}


	// ********** behavior **********

	protected void engageModel() {
		this.valueHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.propertyChangeListener);
		// synch our value *after* we start listening to the value holder,
		// since its value might change when a listener is added
		this.value = this.valueHolder.value();
	}

	protected void disengageModel() {
		this.valueHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.propertyChangeListener);
		// clear out the value when we are not listening to the value holder
		this.value = null;
	}

	/**
	 * synchronize our internal value with the wrapped value
	 * and fire the appropriate events
	 */
	protected void valueChanged(E newValue) {
		// put in "empty" check so we don't fire events unnecessarily
		if (this.value != null) {
			E oldValue = this.value;
			this.value = null;
			this.fireItemRemoved(VALUES, oldValue);
		}
		this.value = newValue;
		// put in "empty" check so we don't fire events unnecessarily
		if (this.value != null) {
			this.fireItemAdded(VALUES, this.value);
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.valueHolder);
	}

}
