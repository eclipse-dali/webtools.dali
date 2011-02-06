/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence.caching;

/**
 *  FlushClearCache
 */
public enum FlushClearCache {
	drop,
	drop_invalidate,
	merge; 

	// EclipseLink value string
	public static final String DROP = "Drop";
	public static final String DROP_INVALIDATE = "DropInvalidate";
	public static final String MERGE = "Merge";
}
