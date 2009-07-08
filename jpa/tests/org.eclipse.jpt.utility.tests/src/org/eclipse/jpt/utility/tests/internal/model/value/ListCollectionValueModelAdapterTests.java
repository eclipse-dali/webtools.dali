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
import java.util.Collection;
import java.util.List;

import javax.swing.JList;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ListModelAdapter;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class ListCollectionValueModelAdapterTests extends TestCase {
	CollectionValueModel<String> adapter;
	private SimpleListValueModel<String> wrappedListHolder;
	private List<String> wrappedList;

	public ListCollectionValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wrappedList = new ArrayList<String>();
		this.wrappedListHolder = new SimpleListValueModel<String>(this.wrappedList);
		this.adapter = new ListCollectionValueModelAdapter<String>(this.wrappedListHolder);
	}

	private Collection<String> wrappedCollection() {
		return CollectionTools.collection(this.wrappedList.iterator());
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {
				// override failure
			}
		});
		this.wrappedListHolder.add(0, "foo");
		this.wrappedListHolder.add(1, "bar");
		this.wrappedListHolder.add(2, "baz");
		Collection<String> adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);
	}

	public void testStaleValues() {
		CollectionChangeListener listener = new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {/* OK */}
		};
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, listener);
		this.wrappedListHolder.add(0, "foo");
		this.wrappedListHolder.add(1, "bar");
		this.wrappedListHolder.add(2, "baz");
		Collection<String> adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);

		this.adapter.removeCollectionChangeListener(CollectionValueModel.VALUES, listener);
		adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(0, adapterCollection.size());
		assertEquals(new HashBag<String>(), adapterCollection);

		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, listener);
		adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);
	}

	public void testAdd() {
		Bag<String> synchCollection = new CoordinatedBag<String>(this.adapter);
		List<String> synchList = new CoordinatedList<String>(this.wrappedListHolder);
		this.wrappedListHolder.add(0, "foo");
		assertTrue(this.wrappedList.contains("foo"));
		this.wrappedListHolder.add(1, "bar");
		this.wrappedListHolder.add(2, "baz");
		this.wrappedListHolder.add(3, "joo");
		this.wrappedListHolder.add(4, "jar");
		this.wrappedListHolder.add(5, "jaz");
		assertEquals(6, this.wrappedList.size());

		Collection<String> adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(this.wrappedCollection(), adapterCollection);
		assertEquals(this.wrappedCollection(), CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection(), synchCollection);
	}

	public void testRemove() {
		Bag<String> synchCollection = new CoordinatedBag<String>(this.adapter);
		List<String> synchList = new CoordinatedList<String>(this.wrappedListHolder);
		this.wrappedListHolder.add(0, "foo");
		this.wrappedListHolder.add(1, "bar");
		this.wrappedListHolder.add(2, "baz");
		this.wrappedListHolder.add(3, "joo");
		this.wrappedListHolder.add(4, "jar");
		this.wrappedListHolder.add(5, "jaz");
		assertEquals("jaz", this.wrappedListHolder.remove(5));
		assertFalse(this.wrappedList.contains("jaz"));
		assertEquals("foo", this.wrappedListHolder.remove(0));
		assertFalse(this.wrappedList.contains("foo"));
		assertEquals(4, this.wrappedList.size());

		Collection<String> adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(this.wrappedCollection(), adapterCollection);
		assertEquals(this.wrappedCollection(), CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection(), synchCollection);
	}

	public void testListSynch() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {
				// override failure
			}
			@Override
			public void itemsRemoved(CollectionRemoveEvent e) {
				// override failure
			}
		});
		this.wrappedListHolder.add(0, "foo");
		this.wrappedListHolder.add(1, "bar");
		this.wrappedListHolder.add(2, "baz");
		this.wrappedListHolder.add(3, "joo");
		this.wrappedListHolder.add(4, "jar");
		this.wrappedListHolder.add(5, "jaz");
		this.wrappedListHolder.remove(5);
		assertFalse(this.wrappedList.contains("jaz"));
		this.wrappedListHolder.remove(0);
		assertFalse(this.wrappedList.contains("foo"));
		assertEquals(4, this.wrappedList.size());

		Collection<String> adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(this.wrappedCollection(), adapterCollection);
	}

	public void testReplace() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {
				// override failure
			}
			@Override
			public void itemsRemoved(CollectionRemoveEvent e) {
				// override failure
			}
		});
		this.wrappedListHolder.add(0, "foo");
		this.wrappedListHolder.add(1, "bar");
		this.wrappedListHolder.add(2, "baz");
		Collection<String> adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(3, adapterCollection.size());
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			@Override
			public void itemsRemoved(CollectionRemoveEvent e) {
				assertEquals("foo", e.getItems().iterator().next());
				assertFalse(CollectionTools.contains(ListCollectionValueModelAdapterTests.this.adapter.iterator(), "joo"));
				assertEquals(2, ListCollectionValueModelAdapterTests.this.adapter.size());
			}
			@Override
			public void itemsAdded(CollectionAddEvent e) {
				assertEquals("joo", e.getItems().iterator().next());
				assertEquals(3, ListCollectionValueModelAdapterTests.this.adapter.size());
			}
		});
		this.wrappedListHolder.set(0, "joo");
		adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);
	}

	public void testHasListeners() {
		assertFalse(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		CoordinatedBag<String> synchCollection = new CoordinatedBag<String>(this.adapter);
		assertTrue(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.adapter.removeCollectionChangeListener(CollectionValueModel.VALUES, synchCollection);
		assertFalse(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		ChangeListener cl = new ChangeAdapter();
		this.adapter.addChangeListener(cl);
		assertTrue(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.adapter.removeChangeListener(cl);
		assertFalse(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
	}
	
	public void testListChangedToEmpty() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {/* OK */}
			@Override
			public void itemsRemoved(CollectionRemoveEvent e) {/* OK */}
		});
		this.wrappedListHolder.add(0, "foo");
		this.wrappedListHolder.add(1, "bar");
		this.wrappedListHolder.add(2, "baz");
		JList jList = new JList(new ListModelAdapter(this.adapter));
		this.wrappedListHolder.setList(new ArrayList<String>());
		assertEquals(0, jList.getModel().getSize());
	}
	
	public void testCollectionChangedFromEmpty() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {/* OK */}
			@Override
			public void itemsRemoved(CollectionRemoveEvent e) {/* OK */}
		});
		JList jList = new JList(new ListModelAdapter(this.adapter));
		
		ArrayList<String> list = new ArrayList<String>();
		list.add("foo");
		list.add("bar");
		this.wrappedListHolder.setList(list);
		assertEquals(2, jList.getModel().getSize());
	}
	
	public void testCollectionChangedFromEmptyToEmpty() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {/* OK */}
			@Override
			public void itemsRemoved(CollectionRemoveEvent e) {/* OK */}
		});
		JList jList = new JList(new ListModelAdapter(this.adapter));
		
		ArrayList<String> list = new ArrayList<String>();
		this.wrappedListHolder.setList(list);
		assertEquals(0, jList.getModel().getSize());
	}


	// ********** inner class **********

	class TestListener implements CollectionChangeListener {
		public void itemsAdded(CollectionAddEvent e) {
			fail("unexpected event");
		}
		public void itemsRemoved(CollectionRemoveEvent e) {
			fail("unexpected event");
		}
		public void collectionCleared(CollectionClearEvent e) {
			fail("unexpected event");
		}
		public void collectionChanged(CollectionChangeEvent e) {
			fail("unexpected event");
		}
	}

}
