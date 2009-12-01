/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.jpa2.resource.java;

import org.eclipse.jpt.core.jpa2.resource.java.JPA2_0;

/**
 * Corresponds to the JPA 2.0 enum
 * javax.persistence.LockModeType
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public enum LockModeType_2_0 {

	READ(JPA2_0.LOCK_MODE_TYPE__READ),
	WRITE(JPA2_0.LOCK_MODE_TYPE__WRITE),
	OPTIMISTIC(JPA2_0.LOCK_MODE_TYPE__OPTIMISTIC),
	OPTIMISTIC_FORCE_INCREMENT(JPA2_0.LOCK_MODE_TYPE__OPTIMISTIC_FORCE_INCREMENT),
	PESSIMISTIC_READ(JPA2_0.LOCK_MODE_TYPE__PESSIMISTIC_READ),
	PESSIMISTIC_WRITE(JPA2_0.LOCK_MODE_TYPE__PESSIMISTIC_WRITE),
	PESSIMISTIC_FORCE_INCREMENT(JPA2_0.LOCK_MODE_TYPE__PESSIMISTIC_FORCE_INCREMENT),
	NONE(JPA2_0.LOCK_MODE_TYPE__NONE);


	private String javaAnnotationValue;

	LockModeType_2_0(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static LockModeType_2_0 fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static LockModeType_2_0 fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (LockModeType_2_0 lockModeType : LockModeType_2_0.values()) {
			if (lockModeType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return lockModeType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(LockModeType_2_0 lockModeType) {
		return (lockModeType == null) ? null : lockModeType.getJavaAnnotationValue();
	}

}
