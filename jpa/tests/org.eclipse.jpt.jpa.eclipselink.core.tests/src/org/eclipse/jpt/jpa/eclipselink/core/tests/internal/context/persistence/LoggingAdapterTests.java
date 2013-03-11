/*******************************************************************************
* Copyright (c) 2008, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence;

import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Logger;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLoggingLevel;

/**
 * Tests the update of model objects by the Logging adapter when the
 * PersistenceUnit changes.
 */
@SuppressWarnings("nls")
public class LoggingAdapterTests extends EclipseLinkPersistenceUnitTestCase
{
	private EclipseLinkLogging logging;

	public static final String LEVEL_KEY = EclipseLinkLogging.ECLIPSELINK_LEVEL;
	public static final EclipseLinkLoggingLevel LEVEL_TEST_VALUE = EclipseLinkLoggingLevel.fine;
	public static final EclipseLinkLoggingLevel LEVEL_TEST_VALUE_2 = EclipseLinkLoggingLevel.finest;

	public static final String TIMESTAMP_KEY = EclipseLinkLogging.ECLIPSELINK_TIMESTAMP;
	public static final Boolean TIMESTAMP_TEST_VALUE = false;
	public static final Boolean TIMESTAMP_TEST_VALUE_2 = ! TIMESTAMP_TEST_VALUE;

	public static final String THREAD_KEY = EclipseLinkLogging.ECLIPSELINK_THREAD;
	public static final Boolean THREAD_TEST_VALUE = false;
	public static final Boolean THREAD_TEST_VALUE_2 = ! THREAD_TEST_VALUE;

	public static final String SESSION_KEY = EclipseLinkLogging.ECLIPSELINK_SESSION;
	public static final Boolean SESSION_TEST_VALUE = false;
	public static final Boolean SESSION_TEST_VALUE_2 = ! SESSION_TEST_VALUE;

	public static final String EXCEPTIONS_KEY = EclipseLinkLogging.ECLIPSELINK_EXCEPTIONS;
	public static final Boolean EXCEPTIONS_TEST_VALUE = false;
	public static final Boolean EXCEPTIONS_TEST_VALUE_2 = ! EXCEPTIONS_TEST_VALUE;

	private static final String LOG_FILE_LOCATION_KEY = EclipseLinkLogging.ECLIPSELINK_LOG_FILE_LOCATION;
	private static final String LOG_FILE_LOCATION_TEST_VALUE = "C:/temp";
	private static final String LOG_FILE_LOCATION_TEST_VALUE_2 = "C:/tmp";

	private static final String LOGGER_KEY = EclipseLinkLogging.ECLIPSELINK_LOGGER;
	private static final Logger LOGGER_TEST_VALUE = Logger.java_logger;
	private static final String LOGGER_TEST_VALUE_2 = "custom.logger.test";
	
	public LoggingAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.logging = this.subject.getLogging();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.logging.addPropertyChangeListener(EclipseLinkLogging.LEVEL_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(EclipseLinkLogging.TIMESTAMP_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(EclipseLinkLogging.THREAD_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(EclipseLinkLogging.SESSION_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(EclipseLinkLogging.EXCEPTIONS_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(EclipseLinkLogging.LOG_FILE_LOCATION_PROPERTY, propertyChangeListener);
		this.logging.addPropertyChangeListener(EclipseLinkLogging.LOGGER_PROPERTY, propertyChangeListener);

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
		
		this.persistenceUnitSetProperty("misc.property.1", "value.1");
		this.persistenceUnitSetProperty(TIMESTAMP_KEY, TIMESTAMP_TEST_VALUE.toString());
		this.persistenceUnitSetProperty("misc.property.2", "value.2");
		this.persistenceUnitSetProperty(LEVEL_KEY, LEVEL_TEST_VALUE);
		this.persistenceUnitSetProperty("misc.property.3", "value.3");
		this.persistenceUnitSetProperty(THREAD_KEY, THREAD_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(SESSION_KEY, SESSION_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(EXCEPTIONS_KEY, EXCEPTIONS_TEST_VALUE.toString());
		this.persistenceUnitSetProperty("misc.property.4", "value.4");
		this.persistenceUnitSetProperty(LOG_FILE_LOCATION_KEY, LOG_FILE_LOCATION_TEST_VALUE);
		this.persistenceUnitSetProperty(LOGGER_KEY, LOGGER_TEST_VALUE);
		return;
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
	public void testSetTimestamp() throws Exception {
		this.verifyModelInitialized(
			TIMESTAMP_KEY,
			TIMESTAMP_TEST_VALUE);
		this.verifySetProperty(
			TIMESTAMP_KEY,
			TIMESTAMP_TEST_VALUE,
			TIMESTAMP_TEST_VALUE_2);
	}

	public void testAddRemoveTimestamp() throws Exception {
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

	public void testSetEmptyFileLocation() throws Exception {
		String puKey = LOG_FILE_LOCATION_KEY;
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(puKey);
		String propertyName = this.getModel().propertyIdOf(property);

		// Set FileLocation to "" & verify that the property is deleted
		this.verifyPuHasProperty(puKey,  "persistenceUnit.properties doesn't contains: ");
		this.setProperty(propertyName, "");

		this.verifyPuHasNotProperty(puKey,  "Property was not deleted");
		this.verifyPutProperty(propertyName, null);
		assertNull(this.getPersistenceUnit().getProperty(puKey));
	}
	
	// ********** Logger tests **********
	public void testSetLogger() throws Exception {
		this.verifyModelInitialized(
			LOGGER_KEY,
			this.getPropertyStringValueOf(LOGGER_TEST_VALUE)); // model is storing EclipseLinkStringValue
		// verify set enum value
		this.verifySetProperty(
			LOGGER_KEY,
			LOGGER_TEST_VALUE,
			LOGGER_TEST_VALUE_2);
		// verify set custom and literal value
		this.verifySetLogger(
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
	
	/**
	 * Verifies setting custom logger and literals.
	 */
	protected void verifySetLogger(String elKey, Object testValue1, Object testValue2) throws Exception {
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(elKey);
		String propertyName = this.getModel().propertyIdOf(property);
		// test set custom logger.
		this.clearEvent();
		this.setProperty(propertyName, testValue2);
		this.verifyPutProperty(propertyName, testValue2);

		// test set (Logger) null
		this.clearEvent();
		this.logging.setLogger((Logger) null);
		assertNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, null);
		
		// test set enum literal
		this.clearEvent();
		this.setProperty(propertyName, testValue1.toString());
		assertNotNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, this.getPropertyStringValueOf(testValue1));

		// test set (String) null
		this.clearEvent();
		this.logging.setLogger((String) null);
		assertNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, null);
	}

	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(EclipseLinkLogging.LEVEL_PROPERTY))
			this.logging.setLevel((EclipseLinkLoggingLevel) newValue);
		else if (propertyName.equals(EclipseLinkLogging.TIMESTAMP_PROPERTY))
			this.logging.setTimestamp((Boolean) newValue);
		else if (propertyName.equals(EclipseLinkLogging.THREAD_PROPERTY))
			this.logging.setThread((Boolean) newValue);
		else if (propertyName.equals(EclipseLinkLogging.SESSION_PROPERTY))
			this.logging.setSession((Boolean) newValue);
		else if (propertyName.equals(EclipseLinkLogging.EXCEPTIONS_PROPERTY))
			this.logging.setExceptions((Boolean) newValue);
		else if (propertyName.equals(EclipseLinkLogging.LOG_FILE_LOCATION_PROPERTY))
			this.logging.setLogFileLocation((String) newValue);
		else if (propertyName.equals(EclipseLinkLogging.LOGGER_PROPERTY)) {
			if (newValue.getClass().isEnum())
				this.logging.setLogger((Logger) newValue);
			else
				this.logging.setLogger((String) newValue);
		}
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(EclipseLinkLogging.LEVEL_PROPERTY))
			modelValue = this.logging.getLevel();
		else if (propertyName.equals(EclipseLinkLogging.TIMESTAMP_PROPERTY))
			modelValue = this.logging.getTimestamp();
		else if (propertyName.equals(EclipseLinkLogging.THREAD_PROPERTY))
			modelValue = this.logging.getThread();
		else if (propertyName.equals(EclipseLinkLogging.SESSION_PROPERTY))
			modelValue = this.logging.getSession();
		else if (propertyName.equals(EclipseLinkLogging.EXCEPTIONS_PROPERTY))
			modelValue = this.logging.getExceptions();
		else if (propertyName.equals(EclipseLinkLogging.LOG_FILE_LOCATION_PROPERTY))
			modelValue = this.logging.getLogFileLocation();
		else if (propertyName.equals(EclipseLinkLogging.LOGGER_PROPERTY))
			modelValue = this.logging.getLogger();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}
	
	@Override
	protected void verifyPutProperty(String propertyName, Object expectedValue) throws Exception {
		Object expectedValue_ = expectedValue;
		if (propertyName.equals(EclipseLinkLogging.LOGGER_PROPERTY)) {
			
			expectedValue_ = (expectedValue != null && expectedValue.getClass().isEnum()) ?
				this.getPropertyStringValueOf(LOGGER_TEST_VALUE) : // model is storing EclipseLinkStringValue
				expectedValue;
		}
		super.verifyPutProperty(propertyName, expectedValue_);
	}
	
	@Override
	protected PersistenceUnitProperties getModel() {
		return this.logging;
	}
}
