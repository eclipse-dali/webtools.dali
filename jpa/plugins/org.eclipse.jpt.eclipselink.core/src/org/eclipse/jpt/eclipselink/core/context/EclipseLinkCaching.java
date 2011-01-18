/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
 * EclipseLink caching
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
// TODO bjv EclipseLinkCachingPolicy
public interface EclipseLinkCaching
	extends JpaContextNode
{
	// ********** type **********

	/**
	 * This is the combination of defaultType and specifiedType.
	 * If getSpecifiedType() returns null, then return getDefaultType()
	 */
	EclipseLinkCacheType getType();
	
	EclipseLinkCacheType getSpecifiedType();	
	void setSpecifiedType(EclipseLinkCacheType type);
		String SPECIFIED_TYPE_PROPERTY = "specifiedType"; //$NON-NLS-1$
	
	EclipseLinkCacheType getDefaultType();		
		String DEFAULT_TYPE_PROPERTY = "defaultType"; //$NON-NLS-1$
		EclipseLinkCacheType DEFAULT_TYPE = EclipseLinkCacheType.SOFT_WEAK;
		
	
	// ********** size **********
			
	/**
	 * This is the combination of defaultSize and specifiedSize.
	 * If getSpecifiedSize() returns null, then return getDefaultSize()
	 */
	int getSize();

	Integer getSpecifiedSize();
	void setSpecifiedSize(Integer size);
		String SPECIFIED_SIZE_PROPERTY = "specifiedSize"; //$NON-NLS-1$
	
	int getDefaultSize();
		String DEFAULT_SIZE_PROPERTY = "defaultSize"; //$NON-NLS-1$
		int DEFAULT_SIZE = 100;


	// ********** shared **********
	
	/**
	 * This is the combination of defaultShared and specifiedShared.
	 * If getSpecifiedShared() returns null, then return isDefaultShared()
	 */
	boolean isShared();
	
	Boolean getSpecifiedShared();
	
	/**
	 * Specifying <em>shared</em> <code>false</code> will return the following
	 * caching settings to their defaults:<ul>
	 * <li>type
	 * <li>size
	 * <li>always refresh
	 * <li>refresh only if newer
	 * <li>disable hits
	 * <li>coordination type
	 * </ul>
	 * Additionally, the following settings will be cleared:<ul>
	 * <li>expiry
	 * <li>expiry time of day
	 * </ul>
	 * These settings do not apply to an unchared cache.
	 */
	void setSpecifiedShared(Boolean shared);
		String SPECIFIED_SHARED_PROPERTY = "specifiedShared"; //$NON-NLS-1$
	
	boolean isDefaultShared();
		String DEFAULT_SHARED_PROPERTY = "defaultShared"; //$NON-NLS-1$
		boolean DEFAULT_SHARED = true;
	
	
	// ********** always refresh **********
		
	/**
	 * This is the combination of defaultAlwaysRefresh and specifiedAlwaysRefresh.
	 * If getSpecifiedAlwaysRefresh() returns null, then return isDefaultAlwaysRefresh()
	 */
	boolean isAlwaysRefresh();
	
	Boolean getSpecifiedAlwaysRefresh();
	void setSpecifiedAlwaysRefresh(Boolean alwaysRefresh);
		String SPECIFIED_ALWAYS_REFRESH_PROPERTY = "specifiedAlwaysRefresh"; //$NON-NLS-1$

	boolean isDefaultAlwaysRefresh();
		String DEFAULT_ALWAYS_REFRESH_PROPERTY = "defaultAlwaysRefresh"; //$NON-NLS-1$
		boolean DEFAULT_ALWAYS_REFRESH = false;
	

	// ********** refresh only if newer **********
		
	/**
	 * This is the combination of defaultRefreshOnlyIfNewer and specifiedRefreshOnlyIfNewer.
	 * If getSpecifiedRefreshOnlyIfNewer() returns null, then return isDefaultRefreshOnlyIfNewer()
	 */
	boolean isRefreshOnlyIfNewer();
	
	Boolean getSpecifiedRefreshOnlyIfNewer();
	void setSpecifiedRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer);
		String SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY = "specifiedRefreshOnlyIfNewer"; //$NON-NLS-1$

	boolean isDefaultRefreshOnlyIfNewer();
		String DEFAULT_REFRESH_ONLY_IF_NEWER_PROPERTY = "defaultRefreshOnlyIfNewer"; //$NON-NLS-1$
		boolean DEFAULT_REFRESH_ONLY_IF_NEWER = false;
	
		
	// ********** disable hits **********
		
	/**
	 * This is the combination of defaultDisableHits and specifiedDisableHits.
	 * If getSpecifiedDisableHits() returns null, then return getDefaultDisableHits()
	 */
	boolean isDisableHits();
	
	Boolean getSpecifiedDisableHits();
	void setSpecifiedDisableHits(Boolean disableHits);
		String SPECIFIED_DISABLE_HITS_PROPERTY = "specifiedDisableHits"; //$NON-NLS-1$

	boolean isDefaultDisableHits();
		String DEFAULT_DISABLE_HITS_PROPERTY = "defaultDisableHits"; //$NON-NLS-1$
		boolean DEFAULT_DISABLE_HITS = false;
	
		
	// ********** coordination type **********
	
	/**
	 * This is the combination of defaultCoordinationType and specifiedCoordinationType.
	 * If getSpecifiedCoordinationType() returns null, then return getDefaultCoordinationType()
	 */
	EclipseLinkCacheCoordinationType getCoordinationType();
	
	EclipseLinkCacheCoordinationType getSpecifiedCoordinationType();	
	void setSpecifiedCoordinationType(EclipseLinkCacheCoordinationType coordinationType);
		String SPECIFIED_COORDINATION_TYPE_PROPERTY = "specifiedCoordinationType"; //$NON-NLS-1$

	EclipseLinkCacheCoordinationType getDefaultCoordinationType();		
		String DEFAULT_COORDINATION_TYPE_PROPERTY = "defaultCoordinationType"; //$NON-NLS-1$
		EclipseLinkCacheCoordinationType DEFAULT_COORDINATION_TYPE = EclipseLinkCacheCoordinationType.SEND_OBJECT_CHANGES;

		
	// ********** expiry **********

	/**
	 * corresponds to the Cache expiry element.  If this returns
	 * a non-null value then getExpiryTimeOfDay will return null.
	 * It is not valid to specify both
	 */
	Integer getExpiry();
	
	/**
	 * Setting this to a non-null value will set timeOfDayExpiry to null
	 */
	void setExpiry(Integer expiry);
		String EXPIRY_PROPERTY = "expiry"; //$NON-NLS-1$
	
		
	// ********** expiry time of day **********

	/**
	 * corresponds to the Cache expiryTimeOfDay annotation or xml element.  
	 * If this returns a non-null value then getExpiry will return null.
	 * It is not valid to specify both.
	 */
	EclipseLinkTimeOfDay getExpiryTimeOfDay();
		String EXPIRY_TIME_OF_DAY_PROPERTY = "expiryTimeOfDay"; //$NON-NLS-1$
	
	/**
	 * Add Cache expiryTimeOfDay annotation or xml element, this will set 
	 * Expiry to null as it is not valid to set both expiry and timeOfDayExpiry
	 */
	EclipseLinkTimeOfDay addExpiryTimeOfDay();
	
	/**
	 * Removes the Cache expiryTimeOfDay annotation/xml element
	 */
	void removeExpiryTimeOfDay();
	

	// ********** existence type **********
	// TODO bjv rename existenceCheckingPolicy
	/**
	 * This is the combination of defaultExistenceType and specifiedExistenceType.
	 * If getSpecifiedExistenceType() returns null, then return getDefaultExistenceType()
	 */
	EclipseLinkExistenceType getExistenceType();
	
	EclipseLinkExistenceType getSpecifiedExistenceType();	
	void setSpecifiedExistenceType(EclipseLinkExistenceType type);
		String SPECIFIED_EXISTENCE_TYPE_PROPERTY = "specifiedExistenceType"; //$NON-NLS-1$

	EclipseLinkExistenceType getDefaultExistenceType();		
		String DEFAULT_EXISTENCE_TYPE_PROPERTY = "defaultExistenceType"; //$NON-NLS-1$
		EclipseLinkExistenceType DEFAULT_EXISTENCE_TYPE = EclipseLinkExistenceType.CHECK_DATABASE;
}
