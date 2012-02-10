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

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.DoublePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class DoublePropertyValueModelTests
	extends TestCase
{
	protected WritablePropertyValueModel<String> stringModel;
	protected ChangeListener stringModelListener;
	protected PropertyChangeEvent stringModelEvent;

	protected WritablePropertyValueModel<WritablePropertyValueModel<String>> stringModelModel;
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
		this.stringModel = new SimplePropertyValueModel<String>("foo");
		this.stringModelListener = new StringModelListener();

		this.stringModelModel = new SimplePropertyValueModel<WritablePropertyValueModel<String>>(stringModel);
		this.stringModelModelListener = new StringModelModelListener();

		this.doubleModel = this.buildDoubleModel();
		this.doubleModelListener = new DoubleModelListener();
	}

	protected PropertyValueModel<String> buildDoubleModel() {
		return new DoublePropertyValueModel<String>(this.stringModelModel);
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

		WritablePropertyValueModel<String> stringModel2 = new SimplePropertyValueModel<String>("TTT");
		this.stringModelModel.setValue(stringModel2);
		assertEquals("TTT", this.doubleModel.getValue());

		this.stringModelModel.setValue(this.stringModel);
		assertEquals("foo", this.doubleModel.getValue());
	}

	public void testLazyListening() {
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
		WritablePropertyValueModel<String> stringModel2 = new SimplePropertyValueModel<String>("TTT");
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) stringModel2).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		this.stringModelModel.setValue(stringModel2);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) stringModel2).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.stringModelModel.setValue(this.stringModel);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) stringModel2).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
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
		WritablePropertyValueModel<String> stringModel2 = new SimplePropertyValueModel<String>("TTT");
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
