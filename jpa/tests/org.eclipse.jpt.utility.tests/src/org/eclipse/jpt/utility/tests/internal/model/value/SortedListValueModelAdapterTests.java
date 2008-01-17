/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.ReverseComparator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class SortedListValueModelAdapterTests extends TestCase {
	private SortedListValueModelAdapter adapter;
	private SimpleCollectionValueModel wrappedCollectionHolder;
	private Collection wrappedCollection;

	
	public SortedListValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wrappedCollection = new HashBag();
		this.wrappedCollectionHolder = new SimpleCollectionValueModel(this.wrappedCollection);
		this.adapter = new SortedListValueModelAdapter(this.wrappedCollectionHolder);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	private void verifyList(Collection expected, ListValueModel actual) {
		this.verifyList(expected, actual, null);
	}

	private void verifyList(Collection expected, ListValueModel actual, Comparator comparator) {
		Collection sortedSet = new TreeSet(comparator);
		sortedSet.addAll(expected);
		List expectedList = new ArrayList(sortedSet);
		List actualList = CollectionTools.list(actual.iterator());
		assertEquals(expectedList, actualList);
	}

	public void testAdd() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {/* OK */}
			public void itemsReplaced(ListChangeEvent e) {/* OK */}
		});
		this.wrappedCollectionHolder.add("foo");
		this.wrappedCollectionHolder.add("bar");
		this.wrappedCollectionHolder.add("baz");
		assertEquals(3, this.adapter.size());
		this.verifyList(this.wrappedCollection, this.adapter);
	}

	public void testAddItem() {
		List synchList = new CoordinatedList(this.adapter);
		Bag synchCollection = new CoordinatedBag(this.wrappedCollectionHolder);
		this.wrappedCollectionHolder.add("foo");
		assertTrue(this.wrappedCollection.contains("foo"));
		this.wrappedCollectionHolder.add("bar");
		this.wrappedCollectionHolder.add("baz");
		this.wrappedCollectionHolder.add("joo");
		this.wrappedCollectionHolder.add("jar");
		this.wrappedCollectionHolder.add("jaz");
		assertEquals(6, this.wrappedCollection.size());

		this.verifyList(this.wrappedCollection, this.adapter);
		assertEquals(this.wrappedCollection, CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection, synchCollection);
	}

	public void testRemoveItem() {
		List synchList = new CoordinatedList(this.adapter);
		Bag synchCollection = new CoordinatedBag(this.wrappedCollectionHolder);
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

		this.verifyList(this.wrappedCollection, this.adapter);
		assertEquals(this.wrappedCollection, CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection, synchCollection);
	}

	public void testListSynch() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {/* OK */}
			public void itemsRemoved(ListChangeEvent e) {/* OK */}
			public void itemsReplaced(ListChangeEvent e) {/* OK */}
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

		this.verifyList(this.wrappedCollection, this.adapter);
	}

	public void testSetComparator() {
		List synchList = new CoordinatedList(this.adapter);
		Bag synchCollection = new CoordinatedBag(this.wrappedCollectionHolder);
		this.wrappedCollectionHolder.add("foo");
		assertTrue(this.wrappedCollection.contains("foo"));
		this.wrappedCollectionHolder.add("bar");
		this.wrappedCollectionHolder.add("baz");
		this.wrappedCollectionHolder.add("joo");
		this.wrappedCollectionHolder.add("jar");
		this.wrappedCollectionHolder.add("jaz");
		assertEquals(6, this.wrappedCollection.size());

		this.verifyList(this.wrappedCollection, this.adapter);
		assertEquals(this.wrappedCollection, CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection, synchCollection);

		this.adapter.setComparator(new ReverseComparator());
		this.verifyList(this.wrappedCollection, this.adapter, new ReverseComparator());
		assertEquals(this.wrappedCollection, CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection, synchCollection);
	}

	public void testHasListeners() {
		assertFalse(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		CoordinatedList synchList = new CoordinatedList(this.adapter);
		assertTrue(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.adapter.removeListChangeListener(ListValueModel.LIST_VALUES, synchList);
		assertFalse(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.adapter.addListChangeListener(synchList);
		assertTrue(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.adapter.removeListChangeListener(synchList);
		assertFalse(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

	private class TestListChangeListener implements ListChangeListener {
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
