/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.BidiTransformer;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class CachingTransformationPropertyValueModelTests extends TestCase {
	private WritablePropertyValueModel<Person> objectHolder;
	PropertyChangeEvent event;

	private PropertyValueModel<Person> transformationObjectHolder;
	PropertyChangeEvent transformationEvent;

	public CachingTransformationPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.objectHolder = new SimplePropertyValueModel<Person>(new Person("Karen", "Peggy", null));
		this.transformationObjectHolder = new CachingTransformationPropertyValueModel<Person, Person>(this.objectHolder, this.buildTransformer());
	}

	private BidiTransformer<Person, Person> buildTransformer() {
		return new BidiTransformer<Person, Person>() {
			public Person transform(Person p) {
				return (p == null) ? null : p.getParent();
			}
			public Person reverseTransform(Person p) {
				return (p == null) ? null : p.getChild();
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		PropertyChangeListener listener = this.buildTransformationListener();
		this.transformationObjectHolder.addPropertyChangeListener(listener);
		
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

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		PropertyChangeListener listener = this.buildTransformationListener();
		this.transformationObjectHolder.addPropertyChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.transformationObjectHolder.removePropertyChangeListener(listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		this.transformationObjectHolder.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		this.transformationObjectHolder.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.objectHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	public void testPropertyChange1() {
		this.objectHolder.addPropertyChangeListener(this.buildListener());
		this.transformationObjectHolder.addPropertyChangeListener(this.buildTransformationListener());
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
		Person karen = this.objectHolder.getValue();
		Person peggyParent = this.transformationObjectHolder.getValue();
		Person peggy = new Person("Peggy", "Marian", null);
		this.objectHolder.setValue(peggy);
		Person marianParent = this.transformationObjectHolder.getValue();
		this.verifyEvent(this.event, this.objectHolder, karen, peggy);
		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, peggyParent, marianParent);

		this.event = null;
		this.transformationEvent = null;
		Person matt = new Person("Matt", "Mitch", null);
		this.objectHolder.setValue(matt);
		Person mitchParent = this.transformationObjectHolder.getValue();
		this.verifyEvent(this.event, this.objectHolder, peggy, matt);
		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, marianParent, mitchParent);

		this.event = null;
		this.transformationEvent = null;
		this.objectHolder.setValue(null);
		this.verifyEvent(this.event, this.objectHolder, matt, null);
		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, mitchParent, null);

		this.event = null;
		this.transformationEvent = null;
		this.objectHolder.setValue(matt);
		mitchParent = this.transformationObjectHolder.getValue();
		this.verifyEvent(this.event, this.objectHolder, null, matt);
		this.verifyEvent(this.transformationEvent, this.transformationObjectHolder, null, mitchParent);
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

	private void verifyEvent(PropertyChangeEvent e, Object source, Object oldValue, Object newValue) {
		assertEquals(source, e.getSource());
		assertEquals(PropertyValueModel.VALUE, e.getPropertyName());
		assertEquals(oldValue, e.getOldValue());
		assertEquals(newValue, e.getNewValue());
	}

	
	private class Person extends AbstractModel {

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
