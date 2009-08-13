/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * An adapter that allows us to make a {@link PropertyValueModel} behave like
 * a read-only, single-element {@link CollectionValueModel}, sorta.
 * <p>
 * If the property's value is null, an empty iterator is returned
 * (i.e. you can't have a collection with a <code>null</code> element).
 */
public class PropertyCollectionValueModelAdapter<E>
	extends AbstractCollectionValueModel
	implements CollectionValueModel<E>
{
	/** The wrapped property value model. */
	protected final PropertyValueModel<? extends E> valueHolder;

	/** A listener that forwards any events fired by the value holder. */
	protected final PropertyChangeListener propertyChangeListener;

	/** Cache the value. */
	protected E value;


	// ********** constructors/initialization **********

	/**
	 * Convert the specified property value model to a collection
	 * value model.
	 */
	public PropertyCollectionValueModelAdapter(PropertyValueModel<? extends E> valueHolder) {
		super();
		if (valueHolder == null) {
			throw new NullPointerException();
		}
		this.valueHolder = valueHolder;
		this.propertyChangeListener = this.buildPropertyChangeListener();
		this.value = null;
		// postpone building the value and listening to the underlying value
		// until we have listeners ourselves...
	}

	/**
	 * The wrapped value has changed, forward an equivalent
	 * collection change event to our listeners.
	 */
	protected PropertyChangeListener buildPropertyChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				@SuppressWarnings("unchecked")
				E eventNewValue = (E) event.getNewValue();
				PropertyCollectionValueModelAdapter.this.valueChanged(eventNewValue);
			}
			@Override
			public String toString() {
				return "property change listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** CollectionValueModel implementation **********

	public Iterator<E> iterator() {
		return (this.value == null) ? EmptyIterator.<E>instance() : this.iterator_();
	}

	protected Iterator<E> iterator_() {
		return new SingleElementIterator<E>(this.value);
	}

	public int size() {
		return (this.value == null) ? 0 : 1;
	}


	// ********** AbstractCollectionValueModel implementation **********

	@Override
	protected void engageModel() {
		this.valueHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.propertyChangeListener);
		// synch our value *after* we start listening to the value holder,
		// since its value might change when a listener is added
		this.value = this.valueHolder.getValue();
	}

	@Override
	protected void disengageModel() {
		this.valueHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.propertyChangeListener);
		// clear out the value when we are not listening to the value holder
		this.value = null;
	}


	// ********** behavior **********

	/**
	 * synchronize our internal value with the wrapped value
	 * and fire the appropriate events
	 */
	protected void valueChanged(E newValue) {
		E oldValue = this.value;
		this.value = newValue;
		if (oldValue == null) {
			// we wouldn't get the event if the new value were null too
			this.fireItemAdded(VALUES, newValue);
		} else {
			if (newValue == null) {
				this.fireItemRemoved(VALUES, oldValue);
			} else {
				// we wouldn't get the event if the new value was the same as the old
				this.fireCollectionChanged(VALUES, Collections.singleton(newValue));
			}
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		StringTools.append(sb, this);
	}

}
