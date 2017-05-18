/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.tests.internal.swt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.eclipse.jpt.common.ui.internal.swt.SpinnerModelAdapter;
import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("nls")
public class SpinnerModelAdapterTest {

	private Model model;
	private Shell shell;
	private ModifiablePropertyValueModel<Model> subjectHolder;

	private ModifiablePropertyValueModel<Model> buildSubjectHolder() {
		return new SimplePropertyValueModel<Model>();
	}

	private ModifiablePropertyValueModel<Integer> buildValueHolder() {
		return PropertyValueModelTools.modifiableModelAspectAdapter(
				this.subjectHolder,
				Model.VALUE_PROPERTY,
				Model.VALUE_TRANSFORMER,
				Model.SET_VALUE_CLOSURE
			);
	}

	@Before
	public void setUp() throws Exception {

		shell              = new Shell(Display.getCurrent());
		model              = new Model();
		subjectHolder      = buildSubjectHolder();
	}

	@After
	public void tearDown() throws Exception {

		if (!shell.isDisposed()) {
			shell.dispose();
		}

		shell         = null;
		subjectHolder = null;
	}

	@Test
	public void testDisposed() {

		int value = 2;
		model.setValue(value);
		model.clearSetValueCalledFlag();
		subjectHolder.setValue(model);

		Spinner spinner = new Spinner(shell, SWT.NULL);
		ModifiablePropertyValueModel<Integer> numberHolder = buildValueHolder();
		int defaultValue = 1;

		SpinnerModelAdapter.adapt(
			numberHolder,
			spinner,
			defaultValue
		);

		assertEquals(
			"The spinner's value should be coming from the model",
			value,
			spinner.getSelection()
		);

		assertEquals(
			"The number holder's value should be the model's value",
			model.getValue(),
			numberHolder.getValue()
		);

		assertFalse(
			"The model should not have received the value during initialization",
			model.isSetValueCalled()
		);

		// Change the value in the model
		spinner.dispose();

		value = 4;
		model.setValue(value);

		assertEquals(
			"The model's value was somehow changed",
			Integer.valueOf(value),
			model.getValue()
		);
	}

	@Test
	public void testInitialization_1() {

		Spinner spinner = new Spinner(shell, SWT.NULL);
		ModifiablePropertyValueModel<Integer> numberHolder = new SimplePropertyValueModel<Integer>();
		int defaultValue = 1;

		SpinnerModelAdapter.adapt(
			numberHolder,
			spinner,
			defaultValue
		);

		assertEquals(
			"The spinner's value should be the default value",
			defaultValue,
			spinner.getSelection()
		);

		assertNull(
			"The number holder's value should be null",
			numberHolder.getValue()
		);
	}

	@Test
	public void testInitialization_2() {

		Spinner spinner = new Spinner(shell, SWT.NULL);
		ModifiablePropertyValueModel<Integer> numberHolder = buildValueHolder();
		int defaultValue = 1;

		SpinnerModelAdapter.adapt(
			numberHolder,
			spinner,
			defaultValue
		);

		assertEquals(
			"The spinner's value should be the default value",
			defaultValue,
			spinner.getSelection()
		);

		assertNull(
			"The number holder's value should be null",
			numberHolder.getValue()
		);
	}

	@Test
	public void testInitialization_3() {

		subjectHolder.setValue(model);

		Spinner spinner = new Spinner(shell, SWT.NULL);
		ModifiablePropertyValueModel<Integer> numberHolder = buildValueHolder();
		int defaultValue = 1;

		SpinnerModelAdapter.adapt(
			numberHolder,
			spinner,
			defaultValue
		);

		assertEquals(
			"The spinner's value should be the default value",
			defaultValue,
			spinner.getSelection()
		);

		assertNull(
			"The number holder's value should be null",
			numberHolder.getValue()
		);

		assertFalse(
			"The model should not have received the value during initialization",
			model.isSetValueCalled()
		);
	}

	@Test
	public void testInitialization_4() {

		int value = 2;
		model.setValue(value);
		model.clearSetValueCalledFlag();
		subjectHolder.setValue(model);

		Spinner spinner = new Spinner(shell, SWT.NULL);
		ModifiablePropertyValueModel<Integer> numberHolder = buildValueHolder();
		int defaultValue = 1;

		SpinnerModelAdapter.adapt(
			numberHolder,
			spinner,
			defaultValue
		);

		assertEquals(
			"The spinner's value should be the value coming from the model",
			value,
			spinner.getSelection()
		);

		assertEquals(
			"The number holder's value should be " + value,
			Integer.valueOf(value),
			numberHolder.getValue()
		);

		assertFalse(
			"The model should not have received the value during initialization",
			model.isSetValueCalled()
		);
	}

	@Test
	public void testValueChanged() {

		int value = 2;
		model.setValue(value);
		model.clearSetValueCalledFlag();
		subjectHolder.setValue(model);

		Spinner spinner = new Spinner(shell, SWT.NULL);
		ModifiablePropertyValueModel<Integer> numberHolder = buildValueHolder();
		int defaultValue = 1;

		SpinnerModelAdapter.adapt(
			numberHolder,
			spinner,
			defaultValue
		);

		assertEquals(
			"The spinner's value should be coming from the model",
			value,
			spinner.getSelection()
		);

		assertEquals(
			"The number holder's value should be the model's value",
			model.getValue(),
			numberHolder.getValue()
		);

		assertFalse(
			"The model should not have received the value during initialization",
			model.isSetValueCalled()
		);

		// Change the value in the model
		value = 4;
		model.setValue(value);

		assertEquals(
			"The spinner's value should be coming from the model",
			value,
			spinner.getSelection()
		);

		assertEquals(
			"The model's value was somehow changed",
			Integer.valueOf(value),
			model.getValue()
		);

		// Change the value from the spinner
		value = 6;
		spinner.setSelection(value);

		assertEquals(
			"The spinner's value should be the new value set",
			value,
			spinner.getSelection()
		);

		assertEquals(
			"The model's value was supposed to be updated",
			Integer.valueOf(value),
			model.getValue()
		);

		// Disconnect from model
		subjectHolder.setValue(null);

		assertEquals(
			"The spinner's value should be the default value",
			defaultValue,
			spinner.getSelection()
		);
	}

	private static class Model extends AbstractModel {

		private boolean setValueCalled;
		private Integer value;

		static final String VALUE_PROPERTY = "value";
		public static final Transformer<Model, Integer> VALUE_TRANSFORMER = new ValueTransformer();
		public static final class ValueTransformer
			extends TransformerAdapter<Model, Integer>
		{
			@Override
			public Integer transform(Model model) {
				return model.getValue();
			}
		}
		public static final BiClosure<Model, Integer> SET_VALUE_CLOSURE = new SetValueClosure();
		public static final class SetValueClosure
			extends BiClosureAdapter<Model, Integer>
		{
			@Override
			public void execute(Model model, Integer value) {
				model.setValue(value);
			}
		}

		void clearSetValueCalledFlag() {
			setValueCalled = false;
		}

		Integer getValue() {
			return value;
		}

		boolean isSetValueCalled() {
			return setValueCalled;
		}

		void setValue(Integer value) {
			setValueCalled = true;

			Integer oldValue = this.value;
			this.value = value;
			firePropertyChanged(VALUE_PROPERTY, oldValue, value);
		}
	}
}
