/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.ExtendedListValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class ExtendedListValueModelWrapperTests extends TestCase {
	private SimpleListValueModel<String> listHolder;
	private ListValueModel<String> extendedListHolder;
	ListEvent event;
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
		assertEquals(this.buildExtendedList(), ListTools.arrayList(this.extendedListHolder.iterator()));
	}

	public void testSize() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());
		assertEquals(this.buildExtendedList().size(), IteratorTools.size(this.extendedListHolder.iterator()));
		assertEquals(this.buildExtendedList().size(), this.extendedListHolder.size());
	}

	private boolean extendedListContains(Object item) {
		return IteratorTools.contains(this.extendedListHolder.iterator(), item);
	}

	private boolean extendedListContainsAll(Collection<String> items) {
		return IteratorTools.containsAll(this.extendedListHolder.iterator(), items);
	}

	private boolean extendedListContainsAny(Collection<String> items) {
		List<String> extendedList = ListTools.arrayList(this.extendedListHolder.iterator());
		for (Iterator<String> stream = items.iterator(); stream.hasNext(); ) {
			if (extendedList.contains(stream.next())) {
				return true;
			}
		}
		return false;
	}

	private boolean listContains(Object item) {
		return IteratorTools.contains(this.listHolder.iterator(), item);
	}

	private boolean listContainsAll(Collection<String> items) {
		return IteratorTools.containsAll(this.listHolder.iterator(), items);
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
		this.extendedListHolder.addChangeListener(this.buildListener());
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
		assertEquals(this.buildList(), ListTools.arrayList(((ListAddEvent) this.event).getItems()));

		this.event = null;
		this.eventType = null;
		this.listHolder.set(0, "AA");
		this.verifyEvent(REPLACE);
		assertFalse(IterableTools.contains(((ListReplaceEvent) this.event).getNewItems(), "A"));
		assertTrue(IterableTools.contains(((ListReplaceEvent) this.event).getNewItems(), "AA"));
	}

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void itemsAdded(ListAddEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = ADD;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
			@Override
			public void itemsRemoved(ListRemoveEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = REMOVE;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
			@Override
			public void itemsReplaced(ListReplaceEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = REPLACE;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
			@Override
			public void itemsMoved(ListMoveEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = MOVE;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
			@Override
			public void listCleared(ListClearEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = CLEAR;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
			@Override
			public void listChanged(ListChangeEvent e) {
				ExtendedListValueModelWrapperTests.this.eventType = CHANGE;
				ExtendedListValueModelWrapperTests.this.event = e;
			}
		};
	}

	private void verifyEvent(String type) {
		assertEquals(type, this.eventType);
		assertEquals(this.extendedListHolder, this.event.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event.getListName());
	}

	private void verifyEvent(String type, int index, Object item) {
		this.verifyEvent(type);
		if (type == ADD) {
			assertEquals(index, ((ListAddEvent) this.event).getIndex());
			assertEquals(item, ((ListAddEvent) this.event).getItems().iterator().next());
		} else if (type == REMOVE) {
			assertEquals(index, ((ListRemoveEvent) this.event).getIndex());
			assertEquals(item, ((ListRemoveEvent) this.event).getItems().iterator().next());
		}
	}

	public void testHasListeners() {
		/*
		 * adding listeners to the extended list will cause listeners
		 * to be added to the wrapped list;
		 * likewise, removing listeners from the extended list will
		 * cause listeners to be removed from the wrapped list
		 */
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ChangeListener listener = this.buildListener();

		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		assertTrue(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.extendedListHolder.removeListChangeListener(ListValueModel.LIST_VALUES, listener);
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.extendedListHolder.addChangeListener(listener);
		assertTrue(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.extendedListHolder.removeChangeListener(listener);
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}

}
