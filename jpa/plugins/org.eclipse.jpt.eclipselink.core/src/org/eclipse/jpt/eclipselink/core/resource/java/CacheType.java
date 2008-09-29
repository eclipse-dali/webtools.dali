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
public enum CacheType {
	
	FULL,
	WEAK,
	SOFT,
	SOFT_WEAK,
	HARD_WEAK,
	CACHE,
	NONE;
	
	
	public static CacheType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_TYPE__FULL)) {
			return FULL;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_TYPE__WEAK)) {
			return WEAK;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_TYPE__SOFT)) {
			return SOFT;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_TYPE__SOFT_WEAK)) {
			return SOFT_WEAK;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_TYPE__HARD_WEAK)) {
			return HARD_WEAK;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_TYPE__CACHE)) {
			return CACHE;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_TYPE__NONE)) {
			return NONE;
		}
		return null;
	}

	public static String toJavaAnnotationValue(CacheType cacheType) {
		if (cacheType == null) {
			return null;
		}
		switch (cacheType) {
			case FULL :
				return EclipseLinkJPA.CACHE_TYPE__FULL;
			case WEAK :
				return EclipseLinkJPA.CACHE_TYPE__WEAK;
			case SOFT :
				return EclipseLinkJPA.CACHE_TYPE__SOFT;
			case SOFT_WEAK :
				return EclipseLinkJPA.CACHE_TYPE__SOFT_WEAK;
			case HARD_WEAK :
				return EclipseLinkJPA.CACHE_TYPE__HARD_WEAK;
			case CACHE :
				return EclipseLinkJPA.CACHE_TYPE__CACHE;
			case NONE :
				return EclipseLinkJPA.CACHE_TYPE__NONE;
			default :
				throw new IllegalArgumentException("unknown cache type: " + cacheType); //$NON-NLS-1$
		}
	}
}
