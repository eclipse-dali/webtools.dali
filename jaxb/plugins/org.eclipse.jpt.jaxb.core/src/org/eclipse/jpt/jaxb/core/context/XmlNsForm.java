/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

/**
 * 
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

	QUALIFIED(
		org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.QUALIFIED
		),
	UNQUALIFIED(
		org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.UNQUALIFIED
		),
	UNSET(
		org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm.UNSET
		);


	private org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm javaNsForm;

	XmlNsForm(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm javaNsForm) {
		if (javaNsForm == null) {
			throw new NullPointerException();
		}
		this.javaNsForm = javaNsForm;
	}

	public org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm getJavaNsForm() {
		return this.javaNsForm;
	}


	// ********** static methods **********

	public static XmlNsForm fromJavaResourceModel(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm javaNsForm) {
		return (javaNsForm == null) ? null : fromJavaResourceModel_(javaNsForm);
	}

	private static XmlNsForm fromJavaResourceModel_(org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm javaNsForm) {
		for (XmlNsForm nsForm : XmlNsForm.values()) {
			if (nsForm.getJavaNsForm() == javaNsForm) {
				return nsForm;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jaxb.core.resource.java.XmlNsForm toJavaResourceModel(XmlNsForm nsForm) {
		return (nsForm == null) ? null : nsForm.getJavaNsForm();
	}
}
