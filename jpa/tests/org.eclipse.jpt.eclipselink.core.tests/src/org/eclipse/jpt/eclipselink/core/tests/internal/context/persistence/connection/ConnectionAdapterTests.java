/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.connection;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.BatchWriting;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.tests.internal.context.persistence.EclipseLinkPersistenceUnitTestCase;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;

/**
 *  ConnectionAdapterTests
 */
@SuppressWarnings("nls")
public class ConnectionAdapterTests extends EclipseLinkPersistenceUnitTestCase
{
	private Connection connection;

	public static final PersistenceUnitTransactionType TRANSACTION_TYPE_TEST_VALUE = PersistenceUnitTransactionType.RESOURCE_LOCAL;
	public static final PersistenceUnitTransactionType TRANSACTION_TYPE_TEST_VALUE_2 = PersistenceUnitTransactionType.JTA;

	public static final String JTA_DATA_SOURCE_TEST_VALUE = "Test_JTA";
	public static final String JTA_DATA_SOURCE_TEST_VALUE_2 = "Test_JTA_2";

	public static final String NON_JTA_DATA_SOURCE_TEST_VALUE = "Test_Non_JTA";
	public static final String NON_JTA_DATA_SOURCE_TEST_VALUE_2 = "Test_Non_JTA_2";
	
	public static final String NATIVE_SQL_KEY = Connection.ECLIPSELINK_NATIVE_SQL;
	public static final Boolean NATIVE_SQL_TEST_VALUE = false;
	public static final Boolean NATIVE_SQL_TEST_VALUE_2 = ! NATIVE_SQL_TEST_VALUE;

	public static final String BATCH_WRITING_KEY = Connection.ECLIPSELINK_BATCH_WRITING;
	public static final BatchWriting BATCH_WRITING_TEST_VALUE = BatchWriting.oracle_jdbc;
	public static final BatchWriting BATCH_WRITING_TEST_VALUE_2 = BatchWriting.buffered;
	
	public static final String CACHE_STATEMENTS_KEY = Connection.ECLIPSELINK_CACHE_STATEMENTS;
	public static final Boolean CACHE_STATEMENTS_TEST_VALUE = false;
	public static final Boolean CACHE_STATEMENTS_TEST_VALUE_2 = ! CACHE_STATEMENTS_TEST_VALUE;
	
	public static final String CACHE_STATEMENTS_SIZE_KEY = Connection.ECLIPSELINK_CACHE_STATEMENTS_SIZE;
	public static final Integer CACHE_STATEMENTS_SIZE_TEST_VALUE = 100;
	public static final Integer CACHE_STATEMENTS_SIZE_TEST_VALUE_2 = 200;
	
	public static final String DRIVER_KEY = Connection.ECLIPSELINK_DRIVER;
	public static final String DRIVER_TEST_VALUE = "connection.driver";
	public static final String DRIVER_TEST_VALUE_2 = "connection.driver.2";
	
	public static final String URL_KEY = Connection.ECLIPSELINK_URL;
	public static final String URL_TEST_VALUE = "test";
	public static final String URL_TEST_VALUE_2 = "test_2";

	public static final String USER_KEY = Connection.ECLIPSELINK_USER;
	public static final String USER_TEST_VALUE = "test";
	public static final String USER_TEST_VALUE_2 = "test_2";

	public static final String PASSWORD_KEY = Connection.ECLIPSELINK_PASSWORD;
	public static final String PASSWORD_TEST_VALUE = "test";
	public static final String PASSWORD_TEST_VALUE_2 = "test_2";
	
	public static final String BIND_PARAMETERS_KEY = Connection.ECLIPSELINK_BIND_PARAMETERS;
	public static final Boolean BIND_PARAMETERS_TEST_VALUE = false;
	public static final Boolean BIND_PARAMETERS_TEST_VALUE_2 = ! BIND_PARAMETERS_TEST_VALUE;
	
	public static final String READ_CONNECTIONS_SHARED_KEY = Connection.ECLIPSELINK_READ_CONNECTIONS_SHARED;
	public static final Boolean READ_CONNECTIONS_SHARED_TEST_VALUE = false;
	public static final Boolean READ_CONNECTIONS_SHARED_TEST_VALUE_2 = ! READ_CONNECTIONS_SHARED_TEST_VALUE;
	
	public static final String READ_CONNECTIONS_MIN_KEY = Connection.ECLIPSELINK_READ_CONNECTIONS_MIN;
	public static final Integer READ_CONNECTIONS_MIN_TEST_VALUE = 100;
	public static final Integer READ_CONNECTIONS_MIN_TEST_VALUE_2 = 200;
	
	public static final String READ_CONNECTIONS_MAX_KEY = Connection.ECLIPSELINK_READ_CONNECTIONS_MAX;
	public static final Integer READ_CONNECTIONS_MAX_TEST_VALUE = 100;
	public static final Integer READ_CONNECTIONS_MAX_TEST_VALUE_2 = 200;
	
	public static final String WRITE_CONNECTIONS_MIN_KEY = Connection.ECLIPSELINK_WRITE_CONNECTIONS_MIN;
	public static final Integer WRITE_CONNECTIONS_MIN_TEST_VALUE = 100;
	public static final Integer WRITE_CONNECTIONS_MIN_TEST_VALUE_2 = 200;
	
	public static final String WRITE_CONNECTIONS_MAX_KEY = Connection.ECLIPSELINK_WRITE_CONNECTIONS_MAX;
	public static final Integer WRITE_CONNECTIONS_MAX_TEST_VALUE = 100;
	public static final Integer WRITE_CONNECTIONS_MAX_TEST_VALUE_2 = 200;

	public ConnectionAdapterTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.connection = this.subject.getConnection();
		PropertyChangeListener propertyChangeListener = this.buildPropertyChangeListener();
		
		this.connection.addPropertyChangeListener(Connection.NATIVE_SQL_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.BATCH_WRITING_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.CACHE_STATEMENTS_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.CACHE_STATEMENTS_SIZE_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.DRIVER_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.URL_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.USER_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.PASSWORD_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.BIND_PARAMETERS_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.READ_CONNECTIONS_SHARED_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.READ_CONNECTIONS_MIN_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.READ_CONNECTIONS_MAX_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.WRITE_CONNECTIONS_MIN_PROPERTY, propertyChangeListener);
		this.connection.addPropertyChangeListener(Connection.WRITE_CONNECTIONS_MAX_PROPERTY, propertyChangeListener);
		
		this.clearEvent();
	}

	/**
	 * Initializes directly the PU properties before testing.
	 */
	@Override
	protected void populatePu() {
		this.modelPropertiesSizeOriginal = 14; // EclipseLink properties
		this.propertiesTotal = this.modelPropertiesSizeOriginal + 4; // 4 misc properties
		this.modelPropertiesSize = this.modelPropertiesSizeOriginal;
		
		// Initializes EclipseLink properties
		this.persistenceUnitSetProperty("misc.property.1", "value.1");
		this.persistenceUnitSetProperty(NATIVE_SQL_KEY, NATIVE_SQL_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(BATCH_WRITING_KEY, BATCH_WRITING_TEST_VALUE);
		this.persistenceUnitSetProperty(CACHE_STATEMENTS_KEY, CACHE_STATEMENTS_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(CACHE_STATEMENTS_SIZE_KEY, CACHE_STATEMENTS_SIZE_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(DRIVER_KEY, DRIVER_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(URL_KEY, URL_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(USER_KEY, USER_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(PASSWORD_KEY, PASSWORD_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(BIND_PARAMETERS_KEY, BIND_PARAMETERS_TEST_VALUE.toString());
		this.persistenceUnitSetProperty("misc.property.2", "value.2");
		this.persistenceUnitSetProperty("misc.property.3", "value.3");
		this.persistenceUnitSetProperty(READ_CONNECTIONS_SHARED_KEY, READ_CONNECTIONS_SHARED_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(READ_CONNECTIONS_MIN_KEY, READ_CONNECTIONS_MIN_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(READ_CONNECTIONS_MAX_KEY, READ_CONNECTIONS_MAX_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(WRITE_CONNECTIONS_MIN_KEY, WRITE_CONNECTIONS_MIN_TEST_VALUE.toString());
		this.persistenceUnitSetProperty(WRITE_CONNECTIONS_MAX_KEY, WRITE_CONNECTIONS_MAX_TEST_VALUE.toString());
		this.persistenceUnitSetProperty("misc.property.4", "value.4");
		
		// Initializes PU property TransactionType
		this.getPersistenceUnit().setSpecifiedTransactionType(TRANSACTION_TYPE_TEST_VALUE);
		this.getPersistenceUnit().setJtaDataSource(JTA_DATA_SOURCE_TEST_VALUE);
		this.getPersistenceUnit().setNonJtaDataSource(NON_JTA_DATA_SOURCE_TEST_VALUE);
		return;
	}

	// ********** NativeSql tests **********
	public void testSetNativeSql() throws Exception {
		this.verifyModelInitialized(
			NATIVE_SQL_KEY,
			NATIVE_SQL_TEST_VALUE);
		this.verifySetProperty(
			NATIVE_SQL_KEY,
			NATIVE_SQL_TEST_VALUE,
			NATIVE_SQL_TEST_VALUE_2);
	}

	public void testAddRemoveNativeSql() throws Exception {
		this.verifyAddRemoveProperty(
			NATIVE_SQL_KEY,
			NATIVE_SQL_TEST_VALUE,
			NATIVE_SQL_TEST_VALUE_2);
	}

	// ********** BatchWriting tests **********
	public void testSetBatchWriting() throws Exception {
		this.verifyModelInitialized(
			BATCH_WRITING_KEY,
			BATCH_WRITING_TEST_VALUE);
		this.verifySetProperty(
			BATCH_WRITING_KEY,
			BATCH_WRITING_TEST_VALUE,
			BATCH_WRITING_TEST_VALUE_2);
	}

	public void testAddRemoveBatchWriting() throws Exception {
		this.verifyAddRemoveProperty(
			BATCH_WRITING_KEY,
			BATCH_WRITING_TEST_VALUE,
			BATCH_WRITING_TEST_VALUE_2);
	}

	// ********** CacheStatements tests **********
	public void testSetCacheStatements() throws Exception {
		this.verifyModelInitialized(
			CACHE_STATEMENTS_KEY,
			CACHE_STATEMENTS_TEST_VALUE);
		this.verifySetProperty(
			CACHE_STATEMENTS_KEY,
			CACHE_STATEMENTS_TEST_VALUE,
			CACHE_STATEMENTS_TEST_VALUE_2);
	}

	public void testAddRemoveCacheStatements() throws Exception {
		this.verifyAddRemoveProperty(
			CACHE_STATEMENTS_KEY,
			CACHE_STATEMENTS_TEST_VALUE,
			CACHE_STATEMENTS_TEST_VALUE_2);
	}
	
	// ********** CacheStatementsSize tests **********
	public void testSetCacheStatementsSize() throws Exception {
		this.verifyModelInitialized(
			CACHE_STATEMENTS_SIZE_KEY,
			CACHE_STATEMENTS_SIZE_TEST_VALUE);
		this.verifySetProperty(
			CACHE_STATEMENTS_SIZE_KEY,
			CACHE_STATEMENTS_SIZE_TEST_VALUE,
			CACHE_STATEMENTS_SIZE_TEST_VALUE_2);
	}

	public void testAddRemoveCacheStatementsSize() throws Exception {
		this.verifyAddRemoveProperty(
			CACHE_STATEMENTS_SIZE_KEY,
			CACHE_STATEMENTS_SIZE_TEST_VALUE,
			CACHE_STATEMENTS_SIZE_TEST_VALUE_2);
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

	// ********** BindParameters tests **********
	public void testSetBindParameters() throws Exception {
		this.verifyModelInitialized(
			BIND_PARAMETERS_KEY,
			BIND_PARAMETERS_TEST_VALUE);
		this.verifySetProperty(
			BIND_PARAMETERS_KEY,
			BIND_PARAMETERS_TEST_VALUE,
			BIND_PARAMETERS_TEST_VALUE_2);
	}

	public void testAddRemoveBindParameters() throws Exception {
		this.verifyAddRemoveProperty(
			BIND_PARAMETERS_KEY,
			BIND_PARAMETERS_TEST_VALUE,
			BIND_PARAMETERS_TEST_VALUE_2);
	}

	// ********** ReadConnectionsShared tests **********
	public void testSetReadConnectionsShared() throws Exception {
		this.verifyModelInitialized(
			READ_CONNECTIONS_SHARED_KEY,
			READ_CONNECTIONS_SHARED_TEST_VALUE);
		this.verifySetProperty(
			READ_CONNECTIONS_SHARED_KEY,
			READ_CONNECTIONS_SHARED_TEST_VALUE,
			READ_CONNECTIONS_SHARED_TEST_VALUE_2);
	}

	public void testAddRemoveReadConnectionsShared() throws Exception {
		this.verifyAddRemoveProperty(
			READ_CONNECTIONS_SHARED_KEY,
			READ_CONNECTIONS_SHARED_TEST_VALUE,
			READ_CONNECTIONS_SHARED_TEST_VALUE_2);
	}
	
	// ********** ReadConnectionsMin tests **********
	public void testSetReadConnectionsMin() throws Exception {
		this.verifyModelInitialized(
			READ_CONNECTIONS_MIN_KEY,
			READ_CONNECTIONS_MIN_TEST_VALUE);
		this.verifySetProperty(
			READ_CONNECTIONS_MIN_KEY,
			READ_CONNECTIONS_MIN_TEST_VALUE,
			READ_CONNECTIONS_MIN_TEST_VALUE_2);
	}

	public void testAddRemoveReadConnectionsMin() throws Exception {
		this.verifyAddRemoveProperty(
			READ_CONNECTIONS_MIN_KEY,
			READ_CONNECTIONS_MIN_TEST_VALUE,
			READ_CONNECTIONS_MIN_TEST_VALUE_2);
	}
	
	// ********** ReadConnectionsMax tests **********
	public void testSetReadConnectionsMax() throws Exception {
		this.verifyModelInitialized(
			READ_CONNECTIONS_MAX_KEY,
			READ_CONNECTIONS_MAX_TEST_VALUE);
		this.verifySetProperty(
			READ_CONNECTIONS_MAX_KEY,
			READ_CONNECTIONS_MAX_TEST_VALUE,
			READ_CONNECTIONS_MAX_TEST_VALUE_2);
	}

	public void testAddRemoveReadConnectionsMax() throws Exception {
		this.verifyAddRemoveProperty(
			READ_CONNECTIONS_MAX_KEY,
			READ_CONNECTIONS_MAX_TEST_VALUE,
			READ_CONNECTIONS_MAX_TEST_VALUE_2);
	}
	
	// ********** WriteConnectionsMin tests **********
	public void testSetWriteConnectionsMin() throws Exception {
		this.verifyModelInitialized(
			WRITE_CONNECTIONS_MIN_KEY,
			WRITE_CONNECTIONS_MIN_TEST_VALUE);
		this.verifySetProperty(
			WRITE_CONNECTIONS_MIN_KEY,
			WRITE_CONNECTIONS_MIN_TEST_VALUE,
			WRITE_CONNECTIONS_MIN_TEST_VALUE_2);
	}

	public void testAddRemoveWriteConnectionsMin() throws Exception {
		this.verifyAddRemoveProperty(
			WRITE_CONNECTIONS_MIN_KEY,
			WRITE_CONNECTIONS_MIN_TEST_VALUE,
			WRITE_CONNECTIONS_MIN_TEST_VALUE_2);
	}
	
	// ********** WriteConnectionsMax tests **********
	public void testSetWriteConnectionsMax() throws Exception {
		this.verifyModelInitialized(
			WRITE_CONNECTIONS_MAX_KEY,
			WRITE_CONNECTIONS_MAX_TEST_VALUE);
		this.verifySetProperty(
			WRITE_CONNECTIONS_MAX_KEY,
			WRITE_CONNECTIONS_MAX_TEST_VALUE,
			WRITE_CONNECTIONS_MAX_TEST_VALUE_2);
	}

	public void testAddRemoveWriteConnectionsMax() throws Exception {
		this.verifyAddRemoveProperty(
			WRITE_CONNECTIONS_MAX_KEY,
			WRITE_CONNECTIONS_MAX_TEST_VALUE,
			WRITE_CONNECTIONS_MAX_TEST_VALUE_2);
	}

	// ********** get/set property **********
	@Override
	protected void setProperty(String propertyName, Object newValue) throws Exception {
		if (propertyName.equals(Connection.NATIVE_SQL_PROPERTY))
			this.connection.setNativeSql((Boolean) newValue);
		else if (propertyName.equals(Connection.BATCH_WRITING_PROPERTY))
			this.connection.setBatchWriting((BatchWriting) newValue);
		else if (propertyName.equals(Connection.CACHE_STATEMENTS_PROPERTY))
			this.connection.setCacheStatements((Boolean) newValue);
		else if (propertyName.equals(Connection.CACHE_STATEMENTS_SIZE_PROPERTY))
			this.connection.setCacheStatementsSize((Integer) newValue);
		else if (propertyName.equals(Connection.DRIVER_PROPERTY))
			this.connection.setDriver((String) newValue);
		else if (propertyName.equals(Connection.URL_PROPERTY))
			this.connection.setUrl((String) newValue);
		else if (propertyName.equals(Connection.USER_PROPERTY))
			this.connection.setUser((String) newValue);
		else if (propertyName.equals(Connection.PASSWORD_PROPERTY))
			this.connection.setPassword((String) newValue);
		else if (propertyName.equals(Connection.BIND_PARAMETERS_PROPERTY))
			this.connection.setBindParameters((Boolean) newValue);
		else if (propertyName.equals(Connection.READ_CONNECTIONS_SHARED_PROPERTY))
			this.connection.setReadConnectionsShared((Boolean) newValue);
		else if (propertyName.equals(Connection.READ_CONNECTIONS_MIN_PROPERTY))
			this.connection.setReadConnectionsMin((Integer) newValue);
		else if (propertyName.equals(Connection.READ_CONNECTIONS_MAX_PROPERTY))
			this.connection.setReadConnectionsMax((Integer) newValue);
		else if (propertyName.equals(Connection.WRITE_CONNECTIONS_MIN_PROPERTY))
			this.connection.setWriteConnectionsMin((Integer) newValue);
		else if (propertyName.equals(Connection.WRITE_CONNECTIONS_MAX_PROPERTY))
			this.connection.setWriteConnectionsMax((Integer) newValue);
		else
			this.throwMissingDefinition("setProperty", propertyName);
	}
	
	@Override
	protected Object getProperty(String propertyName) throws NoSuchFieldException {
		Object modelValue = null;
		if (propertyName.equals(Connection.NATIVE_SQL_PROPERTY))
			modelValue = this.connection.getNativeSql();
		else if (propertyName.equals(Connection.BATCH_WRITING_PROPERTY))
			modelValue = this.connection.getBatchWriting();
		else if (propertyName.equals(Connection.CACHE_STATEMENTS_PROPERTY))
			modelValue = this.connection.getCacheStatements();
		else if (propertyName.equals(Connection.CACHE_STATEMENTS_SIZE_PROPERTY))
			modelValue = this.connection.getCacheStatementsSize();
		else if (propertyName.equals(Connection.DRIVER_PROPERTY))
			modelValue = this.connection.getDriver();
		else if (propertyName.equals(Connection.URL_PROPERTY))
			modelValue = this.connection.getUrl();
		else if (propertyName.equals(Connection.USER_PROPERTY))
			modelValue = this.connection.getUser();
		else if (propertyName.equals(Connection.PASSWORD_PROPERTY))
			modelValue = this.connection.getPassword();
		else if (propertyName.equals(Connection.BIND_PARAMETERS_PROPERTY))
			modelValue = this.connection.getBindParameters();
		else if (propertyName.equals(Connection.READ_CONNECTIONS_SHARED_PROPERTY))
			modelValue = this.connection.getReadConnectionsShared();
		else if (propertyName.equals(Connection.READ_CONNECTIONS_MIN_PROPERTY))
			modelValue = this.connection.getReadConnectionsMin();
		else if (propertyName.equals(Connection.READ_CONNECTIONS_MAX_PROPERTY))
			modelValue = this.connection.getReadConnectionsMax();
		else if (propertyName.equals(Connection.WRITE_CONNECTIONS_MIN_PROPERTY))
			modelValue = this.connection.getWriteConnectionsMin();
		else if (propertyName.equals(Connection.WRITE_CONNECTIONS_MAX_PROPERTY))
			modelValue = this.connection.getWriteConnectionsMax();
		else
			this.throwMissingDefinition("getProperty", propertyName);
		return modelValue;
	}

	@Override
	protected PersistenceUnitProperties getModel() {
		return this.connection;
	}
}
