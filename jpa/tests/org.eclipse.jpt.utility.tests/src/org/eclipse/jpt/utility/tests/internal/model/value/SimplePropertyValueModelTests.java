/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class SimplePropertyValueModelTests extends TestCase {
	private WritablePropertyValueModel<String> objectHolder;
	PropertyChangeEvent event;

	
	public SimplePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.objectHolder = new SimplePropertyValueModel<String>("foo");
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		assertEquals("foo", this.objectHolder.getValue());
	}

	public void testSetValue() {
		this.objectHolder.setValue("bar");
		assertEquals("bar", this.objectHolder.getValue());
		this.objectHolder.setValue(null);
		assertEquals(null, this.objectHolder.getValue());
		this.objectHolder.setValue("baz");
		assertEquals("baz", this.objectHolder.getValue());
	}

	public void testPropertyChange1() {
		this.objectHolder.addChangeListener(this.buildListener());
		this.verifyPropertyChange();
	}

	public void testPropertyChange2() {
		this.objectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildListener());
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

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				SimplePropertyValueModelTests.this.event = e;
			}
		};
	}

	private void verifyEvent(Object oldValue, Object newValue) {
		assertEquals(this.objectHolder, this.event.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event.getPropertyName());
		assertEquals(oldValue, this.event.getOldValue());
		assertEquals(newValue, this.event.getNewValue());
	}

}
