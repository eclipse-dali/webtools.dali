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

import java.util.Date;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.BufferedWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class BufferedWritablePropertyValueModelTests extends TestCase {
	private Employee employee;
	private WritablePropertyValueModel<Employee> employeeHolder;
	PropertyChangeEvent employeeEvent;

	private WritablePropertyValueModel<Integer> idAdapter;
	private WritablePropertyValueModel<String> nameAdapter;
	private WritablePropertyValueModel<Date> hireDateAdapter;
	PropertyChangeEvent adapterEvent;

	private BufferedWritablePropertyValueModel.Trigger trigger;
	private BufferedWritablePropertyValueModel<Integer> bufferedIDHolder;
	private BufferedWritablePropertyValueModel<String> bufferedNameHolder;
	private BufferedWritablePropertyValueModel<Date> bufferedHireDateHolder;
	PropertyChangeEvent bufferedEvent;

	public BufferedWritablePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.employee = new Employee(17, "Freddy", new Date());
		this.employeeHolder = new SimplePropertyValueModel<Employee>(this.employee);

		this.trigger = new BufferedWritablePropertyValueModel.Trigger();

		this.idAdapter = this.buildIDAdapter(this.employeeHolder);
		this.bufferedIDHolder = new BufferedWritablePropertyValueModel<Integer>(this.idAdapter, this.trigger);

		this.nameAdapter = this.buildNameAdapter(this.employeeHolder);
		this.bufferedNameHolder = new BufferedWritablePropertyValueModel<String>(this.nameAdapter, this.trigger);

		this.hireDateAdapter = this.buildHireDateAdapter(this.employeeHolder);
		this.bufferedHireDateHolder = new BufferedWritablePropertyValueModel<Date>(this.hireDateAdapter, this.trigger);
	}

	private WritablePropertyValueModel<Integer> buildIDAdapter(PropertyValueModel<Employee> eHolder) {
		return new PropertyAspectAdapter<Employee, Integer>(eHolder, Employee.ID_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return new Integer(this.subject.getID());
			}
			@Override
			protected void setValue_(Integer value) {
				this.subject.setID(value.intValue());
			}
		};
	}

	private WritablePropertyValueModel<String> buildNameAdapter(PropertyValueModel<Employee> eHolder) {
		return new PropertyAspectAdapter<Employee, String>(eHolder, Employee.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getName();
			}
			@Override
			protected void setValue_(String value) {
				this.subject.setName(value);
			}
		};
	}

	private WritablePropertyValueModel<Date> buildHireDateAdapter(PropertyValueModel<Employee> eHolder) {
		return new PropertyAspectAdapter<Employee, Date>(eHolder, Employee.HIRE_DATE_PROPERTY) {
			@Override
			protected Date buildValue_() {
				return this.subject.getHireDate();
			}
			@Override
			protected void setValue_(Date value) {
				this.subject.setHireDate(value);
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetValue() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedIDHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedNameHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedHireDateHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		assertEquals(new Integer(17), this.idAdapter.getValue());
		assertEquals(new Integer(17), this.bufferedIDHolder.getValue());

		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertEquals("Freddy", this.bufferedNameHolder.getValue());

		Date temp = this.employee.getHireDate();
		assertEquals(temp, this.employee.getHireDate());
		assertEquals(temp, this.hireDateAdapter.getValue());
		assertEquals(temp, this.bufferedHireDateHolder.getValue());

		this.bufferedIDHolder.setValue(new Integer(323));
		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idAdapter.getValue());
		assertEquals(new Integer(323), this.bufferedIDHolder.getValue());

		this.bufferedNameHolder.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());

		this.bufferedHireDateHolder.setValue(null);
		assertEquals(temp, this.employee.getHireDate());
		assertEquals(temp, this.hireDateAdapter.getValue());
		assertEquals(null, this.bufferedHireDateHolder.getValue());
	}

	public void testTriggerAccept() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedIDHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedNameHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedHireDateHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedIDHolder.setValue(new Integer(323));
		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idAdapter.getValue());
		assertEquals(new Integer(323), this.bufferedIDHolder.getValue());

		this.bufferedNameHolder.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());

		Date temp = this.employee.getHireDate();
		this.bufferedHireDateHolder.setValue(null);
		assertEquals(temp, this.employee.getHireDate());
		assertEquals(temp, this.hireDateAdapter.getValue());
		assertEquals(null, this.bufferedHireDateHolder.getValue());

		this.trigger.accept();

		assertEquals(323, this.employee.getID());
		assertEquals(new Integer(323), this.idAdapter.getValue());
		assertEquals(new Integer(323), this.bufferedIDHolder.getValue());

		assertEquals("Ripley", this.employee.getName());
		assertEquals("Ripley", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());

		assertEquals(null, this.employee.getHireDate());
		assertEquals(null, this.hireDateAdapter.getValue());
		assertEquals(null, this.bufferedHireDateHolder.getValue());
	}

	public void testTriggerReset() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedIDHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedNameHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedHireDateHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedIDHolder.setValue(new Integer(323));
		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idAdapter.getValue());
		assertEquals(new Integer(323), this.bufferedIDHolder.getValue());

		this.bufferedNameHolder.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());

		Date temp = this.employee.getHireDate();
		this.bufferedHireDateHolder.setValue(null);
		assertEquals(temp, this.employee.getHireDate());
		assertEquals(temp, this.hireDateAdapter.getValue());
		assertEquals(null, this.bufferedHireDateHolder.getValue());

		this.trigger.reset();

		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idAdapter.getValue());
		assertEquals(new Integer(17), this.bufferedIDHolder.getValue());

		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertEquals("Freddy", this.bufferedNameHolder.getValue());

		assertEquals(temp, this.employee.getHireDate());
		assertEquals(temp, this.hireDateAdapter.getValue());
		assertEquals(temp, this.bufferedHireDateHolder.getValue());
	}

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.bufferedIDHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedNameHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedHireDateHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(((AbstractModel) this.idAdapter).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.nameAdapter).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.hireDateAdapter).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.ID_PROPERTY));
		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.NAME_PROPERTY));
		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.HIRE_DATE_PROPERTY));

		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedIDHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedNameHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedHireDateHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		assertTrue(((AbstractModel) this.bufferedIDHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedNameHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedHireDateHolder).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(((AbstractModel) this.idAdapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.nameAdapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.hireDateAdapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(this.employee.hasAnyPropertyChangeListeners(Employee.ID_PROPERTY));
		assertTrue(this.employee.hasAnyPropertyChangeListeners(Employee.NAME_PROPERTY));
		assertTrue(this.employee.hasAnyPropertyChangeListeners(Employee.HIRE_DATE_PROPERTY));

		this.bufferedIDHolder.removePropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedNameHolder.removePropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedHireDateHolder.removePropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		assertTrue(((AbstractModel) this.bufferedIDHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedNameHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedHireDateHolder).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(((AbstractModel) this.idAdapter).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.nameAdapter).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.hireDateAdapter).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.ID_PROPERTY));
		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.NAME_PROPERTY));
		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.HIRE_DATE_PROPERTY));
	}

	public void testPropertyChange1() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		PropertyChangeListener adapterListener = this.buildAdapterListener();
		this.nameAdapter.addPropertyChangeListener(PropertyValueModel.VALUE, adapterListener);

		PropertyChangeListener employeeListener = this.buildEmployeeListener();
		this.employee.addPropertyChangeListener(Employee.NAME_PROPERTY, employeeListener);

		this.verifyPropertyChanges();
	}

	public void testPropertyChange2() {
		ChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameHolder.addChangeListener(bufferedListener);

		ChangeListener adapterListener = this.buildAdapterListener();
		this.nameAdapter.addChangeListener(adapterListener);

		ChangeListener employeeListener = this.buildEmployeeListener();
		this.employee.addChangeListener(employeeListener);

		this.verifyPropertyChanges();
	}

	private void verifyPropertyChanges() {
		this.bufferedEvent = null;
		this.adapterEvent = null;
		this.employeeEvent = null;
		this.bufferedNameHolder.setValue("Ripley");
		this.verifyEvent(this.bufferedEvent, this.bufferedNameHolder, PropertyValueModel.VALUE, "Freddy", "Ripley");
		assertNull(this.adapterEvent);
		assertNull(this.employeeEvent);

		this.bufferedEvent = null;
		this.adapterEvent = null;
		this.employeeEvent = null;
		this.bufferedNameHolder.setValue("Charlie");
		this.verifyEvent(this.bufferedEvent, this.bufferedNameHolder, PropertyValueModel.VALUE, "Ripley", "Charlie");
		assertNull(this.adapterEvent);
		assertNull(this.employeeEvent);

		this.bufferedEvent = null;
		this.adapterEvent = null;
		this.employeeEvent = null;
		this.trigger.accept();
		assertNull(this.bufferedEvent);
		this.verifyEvent(this.adapterEvent, this.nameAdapter, PropertyValueModel.VALUE, "Freddy", "Charlie");
		this.verifyEvent(this.employeeEvent, this.employee, Employee.NAME_PROPERTY, "Freddy", "Charlie");

		this.bufferedEvent = null;
		this.adapterEvent = null;
		this.employeeEvent = null;
		this.bufferedNameHolder.setValue("Jason");
		this.verifyEvent(this.bufferedEvent, this.bufferedNameHolder, PropertyValueModel.VALUE, "Charlie", "Jason");
		assertNull(this.adapterEvent);
		assertNull(this.employeeEvent);

		this.bufferedEvent = null;
		this.adapterEvent = null;
		this.employeeEvent = null;
		this.trigger.reset();
		this.verifyEvent(this.bufferedEvent, this.bufferedNameHolder, PropertyValueModel.VALUE, "Jason", "Charlie");
		assertNull(this.adapterEvent);
		assertNull(this.employeeEvent);
	}

	/**
	 * changing the value should trigger buffering
	 */
	public void testBuffering1() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedNameHolder.setValue("Ripley");
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());
		assertTrue(this.bufferedNameHolder.isBuffering());
	}

	/**
	 * setting to the same value should not trigger buffering (?)
	 */
	public void testBuffering2() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedNameHolder.setValue("Freddy");
		assertEquals("Freddy", this.bufferedNameHolder.getValue());
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertFalse(this.bufferedNameHolder.isBuffering());
	}

	/**
	 * setting to the original value should not trigger buffering (?)
	 */
	public void testBuffering3() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedNameHolder.setValue("Ripley");
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());
		assertTrue(this.bufferedNameHolder.isBuffering());

		this.bufferedNameHolder.setValue("Freddy");
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertEquals("Freddy", this.bufferedNameHolder.getValue());
		assertFalse(this.bufferedNameHolder.isBuffering());
	}

	/**
	 * back-door changes are ignored - "Last One In Wins"
	 */
	public void testChangeConflict1() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedNameHolder.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());

		this.nameAdapter.setValue("Jason");
		assertEquals("Jason", this.employee.getName());
		assertEquals("Jason", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());

		this.trigger.accept();
		// "Jason" is dropped on the floor...
		assertEquals("Ripley", this.employee.getName());
		assertEquals("Ripley", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());
	}

	/**
	 * back-door changes can de-activate buffering (?)
	 */
	public void testChangeConflict2() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameHolder.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedNameHolder.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());
		assertTrue(this.bufferedNameHolder.isBuffering());

		this.nameAdapter.setValue("Ripley");
		assertEquals("Ripley", this.employee.getName());
		assertEquals("Ripley", this.nameAdapter.getValue());
		assertEquals("Ripley", this.bufferedNameHolder.getValue());
		assertFalse(this.bufferedNameHolder.isBuffering());
	}

	private ChangeListener buildBufferedListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				BufferedWritablePropertyValueModelTests.this.bufferedEvent = e;
			}
		};
	}

	private ChangeListener buildAdapterListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				BufferedWritablePropertyValueModelTests.this.adapterEvent = e;
			}
		};
	}

	private ChangeListener buildEmployeeListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				BufferedWritablePropertyValueModelTests.this.employeeEvent = e;
			}
		};
	}

	private void verifyEvent(PropertyChangeEvent event, Object source, String propertyName, Object oldValue, Object newValue) {
		assertEquals(source, event.getSource());
		assertEquals(propertyName, event.getPropertyName());
		assertEquals(oldValue, event.getOldValue());
		assertEquals(newValue, event.getNewValue());
	}


	// ********** inner class **********

	class Employee extends AbstractModel {
		private int id;
			public static final String ID_PROPERTY = "id";
		private String name;
			public static final String NAME_PROPERTY = "name";
		private Date hireDate;
			public static final String HIRE_DATE_PROPERTY = "hireDate";

		Employee(int id, String name, Date hireDate) {
			super();
			this.id = id;
			this.name = name;
			this.hireDate = hireDate;
		}
		int getID() {
			return this.id;
		}
		void setID(int id) {
			int old = this.id;
			this.id = id;
			this.firePropertyChanged(ID_PROPERTY, old, id);
		}
		String getName() {
			return this.name;
		}
		void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}
		Date getHireDate() {
			return this.hireDate;
		}
		void setHireDate(Date hireDate) {
			Object old = this.hireDate;
			this.hireDate = hireDate;
			this.firePropertyChanged(HIRE_DATE_PROPERTY, old, hireDate);
		}
		@Override
		public void toString(StringBuilder sb) {
			sb.append(this.name);
		}
	}
}
