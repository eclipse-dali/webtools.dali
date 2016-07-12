/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class TransformationPropertyValueModelTests
	extends TestCase
{
	private ModifiablePropertyValueModel<String> stringModel;
	PropertyChangeEvent stringEvent;

	private ModifiablePropertyValueModel<String> testModel;
	PropertyChangeEvent testEvent;

	public TransformationPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.stringModel = new SimplePropertyValueModel<>("foo");
		this.testModel = PropertyValueModelTools.transform_(this.stringModel, UPPER_CASE_TRANSFORMER, LOWER_CASE_TRANSFORMER);
	}

	private static final Transformer<String, String> UPPER_CASE_TRANSFORMER = new UpperCaseTransformer();
	static class UpperCaseTransformer
		extends AbstractTransformer<String, String>
	{
		@Override
		public String transform_(String s) {
			return s.toUpperCase();
		}
	}

	private static final Transformer<String, String> LOWER_CASE_TRANSFORMER = new LowerCaseTransformer();
	static class LowerCaseTransformer
		extends AbstractTransformer<String, String>
	{
		@Override
		public String transform_(String s) {
			return s.toLowerCase();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		assertEquals("foo", this.stringModel.getValue());
		assertNull(this.testModel.getValue());
		ChangeListener listener = this.buildTransformationListener();
		this.testModel.addChangeListener(listener);
		assertEquals("FOO", this.testModel.getValue());

		this.stringModel.setValue("bar");
		assertEquals("bar", this.stringModel.getValue());
		assertEquals("BAR", this.testModel.getValue());

		this.stringModel.setValue("baz");
		assertEquals("baz", this.stringModel.getValue());
		assertEquals("BAZ", this.testModel.getValue());

		this.stringModel.setValue(null);
		assertNull(this.stringModel.getValue());
		assertNull(this.testModel.getValue());

		this.stringModel.setValue("foo");
		assertEquals("foo", this.stringModel.getValue());
		assertEquals("FOO", this.testModel.getValue());
	}

	public void testSetValue() {
		this.testModel.setValue("BAR");
		assertEquals("bar", this.stringModel.getValue());
		assertNull(this.testModel.getValue()); // no listeners

		// NB: odd behavior(!)
		this.testModel.setValue("Foo");
		assertEquals("foo", this.stringModel.getValue());
		assertNull(this.testModel.getValue()); // still no listeners
		ChangeListener listener = this.buildTransformationListener();
		this.testModel.addChangeListener(listener);
		assertEquals("FOO", this.testModel.getValue());
		this.testModel.removeChangeListener(listener);

		this.testModel.setValue(null);
		assertNull(this.stringModel.getValue());
		assertNull(this.testModel.getValue());

		// NB: odd behavior(!)
		this.testModel.setValue("baz");
		assertEquals("baz", this.stringModel.getValue());
		assertNull(this.testModel.getValue()); // no listeners
		this.testModel.addChangeListener(listener);
		assertEquals("BAZ", this.testModel.getValue());
		this.testModel.removeChangeListener(listener);
	}

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		ChangeListener listener = this.buildTransformationListener();
		this.testModel.addChangeListener(listener);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.testModel.removeChangeListener(listener);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.testModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.stringModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.testModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.stringModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testPropertyChange1() {
		this.stringModel.addChangeListener(this.buildListener());
		this.testModel.addChangeListener(this.buildTransformationListener());
		this.verifyPropertyChanges();
	}

	public void testPropertyChange2() {
		this.stringModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildListener());
		this.testModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildTransformationListener());
		this.verifyPropertyChanges();
	}

	private void verifyPropertyChanges() {
		this.stringEvent = null;
		this.testEvent = null;
		this.stringModel.setValue("bar");
		this.verifyEvent(this.stringEvent, this.stringModel, "foo", "bar");
		this.verifyEvent(this.testEvent, this.testModel, "FOO", "BAR");

		this.stringEvent = null;
		this.testEvent = null;
		this.stringModel.setValue("baz");
		this.verifyEvent(this.stringEvent, this.stringModel, "bar", "baz");
		this.verifyEvent(this.testEvent, this.testModel, "BAR", "BAZ");

		this.stringEvent = null;
		this.testEvent = null;
		this.stringModel.setValue("Foo");
		this.verifyEvent(this.stringEvent, this.stringModel, "baz", "Foo");
		this.verifyEvent(this.testEvent, this.testModel, "BAZ", "FOO");

		this.stringEvent = null;
		this.testEvent = null;
		this.stringModel.setValue("FOO");
		this.verifyEvent(this.stringEvent, this.stringModel, "Foo", "FOO");
		assertNull(this.testEvent);

		this.stringEvent = null;
		this.testEvent = null;
		this.stringModel.setValue(null);
		this.verifyEvent(this.stringEvent, this.stringModel, "FOO", null);
		this.verifyEvent(this.testEvent, this.testModel, "FOO", null);

		this.stringEvent = null;
		this.testEvent = null;
		this.stringModel.setValue("bar");
		this.verifyEvent(this.stringEvent, this.stringModel, null, "bar");
		this.verifyEvent(this.testEvent, this.testModel, null, "BAR");
	}

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				TransformationPropertyValueModelTests.this.stringEvent = e;
			}
		};
	}

	private ChangeListener buildTransformationListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				TransformationPropertyValueModelTests.this.testEvent = e;
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
