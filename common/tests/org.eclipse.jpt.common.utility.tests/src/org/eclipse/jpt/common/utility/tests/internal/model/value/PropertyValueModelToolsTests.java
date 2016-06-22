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
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class PropertyValueModelToolsTests
	extends TestCase
{

	public PropertyValueModelToolsTests(String name) {
		super(name);
	}

	public void testModifiablePropertyValueModel() {
		ModifiablePropertyValueModel<String> doubleStringModel = new SimplePropertyValueModel<>("foofoo");
		PluggableModifiablePropertyValueModel.Adapter.Factory<String> factory = new HalfStringModelAdapter.Factory(doubleStringModel);
		ModifiablePropertyValueModel<String> halfStringModel = PropertyValueModelTools.modifiablePropertyValueModel(factory);
		HalfStringListener halfStringListener = new HalfStringListener();
		halfStringModel.addPropertyChangeListener(PropertyValueModel.VALUE, halfStringListener);

		halfStringListener.event = null;
		assertEquals("foofoo", doubleStringModel.getValue());
		assertEquals("foo", halfStringModel.getValue());
		assertNull(halfStringListener.event);

		halfStringListener.event = null;
		halfStringModel.setValue("bar");
		assertEquals("bar", halfStringModel.getValue());
		assertEquals("barbar", doubleStringModel.getValue());
		assertEquals("bar", halfStringListener.event.getNewValue());

		halfStringListener.event = null;
		halfStringModel.setValue("bar");
		assertEquals("bar", halfStringModel.getValue());
		assertEquals("barbar", doubleStringModel.getValue());
		assertNull(halfStringListener.event);

		halfStringListener.event = null;
		doubleStringModel.setValue("xxxxxx");
		assertEquals("xxx", halfStringModel.getValue());
		assertEquals("xxxxxx", doubleStringModel.getValue());
		assertEquals("xxx", halfStringListener.event.getNewValue());

		halfStringListener.event = null;
		halfStringModel.removePropertyChangeListener(PropertyValueModel.VALUE, halfStringListener);
		assertNull(halfStringModel.getValue());
		assertEquals("xxxxxx", doubleStringModel.getValue());
		assertNull(halfStringListener.event);
	}

	public static class HalfStringListener
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
