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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JList;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ListModelAdapter;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class PropertyListValueModelAdapterTests extends TestCase {
	private ListValueModel<String> adapter;
	private WritablePropertyValueModel<String> wrappedValueHolder;

	public PropertyListValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wrappedValueHolder = new SimplePropertyValueModel<String>();
		this.adapter = new PropertyListValueModelAdapter<String>(this.wrappedValueHolder);
	}

	private Collection<String> wrappedList() {
		return CollectionTools.list(new SingleElementIterator<String>(this.wrappedValueHolder.value()));
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListener() {
			@Override
			public void itemsAdded(ListChangeEvent event) {/* OK */}
		});
		assertFalse(this.adapter.iterator().hasNext());
		this.wrappedValueHolder.setValue("foo");
		List<String> adapterList = CollectionTools.list(this.adapter.iterator());
		assertEquals(1, adapterList.size());
		assertEquals(this.wrappedList(), adapterList);
		assertEquals("foo", adapterList.iterator().next());
	}

	public void testGetInt() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListener() {
			@Override
			public void itemsAdded(ListChangeEvent event) {/* OK */}
		});
		this.wrappedValueHolder.setValue("foo");
		assertEquals("foo", this.adapter.get(0));
	}

	public void testToArray1() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListener() {
			@Override
			public void itemsAdded(ListChangeEvent event) {/* OK */}
		});
		this.wrappedValueHolder.setValue("foo");
		Object[] array = this.adapter.toArray();
		assertEquals("foo", array[0]);
		assertEquals(1, array.length);
	}

	public void testToArray2() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListener());
		Object[] array = this.adapter.toArray();
		assertEquals(0, array.length);
	}

	public void testStaleValue() {
		ListChangeListener listener = new TestListener() {
			@Override
			public void itemsAdded(ListChangeEvent event) {/* OK */}
		};
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		this.wrappedValueHolder.setValue("foo");
		List<String> adapterList = CollectionTools.list(this.adapter.iterator());
		assertEquals(1, adapterList.size());
		assertEquals(this.wrappedList(), adapterList);
		assertEquals("foo", adapterList.iterator().next());

		this.adapter.removeListChangeListener(ListValueModel.LIST_VALUES, listener);
		adapterList = CollectionTools.list(this.adapter.iterator());
		assertEquals(0, adapterList.size());
		assertEquals(new ArrayList<String>(), adapterList);

		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		adapterList = CollectionTools.list(this.adapter.iterator());
		assertEquals(1, adapterList.size());
		assertEquals(this.wrappedList(), adapterList);
		assertEquals("foo", adapterList.iterator().next());
	}

	public void testHasListeners() {
		assertFalse(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		CoordinatedList<String> synchList = new CoordinatedList<String>(this.adapter);
		assertTrue(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.adapter.removeListChangeListener(ListValueModel.LIST_VALUES, synchList);
		assertFalse(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.adapter.addListChangeListener(synchList);
		assertTrue(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.adapter.removeListChangeListener(synchList);
		assertFalse(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

	public void testListChangedToEmpty() {
		this.wrappedValueHolder.setValue("foo");
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListener() {
			@Override
			public void itemsRemoved(ListChangeEvent event) {/* OK */}
		});
		JList jList = new JList(new ListModelAdapter(this.adapter));
		this.wrappedValueHolder.setValue(null);
		assertEquals(0, jList.getModel().getSize());
	}

	public void testListChangedFromEmpty() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListener() {
			@Override
			public void itemsAdded(ListChangeEvent event) {/* OK */}
		});
		JList jList = new JList(new ListModelAdapter(this.adapter));
		
		this.wrappedValueHolder.setValue("foo");
		assertEquals(1, jList.getModel().getSize());
	}
	
	public void testListItemChanged() {
		this.wrappedValueHolder.setValue("foo");
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListener() {
			@Override
			public void itemsReplaced(ListChangeEvent event) {/* OK */}
		});
		JList jList = new JList(new ListModelAdapter(this.adapter));
		assertEquals(1, jList.getModel().getSize());
		assertEquals("foo", jList.getModel().getElementAt(0));
		
		this.wrappedValueHolder.setValue("bar");
		assertEquals(1, jList.getModel().getSize());
		assertEquals("bar", jList.getModel().getElementAt(0));
	}
	
	public void testListChangedFromEmptyToEmpty() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListener());
		JList jList = new JList(new ListModelAdapter(this.adapter));
		
		this.wrappedValueHolder.setValue(null);
		assertEquals(0, jList.getModel().getSize());
	}


	// ********** member class **********
	
	static class TestListener implements ListChangeListener {
		public void listChanged(ListChangeEvent event) {
			fail("unexpected event");
		}
		public void listCleared(ListChangeEvent event) {
			fail("unexpected event");
		}
		public void itemsAdded(ListChangeEvent event) {
			fail("unexpected event");
		}
		public void itemsRemoved(ListChangeEvent event) {
			fail("unexpected event");
		}
		public void itemsMoved(ListChangeEvent event) {
			fail("unexpected event");
		}
		public void itemsReplaced(ListChangeEvent event) {
			fail("unexpected event");
		}
	}

}
