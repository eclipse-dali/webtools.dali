/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlEnumValue;

/**
 * Logger
 */
public enum Logger implements PersistenceXmlEnumValue {
	default_logger("DefaultLogger", "org.eclipse.persistence.logging.DefaultSessionLog"),  //$NON-NLS-1$ //$NON-NLS-2$
	java_logger("JavaLogger", "org.eclipse.persistence.logging.JavaLog"),  //$NON-NLS-1$ //$NON-NLS-2$
	server_logger("ServerLogger", "org.eclipse.persistence.platform.server.ServerLog"); //$NON-NLS-1$ //$NON-NLS-2$

	private final String propertyValue;

	private final String className;

	Logger(String propertyValue, String className) {
		this.propertyValue = propertyValue;
		this.className = className;
	}

	public String getPropertyValue() {
		return this.propertyValue;
	}

	public String getClassName() {
		return this.className;
	}

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
		for (Logger logger : values()) {
			if (logger.getPropertyValue() == loggerValue) {
				return logger.getClassName();
			}
		}
		return loggerValue;
	}
}