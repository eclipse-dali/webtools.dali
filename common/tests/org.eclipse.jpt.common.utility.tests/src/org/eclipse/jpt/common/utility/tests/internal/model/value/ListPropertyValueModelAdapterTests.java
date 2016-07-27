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

import java.util.Arrays;
import java.util.List;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListTransformationPluggablePropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class ListPropertyValueModelAdapterTests
	extends TestCase
{
	private PropertyValueModel<Boolean> adapter;
	private SimpleListValueModel<String> listModel;
	PropertyChangeEvent event;

	public ListPropertyValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listModel = new SimpleListValueModel<>();
		this.adapter = ListValueModelTools.propertyValueModel(this.listModel, new LocalTransformer(2, "666"));
		this.event = null;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	private boolean booleanValue() {
		Boolean value = this.adapter.getValue();
		return (value != null) && value.booleanValue();
	}

	private boolean listModelContains(int index, String value) {
		return (this.listModel.size() > index) && ObjectTools.equals(this.listModel.get(index), value);
	}

	public void testValue() {
		assertNull(this.adapter.getValue());
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		});
		assertFalse(this.booleanValue());
		assertFalse(this.listModelContains(2, "666"));

		this.listModel.add("111");
		assertFalse(this.booleanValue());

		this.listModel.add("222");
		assertFalse(this.booleanValue());

		this.listModel.add("666");
		assertTrue(this.booleanValue());
		assertTrue(this.listModelContains(2, "666"));

		this.listModel.remove("666");
		assertFalse(this.booleanValue());
		assertFalse(this.listModelContains(2, "666"));

		this.listModel.add("666");
		assertTrue(this.booleanValue());
		assertTrue(this.listModelContains(2, "666"));

		this.listModel.clear();
		assertFalse(this.booleanValue());
		assertFalse(this.listModelContains(2, "666"));

		this.listModel.add("111");
		this.listModel.add("222");
		this.listModel.add("666");
		assertTrue(this.booleanValue());
		assertTrue(this.listModelContains(2, "666"));

		this.listModel.set(2, "333");
		assertFalse(this.booleanValue());
		assertFalse(this.listModelContains(2, "666"));

		this.listModel.set(2, "666");
		assertTrue(this.booleanValue());
		assertTrue(this.listModelContains(2, "666"));

		this.listModel.move(0, 2);
		assertFalse(this.booleanValue());
		assertTrue(this.listModelContains(0, "666"));

		this.listModel.setListValues(Arrays.asList("111", "222", "666"));
		assertTrue(this.booleanValue());
		assertTrue(this.listModelContains(2, "666"));
	}

	public void testEventFiring() {
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				ListPropertyValueModelAdapterTests.this.event = e;
			}
		});
		assertNull(this.event);

		this.listModel.add("111");
		assertNull(this.event);

		this.listModel.add("222");
		assertNull(this.event);

		this.listModel.add("666");
		this.verifyEvent(false, true);

		this.listModel.remove("666");
		this.verifyEvent(true, false);

		this.listModel.add("666");
		this.verifyEvent(false, true);

		this.listModel.clear();
		this.verifyEvent(true, false);
	}

	private void verifyEvent(boolean oldValue, boolean newValue) {
		assertEquals(this.adapter, this.event.getSource());
		assertEquals(Boolean.valueOf(oldValue), this.event.getOldValue());
		assertEquals(Boolean.valueOf(newValue), this.event.getNewValue());
		this.event = null;
	}

	public void testStaleValue() {
		PropertyChangeListener listener = new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		};
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		this.listModel.add("111");
		this.listModel.add("222");
		this.listModel.add("666");
		assertTrue(this.booleanValue());
		assertTrue(this.listModelContains(2, "666"));

		this.adapter.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertFalse(this.booleanValue());
		assertTrue(this.listModelContains(2, "666"));

		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(this.booleanValue());
		assertTrue(this.listModelContains(2, "666"));
	}

	public void testHasListeners() {
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(((AbstractModel) this.listModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ChangeListener listener = new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		};
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.listModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.adapter.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(((AbstractModel) this.listModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.adapter.addChangeListener(listener);
		assertTrue(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.listModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.adapter.removeChangeListener(listener);
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(((AbstractModel) this.listModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

	public void testToString1() {
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		});
		assertTrue(this.adapter.toString().endsWith("(false)"));
		this.listModel.add("111");
		this.listModel.add("222");
		this.listModel.add("666");
		assertTrue(this.adapter.toString().endsWith("(true)"));
	}

	public void testToString3() {
		ListTransformationPluggablePropertyValueModelAdapter.Factory<String, Boolean> f = new ListTransformationPluggablePropertyValueModelAdapter.Factory<>(this.listModel, new LocalTransformer(2, "666"));
		assertTrue(f.toString().indexOf("Factory") != -1);
	}

	public void testToString4() {
		PropertyChangeListener listener = new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		};
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		Object a = ObjectTools.get(this.adapter, "adapter");
		Object l = ObjectTools.get(a, "listener");
		assertTrue(l.toString().indexOf("AdapterListener") != -1);
	}

	public void testCtor_NPE1A() {
		Object object;
		boolean exCaught = false;
		try {
			object = ListValueModelTools.propertyValueModel(null, new LocalTransformer(2, "666"));
			fail("bogus: " + object);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE1B() {
		Object object;
		boolean exCaught = false;
		try {
			object = ListValueModelTools.propertyValueModel(null, new LocalTransformer(2, "666"));
			fail("bogus: " + object);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE2A() {
		Object object;
		boolean exCaught = false;
		try {
			object = ListValueModelTools.propertyValueModel(this.listModel, null);
			fail("bogus: " + object);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE2B() {
		Object object;
		boolean exCaught = false;
		try {
			object = ListValueModelTools.propertyValueModel(this.listModel, null);
			fail("bogus: " + object);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE4() {
		Object object;
		boolean exCaught = false;
		try {
			object = PropertyValueModelTools.propertyValueModel(null);
			fail("bogus: " + object);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	// ********** member class **********

	/**
	 * Transform the list to <code>true</code> if it contains the specified item
	 * at the specified index, otherwise transform it to <code>false</code>.
	 */
	static class LocalTransformer
		implements Transformer<List<String>, Boolean>
	{
		private final int index;
		private final String item;

		LocalTransformer(int index, String item) {
			super();
			this.index = index;
			this.item = item;
		}

		public Boolean transform(List<String> list) {
			return Boolean.valueOf(this.transform_(list));
		}

		public boolean transform_(List<String> list) {
			return (list.size() > this.index) && ObjectTools.equals(this.item, list.get(this.index));
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.item);
		}
	}
}
