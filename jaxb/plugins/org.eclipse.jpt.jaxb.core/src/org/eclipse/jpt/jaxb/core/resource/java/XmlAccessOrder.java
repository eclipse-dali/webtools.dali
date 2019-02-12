/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

/**
 * Corresponds to the JAXB enum
 * javax.xml.bind.annotation.XmlAccessOrder
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
public enum XmlAccessOrder {

	ALPHABETICAL(JAXB.XML_ACCESS_ORDER__ALPHABETICAL),
	UNDEFINED (JAXB.XML_ACCESS_ORDER__UNDEFINED);


	private String javaAnnotationValue;

	XmlAccessOrder(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static XmlAccessOrder fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static XmlAccessOrder fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (XmlAccessOrder accessType : XmlAccessOrder.values()) {
			if (accessType.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return accessType;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(XmlAccessOrder accessType) {
		return (accessType == null) ? null : accessType.getJavaAnnotationValue();
	}

}
