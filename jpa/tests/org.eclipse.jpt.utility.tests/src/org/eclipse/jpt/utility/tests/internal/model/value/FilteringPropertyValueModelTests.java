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

import org.eclipse.jpt.utility.internal.BidiFilter;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.FilteringWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class FilteringPropertyValueModelTests extends TestCase {
	private WritablePropertyValueModel<String> objectHolder;
	PropertyChangeEvent event;

	private WritablePropertyValueModel<String> filteredObjectHolder;
	PropertyChangeEvent filteredEvent;

	public FilteringPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.objectHolder = new SimplePropertyValueModel<String>("foo");
		this.filteredObjectHolder = new FilteringWritablePropertyValueModel<String>(this.objectHolder, this.buildFilter());
	}

	private BidiFilter<String> buildFilter() {
		return new BidiFilter<String>() {
			public boolean accept(String s) {
				return (s != null) && s.startsWith("b");
			}
			public boolean reverseAccept(String s) {
				return (s != null) && s.startsWith("b");
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
		assertNull(this.filteredObjectHolder.getValue());

		this.objectHolder.setValue("bar");
		assertEquals("bar", this.objectHolder.getValue());
		assertNotNull(this.filteredObjectHolder.getValue());
		assertEquals("bar", this.filteredObjectHolder.getValue());

		this.objectHolder.setValue("baz");
		assertEquals("baz", this.objectHolder.getValue());
		assertNotNull(this.filteredObjectHolder.getValue());
		assertEquals("baz", this.filteredObjectHolder.getValue());

		this.objectHolder.setValue(null);
		assertNull(this.objectHolder.getValue());
		assertNull(this.filteredObjectHolder.getValue());

		this.objectHolder.setValue("foo");
		assertEquals("foo", this.objectHolder.getValue());
		assertNull(this.filteredObjectHolder.getValue());
	}

	public void testSetValue() {
		this.filteredObjectHolder.setValue("bar");
		assertEquals("bar", this.objectHolder.getValue());
		assertEquals("bar", this.filteredObjectHolder.getValue());

		this.filteredObjectHolder.setValue("foo");
		assertEquals("bar", this.objectHolder.getValue());
		assertEquals("bar", this.filteredObjectHolder.getValue());

		this.filteredObjectHolder.setValue(null);
		assertEquals("bar", this.objectHolder.getValue());
		assertEquals("bar", this.filteredObjectHolder.getValue());

		this.filteredObjectHolder.setValue("baz");
		assertEquals("baz", this.objectHolder.getValue());
		assertEquals("baz", this.filteredObjectHolder.getValue());
	}

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		ChangeListener listener = this.buildFilteredListener();
		this.filteredObjectHolder.addChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.filteredObjectHolder.removeChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.filteredObjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.filteredObjectHolder.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testPropertyChange1() {
		this.objectHolder.addChangeListener(this.buildListener());
		this.filteredObjectHolder.addChangeListener(this.buildFilteredListener());
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

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				FilteringPropertyValueModelTests.this.event = e;
			}
		};
	}

	private ChangeListener buildFilteredListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				FilteringPropertyValueModelTests.this.filteredEvent = e;
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
