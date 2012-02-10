/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import org.eclipse.jpt.common.utility.internal.model.value.DoubleModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;

@SuppressWarnings("nls")
public class DoubleModifiablePropertyValueModelTests
	extends DoublePropertyValueModelTests
{
	public DoubleModifiablePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected PropertyValueModel<String> buildDoubleModel() {
		return new DoubleModifiablePropertyValueModel<String>(this.stringModelModel);
	}

	protected WritablePropertyValueModel<String> getDoubleModel() {
		return (WritablePropertyValueModel<String>) this.doubleModel;
	}

	public void testSetValue() {
		assertEquals("foo", this.stringModel.getValue());
		assertEquals(this.stringModel, this.stringModelModel.getValue());
		assertNull(this.doubleModel.getValue());
		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		assertEquals("foo", this.doubleModel.getValue());

		this.getDoubleModel().setValue("bar");
		assertEquals("bar", this.stringModel.getValue());
		assertEquals("bar", this.doubleModel.getValue());

		this.stringModelModel.setValue(null);
		assertNull(this.doubleModel.getValue());
		this.getDoubleModel().setValue("TTT");  // NOP?
		assertEquals("bar", this.stringModel.getValue());
		assertNull(this.doubleModel.getValue());
	}

	public void testPropertyChange3() {
		this.stringModel.addChangeListener(this.stringModelListener);
		this.stringModelModel.addChangeListener(this.stringModelModelListener);
		this.doubleModel.addChangeListener(this.doubleModelListener);
		this.verifyPropertyChanges2();
	}

	public void testPropertyChange4() {
		this.stringModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.stringModelListener);
		this.stringModelModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.stringModelModelListener);
		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		this.verifyPropertyChanges2();
	}

	protected void verifyPropertyChanges2() {
		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		this.getDoubleModel().setValue("bar");
		this.verifyEvent(this.stringModelEvent, this.stringModel, "foo", "bar");
		assertNull(this.stringModelModelEvent);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, "foo", "bar");

		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		this.getDoubleModel().setValue(null);
		this.verifyEvent(this.stringModelEvent, this.stringModel, "bar", null);
		assertNull(this.stringModelModelEvent);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, "bar", null);

		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		this.getDoubleModel().setValue("foo");
		this.verifyEvent(this.stringModelEvent, this.stringModel, null, "foo");
		assertNull(this.stringModelModelEvent);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, null, "foo");

		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		WritablePropertyValueModel<String> stringModel2 = new SimplePropertyValueModel<String>("TTT");
		this.stringModelModel.setValue(stringModel2);
		assertNull(this.stringModelEvent);
		this.verifyEvent(this.stringModelModelEvent, this.stringModelModel, this.stringModel, stringModel2);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, "foo", "TTT");

		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		this.getDoubleModel().setValue("XXX");
		assertNull(this.stringModelEvent);
		assertNull(this.stringModelModelEvent);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, "TTT", "XXX");
		assertEquals("foo", this.stringModel.getValue());
		assertEquals("XXX", stringModel2.getValue());

		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		this.stringModelModel.setValue(this.stringModel);
		assertNull(this.stringModelEvent);
		this.verifyEvent(this.stringModelModelEvent, this.stringModelModel, stringModel2, this.stringModel);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, "XXX", "foo");
	}
}
