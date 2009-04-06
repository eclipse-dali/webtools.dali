/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.java;

/**
 * Corresponds to the EclipseLink enum
 * org.eclipse.persistence.annotations.JoinFetchType
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

    INNER(EclipseLinkJPA.JOIN_FETCH_TYPE__INNER),
    OUTER(EclipseLinkJPA.JOIN_FETCH_TYPE__OUTER);


	private String javaAnnotationValue;

	JoinFetchType(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static JoinFetchType fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static JoinFetchType fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (JoinFetchType joinFetchType : JoinFetchType.values()) {
			if (joinFetchType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return joinFetchType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(JoinFetchType joinFetchType) {
		return (joinFetchType == null) ? null : joinFetchType.getJavaAnnotationValue();
	}

}
