/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;


public enum TemporalType {
	
	DATE,
	TIME,
	TIMESTAMP;
	
	
	public static TemporalType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(JPA.TEMPORAL_TYPE__DATE)) {
			return DATE;
		}
		if (javaAnnotationValue.equals(JPA.TEMPORAL_TYPE__TIME)) {
			return TIME;
		}
		if (javaAnnotationValue.equals(JPA.TEMPORAL_TYPE__TIMESTAMP)) {
			return TIMESTAMP;
		}
		return null;
	}

	public static String toJavaAnnotationValue(TemporalType temporalType) {
		if (temporalType == null) {
			return null;
		}
		switch (temporalType) {
			case DATE :
				return JPA.TEMPORAL_TYPE__DATE;
			case TIME :
				return JPA.TEMPORAL_TYPE__TIME;
			case TIMESTAMP :
				return JPA.TEMPORAL_TYPE__TIMESTAMP;
			default :
				throw new IllegalArgumentException("unknown temporal type: " + temporalType);
		}
	}
}
