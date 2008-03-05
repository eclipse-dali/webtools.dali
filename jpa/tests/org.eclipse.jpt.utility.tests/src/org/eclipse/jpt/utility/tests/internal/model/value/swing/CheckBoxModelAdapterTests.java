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
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.CheckBoxModelAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class CheckBoxModelAdapterTests extends TestCase {
	private WritablePropertyValueModel<Boolean> booleanHolder;
	private ButtonModel buttonModelAdapter;
	boolean eventFired;

	public CheckBoxModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.booleanHolder = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.buttonModelAdapter = new CheckBoxModelAdapter(this.booleanHolder) {
			@Override
			protected PropertyChangeListener buildBooleanChangeListener() {
				return this.buildBooleanChangeListener_();
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSetSelected() throws Exception {
		this.eventFired = false;
		this.buttonModelAdapter.addChangeListener(new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				CheckBoxModelAdapterTests.this.eventFired = true;
			}
		});
		this.buttonModelAdapter.setSelected(false);
		assertTrue(this.eventFired);
		assertEquals(Boolean.FALSE, this.booleanHolder.value());
	}

	public void testSetValue() throws Exception {
		this.eventFired = false;
		this.buttonModelAdapter.addChangeListener(new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				CheckBoxModelAdapterTests.this.eventFired = true;
			}
		});
		assertTrue(this.buttonModelAdapter.isSelected());
		this.booleanHolder.setValue(Boolean.FALSE);
		assertTrue(this.eventFired);
		assertFalse(this.buttonModelAdapter.isSelected());
	}

	public void testDefaultValue() throws Exception {
		this.eventFired = false;
		this.buttonModelAdapter.addChangeListener(new TestChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				CheckBoxModelAdapterTests.this.eventFired = true;
			}
		});
		assertTrue(this.buttonModelAdapter.isSelected());
		this.booleanHolder.setValue(null);
		assertTrue(this.eventFired);
		assertFalse(this.buttonModelAdapter.isSelected());

		this.eventFired = false;
		this.booleanHolder.setValue(Boolean.FALSE);
		assertFalse(this.eventFired);
		assertFalse(this.buttonModelAdapter.isSelected());
	}

	public void testHasListeners() throws Exception {
		SimplePropertyValueModel<Boolean> localBooleanHolder = (SimplePropertyValueModel<Boolean>) this.booleanHolder;
		assertFalse(localBooleanHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasNoListeners(this.buttonModelAdapter);

		ChangeListener listener = new TestChangeListener();
		this.buttonModelAdapter.addChangeListener(listener);
		assertTrue(localBooleanHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasListeners(this.buttonModelAdapter);

		this.buttonModelAdapter.removeChangeListener(listener);
		assertFalse(localBooleanHolder.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.verifyHasNoListeners(this.buttonModelAdapter);
	}

	private void verifyHasNoListeners(Object model) throws Exception {
		EventListenerList listenerList = (EventListenerList) ClassTools.fieldValue(model, "listenerList");
		assertEquals(0, listenerList.getListenerList().length);
	}

	private void verifyHasListeners(Object model) throws Exception {
		EventListenerList listenerList = (EventListenerList) ClassTools.fieldValue(model, "listenerList");
		assertFalse(listenerList.getListenerList().length == 0);
	}


	// ********** member class **********
	private class TestChangeListener implements ChangeListener {
		TestChangeListener() {
			super();
		}
		public void stateChanged(ChangeEvent e) {
			fail("unexpected event");
		}
	}

}
