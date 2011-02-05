/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import java.util.Iterator;
import java.util.ListIterator;

import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.common.utility.internal.iterators.SingleElementListIterator;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * An adapter that allows us to make a {@link PropertyValueModel} behave like
 * a read-only, single-element {@link ListValueModel}, sorta.
 * <p>
 * If the property's value is null, an empty iterator is returned
 * (i.e. you can't have a collection with a <code>null</code> element).
 */
public class PropertyListValueModelAdapter<E>
	extends AbstractListValueModel
	implements ListValueModel<E>
{
	/** The wrapped property value model. */
	protected final PropertyValueModel<? extends E> valueHolder;

	/** A listener that forwards any events fired by the value holder. */
	protected final PropertyChangeListener propertyChangeListener;

	/** Cache the value. */
	protected E value;


	// ********** constructors/initialization **********

	/**
	 * Convert the specified property value model to a list
	 * value model.
	 */
	public PropertyListValueModelAdapter(PropertyValueModel<? extends E> valueHolder) {
		super();
		if (valueHolder == null) {
			throw new NullPointerException();
		}
		this.valueHolder = valueHolder;
		this.propertyChangeListener = this.buildPropertyChangeListener();
		// postpone building the value and listening to the underlying value
		// until we have listeners ourselves...
	}

	/**
	 * The wrapped value has changed, forward an equivalent
	 * list change event to our listeners.
	 */
	protected PropertyChangeListener buildPropertyChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				@SuppressWarnings("unchecked")
				E eventNewValue = (E) event.getNewValue();
				PropertyListValueModelAdapter.this.valueChanged(eventNewValue);
			}
			@Override
			public String toString() {
				return "property change listener"; //$NON-NLS-1$
			}
		};
	}


	// ********** ListValueModel implementation **********

	public Iterator<E> iterator() {
		return this.listIterator();
	}

	public ListIterator<E> listIterator() {
		return (this.value == null) ?
					EmptyListIterator.<E>instance()
				:
					new SingleElementListIterator<E>(this.value);
	}

	public int size() {
		return (this.value == null) ? 0 : 1;
	}

	public E get(int index) {
		if (this.value == null) {
			throw this.buildIOOBE(index, 0);
		}
		if (index > 0) {
			throw this.buildIOOBE(index, 1);
		}
		return this.value;
	}

	protected static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
	public Object[] toArray() {
		return (this.value == null) ? EMPTY_OBJECT_ARRAY : new Object[] {this.value};
	}


	// ********** behavior **********

	protected IndexOutOfBoundsException buildIOOBE(int index, int size) {
		return new IndexOutOfBoundsException("Index: " + index + ", Size: " + size); //$NON-NLS-1$ //$NON-NLS-2$
	}

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

	/**
	 * synchronize our internal value with the wrapped value
	 * and fire the appropriate events
	 */
	protected void valueChanged(E newValue) {
		E oldValue = this.value;
		this.value = newValue;
		if (oldValue == null) {
			this.fireItemAdded(LIST_VALUES, 0, newValue);
		} else {
			if (newValue == null) {
				this.fireItemRemoved(LIST_VALUES, 0, oldValue);
			} else {
				this.fireItemReplaced(LIST_VALUES, 0, newValue, oldValue);
			}
		}
	}

	@Override
	public void toString(StringBuilder sb) {
		StringTools.append(sb, this);
	}

}
