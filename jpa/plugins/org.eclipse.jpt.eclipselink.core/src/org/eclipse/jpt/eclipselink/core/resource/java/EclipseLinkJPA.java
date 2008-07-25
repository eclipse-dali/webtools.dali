/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.java;


/**
 * EclipseLink JPA Java-related stuff (annotations etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface EclipseLinkJPA {

	// EclipseLink JPA package
	String PACKAGE = "org.eclipse.persistence.annotations";
	String PACKAGE_ = PACKAGE + ".";


	// ********** API **********

	// JPA annotations
	String CACHE = PACKAGE_ + "Cache";
		String CACHE__SHARED = "shared";
		String CACHE__TYPE = "type";
		String CACHE__ALWAYS_REFRESH = "alwaysRefresh";
		String CACHE__REFRESH_ONLY_IF_NEWER = "refreshOnlyIfNewer";
		String CACHE__DISABLE_HITS = "disableHits";
	String CACHE_TYPE = PACKAGE_ + "CacheType";
	String CACHE_TYPE_ = CACHE_TYPE + ".";
	String CACHE_TYPE__FULL = CACHE_TYPE_ + "FULL";
	String CACHE_TYPE__WEAK = CACHE_TYPE_ + "WEAK";
	String CACHE_TYPE__SOFT = CACHE_TYPE_ + "SOFT";
	String CACHE_TYPE__SOFT_WEAK = CACHE_TYPE_ + "SOFT_WEAK";
	String CACHE_TYPE__HARD_WEAK = CACHE_TYPE_ + "HARD_WEAK";
	String CACHE_TYPE__CACHE = CACHE_TYPE_ + "CACHE";
	String CACHE_TYPE__NONE = CACHE_TYPE_ + "NONE";

}
