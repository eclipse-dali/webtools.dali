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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class SimpleCollectionValueModelTests extends TestCase {
	private CollectionValueModel bagHolder;
	CollectionChangeEvent bagEvent;
	String bagEventType;

	private CollectionValueModel setHolder;
	CollectionChangeEvent setEvent;
	String setEventType;

	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String CHANGE = "change";
	private static final String CLEAR = "clear";

	
	public SimpleCollectionValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.bagHolder = new SimpleCollectionValueModel(this.buildBag());
		this.setHolder = new SimpleCollectionValueModel(this.buildSet());
	}

	private Bag<String> buildBag() {
		Bag<String> result = new HashBag<String>();
		this.addItemsTo(result);
		return result;
	}

	private Set<String> buildSet() {
		Set<String> result = new HashSet<String>();
		this.addItemsTo(result);
		return result;
	}

	private void addItemsTo(Collection<String> c) {
		c.add("foo");
		c.add("bar");
		c.add("baz");
	}

	private Bag<String> buildAddItems() {
		Bag<String> result = new HashBag<String>();
		result.add("joo");
		result.add("jar");
		result.add("jaz");
		return result;
	}

	private Bag<String> buildRemoveItems() {
		Bag<String> result = new HashBag<String>();
		result.add("foo");
		result.add("baz");
		return result;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValue() {
		assertEquals(this.buildBag(), CollectionTools.bag((Iterator) this.bagHolder.value()));
		assertEquals(this.buildSet(), CollectionTools.set((Iterator) this.setHolder.value()));
	}

	public void testSize() {
		assertEquals(this.buildBag().size(), CollectionTools.size((Iterator) this.bagHolder.value()));
		assertEquals(this.buildSet().size(), CollectionTools.size((Iterator) this.setHolder.value()));
	}

	private boolean bagHolderContains(Object item) {
		return CollectionTools.contains((Iterator) this.bagHolder.value(), item);
	}

	private boolean setHolderContains(Object item) {
		return CollectionTools.contains((Iterator) this.setHolder.value(), item);
	}

	private boolean bagHolderContainsAll(Collection<String> items) {
		return CollectionTools.containsAll((Iterator) this.bagHolder.value(), items);
	}

	private boolean setHolderContainsAll(Collection<String> items) {
		return CollectionTools.containsAll((Iterator) this.setHolder.value(), items);
	}

	private boolean bagHolderContainsAny(Collection<String> items) {
		Bag bag = CollectionTools.bag((Iterator) this.bagHolder.value());
		for (String string : items) {
			if (bag.contains(string)) {
				return true;
			}
		}
		return false;
	}

	private boolean setHolderContainsAny(Collection<String> items) {
		Set set = CollectionTools.set((Iterator) this.setHolder.value());
		for (String string : items) {
			if (set.contains(string)) {
				return true;
			}
		}
		return false;
	}

	public void testAddItem() {
		assertFalse(this.bagHolderContains("joo"));
		this.bagHolder.addItem("joo");
		assertTrue(this.bagHolderContains("joo"));

		assertFalse(this.bagHolderContains(null));
		this.bagHolder.addItem(null);
		assertTrue(this.bagHolderContains(null));

		assertFalse(this.setHolderContains("joo"));
		this.setHolder.addItem("joo");
		assertTrue(this.setHolderContains("joo"));

		assertFalse(this.setHolderContains(null));
		this.setHolder.addItem(null);
		assertTrue(this.setHolderContains(null));
	}

	public void testAddItems() {
		assertFalse(this.bagHolderContainsAny(this.buildAddItems()));
		this.bagHolder.addItems(this.buildAddItems());
		assertTrue(this.bagHolderContainsAll(this.buildAddItems()));

		assertFalse(this.setHolderContainsAny(this.buildAddItems()));
		this.setHolder.addItems(this.buildAddItems());
		assertTrue(this.setHolderContainsAll(this.buildAddItems()));
	}

	public void testRemoveItem() {
		assertTrue(this.bagHolderContains("bar"));
		this.bagHolder.removeItem("bar");
		assertFalse(this.bagHolderContains("bar"));

		this.bagHolder.addItem(null);
		assertTrue(this.bagHolderContains(null));
		this.bagHolder.removeItem(null);
		assertFalse(this.bagHolderContains(null));

		assertTrue(this.setHolderContains("bar"));
		this.setHolder.removeItem("bar");
		assertFalse(this.setHolderContains("bar"));

		this.setHolder.addItem(null);
		assertTrue(this.setHolderContains(null));
		this.setHolder.removeItem(null);
		assertFalse(this.setHolderContains(null));
	}

	public void testRemoveItems() {
		assertTrue(this.bagHolderContainsAll(this.buildRemoveItems()));
		this.bagHolder.removeItems(this.buildRemoveItems());
		assertFalse(this.bagHolderContainsAny(this.buildRemoveItems()));

		assertTrue(this.setHolderContainsAll(this.buildRemoveItems()));
		this.setHolder.removeItems(this.buildRemoveItems());
		assertFalse(this.setHolderContainsAny(this.buildRemoveItems()));
	}

	public void testSetValue() {
		assertTrue(this.bagHolderContains("bar"));
		assertFalse(this.bagHolderContains("jar"));
		((SimpleCollectionValueModel) this.bagHolder).setValue(this.buildAddItems());
		assertFalse(this.bagHolderContains("bar"));
		assertTrue(this.bagHolderContains("jar"));

		this.bagHolder.addItem(null);
		assertTrue(this.bagHolderContains(null));
		this.bagHolder.removeItem(null);
		assertFalse(this.bagHolderContains(null));

		((SimpleCollectionValueModel) this.bagHolder).setValue(null);
		assertFalse(this.bagHolderContains("jar"));

		assertTrue(this.setHolderContains("bar"));
		assertFalse(this.setHolderContains("jar"));
		((SimpleCollectionValueModel) this.setHolder).setValue(this.buildAddItems());
		assertFalse(this.setHolderContains("bar"));
		assertTrue(this.setHolderContains("jar"));

		this.setHolder.addItem(null);
		assertTrue(this.setHolderContains(null));
		this.setHolder.removeItem(null);
		assertFalse(this.setHolderContains(null));

		((SimpleCollectionValueModel) this.setHolder).setValue(null);
		assertFalse(this.setHolderContains("jar"));
	}

	public void testCollectionChange1() {
		this.bagHolder.addCollectionChangeListener(this.buildBagListener());
		this.verifyBagChange();

		this.setHolder.addCollectionChangeListener(this.buildSetListener());
		this.verifySetChange();
	}

	public void testCollectionChange2() {
		this.bagHolder.addCollectionChangeListener(ValueModel.VALUE, this.buildBagListener());
		this.verifyBagChange();

		this.setHolder.addCollectionChangeListener(ValueModel.VALUE, this.buildSetListener());
		this.verifySetChange();
	}

	private void verifyBagChange() {
		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.addItem("foo");
		this.verifyBagEvent(ADD, "foo");

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.addItem("foo");
		this.verifyBagEvent(ADD, "foo");

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.addItem("joo");
		this.verifyBagEvent(ADD, "joo");

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.addItem(null);
		this.verifyBagEvent(ADD, null);

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.addItem(null);
		this.verifyBagEvent(ADD, null);

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.removeItem("joo");
		this.verifyBagEvent(REMOVE, "joo");

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.removeItem(null);
		this.verifyBagEvent(REMOVE, null);

		this.bagEvent = null;
		this.bagEventType = null;
		((SimpleCollectionValueModel) this.bagHolder).setValue(this.buildBag());
		this.verifyBagEvent(CHANGE);

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.addItems(this.buildBag());
		this.verifyBagEvent(ADD);
		assertEquals(this.buildBag(), CollectionTools.bag(this.bagEvent.items()));
	}

	private void verifySetChange() {
		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.addItem("foo");
		assertNull(this.setEvent);
		assertNull(this.setEventType);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.addItem("joo");
		this.verifySetEvent(ADD, "joo");

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.addItem("joo");
		assertNull(this.setEvent);
		assertNull(this.setEventType);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.addItem(null);
		this.verifySetEvent(ADD, null);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.addItem(null);
		assertNull(this.setEvent);
		assertNull(this.setEventType);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.removeItem("joo");
		this.verifySetEvent(REMOVE, "joo");

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.removeItem("joo");
		assertNull(this.setEvent);
		assertNull(this.setEventType);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.removeItem(null);
		this.verifySetEvent(REMOVE, null);

		this.setEvent = null;
		this.setEventType = null;
		((SimpleCollectionValueModel) this.setHolder).setValue(this.buildSet());
		this.verifySetEvent(CHANGE);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.addItems(this.buildSet());
		assertNull(this.setEvent);
		assertNull(this.setEventType);
	}

	private CollectionChangeListener buildBagListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				SimpleCollectionValueModelTests.this.bagEventType = ADD;
				SimpleCollectionValueModelTests.this.bagEvent = e;
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				SimpleCollectionValueModelTests.this.bagEventType = REMOVE;
				SimpleCollectionValueModelTests.this.bagEvent = e;
			}
			public void collectionCleared(CollectionChangeEvent e) {
				SimpleCollectionValueModelTests.this.bagEventType = CLEAR;
				SimpleCollectionValueModelTests.this.bagEvent = e;
			}
			public void collectionChanged(CollectionChangeEvent e) {
				SimpleCollectionValueModelTests.this.bagEventType = CHANGE;
				SimpleCollectionValueModelTests.this.bagEvent = e;
			}
		};
	}

	private CollectionChangeListener buildSetListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionChangeEvent e) {
				SimpleCollectionValueModelTests.this.setEventType = ADD;
				SimpleCollectionValueModelTests.this.setEvent = e;
			}
			public void itemsRemoved(CollectionChangeEvent e) {
				SimpleCollectionValueModelTests.this.setEventType = REMOVE;
				SimpleCollectionValueModelTests.this.setEvent = e;
			}
			public void collectionCleared(CollectionChangeEvent e) {
				SimpleCollectionValueModelTests.this.setEventType = CLEAR;
				SimpleCollectionValueModelTests.this.setEvent = e;
			}
			public void collectionChanged(CollectionChangeEvent e) {
				SimpleCollectionValueModelTests.this.setEventType = CHANGE;
				SimpleCollectionValueModelTests.this.setEvent = e;
			}
		};
	}

	private void verifyBagEvent(String eventType) {
		assertEquals(eventType, this.bagEventType);
		assertEquals(this.bagHolder, this.bagEvent.getSource());
		assertEquals(ValueModel.VALUE, this.bagEvent.collectionName());
	}

	private void verifyBagEvent(String eventType, Object item) {
		this.verifyBagEvent(eventType);
		assertEquals(item, this.bagEvent.items().next());
	}

	private void verifySetEvent(String eventType) {
		assertEquals(eventType, this.setEventType);
		assertEquals(this.setHolder, this.setEvent.getSource());
		assertEquals(ValueModel.VALUE, this.setEvent.collectionName());
	}

	private void verifySetEvent(String eventType, Object item) {
		this.verifySetEvent(eventType);
		assertEquals(item, this.setEvent.items().next());
	}

}
