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

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import junit.framework.TestCase;

import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

public class FilteringCollectionValueModelTests extends TestCase {
	private SimpleCollectionValueModel<String> collectionHolder;
	CollectionChangeEvent addEvent;
	CollectionChangeEvent removeEvent;
	CollectionChangeEvent collectionClearedEvent;
	CollectionChangeEvent collectionChangedEvent;

	private CollectionValueModel<String> filteredCollectionHolder;
	CollectionChangeEvent filteredAddEvent;
	CollectionChangeEvent filteredRemoveEvent;
	CollectionChangeEvent filteredCollectionClearedEvent;
	CollectionChangeEvent filteredCollectionChangedEvent;

	public FilteringCollectionValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.collectionHolder = new SimpleCollectionValueModel<String>(buildCollection());
		this.filteredCollectionHolder = new FilteringCollectionValueModel<String>(this.collectionHolder, this.buildFilter());
	}

	private Collection<String> buildCollection() {
		Collection<String> collection = new Vector<String>();
		collection.add("foo");
		return collection;
	}

	private Filter<String> buildFilter() {
		return new Filter<String>() {
			public boolean accept(String s) {
				return s.startsWith("b");
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		// add a listener to "activate" the wrapper
		this.filteredCollectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildFilteredListener());

		assertEquals("foo", this.collectionHolder.iterator().next());
		assertFalse(this.filteredCollectionHolder.iterator().hasNext());

		this.collectionHolder.add("bar");
		Iterator<String> collectionHolderValue = this.collectionHolder.iterator();
		assertEquals("foo", collectionHolderValue.next());
		assertEquals("bar", collectionHolderValue.next());
		assertTrue(this.filteredCollectionHolder.iterator().hasNext());
		assertEquals("bar", this.filteredCollectionHolder.iterator().next());

		this.collectionHolder.remove("bar");
		assertEquals("foo", this.collectionHolder.iterator().next());
		assertFalse(this.filteredCollectionHolder.iterator().hasNext());

		this.collectionHolder.remove("foo");
		assertFalse(this.collectionHolder.iterator().hasNext());
		assertFalse(this.filteredCollectionHolder.iterator().hasNext());

		this.collectionHolder.add("foo");
		assertEquals("foo", this.collectionHolder.iterator().next());
		assertFalse(this.filteredCollectionHolder.iterator().hasNext());
	}

	public void testSetValue() {
		// add a listener to "activate" the wrapper
		this.filteredCollectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildFilteredListener());

		Collection<String> newCollection = new Vector<String>();
		newCollection.add("fox");
		newCollection.add("baz");
		
		this.collectionHolder.setCollection(newCollection);

		Iterator<String> collectionValues = this.collectionHolder.iterator();
		assertEquals("fox", collectionValues.next());
		assertEquals("baz", collectionValues.next());
		Iterator<String> filteredCollectionValues = this.filteredCollectionHolder.iterator();
		assertEquals("baz", filteredCollectionValues.next());
		assertFalse(filteredCollectionValues.hasNext());
	}		

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.collectionHolder).hasNoCollectionChangeListeners(CollectionValueModel.VALUES));
		CollectionChangeListener listener = this.buildFilteredListener();
		this.filteredCollectionHolder.addCollectionChangeListener(listener);
		assertTrue(((AbstractModel) this.collectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.filteredCollectionHolder.removeCollectionChangeListener(listener);
		assertTrue(((AbstractModel) this.collectionHolder).hasNoCollectionChangeListeners(CollectionValueModel.VALUES));

		this.filteredCollectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, listener);
		assertTrue(((AbstractModel) this.collectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.filteredCollectionHolder.removeCollectionChangeListener(CollectionValueModel.VALUES, listener);
		assertTrue(((AbstractModel) this.collectionHolder).hasNoCollectionChangeListeners(CollectionValueModel.VALUES));
	}

	public void testCollectionChange1() {
		this.collectionHolder.addCollectionChangeListener(this.buildListener());
		this.filteredCollectionHolder.addCollectionChangeListener(this.buildFilteredListener());
		this.verifyCollectionChanges();
	}

	public void testCollectionChange2() {
		this.collectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildListener());
		this.filteredCollectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildFilteredListener());
		this.verifyCollectionChanges();
	}

	private void clearEvents() {
		this.addEvent = null;
		this.removeEvent = null;
		this.collectionClearedEvent = null;
		this.collectionChangedEvent = null;
		this.filteredAddEvent = null;
		this.filteredRemoveEvent = null;
		this.filteredCollectionClearedEvent = null;
		this.filteredCollectionChangedEvent = null;
	}

	private void verifyCollectionChanges() {
		clearEvents();
		this.collectionHolder.add("bar");
		Collection<String> tempCollection = new Vector<String>();
		tempCollection.add("bar");
		this.verifyEvent(this.addEvent, this.collectionHolder, tempCollection);
		this.verifyEvent(this.filteredAddEvent, this.filteredCollectionHolder, tempCollection);
		
		clearEvents();
		this.collectionHolder.remove("foo");
		tempCollection.remove("bar");
		tempCollection.add("foo");
		this.verifyEvent(this.removeEvent, this.collectionHolder, tempCollection);
		assertNull(this.filteredRemoveEvent);


		clearEvents();
		this.collectionHolder.remove("bar");
		tempCollection.add("bar");
		tempCollection.remove("foo");
		this.verifyEvent(this.removeEvent, this.collectionHolder, tempCollection);
		this.verifyEvent(this.filteredRemoveEvent, this.filteredCollectionHolder, tempCollection);


		clearEvents();
		this.collectionHolder.add("foo");
		tempCollection.remove("bar");
		tempCollection.add("foo");
		this.verifyEvent(this.addEvent, this.collectionHolder, tempCollection);
		assertNull(this.filteredAddEvent);


		clearEvents();
		Collection<String> newCollection = new Vector<String>();
		newCollection.add("fox");
		newCollection.add("baz");
		
		this.collectionHolder.setCollection(newCollection);

		this.verifyEvent(this.collectionChangedEvent, this.collectionHolder, new Vector<String>());
		
		tempCollection.remove("foo");
		tempCollection.add("baz");
		this.verifyEvent(this.filteredCollectionChangedEvent, this.filteredCollectionHolder, new Vector<String>());
		
	}

	private CollectionChangeListener buildListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.addEvent = e;
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.removeEvent = e;
			}
			public void collectionCleared(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.collectionClearedEvent = e;
			}
			public void collectionChanged(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.collectionChangedEvent = e;
			}
		};
	}

	private CollectionChangeListener buildFilteredListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.filteredAddEvent = e;
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.filteredRemoveEvent = e;
			}
			public void collectionCleared(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.filteredCollectionClearedEvent = e;
			}
			public void collectionChanged(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.filteredCollectionChangedEvent = e;
			}
		};
	}

	private void verifyEvent(CollectionChangeEvent event, Object source, Object items) {
		assertEquals(source, event.getSource());
		assertEquals(CollectionValueModel.VALUES, event.collectionName());
		assertEquals(items, CollectionTools.vector(event.items()));
	}

	public void testRemoveFilteredItem() {
		// build collection with TestItems
		SimpleCollectionValueModel<TestItem> tiHolder = new SimpleCollectionValueModel<TestItem>(this.buildCollection2());
		CollectionValueModel<TestItem> filteredTIHolder = new FilteringCollectionValueModel<TestItem>(tiHolder, this.buildFilter2());
		// add a listener to "activate" the wrapper
		filteredTIHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildFilteredListener());

		assertEquals(0, filteredTIHolder.size());

		tiHolder.add(new TestItem("bar"));
		assertEquals(1, filteredTIHolder.size());

		TestItem baz = new TestItem("baz");
		tiHolder.add(baz);
		assertEquals(2, filteredTIHolder.size());
		// before removing it, change the item so that it is filtered
		baz.name = "jaz";
		tiHolder.remove(baz);
		// this would fail because the item was not removed from
		// the filtered collection cache... but we've fixed it now
		assertEquals(1, filteredTIHolder.size());
	}

	private Collection<TestItem> buildCollection2() {
		Collection<TestItem> collection = new Vector<TestItem>();
		collection.add(new TestItem("foo"));
		return collection;
	}

	private Filter<TestItem> buildFilter2() {
		return new Filter<TestItem>() {
			public boolean accept(TestItem ti) {
				return ti.name.startsWith("b");
			}
		};
	}


	// ********** TestItem inner class **********

	private class TestItem {
		String name;
		TestItem(String name) {
			super();
			this.name = name;
		}
	}

}
