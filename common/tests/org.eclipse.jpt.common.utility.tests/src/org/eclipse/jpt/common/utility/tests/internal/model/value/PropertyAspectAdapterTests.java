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

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class PropertyAspectAdapterTests
	extends TestCase
{
	private TestSubject subject1;
	private ModifiablePropertyValueModel<TestSubject> subjectHolder1;
	private ModifiablePropertyValueModel<String> aa1;
	private PropertyChangeEvent event1;
	private PropertyChangeListener listener1;

	private TestSubject subject2;

	private PropertyChangeEvent customValueEvent;


	public PropertyAspectAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.subject1 = new TestSubject("foo", "test subject 1");
		this.subjectHolder1 = new SimplePropertyValueModel<>(this.subject1);
		this.aa1 = this.buildAspectAdapter(this.subjectHolder1);
		this.listener1 = this.buildValueChangeListener1();
		this.aa1.addPropertyChangeListener(PropertyValueModel.VALUE, this.listener1);
		this.event1 = null;

		this.subject2 = new TestSubject("bar", "test subject 2");
	}

	private ModifiablePropertyValueModel<String> buildAspectAdapter(PropertyValueModel<TestSubject> subjectHolder) {
		return PropertyValueModelTools.modifiableModelAspectAdapter(
				subjectHolder,
				TestSubject.NAME_PROPERTY,
				TestSubject.NAME_TRANSFORMER,
				TestSubject.SET_NAME_CLOSURE
			);
	}

	private ChangeListener buildValueChangeListener1() {
		return new ChangeAdapter() {
			@Override
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
		assertEquals("foo", this.aa1.getValue());
		assertNull(this.event1);

		this.subjectHolder1.setValue(this.subject2);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event1.getPropertyName());
		assertEquals("foo", this.event1.getOldValue());
		assertEquals("bar", this.event1.getNewValue());
		assertEquals("bar", this.aa1.getValue());
		
		this.event1 = null;
		this.subjectHolder1.setValue(null);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event1.getPropertyName());
		assertEquals("bar", this.event1.getOldValue());
		assertNull(this.event1.getNewValue());
		assertNull(this.aa1.getValue());
		
		this.event1 = null;
		this.subjectHolder1.setValue(this.subject1);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event1.getPropertyName());
		assertEquals(null, this.event1.getOldValue());
		assertEquals("foo", this.event1.getNewValue());
		assertEquals("foo", this.aa1.getValue());
	}

	public void testPropertyChange() {
		assertEquals("foo", this.aa1.getValue());
		assertNull(this.event1);

		this.subject1.setName("baz");
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event1.getPropertyName());
		assertEquals("foo", this.event1.getOldValue());
		assertEquals("baz", this.event1.getNewValue());
		assertEquals("baz", this.aa1.getValue());
		
		this.event1 = null;
		this.subject1.setName(null);
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event1.getPropertyName());
		assertEquals("baz", this.event1.getOldValue());
		assertEquals(null, this.event1.getNewValue());
		assertEquals(null, this.aa1.getValue());
		
		this.event1 = null;
		this.subject1.setName("foo");
		assertNotNull(this.event1);
		assertEquals(this.aa1, this.event1.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event1.getPropertyName());
		assertEquals(null, this.event1.getOldValue());
		assertEquals("foo", this.event1.getNewValue());
		assertEquals("foo", this.aa1.getValue());
	}

	public void testValue() {
		assertEquals("foo", this.subject1.getName());
		assertEquals("foo", this.aa1.getValue());
	}

	public void testStaleValue() {
		assertEquals("foo", this.subject1.getName());
		assertEquals("foo", this.aa1.getValue());

		this.aa1.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener1);
		assertEquals(null, this.aa1.getValue());

		this.aa1.addPropertyChangeListener(PropertyValueModel.VALUE, this.listener1);
		assertEquals("foo", this.aa1.getValue());

		this.aa1.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener1);
		this.subjectHolder1.setValue(this.subject2);
		assertEquals(null, this.aa1.getValue());

		this.aa1.addPropertyChangeListener(PropertyValueModel.VALUE, this.listener1);
		assertEquals("bar", this.aa1.getValue());
	}

	public void testSetValue() {
		this.aa1.setValue("baz");
		assertEquals("baz", this.aa1.getValue());
		assertEquals("baz", this.subject1.getName());
	}

	public void testHasListeners() {
		assertTrue(((AbstractModel) this.aa1).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(this.subject1.hasAnyPropertyChangeListeners(TestSubject.NAME_PROPERTY));
		this.aa1.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener1);
		assertFalse(this.subject1.hasAnyPropertyChangeListeners(TestSubject.NAME_PROPERTY));
		assertFalse(((AbstractModel) this.aa1).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		ChangeListener listener2 = this.buildValueChangeListener1();
		this.aa1.addChangeListener(listener2);
		assertTrue(((AbstractModel) this.aa1).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(this.subject1.hasAnyPropertyChangeListeners(TestSubject.NAME_PROPERTY));
		this.aa1.removeChangeListener(listener2);
		assertFalse(this.subject1.hasAnyPropertyChangeListeners(TestSubject.NAME_PROPERTY));
		assertFalse(((AbstractModel) this.aa1).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	/**
	 * test a bug where we would call #buildValue() in
	 * #engageNonNullSubject(), when we needed to call
	 * it in #engageSubject(), so the cached value would
	 * be rebuilt when the this.subject was set to null
	 */
	public void testCustomBuildValueWithNullSubject() {
		TestSubject customSubject = new TestSubject("fred", "laborer");
		ModifiablePropertyValueModel<TestSubject> customSubjectHolder = new SimplePropertyValueModel<>(customSubject);
		ModifiablePropertyValueModel<String> customAA = this.buildCustomAspectAdapter(customSubjectHolder);
		PropertyChangeListener customListener = this.buildCustomValueChangeListener();
		customAA.addPropertyChangeListener(PropertyValueModel.VALUE, customListener);
		assertEquals("fred", customAA.getValue());

		this.customValueEvent = null;
		customSubject.setName("wilma");
		assertEquals("wilma", customAA.getValue());
		assertEquals("fred", this.customValueEvent.getOldValue());
		assertEquals("wilma", this.customValueEvent.getNewValue());

		this.customValueEvent = null;
		customSubjectHolder.setValue(null);
		// this would fail - the value would be null...
		assertEquals("<unnamed>", customAA.getValue());
		assertEquals("wilma", this.customValueEvent.getOldValue());
		assertEquals("<unnamed>", this.customValueEvent.getNewValue());
	}

	/**
	 * Test a bug:
	 * If two listeners were added to an aspect adapter, one with an
	 * aspect name and one without, the aspect adapter would add its
	 * 'subjectChangeListener' to its 'subjectHolder' twice. As a result,
	 * the following code will trigger an IllegalArgumentException
	 * if the bug is present; otherwise, it completes silently.
	 */
	public void testDuplicateListener() {
		ChangeListener listener2 = new ChangeAdapter();
		this.aa1.addChangeListener(listener2);
	}

	private ModifiablePropertyValueModel<String> buildCustomAspectAdapter(PropertyValueModel<TestSubject> subjectHolder) {
		return PropertyValueModelTools.modifiableModelAspectAdapter_(
				subjectHolder,
				TestSubject.NAME_PROPERTY,
				TestSubject.CUSTOM_NAME_TRANSFORMER,
				TestSubject.SET_NAME_CLOSURE
			);
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


	// ********** test model **********
	
	public static class TestSubject
		extends AbstractModel
	{
		private String name;
			public static final String NAME_PROPERTY = "name";
			public static final Transformer<TestSubject, String> NAME_TRANSFORMER = new NameTransformer();
			public static final class NameTransformer
				extends TransformerAdapter<TestSubject, String>
			{
				@Override
				public String transform(TestSubject model) {
					return model.getName();
				}
			}
			public static final Transformer<TestSubject, String> CUSTOM_NAME_TRANSFORMER = new CustomNameTransformer();
			public static final class CustomNameTransformer
				extends TransformerAdapter<TestSubject, String>
			{
				@Override
				public String transform(TestSubject model) {
					return (model == null) ? "<unnamed>" : model.getName();
				}
			}
			public static final BiClosure<TestSubject, String> SET_NAME_CLOSURE = new SetNameClosure();
			public static final class SetNameClosure
				extends BiClosureAdapter<TestSubject, String>
			{
				@Override
				public void execute(TestSubject model, String name) {
					model.setName(name);
				}
			}
		private String description;
			public static final String DESCRIPTION_PROPERTY = "description";
			public static final Transformer<TestSubject, String> DESCRIPTION_TRANSFORMER = new DescriptionTransformer();
			public static final class DescriptionTransformer
				extends TransformerAdapter<TestSubject, String>
			{
				@Override
				public String transform(TestSubject model) {
					return model.getDescription();
				}
			}
			public static final BiClosure<TestSubject, String> SET_DESCRIPTION_CLOSURE = new SetDescriptionClosure();
			public static final class SetDescriptionClosure
				extends BiClosureAdapter<TestSubject, String>
			{
				@Override
				public void execute(TestSubject model, String name) {
					model.setDescription(name);
				}
			}
	
		public TestSubject(String name, String description) {
			this.name = name;
			this.description = description;
		}
		public String getName() {
			return this.name;
		}
		public void setName(String name) {
			Object old = this.name;
			this.firePropertyChanged(NAME_PROPERTY, old, this.name = name);
		}
		public String getDescription() {
			return this.description;
		}
		public void setDescription(String description) {
			Object old = this.description;
			this.firePropertyChanged(DESCRIPTION_PROPERTY, old, this.description = description);
		}
	}
}
