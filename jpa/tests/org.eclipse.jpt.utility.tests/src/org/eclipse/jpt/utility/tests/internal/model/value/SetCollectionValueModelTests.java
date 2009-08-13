/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.SetCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class SetCollectionValueModelTests extends TestCase {
	private SimpleCollectionValueModel<String> collectionHolder;
	CollectionAddEvent addEvent;
	CollectionRemoveEvent removeEvent;
	CollectionClearEvent collectionClearedEvent;
	CollectionChangeEvent collectionChangedEvent;

	private CollectionValueModel<String> setHolder;
	CollectionAddEvent setAddEvent;
	CollectionRemoveEvent setRemoveEvent;
	CollectionClearEvent setClearedEvent;
	CollectionChangeEvent setChangedEvent;

	public SetCollectionValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.collectionHolder = new SimpleCollectionValueModel<String>(this.buildCollection());
		this.setHolder = new SetCollectionValueModel<String>(this.collectionHolder);
	}

	private Collection<String> buildCollection() {
		Collection<String> collection = new ArrayList<String>();
		collection.add("foo");
		collection.add("foo");
		return collection;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		// add a listener to "activate" the wrapper
		this.setHolder.addChangeListener(this.buildSetChangeListener());

		this.verify(this.collectionHolder, "foo", "foo");
		this.verify(this.setHolder, "foo");

		this.collectionHolder.add("bar");
		this.collectionHolder.add("bar");
		this.verify(this.collectionHolder, "foo", "foo", "bar", "bar");
		this.verify(this.setHolder, "foo", "bar");

		this.collectionHolder.remove("bar");
		this.verify(this.collectionHolder, "foo", "foo", "bar");
		this.verify(this.setHolder, "foo", "bar");

		this.collectionHolder.remove("bar");
		this.verify(this.collectionHolder, "foo", "foo");
		this.verify(this.setHolder, "foo");

		this.collectionHolder.remove("foo");
		this.verify(this.collectionHolder, "foo");
		this.verify(this.setHolder, "foo");

		this.collectionHolder.remove("foo");
		assertFalse(this.collectionHolder.iterator().hasNext());
		assertFalse(this.setHolder.iterator().hasNext());
	}

	public void testSize() {
		// add a listener to "activate" the wrapper
		this.setHolder.addChangeListener(this.buildSetChangeListener());

		assertEquals(2, this.collectionHolder.size());
		assertEquals(1, this.setHolder.size());

		this.collectionHolder.add("bar");
		this.collectionHolder.add("bar");
		assertEquals(4, this.collectionHolder.size());
		assertEquals(2, this.setHolder.size());

		this.collectionHolder.remove("bar");
		assertEquals(3, this.collectionHolder.size());
		assertEquals(2, this.setHolder.size());

		this.collectionHolder.remove("bar");
		assertEquals(2, this.collectionHolder.size());
		assertEquals(1, this.setHolder.size());

		this.collectionHolder.remove("foo");
		assertEquals(1, this.collectionHolder.size());
		assertEquals(1, this.setHolder.size());

		this.collectionHolder.remove("foo");
		assertEquals(0, this.collectionHolder.size());
		assertEquals(0, this.setHolder.size());
	}

	public void testBulkChange() {
		// add a listener to "activate" the wrapper
		this.setHolder.addChangeListener(this.buildSetChangeListener());

		Collection<String> newCollection = new ArrayList<String>();
		newCollection.add("fox");
		newCollection.add("fox");
		newCollection.add("bat");

		this.collectionHolder.setValues(newCollection);
		this.verify(this.collectionHolder, "fox", "fox", "bat");
		this.verify(this.setHolder, "fox", "bat");
	}		

	public void testLazyListening() {
		assertTrue(((AbstractModel) this.collectionHolder).hasNoCollectionChangeListeners(CollectionValueModel.VALUES));
		ChangeListener listener = this.buildSetChangeListener();
		this.setHolder.addChangeListener(listener);
		assertTrue(((AbstractModel) this.collectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.setHolder.removeChangeListener(listener);
		assertTrue(((AbstractModel) this.collectionHolder).hasNoCollectionChangeListeners(CollectionValueModel.VALUES));

		this.setHolder.addCollectionChangeListener(CollectionValueModel.VALUES, listener);
		assertTrue(((AbstractModel) this.collectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.setHolder.removeCollectionChangeListener(CollectionValueModel.VALUES, listener);
		assertTrue(((AbstractModel) this.collectionHolder).hasNoCollectionChangeListeners(CollectionValueModel.VALUES));
	}

	public void testEvents1() {
		this.collectionHolder.addChangeListener(this.buildChangeListener());
		this.setHolder.addChangeListener(this.buildSetChangeListener());
		this.verifyEvents();
	}

	public void testEvents2() {
		this.collectionHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildCollectionChangeListener());
		this.setHolder.addCollectionChangeListener(CollectionValueModel.VALUES, this.buildSetCollectionChangeListener());
		this.verifyEvents();
	}

	private void clearEvents() {
		this.addEvent = null;
		this.removeEvent = null;
		this.collectionClearedEvent = null;
		this.collectionChangedEvent = null;
		this.setAddEvent = null;
		this.setRemoveEvent = null;
		this.setClearedEvent = null;
		this.setChangedEvent = null;
	}

	private void verifyEvents() {
		this.clearEvents();
		this.collectionHolder.add("bar");
		this.verifyEvent(this.addEvent, this.collectionHolder, "bar");
		this.verifyEvent(this.setAddEvent, this.setHolder, "bar");

		this.clearEvents();
		this.collectionHolder.remove("foo");
		this.verifyEvent(this.removeEvent, this.collectionHolder, "foo");
		assertNull(this.setRemoveEvent);

		this.clearEvents();
		this.collectionHolder.add("bar");
		this.verifyEvent(this.addEvent, this.collectionHolder, "bar");
		assertNull(this.setAddEvent);

		this.clearEvents();
		this.collectionHolder.remove("foo");
		this.verifyEvent(this.removeEvent, this.collectionHolder, "foo");
		this.verifyEvent(this.setRemoveEvent, this.setHolder, "foo");

		this.clearEvents();
		this.collectionHolder.add("foo");
		this.verifyEvent(this.addEvent, this.collectionHolder, "foo");
		this.verifyEvent(this.setAddEvent, this.setHolder, "foo");

		this.clearEvents();
		this.collectionHolder.clear();
		this.verifyEvent(this.collectionClearedEvent, this.collectionHolder);
		this.verifyEvent(this.setClearedEvent, this.setHolder);

		this.clearEvents();
		Collection<String> newCollection = new ArrayList<String>();
		newCollection.add("fox");
		newCollection.add("fox");
		newCollection.add("bat");
		newCollection.add("bat");
		newCollection.add("bat");
		this.collectionHolder.setValues(newCollection);
		this.verifyEvent(this.collectionChangedEvent, this.collectionHolder, "fox", "fox", "bat", "bat", "bat");
		this.verifyEvent(this.setChangedEvent, this.setHolder, "fox", "bat");
		
	}

	private CollectionChangeListener buildCollectionChangeListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionAddEvent event) {
				SetCollectionValueModelTests.this.addEvent = event;
			}
			public void itemsRemoved(CollectionRemoveEvent event) {
				SetCollectionValueModelTests.this.removeEvent = event;
			}
			public void collectionCleared(CollectionClearEvent event) {
				SetCollectionValueModelTests.this.collectionClearedEvent = event;
			}
			public void collectionChanged(CollectionChangeEvent event) {
				SetCollectionValueModelTests.this.collectionChangedEvent = event;
			}
		};
	}

	private ChangeListener buildChangeListener() {
		return new ChangeAdapter() {
			@Override
			public void itemsAdded(CollectionAddEvent event) {
				SetCollectionValueModelTests.this.addEvent = event;
			}
			@Override
			public void itemsRemoved(CollectionRemoveEvent event) {
				SetCollectionValueModelTests.this.removeEvent = event;
			}
			@Override
			public void collectionCleared(CollectionClearEvent event) {
				SetCollectionValueModelTests.this.collectionClearedEvent = event;
			}
			@Override
			public void collectionChanged(CollectionChangeEvent event) {
				SetCollectionValueModelTests.this.collectionChangedEvent = event;
			}
		};
	}

	private CollectionChangeListener buildSetCollectionChangeListener() {
		return new CollectionChangeListener() {
			public void itemsAdded(CollectionAddEvent event) {
				SetCollectionValueModelTests.this.setAddEvent = event;
			}
			public void itemsRemoved(CollectionRemoveEvent event) {
				SetCollectionValueModelTests.this.setRemoveEvent = event;
			}
			public void collectionCleared(CollectionClearEvent event) {
				SetCollectionValueModelTests.this.setClearedEvent = event;
			}
			public void collectionChanged(CollectionChangeEvent event) {
				SetCollectionValueModelTests.this.setChangedEvent = event;
			}
		};
	}

	private ChangeListener buildSetChangeListener() {
		return new ChangeAdapter() {
			@Override
			public void itemsAdded(CollectionAddEvent event) {
				SetCollectionValueModelTests.this.setAddEvent = event;
			}
			@Override
			public void itemsRemoved(CollectionRemoveEvent event) {
				SetCollectionValueModelTests.this.setRemoveEvent = event;
			}
			@Override
			public void collectionCleared(CollectionClearEvent event) {
				SetCollectionValueModelTests.this.setClearedEvent = event;
			}
			@Override
			public void collectionChanged(CollectionChangeEvent event) {
				SetCollectionValueModelTests.this.setChangedEvent = event;
			}
		};
	}

	private void verify(CollectionValueModel<String> cvm, String... expectedItems) {
		Bag<String> actual = CollectionTools.bag(cvm);
		Bag<String> expected = CollectionTools.bag(expectedItems);
		assertEquals(expected, actual);
	}

	private void verifyEvent(CollectionAddEvent event, Object source, Object... expectedItems) {
		assertEquals(source, event.getSource());
		assertEquals(CollectionValueModel.VALUES, event.getCollectionName());
		assertEquals(CollectionTools.bag(expectedItems), CollectionTools.bag(event.getItems()));
	}

	private void verifyEvent(CollectionRemoveEvent event, Object source, Object... expectedItems) {
		assertEquals(source, event.getSource());
		assertEquals(CollectionValueModel.VALUES, event.getCollectionName());
		assertEquals(CollectionTools.bag(expectedItems), CollectionTools.bag(event.getItems()));
	}

	private void verifyEvent(CollectionClearEvent event, Object source) {
		assertEquals(source, event.getSource());
		assertEquals(CollectionValueModel.VALUES, event.getCollectionName());
	}

	private void verifyEvent(CollectionChangeEvent event, Object source, Object... expectedItems) {
		assertEquals(source, event.getSource());
		assertEquals(CollectionValueModel.VALUES, event.getCollectionName());
		assertEquals(CollectionTools.bag(expectedItems), CollectionTools.bag(event.getCollection()));
	}

}
