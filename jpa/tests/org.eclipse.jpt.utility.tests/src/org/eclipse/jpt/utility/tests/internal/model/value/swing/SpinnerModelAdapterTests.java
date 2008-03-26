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

import javax.swing.SpinnerModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.SpinnerModelAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class SpinnerModelAdapterTests extends TestCase {
	private WritablePropertyValueModel<Object> valueHolder;
	SpinnerModel spinnerModelAdapter;
	boolean eventFired;

	public SpinnerModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.valueHolder = new SimplePropertyValueModel<Object>(new Integer(0));
		this.spinnerModelAdapter = new SpinnerModelAdapter(this.valueHolder) {
			@Override
			protected PropertyChangeListener buildValueListener() {
				return this.buildValueListener_();
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
				SpinnerModelAdapterTests.this.eventFired = true;
				assertEquals(SpinnerModelAdapterTests.this.spinnerModelAdapter, e.getSource());
			}
		});
		this.spinnerModelAdapter.setValue(new Integer(5));
		assertTrue(this.eventFired);
		assertEquals(new Integer(5), this.valueHolder.getValue());
	}

	public void testSetValueValueHolder() throws Exception {
		this.eventFired = false;
		this.spinnerModelAdapter.addChangeListener(new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				SpinnerModelAdapterTests.this.eventFired = true;
				assertEquals(SpinnerModelAdapterTests.this.spinnerModelAdapter, e.getSource());
			}
		});
		assertEquals(new Integer(0), this.spinnerModelAdapter.getValue());
		this.valueHolder.setValue(new Integer(7));
		assertTrue(this.eventFired);
		assertEquals(new Integer(7), this.spinnerModelAdapter.getValue());
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

	private void verifyHasNoListeners(Object adapter) throws Exception {
		Object delegate = ClassTools.fieldValue(adapter, "delegate");
		Object[] listeners = (Object[]) ClassTools.executeMethod(delegate, "getChangeListeners");
		assertEquals(0, listeners.length);
	}

	private void verifyHasListeners(Object adapter) throws Exception {
		Object delegate = ClassTools.fieldValue(adapter, "delegate");
		Object[] listeners = (Object[]) ClassTools.executeMethod(delegate, "getChangeListeners");
		assertFalse(listeners.length == 0);
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
