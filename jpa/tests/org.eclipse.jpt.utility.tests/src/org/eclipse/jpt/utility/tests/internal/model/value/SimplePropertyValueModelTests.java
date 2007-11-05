/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class SimplePropertyValueModelTests extends TestCase {
	private PropertyValueModel objectHolder;
	PropertyChangeEvent event;

	
	public SimplePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.objectHolder = new SimplePropertyValueModel("foo");
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		assertEquals("foo", this.objectHolder.value());
	}

	public void testSetValue() {
		this.objectHolder.setValue("bar");
		assertEquals("bar", this.objectHolder.value());
		this.objectHolder.setValue(null);
		assertEquals(null, this.objectHolder.value());
		this.objectHolder.setValue("baz");
		assertEquals("baz", this.objectHolder.value());
	}

	public void testPropertyChange1() {
		this.objectHolder.addPropertyChangeListener(this.buildListener());
		this.verifyPropertyChange();
	}

	public void testPropertyChange2() {
		this.objectHolder.addPropertyChangeListener(ValueModel.VALUE, this.buildListener());
		this.verifyPropertyChange();
	}

	private void verifyPropertyChange() {
		this.event = null;
		this.objectHolder.setValue("bar");
		this.verifyEvent("foo", "bar");

		this.event = null;
		this.objectHolder.setValue(null);
		this.verifyEvent("bar", null);

		this.event = null;
		this.objectHolder.setValue("baz");
		this.verifyEvent(null, "baz");
	}

	private PropertyChangeListener buildListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				SimplePropertyValueModelTests.this.event = e;
			}
		};
	}

	private void verifyEvent(Object oldValue, Object newValue) {
		assertEquals(this.objectHolder, this.event.getSource());
		assertEquals(ValueModel.VALUE, this.event.propertyName());
		assertEquals(oldValue, this.event.oldValue());
		assertEquals(newValue, this.event.newValue());
	}

}
