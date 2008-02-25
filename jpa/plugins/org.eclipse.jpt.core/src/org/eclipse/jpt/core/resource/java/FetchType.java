/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;


public enum FetchType {
	
	EAGER,
	LAZY;
	
	
	public static FetchType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(JPA.FETCH_TYPE__EAGER)) {
			return EAGER;
		}
		if (javaAnnotationValue.equals(JPA.FETCH_TYPE__LAZY)) {
			return LAZY;
		}
		return null;
	}

	public static String toJavaAnnotationValue(FetchType fetchType) {
		if (fetchType == null) {
			return null;
		}
		switch (fetchType) {
			case EAGER :
				return JPA.FETCH_TYPE__EAGER;
			case LAZY :
				return JPA.FETCH_TYPE__LAZY;
			default :
				throw new IllegalArgumentException("unknown fetch type: " + fetchType);
		}
	}
}
