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
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * This adapter can be used by a {@link PluggablePropertyAspectAdapter
 * pluggable property aspect adapter} to convert one of a {@link Model model's}
 * <em>bound</em> properties to a
 * {@link org.eclipse.jpt.common.utility.model.value.PropertyValueModel property value model}.
 * 
 * @param <V> the type of the adapter's value
 * @param <S> the type of the subject whose <em>bound</em> property is transformed into
 *   the value
 */
public final class ModelPropertyAspectAdapter<V, S extends Model>
	implements PluggablePropertyAspectAdapter.SubjectAdapter<V, S>, PropertyChangeListener
{
	private final String propertyName;

	private final Transformer<? super S, ? extends V> propertyTransformer;

	private final PluggablePropertyAspectAdapter.SubjectAdapter.Listener<V> listener; // backpointer to aspect adapter


	public ModelPropertyAspectAdapter(String propertyName, Transformer<? super S, ? extends V> propertyTransformer, PluggablePropertyAspectAdapter.SubjectAdapter.Listener<V> listener) {
		super();
		if (propertyName == null) {
			throw new NullPointerException();
		}
		this.propertyName = propertyName;

		if (propertyTransformer == null) {
			throw new NullPointerException();
		}
		this.propertyTransformer = propertyTransformer;

		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public V engageSubject(S subject) {
		if (subject != null) {
			subject.addPropertyChangeListener(this.propertyName, this);
		}
		return this.propertyTransformer.transform(subject);
	}

	public V disengageSubject(S subject) {
		if (subject != null) {
			subject.removePropertyChangeListener(this.propertyName, this);
		}
		return this.propertyTransformer.transform(null);
	}

	public void propertyChanged(PropertyChangeEvent event) {
		@SuppressWarnings("unchecked")
		V newValue = (V) event.getNewValue();
		this.listener.valueChanged(newValue);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.propertyName);
	}


	// ********** Factory **********

	/**
	 * @see PluggablePropertyAspectAdapter
	 */
	public static final class Factory<V, S extends Model>
		implements PluggablePropertyAspectAdapter.SubjectAdapter.Factory<V, S>
	{
		/* CU private */ final String propertyName;
		/* CU private */ final Transformer<? super S, ? extends V> propertyTransformer;

		public Factory(String propertyName, Transformer<? super S, ? extends V> propertyTransformer) {
			super();
			if (propertyName == null) {
				throw new NullPointerException();
			}
			this.propertyName = propertyName;

			if (propertyTransformer == null) {
				throw new NullPointerException();
			}
			this.propertyTransformer = propertyTransformer;
		}

		public PluggablePropertyAspectAdapter.SubjectAdapter<V, S> buildAdapter(PluggablePropertyAspectAdapter.SubjectAdapter.Listener<V> listener) {
			return new ModelPropertyAspectAdapter<>(this.propertyName, this.propertyTransformer, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}
}
