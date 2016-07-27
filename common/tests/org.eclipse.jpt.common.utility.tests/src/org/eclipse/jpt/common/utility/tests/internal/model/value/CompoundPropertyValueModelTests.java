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

import java.util.HashMap;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class CompoundPropertyValueModelTests
	extends TestCase
{
	protected SimplePropertyValueModel<String> keyModel;
	protected HashMap<String, SimplePropertyValueModel<String>> valueModels;
	protected PropertyValueModel<SimplePropertyValueModel<String>> valueModelModel;

	protected PropertyValueModel<String> testModel;
	protected ChangeListener testModelListener;
	protected PropertyChangeEvent testModelEvent;

	public CompoundPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.keyModel = new SimplePropertyValueModel<>(null);
		this.valueModels = new HashMap<>();
		this.valueModelModel = this.buildValueModelModel();

		this.testModel = this.buildTestModel(this.valueModelModel);
		this.testModelListener = new TestModelListener();
	}

	protected PropertyValueModel<SimplePropertyValueModel<String>> buildValueModelModel() {
		return PropertyValueModelTools.transform(this.keyModel, new KeyTransformer());
	}

	public class KeyTransformer
		extends TransformerAdapter<String, SimplePropertyValueModel<String>>
	{
		@Override
		public SimplePropertyValueModel<String> transform(String key) {
			return CompoundPropertyValueModelTests.this.getValueModel(key);
		}
	}

	protected SimplePropertyValueModel<String> getValueModel(String key) {
		SimplePropertyValueModel<String> valueModel = this.valueModels.get(key);
		if (valueModel == null) {
			valueModel = new SimplePropertyValueModel<>(key + key);
			this.valueModels.put(key, valueModel);
		}
		return valueModel;
	}

	protected PropertyValueModel<String> buildTestModel(PropertyValueModel<SimplePropertyValueModel<String>> modelModel) {
		return PropertyValueModelTools.compound(modelModel);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetValue() {
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

		this.keyModel.setValue("bar");
		assertEquals("bar", this.keyModel.getValue());
		assertEquals(this.valueModels.get("bar"), this.valueModelModel.getValue());
		assertEquals("barbar", this.testModel.getValue());

		this.keyModel.setValue("baz");
		assertEquals("baz", this.keyModel.getValue());
		assertEquals(this.valueModels.get("baz"), this.valueModelModel.getValue());
		assertEquals("bazbaz", this.testModel.getValue());

		this.keyModel.setValue(null);
		assertNull(this.keyModel.getValue());
		assertNull(this.valueModelModel.getValue());
		assertNull(this.testModel.getValue());

		this.keyModel.setValue("foo");
		assertEquals("foo", this.keyModel.getValue());
		assertEquals(this.valueModels.get("foo"), this.valueModelModel.getValue());
		assertEquals("foofoo", this.testModel.getValue());

		this.valueModels.get("foo").setValue("XXX");
		assertEquals("foo", this.keyModel.getValue());
		assertEquals(this.valueModels.get("foo"), this.valueModelModel.getValue());
		assertEquals("XXX", this.testModel.getValue());

		this.testModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.testModelListener);

		assertEquals("foo", this.keyModel.getValue()); // simple PVM
		assertNull(this.valueModelModel.getValue());
		assertEquals("XXX", this.testModel.getValue());
	}

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.keyModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.valueModelModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.testModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.testModel.addChangeListener(this.testModelListener);

		assertTrue(((AbstractModel) this.keyModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.valueModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.testModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.testModel.removeChangeListener(this.testModelListener);
		assertTrue(((AbstractModel) this.keyModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.valueModelModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.testModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.testModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.testModelListener);
		assertTrue(((AbstractModel) this.keyModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.testModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.testModelListener);
		assertTrue(((AbstractModel) this.keyModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testPropertyChange() {
		this.testModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.testModelListener);

		this.testModelEvent = null;
		this.keyModel.setValue("foo");
		this.verifyEvent(this.testModelEvent, this.testModel, null, "foofoo");

		this.testModelEvent = null;
		this.keyModel.setValue("bar");
		this.verifyEvent(this.testModelEvent, this.testModel, "foofoo", "barbar");

		this.testModelEvent = null;
		this.keyModel.setValue(null);
		this.verifyEvent(this.testModelEvent, this.testModel, "barbar", null);

		this.testModelEvent = null;
		this.keyModel.setValue("foo");
		this.verifyEvent(this.testModelEvent, this.testModel, null, "foofoo");

		this.testModelEvent = null;
		this.valueModels.get("foo").setValue("XXX");
		this.verifyEvent(this.testModelEvent, this.testModel, "foofoo", "XXX");
	}

	public void testConstructor_NPE() {
		boolean exCaught = false;
		try {
			this.testModel = this.buildTestModel(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	protected void verifyEvent(PropertyChangeEvent event, Object source, Object oldValue, Object newValue) {
		assertNotNull(event);
		assertEquals(source, event.getSource());
		assertEquals(PropertyValueModel.VALUE, event.getPropertyName());
		assertEquals(oldValue, event.getOldValue());
		assertEquals(newValue, event.getNewValue());
	}

	protected class TestModelListener
		extends ChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			CompoundPropertyValueModelTests.this.testModelEvent = event;
		}
	}
}
