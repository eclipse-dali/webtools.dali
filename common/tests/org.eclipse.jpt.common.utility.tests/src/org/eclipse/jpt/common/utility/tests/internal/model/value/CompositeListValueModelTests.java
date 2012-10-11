/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class CompositeListValueModelTests extends TestCase {
	private LocalListValueModel<String> lvm0;
	private LocalListValueModel<String> lvm1;
	private LocalListValueModel<String> lvm2;
	private LocalListValueModel<String> lvm3;
	private LocalListValueModel<LocalListValueModel<String>> uberLVM;
	private CompositeListValueModel<LocalListValueModel<String>, String> compositeLVM;

	public CompositeListValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		this.lvm0 = new LocalListValueModel<String>();
		this.lvm0.add("aaa");
		this.lvm0.add("bbb");
		this.lvm0.add("ccc");

		this.lvm1 = new LocalListValueModel<String>();
		this.lvm1.add("ddd");
		this.lvm1.add("eee");

		this.lvm2 = new LocalListValueModel<String>();
		this.lvm2.add("fff");

		this.lvm3 = new LocalListValueModel<String>();
		this.lvm3.add("ggg");
		this.lvm3.add("hhh");
		this.lvm3.add("iii");
		this.lvm3.add("jjj");
		this.lvm3.add("kkk");

		this.uberLVM = new LocalListValueModel<LocalListValueModel<String>>();
		this.uberLVM.add(this.lvm0);
		this.uberLVM.add(this.lvm1);
		this.uberLVM.add(this.lvm2);
		this.uberLVM.add(this.lvm3);

		this.compositeLVM = CompositeListValueModel.forModels((ListValueModel<LocalListValueModel<String>>) this.uberLVM);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testGetInt() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);
		assertEquals("aaa", this.compositeLVM.get(0));
		assertEquals("aaa", coordList.get(0));
		assertEquals("bbb", this.compositeLVM.get(1));
		assertEquals("bbb", coordList.get(1));
		assertEquals("ccc", this.compositeLVM.get(2));
		assertEquals("ccc", coordList.get(2));

		assertEquals("ddd", this.compositeLVM.get(3));
		assertEquals("ddd", coordList.get(3));
		assertEquals("eee", this.compositeLVM.get(4));
		assertEquals("eee", coordList.get(4));

		assertEquals("fff", this.compositeLVM.get(5));
		assertEquals("fff", coordList.get(5));

		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testIterator() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);
		assertEquals("aaa", this.compositeLVM.iterator().next());
		assertEquals("aaa", coordList.iterator().next());
		Iterator<String> stream1 = coordList.iterator();
		for (Iterator<String> stream2 = this.compositeLVM.iterator(); stream2.hasNext(); ) {
			assertEquals(stream1.next(), stream2.next());
		}
		assertFalse(stream1.hasNext());
	}

	public void testSize() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);
		assertEquals(11, this.compositeLVM.size());
		assertEquals(11, coordList.size());
	}

	public void testToArray() {
		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
	}

	public void testHasListeners() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		assertTrue(this.compositeLVM.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(this.lvm0.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.compositeLVM.removeListChangeListener(ListValueModel.LIST_VALUES, coordList);
		assertFalse(this.compositeLVM.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertFalse(this.lvm0.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, coordList);
		assertTrue(this.compositeLVM.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(this.lvm0.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

	public void testAddSource_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		LocalListValueModel<String> lvm = new LocalListValueModel<String>();
		lvm.add("xxx");
		lvm.add("yyy");
		lvm.add("zzz");
		this.uberLVM.add(0, lvm);

		Object[] expected = new Object[] { "xxx", "yyy", "zzz", "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(9));
		assertEquals("ggg", coordList.get(9));
	}

	public void testAddSource_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		LocalListValueModel<String> lvm = new LocalListValueModel<String>();
		lvm.add("xxx");
		lvm.add("yyy");
		lvm.add("zzz");
		this.uberLVM.add(2, lvm);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "xxx", "yyy", "zzz", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(9));
		assertEquals("ggg", coordList.get(9));
	}

	public void testAddSource_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		LocalListValueModel<String> lvm = new LocalListValueModel<String>();
		lvm.add("xxx");
		lvm.add("yyy");
		lvm.add("zzz");
		this.uberLVM.add(lvm);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk", "xxx", "yyy", "zzz" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testAddSources() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		LocalListValueModel<String> lvmA = new LocalListValueModel<String>();
		lvmA.add("xxx");
		lvmA.add("yyy");
		lvmA.add("zzz");
		LocalListValueModel<String> lvmB = new LocalListValueModel<String>();
		lvmB.add("ppp");
		lvmB.add("qqq");
		lvmB.add("rrr");
		Collection<LocalListValueModel<String>> c = new ArrayList<LocalListValueModel<String>>();
		c.add(lvmA);
		c.add(lvmB);
		this.uberLVM.addAll(2, c);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "xxx", "yyy", "zzz", "ppp", "qqq", "rrr", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(12));
		assertEquals("ggg", coordList.get(12));
	}

	public void testRemoveSource_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.remove(0);

		Object[] expected = new Object[] { "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(3));
		assertEquals("ggg", coordList.get(3));
	}

	public void testRemoveSource_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.remove(2);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(5));
		assertEquals("ggg", coordList.get(5));
	}

	public void testRemoveSource_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.remove(3);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("fff", this.compositeLVM.get(5));
		assertEquals("fff", coordList.get(5));
	}

	public void testRemoveSources() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.remove(2, 2);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("eee", this.compositeLVM.get(4));
		assertEquals("eee", coordList.get(4));
	}

	public void testReplaceSources() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		LocalListValueModel<String> lvmA = new LocalListValueModel<String>();
		lvmA.add("xxx");
		lvmA.add("yyy");
		lvmA.add("zzz");
		LocalListValueModel<String> lvmB = new LocalListValueModel<String>();
		lvmB.add("ppp");
		lvmB.add("qqq");
		lvmB.add("rrr");
		List<LocalListValueModel<String>> list = new ArrayList<LocalListValueModel<String>>();
		list.add(lvmA);
		list.add(lvmB);
		this.uberLVM.set(2, list);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "xxx", "yyy", "zzz", "ppp", "qqq", "rrr" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("qqq", this.compositeLVM.get(9));
		assertEquals("qqq", coordList.get(9));
	}

	public void testMoveSources_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.move(0, 2, 2);

		Object[] expected = new Object[] { "fff", "ggg", "hhh", "iii", "jjj", "kkk", "aaa", "bbb", "ccc", "ddd", "eee" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(1));
		assertEquals("ggg", coordList.get(1));
	}

	public void testMoveSources_Middle() {
		LocalListValueModel<String> lvm4 = new LocalListValueModel<String>();
		lvm4.add("lll");
		lvm4.add("mmm");
		this.uberLVM.add(lvm4);

		LocalListValueModel<String> lvm5 = new LocalListValueModel<String>();
		lvm5.add("nnn");
		lvm5.add("ooo");
		lvm5.add("ppp");
		lvm5.add("qqq");
		this.uberLVM.add(lvm5);

		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.move(1, 3, 2);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ggg", "hhh", "iii", "jjj", "kkk", "lll", "mmm", "ddd", "eee", "fff", "nnn", "ooo", "ppp", "qqq" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(3));
		assertEquals("ggg", coordList.get(3));
	}

	public void testMoveSources_End() {
		LocalListValueModel<String> lvm4 = new LocalListValueModel<String>();
		lvm4.add("lll");
		lvm4.add("mmm");
		this.uberLVM.add(lvm4);

		LocalListValueModel<String> lvm5 = new LocalListValueModel<String>();
		lvm5.add("nnn");
		lvm5.add("ooo");
		lvm5.add("ppp");
		lvm5.add("qqq");
		this.uberLVM.add(lvm5);

		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.move(3, 0, 3);

		Object[] expected = new Object[] { "ggg", "hhh", "iii", "jjj", "kkk", "lll", "mmm", "nnn", "ooo", "ppp", "qqq", "aaa", "bbb", "ccc", "ddd", "eee", "fff" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(0));
		assertEquals("ggg", coordList.get(0));
	}

	public void testMoveSource() {
		LocalListValueModel<String> lvm4 = new LocalListValueModel<String>();
		lvm4.add("lll");
		lvm4.add("mmm");
		this.uberLVM.add(lvm4);

		LocalListValueModel<String> lvm5 = new LocalListValueModel<String>();
		lvm5.add("nnn");
		lvm5.add("ooo");
		lvm5.add("ppp");
		lvm5.add("qqq");
		this.uberLVM.add(lvm5);

		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.move(3, 1);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "fff", "ggg", "hhh", "iii", "jjj", "kkk", "ddd", "eee", "lll", "mmm", "nnn", "ooo", "ppp", "qqq" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ooo", this.compositeLVM.get(14));
		assertEquals("ooo", coordList.get(14));
	}

	public void testClearSources() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.clear();

		Object[] expected = new Object[0];
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
	}

	public void testChangeSources1() {
		List<LocalListValueModel<String>> newList = new ArrayList<LocalListValueModel<String>>();
		LocalListValueModel<String> lvm4 = new LocalListValueModel<String>();
		lvm4.add("lll");
		lvm4.add("mmm");
		newList.add(lvm4);

		LocalListValueModel<String> lvm5 = new LocalListValueModel<String>();
		lvm5.add("nnn");
		lvm5.add("ooo");
		lvm5.add("ppp");
		lvm5.add("qqq");
		newList.add(lvm5);

		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.setListValues(newList);

		Object[] expected = new Object[] { "lll", "mmm", "nnn", "ooo", "ppp", "qqq" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ooo", this.compositeLVM.get(3));
		assertEquals("ooo", coordList.get(3));
	}

	public void testChangeSources2() {
		List<LocalListValueModel<String>> newList = new ArrayList<LocalListValueModel<String>>();
		LocalListValueModel<String> lvm4 = new LocalListValueModel<String>();
		lvm4.add("lll");
		lvm4.add("mmm");
		newList.add(lvm4);

		LocalListValueModel<String> lvm5 = new LocalListValueModel<String>();
		lvm5.add("nnn");
		lvm5.add("ooo");
		lvm5.add("ppp");
		lvm5.add("qqq");
		newList.add(lvm5);

		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.changeListValues(newList);

		Object[] expected = new Object[] { "lll", "mmm", "nnn", "ooo", "ppp", "qqq" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ooo", this.compositeLVM.get(3));
		assertEquals("ooo", coordList.get(3));
	}

	public void testChangeSources3() {
		ListChangeListener listener = new ErrorListener();
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);

		List<LocalListValueModel<String>> newList = new ArrayList<LocalListValueModel<String>>();
		newList.add(this.lvm0);
		newList.add(this.lvm1);
		newList.add(this.lvm2);
		newList.add(this.lvm3);

		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.changeListValues(newList);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ddd", this.compositeLVM.get(3));
		assertEquals("ddd", coordList.get(3));
	}

	public void testChangeSources4() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsAdded(ListAddEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);

		List<LocalListValueModel<String>> newList = new ArrayList<LocalListValueModel<String>>();
		newList.add(this.lvm0);
		newList.add(this.lvm1);
		newList.add(this.lvm2);
		newList.add(this.lvm3);
		LocalListValueModel<String> lvm4 = new LocalListValueModel<String>();
		lvm4.add("lll");
		lvm4.add("mmm");
		newList.add(lvm4);


		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.changeListValues(newList);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk", "lll", "mmm" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ddd", this.compositeLVM.get(3));
		assertEquals("ddd", coordList.get(3));
	}

	public void testChangeSources5() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsRemoved(ListRemoveEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);

		List<LocalListValueModel<String>> newList = new ArrayList<LocalListValueModel<String>>();
		newList.add(this.lvm0);
		newList.add(this.lvm1);
		newList.add(this.lvm2);

		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.changeListValues(newList);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ddd", this.compositeLVM.get(3));
		assertEquals("ddd", coordList.get(3));
	}

	public void testChangeSources6() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsAdded(ListAddEvent event) { /* OK */ }
			@Override
			public void itemsRemoved(ListRemoveEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);

		List<LocalListValueModel<String>> newList = new ArrayList<LocalListValueModel<String>>();
		newList.add(this.lvm0);
		newList.add(this.lvm1);

		LocalListValueModel<String> lvm4 = new LocalListValueModel<String>();
		lvm4.add("lll");
		lvm4.add("mmm");
		newList.add(lvm4);

		newList.add(this.lvm3);

		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.uberLVM.changeListValues(newList);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "lll", "mmm", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ddd", this.compositeLVM.get(3));
		assertEquals("ddd", coordList.get(3));
	}

	public void testAddItem_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.add(0, "xxx");

		Object[] expected = new Object[] { "xxx", "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(7));
		assertEquals("ggg", coordList.get(7));
	}

	public void testAddItem_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm2.add(1, "xxx");

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "xxx", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(7));
		assertEquals("ggg", coordList.get(7));
	}

	public void testAddItem_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.add(5, "xxx");

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk", "xxx" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testAddItems_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.addAll(0, Arrays.asList(new String[] { "xxx", "yyy", "zzz" }));

		Object[] expected = new Object[] { "xxx", "yyy", "zzz", "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(9));
		assertEquals("ggg", coordList.get(9));
	}

	public void testAddItems_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm2.addAll(1, Arrays.asList(new String[] { "xxx", "yyy", "zzz" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "xxx", "yyy", "zzz", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(9));
		assertEquals("ggg", coordList.get(9));
	}

	public void testAddItems_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.addAll(5, Arrays.asList(new String[] { "xxx", "yyy", "zzz" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk", "xxx", "yyy", "zzz" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testRemoveItem_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.remove(0);

		Object[] expected = new Object[] { "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(5));
		assertEquals("ggg", coordList.get(5));
	}

	public void testRemoveItem_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm2.remove(0);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(5));
		assertEquals("ggg", coordList.get(5));
	}

	public void testRemoveItem_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.remove(4);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testRemoveItems_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.remove(0, 3);

		Object[] expected = new Object[] { "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(3));
		assertEquals("ggg", coordList.get(3));
	}

	public void testRemoveItems_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.remove(1, 3);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("kkk", this.compositeLVM.get(7));
		assertEquals("kkk", coordList.get(7));
	}

	public void testRemoveItems_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.remove(3, 2);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testReplaceItem_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.set(0, "xxx");

		Object[] expected = new Object[] { "xxx", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testReplaceItem_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm2.set(0, "xxx");

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "xxx", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testReplaceItem_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.set(4, "xxx");

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "xxx" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testReplaceItems_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.set(0, Arrays.asList(new String[] { "xxx", "yyy", "zzz" }));

		Object[] expected = new Object[] { "xxx", "yyy", "zzz", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testReplaceItems_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.set(1, Arrays.asList(new String[] { "xxx", "yyy", "zzz" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "xxx", "yyy", "zzz", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("kkk", this.compositeLVM.get(10));
		assertEquals("kkk", coordList.get(10));
	}

	public void testReplaceItems_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.set(3, Arrays.asList(new String[] { "xxx", "yyy" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "xxx", "yyy" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testMoveItem_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.move(2, 0);

		Object[] expected = new Object[] { "bbb", "ccc", "aaa", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testMoveItem_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm1.move(0, 1);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "eee", "ddd", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testMoveItem_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.move(0, 4);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "kkk", "ggg", "hhh", "iii", "jjj" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(7));
		assertEquals("ggg", coordList.get(7));
	}

	public void testMoveItems_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.move(1, 0, 2);

		Object[] expected = new Object[] { "ccc", "aaa", "bbb", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testMoveItems_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm1.add("eee.1");
		this.lvm1.add("eee.2");
		this.lvm1.add("eee.3");
		this.lvm1.move(1, 2, 3);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee.1", "eee.2", "eee.3", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(9));
		assertEquals("ggg", coordList.get(9));
	}

	public void testMoveItems_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.move(0, 2, 3);

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "iii", "jjj", "kkk", "ggg", "hhh" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(9));
		assertEquals("ggg", coordList.get(9));
	}

	public void testClearItems_Begin() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.clear();

		Object[] expected = new Object[] { "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(3));
		assertEquals("ggg", coordList.get(3));
	}

	public void testClearItems_Middle() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm1.clear();

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(4));
		assertEquals("ggg", coordList.get(4));
	}

	public void testClearItems_End() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.clear();

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("fff", this.compositeLVM.get(5));
		assertEquals("fff", coordList.get(5));
	}

	public void testChangeItems_Begin1() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.setListValues(Arrays.asList(new String[] { "xxx", "yyy", "zzz" }));

		Object[] expected = new Object[] { "xxx", "yyy", "zzz", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testChangeItems_Middle1() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm1.setListValues(Arrays.asList(new String[] { "xxx", "yyy", "zzz" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "xxx", "yyy", "zzz", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(7));
		assertEquals("ggg", coordList.get(7));
	}

	public void testChangeItems_End1() {
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.setListValues(Arrays.asList(new String[] { "xxx", "yyy", "zzz" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "xxx", "yyy", "zzz" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("fff", this.compositeLVM.get(5));
		assertEquals("fff", coordList.get(5));
	}

	public void testChangeItems_Begin2() {
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, new ErrorListener());
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.changeListValues(Arrays.asList(new String[] { "aaa", "bbb", "ccc" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testChangeItems_Middle2() {
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, new ErrorListener());
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm1.changeListValues(Arrays.asList(new String[] { "ddd", "eee" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("hhh", this.compositeLVM.get(7));
		assertEquals("hhh", coordList.get(7));
	}

	public void testChangeItems_End2() {
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, new ErrorListener());
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.changeListValues(Arrays.asList(new String[] { "ggg", "hhh", "iii", "jjj", "kkk" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("fff", this.compositeLVM.get(5));
		assertEquals("fff", coordList.get(5));
	}

	public void testChangeItems_Begin3() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsReplaced(ListReplaceEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.changeListValues(Arrays.asList(new String[] { "aaa", "bbb", "xxx" }));

		Object[] expected = new Object[] { "aaa", "bbb", "xxx", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(6));
		assertEquals("ggg", coordList.get(6));
	}

	public void testChangeItems_Middle3() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsReplaced(ListReplaceEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm1.changeListValues(Arrays.asList(new String[] { "ddd", "xxx" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "xxx", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("hhh", this.compositeLVM.get(7));
		assertEquals("hhh", coordList.get(7));
	}

	public void testChangeItems_End3() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsReplaced(ListReplaceEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.changeListValues(Arrays.asList(new String[] { "ggg", "hhh", "iii", "xxx", "kkk" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "xxx", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("fff", this.compositeLVM.get(5));
		assertEquals("fff", coordList.get(5));
	}

	public void testChangeItems_Begin4() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsAdded(ListAddEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.changeListValues(Arrays.asList(new String[] { "aaa", "bbb", "ccc", "xxx" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "xxx", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("fff", this.compositeLVM.get(6));
		assertEquals("fff", coordList.get(6));
	}

	public void testChangeItems_Middle4() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsAdded(ListAddEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm1.changeListValues(Arrays.asList(new String[] { "ddd", "eee", "xxx" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "xxx", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("ggg", this.compositeLVM.get(7));
		assertEquals("ggg", coordList.get(7));
	}

	public void testChangeItems_End4() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsAdded(ListAddEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.changeListValues(Arrays.asList(new String[] { "ggg", "hhh", "iii", "jjj", "kkk", "xxx" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk", "xxx" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("fff", this.compositeLVM.get(5));
		assertEquals("fff", coordList.get(5));
	}

	public void testChangeItems_Begin5() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsRemoved(ListRemoveEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm0.changeListValues(Arrays.asList(new String[] { "aaa" }));

		Object[] expected = new Object[] { "aaa", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("iii", this.compositeLVM.get(6));
		assertEquals("iii", coordList.get(6));
	}

	public void testChangeItems_Middle5() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsRemoved(ListRemoveEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm1.changeListValues(Arrays.asList(new String[] { "ddd" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "fff", "ggg", "hhh", "iii", "jjj", "kkk" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("iii", this.compositeLVM.get(7));
		assertEquals("iii", coordList.get(7));
	}

	public void testChangeItems_End5() {
		ListChangeListener listener = new ErrorListener() {
			@Override
			public void itemsRemoved(ListRemoveEvent event) { /* OK */ }
		};
		this.compositeLVM.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		CoordinatedList<String> coordList = new CoordinatedList<String>(this.compositeLVM);

		this.lvm3.changeListValues(Arrays.asList(new String[] { "ggg", "hhh", "iii" }));

		Object[] expected = new Object[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii" };
		assertEquals(expected.length, this.compositeLVM.size());
		assertEquals(expected.length, coordList.size());
		assertTrue(Arrays.equals(expected, this.compositeLVM.toArray()));
		assertTrue(Arrays.equals(expected, coordList.toArray()));
		assertEquals("fff", this.compositeLVM.get(5));
		assertEquals("fff", coordList.get(5));
	}

	class ErrorListener implements ListChangeListener {
		public void itemsAdded(ListAddEvent event) {
			fail();
		}
		public void itemsRemoved(ListRemoveEvent event) {
			fail();
		}
		public void itemsMoved(ListMoveEvent event) {
			fail();
		}
		public void itemsReplaced(ListReplaceEvent event) {
			fail();
		}
		public void listCleared(ListClearEvent event) {
			fail();
		}
		public void listChanged(ListChangeEvent event) {
			fail();
		}
	}

	class LocalListValueModel<E> extends SimpleListValueModel<E> {
		LocalListValueModel() {
			super();
		}
		void changeListValues(Iterable<E> listValues) {
			if (listValues == null) {
				throw new NullPointerException();
			}
			this.list.clear();
			CollectionTools.addAll(this.list, listValues);
			this.fireListChanged(LIST_VALUES, this.list);
		}
	}

}
