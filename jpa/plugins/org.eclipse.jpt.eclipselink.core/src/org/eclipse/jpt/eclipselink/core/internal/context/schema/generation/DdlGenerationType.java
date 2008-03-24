/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.schema.generation;

/**
 *  DdlGenerationType
 */
public enum DdlGenerationType {
	none, 
	create_tables, 
	drop_and_create_tables;
	
	// EclipseLink value string
	static final String NONE = "none";
	static final String CREATE_TABLES = "create-tables";
	static final String DROP_AND_CREATE_TABLES = "drop-and-create-tables";
}