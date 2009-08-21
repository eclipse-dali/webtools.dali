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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.ChangeSupport;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.utility.model.event.CollectionEvent;
import org.eclipse.jpt.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListAddEvent;
import org.eclipse.jpt.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.model.event.ListClearEvent;
import org.eclipse.jpt.utility.model.event.ListEvent;
import org.eclipse.jpt.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeAddEvent;
import org.eclipse.jpt.utility.model.event.TreeChangeEvent;
import org.eclipse.jpt.utility.model.event.TreeClearEvent;
import org.eclipse.jpt.utility.model.event.TreeEvent;
import org.eclipse.jpt.utility.model.event.TreeRemoveEvent;
import org.eclipse.jpt.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.CollectionChangeAdapter;
import org.eclipse.jpt.utility.model.listener.ListChangeAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.utility.model.listener.StateChangeAdapter;
import org.eclipse.jpt.utility.model.listener.TreeChangeAdapter;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class ChangeSupportTests
	extends TestCase
{
	TestModel testModel;
	static final String TEST_TO_STRING = "this is a test";

	ChangeListener changeListener = new Adapter();

	StateChangeEvent stateChangeEvent;
	boolean stateChangedCalled = false;

	PropertyChangeEvent propertyChangeEvent;
	boolean propertyChangeCalled = false;
	static final String PROPERTY_NAME = "propertyName";
	static final Object OLD_OBJECT_VALUE = new Object();
	static final Object NEW_OBJECT_VALUE = new Object();
	static final Integer OLD_INT_VALUE = new Integer(27);
	static final Boolean OLD_BOOLEAN_VALUE = Boolean.TRUE;
	static final Integer NEW_INT_VALUE = new Integer(42);
	static final Boolean NEW_BOOLEAN_VALUE = Boolean.FALSE;

	CollectionEvent collectionEvent;
	boolean itemsAddedCollectionCalled = false;
	boolean itemsRemovedCollectionCalled = false;
	boolean collectionChangedCalled = false;
	boolean collectionClearedCalled = false;
	static final String COLLECTION_NAME = "collectionName";
	static final Object ADDED_OBJECT_VALUE = new Object();
	static final Object ADDED_OBJECT_VALUE_2 = new Object();
	static final Object REMOVED_OBJECT_VALUE = new Object();
	static final int TARGET_INDEX = 7;
	static final int SOURCE_INDEX = 22;

	ListEvent listEvent;
	boolean itemsAddedListCalled = false;
	boolean itemsRemovedListCalled = false;
	boolean itemsReplacedListCalled = false;
	boolean itemsMovedListCalled = false;
	boolean listChangedCalled = false;
	boolean listClearedCalled = false;
	static final String LIST_NAME = "listName";
	static final int ADD_INDEX = 3;
	static final int REMOVE_INDEX = 5;
	static final int REPLACE_INDEX = 2;

	TreeEvent treeEvent;
	boolean nodeAddedCalled = false;
	boolean nodeRemovedCalled = false;
	boolean treeChangedCalled = false;
	boolean treeClearedCalled = false;
	static final String TREE_NAME = "treeName";
	static final List<Object> OBJECT_PATH = Arrays.asList(new Object[] {new Object(), new Object(), new String()});


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
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireStateChange();
		assertNotNull(this.stateChangeEvent);
		assertEquals(this.testModel, this.stateChangeEvent.getSource());
		assertTrue(this.stateChangedCalled);
	}

	public void testHasAnyStateChangeListeners() {
		assertTrue(this.testModel.hasNoStateChangeListeners());
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.hasAnyStateChangeListeners());
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.hasNoStateChangeListeners());
	}

	public void testHasAnyStateChangeListenersDuplicate() {
		assertTrue(this.testModel.hasNoStateChangeListeners());
		this.testModel.addChangeListener(this.changeListener);
		boolean exCaught = false;
		try {
			this.testModel.addChangeListener(this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);
		assertTrue(this.testModel.hasAnyStateChangeListeners());
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.hasNoStateChangeListeners());

		exCaught = false;
		try {
			this.testModel.removeChangeListener(this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
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
			this.testModel.removeChangeListener(this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener("foo", this.changeListener);
		exCaught = false;
		try {
			this.testModel.removeChangeListener(this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addChangeListener(this.changeListener);
		exCaught = false;
		try {
			this.testModel.removeStateChangeListener(new Adapter());
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
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedEvent();
		this.verifyPropertyChangeEvent(OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedEvent();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedEvent();
		this.verifyPropertyChangeEvent(OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedEvent();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedEventNoChange() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedEventNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedEventNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedEventNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedEventNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedObjectObject() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedObjectObject();
		this.verifyPropertyChangeEvent(OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedObjectObject();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedObjectObject();
		this.verifyPropertyChangeEvent(OLD_OBJECT_VALUE, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedObjectObject();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedObjectObjectNoChange() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedObjectObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedObjectObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedObjectObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedObjectObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedObject() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedObject();
		this.verifyPropertyChangeEvent(null, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedObject();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedObject();
		this.verifyPropertyChangeEvent(null, NEW_OBJECT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedObject();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedObjectNoChange() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedObjectNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedIntInt() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedIntInt();
		this.verifyPropertyChangeEvent(OLD_INT_VALUE, NEW_INT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedIntInt();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedIntInt();
		this.verifyPropertyChangeEvent(OLD_INT_VALUE, NEW_INT_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedIntInt();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedIntIntNoChange() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedIntIntNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedIntIntNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedIntIntNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedIntIntNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedBooleanBoolean() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedBooleanBoolean();
		this.verifyPropertyChangeEvent(OLD_BOOLEAN_VALUE, NEW_BOOLEAN_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedBooleanBoolean();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedBooleanBoolean();
		this.verifyPropertyChangeEvent(OLD_BOOLEAN_VALUE, NEW_BOOLEAN_VALUE);
		assertTrue(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedBooleanBoolean();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testFirePropertyChangedBooleanBooleanNoChange() {
		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedBooleanBooleanNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFirePropertyChangedBooleanBooleanNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedBooleanBooleanNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);

		this.propertyChangeEvent = null;
		this.propertyChangeCalled = false;
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		this.testModel.testFirePropertyChangedBooleanBooleanNoChange();
		assertNull(this.propertyChangeEvent);
		assertFalse(this.propertyChangeCalled);
	}

	public void testHasAnyPropertyChangeListeners() {
		assertTrue(this.testModel.hasNoPropertyChangeListeners(PROPERTY_NAME));
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.hasAnyPropertyChangeListeners(PROPERTY_NAME));
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.hasNoPropertyChangeListeners(PROPERTY_NAME));

		assertTrue(this.testModel.hasNoPropertyChangeListeners(PROPERTY_NAME));
		this.testModel.addPropertyChangeListener(PROPERTY_NAME, this.changeListener);
		assertTrue(this.testModel.hasAnyPropertyChangeListeners(PROPERTY_NAME));
		this.testModel.removePropertyChangeListener(PROPERTY_NAME, this.changeListener);
		assertTrue(this.testModel.hasNoPropertyChangeListeners(PROPERTY_NAME));
	}

	public void testAddNullPropertyListener() {
		boolean exCaught = false;
		try {
			this.testModel.addChangeListener(null);
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
			this.testModel.removePropertyChangeListener("foo", new PropertyChangeAdapter());
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addCollectionChangeListener("foo", this.changeListener);
		exCaught = false;
		try {
			this.testModel.removePropertyChangeListener("foo", this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener("foo", this.changeListener);
		exCaught = false;
		try {
			this.testModel.removePropertyChangeListener("foo", new PropertyChangeAdapter());
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
		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedCollectionEvent();
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedCollectionEvent();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsAddedCollectionEvent();
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsAddedCollectionEvent();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testFireItemsAddedCollectionEventNoChange() {
		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedCollectionEventNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedCollectionEventNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsAddedCollectionEventNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsAddedCollectionEventNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testFireItemsAddedCollection() {
		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedCollection();
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedCollection();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsAddedCollection();
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsAddedCollection();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testFireItemsAddedCollectionNoChange() {
		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedCollectionNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedCollectionNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsAddedCollectionNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsAddedCollectionNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testFireItemAddedCollection() {
		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemAddedCollection();
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemAddedCollection();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemAddedCollection();
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemAddedCollection();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testFireItemsRemovedCollectionEvent() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedCollectionEvent();
		this.verifyCollectionEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedCollectionEvent();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedCollectionEvent();
		this.verifyCollectionEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedCollectionEvent();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testFireItemsRemovedCollectionEventNoChange() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedCollectionEventNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedCollectionEventNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedCollectionEventNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedCollectionEventNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testFireItemsRemovedCollection() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedCollection();
		this.verifyCollectionEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedCollection();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedCollection();
		this.verifyCollectionEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedCollection();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testFireItemsRemovedCollectionNoChange() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedCollectionNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedCollectionNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedCollectionNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedCollectionNoChange();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testFireItemRemovedCollection() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemRemovedCollection();
		this.verifyCollectionEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemRemovedCollection();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemRemovedCollection();
		this.verifyCollectionEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireItemRemovedCollection();
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testFireCollectionCleared() {
		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireCollectionCleared();
		this.verifyCollectionEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireCollectionCleared();
		assertNull(this.collectionEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireCollectionCleared();
		this.verifyCollectionEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireCollectionCleared();
		assertNull(this.collectionEvent);
		assertFalse(this.collectionClearedCalled);
	}

	public void testFireCollectionChangedEvent() {
		this.collectionEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireCollectionChangedEvent();
		this.verifyCollectionEvent(null);
		assertTrue(this.collectionChangedCalled);

		this.collectionEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireCollectionChangedEvent();
		assertNull(this.collectionEvent);
		assertFalse(this.collectionChangedCalled);

		this.collectionEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireCollectionChangedEvent();
		this.verifyCollectionEvent(null);
		assertTrue(this.collectionChangedCalled);

		this.collectionEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireCollectionChangedEvent();
		assertNull(this.collectionEvent);
		assertFalse(this.collectionChangedCalled);
	}

	public void testFireCollectionChanged() {
		this.collectionEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireCollectionChanged();
		this.verifyCollectionEvent(null);
		assertTrue(this.collectionChangedCalled);

		this.collectionEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireCollectionChanged();
		assertNull(this.collectionEvent);
		assertFalse(this.collectionChangedCalled);

		this.collectionEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireCollectionChanged();
		this.verifyCollectionEvent(null);
		assertTrue(this.collectionChangedCalled);

		this.collectionEvent = null;
		this.collectionChangedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		this.testModel.testFireCollectionChanged();
		assertNull(this.collectionEvent);
		assertFalse(this.collectionChangedCalled);
	}

	public void testAddItemToCollection() {
		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.testAddItemToCollection());
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.testAddItemToCollection());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testAddItemToCollection());
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testAddItemToCollection());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testAddItemToCollectionNoChange() {
		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertFalse(this.testModel.testAddItemToCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertFalse(this.testModel.testAddItemToCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testAddItemToCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testAddItemToCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testAddItemsToCollection() {
		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.testAddItemsToCollection());
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.testAddItemsToCollection());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testAddItemsToCollection());
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testAddItemsToCollection());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testAddItemsToCollectionNoChange() {
		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertFalse(this.testModel.testAddItemsToCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertFalse(this.testModel.testAddItemsToCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testAddItemsToCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testAddItemsToCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testAddItemsToCollectionMixed() {
		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.testAddItemsToCollectionMixed());
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE_2);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.testAddItemsToCollectionMixed());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testAddItemsToCollectionMixed());
		this.verifyCollectionEvent(ADDED_OBJECT_VALUE_2);
		assertTrue(this.itemsAddedCollectionCalled);

		this.collectionEvent = null;
		this.itemsAddedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testAddItemsToCollectionMixed());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsAddedCollectionCalled);
	}

	public void testRemoveItemFromCollection() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.testRemoveItemFromCollection());
		this.verifyCollectionEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.testRemoveItemFromCollection());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testRemoveItemFromCollection());
		this.verifyCollectionEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testRemoveItemFromCollection());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRemoveItemFromCollectionNoChange() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertFalse(this.testModel.testRemoveItemFromCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertFalse(this.testModel.testRemoveItemFromCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRemoveItemFromCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRemoveItemFromCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRemoveItemsFromCollection() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.testRemoveItemsFromCollection());
		this.verifyCollectionChangeEvent2(REMOVED_OBJECT_VALUE, "foo", "bar");
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.testRemoveItemsFromCollection());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testRemoveItemsFromCollection());
		this.verifyCollectionChangeEvent2(REMOVED_OBJECT_VALUE, "foo", "bar");
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testRemoveItemsFromCollection());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRemoveItemsFromCollectionNoChange1() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange1());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange1());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange1());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange1());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRemoveItemsFromCollectionNoChange2() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange2());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange2());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange2());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange2());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRemoveItemsFromCollectionNoChange3() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange3());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange3());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange3());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRemoveItemsFromCollectionNoChange3());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRetainItemsInCollection1() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.testRetainItemsInCollection1());
		this.verifyCollectionEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.testRetainItemsInCollection1());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testRetainItemsInCollection1());
		this.verifyCollectionEvent(REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testRetainItemsInCollection1());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	// collection cleared...
	public void testRetainItemsInCollection2() {
		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.testRetainItemsInCollection2());
		this.verifyCollectionEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.testRetainItemsInCollection2());
		assertNull(this.collectionEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testRetainItemsInCollection2());
		this.verifyCollectionEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testRetainItemsInCollection2());
		assertNull(this.collectionEvent);
		assertFalse(this.collectionClearedCalled);
	}

	public void testRetainItemsInCollectionNoChange1() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange1());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange1());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange1());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange1());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testRetainItemsInCollectionNoChange2() {
		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange2());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange2());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange2());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);

		this.collectionEvent = null;
		this.itemsRemovedCollectionCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testRetainItemsInCollectionNoChange2());
		assertNull(this.collectionEvent);
		assertFalse(this.itemsRemovedCollectionCalled);
	}

	public void testClearCollection() {
		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.testClearCollection());
		this.verifyCollectionEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.testClearCollection());
		assertNull(this.collectionEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testClearCollection());
		this.verifyCollectionEvent(null);
		assertTrue(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.testClearCollection());
		assertNull(this.collectionEvent);
		assertFalse(this.collectionClearedCalled);
	}

	public void testClearCollectionNoChange() {
		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		assertFalse(this.testModel.testClearCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		assertFalse(this.testModel.testClearCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testClearCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.collectionClearedCalled);

		this.collectionEvent = null;
		this.collectionClearedCalled = false;
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertFalse(this.testModel.testClearCollectionNoChange());
		assertNull(this.collectionEvent);
		assertFalse(this.collectionClearedCalled);
	}

	public void testSynchronizeCollection1() {
		CollectionSynchListener csl = new CollectionSynchListener();
		this.testModel.addChangeListener(csl);
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
		this.testModel.addChangeListener(csl);
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
		this.testModel.addChangeListener(csl);
		assertTrue(this.testModel.testSynchronizeCollection3());
		assertTrue(csl.itemsAdded);
		assertFalse(csl.itemsRemoved);
		assertFalse(csl.collectionChanged);
		assertFalse(csl.collectionCleared);
		assertEquals(3, csl.addedItems.size());
		assertTrue(CollectionTools.containsAll(csl.addedItems, new Object[] {"joo", "jar", "baz"}));
		assertTrue(csl.removedItems.isEmpty());
	}

	class CollectionSynchListener extends ChangeAdapter {
		boolean itemsAdded = false;
		boolean itemsRemoved = false;
		boolean collectionChanged = false;
		boolean collectionCleared = false;
		Collection<Object> addedItems = new ArrayList<Object>();
		Collection<Object> removedItems = new ArrayList<Object>();
		@Override
		public void collectionChanged(CollectionChangeEvent event) {
			this.collectionChanged = true;
		}
		@Override
		public void collectionCleared(CollectionClearEvent event) {
			this.collectionCleared = true;
		}
		@Override
		public void itemsAdded(CollectionAddEvent event) {
			this.itemsAdded = true;
			CollectionTools.addAll(this.addedItems, event.getItems());
		}
		@Override
		public void itemsRemoved(CollectionRemoveEvent event) {
			this.itemsRemoved = true;
			CollectionTools.addAll(this.removedItems, event.getItems());
		}
	}

	public void testHasAnyCollectionChangeListeners() {
		assertTrue(this.testModel.hasNoCollectionChangeListeners(COLLECTION_NAME));
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.hasAnyCollectionChangeListeners(COLLECTION_NAME));
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.hasNoCollectionChangeListeners(COLLECTION_NAME));

		assertTrue(this.testModel.hasNoCollectionChangeListeners(COLLECTION_NAME));
		this.testModel.addCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.hasAnyCollectionChangeListeners(COLLECTION_NAME));
		this.testModel.removeCollectionChangeListener(COLLECTION_NAME, this.changeListener);
		assertTrue(this.testModel.hasNoCollectionChangeListeners(COLLECTION_NAME));
	}

	public void testAddNullCollectionListener() {
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
			this.testModel.removeCollectionChangeListener("foo", this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener("foo", this.changeListener);
		exCaught = false;
		try {
			this.testModel.removeCollectionChangeListener("foo", this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addCollectionChangeListener("foo", this.changeListener);
		exCaught = false;
		try {
			this.testModel.removeCollectionChangeListener("foo", new CollectionChangeAdapter());
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

	private void verifyCollectionEvent(Object item) {
		assertNotNull(this.collectionEvent);
		assertEquals(this.testModel, this.collectionEvent.getSource());
		assertEquals(COLLECTION_NAME, this.collectionEvent.getCollectionName());
		if (item != null) {
			assertEquals(item, this.getCollectionEventItems().iterator().next());
		}
	}

	private Iterable<?> getCollectionEventItems() {
		if (this.collectionEvent instanceof CollectionAddEvent) {
			return ((CollectionAddEvent) this.collectionEvent).getItems();
		} else if (this.collectionEvent instanceof CollectionRemoveEvent) {
			return ((CollectionRemoveEvent) this.collectionEvent).getItems();
		}
		throw new IllegalStateException();
	}

	private void verifyCollectionChangeEvent2(Object... items) {
		assertNotNull(this.collectionEvent);
		assertEquals(this.testModel, this.collectionEvent.getSource());
		assertEquals(COLLECTION_NAME, this.collectionEvent.getCollectionName());
		assertEquals(items.length, this.getCollectionEventItemsSize());
		for (Object item : items) {
			assertTrue(CollectionTools.contains(this.getCollectionEventItems(), item));
		}
	}

	private int getCollectionEventItemsSize() {
		if (this.collectionEvent instanceof CollectionAddEvent) {
			return ((CollectionAddEvent) this.collectionEvent).getItemsSize();
		} else if (this.collectionEvent instanceof CollectionRemoveEvent) {
			return ((CollectionRemoveEvent) this.collectionEvent).getItemsSize();
		}
		throw new IllegalStateException();
	}


	// ********** list change tests **********

	public void testFireItemsAddedListEvent() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedListEvent();
		this.verifyListAddEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedListEvent();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsAddedListEvent();
		this.verifyListAddEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsAddedListEvent();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testFireItemsAddedListEventNoChange() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsAddedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsAddedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testFireItemsAddedList() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedList();
		this.verifyListAddEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsAddedList();
		this.verifyListAddEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsAddedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testFireItemsAddedListNoChange() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsAddedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsAddedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsAddedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testFireItemAddedList() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemAddedList();
		this.verifyListAddEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemAddedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemAddedList();
		this.verifyListAddEvent(ADD_INDEX, ADDED_OBJECT_VALUE);
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemAddedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testFireItemsRemovedListEvent() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedListEvent();
		this.verifyListRemoveEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedListEvent();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedListEvent();
		this.verifyListRemoveEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedListEvent();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testFireItemsRemovedListEventNoChange() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testFireItemsRemovedList() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedList();
		this.verifyListRemoveEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedList();
		this.verifyListRemoveEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testFireItemsRemovedListNoChange() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsRemovedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsRemovedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testFireItemRemovedList() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemRemovedList();
		this.verifyListRemoveEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemRemovedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemRemovedList();
		this.verifyListRemoveEvent(REMOVE_INDEX, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemRemovedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testFireItemsReplacedListEvent() {
		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsReplacedListEvent();
		this.verifyListReplaceEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsReplacedListEvent();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsReplacedListEvent();
		this.verifyListReplaceEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsReplacedListEvent();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testFireItemsReplacedListEventNoChange() {
		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsReplacedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsReplacedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsReplacedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsReplacedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testFireItemsReplacedList() {
		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsReplacedList();
		this.verifyListReplaceEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsReplacedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsReplacedList();
		this.verifyListReplaceEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsReplacedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testFireItemsReplacedListNoChange() {
		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsReplacedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsReplacedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsReplacedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsReplacedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testFireItemReplacedList() {
		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemReplacedList();
		this.verifyListReplaceEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemReplacedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemReplacedList();
		this.verifyListReplaceEvent(REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE);
		assertTrue(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemReplacedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testFireItemsMovedListEvent() {
		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsMovedListEvent();
		this.verifyListMoveEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsMovedListEvent();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsMovedListEvent();
		this.verifyListMoveEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsMovedListEvent();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testFireItemsMovedListEventNoChange() {
		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsMovedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsMovedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsMovedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsMovedListEventNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testFireItemsMovedList() {
		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsMovedList();
		this.verifyListMoveEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsMovedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsMovedList();
		this.verifyListMoveEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsMovedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testFireItemsMovedListNoChange() {
		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemsMovedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemsMovedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsMovedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemsMovedListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testFireItemMovedList() {
		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireItemMovedList();
		this.verifyListMoveEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireItemMovedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemMovedList();
		this.verifyListMoveEvent(TARGET_INDEX, SOURCE_INDEX);
		assertTrue(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireItemMovedList();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testFireListClearedEvent() {
		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireListClearedEvent();
		this.verifyListClearEvent();
		assertTrue(this.listClearedCalled);

		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireListClearedEvent();
		assertNull(this.listEvent);
		assertFalse(this.listClearedCalled);

		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireListClearedEvent();
		this.verifyListClearEvent();
		assertTrue(this.listClearedCalled);

		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireListClearedEvent();
		assertNull(this.listEvent);
		assertFalse(this.listClearedCalled);
	}

	public void testFireListCleared() {
		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireListCleared();
		this.verifyListClearEvent();
		assertTrue(this.listClearedCalled);

		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireListCleared();
		assertNull(this.listEvent);
		assertFalse(this.listClearedCalled);

		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireListCleared();
		this.verifyListClearEvent();
		assertTrue(this.listClearedCalled);

		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireListCleared();
		assertNull(this.listEvent);
		assertFalse(this.listClearedCalled);
	}

	public void testFireListChangedEvent() {
		this.listEvent = null;
		this.listChangedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireListChangedEvent();
		this.verifyListChangeEvent();
		assertTrue(this.listChangedCalled);

		this.listEvent = null;
		this.listChangedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireListChangedEvent();
		assertNull(this.listEvent);
		assertFalse(this.listChangedCalled);

		this.listEvent = null;
		this.listChangedCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireListChangedEvent();
		this.verifyListChangeEvent();
		assertTrue(this.listChangedCalled);

		this.listEvent = null;
		this.listChangedCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireListChangedEvent();
		assertNull(this.listEvent);
		assertFalse(this.listChangedCalled);
	}

	public void testFireListChanged() {
		this.listEvent = null;
		this.listChangedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireListChanged();
		this.verifyListChangeEvent();
		assertTrue(this.listChangedCalled);

		this.listEvent = null;
		this.listChangedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireListChanged();
		assertNull(this.listEvent);
		assertFalse(this.listChangedCalled);

		this.listEvent = null;
		this.listChangedCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireListChanged();
		this.verifyListChangeEvent();
		assertTrue(this.listChangedCalled);

		this.listEvent = null;
		this.listChangedCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testFireListChanged();
		assertNull(this.listEvent);
		assertFalse(this.listChangedCalled);
	}

	public void testAddItemToListIndex() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testAddItemToListIndex();
		this.verifyListAddEvent(2, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testAddItemToListIndex();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemToListIndex();
		this.verifyListAddEvent(2, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemToListIndex();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testAddItemToList() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testAddItemToList();
		this.verifyListAddEvent(3, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testAddItemToList();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemToList();
		this.verifyListAddEvent(3, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemToList();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testAddItemsToListIndex() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testAddItemsToListIndex();
		this.verifyListAddEvent(2, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testAddItemsToListIndex();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemsToListIndex();
		this.verifyListAddEvent(2, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemsToListIndex();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testAddItemsToListIndexNoChange() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testAddItemsToListIndexNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testAddItemsToListIndexNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemsToListIndexNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemsToListIndexNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testAddItemsToList() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testAddItemsToList();
		this.verifyListAddEvent(3, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testAddItemsToList();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemsToList();
		this.verifyListAddEvent(3, "joo");
		assertTrue(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemsToList();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testAddItemsToListNoChange() {
		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testAddItemsToListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testAddItemsToListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemsToListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);

		this.listEvent = null;
		this.itemsAddedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testAddItemsToListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsAddedListCalled);
	}

	public void testRemoveItemFromListIndex() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testRemoveItemFromListIndex();
		this.verifyListRemoveEvent(1, "bar");
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testRemoveItemFromListIndex();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemFromListIndex();
		this.verifyListRemoveEvent(1, "bar");
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemFromListIndex();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testRemoveItemFromList() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testRemoveItemFromList();
		this.verifyListRemoveEvent(1, "bar");
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testRemoveItemFromList();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemFromList();
		this.verifyListRemoveEvent(1, "bar");
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemFromList();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testRemoveItemsFromListIndex() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testRemoveItemsFromListIndex();
		this.verifyListRemoveEvent(1, "bar");
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testRemoveItemsFromListIndex();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemsFromListIndex();
		this.verifyListRemoveEvent(1, "bar");
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemsFromListIndex();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testRemoveItemsFromListIndexNoChange() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testRemoveItemsFromListIndexNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testRemoveItemsFromListIndexNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemsFromListIndexNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemsFromListIndexNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testRemoveItemsFromList() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testRemoveItemsFromList();
		this.verifyListRemoveEvent(1, "bar");
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testRemoveItemsFromList();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemsFromList();
		this.verifyListRemoveEvent(1, "bar");
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemsFromList();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testRemoveItemsFromListNoChange() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testRemoveItemsFromListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testRemoveItemsFromListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemsFromListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRemoveItemsFromListNoChange();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testRetainItemsInList() {
		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testRetainItemsInList();
		this.verifyListRemoveEvent(0, "foo");
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testRetainItemsInList();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRetainItemsInList();
		this.verifyListRemoveEvent(0, "foo");
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testRetainItemsInList();
		assertNull(this.listEvent);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testReplaceItemInList() {
		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testReplaceItemInList();
		this.verifyListReplaceEvent(1, "xxx", "bar");
		assertTrue(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testReplaceItemInList();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testReplaceItemInList();
		this.verifyListReplaceEvent(1, "xxx", "bar");
		assertTrue(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testReplaceItemInList();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testSetItemsInList() {
		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testSetItemsInList();
		this.verifyListReplaceEvent(1, "xxx", "bar");
		assertTrue(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testSetItemsInList();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testSetItemsInList();
		this.verifyListReplaceEvent(1, "xxx", "bar");
		assertTrue(this.itemsReplacedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testSetItemsInList();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);
	}

	public void testMoveItemsInList() {
		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testMoveItemsInList();
		this.verifyListMoveEvent(2, 4, 2);
		assertTrue(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testMoveItemsInList();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testMoveItemsInList();
		this.verifyListMoveEvent(2, 4, 2);
		assertTrue(this.itemsMovedListCalled);

		this.listEvent = null;
		this.itemsMovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testMoveItemsInList();
		assertNull(this.listEvent);
		assertFalse(this.itemsMovedListCalled);
	}

	public void testClearList() {
		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testClearList();
		this.verifyListClearEvent();
		assertTrue(this.listClearedCalled);

		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testClearList();
		assertNull(this.listEvent);
		assertFalse(this.listClearedCalled);

		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testClearList();
		this.verifyListClearEvent();
		assertTrue(this.listClearedCalled);

		this.listEvent = null;
		this.listClearedCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testClearList();
		assertNull(this.listEvent);
		assertFalse(this.listClearedCalled);
	}

	public void testSynchronizeList() {
		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.itemsRemovedListCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testSynchronizeList();
		assertNotNull(this.listEvent);
		assertTrue(this.itemsReplacedListCalled);
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.itemsRemovedListCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testSynchronizeList();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);
		assertFalse(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.itemsRemovedListCalled = false;
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testSynchronizeList();
		assertNotNull(this.listEvent);
		assertTrue(this.itemsReplacedListCalled);
		assertTrue(this.itemsRemovedListCalled);

		this.listEvent = null;
		this.itemsReplacedListCalled = false;
		this.itemsRemovedListCalled = false;
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		this.testModel.testSynchronizeList();
		assertNull(this.listEvent);
		assertFalse(this.itemsReplacedListCalled);
		assertFalse(this.itemsRemovedListCalled);
	}

	public void testHasAnyListChangeListeners() {
		assertTrue(this.testModel.hasNoListChangeListeners(LIST_NAME));
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.hasAnyListChangeListeners(LIST_NAME));
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.hasNoListChangeListeners(LIST_NAME));

		assertTrue(this.testModel.hasNoListChangeListeners(LIST_NAME));
		this.testModel.addListChangeListener(LIST_NAME, this.changeListener);
		assertTrue(this.testModel.hasAnyListChangeListeners(LIST_NAME));
		this.testModel.removeListChangeListener(LIST_NAME, this.changeListener);
		assertTrue(this.testModel.hasNoListChangeListeners(LIST_NAME));
	}

	public void testAddNullListListener() {
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
			this.testModel.removeListChangeListener("foo", this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener("foo", this.changeListener);
		exCaught = false;
		try {
			this.testModel.removeListChangeListener("foo", this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addListChangeListener("foo", this.changeListener);
		exCaught = false;
		try {
			this.testModel.removeListChangeListener("foo", new ListChangeAdapter());
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

	private void verifyListAddEvent(int index, Object item) {
		assertNotNull(this.listEvent);
		assertEquals(this.testModel, this.listEvent.getSource());
		assertEquals(LIST_NAME, this.listEvent.getListName());
		assertEquals(index, ((ListAddEvent) this.listEvent).getIndex());
		assertEquals(item, ((ListAddEvent) this.listEvent).getItems().iterator().next());
	}

	private void verifyListRemoveEvent(int index, Object item) {
		assertNotNull(this.listEvent);
		assertEquals(this.testModel, this.listEvent.getSource());
		assertEquals(LIST_NAME, this.listEvent.getListName());
		assertEquals(index, ((ListRemoveEvent) this.listEvent).getIndex());
		assertEquals(item, ((ListRemoveEvent) this.listEvent).getItems().iterator().next());
	}

	private void verifyListReplaceEvent(int index, Object newItem, Object oldItem) {
		assertNotNull(this.listEvent);
		assertEquals(this.testModel, this.listEvent.getSource());
		assertEquals(LIST_NAME, this.listEvent.getListName());
		assertEquals(index, ((ListReplaceEvent) this.listEvent).getIndex());
		assertEquals(newItem, ((ListReplaceEvent) this.listEvent).getNewItems().iterator().next());
		assertEquals(oldItem, ((ListReplaceEvent) this.listEvent).getOldItems().iterator().next());
	}

	private void verifyListMoveEvent(int targetIndex, int sourceIndex) {
		this.verifyListMoveEvent(targetIndex, sourceIndex, 1);
	}

	private void verifyListMoveEvent(int targetIndex, int sourceIndex, int length) {
		assertNotNull(this.listEvent);
		assertEquals(this.testModel, this.listEvent.getSource());
		assertEquals(LIST_NAME, this.listEvent.getListName());
		assertEquals(targetIndex, ((ListMoveEvent) this.listEvent).getTargetIndex());
		assertEquals(sourceIndex, ((ListMoveEvent) this.listEvent).getSourceIndex());
		assertEquals(length, ((ListMoveEvent) this.listEvent).getLength());
	}

	private void verifyListClearEvent() {
		assertNotNull(this.listEvent);
		assertEquals(this.testModel, this.listEvent.getSource());
		assertEquals(LIST_NAME, this.listEvent.getListName());
		assertEquals(ListClearEvent.class, this.listEvent.getClass());
	}

	private void verifyListChangeEvent() {
		assertNotNull(this.listEvent);
		assertEquals(this.testModel, this.listEvent.getSource());
		assertEquals(LIST_NAME, this.listEvent.getListName());
		assertEquals(ListChangeEvent.class, this.listEvent.getClass());
	}


	// ********** tree change tests **********

	public void testFireNodeAddedTree() {
		this.treeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireNodeAddedTree();
		this.verifyTreeEvent(OBJECT_PATH);
		assertTrue(this.nodeAddedCalled);

		this.treeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireNodeAddedTree();
		assertNull(this.treeEvent);
		assertFalse(this.nodeAddedCalled);

		this.treeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireNodeAddedTree();
		this.verifyTreeEvent(OBJECT_PATH);
		assertTrue(this.nodeAddedCalled);

		this.treeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireNodeAddedTree();
		assertNull(this.treeEvent);
		assertFalse(this.nodeAddedCalled);
	}

	public void testFireNodeAddedTreeEvent() {
		this.treeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireNodeAddedTreeEvent();
		this.verifyTreeEvent(OBJECT_PATH);
		assertTrue(this.nodeAddedCalled);

		this.treeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireNodeAddedTreeEvent();
		assertNull(this.treeEvent);
		assertFalse(this.nodeAddedCalled);

		this.treeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireNodeAddedTreeEvent();
		this.verifyTreeEvent(OBJECT_PATH);
		assertTrue(this.nodeAddedCalled);

		this.treeEvent = null;
		this.nodeAddedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireNodeAddedTreeEvent();
		assertNull(this.treeEvent);
		assertFalse(this.nodeAddedCalled);
	}

	public void testFireNodeRemovedTreeEvent() {
		this.treeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireNodeRemovedTreeEvent();
		this.verifyTreeEvent(OBJECT_PATH);
		assertTrue(this.nodeRemovedCalled);

		this.treeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireNodeRemovedTreeEvent();
		assertNull(this.treeEvent);
		assertFalse(this.nodeRemovedCalled);

		this.treeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireNodeRemovedTreeEvent();
		this.verifyTreeEvent(OBJECT_PATH);
		assertTrue(this.nodeRemovedCalled);

		this.treeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireNodeRemovedTreeEvent();
		assertNull(this.treeEvent);
		assertFalse(this.nodeRemovedCalled);
	}

	public void testFireNodeRemovedTree() {
		this.treeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireNodeRemovedTree();
		this.verifyTreeEvent(OBJECT_PATH);
		assertTrue(this.nodeRemovedCalled);

		this.treeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireNodeRemovedTree();
		assertNull(this.treeEvent);
		assertFalse(this.nodeRemovedCalled);

		this.treeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireNodeRemovedTree();
		this.verifyTreeEvent(OBJECT_PATH);
		assertTrue(this.nodeRemovedCalled);

		this.treeEvent = null;
		this.nodeRemovedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireNodeRemovedTree();
		assertNull(this.treeEvent);
		assertFalse(this.nodeRemovedCalled);
	}

	public void testFireTreeClearedEvent() {
		this.treeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireTreeClearedEvent();
		this.verifyTreeEvent(null);
		assertTrue(this.treeClearedCalled);

		this.treeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireTreeClearedEvent();
		assertNull(this.treeEvent);
		assertFalse(this.treeClearedCalled);

		this.treeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireTreeClearedEvent();
		this.verifyTreeEvent(null);
		assertTrue(this.treeClearedCalled);

		this.treeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireTreeClearedEvent();
		assertNull(this.treeEvent);
		assertFalse(this.treeClearedCalled);
	}

	public void testFireTreeCleared() {
		this.treeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireTreeCleared();
		this.verifyTreeEvent(null);
		assertTrue(this.treeClearedCalled);

		this.treeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireTreeCleared();
		assertNull(this.treeEvent);
		assertFalse(this.treeClearedCalled);

		this.treeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireTreeCleared();
		this.verifyTreeEvent(null);
		assertTrue(this.treeClearedCalled);

		this.treeEvent = null;
		this.treeClearedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireTreeCleared();
		assertNull(this.treeEvent);
		assertFalse(this.treeClearedCalled);
	}

	public void testFireTreeChangedEvent() {
		this.treeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireTreeChangedEvent();
		this.verifyTreeEvent(null);
		assertTrue(this.treeChangedCalled);

		this.treeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireTreeChangedEvent();
		assertNull(this.treeEvent);
		assertFalse(this.treeChangedCalled);

		this.treeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireTreeChangedEvent();
		this.verifyTreeEvent(null);
		assertTrue(this.treeChangedCalled);

		this.treeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireTreeChangedEvent();
		assertNull(this.treeEvent);
		assertFalse(this.treeChangedCalled);
	}

	public void testFireTreeChanged() {
		this.treeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.addChangeListener(this.changeListener);
		this.testModel.testFireTreeChanged();
		this.verifyTreeEvent(null);
		assertTrue(this.treeChangedCalled);

		this.treeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.removeChangeListener(this.changeListener);
		this.testModel.testFireTreeChanged();
		assertNull(this.treeEvent);
		assertFalse(this.treeChangedCalled);

		this.treeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.addTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireTreeChanged();
		this.verifyTreeEvent(null);
		assertTrue(this.treeChangedCalled);

		this.treeEvent = null;
		this.treeChangedCalled = false;
		this.testModel.removeTreeChangeListener(TREE_NAME, this.changeListener);
		this.testModel.testFireTreeChanged();
		assertNull(this.treeEvent);
		assertFalse(this.treeChangedCalled);
	}

	public void testHasAnyTreeChangeListeners() {
		assertTrue(this.testModel.hasNoTreeChangeListeners(TREE_NAME));
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.hasAnyTreeChangeListeners(TREE_NAME));
		this.testModel.removeChangeListener(this.changeListener);
		assertTrue(this.testModel.hasNoTreeChangeListeners(TREE_NAME));

		assertTrue(this.testModel.hasNoTreeChangeListeners(TREE_NAME));
		this.testModel.addTreeChangeListener(TREE_NAME, this.changeListener);
		assertTrue(this.testModel.hasAnyTreeChangeListeners(TREE_NAME));
		this.testModel.removeTreeChangeListener(TREE_NAME, this.changeListener);
		assertTrue(this.testModel.hasNoTreeChangeListeners(TREE_NAME));
	}

	public void testAddNullTreeListener() {
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
			this.testModel.removeTreeChangeListener("foo", this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addPropertyChangeListener("foo", this.changeListener);
		exCaught = false;
		try {
			this.testModel.removeTreeChangeListener("foo", this.changeListener);
		} catch (IllegalArgumentException ex) {
			exCaught = true;
		}
		assertTrue(exCaught);

		this.testModel.addTreeChangeListener("foo", this.changeListener);
		exCaught = false;
		try {
			this.testModel.removeTreeChangeListener("foo", new TreeChangeAdapter());
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

	private void verifyTreeEvent(List<?> path) {
		assertNotNull(this.treeEvent);
		assertEquals(this.testModel, this.treeEvent.getSource());
		assertEquals(TREE_NAME, this.treeEvent.getTreeName());
		assertEquals(path, this.getListPath());
	}

	private List<?> getListPath() {
		Iterable<?> iterable = this.getPath();
		return (iterable == null)  ? null : CollectionTools.list(iterable);
	}

	private Iterable<?> getPath() {
		if (this.treeEvent instanceof TreeAddEvent) {
			return ((TreeAddEvent) this.treeEvent).getPath();
		}
		if (this.treeEvent instanceof TreeRemoveEvent) {
			return ((TreeRemoveEvent) this.treeEvent).getPath();
		}
		return null;
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
		assertTrue(this.testModel.testElementsAreEqual(c1, c2));
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
		assertTrue(this.testModel.testElementsAreDifferent(c1, c2));
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
		this.testModel.addChangeListener(this.changeListener);
		assertTrue(this.testModel.hasAnyPropertyChangeListeners(PROPERTY_NAME));

		// verify that the clone does not have any listeners
		TestModel clone = this.testModel.clone();
		assertFalse(clone.hasAnyPropertyChangeListeners(PROPERTY_NAME));
		clone.addChangeListener(this.changeListener);
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

	class Adapter implements ChangeListener {
		public void stateChanged(StateChangeEvent e) {
			ChangeSupportTests.this.stateChangedCalled = true;
			ChangeSupportTests.this.stateChangeEvent = e;
		}
	
		public void propertyChanged(PropertyChangeEvent e) {
			ChangeSupportTests.this.propertyChangeCalled = true;
			ChangeSupportTests.this.propertyChangeEvent = e;
		}
	
	
		public void itemsAdded(CollectionAddEvent e) {
			ChangeSupportTests.this.itemsAddedCollectionCalled = true;
			ChangeSupportTests.this.collectionEvent = e;
		}
		public void itemsRemoved(CollectionRemoveEvent e) {
			ChangeSupportTests.this.itemsRemovedCollectionCalled = true;
			ChangeSupportTests.this.collectionEvent = e;
		}
		public void collectionCleared(CollectionClearEvent e) {
			ChangeSupportTests.this.collectionClearedCalled = true;
			ChangeSupportTests.this.collectionEvent = e;
		}
		public void collectionChanged(CollectionChangeEvent e) {
			ChangeSupportTests.this.collectionChangedCalled = true;
			ChangeSupportTests.this.collectionEvent = e;
		}
	
		public void itemsAdded(ListAddEvent e) {
			ChangeSupportTests.this.itemsAddedListCalled = true;
			ChangeSupportTests.this.listEvent = e;
		}
		public void itemsRemoved(ListRemoveEvent e) {
			ChangeSupportTests.this.itemsRemovedListCalled = true;
			ChangeSupportTests.this.listEvent = e;
		}
		public void itemsReplaced(ListReplaceEvent e) {
			ChangeSupportTests.this.itemsReplacedListCalled = true;
			ChangeSupportTests.this.listEvent = e;
		}
		public void itemsMoved(ListMoveEvent e) {
			ChangeSupportTests.this.itemsMovedListCalled = true;
			ChangeSupportTests.this.listEvent = e;
		}
		public void listCleared(ListClearEvent e) {
			ChangeSupportTests.this.listClearedCalled = true;
			ChangeSupportTests.this.listEvent = e;
		}
		public void listChanged(ListChangeEvent e) {
			ChangeSupportTests.this.listChangedCalled = true;
			ChangeSupportTests.this.listEvent = e;
		}
	
		public void nodeAdded(TreeAddEvent e) {
			ChangeSupportTests.this.nodeAddedCalled = true;
			ChangeSupportTests.this.treeEvent = e;
		}
		public void nodeRemoved(TreeRemoveEvent e) {
			ChangeSupportTests.this.nodeRemovedCalled = true;
			ChangeSupportTests.this.treeEvent = e;
		}
		public void treeCleared(TreeClearEvent e) {
			ChangeSupportTests.this.treeClearedCalled = true;
			ChangeSupportTests.this.treeEvent = e;
		}
		public void treeChanged(TreeChangeEvent e) {
			ChangeSupportTests.this.treeChangedCalled = true;
			ChangeSupportTests.this.treeEvent = e;
		}
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
			this.fireItemsAdded(new CollectionAddEvent(this, COLLECTION_NAME, ADDED_OBJECT_VALUE));
		}

		public void testFireItemsAddedCollectionEventNoChange() {
			this.fireItemsAdded(new CollectionAddEvent(this, COLLECTION_NAME, Collections.emptySet()));
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
			this.fireItemsRemoved(new CollectionRemoveEvent(this, COLLECTION_NAME, REMOVED_OBJECT_VALUE));
		}

		public void testFireItemsRemovedCollectionEventNoChange() {
			this.fireItemsRemoved(new CollectionRemoveEvent(this, COLLECTION_NAME, Collections.emptySet()));
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
			this.fireCollectionChanged(new CollectionChangeEvent(this, COLLECTION_NAME, Collections.emptySet()));
		}

		public void testFireCollectionChanged() {
			this.fireCollectionChanged(COLLECTION_NAME, Collections.emptySet());
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
			this.fireItemsAdded(new ListAddEvent(this, LIST_NAME, ADD_INDEX, ADDED_OBJECT_VALUE));
		}

		public void testFireItemsAddedListEventNoChange() {
			this.fireItemsAdded(new ListAddEvent(this, LIST_NAME, ADD_INDEX, Collections.emptyList()));
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
			this.fireItemsRemoved(new ListRemoveEvent(this, LIST_NAME, REMOVE_INDEX, REMOVED_OBJECT_VALUE));
		}

		public void testFireItemsRemovedListEventNoChange() {
			this.fireItemsRemoved(new ListRemoveEvent(this, LIST_NAME, REMOVE_INDEX, Collections.emptyList()));
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
			this.fireItemsReplaced(new ListReplaceEvent(this, LIST_NAME, REPLACE_INDEX, ADDED_OBJECT_VALUE, REMOVED_OBJECT_VALUE));
		}

		public void testFireItemsReplacedListEventNoChange() {
			this.fireItemsReplaced(new ListReplaceEvent(this, LIST_NAME, REPLACE_INDEX, Collections.emptyList(), Collections.emptyList()));
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
			this.fireItemsMoved(new ListMoveEvent(this, LIST_NAME, TARGET_INDEX, SOURCE_INDEX, 1));
		}

		public void testFireItemsMovedListEventNoChange() {
			this.fireItemsMoved(new ListMoveEvent(this, LIST_NAME, SOURCE_INDEX, SOURCE_INDEX, 1));
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
			this.fireListCleared(new ListClearEvent(this, LIST_NAME));
		}

		public void testFireListCleared() {
			this.fireListCleared(LIST_NAME);
		}

		public void testFireListChangedEvent() {
			this.fireListChanged(new ListChangeEvent(this, LIST_NAME, Collections.emptyList()));
		}

		public void testFireListChanged() {
			this.fireListChanged(LIST_NAME, Collections.emptyList());
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

		public void testRemoveItemFromListIndex() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.removeItemFromList(1, list, LIST_NAME);
		}

		public void testRemoveItemFromList() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.removeItemFromList("bar", list, LIST_NAME);
		}

		public void testRemoveItemsFromListIndex() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.removeItemsFromList(1, 1, list, LIST_NAME);
		}

		public void testRemoveItemsFromListIndexNoChange() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.removeItemsFromList(2, 0, list, LIST_NAME);
		}

		public void testRemoveItemsFromList() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.removeItemsFromList(Collections.singletonList("bar"), list, LIST_NAME);
		}

		public void testRemoveItemsFromListNoChange() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.addItemsToList(Collections.<String>emptyList(), list, LIST_NAME);
		}

		public void testRetainItemsInList() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.retainItemsInList(new String[] {"bar", "baz"}, list, LIST_NAME);
		}

		public void testReplaceItemInList() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.replaceItemInList("bar", "xxx", list, LIST_NAME);
		}

		public void testSetItemsInList() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.setItemsInList(1, new String[] {"xxx"}, list, LIST_NAME);
		}

		public void testMoveItemsInList() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			list.add("xxx");
			list.add("yyy");
			list.add("zzz");
			this.moveItemsInList(2, 4, 2, list, LIST_NAME);
		}

		public void testClearList() {
			List<String> list = new ArrayList<String>();
			list.add("foo");
			list.add("bar");
			list.add("baz");
			this.clearList(list, LIST_NAME);
		}

		public void testSynchronizeList() {
			List<String> oldList = new ArrayList<String>();
			oldList.add("foo");
			oldList.add("bar");
			oldList.add("baz");
			oldList.add("xxx");
			oldList.add("yyy");
			oldList.add("zzz");
			List<String> newList = new ArrayList<String>();
			newList.add("foo");
			newList.add("ppp");
			newList.add("baz");
			newList.add("xxx");
			newList.add("qqq");
			this.synchronizeList(newList, oldList, LIST_NAME);
			assertEquals(newList, oldList);
		}

		// ***** tree
		public void testFireNodeAddedTreeEvent() {
			this.fireNodeAdded(new TreeAddEvent(this, TREE_NAME, OBJECT_PATH));
		}

		public void testFireNodeAddedTree() {
			this.fireNodeAdded(TREE_NAME, OBJECT_PATH);
		}

		public void testFireNodeRemovedTreeEvent() {
			this.fireNodeRemoved(new TreeRemoveEvent(this, TREE_NAME, OBJECT_PATH));
		}

		public void testFireNodeRemovedTree() {
			this.fireNodeRemoved(TREE_NAME, OBJECT_PATH);
		}

		public void testFireTreeClearedEvent() {
			this.fireTreeCleared(new TreeClearEvent(this, TREE_NAME));
		}

		public void testFireTreeCleared() {
			this.fireTreeCleared(TREE_NAME);
		}

		public void testFireTreeChangedEvent() {
			this.fireTreeChanged(new TreeChangeEvent(this, TREE_NAME, OBJECT_PATH));
		}

		public void testFireTreeChanged() {
			this.fireTreeChanged(TREE_NAME, OBJECT_PATH);
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

		public boolean testElementsAreDifferent(Iterable<?> iterable1, Iterable<?> iterable2) {
			return this.getChangeSupport().elementsAreDifferent(iterable1, iterable2);
		}

		public boolean testElementsAreEqual(Iterable<?> iterable1, Iterable<?> iterable2) {
			return this.getChangeSupport().elementsAreEqual(iterable1, iterable2);
		}

		@Override
		public void toString(StringBuilder sb) {
			sb.append(TEST_TO_STRING);
		}

	}


	// ********** serialization test **********
	//TODO - This test doesn't pass in the Eclipse build environment (Linux) for some reason
//	public void testSerialization() throws java.io.IOException, ClassNotFoundException {
//		LocalModel model1 = new LocalModel();
//		Foo foo1 = new Foo();
//		Bar bar1 = new Bar();
//		Joo joo1 = new Joo();
//		Jar jar1 = new Jar();
//		model1.addStateChangeListener(foo1);
//		model1.addStateChangeListener(bar1);
//		model1.addListChangeListener(joo1);
//		model1.addListChangeListener(jar1);
//
//		ChangeListener[] listeners1 = this.getListeners(model1, StateChangeListener.class);
//		assertEquals(2, listeners1.length);
//		// the order of these could change...
//		assertEquals(Foo.class, listeners1[0].getClass());
//		assertEquals(Bar.class, listeners1[1].getClass());
//
//		listeners1 = this.getListeners(model1, ListChangeListener.class);
//		assertEquals(2, listeners1.length);
//		// the order of these could change...
//		assertEquals(Joo.class, listeners1[0].getClass());
//		assertEquals(Jar.class, listeners1[1].getClass());
//
//		LocalModel model2 = TestTools.serialize(model1);
//
//		ChangeListener[] listeners2 = this.getListeners(model2, StateChangeListener.class);
//		assertEquals(1, listeners2.length);
//		assertEquals(Foo.class, listeners2[0].getClass());
//
//		listeners2 = this.getListeners(model2, ListChangeListener.class);
//		assertEquals(1, listeners2.length);
//		assertEquals(Joo.class, listeners2[0].getClass());
//	}
//
//	private ChangeListener[] getListeners(LocalModel model, Class<? extends ChangeListener> listenerClass) {
//		ChangeSupport changeSupport = (ChangeSupport) ClassTools.fieldValue(model, "changeSupport");
//		return (ChangeListener[]) ClassTools.executeMethod(changeSupport, "getListeners", Class.class, listenerClass);
//	}
//
//	private static class LocalModel extends AbstractModel {
//		LocalModel() {
//			super();
//		}
//	}
//
//	private static class Foo implements Serializable, StateChangeListener {
//		Foo() {
//			super();
//		}
//		public void stateChanged(StateChangeEvent event) {
//			// do nothing
//		}
//	}
//
//	private static class Bar implements StateChangeListener {
//		Bar() {
//			super();
//		}
//		public void stateChanged(StateChangeEvent event) {
//			// do nothing
//		}
//	}
//
//	private static class Joo extends ListChangeAdapter implements Serializable {
////		private static final ObjectStreamField[] serialPersistentFields = {new ObjectStreamField("changeSupport", ChangeSupport.class)};
//		Joo() {
//			super();
//		}
//	}
//
//	private static class Jar extends ListChangeAdapter {
//		Jar() {
//			super();
//		}
//	}


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
			this.fireCollectionChanged("foo", Collections.emptySet());
		}
		void notifyListListeners() {
			this.fireListChanged("foo", Collections.emptyList());
		}
		void notifyTreeListeners() {
			this.fireTreeChanged("foo", Collections.emptySet());
		}
	}

	/**
	 * This object will fire state change events whenever it receives
	 * a state change event from localA.
	 */
	private static class LocalB
		extends AbstractModel
		implements ChangeListener
	{
		LocalB(LocalA localA) {
			super();
			localA.addChangeListener(this);
		}

		public void stateChanged(StateChangeEvent e) {
			this.fireStateChanged();
		}

		public void propertyChanged(PropertyChangeEvent evt) {
			this.firePropertyChanged("bar", 1, 2);
		}

		public void collectionChanged(CollectionChangeEvent e) {
			this.fireCollectionChanged("bar", Collections.emptySet());
		}
		public void collectionCleared(CollectionClearEvent e) {/*ignore*/}
		public void itemsAdded(CollectionAddEvent e) {/*ignore*/}
		public void itemsRemoved(CollectionRemoveEvent e) {/*ignore*/}

		public void listChanged(ListChangeEvent e) {
			this.fireListChanged("bar", Collections.emptyList());
		}
		public void listCleared(ListClearEvent e) {/*ignore*/}
		public void itemsAdded(ListAddEvent e) {/*ignore*/}
		public void itemsRemoved(ListRemoveEvent e) {/*ignore*/}
		public void itemsReplaced(ListReplaceEvent e) {/*ignore*/}
		public void itemsMoved(ListMoveEvent e) {/*ignore*/}

		public void treeChanged(TreeChangeEvent e) {
			this.fireTreeChanged("bar", Collections.emptySet());
		}
		public void treeCleared(TreeClearEvent e) {/*ignore*/}
		public void nodeAdded(TreeAddEvent e) {/*ignore*/}
		public void nodeRemoved(TreeRemoveEvent e) {/*ignore*/}

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
		implements ChangeListener
	{
		private LocalA localA;
		private LocalB localB;
		private boolean listeningToLocalA;

		LocalC(LocalA localA, LocalB localB) {
			super();
			this.localA = localA;
			this.localB = localB;

			localA.addChangeListener(this);
			this.listeningToLocalA = true;

			localB.addChangeListener(this);
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
				this.localA.removeChangeListener(this);
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
				this.localA.removeChangeListener(this);
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
				this.localA.removeChangeListener(this);
				this.listeningToLocalA = false;
			} else {
				throw new IllegalStateException("bogus event source: " + source);
			}
		}
		public void collectionCleared(CollectionClearEvent e) {/*ignore*/}
		public void itemsAdded(CollectionAddEvent e) {/*ignore*/}
		public void itemsRemoved(CollectionRemoveEvent e) {/*ignore*/}

		public void listChanged(ListChangeEvent e) {
			Object source = e.getSource();
			if (source == this.localA) {
				if ( ! this.listeningToLocalA) {
					throw new IllegalStateException(ISE_MESSAGE);
				}
			} else if (source == this.localB) {
				this.localA.removeChangeListener(this);
				this.listeningToLocalA = false;
			} else {
				throw new IllegalStateException("bogus event source: " + source);
			}
		}
		public void listCleared(ListClearEvent e) {/*ignore*/}
		public void itemsAdded(ListAddEvent e) {/*ignore*/}
		public void itemsRemoved(ListRemoveEvent e) {/*ignore*/}
		public void itemsReplaced(ListReplaceEvent e) {/*ignore*/}
		public void itemsMoved(ListMoveEvent e) {/*ignore*/}

		public void treeChanged(TreeChangeEvent e) {
			Object source = e.getSource();
			if (source == this.localA) {
				if ( ! this.listeningToLocalA) {
					throw new IllegalStateException(ISE_MESSAGE);
				}
			} else if (source == this.localB) {
				this.localA.removeChangeListener(this);
				this.listeningToLocalA = false;
			} else {
				throw new IllegalStateException("bogus event source: " + source);
			}
		}
		public void treeCleared(TreeClearEvent e) {/*ignore*/}
		public void nodeAdded(TreeAddEvent e) {/*ignore*/}
		public void nodeRemoved(TreeRemoveEvent e) {/*ignore*/}

	}

}
