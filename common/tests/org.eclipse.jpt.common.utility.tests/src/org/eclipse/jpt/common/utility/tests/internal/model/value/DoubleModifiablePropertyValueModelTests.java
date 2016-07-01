/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import org.eclipse.jpt.common.utility.internal.closure.ClosureAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;

@SuppressWarnings("nls")
public class DoubleModifiablePropertyValueModelTests
	extends DoublePropertyValueModelTests
{
	public DoubleModifiablePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected ModifiablePropertyValueModel<String> buildTestModel(PropertyValueModel<SimplePropertyValueModel<String>> modelModel) {
		return PropertyValueModelTools.doubleWrapModifiable(modelModel);
	}

	public class SetClosure
		extends ClosureAdapter<String>
	{
		@Override
		public void execute(String argument) {
			String key = DoubleModifiablePropertyValueModelTests.this.keyModel.getValue();
			DoubleModifiablePropertyValueModelTests.this.getValueModel(key).setValue(argument);
		}
	}

	protected ModifiablePropertyValueModel<String> getTestModel() {
		return (ModifiablePropertyValueModel<String>) this.testModel;
	}

	public void testSetValue() {
		assertNull(this.keyModel.getValue());
		assertNull(this.valueModelModel.getValue());
		assertNull(this.testModel.getValue());

		this.testModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.testModelListener);

		assertNull(this.keyModel.getValue());
		assertNull(this.valueModelModel.getValue());
		assertNull(this.testModel.getValue());

		this.keyModel.setValue("foo");
		assertEquals("foo", this.keyModel.getValue());
		assertEquals(this.valueModels.get("foo"), this.valueModelModel.getValue());
		assertEquals("foofoo", this.testModel.getValue());

		this.getTestModel().setValue("bar");
		assertEquals("foo", this.keyModel.getValue());
		assertEquals(this.valueModels.get("foo"), this.valueModelModel.getValue());
		assertEquals("bar", this.testModel.getValue());

		this.getTestModel().setValue(null);
		assertEquals("foo", this.keyModel.getValue());
		assertEquals(this.valueModels.get("foo"), this.valueModelModel.getValue());
		assertNull(this.testModel.getValue());
	}

	public void testPropertyChange_directSet() {
		this.testModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.testModelListener);

		this.keyModel.setValue("foo");
		this.verifyEvent(this.testModelEvent, this.testModel, null, "foofoo");

		this.testModelEvent = null;
		this.getTestModel().setValue("bar");
		this.verifyEvent(this.testModelEvent, this.testModel, "foofoo", "bar");

		this.testModelEvent = null;
		this.getTestModel().setValue(null);
		this.verifyEvent(this.testModelEvent, this.testModel, "bar", null);

		this.testModelEvent = null;
		this.getTestModel().setValue("foo");
		this.verifyEvent(this.testModelEvent, this.testModel, null, "foo");
	}
}
