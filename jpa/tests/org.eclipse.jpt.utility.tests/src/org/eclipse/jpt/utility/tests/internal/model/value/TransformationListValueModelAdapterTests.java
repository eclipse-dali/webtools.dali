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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationListValueModelAdapter;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class TransformationListValueModelAdapterTests extends TestCase {
	private SimpleListValueModel listHolder;
	private ListValueModel transformedListHolder;
	ListChangeEvent event;
	String eventType;

	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String REPLACE = "replace";
	private static final String MOVE = "move";
	private static final String CLEAR = "clear";
	private static final String CHANGE = "change";


	public TransformationListValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listHolder = new SimpleListValueModel(this.buildList());
		this.transformedListHolder = this.buildTransformedListHolder(this.listHolder);
	}

	private List buildList() {
		List result = new ArrayList();
		result.add("foo");
		result.add("bar");
		result.add("baz");
		return result;
	}

	private List buildTransformedList() {
		return this.transform(this.buildList());
	}

	private List transform(List list) {
		List result = new ArrayList(list.size());
		for (Iterator stream = list.iterator(); stream.hasNext(); ) {
			String string = (String) stream.next();
			if (string == null) {
				result.add(null);
			} else {
				result.add(string.toUpperCase());
			}
		}
		return result;
	}

	private List buildAddList() {
		List result = new ArrayList();
		result.add("joo");
		result.add("jar");
		result.add("jaz");
		return result;
	}

	private List buildTransformedAddList() {
		return this.transform(this.buildAddList());
	}

	private List buildRemoveList() {
		List result = new ArrayList();
		result.add("foo");
		result.add("bar");
		return result;
	}

	private List buildTransformedRemoveList() {
		return this.transform(this.buildRemoveList());
	}

	ListValueModel buildTransformedListHolder(ListValueModel lvm) {
		return new TransformationListValueModelAdapter(lvm) {
			protected Object transformItem(Object item) {
				return (item == null) ? null : ((String) item).toUpperCase();
			}
		};
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		this.transformedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());
		assertEquals(this.buildTransformedList(), CollectionTools.list(this.transformedListHolder.iterator()));
	}

	public void testStaleValues() {
		ListChangeListener listener = this.buildListener();
		this.transformedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		assertEquals(this.buildTransformedList(), CollectionTools.list(this.transformedListHolder.iterator()));

		this.transformedListHolder.removeListChangeListener(ListValueModel.LIST_VALUES, listener);
		assertEquals(Collections.EMPTY_LIST, CollectionTools.list(this.transformedListHolder.iterator()));
	}

	public void testSize() {
		this.transformedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());
		assertEquals(this.buildTransformedList().size(), CollectionTools.size(this.transformedListHolder.iterator()));
	}

	private boolean transformedListContains(Object item) {
		return CollectionTools.contains(this.transformedListHolder.iterator(), item);
	}

	private boolean transformedListContainsAll(Collection items) {
		return CollectionTools.containsAll(this.transformedListHolder.iterator(), items);
	}

	private boolean transformedListContainsAny(Collection items) {
		List transformedList = CollectionTools.list(this.transformedListHolder.iterator());
		for (Iterator stream = items.iterator(); stream.hasNext(); ) {
			if (transformedList.contains(stream.next())) {
				return true;
			}
		}
		return false;
	}

	public void testAdd() {
		this.transformedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertFalse(this.transformedListContains("JOO"));
		this.listHolder.add(2, "joo");
		assertTrue(this.transformedListContains("JOO"));

		assertFalse(this.transformedListContains(null));
		this.listHolder.add(0, null);
		assertTrue(this.transformedListContains(null));
	}

	public void testAddAll() {
		this.transformedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertFalse(this.transformedListContainsAny(this.buildTransformedAddList()));
		this.listHolder.addAll(2, this.buildAddList());
		assertTrue(this.transformedListContainsAll(this.buildTransformedAddList()));
	}

	public void testRemove() {
		this.transformedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());

		assertTrue(this.transformedListContains("BAR"));
		this.listHolder.remove(this.buildList().indexOf("bar"));
		assertFalse(this.transformedListContains("BAR"));

		this.listHolder.add(1, null);
		assertTrue(this.transformedListContains(null));
		this.listHolder.remove(1);
		assertFalse(this.transformedListContains(null));
	}

	public void testListChangeGeneric() {
		this.transformedListHolder.addListChangeListener(this.buildListener());
		this.verifyListChange();
	}

	public void testListChangeNamed() {
		this.transformedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, this.buildListener());
		this.verifyListChange();
	}

	private void verifyListChange() {
		this.event = null;
		this.eventType = null;
		this.listHolder.add(1, "joo");
		this.verifyEvent(ADD, 1, "JOO");

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
		this.verifyEvent(REMOVE, 1, "JOO");

		this.event = null;
		this.eventType = null;
		this.listHolder.addAll(0, this.buildList());
		this.verifyEvent(ADD);
		assertEquals(this.buildTransformedList(), CollectionTools.list(this.event.items()));

		this.event = null;
		this.eventType = null;
		this.listHolder.set(0, "joo");
		this.verifyEvent(REPLACE);
		assertFalse(CollectionTools.contains(this.event.items(), "FOO"));
		assertTrue(CollectionTools.contains(this.event.items(), "JOO"));
	}

	private ListChangeListener buildListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListChangeEvent e) {
				TransformationListValueModelAdapterTests.this.eventType = ADD;
				TransformationListValueModelAdapterTests.this.event = e;
			}
			public void itemsRemoved(ListChangeEvent e) {
				TransformationListValueModelAdapterTests.this.eventType = REMOVE;
				TransformationListValueModelAdapterTests.this.event = e;
			}
			public void itemsReplaced(ListChangeEvent e) {
				TransformationListValueModelAdapterTests.this.eventType = REPLACE;
				TransformationListValueModelAdapterTests.this.event = e;
			}
			public void itemsMoved(ListChangeEvent e) {
				TransformationListValueModelAdapterTests.this.eventType = MOVE;
				TransformationListValueModelAdapterTests.this.event = e;
			}
			public void listCleared(ListChangeEvent e) {
				TransformationListValueModelAdapterTests.this.eventType = CLEAR;
				TransformationListValueModelAdapterTests.this.event = e;
			}
			public void listChanged(ListChangeEvent e) {
				TransformationListValueModelAdapterTests.this.eventType = CHANGE;
				TransformationListValueModelAdapterTests.this.event = e;
			}
		};
	}

	private void verifyEvent(String type) {
		assertEquals(type, this.eventType);
		assertEquals(this.transformedListHolder, this.event.getSource());
		assertEquals(ListValueModel.LIST_VALUES, this.event.listName());
	}

	private void verifyEvent(String type, int index, Object item) {
		this.verifyEvent(type);
		assertEquals(index, this.event.index());
		assertEquals(item, this.event.items().next());
	}

	public void testHasListeners() {
		/*
		 * adding listeners to the transformed list will cause listeners
		 * to be added to the wrapped list;
		 * likewise, removing listeners from the transformed list will
		 * cause listeners to be removed from the wrapped list
		 */
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ListChangeListener listener = this.buildListener();

		this.transformedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		assertTrue(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.transformedListHolder.removeListChangeListener(ListValueModel.LIST_VALUES, listener);
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.transformedListHolder.addListChangeListener(listener);
		assertTrue(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.transformedListHolder.removeListChangeListener(listener);
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}


	/**
	 * execute the same set of tests again, but by passing a Transformer to the adapter
	 * (as opposed to overriding #transformItem(Object))
	 */
	public static class TransformerTests extends TransformationListValueModelAdapterTests {
		public TransformerTests(String name) {
			super(name);
		}
		ListValueModel buildTransformedListHolder(ListValueModel lvm) {
			return new TransformationListValueModelAdapter(lvm, this.buildTransformer());
		}
		private Transformer buildTransformer() {
			return new Transformer() {
				public Object transform(Object o) {
					return (o == null) ? null : ((String) o).toUpperCase();
				}
			};
		}
	}
	
}
