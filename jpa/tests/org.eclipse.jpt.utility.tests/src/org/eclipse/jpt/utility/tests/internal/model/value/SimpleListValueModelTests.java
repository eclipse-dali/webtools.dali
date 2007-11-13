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
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class SimpleListValueModelTests extends TestCase {
	private ListValueModel listHolder;
	ListChangeEvent event;
	String eventType;

	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String REPLACE = "replace";
	private static final String MOVE = "move";
	private static final String CLEAR = "clear";
	private static final String CHANGE = "change";

	
	public SimpleListValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listHolder = new SimpleListValueModel(this.buildList());
	}

	private List buildList() {
		List result = new ArrayList();
		result.add("foo");
		result.add("bar");
		result.add("baz");
		return result;
	}

	private List buildAddList() {
		List result = new ArrayList();
		result.add("joo");
		result.add("jar");
		result.add("jaz");
		return result;
	}

	private List buildRemoveList() {
		List result = new ArrayList();
		result.add("foo");
		result.add("bar");
		return result;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValues() {
		assertEquals(this.buildList(), CollectionTools.list((Iterator) this.listHolder.values()));
	}

	public void testSize() {
		assertEquals(this.buildList().size(), CollectionTools.size((Iterator) this.listHolder.values()));
	}

	private boolean listContains(Object item) {
		return CollectionTools.contains((Iterator) this.listHolder.values(), item);
	}

	private boolean listContainsAll(Collection items) {
		return CollectionTools.containsAll((Iterator) this.listHolder.values(), items);
	}

	private boolean listContainsAny(Collection items) {
		List list = CollectionTools.list((ListIterator) this.listHolder.values());
		for (Iterator stream = items.iterator(); stream.hasNext(); ) {
			if (list.contains(stream.next())) {
				return true;
			}
		}
		return false;
	}

	public void testAdd() {
		assertFalse(this.listContains("joo"));
		this.listHolder.add(2, "joo");
		assertTrue(this.listContains("joo"));

		assertFalse(this.listContains(null));
		this.listHolder.add(0, null);
		assertTrue(this.listContains(null));
	}

	public void testAddAll() {
		assertFalse(this.listContainsAny(this.buildAddList()));
		this.listHolder.addAll(2, this.buildAddList());
		assertTrue(this.listContainsAll(this.buildAddList()));
	}

	public void testRemove() {
		assertTrue(this.listContains("bar"));
		this.listHolder.remove(this.buildList().indexOf("bar"));
		assertFalse(this.listContains("bar"));

		this.listHolder.add(1, null);
		assertTrue(this.listContains(null));
		this.listHolder.remove(1);
		assertFalse(this.listContains(null));
	}

	public void testRemoveLength() {
		assertTrue(this.listContainsAll(this.buildRemoveList()));
		this.listHolder.remove(0, 2);
		assertFalse(this.listContainsAny(this.buildRemoveList()));
	}

	public void testSetValues() {
		List newList = new ArrayList();
		newList.add("joo");
		newList.add("jar");
		newList.add("jaz");

		assertTrue(this.listContains("bar"));
		assertFalse(this.listContains("jar"));
		((SimpleListValueModel) this.listHolder).setValue(newList);
		assertFalse(this.listContains("bar"));
		assertTrue(this.listContains("jar"));

		this.listHolder.add(1, null);
		assertTrue(this.listContains(null));
		this.listHolder.remove(1);
		assertFalse(this.listContains(null));

		((SimpleListValueModel) this.listHolder).setValue(null);
		assertFalse(this.listContains("jar"));
	}

	public void testListChange1() {
		this.listHolder.addListChangeListener(this.buildListener());
		this.verifyListChange();
	}

	public void testListChange2() {
		this.listHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());
		this.verifyListChange();
	}

	private void verifyListChange() {
		this.event = null;
		this.eventType = null;
		this.listHolder.add(1, "joo");
		this.verifyEvent(ADD, 1, "joo");

		this.event = null;
		this.eventType = null;
		this.listHolder.add(1, null);
		this.verifyEvent(ADD, 1, null);

		this.event = null;
		this.eventType = null;
		this.listHolder.remove(1);
		this.verifyEvent(REMOVE, 1, null);

		this.event = null;
		this.eventType = null;
		this.listHolder.remove(1);
		this.verifyEvent(REMOVE, 1, "joo");

		this.event = null;
		this.eventType = null;
		((SimpleListValueModel) this.listHolder).setValue(this.buildList());
		this.verifyEvent(CHANGE);

		this.event = null;
		this.eventType = null;
		this.listHolder.addAll(0, this.buildList());
		this.verifyEvent(ADD);
		assertEquals(this.buildList(), CollectionTools.list(this.event.items()));

		this.event = null;
		this.eventType = null;
		this.listHolder.replace(0, "joo");
		this.verifyEvent(REPLACE);
		assertFalse(CollectionTools.contains(this.event.items(), "foo"));
		assertTrue(CollectionTools.contains(this.event.items(), "joo"));
	}

	private ListChangeListener buildListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				SimpleListValueModelTests.this.eventType = ADD;
				SimpleListValueModelTests.this.event = e;
			}
			public void itemsRemoved(ListChangeEvent e) {
				SimpleListValueModelTests.this.eventType = REMOVE;
				SimpleListValueModelTests.this.event = e;
			}
			public void itemsReplaced(ListChangeEvent e) {
				SimpleListValueModelTests.this.eventType = REPLACE;
				SimpleListValueModelTests.this.event = e;
			}
			public void itemsMoved(ListChangeEvent e) {
				SimpleListValueModelTests.this.eventType = MOVE;
				SimpleListValueModelTests.this.event = e;
			}
			public void listCleared(ListChangeEvent e) {
				SimpleListValueModelTests.this.eventType = CLEAR;
				SimpleListValueModelTests.this.event = e;
			}
			public void listChanged(ListChangeEvent e) {
				SimpleListValueModelTests.this.eventType = CHANGE;
				SimpleListValueModelTests.this.event = e;
			}
		};
	}

	private void verifyEvent(String e) {
		assertEquals(e, this.eventType);
		assertEquals(this.listHolder, this.event.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event.listName());
	}

	private void verifyEvent(String e, int index, Object item) {
		this.verifyEvent(e);
		assertEquals(index, this.event.index());
		assertEquals(item, this.event.items().next());
	}

}
