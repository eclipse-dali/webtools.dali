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

import org.eclipse.jpt.utility.internal.BidiTransformer;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class TransformationPropertyValueModelTests extends TestCase {
	private WritablePropertyValueModel<String> objectHolder;
	PropertyChangeEvent event;

	private WritablePropertyValueModel<String> transformationObjectHolder;
	PropertyChangeEvent transformationEvent;

	public TransformationPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.objectHolder = new SimplePropertyValueModel<String>("foo");
		this.transformationObjectHolder = new TransformationPropertyValueModel<String, String>(this.objectHolder, this.buildTransformer());
	}

	private BidiTransformer<String, String> buildTransformer() {
		return new BidiTransformer<String, String>() {
			public String transform(String s) {
				return (s == null) ? null : s.toUpperCase();
			}
			public String reverseTransform(String s) {
				return (s == null) ? null : s.toLowerCase();
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		assertEquals("foo", this.objectHolder.value());
		assertEquals("FOO", this.transformationObjectHolder.value());

		this.objectHolder.setValue("bar");
		assertEquals("bar", this.objectHolder.value());
		assertEquals("BAR", this.transformationObjectHolder.value());

		this.objectHolder.setValue("baz");
		assertEquals("baz", this.objectHolder.value());
		assertEquals("BAZ", this.transformationObjectHolder.value());

		this.objectHolder.setValue(null);
		assertNull(this.objectHolder.value());
		assertNull(this.transformationObjectHolder.value());

		this.objectHolder.setValue("foo");
		assertEquals("foo", this.objectHolder.value());
		assertEquals("FOO", this.transformationObjectHolder.value());
	}

	public void testSetValue() {
		this.transformationObjectHolder.setValue("BAR");
		assertEquals("bar", this.objectHolder.value());
		assertEquals("BAR", this.transformationObjectHolder.value());

		this.transformationObjectHolder.setValue("Foo");
		assertEquals("foo", this.objectHolder.value());
		assertEquals("FOO", this.transformationObjectHolder.value());

		this.transformationObjectHolder.setValue(null);
		assertNull(this.objectHolder.value());
		assertNull(this.transformationObjectHolder.value());

		this.transformationObjectHolder.setValue("baz");
		assertEquals("baz", this.objectHolder.value());
		assertEquals("BAZ", this.transformationObjectHolder.value());
	}

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		PropertyChangeListener listener = this.buildTransformationListener();
		this.transformationObjectHolder.addPropertyChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.transformationObjectHolder.removePropertyChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.transformationObjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.transformationObjectHolder.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testPropertyChange1() {
		this.objectHolder.addPropertyChangeListener(this.buildListener());
		this.transformationObjectHolder.addPropertyChangeListener(this.buildTransformationListener());
		this.verifyPropertyChanges();
	}

	public void testPropertyChange2() {
		this.objectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildListener());
		this.transformationObjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildTransformationListener());
		this.verifyPropertyChanges();
	}

	private void verifyPropertyChanges() {
		this.event = null;
		this.transformationEvent = null;
		this.objectHolder.setValue("bar");
		this.verifyEvent(this.event, this.objectHolder, "foo", "bar");
		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, "FOO", "BAR");

		this.event = null;
		this.transformationEvent = null;
		this.objectHolder.setValue("baz");
		this.verifyEvent(this.event, this.objectHolder, "bar", "baz");
		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, "BAR", "BAZ");

		this.event = null;
		this.transformationEvent = null;
		this.objectHolder.setValue("Foo");
		this.verifyEvent(this.event, this.objectHolder, "baz", "Foo");
		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, "BAZ", "FOO");

		this.event = null;
		this.transformationEvent = null;
		this.objectHolder.setValue("FOO");
		this.verifyEvent(this.event, this.objectHolder, "Foo", "FOO");
		assertNull(this.transformationEvent);

		this.event = null;
		this.transformationEvent = null;
		this.objectHolder.setValue(null);
		this.verifyEvent(this.event, this.objectHolder, "FOO", null);
		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, "FOO", null);

		this.event = null;
		this.transformationEvent = null;
		this.objectHolder.setValue("bar");
		this.verifyEvent(this.event, this.objectHolder, null, "bar");
		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, null, "BAR");
	}

	private PropertyChangeListener buildListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				TransformationPropertyValueModelTests.this.event = e;
			}
		};
	}

	private PropertyChangeListener buildTransformationListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				TransformationPropertyValueModelTests.this.transformationEvent = e;
			}
		};
	}

	private void verifyEvent(PropertyChangeEvent e, Object source, Object oldValue, Object newValue) {
		assertEquals(source, e.getSource());
		assertEquals(PropertyValueModel.VALUE, e.propertyName());
		assertEquals(oldValue, e.oldValue());
		assertEquals(newValue, e.newValue());
	}

}
