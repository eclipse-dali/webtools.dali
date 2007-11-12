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
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.value.ExtendedListValueModelWrapper;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class ExtendedListValueModelWrapperTests extends TestCase {
	private ListValueModel listHolder;
	private ListValueModel extendedListHolder;
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
		this.listHolder = new SimpleListValueModel(this.buildList());
		this.extendedListHolder = this.buildExtendedListHolder(this.listHolder);
	}

	private List buildList() {
		List result = new ArrayList();
		result.add("A");
		result.add("B");
		result.add("C");
		result.add("D");
		return result;
	}

	private List buildExtendedList() {
		List extendedList = new ArrayList();
		extendedList.addAll(this.buildPrefix());
		extendedList.addAll(this.buildList());
		extendedList.addAll(this.buildSuffix());
		return extendedList;
	}

	private List buildPrefix() {
		List prefix = new ArrayList();
		prefix.add("x");
		prefix.add("y");
		prefix.add("z");
		return prefix;
	}

	private List buildSuffix() {
		List suffix = new ArrayList();
		suffix.add("i");
		suffix.add("j");
		return suffix;
	}

	private ListValueModel buildExtendedListHolder(ListValueModel lvm) {
		return new ExtendedListValueModelWrapper(this.buildPrefix(), lvm, this.buildSuffix());
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testValues() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());
		assertEquals(this.buildExtendedList(), CollectionTools.list((Iterator) this.extendedListHolder.values()));
	}

	public void testSize() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());
		assertEquals(this.buildExtendedList().size(), CollectionTools.size((Iterator) this.extendedListHolder.values()));
		assertEquals(this.buildExtendedList().size(), this.extendedListHolder.size());
	}

	private boolean extendedListContains(Object item) {
		return CollectionTools.contains((Iterator) this.extendedListHolder.values(), item);
	}

	private boolean extendedListContainsAll(Collection items) {
		return CollectionTools.containsAll((Iterator) this.extendedListHolder.values(), items);
	}

	private boolean extendedListContainsAny(Collection items) {
		List extendedList = CollectionTools.list((ListIterator) this.extendedListHolder.values());
		for (Iterator stream = items.iterator(); stream.hasNext(); ) {
			if (extendedList.contains(stream.next())) {
				return true;
			}
		}
		return false;
	}

	private boolean listContains(Object item) {
		return CollectionTools.contains((Iterator) this.listHolder.values(), item);
	}

	private boolean listContainsAll(Collection items) {
		return CollectionTools.containsAll((Iterator) this.listHolder.values(), items);
	}

	private boolean listContainsAny(Collection items) {
		List extendedList = CollectionTools.list((ListIterator) this.listHolder.values());
		for (Iterator stream = items.iterator(); stream.hasNext(); ) {
			if (extendedList.contains(stream.next())) {
				return true;
			}
		}
		return false;
	}

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

	public void testAdd3() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertFalse(this.extendedListContains("E"));
		this.extendedListHolder.add(7, "E");
		assertTrue(this.extendedListContains("E"));
		assertTrue(this.listContains("E"));
	}

	public void testAdd4() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		boolean exCaught = false;
		try {
			this.extendedListHolder.add(0, "Z");
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf("prefix") != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
		assertFalse(this.extendedListContains("Z"));
		assertFalse(this.listContains("Z"));
	}

	public void testAdd5() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		boolean exCaught = false;
		try {
			this.extendedListHolder.add(8, "Z");
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf("suffix") != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
		assertFalse(this.extendedListContains("Z"));
		assertFalse(this.listContains("Z"));
	}

	private List buildAddList() {
		List addList = new ArrayList();
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

	public void testAddAll2() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertFalse(this.extendedListContainsAny(this.buildAddList()));
		this.extendedListHolder.addAll(4, this.buildAddList());
		assertTrue(this.extendedListContainsAll(this.buildAddList()));
		assertTrue(this.listContainsAll(this.buildAddList()));
	}

	public void testAddAll3() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		boolean exCaught = false;
		try {
			this.extendedListHolder.addAll(0, this.buildAddList());
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf("prefix") != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
		assertFalse(this.extendedListContainsAny(this.buildAddList()));
		assertFalse(this.listContainsAny(this.buildAddList()));
	}

	public void testAddAll4() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		boolean exCaught = false;
		try {
			this.extendedListHolder.add(8, this.buildAddList());
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf("suffix") != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
		assertFalse(this.extendedListContainsAny(this.buildAddList()));
		assertFalse(this.listContainsAny(this.buildAddList()));
	}

	public void testRemove1() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertTrue(this.extendedListContains("B"));
		this.listHolder.remove(this.buildList().indexOf("B"));
		assertFalse(this.extendedListContains("B"));
		assertFalse(this.listContains("B"));
	}

	public void testRemove2() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertTrue(this.extendedListContains("B"));
		this.extendedListHolder.remove(this.buildPrefix().size() + this.buildList().indexOf("B"));
		assertFalse(this.extendedListContains("B"));
		assertFalse(this.listContains("B"));
	}

	public void testRemove3() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		this.listHolder.add(0, null);
		assertTrue(this.extendedListContains(null));
		this.extendedListHolder.remove(this.buildPrefix().size());
		assertFalse(this.extendedListContains(null));
		assertFalse(this.listContains(null));
	}

	public void testRemove4() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertTrue(this.extendedListContains("x"));
		boolean exCaught = false;
		try {
			this.extendedListHolder.remove(CollectionTools.indexOf((ListIterator) this.extendedListHolder.values(), "x"));
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf("prefix") != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
		assertTrue(this.extendedListContains("x"));
	}

	public void testRemove5() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertTrue(this.extendedListContains("i"));
		boolean exCaught = false;
		try {
			this.extendedListHolder.remove(CollectionTools.indexOf((ListIterator) this.extendedListHolder.values(), "i"));
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf("suffix") != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
		assertTrue(this.extendedListContains("i"));
	}

	public void testRemoveLength1() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertTrue(this.extendedListContains("B"));
		assertTrue(this.extendedListContains("C"));
		this.listHolder.remove(this.buildList().indexOf("B"), 2);
		assertFalse(this.extendedListContains("B"));
		assertFalse(this.extendedListContains("C"));
		assertFalse(this.listContains("B"));
		assertFalse(this.listContains("C"));
	}

	public void testRemoveLength2() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertTrue(this.extendedListContains("B"));
		assertTrue(this.extendedListContains("C"));
		this.extendedListHolder.remove(this.buildPrefix().size() + this.buildList().indexOf("B"), 2);
		assertFalse(this.extendedListContains("B"));
		assertFalse(this.extendedListContains("C"));
		assertFalse(this.listContains("B"));
		assertFalse(this.listContains("C"));
	}

	public void testRemoveLength3() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertTrue(this.extendedListContains("x"));
		assertTrue(this.extendedListContains("y"));
		boolean exCaught = false;
		try {
			this.extendedListHolder.remove(CollectionTools.indexOf((ListIterator) this.extendedListHolder.values(), "x"), 2);
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf("prefix") != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
		assertTrue(this.extendedListContains("x"));
		assertTrue(this.extendedListContains("y"));
	}

	public void testRemoveLength4() {
		this.extendedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertTrue(this.extendedListContains("D"));
		assertTrue(this.extendedListContains("i"));
		boolean exCaught = false;
		try {
			this.extendedListHolder.remove(CollectionTools.indexOf((ListIterator) this.extendedListHolder.values(), "D"), 2);
		} catch (IllegalArgumentException ex) {
			if (ex.getMessage().indexOf("suffix") != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
		assertTrue(this.extendedListContains("D"));
		assertTrue(this.extendedListContains("i"));
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
		this.listHolder.replace(0, "AA");
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
