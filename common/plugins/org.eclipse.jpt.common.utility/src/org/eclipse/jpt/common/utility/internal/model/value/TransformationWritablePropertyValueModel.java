/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;

/**
 * A <code>TransformationWritablePropertyValueModel</code> wraps another
 * {@link WritablePropertyValueModel} and uses two {@link Transformer}s
 * to:<ul>
 * <li>transform the wrapped value before it is returned by {@link #getValue()}
 * <li>"reverse-transform" the new value that comes in via
 * {@link #setValue(Object)}
 * </ul>
 * <strong>NB:</strong> If the mapping between the wrapped values and the
 * transformed values is not strictly one-to-one, the behavior of the
 * transformation model may seem unpredictable; since the wrapped model's
 * change events can cause the transformation model's value to change.
 * For example: If the value is a string to be <em>transformed</em> to uppercase
 * and <em>reverse-transformed</em> to lowercase; then mixed case values passed to
 * the transformation model will be<ul>
 * <li>unchanged if the transformation model has <em>no</em> listeners
 * <li>changed to uppercase if the transformation model has any listeners
 * ({@link #wrappedValueChanged(org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent)})
 * </ul>
 * As an alternative to building two {@link Transformer}s,
 * a subclass of <code>TransformationWritablePropertyValueModel</code> can
 * override {@link #transform_(Object)} and {@link #reverseTransform_(Object)};
 * or, if something other than <code>null</code> should be returned when the
 * wrapped value is <code>null</code> or the new value is <code>null</code>,
 * override {@link #transform(Object)} and {@link #reverseTransform(Object)}.
 * 
 * @param <V1> the type of the <em>wrapped</em> model's value
 * @param <V2> the type of the model's <em>transformed</em> value
 * @see Transformer
 */
public class TransformationWritablePropertyValueModel<V1, V2>
	extends TransformationPropertyValueModel<V1, V2>
	implements WritablePropertyValueModel<V2>
{
	protected final Transformer<V2, V1> reverseTransformer;


	// ********** constructors/initialization **********

	/**
	 * Construct a writable property value model with the specified nested
	 * writable property value model and the default transformers.
	 * Use this constructor if you want to override the
	 * {@link #transform_(Object)} and {@link #reverseTransform_(Object)}
	 * (or {@link #transform(Object)} and {@link #reverseTransform(Object)})
	 * methods instead of building {@link Transformer}s.
	 */
	public TransformationWritablePropertyValueModel(WritablePropertyValueModel<V1> valueModel) {
		super(valueModel);
		this.reverseTransformer = this.buildReverseTransformer();
	}

	/**
	 * Construct a writable property value model with the specified nested
	 * writable property value model and transformers.
	 */
	public TransformationWritablePropertyValueModel(WritablePropertyValueModel<V1> valueModel, Transformer<V1, V2> transformer, Transformer<V2, V1> reverseTransformer) {
		super(valueModel, transformer);
		if (reverseTransformer == null) {
			throw new NullPointerException();
		}
		this.reverseTransformer = reverseTransformer;
	}

	protected Transformer<V2, V1> buildReverseTransformer() {
		return new DefaultReverseTransformer();
	}


	// ********** WritablePropertyValueModel implementation **********

	/**
	 * Cache and "reverse-transform" the new value before passing it to the the
	 * nested value model.
	 * A side-effect of caching the new value is that the wrapper will
	 * <em>not</em> fire an event when the wrapped model fires an event when
	 * called from here.
	 * @see #wrappedValueChanged(org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent)
	 */
	public void setValue(V2 value) {
		this.value = value;
		this.getValueModel().setValue(this.reverseTransform(value));
	}


	// ********** transformation **********

	/**
	 * "Reverse-transform" the specified value and return the result.
	 * This is called by {@link #setValue(Object)}.
	 */
	protected V1 reverseTransform(V2 v) {
		return this.reverseTransformer.transform(v);
	}

	/**
	 * "Reverse-transform" the specified, non-<code>null</code>,
	 * value and return the result.
	 */
	protected V1 reverseTransform_(@SuppressWarnings("unused") V2 v) {
		throw new RuntimeException("This method was not overridden."); //$NON-NLS-1$
	}


	// ********** queries **********

	/**
	 * Our constructors accept only a {@link WritablePropertyValueModel}{@code<V>},
	 * so this cast should be safe.
	 */
	@SuppressWarnings("unchecked")
	protected WritablePropertyValueModel<V1> getValueModel() {
		return (WritablePropertyValueModel<V1>) this.valueModel;
	}


	// ********** default reverse transformer **********

	/**
	 * The default reverse transformer will return <code>null</code>
	 * if the new value is <code>null</code>.
	 * If the new value is not <code>null</code>, it is reverse-transformed
	 * by a subclass implementation of {@link #reverseTransform_(Object)}.
	 */
	protected class DefaultReverseTransformer
		implements Transformer<V2, V1>
	{
		public V1 transform(V2 v) {
			return (v == null) ? null : TransformationWritablePropertyValueModel.this.reverseTransform_(v);
		}
	}
}
