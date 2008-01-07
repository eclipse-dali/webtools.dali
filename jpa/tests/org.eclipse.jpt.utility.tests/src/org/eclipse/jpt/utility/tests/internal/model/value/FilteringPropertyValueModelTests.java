/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import org.eclipse.jpt.utility.internal.BidiFilter;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.FilteringWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class FilteringPropertyValueModelTests extends TestCase {
	private WritablePropertyValueModel objectHolder;
	PropertyChangeEvent event;

	private WritablePropertyValueModel filteredObjectHolder;
	PropertyChangeEvent filteredEvent;

	public FilteringPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.objectHolder = new SimplePropertyValueModel("foo");
		this.filteredObjectHolder = new FilteringWritablePropertyValueModel(this.objectHolder, this.buildFilter());
	}

	private BidiFilter buildFilter() {
		return new BidiFilter() {
			public boolean accept(Object o) {
				return (o != null) && ((String) o).startsWith("b");
			}
			public boolean reverseAccept(Object o) {
				return (o != null) && ((String) o).startsWith("b");
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
		assertNull(this.filteredObjectHolder.value());

		this.objectHolder.setValue("bar");
		assertEquals("bar", this.objectHolder.value());
		assertNotNull(this.filteredObjectHolder.value());
		assertEquals("bar", this.filteredObjectHolder.value());

		this.objectHolder.setValue("baz");
		assertEquals("baz", this.objectHolder.value());
		assertNotNull(this.filteredObjectHolder.value());
		assertEquals("baz", this.filteredObjectHolder.value());

		this.objectHolder.setValue(null);
		assertNull(this.objectHolder.value());
		assertNull(this.filteredObjectHolder.value());

		this.objectHolder.setValue("foo");
		assertEquals("foo", this.objectHolder.value());
		assertNull(this.filteredObjectHolder.value());
	}

	public void testSetValue() {
		this.filteredObjectHolder.setValue("bar");
		assertEquals("bar", this.objectHolder.value());
		assertEquals("bar", this.filteredObjectHolder.value());

		this.filteredObjectHolder.setValue("foo");
		assertEquals("bar", this.objectHolder.value());
		assertEquals("bar", this.filteredObjectHolder.value());

		this.filteredObjectHolder.setValue(null);
		assertEquals("bar", this.objectHolder.value());
		assertEquals("bar", this.filteredObjectHolder.value());

		this.filteredObjectHolder.setValue("baz");
		assertEquals("baz", this.objectHolder.value());
		assertEquals("baz", this.filteredObjectHolder.value());
	}

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		PropertyChangeListener listener = this.buildFilteredListener();
		this.filteredObjectHolder.addPropertyChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.filteredObjectHolder.removePropertyChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.filteredObjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.filteredObjectHolder.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testPropertyChange1() {
		this.objectHolder.addPropertyChangeListener(this.buildListener());
		this.filteredObjectHolder.addPropertyChangeListener(this.buildFilteredListener());
		this.verifyPropertyChanges();
	}

	public void testPropertyChange2() {
		this.objectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildListener());
		this.filteredObjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildFilteredListener());
		this.verifyPropertyChanges();
	}

	private void verifyPropertyChanges() {
		this.event = null;
		this.filteredEvent = null;
		this.objectHolder.setValue("bar");
		this.verifyEvent(this.event, this.objectHolder, "foo", "bar");
		this.verifyEvent(this.filteredEvent, this.filteredObjectHolder, null, "bar");

		this.event = null;
		this.filteredEvent = null;
		this.objectHolder.setValue("baz");
		this.verifyEvent(this.event, this.objectHolder, "bar", "baz");
		this.verifyEvent(this.filteredEvent, this.filteredObjectHolder, "bar", "baz");

		this.event = null;
		this.filteredEvent = null;
		this.objectHolder.setValue("foo");
		this.verifyEvent(this.event, this.objectHolder, "baz", "foo");
		this.verifyEvent(this.filteredEvent, this.filteredObjectHolder, "baz", null);

		this.event = null;
		this.filteredEvent = null;
		this.objectHolder.setValue("fop");
		this.verifyEvent(this.event, this.objectHolder, "foo", "fop");
		assertNull(this.filteredEvent);

		this.event = null;
		this.filteredEvent = null;
		this.objectHolder.setValue(null);
		this.verifyEvent(this.event, this.objectHolder, "fop", null);
		assertNull(this.filteredEvent);

		this.event = null;
		this.filteredEvent = null;
		this.objectHolder.setValue("bar");
		this.verifyEvent(this.event, this.objectHolder, null, "bar");
		this.verifyEvent(this.filteredEvent, this.filteredObjectHolder, null, "bar");
	}

	private PropertyChangeListener buildListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				FilteringPropertyValueModelTests.this.event = e;
			}
		};
	}

	private PropertyChangeListener buildFilteredListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				FilteringPropertyValueModelTests.this.filteredEvent = e;
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
