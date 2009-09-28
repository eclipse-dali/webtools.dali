/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink2_0.core.tests.internal.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.ValidationMode;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;

/**
 *  EclipseLinkOptions2_0Tests
 */
public class EclipseLink2_0OptionsTests extends EclipseLink2_0PersistenceUnitTestCase
{
	private Options2_0 options;

	public static final String LOCK_TIMEOUT_KEY = Options2_0.PERSISTENCE_LOCK_TIMEOUT;
	public static final Integer LOCK_TIMEOUT_TEST_VALUE = 100;
	public static final Integer LOCK_TIMEOUT_TEST_VALUE_2 = 200;

	public static final String QUERY_TIMEOUT_KEY = Options2_0.PERSISTENCE_QUERY_TIMEOUT;
	public static final Integer QUERY_TIMEOUT_TEST_VALUE = 100;
	public static final Integer QUERY_TIMEOUT_TEST_VALUE_2 = 200;

	public static final String VALIDATION_MODE_KEY = JpaOptions2_0.PERSISTENCE_VALIDATION_MODE;
	public static final ValidationMode VALIDATION_MODE_TEST_VALUE = ValidationMode.none;
	public static final ValidationMode VALIDATION_MODE_TEST_VALUE_2 = ValidationMode.callback;

	public static final String VALIDATION_GROUP_PRE_PERSIST_KEY = Options2_0.PERSISTENCE_VALIDATION_GROUP_PRE_PERSIST;
	public static final String VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE = "test_pre-persist_group";
	public static final String VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE_2 = "test_2_pre-persist_group";
	
	public static final String VALIDATION_GROUP_PRE_UPDATE_KEY = Options2_0.PERSISTENCE_VALIDATION_GROUP_PRE_UPDATE;
	public static final String VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE = "test_pre-update_group";
	public static final String VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE_2 = "test_2_pre-update_group";
	
	public static final String VALIDATION_GROUP_PRE_REMOVE_KEY = Options2_0.PERSISTENCE_VALIDATION_GROUP_PRE_REMOVE;
	public static final String VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE = "test_pre-remove_group";
	public static final String VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE_2 = "test_2_pre-remove_group";

	// ********** constructors **********
	public EclipseLink2_0OptionsTests(String name) {
		super(name);
	}

	// ********** behavior **********
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.options = this.subject.getOptions();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();

		this.options.addPropertyChangeListener(Options2_0.LOCK_TIMEOUT_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options2_0.QUERY_TIMEOUT_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(JpaOptions2_0.VALIDATION_MODE_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY, propertyChangeListener);

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
		this.persistenceUnitSetProperty("misc.property.1", "value.1");
		this.persistenceUnitSetProperty(LOCK_TIMEOUT_KEY, LOCK_TIMEOUT_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(QUERY_TIMEOUT_KEY, QUERY_TIMEOUT_TEST_VALUE.toString());

		this.getPersistenceUnit().setSpecifiedValidationMode(VALIDATION_MODE_TEST_VALUE);		// test ValidationMode element 
		this.persistenceUnitSetProperty(VALIDATION_GROUP_PRE_PERSIST_KEY, VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(VALIDATION_GROUP_PRE_UPDATE_KEY, VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(VALIDATION_GROUP_PRE_REMOVE_KEY, VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE.toString());
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.options;
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(Options2_0.LOCK_TIMEOUT_PROPERTY))
			modelValue = this.options.getLockTimeout();
		else if (propertyName.equals(Options2_0.QUERY_TIMEOUT_PROPERTY))
			modelValue = this.options.getQueryTimeout();
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_MODE_PROPERTY))
			modelValue = this.options.getValidationMode();
		else if (propertyName.equals(Options2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY))
			modelValue = this.options.getValidationGroupPrePersist();
		else if (propertyName.equals(Options2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY))
			modelValue = this.options.getValidationGroupPreUpdate();
		else if (propertyName.equals(Options2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY))
			modelValue = this.options.getValidationGroupPreRemove();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}

	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(Options2_0.LOCK_TIMEOUT_PROPERTY))
			this.options.setLockTimeout((Integer) newValue);
		else if (propertyName.equals(Options2_0.QUERY_TIMEOUT_PROPERTY))
			this.options.setQueryTimeout((Integer) newValue);
		else if (propertyName.equals(JpaOptions2_0.VALIDATION_MODE_PROPERTY))
			this.options.setValidationMode((ValidationMode) newValue);
		else if (propertyName.equals(Options2_0.VALIDATION_GROUP_PRE_PERSIST_PROPERTY))
			this.options.setValidationGroupPrePersist((String) newValue);
		else if (propertyName.equals(Options2_0.VALIDATION_GROUP_PRE_UPDATE_PROPERTY))
			this.options.setValidationGroupPreUpdate((String) newValue);
		else if (propertyName.equals(Options2_0.VALIDATION_GROUP_PRE_REMOVE_PROPERTY))
			this.options.setValidationGroupPreRemove((String) newValue);
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
	
	// ********** ValidationMode tests **********
	public void testSetValidationMode() throws Exception {
		this.verifySetValidationMode(
			VALIDATION_MODE_TEST_VALUE,
			VALIDATION_MODE_TEST_VALUE_2);
	}

	public void testAddRemoveValidationMode() throws Exception {
		this.verifyAddRemoveValidationMode(
			VALIDATION_MODE_KEY,
			VALIDATION_MODE_TEST_VALUE,
			VALIDATION_MODE_TEST_VALUE_2);
	}
	
	/**
	 * Verify if ValidationMode element is set
	 */
	private void verifySetValidationMode(ValidationMode testValue1, ValidationMode testValue2) throws Exception {
		
		this.getPersistenceUnit().setSpecifiedValidationMode(testValue2);
		assertEquals(testValue2, this.getPersistenceUnit().getSpecifiedValidationMode());
		// Replace
		this.options.setValidationMode(testValue1);
		assertEquals(testValue1, this.getPersistenceUnit().getSpecifiedValidationMode());
		assertNull(this.options.getValidationMode());
		
		// Replace by setting model object
		this.options.setValidationMode(testValue2);
		assertEquals(testValue2, this.getPersistenceUnit().getValidationMode());
	}
	
	/**
	 * Add & Remove ValidationMode
	 * Verify if ValidationMode element is added
	 */
	private void verifyAddRemoveValidationMode(String puKey, ValidationMode testValue1, ValidationMode testValue2) throws Exception {
		// Remove
		this.clearEvent();
		assertNull(this.getPersistenceUnit().getProperty(puKey));
		this.getPersistenceUnit().setSpecifiedValidationMode(null);
		assertEquals(this.getPersistenceUnit().getDefaultValidationMode(), this.getPersistenceUnit().getValidationMode());
		
		// Add original 
		++this.propertiesTotal;
		++this.modelPropertiesSize;
		this.options.setValidationMode(testValue1);
		assertEquals(testValue1, this.getPersistenceUnit().getValidationMode());
		
		// Replace
		this.options.setValidationMode(testValue2);
		assertEquals(testValue2, this.getPersistenceUnit().getValidationMode());
	}

	// ********** ValidationGroupPrePersist tests **********
	public void testSetValidationGroupPrePersist() throws Exception {
		this.verifyModelInitialized(
			VALIDATION_GROUP_PRE_PERSIST_KEY,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE);
		this.verifySetProperty(
			VALIDATION_GROUP_PRE_PERSIST_KEY,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE_2);
	}

	public void testAddRemoveValidationGroupPrePersist() throws Exception {
		this.verifyAddRemoveProperty(
			VALIDATION_GROUP_PRE_PERSIST_KEY,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE,
			VALIDATION_GROUP_PRE_PERSIST_TEST_VALUE_2);
	}

	// ********** ValidationGroupPreUpdate tests **********
	public void testSetValidationGroupPreUpdate() throws Exception {
		this.verifyModelInitialized(
			VALIDATION_GROUP_PRE_UPDATE_KEY,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE);
		this.verifySetProperty(
			VALIDATION_GROUP_PRE_UPDATE_KEY,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE_2);
	}

	public void testAddRemoveValidationGroupPreUpdate() throws Exception {
		this.verifyAddRemoveProperty(
			VALIDATION_GROUP_PRE_UPDATE_KEY,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE,
			VALIDATION_GROUP_PRE_UPDATE_TEST_VALUE_2);
	}

	// ********** ValidationGroupPreRemove tests **********
	public void testSetValidationGroupPreRemove() throws Exception {
		this.verifyModelInitialized(
			VALIDATION_GROUP_PRE_REMOVE_KEY,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE);
		this.verifySetProperty(
			VALIDATION_GROUP_PRE_REMOVE_KEY,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE_2);
	}

	public void testAddRemoveValidationGroupPreRemove() throws Exception {
		this.verifyAddRemoveProperty(
			VALIDATION_GROUP_PRE_REMOVE_KEY,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE,
			VALIDATION_GROUP_PRE_REMOVE_TEST_VALUE_2);
	}
	
}
