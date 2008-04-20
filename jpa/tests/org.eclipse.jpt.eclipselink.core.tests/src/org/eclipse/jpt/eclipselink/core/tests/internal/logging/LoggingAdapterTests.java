/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.logging;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.persistence.GenericProperty;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkJpaProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitPropertyListListener;
import org.eclipse.jpt.eclipselink.core.internal.context.logging.EclipseLinkLogging;
import org.eclipse.jpt.eclipselink.core.internal.context.logging.Logger;
import org.eclipse.jpt.eclipselink.core.internal.context.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.logging.LoggingLevel;
import org.eclipse.jpt.eclipselink.core.tests.internal.PersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * Tests the update of model objects by the Logging adapter when the
 * PersistenceUnit changes.
 */
public class LoggingAdapterTests extends PersistenceUnitTestCase
{
	private Logging logging;

	public static final String LEVEL_KEY = Logging.ECLIPSELINK_LEVEL;
	public static final LoggingLevel LEVEL_TEST_VALUE = LoggingLevel.fine;
	public static final LoggingLevel LEVEL_TEST_VALUE_2 = LoggingLevel.finest;

	public static final String TIMESTAMP_KEY = Logging.ECLIPSELINK_TIMESTAMP;
	public static final Boolean TIMESTAMP_TEST_VALUE = false;
	public static final Boolean TIMESTAMP_TEST_VALUE_2 = ! TIMESTAMP_TEST_VALUE;

	public static final String THREAD_KEY = Logging.ECLIPSELINK_THREAD;
	public static final Boolean THREAD_TEST_VALUE = false;
	public static final Boolean THREAD_TEST_VALUE_2 = ! THREAD_TEST_VALUE;

	public static final String SESSION_KEY = Logging.ECLIPSELINK_SESSION;
	public static final Boolean SESSION_TEST_VALUE = false;
	public static final Boolean SESSION_TEST_VALUE_2 = ! SESSION_TEST_VALUE;

	public static final String EXCEPTIONS_KEY = Logging.ECLIPSELINK_EXCEPTIONS;
	public static final Boolean EXCEPTIONS_TEST_VALUE = false;
	public static final Boolean EXCEPTIONS_TEST_VALUE_2 = ! EXCEPTIONS_TEST_VALUE;

	private static final String LOG_FILE_LOCATION_KEY = Logging.ECLIPSELINK_LOG_FILE_LOCATION;
	private static final String LOG_FILE_LOCATION_TEST_VALUE = "C:/temp";
	private static final String LOG_FILE_LOCATION_TEST_VALUE_2 = "C:/tmp";

	private static final String LOGGER_KEY = Logging.ECLIPSELINK_LOGGER;
	private static final Logger LOGGER_TEST_VALUE = Logger.java_logger;
	private static final String LOGGER_TEST_VALUE_2 = "custom.logger.test";
	
	public LoggingAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.logging = this.persistenceUnitProperties.getLogging();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.logging.addPropertyChangeListener(Logging.LEVEL_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging.TIMESTAMP_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging.THREAD_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging.SESSION_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging.EXCEPTIONS_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging.LOG_FILE_LOCATION_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(Logging.LOGGER_PROPERTY, propertyChangeListener);

		this.clearEvent();
	}

	/**
	 * Initializes directly the PU properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 7;
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 4; // 4 misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		this.persistenceUnitPut("misc.property.1", "value.1");
		this.persistenceUnitPut(TIMESTAMP_KEY, TIMESTAMP_TEST_VALUE.toString());
		this.persistenceUnitPut("misc.property.2", "value.2");
		this.persistenceUnitPut(LEVEL_KEY, LEVEL_TEST_VALUE);
		this.persistenceUnitPut("misc.property.3", "value.3");
		this.persistenceUnitPut(THREAD_KEY, THREAD_TEST_VALUE.toString());
		this.persistenceUnitPut(SESSION_KEY, SESSION_TEST_VALUE.toString());
		this.persistenceUnitPut(EXCEPTIONS_KEY, EXCEPTIONS_TEST_VALUE.toString());
		this.persistenceUnitPut("misc.property.4", "value.4");
		this.persistenceUnitPut(LOG_FILE_LOCATION_KEY, LOG_FILE_LOCATION_TEST_VALUE);
		this.persistenceUnitPut(LOGGER_KEY, LOGGER_TEST_VALUE);
		return;
	}
	
	// ********** Listeners **********

	// ********** Listeners tests **********
	public void testHasListeners() throws Exception {
		// new
		ListAspectAdapter<PersistenceUnit, Property> propertiesAdapter = 
			(ListAspectAdapter<PersistenceUnit, Property>) ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertiesAdapter();
		GenericProperty ctdProperty = (GenericProperty) this.persistenceUnit().getProperty(TIMESTAMP_KEY);
		ListValueModel<Property> propertyListAdapter = ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertyListAdapter();
		
		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(ctdProperty.hasAnyPropertyChangeListeners(Property.VALUE_PROPERTY));
		this.verifyHasListeners(this.logging, Logging.TIMESTAMP_PROPERTY);
//TODO ADD OTHER PROPERTIES
		this.verifyHasListeners(propertyListAdapter);
		
		EclipseLinkLogging elLogging = (EclipseLinkLogging) this.logging;
		PersistenceUnitPropertyListListener propertyListListener = elLogging.propertyListListener();
		propertyListAdapter.removeListChangeListener(ListValueModel.LIST_VALUES, propertyListListener);
		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES)); // other properties are still listening
		this.verifyHasListeners(this.logging, Logging.TIMESTAMP_PROPERTY);
	}


	// ********** Level tests **********
	public void testSetLevel() throws Exception {
		this.verifyModelInitialized(
			LEVEL_KEY,
			LEVEL_TEST_VALUE);
		this.verifySetProperty(
			LEVEL_KEY,
			LEVEL_TEST_VALUE,
			LEVEL_TEST_VALUE_2);
	}

	public void testAddRemoveLevel() throws Exception {
		this.verifyAddRemoveProperty(
			LEVEL_KEY,
			LEVEL_TEST_VALUE,
			LEVEL_TEST_VALUE_2);
	}

	// ********** Timestamp tests **********
	public void testSetThrowExceptions() throws Exception {
		this.verifyModelInitialized(
			TIMESTAMP_KEY,
			TIMESTAMP_TEST_VALUE);
		this.verifySetProperty(
			TIMESTAMP_KEY,
			TIMESTAMP_TEST_VALUE,
			TIMESTAMP_TEST_VALUE_2);
	}

	public void testAddRemoveThrowExceptions() throws Exception {
		this.verifyAddRemoveProperty(
			TIMESTAMP_KEY,
			TIMESTAMP_TEST_VALUE,
			TIMESTAMP_TEST_VALUE_2);
	}

	// ********** Thread tests **********
	public void testSetThread() throws Exception {
		this.verifyModelInitialized(
			THREAD_KEY,
			THREAD_TEST_VALUE);
		this.verifySetProperty(
			THREAD_KEY,
			THREAD_TEST_VALUE,
			THREAD_TEST_VALUE_2);
	}

	public void testAddRemoveThread() throws Exception {
		this.verifyAddRemoveProperty(
			THREAD_KEY,
			THREAD_TEST_VALUE,
			THREAD_TEST_VALUE_2);
	}

	// ********** Session tests **********
	public void testSetSession() throws Exception {
		this.verifyModelInitialized(
			SESSION_KEY,
			SESSION_TEST_VALUE);
		this.verifySetProperty(
			SESSION_KEY,
			SESSION_TEST_VALUE,
			SESSION_TEST_VALUE_2);
	}

	public void testAddRemoveSession() throws Exception {
		this.verifyAddRemoveProperty(
			SESSION_KEY,
			SESSION_TEST_VALUE,
			SESSION_TEST_VALUE_2);
	}

	// ********** Exceptions tests **********
	public void testSetExceptions() throws Exception {
		this.verifyModelInitialized(
			EXCEPTIONS_KEY,
			EXCEPTIONS_TEST_VALUE);
		this.verifySetProperty(
			EXCEPTIONS_KEY,
			EXCEPTIONS_TEST_VALUE,
			EXCEPTIONS_TEST_VALUE_2);
	}
	
	public void testAddRemoveExceptions() throws Exception {
		this.verifyAddRemoveProperty(
			EXCEPTIONS_KEY,
			EXCEPTIONS_TEST_VALUE,
			EXCEPTIONS_TEST_VALUE_2);
	}

	// ********** LogFileLocation **********
	public void testSetLogFileLocation() throws Exception {
		this.verifyModelInitialized(
			LOG_FILE_LOCATION_KEY,
			LOG_FILE_LOCATION_TEST_VALUE);
		this.verifySetProperty(
			LOG_FILE_LOCATION_KEY,
			LOG_FILE_LOCATION_TEST_VALUE,
			LOG_FILE_LOCATION_TEST_VALUE_2);
	}

	public void testAddRemoveLogFileLocation() throws Exception {
		this.verifyAddRemoveProperty(
			LOG_FILE_LOCATION_KEY,
			LOG_FILE_LOCATION_TEST_VALUE,
			LOG_FILE_LOCATION_TEST_VALUE_2);
	}
	
	// ********** Logger tests **********
	public void testSetLogger() throws Exception {
		this.verifyModelInitialized(
			LOGGER_KEY,
			this.getEclipseLinkStringValueOf(LOGGER_TEST_VALUE)); // model is storing EclipseLinkStringValue
		this.verifySetProperty(
			LOGGER_KEY,
			LOGGER_TEST_VALUE,
			LOGGER_TEST_VALUE_2);
	}

	public void testAddRemoveLogger() throws Exception {
		this.verifyAddRemoveProperty(
			LOGGER_KEY,
			LOGGER_TEST_VALUE,
			LOGGER_TEST_VALUE_2);
	}

	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(Logging.LEVEL_PROPERTY))
			this.logging.setLevel((LoggingLevel) newValue);
		else if (propertyName.equals(Logging.TIMESTAMP_PROPERTY))
			this.logging.setTimestamp((Boolean) newValue);
		else if (propertyName.equals(Logging.THREAD_PROPERTY))
			this.logging.setThread((Boolean) newValue);
		else if (propertyName.equals(Logging.SESSION_PROPERTY))
			this.logging.setSession((Boolean) newValue);
		else if (propertyName.equals(Logging.EXCEPTIONS_PROPERTY))
			this.logging.setExceptions((Boolean) newValue);
		else if (propertyName.equals(Logging.LOG_FILE_LOCATION_PROPERTY))
			this.logging.setLogFileLocation((String) newValue);
		else if (propertyName.equals(Logging.LOGGER_PROPERTY))
			this.logging.setLogger((Logger) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(Logging.LEVEL_PROPERTY))
			modelValue = this.logging.getLevel();
		else if (propertyName.equals(Logging.TIMESTAMP_PROPERTY))
			modelValue = this.logging.getTimestamp();
		else if (propertyName.equals(Logging.THREAD_PROPERTY))
			modelValue = this.logging.getThread();
		else if (propertyName.equals(Logging.SESSION_PROPERTY))
			modelValue = this.logging.getSession();
		else if (propertyName.equals(Logging.EXCEPTIONS_PROPERTY))
			modelValue = this.logging.getExceptions();
		else if (propertyName.equals(Logging.LOG_FILE_LOCATION_PROPERTY))
			modelValue = this.logging.getLogFileLocation();
		else if (propertyName.equals(Logging.LOGGER_PROPERTY))
			modelValue = this.logging.getLogger();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}
	
	@Override
	protected void verifyPutProperty(String propertyName, Object expectedValue) throws Exception {
		Object expectedValue_ = expectedValue;
		if (propertyName.equals(Logging.LOGGER_PROPERTY)) {
			
			expectedValue_ = (expectedValue != null && expectedValue.getClass().isEnum()) ?
				this.getEclipseLinkStringValueOf(LOGGER_TEST_VALUE) : // model is storing EclipseLinkStringValue
				expectedValue;
		}
		super.verifyPutProperty(propertyName, expectedValue_);
	}
	
	protected PersistenceUnitProperties model() {
		return this.logging;
	}
}
