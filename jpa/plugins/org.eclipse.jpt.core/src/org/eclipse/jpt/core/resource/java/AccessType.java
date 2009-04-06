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
 * Corresponds to the JPA 2.0 enum
 * javax.persistence.AccessType
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public enum AccessType {

	FIELD(JPA.ACCESS_TYPE__FIELD),
	PROPERTY(JPA.ACCESS_TYPE__PROPERTY);


	private String javaAnnotationValue;

	AccessType(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static AccessType fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static AccessType fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (AccessType accessType : AccessType.values()) {
			if (accessType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return accessType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(AccessType accessType) {
		return (accessType == null) ? null : accessType.getJavaAnnotationValue();
	}

}
