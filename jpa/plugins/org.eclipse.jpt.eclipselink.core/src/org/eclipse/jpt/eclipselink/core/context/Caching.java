/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
public interface Caching extends JpaContextNode
{
	//***************** shared ************************
	
	/**
	 * This is the combination of defaultShared and specifiedShared.
	 * If getSpecifiedShared() returns null, then return isDefaultShared()
	 */
	boolean isShared();
	
	boolean isDefaultShared();
		String DEFAULT_SHARED_PROPERTY = "defaultShared"; //$NON-NLS-1$
		boolean DEFAULT_SHARED = true;
	
	Boolean getSpecifiedShared();
	
	/**
	 * Setting this to false means that cacheType, cacheSize, alwaysRefresh, 
	 * refreshOnlyIfNewer, disableHits, cacheCoordinationType will all be set 
	 * to their default states.  These settings do not apply to a cache that is not shared
	 * @param newSpecifiedShared
	 */
	void setSpecifiedShared(Boolean newSpecifiedShared);
		String SPECIFIED_SHARED_PROPERTY = "specifiedShared"; //$NON-NLS-1$
	
	
	//***************** cache type ************************
		
	/**
	 * This is the combination of defaultType and specifiedType.
	 * If getSpecifiedType() returns null, then return getDefaultType()
	 */
	EclipseLinkCacheType getType();
	
	EclipseLinkCacheType getDefaultType();		
		String DEFAULT_TYPE_PROPERTY = "defaultType"; //$NON-NLS-1$
		EclipseLinkCacheType DEFAULT_TYPE = EclipseLinkCacheType.SOFT_WEAK;
		
	EclipseLinkCacheType getSpecifiedType();	
	void setSpecifiedType(EclipseLinkCacheType newSpecifiedType);
		String SPECIFIED_TYPE_PROPERTY = "specifiedType"; //$NON-NLS-1$
	
	
	//***************** size ************************
			
	/**
	 * This is the combination of defaultSize and specifiedSize.
	 * If getSpecifiedSize() returns null, then return getDefaultSize()
	 */
	int getSize();

	int getDefaultSize();
	int DEFAULT_SIZE = 100;
		String DEFAULT_SIZE_PROPERTY = "defaultSize"; //$NON-NLS-1$

	Integer getSpecifiedSize();
	void setSpecifiedSize(Integer newSpecifiedSize);
		String SPECIFIED_SIZE_PROPERTY = "specifiedSize"; //$NON-NLS-1$
	
	//***************** always refresh ************************		
		
	/**
	 * This is the combination of defaultAlwaysRefresh and specifiedAlwaysRefresh.
	 * If getSpecifiedAlwaysRefresh() returns null, then return isDefaultAlwaysRefresh()
	 */
	boolean isAlwaysRefresh();
	
	boolean isDefaultAlwaysRefresh();
		String DEFAULT_ALWAYS_REFRESH_PROPERTY = "defaultAlwaysRefresh"; //$NON-NLS-1$
		boolean DEFAULT_ALWAYS_REFRESH = false;
	
	Boolean getSpecifiedAlwaysRefresh();
	void setSpecifiedAlwaysRefresh(Boolean newSpecifiedAlwaysRefresh);
		String SPECIFIED_ALWAYS_REFRESH_PROPERTY = "specifiedAlwaysRefresh"; //$NON-NLS-1$


	//***************** refresh only if newer ************************
		
	/**
	 * This is the combination of defaultRefreshOnlyIfNewer and specifiedRefreshOnlyIfNewer.
	 * If getSpecifiedRefreshOnlyIfNewer() returns null, then return isDefaultRefreshOnlyIfNewer()
	 */
	boolean isRefreshOnlyIfNewer();
	
	boolean isDefaultRefreshOnlyIfNewer();
		String DEFAULT_REFRESH_ONLY_IF_NEWER_PROPERTY = "defaultRefreshOnlyIfNewer"; //$NON-NLS-1$
		boolean DEFAULT_REFRESH_ONLY_IF_NEWER = false;
	
	Boolean getSpecifiedRefreshOnlyIfNewer();
	void setSpecifiedRefreshOnlyIfNewer(Boolean newSpecifiedRefreshOnlyIfNewer);
		String SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY = "specifiedRefreshOnlyIfNewer"; //$NON-NLS-1$

		
	//***************** disable hits ************************
		
	/**
	 * This is the combination of defaultDisableHits and specifiedDisableHits.
	 * If getSpecifiedDisableHits() returns null, then return getDefaultDisableHits()
	 */
	boolean isDisableHits();
	
	boolean isDefaultDisableHits();
		String DEFAULT_DISABLE_HITS_PROPERTY = "defaultDisableHits"; //$NON-NLS-1$
		boolean DEFAULT_DISABLE_HITS = false;
	
	Boolean getSpecifiedDisableHits();
	void setSpecifiedDisableHits(Boolean newSpecifiedDisableHits);
		String SPECIFIED_DISABLE_HITS_PROPERTY = "specifiedDisableHits"; //$NON-NLS-1$

		
	//***************** coordination type ************************
	
	/**
	 * This is the combination of defaultCoordinationType and specifiedCoordinationType.
	 * If getSpecifiedCoordinationType() returns null, then return getDefaultCoordinationType()
	 */
	EclipseLinkCacheCoordinationType getCoordinationType();
	
	EclipseLinkCacheCoordinationType getDefaultCoordinationType();		
		String DEFAULT_COORDINATION_TYPE_PROPERTY = "defaultCoordinationType"; //$NON-NLS-1$
		EclipseLinkCacheCoordinationType DEFAULT_COORDINATION_TYPE = EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES;
		
	EclipseLinkCacheCoordinationType getSpecifiedCoordinationType();	
	void setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType newSpecifiedCoordinationType);
		String SPECIFIED_COORDINATION_TYPE_PROPERTY = "specifiedCoordinationType"; //$NON-NLS-1$

			
	//***************** existence checking ************************
	
	/**
	 * This is the combination of defaultExistenceType and specifiedExistenceType.
	 * If getSpecifiedExistenceType() returns null, then return getDefaultExistenceType()
	 */
	EclipseLinkExistenceType getExistenceType();
	
	EclipseLinkExistenceType getDefaultExistenceType();		
		String DEFAULT_EXISTENCE_TYPE_PROPERTY = "defaultExistenceType"; //$NON-NLS-1$
		EclipseLinkExistenceType DEFAULT_EXISTENCE_TYPE = EclipseLinkExistenceType.CHECK_DATABASE;
		
	EclipseLinkExistenceType getSpecifiedExistenceType();	
	void setSpecifiedExistenceType(EclipseLinkExistenceType newSpecifiedExistenceType);
		String SPECIFIED_EXISTENCE_TYPE_PROPERTY = "specifiedExistenceType"; //$NON-NLS-1$

		
	//***************** expiry ************************

	/**
	 * corresponds to the Cache expiry element.  If this returns
	 * a non-null value then getExpiryTimeOfDay will return null.
	 * It is not valid to specify both
	 */
	Integer getExpiry();
	
	/**
	 * Setting this to a non-null value will set timeOfDayExpiry to null
	 * @param expiry
	 */
	void setExpiry(Integer expiry);
		String EXPIRY_PROPERTY = "expiry"; //$NON-NLS-1$
	
		
	/**
	 * corresponds to the Cache expiryTimeOfDay annotation or xml element.  
	 * If this returns a non-null value then getExpiry will return null.
	 * It is not valid to specify both.
	 */
	EclipseLinkExpiryTimeOfDay getExpiryTimeOfDay();
	
	/**
	 * Add Cache expiryTimeOfDay annotation or xml element, this will set 
	 * Expiry to null as it is not valid to set both expiry and timeOfDayExpiry
	 */
	EclipseLinkExpiryTimeOfDay addExpiryTimeOfDay();
	
	/**
	 * Removes the Cache expiryTimeOfDay annotation/xml element
	 */
	void removeExpiryTimeOfDay();
		String EXPIRY_TIME_OF_DAY_PROPERTY = "expiryTimeOfDay"; //$NON-NLS-1$
}
