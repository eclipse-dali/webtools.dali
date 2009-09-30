/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection;

import java.util.Map;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.BatchWriting;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnitProperties;

/**
 *  EclipseLinkConnection
 */
public class EclipseLinkConnection extends EclipseLinkPersistenceUnitProperties
	implements Connection
{
	
	// ********** EclipseLink properties **********
	private BatchWriting batchWriting;
	private Boolean nativeSql;
	private Boolean cacheStatements;
	private Integer cacheStatementsSize;
	protected String driver;
	protected String url;
	protected String user;
	protected String password;
	private Boolean bindParameters;
	private Boolean readConnectionsShared;
	private Integer readConnectionsMin;
	private Integer readConnectionsMax;
	private Integer writeConnectionsMin;
	private Integer writeConnectionsMax;
	

	// ********** constructors **********
	public EclipseLinkConnection(PersistenceUnit parent) {
		super(parent);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		this.initializeDatabaseConnectionProperties();
		
		this.batchWriting = 
			this.getEnumValue(ECLIPSELINK_BATCH_WRITING, BatchWriting.values());
		this.nativeSql = 
			this.getBooleanValue(ECLIPSELINK_NATIVE_SQL);
		this.cacheStatements = 
			this.getBooleanValue(ECLIPSELINK_CACHE_STATEMENTS);
		this.cacheStatementsSize = 
			this.getIntegerValue(ECLIPSELINK_CACHE_STATEMENTS_SIZE);
		this.bindParameters = 
			this.getBooleanValue(ECLIPSELINK_BIND_PARAMETERS);
		this.readConnectionsShared = 
			this.getBooleanValue(ECLIPSELINK_READ_CONNECTIONS_SHARED);
		this.readConnectionsMin = 
			this.getIntegerValue(ECLIPSELINK_READ_CONNECTIONS_MIN);
		this.readConnectionsMax = 
			this.getIntegerValue(ECLIPSELINK_READ_CONNECTIONS_MAX);
		this.writeConnectionsMin = 
			this.getIntegerValue(ECLIPSELINK_WRITE_CONNECTIONS_MIN);
		this.writeConnectionsMax = 
			this.getIntegerValue(ECLIPSELINK_WRITE_CONNECTIONS_MAX);
	}
	
	protected void initializeDatabaseConnectionProperties() {
		this.driver = 
			this.getStringValue(ECLIPSELINK_DRIVER);
		this.url = 
			this.getStringValue(ECLIPSELINK_URL);
		this.user = 
			this.getStringValue(ECLIPSELINK_USER);
		this.password = 
			this.getStringValue(ECLIPSELINK_PASSWORD);
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		if (propertyName.equals(ECLIPSELINK_NATIVE_SQL)) {
			this.nativeSqlChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_BATCH_WRITING)) {
			this.batchWritingChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_CACHE_STATEMENTS)) {
			this.cacheStatementsChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_DRIVER)) {
			this.driverChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_URL)) {
			this.urlChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_USER)) {
			this.userChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_PASSWORD)) {
			this.passwordChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_BIND_PARAMETERS)) {
			this.bindParametersChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_READ_CONNECTIONS_SHARED)) {
			this.readConnectionsSharedChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_CACHE_STATEMENTS_SIZE)) {
			this.cacheStatementsSizeChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_READ_CONNECTIONS_MIN)) {
			readConnectionsMinChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_READ_CONNECTIONS_MAX)) {
			readConnectionsMaxChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_WRITE_CONNECTIONS_MIN)) {
			writeConnectionsMinChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_WRITE_CONNECTIONS_MAX)) {
			writeConnectionsMaxChanged(newValue);
		}
	}
	
	public void propertyRemoved(String propertyName) {
		if (propertyName.equals(ECLIPSELINK_NATIVE_SQL)) {
			this.nativeSqlChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_BATCH_WRITING)) {
			this.batchWritingChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_CACHE_STATEMENTS)) {
			this.cacheStatementsChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_DRIVER)) {
			this.driverChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_URL)) {
			this.urlChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_USER)) {
			this.userChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_PASSWORD)) {
			this.passwordChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_BIND_PARAMETERS)) {
			this.bindParametersChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_READ_CONNECTIONS_SHARED)) {
			this.readConnectionsSharedChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_CACHE_STATEMENTS_SIZE)) {
			this.cacheStatementsSizeChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_READ_CONNECTIONS_MIN)) {
			readConnectionsMinChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_READ_CONNECTIONS_MAX)) {
			readConnectionsMaxChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_WRITE_CONNECTIONS_MIN)) {
			writeConnectionsMinChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_WRITE_CONNECTIONS_MAX)) {
			writeConnectionsMaxChanged(null);
		}
	}

	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = EclipseLink property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		this.addDatabaseConnectionPropertyNames(propertyNames);
		
		propertyNames.put(
			ECLIPSELINK_NATIVE_SQL,
			NATIVE_SQL_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_BATCH_WRITING,
			BATCH_WRITING_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_CACHE_STATEMENTS,
			CACHE_STATEMENTS_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_CACHE_STATEMENTS_SIZE,
			CACHE_STATEMENTS_SIZE_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_BIND_PARAMETERS,
			BIND_PARAMETERS_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_READ_CONNECTIONS_SHARED,
			READ_CONNECTIONS_SHARED_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_READ_CONNECTIONS_MIN,
			READ_CONNECTIONS_MIN_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_READ_CONNECTIONS_MAX,
			READ_CONNECTIONS_MAX_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_WRITE_CONNECTIONS_MIN,
			WRITE_CONNECTIONS_MIN_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_WRITE_CONNECTIONS_MAX,
			WRITE_CONNECTIONS_MAX_PROPERTY);
	}
	
	protected void addDatabaseConnectionPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			ECLIPSELINK_DRIVER,
			DRIVER_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_URL,
			URL_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_USER,
			USER_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_PASSWORD,
			PASSWORD_PROPERTY);
	}

	/**
     * Does all pre-treatment in this method before the property is set
	 */
	protected void preSetProperty() {
		// do nothing by default
	}

	// ********** NativeSql **********
	public Boolean getNativeSql() {
		return this.nativeSql;
	}

	public void setNativeSql(Boolean newNativeSql) {
		this.preSetProperty();
		
		Boolean old = this.nativeSql;
		this.nativeSql = newNativeSql;
		this.putProperty(NATIVE_SQL_PROPERTY, newNativeSql);
		this.firePropertyChanged(NATIVE_SQL_PROPERTY, old, newNativeSql);
	}
	
	private void nativeSqlChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.nativeSql;
		this.nativeSql = newValue;
		this.firePropertyChanged(NATIVE_SQL_PROPERTY, old, newValue);
	}

	public Boolean getDefaultNativeSql() {
		return DEFAULT_NATIVE_SQL;
	}

	// ********** BatchWriting **********
	
	public BatchWriting getBatchWriting() {
		return this.batchWriting;
	}
	
	public void setBatchWriting(BatchWriting newBatchWriting) {
		this.preSetProperty();
		
		BatchWriting old = this.batchWriting;
		this.batchWriting = newBatchWriting;
		this.putProperty(BATCH_WRITING_PROPERTY, newBatchWriting);
		this.firePropertyChanged(BATCH_WRITING_PROPERTY, old, newBatchWriting);
	}

	private void batchWritingChanged(String stringValue) {
		BatchWriting newValue = getEnumValueOf(stringValue, BatchWriting.values());
		BatchWriting old = this.batchWriting;
		this.batchWriting = newValue;
		this.firePropertyChanged(BATCH_WRITING_PROPERTY, old, newValue);
	}
	
	public BatchWriting getDefaultBatchWriting() {
		return DEFAULT_BATCH_WRITING;
	}

	// ********** CacheStatements **********
	public Boolean getCacheStatements() {
		return this.cacheStatements;
	}

	public void setCacheStatements(Boolean newCacheStatements) {
		this.preSetProperty();
		
		Boolean old = this.cacheStatements;
		this.cacheStatements = newCacheStatements;
		this.putProperty(CACHE_STATEMENTS_PROPERTY, newCacheStatements);
		this.firePropertyChanged(CACHE_STATEMENTS_PROPERTY, old, newCacheStatements);
	}

	private void cacheStatementsChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.cacheStatements;
		this.cacheStatements = newValue;
		this.firePropertyChanged(CACHE_STATEMENTS_PROPERTY, old, newValue);
	}

	public Boolean getDefaultCacheStatements() {
		return DEFAULT_CACHE_STATEMENTS;
	}

	// ********** CacheStatementsSize **********
	public Integer getCacheStatementsSize() {
		return this.cacheStatementsSize;
	}

	public void setCacheStatementsSize(Integer newCacheStatementsSize) {
		this.preSetProperty();
		
		Integer old = this.cacheStatementsSize;
		this.cacheStatementsSize = newCacheStatementsSize;
		this.putProperty(CACHE_STATEMENTS_SIZE_PROPERTY, newCacheStatementsSize);
		this.firePropertyChanged(CACHE_STATEMENTS_SIZE_PROPERTY, old, newCacheStatementsSize);
	}

	private void cacheStatementsSizeChanged(String stringValue) {
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.cacheStatementsSize;
		this.cacheStatementsSize = newValue;
		this.firePropertyChanged(CACHE_STATEMENTS_SIZE_PROPERTY, old, newValue);
	}

	public Integer getDefaultCacheStatementsSize() {
		return DEFAULT_CACHE_STATEMENTS_SIZE;
	}

	// ********** Driver **********
	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String newDriver) {
		this.preSetProperty();
		
		String old = this.driver;
		this.driver = newDriver;
		this.putProperty(DRIVER_PROPERTY, newDriver);
		this.firePropertyChanged(DRIVER_PROPERTY, old, newDriver);
	}

	protected void driverChanged(String newValue) {
		String old = this.driver;
		this.driver = newValue;
		this.firePropertyChanged(DRIVER_PROPERTY, old, newValue);
	}

	public String getDefaultDriver() {
		return DEFAULT_DRIVER;
	}

	// ********** URL **********
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String newUrl) {
		this.preSetProperty();
		
		String old = this.url;
		this.url = newUrl;
		this.putProperty(URL_PROPERTY, newUrl);
		this.firePropertyChanged(URL_PROPERTY, old, newUrl);
	}

	protected void urlChanged(String newValue) {
		String old = this.url;
		this.url = newValue;
		this.firePropertyChanged(URL_PROPERTY, old, newValue);
	}

	public String getDefaultUrl() {
		return DEFAULT_URL;
	}

	// ********** User **********
	public String getUser() {
		return this.user;
	}

	public void setUser(String newUser) {
		this.preSetProperty();
		
		String old = this.user;
		this.user = newUser;
		this.putProperty(USER_PROPERTY, newUser);
		this.firePropertyChanged(USER_PROPERTY, old, newUser);
	}

	protected void userChanged(String newValue) {
		String old = this.user;
		this.user = newValue;
		this.firePropertyChanged(USER_PROPERTY, old, newValue);
	}

	public String getDefaultUser() {
		return DEFAULT_USER;
	}

	// ********** Password **********
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String newPassword) {
		this.preSetProperty();
		
		String old = this.password;
		this.password = newPassword;
		this.putProperty(PASSWORD_PROPERTY, newPassword);
		this.firePropertyChanged(PASSWORD_PROPERTY, old, newPassword);
	}

	protected void passwordChanged(String newValue) {
		String old = this.password;
		this.password = newValue;
		this.firePropertyChanged(PASSWORD_PROPERTY, old, newValue);
	}

	public String getDefaultPassword() {
		return DEFAULT_PASSWORD;
	}

	// ********** BindParameters **********
	public Boolean getBindParameters() {
		return this.bindParameters;
	}

	public void setBindParameters(Boolean newBindParameters) {
		this.preSetProperty();
		
		Boolean old = this.bindParameters;
		this.bindParameters = newBindParameters;
		this.putProperty(BIND_PARAMETERS_PROPERTY, newBindParameters);
		this.firePropertyChanged(BIND_PARAMETERS_PROPERTY, old, newBindParameters);
	}

	private void bindParametersChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.bindParameters;
		this.bindParameters = newValue;
		this.firePropertyChanged(BIND_PARAMETERS_PROPERTY, old, newValue);
	}

	public Boolean getDefaultBindParameters() {
		return DEFAULT_BIND_PARAMETERS;
	}

	// ********** ReadConnectionsShared **********
	public Boolean getReadConnectionsShared() {
		return this.readConnectionsShared;
	}

	public void setReadConnectionsShared(Boolean newReadConnectionsShared) {
		this.preSetProperty();
		
		Boolean old = this.readConnectionsShared;
		this.readConnectionsShared = newReadConnectionsShared;
		this.putProperty(READ_CONNECTIONS_SHARED_PROPERTY, newReadConnectionsShared);
		this.firePropertyChanged(READ_CONNECTIONS_SHARED_PROPERTY, old, newReadConnectionsShared);
	}

	private void readConnectionsSharedChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.readConnectionsShared;
		this.readConnectionsShared = newValue;
		this.firePropertyChanged(READ_CONNECTIONS_SHARED_PROPERTY, old, newValue);
	}

	public Boolean getDefaultReadConnectionsShared() {
		return DEFAULT_READ_CONNECTIONS_SHARED;
	}

	// ********** ReadConnectionsMin **********
	public Integer getReadConnectionsMin() {
		return this.readConnectionsMin;
	}

	public void setReadConnectionsMin(Integer newReadConnectionsMin) {
		this.preSetProperty();
		
		Integer old = this.readConnectionsMin;
		this.readConnectionsMin = newReadConnectionsMin;
		this.putProperty(READ_CONNECTIONS_MIN_PROPERTY, newReadConnectionsMin);
		this.firePropertyChanged(READ_CONNECTIONS_MIN_PROPERTY, old, newReadConnectionsMin);
	}

	private void readConnectionsMinChanged(String stringValue) {
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.readConnectionsMin;
		this.readConnectionsMin = newValue;
		this.firePropertyChanged(READ_CONNECTIONS_MIN_PROPERTY, old, newValue);
	}

	public Integer getDefaultReadConnectionsMin() {
		return DEFAULT_READ_CONNECTIONS_MIN;
	}

	// ********** ReadConnectionsMax **********
	public Integer getReadConnectionsMax() {
		return this.readConnectionsMax;
	}

	public void setReadConnectionsMax(Integer newReadConnectionsMax) {
		this.preSetProperty();
		
		Integer old = this.readConnectionsMax;
		this.readConnectionsMax = newReadConnectionsMax;
		this.putProperty(READ_CONNECTIONS_MAX_PROPERTY, newReadConnectionsMax);
		this.firePropertyChanged(READ_CONNECTIONS_MAX_PROPERTY, old, newReadConnectionsMax);
	}

	private void readConnectionsMaxChanged(String stringValue) {
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.readConnectionsMax;
		this.readConnectionsMax = newValue;
		this.firePropertyChanged(READ_CONNECTIONS_MAX_PROPERTY, old, newValue);
	}

	public Integer getDefaultReadConnectionsMax() {
		return DEFAULT_READ_CONNECTIONS_MAX;
	}

	// ********** WriteConnectionsMin **********
	public Integer getWriteConnectionsMin() {
		return this.writeConnectionsMin;
	}

	public void setWriteConnectionsMin(Integer newWriteConnectionsMin) {
		this.preSetProperty();
		
		Integer old = this.writeConnectionsMin;
		this.writeConnectionsMin = newWriteConnectionsMin;
		this.putProperty(WRITE_CONNECTIONS_MIN_PROPERTY, newWriteConnectionsMin);
		this.firePropertyChanged(WRITE_CONNECTIONS_MIN_PROPERTY, old, newWriteConnectionsMin);
	}

	private void writeConnectionsMinChanged(String stringValue) {
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.writeConnectionsMin;
		this.writeConnectionsMin = newValue;
		this.firePropertyChanged(WRITE_CONNECTIONS_MIN_PROPERTY, old, newValue);
	}

	public Integer getDefaultWriteConnectionsMin() {
		return DEFAULT_WRITE_CONNECTIONS_MIN;
	}

	// ********** WriteConnectionsMax **********
	public Integer getWriteConnectionsMax() {
		return this.writeConnectionsMax;
	}

	public void setWriteConnectionsMax(Integer newWriteConnectionsMax) {
		this.preSetProperty();
		
		Integer old = this.writeConnectionsMax;
		this.writeConnectionsMax = newWriteConnectionsMax;
		this.putProperty(WRITE_CONNECTIONS_MAX_PROPERTY, newWriteConnectionsMax);
		this.firePropertyChanged(WRITE_CONNECTIONS_MAX_PROPERTY, old, newWriteConnectionsMax);
	}

	private void writeConnectionsMaxChanged(String stringValue) {
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.writeConnectionsMax;
		this.writeConnectionsMax = newValue;
		this.firePropertyChanged(WRITE_CONNECTIONS_MAX_PROPERTY, old, newValue);
	}

	public Integer getDefaultWriteConnectionsMax() {
		return DEFAULT_WRITE_CONNECTIONS_MAX;
	}

}
