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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.Icon;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.swing.Displayable;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class ItemPropertyListValueModelAdapterTests extends TestCase {
	private Junk foo;
	private Junk bar;
	private Junk baz;
	private Junk joo;
	private Junk jar;
	private Junk jaz;

	private Junk tom;
	private Junk dick;
	private Junk harry;

	public ItemPropertyListValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.foo = new Junk("this.foo");
		this.bar = new Junk("this.bar");
		this.baz = new Junk("this.baz");
		this.joo = new Junk("this.joo");
		this.jar = new Junk("this.jar");
		this.jaz = new Junk("this.jaz");

		this.tom = new Junk("this.tom");
		this.dick = new Junk("this.dick");
		this.harry = new Junk("this.harry");
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testCollectionSynchronization() {
		SimpleCollectionValueModel<Junk> collectionHolder = this.buildCollectionHolder();
		ListValueModel<Junk> listValueModel = new ItemPropertyListValueModelAdapter<Junk>(collectionHolder, Displayable.DISPLAY_STRING_PROPERTY, Displayable.ICON_PROPERTY);
		CoordinatedList<Junk> synchList = new CoordinatedList<Junk>(listValueModel);
		assertEquals(6, synchList.size());
		this.compare(listValueModel, synchList);

		collectionHolder.add(this.tom);
		collectionHolder.add(this.dick);
		collectionHolder.add(this.harry);
		assertEquals(9, synchList.size());
		this.compare(listValueModel, synchList);

		collectionHolder.remove(this.foo);
		collectionHolder.remove(this.jar);
		collectionHolder.remove(this.harry);
		assertEquals(6, synchList.size());
		this.compare(listValueModel, synchList);

		collectionHolder.setCollection(this.buildCollection());
		assertEquals(6, synchList.size());
		this.compare(listValueModel, synchList);
	}

	public void testListSynchronization() {
		SimpleListValueModel<Junk> listHolder = this.buildListHolder();
		ListValueModel<Junk> listValueModel = new ItemPropertyListValueModelAdapter<Junk>(listHolder, Displayable.DISPLAY_STRING_PROPERTY, Displayable.ICON_PROPERTY);
		CoordinatedList<Junk> synchList = new CoordinatedList<Junk>(listValueModel);
		assertEquals(6, synchList.size());
		this.compare(listValueModel, synchList);

		listHolder.add(6, this.tom);
		listHolder.add(7, this.dick);
		listHolder.add(8, this.harry);
		assertEquals(9, synchList.size());
		this.compare(listValueModel, synchList);

		listHolder.remove(8);
		listHolder.remove(0);
		listHolder.remove(4);
		assertEquals(6, synchList.size());
		this.compare(listValueModel, synchList);

		// test concurrent modification exception
		listHolder.setList(this.buildList());
		assertEquals(6, synchList.size());
		this.compare(listValueModel, synchList);
	}

	private void compare(ListValueModel<Junk> listValueModel, List<Junk> list) {
		assertEquals(listValueModel.size(), list.size());
		for (int i = 0; i < listValueModel.size(); i++) {
			assertEquals(listValueModel.get(i), list.get(i));
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

	private Comparator<Junk> buildCustomComparator() {
		// sort with reverse order
		return new Comparator<Junk>() {
			public int compare(Junk o1, Junk o2) {
				return o2.compareTo(o1);
			}
		};
	}

	private void verifyCollectionSort(Comparator<Junk> comparator) {
		SimpleCollectionValueModel<Junk> collectionHolder = this.buildCollectionHolder();
		ListValueModel<Junk> listValueModel = new ItemPropertyListValueModelAdapter<Junk>(new SortedListValueModelAdapter<Junk>(collectionHolder, comparator), Displayable.DISPLAY_STRING_PROPERTY, Displayable.ICON_PROPERTY);
		CoordinatedList<Junk> synchList = new CoordinatedList<Junk>(listValueModel);
		assertEquals(6, synchList.size());
		this.compareSort(listValueModel, synchList, comparator);

		collectionHolder.add(this.tom);
		collectionHolder.add(this.dick);
		collectionHolder.add(this.harry);
		assertEquals(9, synchList.size());
		this.compareSort(listValueModel, synchList, comparator);

		collectionHolder.remove(this.foo);
		collectionHolder.remove(this.jar);
		collectionHolder.remove(this.harry);
		assertEquals(6, synchList.size());
		this.compareSort(listValueModel, synchList, comparator);

		collectionHolder.setCollection(this.buildCollection());
		assertEquals(6, synchList.size());
		this.compareSort(listValueModel, synchList, comparator);
	}

	private void verifyListSort(Comparator<Junk> comparator) {
		SimpleListValueModel<Junk> listHolder = this.buildListHolder();
		ListValueModel<Junk> listValueModel = new ItemPropertyListValueModelAdapter<Junk>(new SortedListValueModelAdapter<Junk>(listHolder, comparator), Displayable.DISPLAY_STRING_PROPERTY, Displayable.ICON_PROPERTY);
		CoordinatedList<Junk> synchList = new CoordinatedList<Junk>(listValueModel);
		assertEquals(6, synchList.size());
		this.compareSort(listValueModel, synchList, comparator);

		listHolder.add(0, this.tom);
		listHolder.add(0, this.dick);
		listHolder.add(0, this.harry);
		assertEquals(9, synchList.size());
		this.compareSort(listValueModel, synchList, comparator);

		listHolder.remove(8);
		listHolder.remove(4);
		listHolder.remove(0);
		listHolder.remove(5);
		assertEquals(5, synchList.size());
		this.compareSort(listValueModel, synchList, comparator);

		listHolder.setList(this.buildList());
		assertEquals(6, synchList.size());
		this.compareSort(listValueModel, synchList, comparator);
	}

	private void compareSort(ListValueModel<Junk> listValueModel, List<Junk> list, Comparator<Junk> comparator) {
		SortedSet<Junk> ss = new TreeSet<Junk>(comparator);
		for (int i = 0; i < listValueModel.size(); i++) {
			ss.add(listValueModel.get(i));
		}
		assertEquals(ss.size(), list.size());
		for (Iterator<Junk> stream1 = ss.iterator(), stream2 = list.iterator(); stream1.hasNext(); ) {
			assertEquals(stream1.next(), stream2.next());
		}
	}

	public void testHasListeners() throws Exception {
		SimpleListValueModel<Junk> listHolder = this.buildListHolder();
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertFalse(this.foo.hasAnyPropertyChangeListeners(Displayable.DISPLAY_STRING_PROPERTY));
		assertFalse(this.foo.hasAnyPropertyChangeListeners(Displayable.ICON_PROPERTY));
		assertFalse(this.jaz.hasAnyPropertyChangeListeners(Displayable.DISPLAY_STRING_PROPERTY));
		assertFalse(this.jaz.hasAnyPropertyChangeListeners(Displayable.ICON_PROPERTY));

		ListValueModel<Junk> listValueModel = new ItemPropertyListValueModelAdapter<Junk>(new SortedListValueModelAdapter<Junk>(listHolder), Displayable.DISPLAY_STRING_PROPERTY, Displayable.ICON_PROPERTY);
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertFalse(this.foo.hasAnyPropertyChangeListeners(Displayable.DISPLAY_STRING_PROPERTY));
		assertFalse(this.foo.hasAnyPropertyChangeListeners(Displayable.ICON_PROPERTY));
		assertFalse(this.jaz.hasAnyPropertyChangeListeners(Displayable.DISPLAY_STRING_PROPERTY));
		assertFalse(this.jaz.hasAnyPropertyChangeListeners(Displayable.ICON_PROPERTY));
		this.verifyHasNoListeners(listValueModel);

		CoordinatedList<Junk> synchList = new CoordinatedList<Junk>(listValueModel);
		assertTrue(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(this.foo.hasAnyPropertyChangeListeners(Displayable.DISPLAY_STRING_PROPERTY));
		assertTrue(this.foo.hasAnyPropertyChangeListeners(Displayable.ICON_PROPERTY));
		assertTrue(this.jaz.hasAnyPropertyChangeListeners(Displayable.DISPLAY_STRING_PROPERTY));
		assertTrue(this.jaz.hasAnyPropertyChangeListeners(Displayable.ICON_PROPERTY));
		this.verifyHasListeners(listValueModel);

		listValueModel.removeListChangeListener(ListValueModel.LIST_VALUES, synchList);
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertFalse(this.foo.hasAnyPropertyChangeListeners(Displayable.DISPLAY_STRING_PROPERTY));
		assertFalse(this.foo.hasAnyPropertyChangeListeners(Displayable.ICON_PROPERTY));
		assertFalse(this.jaz.hasAnyPropertyChangeListeners(Displayable.DISPLAY_STRING_PROPERTY));
		assertFalse(this.jaz.hasAnyPropertyChangeListeners(Displayable.ICON_PROPERTY));
		this.verifyHasNoListeners(listValueModel);
	}

	public void testGetSize() throws Exception {
		SimpleListValueModel<Junk> listHolder = this.buildListHolder();
		ListValueModel<Junk> listValueModel = new ItemPropertyListValueModelAdapter<Junk>(new SortedListValueModelAdapter<Junk>(listHolder), Displayable.DISPLAY_STRING_PROPERTY, Displayable.ICON_PROPERTY);
		CoordinatedList<Junk> synchList = new CoordinatedList<Junk>(listValueModel);
		this.verifyHasListeners(listValueModel);
		assertEquals(6, listValueModel.size());
		assertEquals(6, synchList.size());
	}

	public void testGet() throws Exception {
		SimpleListValueModel<Junk> listHolder = this.buildListHolder();
		ListValueModel<Junk> listValueModel = new SortedListValueModelAdapter<Junk>(new ItemPropertyListValueModelAdapter<Junk>(listHolder, Displayable.DISPLAY_STRING_PROPERTY, Displayable.ICON_PROPERTY));
		CoordinatedList<Junk> synchList = new CoordinatedList<Junk>(listValueModel);
		this.verifyHasListeners(listValueModel);
		assertEquals(this.bar, listValueModel.get(0));
		assertEquals(this.bar, synchList.get(0));
		this.bar.setName("zzz");
		assertEquals(this.bar, listValueModel.get(5));
		assertEquals(this.bar, synchList.get(5));
		this.bar.setName("this.bar");
	}

	private void verifyHasNoListeners(ListValueModel<Junk> listValueModel) throws Exception {
		assertTrue(((AbstractModel) listValueModel).hasNoListChangeListeners(ListValueModel.LIST_VALUES));
	}

	private void verifyHasListeners(ListValueModel<Junk> listValueModel) throws Exception {
		assertTrue(((AbstractModel) listValueModel).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

	private SimpleCollectionValueModel<Junk> buildCollectionHolder() {
		return new SimpleCollectionValueModel<Junk>(this.buildCollection());
	}

	private Collection<Junk> buildCollection() {
		Bag<Junk> bag = new HashBag<Junk>();
		this.populateCollection(bag);
		return bag;
	}

	private SimpleListValueModel<Junk> buildListHolder() {
		return new SimpleListValueModel<Junk>(this.buildList());
	}

	private List<Junk> buildList() {
		List<Junk> list = new ArrayList<Junk>();
		this.populateCollection(list);
		return list;
	}

	private void populateCollection(Collection<Junk> c) {
		c.add(this.foo);
		c.add(this.bar);
		c.add(this.baz);
		c.add(this.joo);
		c.add(this.jar);
		c.add(this.jaz);
	}


	// ********** Junk class **********

	private class Junk extends AbstractModel implements Displayable {
		private String name;
		public Junk(String name) {
			this.name = name;
		}
		public String displayString() {
			return this.name;
		}
		public Icon icon() {
			return null;
		}
		public int compareTo(Displayable o) {
			return DEFAULT_COMPARATOR.compare(this, o);
		}
		public void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(DISPLAY_STRING_PROPERTY, old, name);
		}
		@Override
		public String toString() {
			return "Junk(" + this.name + ")";
		}
	}

}
