/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXmlEnumValue;

/**
 * Logger
 */
public enum EclipseLinkLogger implements PersistenceXmlEnumValue {
	default_logger("DefaultLogger", "org.eclipse.persistence.logging.DefaultSessionLog"),  //$NON-NLS-1$ //$NON-NLS-2$
	java_logger("JavaLogger", "org.eclipse.persistence.logging.JavaLog"),  //$NON-NLS-1$ //$NON-NLS-2$
	server_logger("ServerLogger", "org.eclipse.persistence.platform.server.ServerLog"); //$NON-NLS-1$ //$NON-NLS-2$

	private final String propertyValue;

	private final String className;

	EclipseLinkLogger(String propertyValue, String className) {
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
	public static EclipseLinkLogger getLoggerFor(String literal) {
		
		for( EclipseLinkLogger logger : EclipseLinkLogger.values()) {
			if(logger.toString().equals(literal)) {
				return logger;
			}
		}
		return null;
	}

	public static String getLoggerClassName(String loggerValue) {
		for (EclipseLinkLogger logger : values()) {
			if (logger.getPropertyValue() == loggerValue) {
				return logger.getClassName();
			}
		}
		return loggerValue;
	}
}