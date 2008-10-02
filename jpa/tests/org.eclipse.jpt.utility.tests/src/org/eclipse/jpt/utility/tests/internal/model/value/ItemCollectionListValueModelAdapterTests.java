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
import java.util.List;
import javax.swing.Icon;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.ItemCollectionListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.swing.Displayable;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

public class ItemCollectionListValueModelAdapterTests extends TestCase {
	private Junk foo;
	private Junk bar;
	private Junk baz;
	private Junk joo;
	private Junk jar;
	private Junk jaz;

	private Junk tom;
	private Junk dick;
	private Junk harry;

	public ItemCollectionListValueModelAdapterTests(String name) {
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
		SimpleCollectionValueModel<Junk> collectionHolder = this.buildCollectionHolder();
		ListValueModel<Junk> listValueModel = new ItemCollectionListValueModelAdapter<Junk>(collectionHolder, Junk.STUFF_COLLECTION);
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
	}

	public void testListSynchronization() {
		SimpleListValueModel<Junk> listHolder = this.buildListHolder();
		ListValueModel<Junk> listValueModel = new ItemCollectionListValueModelAdapter<Junk>(listHolder, Junk.STUFF_COLLECTION);
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
	}

	private void compare(ListValueModel<Junk> listValueModel, List<Junk> list) {
		assertEquals(listValueModel.size(), list.size());
		for (int i = 0; i < listValueModel.size(); i++) {
			assertEquals(listValueModel.get(i), list.get(i));
		}
	}


	public void testHasListeners() throws Exception {
		SimpleListValueModel<Junk> listHolder = this.buildListHolder();
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertFalse(this.foo.hasAnyListChangeListeners(Junk.STUFF_COLLECTION));
		assertFalse(this.jaz.hasAnyListChangeListeners(Junk.STUFF_COLLECTION));

		ListValueModel<Junk> listValueModel = new ItemCollectionListValueModelAdapter<Junk>(listHolder, Junk.STUFF_COLLECTION);
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertFalse(this.foo.hasAnyCollectionChangeListeners(Junk.STUFF_COLLECTION));
		assertFalse(this.jaz.hasAnyCollectionChangeListeners(Junk.STUFF_COLLECTION));
		this.verifyHasNoListeners(listValueModel);

		CoordinatedList<Junk> synchList = new CoordinatedList<Junk>(listValueModel);
		assertTrue(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(this.foo.hasAnyCollectionChangeListeners(Junk.STUFF_COLLECTION));
		assertTrue(this.jaz.hasAnyCollectionChangeListeners(Junk.STUFF_COLLECTION));
		this.verifyHasListeners(listValueModel);

		listValueModel.removeListChangeListener(ListValueModel.LIST_VALUES, synchList);
		assertFalse(listHolder.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertFalse(this.foo.hasAnyCollectionChangeListeners(Junk.STUFF_COLLECTION));
		assertFalse(this.jaz.hasAnyCollectionChangeListeners(Junk.STUFF_COLLECTION));
		this.verifyHasNoListeners(listValueModel);
	}

	public void testGetSize() throws Exception {
		SimpleListValueModel<Junk> listHolder = this.buildListHolder();
		ListValueModel<Junk> listValueModel = new ItemCollectionListValueModelAdapter<Junk>(listHolder, Junk.STUFF_COLLECTION);
		CoordinatedList<Junk> synchList = new CoordinatedList<Junk>(listValueModel);
		this.verifyHasListeners(listValueModel);
		assertEquals(6, listValueModel.size());
		assertEquals(6, synchList.size());
	}

	public void testGet() throws Exception {
		SimpleListValueModel<Junk> listHolder = this.buildListHolder();
		ListValueModel<Junk> listValueModel = new SortedListValueModelAdapter<Junk>(new ItemCollectionListValueModelAdapter<Junk>(listHolder, Junk.STUFF_COLLECTION));
		CoordinatedList<Junk> synchList = new CoordinatedList<Junk>(listValueModel);
		this.verifyHasListeners(listValueModel);
		assertEquals(this.bar, listValueModel.get(0));
		assertEquals(this.bar, synchList.get(0));
		this.bar.removeStuff("bar");
		this.bar.addStuff("zzz");
		this.bar.addStuff("bar");
		assertEquals(this.bar, listValueModel.get(5));
		assertEquals(this.bar, synchList.get(5));
		this.bar.removeStuff("zzz");
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
		private Collection<String> stuff;
			public static final String STUFF_COLLECTION = "stuff";
			
	
		public Junk(String stuffItem) {
			this.stuff = new ArrayList<String>();
			this.stuff.add(stuffItem);
		}
	
		public void addStuff(String stuffItem) {
			this.addItemToCollection(stuffItem, this.stuff, STUFF_COLLECTION);
		}
		
		public void removeStuff(String stuffItem) {
			this.removeItemFromCollection(stuffItem, this.stuff, STUFF_COLLECTION);
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
