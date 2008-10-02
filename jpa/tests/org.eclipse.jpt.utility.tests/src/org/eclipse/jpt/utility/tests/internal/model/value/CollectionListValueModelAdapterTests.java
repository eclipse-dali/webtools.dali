/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import java.util.Collection;
import java.util.List;
import javax.swing.JList;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ListModelAdapter;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

public class CollectionListValueModelAdapterTests extends TestCase {
	private ListValueModel<String> adapter;
	private SimpleCollectionValueModel<String> wrappedCollectionHolder;
	private Collection<String> wrappedCollection;

	public CollectionListValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wrappedCollection = new HashBag<String>();
		this.wrappedCollectionHolder = new SimpleCollectionValueModel<String>(this.wrappedCollection);
		this.adapter = new CollectionListValueModelAdapter<String>(this.wrappedCollectionHolder);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			@Override
			public void itemsAdded(ListChangeEvent e) {/* OK */}
		});
		this.wrappedCollectionHolder.add("foo");
		this.wrappedCollectionHolder.add("bar");
		this.wrappedCollectionHolder.add("baz");
		Collection<String> adapterCollection = this.adapterCollection();
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection, adapterCollection);
	}

	private Collection<String> adapterCollection() {
		return CollectionTools.collection(this.adapter.iterator());
	}

	public void testStaleValue() {
		ListChangeListener listener = new TestListChangeListener() {
			@Override
			public void itemsAdded(ListChangeEvent e) {/* OK */}
		};
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		this.wrappedCollectionHolder.add("foo");
		this.wrappedCollectionHolder.add("bar");
		this.wrappedCollectionHolder.add("baz");
		Collection<String> adapterCollection = this.adapterCollection();
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection, adapterCollection);

		this.adapter.removeListChangeListener(ListValueModel.LIST_VALUES, listener);
		adapterCollection = this.adapterCollection();
		assertEquals(0, adapterCollection.size());
		assertEquals(new HashBag<String>(), adapterCollection);

		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		adapterCollection = this.adapterCollection();
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection, adapterCollection);
	}

	public void testAdd() {
		List<String> synchList = new CoordinatedList<String>(this.adapter);
		Bag<String> synchCollection = new CoordinatedBag<String>(this.wrappedCollectionHolder);
		this.wrappedCollectionHolder.add("foo");
		assertTrue(this.wrappedCollection.contains("foo"));
		this.wrappedCollectionHolder.add("bar");
		this.wrappedCollectionHolder.add("baz");
		this.wrappedCollectionHolder.add("joo");
		this.wrappedCollectionHolder.add("jar");
		this.wrappedCollectionHolder.add("jaz");
		assertEquals(6, this.wrappedCollection.size());

		Collection<String> adapterCollection = this.adapterCollection();
		assertEquals(this.wrappedCollection, adapterCollection);
		assertEquals(this.wrappedCollection, CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection, synchCollection);
	}

	public void testRemove() {
		List<String> synchList = new CoordinatedList<String>(this.adapter);
		Bag<String> synchCollection = new CoordinatedBag<String>(this.wrappedCollectionHolder);
		this.wrappedCollectionHolder.add("foo");
		this.wrappedCollectionHolder.add("bar");
		this.wrappedCollectionHolder.add("baz");
		this.wrappedCollectionHolder.add("joo");
		this.wrappedCollectionHolder.add("jar");
		this.wrappedCollectionHolder.add("jaz");
		assertTrue(this.wrappedCollection.contains("jaz"));
		this.wrappedCollectionHolder.remove("jaz");
		assertFalse(this.wrappedCollection.contains("jaz"));
		this.wrappedCollectionHolder.remove("foo");
		assertFalse(this.wrappedCollection.contains("foo"));
		assertEquals(4, this.wrappedCollection.size());

		Collection<String> adapterCollection = this.adapterCollection();
		assertEquals(this.wrappedCollection, adapterCollection);
		assertEquals(this.wrappedCollection, CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection, synchCollection);
	}

	public void testListSynch() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			@Override
			public void itemsAdded(ListChangeEvent e) {/* OK */}
			@Override
			public void itemsRemoved(ListChangeEvent e) {/* OK */}
		});
		this.wrappedCollectionHolder.add("foo");
		this.wrappedCollectionHolder.add("bar");
		this.wrappedCollectionHolder.add("baz");
		this.wrappedCollectionHolder.add("joo");
		this.wrappedCollectionHolder.add("jar");
		this.wrappedCollectionHolder.add("jaz");
		this.wrappedCollectionHolder.remove("jaz");
		assertFalse(this.wrappedCollection.contains("jaz"));
		this.wrappedCollectionHolder.remove("foo");
		assertFalse(this.wrappedCollection.contains("foo"));
		assertEquals(4, this.wrappedCollection.size());

		Collection<String> adapterCollection = this.adapterCollection();
		assertEquals(this.wrappedCollection, adapterCollection);
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

	public void testCollectionChangedToEmpty() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			@Override
			public void itemsAdded(ListChangeEvent e) {/* OK */}
			@Override
			public void itemsRemoved(ListChangeEvent e) {/* OK */}
		});
		this.wrappedCollectionHolder.add("foo");
		this.wrappedCollectionHolder.add("bar");
		this.wrappedCollectionHolder.add("baz");
		JList jList = new JList(new ListModelAdapter(this.adapter));
		this.wrappedCollectionHolder.setCollection(new HashBag<String>());
		assertEquals(0, jList.getModel().getSize());
	}
	
	public void testCollectionChangedFromEmpty() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			@Override
			public void itemsAdded(ListChangeEvent e) {/* OK */}
			@Override
			public void itemsRemoved(ListChangeEvent e) {/* OK */}
		});
		JList jList = new JList(new ListModelAdapter(this.adapter));
		
		HashBag<String> bag = new HashBag<String>();
		bag.add("foo");
		bag.add("bar");
		this.wrappedCollectionHolder.setCollection(bag);
		assertEquals(2, jList.getModel().getSize());
	}
	
	public void testCollectionChangedFromEmptyToEmpty() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			@Override
			public void itemsAdded(ListChangeEvent e) {/* OK */}
			@Override
			public void itemsRemoved(ListChangeEvent e) {/* OK */}
		});
		JList jList = new JList(new ListModelAdapter(this.adapter));
		
		HashBag<String> bag = new HashBag<String>();
		this.wrappedCollectionHolder.setCollection(bag);
		assertEquals(0, jList.getModel().getSize());
	}


	class TestListChangeListener implements ListChangeListener {
		public void itemsAdded(ListChangeEvent e) {
			fail("unexpected event");
		}
		public void itemsRemoved(ListChangeEvent e) {
			fail("unexpected event");
		}
		public void itemsReplaced(ListChangeEvent e) {
			fail("unexpected event");
		}
		public void itemsMoved(ListChangeEvent e) {
			fail("unexpected event");
		}
		public void listCleared(ListChangeEvent e) {
			fail("unexpected event");
		}
		public void listChanged(ListChangeEvent e) {
			fail("unexpected event");
		}
	}

}
