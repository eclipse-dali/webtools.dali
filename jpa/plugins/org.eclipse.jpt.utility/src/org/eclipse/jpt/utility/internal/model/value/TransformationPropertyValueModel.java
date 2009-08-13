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

import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * A <code>TransformationPropertyValueModel</code> wraps another
 * {@link PropertyValueModel} and uses a {@link Transformer}
 * to transform the wrapped value before it is returned by {@link #getValue()}.
 * <p>
 * As an alternative to building a {@link Transformer},
 * a subclass of <code>TransformationPropertyValueModel</code> can
 * either override {@link #transform_(Object)} or,
 * if something other than null should be returned when the wrapped value
 * is null, override {@link #transform(Object)}.
 * 
 * @see Transformer
 */
public class TransformationPropertyValueModel<T1, T2>
	extends PropertyValueModelWrapper<T1>
	implements PropertyValueModel<T2>
{
	protected final Transformer<T1, T2> transformer;


	// ********** constructors/initialization **********

	/**
	 * Construct a property value model with the specified nested
	 * property value model and the default transformer.
	 * Use this constructor if you want to override
	 * {@link #transform_(Object)} or {@link #transform(Object)}
	 * method instead of building a {@link Transformer}.
	 */
	public TransformationPropertyValueModel(PropertyValueModel<? extends T1> valueHolder) {
		super(valueHolder);
		this.transformer = this.buildTransformer();
	}

	/**
	 * Construct an property value model with the specified nested
	 * property value model and transformer.
	 */
	public TransformationPropertyValueModel(PropertyValueModel<? extends T1> valueHolder, Transformer<T1, T2> transformer) {
		super(valueHolder);
		this.transformer = transformer;
	}

	protected Transformer<T1, T2> buildTransformer() {
		return new DefaultTransformer();
	}


	// ********** PropertyValueModel implementation **********

	public T2 getValue() {
		// transform the object returned by the nested value model before returning it
		return this.transform(this.valueHolder.getValue());
	}


	// ********** PropertyValueModelWrapper implementation **********

	@Override
	protected void valueChanged(PropertyChangeEvent event) {
		// transform the values before propagating the change event
	    @SuppressWarnings("unchecked")
	    T1 eventOldValue = (T1) event.getOldValue();
		Object oldValue = this.transformOld(eventOldValue);
	    @SuppressWarnings("unchecked")
	    T1 eventNewValue = (T1) event.getNewValue();
		Object newValue = this.transformNew(eventNewValue);
		this.firePropertyChanged(VALUE, oldValue, newValue);
	}


	// ********** behavior **********

	/**
	 * Transform the specified value and return the result.
	 * This is called by
	 * {@link #getValue()},
	 * {@link #transformOld(T1)}, and
	 * {@link #transformNew(T1)}.
	 */
	protected T2 transform(T1 value) {
		return this.transformer.transform(value);
	}

	/**
	 * Transform the specified, non-null, value and return the result.
	 */
	protected T2 transform_(@SuppressWarnings("unused") T1 value) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}

	/**
	 * Transform the specified old value and return the result.
	 * By default, call {@link #transform(Object)}.
	 * This is called by {@link #valueChanged(PropertyChangeEvent)}.
	 */
	protected T2 transformOld(T1 value) {
		return this.transform(value);
	}
	
	/**
	 * Transform the specified new value and return the result.
	 * By default, call {@link #transform(Object)}.
	 * This is called by {@link #valueChanged(PropertyChangeEvent)}.
	 */
	protected T2 transformNew(T1 value) {
		return this.transform(value);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.getValue());
	}


	// ********** default transformer **********

	/**
	 * The default transformer will return null if the wrapped value is null.
	 * If the wrapped value is not null, it is transformed by a subclass
	 * implementation of #transform_(Object).
	 */
	protected class DefaultTransformer implements Transformer<T1, T2> {
		public T2 transform(T1 value) {
			return (value == null) ? null : TransformationPropertyValueModel.this.transform_(value);
		}
	}

}
