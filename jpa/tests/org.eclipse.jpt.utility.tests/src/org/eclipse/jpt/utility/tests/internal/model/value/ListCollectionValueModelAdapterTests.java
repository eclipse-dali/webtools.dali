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
import java.util.Iterator;
import java.util.List;
import javax.swing.JList;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ListModelAdapter;
import org.eclipse.jpt.utility.tests.internal.TestTools;

public class ListCollectionValueModelAdapterTests extends TestCase {
	CollectionValueModel adapter;
	private ListValueModel wrappedListHolder;
	private List wrappedList;

	public ListCollectionValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wrappedList = new ArrayList();
		this.wrappedListHolder = new SimpleListValueModel(this.wrappedList);
		this.adapter = new ListCollectionValueModelAdapter(this.wrappedListHolder);
	}

	private Collection wrappedCollection() {
		return CollectionTools.collection(this.wrappedList.iterator());
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				// override failure
			}
		});
		this.wrappedListHolder.addItem(0, "foo");
		this.wrappedListHolder.addItem(1, "bar");
		this.wrappedListHolder.addItem(2, "baz");
		Collection adapterCollection = CollectionTools.collection((Iterator) this.adapter.values());
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);
	}

	public void testStaleValue() {
		CollectionChangeListener listener = new TestListener() {
			public void itemsAdded(CollectionChangeEvent e) {/* OK */}
		};
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, listener);
		this.wrappedListHolder.addItem(0, "foo");
		this.wrappedListHolder.addItem(1, "bar");
		this.wrappedListHolder.addItem(2, "baz");
		Collection adapterCollection = CollectionTools.collection((Iterator) this.adapter.values());
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);

		this.adapter.removeCollectionChangeListener(CollectionValueModel.VALUES, listener);
		adapterCollection = CollectionTools.collection((Iterator) this.adapter.values());
		assertEquals(0, adapterCollection.size());
		assertEquals(new HashBag(), adapterCollection);

		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, listener);
		adapterCollection = CollectionTools.collection((Iterator) this.adapter.values());
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);
	}

	public void testAddItem() {
		Bag synchCollection = new SynchronizedBag(this.adapter);
		List synchList = new SynchronizedList(this.wrappedListHolder);
		this.wrappedListHolder.addItem(0, "foo");
		assertTrue(this.wrappedList.contains("foo"));
		this.wrappedListHolder.addItem(1, "bar");
		this.wrappedListHolder.addItem(2, "baz");
		this.wrappedListHolder.addItem(3, "joo");
		this.wrappedListHolder.addItem(4, "jar");
		this.wrappedListHolder.addItem(5, "jaz");
		assertEquals(6, this.wrappedList.size());

		Collection adapterCollection = CollectionTools.collection((Iterator) this.adapter.values());
		assertEquals(this.wrappedCollection(), adapterCollection);
		assertEquals(this.wrappedCollection(), CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection(), synchCollection);
	}

	public void testRemoveItem() {
		Bag synchCollection = new SynchronizedBag(this.adapter);
		List synchList = new SynchronizedList(this.wrappedListHolder);
		this.wrappedListHolder.addItem(0, "foo");
		this.wrappedListHolder.addItem(1, "bar");
		this.wrappedListHolder.addItem(2, "baz");
		this.wrappedListHolder.addItem(3, "joo");
		this.wrappedListHolder.addItem(4, "jar");
		this.wrappedListHolder.addItem(5, "jaz");
		assertEquals("jaz", this.wrappedListHolder.removeItem(5));
		assertFalse(this.wrappedList.contains("jaz"));
		assertEquals("foo", this.wrappedListHolder.removeItem(0));
		assertFalse(this.wrappedList.contains("foo"));
		assertEquals(4, this.wrappedList.size());

		Collection adapterCollection = CollectionTools.collection((Iterator) this.adapter.values());
		assertEquals(this.wrappedCollection(), adapterCollection);
		assertEquals(this.wrappedCollection(), CollectionTools.collection(synchList.iterator()));
		assertEquals(this.wrappedCollection(), synchCollection);
	}

	public void testListSynch() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				// override failure
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				// override failure
			}
		});
		this.wrappedListHolder.addItem(0, "foo");
		this.wrappedListHolder.addItem(1, "bar");
		this.wrappedListHolder.addItem(2, "baz");
		this.wrappedListHolder.addItem(3, "joo");
		this.wrappedListHolder.addItem(4, "jar");
		this.wrappedListHolder.addItem(5, "jaz");
		this.wrappedListHolder.removeItem(5);
		assertFalse(this.wrappedList.contains("jaz"));
		this.wrappedListHolder.removeItem(0);
		assertFalse(this.wrappedList.contains("foo"));
		assertEquals(4, this.wrappedList.size());

		Collection adapterCollection = CollectionTools.collection((Iterator) this.adapter.values());
		assertEquals(this.wrappedCollection(), adapterCollection);
	}

	public void testReplaceItem() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				// override failure
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				// override failure
			}
		});
		this.wrappedListHolder.addItem(0, "foo");
		this.wrappedListHolder.addItem(1, "bar");
		this.wrappedListHolder.addItem(2, "baz");
		Collection adapterCollection = CollectionTools.collection((Iterator) this.adapter.values());
		assertEquals(3, adapterCollection.size());
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			public void itemsRemoved(CollectionChangeEvent e) {
				assertEquals("foo", e.items().next());
				assertFalse(CollectionTools.contains((Iterator) ListCollectionValueModelAdapterTests.this.adapter.values(), "joo"));
				assertEquals(2, ListCollectionValueModelAdapterTests.this.adapter.size());
			}
			public void itemsAdded(CollectionChangeEvent e) {
				assertEquals("joo", e.items().next());
				assertEquals(3, ListCollectionValueModelAdapterTests.this.adapter.size());
			}
		});
		this.wrappedListHolder.replaceItem(0, "joo");
		adapterCollection = CollectionTools.collection((Iterator) this.adapter.values());
		assertEquals(3, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);
	}

	public void testHasListeners() {
		assertFalse(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		SynchronizedBag synchCollection = new SynchronizedBag(this.adapter);
		assertTrue(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.adapter.removeCollectionChangeListener(CollectionValueModel.VALUES, synchCollection);
		assertFalse(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.adapter.addCollectionChangeListener(synchCollection);
		assertTrue(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.adapter.removeCollectionChangeListener(synchCollection);
		assertFalse(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
	}
	
	public void testListChangedToEmpty() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			public void itemsAdded(CollectionChangeEvent e) {/* OK */}
			public void itemsRemoved(CollectionChangeEvent e) {/* OK */}
		});
		this.wrappedListHolder.addItem(0, "foo");
		this.wrappedListHolder.addItem(1, "bar");
		this.wrappedListHolder.addItem(2, "baz");
		JList jList = new JList(new ListModelAdapter(this.adapter));
		((SimpleListValueModel) this.wrappedListHolder).setValue(new ArrayList());
		assertEquals(0, jList.getModel().getSize());
	}
	
	public void testCollectionChangedFromEmpty() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			public void itemsAdded(CollectionChangeEvent e) {/* OK */}
			public void itemsRemoved(CollectionChangeEvent e) {/* OK */}
		});
		JList jList = new JList(new ListModelAdapter(this.adapter));
		
		ArrayList list = new ArrayList();
		list.add("foo");
		list.add("bar");
		((SimpleListValueModel) this.wrappedListHolder).setValue(list);
		assertEquals(2, jList.getModel().getSize());
	}
	
	public void testCollectionChangedFromEmptyToEmpty() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			public void itemsAdded(CollectionChangeEvent e) {/* OK */}
			public void itemsRemoved(CollectionChangeEvent e) {/* OK */}
		});
		JList jList = new JList(new ListModelAdapter(this.adapter));
		
		ArrayList list = new ArrayList();
		((SimpleListValueModel) this.wrappedListHolder).setValue(list);
		assertEquals(0, jList.getModel().getSize());
	}


	// ********** inner class **********

	private class TestListener implements CollectionChangeListener {
		public void itemsAdded(CollectionChangeEvent e) {
			fail("unexpected event");
		}
		public void itemsRemoved(CollectionChangeEvent e) {
			fail("unexpected event");
		}
		public void collectionCleared(CollectionChangeEvent e) {
			fail("unexpected event");
		}
		public void collectionChanged(CollectionChangeEvent e) {
			fail("unexpected event");
		}
	}

}
