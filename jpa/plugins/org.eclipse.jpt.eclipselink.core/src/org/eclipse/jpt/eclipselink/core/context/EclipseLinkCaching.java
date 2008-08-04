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

import org.eclipse.jpt.core.context.JpaContextNode;

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
public interface EclipseLinkCaching extends JpaContextNode
{
	//***************** shared ************************
	
	Boolean getShared();
	
	Boolean getDefaultShared();
		String DEFAULT_SHARED_PROPERTY = "defaultSharedProperty";
		Boolean DEFAULT_SHARED = Boolean.TRUE;
	
	Boolean getSpecifiedShared();
	
	/**
	 * Setting this to false means that cacheType, cacheSize, alwaysRefresh, 
	 * refreshOnlyIfNewer, disableHits, cacheCoordinationType will all be set 
	 * to their default states.  These settings do not apply to a cache that is not shared
	 * @param newSpecifiedShared
	 */
	void setSpecifiedShared(Boolean newSpecifiedShared);
		String SPECIFIED_SHARED_PROPERTY = "specifiedSharedProperty";
	
	
	//***************** cache type ************************
		
	CacheType getType();
	
	CacheType getDefaultType();		
		String DEFAULT_TYPE_PROPERTY = "defaultTypeProperty";
		CacheType DEFAULT_TYPE = CacheType.SOFT_WEAK;
		
	CacheType getSpecifiedType();	
	void setSpecifiedType(CacheType newSpecifiedType);
		String SPECIFIED_TYPE_PROPERTY = "specifiedTypeProperty";
	
	
	//***************** size ************************
	
	Integer getSize();

	Integer getDefaultSize();
	Integer DEFAULT_SIZE = Integer.valueOf(100);
		String DEFAULT_SIZE_PROPERTY = "defaultSizeProperty";

	Integer getSpecifiedSize();
	void setSpecifiedSize(Integer newSpecifiedSize);
		String SPECIFIED_SIZE_PROPERTY = "spcifiedSizeProperty";
	
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

		
	//***************** coordination type ************************
	
	CacheCoordinationType getCoordinationType();
	
	CacheCoordinationType getDefaultCoordinationType();		
		String DEFAULT_COORDINATION_TYPE_PROPERTY = "defaultCoordinationTypeProperty";
		CacheCoordinationType DEFAULT_COORDINATION_TYPE = CacheCoordinationType.SEND_OBJECT_CHANGES;
		
	CacheCoordinationType getSpecifiedCoordinationType();	
	void setSpecifiedCoordinationType(CacheCoordinationType newSpecifiedCoordinationType);
		String SPECIFIED_COORDINATION_TYPE_PROPERTY = "specifiedCoordinationTypeProperty";

			
	//***************** existence checking ************************
	
		
	boolean hasExistenceChecking();
	void setExistenceChecking(boolean existenceChecking);
		String EXISTENCE_CHECKING_PROPERTY = "existenceCheckingProperty";
	
	ExistenceType getExistenceType();
	
	ExistenceType getDefaultExistenceType();		
		String DEFAULT_EXISTENCE_TYPE_PROPERTY = "defaultExistenceTypeProperty";
		//default if hasExistenceChecking returns false
		ExistenceType DEFAULT_EXISTENCE_TYPE = ExistenceType.CHECK_DATABASE;
		
	ExistenceType getSpecifiedExistenceType();	
	void setSpecifiedExistenceType(ExistenceType newSpecifiedExistenceType);
		String SPECIFIED_EXISTENCE_TYPE_PROPERTY = "specifiedExistenceTypeProperty";

}
