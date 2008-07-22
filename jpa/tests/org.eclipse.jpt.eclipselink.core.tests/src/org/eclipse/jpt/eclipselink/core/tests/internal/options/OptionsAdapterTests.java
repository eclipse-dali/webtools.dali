/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.options;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.persistence.GenericProperty;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLinkJpaProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitProperties;
import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitPropertyListListener;
import org.eclipse.jpt.eclipselink.core.internal.context.options.EclipseLinkOptions;
import org.eclipse.jpt.eclipselink.core.internal.context.options.Options;
import org.eclipse.jpt.eclipselink.core.internal.context.options.TargetDatabase;
import org.eclipse.jpt.eclipselink.core.internal.context.options.TargetServer;
import org.eclipse.jpt.eclipselink.core.tests.internal.PersistenceUnitTestCase;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 * Tests the update of model objects by the Logging adapter when the
 * PersistenceUnit changes.
 */
public class OptionsAdapterTests extends PersistenceUnitTestCase
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
	public static final TargetDatabase TARGET_DATABASE_TEST_VALUE_2 = TargetDatabase.oracle;

	private static final String TARGET_SERVER_KEY = Options.ECLIPSELINK_TARGET_SERVER;
	private static final TargetServer TARGET_SERVER_TEST_VALUE = TargetServer.weblogic_9;
	private static final String TARGET_SERVER_TEST_VALUE_2 = "custom.targetServer.test";

	public static final String INCLUDE_DESCRIPTOR_QUERIES_KEY = Options.ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES;
	public static final Boolean INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE = false;
	public static final Boolean INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE_2 = ! INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE;

	public static final String SESSION_EVENT_LISTENER_KEY = Options.ECLIPSELINK_SESSION_EVENT_LISTENER;
	public static final String SESSION_EVENT_LISTENER_TEST_VALUE = "acme.CustomSessionEventListener";
	public static final String SESSION_EVENT_LISTENER_TEST_VALUE_2 = "oracle.sessions.CustomSessionEventListener";

	public OptionsAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.options = this.persistenceUnitProperties.getOptions();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();

		this.options.addPropertyChangeListener(Options.SESSION_NAME_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options.SESSIONS_XML_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options.TARGET_DATABASE_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options.TARGET_SERVER_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(Options.SESSION_EVENT_LISTENER_PROPERTY, propertyChangeListener);
		this.options.addPropertyChangeListener(
			Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY, propertyChangeListener);

		this.clearEvent();
	}

	/**
	 * Initializes directly the PU properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 6;
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 4; // 4 misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		this.persistenceUnitPut(SESSION_NAME_KEY, SESSION_NAME_TEST_VALUE);
		this.persistenceUnitPut(SESSIONS_XML_KEY, SESSIONS_XML_TEST_VALUE);
		this.persistenceUnitPut("misc.property.1", "value.1");
		this.persistenceUnitPut(INCLUDE_DESCRIPTOR_QUERIES_KEY, INCLUDE_DESCRIPTOR_QUERIES_TEST_VALUE.toString());
		this.persistenceUnitPut("misc.property.2", "value.2");
		this.persistenceUnitPut("misc.property.3", "value.3");
		this.persistenceUnitPut(TARGET_DATABASE_KEY, TARGET_DATABASE_TEST_VALUE);
		this.persistenceUnitPut(TARGET_SERVER_KEY, TARGET_SERVER_TEST_VALUE);
		this.persistenceUnitPut(SESSION_EVENT_LISTENER_KEY, SESSION_EVENT_LISTENER_TEST_VALUE);
		this.persistenceUnitPut("misc.property.4", "value.4");
		return;
	}
	
	// ********** Listeners **********

	// ********** Listeners tests **********
	public void testHasListeners() throws Exception {
		// new
		ListAspectAdapter<PersistenceUnit, Property> propertiesAdapter = 
			(ListAspectAdapter<PersistenceUnit, Property>) ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertiesAdapter();
		GenericProperty ctdProperty = (GenericProperty) this.persistenceUnit().getProperty(INCLUDE_DESCRIPTOR_QUERIES_KEY);
		ListValueModel<Property> propertyListAdapter = ((EclipseLinkJpaProperties) this.persistenceUnitProperties).propertyListAdapter();
		
		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES));
		assertTrue(ctdProperty.hasAnyPropertyChangeListeners(Property.VALUE_PROPERTY));
		this.verifyHasListeners(this.options, Options.SESSION_NAME_PROPERTY);
		this.verifyHasListeners(this.options, Options.SESSIONS_XML_PROPERTY);
		this.verifyHasListeners(this.options, Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY);
		this.verifyHasListeners(this.options, Options.TARGET_DATABASE_PROPERTY);
		this.verifyHasListeners(this.options, Options.SESSION_EVENT_LISTENER_PROPERTY);
		this.verifyHasListeners(propertyListAdapter);
		
		EclipseLinkOptions elOptions = (EclipseLinkOptions) this.options;
		PersistenceUnitPropertyListListener propertyListListener = elOptions.propertyListListener();
		propertyListAdapter.removeListChangeListener(ListValueModel.LIST_VALUES, propertyListListener);
		assertTrue(propertiesAdapter.hasAnyListChangeListeners(ListValueModel.LIST_VALUES)); // other properties are still listening
		this.verifyHasListeners(this.options, Options.SESSION_NAME_PROPERTY);
		this.verifyHasListeners(this.options, Options.SESSIONS_XML_PROPERTY);
		this.verifyHasListeners(this.options, Options.SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY);
		this.verifyHasListeners(this.options, Options.TARGET_DATABASE_PROPERTY);
		this.verifyHasListeners(this.options, Options.SESSION_EVENT_LISTENER_PROPERTY);
	}


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
			TARGET_DATABASE_TEST_VALUE);
		this.verifySetProperty(
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
	
	// ********** TargetServer tests **********
	public void testSetTargetServer() throws Exception {
		this.verifyModelInitialized(
			TARGET_SERVER_KEY,
			this.getEclipseLinkStringValueOf(TARGET_SERVER_TEST_VALUE)); // model is storing EclipseLinkStringValue
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
		Property property = this.persistenceUnit().getProperty(elKey);
		String propertyName = this.model().propertyIdFor(property);
		// test set custom targetServer.
		this.clearEvent();
		this.setProperty(propertyName, testValue2);
		this.verifyPutProperty(propertyName, testValue2);

		// test set (TargetServer) null
		this.clearEvent();
		this.options.setTargetServer((TargetServer) null);
		assertFalse(this.persistenceUnit().containsProperty(elKey));
		this.verifyPutProperty(propertyName, null);
		
		// test set enum literal
		this.clearEvent();
		this.setProperty(propertyName, testValue1.toString());
		assertTrue(this.persistenceUnit().containsProperty(elKey));
		this.verifyPutProperty(propertyName, this.getEclipseLinkStringValueOf(testValue1));

		// test set (String) null
		this.clearEvent();
		this.options.setTargetServer((String) null);
		assertFalse(this.persistenceUnit().containsProperty(elKey));
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
			this.options.setTargetDatabase((TargetDatabase) newValue);
		else if (propertyName.equals(Options.SESSION_EVENT_LISTENER_PROPERTY))
			this.options.setEventListener((String) newValue);
		else if (propertyName.equals(Options.TARGET_SERVER_PROPERTY)) {
			if (newValue.getClass().isEnum())
				this.options.setTargetServer((TargetServer) newValue);
			else
				this.options.setTargetServer((String) newValue);
		}
		else
			this.throwMissingDefinition("setProperty", propertyName);
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
		else if (propertyName.equals(Options.SESSION_EVENT_LISTENER_PROPERTY))
			modelValue = this.options.getEventListener();
		else if (propertyName.equals(Options.TARGET_SERVER_PROPERTY))
			modelValue = this.options.getTargetServer();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}
	
	@Override
	protected void verifyPutProperty(String propertyName, Object expectedValue) throws Exception {
		Object expectedValue_ = expectedValue;
		if (propertyName.equals(Options.TARGET_SERVER_PROPERTY)) {
			
			expectedValue_ = (expectedValue != null && expectedValue.getClass().isEnum()) ?
				this.getEclipseLinkStringValueOf(TARGET_SERVER_TEST_VALUE) : // model is storing EclipseLinkStringValue
				expectedValue;
		}
		super.verifyPutProperty(propertyName, expectedValue_);
	}
	
	protected PersistenceUnitProperties model() {
		return this.options;
	}
}
