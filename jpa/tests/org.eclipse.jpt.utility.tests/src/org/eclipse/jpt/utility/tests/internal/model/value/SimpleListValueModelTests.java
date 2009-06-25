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
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class SimpleListValueModelTests extends TestCase {
	private SimpleListValueModel<String> listHolder;
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
		this.listHolder = new SimpleListValueModel<String>(this.buildList());
	}

	private List<String> buildList() {
		List<String> result = new ArrayList<String>();
		result.add("foo");
		result.add("bar");
		result.add("baz");
		return result;
	}

	private List<String> buildAddList() {
		List<String> result = new ArrayList<String>();
		result.add("joo");
		result.add("jar");
		result.add("jaz");
		return result;
	}

//	private List<String> buildRemoveList() {
//		List<String> result = new ArrayList<String>();
//		result.add("foo");
//		result.add("bar");
//		return result;
//	}
//
	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		assertEquals(this.buildList(), CollectionTools.list(this.listHolder.iterator()));
	}

	public void testListIterator() {
		assertEquals(this.buildList(), CollectionTools.list(this.listHolder.listIterator()));
	}

	public void testListIteratorInt() {
		assertEquals(CollectionTools.list(this.buildList().listIterator(1)), CollectionTools.list(this.listHolder.listIterator(1)));
	}

	public void testSize() {
		assertEquals(this.buildList().size(), this.listHolder.size());
	}

	private boolean listContains(Object item) {
		return CollectionTools.contains(this.listHolder.listIterator(), item);
	}

	private boolean listContainsAll(Collection<String> items) {
		return CollectionTools.containsAll(this.listHolder.listIterator(), items);
	}

	private boolean listContainsAny(Collection<String> items) {
		Set<String> set = CollectionTools.set(this.listHolder.iterator());
		for (Iterator<String> stream = items.iterator(); stream.hasNext(); ) {
			if (set.contains(stream.next())) {
				return true;
			}
		}
		return false;
	}

	public void testAddObject() {
		assertFalse(this.listContains("joo"));
		this.listHolder.add("joo");
		assertTrue(this.listContains("joo"));

		assertFalse(this.listContains(null));
		this.listHolder.add(null);
		assertTrue(this.listContains(null));
	}

	public void testAddIntObject() {
		assertFalse(this.listContains("joo"));
		this.listHolder.add(2, "joo");
		assertTrue(this.listContains("joo"));

		assertFalse(this.listContains(null));
		this.listHolder.add(0, null);
		assertTrue(this.listContains(null));
	}

	public void testAddAllCollection() {
		assertFalse(this.listContainsAny(this.buildAddList()));
		this.listHolder.addAll(this.buildAddList());
		assertTrue(this.listContainsAll(this.buildAddList()));
	}

	public void testAddAllIntCollection() {
		assertFalse(this.listContainsAny(this.buildAddList()));
		this.listHolder.addAll(2, this.buildAddList());
		assertTrue(this.listContainsAll(this.buildAddList()));
	}

	public void testClear() {
		assertFalse(this.listHolder.isEmpty());
		this.listHolder.clear();
		assertTrue(this.listHolder.isEmpty());
	}

	public void testContainsObject() {
		assertTrue(this.listHolder.contains("foo"));
		assertFalse(this.listHolder.contains("joo"));
	}

	public void testContainsAllCollection() {
		Collection<String> c = new ArrayList<String>();
		c.add("foo");
		c.add("bar");
		assertTrue(this.listHolder.containsAll(c));

		c.add("joo");
		assertFalse(this.listHolder.containsAll(c));
	}

	public void testEquals() {
		assertEquals(new SimpleListValueModel<String>(this.buildList()), this.listHolder);
		assertFalse(this.listHolder.equals(new SimpleListValueModel<String>(this.buildAddList())));
		assertFalse(this.listHolder.equals(this.buildList()));
		assertFalse(this.listHolder.equals(new SimpleListValueModel<String>()));
	}

	public void testGetInt() {
		assertEquals("foo", this.listHolder.get(0));
		assertEquals("bar", this.listHolder.get(1));
		assertEquals("baz", this.listHolder.get(2));
	}

	public void testHashCode() {
		assertEquals(new SimpleListValueModel<String>(this.buildList()).hashCode(), this.listHolder.hashCode());
	}

	public void testIndexOfObject() {
		assertEquals(0, this.listHolder.indexOf("foo"));
		assertEquals(1, this.listHolder.indexOf("bar"));
		assertEquals(2, this.listHolder.indexOf("baz"));
		assertEquals(-1, this.listHolder.indexOf("joo"));
	}

	public void testLastIndexOfObject() {
		assertEquals(0, this.listHolder.lastIndexOf("foo"));
		assertEquals(1, this.listHolder.lastIndexOf("bar"));
		assertEquals(2, this.listHolder.lastIndexOf("baz"));
		assertEquals(-1, this.listHolder.lastIndexOf("joo"));

		this.listHolder.add("foo");
		assertEquals(3, this.listHolder.lastIndexOf("foo"));
	}

	public void testIsEmpty() {
		assertFalse(this.listHolder.isEmpty());
		this.listHolder.clear();
		assertTrue(this.listHolder.isEmpty());
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

	public void testSetValues() {
		List<String> newList = new ArrayList<String>();
		newList.add("joo");
		newList.add("jar");
		newList.add("jaz");

		assertTrue(this.listContains("bar"));
		assertFalse(this.listContains("jar"));
		this.listHolder.setList(newList);
		assertFalse(this.listContains("bar"));
		assertTrue(this.listContains("jar"));

		this.listHolder.add(1, null);
		assertTrue(this.listContains(null));
		this.listHolder.remove(1);
		assertFalse(this.listContains(null));

		this.listHolder.setList(new ArrayList<String>());
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
		this.listHolder.setList(this.buildList());
		this.verifyEvent(CHANGE);

		this.event = null;
		this.eventType = null;
		this.listHolder.addAll(0, this.buildList());
		this.verifyEvent(ADD);
		assertEquals(this.buildList(), CollectionTools.list(this.event.getItems()));

		this.event = null;
		this.eventType = null;
		this.listHolder.set(0, "joo");
		this.verifyEvent(REPLACE);
		assertFalse(CollectionTools.contains(this.event.getItems(), "foo"));
		assertTrue(CollectionTools.contains(this.event.getItems(), "joo"));
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
		assertEquals(ListValueModel.LIST_VALUES, this.event.getListName());
	}

	private void verifyEvent(String e, int index, Object item) {
		this.verifyEvent(e);
		assertEquals(index, this.event.getIndex());
		assertEquals(item, this.event.getItems().iterator().next());
	}

}
