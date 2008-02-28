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
public enum GenerationType {
	
	TABLE,
	SEQUENCE,
	IDENTITY,
	AUTO;
	
	
	public static GenerationType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(JPA.GENERATION_TYPE__TABLE)) {
			return TABLE;
		}
		if (javaAnnotationValue.equals(JPA.GENERATION_TYPE__SEQUENCE)) {
			return SEQUENCE;
		}
		if (javaAnnotationValue.equals(JPA.GENERATION_TYPE__IDENTITY)) {
			return IDENTITY;
		}
		if (javaAnnotationValue.equals(JPA.GENERATION_TYPE__AUTO)) {
			return AUTO;
		}
		return null;
	}

	public static String toJavaAnnotationValue(GenerationType generationType) {
		if (generationType == null) {
			return null;
		}
		switch (generationType) {
			case TABLE :
				return JPA.GENERATION_TYPE__TABLE;
			case SEQUENCE :
				return JPA.GENERATION_TYPE__SEQUENCE;
			case IDENTITY :
				return JPA.GENERATION_TYPE__IDENTITY;
			case AUTO :
				return JPA.GENERATION_TYPE__AUTO;
			default :
				throw new IllegalArgumentException("unknown generation type: " + generationType);
		}
	}
}
