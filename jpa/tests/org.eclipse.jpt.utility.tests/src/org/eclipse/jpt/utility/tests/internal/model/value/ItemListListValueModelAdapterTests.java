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
import java.util.List;

import javax.swing.Icon;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemListListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;
import org.eclipse.jpt.utility.internal.swing.Displayable;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class ItemListListValueModelAdapterTests extends TestCase {
	private Junk foo;
	private Junk bar;
	private Junk baz;
	private Junk joo;
	private Junk jar;
	private Junk jaz;

	private Junk tom;
	private Junk dick;
	private Junk harry;

	public ItemListListValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.foo = new Junk("foo");
		this.bar = new Junk("bar");
		this.baz = new Junk("baz");
		this.joo = new Junk("joo");
		this.jar = new Junk("jar");
		this.jaz = new Junk("jaz");

		this.tom = new Junk("tom");
		this.dick = new Junk("dick");
		this.harry = new Junk("harry");
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testCollectionSynchronization() {
		CollectionValueModel collectionHolder = this.buildCollectionHolder();
		ListValueModel listValueModel = new ItemListListValueModelAdapter(collectionHolder, Junk.STUFF_LIST);
		SynchronizedList synchList = new SynchronizedList(listValueModel);
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
	}

	public void testListSynchronization() {
		ListValueModel listHolder = this.buildListHolder();
		ListValueModel listValueModel = new ItemListListValueModelAdapter(listHolder, Junk.STUFF_LIST);
		SynchronizedList synchList = new SynchronizedList(listValueModel);
		assertEquals(6, synchList.size());
		this.compare(listValueModel, synchList);

		listHolder.addItem(6, this.tom);
		listHolder.addItem(7, this.dick);
		listHolder.addItem(8, this.harry);
		assertEquals(9, synchList.size());
		this.compare(listValueModel, synchList);

		listHolder.removeItem(8);
		listHolder.removeItem(0);
		listHolder.removeItem(4);
		assertEquals(6, synchList.size());
		this.compare(listValueModel, synchList);
	}

	private void compare(ListValueModel listValueModel, List list) {
		assertEquals(listValueModel.size(), list.size());
		for (int i = 0; i < listValueModel.size(); i++) {
			assertEquals(listValueModel.getItem(i), list.get(i));
		}
	}


	public void testHasListeners() throws Exception {
		SimpleListValueModel listHolder = this.buildListHolder();
		assertFalse(listHolder.hasAnyListChangeListeners(ValueModel.VALUE));
		assertFalse(this.foo.hasAnyListChangeListeners(Junk.STUFF_LIST));
		assertFalse(this.jaz.hasAnyListChangeListeners(Junk.STUFF_LIST));

		ListValueModel listValueModel = new ItemListListValueModelAdapter(listHolder, Junk.STUFF_LIST);
		assertFalse(listHolder.hasAnyListChangeListeners(ValueModel.VALUE));
		assertFalse(this.foo.hasAnyListChangeListeners(Junk.STUFF_LIST));
		assertFalse(this.jaz.hasAnyListChangeListeners(Junk.STUFF_LIST));
		this.verifyHasNoListeners(listValueModel);

		SynchronizedList synchList = new SynchronizedList(listValueModel);
		assertTrue(listHolder.hasAnyListChangeListeners(ValueModel.VALUE));
		assertTrue(this.foo.hasAnyListChangeListeners(Junk.STUFF_LIST));
		assertTrue(this.jaz.hasAnyListChangeListeners(Junk.STUFF_LIST));
		this.verifyHasListeners(listValueModel);

		listValueModel.removeListChangeListener(ValueModel.VALUE, synchList);
		assertFalse(listHolder.hasAnyListChangeListeners(ValueModel.VALUE));
		assertFalse(this.foo.hasAnyListChangeListeners(Junk.STUFF_LIST));
		assertFalse(this.jaz.hasAnyListChangeListeners(Junk.STUFF_LIST));
		this.verifyHasNoListeners(listValueModel);
	}

	public void testGetSize() throws Exception {
		SimpleListValueModel listHolder = this.buildListHolder();
		ListValueModel listValueModel = new ItemListListValueModelAdapter(listHolder, Junk.STUFF_LIST);
		SynchronizedList synchList = new SynchronizedList(listValueModel);
		this.verifyHasListeners(listValueModel);
		assertEquals(6, listValueModel.size());
		assertEquals(6, synchList.size());
	}

	public void testGetElementAt() throws Exception {
		SimpleListValueModel listHolder = this.buildListHolder();
		ListValueModel listValueModel = new SortedListValueModelAdapter(new ItemListListValueModelAdapter(listHolder, Junk.STUFF_LIST));
		SynchronizedList synchList = new SynchronizedList(listValueModel);
		this.verifyHasListeners(listValueModel);
		assertEquals(this.bar, listValueModel.getItem(0));
		assertEquals(this.bar, synchList.get(0));
		this.bar.removeStuff("bar");
		this.bar.addStuff("zzz");
		this.bar.addStuff("bar");
		assertEquals(this.bar, listValueModel.getItem(5));
		assertEquals(this.bar, synchList.get(5));
		this.bar.removeStuff("zzz");
	}

	private void verifyHasNoListeners(ListValueModel listValueModel) throws Exception {
		assertTrue(((AbstractModel) listValueModel).hasNoListChangeListeners(ValueModel.VALUE));
	}

	private void verifyHasListeners(ListValueModel listValueModel) throws Exception {
		assertTrue(((AbstractModel) listValueModel).hasAnyListChangeListeners(ValueModel.VALUE));
	}

	private CollectionValueModel buildCollectionHolder() {
		return new SimpleCollectionValueModel(this.buildCollection());
	}

	private Collection buildCollection() {
		Bag bag = new HashBag();
		this.populateCollection(bag);
		return bag;
	}

	private SimpleListValueModel buildListHolder() {
		return new SimpleListValueModel(this.buildList());
	}

	private List buildList() {
		List list = new ArrayList();
		this.populateCollection(list);
		return list;
	}

	private void populateCollection(Collection c) {
		c.add(this.foo);
		c.add(this.bar);
		c.add(this.baz);
		c.add(this.joo);
		c.add(this.jar);
		c.add(this.jaz);
	}

private class Junk extends AbstractModel implements Displayable {
	private List stuff;
		public static final String STUFF_LIST = "stuff";
		

	public Junk(String stuffItem) {
		this.stuff = new ArrayList();
		this.stuff.add(stuffItem);
	}
	public void addStuff(String stuffItem) {
		this.stuff.add(stuffItem);
		fireItemAdded(STUFF_LIST, this.stuff.indexOf(stuffItem), stuffItem);
	}
	
	public void removeStuff(String stuffItem) {
		int index = this.stuff.indexOf(stuffItem);
		this.stuff.remove(stuffItem);
		fireItemRemoved(STUFF_LIST, index, stuffItem);
	}

	public String displayString() {
		return toString();
	}

	public Icon icon() {
		return null;
	}
	
	public int compareTo(Displayable o) {
		return DEFAULT_COMPARATOR.compare(this, o);
	}

	@Override
	public String toString() {
		return "Junk(" + this.stuff + ")";
	}
}

}
