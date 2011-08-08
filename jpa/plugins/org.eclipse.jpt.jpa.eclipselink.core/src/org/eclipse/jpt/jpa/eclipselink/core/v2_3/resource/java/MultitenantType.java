/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.v2_3.resource.java;

/**
 * Corresponds to the EclipseLink JPA enum
 * org.eclipse.persistence.annotations.MultitenantType
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
public enum MultitenantType {

	SINGLE_TABLE(EclipseLink2_3.MULTITENANT_TYPE__SINGLE_TABLE),
	TABLE_PER_TENANT(EclipseLink2_3.MULTITENANT_TYPE__TABLE_PER_TENANT);


	private String javaAnnotationValue;

	MultitenantType(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static MultitenantType fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static MultitenantType fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (MultitenantType fetchType : MultitenantType.values()) {
			if (fetchType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return fetchType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(MultitenantType fetchType) {
		return (fetchType == null) ? null : fetchType.getJavaAnnotationValue();
	}

}
