/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.BasePluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * This {@link javax.swing.ButtonModel} can be used to keep a listener
 * (e.g. a {@link javax.swing.JRadioButton}) in sync with a (typically shared)
 * {@link org.eclipse.jpt.common.utility.model.value.PropertyValueModel}
 * that holds one value out of a set of values.
 * <p>
 * <strong>NB:</strong> Do <em>not</em> use this model with a
 * {@link javax.swing.ButtonGroup}, since the
 * shared model and the wrappers built by this adapter will
 * keep the appropriate radio button checked. Also, this allows
 * us to uncheck all the radio buttons in a group when the shared
 * value is <code>null</code>.
 * 
 * @param <V> the type of the value corresponding to the radio button
 */
public class RadioButtonModelAdapter<V>
	extends ToggleButtonModelAdapter
{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor - the (typically shared) value model is required.
	 * The radio button's default value will be <code>false</code> (i.e. off).
	 */
	public RadioButtonModelAdapter(ModifiablePropertyValueModel<V> sharedValueModel, V buttonValue) {
		super(buildBooleanModel(sharedValueModel, buttonValue));
	}

	/**
	 * Constructor - the (typically shared) value model is required.
	 * The radio button's default value will be the specified default value
	 * (where <code>false</code> = off and <code>true</code> = on).
	 */
	public RadioButtonModelAdapter(ModifiablePropertyValueModel<V> sharedValueModel, V buttonValue, boolean defaultValue) {
		super(buildBooleanModel(sharedValueModel, buttonValue), defaultValue);
	}

	/**
	 * Build up a set of wrappers that will convert the
	 * specified (typically shared) value model and button value to/from a boolean.
	 * <p>
	 * If the specified model's value matches the button value,
	 * the wrapper's value will be <code>true</code>.
	 * Likewise, if the wrapper's value is set to <code>true</code>,
	 * the wrapper will set the shared model's
	 * value to the button value.
	 */
	public static <V> ModifiablePropertyValueModel<Boolean> buildBooleanModel(ModifiablePropertyValueModel<V> sharedValueModel, V buttonValue) {
		return new LocalPropertyValueModel<>(sharedValueModel, buttonValue);
	}


	// ********** selection **********

	/**
	 * The user cannot de-select a radio button - the user
	 * can only <em>select</em> a radio button. Only the model can
	 * cause a radio button to be de-selected. We check the
	 * {@link javax.swing.DefaultButtonModel#ARMED} flag to
	 * determine whether the button is being de-selected by the user.
	 */
    @Override
	public void setSelected(boolean b) {
		if ((b == false) && this.isArmed()) {
			return;
		}
		super.setSelected(b);
	}


	// ********** shared model adapter **********

	/**
	 * Modifiable property value model that can be used as a particular
	 * radio button's model:<ul>
	 * <li><strong>GET:</strong><ul>
	 *     <li>If the shared model's value matches this model's button value,
	 *     this model's value will be <code>true</code>
	 *     <li>If the shared model's value is a non-<code>null</code> value
	 *     that does <em>not</em> match this model's button value,
	 *     this model's value will be <code>false</code>
	 *     <li>If the shared model's value is <code>null</code>
	 *     this model's value will also be <code>null</code>,
	 *     allowing the radio button to take it's default value
	 *     </ul>
	 * <li><strong>SET:</strong><ul>
	 *     <li>If this model's value is set to <code>true</code>,
	 *     it sets the shared model's value to this model's button value
	 *     <li>If this model's value is set to <code>false</code>,
	 *     it will <em>not</em> modify the shared model's value, as it will be
	 *     modified by another radio button's model
	 *     <li>This model's value should never be set to <code>null</code>
	 *     via its setter (i.e. from the radio button; since a radio button
	 *     sets its model's value with a
	 *     {@link javax.swing.ButtonModel#setSelected(boolean) <code>boolean</code> not a <code>Boolean</code>})
	 * </ul>
	 */
	public static final class LocalPropertyValueModel<V>
		extends BasePluggablePropertyValueModel<Boolean, PluggablePropertyValueModel.Adapter<Boolean>>
		implements ModifiablePropertyValueModel<Boolean>
	{
		private final ModifiablePropertyValueModel<V> sharedValueModel;
		private final V buttonValue;

		public LocalPropertyValueModel(ModifiablePropertyValueModel<V> sharedValueModel, V buttonValue) {
			super(buildAdapterFactory(sharedValueModel, buttonValue));
			this.sharedValueModel = sharedValueModel;
			this.buttonValue = buttonValue;
		}

		private static <V> PluggablePropertyValueModel.Adapter.Factory<Boolean> buildAdapterFactory(PropertyValueModel<V> sharedValueModel, V buttonValue) {
			return PropertyValueModelTools.pluggablePropertyValueModelAdapterFactory_(sharedValueModel, new GetTransformer<>(buttonValue));
		}
	
		public void setValue(Boolean value) {
			if (value.booleanValue()) {
				this.sharedValueModel.setValue(this.buttonValue);
			}
		}
	}

	/**
	 * This transformer will convert a value to {@link Boolean#TRUE}
	 * when it matches the configured button value.
	 */
	public static class GetTransformer<V>
		implements Transformer<V, Boolean>
	{
		private final V buttonValue;

		public GetTransformer(V buttonValue) {
			super();
			this.buttonValue = buttonValue;
		}

		/**
		 * If the specified value matches the button value return {@link Boolean#TRUE},
		 * if it is some other value return {@link Boolean#FALSE};
		 * but if it is <code>null</code> simply pass it through because it will
		 * cause the button model's default value to be used
		 */
		public Boolean transform(V value) {
			return (value == null) ? null : Boolean.valueOf(value.equals(this.buttonValue));
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.buttonValue);
		}
	}
}
