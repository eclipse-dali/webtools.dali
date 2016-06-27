/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.swing;

import javax.swing.ButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.CheckBoxModelAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class CheckBoxModelAdapterTests extends TestCase {
	private ModifiablePropertyValueModel<Boolean> booleanModel;
	private ButtonModel buttonModelAdapter;
	boolean eventFired;

	public CheckBoxModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.booleanModel = new SimplePropertyValueModel<>(Boolean.TRUE);
		this.buttonModelAdapter = new TestCheckBoxModelAdapter(this.booleanModel);
	}

	public static class TestCheckBoxModelAdapter
		extends CheckBoxModelAdapter
	{
		private static final long serialVersionUID = 1L;
		public TestCheckBoxModelAdapter(ModifiablePropertyValueModel<Boolean> booleanModel) {
			super(booleanModel);
		}
		@Override
		protected PropertyChangeListener buildBooleanChangeListener() {
			return this.buildBooleanChangeListener_();
		}
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
		assertEquals(Boolean.FALSE, this.booleanModel.getValue());
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
		this.booleanModel.setValue(Boolean.FALSE);
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
		this.booleanModel.setValue(null);
		assertTrue(this.eventFired);
		assertFalse(this.buttonModelAdapter.isSelected());

		this.eventFired = false;
		this.booleanModel.setValue(Boolean.FALSE);
		assertFalse(this.eventFired);
		assertFalse(this.buttonModelAdapter.isSelected());
	}

	public void testHasListeners() throws Exception {
		SimplePropertyValueModel<Boolean> localBooleanHolder = (SimplePropertyValueModel<Boolean>) this.booleanModel;
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
		EventListenerList listenerList = (EventListenerList) ObjectTools.get(model, "listenerList");
		assertEquals(0, listenerList.getListenerList().length);
	}

	private void verifyHasListeners(Object model) throws Exception {
		EventListenerList listenerList = (EventListenerList) ObjectTools.get(model, "listenerList");
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
