/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

/**
 * This adapter adapts an (<em>outer</em>) property value model whose
 * value is yet another (<em>inner</em>) property value model
 * and treats the <em>inner</em> model's value as this adapter's models's value.
 * As a result, this adapter listens for changes to either model
 * (<em>inner</em> or <em>outer</em>).
 * <p>
 * A typical usage:<br>A (simple) model <code>A</code> is wrapped by
 * a transformation model <code>B</code> that transforms model <code>A</code>'s
 * value to yet another model <code>C</code>. This adapter is
 * then constructed with model <code>B</code>.<ul>
 * <li>If model <code>A</code>'s value changes
 * (e.g. as the result of an Eclipse-generated event), model <code>B</code>
 * will recalculate its value, a new model <code>C</code>, and this adapter's value will
 * be recalculated etc.</li>
 * <li>If model <code>C</code>'s value changes
 * (e.g. its value is deleted from the Eclipse workspace), this adapter's value will
 * be recalculated etc.</li>
 * </ul>
 * This is an adapter that can be used by a {@link BasePluggablePropertyValueModel}.
 * 
 * @param <V> the type of both the adapter's and the <em>inner</em> model's values
 * @param <IM> the type of the <em>inner</em> model (and the <em>outer</em> model's value)
 * @param <OM> the type of the <em>outer</em> model
 * @param <A> the type of the adapter itself
 * @param <F> the type of the adapter factory
 * 
 * @see BasePluggablePropertyValueModel
 */
public abstract class BaseCompoundPropertyValueModelAdapter<V, IM extends PropertyValueModel<? extends V>, OM extends PropertyValueModel<? extends IM>, A extends BasePluggablePropertyValueModel.Adapter<V>, F extends BaseCompoundPropertyValueModelAdapter.Factory<V, IM, OM, A>>
	implements BasePluggablePropertyValueModel.Adapter<V>
{
	/** The <em>outer</em> model; whose value is cached as {@link #innerModel}. */
	protected final OM outerModel;

	/** Listens to {@link #outerModel}. */
	protected final PropertyChangeListener outerValueListener;

	/** The <em>inner</em> model; which is the value of {@link #outerModel}. Can be <code>null</code>. */
	protected volatile IM innerModel;

	/** Listens to {@link #innerModel}, if present. */
	protected final PropertyChangeListener innerValueListener;

	/** The derived value. */
	private volatile V value;

	/** The <em>real</em> adapter. */
	private final BasePluggablePropertyValueModel.Adapter.Listener<V> listener;


	public BaseCompoundPropertyValueModelAdapter(OM outerModel, BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
		super();
		if (outerModel == null) {
			throw new NullPointerException();
		}
		this.outerModel = outerModel;
		this.outerValueListener = new OuterValueListener();

		this.innerValueListener = new InnerValueListener();

		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	/* CU private */ class OuterValueListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			@SuppressWarnings("unchecked")
			IM newInnerModel = (IM) event.getNewValue();
			BaseCompoundPropertyValueModelAdapter.this.outerValueChanged(newInnerModel);
		}
	}

	/* CU private */ class InnerValueListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			@SuppressWarnings("unchecked")
			V newValue = (V) event.getNewValue();
			BaseCompoundPropertyValueModelAdapter.this.innerValueChanged(newValue);
		}
	}


	// ********** BasePluggablePropertyValueModel.Adapter **********

	public V engageModel() {
		this.outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.outerValueListener);
		this.innerModel = this.outerModel.getValue();
		return this.engageInnerModel();
	}

	private V engageInnerModel() {
		if (this.innerModel == null) {
			return null;
		}
		this.innerModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.innerValueListener);
		return this.value = this.innerModel.getValue();
	}

	public V disengageModel() {
		this.outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.outerValueListener);
		return this.disengageInnerModel();
	}

	private V disengageInnerModel() {
		if (this.innerModel == null) {
			return null;
		}
		this.innerModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.innerValueListener);
		this.innerModel = null;
		return this.value = null;
	}


	// ********** change events **********

	/**
	 * Move our <em>inner</em> value listener to the new inner model.
	 */
	protected void outerValueChanged(IM newInnerModel) {
		this.disengageInnerModel();
		this.innerModel = newInnerModel;
		this.engageInnerModel();
		this.listener.valueChanged(this.value);
	}

	protected void innerValueChanged(V newValue) {
		this.listener.valueChanged(this.value = newValue);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.value);
	}


	// ********** Factory **********

	public abstract static class Factory<V, IM extends PropertyValueModel<? extends V>, OM extends PropertyValueModel<? extends IM>, A extends BasePluggablePropertyValueModel.Adapter<V>>
		implements BasePluggablePropertyValueModel.Adapter.Factory<V, A>
	{
		/* CU private */ final OM outerModel;

		public Factory(OM outerModel) {
			super();
			if (outerModel == null) {
				throw new NullPointerException();
			}
			this.outerModel = outerModel;
		}

		public abstract A buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener);

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}
