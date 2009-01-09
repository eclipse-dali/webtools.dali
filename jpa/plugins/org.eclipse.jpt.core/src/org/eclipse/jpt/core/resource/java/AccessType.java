/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
public enum AccessType {

	PROPERTY,
	FIELD;
	
	public static AccessType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(JPA.ACCESS_TYPE__FIELD)) {
			return FIELD;
		}
		if (javaAnnotationValue.equals(JPA.ACCESS_TYPE__PROPERTY)) {
			return PROPERTY;
		}
		return null;
	}

	public static String toJavaAnnotationValue(AccessType accessType) {
		if (accessType == null) {
			return null;
		}
		switch (accessType) {
			case FIELD :
				return JPA.ACCESS_TYPE__FIELD;
			case PROPERTY :
				return JPA.ACCESS_TYPE__PROPERTY;
			default :
				throw new IllegalArgumentException("unknown access type: " + accessType); //$NON-NLS-1$
		}
	}
}
