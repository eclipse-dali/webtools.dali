/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.java;

/**
 * Corresponds to the EclipseLink enum
 * org.eclipse.persistence.annotations.ExistenceType
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
public enum ExistenceType {

    CHECK_CACHE(EclipseLink.EXISTENCE_TYPE__CHECK_CACHE),
    CHECK_DATABASE(EclipseLink.EXISTENCE_TYPE__CHECK_DATABASE),
    ASSUME_EXISTENCE(EclipseLink.EXISTENCE_TYPE__ASSUME_EXISTENCE),
    ASSUME_NON_EXISTENCE(EclipseLink.EXISTENCE_TYPE__ASSUME_NON_EXISTENCE);


	private String javaAnnotationValue;

	ExistenceType(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static ExistenceType fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static ExistenceType fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (ExistenceType existenceType : ExistenceType.values()) {
			if (existenceType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return existenceType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(ExistenceType existenceType) {
		return (existenceType == null) ? null : existenceType.getJavaAnnotationValue();
	}

}
