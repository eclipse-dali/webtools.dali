/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class TransformationModifiablePropertyValueModelTests
	extends TestCase
{
	private ModifiablePropertyValueModel<Person> objectHolder;
	PropertyChangeEvent event;

	private ModifiablePropertyValueModel<Person> transformationObjectHolder;
	PropertyChangeEvent transformationEvent;

	public TransformationModifiablePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.objectHolder = new SimplePropertyValueModel<Person>(new Person("Karen", "Peggy", null));
		this.transformationObjectHolder = new TransformationModifiablePropertyValueModel<Person, Person>(this.objectHolder, PARENT_TRANSFORMER, CHILD_TRANSFORMER);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		ChangeListener listener = this.buildTransformationListener();
		this.transformationObjectHolder.addChangeListener(listener);

		
		Person person = this.objectHolder.getValue();
		assertEquals("Karen", person.getName());
		Person parent = this.transformationObjectHolder.getValue();
		assertEquals(person.getParent().getName(), parent.getName());
		assertNotSame(person.getParent(), this.transformationObjectHolder.getValue());
		assertEquals(parent, this.transformationObjectHolder.getValue());

		Person person1 = new Person("Matt", "Mitch", null);
		this.objectHolder.setValue(person1);
		Person parent2 = this.transformationObjectHolder.getValue();
		assertEquals(person1.getParent().getName(), parent2.getName());
		assertNotSame(person1.getParent(), this.transformationObjectHolder.getValue());
		assertEquals(parent2, this.transformationObjectHolder.getValue());


		this.objectHolder.setValue(null);
		assertNull(this.objectHolder.getValue());
		assertNull(this.transformationObjectHolder.getValue());

		Person person3 = new Person("Karen", "Peggy", null);
		this.objectHolder.setValue(person3);
		assertEquals("Karen", person3.getName());
		Person parent3 = this.transformationObjectHolder.getValue();
		assertEquals(person3.getParent().getName(), parent3.getName());
		assertNotSame(person3.getParent(), this.transformationObjectHolder.getValue());
		assertEquals(parent3, this.transformationObjectHolder.getValue());
	}

	public void testSetValue() {
		ChangeListener listener = this.buildTransformationListener();
		this.transformationObjectHolder.addChangeListener(listener);

		Person person = new Person("Chris", "Noel", null);
		this.transformationObjectHolder.setValue(person.getParent());
		assertEquals(person, this.objectHolder.getValue());
		assertEquals(person.getParent().getName(), this.transformationObjectHolder.getValue().getName());
		assertNotSame(person.getParent(), this.transformationObjectHolder.getValue());

		Person person2 = new Person("Jon", "Elizabeth", null);
		this.transformationObjectHolder.setValue(person2.getParent());
		assertEquals(person2, this.objectHolder.getValue());
		assertEquals(person2.getParent().getName(), this.transformationObjectHolder.getValue().getName());
		assertNotSame(person2.getParent(), this.transformationObjectHolder.getValue());

		this.transformationObjectHolder.setValue(null);
		assertNull(this.objectHolder.getValue());
		assertNull(this.transformationObjectHolder.getValue());

		this.transformationObjectHolder.setValue(person.getParent());
		assertEquals(person, this.objectHolder.getValue());
		assertEquals(person.getParent().getName(), this.transformationObjectHolder.getValue().getName());
		assertNotSame(person.getParent(), this.transformationObjectHolder.getValue());
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
		Person oldPerson = this.objectHolder.getValue();
		Person oldParent = this.transformationObjectHolder.getValue();
		Person newPerson = new Person("Karen" , "Peggy", null);
		this.objectHolder.setValue(newPerson);
		Person newParent = this.transformationObjectHolder.getValue();
		this.verifyEvent(this.event, this.objectHolder, oldPerson, newPerson);
		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, oldParent, newParent);

//
//		this.event = null;
//		this.transformationEvent = null;
//		this.objectHolder.setValue("Foo");
//		this.verifyEvent(this.event, this.objectHolder, "baz", "Foo");
//		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, "BAZ", "FOO");
//
//		this.event = null;
//		this.transformationEvent = null;
//		this.objectHolder.setValue("FOO");
//		this.verifyEvent(this.event, this.objectHolder, "Foo", "FOO");
//		assertNull(this.transformationEvent);
//
//		this.event = null;
//		this.transformationEvent = null;
//		this.objectHolder.setValue(null);
//		this.verifyEvent(this.event, this.objectHolder, "FOO", null);
//		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, "FOO", null);
//
//		this.event = null;
//		this.transformationEvent = null;
//		this.objectHolder.setValue("bar");
//		this.verifyEvent(this.event, this.objectHolder, null, "bar");
//		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, null, "BAR");
	}

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				TransformationModifiablePropertyValueModelTests.this.event = e;
			}
		};
	}

	private ChangeListener buildTransformationListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				TransformationModifiablePropertyValueModelTests.this.transformationEvent = e;
			}
		};
	}

	private void verifyEvent(PropertyChangeEvent e, Object source, Object oldValue, Object newValue) {
		assertEquals(source, e.getSource());
		assertEquals(PropertyValueModel.VALUE, e.getPropertyName());
		assertEquals(oldValue, e.getOldValue());
		assertEquals(newValue, e.getNewValue());
	}

	
	class Person extends AbstractModel {

		private String name;
			public static final String NAME_PROPERTY = "name";
		
		private String parentName;
			public static final String PARENT_NAME_PROPERTY = "parentName";
		
		private Person child;
		
		public Person(String name, String parentName, Person child) {
			this.name = name;
			this.parentName = parentName;
			this.child = child;
		}
		
		public String getName() {
			return this.name;
		}
		
		public void setName(String newName) {
			String oldName = this.name;
			this.name = newName;
			firePropertyChanged(NAME_PROPERTY, oldName, newName);
		}
		
		public Person getParent() {
			return new Person(this.parentName, null, this);
		}
		
		public String getParentName() {
			return this.parentName;
		}
		
		public void setParentName(String newParentName) {
			String oldParentName = this.parentName;
			this.parentName = newParentName;
			firePropertyChanged(PARENT_NAME_PROPERTY, oldParentName, newParentName);
		}
		
		public Person getChild() {
			return this.child;
		}
	}

	private static final Transformer<Person, Person> PARENT_TRANSFORMER = new ParentTransformer();
	static class ParentTransformer
		extends AbstractTransformer<Person, Person>
	{
		@Override
		public Person transform_(Person p) {
			return p.getParent();
		}
	}

	private static final Transformer<Person, Person> CHILD_TRANSFORMER = new ChildTransformer();
	static class ChildTransformer
		extends AbstractTransformer<Person, Person>
	{
		@Override
		public Person transform_(Person p) {
			return p.getChild();
		}
	}
}
