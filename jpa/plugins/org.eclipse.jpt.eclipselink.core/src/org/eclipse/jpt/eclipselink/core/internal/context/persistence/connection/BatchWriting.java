/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection;

/**
 *  BatchWriting
 */
public enum BatchWriting {
			none, 
			jdbc, 
			buffered, 
			oracle_jdbc;

	// EclipseLink value string
	static final String NONE = "None";
	static final String JDBC = "JDBC";
	static final String BUFFERED = "Buffered";
	static final String ORACLE_JDBC = "Oracle-JDBC";
}
