/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.ReverseComparator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelWrapper;
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
public class SortedListValueModelWrapperTests extends TestCase {
	private List<String> list;
	private SimpleListValueModel<String> listModel;
	private SortedListValueModelWrapper<String> sortedListModel;

	
	public SortedListValueModelWrapperTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.list = new ArrayList<String>();
		this.listModel = new SimpleListValueModel<String>(this.list);
		this.sortedListModel = new SortedListValueModelWrapper<String>(this.listModel);
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
		List<String> actualList = CollectionTools.list(actual);
		assertEquals(expectedList, actualList);
	}

	public void testAdd() {
		this.sortedListModel.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			@Override
			public void itemsAdded(ListAddEvent e) {/* OK */}
			@Override
			public void itemsReplaced(ListReplaceEvent e) {/* OK */}
		});
		this.listModel.add("foo");
		this.listModel.add("bar");
		this.listModel.add("baz");
		assertEquals(3, this.sortedListModel.size());
		this.verifyList(this.list, this.sortedListModel);
	}

	public void testAddItem() {
		List<String> sortedSynchList = new CoordinatedList<String>(this.sortedListModel);
		List<String> synchList = new CoordinatedList<String>(this.listModel);
		this.listModel.add("foo");
		assertTrue(this.list.contains("foo"));
		this.listModel.add("bar");
		this.listModel.add("baz");
		this.listModel.add("joo");
		this.listModel.add("jar");
		this.listModel.add("jaz");
		assertEquals(6, this.list.size());

		this.verifyList(this.list, this.sortedListModel);
		assertEquals(this.list, synchList);
		assertEquals(CollectionTools.list(this.sortedListModel), sortedSynchList);
	}

	public void testRemoveItem() {
		List<String> sortedSynchList = new CoordinatedList<String>(this.sortedListModel);
		List<String> synchList = new CoordinatedList<String>(this.listModel);
		this.listModel.add("foo");
		this.listModel.add("bar");
		this.listModel.add("baz");
		this.listModel.add("joo");
		this.listModel.add("jar");
		this.listModel.add("jaz");
		this.listModel.remove("jaz");
		assertFalse(this.list.contains("jaz"));
		this.listModel.remove("foo");
		assertFalse(this.list.contains("foo"));
		assertEquals(4, this.list.size());

		this.verifyList(this.list, this.sortedListModel);
		assertEquals(this.list, synchList);
		assertEquals(CollectionTools.list(this.sortedListModel), sortedSynchList);
	}

	public void testReplaceItem() {
		List<String> sortedSynchList = new CoordinatedList<String>(this.sortedListModel);
		List<String> synchList = new CoordinatedList<String>(this.listModel);
		this.listModel.add("foo");
		assertTrue(this.list.contains("foo"));
		this.listModel.add("bar");
		this.listModel.add("baz");
		this.listModel.add("joo");
		this.listModel.add("jar");
		this.listModel.add("jaz");
		assertEquals(6, this.list.size());
		this.listModel.set(3, "ttt");
		this.listModel.set(4, "xxx");
		assertTrue(this.list.contains("xxx"));

		this.verifyList(this.list, this.sortedListModel);
		assertEquals(this.list, synchList);
		assertEquals(CollectionTools.list(this.sortedListModel), sortedSynchList);
	}

	public void testListSynch() {
		this.sortedListModel.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			@Override
			public void itemsAdded(ListAddEvent e) {/* OK */}
			@Override
			public void itemsRemoved(ListRemoveEvent e) {/* OK */}
			@Override
			public void itemsReplaced(ListReplaceEvent e) {/* OK */}
		});
		this.listModel.add("foo");
		this.listModel.add("bar");
		this.listModel.add("baz");
		this.listModel.add("joo");
		this.listModel.add("jar");
		this.listModel.add("jaz");
		this.listModel.remove("jaz");
		assertFalse(this.list.contains("jaz"));
		this.listModel.remove("foo");
		assertFalse(this.list.contains("foo"));
		assertEquals(4, this.list.size());

		this.verifyList(this.list, this.sortedListModel);
	}

	public void testSetComparator() {
		List<String> sortedSynchList = new CoordinatedList<String>(this.sortedListModel);
		List<String> synchList = new CoordinatedList<String>(this.listModel);
		this.listModel.add("foo");
		assertTrue(this.list.contains("foo"));
		this.listModel.add("bar");
		this.listModel.add("baz");
		this.listModel.add("joo");
		this.listModel.add("jar");
		this.listModel.add("jaz");
		assertEquals(6, this.list.size());

		this.verifyList(this.list, this.sortedListModel);
		assertEquals(this.list, synchList);
		assertEquals(CollectionTools.list(this.sortedListModel), sortedSynchList);

		this.sortedListModel.setComparator(new ReverseComparator<String>());
		this.verifyList(this.list, this.sortedListModel, new ReverseComparator<String>());
		assertEquals(this.list, synchList);
	}

	public void testHasListeners() {
		assertFalse(((AbstractModel) this.sortedListModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		CoordinatedList<String> sortedSynchList = new CoordinatedList<String>(this.sortedListModel);
		assertTrue(((AbstractModel) this.sortedListModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.sortedListModel.removeListChangeListener(ListValueModel.LIST_VALUES, sortedSynchList);
		assertFalse(((AbstractModel) this.sortedListModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ChangeListener cl = new ChangeAdapter();
		this.sortedListModel.addChangeListener(cl);
		assertTrue(((AbstractModel) this.sortedListModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.sortedListModel.removeChangeListener(cl);
		assertFalse(((AbstractModel) this.sortedListModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

	public void testListChange() {
		this.listModel.add("fred");
		this.sortedListModel.addListChangeListener(ListValueModel.LIST_VALUES, new TestListChangeListener() {
			@Override
			public void listChanged(ListChangeEvent e) {/* OK */}
		});
		this.listModel.setListValues(Arrays.asList(new String[] {"foo", "bar", "baz"}));
		assertEquals(3, this.sortedListModel.size());
		this.verifyList(this.list, this.sortedListModel);
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
