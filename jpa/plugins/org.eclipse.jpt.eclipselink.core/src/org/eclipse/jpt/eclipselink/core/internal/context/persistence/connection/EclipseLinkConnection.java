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
import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 *  EclipseLinkConnection
 */
public class EclipseLinkConnection extends EclipseLinkPersistenceUnitProperties
	implements Connection
{
	// ********** PersistenceUnit properties **********
	private PersistenceUnitTransactionType transactionType;
	private String jtaDataSource;
	private String nonJtaDataSource;
	
	// ********** EclipseLink properties **********
	private BatchWriting batchWriting;
	private Boolean nativeSql;
	private Boolean cacheStatements;
	private Integer cacheStatementsSize;
	private String driver;
	private String url;
	private String user;
	private String password;
	private Boolean bindParameters;
	private Boolean readConnectionsShared;
	private Integer readConnectionsMin;
	private Integer readConnectionsMax;
	private Integer writeConnectionsMin;
	private Integer writeConnectionsMax;
	

	// ********** constructors **********
	public EclipseLinkConnection(PersistenceUnit parent, ListValueModel<PersistenceUnit.Property> propertyListAdapter) {
		super(parent, propertyListAdapter);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		// TOREVIEW - handle incorrect String in persistence.xml
		this.transactionType = 
			this.getPersistenceUnit().getSpecifiedTransactionType();
		this.jtaDataSource = 
			this.getPersistenceUnit().getJtaDataSource();
		this.nonJtaDataSource = 
			this.getPersistenceUnit().getNonJtaDataSource();
		this.batchWriting = 
			this.getEnumValue(ECLIPSELINK_BATCH_WRITING, BatchWriting.values());
		this.nativeSql = 
			this.getBooleanValue(ECLIPSELINK_NATIVE_SQL);
		this.cacheStatements = 
			this.getBooleanValue(ECLIPSELINK_CACHE_STATEMENTS);
		this.cacheStatementsSize = 
			this.getIntegerValue(ECLIPSELINK_CACHE_STATEMENTS_SIZE);
		this.driver = 
			this.getStringValue(ECLIPSELINK_DRIVER);
		this.url = 
			this.getStringValue(ECLIPSELINK_URL);
		this.user = 
			this.getStringValue(ECLIPSELINK_USER);
		this.password = 
			this.getStringValue(ECLIPSELINK_PASSWORD);
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

	/**
	 * Initialize and add listeners to the persistence unit.
	 */
	@Override
	protected void initialize(PersistenceUnit parent, ListValueModel<PersistenceUnit.Property> propertyListAdapter) {
		super.initialize(parent, propertyListAdapter);

		this.getPersistenceUnit().addPropertyChangeListener(
			PersistenceUnit.SPECIFIED_TRANSACTION_TYPE_PROPERTY, 
			this.buildTransactionTypeChangeListener());
		this.getPersistenceUnit().addPropertyChangeListener(
			PersistenceUnit.JTA_DATA_SOURCE_PROPERTY,
			this.buildJtaDataSourceChangeListener());
		this.getPersistenceUnit().addPropertyChangeListener(
			PersistenceUnit.NON_JTA_DATA_SOURCE_PROPERTY,
			this.buildNonJtaDataSourceChangeListener());
	}

	// ********** behavior **********
	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = EclipseLink property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
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

	public void propertyChanged(PropertyChangeEvent event) {
		String aspectName = event.getAspectName();
		if (aspectName.equals(NATIVE_SQL_PROPERTY)) {
			this.nativeSqlChanged(event);
		}
		 else if (aspectName.equals(BATCH_WRITING_PROPERTY)) {
			this.batchWritingChanged(event);
		}
		 else if (aspectName.equals(CACHE_STATEMENTS_PROPERTY)) {
			 this.cacheStatementsChanged(event);
		 }
		 else if (aspectName.equals(DRIVER_PROPERTY)) {
			 this.driverChanged(event);
		 }
		 else if (aspectName.equals(URL_PROPERTY)) {
			 this.urlChanged(event);
		 }
		 else if (aspectName.equals(USER_PROPERTY)) {
			 this.userChanged(event);
		 }
		 else if (aspectName.equals(PASSWORD_PROPERTY)) {
			 this.passwordChanged(event);
		 }
		 else if (aspectName.equals(BIND_PARAMETERS_PROPERTY)) {
			 this.bindParametersChanged(event);
		 }
		 else if (aspectName.equals(READ_CONNECTIONS_SHARED_PROPERTY)) {
			 this.readConnectionsSharedChanged(event);
		 }
		 else if (aspectName.equals(CACHE_STATEMENTS_SIZE_PROPERTY)) {
			 this.cacheStatementsSizeChanged(event);
		 }
		 else if (aspectName.equals(READ_CONNECTIONS_MIN_PROPERTY)) {
			 this.readConnectionsMinChanged(event);
		 }
		 else if (aspectName.equals(READ_CONNECTIONS_MAX_PROPERTY)) {
			 this.readConnectionsMaxChanged(event);
		 }
		 else if (aspectName.equals(WRITE_CONNECTIONS_MIN_PROPERTY)) {
			 this.writeConnectionsMinChanged(event);
		 }
		 else if (aspectName.equals(WRITE_CONNECTIONS_MAX_PROPERTY)) {
			 this.writeConnectionsMaxChanged(event);
		 }
	}

	// ********** TransactionType **********
	
	public PersistenceUnitTransactionType getTransactionType() {
		return this.transactionType;
	}
	
	public void setTransactionType(PersistenceUnitTransactionType newTransactionType) {
		PersistenceUnitTransactionType old = this.transactionType;
		this.transactionType = newTransactionType;
		
		this.getPersistenceUnit().setSpecifiedTransactionType( newTransactionType);
		this.firePropertyChanged(TRANSACTION_TYPE_PROPERTY, old, newTransactionType);
	}

	private void transactionTypeChanged(PropertyChangeEvent event) {
		PersistenceUnitTransactionType newValue = (PersistenceUnitTransactionType) event.getNewValue();
		PersistenceUnitTransactionType old = this.transactionType;
		this.transactionType = newValue;
		this.firePropertyChanged(TRANSACTION_TYPE_PROPERTY, old, newValue);
	}
	
	public PersistenceUnitTransactionType getDefaultTransactionType() {
		return DEFAULT_TRANSACTION_TYPE;
	}

	protected PropertyChangeListener buildTransactionTypeChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				EclipseLinkConnection.this.transactionTypeChanged(event);
			}
		};
	}

	// ********** JtaDataSource **********
	
	public String getJtaDataSource() {
		return this.jtaDataSource;
	}
	
	public void setJtaDataSource(String newJtaDataSource) {
		String old = this.jtaDataSource;
		this.jtaDataSource = newJtaDataSource;
		
		this.getPersistenceUnit().setJtaDataSource( newJtaDataSource);
		this.firePropertyChanged(JTA_DATA_SOURCE_PROPERTY, old, newJtaDataSource);
	}

	private void jtaDataSourceChanged(PropertyChangeEvent event) {
		String newValue = (String) event.getNewValue();
		String old = this.jtaDataSource;
		this.jtaDataSource = newValue;
		this.firePropertyChanged(JTA_DATA_SOURCE_PROPERTY, old, newValue);
	}
	
	public String getDefaultJtaDataSource() {
		return DEFAULT_JTA_DATA_SOURCE;
	}

	protected PropertyChangeListener buildJtaDataSourceChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				EclipseLinkConnection.this.jtaDataSourceChanged(event);
			}
		};
	}

	// ********** NonJtaDataSource **********
	
	public String getNonJtaDataSource() {
		return this.nonJtaDataSource;
	}
	
	public void setNonJtaDataSource(String newNonJtaDataSource) {
		String old = this.nonJtaDataSource;
		this.nonJtaDataSource = newNonJtaDataSource;
		
		this.getPersistenceUnit().setNonJtaDataSource( newNonJtaDataSource);
		this.firePropertyChanged(NON_JTA_DATA_SOURCE_PROPERTY, old, newNonJtaDataSource);
	}

	private void nonJtaDataSourceChanged(PropertyChangeEvent event) {
		String newValue = (String) event.getNewValue();
		String old = this.nonJtaDataSource;
		this.nonJtaDataSource = newValue;
		this.firePropertyChanged(NON_JTA_DATA_SOURCE_PROPERTY, old, newValue);
	}
	
	public String getDefaultNonJtaDataSource() {
		return DEFAULT_NON_JTA_DATA_SOURCE;
	}

	protected PropertyChangeListener buildNonJtaDataSourceChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				EclipseLinkConnection.this.nonJtaDataSourceChanged(event);
			}
		};
	}

	// ********** NativeSql **********
	public Boolean getNativeSql() {
		return this.nativeSql;
	}

	public void setNativeSql(Boolean newNativeSql) {
		Boolean old = this.nativeSql;
		this.nativeSql = newNativeSql;
		this.putProperty(NATIVE_SQL_PROPERTY, newNativeSql);
		this.firePropertyChanged(NATIVE_SQL_PROPERTY, old, newNativeSql);
	}

	private void nativeSqlChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.nativeSql;
		this.nativeSql = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultNativeSql() {
		return DEFAULT_NATIVE_SQL;
	}

	// ********** BatchWriting **********
	
	public BatchWriting getBatchWriting() {
		return this.batchWriting;
	}
	
	public void setBatchWriting(BatchWriting newBatchWriting) {
		BatchWriting old = this.batchWriting;
		this.batchWriting = newBatchWriting;
		this.putProperty(BATCH_WRITING_PROPERTY, newBatchWriting);
		this.firePropertyChanged(BATCH_WRITING_PROPERTY, old, newBatchWriting);
	}

	private void batchWritingChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		BatchWriting newValue = getEnumValueOf(stringValue, BatchWriting.values());
		BatchWriting old = this.batchWriting;
		this.batchWriting = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}
	
	public BatchWriting getDefaultBatchWriting() {
		return DEFAULT_BATCH_WRITING;
	}

	// ********** CacheStatements **********
	public Boolean getCacheStatements() {
		return this.cacheStatements;
	}

	public void setCacheStatements(Boolean newCacheStatements) {
		Boolean old = this.cacheStatements;
		this.cacheStatements = newCacheStatements;
		this.putProperty(CACHE_STATEMENTS_PROPERTY, newCacheStatements);
		this.firePropertyChanged(CACHE_STATEMENTS_PROPERTY, old, newCacheStatements);
	}

	private void cacheStatementsChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.cacheStatements;
		this.cacheStatements = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultCacheStatements() {
		return DEFAULT_CACHE_STATEMENTS;
	}

	// ********** CacheStatementsSize **********
	public Integer getCacheStatementsSize() {
		return this.cacheStatementsSize;
	}

	public void setCacheStatementsSize(Integer newCacheStatementsSize) {
		Integer old = this.cacheStatementsSize;
		this.cacheStatementsSize = newCacheStatementsSize;
		this.putProperty(CACHE_STATEMENTS_SIZE_PROPERTY, newCacheStatementsSize);
		this.firePropertyChanged(CACHE_STATEMENTS_SIZE_PROPERTY, old, newCacheStatementsSize);
	}

	private void cacheStatementsSizeChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.cacheStatementsSize;
		this.cacheStatementsSize = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Integer getDefaultCacheStatementsSize() {
		return DEFAULT_CACHE_STATEMENTS_SIZE;
	}

	// ********** Driver **********
	public String getDriver() {
		return this.driver;
	}

	public void setDriver(String newDriver) {
		String old = this.driver;
		this.driver = newDriver;
		this.putProperty(DRIVER_PROPERTY, newDriver);
		this.firePropertyChanged(DRIVER_PROPERTY, old, newDriver);
	}

	private void driverChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		String old = this.driver;
		this.driver = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultDriver() {
		return DEFAULT_DRIVER;
	}

	// ********** URL **********
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String newUrl) {
		String old = this.url;
		this.url = newUrl;
		this.putProperty(URL_PROPERTY, newUrl);
		this.firePropertyChanged(URL_PROPERTY, old, newUrl);
	}

	private void urlChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		String old = this.url;
		this.url = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultUrl() {
		return DEFAULT_URL;
	}

	// ********** User **********
	public String getUser() {
		return this.user;
	}

	public void setUser(String newUser) {
		String old = this.user;
		this.user = newUser;
		this.putProperty(USER_PROPERTY, newUser);
		this.firePropertyChanged(USER_PROPERTY, old, newUser);
	}

	private void userChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		String old = this.user;
		this.user = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultUser() {
		return DEFAULT_USER;
	}

	// ********** Password **********
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String newPassword) {
		String old = this.password;
		this.password = newPassword;
		this.putProperty(PASSWORD_PROPERTY, newPassword);
		this.firePropertyChanged(PASSWORD_PROPERTY, old, newPassword);
	}

	private void passwordChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		String old = this.password;
		this.password = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultPassword() {
		return DEFAULT_PASSWORD;
	}

	// ********** BindParameters **********
	public Boolean getBindParameters() {
		return this.bindParameters;
	}

	public void setBindParameters(Boolean newBindParameters) {
		Boolean old = this.bindParameters;
		this.bindParameters = newBindParameters;
		this.putProperty(BIND_PARAMETERS_PROPERTY, newBindParameters);
		this.firePropertyChanged(BIND_PARAMETERS_PROPERTY, old, newBindParameters);
	}

	private void bindParametersChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.bindParameters;
		this.bindParameters = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultBindParameters() {
		return DEFAULT_BIND_PARAMETERS;
	}

	// ********** ReadConnectionsShared **********
	public Boolean getReadConnectionsShared() {
		return this.readConnectionsShared;
	}

	public void setReadConnectionsShared(Boolean newReadConnectionsShared) {
		Boolean old = this.readConnectionsShared;
		this.readConnectionsShared = newReadConnectionsShared;
		this.putProperty(READ_CONNECTIONS_SHARED_PROPERTY, newReadConnectionsShared);
		this.firePropertyChanged(READ_CONNECTIONS_SHARED_PROPERTY, old, newReadConnectionsShared);
	}

	private void readConnectionsSharedChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.readConnectionsShared;
		this.readConnectionsShared = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultReadConnectionsShared() {
		return DEFAULT_READ_CONNECTIONS_SHARED;
	}

	// ********** ReadConnectionsMin **********
	public Integer getReadConnectionsMin() {
		return this.readConnectionsMin;
	}

	public void setReadConnectionsMin(Integer newReadConnectionsMin) {
		Integer old = this.readConnectionsMin;
		this.readConnectionsMin = newReadConnectionsMin;
		this.putProperty(READ_CONNECTIONS_MIN_PROPERTY, newReadConnectionsMin);
		this.firePropertyChanged(READ_CONNECTIONS_MIN_PROPERTY, old, newReadConnectionsMin);
	}

	private void readConnectionsMinChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.readConnectionsMin;
		this.readConnectionsMin = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Integer getDefaultReadConnectionsMin() {
		return DEFAULT_READ_CONNECTIONS_MIN;
	}

	// ********** ReadConnectionsMax **********
	public Integer getReadConnectionsMax() {
		return this.readConnectionsMax;
	}

	public void setReadConnectionsMax(Integer newReadConnectionsMax) {
		Integer old = this.readConnectionsMax;
		this.readConnectionsMax = newReadConnectionsMax;
		this.putProperty(READ_CONNECTIONS_MAX_PROPERTY, newReadConnectionsMax);
		this.firePropertyChanged(READ_CONNECTIONS_MAX_PROPERTY, old, newReadConnectionsMax);
	}

	private void readConnectionsMaxChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.readConnectionsMax;
		this.readConnectionsMax = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Integer getDefaultReadConnectionsMax() {
		return DEFAULT_READ_CONNECTIONS_MAX;
	}

	// ********** WriteConnectionsMin **********
	public Integer getWriteConnectionsMin() {
		return this.writeConnectionsMin;
	}

	public void setWriteConnectionsMin(Integer newWriteConnectionsMin) {
		Integer old = this.writeConnectionsMin;
		this.writeConnectionsMin = newWriteConnectionsMin;
		this.putProperty(WRITE_CONNECTIONS_MIN_PROPERTY, newWriteConnectionsMin);
		this.firePropertyChanged(WRITE_CONNECTIONS_MIN_PROPERTY, old, newWriteConnectionsMin);
	}

	private void writeConnectionsMinChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.writeConnectionsMin;
		this.writeConnectionsMin = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Integer getDefaultWriteConnectionsMin() {
		return DEFAULT_WRITE_CONNECTIONS_MIN;
	}

	// ********** WriteConnectionsMax **********
	public Integer getWriteConnectionsMax() {
		return this.writeConnectionsMax;
	}

	public void setWriteConnectionsMax(Integer newWriteConnectionsMax) {
		Integer old = this.writeConnectionsMax;
		this.writeConnectionsMax = newWriteConnectionsMax;
		this.putProperty(WRITE_CONNECTIONS_MAX_PROPERTY, newWriteConnectionsMax);
		this.firePropertyChanged(WRITE_CONNECTIONS_MAX_PROPERTY, old, newWriteConnectionsMax);
	}

	private void writeConnectionsMaxChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Integer newValue = getIntegerValueOf(stringValue);
		
		Integer old = this.writeConnectionsMax;
		this.writeConnectionsMax = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Integer getDefaultWriteConnectionsMax() {
		return DEFAULT_WRITE_CONNECTIONS_MAX;
	}

}
