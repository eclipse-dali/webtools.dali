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

import java.util.Collection;

import javax.swing.JList;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.HashBag;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ListModelAdapter;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class PropertyCollectionValueModelAdapterTests extends TestCase {
	private CollectionValueModel<String> adapter;
	private WritablePropertyValueModel<String> wrappedValueHolder;

	public PropertyCollectionValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wrappedValueHolder = new SimplePropertyValueModel<String>();
		this.adapter = new PropertyCollectionValueModelAdapter<String>(this.wrappedValueHolder);
	}

	private Collection<String> wrappedCollection() {
		return CollectionTools.collection(new SingleElementIterator<String>(this.wrappedValueHolder.getValue()));
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testIterator() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {/* OK */}
		});
		this.wrappedValueHolder.setValue("foo");
		Collection<String> adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(1, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);
		assertEquals("foo", adapterCollection.iterator().next());
	}

	public void testStaleValue() {
		CollectionChangeListener listener = new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {/* OK */}
		};
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, listener);
		this.wrappedValueHolder.setValue("foo");
		Collection<String> adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(1, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);
		assertEquals("foo", adapterCollection.iterator().next());

		this.adapter.removeCollectionChangeListener(CollectionValueModel.VALUES, listener);
		adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(0, adapterCollection.size());
		assertEquals(new HashBag<String>(), adapterCollection);

		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, listener);
		adapterCollection = CollectionTools.collection(this.adapter.iterator());
		assertEquals(1, adapterCollection.size());
		assertEquals(this.wrappedCollection(), adapterCollection);
		assertEquals("foo", adapterCollection.iterator().next());
	}

	public void testHasListeners() {
		assertFalse(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		CoordinatedBag<String> synchCollection = new CoordinatedBag<String>(this.adapter);
		assertTrue(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.adapter.removeCollectionChangeListener(CollectionValueModel.VALUES, synchCollection);
		assertFalse(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.adapter.addCollectionChangeListener(synchCollection);
		assertTrue(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		this.adapter.removeCollectionChangeListener(synchCollection);
		assertFalse(((AbstractModel) this.adapter).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
	}

	public void testListChangedToEmpty() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {/* OK */}
			@Override
			public void itemsRemoved(CollectionRemoveEvent e) {/* OK */}
		});
		this.wrappedValueHolder.setValue("foo");
		JList jList = new JList(new ListModelAdapter(this.adapter));
		this.wrappedValueHolder.setValue(null);
		assertEquals(0, jList.getModel().getSize());
	}

	public void testCollectionChangedFromEmpty() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener() {
			@Override
			public void itemsAdded(CollectionAddEvent e) {/* OK */}
		});
		JList jList = new JList(new ListModelAdapter(this.adapter));
		
		this.wrappedValueHolder.setValue("foo");
		assertEquals(1, jList.getModel().getSize());
	}
	
	public void testCollectionChangedFromEmptyToEmpty() {
		this.adapter.addCollectionChangeListener(CollectionValueModel.VALUES, new TestListener());
		JList jList = new JList(new ListModelAdapter(this.adapter));
		
		this.wrappedValueHolder.setValue(null);
		assertEquals(0, jList.getModel().getSize());
	}


	// ********** member class **********
	
	static class TestListener implements CollectionChangeListener {
		public void collectionChanged(CollectionChangeEvent event) {
			fail("unexpected event");
		}
		public void collectionCleared(CollectionChangeEvent event) {
			fail("unexpected event");
		}
		public void itemsAdded(CollectionAddEvent event) {
			fail("unexpected event");
		}
		public void itemsRemoved(CollectionRemoveEvent event) {
			fail("unexpected event");
		}
	}

}
