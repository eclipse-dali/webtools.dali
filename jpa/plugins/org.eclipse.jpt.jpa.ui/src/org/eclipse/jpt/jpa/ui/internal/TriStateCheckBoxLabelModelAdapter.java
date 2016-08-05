/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a pair of boolean models, one for the <em>specified</em> value and one for the
 * <em>default</em> value to a single boolean model.
 * If the <em>specified</em> model's value is non-<code>null</code>,
 * the model's value is <code>null</code>;
 * If the <em>specified</em> model's value is <code>null</code>,
 * the model's value is simply the <em>default</em> model's value.
 * <p>
 * This is, perhaps, backwards from expected behavior....
 * <p>
 * This model can be used to control the label of a tri-state checkbox.
 * The checkbox can be used with a {@link Boolean} value that can be either
 * explicitly specified as {@link Boolean#TRUE} or {@link Boolean#FALSE}; or
 * left unspecified, in which case the value takes on the default value,
 * which may change depending on context changes. Typically, the checkbox will
 * have a straightforward, normal label if the value is <em>specified</em>, since
 * the checkbox itself will indicate whether the value is true or false. But,
 * if the value is not <em>specified</em>, the checkbox will simply indicate that
 * its value is "unspecified". In this case the checkbox's <em>label</em>
 * will be changed to indicate what the resulting <em>default</em> value is;
 * and this default value can change according to the models' context etc.
 * 
 * @see TriStateCheckBoxLabelModelStringTransformer
 */
public class TriStateCheckBoxLabelModelAdapter
	implements PluggablePropertyValueModel.Adapter<Boolean>
{
	private final PropertyValueModel<Boolean> specifiedModel;
	private final PropertyChangeListener specifiedListener;
	/* class private */ volatile Boolean specified;

	private final PropertyValueModel<Boolean> defaultModel;
	private final PropertyChangeListener defaultListener;
	/* class private */ volatile Boolean default_;

	private final PluggablePropertyValueModel.Adapter.Listener<Boolean> listener;


	public TriStateCheckBoxLabelModelAdapter(
			PropertyValueModel<Boolean> specifiedModel,
			PropertyValueModel<Boolean> defaultModel,
			PluggablePropertyValueModel.Adapter.Listener<Boolean> listener
	) {
		super();
		if (specifiedModel == null) {
			throw new NullPointerException();
		}
		this.specifiedModel = specifiedModel;
		this.specifiedListener = new SpecifiedListener();

		if (defaultModel == null) {
			throw new NullPointerException();
		}
		this.defaultModel = defaultModel;
		this.defaultListener = new DefaultListener();

		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	/* class private */ class SpecifiedListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			TriStateCheckBoxLabelModelAdapter.this.specified = (Boolean) event.getNewValue();
			TriStateCheckBoxLabelModelAdapter.this.update();
		}
	}

	/* class private */ class DefaultListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			TriStateCheckBoxLabelModelAdapter.this.default_ = (Boolean) event.getNewValue();
			TriStateCheckBoxLabelModelAdapter.this.update();
		}
	}

	/* class private */ void update() {
		this.listener.valueChanged(this.buildValue());
	}

	/**
	 * If something is specified, the value is <code>null</code>;
	 * otherwise, the value is whatever the default value is.
	 */
	private Boolean buildValue() {
		return (this.specified != null) ? null : this.default_;
	}

	public Boolean engageModel() {
		this.specifiedModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.specifiedListener);
		this.specified = this.specifiedModel.getValue();
		this.defaultModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.defaultListener);
		this.default_ = this.defaultModel.getValue();
		return this.buildValue();
	}

	public Boolean disengageModel() {
		this.specifiedModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.specifiedListener);
		this.specified = null;
		this.defaultModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.defaultListener);
		this.default_ = null;
		return this.buildValue();
	}

	// ********** Factory **********

	static class Factory
		implements PluggablePropertyValueModel.Adapter.Factory<Boolean>
	{
		private final PropertyValueModel<Boolean> specifiedModel;
		private final PropertyValueModel<Boolean> defaultModel;

		public Factory(PropertyValueModel<Boolean> specifiedModel, PropertyValueModel<Boolean> defaultModel) {
			super();
			if (specifiedModel == null) {
				throw new NullPointerException();
			}
			this.specifiedModel = specifiedModel;
	
			if (defaultModel == null) {
				throw new NullPointerException();
			}
			this.defaultModel = defaultModel;
		}

		public TriStateCheckBoxLabelModelAdapter buildAdapter(PluggablePropertyValueModel.Adapter.Listener<Boolean> listener) {
			return new TriStateCheckBoxLabelModelAdapter(this.specifiedModel, this.defaultModel, listener);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	// ********** Convenience methods **********

	public static <S extends Model, SM extends PropertyValueModel<? extends S>> PropertyValueModel<Boolean> propertyValueModel(
			SM subjectModel,
			String specifiedAspectName,
			Transformer<? super S, Boolean> specifiedTransformer,
			String defaultAspectName,
			Predicate<? super S> defaultPredicate
	) {
		return propertyValueModel(
				PropertyValueModelTools.modelAspectAdapter(subjectModel, specifiedAspectName, specifiedTransformer),
				PropertyValueModelTools.modelAspectAdapter(subjectModel, defaultAspectName, defaultPredicate)
			);
	}

	public static <S extends Model> PropertyValueModel<Boolean> propertyValueModel(
			S subject,
			String specifiedAspectName,
			Transformer<? super S, Boolean> specifiedTransformer,
			String defaultAspectName,
			Predicate<? super S> defaultPredicate
	) {
		return propertyValueModel(
				PropertyValueModelTools.modelAspectAdapter(subject, specifiedAspectName, specifiedTransformer),
				PropertyValueModelTools.modelAspectAdapter(subject, defaultAspectName, defaultPredicate)
			);
	}

	public static <S extends Model, SM extends PropertyValueModel<? extends S>> PropertyValueModel<Boolean> propertyValueModel(
			SM subjectModel,
			String specifiedAspectName,
			Transformer<? super S, Boolean> specifiedTransformer,
			String defaultAspectName,
			Transformer<? super S, Boolean> defaultTransformer
	) {
		return propertyValueModel(
				PropertyValueModelTools.modelAspectAdapter(subjectModel, specifiedAspectName, specifiedTransformer),
				PropertyValueModelTools.modelAspectAdapter(subjectModel, defaultAspectName, defaultTransformer)
			);
	}

	public static <S extends Model> PropertyValueModel<Boolean> propertyValueModel(
			S subject,
			String specifiedAspectName,
			Transformer<? super S, Boolean> specifiedTransformer,
			String defaultAspectName,
			Transformer<? super S, Boolean> defaultTransformer
	) {
		return propertyValueModel(
				PropertyValueModelTools.modelAspectAdapter(subject, specifiedAspectName, specifiedTransformer),
				PropertyValueModelTools.modelAspectAdapter(subject, defaultAspectName, defaultTransformer)
			);
	}

	public static PropertyValueModel<Boolean> propertyValueModel(PropertyValueModel<Boolean> specifiedModel, PropertyValueModel<Boolean> defaultModel) {
		return PropertyValueModelTools.propertyValueModel(new Factory(specifiedModel, defaultModel));
	}
}
