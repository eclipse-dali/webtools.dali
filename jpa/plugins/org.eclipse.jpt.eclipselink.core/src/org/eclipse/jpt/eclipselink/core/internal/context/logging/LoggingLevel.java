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
			finest;

	// EclipseLink value string
	static final String OFF = "OFF";
	static final String SEVERE = "SEVERE";
	static final String WARNING = "WARNING";
	static final String INFO = "INFO";
	static final String CONFIG = "CONFIG";
	static final String FINE = "FINE";
	static final String FINER = "FINER";
	static final String FINEST = "FINEST";
}
