/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value;

import java.util.Collection;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.model.AbstractModel;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionTransformationPluggablePropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.tests.internal.TestTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import junit.framework.TestCase;

@SuppressWarnings("nls")
public class CollectionPropertyValueModelAdapterTests
	extends TestCase
{
	private PropertyValueModel<Boolean> adapter;
	private SimpleCollectionValueModel<String> collectionModel;
	PropertyChangeEvent event;

	public CollectionPropertyValueModelAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.collectionModel = new SimpleCollectionValueModel<>();
		this.adapter = CollectionValueModelTools.propertyValueModel(this.collectionModel, new LocalTransformer("666"));
		this.event = null;
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	private boolean booleanValue() {
		Boolean value = this.adapter.getValue();
		return (value != null) && value.booleanValue();
	}

	private Collection<String> wrappedCollection() {
		return CollectionTools.hashBag(this.collectionModel.iterator());
	}

	public void testValue() {
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		});
		assertFalse(this.booleanValue());
		assertFalse(this.wrappedCollection().contains("666"));

		this.collectionModel.add("111");
		assertFalse(this.booleanValue());

		this.collectionModel.add("222");
		assertFalse(this.booleanValue());

		this.collectionModel.add("666");
		assertTrue(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));

		this.collectionModel.remove("666");
		assertFalse(this.booleanValue());
		assertFalse(this.wrappedCollection().contains("666"));

		this.collectionModel.add("666");
		assertTrue(this.booleanValue());
		assertTrue(this.wrappedCollection().contains("666"));

		this.collectionModel.clear();
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

		this.collectionModel.add("111");
		assertNull(this.event);

		this.collectionModel.add("222");
		assertNull(this.event);

		this.collectionModel.add("666");
		this.verifyEvent(false, true);

		this.collectionModel.remove("666");
		this.verifyEvent(true, false);

		this.collectionModel.add("666");
		this.verifyEvent(false, true);

		this.collectionModel.clear();
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
		this.collectionModel.add("666");
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
		assertFalse(((AbstractModel) this.collectionModel).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		ChangeListener listener = new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		};
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertTrue(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.collectionModel).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		this.adapter.removePropertyChangeListener(PropertyValueModel.VALUE, listener);
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(((AbstractModel) this.collectionModel).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		this.adapter.addChangeListener(listener);
		assertTrue(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(((AbstractModel) this.collectionModel).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		this.adapter.removeChangeListener(listener);
		assertFalse(((AbstractModel) this.adapter).hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertFalse(((AbstractModel) this.collectionModel).hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
	}

	public void testToString1() {
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		});
		assertTrue(this.adapter.toString().endsWith("(false)"));
		this.collectionModel.add("666");
		assertTrue(this.adapter.toString().endsWith("(true)"));
	}

	public void testToString3() {
		CollectionTransformationPluggablePropertyValueModelAdapter.Factory<String, Boolean> f = new CollectionTransformationPluggablePropertyValueModelAdapter.Factory<>(this.collectionModel, new LocalTransformer("666"));
		assertTrue(f.toString().indexOf("Factory") != -1);
	}

	public void testToString4() {
		PropertyChangeListener listener = new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {/* OK */}
		};
		this.adapter.addPropertyChangeListener(PropertyValueModel.VALUE, listener);

		Object a = ObjectTools.get(this.adapter, "adapter");
		Object l = ObjectTools.get(a, "listener");
		assertTrue(l.toString().indexOf("AdapterListener") != -1);
	}

	public void testCtor_NPE1A() {
		Object object;
		boolean exCaught = false;
		try {
			object = CollectionValueModelTools.propertyValueModel(null, new LocalTransformer("666"));
			fail("bogus: " + object);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE1B() {
		Object object;
		boolean exCaught = false;
		try {
			object = CollectionValueModelTools.propertyValueModel(null, new LocalTransformer("666"));
			fail("bogus: " + object);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE2A() {
		Object object;
		boolean exCaught = false;
		try {
			object = CollectionValueModelTools.propertyValueModel(this.collectionModel, null);
			fail("bogus: " + object);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE2B() {
		Object object;
		boolean exCaught = false;
		try {
			object = CollectionValueModelTools.propertyValueModel(this.collectionModel, null);
			fail("bogus: " + object);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testCtor_NPE4() {
		Object object;
		boolean exCaught = false;
		try {
			object = PropertyValueModelTools.propertyValueModel(null);
			fail("bogus: " + object);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	// ********** member class **********

	/**
	 * Transform the collection to <code>true</code> if it contains the specified item,
	 * otherwise transform it to <code>false</code>.
	 */
	static class LocalTransformer
		implements Transformer<Collection<String>, Boolean>
	{
		private final String item;

		LocalTransformer(String item) {
			super();
			this.item = item;
		}

		public Boolean transform(Collection<String> collection) {
			return Boolean.valueOf(collection.contains(this.item));
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.item);
		}
	}
}
