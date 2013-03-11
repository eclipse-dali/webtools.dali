/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
 * EclipseLink logging
 */
public interface EclipseLinkLogging
	extends PersistenceUnitProperties
{
	EclipseLinkLoggingLevel getDefaultLevel();
	EclipseLinkLoggingLevel getLevel();
	void setLevel(EclipseLinkLoggingLevel level);
		static final String LEVEL_PROPERTY = "level"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_LEVEL = "eclipselink.logging.level"; //$NON-NLS-1$
		static final EclipseLinkLoggingLevel DEFAULT_LEVEL = EclipseLinkLoggingLevel.info;
	
	Boolean getDefaultTimestamp();
	Boolean getTimestamp();
	void setTimestamp(Boolean timestamp);
		static final String TIMESTAMP_PROPERTY = "timestamp"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_TIMESTAMP = "eclipselink.logging.timestamp"; //$NON-NLS-1$
		static final Boolean DEFAULT_TIMESTAMP = Boolean.TRUE;
	
	Boolean getDefaultThread();
	Boolean getThread();
	void setThread(Boolean thread);
		static final String THREAD_PROPERTY = "thread"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_THREAD = "eclipselink.logging.thread"; //$NON-NLS-1$
		static final Boolean DEFAULT_THREAD = Boolean.TRUE;
	
	Boolean getDefaultSession();
	Boolean getSession();
	void setSession(Boolean session);
		static final String SESSION_PROPERTY = "session"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_SESSION = "eclipselink.logging.session"; //$NON-NLS-1$
		static final Boolean DEFAULT_SESSION = Boolean.TRUE;
	
	Boolean getDefaultExceptions();
	Boolean getExceptions();
	void setExceptions(Boolean exceptions);
		static final String EXCEPTIONS_PROPERTY = "exceptions"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_EXCEPTIONS = "eclipselink.logging.exceptions"; //$NON-NLS-1$
		static final Boolean DEFAULT_EXCEPTIONS = Boolean.FALSE;
		
	String getDefaultLogFileLocation();
	String getLogFileLocation();
	void setLogFileLocation(String newLogFileLocation);
		static final String LOG_FILE_LOCATION_PROPERTY = "logFileLocation"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_LOG_FILE_LOCATION = "eclipselink.logging.file"; //$NON-NLS-1$
		static final String DEFAULT_LOG_FILE_LOCATION = null;		// No Default
		
	String getDefaultLogger();
	String getLogger();
	void setLogger(String newLogger);
	void setLogger(Logger newLogger);
		static final String LOGGER_PROPERTY = "logger"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_LOGGER = "eclipselink.logging.logger"; //$NON-NLS-1$
		static final String DEFAULT_LOGGER = Logger.default_logger.getPropertyValue();
		static final String[] RESERVED_LOGGER_NAMES = {Logger.default_logger.getPropertyValue(), Logger.java_logger.getPropertyValue(), Logger.server_logger.getPropertyValue()};
		String ECLIPSELINK_LOGGER_CLASS_NAME = "org.eclipse.persistence.logging.SessionLog"; //$NON-NLS-1$
}
