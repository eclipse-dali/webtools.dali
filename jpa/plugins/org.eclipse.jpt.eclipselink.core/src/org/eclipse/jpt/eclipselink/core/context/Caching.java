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
public interface Caching extends JpaContextNode
{
	//***************** shared ************************
	
	/**
	 * This is the combination of defaultShared and specifiedShared.
	 * If getSpecifiedShared() returns null, then return getDefaultShared()
	 */
	Boolean getShared();
	
	Boolean getDefaultShared();
		String DEFAULT_SHARED_PROPERTY = "defaultSharedProperty"; //$NON-NLS-1$
		Boolean DEFAULT_SHARED = Boolean.TRUE;
	
	Boolean getSpecifiedShared();
	
	/**
	 * Setting this to false means that cacheType, cacheSize, alwaysRefresh, 
	 * refreshOnlyIfNewer, disableHits, cacheCoordinationType will all be set 
	 * to their default states.  These settings do not apply to a cache that is not shared
	 * @param newSpecifiedShared
	 */
	void setSpecifiedShared(Boolean newSpecifiedShared);
		String SPECIFIED_SHARED_PROPERTY = "specifiedSharedProperty"; //$NON-NLS-1$
	
	
	//***************** cache type ************************
		
	/**
	 * This is the combination of defaultType and specifiedType.
	 * If getSpecifiedType() returns null, then return getDefaultType()
	 */
	CacheType getType();
	
	CacheType getDefaultType();		
		String DEFAULT_TYPE_PROPERTY = "defaultTypeProperty"; //$NON-NLS-1$
		CacheType DEFAULT_TYPE = CacheType.SOFT_WEAK;
		
	CacheType getSpecifiedType();	
	void setSpecifiedType(CacheType newSpecifiedType);
		String SPECIFIED_TYPE_PROPERTY = "specifiedTypeProperty"; //$NON-NLS-1$
	
	
	//***************** size ************************
			
	/**
	 * This is the combination of defaultSize and specifiedSize.
	 * If getSpecifiedSize() returns null, then return getDefaultSize()
	 */
	Integer getSize();

	Integer getDefaultSize();
		Integer DEFAULT_SIZE = Integer.valueOf(100);
		String DEFAULT_SIZE_PROPERTY = "defaultSizeProperty"; //$NON-NLS-1$

	Integer getSpecifiedSize();
	void setSpecifiedSize(Integer newSpecifiedSize);
		String SPECIFIED_SIZE_PROPERTY = "spcifiedSizeProperty"; //$NON-NLS-1$
	
	//***************** always refresh ************************		
		
	/**
	 * This is the combination of defaultAlwaysRefresh and specifiedAlwaysRefresh.
	 * If getSpecifiedAlwaysRefresh() returns null, then return getDefaultAlwaysRefresh()
	 */
	Boolean getAlwaysRefresh();
	
	Boolean getDefaultAlwaysRefresh();
		String DEFAULT_ALWAYS_REFRESH_PROPERTY = "defaultAlwaysRefreshProperty"; //$NON-NLS-1$
		Boolean DEFAULT_ALWAYS_REFRESH = Boolean.FALSE;
	
	Boolean getSpecifiedAlwaysRefresh();
	void setSpecifiedAlwaysRefresh(Boolean newSpecifiedAlwaysRefresh);
		String SPECIFIED_ALWAYS_REFRESH_PROPERTY = "specifiedAlwaysRefreshProperty"; //$NON-NLS-1$


	//***************** refresh only if newer ************************
		
	/**
	 * This is the combination of defaultRefreshOnlyIfNewer and specifiedRefreshOnlyIfNewer.
	 * If getSpecifiedRefreshOnlyIfNewer() returns null, then return getDefaultRefreshOnlyIfNewer()
	 */
	Boolean getRefreshOnlyIfNewer();
	
	Boolean getDefaultRefreshOnlyIfNewer();
		String DEFAULT_REFRESH_ONLY_IF_NEWER_PROPERTY = "defaultRefreshOnlyIfNewerProperty"; //$NON-NLS-1$
		Boolean DEFAULT_REFRESH_ONLY_IF_NEWER = Boolean.FALSE;
	
	Boolean getSpecifiedRefreshOnlyIfNewer();
	void setSpecifiedRefreshOnlyIfNewer(Boolean newSpecifiedRefreshOnlyIfNewer);
		String SPECIFIED_REFRESH_ONLY_IF_NEWER_PROPERTY = "specifiedRefreshOnlyIfNewerProperty"; //$NON-NLS-1$

		
	//***************** disable hits ************************
		
	/**
	 * This is the combination of defaultDisableHits and specifiedDisableHits.
	 * If getSpecifiedDisableHits() returns null, then return getDefaultDisableHits()
	 */
	Boolean getDisableHits();
	
	Boolean getDefaultDisableHits();
		String DEFAULT_DISABLE_HITS_PROPERTY = "defaultDisableHitsProperty"; //$NON-NLS-1$
		Boolean DEFAULT_DISABLE_HITS = Boolean.FALSE;
	
	Boolean getSpecifiedDisableHits();
	void setSpecifiedDisableHits(Boolean newSpecifiedDisableHits);
		String SPECIFIED_DISABLE_HITS_PROPERTY = "specifiedDisableHitsProperty"; //$NON-NLS-1$

		
	//***************** coordination type ************************
	
	/**
	 * This is the combination of defaultCoordinationType and specifiedCoordinationType.
	 * If getSpecifiedCoordinationType() returns null, then return getDefaultCoordinationType()
	 */
	CacheCoordinationType getCoordinationType();
	
	CacheCoordinationType getDefaultCoordinationType();		
		String DEFAULT_COORDINATION_TYPE_PROPERTY = "defaultCoordinationTypeProperty"; //$NON-NLS-1$
		CacheCoordinationType DEFAULT_COORDINATION_TYPE = CacheCoordinationType.SEND_OBJECT_CHANGES;
		
	CacheCoordinationType getSpecifiedCoordinationType();	
	void setSpecifiedCoordinationType(CacheCoordinationType newSpecifiedCoordinationType);
		String SPECIFIED_COORDINATION_TYPE_PROPERTY = "specifiedCoordinationTypeProperty"; //$NON-NLS-1$

			
	//***************** existence checking ************************
	
	/**
	 * Return true if the existence-checking model object exists.  
	 * Have to have a separate flag for this since the default existence
	 * type is different depending on whether hasExistenceChecking() returns
	 * true or false.
	 */
	boolean hasExistenceChecking();
	void setExistenceChecking(boolean existenceChecking);
		String EXISTENCE_CHECKING_PROPERTY = "existenceCheckingProperty"; //$NON-NLS-1$
	
	/**
	 * This is the combination of defaultExistenceType and specifiedExistenceType.
	 * If getSpecifiedExistenceType() returns null, then return getDefaultExistenceType()
	 */
	ExistenceType getExistenceType();
	
	ExistenceType getDefaultExistenceType();		
		String DEFAULT_EXISTENCE_TYPE_PROPERTY = "defaultExistenceTypeProperty"; //$NON-NLS-1$
		//default if hasExistenceChecking returns false
		ExistenceType DEFAULT_EXISTENCE_TYPE = ExistenceType.CHECK_DATABASE;
		
	ExistenceType getSpecifiedExistenceType();	
	void setSpecifiedExistenceType(ExistenceType newSpecifiedExistenceType);
		String SPECIFIED_EXISTENCE_TYPE_PROPERTY = "specifiedExistenceTypeProperty"; //$NON-NLS-1$

		
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
		String EXPIRY_PROPERTY = "expiryProperty"; //$NON-NLS-1$
	
		
	/**
	 * corresponds to the Cache expiryTimeOfDay annotation or xml element.  
	 * If this returns a non-null value then getExpiry will return null.
	 * It is not valid to specify both.
	 */
	ExpiryTimeOfDay getExpiryTimeOfDay();
	
	/**
	 * Add Cache expiryTimeOfDay annotation or xml element, this will set 
	 * Expiry to null as it is not valid to set both expiry and timeOfDayExpiry
	 */
	ExpiryTimeOfDay addExpiryTimeOfDay();
	
	/**
	 * Removes the Cache expiryTimeOfDay annotation/xml element
	 */
	void removeExpiryTimeOfDay();
		String EXPIRY_TIME_OF_DAY_PROPERTY = "expiryTimeOfDayProperty"; //$NON-NLS-1$
}
