/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value.swing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.ListModel;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.swing.ListModelAdapter;
import org.eclipse.jpt.utility.tests.internal.model.value.SynchronizedList;

import junit.framework.TestCase;

public class ListModelAdapterTests extends TestCase {

	public ListModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// nothing yet...
	}

	@Override
	protected void tearDown() throws Exception {
		// nothing yet...
		super.tearDown();
	}

	private ListModelAdapter buildListModel(ListValueModel listHolder) {
		return new ListModelAdapter(listHolder) {
			@Override
			protected ListChangeListener buildListChangeListener() {
				return this.buildListChangeListener_();
			}
		};
	}

	private ListModel buildListModel(CollectionValueModel collectionHolder) {
		return new ListModelAdapter(collectionHolder) {
			@Override
			protected ListChangeListener buildListChangeListener() {
				return this.buildListChangeListener_();
			}
		};
	}

	public void testCollectionSynchronization() {
		SimpleCollectionValueModel collectionHolder = this.buildCollectionHolder();
		ListModel listModel = this.buildListModel(collectionHolder);
		SynchronizedList synchList = new SynchronizedList(listModel);
		assertEquals(6, synchList.size());
		this.compare(listModel, synchList);

		collectionHolder.add("tom");
		collectionHolder.add("dick");
		collectionHolder.add("harry");
		collectionHolder.add(null);
		assertEquals(10, synchList.size());
		this.compare(listModel, synchList);

		collectionHolder.remove("foo");
		collectionHolder.remove("jar");
		collectionHolder.remove("harry");
		collectionHolder.remove(null);
		assertEquals(6, synchList.size());
		this.compare(listModel, synchList);
	}

	public void testListSynchronization() {
		SimpleListValueModel listHolder = this.buildListHolder();
		ListModel listModel = this.buildListModel(listHolder);
		SynchronizedList synchList = new SynchronizedList(listModel);
		assertEquals(6, synchList.size());
		this.compare(listModel, synchList);

		listHolder.add(6, "tom");
		listHolder.add(7, "dick");
		listHolder.add(8, "harry");
		listHolder.add(9, null);
		assertEquals(10, synchList.size());
		this.compare(listModel, synchList);

		listHolder.remove(9);
		listHolder.remove(8);
		listHolder.remove(4);
		listHolder.remove(0);
		assertEquals(6, synchList.size());
		this.compare(listModel, synchList);
	}

	public void testSetModel() {
		SimpleListValueModel listHolder1 = this.buildListHolder();
		ListModelAdapter listModel = this.buildListModel(listHolder1);
		SynchronizedList synchList = new SynchronizedList(listModel);
		assertTrue(listHolder1.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertEquals(6, synchList.size());
		this.compare(listModel, synchList);

		SimpleListValueModel listHolder2 = this.buildListHolder2();
		listModel.setModel(listHolder2);
		assertEquals(3, synchList.size());
		this.compare(listModel, synchList);
		assertTrue(listHolder1.hasNoListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(listHolder2.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		listModel.setModel(new SimpleListValueModel());
		assertEquals(0, synchList.size());
		this.compare(listModel, synchList);
		assertTrue(listHolder1.hasNoListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(listHolder2.hasNoListChangeListeners(ListValueModel.LIST_VALUES));
	}

	private void compare(ListModel listModel, List list) {
		assertEquals(listModel.getSize(), list.size());
		for (int i = 0; i < listModel.getSize(); i++) {
			assertEquals(listModel.getElementAt(i), list.get(i));
		}
	}

	public void testCollectionSort() {
		this.verifyCollectionSort(null);
	}

	public void testListSort() {
		this.verifyListSort(null);
	}

	public void testCustomCollectionSort() {
		this.verifyCollectionSort(this.buildCustomComparator());
	}

	public void testCustomListSort() {
		this.verifyListSort(this.buildCustomComparator());
	}

	private Comparator buildCustomComparator() {
		// sort with reverse order
		return new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) o2).compareTo(o1);
			}
		};
	}

	private void verifyCollectionSort(Comparator comparator) {
		SimpleCollectionValueModel collectionHolder = this.buildCollectionHolder();
		ListModel listModel = this.buildListModel(new SortedListValueModelAdapter(collectionHolder, comparator));
		SynchronizedList synchList = new SynchronizedList(listModel);
		assertEquals(6, synchList.size());
		this.compareSort(listModel, synchList, comparator);

		collectionHolder.add("tom");
		collectionHolder.add("dick");
		collectionHolder.add("harry");
		assertEquals(9, synchList.size());
		this.compareSort(listModel, synchList, comparator);

		collectionHolder.remove("foo");
		collectionHolder.remove("jar");
		collectionHolder.remove("harry");
		assertEquals(6, synchList.size());
		this.compareSort(listModel, synchList, comparator);
	}

	private void verifyListSort(Comparator comparator) {
		SimpleListValueModel listHolder = this.buildListHolder();
		ListModel listModel = this.buildListModel(new SortedListValueModelAdapter(listHolder, comparator));
		SynchronizedList synchList = new SynchronizedList(listModel);
		assertEquals(6, synchList.size());
		this.compareSort(listModel, synchList, comparator);

		listHolder.add(0, "tom");
		listHolder.add(0, "dick");
		listHolder.add(0, "harry");
		assertEquals(9, synchList.size());
		this.compareSort(listModel, synchList, comparator);

		listHolder.remove(8);
		listHolder.remove(4);
		listHolder.remove(0);
		listHolder.remove(5);
		assertEquals(5, synchList.size());
		this.compareSort(listModel, synchList, comparator);
	}

	private void compareSort(ListModel listModel, List list, Comparator comparator) {
		SortedSet ss = new TreeSet(comparator);
		for (int i = 0; i < listModel.getSize(); i++) {
			ss.add(listModel.getElementAt(i));
		}
		assertEquals(ss.size(), list.size());
		for (Iterator stream1 = ss.iterator(), stream2 = list.iterator(); stream1.hasNext(); ) {
			assertEquals(stream1.next(), stream2.next());
		}
	}

	public void testHasListeners() throws Exception {
		SimpleListValueModel listHolder = this.buildListHolder();
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ListModel listModel = this.buildListModel(listHolder);
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.verifyHasNoListeners(listModel);

		SynchronizedList synchList = new SynchronizedList(listModel);
		assertTrue(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.verifyHasListeners(listModel);

		listModel.removeListDataListener(synchList);
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		this.verifyHasNoListeners(listModel);
	}

	public void testGetSize() throws Exception {
		SimpleListValueModel listHolder = this.buildListHolder();
		ListModel listModel = this.buildListModel(listHolder);
		this.verifyHasNoListeners(listModel);
		assertEquals(6, listModel.getSize());

		SynchronizedList synchList = new SynchronizedList(listModel);
		this.verifyHasListeners(listModel);
		assertEquals(6, listModel.getSize());

		listModel.removeListDataListener(synchList);
		this.verifyHasNoListeners(listModel);
		assertEquals(6, listModel.getSize());
	}

	public void testGetElementAt() throws Exception {
		SimpleListValueModel listHolder = this.buildListHolder();
		ListModel listModel = this.buildListModel(new SortedListValueModelAdapter(listHolder));
		SynchronizedList synchList = new SynchronizedList(listModel);
		this.verifyHasListeners(listModel);
		assertEquals("bar", listModel.getElementAt(0));
		assertEquals("bar", synchList.get(0));
	}

	private void verifyHasNoListeners(ListModel listModel) throws Exception {
		boolean hasNoListeners = ((Boolean) ClassTools.executeMethod(listModel, "hasNoListDataListeners")).booleanValue();
		assertTrue(hasNoListeners);
	}

	private void verifyHasListeners(ListModel listModel) throws Exception {
		boolean hasListeners = ((Boolean) ClassTools.executeMethod(listModel, "hasListDataListeners")).booleanValue();
		assertTrue(hasListeners);
	}

	private SimpleCollectionValueModel buildCollectionHolder() {
		return new SimpleCollectionValueModel(this.buildCollection());
	}

	private Collection<String> buildCollection() {
		Bag<String> bag = new HashBag<String>();
		this.populateCollection(bag);
		return bag;
	}

	private SimpleListValueModel buildListHolder() {
		return new SimpleListValueModel(this.buildList());
	}

	private List<String> buildList() {
		List<String> list = new ArrayList<String>();
		this.populateCollection(list);
		return list;
	}

	private void populateCollection(Collection<String> c) {
		c.add("foo");
		c.add("bar");
		c.add("baz");
		c.add("joo");
		c.add("jar");
		c.add("jaz");
	}

	private SimpleListValueModel buildListHolder2() {
		return new SimpleListValueModel(this.buildList2());
	}

	private List<String> buildList2() {
		List<String> list = new ArrayList<String>();
		this.populateCollection2(list);
		return list;
	}

	private void populateCollection2(Collection<String> c) {
		c.add("tom");
		c.add("dick");
		c.add("harry");
	}

}
