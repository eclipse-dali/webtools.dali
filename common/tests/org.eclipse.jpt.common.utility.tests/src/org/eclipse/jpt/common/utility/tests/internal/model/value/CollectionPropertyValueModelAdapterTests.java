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

import java.util.Collection;

import junit.framework.TestCase;

import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class CollectionPropertyValueModelAdapterTests extends TestCase {
	private ModifiablePropertyValueModel<Boolean> adapter;
	private SimpleCollectionValueModel<String> wrappedCollectionHolder;
	PropertyChangeEvent event;

	public CollectionPropertyValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.wrappedCollectionHolder = new SimpleCollectionValueModel<String>();
		this.adapter = new LocalAdapter(this.wrappedCollectionHolder, "666");
		this.event = null;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	private boolean booleanValue() {
		return this.adapter.getValue().booleanValue();
	}

	private Collection<String> wrappedCollection() {
		return CollectionTools.hashBag(this.wrappedCollectionHolder.iterator());
	}

	public void testValue() {
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, new PropertyChangeListener() {
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

		this.wrappedCollectionHolder.clear();
		assertFalse(this.booleanValue());
		assertFalse(this.wrappedCollection().contains("666"));
	}

	public void testSetValue() {
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, new PropertyChangeListener() {
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
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, new PropertyChangeListener() {
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

		this.wrappedCollectionHolder.clear();
		this.verifyEvent(true, false);
	}

	private void verifyEvent(boolean oldValue, boolean newValue) {
		assertEquals(this.adapter, this.event.getSource());
		assertEquals(Boolean.valueOf(oldValue), this.event.getOldValue());
		assertEquals(Boolean.valueOf(newValue), this.event.getNewValue());
		this.event = null;
	}

	public void testStaleValue() {
		PropertyChangeListener listener = new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		};
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		this.wrappedCollectionHolder.add("666");
		assertTrue(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));

		this.adapter.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertFalse(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));

		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));
	}

	public void testHasListeners() {
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(((AbstractModel) this.wrappedCollectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		ChangeListener listener = new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		};
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.wrappedCollectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		this.adapter.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(((AbstractModel) this.wrappedCollectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		this.adapter.addChangeListener(listener);
		assertTrue(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.wrappedCollectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		this.adapter.removeChangeListener(listener);
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(((AbstractModel) this.wrappedCollectionHolder).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
	}


	// ********** member class **********

	/**
	 * the value is true if the wrapped collection contains the specified item,
	 * otherwise the value is false
	 */
	static class LocalAdapter
		extends CollectionPropertyValueModelAdapter<Boolean, String>
		implements ModifiablePropertyValueModel<Boolean>
	{
		private String item;

		LocalAdapter(CollectionValueModel<String> collectionHolder, String item) {
			super(collectionHolder);
			this.item = item;
		}

		// ********** CollectionPropertyValueModelAdapter implementation **********
		/**
		 * always return a Boolean
		 */
		@Override
		public Boolean getValue() {
			Boolean result = super.getValue();
			return (result == null) ? Boolean.FALSE : result;
		}
		@SuppressWarnings("unchecked")
		public void setValue(Boolean value) {
			if (this.booleanValue()) {
				if ( ! this.booleanValueOf(value)) {
					// the value is changing from true to false
					((SimpleCollectionValueModel<String>) this.collectionModel).remove(this.item);
				}
			} else {
				if (this.booleanValueOf(value)) {
					// the value is changing from false to true
					((SimpleCollectionValueModel<String>) this.collectionModel).add(this.item);
				}
			}
		}
		@Override
		protected Boolean buildValue() {
			return Boolean.valueOf(IteratorTools.contains(this.collectionModel.iterator(), this.item));
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
