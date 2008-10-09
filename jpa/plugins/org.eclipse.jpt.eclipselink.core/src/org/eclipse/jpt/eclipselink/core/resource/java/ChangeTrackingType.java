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
public enum ChangeTrackingType {


	ATTRIBUTE,
	OBJECT,
	DEFERRED,
	AUTO;
	
	public static ChangeTrackingType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CHANGE_TRACKING_TYPE__ATTRIBUTE)) {
			return ATTRIBUTE;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CHANGE_TRACKING_TYPE__OBJECT)) {
			return OBJECT;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CHANGE_TRACKING_TYPE__DEFERRED)) {
			return DEFERRED;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.CHANGE_TRACKING_TYPE__AUTO)) {
			return AUTO;
		}
		return null;
	}

	public static String toJavaAnnotationValue(ChangeTrackingType changeTrackingType) {
		if (changeTrackingType == null) {
			return null;
		}
		switch (changeTrackingType) {
			case ATTRIBUTE :
				return EclipseLinkJPA.CHANGE_TRACKING_TYPE__ATTRIBUTE;
			case OBJECT :
				return EclipseLinkJPA.CHANGE_TRACKING_TYPE__OBJECT;
			case DEFERRED :
				return EclipseLinkJPA.CHANGE_TRACKING_TYPE__DEFERRED;
			case AUTO :
				return EclipseLinkJPA.CHANGE_TRACKING_TYPE__AUTO;
			default :
				throw new IllegalArgumentException("unknown change tracking type: " + changeTrackingType); //$NON-NLS-1$
		}
	}
}
