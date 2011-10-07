/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

/**
 *  ExclusiveConnectionMode
 */
public enum ExclusiveConnectionMode {
	always,
	isolated,
	transactional;

	// EclipseLink value string
	static final String ALWAYS = "Always";
	static final String ISOLATED = "Isolated";
	static final String TRANSACTIONAL = "Transactional";

}
