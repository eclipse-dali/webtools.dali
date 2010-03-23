/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.persistence.logging;

/**
 *  LoggingLevel
 */
public enum LoggingLevel {
			off, 
			severe, 
			warning, 
			info, 
			config, 
			fine, 
			finer,
			finest,
			all;

	// EclipseLink value string
	public static final String OFF = "OFF";
	public static final String SEVERE = "SEVERE";
	public static final String WARNING = "WARNING";
	public static final String INFO = "INFO";
	public static final String CONFIG = "CONFIG";
	public static final String FINE = "FINE";
	public static final String FINER = "FINER";
	public static final String FINEST = "FINEST";
	public static final String ALL = "ALL";
}
