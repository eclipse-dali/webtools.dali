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

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.ValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

import junit.framework.TestCase;

public class CollectionPropertyValueModelAdapterTests extends TestCase {
	private PropertyValueModel adapter;
	private CollectionValueModel wrappedCollectionHolder;
	PropertyChangeEvent event;

	public CollectionPropertyValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wrappedCollectionHolder = new SimpleCollectionValueModel();
		this.adapter = new LocalAdapter(this.wrappedCollectionHolder, "666");
		this.event = null;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	private boolean booleanValue() {
		return ((Boolean) this.adapter.value()).booleanValue();
	}

	private Collection wrappedCollection() {
		return CollectionTools.collection((Iterator) this.wrappedCollectionHolder.values());
	}

	public void testGetValue() {
		this.adapter.addPropertyChangeListener(ValueModel.VALUE, new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		});
		assertFalse(this.booleanValue());
		assertFalse(this.wrappedCollection().contains("666"));

		this.wrappedCollectionHolder.add("111");
		assertFalse(this.booleanValue());

		this.wrappedCollectionHolder.add("222");
		assertFalse(this.booleanValue());

		this.wrappedCollectionHolder.add("666");
		assertTrue(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));

		this.wrappedCollectionHolder.remove("666");
		assertFalse(this.booleanValue());
		assertFalse(this.wrappedCollection().contains("666"));

		this.wrappedCollectionHolder.add("666");
		assertTrue(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));

		((SimpleCollectionValueModel) this.wrappedCollectionHolder).clear();
		assertFalse(this.booleanValue());
		assertFalse(this.wrappedCollection().contains("666"));
	}

	public void testSetValue() {
		this.adapter.addPropertyChangeListener(ValueModel.VALUE, new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		});
		assertFalse(this.booleanValue());
		assertFalse(this.wrappedCollection().contains("666"));

		this.adapter.setValue(Boolean.TRUE);
		assertTrue(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));

		this.adapter.setValue(Boolean.FALSE);
		assertFalse(this.booleanValue());
		assertFalse(this.wrappedCollection().contains("666"));
	}

	public void testEventFiring() {
		this.adapter.addPropertyChangeListener(ValueModel.VALUE, new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				CollectionPropertyValueModelAdapterTests.this.event = e;
			}
		});
		assertNull(this.event);

		this.wrappedCollectionHolder.add("111");
		assertNull(this.event);

		this.wrappedCollectionHolder.add("222");
		assertNull(this.event);

		this.wrappedCollectionHolder.add("666");
		this.verifyEvent(false, true);

		this.wrappedCollectionHolder.remove("666");
		this.verifyEvent(true, false);

		this.wrappedCollectionHolder.add("666");
		this.verifyEvent(false, true);

		((SimpleCollectionValueModel) this.wrappedCollectionHolder).clear();
		this.verifyEvent(true, false);
	}

	private void verifyEvent(boolean oldValue, boolean newValue) {
		assertEquals(this.adapter, this.event.getSource());
		assertEquals(Boolean.valueOf(oldValue), this.event.oldValue());
		assertEquals(Boolean.valueOf(newValue), this.event.newValue());
		this.event = null;
	}

	public void testStaleValue() {
		PropertyChangeListener listener = new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		};
		this.adapter.addPropertyChangeListener(ValueModel.VALUE, listener);
		this.wrappedCollectionHolder.add("666");
		assertTrue(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));

		this.adapter.removePropertyChangeListener(ValueModel.VALUE, listener);
		assertFalse(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));

		this.adapter.addPropertyChangeListener(ValueModel.VALUE, listener);
		assertTrue(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));
	}

	public void testHasListeners() {
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(ValueModel.VALUE));
		assertFalse(((AbstractModel) this.wrappedCollectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		PropertyChangeListener listener = new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		};
		this.adapter.addPropertyChangeListener(ValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(ValueModel.VALUE));
		assertTrue(((AbstractModel) this.wrappedCollectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		this.adapter.removePropertyChangeListener(ValueModel.VALUE, listener);
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(ValueModel.VALUE));
		assertFalse(((AbstractModel) this.wrappedCollectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		this.adapter.addPropertyChangeListener(listener);
		assertTrue(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(ValueModel.VALUE));
		assertTrue(((AbstractModel) this.wrappedCollectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		this.adapter.removePropertyChangeListener(listener);
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(ValueModel.VALUE));
		assertFalse(((AbstractModel) this.wrappedCollectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
	}


	// ********** member class **********

	/**
	 * the value is true if the wrapped collection contains the specified item,
	 * otherwise the value is false
	 */
	private static class LocalAdapter extends CollectionPropertyValueModelAdapter {
		private Object item;

		LocalAdapter(CollectionValueModel collectionHolder, Object item) {
			super(collectionHolder);
			this.item = item;
		}

		// ********** CollectionPropertyValueModelAdapter implementation **********
		/**
		 * always return a Boolean
		 */
		@Override
		public Object value() {
			Object result = super.value();
			return (result == null) ? Boolean.FALSE : result;
		}
		@Override
		public void setValue(Object value) {
			if (this.booleanValue()) {
				if ( ! this.booleanValueOf(value)) {
					// the value is changing from true to false
					this.collectionHolder.remove(this.item);
				}
			} else {
				if (this.booleanValueOf(value)) {
					// the value is changing from false to true
					this.collectionHolder.add(this.item);
				}
			}
		}
		@Override
		protected Object buildValue() {
			return Boolean.valueOf(CollectionTools.contains((Iterator) this.collectionHolder.values(), this.item));
		}

		// ********** internal methods **********
		private boolean booleanValue() {
			return this.booleanValueOf(this.value);
		}
		private boolean booleanValueOf(Object b) {
			return (b == null) ? false : ((Boolean) b).booleanValue();
		}

	}

}
