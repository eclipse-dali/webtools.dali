/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.prefs;

import java.util.HashMap;
import java.util.Map;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

import org.eclipse.jpt.common.utility.internal.ReflectionTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.prefs.PreferencePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.prefs.PreferencesCollectionValueModel;
import org.eclipse.jpt.common.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionClearEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionEvent;
import org.eclipse.jpt.common.utility.model.event.CollectionRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;

@SuppressWarnings("nls")
public class PreferencesCollectionValueModelTests extends PreferencesTestCase {
	private Map<String, String> expectedValues;
	private WritablePropertyValueModel<Preferences> nodeHolder;
	PreferencesCollectionValueModel<String> preferencesAdapter;
	CollectionEvent event;
	CollectionChangeListener listener;
	private PropertyChangeListener itemListener;
	boolean listenerRemoved = false;
	private static final String KEY_NAME_1 = "foo 1";
	private static final String KEY_NAME_2 = "foo 2";
	private static final String KEY_NAME_3 = "foo 3";
	private static final String STRING_VALUE_1 = "original string value 1";
	private static final String STRING_VALUE_2 = "original string value 2";
	private static final String STRING_VALUE_3 = "original string value 3";

	public PreferencesCollectionValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.expectedValues = new HashMap<String, String>();
		this.testNode.put(KEY_NAME_1, STRING_VALUE_1);	this.expectedValues.put(KEY_NAME_1, STRING_VALUE_1);
		this.testNode.put(KEY_NAME_2, STRING_VALUE_2);	this.expectedValues.put(KEY_NAME_2, STRING_VALUE_2);
		this.testNode.put(KEY_NAME_3, STRING_VALUE_3);	this.expectedValues.put(KEY_NAME_3, STRING_VALUE_3);

		this.nodeHolder = new SimplePropertyValueModel<Preferences>(this.testNode);
		this.preferencesAdapter = new PreferencesCollectionValueModel<String>(this.nodeHolder);
		this.listener = this.buildCollectionChangeListener();
		this.itemListener = this.buildItemListener();
		this.preferencesAdapter.addCollectionChangeListener(CollectionValueModel.VALUES, this.listener);
		this.event = null;
	}

	private CollectionChangeListener buildCollectionChangeListener() {
		return new CollectionChangeListener() {
			public void collectionChanged(CollectionChangeEvent e) {
				this.logEvent(e);
			}
			public void collectionCleared(CollectionClearEvent e) {
				this.logEvent(e);
			}
			public void itemsAdded(CollectionAddEvent e) {
				this.logEvent(e);
			}
			public void itemsRemoved(CollectionRemoveEvent e) {
				this.logEvent(e);
			}
			private void logEvent(CollectionEvent e) {
				if (PreferencesCollectionValueModelTests.this.event != null) {
					throw new IllegalStateException("unexpected this.event: " + e);
				}
				PreferencesCollectionValueModelTests.this.event = e;
			}
		};
	}

	private PropertyChangeListener buildItemListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				throw new IllegalStateException("unexpected this.event: " + e);
			}
		};
	}

	public void testSubjectHolder() throws Exception {
		this.verifyAdapter(this.preferencesAdapter);
		assertNull(this.event);

		String ANOTHER_KEY_NAME_1 = "another key 1";
		String ANOTHER_KEY_NAME_2 = "another key 2";
		String ANOTHER_KEY_NAME_3 = "another key 3";
		String ANOTHER_STRING_VALUE_1 = "another string value 1";
		String ANOTHER_STRING_VALUE_2 = "another string value 2";
		String ANOTHER_STRING_VALUE_3 = "another string value 3";
		Preferences anotherNode = this.classNode.node("another test node");
		this.expectedValues.clear();
		anotherNode.put(ANOTHER_KEY_NAME_1, ANOTHER_STRING_VALUE_1);	this.expectedValues.put(ANOTHER_KEY_NAME_1, ANOTHER_STRING_VALUE_1);
		anotherNode.put(ANOTHER_KEY_NAME_2, ANOTHER_STRING_VALUE_2);	this.expectedValues.put(ANOTHER_KEY_NAME_2, ANOTHER_STRING_VALUE_2);
		anotherNode.put(ANOTHER_KEY_NAME_3, ANOTHER_STRING_VALUE_3);	this.expectedValues.put(ANOTHER_KEY_NAME_3, ANOTHER_STRING_VALUE_3);

		this.nodeHolder.setValue(anotherNode);
		// collectionChanged does not pass any items in the this.event
		this.verifyEvent();
		this.verifyAdapter(this.preferencesAdapter);
		
		this.event = null;
		this.expectedValues.clear();
		this.nodeHolder.setValue(null);
		this.verifyEvent();
		assertFalse(this.preferencesAdapter.iterator().hasNext());
		
		this.event = null;
		this.nodeHolder.setValue(this.testNode);
		this.verifyEvent();
		this.expectedValues.clear();
		this.expectedValues.put(KEY_NAME_1, STRING_VALUE_1);
		this.expectedValues.put(KEY_NAME_2, STRING_VALUE_2);
		this.expectedValues.put(KEY_NAME_3, STRING_VALUE_3);
		this.verifyAdapter(this.preferencesAdapter);
	}

	public void testAddPreference() throws Exception {
		this.verifyAdapter(this.preferencesAdapter);
		assertNull(this.event);

		String ANOTHER_KEY_NAME = "another key";
		String ANOTHER_STRING_VALUE = "another string value";
		this.testNode.put(ANOTHER_KEY_NAME, ANOTHER_STRING_VALUE);
		this.waitForEventQueueToClear();
		Map<String, String> expectedItems = new HashMap<String, String>();
		expectedItems.put(ANOTHER_KEY_NAME, ANOTHER_STRING_VALUE);
		this.verifyEvent(expectedItems);
		this.expectedValues.put(ANOTHER_KEY_NAME, ANOTHER_STRING_VALUE);
		this.verifyAdapter(this.preferencesAdapter);
	}

	public void testRemovePreference() throws Exception {
		this.verifyAdapter(this.preferencesAdapter);
		assertNull(this.event);

		this.testNode.remove(KEY_NAME_2);
		this.waitForEventQueueToClear();

		assertNotNull(this.event);
		assertEquals(this.preferencesAdapter, this.event.getSource());
		assertEquals(CollectionValueModel.VALUES, this.event.getCollectionName());
		assertEquals(1, ((CollectionRemoveEvent) this.event).getItemsSize());
		@SuppressWarnings("unchecked")
		String key = ((PreferencePropertyValueModel<String>) ((CollectionRemoveEvent) this.event).getItems().iterator().next()).getKey();
		assertEquals(KEY_NAME_2, key);

		this.expectedValues.remove(KEY_NAME_2);
		this.verifyAdapter(this.preferencesAdapter);
	}

	public void testChangePreference() throws Exception {
		this.verifyAdapter(this.preferencesAdapter);
		assertNull(this.event);

		String DIFFERENT = "something completely different";
		this.testNode.put(KEY_NAME_2, DIFFERENT);
		this.waitForEventQueueToClear();

		assertNull(this.event);

		this.expectedValues.put(KEY_NAME_2, DIFFERENT);
		this.verifyAdapter(this.preferencesAdapter);
	}

	public void testValues() throws Exception {
		this.verifyNode(this.testNode);
		this.verifyAdapter(this.preferencesAdapter);
	}

	/**
	 * test a situation where
	 * - we are listening to the node when it gets removed from the preferences "repository"
	 * - we get notification that it has been removed
	 * - we try to remove our this.listener
	 * - the node will throw an IllegalStateException - the adapter should handle it OK...
	 */
	public void testRemoveNode() throws Exception {
		assertTrue(this.preferencesAdapter.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		Preferences parent = this.testNode.parent();
		parent.addNodeChangeListener(this.buildParentNodeChangeListener());
		this.testNode.removeNode();
		this.testNode.flush();		// this seems to be required for the this.event to trigger...
		this.waitForEventQueueToClear();

		assertTrue(this.listenerRemoved);
		assertFalse(this.preferencesAdapter.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
	}

	private NodeChangeListener buildParentNodeChangeListener() {
		return new NodeChangeListener() {
			public void childAdded(NodeChangeEvent e) {
				throw new IllegalStateException("unexpected this.event: " + e);
			}
			public void childRemoved(NodeChangeEvent e) {
				if (e.getChild() == PreferencesCollectionValueModelTests.this.testNode) {
					PreferencesCollectionValueModelTests.this.preferencesAdapter.removeCollectionChangeListener(CollectionValueModel.VALUES, PreferencesCollectionValueModelTests.this.listener);
					// this line of code will not execute if the line above triggers an exception
					PreferencesCollectionValueModelTests.this.listenerRemoved = true;
				}
			}
		};
	}

	public void testHasListeners() throws Exception {
		assertTrue(this.preferencesAdapter.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		assertTrue(this.nodeHasAnyPrefListeners(this.testNode));
		this.preferencesAdapter.removeCollectionChangeListener(CollectionValueModel.VALUES, this.listener);
		assertFalse(this.nodeHasAnyPrefListeners(this.testNode));
		assertFalse(this.preferencesAdapter.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));

		ChangeListener listener2 = this.buildChangeListener();
		this.preferencesAdapter.addChangeListener(listener2);
		assertTrue(this.preferencesAdapter.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		assertTrue(this.nodeHasAnyPrefListeners(this.testNode));
		this.preferencesAdapter.removeChangeListener(listener2);
		assertFalse(this.nodeHasAnyPrefListeners(this.testNode));
		assertFalse(this.preferencesAdapter.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
	}

	private ChangeListener buildChangeListener() {
		return new ChangeAdapter() {
			@Override
			public void collectionChanged(CollectionChangeEvent e) {
				this.logEvent(e);
			}
			@Override
			public void collectionCleared(CollectionClearEvent e) {
				this.logEvent(e);
			}
			@Override
			public void itemsAdded(CollectionAddEvent e) {
				this.logEvent(e);
			}
			@Override
			public void itemsRemoved(CollectionRemoveEvent e) {
				this.logEvent(e);
			}
			private void logEvent(CollectionEvent e) {
				if (PreferencesCollectionValueModelTests.this.event != null) {
					throw new IllegalStateException("unexpected this.event: " + e);
				}
				PreferencesCollectionValueModelTests.this.event = e;
			}
		};
	}

	private void verifyEvent(Map<String, String> items) {
		this.verifyEvent();
		assertEquals(items.size(), ((CollectionAddEvent) this.event).getItemsSize());
		@SuppressWarnings("unchecked")
		Iterable<PreferencePropertyValueModel<String>> eventItems = (Iterable<PreferencePropertyValueModel<String>>) ((CollectionAddEvent) this.event).getItems();
		this.verifyItems(items, eventItems);
	}

	private void verifyEvent() {
		assertNotNull(this.event);
		assertEquals(this.preferencesAdapter, this.event.getSource());
		assertEquals(CollectionValueModel.VALUES, this.event.getCollectionName());
	}

	private void verifyNode(Preferences node) throws Exception {
		String[] keys = node.keys();
		assertEquals(this.expectedValues.size(), keys.length);
		for (int i = 0; i < keys.length; i++) {
			assertEquals(this.expectedValues.get(keys[i]), node.get(keys[i], "<missing preference>"));
		}
	}

	private void verifyAdapter(PreferencesCollectionValueModel<String> cvm) {
		assertEquals(this.expectedValues.size(), cvm.size());
		this.verifyItems(this.expectedValues, cvm);
	}

	private void verifyItems(Map<String, String> expected, Iterable<PreferencePropertyValueModel<String>> actual) {
		for (PreferencePropertyValueModel<String> model : actual) {
			model.addPropertyChangeListener(PropertyValueModel.VALUE, this.itemListener);
			assertEquals(expected.get(model.getKey()), model.getValue());
			model.removePropertyChangeListener(PropertyValueModel.VALUE, this.itemListener);
		}
	}

	private boolean nodeHasAnyPrefListeners(Preferences node) throws Exception {
		PreferenceChangeListener[] prefListeners = (PreferenceChangeListener[]) ReflectionTools.getFieldValue(node, "prefListeners");
		return prefListeners.length > 0;
	}

}
