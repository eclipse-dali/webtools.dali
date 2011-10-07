/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

/**
 * Corresponds to the EclipseLink enum 
 * org.eclipse.persistence.config.CacheIsolationType added in EclipseLink 2.2
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
public enum CacheIsolationType2_2 {

	SHARED(EclipseLink.CACHE_ISOLATION_TYPE__SHARED),
	PROTECTED(EclipseLink.CACHE_ISOLATION_TYPE__PROTECTED),
	ISOLATED(EclipseLink.CACHE_ISOLATION_TYPE__ISOLATED);


	private String javaAnnotationValue;

	CacheIsolationType2_2(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static CacheIsolationType2_2 fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static CacheIsolationType2_2 fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (CacheIsolationType2_2 cacheCoordinationType : CacheIsolationType2_2.values()) {
			if (cacheCoordinationType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return cacheCoordinationType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(CacheIsolationType2_2 cacheCoordinationType) {
		return (cacheCoordinationType == null) ? null : cacheCoordinationType.getJavaAnnotationValue();
	}

}
