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
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PluggableModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PluggableModifiablePropertyValueModel.Adapter;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
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

	public void testModifiablePropertyValueModel() {
		ModifiablePropertyValueModel<String> doubleStringModel = new SimplePropertyValueModel<>("foofoo");
		PluggableModifiablePropertyValueModel.Adapter.Factory<String> factory = new HalfStringModelAdapter.Factory(doubleStringModel);
		ModifiablePropertyValueModel<String> halfStringModel = PropertyValueModelTools.modifiablePropertyValueModel(factory);
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
		private volatile String value;

		public HalfStringModelAdapter(ModifiablePropertyValueModel<String> stringModel, PluggableModifiablePropertyValueModel.Adapter.Listener<String> listener) {
			super();
			this.stringModel = stringModel;
			this.listener = listener;
			this.stringListener = new StringListener();
			this.value = null;
		}

		public void engageModel() {
			this.stringModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.stringListener);
			String v = this.stringModel.getValue();
			this.value = v.substring(v.length() / 2);
		}

		public String getValue() {
			return this.value;
		}

		public void setValue(String value) {
			this.value = value;
			this.stringModel.setValue(value + value);
		}

		public void disengageModel() {
			this.value = null;
			this.stringModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.stringListener);
		}

		void stringChanged(String newStringValue) {
			String newValue = newStringValue.substring(newStringValue.length() / 2);
			this.value = newValue;
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
		PluggablePropertyValueModel.Adapter.Factory<String> factory = CollectionValueModelTools.pluggablePropertyValueModelAdapterFactory(new SimpleCollectionValueModel<>(), new TransformerAdapter<>());
		Closure<String> closure = null;
		boolean exCaught = false;
		try {
			ModifiablePropertyValueModel<String> pvm = PropertyValueModelTools.pluggableModifiablePropertyValueModel(factory, closure);
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
