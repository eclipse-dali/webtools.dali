/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

/**
 * Corresponds to the EclipseLink enum
 * org.eclipse.persistence.annotations.CacheType
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

	FULL(EclipseLink.CACHE_TYPE__FULL),
	WEAK(EclipseLink.CACHE_TYPE__WEAK),
	SOFT(EclipseLink.CACHE_TYPE__SOFT),
	SOFT_WEAK(EclipseLink.CACHE_TYPE__SOFT_WEAK),
	HARD_WEAK(EclipseLink.CACHE_TYPE__HARD_WEAK),
	CACHE(EclipseLink.CACHE_TYPE__CACHE),
	NONE(EclipseLink.CACHE_TYPE__NONE);


	private String javaAnnotationValue;

	CacheType(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static CacheType fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static CacheType fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (CacheType cacheType : CacheType.values()) {
			if (cacheType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return cacheType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(CacheType cacheType) {
		return (cacheType == null) ? null : cacheType.getJavaAnnotationValue();
	}

}
