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
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * This adapter adapts an subject property value model whose value is another model
 * and treats the <em>inner</em> model's value as this adapter's models's value.
 * As a result, this adapter listens for changes to either model
 * (<em>inner</em> or <em>subject</em>).
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
 * @param <V> the type of the adapter's value
 * @param <S> the type of the subject (and the subject model's value)
 * @param <SM> the type of the subject model
 * 
 * @see BasePluggablePropertyValueModel
 */
public final class PropertyAspectAdapter<V, S extends Model, SM extends PropertyValueModel<? extends S>>
	implements PluggablePropertyValueModel.Adapter<V>
{
	/** The subject model; whose value is cached as {@link #subject}. */
	private final SM subjectModel;

	/** Listens to {@link #subjectModel}. */
	private final PropertyChangeListener subjectListener;

	/** The subject; which is the value of {@link #subjectModel}. Can be <code>null</code>. */
	/* package */ volatile S subject;

	/** The name of the {@link #subject}'s aspect, which is transformed into the adapter's value. */
	private final String aspectName;

	/** The transformer that converts {@link #subject} into the adapter's value. */
	private final Transformer<? super S, ? extends V> transformer;

	/** Listens to {@link #subject}, if present. */
	private final PropertyChangeListener aspectListener;

	/** Backpointer to the <em>real</em> adapter. */
	private final BasePluggablePropertyValueModel.Adapter.Listener<V> listener;


	public PropertyAspectAdapter(Factory<V, S, SM> factory, BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
		super();
		if (factory == null) {
			throw new NullPointerException();
		}
		this.subjectModel = factory.subjectModel;
		this.subjectListener = new SubjectListener();

		this.aspectName = factory.aspectName;
		this.transformer = factory.transformer;

		this.aspectListener = new AspectListener();

		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	/* CU private */ class SubjectListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			@SuppressWarnings("unchecked")
			S newSubject = (S) event.getNewValue();
			PropertyAspectAdapter.this.subjectChanged(newSubject);
		}
	}

	/* CU private */ class AspectListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			@SuppressWarnings("unchecked")
			V newValue = (V) event.getNewValue();
			PropertyAspectAdapter.this.aspectChanged(newValue);
		}
	}


	// ********** BasePluggablePropertyValueModel.Adapter **********

	public V engageModel() {
		this.subjectModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.subjectListener);
		this.subject = this.subjectModel.getValue();
		return this.engageSubject();
	}

	private V engageSubject() {
		if (this.subject != null) {
			this.subject.addPropertyChangeListener(this.aspectName, this.aspectListener);
		}
		return this.buildValue();
	}

	public V disengageModel() {
		this.subjectModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.subjectListener);
		return this.disengageSubject();
	}

	private V disengageSubject() {
		if (this.subject != null) {
			this.subject.removePropertyChangeListener(this.aspectName, this.aspectListener);
			this.subject = null;
		}
		return null;
	}


	// ********** event handling **********

	/**
	 * Move our aspect listener to the new subject.
	 */
	protected void subjectChanged(S newSubject) {
		this.disengageSubject();
		this.subject = newSubject;
		this.listener.valueChanged(this.engageSubject());
	}

	protected void aspectChanged(V newValue) {
		this.listener.valueChanged(newValue);
	}


	// ********** misc **********

	/* package */ V buildValue() {
		return this.transformer.transform(this.subject);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.buildValue());
	}


	// ********** Factory **********

	/**
	 * @see PropertyAspectAdapter
	 */
	public static final class Factory<V, S extends Model, SM extends PropertyValueModel<? extends S>>
		implements PluggablePropertyValueModel.Adapter.Factory<V>
	{
		/* CU private */ final SM subjectModel;
		/* CU private */ final String aspectName;
		/* CU private */ final Transformer<? super S, ? extends V> transformer;

		public Factory(SM subjectModel, String aspectName, Transformer<? super S, ? extends V> transformer) {
			super();
			if (subjectModel == null) {
				throw new NullPointerException();
			}
			this.subjectModel = subjectModel;

			if (aspectName == null) {
				throw new NullPointerException();
			}
			this.aspectName = aspectName;

			if (transformer == null) {
				throw new NullPointerException();
			}
			this.transformer = transformer;
		}

		public PropertyAspectAdapter<V, S, SM> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new PropertyAspectAdapter<>(this, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}
