/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value.swing;

import java.util.Date;

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.DateSpinnerModelAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class DateSpinnerModelAdapterTests extends TestCase {
	private WritablePropertyValueModel<Object> valueHolder;
	private SpinnerModel spinnerModelAdapter;
	boolean eventFired;

	public DateSpinnerModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.valueHolder = new SimplePropertyValueModel<Object>(new Date());
		this.spinnerModelAdapter = new DateSpinnerModelAdapter(this.valueHolder) {
			@Override
			protected PropertyChangeListener buildDateChangeListener() {
				return this.buildDateChangeListener_();
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSetValueSpinnerModel() throws Exception {
		this.eventFired = false;
		this.spinnerModelAdapter.addChangeListener(new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				DateSpinnerModelAdapterTests.this.eventFired = true;
			}
		});
		Date newDate = new Date();
		newDate.setTime(777777);
		this.spinnerModelAdapter.setValue(newDate);
		assertTrue(this.eventFired);
		assertEquals(777777, ((Date) this.valueHolder.value()).getTime());
	}

	public void testSetValueValueHolder() throws Exception {
		this.eventFired = false;
		this.spinnerModelAdapter.addChangeListener(new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				DateSpinnerModelAdapterTests.this.eventFired = true;
			}
		});
		Date newDate = new Date();
		newDate.setTime(777777);
		this.valueHolder.setValue(newDate);
		assertTrue(this.eventFired);
		assertEquals(777777, ((Date) this.spinnerModelAdapter.getValue()).getTime());
	}

	public void testDefaultValue() throws Exception {
		Date newDate = new Date();
		newDate.setTime(777777);
		this.valueHolder.setValue(newDate);
		this.eventFired = false;
		this.spinnerModelAdapter.addChangeListener(new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				DateSpinnerModelAdapterTests.this.eventFired = true;
			}
		});
		assertEquals(777777, ((Date) this.spinnerModelAdapter.getValue()).getTime());
		this.valueHolder.setValue(null);
		assertTrue(this.eventFired);
		assertFalse(((Date) this.spinnerModelAdapter.getValue()).getTime() == 777777);
	}

	public void testHasListeners() throws Exception {
		SimplePropertyValueModel<Object> localValueHolder = (SimplePropertyValueModel<Object>) this.valueHolder;
		assertFalse(localValueHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasNoListeners(this.spinnerModelAdapter);

		ChangeListener listener = new TestChangeListener();
		this.spinnerModelAdapter.addChangeListener(listener);
		assertTrue(localValueHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasListeners(this.spinnerModelAdapter);

		this.spinnerModelAdapter.removeChangeListener(listener);
		assertFalse(localValueHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasNoListeners(this.spinnerModelAdapter);
	}

	private void verifyHasNoListeners(SpinnerModel adapter) throws Exception {
		assertEquals(0, ((DateSpinnerModelAdapter) adapter).getChangeListeners().length);
	}

	private void verifyHasListeners(Object adapter) throws Exception {
		assertFalse(((DateSpinnerModelAdapter) adapter).getChangeListeners().length == 0);
	}

	public void testNullInitialValue() {
		Date today = new Date();
		this.valueHolder = new SimplePropertyValueModel<Object>();
		this.spinnerModelAdapter = new DateSpinnerModelAdapter(this.valueHolder, today) {
			@Override
			protected PropertyChangeListener buildDateChangeListener() {
				return this.buildDateChangeListener_();
			}
		};

		this.eventFired = false;
		this.spinnerModelAdapter.addChangeListener(new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				DateSpinnerModelAdapterTests.this.eventFired = true;
			}
		});
		assertEquals(today, this.spinnerModelAdapter.getValue());

		Date newDate = new Date();
		newDate.setTime(777777);
		this.valueHolder.setValue(newDate);

		assertTrue(this.eventFired);
		assertEquals(777777, ((Date) this.spinnerModelAdapter.getValue()).getTime());
	}


	// ********** inner class **********
	private class TestChangeListener implements ChangeListener {
		TestChangeListener() {
			super();
		}
		public void stateChanged(ChangeEvent e) {
			fail("unexpected event");
		}
	}

}
