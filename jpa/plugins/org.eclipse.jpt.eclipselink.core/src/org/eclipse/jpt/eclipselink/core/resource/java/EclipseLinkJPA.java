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
		String CACHE__EXPIRY = "expiry";
		String CACHE__EXPIRY_TIME_OF_DAY = "expiryTimeOfDay";
		String CACHE__TYPE = "type";
		String CACHE__SIZE = "size";
		String CACHE__ALWAYS_REFRESH = "alwaysRefresh";
		String CACHE__REFRESH_ONLY_IF_NEWER = "refreshOnlyIfNewer";
		String CACHE__DISABLE_HITS = "disableHits";
		String CACHE__COORDINATION_TYPE = "coordinationType";
	String CACHE_TYPE = PACKAGE_ + "CacheType";
	String CACHE_TYPE_ = CACHE_TYPE + ".";
	String CACHE_TYPE__FULL = CACHE_TYPE_ + "FULL";
	String CACHE_TYPE__WEAK = CACHE_TYPE_ + "WEAK";
	String CACHE_TYPE__SOFT = CACHE_TYPE_ + "SOFT";
	String CACHE_TYPE__SOFT_WEAK = CACHE_TYPE_ + "SOFT_WEAK";
	String CACHE_TYPE__HARD_WEAK = CACHE_TYPE_ + "HARD_WEAK";
	String CACHE_TYPE__CACHE = CACHE_TYPE_ + "CACHE";
	String CACHE_TYPE__NONE = CACHE_TYPE_ + "NONE";

	String CACHE_COORDINATION_TYPE = PACKAGE_ + "CacheCoordinationType";
	String CACHE_COORDINATION_TYPE_ = CACHE_COORDINATION_TYPE + ".";
	String CACHE_COORDINATION_TYPE__SEND_OBJECT_CHANGES = CACHE_COORDINATION_TYPE_ + "SEND_OBJECT_CHANGES";
	String CACHE_COORDINATION_TYPE__INVALIDATE_CHANGED_OBJECTS = CACHE_COORDINATION_TYPE_ + "INVALIDATE_CHANGED_OBJECTS";
	String CACHE_COORDINATION_TYPE__SEND_NEW_OBJECTS_WITH_CHANGES = CACHE_COORDINATION_TYPE_ + "SEND_NEW_OBJECTS_WITH_CHANGES";
	String CACHE_COORDINATION_TYPE__NONE = CACHE_COORDINATION_TYPE_ + "NONE";


	String EXISTENCE_CHECKING = PACKAGE_ + "ExistenceChecking";
	String EXISTENCE_CHECKING__VALUE = "value";

	String EXISTENCE_TYPE = PACKAGE_ + "ExistenceType";
	String EXISTENCE_TYPE_ = EXISTENCE_TYPE + ".";
	String EXISTENCE_TYPE__CHECK_CACHE = EXISTENCE_TYPE_ + "CHECK_CACHE";
	String EXISTENCE_TYPE__CHECK_DATABASE = EXISTENCE_TYPE_ + "CHECK_DATABASE";
	String EXISTENCE_TYPE__ASSUME_EXISTENCE = EXISTENCE_TYPE_ + "ASSUME_EXISTENCE";
	String EXISTENCE_TYPE__ASSUME_NON_EXISTENCE = EXISTENCE_TYPE_ + "ASSUME_NON_EXISTENCE";

	
	String TIME_OF_DAY = PACKAGE_ + "TimeOfDay";
		String TIME_OF_DAY__HOUR = "hour";
		String TIME_OF_DAY__MINUTE = "minute";
		String TIME_OF_DAY__SECOND = "second";
		String TIME_OF_DAY__MILLISECOND = "millisecond";

}
