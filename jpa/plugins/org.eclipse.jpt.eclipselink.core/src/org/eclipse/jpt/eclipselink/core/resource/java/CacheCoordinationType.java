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
public enum CacheCoordinationType {
	
	SEND_OBJECT_CHANGES,
	INVALIDATE_CHANGED_OBJECTS,
	SEND_NEW_OBJECTS_WITH_CHANGES,
	NONE;
	
	public static CacheCoordinationType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_COORDINATION_TYPE__SEND_OBJECT_CHANGES)) {
			return SEND_OBJECT_CHANGES;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_COORDINATION_TYPE__INVALIDATE_CHANGED_OBJECTS)) {
			return INVALIDATE_CHANGED_OBJECTS;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_COORDINATION_TYPE__SEND_NEW_OBJECTS_WITH_CHANGES)) {
			return SEND_NEW_OBJECTS_WITH_CHANGES;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CACHE_COORDINATION_TYPE__NONE)) {
			return NONE;
		}
		return null;
	}

	public static String toJavaAnnotationValue(CacheCoordinationType cacheCoordinationType) {
		if (cacheCoordinationType == null) {
			return null;
		}
		switch (cacheCoordinationType) {
			case SEND_OBJECT_CHANGES :
				return EclipseLinkJPA.CACHE_COORDINATION_TYPE__SEND_OBJECT_CHANGES;
			case INVALIDATE_CHANGED_OBJECTS :
				return EclipseLinkJPA.CACHE_COORDINATION_TYPE__INVALIDATE_CHANGED_OBJECTS;
			case SEND_NEW_OBJECTS_WITH_CHANGES :
				return EclipseLinkJPA.CACHE_COORDINATION_TYPE__SEND_NEW_OBJECTS_WITH_CHANGES;
			case NONE :
				return EclipseLinkJPA.CACHE_COORDINATION_TYPE__NONE;
			default :
				throw new IllegalArgumentException("unknown cache coordination type: " + cacheCoordinationType); //$NON-NLS-1$
		}
	}
}
