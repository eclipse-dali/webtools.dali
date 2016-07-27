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
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

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
 * @see ModifiablePropertyAspectAdapter
 */
public final class PluggablePropertyAspectAdapter<V, S, SM extends PropertyValueModel<? extends S>>
	implements ModifiablePropertyAspectAdapter.GetAdapter<V, S>, PropertyChangeListener
{
	private final SM subjectModel;

	private volatile S subject;

	private final SubjectAdapter<V, S> subjectAdapter;

	private final BasePluggablePropertyValueModel.Adapter.Listener<V> listener; // backpointer


	public PluggablePropertyAspectAdapter(SM subjectModel, SubjectAdapter.Factory<V, S> subjectAdapterFactory, BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
		super();
		if (subjectModel == null) {
			throw new NullPointerException();
		}
		this.subjectModel = subjectModel;

		if (subjectAdapterFactory == null) {
			throw new NullPointerException();
		}
		this.subjectAdapter = subjectAdapterFactory.buildAdapter(new SubjectAdapterListener());

		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	/**
	 * Simple callback.
	 * Allows us to keep the callback method internal.
	 * Also, a type cannot extend or implement one of its member types.
	 */
	/* CU private */ class SubjectAdapterListener
		implements SubjectAdapter.Listener<V>
	{
		public void valueChanged(V newValue) {
			PluggablePropertyAspectAdapter.this.aspectChanged(newValue);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	// ********** BasePluggablePropertyValueModel.Adapter **********

	public V engageModel() {
		this.subjectModel.addPropertyChangeListener(PropertyValueModel.VALUE, this);
		this.subject = this.subjectModel.getValue();
		return this.subjectAdapter.engageSubject(this.subject);
	}

	public V disengageModel() {
		S old = this.subject;
		this.subjectModel.removePropertyChangeListener(PropertyValueModel.VALUE, this);
		this.subject = null;
		return this.subjectAdapter.disengageSubject(old);
	}

	public S getSubject() {
		return this.subject;
	}


	// ********** event handling **********

	/**
	 * Move our subject adapter to the new subject.
	 */
	public void propertyChanged(PropertyChangeEvent event) {
		@SuppressWarnings("unchecked")
		S newSubject = (S) event.getNewValue();
		this.subjectAdapter.disengageSubject(this.subject);
		this.subject = newSubject;
		this.listener.valueChanged(this.subjectAdapter.engageSubject(this.subject));
	}

	/* CU private */ void aspectChanged(V newValue) {
		this.listener.valueChanged(newValue);
	}


	// ********** misc **********

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.subjectAdapter);
	}


	// ********** Subject Adapter **********

	/**
	 * Adapt an arbitrary subject to the aspect adapter's value;
	 * notifying the aspect adapter (via the {@link SubjectAdapter.Listener} interface)
	 * of any changes to the value.
	 */
	public interface SubjectAdapter<V, S> {

		/**
		 * Engage the specified subject, which can be <code>null</code>,
		 * and return the current value.
		 */
		V engageSubject(S subject);

		/**
		 * Disengage the specified subject, which can be <code>null</code>,
		 * and return the current value.
		 */
		V disengageSubject(S subject);

		/**
		 * Callback interface.
		 */
		interface Listener<V> {
			/**
			 * Callback to notify listener that the subject's aspect value
			 * has changed.
			 */
			void valueChanged(V newValue);
		}

		/**
		 * Subject adapter factory interface.
		 * This factory allows both the pluggable property value model adapter and its
		 * subject adapter to have circular <em>final</em> references to each other.
		 */
		interface Factory<V, S> {
			/**
			 * Create a subject adapter with the specified listener.
			 */
			SubjectAdapter<V, S> buildAdapter(Listener<V> listener);
		}
	}


	// ********** Factory **********

	/**
	 * @see PluggablePropertyAspectAdapter
	 */
	public static final class Factory<V, S, SM extends PropertyValueModel<? extends S>>
		implements PluggablePropertyValueModel.Adapter.Factory<V>, ModifiablePropertyAspectAdapter.GetAdapter.Factory<V, S>
	{
		private final SM subjectModel;
		private final SubjectAdapter.Factory<V, S> subjectAdapterFactory;

		public Factory(SM subjectModel, SubjectAdapter.Factory<V, S> subjectAdapterFactory) {
			super();
			if (subjectModel == null) {
				throw new NullPointerException();
			}
			this.subjectModel = subjectModel;

			if (subjectAdapterFactory == null) {
				throw new NullPointerException();
			}
			this.subjectAdapterFactory = subjectAdapterFactory;
		}

		public PluggablePropertyAspectAdapter<V, S, SM> buildAdapter(BasePluggablePropertyValueModel.Adapter.Listener<V> listener) {
			return new PluggablePropertyAspectAdapter<>(this.subjectModel, this.subjectAdapterFactory, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}
