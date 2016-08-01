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

import java.util.Date;
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.BufferedPropertyValueModelAdapter;
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
public class BufferedPropertyValueModelTests
	extends TestCase
{
	private Employee employee;
	private ModifiablePropertyValueModel<Employee> employeeModel;
	PropertyChangeEvent employeeEvent;

	private ModifiablePropertyValueModel<Integer> idModel;
	private ModifiablePropertyValueModel<String> nameModel;
	private ModifiablePropertyValueModel<Date> hireDateModel;
	PropertyChangeEvent adapterEvent;

	private BufferedPropertyValueModelAdapter.Trigger trigger;

	private ModifiablePropertyValueModel<Integer> bufferedIDModel;

	private ModifiablePropertyValueModel<String> bufferedNameModel;
	private PropertyValueModel<Boolean> nameIsBufferingModel;

	private ModifiablePropertyValueModel<Date> bufferedHireDateModel;

	PropertyChangeEvent bufferedEvent;

	public BufferedPropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.employee = new Employee(17, "Freddy", new Date());
		this.employeeModel = new SimplePropertyValueModel<>(this.employee);

		this.trigger = PropertyValueModelTools.bufferedModelAdapterTrigger();

		this.idModel = this.buildIDModel(this.employeeModel);
		Association<ModifiablePropertyValueModel<Integer>, PropertyValueModel<Boolean>> idAssociation = PropertyValueModelTools.buffer(this.idModel, this.trigger);
		this.bufferedIDModel = idAssociation.getKey();

		this.nameModel = this.buildNameModel(this.employeeModel);
		Association<ModifiablePropertyValueModel<String>, PropertyValueModel<Boolean>> nameAssociation = PropertyValueModelTools.buffer(this.nameModel, this.trigger);
		this.bufferedNameModel = nameAssociation.getKey();
		this.nameIsBufferingModel = nameAssociation.getValue();

		this.hireDateModel = this.buildHireDateModel(this.employeeModel);
		Association<ModifiablePropertyValueModel<Date>, PropertyValueModel<Boolean>> hireDateAssociation = PropertyValueModelTools.buffer(this.hireDateModel, this.trigger);
		this.bufferedHireDateModel = hireDateAssociation.getKey();
	}

	private ModifiablePropertyValueModel<Integer> buildIDModel(PropertyValueModel<Employee> eHolder) {
		return PropertyValueModelTools.modifiableModelAspectAdapter(
				eHolder,
				Employee.ID_PROPERTY,
				Employee.ID_TRANSFORMER,
				Employee.SET_ID_CLOSURE
			);
	}

	private ModifiablePropertyValueModel<String> buildNameModel(PropertyValueModel<Employee> eHolder) {
		return PropertyValueModelTools.modifiableModelAspectAdapter(
				eHolder,
				Employee.NAME_PROPERTY,
				Employee.NAME_TRANSFORMER,
				Employee.SET_NAME_CLOSURE
			);
	}

	private ModifiablePropertyValueModel<Date> buildHireDateModel(PropertyValueModel<Employee> eHolder) {
		return PropertyValueModelTools.modifiableModelAspectAdapter(
				eHolder,
				Employee.HIRE_DATE_PROPERTY,
				Employee.HIRE_DATE_TRANSFORMER,
				Employee.SET_HIRE_DATE_CLOSURE
			);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetValue() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedIDModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedHireDateModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		assertEquals(new Integer(17), this.idModel.getValue());
		assertEquals(new Integer(17), this.bufferedIDModel.getValue());

		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Freddy", this.bufferedNameModel.getValue());

		Date temp = this.employee.getHireDate();
		assertEquals(temp, this.employee.getHireDate());
		assertEquals(temp, this.hireDateModel.getValue());
		assertEquals(temp, this.bufferedHireDateModel.getValue());

		this.bufferedIDModel.setValue(new Integer(323));
		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idModel.getValue());
		assertEquals(new Integer(323), this.bufferedIDModel.getValue());

		this.bufferedNameModel.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());

		this.bufferedHireDateModel.setValue(null);
		assertEquals(temp, this.employee.getHireDate());
		assertEquals(temp, this.hireDateModel.getValue());
		assertEquals(null, this.bufferedHireDateModel.getValue());
	}

	public void testTriggerAccept() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedIDModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedHireDateModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idModel.getValue());
		assertEquals(new Integer(17), this.bufferedIDModel.getValue());

		this.trigger.accept(); // NOP

		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idModel.getValue());
		assertEquals(new Integer(17), this.bufferedIDModel.getValue());

		this.bufferedIDModel.setValue(new Integer(323));
		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idModel.getValue());
		assertEquals(new Integer(323), this.bufferedIDModel.getValue());

		this.bufferedNameModel.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());

		Date temp = this.employee.getHireDate();
		this.bufferedHireDateModel.setValue(null);
		assertEquals(temp, this.employee.getHireDate());
		assertEquals(temp, this.hireDateModel.getValue());
		assertEquals(null, this.bufferedHireDateModel.getValue());

		this.trigger.accept();

		assertEquals(323, this.employee.getID());
		assertEquals(new Integer(323), this.idModel.getValue());
		assertEquals(new Integer(323), this.bufferedIDModel.getValue());

		assertEquals("Ripley", this.employee.getName());
		assertEquals("Ripley", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());

		assertNull(this.employee.getHireDate());
		assertNull(this.hireDateModel.getValue());
		assertNull(this.bufferedHireDateModel.getValue());
	}

	public void testTriggerReset() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedIDModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedHireDateModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idModel.getValue());
		assertEquals(new Integer(17), this.bufferedIDModel.getValue());

		this.trigger.reset(); // NOP

		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idModel.getValue());
		assertEquals(new Integer(17), this.bufferedIDModel.getValue());

		this.bufferedIDModel.setValue(new Integer(323));
		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idModel.getValue());
		assertEquals(new Integer(323), this.bufferedIDModel.getValue());

		this.bufferedNameModel.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());

		Date temp = this.employee.getHireDate();
		this.bufferedHireDateModel.setValue(null);
		assertEquals(temp, this.employee.getHireDate());
		assertEquals(temp, this.hireDateModel.getValue());
		assertEquals(null, this.bufferedHireDateModel.getValue());

		this.trigger.reset();

		assertEquals(17, this.employee.getID());
		assertEquals(new Integer(17), this.idModel.getValue());
		assertEquals(new Integer(17), this.bufferedIDModel.getValue());

		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Freddy", this.bufferedNameModel.getValue());

		assertEquals(temp, this.employee.getHireDate());
		assertEquals(temp, this.hireDateModel.getValue());
		assertEquals(temp, this.bufferedHireDateModel.getValue());
	}

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.bufferedIDModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedNameModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedHireDateModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(((AbstractModel) this.idModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.nameModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.hireDateModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.ID_PROPERTY));
		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.NAME_PROPERTY));
		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.HIRE_DATE_PROPERTY));

		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedIDModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedHireDateModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		assertTrue(((AbstractModel) this.bufferedIDModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedNameModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedHireDateModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(((AbstractModel) this.idModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.nameModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.hireDateModel).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(this.employee.hasAnyPropertyChangeListeners(Employee.ID_PROPERTY));
		assertTrue(this.employee.hasAnyPropertyChangeListeners(Employee.NAME_PROPERTY));
		assertTrue(this.employee.hasAnyPropertyChangeListeners(Employee.HIRE_DATE_PROPERTY));

		this.bufferedIDModel.removePropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedNameModel.removePropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);
		this.bufferedHireDateModel.removePropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		assertTrue(((AbstractModel) this.bufferedIDModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedNameModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.bufferedHireDateModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(((AbstractModel) this.idModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.nameModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.hireDateModel).hasNoPropertyChangeListeners(PropertyValueModel.VALUE));

		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.ID_PROPERTY));
		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.NAME_PROPERTY));
		assertTrue(this.employee.hasNoPropertyChangeListeners(Employee.HIRE_DATE_PROPERTY));
	}

	public void testPropertyChange1() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		PropertyChangeListener adapterListener = this.buildAdapterListener();
		this.nameModel.addPropertyChangeListener(PropertyValueModel.VALUE, adapterListener);

		PropertyChangeListener employeeListener = this.buildEmployeeListener();
		this.employee.addPropertyChangeListener(Employee.NAME_PROPERTY, employeeListener);

		this.verifyPropertyChanges();
	}

	public void testPropertyChange2() {
		ChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameModel.addChangeListener(bufferedListener);

		ChangeListener adapterListener = this.buildAdapterListener();
		this.nameModel.addChangeListener(adapterListener);

		ChangeListener employeeListener = this.buildEmployeeListener();
		this.employee.addChangeListener(employeeListener);

		this.verifyPropertyChanges();
	}

	private void verifyPropertyChanges() {
		this.bufferedEvent = null;
		this.adapterEvent = null;
		this.employeeEvent = null;
		this.bufferedNameModel.setValue("Ripley");
		this.verifyEvent(this.bufferedEvent, this.bufferedNameModel, PropertyValueModel.VALUE, "Freddy", "Ripley");
		assertNull(this.adapterEvent);
		assertNull(this.employeeEvent);

		this.bufferedEvent = null;
		this.adapterEvent = null;
		this.employeeEvent = null;
		this.bufferedNameModel.setValue("Charlie");
		this.verifyEvent(this.bufferedEvent, this.bufferedNameModel, PropertyValueModel.VALUE, "Ripley", "Charlie");
		assertNull(this.adapterEvent);
		assertNull(this.employeeEvent);

		this.bufferedEvent = null;
		this.adapterEvent = null;
		this.employeeEvent = null;
		this.trigger.accept();
		assertNull(this.bufferedEvent);
		this.verifyEvent(this.adapterEvent, this.nameModel, PropertyValueModel.VALUE, "Freddy", "Charlie");
		this.verifyEvent(this.employeeEvent, this.employee, Employee.NAME_PROPERTY, "Freddy", "Charlie");

		this.bufferedEvent = null;
		this.adapterEvent = null;
		this.employeeEvent = null;
		this.bufferedNameModel.setValue("Jason");
		this.verifyEvent(this.bufferedEvent, this.bufferedNameModel, PropertyValueModel.VALUE, "Charlie", "Jason");
		assertNull(this.adapterEvent);
		assertNull(this.employeeEvent);

		this.bufferedEvent = null;
		this.adapterEvent = null;
		this.employeeEvent = null;
		this.trigger.reset();
		this.verifyEvent(this.bufferedEvent, this.bufferedNameModel, PropertyValueModel.VALUE, "Jason", "Charlie");
		assertNull(this.adapterEvent);
		assertNull(this.employeeEvent);
	}

	/**
	 * changing the value should trigger buffering
	 */
	public void testBuffering1() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedNameModel.setValue("Ripley");
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());
		assertTrue(this.nameIsBufferingModel.getValue().booleanValue());
	}

	/**
	 * setting to the same value should not trigger buffering (?)
	 */
	public void testBuffering2() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedNameModel.setValue("Freddy");
		assertEquals("Freddy", this.bufferedNameModel.getValue());
		assertEquals("Freddy", this.nameModel.getValue());
		assertFalse(this.nameIsBufferingModel.getValue().booleanValue());
	}

	/**
	 * setting to the original value should not trigger buffering (?)
	 */
	public void testBuffering3() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedNameModel.setValue("Ripley");
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());
		assertTrue(this.nameIsBufferingModel.getValue().booleanValue());

		this.bufferedNameModel.setValue("Freddy");
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Freddy", this.bufferedNameModel.getValue());
		assertFalse(this.nameIsBufferingModel.getValue().booleanValue());
	}

	/**
	 * back-door changes are ignored - "Last One In Wins"
	 */
	public void testChangeConflict1() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedNameModel.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());

		this.nameModel.setValue("Jason");
		assertEquals("Jason", this.employee.getName());
		assertEquals("Jason", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());

		this.trigger.accept();
		// "Jason" is dropped on the floor...
		assertEquals("Ripley", this.employee.getName());
		assertEquals("Ripley", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());
	}

	/**
	 * back-door changes can de-activate buffering (?)
	 */
	public void testChangeConflict2() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.bufferedNameModel.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());
		assertTrue(this.nameIsBufferingModel.getValue().booleanValue());

		this.nameModel.setValue("Ripley");
		assertEquals("Ripley", this.employee.getName());
		assertEquals("Ripley", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());
		assertFalse(this.nameIsBufferingModel.getValue().booleanValue());
	}

	/**
	 * back-door change when not buffering
	 */
	public void testChangeConflict3() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		this.nameModel.setValue("Ripley");
		assertEquals("Ripley", this.employee.getName());
		assertEquals("Ripley", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());
		assertEquals("Ripley", this.bufferedEvent.getNewValue());
		assertFalse(this.nameIsBufferingModel.getValue().booleanValue());
	}

	public void testAdapterToString() {
		PropertyChangeListener bufferedListener = this.buildBufferedListener();
		this.bufferedNameModel.addPropertyChangeListener(PropertyValueModel.VALUE, bufferedListener);

		Object adapter = ObjectTools.get(this.bufferedNameModel, "adapter");
		String s = adapter.toString();
		assertTrue(s.contains("(Freddy)"));

		this.bufferedNameModel.setValue("Ripley");
		assertEquals("Freddy", this.employee.getName());
		assertEquals("Freddy", this.nameModel.getValue());
		assertEquals("Ripley", this.bufferedNameModel.getValue());
		assertTrue(this.nameIsBufferingModel.getValue().booleanValue());

		s = adapter.toString();
		assertTrue(s.contains("(Ripley [Freddy])"));
	}

	public void testTriggerToString() {
		assertNotNull(this.trigger.toString());
	}

	public void testFactoryCtor_NPE1() {
		boolean exCaught = false;
		try {
			Association<ModifiablePropertyValueModel<Integer>, PropertyValueModel<Boolean>> association = PropertyValueModelTools.buffer(null, this.trigger);
			fail("bogus: " + association);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testFactoryCtor_NPE2() {
		boolean exCaught = false;
		try {
			Association<ModifiablePropertyValueModel<Integer>, PropertyValueModel<Boolean>> association = PropertyValueModelTools.buffer(this.idModel, null);
			fail("bogus: " + association);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private ChangeListener buildBufferedListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				BufferedPropertyValueModelTests.this.bufferedEvent = e;
			}
		};
	}

	private ChangeListener buildAdapterListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				BufferedPropertyValueModelTests.this.adapterEvent = e;
			}
		};
	}

	private ChangeListener buildEmployeeListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				BufferedPropertyValueModelTests.this.employeeEvent = e;
			}
		};
	}

	private void verifyEvent(PropertyChangeEvent event, Object source, String propertyName, Object oldValue, Object newValue) {
		assertEquals(source, event.getSource());
		assertEquals(propertyName, event.getPropertyName());
		assertEquals(oldValue, event.getOldValue());
		assertEquals(newValue, event.getNewValue());
	}


	// ********** Employee **********

	static class Employee
		extends AbstractModel
	{
		private int id;
			public static final String ID_PROPERTY = "id";
			public static final Transformer<Employee, Integer> ID_TRANSFORMER = new IDTransformer();
			public static final class IDTransformer
				extends TransformerAdapter<Employee, Integer>
			{
				@Override
				public Integer transform(Employee model) {
					return Integer.valueOf(model.getID());
				}
			}
			public static final BiClosure<Employee, Integer> SET_ID_CLOSURE = new SetIDClosure();
			public static final class SetIDClosure
				extends BiClosureAdapter<Employee, Integer>
			{
				@Override
				public void execute(Employee model, Integer id) {
					model.setID(id.intValue());
				}
			}
		private String name;
			public static final String NAME_PROPERTY = "name";
			public static final Transformer<Employee, String> NAME_TRANSFORMER = new NameTransformer();
			public static final class NameTransformer
				extends TransformerAdapter<Employee, String>
			{
				@Override
				public String transform(Employee model) {
					return model.getName();
				}
			}
			public static final BiClosure<Employee, String> SET_NAME_CLOSURE = new SetNameClosure();
			public static final class SetNameClosure
				extends BiClosureAdapter<Employee, String>
			{
				@Override
				public void execute(Employee model, String name) {
					model.setName(name);
				}
			}
		private Date hireDate;
			public static final String HIRE_DATE_PROPERTY = "hireDate";
			public static final Transformer<Employee, Date> HIRE_DATE_TRANSFORMER = new HireDateTransformer();
			public static final class HireDateTransformer
				extends TransformerAdapter<Employee, Date>
			{
				@Override
				public Date transform(Employee model) {
					return model.getHireDate();
				}
			}
			public static final BiClosure<Employee, Date> SET_HIRE_DATE_CLOSURE = new SetHireDateClosure();
			public static final class SetHireDateClosure
				extends BiClosureAdapter<Employee, Date>
			{
				@Override
				public void execute(Employee model, Date hireDate) {
					model.setHireDate(hireDate);
				}
			}

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
			this.firePropertyChanged(ID_PROPERTY, old, this.id = id);
		}
		String getName() {
			return this.name;
		}
		void setName(String name) {
			Object old = this.name;
			this.firePropertyChanged(NAME_PROPERTY, old, this.name = name);
		}
		Date getHireDate() {
			return this.hireDate;
		}
		void setHireDate(Date hireDate) {
			Object old = this.hireDate;
			this.firePropertyChanged(HIRE_DATE_PROPERTY, old, this.hireDate = hireDate);
		}
		@Override
		public void toString(StringBuilder sb) {
			sb.append(this.name);
		}
	}
}
