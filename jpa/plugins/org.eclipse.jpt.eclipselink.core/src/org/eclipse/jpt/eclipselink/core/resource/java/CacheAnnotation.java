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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Resource model interface that represents the 
 * org.eclipse.persistence.annotations.Cache annotation
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
public interface CacheAnnotation extends JavaResourceNode
{
	
	String ANNOTATION_NAME = EclipseLinkJPA.CACHE;
	
	/**
	 * Corresponds to the type element of the Cache annotation.
	 * Returns null if the type element does not exist in java.
	 */
	CacheType getType();
	
	/**
	 * Corresponds to the type element of the Cache annotation.
	 * Set to null to remove the type element.
	 */
	void setType(CacheType type);
		String TYPE_PROPERTY = "typeProperty";	

	/**
	 * Corresponds to the size element of the Cache annotation.
	 * Returns null if the size valuePair does not exist in the annotation
	 */
	Integer getSize();
	
	/**
	 * Corresponds to the size element of the Cache annotation.
	 * Set to null to remove the size valuePair from the annotation
	 */
	void setSize(Integer size);
		String SIZE_PROPERTY = "sizeProperty";
		
	/**
	 * Corresponds to the shared element of the Cache annotation.
	 * Returns null if the shared element does not exist in java.
	 */
	Boolean getShared();
	
	/**
	 * Corresponds to the shared element of the Cache annotation.
	 * Set to null to remove the shared element.
	 */
	void setShared(Boolean shared);
		String SHARED_PROPERTY = "sharedProperty";

	
	/**
	 * Corresponds to the expiry element of the Cache annotation.
	 * Returns null if the expiry valuePair does not exist in the annotation
	 */
	Integer getExpiry();
	
	/**
	 * Corresponds to the expiry element of the Cache annotation.
	 * Set to null to remove the expiry valuePair from the annotation
	 */
	void setExpiry(Integer expiry);
		String EXPIRY_PROPERTY = "expiryProperty";

	TimeOfDayAnnotation getExpiryTimeOfDay();
		String EXPIRY_TIME_OF_DAY_PROPERTY = "expiryTimeOfDayProperty";
		
	/**
	 * Corresponds to the alwaysRefresh element of the Cache annotation.
	 * Returns null if the alwaysRefresh element does not exist in java.
	 */
	Boolean getAlwaysRefresh();
	
	/**
	 * Corresponds to the alwaysRefresh element of the Cache annotation.
	 * Set to null to remove the alwaysRefresh element.
	 */
	void setAlwaysRefresh(Boolean alwaysRefresh);
		String ALWAYS_REFRESH_PROPERTY = "alwaysRefreshProperty";
		
	/**
	 * Corresponds to the refreshOnlyIfNewer element of the Cache annotation.
	 * Returns null if the refreshOnlyIfNewer element does not exist in java.
	 */
	Boolean getRefreshOnlyIfNewer();
	
	/**
	 * Corresponds to the refreshOnlyIfNewer element of the Cache annotation.
	 * Set to null to remove the refreshOnlyIfNewer element.
	 */
	void setRefreshOnlyIfNewer(Boolean refreshOnlyIfNewer);
		String REFRESH_ONLY_IF_NEWER_PROPERTY = "refreshOnlyIfNewerProperty";
		
	/**
	 * Corresponds to the disableHits element of the Cache annotation.
	 * Returns null if the disableHits element does not exist in java.
	 */
	Boolean getDisableHits();
	
	/**
	 * Corresponds to the disableHits element of the Cache annotation.
	 * Set to null to remove the disableHits element.
	 */
	void setDisableHits(Boolean disableHits);
		String DISABLE_HITS_PROPERTY = "disableHitsProperty";
		
	/**
	 * Corresponds to the type element of the Cache annotation.
	 * Returns null if the type element does not exist in java.
	 */
	CacheCoordinationType getCoordinationType();
	
	/**
	 * Corresponds to the type element of the Cache annotation.
	 * Set to null to remove the type element.
	 */
	void setCoordinationType(CacheCoordinationType coordinationType);
		String COORDINATION_TYPE_PROPERTY = "coordinationTypeProperty";

			
	/**
	 * Return the {@link TextRange} for the type element.  If the type element 
	 * does not exist return the {@link TextRange} for the Cache annotation.
	 */
	TextRange getTypeTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the shared element.  If the shared element 
	 * does not exist return the {@link TextRange} for the Cache annotation.
	 */
	TextRange getSizeTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the shared element.  If the shared element 
	 * does not exist return the {@link TextRange} for the Cache annotation.
	 */
	TextRange getSharedTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the expiry element.  If the expiry element 
	 * does not exist return the {@link TextRange} for the Cache annotation.
	 */
	TextRange getExpiryTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the expiryTimeOfDay element.  If the expiryTimeOfDay element 
	 * does not exist return the {@link TextRange} for the Cache annotation.
	 */
	TextRange getExpiryTimeOfDayTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the alwaysRefresh element.  If the alwaysRefresh element 
	 * does not exist return the {@link TextRange} for the Cache annotation.
	 */
	TextRange getAlwaysRefreshTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the refreshOnlyIfNewer element.  If the refreshOnlyIfNewer element 
	 * does not exist return the {@link TextRange} for the Cache annotation.
	 */
	TextRange getRefreshOnlyIfNewerTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the disableHits element.  If the disableHits element 
	 * does not exist return the {@link TextRange} for the Cache annotation.
	 */
	TextRange getDisablesHitsTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the coordinationType element.  If the coordinationType element 
	 * does not exist return the {@link TextRange} for the Cache annotation.
	 */
	TextRange getCoordinationTypeTextRange(CompilationUnit astRoot);
	
}
