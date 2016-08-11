/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 * EclipseLink connection
 */
public interface EclipseLinkConnection
	extends PersistenceUnitProperties
{
	Boolean getNativeSql();
	void setNativeSql(Boolean newNativeSql);
		String NATIVE_SQL_PROPERTY = "nativeSql"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_NATIVE_SQL = "eclipselink.jdbc.native-sql"; //$NON-NLS-1$
	Boolean getDefaultNativeSql();
		String DEFAULT_NATIVE_SQL_PROPERTY = "defaultNativeSql"; //$NON-NLS-1$
		Boolean DEFAULT_NATIVE_SQL = Boolean.FALSE;

	EclipseLinkBatchWriting getBatchWriting();
	void setBatchWriting(EclipseLinkBatchWriting newBatchWriting);
		String BATCH_WRITING_PROPERTY = "batchWriting"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_BATCH_WRITING = "eclipselink.jdbc.batch-writing"; //$NON-NLS-1$
	EclipseLinkBatchWriting getDefaultBatchWriting();
		String DEFAULT_BATCH_WRITING_PROPERTY = "defaultBatchWriting"; //$NON-NLS-1$
		EclipseLinkBatchWriting DEFAULT_BATCH_WRITING = EclipseLinkBatchWriting.none;

	Boolean getCacheStatements();
	void setCacheStatements(Boolean newCacheStatements);
		String CACHE_STATEMENTS_PROPERTY = "cacheStatements"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_CACHE_STATEMENTS = "eclipselink.jdbc.cache-statements"; //$NON-NLS-1$
	Boolean getDefaultCacheStatements();
		String DEFAULT_CACHE_STATEMENTS_PROPERTY = "defaultCacheStatements"; //$NON-NLS-1$
		Boolean DEFAULT_CACHE_STATEMENTS = Boolean.FALSE;

	Integer getCacheStatementsSize();
	void setCacheStatementsSize(Integer newCacheStatementsSize);
		String CACHE_STATEMENTS_SIZE_PROPERTY = "cacheStatementsSize"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_CACHE_STATEMENTS_SIZE = "eclipselink.jdbc.cache-statements.size"; //$NON-NLS-1$
	Integer getDefaultCacheStatementsSize();
		String DEFAULT_CACHE_STATEMENTS_SIZE_PROPERTY = "defaultCacheStatementsSize"; //$NON-NLS-1$
		Integer DEFAULT_CACHE_STATEMENTS_SIZE = Integer.valueOf(50);

	String getDriver();
	void setDriver(String newDriver);
		String DRIVER_PROPERTY = "driver"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_DRIVER = "eclipselink.jdbc.driver"; //$NON-NLS-1$
	String getDefaultDriver();
		String DEFAULT_DRIVER_PROPERTY = "defaultDriver"; //$NON-NLS-1$
		String DEFAULT_DRIVER = ""; //$NON-NLS-1$

	String getUrl();
	void setUrl(String newUrl);
		String URL_PROPERTY = "url"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_URL = "eclipselink.jdbc.url"; //$NON-NLS-1$
	String getDefaultUrl();
		String DEFAULT_URL_PROPERTY = "defaultUrl"; //$NON-NLS-1$
		String DEFAULT_URL = ""; //$NON-NLS-1$

	String getUser();
	void setUser(String newUser);
		String USER_PROPERTY = "user"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_USER = "eclipselink.jdbc.user"; //$NON-NLS-1$
	String getDefaultUser();
		String DEFAULT_USER_PROPERTY = "defaultUser"; //$NON-NLS-1$
		String DEFAULT_USER = ""; //$NON-NLS-1$

	String getPassword();
	void setPassword(String newPassword);
		String PASSWORD_PROPERTY = "password"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_PASSWORD = "eclipselink.jdbc.password"; //$NON-NLS-1$
	String getDefaultPassword();
		String DEFAULT_PASSWORD_PROPERTY = "defaultPassword"; //$NON-NLS-1$
		String DEFAULT_PASSWORD = ""; //$NON-NLS-1$

	Boolean getBindParameters();
	void setBindParameters(Boolean newBindParameters);
		String BIND_PARAMETERS_PROPERTY = "bindParameters"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_BIND_PARAMETERS = "eclipselink.jdbc.bind-parameters"; //$NON-NLS-1$
	Boolean getDefaultBindParameters();
		String DEFAULT_BIND_PARAMETERS_PROPERTY = "defaultBindParameters"; //$NON-NLS-1$
		Boolean DEFAULT_BIND_PARAMETERS = Boolean.TRUE;

	Boolean getReadConnectionsShared();
	void setReadConnectionsShared(Boolean newReadConnectionsShared);
		String READ_CONNECTIONS_SHARED_PROPERTY = "readConnectionsShared"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_READ_CONNECTIONS_SHARED = "eclipselink.jdbc.read-connections.shared"; //$NON-NLS-1$
	Boolean getDefaultReadConnectionsShared();
		String DEFAULT_READ_CONNECTIONS_SHARED_PROPERTY = "defaultReadConnectionsShared"; //$NON-NLS-1$
		Boolean DEFAULT_READ_CONNECTIONS_SHARED = Boolean.FALSE;

	Integer getReadConnectionsMin();
	void setReadConnectionsMin(Integer newReadConnectionsMin);
		String READ_CONNECTIONS_MIN_PROPERTY = "readConnectionsMin"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_READ_CONNECTIONS_MIN = "eclipselink.jdbc.read-connections.min"; //$NON-NLS-1$
	Integer getDefaultReadConnectionsMin();
		String DEFAULT_READ_CONNECTIONS_MIN_PROPERTY = "defaultReadConnectionsMin"; //$NON-NLS-1$
		Integer DEFAULT_READ_CONNECTIONS_MIN = Integer.valueOf(2);

	Integer getReadConnectionsMax();
	void setReadConnectionsMax(Integer newReadConnectionsMax);
		String READ_CONNECTIONS_MAX_PROPERTY = "readConnectionsMax"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_READ_CONNECTIONS_MAX = "eclipselink.jdbc.read-connections.max"; //$NON-NLS-1$
	Integer getDefaultReadConnectionsMax();
		String DEFAULT_READ_CONNECTIONS_MAX_PROPERTY = "defaultReadConnectionsMax"; //$NON-NLS-1$
		Integer DEFAULT_READ_CONNECTIONS_MAX = Integer.valueOf(2);

	Integer getWriteConnectionsMin();
	void setWriteConnectionsMin(Integer newWriteConnectionsMin);
		String WRITE_CONNECTIONS_MIN_PROPERTY = "writeConnectionsMin"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_WRITE_CONNECTIONS_MIN = "eclipselink.jdbc.write-connections.min"; //$NON-NLS-1$
	Integer getDefaultWriteConnectionsMin();
		String DEFAULT_WRITE_CONNECTIONS_MIN_PROPERTY = "defaultWriteConnectionsMin"; //$NON-NLS-1$
		Integer DEFAULT_WRITE_CONNECTIONS_MIN = Integer.valueOf(5);


	Integer getWriteConnectionsMax();
	void setWriteConnectionsMax(Integer newWriteConnectionsMax);
		String WRITE_CONNECTIONS_MAX_PROPERTY = "writeConnectionsMax"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_WRITE_CONNECTIONS_MAX = "eclipselink.jdbc.write-connections.max"; //$NON-NLS-1$
	Integer getDefaultWriteConnectionsMax();
		String DEFAULT_WRITE_CONNECTIONS_MAX_PROPERTY = "defaultWriteConnectionsMax"; //$NON-NLS-1$
		Integer DEFAULT_WRITE_CONNECTIONS_MAX = Integer.valueOf(10);

	EclipseLinkExclusiveConnectionMode getExclusiveConnectionMode();
	void setExclusiveConnectionMode(EclipseLinkExclusiveConnectionMode newExclusiveConnectionMode);
		String EXCLUSIVE_CONNECTION_MODE_PROPERTY = "exclusiveConnectionMode"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_EXCLUSIVE_CONNECTION_MODE = "eclipselink.jdbc.exclusive-connection.mode"; //$NON-NLS-1$
	EclipseLinkExclusiveConnectionMode getDefaultExclusiveConnectionMode();
		String DEFAULT_EXCLUSIVE_CONNECTION_MODE_PROPERTY = "defaultExclusiveConnectionMode"; //$NON-NLS-1$
		EclipseLinkExclusiveConnectionMode DEFAULT_EXCLUSIVE_CONNECTION_MODE = EclipseLinkExclusiveConnectionMode.transactional;

	Boolean getLazyConnection();
	void setLazyConnection(Boolean newLazyConnection);
		String LAZY_CONNECTION_PROPERTY = "lazyConnection"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_LAZY_CONNECTION = "eclipselink.jdbc.exclusive-connection.is-lazy"; //$NON-NLS-1$
	Boolean getDefaultLazyConnection();
		String DEFAULT_LAZY_CONNECTION_PROPERTY = "defaultLazyConnection"; //$NON-NLS-1$
		Boolean DEFAULT_LAZY_CONNECTION = Boolean.TRUE;
}
