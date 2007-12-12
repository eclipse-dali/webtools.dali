/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.BidiTransformer;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;

/**
 * A <code>TransformationPropertyValueModel</code> wraps another
 * <code>PropertyValueModel</code> and uses a <code>BidiTransformer</code>
 * to:<ul>
 * <li>transform the wrapped value before it is returned by <code>value()</code>
 * <li>"reverse-transform" the new value that comes in via
 * <code>setValue(Object)</code>
 * </ul>
 * As an alternative to building a <code>BidiTransformer</code>,
 * a subclass of <code>TransformationPropertyValueModel</code> can
 * override the <code>transform(Object)</code> and 
 * <code>reverseTransform(Object)</code> methods.
 */
public class TransformationPropertyValueModel
	extends WritablePropertyValueModelWrapper
{
	private final BidiTransformer transformer;


	// ********** constructors **********

	/**
	 * Construct a property value model with the specified nested
	 * property value model and a disabled transformer.
	 * Use this constructor if you want to override the
	 * <code>transform(Object)</code> and <code>reverseTransform(Object)</code>
	 * methods instead of building a <code>BidiTransformer</code>.
	 */
	public TransformationPropertyValueModel(WritablePropertyValueModel valueHolder) {
		this(valueHolder, BidiTransformer.Disabled.instance());
	}

	/**
	 * Construct an property value model with the specified nested
	 * property value model and transformer.
	 */
	public TransformationPropertyValueModel(WritablePropertyValueModel valueHolder, BidiTransformer transformer) {
		super(valueHolder);
		this.transformer = transformer;
	}


	// ********** PropertyValueModel implementation **********

	public Object value() {
		// transform the object returned by the nested value model before returning it
		return this.transform(this.valueHolder.value());
	}


	// ********** WritablePropertyValueModel implementation **********

	public void setValue(Object value) {
		// "reverse-transform" the object before passing it to the the nested value model
		this.valueHolder.setValue(this.reverseTransform(value));
	}


	// ********** PropertyValueModelWrapper implementation **********

    @Override
	protected void valueChanged(PropertyChangeEvent e) {
		// transform the values before propagating the change event
		Object oldValue = this.transform(e.oldValue());
		Object newValue = this.transform(e.newValue());
		this.firePropertyChanged(VALUE, oldValue, newValue);
	}


	// ********** behavior **********

	/**
	 * Transform the specified object and return the result.
	 * This is called by #value().
	 */
	protected Object transform(Object value) {
		return this.transformer.transform(value);
	}

	/**
	 * "Reverse-transform" the specified object and return the result.
	 * This is called by #setValue(Object).
	 */
	protected Object reverseTransform(Object value) {
		return this.transformer.reverseTransform(value);
	}

}
