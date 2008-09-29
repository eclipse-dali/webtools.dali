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
public enum ExistenceType {


    CHECK_CACHE,
    CHECK_DATABASE,
    ASSUME_EXISTENCE,
    ASSUME_NON_EXISTENCE;
	
	public static ExistenceType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.EXISTENCE_TYPE__CHECK_CACHE)) {
			return CHECK_CACHE;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.EXISTENCE_TYPE__CHECK_DATABASE)) {
			return CHECK_DATABASE;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.EXISTENCE_TYPE__ASSUME_EXISTENCE)) {
			return ASSUME_EXISTENCE;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.EXISTENCE_TYPE__ASSUME_NON_EXISTENCE)) {
			return ASSUME_NON_EXISTENCE;
		}
		return null;
	}

	public static String toJavaAnnotationValue(ExistenceType existenceType) {
		if (existenceType == null) {
			return null;
		}
		switch (existenceType) {
			case CHECK_CACHE :
				return EclipseLinkJPA.EXISTENCE_TYPE__CHECK_CACHE;
			case CHECK_DATABASE :
				return EclipseLinkJPA.EXISTENCE_TYPE__CHECK_DATABASE;
			case ASSUME_EXISTENCE :
				return EclipseLinkJPA.EXISTENCE_TYPE__ASSUME_EXISTENCE;
			case ASSUME_NON_EXISTENCE :
				return EclipseLinkJPA.EXISTENCE_TYPE__ASSUME_NON_EXISTENCE;
			default :
				throw new IllegalArgumentException("unknown existence type: " + existenceType); //$NON-NLS-1$
		}
	}
}
