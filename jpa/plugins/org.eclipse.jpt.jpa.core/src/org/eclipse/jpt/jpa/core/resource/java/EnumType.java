/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

/**
 * Corresponds to the JPA enum
 * javax.persistence.EnumType
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
public enum EnumType {

	ORDINAL(JPA.ENUM_TYPE__ORDINAL),
	STRING(JPA.ENUM_TYPE__STRING);


	private String javaAnnotationValue;

	EnumType(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static EnumType fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static EnumType fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (EnumType enumType : EnumType.values()) {
			if (enumType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return enumType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(EnumType enumType) {
		return (enumType == null) ? null : enumType.getJavaAnnotationValue();
	}

}
