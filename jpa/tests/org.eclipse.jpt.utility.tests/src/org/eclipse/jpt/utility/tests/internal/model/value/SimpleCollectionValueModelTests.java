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
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class SimpleCollectionValueModelTests extends TestCase {
	private SimpleCollectionValueModel<String> bagHolder;
	CollectionChangeEvent bagEvent;
	String bagEventType;

	private SimpleCollectionValueModel<String> setHolder;
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
		this.bagHolder = new SimpleCollectionValueModel<String>(this.buildBag());
		this.setHolder = new SimpleCollectionValueModel<String>(this.buildSet());
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

	public void testIterator() {
		assertEquals(this.buildBag(), CollectionTools.bag(this.bagHolder.iterator()));
		assertEquals(this.buildSet(), CollectionTools.set(this.setHolder.iterator()));
	}

	public void testSize() {
		assertEquals(this.buildBag().size(), CollectionTools.size(this.bagHolder.iterator()));
		assertEquals(this.buildSet().size(), CollectionTools.size(this.setHolder.iterator()));
	}

	private boolean bagHolderContains(Object item) {
		return CollectionTools.contains(this.bagHolder.iterator(), item);
	}

	private boolean setHolderContains(Object item) {
		return CollectionTools.contains(this.setHolder.iterator(), item);
	}

	private boolean bagHolderContainsAll(Collection<String> items) {
		return CollectionTools.containsAll(this.bagHolder.iterator(), items);
	}

	private boolean setHolderContainsAll(Collection<String> items) {
		return CollectionTools.containsAll(this.setHolder.iterator(), items);
	}

	private boolean bagHolderContainsAny(Collection<String> items) {
		Bag<String> bag = CollectionTools.bag(this.bagHolder.iterator());
		for (String string : items) {
			if (bag.contains(string)) {
				return true;
			}
		}
		return false;
	}

	private boolean setHolderContainsAny(Collection<String> items) {
		Set<String> set = CollectionTools.set(this.setHolder.iterator());
		for (String string : items) {
			if (set.contains(string)) {
				return true;
			}
		}
		return false;
	}

	public void testAdd() {
		assertFalse(this.bagHolderContains("joo"));
		this.bagHolder.add("joo");
		assertTrue(this.bagHolderContains("joo"));

		assertFalse(this.bagHolderContains(null));
		this.bagHolder.add(null);
		assertTrue(this.bagHolderContains(null));

		assertFalse(this.setHolderContains("joo"));
		this.setHolder.add("joo");
		assertTrue(this.setHolderContains("joo"));

		assertFalse(this.setHolderContains(null));
		this.setHolder.add(null);
		assertTrue(this.setHolderContains(null));
	}

	public void testAddAll() {
		assertFalse(this.bagHolderContainsAny(this.buildAddItems()));
		this.bagHolder.addAll(this.buildAddItems());
		assertTrue(this.bagHolderContainsAll(this.buildAddItems()));

		assertFalse(this.setHolderContainsAny(this.buildAddItems()));
		this.setHolder.addAll(this.buildAddItems());
		assertTrue(this.setHolderContainsAll(this.buildAddItems()));
	}

	public void testRemove() {
		assertTrue(this.bagHolderContains("bar"));
		this.bagHolder.remove("bar");
		assertFalse(this.bagHolderContains("bar"));

		this.bagHolder.add(null);
		assertTrue(this.bagHolderContains(null));
		this.bagHolder.remove(null);
		assertFalse(this.bagHolderContains(null));

		assertTrue(this.setHolderContains("bar"));
		this.setHolder.remove("bar");
		assertFalse(this.setHolderContains("bar"));

		this.setHolder.add(null);
		assertTrue(this.setHolderContains(null));
		this.setHolder.remove(null);
		assertFalse(this.setHolderContains(null));
	}

	public void testRemoveAll() {
		assertTrue(this.bagHolderContainsAll(this.buildRemoveItems()));
		this.bagHolder.removeAll(this.buildRemoveItems());
		assertFalse(this.bagHolderContainsAny(this.buildRemoveItems()));

		assertTrue(this.setHolderContainsAll(this.buildRemoveItems()));
		this.setHolder.removeAll(this.buildRemoveItems());
		assertFalse(this.setHolderContainsAny(this.buildRemoveItems()));
	}

	public void testSetCollection() {
		assertTrue(this.bagHolderContains("bar"));
		assertFalse(this.bagHolderContains("jar"));
		this.bagHolder.setCollection(this.buildAddItems());
		assertFalse(this.bagHolderContains("bar"));
		assertTrue(this.bagHolderContains("jar"));

		this.bagHolder.add(null);
		assertTrue(this.bagHolderContains(null));
		this.bagHolder.remove(null);
		assertFalse(this.bagHolderContains(null));

		this.bagHolder.setCollection(new HashBag<String>());
		assertFalse(this.bagHolderContains("jar"));

		assertTrue(this.setHolderContains("bar"));
		assertFalse(this.setHolderContains("jar"));
		this.setHolder.setCollection(this.buildAddItems());
		assertFalse(this.setHolderContains("bar"));
		assertTrue(this.setHolderContains("jar"));

		this.setHolder.add(null);
		assertTrue(this.setHolderContains(null));
		this.setHolder.remove(null);
		assertFalse(this.setHolderContains(null));

		this.setHolder.setCollection(new HashBag<String>());
		assertFalse(this.setHolderContains("jar"));
	}

	public void testCollectionChange1() {
		this.bagHolder.addCollectionChangeListener(this.buildBagListener());
		this.verifyBagChange();

		this.setHolder.addCollectionChangeListener(this.buildSetListener());
		this.verifySetChange();
	}

	public void testCollectionChange2() {
		this.bagHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildBagListener());
		this.verifyBagChange();

		this.setHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildSetListener());
		this.verifySetChange();
	}

	private void verifyBagChange() {
		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.add("foo");
		this.verifyBagEvent(ADD, "foo");

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.add("foo");
		this.verifyBagEvent(ADD, "foo");

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.add("joo");
		this.verifyBagEvent(ADD, "joo");

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.add(null);
		this.verifyBagEvent(ADD, null);

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.add(null);
		this.verifyBagEvent(ADD, null);

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.remove("joo");
		this.verifyBagEvent(REMOVE, "joo");

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.remove(null);
		this.verifyBagEvent(REMOVE, null);

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.setCollection(this.buildBag());
		this.verifyBagEvent(CHANGE);

		this.bagEvent = null;
		this.bagEventType = null;
		this.bagHolder.addAll(this.buildBag());
		this.verifyBagEvent(ADD);
		assertEquals(this.buildBag(), CollectionTools.bag(this.bagEvent.items()));
	}

	private void verifySetChange() {
		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.add("foo");
		assertNull(this.setEvent);
		assertNull(this.setEventType);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.add("joo");
		this.verifySetEvent(ADD, "joo");

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.add("joo");
		assertNull(this.setEvent);
		assertNull(this.setEventType);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.add(null);
		this.verifySetEvent(ADD, null);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.add(null);
		assertNull(this.setEvent);
		assertNull(this.setEventType);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.remove("joo");
		this.verifySetEvent(REMOVE, "joo");

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.remove("joo");
		assertNull(this.setEvent);
		assertNull(this.setEventType);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.remove(null);
		this.verifySetEvent(REMOVE, null);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.setCollection(this.buildSet());
		this.verifySetEvent(CHANGE);

		this.setEvent = null;
		this.setEventType = null;
		this.setHolder.addAll(this.buildSet());
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
		assertEquals(CollectionValueModel.VALUES, this.bagEvent.collectionName());
	}

	private void verifyBagEvent(String eventType, Object item) {
		this.verifyBagEvent(eventType);
		assertEquals(item, this.bagEvent.items().next());
	}

	private void verifySetEvent(String eventType) {
		assertEquals(eventType, this.setEventType);
		assertEquals(this.setHolder, this.setEvent.getSource());
		assertEquals(CollectionValueModel.VALUES, this.setEvent.collectionName());
	}

	private void verifySetEvent(String eventType, Object item) {
		this.verifySetEvent(eventType);
		assertEquals(item, this.setEvent.items().next());
	}

}
