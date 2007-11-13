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

import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class PropertyAspectAdapterTests extends TestCase {
	private TestSubject subject1;
	private PropertyValueModel subjectHolder1;
	private PropertyAspectAdapter aa1;
	private PropertyChangeEvent event1;
	private PropertyChangeListener listener1;

	private TestSubject subject2;

	private PropertyChangeEvent multipleValueEvent;

	private PropertyChangeEvent customValueEvent;


	public PropertyAspectAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject1 = new TestSubject("foo", "test subject 1");
		this.subjectHolder1 = new SimplePropertyValueModel(this.subject1);
		this.aa1 = this.buildAspectAdapter(this.subjectHolder1);
		this.listener1 = this.buildValueChangeListener1();
		this.aa1.addPropertyChangeListener(ValueModel.VALUE, this.listener1);
		this.event1 = null;

		this.subject2 = new TestSubject("bar", "test subject 2");
	}

	private PropertyAspectAdapter buildAspectAdapter(ValueModel subjectHolder) {
		return new PropertyAspectAdapter(subjectHolder, TestSubject.NAME_PROPERTY) {
			// this is not a aspect adapter - the value is determined by the aspect name
			@Override
			protected Object getValueFromSubject() {
				if (this.propertyNames[0] == TestSubject.NAME_PROPERTY) {
					return ((TestSubject) this.subject).getName();
				} else if (this.propertyNames[0] == TestSubject.DESCRIPTION_PROPERTY) {
					return ((TestSubject) this.subject).getDescription();
				} else {
					throw new IllegalStateException("invalid aspect name: " + this.propertyNames[0]);
				}
			}
			@Override
			protected void setValueOnSubject(Object value) {
				if (this.propertyNames[0] == TestSubject.NAME_PROPERTY) {
					((TestSubject) this.subject).setName((String) value);
				} else if (this.propertyNames[0] == TestSubject.DESCRIPTION_PROPERTY) {
					((TestSubject) this.subject).setDescription((String) value);
				} else {
					throw new IllegalStateException("invalid aspect name: " + this.propertyNames[0]);
				}
			}
		};
	}

	private PropertyChangeListener buildValueChangeListener1() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				PropertyAspectAdapterTests.this.value1Changed(e);
			}
		};
	}

	void value1Changed(PropertyChangeEvent e) {
		this.event1 = e;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSubjectHolder() {
		assertEquals("foo", this.aa1.value());
		assertNull(this.event1);

		this.subjectHolder1.setValue(this.subject2);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ValueModel.VALUE, this.event1.propertyName());
		assertEquals("foo", this.event1.oldValue());
		assertEquals("bar", this.event1.newValue());
		assertEquals("bar", this.aa1.value());
		
		this.event1 = null;
		this.subjectHolder1.setValue(null);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ValueModel.VALUE, this.event1.propertyName());
		assertEquals("bar", this.event1.oldValue());
		assertNull(this.event1.newValue());
		assertNull(this.aa1.value());
		
		this.event1 = null;
		this.subjectHolder1.setValue(this.subject1);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ValueModel.VALUE, this.event1.propertyName());
		assertEquals(null, this.event1.oldValue());
		assertEquals("foo", this.event1.newValue());
		assertEquals("foo", this.aa1.value());
	}

	public void testPropertyChange() {
		assertEquals("foo", this.aa1.value());
		assertNull(this.event1);

		this.subject1.setName("baz");
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ValueModel.VALUE, this.event1.propertyName());
		assertEquals("foo", this.event1.oldValue());
		assertEquals("baz", this.event1.newValue());
		assertEquals("baz", this.aa1.value());
		
		this.event1 = null;
		this.subject1.setName(null);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ValueModel.VALUE, this.event1.propertyName());
		assertEquals("baz", this.event1.oldValue());
		assertEquals(null, this.event1.newValue());
		assertEquals(null, this.aa1.value());
		
		this.event1 = null;
		this.subject1.setName("foo");
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(ValueModel.VALUE, this.event1.propertyName());
		assertEquals(null, this.event1.oldValue());
		assertEquals("foo", this.event1.newValue());
		assertEquals("foo", this.aa1.value());
	}

	public void testValue() {
		assertEquals("foo", this.subject1.getName());
		assertEquals("foo", this.aa1.value());
	}

	public void testStaleValue() {
		assertEquals("foo", this.subject1.getName());
		assertEquals("foo", this.aa1.value());

		this.aa1.removePropertyChangeListener(ValueModel.VALUE, this.listener1);
		assertEquals(null, this.aa1.value());

		this.aa1.addPropertyChangeListener(ValueModel.VALUE, this.listener1);
		assertEquals("foo", this.aa1.value());

		this.aa1.removePropertyChangeListener(ValueModel.VALUE, this.listener1);
		this.subjectHolder1.setValue(this.subject2);
		assertEquals(null, this.aa1.value());

		this.aa1.addPropertyChangeListener(ValueModel.VALUE, this.listener1);
		assertEquals("bar", this.aa1.value());
	}

	public void testSetValue() {
		this.aa1.setValue("baz");
		assertEquals("baz", this.aa1.value());
		assertEquals("baz", this.subject1.getName());
	}

	public void testHasListeners() {
		assertTrue(this.aa1.hasAnyPropertyChangeListeners(ValueModel.VALUE));
		assertTrue(this.subject1.hasAnyPropertyChangeListeners(TestSubject.NAME_PROPERTY));
		this.aa1.removePropertyChangeListener(ValueModel.VALUE, this.listener1);
		assertFalse(this.subject1.hasAnyPropertyChangeListeners(TestSubject.NAME_PROPERTY));
		assertFalse(this.aa1.hasAnyPropertyChangeListeners(ValueModel.VALUE));

		PropertyChangeListener listener2 = this.buildValueChangeListener1();
		this.aa1.addPropertyChangeListener(listener2);
		assertTrue(this.aa1.hasAnyPropertyChangeListeners(ValueModel.VALUE));
		assertTrue(this.subject1.hasAnyPropertyChangeListeners(TestSubject.NAME_PROPERTY));
		this.aa1.removePropertyChangeListener(listener2);
		assertFalse(this.subject1.hasAnyPropertyChangeListeners(TestSubject.NAME_PROPERTY));
		assertFalse(this.aa1.hasAnyPropertyChangeListeners(ValueModel.VALUE));
	}

	public void testMultipleAspectAdapter() {
		TestSubject testSubject = new TestSubject("fred", "husband");
		PropertyValueModel testSubjectHolder = new SimplePropertyValueModel(testSubject);
		PropertyValueModel testAA = this.buildMultipleAspectAdapter(testSubjectHolder);
		PropertyChangeListener testListener = this.buildMultipleValueChangeListener();
		testAA.addPropertyChangeListener(ValueModel.VALUE, testListener);
		assertEquals("fred:husband", testAA.value());

		this.multipleValueEvent = null;
		testSubject.setName("wilma");
		assertEquals("wilma:husband", testAA.value());
		assertEquals("fred:husband", this.multipleValueEvent.oldValue());
		assertEquals("wilma:husband", this.multipleValueEvent.newValue());

		this.multipleValueEvent = null;
		testSubject.setDescription("wife");
		assertEquals("wilma:wife", testAA.value());
		assertEquals("wilma:husband", this.multipleValueEvent.oldValue());
		assertEquals("wilma:wife", this.multipleValueEvent.newValue());
	}

	private PropertyValueModel buildMultipleAspectAdapter(ValueModel subjectHolder) {
		return new PropertyAspectAdapter(subjectHolder, TestSubject.NAME_PROPERTY, TestSubject.DESCRIPTION_PROPERTY) {
			@Override
			protected Object getValueFromSubject() {
				TestSubject ts = (TestSubject) this.subject;
				return ts.getName() + ":" + ts.getDescription();
			}
		};
	}

	private PropertyChangeListener buildMultipleValueChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				PropertyAspectAdapterTests.this.multipleValueChanged(e);
			}
		};
	}

	void multipleValueChanged(PropertyChangeEvent e) {
		this.multipleValueEvent = e;
	}

	/**
	 * test a bug where we would call #buildValue() in
	 * #engageNonNullSubject(), when we needed to call
	 * it in #engageSubject(), so the cached value would
	 * be rebuilt when the this.subject was set to null
	 */
	public void testCustomBuildValueWithNullSubject() {
		TestSubject customSubject = new TestSubject("fred", "laborer");
		PropertyValueModel customSubjectHolder = new SimplePropertyValueModel(customSubject);
		PropertyValueModel customAA = this.buildCustomAspectAdapter(customSubjectHolder);
		PropertyChangeListener customListener = this.buildCustomValueChangeListener();
		customAA.addPropertyChangeListener(ValueModel.VALUE, customListener);
		assertEquals("fred", customAA.value());

		this.customValueEvent = null;
		customSubject.setName("wilma");
		assertEquals("wilma", customAA.value());
		assertEquals("fred", this.customValueEvent.oldValue());
		assertEquals("wilma", this.customValueEvent.newValue());

		this.customValueEvent = null;
		customSubjectHolder.setValue(null);
		// this would fail - the value would be null...
		assertEquals("<unnamed>", customAA.value());
		assertEquals("wilma", this.customValueEvent.oldValue());
		assertEquals("<unnamed>", this.customValueEvent.newValue());
	}

	private PropertyValueModel buildCustomAspectAdapter(ValueModel subjectHolder) {
		return new PropertyAspectAdapter(subjectHolder, TestSubject.NAME_PROPERTY) {
			@Override
			protected Object buildValue() {
				TestSubject ts = (TestSubject) this.subject;
				return (ts == null) ? "<unnamed>" : ts.getName();
			}
		};
	}

	private PropertyChangeListener buildCustomValueChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				PropertyAspectAdapterTests.this.customValueChanged(e);
			}
		};
	}

	void customValueChanged(PropertyChangeEvent e) {
		this.customValueEvent = e;
	}


	// ********** inner class **********
	
	private class TestSubject extends AbstractModel {
		private String name;
		public static final String NAME_PROPERTY = "name";
		private String description;
		public static final String DESCRIPTION_PROPERTY = "description";
	
		public TestSubject(String name, String description) {
			this.name = name;
			this.description = description;
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}
		public String getDescription() {
			return this.description;
		}
		public void setDescription(String description) {
			Object old = this.description;
			this.description = description;
			this.firePropertyChanged(DESCRIPTION_PROPERTY, old, description);
		}
	}

}
