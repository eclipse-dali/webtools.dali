/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public enum EnumType {
	
	ORDINAL,
	STRING;
	
	
	public static EnumType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(JPA.ENUM_TYPE__ORDINAL)) {
			return ORDINAL;
		}
		if (javaAnnotationValue.equals(JPA.ENUM_TYPE__STRING)) {
			return STRING;
		}
		return null;
	}

	public static String toJavaAnnotationValue(EnumType enumType) {
		if (enumType == null) {
			return null;
		}
		switch (enumType) {
			case ORDINAL :
				return JPA.ENUM_TYPE__ORDINAL;
			case STRING :
				return JPA.ENUM_TYPE__STRING;
			default :
				throw new IllegalArgumentException("unknown enum type: " + enumType);
		}
	}
}
