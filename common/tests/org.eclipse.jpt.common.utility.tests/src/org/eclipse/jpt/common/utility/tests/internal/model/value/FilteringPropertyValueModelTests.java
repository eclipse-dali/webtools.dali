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
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class FilteringPropertyValueModelTests
	extends TestCase
{
	private ModifiablePropertyValueModel<String> innerModel;
	PropertyChangeEvent innerModelEvent;

	private ModifiablePropertyValueModel<String> filteredModel;
	PropertyChangeEvent filteredModelEvent;

	public FilteringPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.innerModel = new SimplePropertyValueModel<>("foo");
		this.filteredModel = PropertyValueModelTools.filterModifiable(this.innerModel, this.buildGetFilter(), this.buildSetFilter());
//		this.filteredModel = new FilteringModifiablePropertyValueModel<>(this.innerModel, this.buildGetFilter(), this.buildSetFilter());
	}

	private Predicate<String> buildGetFilter() {
		return new StringStartsWithB();
	}

	private Predicate<String> buildSetFilter() {
		return new StringStartsWithB();
	}

	class StringStartsWithB
		extends PredicateAdapter<String>
	{
		@Override
		public boolean evaluate(String s) {
			return (s != null) && s.startsWith("b");
		}
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		assertEquals("foo", this.innerModel.getValue());
		assertNull(this.filteredModel.getValue());

		ChangeListener innerListener = this.buildInnerListener();
		this.innerModel.addChangeListener(innerListener);
		ChangeListener filteredListener = this.buildFilteredListener();
		this.filteredModel.addChangeListener(filteredListener);

		assertEquals("foo", this.innerModel.getValue());
		assertNull(this.filteredModel.getValue());

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModel.setValue("bar");
		assertEquals("bar", this.innerModel.getValue());
		assertEquals("bar", this.innerModelEvent.getNewValue());
		assertNotNull(this.filteredModel.getValue());
		assertEquals("bar", this.filteredModel.getValue());
		assertEquals("bar", this.filteredModelEvent.getNewValue());

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModel.setValue("baz");
		assertEquals("baz", this.innerModel.getValue());
		assertEquals("baz", this.innerModelEvent.getNewValue());
		assertNotNull(this.filteredModel.getValue());
		assertEquals("baz", this.filteredModel.getValue());
		assertEquals("baz", this.filteredModelEvent.getNewValue());

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModel.setValue(null);
		assertNull(this.innerModel.getValue());
		assertNull(this.innerModelEvent.getNewValue());
		assertNull(this.filteredModel.getValue());
		assertNull(this.filteredModelEvent.getNewValue());

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModel.setValue("foo");
		assertEquals("foo", this.innerModel.getValue());
		assertEquals("foo", this.innerModelEvent.getNewValue());
		assertNull(this.filteredModel.getValue());
		assertNull(this.filteredModelEvent);
	}

	public void testSetValue() {
		assertEquals("foo", this.innerModel.getValue());
		assertNull(this.filteredModel.getValue());

		ChangeListener innerListener = this.buildInnerListener();
		this.innerModel.addChangeListener(innerListener);
		ChangeListener filteredListener = this.buildFilteredListener();
		this.filteredModel.addChangeListener(filteredListener);

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.filteredModel.setValue("bar");
		assertEquals("bar", this.innerModel.getValue());
		assertEquals("bar", this.innerModelEvent.getNewValue());
		assertEquals("bar", this.filteredModel.getValue());
		assertEquals("bar", this.filteredModelEvent.getNewValue());

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.filteredModel.setValue("foo");
		assertNull(this.innerModel.getValue());
		assertNull(this.innerModelEvent.getNewValue());
		assertNull(this.filteredModel.getValue());
		assertNull(this.filteredModelEvent.getNewValue());

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.filteredModel.setValue(null);
		assertNull(this.innerModel.getValue());
		assertNull(this.innerModelEvent);
		assertNull(this.filteredModel.getValue());
		assertNull(this.filteredModelEvent);

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.filteredModel.setValue("baz");
		assertEquals("baz", this.innerModel.getValue());
		assertEquals("baz", this.innerModelEvent.getNewValue());
		assertEquals("baz", this.filteredModel.getValue());
		assertEquals("baz", this.filteredModelEvent.getNewValue());
	}

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.innerModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		ChangeListener listener = this.buildFilteredListener();
		this.filteredModel.addChangeListener(listener);
		assertTrue(((AbstractModel) this.innerModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.filteredModel.removeChangeListener(listener);
		assertTrue(((AbstractModel) this.innerModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.filteredModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.innerModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.filteredModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.innerModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testPropertyChange1() {
		this.innerModel.addChangeListener(this.buildInnerListener());
		this.filteredModel.addChangeListener(this.buildFilteredListener());
		this.verifyPropertyChanges();
	}

	public void testPropertyChange2() {
		this.innerModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildInnerListener());
		this.filteredModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildFilteredListener());
		this.verifyPropertyChanges();
	}

	private void verifyPropertyChanges() {
		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModel.setValue("bar");
		this.verifyEvent(this.innerModelEvent, this.innerModel, "foo", "bar");
		this.verifyEvent(this.filteredModelEvent, this.filteredModel, null, "bar");

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModel.setValue("baz");
		this.verifyEvent(this.innerModelEvent, this.innerModel, "bar", "baz");
		this.verifyEvent(this.filteredModelEvent, this.filteredModel, "bar", "baz");

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModel.setValue("foo");
		this.verifyEvent(this.innerModelEvent, this.innerModel, "baz", "foo");
		this.verifyEvent(this.filteredModelEvent, this.filteredModel, "baz", null);

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModel.setValue("fop");
		this.verifyEvent(this.innerModelEvent, this.innerModel, "foo", "fop");
		assertNull(this.filteredModelEvent);

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModel.setValue(null);
		this.verifyEvent(this.innerModelEvent, this.innerModel, "fop", null);
		assertNull(this.filteredModelEvent);

		this.innerModelEvent = null;
		this.filteredModelEvent = null;
		this.innerModel.setValue("bar");
		this.verifyEvent(this.innerModelEvent, this.innerModel, null, "bar");
		this.verifyEvent(this.filteredModelEvent, this.filteredModel, null, "bar");
	}

	private ChangeListener buildInnerListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				FilteringPropertyValueModelTests.this.innerModelEvent = e;
			}
		};
	}

	private ChangeListener buildFilteredListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				FilteringPropertyValueModelTests.this.filteredModelEvent = e;
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
