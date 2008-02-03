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
import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.value.ExtendedListValueModelWrapper;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class ExtendedListValueModelWrapperTests extends TestCase {
	private SimpleListValueModel<String> listHolder;
	private ListValueModel<String> extendedListHolder;
	ListChangeEvent event;
	String eventType;

	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String REPLACE = "replace";
	private static final String MOVE = "move";
	private static final String CLEAR = "clear";
	private static final String CHANGE = "change";

	public ExtendedListValueModelWrapperTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listHolder = new SimpleListValueModel<String>(this.buildList());
		this.extendedListHolder = this.buildExtendedListHolder(this.listHolder);
	}

	private List<String> buildList() {
		List<String> result = new ArrayList<String>();
		result.add("A");
		result.add("B");
		result.add("C");
		result.add("D");
		return result;
	}

	private List<String> buildExtendedList() {
		List<String> extendedList = new ArrayList<String>();
		extendedList.addAll(this.buildPrefix());
		extendedList.addAll(this.buildList());
		extendedList.addAll(this.buildSuffix());
		return extendedList;
	}

	private List<String> buildPrefix() {
		List<String> prefix = new ArrayList<String>();
		prefix.add("x");
		prefix.add("y");
		prefix.add("z");
		return prefix;
	}

	private List<String> buildSuffix() {
		List<String> suffix = new ArrayList<String>();
		suffix.add("i");
		suffix.add("j");
		return suffix;
	}

	private ListValueModel<String> buildExtendedListHolder(ListValueModel<String> lvm) {
		return new ExtendedListValueModelWrapper<String>(this.buildPrefix(), lvm, this.buildSuffix());
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());
		assertEquals(this.buildExtendedList(), CollectionTools.list(this.extendedListHolder.iterator()));
	}

	public void testSize() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());
		assertEquals(this.buildExtendedList().size(), CollectionTools.size(this.extendedListHolder.iterator()));
		assertEquals(this.buildExtendedList().size(), this.extendedListHolder.size());
	}

	private boolean extendedListContains(Object item) {
		return CollectionTools.contains(this.extendedListHolder.iterator(), item);
	}

	private boolean extendedListContainsAll(Collection<String> items) {
		return CollectionTools.containsAll(this.extendedListHolder.iterator(), items);
	}

	private boolean extendedListContainsAny(Collection<String> items) {
		List<String> extendedList = CollectionTools.list(this.extendedListHolder.iterator());
		for (Iterator<String> stream = items.iterator(); stream.hasNext(); ) {
			if (extendedList.contains(stream.next())) {
				return true;
			}
		}
		return false;
	}

	private boolean listContains(Object item) {
		return CollectionTools.contains(this.listHolder.iterator(), item);
	}

	private boolean listContainsAll(Collection<String> items) {
		return CollectionTools.containsAll(this.listHolder.iterator(), items);
	}

//	private boolean listContainsAny(Collection<String> items) {
//		List<String> extendedList = CollectionTools.list(this.listHolder.iterator());
//		for (Iterator<String> stream = items.iterator(); stream.hasNext(); ) {
//			if (extendedList.contains(stream.next())) {
//				return true;
//			}
//		}
//		return false;
//	}
//
	public void testAdd1() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertFalse(this.extendedListContains("E"));
		this.listHolder.add(4, "E");
		assertTrue(this.extendedListContains("E"));
		assertTrue(this.listContains("E"));
	}

	public void testAdd2() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertFalse(this.extendedListContains(null));
		this.listHolder.add(4, null);
		assertTrue(this.extendedListContains(null));
		assertTrue(this.listContains(null));
	}

	private List<String> buildAddList() {
		List<String> addList = new ArrayList<String>();
		addList.add("E");
		addList.add("F");
		return addList;
	}

	public void testAddAll1() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertFalse(this.extendedListContainsAny(this.buildAddList()));
		this.listHolder.addAll(4, this.buildAddList());
		assertTrue(this.extendedListContainsAll(this.buildAddList()));
		assertTrue(this.listContainsAll(this.buildAddList()));
	}

	public void testRemove1() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertTrue(this.extendedListContains("B"));
		this.listHolder.remove(this.buildList().indexOf("B"));
		assertFalse(this.extendedListContains("B"));
		assertFalse(this.listContains("B"));
	}

	public void testListChangeGeneric() {
		this.extendedListHolder.addListChangeListener(this.buildListener());
		this.verifyListChange();
	}

	public void testListChangeNamed() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());
		this.verifyListChange();
	}

	private void verifyListChange() {
		this.event = null;
		this.eventType = null;
		this.listHolder.add(4, "E");
		this.verifyEvent(ADD, 7, "E");

		this.event = null;
		this.eventType = null;
		this.listHolder.add(5, null);
		this.verifyEvent(ADD, 8, null);

		this.event = null;
		this.eventType = null;
		this.listHolder.remove(5);
		this.verifyEvent(REMOVE, 8, null);

		this.event = null;
		this.eventType = null;
		this.listHolder.remove(4);
		this.verifyEvent(REMOVE, 7, "E");

		this.event = null;
		this.eventType = null;
		this.listHolder.addAll(0, this.buildList());
		this.verifyEvent(ADD);
		assertEquals(this.buildList(), CollectionTools.list(this.event.items()));

		this.event = null;
		this.eventType = null;
		this.listHolder.set(0, "AA");
		this.verifyEvent(REPLACE);
		assertFalse(CollectionTools.contains(this.event.items(), "A"));
		assertTrue(CollectionTools.contains(this.event.items(), "AA"));
	}

	private ListChangeListener buildListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = ADD;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
			public void itemsRemoved(ListChangeEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = REMOVE;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
			public void itemsReplaced(ListChangeEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = REPLACE;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
			public void itemsMoved(ListChangeEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = MOVE;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
			public void listCleared(ListChangeEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = CLEAR;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
			public void listChanged(ListChangeEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = CHANGE;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
		};
	}

	private void verifyEvent(String type) {
		assertEquals(type, this.eventType);
		assertEquals(this.extendedListHolder, this.event.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event.listName());
	}

	private void verifyEvent(String type, int index, Object item) {
		this.verifyEvent(type);
		assertEquals(index, this.event.index());
		assertEquals(item, this.event.items().next());
	}

	public void testHasListeners() {
		/*
		 * adding listeners to the extended list will cause listeners
		 * to be added to the wrapped list;
		 * likewise, removing listeners from the extended list will
		 * cause listeners to be removed from the wrapped list
		 */
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ListChangeListener listener = this.buildListener();

		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		assertTrue(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.extendedListHolder.removeListChangeListener(ListValueModel.LIST_VALUES, listener);
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.extendedListHolder.addListChangeListener(listener);
		assertTrue(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.extendedListHolder.removeListChangeListener(listener);
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

}
