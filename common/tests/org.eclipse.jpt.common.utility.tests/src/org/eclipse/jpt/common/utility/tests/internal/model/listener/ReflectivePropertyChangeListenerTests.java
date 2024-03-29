/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.listener;

import java.lang.reflect.Method;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.exception.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.listener.ReflectiveChangeListener;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;

@SuppressWarnings("nls")
public class ReflectivePropertyChangeListenerTests extends TestCase {
	
	public ReflectivePropertyChangeListenerTests(String name) {
		super(name);
	}

	public void testZeroArgumentNamedProperty() {
		TestModel testModel = new TestModel(7);
		Target target = new Target(testModel, TestModel.VALUE_PROPERTY, 7, 99);
		testModel.addPropertyChangeListener(TestModel.VALUE_PROPERTY, ReflectiveChangeListener.buildPropertyChangeListener(target, "propertyChangedZeroArgument"));
		testModel.setValue(99);
		assertTrue(target.zeroArgumentFlag);
		assertFalse(target.singleArgumentFlag);
	}

	/**
	 * test method that has more general method parameter type
	 */
	public void testSingleArgument2() throws Exception {
		TestModel testModel = new TestModel(7);
		Target target = new Target(testModel, TestModel.VALUE_PROPERTY, 7, 99);
		Method method = ObjectTools.method(target, "propertyChangedSingleArgument2", new Class[] {Object.class});
		testModel.addPropertyChangeListener(TestModel.VALUE_PROPERTY, ReflectiveChangeListener.buildPropertyChangeListener(target, method));
		testModel.setValue(99);
		assertFalse(target.zeroArgumentFlag);
		assertTrue(target.singleArgumentFlag);
	}

	public void testSingleArgumentNamedProperty() {
		TestModel testModel = new TestModel(7);
		Target target = new Target(testModel, TestModel.VALUE_PROPERTY, 7, 99);
		testModel.addPropertyChangeListener(TestModel.VALUE_PROPERTY, ReflectiveChangeListener.buildPropertyChangeListener(target, "propertyChangedSingleArgument"));
		testModel.setValue(99);
		assertFalse(target.zeroArgumentFlag);
		assertTrue(target.singleArgumentFlag);
	}

	/**
	 * test method that has more general method parameter type
	 */
	public void testSingleArgumentNamedProperty2() throws Exception {
		TestModel testModel = new TestModel(7);
		Target target = new Target(testModel, TestModel.VALUE_PROPERTY, 7, 99);
		Method method = ObjectTools.method(target, "propertyChangedSingleArgument2", new Class[] {Object.class});
		testModel.addPropertyChangeListener(TestModel.VALUE_PROPERTY, ReflectiveChangeListener.buildPropertyChangeListener(target, method));
		testModel.setValue(99);
		assertFalse(target.zeroArgumentFlag);
		assertTrue(target.singleArgumentFlag);
	}

	public void testListenerMismatch() {
		TestModel testModel = new TestModel(7);
		Target target = new Target(testModel, TestModel.VALUE_PROPERTY, 7, 99);
		// build a PROPERTY change listener and hack it so we
		// can add it as a STATE change listener
		Object listener = ReflectiveChangeListener.buildPropertyChangeListener(target, "propertyChangedSingleArgument");
		testModel.addStateChangeListener((StateChangeListener) listener);

		boolean exCaught = false;
		try {
			testModel.setValue(99);
			fail("listener mismatch: " + listener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testBogusDoubleArgument1() {
		TestModel testModel = new TestModel(7);
		Target target = new Target(testModel, TestModel.VALUE_PROPERTY, 7, 99);
		boolean exCaught = false;
		try {
			PropertyChangeListener listener = ReflectiveChangeListener.buildPropertyChangeListener(target, "stateChangedDoubleArgument");
			fail("bogus listener: " + listener);
		} catch (RuntimeException ex) {
			if (ex.getCause().getClass() == NoSuchMethodException.class) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}

	public void testBogusDoubleArgument2() throws Exception {
		TestModel testModel = new TestModel(7);
		Target target = new Target(testModel, TestModel.VALUE_PROPERTY, 7, 99);
		Method method = ObjectTools.method(target, "propertyChangedDoubleArgument", new Class[] {PropertyChangeEvent.class, Object.class});
		boolean exCaught = false;
		try {
			PropertyChangeListener listener = ReflectiveChangeListener.buildPropertyChangeListener(target, method);
			fail("bogus listener: " + listener);
		} catch (RuntimeException ex) {
			if (ex.getMessage().equals(method.toString())) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
	}


	class TestModel extends AbstractModel {
		private int value = 0;
			public static final String VALUE_PROPERTY = "value";
		TestModel(int value) {
			super();
			this.value = value;
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new ChangeSupport(this, RuntimeExceptionHandler.instance());
		}
		void setValue(int value) {
			int old = this.value;
			this.value = value;
			this.firePropertyChanged(VALUE_PROPERTY, old, value);
			if (old != value) {
				this.fireStateChanged();
			}
		}
	}

	class Target {
		TestModel testModel;
		String propertyName;
		Object oldValue;
		Object newValue;
		boolean zeroArgumentFlag = false;
		boolean singleArgumentFlag = false;
		Target(TestModel testModel, String propertyName, int oldValue, int newValue) {
			super();
			this.testModel = testModel;
			this.propertyName = propertyName;
			this.oldValue = new Integer(oldValue);
			this.newValue = new Integer(newValue);
		}
		void propertyChangedZeroArgument() {
			this.zeroArgumentFlag = true;
		}
		void propertyChangedSingleArgument(PropertyChangeEvent e) {
			this.singleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.propertyName, e.getPropertyName());
			assertEquals(this.oldValue, e.getOldValue());
			assertEquals(this.newValue, e.getNewValue());
		}
		void propertyChangedSingleArgument2(Object o) {
			PropertyChangeEvent e = (PropertyChangeEvent) o;
			this.singleArgumentFlag = true;
			assertSame(this.testModel, e.getSource());
			assertEquals(this.propertyName, e.getPropertyName());
			assertEquals(this.oldValue, e.getOldValue());
			assertEquals(this.newValue, e.getNewValue());
		}
		void propertyChangedDoubleArgument(PropertyChangeEvent e, Object o) {
			fail("bogus event: " + e + " - object: " + o);
		}
	}

}
