/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
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
		String LEVEL_PROPERTY = "level"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_LEVEL = "eclipselink.logging.level"; //$NON-NLS-1$
		EclipseLinkLoggingLevel DEFAULT_LEVEL = EclipseLinkLoggingLevel.info;
	
	Boolean getDefaultTimestamp();
	Boolean getTimestamp();
	void setTimestamp(Boolean timestamp);
		String TIMESTAMP_PROPERTY = "timestamp"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_TIMESTAMP = "eclipselink.logging.timestamp"; //$NON-NLS-1$
		Boolean DEFAULT_TIMESTAMP = Boolean.TRUE;
		Transformer<EclipseLinkLogging, Boolean> TIMESTAMP_TRANSFORMER = new TimestampTransformer();
		class TimestampTransformer
			extends TransformerAdapter<EclipseLinkLogging, Boolean>
		{
			@Override
			public Boolean transform(EclipseLinkLogging logging) {
				return logging.getTimestamp();
			}
		}
	
		BiClosure<EclipseLinkLogging, Boolean> SET_TIMESTAMP_CLOSURE = new SetTimestampClosure();
		class SetTimestampClosure
			extends BiClosureAdapter<EclipseLinkLogging, Boolean>
		{
			@Override
			public void execute(EclipseLinkLogging logging, Boolean timestamp) {
				logging.setTimestamp(timestamp);
			}
		}
	
	Boolean getDefaultThread();
	Boolean getThread();
	void setThread(Boolean thread);
		String THREAD_PROPERTY = "thread"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_THREAD = "eclipselink.logging.thread"; //$NON-NLS-1$
		Boolean DEFAULT_THREAD = Boolean.TRUE;
	
	Boolean getDefaultSession();
	Boolean getSession();
	void setSession(Boolean session);
		String SESSION_PROPERTY = "session"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_SESSION = "eclipselink.logging.session"; //$NON-NLS-1$
		Boolean DEFAULT_SESSION = Boolean.TRUE;
	
	Boolean getDefaultExceptions();
	Boolean getExceptions();
	void setExceptions(Boolean exceptions);
		String EXCEPTIONS_PROPERTY = "exceptions"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_EXCEPTIONS = "eclipselink.logging.exceptions"; //$NON-NLS-1$
		Boolean DEFAULT_EXCEPTIONS = Boolean.FALSE;
		
	String getDefaultLogFileLocation();
	String getLogFileLocation();
	void setLogFileLocation(String newLogFileLocation);
		String LOG_FILE_LOCATION_PROPERTY = "logFileLocation"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_LOG_FILE_LOCATION = "eclipselink.logging.file"; //$NON-NLS-1$
		String DEFAULT_LOG_FILE_LOCATION = null;		// No Default
		
	String getDefaultLogger();
	String getLogger();
	void setLogger(String newLogger);
	void setLogger(EclipseLinkLogger newLogger);
		String LOGGER_PROPERTY = "logger"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_LOGGER = "eclipselink.logging.logger"; //$NON-NLS-1$
		String DEFAULT_LOGGER = EclipseLinkLogger.default_logger.getPropertyValue();
		String[] RESERVED_LOGGER_NAMES = {EclipseLinkLogger.default_logger.getPropertyValue(), EclipseLinkLogger.java_logger.getPropertyValue(), EclipseLinkLogger.server_logger.getPropertyValue()};
		String ECLIPSELINK_LOGGER_CLASS_NAME = "org.eclipse.persistence.logging.SessionLog"; //$NON-NLS-1$
}
