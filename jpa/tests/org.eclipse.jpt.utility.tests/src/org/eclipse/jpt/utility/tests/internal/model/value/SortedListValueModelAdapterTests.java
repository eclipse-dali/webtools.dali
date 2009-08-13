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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.ReverseComparator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class SortedListValueModelAdapterTests extends TestCase {
	private SortedListValueModelAdapter<String> adapter;
	private SimpleCollectionValueModel<String> wrappedCollectionHolder;
	private Collection<String> wrappedCollection;

	
	public SortedListValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wrappedCollection = new HashBag<String>();
		this.wrappedCollectionHolder = new SimpleCollectionValueModel<String>(this.wrappedCollection);
		this.adapter = new SortedListValueModelAdapter<String>(this.wrappedCollectionHolder);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	private void verifyList(Collection<String> expected, ListValueModel<String> actual) {
		this.verifyList(expected, actual, null);
	}

	private void verifyList(Collection<String> expected, ListValueModel<String> actual, Comparator<String> comparator) {
		Collection<String> sortedSet = new TreeSet<String>(comparator);
		sortedSet.addAll(expected);
		List<String> expectedList = new ArrayList<String>(sortedSet);
		List<String> actualList = CollectionTools.list(actual.iterator());
		assertEquals(expectedList, actualList);
	}

	public void testAdd() {
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			@Override
			public void itemsAdded(ListAddEvent e) {/* OK */}
			@Override
			public void itemsReplaced(ListReplaceEvent e) {/* OK */}
		});
		this.wrappedCollectionHolder.add("foo");
		this.wrappedCollectionHolder.add("bar");
		this.wrappedCollectionHolder.add("baz");
		assertEquals(3, this.adapter.size());
		this.verifyList(this.wrappedCollection, this.adapter);
	}

	public void testAddItem() {
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

		this.verifyList(this.wrappedCollection, this.adapter);
		assertEquals(this.wrappedCollection, CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection, synchCollection);
	}

	public void testRemoveItem() {
		List<String> synchList = new CoordinatedList<String>(this.adapter);
		Bag<String> synchCollection = new CoordinatedBag<String>(this.wrappedCollectionHolder);
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
			@Override
			public void itemsAdded(ListAddEvent e) {/* OK */}
			@Override
			public void itemsRemoved(ListRemoveEvent e) {/* OK */}
			@Override
			public void itemsReplaced(ListReplaceEvent e) {/* OK */}
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

		this.verifyList(this.wrappedCollection, this.adapter);
		assertEquals(this.wrappedCollection, CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection, synchCollection);

		this.adapter.setComparator(new ReverseComparator<String>());
		this.verifyList(this.wrappedCollection, this.adapter, new ReverseComparator<String>());
		assertEquals(this.wrappedCollection, CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection, synchCollection);
	}

	public void testHasListeners() {
		assertFalse(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		CoordinatedList<String> synchList = new CoordinatedList<String>(this.adapter);
		assertTrue(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.adapter.removeListChangeListener(ListValueModel.LIST_VALUES, synchList);
		assertFalse(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ChangeListener cl = new ChangeAdapter();
		this.adapter.addChangeListener(cl);
		assertTrue(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.adapter.removeChangeListener(cl);
		assertFalse(((AbstractModel) this.adapter).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

	public void testCollectionChange() {
		this.wrappedCollectionHolder.add("fred");
		this.adapter.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			@Override
			public void listChanged(ListChangeEvent e) {/* OK */}
		});
		this.wrappedCollectionHolder.setValues(Arrays.asList(new String[] {"foo", "bar", "baz"}));
		assertEquals(3, this.adapter.size());
		this.verifyList(this.wrappedCollection, this.adapter);
	}

	class TestListChangeListener implements ListChangeListener {
		public void itemsAdded(ListAddEvent e) {
			fail("unexpected event");
		}
		public void itemsRemoved(ListRemoveEvent e) {
			fail("unexpected event");
		}
		public void itemsReplaced(ListReplaceEvent e) {
			fail("unexpected event");
		}
		public void itemsMoved(ListMoveEvent e) {
			fail("unexpected event");
		}
		public void listCleared(ListClearEvent e) {
			fail("unexpected event");
		}
		public void listChanged(ListChangeEvent e) {
			fail("unexpected event");
		}
	}

}
