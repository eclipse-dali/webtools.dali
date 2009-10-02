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
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.connection.Connection2_0;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;

/**
 *  EclipseLink2_0ConnectionTests
 */
public class EclipseLink2_0ConnectionTests extends EclipseLink2_0PersistenceUnitTestCase
{
	private Connection2_0 connection;

	public static final String NATIVE_SQL_KEY = Connection.ECLIPSELINK_NATIVE_SQL;
	public static final Boolean NATIVE_SQL_TEST_VALUE = false;
	public static final Boolean NATIVE_SQL_TEST_VALUE_2 = ! NATIVE_SQL_TEST_VALUE;
	
	public static final String DRIVER_KEY = Connection.ECLIPSELINK2_0_DRIVER;
	public static final String DRIVER_TEST_VALUE = "connection.driver";
	public static final String DRIVER_TEST_VALUE_2 = "connection.driver.2";
	public static final String LEGACY_DRIVER_KEY = Connection.ECLIPSELINK_DRIVER;
	public static final String LEGACY_DRIVER_TEST_VALUE = "legacy.connection.driver";
	
	public static final String URL_KEY = Connection.ECLIPSELINK2_0_URL;
	public static final String URL_TEST_VALUE = "test";
	public static final String URL_TEST_VALUE_2 = "test_2";
	public static final String LEGACY_URL_KEY = Connection.ECLIPSELINK_URL;
	public static final String LEGACY_URL_TEST_VALUE = "legacy.connection.url";

	public static final String USER_KEY = Connection.ECLIPSELINK2_0_USER;
	public static final String USER_TEST_VALUE = "test";
	public static final String USER_TEST_VALUE_2 = "test_2";
	public static final String LEGACY_USER_KEY = Connection.ECLIPSELINK_USER;
	public static final String LEGACY_USER_TEST_VALUE = "legacy.connection.user";

	public static final String PASSWORD_KEY = Connection.ECLIPSELINK2_0_PASSWORD;
	public static final String PASSWORD_TEST_VALUE = "test";
	public static final String PASSWORD_TEST_VALUE_2 = "test_2";
	public static final String LEGACY_PASSWORD_KEY = Connection.ECLIPSELINK_PASSWORD;
	public static final String LEGACY_PASSWORD_TEST_VALUE = "legacy.connection.password";

	public EclipseLink2_0ConnectionTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.connection = this.subject.getConnection();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.connection.addPropertyChangeListener(Connection.DRIVER_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.URL_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.USER_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.PASSWORD_PROPERTY, propertyChangeListener);
		
		this.clearEvent();
	}

	/**
	 * Initializes directly the PU properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 8; // EclipseLink properties
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 1; // 4 misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		// Initializes EclipseLink properties
		this.persistenceUnitSetProperty("misc.property.1", "value.1");
		this.persistenceUnitSetProperty(LEGACY_DRIVER_KEY, LEGACY_DRIVER_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(DRIVER_KEY, DRIVER_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(LEGACY_URL_KEY, LEGACY_URL_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(URL_KEY, URL_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(LEGACY_USER_KEY, LEGACY_USER_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(USER_KEY, USER_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(LEGACY_PASSWORD_KEY, LEGACY_PASSWORD_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(PASSWORD_KEY, PASSWORD_TEST_VALUE.toString());
		
		return;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.connection;
	}

	// ********** get/set property **********
	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;

		if (propertyName.equals(Connection.DRIVER_PROPERTY))
			modelValue = this.connection.getDriver();
		else if (propertyName.equals(Connection.URL_PROPERTY))
			modelValue = this.connection.getUrl();
		else if (propertyName.equals(Connection.USER_PROPERTY))
			modelValue = this.connection.getUser();
		else if (propertyName.equals(Connection.PASSWORD_PROPERTY))
			modelValue = this.connection.getPassword();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}

	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(Connection.DRIVER_PROPERTY))
			this.connection.setDriver((String) newValue);
		else if (propertyName.equals(Connection.URL_PROPERTY))
			this.connection.setUrl((String) newValue);
		else if (propertyName.equals(Connection.USER_PROPERTY))
			this.connection.setUser((String) newValue);
		else if (propertyName.equals(Connection.PASSWORD_PROPERTY))
			this.connection.setPassword((String) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}
	

	// ********** Properties Name Migration tests **********
	public void testDriverPropertyNameMigration() throws Exception {

		// Verify legacy driver exists
		assertTrue("Legacy driver not exists", this.propertyValueEquals(LEGACY_DRIVER_KEY, LEGACY_DRIVER_TEST_VALUE));
		
		// Verify driver read in
		assertEquals("Incorrect driver read", this.connection.getDriver(), DRIVER_TEST_VALUE);

		// Change driver value
		this.connection.setDriver(DRIVER_TEST_VALUE_2);
		// Verify driver value changed
		assertEquals("Driver not set", this.connection.getDriver(), DRIVER_TEST_VALUE_2);
		assertTrue("PersistenceUnit property not set", this.propertyValueEquals(DRIVER_KEY, DRIVER_TEST_VALUE_2));
		
		// Verify legacy entry has been deleted
		this.verifyPuHasNotProperty(LEGACY_DRIVER_KEY,  "Legacy property has not been deleted");
	}

	public void testPropertiesNamesMigration() throws Exception {
		// connection.initializeProperties() occurred before test.puPopulate() therefore
		// we cannot test the case where there are legacy properties only exist in the xml
		// Verify that User & Password exist in both forms
		this.verifyPuHasProperty(USER_KEY,  "Property not exists");
		this.verifyPuHasProperty(PASSWORD_KEY,  "Property not exists");
		this.verifyPuHasProperty(LEGACY_USER_KEY,  "Legacy property not exists");
		this.verifyPuHasProperty(LEGACY_PASSWORD_KEY,  "Legacy property not exists");
		
		// Change a property value to trigger migration routine
		this.connection.setNativeSql(NATIVE_SQL_TEST_VALUE);
		
		// Verify that all legacy entry has been deleted
		this.verifyPuHasNotProperty(LEGACY_DRIVER_KEY,  "Legacy property has not been deleted");
		this.verifyPuHasNotProperty(LEGACY_URL_KEY,  "Legacy property has not been deleted");
		this.verifyPuHasNotProperty(LEGACY_USER_KEY,  "Legacy property has not been deleted");
		this.verifyPuHasNotProperty(LEGACY_PASSWORD_KEY,  "Legacy property has not been deleted");
	}

	
	
}