/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.resource.java;


/**
 * Corresponds to the JPA 2.1 enum
 * javax.persistence.ParameterMode
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public enum ParameterMode2_1
{

	IN(JPA2_1.PARAMETER_MODE__IN),
	INOUT(JPA2_1.PARAMETER_MODE__INOUT),
	OUT(JPA2_1.PARAMETER_MODE__OUT),
	REF_CURSOR(JPA2_1.PARAMETER_MODE__REF_CURSOR);


	private String javaAnnotationValue;

	ParameterMode2_1(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static ParameterMode2_1 fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static ParameterMode2_1 fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (ParameterMode2_1 parameterMode : ParameterMode2_1.values()) {
			if (parameterMode.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return parameterMode;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(ParameterMode2_1 parameterMode) {
		return (parameterMode == null) ? null : parameterMode.getJavaAnnotationValue();
	}
}
