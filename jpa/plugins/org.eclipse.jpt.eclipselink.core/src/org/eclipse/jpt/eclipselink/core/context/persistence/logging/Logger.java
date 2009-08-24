/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.persistence.logging;

/**
 * Logger
 */
public enum Logger {
			default_logger, 
			java_logger, 
			server_logger;
	
	// EclipseLink value string
	public static final String DEFAULT_LOGGER = "DefaultLogger"; //$NON-NLS-1$
	public static final String JAVA_LOGGER = "JavaLogger"; //$NON-NLS-1$
	public static final String SERVER_LOGGER = "ServerLogger"; //$NON-NLS-1$
	
	// EclipseLink logger class names
	public static final String DEFAULT_LOGGER_CLASS_NAME = "org.eclipse.persistence.logging.DefaultSessionLog"; //$NON-NLS-1$
	public static final String JAVA_LOGGER_CLASS_NAME = "org.eclipse.persistence.logging.JavaLog"; //$NON-NLS-1$
	public static final String SERVER_LOGGER_CLASS_NAME = "org.eclipse.persistence.platform.server.ServerLog"; //$NON-NLS-1$
	
	/**
	 * Return the Logger value corresponding to the given literal.
	 */
	public static Logger getLoggerFor(String literal) {
		
		for( Logger logger : Logger.values()) {
			if(logger.toString().equals(literal)) {
				return logger;
			}
		}
		return null;
	}
	
	public static String getLoggerClassName(String loggerValue) {
		if (loggerValue == DEFAULT_LOGGER) {
			return DEFAULT_LOGGER_CLASS_NAME;
		}
		if (loggerValue == JAVA_LOGGER) {
			return JAVA_LOGGER_CLASS_NAME;
		}
		if (loggerValue == SERVER_LOGGER) {
			return SERVER_LOGGER_CLASS_NAME;
		}
		return loggerValue;
	}
}