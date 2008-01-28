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

import javax.swing.ButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.RadioButtonModelAdapter;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class RadioButtonModelAdapterTests extends TestCase {
	private WritablePropertyValueModel<Object> valueHolder;

	private ButtonModel redButtonModelAdapter;
	private ChangeListener redListener;
	boolean redEventFired;

	private ButtonModel greenButtonModelAdapter;
	private ChangeListener greenListener;
	boolean greenEventFired;

	private ButtonModel blueButtonModelAdapter;
	private ChangeListener blueListener;
	boolean blueEventFired;

//	private ButtonGroup buttonGroup;	// DO NOT use a ButtonGroup

	private static final String RED = "red";
	private static final String GREEN = "green";
	private static final String BLUE = "blue";

	public RadioButtonModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.valueHolder = new SimplePropertyValueModel<Object>(null);
//		buttonGroup = new ButtonGroup();

		this.redButtonModelAdapter = this.buildButtonModel(this.valueHolder, RED);
//		this.redButtonModelAdapter.setGroup(buttonGroup);
		this.redListener = new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				RadioButtonModelAdapterTests.this.redEventFired = true;
			}
		};

		this.greenButtonModelAdapter = this.buildButtonModel(this.valueHolder, GREEN);
//		this.greenButtonModelAdapter.setGroup(buttonGroup);
		this.greenListener = new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				RadioButtonModelAdapterTests.this.greenEventFired = true;
			}
		};

		this.blueButtonModelAdapter = this.buildButtonModel(this.valueHolder, BLUE);
//		this.blueButtonModelAdapter.setGroup(buttonGroup);
		this.blueListener = new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				RadioButtonModelAdapterTests.this.blueEventFired = true;
			}
		};
		
		this.clearFlags();
	}

	private ButtonModel buildButtonModel(WritablePropertyValueModel<Object> pvm, Object buttonValue) {
		return new RadioButtonModelAdapter(pvm, buttonValue) {
			@Override
			protected PropertyChangeListener buildBooleanChangeListener() {
				return this.buildBooleanChangeListener_();
			}
		};
	}

	private void listenToModelAdapters() {
		this.redButtonModelAdapter.addChangeListener(this.redListener);
		this.greenButtonModelAdapter.addChangeListener(this.greenListener);
		this.blueButtonModelAdapter.addChangeListener(this.blueListener);
	}

	private void clearFlags() {
		this.redEventFired = false;
		this.greenEventFired = false;
		this.blueEventFired = false;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSetSelected() throws Exception {
		this.listenToModelAdapters();

		this.greenButtonModelAdapter.setSelected(true);
		assertFalse(this.redEventFired);
		assertTrue(this.greenEventFired);
		assertFalse(this.blueEventFired);
		assertEquals(GREEN, this.valueHolder.value());

		this.clearFlags();
		this.blueButtonModelAdapter.setSelected(true);
		assertFalse(this.redEventFired);
		assertTrue(this.greenEventFired);
		assertTrue(this.blueEventFired);
		assertEquals(BLUE, this.valueHolder.value());

		this.clearFlags();
		this.redButtonModelAdapter.setSelected(true);
		assertTrue(this.redEventFired);
		assertFalse(this.greenEventFired);
		assertTrue(this.blueEventFired);
		assertEquals(RED, this.valueHolder.value());
	}

	public void testSetValue() throws Exception {
		this.listenToModelAdapters();

		this.greenButtonModelAdapter.setSelected(true);

		this.clearFlags();
		this.valueHolder.setValue(BLUE);
		assertFalse(this.redEventFired);
		assertTrue(this.greenEventFired);
		assertTrue(this.blueEventFired);
		assertFalse(this.redButtonModelAdapter.isSelected());
		assertFalse(this.greenButtonModelAdapter.isSelected());
		assertTrue(this.blueButtonModelAdapter.isSelected());

		this.clearFlags();
		this.valueHolder.setValue(RED);
		assertTrue(this.redEventFired);
		assertFalse(this.greenEventFired);
		assertTrue(this.blueEventFired);
		assertTrue(this.redButtonModelAdapter.isSelected());
		assertFalse(this.greenButtonModelAdapter.isSelected());
		assertFalse(this.blueButtonModelAdapter.isSelected());
	}

	public void testDefaultValue() throws Exception {
		this.listenToModelAdapters();

		this.valueHolder.setValue(GREEN);
		assertFalse(this.redButtonModelAdapter.isSelected());
		assertTrue(this.greenButtonModelAdapter.isSelected());
		assertFalse(this.blueButtonModelAdapter.isSelected());

		this.clearFlags();
		this.valueHolder.setValue(null);
		assertFalse(this.redEventFired);
		assertTrue(this.greenEventFired);
		assertFalse(this.blueEventFired);
		assertFalse(this.redButtonModelAdapter.isSelected());
		assertFalse(this.greenButtonModelAdapter.isSelected());
		assertFalse(this.blueButtonModelAdapter.isSelected());

		this.clearFlags();
		this.valueHolder.setValue(BLUE);
		assertFalse(this.redEventFired);
		assertFalse(this.greenEventFired);
		assertTrue(this.blueEventFired);
		assertFalse(this.redButtonModelAdapter.isSelected());
		assertFalse(this.greenButtonModelAdapter.isSelected());
		assertTrue(this.blueButtonModelAdapter.isSelected());
	}

	public void testHasListeners() throws Exception {
		SimplePropertyValueModel<Object> localValueHolder = (SimplePropertyValueModel<Object>) this.valueHolder;
		assertFalse(localValueHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasNoListeners(this.redButtonModelAdapter);
		this.verifyHasNoListeners(this.greenButtonModelAdapter);
		this.verifyHasNoListeners(this.blueButtonModelAdapter);

		ChangeListener listener = new TestChangeListener();
		this.redButtonModelAdapter.addChangeListener(listener);
		assertTrue(localValueHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasListeners(this.redButtonModelAdapter);
		this.verifyHasNoListeners(this.greenButtonModelAdapter);
		this.verifyHasNoListeners(this.blueButtonModelAdapter);

		this.redButtonModelAdapter.removeChangeListener(listener);
		assertFalse(localValueHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasNoListeners(this.redButtonModelAdapter);
		this.verifyHasNoListeners(this.greenButtonModelAdapter);
		this.verifyHasNoListeners(this.blueButtonModelAdapter);
	}

	private void verifyHasNoListeners(Object model) throws Exception {
		EventListenerList listenerList = (EventListenerList) ClassTools.fieldValue(model, "listenerList");
		assertEquals(0, listenerList.getListenerList().length);
	}

	private void verifyHasListeners(Object model) throws Exception {
		EventListenerList listenerList = (EventListenerList) ClassTools.fieldValue(model, "listenerList");
		assertFalse(listenerList.getListenerList().length == 0);
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
