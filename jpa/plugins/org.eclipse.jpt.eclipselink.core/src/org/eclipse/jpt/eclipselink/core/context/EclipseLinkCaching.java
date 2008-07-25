/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context;

import org.eclipse.jpt.core.context.java.JavaJpaContextNode;

/**
 * 
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
public interface EclipseLinkCaching extends JavaJpaContextNode
{
	//***************** shared ************************
	
	Boolean getShared();
	
	Boolean getDefaultShared();
		String DEFAULT_SHARED_PROPERTY = "defaultSharedProperty";
		Boolean DEFAULT_SHARED = Boolean.TRUE;
	
	Boolean getSpecifiedShared();
	/**
	 * Setting this to false means that cacheType, alwaysRefresh, refreshOnlyIfNewer,
	 * disableHits will all be set to their default states.  They do not apply
	 * to a cache that is not shared
	 * @param newSpecifiedShared
	 */
	void setSpecifiedShared(Boolean newSpecifiedShared);
		String SPECIFIED_SHARED_PROPERTY = "specifiedSharedProperty";
	
	
	//***************** cache type ************************
		
	CacheType getCacheType();
	
	CacheType getDefaultCacheType();		
		String DEFAULT_CACHE_TYPE_PROPERTY = "defaultCacheTypeProperty";
		CacheType DEFAULT_CACHE_TYPE = CacheType.SOFT_WEAK;
		
	CacheType getSpecifiedCacheType();	
	void setSpecifiedCacheType(CacheType newSpecifiedCacheType);
		String SPECIFIED_CACHE_TYPE_PROPERTY = "specifiedCacheTypeProperty";
	
		
	//***************** always refresh ************************
		
	Boolean getAlwaysRefresh();
	
	Boolean getDefaultAlwaysRefresh();
		String DEFAULT_ALWAYS_REFRESH_PROPERTY = "defaultAlwaysRefreshProperty";
		Boolean DEFAULT_ALWAYS_REFRESH = Boolean.FALSE;
	
	Boolean getSpecifiedAlwaysRefresh();
	void setSpecifiedAlwaysRefresh(Boolean newSpecifiedAlwaysRefresh);
		String SPECIFIED_ALWAYS_REFRESH_PROPERTY = "specifiedAlwaysRefreshProperty";


	//***************** refresh only if newer ************************
		
	Boolean getRefreshOnlyIfNewer();
	
	Boolean getDefaultRefreshOnlyIfNewer();
		String DEFAULT_REFRESH_ONLY_IF_NEWER_PROPERTY = "defaultRefreshOnlyIfNewerProperty";
		Boolean DEFAULT_REFRESH_ONLY_IF_NEWER = Boolean.FALSE;
	
	Boolean getSpecifiedRefreshOnlyIfNewer();
	void setSpecifiedRefreshOnlyIfNewer(Boolean newSpecifiedRefreshOnlyIfNewer);
		String SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY = "specifiedRefreshOnlyIfNewerProperty";

		
	//***************** disable hits ************************
		
	Boolean getDisableHits();
	
	Boolean getDefaultDisableHits();
		String DEFAULT_DISABLE_HITS_PROPERTY = "defaultDisableHitsProperty";
		Boolean DEFAULT_DISABLE_HITS = Boolean.FALSE;
	
	Boolean getSpecifiedDisableHits();
	void setSpecifiedDisableHits(Boolean newSpecifiedDisableHits);
		String SPECIFIED_DISABLE_HITS_PROPERTY = "specifiedDisableHitsProperty";

}
