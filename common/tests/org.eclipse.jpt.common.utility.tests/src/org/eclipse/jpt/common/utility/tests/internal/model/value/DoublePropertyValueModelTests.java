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

import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class DoublePropertyValueModelTests
	extends TestCase
{
	protected ModifiablePropertyValueModel<String> stringModel;
	protected ChangeListener stringModelListener;
	protected PropertyChangeEvent stringModelEvent;

	protected ModifiablePropertyValueModel<ModifiablePropertyValueModel<String>> stringModelModel;
	protected ChangeListener stringModelModelListener;
	protected PropertyChangeEvent stringModelModelEvent;

	protected PropertyValueModel<String> doubleModel;
	protected ChangeListener doubleModelListener;
	protected PropertyChangeEvent doubleModelEvent;

	public DoublePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.stringModel = new SimplePropertyValueModel<>("foo");
		this.stringModelListener = new StringModelListener();

		this.stringModelModel = new SimplePropertyValueModel<>(this.stringModel);
		this.stringModelModelListener = new StringModelModelListener();

		this.doubleModel = this.buildDoubleModel(this.stringModelModel);
		this.doubleModelListener = new DoubleModelListener();
	}

	protected PropertyValueModel<String> buildDoubleModel(ModifiablePropertyValueModel<ModifiablePropertyValueModel<String>> modelModel) {
		return PropertyValueModelTools.doubleWrap(modelModel);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetValue1() {
		assertEquals("foo", this.stringModel.getValue());
		assertEquals(this.stringModel, this.stringModelModel.getValue());
		assertNull(this.doubleModel.getValue());
		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		assertEquals("foo", this.doubleModel.getValue());

		this.stringModel.setValue("bar");
		assertEquals("bar", this.stringModel.getValue());
		assertEquals("bar", this.doubleModel.getValue());

		this.stringModel.setValue("baz");
		assertEquals("baz", this.stringModel.getValue());
		assertEquals("baz", this.doubleModel.getValue());

		this.stringModel.setValue(null);
		assertNull(this.stringModel.getValue());
		assertNull(this.doubleModel.getValue());

		this.stringModel.setValue("foo");
		assertEquals("foo", this.stringModel.getValue());
		assertEquals("foo", this.doubleModel.getValue());
	}

	public void testGetValue2() {
		assertNull(this.doubleModel.getValue());
		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		assertEquals("foo", this.doubleModel.getValue());

		this.stringModelModel.setValue(null);
		assertNull(this.doubleModel.getValue());

		ModifiablePropertyValueModel<String> stringModel2 = new SimplePropertyValueModel<>("TTT");
		this.stringModelModel.setValue(stringModel2);
		assertEquals("TTT", this.doubleModel.getValue());

		this.stringModelModel.setValue(this.stringModel);
		assertEquals("foo", this.doubleModel.getValue());
	}

	public void testLazyListening1() {
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		this.doubleModel.addChangeListener(this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.doubleModel.removeChangeListener(this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.doubleModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		ModifiablePropertyValueModel<String> stringModel2 = new SimplePropertyValueModel<>("TTT");
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) stringModel2).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		this.stringModelModel.setValue(stringModel2);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) stringModel2).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.stringModelModel.setValue(this.stringModel);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) stringModel2).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testLazyListening2() {
		ChangeListener doubleModelListener2 = new ChangeAdapter();
		PropertyChangeListener doubleModelListener3 = new ChangeAdapter();

		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		this.doubleModel.addChangeListener(this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.doubleModel.addChangeListener(doubleModelListener2);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, doubleModelListener3);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.doubleModel.removeChangeListener(doubleModelListener2);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.doubleModel.removePropertyChangeListener(PropertyValueModel.VALUE, doubleModelListener3);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.doubleModel.removeChangeListener(this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.doubleModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		ModifiablePropertyValueModel<String> stringModel2 = new SimplePropertyValueModel<>("TTT");
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) stringModel2).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		this.stringModelModel.setValue(stringModel2);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) stringModel2).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.stringModelModel.setValue(this.stringModel);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) stringModel2).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testLazyListening3() {
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.stringModelModel.setValue(null);
		this.doubleModel.addChangeListener(this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		this.stringModelModel.setValue(this.stringModel);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		this.stringModelModel.setValue(null);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		this.doubleModel.removeChangeListener(this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));


		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		this.stringModelModel.setValue(this.stringModel);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		this.stringModelModel.setValue(null);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		this.doubleModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.stringModelModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testPropertyChange1() {
		this.stringModel.addChangeListener(this.stringModelListener);
		this.stringModelModel.addChangeListener(this.stringModelModelListener);
		this.doubleModel.addChangeListener(this.doubleModelListener);
		this.verifyPropertyChanges1();
	}

	public void testPropertyChange2() {
		this.stringModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.stringModelListener);
		this.stringModelModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.stringModelModelListener);
		this.doubleModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.doubleModelListener);
		this.verifyPropertyChanges1();
	}

	public void testToString() {
		this.stringModel.addChangeListener(this.stringModelListener);
		this.stringModelModel.addChangeListener(this.stringModelModelListener);
		this.doubleModel.addChangeListener(this.doubleModelListener);
		assertFalse(this.doubleModel.toString().indexOf("foo") == -1);
	}

	public void testConstructor_NPE() {
		boolean exCaught = false;
		try {
			this.doubleModel = this.buildDoubleModel(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	protected void verifyPropertyChanges1() {
		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		this.stringModel.setValue("bar");
		this.verifyEvent(this.stringModelEvent, this.stringModel, "foo", "bar");
		assertNull(this.stringModelModelEvent);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, "foo", "bar");

		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		this.stringModel.setValue(null);
		this.verifyEvent(this.stringModelEvent, this.stringModel, "bar", null);
		assertNull(this.stringModelModelEvent);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, "bar", null);

		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		this.stringModel.setValue("foo");
		this.verifyEvent(this.stringModelEvent, this.stringModel, null, "foo");
		assertNull(this.stringModelModelEvent);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, null, "foo");

		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		ModifiablePropertyValueModel<String> stringModel2 = new SimplePropertyValueModel<>("TTT");
		this.stringModelModel.setValue(stringModel2);
		assertNull(this.stringModelEvent);
		this.verifyEvent(this.stringModelModelEvent, this.stringModelModel, this.stringModel, stringModel2);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, "foo", "TTT");

		this.stringModelEvent = null;
		this.stringModelModelEvent = null;
		this.doubleModelEvent = null;
		this.stringModelModel.setValue(this.stringModel);
		assertNull(this.stringModelEvent);
		this.verifyEvent(this.stringModelModelEvent, this.stringModelModel, stringModel2, this.stringModel);
		this.verifyEvent(this.doubleModelEvent, this.doubleModel, "TTT", "foo");
	}

	protected void verifyEvent(PropertyChangeEvent event, Object source, Object oldValue, Object newValue) {
		assertEquals(source, event.getSource());
		assertEquals(PropertyValueModel.VALUE, event.getPropertyName());
		assertEquals(oldValue, event.getOldValue());
		assertEquals(newValue, event.getNewValue());
	}

	protected class StringModelListener
		extends ChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			DoublePropertyValueModelTests.this.stringModelEvent = event;
		}
	}

	protected class StringModelModelListener
		extends ChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			DoublePropertyValueModelTests.this.stringModelModelEvent = event;
		}
	}

	protected class DoubleModelListener
		extends ChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			DoublePropertyValueModelTests.this.doubleModelEvent = event;
		}
	}
}
