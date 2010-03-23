/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.persistence.schema.generation;

/**
 *  OutputMode
 */
public enum OutputMode {
	both, 
	sql_script, 
	database;

	// EclipseLink value string
	public static final String BOTH = "both";
	public static final String DATABASE = "database";
	public static final String SQL_SCRIPT = "sql-script";
}
