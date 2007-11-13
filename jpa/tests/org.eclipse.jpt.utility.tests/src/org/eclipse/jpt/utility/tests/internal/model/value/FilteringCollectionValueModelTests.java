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

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Filter;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

public class FilteringCollectionValueModelTests extends TestCase {
	private CollectionValueModel collectionHolder;
	CollectionChangeEvent addEvent;
	CollectionChangeEvent removeEvent;
	CollectionChangeEvent collectionClearedEvent;
	CollectionChangeEvent collectionChangedEvent;

	private CollectionValueModel filteredCollectionHolder;
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
		this.collectionHolder = new SimpleCollectionValueModel(buildCollection());
		this.filteredCollectionHolder = new FilteringCollectionValueModel(this.collectionHolder, this.buildFilter());
	}

	private Collection buildCollection() {
		Collection collection = new Vector();
		collection.add("foo");
		return collection;
	}

	private Filter buildFilter() {
		return new Filter() {
			public boolean accept(Object o) {
				return ((String) o).startsWith("b");
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValues() {
		// add a listener to "activate" the wrapper
		this.filteredCollectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildFilteredListener());

		assertEquals("foo", ((Iterator) this.collectionHolder.values()).next());
		assertFalse(((Iterator) this.filteredCollectionHolder.values()).hasNext());

		this.collectionHolder.add("bar");
		Iterator collectionHolderValue = (Iterator) this.collectionHolder.values();
		assertEquals("foo", collectionHolderValue.next());
		assertEquals("bar", collectionHolderValue.next());
		assertTrue(((Iterator) this.filteredCollectionHolder.values()).hasNext());
		assertEquals("bar", ((Iterator) this.filteredCollectionHolder.values()).next());

		this.collectionHolder.remove("bar");
		assertEquals("foo", ((Iterator) this.collectionHolder.values()).next());
		assertFalse(((Iterator) this.filteredCollectionHolder.values()).hasNext());

		this.collectionHolder.remove("foo");
		assertFalse(((Iterator) this.collectionHolder.values()).hasNext());
		assertFalse(((Iterator) this.filteredCollectionHolder.values()).hasNext());

		this.collectionHolder.add("foo");
		assertEquals("foo", ((Iterator) this.collectionHolder.values()).next());
		assertFalse(((Iterator) this.filteredCollectionHolder.values()).hasNext());
	}

	public void testSetValue() {
		// add a listener to "activate" the wrapper
		this.filteredCollectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildFilteredListener());

		Collection newCollection = new Vector();
		newCollection.add("fox");
		newCollection.add("baz");
		
		((SimpleCollectionValueModel) this.collectionHolder).setValue(newCollection);

		Iterator collectionValues = (Iterator) this.collectionHolder.values();
		assertEquals("fox", collectionValues.next());
		assertEquals("baz", collectionValues.next());
		Iterator filteredCollectionValues = (Iterator) this.filteredCollectionHolder.values();
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
		Collection tempCollection = new Vector();
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
		Collection newCollection = new Vector();
		newCollection.add("fox");
		newCollection.add("baz");
		
		((SimpleCollectionValueModel) this.collectionHolder).setValue(newCollection);

		this.verifyEvent(this.collectionChangedEvent, this.collectionHolder, new Vector());
		
		tempCollection.remove("foo");
		tempCollection.add("baz");
		this.verifyEvent(this.filteredCollectionChangedEvent, this.filteredCollectionHolder, new Vector());
		
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
		this.collectionHolder = new SimpleCollectionValueModel(this.buildCollection2());
		this.filteredCollectionHolder = new FilteringCollectionValueModel(this.collectionHolder, this.buildFilter2());
		// add a listener to "activate" the wrapper
		this.filteredCollectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildFilteredListener());

		assertEquals(0, this.filteredCollectionHolder.size());

		this.collectionHolder.add(new TestItem("bar"));
		assertEquals(1, this.filteredCollectionHolder.size());

		TestItem baz = new TestItem("baz");
		this.collectionHolder.add(baz);
		assertEquals(2, this.filteredCollectionHolder.size());
		// before removing it, change the item so that it is filtered
		baz.name = "jaz";
		this.collectionHolder.remove(baz);
		// this would fail because the item was not removed from
		// the filtered collection cache... but we've fixed it now
		assertEquals(1, this.filteredCollectionHolder.size());
	}

	private Collection buildCollection2() {
		Collection collection = new Vector();
		collection.add(new TestItem("foo"));
		return collection;
	}

	private Filter buildFilter2() {
		return new Filter() {
			public boolean accept(Object o) {
				return ((TestItem) o).name.startsWith("b");
			}
		};
	}


private class TestItem {
	String name;
	TestItem(String name) {
		super();
		this.name = name;
	}
}

}
