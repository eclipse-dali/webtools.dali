/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.CollectionChangeAdapter;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.ListChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.listener.StateChangeAdapter;
import org.eclipse.jpt.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.utility.model.listener.TreeChangeAdapter;
import org.eclipse.jpt.utility.model.listener.TreeChangeListener;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class ChangeSupportTests
	extends TestCase
	implements StateChangeListener, PropertyChangeListener, CollectionChangeListener, ListChangeListener, TreeChangeListener
{
	private TestModel testModel;
	private static final String TEST_TO_STRING = "this is a test";

	private StateChangeEvent stateChangeEvent;
	private boolean stateChangedCalled = false;

	private PropertyChangeEvent propertyChangeEvent;
	private boolean propertyChangeCalled = false;
	private static final String PROPERTY_NAME = "propertyName";
	static final Object OLD_OBJECT_VALUE = new Object();
	static final Object NEW_OBJECT_VALUE = new Object();
	static final Integer OLD_INT_VALUE = new Integer(27);
	static final Boolean OLD_BOOLEAN_VALUE = Boolean.TRUE;
	static final Integer NEW_INT_VALUE = new Integer(42);
	static final Boolean NEW_BOOLEAN_VALUE = Boolean.FALSE;

	private CollectionChangeEvent collectionChangeEvent;
	private boolean itemsAddedCollectionCalled = false;
	private boolean itemsRemovedCollectionCalled = false;
	private boolean collectionChangedCalled = false;
	private boolean collectionClearedCalled = false;
	private static final String COLLECTION_NAME = "collectionName";
	static final Object ADDED_OBJECT_VALUE = new Object();
	static final Object ADDED_OBJECT_VALUE_2 = new Object();
	static final Object REMOVED_OBJECT_VALUE = new Object();
	static final int TARGET_INDEX = 7;
	static final int SOURCE_INDEX = 22;

	private ListChangeEvent listChangeEvent;
	private boolean itemsAddedListCalled = false;
	private boolean itemsRemovedListCalled = false;
	private boolean itemsReplacedListCalled = false;
	private boolean itemsMovedListCalled = false;
	private boolean listChangedCalled = false;
	private boolean listClearedCalled = false;
	private static final String LIST_NAME = "listName";
	private static final int ADD_INDEX = 3;
	private static final int REMOVE_INDEX = 5;
	private static final int REPLACE_INDEX = 2;

	private TreeChangeEvent treeChangeEvent;
	private boolean nodeAddedCalled = false;
	private boolean nodeRemovedCalled = false;
	private boolean treeChangedCalled = false;
	private boolean treeClearedCalled = false;
	private static final String TREE_NAME = "treeName";
	static final Object[] OBJECT_ARRAY_PATH = {new Object(), new Object(), new String()};
	static final Object[] EMPTY_PATH = {};


	public ChangeSupportTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.testModel = new TestModel();
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}


	// ********** general tests **********

	public void testNullSource() {
		boolean exCaught = false;
		try {
			ChangeSupport cs = new ChangeSupport(null);
			fail("bogus change support: " + cs);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	// ********** state change tests **********

	public void testFireStateChange() {
		this.stateChangeEvent = null;
		this.stateChangedCalled = false;
		this.testModel.addStateChangeListener(this);
		this.testModel.testFireStateChange();
		assertNotNull(this.stateChangeEvent);
		assertEquals(this.testModel, this.stateChangeEvent.getSource());
		assertTrue(this.stateChangedCalled);
	}

	public void testHasAnyStateChangeListeners() {
		assertTrue(this.testModel.hasNoStateChangeListeners());
		this.testModel.addStateChangeListener(this);
		assertTrue(this.testModel.hasAnyStateChangeListeners());
		this.testModel.removeStateChangeListener(this);
		assertTrue(this.testModel.hasNoStateChangeListeners());
	}

	public void testHasAnyStateChangeListenersDuplicate() {
		assertTrue(this.testModel.hasNoStateChangeListeners());
		this.testModel.addStateChangeListener(this);
		this.testModel.addStateChangeListener(this);
		assertTrue(this.testModel.hasAnyStateChangeListeners());
		this.testModel.removeStateChangeListener(this);
		assertTrue(this.testModel.hasAnyStateChangeListeners());
		this.testModel.removeStateChangeListener(this);
		assertTrue(this.testModel.hasNoStateChangeListeners());
	}

	public void testAddNullStateListener() {
		boolean exCaught = false;
		try {
			this.testModel.addStateChangeListener(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveBogusStateListener() {
		boolean exCaught = false;
		try {
			this.testModel.removeStateChangeListener(this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener(this);
		exCaught = false;
		try {
			this.testModel.removeStateChangeListener(this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addStateChangeListener(this);
		exCaught = false;
		try {
			this.testModel.removeStateChangeListener(new ChangeSupportTests("dummy"));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.testModel.removeStateChangeListener(new StateChangeAdapter());
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}


	// ********** property change tests **********

	public void testFirePropertyChangedEvent() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(this);
		this.testModel.testFirePropertyChangedEvent();
		this.verifyPropertyChangeEvent(OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(this);
		this.testModel.testFirePropertyChangedEvent();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedEvent();
		this.verifyPropertyChangeEvent(OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedEvent();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedEventNoChange() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(this);
		this.testModel.testFirePropertyChangedEventNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(this);
		this.testModel.testFirePropertyChangedEventNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedEventNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedEventNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedObjectObject() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(this);
		this.testModel.testFirePropertyChangedObjectObject();
		this.verifyPropertyChangeEvent(OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(this);
		this.testModel.testFirePropertyChangedObjectObject();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedObjectObject();
		this.verifyPropertyChangeEvent(OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedObjectObject();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedObjectObjectNoChange() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(this);
		this.testModel.testFirePropertyChangedObjectObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(this);
		this.testModel.testFirePropertyChangedObjectObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedObjectObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedObjectObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedObject() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(this);
		this.testModel.testFirePropertyChangedObject();
		this.verifyPropertyChangeEvent(null, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(this);
		this.testModel.testFirePropertyChangedObject();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedObject();
		this.verifyPropertyChangeEvent(null, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedObject();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedObjectNoChange() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(this);
		this.testModel.testFirePropertyChangedObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(this);
		this.testModel.testFirePropertyChangedObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedIntInt() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(this);
		this.testModel.testFirePropertyChangedIntInt();
		this.verifyPropertyChangeEvent(OLD_INT_VALUE, NEW_INT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(this);
		this.testModel.testFirePropertyChangedIntInt();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedIntInt();
		this.verifyPropertyChangeEvent(OLD_INT_VALUE, NEW_INT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedIntInt();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedIntIntNoChange() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(this);
		this.testModel.testFirePropertyChangedIntIntNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(this);
		this.testModel.testFirePropertyChangedIntIntNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedIntIntNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedIntIntNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedBooleanBoolean() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(this);
		this.testModel.testFirePropertyChangedBooleanBoolean();
		this.verifyPropertyChangeEvent(OLD_BOOLEAN_VALUE, NEW_BOOLEAN_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(this);
		this.testModel.testFirePropertyChangedBooleanBoolean();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedBooleanBoolean();
		this.verifyPropertyChangeEvent(OLD_BOOLEAN_VALUE, NEW_BOOLEAN_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedBooleanBoolean();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedBooleanBooleanNoChange() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(this);
		this.testModel.testFirePropertyChangedBooleanBooleanNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(this);
		this.testModel.testFirePropertyChangedBooleanBooleanNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedBooleanBooleanNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		this.testModel.testFirePropertyChangedBooleanBooleanNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testHasAnyPropertyChangeListeners() {
		assertTrue(this.testModel.hasNoPropertyChangeListeners(PROPERTY_NAME));
		this.testModel.addPropertyChangeListener(this);
		assertTrue(this.testModel.hasAnyPropertyChangeListeners(PROPERTY_NAME));
		this.testModel.removePropertyChangeListener(this);
		assertTrue(this.testModel.hasNoPropertyChangeListeners(PROPERTY_NAME));

		assertTrue(this.testModel.hasNoPropertyChangeListeners(PROPERTY_NAME));
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this);
		assertTrue(this.testModel.hasAnyPropertyChangeListeners(PROPERTY_NAME));
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this);
		assertTrue(this.testModel.hasNoPropertyChangeListeners(PROPERTY_NAME));
	}

	public void testAddNullPropertyListener() {
		boolean exCaught = false;
		try {
			this.testModel.addPropertyChangeListener(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddNullPropertyListenerName() {
		boolean exCaught = false;
		try {
			this.testModel.addPropertyChangeListener("foo", null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveBogusPropertyListener() {
		boolean exCaught = false;
		try {
			this.testModel.removePropertyChangeListener(this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addCollectionChangeListener(this);
		exCaught = false;
		try {
			this.testModel.removePropertyChangeListener(this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener(this);
		exCaught = false;
		try {
			this.testModel.removePropertyChangeListener(new ChangeSupportTests("dummy"));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.testModel.removePropertyChangeListener(new PropertyChangeAdapter());
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveBogusPropertyListenerName() {
		boolean exCaught = false;
		try {
			this.testModel.removePropertyChangeListener("foo", this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addCollectionChangeListener("foo", this);
		exCaught = false;
		try {
			this.testModel.removePropertyChangeListener("foo", this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener("foo", this);
		exCaught = false;
		try {
			this.testModel.removePropertyChangeListener("foo", new ChangeSupportTests("dummy"));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.testModel.removePropertyChangeListener("foo", new PropertyChangeAdapter());
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private void verifyPropertyChangeEvent(Object oldValue, Object newValue) {
		this.verifyPropertyChangeEvent(this.testModel, oldValue, newValue);
	}

	private void verifyPropertyChangeEvent(Object source, Object oldValue, Object newValue) {
		assertNotNull(this.propertyChangeEvent);
		assertEquals(source, this.propertyChangeEvent.getSource());
		assertEquals(PROPERTY_NAME, this.propertyChangeEvent.getPropertyName());
		assertEquals(oldValue, this.propertyChangeEvent.getOldValue());
		assertEquals(newValue, this.propertyChangeEvent.getNewValue());
	}


	// ********** collection change tests **********

	public void testFireItemsAddedCollectionEvent() {
		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireItemsAddedCollectionEvent();
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireItemsAddedCollectionEvent();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsAddedCollectionEvent();
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsAddedCollectionEvent();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testFireItemsAddedCollectionEventNoChange() {
		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireItemsAddedCollectionEventNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireItemsAddedCollectionEventNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsAddedCollectionEventNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsAddedCollectionEventNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testFireItemsAddedCollection() {
		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireItemsAddedCollection();
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireItemsAddedCollection();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsAddedCollection();
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsAddedCollection();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testFireItemsAddedCollectionNoChange() {
		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireItemsAddedCollectionNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireItemsAddedCollectionNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsAddedCollectionNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsAddedCollectionNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testFireItemAddedCollection() {
		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireItemAddedCollection();
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireItemAddedCollection();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemAddedCollection();
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemAddedCollection();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testFireItemsRemovedCollectionEvent() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireItemsRemovedCollectionEvent();
		this.verifyCollectionChangeEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireItemsRemovedCollectionEvent();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsRemovedCollectionEvent();
		this.verifyCollectionChangeEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsRemovedCollectionEvent();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testFireItemsRemovedCollectionEventNoChange() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireItemsRemovedCollectionEventNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireItemsRemovedCollectionEventNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsRemovedCollectionEventNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsRemovedCollectionEventNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testFireItemsRemovedCollection() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireItemsRemovedCollection();
		this.verifyCollectionChangeEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireItemsRemovedCollection();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsRemovedCollection();
		this.verifyCollectionChangeEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsRemovedCollection();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testFireItemsRemovedCollectionNoChange() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireItemsRemovedCollectionNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireItemsRemovedCollectionNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsRemovedCollectionNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemsRemovedCollectionNoChange();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testFireItemRemovedCollection() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireItemRemovedCollection();
		this.verifyCollectionChangeEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireItemRemovedCollection();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemRemovedCollection();
		this.verifyCollectionChangeEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireItemRemovedCollection();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testFireCollectionCleared() {
		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireCollectionCleared();
		this.verifyCollectionChangeEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireCollectionCleared();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireCollectionCleared();
		this.verifyCollectionChangeEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireCollectionCleared();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionClearedCalled);
	}

	public void testFireCollectionChangedEvent() {
		this.collectionChangeEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireCollectionChangedEvent();
		this.verifyCollectionChangeEvent(null);
		assertTrue(this.collectionChangedCalled);

		this.collectionChangeEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireCollectionChangedEvent();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionChangedCalled);

		this.collectionChangeEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireCollectionChangedEvent();
		this.verifyCollectionChangeEvent(null);
		assertTrue(this.collectionChangedCalled);

		this.collectionChangeEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireCollectionChangedEvent();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionChangedCalled);
	}

	public void testFireCollectionChanged() {
		this.collectionChangeEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.addCollectionChangeListener(this);
		this.testModel.testFireCollectionChanged();
		this.verifyCollectionChangeEvent(null);
		assertTrue(this.collectionChangedCalled);

		this.collectionChangeEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		this.testModel.testFireCollectionChanged();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionChangedCalled);

		this.collectionChangeEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireCollectionChanged();
		this.verifyCollectionChangeEvent(null);
		assertTrue(this.collectionChangedCalled);

		this.collectionChangeEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		this.testModel.testFireCollectionChanged();
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionChangedCalled);
	}

	public void testAddItemToCollection() {
		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertTrue(this.testModel.testAddItemToCollection());
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertTrue(this.testModel.testAddItemToCollection());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testAddItemToCollection());
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testAddItemToCollection());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testAddItemToCollectionNoChange() {
		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertFalse(this.testModel.testAddItemToCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertFalse(this.testModel.testAddItemToCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testAddItemToCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testAddItemToCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testAddItemsToCollection() {
		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertTrue(this.testModel.testAddItemsToCollection());
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertTrue(this.testModel.testAddItemsToCollection());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testAddItemsToCollection());
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testAddItemsToCollection());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testAddItemsToCollectionNoChange() {
		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertFalse(this.testModel.testAddItemsToCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertFalse(this.testModel.testAddItemsToCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testAddItemsToCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testAddItemsToCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testAddItemsToCollectionMixed() {
		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertTrue(this.testModel.testAddItemsToCollectionMixed());
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE_2);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertTrue(this.testModel.testAddItemsToCollectionMixed());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testAddItemsToCollectionMixed());
		this.verifyCollectionChangeEvent(ADDED_OBJECT_VALUE_2);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testAddItemsToCollectionMixed());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testRemoveItemFromCollection() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertTrue(this.testModel.testRemoveItemFromCollection());
		this.verifyCollectionChangeEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertTrue(this.testModel.testRemoveItemFromCollection());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testRemoveItemFromCollection());
		this.verifyCollectionChangeEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testRemoveItemFromCollection());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRemoveItemFromCollectionNoChange() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertFalse(this.testModel.testRemoveItemFromCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertFalse(this.testModel.testRemoveItemFromCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRemoveItemFromCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRemoveItemFromCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRemoveItemsFromCollection() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertTrue(this.testModel.testRemoveItemsFromCollection());
		this.verifyCollectionChangeEvent2(REMOVED_OBJECT_VALUE, "foo", "bar");
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertTrue(this.testModel.testRemoveItemsFromCollection());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testRemoveItemsFromCollection());
		this.verifyCollectionChangeEvent2(REMOVED_OBJECT_VALUE, "foo", "bar");
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testRemoveItemsFromCollection());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRemoveItemsFromCollectionNoChange1() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange1());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange1());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange1());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange1());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRemoveItemsFromCollectionNoChange2() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange2());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange2());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange2());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange2());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRemoveItemsFromCollectionNoChange3() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange3());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange3());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange3());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange3());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRetainItemsInCollection1() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertTrue(this.testModel.testRetainItemsInCollection1());
		this.verifyCollectionChangeEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertTrue(this.testModel.testRetainItemsInCollection1());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testRetainItemsInCollection1());
		this.verifyCollectionChangeEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testRetainItemsInCollection1());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	// collection cleared...
	public void testRetainItemsInCollection2() {
		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertTrue(this.testModel.testRetainItemsInCollection2());
		this.verifyCollectionChangeEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertTrue(this.testModel.testRetainItemsInCollection2());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testRetainItemsInCollection2());
		this.verifyCollectionChangeEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testRetainItemsInCollection2());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionClearedCalled);
	}

	public void testRetainItemsInCollectionNoChange1() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange1());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange1());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange1());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange1());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRetainItemsInCollectionNoChange2() {
		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange2());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange2());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange2());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionChangeEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange2());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testClearCollection() {
		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertTrue(this.testModel.testClearCollection());
		this.verifyCollectionChangeEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertTrue(this.testModel.testClearCollection());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testClearCollection());
		this.verifyCollectionChangeEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.testClearCollection());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionClearedCalled);
	}

	public void testClearCollectionNoChange() {
		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(this);
		assertFalse(this.testModel.testClearCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(this);
		assertFalse(this.testModel.testClearCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testClearCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionChangeEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertFalse(this.testModel.testClearCollectionNoChange());
		assertNull(this.collectionChangeEvent);
		assertFalse(this.collectionClearedCalled);
	}

	public void testSynchronizeCollection1() {
		CollectionSynchListener csl = new CollectionSynchListener();
		this.testModel.addCollectionChangeListener(csl);
		assertTrue(this.testModel.testSynchronizeCollection1());
		assertTrue(csl.itemsAdded);
		assertTrue(csl.itemsRemoved);
		assertFalse(csl.collectionChanged);
		assertFalse(csl.collectionCleared);
		assertEquals(2, csl.addedItems.size());
		assertTrue(CollectionTools.containsAll(csl.addedItems, new Object[] {"joo", "jar"}));
		assertEquals(2, csl.removedItems.size());
		assertTrue(CollectionTools.containsAll(csl.removedItems, new Object[] {"foo", "bar"}));
	}

	public void testSynchronizeCollection2() {
		CollectionSynchListener csl = new CollectionSynchListener();
		this.testModel.addCollectionChangeListener(csl);
		assertTrue(this.testModel.testSynchronizeCollection2());
		assertFalse(csl.itemsAdded);
		assertFalse(csl.itemsRemoved);
		assertFalse(csl.collectionChanged);
		assertTrue(csl.collectionCleared);
		assertTrue(csl.addedItems.isEmpty());
		assertTrue(csl.removedItems.isEmpty());
	}

	public void testSynchronizeCollection3() {
		CollectionSynchListener csl = new CollectionSynchListener();
		this.testModel.addCollectionChangeListener(csl);
		assertTrue(this.testModel.testSynchronizeCollection3());
		assertTrue(csl.itemsAdded);
		assertFalse(csl.itemsRemoved);
		assertFalse(csl.collectionChanged);
		assertFalse(csl.collectionCleared);
		assertEquals(3, csl.addedItems.size());
		assertTrue(CollectionTools.containsAll(csl.addedItems, new Object[] {"joo", "jar", "baz"}));
		assertTrue(csl.removedItems.isEmpty());
	}

	class CollectionSynchListener implements CollectionChangeListener {
		boolean itemsAdded = false;
		boolean itemsRemoved = false;
		boolean collectionChanged = false;
		boolean collectionCleared = false;
		Collection<Object> addedItems = new ArrayList<Object>();
		Collection<Object> removedItems = new ArrayList<Object>();
		public void collectionChanged(CollectionChangeEvent event) {
			this.collectionChanged = true;
		}
		public void collectionCleared(CollectionChangeEvent event) {
			this.collectionCleared = true;
		}
		public void itemsAdded(CollectionChangeEvent event) {
			this.itemsAdded = true;
			CollectionTools.addAll(this.addedItems, event.items());
		}
		public void itemsRemoved(CollectionChangeEvent event) {
			this.itemsRemoved = true;
			CollectionTools.addAll(this.removedItems, event.items());
		}
	}

	public void testHasAnyCollectionChangeListeners() {
		assertTrue(this.testModel.hasNoCollectionChangeListeners(COLLECTION_NAME));
		this.testModel.addCollectionChangeListener(this);
		assertTrue(this.testModel.hasAnyCollectionChangeListeners(COLLECTION_NAME));
		this.testModel.removeCollectionChangeListener(this);
		assertTrue(this.testModel.hasNoCollectionChangeListeners(COLLECTION_NAME));

		assertTrue(this.testModel.hasNoCollectionChangeListeners(COLLECTION_NAME));
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.hasAnyCollectionChangeListeners(COLLECTION_NAME));
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this);
		assertTrue(this.testModel.hasNoCollectionChangeListeners(COLLECTION_NAME));
	}

	public void testAddNullCollectionListener() {
		boolean exCaught = false;
		try {
			this.testModel.addCollectionChangeListener(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddNullCollectionListenerName() {
		boolean exCaught = false;
		try {
			this.testModel.addCollectionChangeListener("foo", null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveBogusCollectionListener() {
		boolean exCaught = false;
		try {
			this.testModel.removeCollectionChangeListener(this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener(this);
		exCaught = false;
		try {
			this.testModel.removeCollectionChangeListener(this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addCollectionChangeListener(this);
		exCaught = false;
		try {
			this.testModel.removeCollectionChangeListener(new ChangeSupportTests("dummy"));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.testModel.removeCollectionChangeListener(new CollectionChangeAdapter());
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveBogusCollectionListenerName() {
		boolean exCaught = false;
		try {
			this.testModel.removeCollectionChangeListener("foo", this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener("foo", this);
		exCaught = false;
		try {
			this.testModel.removeCollectionChangeListener("foo", this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addCollectionChangeListener("foo", this);
		exCaught = false;
		try {
			this.testModel.removeCollectionChangeListener("foo", new ChangeSupportTests("dummy"));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.testModel.removeCollectionChangeListener("foo", new CollectionChangeAdapter());
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private void verifyCollectionChangeEvent(Object item) {
		assertNotNull(this.collectionChangeEvent);
		assertEquals(this.testModel, this.collectionChangeEvent.getSource());
		assertEquals(COLLECTION_NAME, this.collectionChangeEvent.getCollectionName());
		if (item == null) {
			assertFalse(this.collectionChangeEvent.items().hasNext());
		} else {
			assertEquals(item, this.collectionChangeEvent.items().next());
		}
	}

	private void verifyCollectionChangeEvent2(Object... items) {
		assertNotNull(this.collectionChangeEvent);
		assertEquals(this.testModel, this.collectionChangeEvent.getSource());
		assertEquals(COLLECTION_NAME, this.collectionChangeEvent.getCollectionName());
		assertEquals(items.length, this.collectionChangeEvent.itemsSize());
		for (Object item : items) {
			assertTrue(CollectionTools.contains(this.collectionChangeEvent.items(), item));
		}
	}


	// ********** list change tests **********

	public void testFireItemsAddedListEvent() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsAddedListEvent();
		this.verifyListChangeEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsAddedListEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsAddedListEvent();
		this.verifyListChangeEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsAddedListEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testFireItemsAddedListEventNoChange() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsAddedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsAddedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsAddedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsAddedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testFireItemsAddedList() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsAddedList();
		this.verifyListChangeEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsAddedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsAddedList();
		this.verifyListChangeEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsAddedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testFireItemsAddedListNoChange() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsAddedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsAddedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsAddedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsAddedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testFireItemAddedList() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemAddedList();
		this.verifyListChangeEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemAddedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemAddedList();
		this.verifyListChangeEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemAddedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testFireItemsRemovedListEvent() {
		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsRemovedListEvent();
		this.verifyListChangeEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsRemovedListEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsRemovedListEvent();
		this.verifyListChangeEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsRemovedListEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testFireItemsRemovedListEventNoChange() {
		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsRemovedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsRemovedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsRemovedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsRemovedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testFireItemsRemovedList() {
		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsRemovedList();
		this.verifyListChangeEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsRemovedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsRemovedList();
		this.verifyListChangeEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsRemovedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testFireItemsRemovedListNoChange() {
		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsRemovedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsRemovedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsRemovedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsRemovedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testFireItemRemovedList() {
		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemRemovedList();
		this.verifyListChangeEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemRemovedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemRemovedList();
		this.verifyListChangeEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listChangeEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemRemovedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testFireItemsReplacedListEvent() {
		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsReplacedListEvent();
		this.verifyListChangeEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsReplacedListEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsReplacedListEvent();
		this.verifyListChangeEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsReplacedListEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testFireItemsReplacedListEventNoChange() {
		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsReplacedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsReplacedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsReplacedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsReplacedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testFireItemsReplacedList() {
		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsReplacedList();
		this.verifyListChangeEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsReplacedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsReplacedList();
		this.verifyListChangeEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsReplacedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testFireItemsReplacedListNoChange() {
		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsReplacedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsReplacedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsReplacedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsReplacedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testFireItemReplacedList() {
		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemReplacedList();
		this.verifyListChangeEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemReplacedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemReplacedList();
		this.verifyListChangeEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listChangeEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemReplacedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testFireItemsMovedListEvent() {
		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsMovedListEvent();
		this.verifyListChangeEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsMovedListEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsMovedListEvent();
		this.verifyListChangeEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsMovedListEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testFireItemsMovedListEventNoChange() {
		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsMovedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsMovedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsMovedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsMovedListEventNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testFireItemsMovedList() {
		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsMovedList();
		this.verifyListChangeEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsMovedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsMovedList();
		this.verifyListChangeEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsMovedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testFireItemsMovedListNoChange() {
		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemsMovedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemsMovedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsMovedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemsMovedListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testFireItemMovedList() {
		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireItemMovedList();
		this.verifyListChangeEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireItemMovedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemMovedList();
		this.verifyListChangeEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listChangeEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireItemMovedList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testFireListClearedEvent() {
		this.listChangeEvent = null;
		this.listClearedCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireListClearedEvent();
		this.verifyListChangeEvent(-1, null);
		assertTrue(this.listClearedCalled);

		this.listChangeEvent = null;
		this.listClearedCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireListClearedEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.listClearedCalled);

		this.listChangeEvent = null;
		this.listClearedCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireListClearedEvent();
		this.verifyListChangeEvent(-1, null);
		assertTrue(this.listClearedCalled);

		this.listChangeEvent = null;
		this.listClearedCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireListClearedEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.listClearedCalled);
	}

	public void testFireListCleared() {
		this.listChangeEvent = null;
		this.listClearedCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireListCleared();
		this.verifyListChangeEvent(-1, null);
		assertTrue(this.listClearedCalled);

		this.listChangeEvent = null;
		this.listClearedCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireListCleared();
		assertNull(this.listChangeEvent);
		assertFalse(this.listClearedCalled);

		this.listChangeEvent = null;
		this.listClearedCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireListCleared();
		this.verifyListChangeEvent(-1, null);
		assertTrue(this.listClearedCalled);

		this.listChangeEvent = null;
		this.listClearedCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireListCleared();
		assertNull(this.listChangeEvent);
		assertFalse(this.listClearedCalled);
	}

	public void testFireListChangedEvent() {
		this.listChangeEvent = null;
		this.listChangedCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireListChangedEvent();
		this.verifyListChangeEvent(-1, null);
		assertTrue(this.listChangedCalled);

		this.listChangeEvent = null;
		this.listChangedCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireListChangedEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.listChangedCalled);

		this.listChangeEvent = null;
		this.listChangedCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireListChangedEvent();
		this.verifyListChangeEvent(-1, null);
		assertTrue(this.listChangedCalled);

		this.listChangeEvent = null;
		this.listChangedCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireListChangedEvent();
		assertNull(this.listChangeEvent);
		assertFalse(this.listChangedCalled);
	}

	public void testFireListChanged() {
		this.listChangeEvent = null;
		this.listChangedCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testFireListChanged();
		this.verifyListChangeEvent(-1, null);
		assertTrue(this.listChangedCalled);

		this.listChangeEvent = null;
		this.listChangedCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testFireListChanged();
		assertNull(this.listChangeEvent);
		assertFalse(this.listChangedCalled);

		this.listChangeEvent = null;
		this.listChangedCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testFireListChanged();
		this.verifyListChangeEvent(-1, null);
		assertTrue(this.listChangedCalled);

		this.listChangeEvent = null;
		this.listChangedCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testFireListChanged();
		assertNull(this.listChangeEvent);
		assertFalse(this.listChangedCalled);
	}

	public void testAddItemToListIndex() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testAddItemToListIndex();
		this.verifyListChangeEvent(2, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testAddItemToListIndex();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemToListIndex();
		this.verifyListChangeEvent(2, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemToListIndex();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testAddItemToList() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testAddItemToList();
		this.verifyListChangeEvent(3, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testAddItemToList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemToList();
		this.verifyListChangeEvent(3, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemToList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testAddItemsToListIndex() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testAddItemsToListIndex();
		this.verifyListChangeEvent(2, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testAddItemsToListIndex();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemsToListIndex();
		this.verifyListChangeEvent(2, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemsToListIndex();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testAddItemsToListIndexNoChange() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testAddItemsToListIndexNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testAddItemsToListIndexNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemsToListIndexNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemsToListIndexNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testAddItemsToList() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testAddItemsToList();
		this.verifyListChangeEvent(3, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testAddItemsToList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemsToList();
		this.verifyListChangeEvent(3, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemsToList();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testAddItemsToListNoChange() {
		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(this);
		this.testModel.testAddItemsToListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(this);
		this.testModel.testAddItemsToListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemsToListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listChangeEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this);
		this.testModel.testAddItemsToListNoChange();
		assertNull(this.listChangeEvent);
		assertFalse(this.itemsAddedListCalled);
	}

// TODO remove
// TODO retain
// TODO replace
// TODO set
// TODO move
// TODO clear
// TODO synchronize

	public void testHasAnyListChangeListeners() {
		assertTrue(this.testModel.hasNoListChangeListeners(LIST_NAME));
		this.testModel.addListChangeListener(this);
		assertTrue(this.testModel.hasAnyListChangeListeners(LIST_NAME));
		this.testModel.removeListChangeListener(this);
		assertTrue(this.testModel.hasNoListChangeListeners(LIST_NAME));

		assertTrue(this.testModel.hasNoListChangeListeners(LIST_NAME));
		this.testModel.addListChangeListener(LIST_NAME, this);
		assertTrue(this.testModel.hasAnyListChangeListeners(LIST_NAME));
		this.testModel.removeListChangeListener(LIST_NAME, this);
		assertTrue(this.testModel.hasNoListChangeListeners(LIST_NAME));
	}

	public void testAddNullListListener() {
		boolean exCaught = false;
		try {
			this.testModel.addListChangeListener(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddNullListListenerName() {
		boolean exCaught = false;
		try {
			this.testModel.addListChangeListener("foo", null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveBogusListListener() {
		boolean exCaught = false;
		try {
			this.testModel.removeListChangeListener(this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener(this);
		exCaught = false;
		try {
			this.testModel.removeListChangeListener(this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addListChangeListener(this);
		exCaught = false;
		try {
			this.testModel.removeListChangeListener(new ChangeSupportTests("dummy"));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.testModel.removeListChangeListener(new ListChangeAdapter());
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveBogusListListenerName() {
		boolean exCaught = false;
		try {
			this.testModel.removeListChangeListener("foo", this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener("foo", this);
		exCaught = false;
		try {
			this.testModel.removeListChangeListener("foo", this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addListChangeListener("foo", this);
		exCaught = false;
		try {
			this.testModel.removeListChangeListener("foo", new ChangeSupportTests("dummy"));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.testModel.removeListChangeListener("foo", new ListChangeAdapter());
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private void verifyListChangeEvent(int index, Object item) {
		this.verifyListChangeEvent(index, item, null);
	}

	private void verifyListChangeEvent(int targetIndex, int sourceIndex) {
		assertNotNull(this.listChangeEvent);
		assertEquals(this.testModel, this.listChangeEvent.getSource());
		assertEquals(LIST_NAME, this.listChangeEvent.getListName());
		assertEquals(targetIndex, this.listChangeEvent.getTargetIndex());
		assertEquals(sourceIndex, this.listChangeEvent.getSourceIndex());
	}

	private void verifyListChangeEvent(int index, Object item, Object replacedItem) {
		assertNotNull(this.listChangeEvent);
		assertEquals(this.testModel, this.listChangeEvent.getSource());
		assertEquals(LIST_NAME, this.listChangeEvent.getListName());
		assertEquals(index, this.listChangeEvent.getIndex());
		if (item == null) {
			assertFalse(this.listChangeEvent.items().hasNext());
		} else {
			assertEquals(item, this.listChangeEvent.items().next());
		}
		if (replacedItem == null) {
			assertFalse(this.listChangeEvent.replacedItems().hasNext());
		} else {
			assertEquals(replacedItem, this.listChangeEvent.replacedItems().next());
		}
	}


	// ********** tree change tests **********

	public void testFireNodeAddedTree() {
		this.treeChangeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.addTreeChangeListener(this);
		this.testModel.testFireNodeAddedTree();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.nodeAddedCalled);

		this.treeChangeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.removeTreeChangeListener(this);
		this.testModel.testFireNodeAddedTree();
		assertNull(this.treeChangeEvent);
		assertFalse(this.nodeAddedCalled);

		this.treeChangeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireNodeAddedTree();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.nodeAddedCalled);

		this.treeChangeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireNodeAddedTree();
		assertNull(this.treeChangeEvent);
		assertFalse(this.nodeAddedCalled);
	}

	public void testFireNodeAddedTreeEvent() {
		this.treeChangeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.addTreeChangeListener(this);
		this.testModel.testFireNodeAddedTreeEvent();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.nodeAddedCalled);

		this.treeChangeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.removeTreeChangeListener(this);
		this.testModel.testFireNodeAddedTreeEvent();
		assertNull(this.treeChangeEvent);
		assertFalse(this.nodeAddedCalled);

		this.treeChangeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireNodeAddedTreeEvent();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.nodeAddedCalled);

		this.treeChangeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireNodeAddedTreeEvent();
		assertNull(this.treeChangeEvent);
		assertFalse(this.nodeAddedCalled);
	}

	public void testFireNodeRemovedTreeEvent() {
		this.treeChangeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.addTreeChangeListener(this);
		this.testModel.testFireNodeRemovedTreeEvent();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.nodeRemovedCalled);

		this.treeChangeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.removeTreeChangeListener(this);
		this.testModel.testFireNodeRemovedTreeEvent();
		assertNull(this.treeChangeEvent);
		assertFalse(this.nodeRemovedCalled);

		this.treeChangeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireNodeRemovedTreeEvent();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.nodeRemovedCalled);

		this.treeChangeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireNodeRemovedTreeEvent();
		assertNull(this.treeChangeEvent);
		assertFalse(this.nodeRemovedCalled);
	}

	public void testFireNodeRemovedTree() {
		this.treeChangeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.addTreeChangeListener(this);
		this.testModel.testFireNodeRemovedTree();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.nodeRemovedCalled);

		this.treeChangeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.removeTreeChangeListener(this);
		this.testModel.testFireNodeRemovedTree();
		assertNull(this.treeChangeEvent);
		assertFalse(this.nodeRemovedCalled);

		this.treeChangeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireNodeRemovedTree();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.nodeRemovedCalled);

		this.treeChangeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireNodeRemovedTree();
		assertNull(this.treeChangeEvent);
		assertFalse(this.nodeRemovedCalled);
	}

	public void testFireTreeClearedEvent() {
		this.treeChangeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.addTreeChangeListener(this);
		this.testModel.testFireTreeClearedEvent();
		this.verifyTreeChangeEvent(EMPTY_PATH);
		assertTrue(this.treeClearedCalled);

		this.treeChangeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.removeTreeChangeListener(this);
		this.testModel.testFireTreeClearedEvent();
		assertNull(this.treeChangeEvent);
		assertFalse(this.treeClearedCalled);

		this.treeChangeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireTreeClearedEvent();
		this.verifyTreeChangeEvent(EMPTY_PATH);
		assertTrue(this.treeClearedCalled);

		this.treeChangeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireTreeClearedEvent();
		assertNull(this.treeChangeEvent);
		assertFalse(this.treeClearedCalled);
	}

	public void testFireTreeCleared() {
		this.treeChangeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.addTreeChangeListener(this);
		this.testModel.testFireTreeCleared();
		this.verifyTreeChangeEvent(EMPTY_PATH);
		assertTrue(this.treeClearedCalled);

		this.treeChangeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.removeTreeChangeListener(this);
		this.testModel.testFireTreeCleared();
		assertNull(this.treeChangeEvent);
		assertFalse(this.treeClearedCalled);

		this.treeChangeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireTreeCleared();
		this.verifyTreeChangeEvent(EMPTY_PATH);
		assertTrue(this.treeClearedCalled);

		this.treeChangeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireTreeCleared();
		assertNull(this.treeChangeEvent);
		assertFalse(this.treeClearedCalled);
	}

	public void testFireTreeChangedEvent() {
		this.treeChangeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.addTreeChangeListener(this);
		this.testModel.testFireTreeChangedEvent();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.treeChangedCalled);

		this.treeChangeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.removeTreeChangeListener(this);
		this.testModel.testFireTreeChangedEvent();
		assertNull(this.treeChangeEvent);
		assertFalse(this.treeChangedCalled);

		this.treeChangeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireTreeChangedEvent();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.treeChangedCalled);

		this.treeChangeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireTreeChangedEvent();
		assertNull(this.treeChangeEvent);
		assertFalse(this.treeChangedCalled);
	}

	public void testFireTreeChanged() {
		this.treeChangeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.addTreeChangeListener(this);
		this.testModel.testFireTreeChanged();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.treeChangedCalled);

		this.treeChangeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.removeTreeChangeListener(this);
		this.testModel.testFireTreeChanged();
		assertNull(this.treeChangeEvent);
		assertFalse(this.treeChangedCalled);

		this.treeChangeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireTreeChanged();
		this.verifyTreeChangeEvent(OBJECT_ARRAY_PATH);
		assertTrue(this.treeChangedCalled);

		this.treeChangeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this);
		this.testModel.testFireTreeChanged();
		assertNull(this.treeChangeEvent);
		assertFalse(this.treeChangedCalled);
	}

	public void testHasAnyTreeChangeListeners() {
		assertTrue(this.testModel.hasNoTreeChangeListeners(TREE_NAME));
		this.testModel.addTreeChangeListener(this);
		assertTrue(this.testModel.hasAnyTreeChangeListeners(TREE_NAME));
		this.testModel.removeTreeChangeListener(this);
		assertTrue(this.testModel.hasNoTreeChangeListeners(TREE_NAME));

		assertTrue(this.testModel.hasNoTreeChangeListeners(TREE_NAME));
		this.testModel.addTreeChangeListener(TREE_NAME, this);
		assertTrue(this.testModel.hasAnyTreeChangeListeners(TREE_NAME));
		this.testModel.removeTreeChangeListener(TREE_NAME, this);
		assertTrue(this.testModel.hasNoTreeChangeListeners(TREE_NAME));
	}

	public void testAddNullTreeListener() {
		boolean exCaught = false;
		try {
			this.testModel.addTreeChangeListener(null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testAddNullTreeListenerName() {
		boolean exCaught = false;
		try {
			this.testModel.addTreeChangeListener("foo", null);
		} catch (NullPointerException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveBogusTreeListener() {
		boolean exCaught = false;
		try {
			this.testModel.removeTreeChangeListener(this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener(this);
		exCaught = false;
		try {
			this.testModel.removeTreeChangeListener(this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addTreeChangeListener(this);
		exCaught = false;
		try {
			this.testModel.removeTreeChangeListener(new ChangeSupportTests("dummy"));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.testModel.removeTreeChangeListener(new TreeChangeAdapter());
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	public void testRemoveBogusTreeListenerName() {
		boolean exCaught = false;
		try {
			this.testModel.removeTreeChangeListener("foo", this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener("foo", this);
		exCaught = false;
		try {
			this.testModel.removeTreeChangeListener("foo", this);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addTreeChangeListener("foo", this);
		exCaught = false;
		try {
			this.testModel.removeTreeChangeListener("foo", new ChangeSupportTests("dummy"));
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		exCaught = false;
		try {
			this.testModel.removeTreeChangeListener("foo", new TreeChangeAdapter());
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
	}

	private void verifyTreeChangeEvent(Object[] path) {
		assertNotNull(this.treeChangeEvent);
		assertEquals(this.testModel, this.treeChangeEvent.getSource());
		assertEquals(TREE_NAME, this.treeChangeEvent.getTreeName());
		assertTrue(Arrays.equals(path, this.treeChangeEvent.getPath()));
	}


	// ********** convenience method tests **********

	public void testElementsAreEqual() {
		Collection<String> c1 = new ArrayList<String>();
		c1.add("foo");
		c1.add("bar");
		c1.add("baz");
		Collection<String> c2 = new ArrayList<String>();
		c2.add("foo");
		c2.add("bar");
		c2.add("baz");
		assertTrue(this.testModel.testElementsAreEqual(c1.iterator(), c2.iterator()));
	}

	public void testElementsAreDifferent() {
		Collection<String> c1 = new ArrayList<String>();
		c1.add("foo");
		c1.add("bar");
		c1.add("baz");
		Collection<String> c2 = new ArrayList<String>();
		c2.add("baz");
		c2.add("bar");
		c2.add("foo");
		assertTrue(this.testModel.testElementsAreDifferent(c1.iterator(), c2.iterator()));
	}


	// ********** AbstractModel tests **********

	public void testAbstractModelValuesAreEqual1() {
		assertTrue(this.testModel.testValuesAreEqual(null, null));
	}

	public void testAbstractModelValuesAreEqual2() {
		assertTrue(this.testModel.testValuesAreEqual("foo", "foo"));
	}

	public void testAbstractModelValuesAreEqual3() {
		assertFalse(this.testModel.testValuesAreEqual("foo", null));
	}

	public void testAbstractModelValuesAreEqual4() {
		assertFalse(this.testModel.testValuesAreEqual(null, "foo"));
	}

	public void testAbstractModelValuesAreEqual5() {
		assertFalse(this.testModel.testValuesAreEqual("bar", "foo"));
	}

	public void testAbstractModelValuesAreDifferent1() {
		assertFalse(this.testModel.testValuesAreDifferent(null, null));
	}

	public void testAbstractModelValuesAreDifferent2() {
		assertFalse(this.testModel.testValuesAreDifferent("foo", "foo"));
	}

	public void testAbstractModelValuesAreDifferent3() {
		assertTrue(this.testModel.testValuesAreDifferent("foo", null));
	}

	public void testAbstractModelValuesAreDifferent4() {
		assertTrue(this.testModel.testValuesAreDifferent(null, "foo"));
	}

	public void testAbstractModelValuesAreDifferent5() {
		assertTrue(this.testModel.testValuesAreDifferent("bar", "foo"));
	}

	public void testAbstractModelAttributeValueHasChanged1() {
		assertFalse(this.testModel.testAttributeValueHasChanged(null, null));
	}

	public void testAbstractModelAttributeValueHasChanged2() {
		assertFalse(this.testModel.testAttributeValueHasChanged("foo", "foo"));
	}

	public void testAbstractModelAttributeValueHasChanged3() {
		assertTrue(this.testModel.testAttributeValueHasChanged("foo", null));
	}

	public void testAbstractModelAttributeValueHasChanged4() {
		assertTrue(this.testModel.testAttributeValueHasChanged(null, "foo"));
	}

	public void testAbstractModelAttributeValueHasChanged5() {
		assertTrue(this.testModel.testAttributeValueHasChanged("bar", "foo"));
	}

	public void testAbstractModelAttributeValueHasNotChanged1() {
		assertTrue(this.testModel.testAttributeValueHasNotChanged(null, null));
	}

	public void testAbstractModelAttributeValueHasNotChanged2() {
		assertTrue(this.testModel.testAttributeValueHasNotChanged("foo", "foo"));
	}

	public void testAbstractModelAttributeValueHasNotChanged3() {
		assertFalse(this.testModel.testAttributeValueHasNotChanged("foo", null));
	}

	public void testAbstractModelAttributeValueHasNotChanged4() {
		assertFalse(this.testModel.testAttributeValueHasNotChanged(null, "foo"));
	}

	public void testAbstractModelAttributeValueHasNotChanged5() {
		assertFalse(this.testModel.testAttributeValueHasNotChanged("bar", "foo"));
	}

	public void testAbstractModelClone() {
		assertFalse(this.testModel.hasAnyPropertyChangeListeners(PROPERTY_NAME));
		this.testModel.addPropertyChangeListener(this);
		assertTrue(this.testModel.hasAnyPropertyChangeListeners(PROPERTY_NAME));

		// verify that the clone does not have any listeners
		TestModel clone = this.testModel.clone();
		assertFalse(clone.hasAnyPropertyChangeListeners(PROPERTY_NAME));
		clone.addPropertyChangeListener(this);
		assertTrue(clone.hasAnyPropertyChangeListeners(PROPERTY_NAME));
		// check original
		assertTrue(this.testModel.hasAnyPropertyChangeListeners(PROPERTY_NAME));

		// now test events fired by original
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.testFirePropertyChangedObjectObject();
		this.verifyPropertyChangeEvent(OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		// now test events fired by clone
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		clone.testFirePropertyChangedObjectObject();
		this.verifyPropertyChangeEvent(clone, OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);
	}

	public void testAbstractModelToString() {
		assertTrue(this.testModel.toString().contains('(' + TEST_TO_STRING + ')'));
	}


	// ********** listener implementations **********

	public void stateChanged(StateChangeEvent e) {
		this.stateChangedCalled = true;
		this.stateChangeEvent = e;
	}

	public void propertyChanged(PropertyChangeEvent e) {
		this.propertyChangeCalled = true;
		this.propertyChangeEvent = e;
	}

	public void itemsAdded(CollectionChangeEvent e) {
		this.itemsAddedCollectionCalled = true;
		this.collectionChangeEvent = e;
	}
	public void itemsRemoved(CollectionChangeEvent e) {
		this.itemsRemovedCollectionCalled = true;
		this.collectionChangeEvent = e;
	}
	public void collectionCleared(CollectionChangeEvent e) {
		this.collectionClearedCalled = true;
		this.collectionChangeEvent = e;
	}
	public void collectionChanged(CollectionChangeEvent e) {
		this.collectionChangedCalled = true;
		this.collectionChangeEvent = e;
	}

	public void itemsAdded(ListChangeEvent e) {
		this.itemsAddedListCalled = true;
		this.listChangeEvent = e;
	}
	public void itemsRemoved(ListChangeEvent e) {
		this.itemsRemovedListCalled = true;
		this.listChangeEvent = e;
	}
	public void itemsReplaced(ListChangeEvent e) {
		this.itemsReplacedListCalled = true;
		this.listChangeEvent = e;
	}
	public void itemsMoved(ListChangeEvent e) {
		this.itemsMovedListCalled = true;
		this.listChangeEvent = e;
	}
	public void listCleared(ListChangeEvent e) {
		this.listClearedCalled = true;
		this.listChangeEvent = e;
	}
	public void listChanged(ListChangeEvent e) {
		this.listChangedCalled = true;
		this.listChangeEvent = e;
	}

	public void nodeAdded(TreeChangeEvent e) {
		this.nodeAddedCalled = true;
		this.treeChangeEvent = e;
	}
	public void nodeRemoved(TreeChangeEvent e) {
		this.nodeRemovedCalled = true;
		this.treeChangeEvent = e;
	}
	public void treeCleared(TreeChangeEvent e) {
		this.treeClearedCalled = true;
		this.treeChangeEvent = e;
	}
	public void treeChanged(TreeChangeEvent e) {
		this.treeChangedCalled = true;
		this.treeChangeEvent = e;
	}


	// ********** inner class **********

	private static class TestModel extends AbstractModel implements Cloneable {
		TestModel() {
			super();
		}

		// ***** state
		public void testFireStateChange() {
			this.fireStateChanged();
		}

		// ***** property
		public void testFirePropertyChangedEvent() {
			this.firePropertyChanged(new PropertyChangeEvent(this, PROPERTY_NAME, OLD_OBJECT_VALUE, NEW_OBJECT_VALUE));
		}

		public void testFirePropertyChangedEventNoChange() {
			this.firePropertyChanged(new PropertyChangeEvent(this, PROPERTY_NAME, OLD_OBJECT_VALUE, OLD_OBJECT_VALUE));
		}

		public void testFirePropertyChangedObjectObject() {
			this.firePropertyChanged(PROPERTY_NAME, OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		}

		public void testFirePropertyChangedObjectObjectNoChange() {
			this.firePropertyChanged(PROPERTY_NAME, OLD_OBJECT_VALUE, OLD_OBJECT_VALUE);
		}

		public void testFirePropertyChangedObject() {
			this.firePropertyChanged(PROPERTY_NAME, NEW_OBJECT_VALUE);
		}

		public void testFirePropertyChangedObjectNoChange() {
			this.firePropertyChanged(PROPERTY_NAME, null);
		}

		public void testFirePropertyChangedIntInt() {
			this.firePropertyChanged(PROPERTY_NAME, OLD_INT_VALUE.intValue(), NEW_INT_VALUE.intValue());
		}

		public void testFirePropertyChangedIntIntNoChange() {
			this.firePropertyChanged(PROPERTY_NAME, OLD_INT_VALUE.intValue(), OLD_INT_VALUE.intValue());
		}

		public void testFirePropertyChangedBooleanBoolean() {
			this.firePropertyChanged(PROPERTY_NAME, OLD_BOOLEAN_VALUE.booleanValue(), NEW_BOOLEAN_VALUE.booleanValue());
		}

		public void testFirePropertyChangedBooleanBooleanNoChange() {
			this.firePropertyChanged(PROPERTY_NAME, OLD_BOOLEAN_VALUE.booleanValue(), OLD_BOOLEAN_VALUE.booleanValue());
		}

		// ***** collection
		public void testFireItemsAddedCollectionEvent() {
			this.fireItemsAdded(new CollectionChangeEvent(this, COLLECTION_NAME, Collections.singleton(ADDED_OBJECT_VALUE)));
		}

		public void testFireItemsAddedCollectionEventNoChange() {
			this.fireItemsAdded(new CollectionChangeEvent(this, COLLECTION_NAME, Collections.emptySet()));
		}

		public void testFireItemsAddedCollection() {
			this.fireItemsAdded(COLLECTION_NAME, Collections.singleton(ADDED_OBJECT_VALUE));
		}

		public void testFireItemsAddedCollectionNoChange() {
			this.fireItemsAdded(COLLECTION_NAME, Collections.emptySet());
		}

		public void testFireItemAddedCollection() {
			this.fireItemAdded(COLLECTION_NAME, ADDED_OBJECT_VALUE);
		}

		public void testFireItemsRemovedCollectionEvent() {
			this.fireItemsRemoved(new CollectionChangeEvent(this, COLLECTION_NAME, Collections.singleton(REMOVED_OBJECT_VALUE)));
		}

		public void testFireItemsRemovedCollectionEventNoChange() {
			this.fireItemsRemoved(new CollectionChangeEvent(this, COLLECTION_NAME, Collections.emptySet()));
		}

		public void testFireItemsRemovedCollection() {
			this.fireItemsRemoved(COLLECTION_NAME, Collections.singleton(REMOVED_OBJECT_VALUE));
		}

		public void testFireItemsRemovedCollectionNoChange() {
			this.fireItemsRemoved(COLLECTION_NAME, Collections.emptySet());
		}

		public void testFireItemRemovedCollection() {
			this.fireItemRemoved(COLLECTION_NAME, REMOVED_OBJECT_VALUE);
		}

		public void testFireCollectionCleared() {
			this.fireCollectionCleared(COLLECTION_NAME);
		}

		public void testFireCollectionChangedEvent() {
			this.fireCollectionChanged(new CollectionChangeEvent(this, COLLECTION_NAME));
		}

		public void testFireCollectionChanged() {
			this.fireCollectionChanged(COLLECTION_NAME);
		}

		public boolean testAddItemToCollection() {
			return this.addItemToCollection(ADDED_OBJECT_VALUE, new ArrayList<Object>(), COLLECTION_NAME);
		}

		public boolean testAddItemToCollectionNoChange() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(ADDED_OBJECT_VALUE);
			return this.addItemToCollection(ADDED_OBJECT_VALUE, collection, COLLECTION_NAME);
		}

		public boolean testAddItemsToCollection() {
			return this.addItemsToCollection(Collections.singleton(ADDED_OBJECT_VALUE), new ArrayList<Object>(), COLLECTION_NAME);
		}

		public boolean testAddItemsToCollectionNoChange() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(ADDED_OBJECT_VALUE);
			return this.addItemsToCollection(Collections.singleton(ADDED_OBJECT_VALUE), collection, COLLECTION_NAME);
		}

		public boolean testAddItemsToCollectionMixed() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(ADDED_OBJECT_VALUE);
			return this.addItemsToCollection(new Object[] {ADDED_OBJECT_VALUE, ADDED_OBJECT_VALUE_2}, collection, COLLECTION_NAME);
		}

		public boolean testRemoveItemFromCollection() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(REMOVED_OBJECT_VALUE);
			return this.removeItemFromCollection(REMOVED_OBJECT_VALUE, collection, COLLECTION_NAME);
		}

		public boolean testRemoveItemFromCollectionNoChange() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(REMOVED_OBJECT_VALUE);
			return this.removeItemFromCollection("foo", collection, COLLECTION_NAME);
		}

		public boolean testRemoveItemsFromCollection() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(REMOVED_OBJECT_VALUE);
			collection.add("foo");
			collection.add("bar");
			return this.removeItemsFromCollection(new Object[] {"foo", "bar", REMOVED_OBJECT_VALUE}, collection, COLLECTION_NAME);
		}

		public boolean testRemoveItemsFromCollectionNoChange1() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(REMOVED_OBJECT_VALUE);
			return this.removeItemsFromCollection(Collections.emptySet(), collection, COLLECTION_NAME);
		}

		public boolean testRemoveItemsFromCollectionNoChange2() {
			Collection<Object> collection = new HashSet<Object>();
			return this.removeItemsFromCollection(Collections.singleton("foo"), collection, COLLECTION_NAME);
		}

		public boolean testRemoveItemsFromCollectionNoChange3() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(REMOVED_OBJECT_VALUE);
			return this.removeItemsFromCollection(Collections.singleton("foo"), collection, COLLECTION_NAME);
		}

		public boolean testRetainItemsInCollection1() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(REMOVED_OBJECT_VALUE);
			collection.add("foo");
			collection.add("bar");
			return this.retainItemsInCollection(new Object[] {"foo", "bar"}, collection, COLLECTION_NAME);
		}

		public boolean testRetainItemsInCollection2() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(REMOVED_OBJECT_VALUE);
			collection.add("foo");
			collection.add("bar");
			return this.retainItemsInCollection(Collections.emptySet(), collection, COLLECTION_NAME);
		}

		public boolean testRetainItemsInCollectionNoChange1() {
			Collection<Object> collection = new HashSet<Object>();
			return this.retainItemsInCollection(new Object[] {"foo", "bar"}, collection, COLLECTION_NAME);
		}

		public boolean testRetainItemsInCollectionNoChange2() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(REMOVED_OBJECT_VALUE);
			collection.add("foo");
			collection.add("bar");
			return this.retainItemsInCollection(new Object[] {"foo", "bar", REMOVED_OBJECT_VALUE}, collection, COLLECTION_NAME);
		}

		public boolean testClearCollection() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add(REMOVED_OBJECT_VALUE);
			collection.add("foo");
			collection.add("bar");
			return this.clearCollection(collection, COLLECTION_NAME);
		}

		public boolean testClearCollectionNoChange() {
			Collection<Object> collection = new HashSet<Object>();
			return this.clearCollection(collection, COLLECTION_NAME);
		}

		public boolean testSynchronizeCollection1() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add("foo");
			collection.add("bar");
			collection.add("baz");
			Collection<Object> newCollection = new HashSet<Object>();
			newCollection.add("joo");
			newCollection.add("jar");
			newCollection.add("baz");
			boolean result = this.synchronizeCollection(newCollection, collection, COLLECTION_NAME);
			assertEquals(newCollection, collection);
			return result;
		}

		public boolean testSynchronizeCollection2() {
			Collection<Object> collection = new HashSet<Object>();
			collection.add("foo");
			collection.add("bar");
			collection.add("baz");
			Collection<Object> newCollection = new HashSet<Object>();
			boolean result = this.synchronizeCollection(newCollection, collection, COLLECTION_NAME);
			assertEquals(newCollection, collection);
			return result;
		}

		public boolean testSynchronizeCollection3() {
			Collection<Object> collection = new HashSet<Object>();
			Collection<Object> newCollection = new HashSet<Object>();
			newCollection.add("joo");
			newCollection.add("jar");
			newCollection.add("baz");
			boolean result = this.synchronizeCollection(newCollection, collection, COLLECTION_NAME);
			assertEquals(newCollection, collection);
			return result;
		}

		// ***** list
		public void testFireItemsAddedListEvent() {
			this.fireItemsAdded(new ListChangeEvent(this, LIST_NAME, ADD_INDEX, Collections.singletonList(ADDED_OBJECT_VALUE)));
		}

		public void testFireItemsAddedListEventNoChange() {
			this.fireItemsAdded(new ListChangeEvent(this, LIST_NAME, ADD_INDEX, Collections.emptyList()));
		}

		public void testFireItemsAddedList() {
			this.fireItemsAdded(LIST_NAME, ADD_INDEX, Collections.singletonList(ADDED_OBJECT_VALUE));
		}

		public void testFireItemsAddedListNoChange() {
			this.fireItemsAdded(LIST_NAME, ADD_INDEX, Collections.emptyList());
		}

		public void testFireItemAddedList() {
			this.fireItemAdded(LIST_NAME, ADD_INDEX, ADDED_OBJECT_VALUE);
		}

		public void testFireItemsRemovedListEvent() {
			this.fireItemsRemoved(new ListChangeEvent(this, LIST_NAME, REMOVE_INDEX, Collections.singletonList(REMOVED_OBJECT_VALUE)));
		}

		public void testFireItemsRemovedListEventNoChange() {
			this.fireItemsRemoved(new ListChangeEvent(this, LIST_NAME, REMOVE_INDEX, Collections.emptyList()));
		}

		public void testFireItemsRemovedList() {
			this.fireItemsRemoved(LIST_NAME, REMOVE_INDEX, Collections.singletonList(REMOVED_OBJECT_VALUE));
		}

		public void testFireItemsRemovedListNoChange() {
			this.fireItemsRemoved(LIST_NAME, REMOVE_INDEX, Collections.emptyList());
		}

		public void testFireItemRemovedList() {
			this.fireItemRemoved(LIST_NAME, REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		}

		public void testFireItemsReplacedListEvent() {
			this.fireItemsReplaced(new ListChangeEvent(this, LIST_NAME, REPLACE_INDEX, Collections.singletonList(ADDED_OBJECT_VALUE), Collections.singletonList(REMOVED_OBJECT_VALUE)));
		}

		public void testFireItemsReplacedListEventNoChange() {
			this.fireItemsReplaced(new ListChangeEvent(this, LIST_NAME, REPLACE_INDEX, Collections.emptyList(), Collections.emptyList()));
		}

		public void testFireItemsReplacedList() {
			this.fireItemsReplaced(LIST_NAME, REPLACE_INDEX, Collections.singletonList(ADDED_OBJECT_VALUE), Collections.singletonList(REMOVED_OBJECT_VALUE));
		}

		public void testFireItemsReplacedListNoChange() {
			this.fireItemsReplaced(LIST_NAME, REPLACE_INDEX, Collections.emptyList(), Collections.emptyList());
		}

		public void testFireItemReplacedList() {
			this.fireItemReplaced(LIST_NAME, REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		}

		public void testFireItemsMovedListEvent() {
			this.fireItemsMoved(new ListChangeEvent(this, LIST_NAME, TARGET_INDEX, SOURCE_INDEX, 1));
		}

		public void testFireItemsMovedListEventNoChange() {
			this.fireItemsMoved(new ListChangeEvent(this, LIST_NAME, SOURCE_INDEX, SOURCE_INDEX, 1));
		}

		public void testFireItemsMovedList() {
			this.fireItemsMoved(LIST_NAME, TARGET_INDEX, SOURCE_INDEX, 1);
		}

		public void testFireItemsMovedListNoChange() {
			this.fireItemsMoved(LIST_NAME, SOURCE_INDEX, SOURCE_INDEX, 1);
		}

		public void testFireItemMovedList() {
			this.fireItemMoved(LIST_NAME, TARGET_INDEX, SOURCE_INDEX);
		}

		public void testFireListClearedEvent() {
			this.fireListCleared(new ListChangeEvent(this, LIST_NAME));
		}

		public void testFireListCleared() {
			this.fireListCleared(LIST_NAME);
		}

		public void testFireListChangedEvent() {
			this.fireListChanged(new ListChangeEvent(this, LIST_NAME));
		}

		public void testFireListChanged() {
			this.fireListChanged(LIST_NAME);
		}

		public void testAddItemToListIndex() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.addItemToList(2, "joo", list, LIST_NAME);
		}

		public void testAddItemToList() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.addItemToList("joo", list, LIST_NAME);
		}

		public void testAddItemsToListIndex() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.addItemsToList(2, Collections.singletonList("joo"), list, LIST_NAME);
		}

		public void testAddItemsToListIndexNoChange() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.addItemsToList(2, Collections.<String>emptyList(), list, LIST_NAME);
		}

		public void testAddItemsToList() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.addItemsToList(Collections.singletonList("joo"), list, LIST_NAME);
		}

		public void testAddItemsToListNoChange() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.addItemsToList(Collections.<String>emptyList(), list, LIST_NAME);
		}

		// ***** tree
		public void testFireNodeAddedTreeEvent() {
			this.fireNodeAdded(new TreeChangeEvent(this, TREE_NAME, OBJECT_ARRAY_PATH));
		}

		public void testFireNodeAddedTree() {
			this.fireNodeAdded(TREE_NAME, OBJECT_ARRAY_PATH);
		}

		public void testFireNodeRemovedTreeEvent() {
			this.fireNodeRemoved(new TreeChangeEvent(this, TREE_NAME, OBJECT_ARRAY_PATH));
		}

		public void testFireNodeRemovedTree() {
			this.fireNodeRemoved(TREE_NAME, OBJECT_ARRAY_PATH);
		}

		public void testFireTreeClearedEvent() {
			this.fireTreeCleared(new TreeChangeEvent(this, TREE_NAME));
		}

		public void testFireTreeCleared() {
			this.fireTreeCleared(TREE_NAME);
		}

		public void testFireTreeChangedEvent() {
			this.fireTreeChanged(new TreeChangeEvent(this, TREE_NAME, OBJECT_ARRAY_PATH));
		}

		public void testFireTreeChanged() {
			this.fireTreeChanged(TREE_NAME, OBJECT_ARRAY_PATH);
		}

		public boolean testAttributeValueHasChanged(Object value1, Object value2) {
			return this.attributeValueHasChanged(value1, value2);
		}

		public boolean testAttributeValueHasNotChanged(Object value1, Object value2) {
			return this.attributeValueHasNotChanged(value1, value2);
		}

		// ***** misc
		@Override
		public TestModel clone() {
			try {
				return (TestModel) super.clone();
			} catch (CloneNotSupportedException ex) {
				throw new InternalError();
			}
		}

		public boolean testValuesAreDifferent(Object value1, Object value2) {
			return this.valuesAreDifferent(value1, value2);
		}

		public boolean testValuesAreEqual(Object value1, Object value2) {
			return this.valuesAreEqual(value1, value2);
		}

		public boolean testElementsAreDifferent(Iterator<?> iterator1, Iterator<?> iterator2) {
			return this.getChangeSupport().elementsAreDifferent(iterator1, iterator2);
		}

		public boolean testElementsAreEqual(Iterator<?> iterator1, Iterator<?> iterator2) {
			return this.getChangeSupport().elementsAreEqual(iterator1, iterator2);
		}

		@Override
		public void toString(StringBuilder sb) {
			sb.append(TEST_TO_STRING);
		}

	}


	// ********** serialization test **********

	public void testSerialization() throws java.io.IOException, ClassNotFoundException {
		LocalModel model1 = new LocalModel();
		Foo foo1 = new Foo();
		Bar bar1 = new Bar();
		Joo joo1 = new Joo();
		Jar jar1 = new Jar();
		model1.addStateChangeListener(foo1);
		model1.addStateChangeListener(bar1);
		model1.addListChangeListener(joo1);
		model1.addListChangeListener(jar1);

		ChangeListener[] listeners1 = this.listeners(model1, StateChangeListener.class);
		assertEquals(2, listeners1.length);
		// the order of these could change...
		assertEquals(Foo.class, listeners1[0].getClass());
		assertEquals(Bar.class, listeners1[1].getClass());

		listeners1 = this.listeners(model1, ListChangeListener.class);
		assertEquals(2, listeners1.length);
		// the order of these could change...
		assertEquals(Joo.class, listeners1[0].getClass());
		assertEquals(Jar.class, listeners1[1].getClass());

		LocalModel model2 = TestTools.serialize(model1);

		ChangeListener[] listeners2 = this.listeners(model2, StateChangeListener.class);
		assertEquals(1, listeners2.length);
		assertEquals(Foo.class, listeners2[0].getClass());

		listeners2 = this.listeners(model2, ListChangeListener.class);
		assertEquals(1, listeners2.length);
		assertEquals(Joo.class, listeners2[0].getClass());
	}

	private ChangeListener[] listeners(LocalModel model, Class<? extends ChangeListener> listenerClass) {
		ChangeSupport changeSupport = (ChangeSupport) ClassTools.fieldValue(model, "changeSupport");
		return (ChangeListener[]) ClassTools.executeMethod(changeSupport, "getListeners", Class.class, listenerClass);
	}

	private static class LocalModel extends AbstractModel {
		LocalModel() {
			super();
		}
	}

	private static class Foo implements Serializable, StateChangeListener {
		Foo() {
			super();
		}
		public void stateChanged(StateChangeEvent event) {
			// do nothing
		}
	}

	private static class Bar implements StateChangeListener {
		Bar() {
			super();
		}
		public void stateChanged(StateChangeEvent event) {
			// do nothing
		}
	}

	private static class Joo extends ListChangeAdapter implements Serializable {
//		private static final ObjectStreamField[] serialPersistentFields = {new ObjectStreamField("changeSupport", ChangeSupport.class)};
		Joo() {
			super();
		}
	}

	private static class Jar extends ListChangeAdapter {
		Jar() {
			super();
		}
	}


	// ********** bug(?) test **********

	private static final String ISE_MESSAGE = "this object is no longer listening to localA";

	/**
	 * Test the following situation:
	 * 	- both B and C are listening to A
	 * 	- C is also listening to B
	 * 	- when B receives an event from A, it will fire an event to C
	 * 	- when C receives an event from B, it will STOP listening to A
	 * 	- the event from B to C may be preceded or followed (depending on
	 * 		the hash positions of listeners) by an event from A to C:
	 * 		- if the A to C event comes first, no problem
	 * 		- but if the A to B event comes first, the A to C event should NOT happen
	 */
	public void testIndirectRemoveStateListener() {
		this.verifyIndirectRemoveListener(
			new NotifyCommand() {
				public void notifyListeners(LocalA localA) {
					localA.notifyStateListeners();
				}
			}
		);
	}

	public void testIndirectRemovePropertyListener() {
		this.verifyIndirectRemoveListener(
			new NotifyCommand() {
				public void notifyListeners(LocalA localA) {
					localA.notifyPropertyListeners();
				}
			}
		);
	}

	public void testIndirectRemoveCollectionListener() {
		this.verifyIndirectRemoveListener(
			new NotifyCommand() {
				public void notifyListeners(LocalA localA) {
					localA.notifyCollectionListeners();
				}
			}
		);
	}

	public void testIndirectRemoveListListener() {
		this.verifyIndirectRemoveListener(
			new NotifyCommand() {
				public void notifyListeners(LocalA localA) {
					localA.notifyListListeners();
				}
			}
		);
	}

	public void testIndirectRemoveTreeListener() {
		this.verifyIndirectRemoveListener(
			new NotifyCommand() {
				public void notifyListeners(LocalA localA) {
					localA.notifyTreeListeners();
				}
			}
		);
	}

	public void verifyIndirectRemoveListener(NotifyCommand command) {
		LocalA localA = new LocalA();
		LocalB localB = new LocalB(localA);

		// build a bunch of LocalCs so at least one of them is notified AFTER the LocalB;
		// using 1000 seemed to fail very consistently before ChangeSupport was fixed
		LocalC[] localCs = new LocalC[1000];
		for (int i = localCs.length; i-- > 0; ) {
			localCs[i] = new LocalC(localA, localB);
		}

		boolean exCaught = false;
		try {
			command.notifyListeners(localA);
		} catch (IllegalStateException ex) {
			if (ex.getMessage() == ISE_MESSAGE) {
				exCaught = true;
			} else {
				throw ex;
			}
		}
		assertFalse(exCaught);

		for (int i = localCs.length; i-- > 0; ) {
			assertFalse(localCs[i].isListeningToLocalA());
		}
	}

	private interface NotifyCommand {
		void notifyListeners(LocalA localA);
	}

	/**
	 * This object simply fires a state change event. Both LocalB and LocalC
	 * will be listeners.
	 */
	private static class LocalA extends AbstractModel {
		LocalA() {
			super();
		}
		void notifyStateListeners() {
			this.fireStateChanged();
		}
		void notifyPropertyListeners() {
			this.firePropertyChanged("foo", 1, 2);
		}
		void notifyCollectionListeners() {
			this.fireCollectionChanged("foo");
		}
		void notifyListListeners() {
			this.fireListChanged("foo");
		}
		void notifyTreeListeners() {
			this.fireTreeChanged("foo");
		}
	}

	/**
	 * This object will fire state change events whenever it receives
	 * a state change event from localA.
	 */
	private static class LocalB
		extends AbstractModel
		implements StateChangeListener, PropertyChangeListener, CollectionChangeListener, ListChangeListener, TreeChangeListener
	{
		LocalB(LocalA localA) {
			super();
			localA.addStateChangeListener(this);
			localA.addPropertyChangeListener(this);
			localA.addCollectionChangeListener(this);
			localA.addListChangeListener(this);
			localA.addTreeChangeListener(this);
		}

		public void stateChanged(StateChangeEvent e) {
			this.fireStateChanged();
		}

		public void propertyChanged(PropertyChangeEvent evt) {
			this.firePropertyChanged("bar", 1, 2);
		}

		public void collectionChanged(CollectionChangeEvent e) {
			this.fireCollectionChanged("bar");
		}
		public void collectionCleared(CollectionChangeEvent e) {/*ignore*/}
		public void itemsAdded(CollectionChangeEvent e) {/*ignore*/}
		public void itemsRemoved(CollectionChangeEvent e) {/*ignore*/}

		public void listChanged(ListChangeEvent e) {
			this.fireListChanged("bar");
		}
		public void listCleared(ListChangeEvent e) {/*ignore*/}
		public void itemsAdded(ListChangeEvent e) {/*ignore*/}
		public void itemsRemoved(ListChangeEvent e) {/*ignore*/}
		public void itemsReplaced(ListChangeEvent e) {/*ignore*/}
		public void itemsMoved(ListChangeEvent e) {/*ignore*/}

		public void treeChanged(TreeChangeEvent e) {
			this.fireTreeChanged("bar");
		}
		public void treeCleared(TreeChangeEvent e) {/*ignore*/}
		public void nodeAdded(TreeChangeEvent e) {/*ignore*/}
		public void nodeRemoved(TreeChangeEvent e) {/*ignore*/}

	}

	/**
	 * This object will listen to two other objects, localA and localB.
	 * If this object receives notification from localB, it will stop listening to
	 * localA. If this object receives notification from localA, it will check to
	 * see whether it still listening to localA. If this object is no longer
	 * listening to localA, it will complain about receiving the event and
	 * throw an exception.
	 */
	private static class LocalC
		extends AbstractModel
		implements StateChangeListener, PropertyChangeListener, CollectionChangeListener, ListChangeListener, TreeChangeListener
	{
		private LocalA localA;
		private LocalB localB;
		private boolean listeningToLocalA;

		LocalC(LocalA localA, LocalB localB) {
			super();
			this.localA = localA;
			this.localB = localB;

			localA.addStateChangeListener(this);
			localA.addPropertyChangeListener(this);
			localA.addCollectionChangeListener(this);
			localA.addListChangeListener(this);
			localA.addTreeChangeListener(this);
			this.listeningToLocalA = true;

			localB.addStateChangeListener(this);
			localB.addPropertyChangeListener(this);
			localB.addCollectionChangeListener(this);
			localB.addListChangeListener(this);
			localB.addTreeChangeListener(this);
		}
		boolean isListeningToLocalA() {
			return this.listeningToLocalA;
		}

		public void stateChanged(StateChangeEvent e) {
			Object source = e.getSource();
			if (source == this.localA) {
				if ( ! this.listeningToLocalA) {
					throw new IllegalStateException(ISE_MESSAGE);
				}
			} else if (source == this.localB) {
				this.localA.removeStateChangeListener(this);
				this.listeningToLocalA = false;
			} else {
				throw new IllegalStateException("bogus event source: " + source);
			}
		}

		public void propertyChanged(PropertyChangeEvent e) {
			Object source = e.getSource();
			if (source == this.localA) {
				if ( ! this.listeningToLocalA) {
					throw new IllegalStateException(ISE_MESSAGE);
				}
			} else if (source == this.localB) {
				this.localA.removePropertyChangeListener(this);
				this.listeningToLocalA = false;
			} else {
				throw new IllegalStateException("bogus event source: " + source);
			}
		}

		public void collectionChanged(CollectionChangeEvent e) {
			Object source = e.getSource();
			if (source == this.localA) {
				if ( ! this.listeningToLocalA) {
					throw new IllegalStateException(ISE_MESSAGE);
				}
			} else if (source == this.localB) {
				this.localA.removeCollectionChangeListener(this);
				this.listeningToLocalA = false;
			} else {
				throw new IllegalStateException("bogus event source: " + source);
			}
		}
		public void collectionCleared(CollectionChangeEvent e) {/*ignore*/}
		public void itemsAdded(CollectionChangeEvent e) {/*ignore*/}
		public void itemsRemoved(CollectionChangeEvent e) {/*ignore*/}

		public void listChanged(ListChangeEvent e) {
			Object source = e.getSource();
			if (source == this.localA) {
				if ( ! this.listeningToLocalA) {
					throw new IllegalStateException(ISE_MESSAGE);
				}
			} else if (source == this.localB) {
				this.localA.removeListChangeListener(this);
				this.listeningToLocalA = false;
			} else {
				throw new IllegalStateException("bogus event source: " + source);
			}
		}
		public void listCleared(ListChangeEvent e) {/*ignore*/}
		public void itemsAdded(ListChangeEvent e) {/*ignore*/}
		public void itemsRemoved(ListChangeEvent e) {/*ignore*/}
		public void itemsReplaced(ListChangeEvent e) {/*ignore*/}
		public void itemsMoved(ListChangeEvent e) {/*ignore*/}

		public void treeChanged(TreeChangeEvent e) {
			Object source = e.getSource();
			if (source == this.localA) {
				if ( ! this.listeningToLocalA) {
					throw new IllegalStateException(ISE_MESSAGE);
				}
			} else if (source == this.localB) {
				this.localA.removeTreeChangeListener(this);
				this.listeningToLocalA = false;
			} else {
				throw new IllegalStateException("bogus event source: " + source);
			}
		}
		public void treeCleared(TreeChangeEvent e) {/*ignore*/}
		public void nodeAdded(TreeChangeEvent e) {/*ignore*/}
		public void nodeRemoved(TreeChangeEvent e) {/*ignore*/}

	}

}
