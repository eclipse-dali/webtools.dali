/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model.value.swing;

import org.eclipse.jpt.common.utility.internal.model.value.FilteringModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * This {@link javax.swing.ButtonModel} can be used to keep a listener
 * (e.g. a {@link javax.swing.JRadioButton}) in synch with a (typically shared)
 * {@link org.eclipse.jpt.common.utility.model.value.PropertyValueModel}
 * that holds one value out of a set of values.
 * <p>
 * <strong>NB:</strong> Do <em>not</em> use this model with a
 * {@link javax.swing.ButtonGroup}, since the
 * shared value holder and the wrappers built by this adapter will
 * keep the appropriate radio button checked. Also, this allows
 * us to uncheck all the radio buttons in a group when the shared
 * value is <code>null</code>.
 */
public class RadioButtonModelAdapter
	extends ToggleButtonModelAdapter
{
	private static final long serialVersionUID = 1L;

	// ********** constructors **********

	/**
	 * Constructor - the value holder is required.
	 */
	public RadioButtonModelAdapter(ModifiablePropertyValueModel<Object> valueHolder, Object buttonValue, boolean defaultValue) {
		super(buildBooleanHolder(valueHolder, buttonValue), defaultValue);
	}

	/**
	 * Constructor - the value holder is required.
	 * The default value will be false.
	 */
	public RadioButtonModelAdapter(ModifiablePropertyValueModel<Object> valueHolder, Object buttonValue) {
		super(buildBooleanHolder(valueHolder, buttonValue));
	}


	// ********** static methods **********

	/**
	 * Build up a set of wrappers that will convert the
	 * specified value holder and button value to/from a boolean.
	 * 
	 * If the value holder's value matches the button value,
	 * the wrapper will return true. Likewise, if the value holder's
	 * value is set to true, the wrapper will set the value holder's
	 * value to the button value.
	 */
	public static ModifiablePropertyValueModel<Boolean> buildBooleanHolder(ModifiablePropertyValueModel<Object> valueHolder, Object buttonValue) {
		ModifiablePropertyValueModel<Object> filteringPVM = new FilteringModifiablePropertyValueModel<Object>(valueHolder, Predicate.True.instance(), new SetRadioButtonFilter(buttonValue));
		return new TransformationModifiablePropertyValueModel<Object, Boolean>(filteringPVM, new RadioButtonTransformer(buttonValue), new ReverseRadioButtonTransformer(buttonValue));
	}


	// ********** overrides **********

	/**
	 * The user cannot de-select a radio button - the user
	 * can only *select* a radio button. Only the model can
	 * cause a radio button to be de-selected. We use the
	 * ARMED flag to indicate whether we are being de-selected
	 * by the user.
	 */
    @Override
	public void setSelected(boolean b) {
		// do not allow the user to de-select a radio button
		// radio buttons can
		if ((b == false) && this.isArmed()) {
			return;
		}
		super.setSelected(b);
	}


	// ********** filters **********

	/**
	 * This filter will only pass through a new value to the wrapped
	 * value model when it matches the configured button value.
	 */
	public static class SetRadioButtonFilter
		implements Predicate<Object>
	{
		private Object buttonValue;

		public SetRadioButtonFilter(Object buttonValue) {
			super();
			this.buttonValue = buttonValue;
		}

		/**
		 * pass through the value to the wrapped property value model
		 * *only* when it matches our button value
		 */
		public boolean evaluate(Object value) {
			return (value != null) && value.equals(this.buttonValue);
		}
	}


	// ********** transformers **********

	/**
	 * This transformer will convert a value to {@link Boolean#TRUE}
	 * when it matches the configured button value.
	 */
	public static class RadioButtonTransformer
		implements Transformer<Object, Boolean>
	{
		private Object buttonValue;

		public RadioButtonTransformer(Object buttonValue) {
			super();
			this.buttonValue = buttonValue;
		}

		/**
		 * If the specified value matches the button value return {@link Boolean#TRUE},
		 * if it is some other value return {@link Boolean#FALSE};
		 * but if it is <code>null</code> simply pass it through because it will
		 * cause the button model's default value to be used
		 */
		public Boolean transform(Object value) {
			return (value == null) ? null : Boolean.valueOf(value.equals(this.buttonValue));
		}
	}

	/**
	 * This transformer will convert {@link Boolean#TRUE} to the configured
	 * button value and {@link Boolean#FALSE} to <code>null</code>.
	 */
	public static class ReverseRadioButtonTransformer
		implements Transformer<Boolean, Object>
	{
		private Object buttonValue;

		public ReverseRadioButtonTransformer(Object buttonValue) {
			super();
			this.buttonValue = buttonValue;
		}

		/**
		 * If the specified value is {@link Boolean#TRUE},
		 * pass through the our button value;
		 * otherwise pass through <code>null</code>.
		 */
		public Object transform (Boolean value) {
			return (value.booleanValue()) ? this.buttonValue : null;
		}
	}
}
