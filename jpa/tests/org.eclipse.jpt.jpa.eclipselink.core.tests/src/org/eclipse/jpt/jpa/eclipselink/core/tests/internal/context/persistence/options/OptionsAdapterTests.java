/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.options;

import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.TargetDatabase;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.TargetServer;
import org.eclipse.jpt.jpa.eclipselink.core.tests.internal.context.persistence.EclipseLinkPersistenceUnitTestCase;

/**
 * Tests the update of model objects by the Option adapter when the
 * PersistenceUnit changes.
 */
public class OptionsAdapterTests extends EclipseLinkPersistenceUnitTestCase
{
	private Options options;

	private static final String SESSION_NAME_KEY = Options.ECLIPSELINK_SESSION_NAME;
	private static final String SESSION_NAME_TEST_VALUE = "session-name.test";
	private static final String SESSION_NAME_TEST_VALUE_2 = "session-name-2.test";

	private static final String SESSIONS_XML_KEY = Options.ECLIPSELINK_SESSIONS_XML;
	private static final String SESSIONS_XML_TEST_VALUE = "sessions-xml.test";
	private static final String SESSIONS_XML_TEST_VALUE_2 = "sessions-xml-2.test";

	public static final String TARGET_DATABASE_KEY = Options.ECLIPSELINK_TARGET_DATABASE;
	public static final TargetDatabase TARGET_DATABASE_TEST_VALUE = TargetDatabase.cloudscape;
	public static final String TARGET_DATABASE_TEST_VALUE_2 = "custom.targetDatabase.test";

	private static final String TARGET_SERVER_KEY = Options.ECLIPSELINK_TARGET_SERVER;
	private static final TargetServer TARGET_SERVER_TEST_VALUE = TargetServer.weblogic_9;
	private static final String TARGET_SERVER_TEST_VALUE_2 = "custom.targetServer.test";

	public static final String INCLUDE_DESCRIPTOR_QUERIES_KEY = Options.ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES;
	public static final Boolean INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE = false;
	public static final Boolean INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE_2 = ! INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE;

	public static final String SESSION_EVENT_LISTENER_KEY = Options.ECLIPSELINK_SESSION_EVENT_LISTENER;
	public static final String SESSION_EVENT_LISTENER_TEST_VALUE = "acme.CustomSessionEventListener";
	public static final String SESSION_EVENT_LISTENER_TEST_VALUE_2 = "oracle.sessions.CustomSessionEventListener";

	public static final String TEMPORAL_MUTABLE_KEY = Options.ECLIPSELINK_TEMPORAL_MUTABLE;
	public static final Boolean TEMPORAL_MUTABLE_TEST_VALUE = true;
	public static final Boolean TEMPORAL_MUTABLE_TEST_VALUE_2 = ! TEMPORAL_MUTABLE_TEST_VALUE;

	public OptionsAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.options = this.subject.getOptions();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();

		this.options.addPropertyChangeListener(Options.SESSION_NAME_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options.SESSIONS_XML_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options.TARGET_DATABASE_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options.TARGET_SERVER_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options.SESSION_EVENT_LISTENER_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(
			Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options.TEMPORAL_MUTABLE_PROPERTY, propertyChangeListener);

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
		
		this.persistenceUnitSetProperty(SESSION_NAME_KEY, SESSION_NAME_TEST_VALUE);
		this.persistenceUnitSetProperty(SESSIONS_XML_KEY, SESSIONS_XML_TEST_VALUE);
		this.persistenceUnitSetProperty("misc.property.1", "value.1");
		this.persistenceUnitSetProperty(INCLUDE_DESCRIPTOR_QUERIES_KEY, INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE.toString());
		this.persistenceUnitSetProperty("misc.property.2", "value.2");
		this.persistenceUnitSetProperty("misc.property.3", "value.3");
		this.persistenceUnitSetProperty(TARGET_DATABASE_KEY, TARGET_DATABASE_TEST_VALUE);
		this.persistenceUnitSetProperty(TARGET_SERVER_KEY, TARGET_SERVER_TEST_VALUE);
		this.persistenceUnitSetProperty(SESSION_EVENT_LISTENER_KEY, SESSION_EVENT_LISTENER_TEST_VALUE);
		this.persistenceUnitSetProperty("misc.property.4", "value.4");
		this.persistenceUnitSetProperty(TEMPORAL_MUTABLE_KEY, TEMPORAL_MUTABLE_TEST_VALUE.toString());
		return;
	}
	
	// ********** Listeners **********

	// ********** SessionName tests **********
	public void testSetSessionName() throws Exception {
		this.verifyModelInitialized(
			SESSION_NAME_KEY,
			SESSION_NAME_TEST_VALUE);
		this.verifySetProperty(
			SESSION_NAME_KEY,
			SESSION_NAME_TEST_VALUE,
			SESSION_NAME_TEST_VALUE_2);
	}

	public void testAddRemoveSessionName() throws Exception {
		this.verifyAddRemoveProperty(
			SESSION_NAME_KEY,
			SESSION_NAME_TEST_VALUE,
			SESSION_NAME_TEST_VALUE_2);
	}

	// ********** SessionsXml tests **********
	public void testSetSessionsXml() throws Exception {
		this.verifyModelInitialized(
			SESSIONS_XML_KEY,
			SESSIONS_XML_TEST_VALUE);
		this.verifySetProperty(
			SESSIONS_XML_KEY,
			SESSIONS_XML_TEST_VALUE,
			SESSIONS_XML_TEST_VALUE_2);
	}

	public void testAddRemoveSessionsXml() throws Exception {
		this.verifyAddRemoveProperty(
			SESSIONS_XML_KEY,
			SESSIONS_XML_TEST_VALUE,
			SESSIONS_XML_TEST_VALUE_2);
	}

	// ********** IncludeDescriptorQueries tests **********
	public void testSetIncludeDescriptorQueries() throws Exception {
		this.verifyModelInitialized(
			INCLUDE_DESCRIPTOR_QUERIES_KEY,
			INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE);
		this.verifySetProperty(
			INCLUDE_DESCRIPTOR_QUERIES_KEY,
			INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE,
			INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE_2);
	}

	public void testAddRemoveIncludeDescriptorQueries() throws Exception {
		this.verifyAddRemoveProperty(
			INCLUDE_DESCRIPTOR_QUERIES_KEY,
			INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE,
			INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE_2);
	}

	// ********** TargetDatabase tests **********
	public void testSetTargetDatabase() throws Exception {
		this.verifyModelInitialized(
			TARGET_DATABASE_KEY,
			this.getPropertyStringValueOf(TARGET_DATABASE_TEST_VALUE)); // model is storing EclipseLinkStringValue
		this.verifySetProperty(
			TARGET_DATABASE_KEY,
			TARGET_DATABASE_TEST_VALUE,
			TARGET_DATABASE_TEST_VALUE_2);
		// verify set custom and literal value
		this.verifySetTargetDatabase(
			TARGET_DATABASE_KEY,
			TARGET_DATABASE_TEST_VALUE,
			TARGET_DATABASE_TEST_VALUE_2);
	}

	public void testAddRemoveTargetDatabase() throws Exception {
		this.verifyAddRemoveProperty(
			TARGET_DATABASE_KEY,
			TARGET_DATABASE_TEST_VALUE,
			TARGET_DATABASE_TEST_VALUE_2);
	}
	
	/**
	 * Verifies setting custom targetDatabase and literals.
	 */
	protected void verifySetTargetDatabase(String elKey, Object testValue1, Object testValue2) throws Exception {
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(elKey);
		String propertyName = this.getModel().propertyIdOf(property);
		// test set custom targetDatabase.
		this.clearEvent();
		this.setProperty(propertyName, testValue2);
		this.verifyPutProperty(propertyName, testValue2);

		// test set (TargetDatabase) null
		this.clearEvent();
		this.options.setTargetDatabase((TargetDatabase) null);
		assertNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, null);
		
		// test set enum literal
		this.clearEvent();
		this.setProperty(propertyName, testValue1.toString());
		assertNotNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, this.getPropertyStringValueOf(testValue1));

		// test set (String) null
		this.clearEvent();
		this.options.setTargetDatabase((String) null);
		assertNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, null);
	}
	
	// ********** TargetServer tests **********
	public void testSetTargetServer() throws Exception {
		this.verifyModelInitialized(
			TARGET_SERVER_KEY,
			this.getPropertyStringValueOf(TARGET_SERVER_TEST_VALUE)); // model is storing EclipseLinkStringValue
		// verify set enum value
		this.verifySetProperty(
			TARGET_SERVER_KEY,
			TARGET_SERVER_TEST_VALUE,
			TARGET_SERVER_TEST_VALUE_2);
		// verify set custom and literal value
		this.verifySetTargetServer(
			TARGET_SERVER_KEY,
			TARGET_SERVER_TEST_VALUE,
			TARGET_SERVER_TEST_VALUE_2);
	}

	public void testAddRemoveTargetServer() throws Exception {
		this.verifyAddRemoveProperty(
			TARGET_SERVER_KEY,
			TARGET_SERVER_TEST_VALUE,
			TARGET_SERVER_TEST_VALUE_2);
	}
	
	/**
	 * Verifies setting custom targetServer and literals.
	 */
	protected void verifySetTargetServer(String elKey, Object testValue1, Object testValue2) throws Exception {
		PersistenceUnit.Property property = this.getPersistenceUnit().getProperty(elKey);
		String propertyName = this.getModel().propertyIdOf(property);
		// test set custom targetServer.
		this.clearEvent();
		this.setProperty(propertyName, testValue2);
		this.verifyPutProperty(propertyName, testValue2);

		// test set (TargetServer) null
		this.clearEvent();
		this.options.setTargetServer((TargetServer) null);
		assertNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, null);
		
		// test set enum literal
		this.clearEvent();
		this.setProperty(propertyName, testValue1.toString());
		assertNotNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, this.getPropertyStringValueOf(testValue1));

		// test set (String) null
		this.clearEvent();
		this.options.setTargetServer((String) null);
		assertNull(this.getPersistenceUnit().getProperty(elKey));
		this.verifyPutProperty(propertyName, null);
	}

	// ********** EventListener tests **********
	public void testSetEventListener() throws Exception {
		this.verifyModelInitialized(
			SESSION_EVENT_LISTENER_KEY,
			SESSION_EVENT_LISTENER_TEST_VALUE);
		this.verifySetProperty(
			SESSION_EVENT_LISTENER_KEY,
			SESSION_EVENT_LISTENER_TEST_VALUE,
			SESSION_EVENT_LISTENER_TEST_VALUE_2);
	}

	public void testAddRemoveEventListener() throws Exception {
		this.verifyAddRemoveProperty(
			SESSION_EVENT_LISTENER_KEY,
			SESSION_EVENT_LISTENER_TEST_VALUE,
			SESSION_EVENT_LISTENER_TEST_VALUE_2);
	}

	// ********** TemporalMutable tests **********
	public void testSetTemporalMutable() throws Exception {
		this.verifyModelInitialized(
			TEMPORAL_MUTABLE_KEY,
			TEMPORAL_MUTABLE_TEST_VALUE);
		this.verifySetProperty(
			TEMPORAL_MUTABLE_KEY,
			TEMPORAL_MUTABLE_TEST_VALUE,
			TEMPORAL_MUTABLE_TEST_VALUE_2);
	}

	public void testAddRemoveTemporalMutable() throws Exception {
		this.verifyAddRemoveProperty(
			TEMPORAL_MUTABLE_KEY,
			TEMPORAL_MUTABLE_TEST_VALUE,
			TEMPORAL_MUTABLE_TEST_VALUE_2);
	}

	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(Options.SESSION_NAME_PROPERTY))
			this.options.setSessionName((String) newValue);
		else if (propertyName.equals(Options.SESSIONS_XML_PROPERTY))
			this.options.setSessionsXml((String) newValue);
		else if (propertyName.equals(Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY))
			this.options.setIncludeDescriptorQueries((Boolean) newValue);
		else if (propertyName.equals(Options.TARGET_DATABASE_PROPERTY))
			this.setTargetDatabaseProperty(newValue);
		else if (propertyName.equals(Options.TARGET_SERVER_PROPERTY))
			this.setTargetServerProperty(newValue);
		else if (propertyName.equals(Options.SESSION_EVENT_LISTENER_PROPERTY))
			this.options.setEventListener((String) newValue);
		else if (propertyName.equals(Options.TEMPORAL_MUTABLE_PROPERTY))
			this.options.setTemporalMutable((Boolean) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}

	private void setTargetDatabaseProperty(Object newValue) {
		if (newValue.getClass().isEnum())
			this.options.setTargetDatabase((TargetDatabase) newValue);
		else
			this.options.setTargetDatabase((String) newValue);
	}

	private void setTargetServerProperty(Object newValue) {
		if (newValue.getClass().isEnum())
			this.options.setTargetServer((TargetServer) newValue);
		else
			this.options.setTargetServer((String) newValue);
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(Options.SESSION_NAME_PROPERTY))
			modelValue = this.options.getSessionName();
		else if (propertyName.equals(Options.SESSIONS_XML_PROPERTY))
			modelValue = this.options.getSessionsXml();
		else if (propertyName.equals(Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY))
			modelValue = this.options.getIncludeDescriptorQueries();
		else if (propertyName.equals(Options.TARGET_DATABASE_PROPERTY))
			modelValue = this.options.getTargetDatabase();
		else if (propertyName.equals(Options.TARGET_SERVER_PROPERTY))
			modelValue = this.options.getTargetServer();
		else if (propertyName.equals(Options.SESSION_EVENT_LISTENER_PROPERTY))
			modelValue = this.options.getEventListener();
		else if (propertyName.equals(Options.TEMPORAL_MUTABLE_PROPERTY))
			modelValue = this.options.getTemporalMutable();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}
	
	@Override
	protected void verifyPutProperty(String propertyName, Object expectedValue) throws Exception {
		Object expectedValue_ = expectedValue;
		if (propertyName.equals(Options.TARGET_DATABASE_PROPERTY) ||
			propertyName.equals(Options.TARGET_SERVER_PROPERTY)) {
			
			expectedValue_ = this.convertToEclipseLinkStringValue(expectedValue);
		}
		
		super.verifyPutProperty(propertyName, expectedValue_);
	}
	
	private String convertToEclipseLinkStringValue(Object expectedValue) {
		return (String) ((expectedValue != null && expectedValue.getClass().isEnum()) ?
				this.getPropertyStringValueOf(expectedValue) : // model is storing EclipseLinkStringValue
				expectedValue); // already a EclipseLinkStringValue
	}
	
	@Override
	protected PersistenceUnitProperties getModel() {
		return this.options;
	}
}
