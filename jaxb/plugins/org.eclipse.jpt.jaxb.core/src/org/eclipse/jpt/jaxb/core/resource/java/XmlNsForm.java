/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

/**
 * Corresponds to the JAXB enum
 * javax.xml.bind.annotation.XmlNsForm
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
public enum XmlNsForm {
	
	QUALIFIED(JAXB.XML_NS_FORM__QUALIFIED),
	UNQUALIFIED(JAXB.XML_NS_FORM__UNQUALIFIED),
	UNSET(JAXB.XML_NS_FORM__UNSET);
	

	private String javaAnnotationValue;

	XmlNsForm(String javaAnnotationValue) {
		if (javaAnnotationValue == null) {
			throw new NullPointerException();
		}
		this.javaAnnotationValue = javaAnnotationValue;
	}

	public String getJavaAnnotationValue() {
		return this.javaAnnotationValue;
	}


	// ********** static methods **********

	public static XmlNsForm fromJavaAnnotationValue(Object javaAnnotationValue) {
		return (javaAnnotationValue == null) ? null : fromJavaAnnotationValue_(javaAnnotationValue);
	}

	private static XmlNsForm fromJavaAnnotationValue_(Object javaAnnotationValue) {
		for (XmlNsForm nsForm : XmlNsForm.values()) {
			if (nsForm.getJavaAnnotationValue().equals(javaAnnotationValue)) {
				return nsForm;
			}
		}
		return null;
	}

	public static String toJavaAnnotationValue(XmlNsForm nsForm) {
		return (nsForm == null) ? null : nsForm.getJavaAnnotationValue();
	}
}
