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

public class BooleanUtility
{
	public static Boolean fromJavaAnnotationValue(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			return null;
		}
		return Boolean.parseBoolean(javaAnnotationValue);
	}

	public static String toJavaAnnotationValue(Boolean value) {
		if (value == null) {
			return null;
		}
		return Boolean.toString(value);
	}
}
