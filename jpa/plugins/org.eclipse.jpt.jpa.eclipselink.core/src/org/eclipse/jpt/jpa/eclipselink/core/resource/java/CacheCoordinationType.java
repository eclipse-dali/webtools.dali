/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
 * org.eclipse.persistence.annotations.CacheCoordinationType
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
public enum CacheCoordinationType {

	SEND_OBJECT_CHANGES(EclipseLink.CACHE_COORDINATION_TYPE__SEND_OBJECT_CHANGES),
	INVALIDATE_CHANGED_OBJECTS(EclipseLink.CACHE_COORDINATION_TYPE__INVALIDATE_CHANGED_OBJECTS),
	SEND_NEW_OBJECTS_WITH_CHANGES(EclipseLink.CACHE_COORDINATION_TYPE__SEND_NEW_OBJECTS_WITH_CHANGES),
	NONE(EclipseLink.CACHE_COORDINATION_TYPE__NONE);


	private String javaAnnotationValue;

	CacheCoordinationType(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static CacheCoordinationType fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static CacheCoordinationType fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (CacheCoordinationType cacheCoordinationType : CacheCoordinationType.values()) {
			if (cacheCoordinationType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return cacheCoordinationType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(CacheCoordinationType cacheCoordinationType) {
		return (cacheCoordinationType == null) ? null : cacheCoordinationType.getJavaAnnotationValue();
	}

}
