/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value.swing;

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ListSpinnerModelAdapter;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class ListSpinnerModelAdapterTests extends TestCase {
	private PropertyValueModel valueHolder;
	private SpinnerModel spinnerModelAdapter;
	boolean eventFired;
	private static final String[] VALUE_LIST = {"red", "green", "blue"};
	private static final String DEFAULT_VALUE = VALUE_LIST[0];

	public ListSpinnerModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.valueHolder = new SimplePropertyValueModel(DEFAULT_VALUE);
		this.spinnerModelAdapter = new ListSpinnerModelAdapter(this.valueHolder, VALUE_LIST);
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
				ListSpinnerModelAdapterTests.this.eventFired = true;
			}
		});
		assertEquals(DEFAULT_VALUE, this.valueHolder.getValue());
		this.spinnerModelAdapter.setValue(VALUE_LIST[2]);
		assertTrue(this.eventFired);
		assertEquals(VALUE_LIST[2], this.valueHolder.getValue());
	}

	public void testSetValueValueHolder() throws Exception {
		this.eventFired = false;
		this.spinnerModelAdapter.addChangeListener(new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				ListSpinnerModelAdapterTests.this.eventFired = true;
			}
		});
		assertEquals(DEFAULT_VALUE, this.spinnerModelAdapter.getValue());
		this.valueHolder.setValue(VALUE_LIST[2]);
		assertTrue(this.eventFired);
		assertEquals(VALUE_LIST[2], this.spinnerModelAdapter.getValue());
	}

	public void testDefaultValue() throws Exception {
		this.eventFired = false;
		this.spinnerModelAdapter.addChangeListener(new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				ListSpinnerModelAdapterTests.this.eventFired = true;
			}
		});
		assertEquals(DEFAULT_VALUE, this.spinnerModelAdapter.getValue());

		this.valueHolder.setValue(VALUE_LIST[2]);
		assertTrue(this.eventFired);
		assertEquals(VALUE_LIST[2], this.spinnerModelAdapter.getValue());

		this.eventFired = false;
		this.valueHolder.setValue(null);
		assertTrue(this.eventFired);
		assertEquals(VALUE_LIST[0], this.spinnerModelAdapter.getValue());
	}

	public void testHasListeners() throws Exception {
		SimplePropertyValueModel localValueHolder = (SimplePropertyValueModel) this.valueHolder;
		assertFalse(localValueHolder.hasAnyPropertyChangeListeners(ValueModel.VALUE));
		this.verifyHasNoListeners(this.spinnerModelAdapter);

		ChangeListener listener = new TestChangeListener();
		this.spinnerModelAdapter.addChangeListener(listener);
		assertTrue(localValueHolder.hasAnyPropertyChangeListeners(ValueModel.VALUE));
		this.verifyHasListeners(this.spinnerModelAdapter);

		this.spinnerModelAdapter.removeChangeListener(listener);
		assertFalse(localValueHolder.hasAnyPropertyChangeListeners(ValueModel.VALUE));
		this.verifyHasNoListeners(this.spinnerModelAdapter);
	}

	private void verifyHasNoListeners(SpinnerModel adapter) throws Exception {
		assertEquals(0, ((ListSpinnerModelAdapter) adapter).getChangeListeners().length);
	}

	private void verifyHasListeners(Object adapter) throws Exception {
		assertFalse(((ListSpinnerModelAdapter) adapter).getChangeListeners().length == 0);
	}


	private class TestChangeListener implements ChangeListener {
		TestChangeListener() {
			super();
		}
		public void stateChanged(ChangeEvent e) {
			fail("unexpected event");
		}
	}

}
