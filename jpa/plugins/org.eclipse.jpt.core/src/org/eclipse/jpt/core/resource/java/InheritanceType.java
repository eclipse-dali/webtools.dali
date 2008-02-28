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
public enum InheritanceType {
	
	SINGLE_TABLE,
	JOINED,
	TABLE_PER_CLASS;
	
	
	public static InheritanceType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(JPA.INHERITANCE_TYPE__SINGLE_TABLE)) {
			return SINGLE_TABLE;
		}
		if (javaAnnotationValue.equals(JPA.INHERITANCE_TYPE__JOINED)) {
			return JOINED;
		}
		if (javaAnnotationValue.equals(JPA.INHERITANCE_TYPE__TABLE_PER_CLASS)) {
			return TABLE_PER_CLASS;
		}
		return null;
	}

	public static String toJavaAnnotationValue(InheritanceType inheritanceType) {
		if (inheritanceType == null) {
			return null;
		}
		switch (inheritanceType) {
			case SINGLE_TABLE :
				return JPA.INHERITANCE_TYPE__SINGLE_TABLE;
			case JOINED :
				return JPA.INHERITANCE_TYPE__JOINED;
			case TABLE_PER_CLASS :
				return JPA.INHERITANCE_TYPE__TABLE_PER_CLASS;
			default :
				throw new IllegalArgumentException("unknown inheritance type: " + inheritanceType);
		}
	}
}
