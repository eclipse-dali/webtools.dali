/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

/**
 * Corresponds to the JPA enum
 * javax.persistence.FetchType
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public enum FetchType {

	EAGER(JPA.FETCH_TYPE__EAGER),
	LAZY(JPA.FETCH_TYPE__LAZY);


	private String javaAnnotationValue;

	FetchType(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static FetchType fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static FetchType fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (FetchType fetchType : FetchType.values()) {
			if (fetchType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return fetchType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(FetchType fetchType) {
		return (fetchType == null) ? null : fetchType.getJavaAnnotationValue();
	}

}
