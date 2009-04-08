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
 * javax.persistence.CascadeType
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public enum CascadeType {

	ALL(JPA.CASCADE_TYPE__ALL),
	PERSIST(JPA.CASCADE_TYPE__PERSIST),
	MERGE(JPA.CASCADE_TYPE__MERGE),
	REMOVE(JPA.CASCADE_TYPE__REMOVE),
	REFRESH(JPA.CASCADE_TYPE__REFRESH);


	private String javaAnnotationValue;

	CascadeType(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static CascadeType[] fromJavaAnnotationValues(Object[] javaAnnotationValues) {
		if (javaAnnotationValues == null) {
			return EMPTY_CASCADE_TYPE_ARRAY;
		}
		int len = javaAnnotationValues.length;
		if (len == 0) {
			return EMPTY_CASCADE_TYPE_ARRAY;
		}

		CascadeType[] cascadeTypes = new CascadeType[len];
		for (int i = 0; i < len; i++) {
			cascadeTypes[i] = fromJavaAnnotationValue(javaAnnotationValues[i]);
		}
		return cascadeTypes;
	}
	private static final CascadeType[] EMPTY_CASCADE_TYPE_ARRAY = new CascadeType[0];

	public static CascadeType fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static CascadeType fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (CascadeType cascadeType : CascadeType.values()) {
			if (cascadeType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return cascadeType;
			}
		}
		return null;
	}

	public static String[] toJavaAnnotationValues(CascadeType[] cascadeTypes) {
		if (cascadeTypes == null) {
			return EMPTY_STRING_ARRAY;
		}
		int len = cascadeTypes.length;
		if (len == 0) {
			return EMPTY_STRING_ARRAY;
		}

		String[] javaAnnotationValues = new String[len];
		for (int i = 0; i < len; i++) {
			javaAnnotationValues[i] = toJavaAnnotationValue(cascadeTypes[i]);
		}
		return javaAnnotationValues;
	}
	private static final String[] EMPTY_STRING_ARRAY = new String[0];

	public static String toJavaAnnotationValue(CascadeType cascadeType) {
		return (cascadeType == null) ? null : cascadeType.getJavaAnnotationValue();
	}

}
