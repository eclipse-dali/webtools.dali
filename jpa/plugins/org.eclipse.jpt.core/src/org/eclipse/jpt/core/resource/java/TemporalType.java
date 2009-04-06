/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
 * javax.persistence.TemporalType
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public enum TemporalType {

	DATE(JPA.TEMPORAL_TYPE__DATE),
	TIME(JPA.TEMPORAL_TYPE__TIME),
	TIMESTAMP(JPA.TEMPORAL_TYPE__TIMESTAMP);


	private String javaAnnotationValue;

	TemporalType(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static TemporalType fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static TemporalType fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (TemporalType temporalType : TemporalType.values()) {
			if (temporalType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return temporalType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(TemporalType temporalType) {
		return (temporalType == null) ? null : temporalType.getJavaAnnotationValue();
	}

}
