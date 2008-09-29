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
 * Resource model interface that represents the 
 * org.eclipse.persistence.annotations.JoinFetchType enum
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
public enum JoinFetchType {


    INNER,
    OUTER;
	
	public static JoinFetchType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.JOIN_FETCH_TYPE__INNER)) {
			return INNER;
		}
		if (javaAnnotationValue.equals(EclipseLinkJPA.JOIN_FETCH_TYPE__OUTER)) {
			return OUTER;
		}
		return null;
	}

	public static String toJavaAnnotationValue(JoinFetchType joinFetchType) {
		if (joinFetchType == null) {
			return null;
		}
		switch (joinFetchType) {
			case INNER :
				return EclipseLinkJPA.JOIN_FETCH_TYPE__INNER;
			case OUTER :
				return EclipseLinkJPA.JOIN_FETCH_TYPE__OUTER;
			default :
				throw new IllegalArgumentException("unknown join fetch type: " + joinFetchType); //$NON-NLS-1$
		}
	}
}
