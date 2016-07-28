/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.closure.ClosureAdapter;
import org.eclipse.jpt.common.utility.internal.exception.RuntimeExceptionHandler;
import org.eclipse.jpt.common.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggableModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PluggableModifiablePropertyValueModel.Adapter;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class PropertyValueModelToolsTests
	extends TestCase
{

	public PropertyValueModelToolsTests(String name) {
		super(name);
	}

	public void testValueIsNull() {
		ModifiablePropertyValueModel<String> stringModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueIsNull(stringModel);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("", stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue("foo");
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("foo", stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue(null);
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue("bar");
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("bar", stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertEquals("bar", stringModel.getValue());
		assertNull(listener.event);
	}

	public void testValueIsNotNull() {
		ModifiablePropertyValueModel<String> stringModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueIsNotNull(stringModel);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals("", stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue("foo");
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals("foo", stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue(null);
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue("bar");
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals("bar", stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertEquals("bar", stringModel.getValue());
		assertNull(listener.event);
	}

	public void testValueEquals() {
		String string = "foo";
		ModifiablePropertyValueModel<String> stringModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueEquals(stringModel, string);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("", stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue("foo");
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals("foo", stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue("bar");
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("bar", stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(null);
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);
	}

	public void testValueEquals_() {
		String string = "foo";
		ModifiablePropertyValueModel<String> stringModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueEquals_(stringModel, string);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("", stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue("foo");
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals("foo", stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue("bar");
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("bar", stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(null);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);
	}

	public void testValueNotEquals() {
		String string = "foo";
		ModifiablePropertyValueModel<String> stringModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueNotEquals(stringModel, string);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals("", stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue("foo");
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("foo", stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue("bar");
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals("bar", stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(null);
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);
	}

	public void testValueNotEquals_() {
		String string = "foo";
		ModifiablePropertyValueModel<String> stringModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueNotEquals_(stringModel, string);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals("", stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue("foo");
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("foo", stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue("bar");
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals("bar", stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(null);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);
	}

	public void testValueIsIdentical() {
		Object object0 = new Object();
		Object object1 = new Object();
		Object object2 = new Object();
		ModifiablePropertyValueModel<Object> stringModel = new SimplePropertyValueModel<>(object0);
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueIsIdentical(stringModel, object1);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals(object0, stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue(object1);
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals(object1, stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(object2);
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals(object2, stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(null);
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);
	}

	public void testValueIsIdentical_() {
		Object object0 = new Object();
		Object object1 = new Object();
		Object object2 = new Object();
		ModifiablePropertyValueModel<Object> stringModel = new SimplePropertyValueModel<>(object0);
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueIsIdentical_(stringModel, object1);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals(object0, stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue(object1);
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals(object1, stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(object2);
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals(object2, stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(null);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);
	}

	public void testValueIsNotIdentical() {
		Object object0 = new Object();
		Object object1 = new Object();
		Object object2 = new Object();
		ModifiablePropertyValueModel<Object> stringModel = new SimplePropertyValueModel<>(object0);
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueIsNotIdentical(stringModel, object1);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals(object0, stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue(object1);
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals(object1, stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(object2);
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals(object2, stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(null);
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);
	}

	public void testValueIsNotIdentical_() {
		Object object0 = new Object();
		Object object1 = new Object();
		Object object2 = new Object();
		ModifiablePropertyValueModel<Object> stringModel = new SimplePropertyValueModel<>(object0);
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueIsNotIdentical_(stringModel, object1);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals(object0, stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue(object1);
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals(object1, stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(object2);
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals(object2, stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(null);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);
	}

	public void testValueAffirms() {
		String string = "foo";
		ModifiablePropertyValueModel<String> stringModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<Boolean> booleanModel = PropertyValueModelTools.valueAffirms(stringModel, PredicateTools.isEqual(string), true);
		LocalListener listener = new LocalListener();
		booleanModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("", stringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.setValue("foo");
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertEquals("foo", stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue("bar");
		assertEquals(Boolean.FALSE, booleanModel.getValue());
		assertEquals("bar", stringModel.getValue());
		assertEquals(Boolean.FALSE, listener.event.getNewValue());

		listener.event = null;
		stringModel.setValue(null);
		assertEquals(Boolean.TRUE, booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertEquals(Boolean.TRUE, listener.event.getNewValue());

		listener.event = null;
		booleanModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(booleanModel.getValue());
		assertNull(stringModel.getValue());
		assertNull(listener.event);
	}

	public void testFilterModelClass() {
		Class<String> clazz = String.class;
		ModifiablePropertyValueModel<Object> objectModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<String> stringModel = PropertyValueModelTools.filter(objectModel, clazz);
		LocalListener listener = new LocalListener();
		stringModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals("", stringModel.getValue());
		assertEquals("", objectModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		objectModel.setValue("foo");
		assertEquals("foo", stringModel.getValue());
		assertEquals("foo", objectModel.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		objectModel.setValue(Integer.valueOf(0));
		assertNull(stringModel.getValue());
		assertEquals(Integer.valueOf(0), objectModel.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		objectModel.setValue(null);
		assertNull(stringModel.getValue());
		assertNull(objectModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(stringModel.getValue());
		assertNull(objectModel.getValue());
		assertNull(listener.event);
	}

	public void testFilterModelClassObject() {
		Class<String> clazz = String.class;
		ModifiablePropertyValueModel<Object> objectModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<String> stringModel = PropertyValueModelTools.filter(objectModel, clazz, "XXX");
		LocalListener listener = new LocalListener();
		stringModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals("", stringModel.getValue());
		assertEquals("", objectModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		objectModel.setValue("foo");
		assertEquals("foo", stringModel.getValue());
		assertEquals("foo", objectModel.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		objectModel.setValue(Integer.valueOf(0));
		assertEquals("XXX", stringModel.getValue());
		assertEquals(Integer.valueOf(0), objectModel.getValue());
		assertEquals("XXX", listener.event.getNewValue());

		listener.event = null;
		objectModel.setValue(null);
		assertNull(stringModel.getValue());
		assertNull(objectModel.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		stringModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(stringModel.getValue());
		assertNull(objectModel.getValue());
		assertNull(listener.event);
	}

	public void testFilter_ModelClassObject() {
		Class<String> clazz = String.class;
		ModifiablePropertyValueModel<Object> objectModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<String> stringModel = PropertyValueModelTools.filter_(objectModel, clazz, "XXX");
		LocalListener listener = new LocalListener();
		stringModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals("", stringModel.getValue());
		assertEquals("", objectModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		objectModel.setValue("foo");
		assertEquals("foo", stringModel.getValue());
		assertEquals("foo", objectModel.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		objectModel.setValue(Integer.valueOf(0));
		assertEquals("XXX", stringModel.getValue());
		assertEquals(Integer.valueOf(0), objectModel.getValue());
		assertEquals("XXX", listener.event.getNewValue());

		listener.event = null;
		objectModel.setValue(null);
		assertEquals("XXX", stringModel.getValue());
		assertNull(objectModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		stringModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(stringModel.getValue());
		assertNull(objectModel.getValue());
		assertNull(listener.event);
	}

	public void testFilterModelPredicate() {
		Predicate<String> predicate = new LengthIs(3);
		ModifiablePropertyValueModel<String> innerModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<String> outerModel = PropertyValueModelTools.filter(innerModel, predicate);
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertNull(outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("foo");
		assertEquals("foo", outerModel.getValue());
		assertEquals("foo", innerModel.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue("asdfasdf");
		assertNull(outerModel.getValue());
		assertEquals("asdfasdf", innerModel.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue(null);
		assertNull(outerModel.getValue());
		assertNull(innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("bar");
		assertEquals("bar", outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertNull(listener.event);
	}

	public void testFilter_ModelPredicate() {
		Predicate<String> predicate = new LengthIs_NullCheck(3);
		ModifiablePropertyValueModel<String> innerModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<String> outerModel = PropertyValueModelTools.filter_(innerModel, predicate);
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertNull(outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("foo");
		assertEquals("foo", outerModel.getValue());
		assertEquals("foo", innerModel.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue("asdfasdf");
		assertNull(outerModel.getValue());
		assertEquals("asdfasdf", innerModel.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue(null);
		assertNull(outerModel.getValue());
		assertNull(innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("bar");
		assertEquals("bar", outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertNull(listener.event);
	}

	public void testFilter_ModelPredicate_NPE() {
		Predicate<String> predicate = new LengthIs(3);
		ModifiablePropertyValueModel<String> innerModel = new LocalSimplePropertyValueModel<>("");
		PropertyValueModel<String> outerModel = PropertyValueModelTools.filter_(innerModel, predicate);
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertNull(outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("foo");
		assertEquals("foo", outerModel.getValue());
		assertEquals("foo", innerModel.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue("asdfasdf");
		assertNull(outerModel.getValue());
		assertEquals("asdfasdf", innerModel.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		boolean exCaught = true;
		try {
			innerModel.setValue(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		assertNull(outerModel.getValue()); // stays null...
		assertNull(innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("bar");
		assertEquals("bar", outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertNull(listener.event);
	}

	public void testFilterModelPredicateObject() {
		Predicate<String> predicate = new LengthIs(3);
		ModifiablePropertyValueModel<String> innerModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<String> outerModel = PropertyValueModelTools.filter(innerModel, predicate, "XXX");
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals("XXX", outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("foo");
		assertEquals("foo", outerModel.getValue());
		assertEquals("foo", innerModel.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue("asdfasdf");
		assertEquals("XXX", outerModel.getValue());
		assertEquals("XXX", listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue(null);
		assertEquals("XXX", outerModel.getValue());
		assertNull(innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("bar");
		assertEquals("bar", outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertNull(listener.event);
	}

	public void testFilter_ModelPredicateObject() {
		Predicate<String> predicate = new LengthIs_NullCheck(3);
		ModifiablePropertyValueModel<String> innerModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<String> outerModel = PropertyValueModelTools.filter_(innerModel, predicate, "XXX");
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals("XXX", outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("foo");
		assertEquals("foo", outerModel.getValue());
		assertEquals("foo", innerModel.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue("asdfasdf");
		assertEquals("XXX", outerModel.getValue());
		assertEquals("XXX", listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue(null);
		assertEquals("XXX", outerModel.getValue());
		assertNull(innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("bar");
		assertEquals("bar", outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertNull(listener.event);
	}

	public void testFilter_ModelPredicateObject_NPE() {
		Predicate<String> predicate = new LengthIs(3);
		ModifiablePropertyValueModel<String> innerModel = new LocalSimplePropertyValueModel<>("");
		PropertyValueModel<String> outerModel = PropertyValueModelTools.filter_(innerModel, predicate, "XXX");
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals("XXX", outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("foo");
		assertEquals("foo", outerModel.getValue());
		assertEquals("foo", innerModel.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue("asdfasdf");
		assertEquals("XXX", outerModel.getValue());
		assertEquals("XXX", listener.event.getNewValue());

		listener.event = null;
		listener.event = null;
		boolean exCaught = true;
		try {
			innerModel.setValue(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		assertEquals("XXX", outerModel.getValue()); // unchanged
		assertNull(innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("bar");
		assertEquals("bar", outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertNull(listener.event);
	}

	public void testNullCheck() {
		ModifiablePropertyValueModel<String> innerModel = new SimplePropertyValueModel<>("");
		PropertyValueModel<String> outerModel = PropertyValueModelTools.nullCheck(innerModel, "null");
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals("", outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("foo");
		assertEquals("foo", outerModel.getValue());
		assertEquals("foo", innerModel.getValue());
		assertEquals("foo", listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue("bar");
		assertEquals("bar", outerModel.getValue());
		assertEquals("bar", innerModel.getValue());
		assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue(null);
		assertEquals("null", outerModel.getValue());
		assertNull(innerModel.getValue());
		assertEquals("null", listener.event.getNewValue());

		listener.event = null;
		outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(outerModel.getValue());
		assertNull(innerModel.getValue());
		assertNull(listener.event);
	}

	public static class LengthIs
		extends PredicateAdapter<String>
	{
		private final int length;
		public LengthIs(int length) {
			super();
			this.length = length;
		}
		@Override
		public boolean evaluate(String string) {
			return string.length() == this.length;
		}
	}

	public static class LengthIs_NullCheck
		extends LengthIs
	{
		public LengthIs_NullCheck(int length) {
			super(length);
		}
		@Override
		public boolean evaluate(String string) {
			return (string != null) && super.evaluate(string);
		}
	}

	public static class LocalSimplePropertyValueModel<V>
		extends SimplePropertyValueModel<V>
	{
		public LocalSimplePropertyValueModel(V value) {
			super(value);
		}
		@Override
		protected ChangeSupport buildChangeSupport() {
			return new ChangeSupport(this, RuntimeExceptionHandler.instance());
		}
	}

	public void testTransformModelTransformerClosure() {
		ModifiablePropertyValueModel<String> innerModel = new SimplePropertyValueModel<>("foo");
		Closure<Integer> closure = new SetStringModelClosure(innerModel);
		ModifiablePropertyValueModel<Integer> outerModel = PropertyValueModelTools.transform(innerModel, LENGTH_TRANSFORMER, closure);
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Integer.valueOf(3), outerModel.getValue());
		assertEquals("foo", innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("");
		assertEquals(Integer.valueOf(0), outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertEquals(Integer.valueOf(0), listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue(null);
		assertNull(outerModel.getValue());
		assertNull(innerModel.getValue());
		assertNull(listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue("asdfasdf");
		assertEquals(Integer.valueOf(8), outerModel.getValue());
		assertEquals("asdfasdf", innerModel.getValue());
		assertEquals(Integer.valueOf(8), listener.event.getNewValue());

		listener.event = null;
		outerModel.setValue(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), outerModel.getValue());
		assertEquals("XXXXX", innerModel.getValue());
		assertEquals(Integer.valueOf(5), listener.event.getNewValue());

		listener.event = null;
		outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(outerModel.getValue());
		assertEquals("XXXXX", innerModel.getValue());
		assertNull(listener.event);
	}

	public void testTransform_ModelTransformerClosure() {
		ModifiablePropertyValueModel<String> innerModel = new LocalSimplePropertyValueModel<>("foo");
		Closure<Integer> closure = new SetStringModelClosure_NullCheck(innerModel);
		ModifiablePropertyValueModel<Integer> outerModel = PropertyValueModelTools.transform_(innerModel, NULL_CHECK_LENGTH_TRANSFORMER, closure);
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Integer.valueOf(3), outerModel.getValue());
		assertEquals("foo", innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("");
		assertEquals(Integer.valueOf(0), outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertEquals(Integer.valueOf(0), listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue(null);
		assertEquals(Integer.valueOf(0), outerModel.getValue());
		assertNull(innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("asdfasdf");
		assertEquals(Integer.valueOf(8), outerModel.getValue());
		assertEquals("asdfasdf", innerModel.getValue());
		assertEquals(Integer.valueOf(8), listener.event.getNewValue());

		listener.event = null;
		outerModel.setValue(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), outerModel.getValue());
		assertEquals("XXXXX", innerModel.getValue());
		assertEquals(Integer.valueOf(5), listener.event.getNewValue());

		listener.event = null;
		outerModel.setValue(null);
		assertEquals(Integer.valueOf(0), outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertEquals(Integer.valueOf(0), listener.event.getNewValue());

		listener.event = null;
		outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertNull(listener.event);
	}

	public void testTransform_ModelTransformerClosure_NPE() {
		ModifiablePropertyValueModel<String> innerModel = new LocalSimplePropertyValueModel<>("foo");
		Closure<Integer> closure = new SetStringModelClosure(innerModel);
		ModifiablePropertyValueModel<Integer> outerModel = PropertyValueModelTools.transform_(innerModel, NULL_CHECK_LENGTH_TRANSFORMER, closure);
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals(Integer.valueOf(3), outerModel.getValue());
		assertEquals("foo", innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("");
		assertEquals(Integer.valueOf(0), outerModel.getValue());
		assertEquals("", innerModel.getValue());
		assertEquals(Integer.valueOf(0), listener.event.getNewValue());

		listener.event = null;
		innerModel.setValue(null);
		assertEquals(Integer.valueOf(0), outerModel.getValue());
		assertNull(innerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("asdfasdf");
		assertEquals(Integer.valueOf(8), outerModel.getValue());
		assertEquals("asdfasdf", innerModel.getValue());
		assertEquals(Integer.valueOf(8), listener.event.getNewValue());

		listener.event = null;
		outerModel.setValue(Integer.valueOf(5));
		assertEquals(Integer.valueOf(5), outerModel.getValue());
		assertEquals("XXXXX", innerModel.getValue());
		assertEquals(Integer.valueOf(5), listener.event.getNewValue());

		listener.event = null;
		boolean exCaught = false;
		try {
			outerModel.setValue(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		assertEquals(Integer.valueOf(5), outerModel.getValue()); // unchanged
		assertEquals("XXXXX", innerModel.getValue()); // unchanged
		assertNull(listener.event);

		listener.event = null;
		outerModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(outerModel.getValue());
		assertEquals("XXXXX", innerModel.getValue());
		assertNull(listener.event);
	}

	public static final Transformer<String, Integer> LENGTH_TRANSFORMER = new LengthTransformer();
	public static class LengthTransformer
		extends TransformerAdapter<String, Integer>
	{
		@Override
		public Integer transform(String string) {
			return Integer.valueOf(string.length());
		}
	}

	public static final Transformer<String, Integer> NULL_CHECK_LENGTH_TRANSFORMER = new NullCheckLengthTransformer();
	public static class NullCheckLengthTransformer
		extends TransformerAdapter<String, Integer>
	{
		@Override
		public Integer transform(String string) {
			return Integer.valueOf((string != null) ? string.length() : 0);
		}
	}

	public static class SetStringModelClosure
		extends ClosureAdapter<Integer>
	{
		private final ModifiablePropertyValueModel<? super String> model;
		public SetStringModelClosure(ModifiablePropertyValueModel<? super String> model) {
			super();
			this.model = model;
		}
		@Override
		public void execute(Integer integer) {
			this.model.setValue(StringTools.repeat("X", integer.intValue()));
		}
	}

	public static class SetStringModelClosure_NullCheck
		extends SetStringModelClosure
	{
		public SetStringModelClosure_NullCheck(ModifiablePropertyValueModel<? super String> model) {
			super(model);
		}
		@Override
		public void execute(Integer integer) {
			if (integer == null) {
				integer = Integer.valueOf(0);
			}
			super.execute(integer);
		}
	}

	public void testModifiablePropertyValueModel() {
		ModifiablePropertyValueModel<String> doubleStringModel = new SimplePropertyValueModel<>("foofoo");
		PluggableModifiablePropertyValueModel.Adapter.Factory<String> factory = new HalfStringModelAdapter.Factory(doubleStringModel);
		ModifiablePropertyValueModel<String> halfStringModel = PropertyValueModelTools.modifiableModel(factory);
		LocalListener listener = new LocalListener();
		halfStringModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals("foofoo", doubleStringModel.getValue());
		
		assertEquals("foo", halfStringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		halfStringModel.setValue("bar");
		assertEquals("bar", halfStringModel.getValue());
		assertEquals("barbar", doubleStringModel.getValue());
		assertEquals("bar", listener.event.getNewValue());

		listener.event = null;
		halfStringModel.setValue("bar");
		assertEquals("bar", halfStringModel.getValue());
		assertEquals("barbar", doubleStringModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		doubleStringModel.setValue("xxxxxx");
		assertEquals("xxx", halfStringModel.getValue());
		assertEquals("xxxxxx", doubleStringModel.getValue());
		assertEquals("xxx", listener.event.getNewValue());

		listener.event = null;
		halfStringModel.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertNull(halfStringModel.getValue());
		assertEquals("xxxxxx", doubleStringModel.getValue());
		assertNull(listener.event);
	}

	public static class LocalListener
		implements PropertyChangeListener
	{
		public PropertyChangeEvent event;
		public void propertyChanged(PropertyChangeEvent e) {
			this.event = e;
		}
	}

	public static class HalfStringModelAdapter
		implements PluggableModifiablePropertyValueModel.Adapter<String>
	{
		private final ModifiablePropertyValueModel<String> stringModel;
		private final PluggableModifiablePropertyValueModel.Adapter.Listener<String> listener;
		private final PropertyChangeListener stringListener;

		public HalfStringModelAdapter(ModifiablePropertyValueModel<String> stringModel, PluggableModifiablePropertyValueModel.Adapter.Listener<String> listener) {
			super();
			this.stringModel = stringModel;
			this.listener = listener;
			this.stringListener = new StringListener();
		}

		public String engageModel() {
			this.stringModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.stringListener);
			String v = this.stringModel.getValue();
			return v.substring(v.length() / 2);
		}

		public void setValue(String value) {
			this.stringModel.setValue(value + value);
		}

		public String disengageModel() {
			this.stringModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.stringListener);
			return null;
		}

		void stringChanged(String newStringValue) {
			String newValue = newStringValue.substring(newStringValue.length() / 2);
			this.listener.valueChanged(newValue);
		}

		public class StringListener
			implements PropertyChangeListener
		{
			public void propertyChanged(PropertyChangeEvent event) {
				HalfStringModelAdapter.this.stringChanged((String) event.getNewValue());
			}
		}

		public static class Factory
			implements PluggableModifiablePropertyValueModel.Adapter.Factory<String>
		{
			private final ModifiablePropertyValueModel<String> stringModel;
			public Factory(ModifiablePropertyValueModel<String> stringModel) {
				super();
				this.stringModel = stringModel;
			}
			public Adapter<String> buildAdapter(PluggableModifiablePropertyValueModel.Adapter.Listener<String> listener) {
				return new HalfStringModelAdapter(this.stringModel, listener);
			}
		}
	}

	public void testPluggableModifiablePropertyValueModel_NPE() {
		PluggablePropertyValueModel.Adapter.Factory<String> factory = CollectionValueModelTools.transformationPluggablePropertyValueModelAdapterFactory(new SimpleCollectionValueModel<>(), new TransformerAdapter<>());
		Closure<String> closure = null;
		boolean exCaught = false;
		try {
			ModifiablePropertyValueModel<String> pvm = PropertyValueModelTools.pluggableModifiableModel(factory, closure);
			fail("bogus: " + pvm);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testTransformModifiablePropertyValueModel() {
		ModifiablePropertyValueModel<String> innerModel = new SimplePropertyValueModel<>("1");
		Transformer<String, Integer> getTransformer = new StringToIntegerTransformer();
		Transformer<Integer, String> setTransformer = new IntegerToStringTransformer();
		ModifiablePropertyValueModel<Integer> outerModel = PropertyValueModelTools.transform(innerModel, getTransformer, setTransformer);
		LocalListener listener = new LocalListener();
		outerModel.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		listener.event = null;
		assertEquals("1", innerModel.getValue());
		assertEquals(Integer.valueOf(1), outerModel.getValue());
		assertNull(listener.event);

		listener.event = null;
		innerModel.setValue("42");
		assertEquals("42", innerModel.getValue());
		assertEquals(Integer.valueOf(42), outerModel.getValue());
		assertEquals(Integer.valueOf(42), listener.event.getNewValue());

		listener.event = null;
		outerModel.setValue(Integer.valueOf(666));
		assertEquals("666", innerModel.getValue());
		assertEquals(Integer.valueOf(666), outerModel.getValue());
		assertEquals(Integer.valueOf(666), listener.event.getNewValue());
	}

	public static class StringToIntegerTransformer
		extends TransformerAdapter<String, Integer>
	{
		@Override
		public Integer transform(String input) {
			return Integer.valueOf(input);
		}
	}

	public static class IntegerToStringTransformer
		extends TransformerAdapter<Integer, String>
	{
		@Override
		public String transform(Integer input) {
			return input.toString();
		}
	}

	public void testNullCheckValueTransformer() {
		SimplePropertyValueModel<String> stringModel = new SimplePropertyValueModel<>("foo");
		Transformer<PropertyValueModel<String>, String> transformer = PropertyValueModelTools.valueTransformer();
		assertEquals("foo", transformer.transform(stringModel));
		assertNull(transformer.transform(null));
	}

	public void testNullCheckValueTransformerObject() {
		SimplePropertyValueModel<String> stringModel = new SimplePropertyValueModel<>("foo");
		Transformer<PropertyValueModel<String>, String> transformer = PropertyValueModelTools.valueTransformer("XXX");
		assertEquals("foo", transformer.transform(stringModel));
		assertEquals("XXX", transformer.transform(null));
	}

	public void testValueTransformer() {
		SimplePropertyValueModel<String> stringModel = new SimplePropertyValueModel<>("foo");
		Transformer<PropertyValueModel<String>, String> transformer = PropertyValueModelTools.valueTransformer_();
		assertEquals("foo", transformer.transform(stringModel));
	}

	public void testConstructor() {
		boolean exCaught = false;
		try {
			Object o = ClassTools.newInstance(PropertyValueModelTools.class);
			fail("bogus: " + o);
		} catch (RuntimeException ex) {
			if (ex.getCause() instanceof InvocationTargetException) {
				if (ex.getCause().getCause() instanceof UnsupportedOperationException) {
					exCaught = true;
				}
			}
		}
		assertTrue(exCaught);
	}
}
