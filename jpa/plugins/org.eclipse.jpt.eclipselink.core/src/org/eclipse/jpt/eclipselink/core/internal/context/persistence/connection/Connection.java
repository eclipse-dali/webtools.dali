/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitTransactionType;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.PersistenceUnitProperties;

/**
 *  Connection
 */
public interface Connection extends PersistenceUnitProperties
{
	PersistenceUnitTransactionType getDefaultTransactionType();
	PersistenceUnitTransactionType getTransactionType();
	void setTransactionType(PersistenceUnitTransactionType newTransactionType);
		// PersistenceUnit property
		static final String TRANSACTION_TYPE_PROPERTY = "transactionTypeProperty";
		static final PersistenceUnitTransactionType DEFAULT_TRANSACTION_TYPE = PersistenceUnitTransactionType.JTA;

	String getDefaultJtaDataSource();
	String getJtaDataSource();
	void setJtaDataSource(String newJtaDataSource);
		// PersistenceUnit property
		static final String JTA_DATA_SOURCE_PROPERTY = "jtaDataSourceProperty";
		static final String DEFAULT_JTA_DATA_SOURCE = "";

	String getDefaultNonJtaDataSource();
	String getNonJtaDataSource();
	void setNonJtaDataSource(String newNonJtaDataSource);
		// PersistenceUnit property
		static final String NON_JTA_DATA_SOURCE_PROPERTY = "nonJtaDataSourceProperty";
		static final String DEFAULT_NON_JTA_DATA_SOURCE = "";

	Boolean getDefaultNativeSql();
	Boolean getNativeSql();
	void setNativeSql(Boolean newNativeSql);
		static final String NATIVE_SQL_PROPERTY = "nativeSqlProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_NATIVE_SQL = "eclipselink.jdbc.native-sql";
		static final Boolean DEFAULT_NATIVE_SQL = Boolean.FALSE;

	BatchWriting getDefaultBatchWriting();
	BatchWriting getBatchWriting();
	void setBatchWriting(BatchWriting newBatchWriting);
		static final String BATCH_WRITING_PROPERTY = "batchWritingProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_BATCH_WRITING = "eclipselink.jdbc.batch-writing";
		static final BatchWriting DEFAULT_BATCH_WRITING = BatchWriting.none;
		
	Boolean getDefaultCacheStatements();
	Boolean getCacheStatements();
	void setCacheStatements(Boolean newCacheStatements);
		static final String CACHE_STATEMENTS_PROPERTY = "cacheStatementsProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_STATEMENTS = "eclipselink.jdbc.cache-statements";
		static final Boolean DEFAULT_CACHE_STATEMENTS = Boolean.FALSE;
		
	Integer getDefaultCacheStatementsSize();
	Integer getCacheStatementsSize();
	void setCacheStatementsSize(Integer newCacheStatementsSize);
		static final String CACHE_STATEMENTS_SIZE_PROPERTY = "cacheStatementsSizeProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_STATEMENTS_SIZE = "eclipselink.jdbc.cache-statements.size";
		static final Integer DEFAULT_CACHE_STATEMENTS_SIZE = 50;
		
	String getDefaultDriver();
	String getDriver();
	void setDriver(String newDriver);
		static final String DRIVER_PROPERTY = "driverProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_DRIVER = "eclipselink.jdbc.driver";
		static final String DEFAULT_DRIVER = "";
		
	String getDefaultUrl();
	String getUrl();
	void setUrl(String newUrl);
		static final String URL_PROPERTY = "urlProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_URL = "eclipselink.jdbc.url";
		static final String DEFAULT_URL = "";
		
	String getDefaultUser();
	String getUser();
	void setUser(String newUser);
		static final String USER_PROPERTY = "userProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_USER = "eclipselink.jdbc.user";
		static final String DEFAULT_USER = "";
		
	String getDefaultPassword();
	String getPassword();
	void setPassword(String newPassword);
		static final String PASSWORD_PROPERTY = "passwordProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_PASSWORD = "eclipselink.jdbc.password";
		static final String DEFAULT_PASSWORD = "";

	Boolean getDefaultBindParameters();
	Boolean getBindParameters();
	void setBindParameters(Boolean newBindParameters);
		static final String BIND_PARAMETERS_PROPERTY = "bindParametersProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_BIND_PARAMETERS = "eclipselink.jdbc.bind-parameters";
		static final Boolean DEFAULT_BIND_PARAMETERS = Boolean.TRUE;
		
	Boolean getDefaultReadConnectionsShared();
	Boolean getReadConnectionsShared();
	void setReadConnectionsShared(Boolean newReadConnectionsShared);
		static final String READ_CONNECTIONS_SHARED_PROPERTY = "readConnectionsSharedProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_READ_CONNECTIONS_SHARED = "eclipselink.jdbc.read-connections.shared";
		static final Boolean DEFAULT_READ_CONNECTIONS_SHARED = Boolean.FALSE;

	Integer getDefaultReadConnectionsMin();
	Integer getReadConnectionsMin();
	void setReadConnectionsMin(Integer newReadConnectionsMin);
		static final String READ_CONNECTIONS_MIN_PROPERTY = "readConnectionsMinProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_READ_CONNECTIONS_MIN = "eclipselink.jdbc.read-connections.min";
		static final Integer DEFAULT_READ_CONNECTIONS_MIN = 2;

	Integer getDefaultReadConnectionsMax();
	Integer getReadConnectionsMax();
	void setReadConnectionsMax(Integer newReadConnectionsMax);
		static final String READ_CONNECTIONS_MAX_PROPERTY = "readConnectionsMaxProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_READ_CONNECTIONS_MAX = "eclipselink.jdbc.read-connections.max";
		static final Integer DEFAULT_READ_CONNECTIONS_MAX = 2;

	Integer getDefaultWriteConnectionsMin();
	Integer getWriteConnectionsMin();
	void setWriteConnectionsMin(Integer newWriteConnectionsMin);
		static final String WRITE_CONNECTIONS_MIN_PROPERTY = "writeConnectionsMinProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_WRITE_CONNECTIONS_MIN = "eclipselink.jdbc.write-connections.min";
		static final Integer DEFAULT_WRITE_CONNECTIONS_MIN = 5;

	Integer getDefaultWriteConnectionsMax();
	Integer getWriteConnectionsMax();
	void setWriteConnectionsMax(Integer newWriteConnectionsMax);
		static final String WRITE_CONNECTIONS_MAX_PROPERTY = "writeConnectionsMaxProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_WRITE_CONNECTIONS_MAX = "eclipselink.jdbc.write-connections.max";
		static final Integer DEFAULT_WRITE_CONNECTIONS_MAX = 10;
}
