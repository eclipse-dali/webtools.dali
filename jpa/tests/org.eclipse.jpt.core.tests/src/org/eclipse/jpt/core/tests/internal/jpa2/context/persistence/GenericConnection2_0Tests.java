/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.tests.internal.jpa2.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;

/**
 *  GenericConnection2_0Tests
 */
@SuppressWarnings("nls")
public class GenericConnection2_0Tests extends GenericPersistenceUnit2_0Tests
{
	private JpaConnection2_0 connection;

	public static final String DRIVER_KEY = JpaConnection2_0.PERSISTENCE_JDBC_DRIVER;
	public static final String DRIVER_TEST_VALUE = "test";
	public static final String DRIVER_TEST_VALUE_2 = "test_2";
	
	public static final String URL_KEY = JpaConnection2_0.PERSISTENCE_JDBC_URL;
	public static final String URL_TEST_VALUE = "test";
	public static final String URL_TEST_VALUE_2 = "test_2";

	public static final String USER_KEY = JpaConnection2_0.PERSISTENCE_JDBC_USER;
	public static final String USER_TEST_VALUE = "test";
	public static final String USER_TEST_VALUE_2 = "test_2";

	public static final String PASSWORD_KEY = JpaConnection2_0.PERSISTENCE_JDBC_PASSWORD;
	public static final String PASSWORD_TEST_VALUE = "test";
	public static final String PASSWORD_TEST_VALUE_2 = "test_2";

	// ********** constructors **********
	public GenericConnection2_0Tests(String name) {
		super(name);
	}

	// ********** behavior **********
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.connection = this.subject.getConnection();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();

		this.connection.addPropertyChangeListener(JpaConnection2_0.DRIVER_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(JpaConnection2_0.URL_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(JpaConnection2_0.USER_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(JpaConnection2_0.PASSWORD_PROPERTY, propertyChangeListener);
		
		this.clearEvent();
	}

	/**
	 * Initializes directly the PersistenceUnit properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 4; // PersistenceUnit properties
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 1; // 1 misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		// Initializes PersistenceUnit properties
		this.persistenceUnitSetProperty("misc.property.1", "value.1");
		this.persistenceUnitSetProperty(DRIVER_KEY, DRIVER_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(URL_KEY, URL_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(USER_KEY, USER_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(PASSWORD_KEY, PASSWORD_TEST_VALUE.toString());

	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.connection;
	}

	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(JpaConnection2_0.DRIVER_PROPERTY))
			modelValue = this.connection.getDriver();
		else if (propertyName.equals(JpaConnection2_0.URL_PROPERTY))
			modelValue = this.connection.getUrl();
		else if (propertyName.equals(JpaConnection2_0.USER_PROPERTY))
			modelValue = this.connection.getUser();
		else if (propertyName.equals(JpaConnection2_0.PASSWORD_PROPERTY))
			modelValue = this.connection.getPassword();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}


	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(JpaConnection2_0.DRIVER_PROPERTY))
			this.connection.setDriver((String) newValue);
		else if (propertyName.equals(JpaConnection2_0.URL_PROPERTY))
			this.connection.setUrl((String) newValue);
		else if (propertyName.equals(JpaConnection2_0.USER_PROPERTY))
			this.connection.setUser((String) newValue);
		else if (propertyName.equals(JpaConnection2_0.PASSWORD_PROPERTY))
			this.connection.setPassword((String) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}

	// ********** Driver tests **********
	public void testSetDriver() throws Exception {
		this.verifyModelInitialized(
			DRIVER_KEY,
			DRIVER_TEST_VALUE);
		this.verifySetProperty(
			DRIVER_KEY,
			DRIVER_TEST_VALUE,
			DRIVER_TEST_VALUE_2);
	}

	public void testAddRemoveDriver() throws Exception {
		this.verifyAddRemoveProperty(
			DRIVER_KEY,
			DRIVER_TEST_VALUE,
			DRIVER_TEST_VALUE_2);
	}

	// ********** Url tests **********
	public void testSetUrl() throws Exception {
		this.verifyModelInitialized(
			URL_KEY,
			URL_TEST_VALUE);
		this.verifySetProperty(
			URL_KEY,
			URL_TEST_VALUE,
			URL_TEST_VALUE_2);
	}

	public void testAddRemoveUrl() throws Exception {
		this.verifyAddRemoveProperty(
			URL_KEY,
			URL_TEST_VALUE,
			URL_TEST_VALUE_2);
	}

	// ********** User tests **********
	public void testSetUser() throws Exception {
		this.verifyModelInitialized(
			USER_KEY,
			USER_TEST_VALUE);
		this.verifySetProperty(
			USER_KEY,
			USER_TEST_VALUE,
			USER_TEST_VALUE_2);
	}

	public void testAddRemoveUser() throws Exception {
		this.verifyAddRemoveProperty(
			USER_KEY,
			USER_TEST_VALUE,
			USER_TEST_VALUE_2);
	}

	// ********** Password tests **********
	public void testSetPassword() throws Exception {
		this.verifyModelInitialized(
			PASSWORD_KEY,
			PASSWORD_TEST_VALUE);
		this.verifySetProperty(
			PASSWORD_KEY,
			PASSWORD_TEST_VALUE,
			PASSWORD_TEST_VALUE_2);
	}

	public void testAddRemovePassword() throws Exception {
		this.verifyAddRemoveProperty(
			PASSWORD_KEY,
			PASSWORD_TEST_VALUE,
			PASSWORD_TEST_VALUE_2);
	}
	
}
