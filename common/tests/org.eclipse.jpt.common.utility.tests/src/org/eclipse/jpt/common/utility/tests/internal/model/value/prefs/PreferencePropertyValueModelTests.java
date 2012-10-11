/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.tests.internal.model.value.prefs;

import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.prefs.PreferencePropertyValueModel;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.ChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.ChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;

@SuppressWarnings("nls")
public class PreferencePropertyValueModelTests extends PreferencesTestCase {
	private ModifiablePropertyValueModel<Preferences> nodeHolder;
	PreferencePropertyValueModel<String> preferenceAdapter;
	PropertyChangeEvent event;
	PropertyChangeListener listener;
	boolean listenerRemoved = false;
	PreferenceChangeEvent preferenceEvent;
	private static final String KEY_NAME = "foo";
	private static final String STRING_VALUE = "original string value";

	public PreferencePropertyValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.testNode.put(KEY_NAME, STRING_VALUE);

		this.nodeHolder = new SimplePropertyValueModel<Preferences>(this.testNode);
		this.preferenceAdapter = PreferencePropertyValueModel.forString(this.nodeHolder, KEY_NAME, null);
		this.listener = this.buildValueChangeListener();
		this.preferenceAdapter.addPropertyChangeListener(PropertyValueModel.VALUE, this.listener);
		this.event = null;
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	private PropertyChangeListener buildValueChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				if (PreferencePropertyValueModelTests.this.event != null) {
					throw new IllegalStateException("unexpected this.event: " + e);
				}
				PreferencePropertyValueModelTests.this.event = e;
			}
		};
	}

	public void testSubjectHolder() throws Exception {
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
		assertNull(this.event);

		String ANOTHER_STRING_VALUE = "some other value";
		Preferences anotherNode = this.classNode.node("another test node");
		anotherNode.put(KEY_NAME, ANOTHER_STRING_VALUE);

		this.nodeHolder.setValue(anotherNode);
		this.verifyEvent(STRING_VALUE, ANOTHER_STRING_VALUE);
		assertEquals(ANOTHER_STRING_VALUE, this.preferenceAdapter.getValue());
		
		this.event = null;
		this.nodeHolder.setValue(null);
		this.verifyEvent(ANOTHER_STRING_VALUE, null);
		assertNull(this.preferenceAdapter.getValue());
		
		this.event = null;
		this.nodeHolder.setValue(this.testNode);
		this.verifyEvent(null, STRING_VALUE);
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
	}

	public void testPreferenceChange() throws Exception {
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
		assertNull(this.event);

		this.testNode.put(KEY_NAME, STRING_VALUE + STRING_VALUE);
		this.waitForEventQueueToClear();
		this.verifyEvent(STRING_VALUE, STRING_VALUE + STRING_VALUE);
		assertEquals(STRING_VALUE + STRING_VALUE, this.preferenceAdapter.getValue());
		
		this.event = null;
		this.testNode.remove(KEY_NAME);
		this.waitForEventQueueToClear();
		this.verifyEvent(STRING_VALUE + STRING_VALUE, null);
		assertNull(this.preferenceAdapter.getValue());
		
		this.event = null;
		this.testNode.put(KEY_NAME, STRING_VALUE);
		this.waitForEventQueueToClear();
		this.verifyEvent(null, STRING_VALUE);
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
	}

	public void testValue() throws Exception {
		assertEquals(STRING_VALUE, this.testNode.get(KEY_NAME, "<missing preference>"));
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
	}

	public void testSetValue() throws Exception {
		String ANOTHER_STRING_VALUE = "some other value";
		this.preferenceAdapter.setValue(ANOTHER_STRING_VALUE);
		assertEquals(ANOTHER_STRING_VALUE, this.preferenceAdapter.getValue());
		assertEquals(ANOTHER_STRING_VALUE, this.testNode.get(KEY_NAME, "<missing preference>"));
	}

	public void testHasListeners() throws Exception {
		assertTrue(this.preferenceAdapter.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(this.nodeHasAnyPrefListeners(this.testNode));
		this.preferenceAdapter.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener);
		assertFalse(this.nodeHasAnyPrefListeners(this.testNode));
		assertFalse(this.preferenceAdapter.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		ChangeListener listener2 = this.buildChangeListener();
		this.preferenceAdapter.addChangeListener(listener2);
		assertTrue(this.preferenceAdapter.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
		assertTrue(this.nodeHasAnyPrefListeners(this.testNode));
		this.preferenceAdapter.removeChangeListener(listener2);
		assertFalse(this.nodeHasAnyPrefListeners(this.testNode));
		assertFalse(this.preferenceAdapter.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	private ChangeListener buildChangeListener() {
		return new ChangeAdapter() {
			@Override
			public void propertyChanged(PropertyChangeEvent e) {
				if (PreferencePropertyValueModelTests.this.event != null) {
					throw new IllegalStateException("unexpected this.event: " + e);
				}
				PreferencePropertyValueModelTests.this.event = e;
			}
		};
	}

	public void testRemoveAndReAddPreference() throws Exception {
		assertEquals(STRING_VALUE, this.testNode.get(KEY_NAME, null));
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
		assertNull(this.event);

		// remove the preference entirely...
		this.testNode.remove(KEY_NAME);
		this.waitForEventQueueToClear();
		assertNull(this.testNode.get(KEY_NAME, null));
		this.verifyEvent(STRING_VALUE, null);
		assertNull(this.preferenceAdapter.getValue());

		// ...then re-add it with the same key
		this.event = null;
		this.testNode.put(KEY_NAME, STRING_VALUE);
		this.waitForEventQueueToClear();
		assertEquals(STRING_VALUE, this.testNode.get(KEY_NAME, null));
		this.verifyEvent(null, STRING_VALUE);
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
	}

	public void testDefaultValue() throws Exception {
		// rebuild the adapter with a default value
		String DEFAULT_VALUE = "default value";
		this.preferenceAdapter.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener);
		this.preferenceAdapter = PreferencePropertyValueModel.forString(this.nodeHolder, KEY_NAME, DEFAULT_VALUE);
		this.preferenceAdapter.addPropertyChangeListener(PropertyValueModel.VALUE, this.listener);

		assertEquals(STRING_VALUE, this.testNode.get(KEY_NAME, null));
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
		assertNull(this.event);

		// remove the preference entirely...
		this.testNode.remove(KEY_NAME);
		this.waitForEventQueueToClear();
		assertNull(this.testNode.get(KEY_NAME, null));
		this.verifyEvent(STRING_VALUE, DEFAULT_VALUE);
		assertEquals(DEFAULT_VALUE, this.preferenceAdapter.getValue());

		// ...then re-add it with the same key
		this.event = null;
		this.testNode.put(KEY_NAME, STRING_VALUE);
		this.waitForEventQueueToClear();
		assertEquals(STRING_VALUE, this.testNode.get(KEY_NAME, null));
		this.verifyEvent(DEFAULT_VALUE, STRING_VALUE);
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
	}

	public void testUnsynchronizedValue() throws Exception {
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
		assertNull(this.event);

		// remove the this.listener so the adapter no longer listens to the preference
		this.preferenceAdapter.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener);

		this.testNode.put(KEY_NAME, STRING_VALUE + STRING_VALUE);
		this.waitForEventQueueToClear();
		// no this.event should have been fired...
		assertNull(this.event);
		// ...and the adapter's value should be null
		assertNull(this.preferenceAdapter.getValue());
		
		this.testNode.remove(KEY_NAME);
		this.waitForEventQueueToClear();
		assertNull(this.event);
		assertNull(this.preferenceAdapter.getValue());
		
		this.testNode.put(KEY_NAME, STRING_VALUE);
		this.waitForEventQueueToClear();
		assertNull(this.event);
		assertNull(this.preferenceAdapter.getValue());

		// add the this.listener so the adapter synchs
		this.preferenceAdapter.addPropertyChangeListener(PropertyValueModel.VALUE, this.listener);
		assertEquals(STRING_VALUE, this.preferenceAdapter.getValue());
	}

	public void testIntegerPreference() throws Exception {
		// stop listening to the node and convert it to an integer
		this.preferenceAdapter.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener);

		PreferencePropertyValueModel<Integer> integerPreferenceAdapter = PreferencePropertyValueModel.forInteger(this.nodeHolder, KEY_NAME, 0);
		this.testNode.putInt(KEY_NAME, 123);
		integerPreferenceAdapter = PreferencePropertyValueModel.forInteger(this.testNode, KEY_NAME, 0);
		integerPreferenceAdapter.addPropertyChangeListener(PropertyValueModel.VALUE, this.listener);
		assertEquals(new Integer(123), integerPreferenceAdapter.getValue());
		assertNull(this.event);

		this.testNode.putInt(KEY_NAME, 246);
		this.waitForEventQueueToClear();
		this.verifyEvent(integerPreferenceAdapter, new Integer(123), new Integer(246));
		assertEquals(new Integer(246), integerPreferenceAdapter.getValue());
		
		this.event = null;
		this.testNode.remove(KEY_NAME);
		this.waitForEventQueueToClear();
		this.verifyEvent(integerPreferenceAdapter, new Integer(246), new Integer(0));
		assertEquals(new Integer(0), integerPreferenceAdapter.getValue());
		
		this.event = null;
		this.testNode.putInt(KEY_NAME, 123);
		this.waitForEventQueueToClear();
		this.verifyEvent(integerPreferenceAdapter, new Integer(0), new Integer(123));
		assertEquals(new Integer(123), integerPreferenceAdapter.getValue());
	}

	/**
	 * test a situation where
	 * - we are listening to the node when it gets removed from the preferences "repository"
	 * - we get notification that it has been removed
	 * - we try to remove our this.listener
	 * - the node will throw an IllegalStateException - the adapter should handle it OK...
	 */
	public void testRemoveNode() throws Exception {
		assertTrue(this.preferenceAdapter.hasAnyPropertyChangeListeners(PropertyValueModel.VALUE));

		Preferences parent = this.testNode.parent();
		parent.addNodeChangeListener(this.buildParentNodeChangeListener());
		this.testNode.removeNode();
		this.testNode.flush();		// this seems to be required for the this.event to trigger...
		this.waitForEventQueueToClear();

		assertTrue(this.listenerRemoved);
		assertTrue(this.preferenceAdapter.hasNoPropertyChangeListeners(PropertyValueModel.VALUE));
	}

	private NodeChangeListener buildParentNodeChangeListener() {
		return new NodeChangeListener() {
			public void childAdded(NodeChangeEvent e) {
				throw new IllegalStateException("unexpected this.event: " + e);
			}
			public void childRemoved(NodeChangeEvent e) {
				if (e.getChild() == PreferencePropertyValueModelTests.this.testNode) {
					PreferencePropertyValueModelTests.this.preferenceAdapter.removePropertyChangeListener(PropertyValueModel.VALUE, PreferencePropertyValueModelTests.this.listener);
					// this line of code will not execute if the line above triggers an exception
					PreferencePropertyValueModelTests.this.listenerRemoved = true;
				}
			}
		};
	}

	public void testSetSameValue() {
		assertNull(this.event);
		assertNull(this.preferenceEvent);
		this.testNode.addPreferenceChangeListener(this.buildPreferenceChangeListener());

		String ANOTHER_STRING_VALUE = "some other value";
		this.preferenceAdapter.setValue(ANOTHER_STRING_VALUE);

		this.verifyEvent(STRING_VALUE, ANOTHER_STRING_VALUE);
		this.waitForEventQueueToClear();
		this.verifyPreferenceEvent(ANOTHER_STRING_VALUE);

		// now set to *same* value - nothing should happen...
		this.event = null;
		this.preferenceEvent = null;
		this.preferenceAdapter.setValue(ANOTHER_STRING_VALUE);

		assertNull(this.event);
		assertNull(this.preferenceEvent);
	}

	public void testSetSameValueForcePassThrough() throws Exception {
		assertNull(this.event);
		assertNull(this.preferenceEvent);

		this.preferenceAdapter.removePropertyChangeListener(PropertyValueModel.VALUE, this.listener);
		this.preferenceAdapter = AlwaysUpdatePreferencePropertyValueModel.forString(this.nodeHolder, KEY_NAME, null);
		this.preferenceAdapter.addPropertyChangeListener(PropertyValueModel.VALUE, this.listener);

		this.testNode.addPreferenceChangeListener(this.buildPreferenceChangeListener());

		String ANOTHER_STRING_VALUE = "some other value";
		this.preferenceAdapter.setValue(ANOTHER_STRING_VALUE);

		this.verifyEvent(STRING_VALUE, ANOTHER_STRING_VALUE);
		this.waitForEventQueueToClear();
		this.verifyPreferenceEvent(ANOTHER_STRING_VALUE);

		// now set to *same* value - only one this.event should fire
		this.event = null;
		this.preferenceEvent = null;
		this.preferenceAdapter.setValue(ANOTHER_STRING_VALUE);

		assertNull(this.event);
		this.waitForEventQueueToClear();
		this.verifyPreferenceEvent(ANOTHER_STRING_VALUE);
		assertNull(this.event);
	}

	private PreferenceChangeListener buildPreferenceChangeListener() {
		return new PreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent evt) {
				PreferencePropertyValueModelTests.this.preferenceEvent = evt;
			}
		};
	}

	private void verifyEvent(Model source, Object oldValue, Object newValue) {
		assertNotNull(this.event);
		assertEquals(source, this.event.getSource());
		assertEquals(PropertyValueModel.VALUE, this.event.getPropertyName());
		assertEquals(oldValue, this.event.getOldValue());
		assertEquals(newValue, this.event.getNewValue());
	}

	private void verifyEvent(Object oldValue, Object newValue) {
		this.verifyEvent(this.preferenceAdapter, oldValue, newValue);
	}

	private void verifyPreferenceEvent(Object newValue) {
		assertNotNull(this.preferenceEvent);
		assertEquals(this.testNode, this.preferenceEvent.getSource());
		assertEquals(KEY_NAME, this.preferenceEvent.getKey());
		assertEquals(newValue, this.preferenceEvent.getNewValue());
		assertEquals(newValue, this.testNode.get(KEY_NAME, "<missing preference>"));
	}

	private boolean nodeHasAnyPrefListeners(Preferences node) throws Exception {
		PreferenceChangeListener[] prefListeners = (PreferenceChangeListener[]) ObjectTools.get(node, "prefListeners");
		return prefListeners.length > 0;
	}


	/**
	 * Use this adapter to test out always passing through the new value
	 * to the preference.
	 */
	/* CU private */ static class AlwaysUpdatePreferencePropertyValueModel<P>
		extends PreferencePropertyValueModel<P>
	{
		public static PreferencePropertyValueModel<String> forString(PropertyValueModel<? extends Preferences> preferencesModel, String key, String defaultValue) {
			return new AlwaysUpdatePreferencePropertyValueModel<String>(
					preferencesModel,
					key,
					defaultValue,
					Transformer.Null.<String>instance()
				);
		}

		AlwaysUpdatePreferencePropertyValueModel(
				PropertyValueModel<? extends Preferences> preferencesModel,
				String key,
				P defaultValue,
				Transformer<String, P> stringTransformer) {
			super(preferencesModel, key, defaultValue, stringTransformer);
		}

		@Override
		protected boolean preferenceIsToBeSet(Object oldValue, Object newValue) {
			return true;
		}
	}
}
