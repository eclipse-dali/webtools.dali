/*******************************************************************************
* Copyright (c) 2008, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 *  Connection
 */
public interface Connection extends PersistenceUnitProperties
{
	Boolean getDefaultNativeSql();
	Boolean getNativeSql();
	void setNativeSql(Boolean newNativeSql);
		static final String NATIVE_SQL_PROPERTY = "nativeSql"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_NATIVE_SQL = "eclipselink.jdbc.native-sql"; //$NON-NLS-1$
		static final Boolean DEFAULT_NATIVE_SQL = Boolean.FALSE;

	BatchWriting getDefaultBatchWriting();
	BatchWriting getBatchWriting();
	void setBatchWriting(BatchWriting newBatchWriting);
		static final String BATCH_WRITING_PROPERTY = "batchWriting"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_BATCH_WRITING = "eclipselink.jdbc.batch-writing"; //$NON-NLS-1$
		static final BatchWriting DEFAULT_BATCH_WRITING = BatchWriting.none;

	Boolean getDefaultCacheStatements();
	Boolean getCacheStatements();
	void setCacheStatements(Boolean newCacheStatements);
		static final String CACHE_STATEMENTS_PROPERTY = "cacheStatements"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_STATEMENTS = "eclipselink.jdbc.cache-statements"; //$NON-NLS-1$
		static final Boolean DEFAULT_CACHE_STATEMENTS = Boolean.FALSE;

	Integer getDefaultCacheStatementsSize();
	Integer getCacheStatementsSize();
	void setCacheStatementsSize(Integer newCacheStatementsSize);
		static final String CACHE_STATEMENTS_SIZE_PROPERTY = "cacheStatementsSize"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_CACHE_STATEMENTS_SIZE = "eclipselink.jdbc.cache-statements.size"; //$NON-NLS-1$
		static final Integer DEFAULT_CACHE_STATEMENTS_SIZE = Integer.valueOf(50);

	String getDefaultDriver();
	String getDriver();
	void setDriver(String newDriver);
		static final String DRIVER_PROPERTY = "driver"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_DRIVER = "eclipselink.jdbc.driver"; //$NON-NLS-1$
		static final String DEFAULT_DRIVER = ""; //$NON-NLS-1$

	String getDefaultUrl();
	String getUrl();
	void setUrl(String newUrl);
		static final String URL_PROPERTY = "url"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_URL = "eclipselink.jdbc.url"; //$NON-NLS-1$
		static final String DEFAULT_URL = ""; //$NON-NLS-1$

	String getDefaultUser();
	String getUser();
	void setUser(String newUser);
		static final String USER_PROPERTY = "user"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_USER = "eclipselink.jdbc.user"; //$NON-NLS-1$
		static final String DEFAULT_USER = ""; //$NON-NLS-1$

	String getDefaultPassword();
	String getPassword();
	void setPassword(String newPassword);
		static final String PASSWORD_PROPERTY = "password"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_PASSWORD = "eclipselink.jdbc.password"; //$NON-NLS-1$
		static final String DEFAULT_PASSWORD = ""; //$NON-NLS-1$

	Boolean getDefaultBindParameters();
	Boolean getBindParameters();
	void setBindParameters(Boolean newBindParameters);
		static final String BIND_PARAMETERS_PROPERTY = "bindParameters"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_BIND_PARAMETERS = "eclipselink.jdbc.bind-parameters"; //$NON-NLS-1$
		static final Boolean DEFAULT_BIND_PARAMETERS = Boolean.TRUE;

	Boolean getDefaultReadConnectionsShared();
	Boolean getReadConnectionsShared();
	void setReadConnectionsShared(Boolean newReadConnectionsShared);
		static final String READ_CONNECTIONS_SHARED_PROPERTY = "readConnectionsShared"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_READ_CONNECTIONS_SHARED = "eclipselink.jdbc.read-connections.shared"; //$NON-NLS-1$
		static final Boolean DEFAULT_READ_CONNECTIONS_SHARED = Boolean.FALSE;

	Integer getDefaultReadConnectionsMin();
	Integer getReadConnectionsMin();
	void setReadConnectionsMin(Integer newReadConnectionsMin);
		static final String READ_CONNECTIONS_MIN_PROPERTY = "readConnectionsMin"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_READ_CONNECTIONS_MIN = "eclipselink.jdbc.read-connections.min"; //$NON-NLS-1$
		static final Integer DEFAULT_READ_CONNECTIONS_MIN = Integer.valueOf(2);

	Integer getDefaultReadConnectionsMax();
	Integer getReadConnectionsMax();
	void setReadConnectionsMax(Integer newReadConnectionsMax);
		static final String READ_CONNECTIONS_MAX_PROPERTY = "readConnectionsMax"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_READ_CONNECTIONS_MAX = "eclipselink.jdbc.read-connections.max"; //$NON-NLS-1$
		static final Integer DEFAULT_READ_CONNECTIONS_MAX = Integer.valueOf(2);

	Integer getDefaultWriteConnectionsMin();
	Integer getWriteConnectionsMin();
	void setWriteConnectionsMin(Integer newWriteConnectionsMin);
		static final String WRITE_CONNECTIONS_MIN_PROPERTY = "writeConnectionsMin"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_WRITE_CONNECTIONS_MIN = "eclipselink.jdbc.write-connections.min"; //$NON-NLS-1$
		static final Integer DEFAULT_WRITE_CONNECTIONS_MIN = Integer.valueOf(5);

	Integer getDefaultWriteConnectionsMax();
	Integer getWriteConnectionsMax();
	void setWriteConnectionsMax(Integer newWriteConnectionsMax);
		static final String WRITE_CONNECTIONS_MAX_PROPERTY = "writeConnectionsMax"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_WRITE_CONNECTIONS_MAX = "eclipselink.jdbc.write-connections.max"; //$NON-NLS-1$
		static final Integer DEFAULT_WRITE_CONNECTIONS_MAX = Integer.valueOf(10);

	ExclusiveConnectionMode getDefaultExclusiveConnectionMode();
	ExclusiveConnectionMode getExclusiveConnectionMode();
	void setExclusiveConnectionMode(ExclusiveConnectionMode newExclusiveConnectionMode);
		static final String EXCLUSIVE_CONNECTION_MODE_PROPERTY = "exclusiveConnectionMode"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_EXCLUSIVE_CONNECTION_MODE = "eclipselink.jdbc.exclusive-connection.mode"; //$NON-NLS-1$
		static final ExclusiveConnectionMode DEFAULT_EXCLUSIVE_CONNECTION_MODE = ExclusiveConnectionMode.transactional;

	Boolean getDefaultLazyConnection();
	Boolean getLazyConnection();
	void setLazyConnection(Boolean newLazyConnection);
		static final String LAZY_CONNECTION_PROPERTY = "lazyConnection"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_LAZY_CONNECTION = "eclipselink.jdbc.exclusive-connection.is-lazy"; //$NON-NLS-1$
		static final Boolean DEFAULT_LAZY_CONNECTION = Boolean.TRUE;

}
