/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.value;

import org.eclipse.jpt.utility.internal.BidiTransformer;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;

/**
 * A <code>TransformationWritablePropertyValueModel</code> wraps another
 * <code>WritablePropertyValueModel</code> and uses a <code>BidiTransformer</code>
 * to:<ul>
 * <li>transform the wrapped value before it is returned by <code>value()</code>
 * <li>"reverse-transform" the new value that comes in via
 * <code>setValue(Object)</code>
 * </ul>
 * As an alternative to building a <code>BidiTransformer</code>,
 * a subclass of <code>TransformationWritablePropertyValueModel</code> can
 * override the <code>transform_(Object)</code> and 
 * <code>reverseTransform_(Object)</code> methods; or,
 * if something other than null should be returned when the wrapped value
 * is null or the new value is null, override the <code>transform(Object)</code>
 * and <code>reverseTransform(Object)</code> methods.
 */
public class TransformationWritablePropertyValueModel<T1, T2>
	extends TransformationPropertyValueModel<T1, T2>
	implements WritablePropertyValueModel<T2>
{

	// ********** constructors/initialization **********

	/**
	 * Construct a writable property value model with the specified nested
	 * writable property value model and the default bidi transformer.
	 * Use this constructor if you want to override the
	 * <code>transform_(Object)</code> and <code>reverseTransform_(Object)</code>
	 * (or <code>transform(Object)</code> and <code>reverseTransform(Object)</code>)
	 * methods instead of building a <code>BidiTransformer</code>.
	 */
	public TransformationWritablePropertyValueModel(WritablePropertyValueModel<T1> valueHolder) {
		super(valueHolder);
	}

	/**
	 * Construct a writable property value model with the specified nested
	 * writable property value model and bidi transformer.
	 */
	public TransformationWritablePropertyValueModel(WritablePropertyValueModel<T1> valueHolder, BidiTransformer<T1, T2> transformer) {
		super(valueHolder, transformer);
	}

	@Override
	protected BidiTransformer<T1, T2> buildTransformer() {
		return new DefaultBidiTransformer();
	}


	// ********** WritablePropertyValueModel implementation **********

	public void setValue(T2 value) {
		// "reverse-transform" the object before passing it to the the nested value model
		this.valueHolder().setValue(this.reverseTransform(value));
	}


	// ********** behavior **********

	/**
	 * "Reverse-transform" the specified value and return the result.
	 * This is called by #setValue(Object).
	 */
	protected T1 reverseTransform(T2 value) {
		return this.transformer().reverseTransform(value);
	}

	/**
	 * "Reverse-transform" the specified, non-null, value and return the result.
	 */
	protected T1 reverseTransform_(T2 value) {
		throw new UnsupportedOperationException();
	}


	// ********** queries **********

	/**
	 * Our constructors accept only a WritablePropertyValueModel<T1>.
	 */
	@SuppressWarnings("unchecked")
	protected WritablePropertyValueModel<T1> valueHolder() {
		return (WritablePropertyValueModel<T1>) this.valueHolder;
	}

	/**
	 * Our constructors accept only a bidirectional transformer.
	 */
	protected BidiTransformer<T1, T2> transformer() {
		return (BidiTransformer<T1, T2>) this.transformer;
	}


	// ********** default bidi transformer **********

	/**
	 * The default bidi transformer will return null if the wrapped value is null.
	 * If the wrapped value is not null, it is transformed by a subclass
	 * implementation of #transform_(Object).
	 * The default bidi transformer will also return null if the new value is null.
	 * If the new value is not null, it is reverse-transformed by a subclass
	 * implementation of #reverseTransform_(Object).
	 */
	protected class DefaultBidiTransformer
		extends DefaultTransformer
		implements BidiTransformer<T1, T2>
	{
		public T1 reverseTransform(T2 value) {
			return (value == null) ? null : TransformationWritablePropertyValueModel.this.reverseTransform_(value);
		}
	}

}
