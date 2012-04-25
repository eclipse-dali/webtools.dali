/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationWritablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class TransformationPropertyValueModelTests
	extends TestCase
{
	private ModifiablePropertyValueModel<String> objectHolder;
	PropertyChangeEvent event;

	private ModifiablePropertyValueModel<String> transformationObjectHolder;
	PropertyChangeEvent transformationEvent;

	public TransformationPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.objectHolder = new SimplePropertyValueModel<String>("foo");
		this.transformationObjectHolder = new TransformationWritablePropertyValueModel<String, String>(this.objectHolder, this.buildTransformer(), this.buildReverseTransformer());
	}

	private Transformer<String, String> buildTransformer() {
		return new Transformer<String, String>() {
			public String transform(String s) {
				return (s == null) ? null : s.toUpperCase();
			}
		};
	}

	private Transformer<String, String> buildReverseTransformer() {
		return new Transformer<String, String>() {
			public String transform(String s) {
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
		assertEquals("foo", this.objectHolder.getValue());
		assertNull(this.transformationObjectHolder.getValue());
		ChangeListener listener = this.buildTransformationListener();
		this.transformationObjectHolder.addChangeListener(listener);
		assertEquals("FOO", this.transformationObjectHolder.getValue());

		this.objectHolder.setValue("bar");
		assertEquals("bar", this.objectHolder.getValue());
		assertEquals("BAR", this.transformationObjectHolder.getValue());

		this.objectHolder.setValue("baz");
		assertEquals("baz", this.objectHolder.getValue());
		assertEquals("BAZ", this.transformationObjectHolder.getValue());

		this.objectHolder.setValue(null);
		assertNull(this.objectHolder.getValue());
		assertNull(this.transformationObjectHolder.getValue());

		this.objectHolder.setValue("foo");
		assertEquals("foo", this.objectHolder.getValue());
		assertEquals("FOO", this.transformationObjectHolder.getValue());
	}

	public void testSetValue() {
		this.transformationObjectHolder.setValue("BAR");
		assertEquals("bar", this.objectHolder.getValue());
		assertEquals("BAR", this.transformationObjectHolder.getValue());

		// NB: odd behavior(!)
		this.transformationObjectHolder.setValue("Foo");
		assertEquals("foo", this.objectHolder.getValue());
		assertEquals("Foo", this.transformationObjectHolder.getValue());
		ChangeListener listener = this.buildTransformationListener();
		this.transformationObjectHolder.addChangeListener(listener);
		assertEquals("FOO", this.transformationObjectHolder.getValue());
		this.transformationObjectHolder.removeChangeListener(listener);

		this.transformationObjectHolder.setValue(null);
		assertNull(this.objectHolder.getValue());
		assertNull(this.transformationObjectHolder.getValue());

		// NB: odd behavior(!)
		this.transformationObjectHolder.setValue("baz");
		assertEquals("baz", this.objectHolder.getValue());
		assertEquals("baz", this.transformationObjectHolder.getValue());
		this.transformationObjectHolder.addChangeListener(listener);
		assertEquals("BAZ", this.transformationObjectHolder.getValue());
		this.transformationObjectHolder.removeChangeListener(listener);
	}

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		ChangeListener listener = this.buildTransformationListener();
		this.transformationObjectHolder.addChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.transformationObjectHolder.removeChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.transformationObjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.transformationObjectHolder.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testPropertyChange1() {
		this.objectHolder.addChangeListener(this.buildListener());
		this.transformationObjectHolder.addChangeListener(this.buildTransformationListener());
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

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				TransformationPropertyValueModelTests.this.event = e;
			}
		};
	}

	private ChangeListener buildTransformationListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				TransformationPropertyValueModelTests.this.transformationEvent = e;
			}
		};
	}

	private void verifyEvent(PropertyChangeEvent e, Object source, Object oldValue, Object newValue) {
		assertEquals(source, e.getSource());
		assertEquals(PropertyValueModel.VALUE, e.getPropertyName());
		assertEquals(oldValue, e.getOldValue());
		assertEquals(newValue, e.getNewValue());
	}
}