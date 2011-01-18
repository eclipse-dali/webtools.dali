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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationListValueModel;
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class TransformationListValueModelTests extends TestCase {
	private SimpleListValueModel<String> listHolder;
	private ListValueModel<String> transformedListHolder;
	ListEvent event;
	String eventType;

	private static final String ADD = "add";
	private static final String REMOVE = "remove";
	private static final String REPLACE = "replace";
	private static final String MOVE = "move";
	private static final String CLEAR = "clear";
	private static final String CHANGE = "change";


	public TransformationListValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.listHolder = new SimpleListValueModel<String>(this.buildList());
		this.transformedListHolder = this.buildTransformedListHolder(this.listHolder);
	}

	private List<String> buildList() {
		List<String> result = new ArrayList<String>();
		result.add("foo");
		result.add("bar");
		result.add("baz");
		return result;
	}

	private List<String> buildTransformedList() {
		return this.transform(this.buildList());
	}

	private List<String> transform(List<String> list) {
		List<String> result = new ArrayList<String>(list.size());
		for (String string : list) {
			if (string == null) {
				result.add(null);
			} else {
				result.add(string.toUpperCase());
			}
		}
		return result;
	}

	private List<String> buildAddList() {
		List<String> result = new ArrayList<String>();
		result.add("joo");
		result.add("jar");
		result.add("jaz");
		return result;
	}

	private List<String> buildTransformedAddList() {
		return this.transform(this.buildAddList());
	}

//	private List<String> buildRemoveList() {
//		List<String> result = new ArrayList<String>();
//		result.add("foo");
//		result.add("bar");
//		return result;
//	}
//
//	private List<String> buildTransformedRemoveList() {
//		return this.transform(this.buildRemoveList());
//	}
//
	ListValueModel<String> buildTransformedListHolder(ListValueModel<String> lvm) {
		return new TransformationListValueModel<String, String>(lvm) {
			@Override
			protected String transformItem_(String s) {
				return s.toUpperCase();
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

	private boolean transformedListContainsAll(Collection<String> items) {
		return CollectionTools.containsAll(this.transformedListHolder.iterator(), items);
	}

	private boolean transformedListContainsAny(Collection<String> items) {
		List<String> transformedList = CollectionTools.list(this.transformedListHolder.iterator());
		for (Iterator<String> stream = items.iterator(); stream.hasNext(); ) {
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
		this.transformedListHolder.addChangeListener(this.buildListener());
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
		assertEquals(this.buildTransformedList(), CollectionTools.list(((ListAddEvent) this.event).getItems()));

		this.event = null;
		this.eventType = null;
		this.listHolder.set(0, "joo");
		this.verifyEvent(REPLACE);
		assertFalse(CollectionTools.contains(((ListReplaceEvent) this.event).getNewItems(), "FOO"));
		assertTrue(CollectionTools.contains(((ListReplaceEvent) this.event).getNewItems(), "JOO"));
	}

	private ChangeListener buildListener() {
		return new ChangeAdapter() {
			@Override
			public void itemsAdded(ListAddEvent e) {
				TransformationListValueModelTests.this.eventType = ADD;
				TransformationListValueModelTests.this.event = e;
			}
			@Override
			public void itemsRemoved(ListRemoveEvent e) {
				TransformationListValueModelTests.this.eventType = REMOVE;
				TransformationListValueModelTests.this.event = e;
			}
			@Override
			public void itemsReplaced(ListReplaceEvent e) {
				TransformationListValueModelTests.this.eventType = REPLACE;
				TransformationListValueModelTests.this.event = e;
			}
			@Override
			public void itemsMoved(ListMoveEvent e) {
				TransformationListValueModelTests.this.eventType = MOVE;
				TransformationListValueModelTests.this.event = e;
			}
			@Override
			public void listCleared(ListClearEvent e) {
				TransformationListValueModelTests.this.eventType = CLEAR;
				TransformationListValueModelTests.this.event = e;
			}
			@Override
			public void listChanged(ListChangeEvent e) {
				TransformationListValueModelTests.this.eventType = CHANGE;
				TransformationListValueModelTests.this.event = e;
			}
		};
	}

	private void verifyEvent(String type) {
		assertEquals(type, this.eventType);
		assertEquals(this.transformedListHolder, this.event.getSource());
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
		 * adding listeners to the transformed list will cause listeners
		 * to be added to the wrapped list;
		 * likewise, removing listeners from the transformed list will
		 * cause listeners to be removed from the wrapped list
		 */
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		ChangeListener listener = this.buildListener();

		this.transformedListHolder.addListChangeListener(ListValueModel.LIST_VALUES, listener);
		assertTrue(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.transformedListHolder.removeListChangeListener(ListValueModel.LIST_VALUES, listener);
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.transformedListHolder.addChangeListener(listener);
		assertTrue(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));

		this.transformedListHolder.removeChangeListener(listener);
		assertFalse(((AbstractModel) this.listHolder).hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
	}


	/**
	 * execute the same set of tests again, but by passing a Transformer to the adapter
	 * (as opposed to overriding #transformItem(Object))
	 */
	public static class TransformerTests extends TransformationListValueModelTests {
		public TransformerTests(String name) {
			super(name);
		}
		@Override
		ListValueModel<String> buildTransformedListHolder(ListValueModel<String> lvm) {
			return new TransformationListValueModel<String, String>(lvm, this.buildTransformer());
		}
		private Transformer<String, String> buildTransformer() {
			return new Transformer<String, String>() {
				public String transform(String s) {
					return (s == null) ? null : s.toUpperCase();
				}
			};
		}
	}
	
}
