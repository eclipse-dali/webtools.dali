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
 *  CacheType
 */
public enum CacheType {
	soft_weak, 
	hard_weak, 
	weak,
	soft,
	full,
	none; 

	// EclipseLink value string
	public static final String FULL = "Full";
	public static final String HARD_WEAK = "HardWeak";
	public static final String NONE = "NONE";
	public static final String SOFT = "Soft";
	public static final String SOFT_WEAK = "SoftWeak";
	public static final String WEAK = "Weak";
}
