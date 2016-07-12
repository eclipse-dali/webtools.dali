/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
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
public class TransformationModifiablePropertyValueModelTests
	extends TestCase
{
	private ModifiablePropertyValueModel<Person> personModel;
	PropertyChangeEvent personEvent;

	private ModifiablePropertyValueModel<Person> testModel;
	PropertyChangeEvent transformationEvent;

	public TransformationModifiablePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.personModel = new SimplePropertyValueModel<>(new Person("Karen", "Peggy", null));
		this.testModel = PropertyValueModelTools.transform_(this.personModel, PARENT_TRANSFORMER, CHILD_TRANSFORMER);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		ChangeListener listener = this.buildTransformationListener();
		this.testModel.addChangeListener(listener);

		
		Person person = this.personModel.getValue();
		assertEquals("Karen", person.getName());
		Person parent = this.testModel.getValue();
		assertEquals(person.getParent().getName(), parent.getName());
		assertNotSame(person.getParent(), this.testModel.getValue());
		assertEquals(parent, this.testModel.getValue());

		Person person1 = new Person("Matt", "Mitch", null);
		this.personModel.setValue(person1);
		Person parent2 = this.testModel.getValue();
		assertEquals(person1.getParent().getName(), parent2.getName());
		assertNotSame(person1.getParent(), this.testModel.getValue());
		assertEquals(parent2, this.testModel.getValue());


		this.personModel.setValue(null);
		assertNull(this.personModel.getValue());
		assertNull(this.testModel.getValue());

		Person person3 = new Person("Karen", "Peggy", null);
		this.personModel.setValue(person3);
		assertEquals("Karen", person3.getName());
		Person parent3 = this.testModel.getValue();
		assertEquals(person3.getParent().getName(), parent3.getName());
		assertNotSame(person3.getParent(), this.testModel.getValue());
		assertEquals(parent3, this.testModel.getValue());
	}

	public void testSetValue() {
		ChangeListener listener = this.buildTransformationListener();
		this.testModel.addChangeListener(listener);

		Person person = new Person("Chris", "Noel", null);
		this.testModel.setValue(person.getParent());
		assertEquals(person, this.personModel.getValue());
		assertEquals(person.getParent().getName(), this.testModel.getValue().getName());
		assertNotSame(person.getParent(), this.testModel.getValue());

		Person person2 = new Person("Jon", "Elizabeth", null);
		this.testModel.setValue(person2.getParent());
		assertEquals(person2, this.personModel.getValue());
		assertEquals(person2.getParent().getName(), this.testModel.getValue().getName());
		assertNotSame(person2.getParent(), this.testModel.getValue());

		this.testModel.setValue(null);
		assertNull(this.personModel.getValue());
		assertNull(this.testModel.getValue());

		this.testModel.setValue(person.getParent());
		assertEquals(person, this.personModel.getValue());
		assertEquals(person.getParent().getName(), this.testModel.getValue().getName());
		assertNotSame(person.getParent(), this.testModel.getValue());
	}

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.personModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		ChangeListener listener = this.buildTransformationListener();
		this.testModel.addChangeListener(listener);
		assertTrue(((AbstractModel) this.personModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.testModel.removeChangeListener(listener);
		assertTrue(((AbstractModel) this.personModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.testModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.personModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.testModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.personModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testPropertyChange1() {
		this.personModel.addChangeListener(this.buildListener());
		this.testModel.addChangeListener(this.buildTransformationListener());
		this.verifyPropertyChanges();
	}

	public void testPropertyChange2() {
		this.personModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildListener());
		this.testModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildTransformationListener());
		this.verifyPropertyChanges();
	}

	private void verifyPropertyChanges() {
		this.personEvent = null;
		this.transformationEvent = null;
		Person oldPerson = this.personModel.getValue();
		Person oldParent = this.testModel.getValue();
		Person newPerson = new Person("Karen" , "Peggy", null);
		this.personModel.setValue(newPerson);
		Person newParent = this.testModel.getValue();
		this.verifyEvent(this.personEvent, this.personModel, oldPerson, newPerson);
		this.verifyEvent(this.transformationEvent, this.testModel, oldParent, newParent);
	}

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				TransformationModifiablePropertyValueModelTests.this.personEvent = e;
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
		assertNotNull(e);
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
