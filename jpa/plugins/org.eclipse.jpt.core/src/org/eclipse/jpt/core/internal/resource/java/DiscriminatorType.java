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


public enum DiscriminatorType {
	
	STRING,
	CHAR,
	INTEGER;
	
	
	public static DiscriminatorType fromJavaAnnotationValue(Object javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		if (javaAnnotationValue.equals(JPA.DISCRIMINATOR_TYPE__STRING)) {
			return STRING;
		}
		if (javaAnnotationValue.equals(JPA.DISCRIMINATOR_TYPE__CHAR)) {
			return CHAR;
		}
		if (javaAnnotationValue.equals(JPA.DISCRIMINATOR_TYPE__INTEGER)) {
			return INTEGER;
		}
		return null;
	}

	public static String toJavaAnnotationValue(DiscriminatorType discriminatorType) {
		if (discriminatorType == null) {
			return null;
		}
		switch (discriminatorType) {
			case STRING :
				return JPA.DISCRIMINATOR_TYPE__STRING;
			case CHAR :
				return JPA.DISCRIMINATOR_TYPE__CHAR;
			case INTEGER :
				return JPA.DISCRIMINATOR_TYPE__INTEGER;
			default :
				throw new IllegalArgumentException("unknown discriminator type: " + discriminatorType);
		}
	}
}
