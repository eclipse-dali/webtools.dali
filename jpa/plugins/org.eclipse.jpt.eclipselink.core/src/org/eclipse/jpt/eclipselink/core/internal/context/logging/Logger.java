/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.logging;

/**
 * Logger
 */
public enum Logger {
			default_logger, 
			java_logger, 
			server_logger;
	
	// EclipseLink value string
	public static final String DEFAULT_LOGGER = "DefaultLogger";
	public static final String JAVA_LOGGER = "JavaLogger";
	public static final String SERVER_LOGGER = "ServerLogger";
	
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
}