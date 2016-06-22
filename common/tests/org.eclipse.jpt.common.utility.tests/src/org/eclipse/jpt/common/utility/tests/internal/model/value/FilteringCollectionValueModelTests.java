/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import junit.framework.TestCase;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.FilteringCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class FilteringCollectionValueModelTests extends TestCase {
	private SimpleCollectionValueModel<String> simpleCVM;
	CollectionAddEvent addEvent;
	CollectionRemoveEvent removeEvent;
	CollectionClearEvent collectionClearedEvent;
	CollectionChangeEvent collectionChangedEvent;

	private CollectionValueModel<String> filteredCVM;
	CollectionAddEvent filteredAddEvent;
	CollectionRemoveEvent filteredRemoveEvent;
	CollectionClearEvent filteredCollectionClearedEvent;
	CollectionChangeEvent filteredCollectionChangedEvent;

	public FilteringCollectionValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.simpleCVM = new SimpleCollectionValueModel<>(buildCollection());
		this.filteredCVM = CollectionValueModelTools.filter(this.simpleCVM, this.buildFilter());
	}

	private Collection<String> buildCollection() {
		Collection<String> collection = new Vector<>();
		collection.add("foo");
		return collection;
	}

	private Predicate<String> buildFilter() {
		return new StringStartsWithB();
	}

	class StringStartsWithB
		extends PredicateAdapter<String>
	{
		@Override
		public boolean evaluate(String s) {
			return s.startsWith("b");
		}
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		// add a listener to "activate" the wrapper
		this.filteredCVM.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildFilteredListener());

		assertEquals("foo", this.simpleCVM.iterator().next());
		assertFalse(this.filteredCVM.iterator().hasNext());

		this.simpleCVM.add("bar");
		Iterator<String> collectionHolderValue = this.simpleCVM.iterator();
		assertEquals("foo", collectionHolderValue.next());
		assertEquals("bar", collectionHolderValue.next());
		assertTrue(this.filteredCVM.iterator().hasNext());
		assertEquals("bar", this.filteredCVM.iterator().next());

		this.simpleCVM.remove("bar");
		assertEquals("foo", this.simpleCVM.iterator().next());
		assertFalse(this.filteredCVM.iterator().hasNext());

		this.simpleCVM.remove("foo");
		assertFalse(this.simpleCVM.iterator().hasNext());
		assertFalse(this.filteredCVM.iterator().hasNext());

		this.simpleCVM.add("foo");
		assertEquals("foo", this.simpleCVM.iterator().next());
		assertFalse(this.filteredCVM.iterator().hasNext());
	}

	public void testSetValue() {
		// add a listener to "activate" the wrapper
		this.filteredCVM.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildFilteredListener());

		Collection<String> newCollection = new Vector<>();
		newCollection.add("fox");
		newCollection.add("baz");
		
		this.simpleCVM.setValues(newCollection);

		Iterator<String> collectionValues = this.simpleCVM.iterator();
		assertEquals("fox", collectionValues.next());
		assertEquals("baz", collectionValues.next());
		Iterator<String> filteredCollectionValues = this.filteredCVM.iterator();
		assertEquals("baz", filteredCollectionValues.next());
		assertFalse(filteredCollectionValues.hasNext());
	}		

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.simpleCVM).hasNoCollectionChangeListeners(CollectionValueModel.VALUES));
		ChangeListener listener = this.buildFilteredChangeListener();
		this.filteredCVM.addChangeListener(listener);
		assertTrue(((AbstractModel) this.simpleCVM).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.filteredCVM.removeChangeListener(listener);
		assertTrue(((AbstractModel) this.simpleCVM).hasNoCollectionChangeListeners(CollectionValueModel.VALUES));

		this.filteredCVM.addCollectionChangeListener(CollectionValueModel.VALUES, listener);
		assertTrue(((AbstractModel) this.simpleCVM).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.filteredCVM.removeCollectionChangeListener(CollectionValueModel.VALUES, listener);
		assertTrue(((AbstractModel) this.simpleCVM).hasNoCollectionChangeListeners(CollectionValueModel.VALUES));
	}

	public void testCollectionChange1() {
		this.simpleCVM.addChangeListener(this.buildChangeListener());
		this.filteredCVM.addChangeListener(this.buildFilteredChangeListener());
		this.verifyCollectionChanges();
	}

	public void testCollectionChange2() {
		this.simpleCVM.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildListener());
		this.filteredCVM.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildFilteredListener());
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
		this.simpleCVM.add("bar");
		Collection<String> tempCollection = new Vector<>();
		tempCollection.add("bar");
		this.verifyEvent(this.addEvent, this.simpleCVM, tempCollection);
		this.verifyEvent(this.filteredAddEvent, this.filteredCVM, tempCollection);
		
		clearEvents();
		this.simpleCVM.remove("foo");
		tempCollection.remove("bar");
		tempCollection.add("foo");
		this.verifyEvent(this.removeEvent, this.simpleCVM, tempCollection);
		assertNull(this.filteredRemoveEvent);


		clearEvents();
		this.simpleCVM.remove("bar");
		tempCollection.add("bar");
		tempCollection.remove("foo");
		this.verifyEvent(this.removeEvent, this.simpleCVM, tempCollection);
		this.verifyEvent(this.filteredRemoveEvent, this.filteredCVM, tempCollection);


		clearEvents();
		this.simpleCVM.add("foo");
		tempCollection.remove("bar");
		tempCollection.add("foo");
		this.verifyEvent(this.addEvent, this.simpleCVM, tempCollection);
		assertNull(this.filteredAddEvent);


		clearEvents();
		Collection<String> newCollection = new Vector<>();
		newCollection.add("fox");
		newCollection.add("baz");
		
		this.simpleCVM.setValues(newCollection);

		this.verifyEvent(this.collectionChangedEvent, this.simpleCVM);
		
		tempCollection.remove("foo");
		tempCollection.add("baz");
		this.verifyEvent(this.filteredCollectionChangedEvent, this.filteredCVM);
		
	}

	private CollectionChangeListener buildListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionAddEvent e) {
				FilteringCollectionValueModelTests.this.addEvent = e;
			}
			public void itemsRemoved(CollectionRemoveEvent e) {
				FilteringCollectionValueModelTests.this.removeEvent = e;
			}
			public void collectionCleared(CollectionClearEvent e) {
				FilteringCollectionValueModelTests.this.collectionClearedEvent = e;
			}
			public void collectionChanged(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.collectionChangedEvent = e;
			}
		};
	}

	private ChangeListener buildChangeListener() {
		return new ChangeAdapter() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {
				FilteringCollectionValueModelTests.this.addEvent = e;
			}
			@Override
			public void itemsRemoved(CollectionRemoveEvent e) {
				FilteringCollectionValueModelTests.this.removeEvent = e;
			}
			@Override
			public void collectionCleared(CollectionClearEvent e) {
				FilteringCollectionValueModelTests.this.collectionClearedEvent = e;
			}
			@Override
			public void collectionChanged(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.collectionChangedEvent = e;
			}
		};
	}

	private CollectionChangeListener buildFilteredListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionAddEvent e) {
				FilteringCollectionValueModelTests.this.filteredAddEvent = e;
			}
			public void itemsRemoved(CollectionRemoveEvent e) {
				FilteringCollectionValueModelTests.this.filteredRemoveEvent = e;
			}
			public void collectionCleared(CollectionClearEvent e) {
				FilteringCollectionValueModelTests.this.filteredCollectionClearedEvent = e;
			}
			public void collectionChanged(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.filteredCollectionChangedEvent = e;
			}
		};
	}

	private ChangeListener buildFilteredChangeListener() {
		return new ChangeAdapter() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {
				FilteringCollectionValueModelTests.this.filteredAddEvent = e;
			}
			@Override
			public void itemsRemoved(CollectionRemoveEvent e) {
				FilteringCollectionValueModelTests.this.filteredRemoveEvent = e;
			}
			@Override
			public void collectionCleared(CollectionClearEvent e) {
				FilteringCollectionValueModelTests.this.filteredCollectionClearedEvent = e;
			}
			@Override
			public void collectionChanged(CollectionChangeEvent e) {
				FilteringCollectionValueModelTests.this.filteredCollectionChangedEvent = e;
			}
		};
	}

	private void verifyEvent(CollectionChangeEvent event, Object source) {
		assertEquals(source, event.getSource());
		assertEquals(CollectionValueModel.VALUES, event.getCollectionName());
	}

	private void verifyEvent(CollectionAddEvent event, Object source, Object items) {
		assertEquals(source, event.getSource());
		assertEquals(CollectionValueModel.VALUES, event.getCollectionName());
		assertEquals(items, CollectionTools.vector(event.getItems()));
	}

	private void verifyEvent(CollectionRemoveEvent event, Object source, Object items) {
		assertEquals(source, event.getSource());
		assertEquals(CollectionValueModel.VALUES, event.getCollectionName());
		assertEquals(items, CollectionTools.vector(event.getItems()));
	}

	public void testRemoveFilteredItem() {
		// build collection with TestItems
		SimpleCollectionValueModel<TestItem> tiHolder = new SimpleCollectionValueModel<>(this.buildCollection2());
		CollectionValueModel<TestItem> filteredTIHolder = new FilteringCollectionValueModel<>(tiHolder, this.buildFilter2());
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
		Collection<TestItem> collection = new Vector<>();
		collection.add(new TestItem("foo"));
		return collection;
	}

	private Predicate<TestItem> buildFilter2() {
		return new NameStartsWithB();
	}


	class NameStartsWithB
		extends PredicateAdapter<TestItem>
	{
		@Override
		public boolean evaluate(TestItem ti) {
			return ti.name.startsWith("b");
		}
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
