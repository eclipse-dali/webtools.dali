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
public class CachingTransformationPropertyValueModelTests
	extends TestCase
{
	private ModifiablePropertyValueModel<Person> personModel;
	PropertyChangeEvent event;

	private PropertyValueModel<Person> testModel;
	PropertyChangeEvent transformationEvent;

	public CachingTransformationPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.personModel = new SimplePropertyValueModel<>(new Person("Karen", "Peggy", null));
		this.testModel = PropertyValueModelTools.transform(this.personModel, PARENT_TRANSFORMER);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		ChangeListener listener = this.buildTransformationChangeListener();
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

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.personModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		ChangeListener listener = this.buildTransformationChangeListener();
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
		this.personModel.addChangeListener(this.buildChangeListener());
		this.testModel.addChangeListener(this.buildTransformationChangeListener());
		this.verifyPropertyChanges();
	}

	public void testPropertyChange2() {
		this.personModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildListener());
		this.testModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.buildTransformationListener());
		this.verifyPropertyChanges();
	}

	private void verifyPropertyChanges() {
		this.event = null;
		this.transformationEvent = null;
		Person karen = this.personModel.getValue();
		Person peggyParent = this.testModel.getValue();
		Person peggy = new Person("Peggy", "Marian", null);
		this.personModel.setValue(peggy);
		Person marianParent = this.testModel.getValue();
		this.verifyEvent(this.event, this.personModel, karen, peggy);
		this.verifyEvent(this.transformationEvent, this.testModel, peggyParent, marianParent);

		this.event = null;
		this.transformationEvent = null;
		Person matt = new Person("Matt", "Mitch", null);
		this.personModel.setValue(matt);
		Person mitchParent = this.testModel.getValue();
		this.verifyEvent(this.event, this.personModel, peggy, matt);
		this.verifyEvent(this.transformationEvent, this.testModel, marianParent, mitchParent);

		this.event = null;
		this.transformationEvent = null;
		this.personModel.setValue(null);
		this.verifyEvent(this.event, this.personModel, matt, null);
		this.verifyEvent(this.transformationEvent, this.testModel, mitchParent, null);

		this.event = null;
		this.transformationEvent = null;
		this.personModel.setValue(matt);
		mitchParent = this.testModel.getValue();
		this.verifyEvent(this.event, this.personModel, null, matt);
		this.verifyEvent(this.transformationEvent, this.testModel, null, mitchParent);
	}

	private PropertyChangeListener buildListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				CachingTransformationPropertyValueModelTests.this.event = e;
			}
		};
	}

	private PropertyChangeListener buildTransformationListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				CachingTransformationPropertyValueModelTests.this.transformationEvent = e;
			}
		};
	}

	private ChangeListener buildChangeListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				CachingTransformationPropertyValueModelTests.this.event = e;
			}
		};
	}

	private ChangeListener buildTransformationChangeListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				CachingTransformationPropertyValueModelTests.this.transformationEvent = e;
			}
		};
	}

	private void verifyEvent(PropertyChangeEvent e, Object source, Object oldValue, Object newValue) {
		assertEquals(source, e.getSource());
		assertEquals(PropertyValueModel.VALUE, e.getPropertyName());
		assertEquals(oldValue, e.getOldValue());
		assertEquals(newValue, e.getNewValue());
	}

	
	private static final Transformer<Person, Person> PARENT_TRANSFORMER = new ParentTransformer();
	static class ParentTransformer
		extends TransformerAdapter<Person, Person>
	{
		@Override
		public Person transform(Person person) {
			return person.getParent();
		}
	}


	class Person
		extends AbstractModel
	{
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
}
