/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
 * {@link WritablePropertyValueModel} and uses a {@link BidiTransformer}
 * to:<ul>
 * <li>transform the wrapped value before it is returned by {@link #getValue()}
 * <li>"reverse-transform" the new value that comes in via
 * {@link #setValue(Object)}
 * </ul>
 * As an alternative to building a {@link BidiTransformer},
 * a subclass of <code>TransformationWritablePropertyValueModel</code> can
 * override {@link #transform_(Object)} and {@link #reverseTransform_(Object)};
 * or, if something other than null should be returned when the wrapped value
 * is null or the new value is null, override {@link #transform(Object)}
 * and {@link #reverseTransform(Object)}.
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
	 * {@link #transform_(Object)} and {@link #reverseTransform_(Object)}
	 * (or {@link #transform(Object)} and {@link #reverseTransform(Object)})
	 * methods instead of building a {@link BidiTransformer}.
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
		this.getValueHolder().setValue(this.reverseTransform(value));
	}


	// ********** behavior **********

	/**
	 * "Reverse-transform" the specified value and return the result.
	 * This is called by {@link #setValue(Object)}.
	 */
	protected T1 reverseTransform(T2 value) {
		return this.getTransformer().reverseTransform(value);
	}

	/**
	 * "Reverse-transform" the specified, non-<code>null</code>,
	 * value and return the result.
	 */
	protected T1 reverseTransform_(@SuppressWarnings("unused") T2 value) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}


	// ********** queries **********

	/**
	 * Our constructors accept only a {@link WritablePropertyValueModel<T1>},
	 * so this cast should be safe.
	 */
	@SuppressWarnings("unchecked")
	protected WritablePropertyValueModel<T1> getValueHolder() {
		return (WritablePropertyValueModel<T1>) this.valueHolder;
	}

	/**
	 * Our constructors accept only a {@link BidiTransformer<T1, T2>},
	 * so this cast should be safe.
	 */
	protected BidiTransformer<T1, T2> getTransformer() {
		return (BidiTransformer<T1, T2>) this.transformer;
	}


	// ********** default bidi transformer **********

	/**
	 * The default bidi transformer will return <code>null</code> if the
	 * wrapped value is <code>null</code>.
	 * If the wrapped value is not <code>null</code>, it is transformed by a subclass
	 * implementation of {@link #transform_(Object)}.
	 * The default bidi transformer will also return <code>null</code>
	 * if the new value is <code>null</code>.
	 * If the new value is not <code>null</code>, it is reverse-transformed
	 * by a subclass implementation of {@link #reverseTransform_(Object)}.
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
