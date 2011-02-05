/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.persistence;

import java.util.ListIterator;

import org.eclipse.jpt.common.utility.model.event.ListAddEvent;
import org.eclipse.jpt.common.utility.model.event.ListChangeEvent;
import org.eclipse.jpt.common.utility.model.event.ListClearEvent;
import org.eclipse.jpt.common.utility.model.event.ListMoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListRemoveEvent;
import org.eclipse.jpt.common.utility.model.event.ListReplaceEvent;
import org.eclipse.jpt.common.utility.model.listener.ListChangeListener;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;

/**
 *  GenericOptions2_0Tests
 */
@SuppressWarnings("nls")
public class Generic2_0OptionsTests extends Generic2_0PersistenceUnitTests
{
	private JpaOptions2_0 options;
	private ListChangeEvent prePersistEvent;
	private ListChangeEvent preUpdateEvent;
	private ListChangeEvent preRemoveEvent;

	public static final String LOCK_TIMEOUT_KEY = JpaOptions2_0.PERSISTENCE_LOCK_TIMEOUT;
	public static final Integer LOCK_TIMEOUT_TEST_VALUE = 100;
	public static final Integer LOCK_TIMEOUT_TEST_VALUE_2 = 200;

	public static final String QUERY_TIMEOUT_KEY = JpaOptions2_0.PERSISTENCE_QUERY_TIMEOUT;
	public static final Integer QUERY_TIMEOUT_TEST_VALUE = 100;
	public static final Integer QUERY_TIMEOUT_TEST_VALUE_2 = 200;
	
	public static final String VALIDATION_GROUP_PRE_PERSIST_KEY = JpaOptions2_0.PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST;
	public static final String VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE = "test_pre-persist_group";
	public static final String VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE_2 = "test_2_pre-persist_group";
	
	public static final String VALIDATION_GROUP_PRE_UPDATE_KEY = JpaOptions2_0.PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE;
	public static final String VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE = "test_pre-update_group";
	public static final String VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE_2 = "test_2_pre-update_group";
	
	public static final String VALIDATION_GROUP_PRE_REMOVE_KEY = JpaOptions2_0.PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE;
	public static final String VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE = "test_pre-remove_group";
	public static final String VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE_2 = "test_2_pre-remove_group";

	// ********** constructors **********
	public Generic2_0OptionsTests(String name) {
		super(name);
	}

	// ********** behavior **********
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.options = (JpaOptions2_0) this.subject.getOptions();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();

		this.options.addPropertyChangeListener(JpaOptions2_0.LOCK_TIMEOUT_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(JpaOptions2_0.QUERY_TIMEOUT_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY, propertyChangeListener);

		ListChangeListener validationGroupListChangeListener = this.buildValidationGroupListChangeListener();
		this.options.addListChangeListener(JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_LIST, validationGroupListChangeListener);
		this.options.addListChangeListener(JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_LIST, validationGroupListChangeListener);
		this.options.addListChangeListener(JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_LIST, validationGroupListChangeListener);
		
		this.clearEvent();
	}

	/**
	 * Initializes directly the PersistenceUnit properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 5; // PersistenceUnit properties
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 1; // 1 misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		// Initializes PersistenceUnit properties
		this.persistenceUnitSetProperty(LOCK_TIMEOUT_KEY, LOCK_TIMEOUT_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(QUERY_TIMEOUT_KEY, QUERY_TIMEOUT_TEST_VALUE.toString());
		this.persistenceUnitSetProperty("misc.property.3", "value.3");

		this.persistenceUnitSetProperty(VALIDATION_GROUP_PRE_PERSIST_KEY, VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE);
		this.persistenceUnitSetProperty(VALIDATION_GROUP_PRE_UPDATE_KEY, VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE);
		this.persistenceUnitSetProperty(VALIDATION_GROUP_PRE_REMOVE_KEY, VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE);
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.options;
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(JpaOptions2_0.LOCK_TIMEOUT_PROPERTY))
			modelValue = this.options.getLockTimeout();
		else if (propertyName.equals(JpaOptions2_0.QUERY_TIMEOUT_PROPERTY))
			modelValue = this.options.getQueryTimeout();
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY)) {
			ListIterator<String> iterator = this.options.validationGroupPrePersists();
			modelValue = this.getFirstElement(iterator);
		}
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY)) {
			ListIterator<String> iterator = this.options.validationGroupPreUpdates();
			modelValue = this.getFirstElement(iterator);
		}
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY)) {
			ListIterator<String> iterator = this.options.validationGroupPreRemoves();
			modelValue = this.getFirstElement(iterator);
		}
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}
	
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(JpaOptions2_0.LOCK_TIMEOUT_PROPERTY))
			this.options.setLockTimeout((Integer) newValue);
		else if (propertyName.equals(JpaOptions2_0.QUERY_TIMEOUT_PROPERTY))
			this.options.setQueryTimeout((Integer) newValue);
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY))
			this.options.addValidationGroupPrePersist((String) newValue);
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY))
			this.options.addValidationGroupPreUpdate((String) newValue);
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY))
			this.options.addValidationGroupPreRemove((String) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}
	
	// ********** LockTimeout tests **********
	public void testSetLockTimeout() throws Exception {
		this.verifyModelInitialized(
			LOCK_TIMEOUT_KEY,
			LOCK_TIMEOUT_TEST_VALUE);
		this.verifySetProperty(
			LOCK_TIMEOUT_KEY,
			LOCK_TIMEOUT_TEST_VALUE,
			LOCK_TIMEOUT_TEST_VALUE_2);
	}

	public void testAddRemoveLockTimeout() throws Exception {
		this.verifyAddRemoveProperty(
			LOCK_TIMEOUT_KEY,
			LOCK_TIMEOUT_TEST_VALUE,
			LOCK_TIMEOUT_TEST_VALUE_2);
	}
	
	// ********** QueryTimeout tests **********
	public void testSetQueryTimeout() throws Exception {
		this.verifyModelInitialized(
			QUERY_TIMEOUT_KEY,
			QUERY_TIMEOUT_TEST_VALUE);
		this.verifySetProperty(
			QUERY_TIMEOUT_KEY,
			QUERY_TIMEOUT_TEST_VALUE,
			QUERY_TIMEOUT_TEST_VALUE_2);
	}

	public void testAddRemoveQueryTimeout() throws Exception {
		this.verifyAddRemoveProperty(
			QUERY_TIMEOUT_KEY,
			QUERY_TIMEOUT_TEST_VALUE,
			QUERY_TIMEOUT_TEST_VALUE_2);
	}

	// ********** ValidationGroupPrePersist tests **********
	public void testSetValidationGroupPrePersist() throws Exception {
		this.verifyModelInitialized(
			VALIDATION_GROUP_PRE_PERSIST_KEY,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE);
		this.verifySetValidationGroupProperty(
			JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY,
			VALIDATION_GROUP_PRE_PERSIST_KEY,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE_2);
	}

	public void testAddRemoveValidationGroupPrePersist() throws Exception {
		this.verifyAddRemoveValidationGroupProperty(
			JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY,
			VALIDATION_GROUP_PRE_PERSIST_KEY,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE_2);
	}

	public void testAddValidationGroupPrePersistCompositeValue() throws Exception {
		this.verifyAddCompositeValue(
			JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY,
			VALIDATION_GROUP_PRE_PERSIST_KEY,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE_2);
	}

	public void testRemoveValidationGroupPrePersistCompositeValue() throws Exception {
		this.verifyRemoveCompositeValue(
			JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY,
			VALIDATION_GROUP_PRE_PERSIST_KEY,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE_2);
	}

	// ********** ValidationGroupPreUpdate tests **********
	public void testSetValidationGroupPreUpdate() throws Exception {
		this.verifyModelInitialized(
			VALIDATION_GROUP_PRE_UPDATE_KEY,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE);
		this.verifySetValidationGroupProperty(
			JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY,
			VALIDATION_GROUP_PRE_UPDATE_KEY,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE_2);
	}

	public void testAddRemoveValidationGroupPreUpdate() throws Exception {
		this.verifyAddRemoveValidationGroupProperty(
			JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY,
			VALIDATION_GROUP_PRE_UPDATE_KEY,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE_2);
	}

	public void testAddValidationGroupPreUpdateCompositeValue() throws Exception {
		this.verifyAddCompositeValue(
			JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY,
			VALIDATION_GROUP_PRE_UPDATE_KEY,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE_2);
	}

	public void testRemoveValidationGroupPreUpdateCompositeValue() throws Exception {
		this.verifyRemoveCompositeValue(
			JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY,
			VALIDATION_GROUP_PRE_UPDATE_KEY,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE_2);
	}

	// ********** ValidationGroupPreRemove tests **********
	public void testSetValidationGroupPreRemove() throws Exception {
		this.verifyModelInitialized(
			VALIDATION_GROUP_PRE_REMOVE_KEY,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE);
		this.verifySetValidationGroupProperty(
			JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY,
			VALIDATION_GROUP_PRE_REMOVE_KEY,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE_2);
	}

	public void testAddRemoveValidationGroupPreRemove() throws Exception {
		this.verifyAddRemoveValidationGroupProperty(
			JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY,
			VALIDATION_GROUP_PRE_REMOVE_KEY,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE_2);
	}

	public void testAddValidationGroupPreRemoveCompositeValue() throws Exception {
		this.verifyAddCompositeValue(
			JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY,
			VALIDATION_GROUP_PRE_REMOVE_KEY,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE_2);
	}

	public void testRemoveValidationGroupPreRemoveCompositeValue() throws Exception {
		this.verifyRemoveCompositeValue(
			JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY,
			VALIDATION_GROUP_PRE_REMOVE_KEY,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE_2);
	}

	// ********** ValidationGroups list **********
	public void testValidationGroupsList() throws Exception {
		this.verifyListEvents(
			JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE_2,
			JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_LIST);
		
		this.verifyListEvents(
			JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE_2,
			JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_LIST);
		
		this.verifyListEvents(
			JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE_2,
			JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_LIST);
	}

	// ********** override **********

	@Override
	protected void clearEvent() {
		super.clearEvent();
		this.prePersistEvent = null;
		this.preUpdateEvent = null;
		this.preRemoveEvent = null;
	}

	// ****** convenience methods *******

	// ********** verify ValidationGroup property **********
	protected void verifySetValidationGroupProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		// Replace
		this.persistenceUnitSetProperty(key, testValue2, true); 
		this.propertiesTotal++;
		this.verifyPutValidationGroupProperty(propertyName, testValue1);
	}

	@SuppressWarnings("unused")
	protected void verifyAddRemoveValidationGroupProperty(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		assertTrue(this.validationGroupValueExists(propertyName, (String)testValue1));
		// Remove
		this.clearEvent();
		--this.propertiesTotal;
		--this.modelPropertiesSize;
		this.getPersistenceUnit().removeProperty(key, (String)testValue1);
		assertFalse(this.validationGroupValueExists(propertyName, (String)testValue1));
		assertEquals(this.modelPropertiesSize, this.modelPropertiesSizeOriginal - 1);
		
		// Add original Property
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.persistenceUnitSetProperty(key, testValue1, true); 
		this.verifyPutValidationGroupProperty(propertyName, testValue1);
	}

	protected void verifyAddCompositeValue(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		String stringTestValue1 = (String)testValue1;
		String stringTestValue2 = (String)testValue2;
		assertTrue(this.validationGroupValueExists(propertyName, stringTestValue1));
		assertEquals(this.getValidationGroupSize(propertyName), 1);
		this.addValidationGroupValue(propertyName, stringTestValue2);
		assertEquals(this.getValidationGroupSize(propertyName), 2);

		assertTrue(this.validationGroupValueExists(propertyName, stringTestValue1));
		assertTrue(this.validationGroupValueExists(propertyName, stringTestValue2));

		String propertyValue = this.getPersistenceUnit().getProperty(key).getValue();
		assertTrue(propertyValue.indexOf(stringTestValue1) != -1);
		assertTrue(propertyValue.indexOf(stringTestValue2) != -1);
	}

	protected void verifyRemoveCompositeValue(String propertyName, String key, Object testValue1, Object testValue2) throws Exception {
		String stringTestValue1 = (String)testValue1;
		String stringTestValue2 = (String)testValue2;
		assertTrue(this.validationGroupValueExists(propertyName, stringTestValue1));
		assertEquals(this.getValidationGroupSize(propertyName), 1);
		this.addValidationGroupValue(propertyName, stringTestValue2);
		assertEquals(this.getValidationGroupSize(propertyName), 2);

		this.removeValidationGroupValue(propertyName, stringTestValue2);
		assertEquals(this.getValidationGroupSize(propertyName), 1);
		assertFalse(this.validationGroupValueExists(propertyName, stringTestValue2));
		String propertyValue = this.getPersistenceUnit().getProperty(key).getValue();
		assertTrue(propertyValue.indexOf(stringTestValue1) != -1);
		assertTrue(propertyValue.indexOf(stringTestValue2) == -1);
		
		this.removeValidationGroupValue(propertyName, stringTestValue2);
		assertEquals(this.getValidationGroupSize(propertyName), 1);
		
		this.removeValidationGroupValue(propertyName, stringTestValue1);
		assertEquals(this.getValidationGroupSize(propertyName), 0);
		assertFalse(this.validationGroupValueExists(propertyName, stringTestValue1));
		
		assertNull(this.getPersistenceUnit().getProperty(key));
	}

	protected void verifyListEvents(String propertyName, String testValue, String listName) throws Exception {
		// add
		this.clearEvent();
		this.addValidationGroupValue(propertyName, testValue);
		
		// verify event received
		assertNotNull("No Event Fired.", this.getEventFor(propertyName));
		// verify event for the expected property
		assertEquals("Wrong Event.", this.getEventFor(propertyName).getListName(), listName);

		// remove
		this.clearEvent();
		
		this.removeValidationGroupValue(propertyName, testValue);
		// verify event received
		assertNotNull("No Event Fired.", this.getEventFor(propertyName));
		// verify event for the expected property
		assertEquals("Wrong Event.", this.getEventFor(propertyName).getListName(), listName);
	}
	
	protected void verifyPutValidationGroupProperty(String propertyName, Object expectedValue) throws Exception {
		// verify event received
		assertNotNull("No Event Fired.", this.getEventFor(propertyName));
		// verify event value
		assertTrue(this.validationGroupValueExists(propertyName, (String) expectedValue));
		 return;
	}
	
	// ********** internal method **********

	private ListChangeListener buildValidationGroupListChangeListener() {
		return new ListChangeListener() {
			public void itemsAdded(ListAddEvent e) {
				Generic2_0OptionsTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsRemoved(ListRemoveEvent e) {
				Generic2_0OptionsTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsReplaced(ListReplaceEvent e) {
				Generic2_0OptionsTests.this.throwUnsupportedOperationException(e);
			}

			public void itemsMoved(ListMoveEvent e) {
				Generic2_0OptionsTests.this.throwUnsupportedOperationException(e);
			}

			public void listCleared(ListClearEvent e) {
				Generic2_0OptionsTests.this.throwUnsupportedOperationException(e);
			}

			public void listChanged(ListChangeEvent e) {
				
				Generic2_0OptionsTests.this.validationGroupChanged(e);
			}
		};
	}

	private void validationGroupChanged(ListChangeEvent e) {
		String listName = e.getListName();
		
		if (listName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_LIST))
			this.prePersistEvent = e;
		else if (listName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_LIST))
			this.preUpdateEvent = e;
		else if (listName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_LIST))
			this.preRemoveEvent = e;
		else
			this.throwUnsupportedOperationException(e);
	}

	private void addValidationGroupValue(String propertyName, String propertyValue) throws NoSuchFieldException {
		if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY))
			this.options.addValidationGroupPrePersist(propertyValue);
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY))
			this.options.addValidationGroupPreUpdate(propertyValue);
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY))
			this.options.addValidationGroupPreRemove(propertyValue);
		else
			this.throwMissingDefinition("addValidationGroupValue", propertyName);
	}

	private void removeValidationGroupValue(String propertyName, String propertyValue) throws NoSuchFieldException {
		if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY))
			this.options.removeValidationGroupPrePersist(propertyValue);
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY))
			this.options.removeValidationGroupPreUpdate(propertyValue);
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY))
			this.options.removeValidationGroupPreRemove(propertyValue);
		else
			this.throwMissingDefinition("removeValidationGroupValue", propertyName);
	}

	private boolean validationGroupValueExists(String propertyName, String propertyValue) throws NoSuchFieldException {
		boolean result = false;
		if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY))
			result = this.options.validationGroupPrePersistExists(propertyValue);
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY))
			result = this.options.validationGroupPreUpdateExists(propertyValue);
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY))
			result = this.options.validationGroupPreRemoveExists(propertyValue);
		else
			this.throwMissingDefinition("verifyValidationGroupValueExists", propertyName);
		return result;
	}

	private int getValidationGroupSize(String propertyName) throws NoSuchFieldException {
		int result = 0;
		if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY))
			result =  this.options.validationGroupPrePersistsSize();
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY))
			result =  this.options.validationGroupPreUpdatesSize();
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY))
			result =  this.options.validationGroupPreRemovesSize();
		else
			this.throwMissingDefinition("verifyValidationGroupSize", propertyName);
		return result;
	}
	
	private ListChangeEvent getEventFor(String propertyName) throws NoSuchFieldException {
		ListChangeEvent event = null;
		if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY))
			event = this.prePersistEvent;
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY))
			event = this.preUpdateEvent;
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY))
			event = this.preRemoveEvent;
		else
			this.throwMissingDefinition("getEventFor", propertyName);
		return event;
	}

}
