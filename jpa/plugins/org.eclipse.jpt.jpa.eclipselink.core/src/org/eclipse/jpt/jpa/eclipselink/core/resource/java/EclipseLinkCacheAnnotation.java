/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the EclipseLink annotation
 * <code>org.eclipse.persistence.annotations.Cache</code>
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
public interface EclipseLinkCacheAnnotation
	extends Annotation
{
	String ANNOTATION_NAME = EclipseLink.CACHE;

	/**
	 * Corresponds to the 'type' element of the Cache annotation.
	 * Return null if the element does not exist in Java.
	 */
	CacheType getType();
		String TYPE_PROPERTY = "type"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'type' element of the Cache annotation.
	 * Set to null to remove the element.
	 */
	void setType(CacheType type);

	/**
	 * Return the {@link TextRange} for the 'type' element.
	 * If the element does not exist return the {@link TextRange}
	 * for the Cache annotation.
	 */
	TextRange getTypeTextRange();
	

	/**
	 * Corresponds to the 'size' element of the Cache annotation.
	 * Return null if the element does not exist in Java.
	 */
	Integer getSize();
		String SIZE_PROPERTY = "size"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'size' element of the Cache annotation.
	 * Set to null to remove the element.
	 */
	void setSize(Integer size);
		
	/**
	 * Return the {@link TextRange} for the 'size' element.
	 * If the element does not exist return the {@link TextRange}
	 * for the Cache annotation.
	 */
	TextRange getSizeTextRange();
	

	/**
	 * Corresponds to the 'shared' element of the Cache annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getShared();
		String SHARED_PROPERTY = "shared"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'shared' element of the Cache annotation.
	 * Set to null to remove the element.
	 */
	void setShared(Boolean shared);

	/**
	 * Return the {@link TextRange} for the 'shared' element.
	 * If the element does not exist return the {@link TextRange}
	 * for the Cache annotation.
	 */
	TextRange getSharedTextRange();


	/**
	 * Corresponds to the 'expiry' element of the Cache annotation.
	 * Return null if the element does not exist in Java.
	 */
	Integer getExpiry();
		String EXPIRY_PROPERTY = "expiry"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'expiry' element of the Cache annotation.
	 * Set to null to remove the element.
	 */
	void setExpiry(Integer expiry);

	/**
	 * Return the {@link TextRange} for the 'expiry' element.
	 * If the element does not exist return the {@link TextRange}
	 * for the Cache annotation.
	 */
	TextRange getExpiryTextRange();
	

	/**
	 * Corresponds to the 'expiryTimeOfDay' element of the Cache annotation.
	 * Return null if the element does not exist in Java.
	 */
	EclipseLinkTimeOfDayAnnotation getExpiryTimeOfDay();
		String EXPIRY_TIME_OF_DAY_PROPERTY = "expiryTimeOfDay"; //$NON-NLS-1$

	EclipseLinkTimeOfDayAnnotation addExpiryTimeOfDay();

	void removeExpiryTimeOfDay();
		
	/**
	 * Return the {@link TextRange} for the 'expiryTimeOfDay' element.
	 * If the element does not exist return the {@link TextRange}
	 * for the Cache annotation.
	 */
	TextRange getExpiryTimeOfDayTextRange();
	

	/**
	 * Corresponds to the 'alwaysRefresh' element of the Cache annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getAlwaysRefresh();
		String ALWAYS_REFRESH_PROPERTY = "alwaysRefresh"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'alwaysRefresh' element of the Cache annotation.
	 * Set to null to remove the element.
	 */
	void setAlwaysRefresh(Boolean alwaysRefresh);
		
	/**
	 * Return the {@link TextRange} for the 'alwaysRefresh' element.
	 * If the element does not exist return the {@link TextRange}
	 * for the Cache annotation.
	 */
	TextRange getAlwaysRefreshTextRange();
	

	/**
	 * Corresponds to the 'refreshOnlyIfNewer' element of the Cache annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getRefreshOnlyIfNewer();
		String REFRESH_ONLY_IF_NEWER_PROPERTY = "refreshOnlyIfNewer"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'refreshOnlyIfNewer' element of the Cache annotation.
	 * Set to null to remove the element.
	 */
	void setRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer);
		
	/**
	 * Return the {@link TextRange} for the 'refreshOnlyIfNewer' element.
	 * If the element does not exist return the {@link TextRange}
	 * for the Cache annotation.
	 */
	TextRange getRefreshOnlyIfNewerTextRange();
	

	/**
	 * Corresponds to the 'disableHits' element of the Cache annotation.
	 * Return null if the element does not exist in Java.
	 */
	Boolean getDisableHits();
		String DISABLE_HITS_PROPERTY = "disableHits"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'disableHits' element of the Cache annotation.
	 * Set to null to remove the element.
	 */
	void setDisableHits(Boolean disableHits);
		
	/**
	 * Return the {@link TextRange} for the 'disableHits' element.
	 * If the element does not exist return the {@link TextRange}
	 * for the Cache annotation.
	 */
	TextRange getDisableHitsTextRange();
	

	/**
	 * Corresponds to the 'coordinationType' element of the Cache annotation.
	 * Return null if the element does not exist in Java.
	 */
	CacheCoordinationType getCoordinationType();
		String COORDINATION_TYPE_PROPERTY = "coordinationType"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'coordinationType' element of the Cache annotation.
	 * Set to null to remove the element.
	 */
	void setCoordinationType(CacheCoordinationType coordinationType);

	/**
	 * Return the {@link TextRange} for the 'coordinationType' element.
	 * If the element does not exist return the {@link TextRange}
	 * for the Cache annotation.
	 */
	TextRange getCoordinationTypeTextRange();


	/**
	 * Corresponds to the 'isolation' element of the Cache annotation.
	 * Return null if the element does not exist in Java.
	 */
	CacheIsolationType2_2 getIsolation();
		String ISOLATION_PROPERTY = "isolation"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'isolation' element of the Cache annotation.
	 * Set to null to remove the element.
	 */
	void setIsolation(CacheIsolationType2_2 isolation);

	/**
	 * Return the {@link TextRange} for the 'isolation' element.
	 * If the element does not exist return the {@link TextRange}
	 * for the Cache annotation.
	 */
	TextRange getIsolationTextRange();
}
