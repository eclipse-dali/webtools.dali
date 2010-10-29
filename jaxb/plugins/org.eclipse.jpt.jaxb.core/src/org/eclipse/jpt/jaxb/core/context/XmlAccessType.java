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
 * Access Type
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
public enum XmlAccessType {

	FIELD(
		org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.FIELD
		),
	NONE(
		org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.NONE
		),
	PROPERTY(
		org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PROPERTY
		),
	PUBLIC_MEMBER(
		org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PUBLIC_MEMBER
		);


	private org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType;

	XmlAccessType(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType) {
		if (javaAccessType == null) {
			throw new NullPointerException();
		}
		this.javaAccessType = javaAccessType;
	}

	public org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType getJavaAccessType() {
		return this.javaAccessType;
	}


	// ********** static methods **********

	public static XmlAccessType fromJavaResourceModel(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType) {
		return (javaAccessType == null) ? null : fromJavaResourceModel_(javaAccessType);
	}

	private static XmlAccessType fromJavaResourceModel_(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType) {
		for (XmlAccessType accessType : XmlAccessType.values()) {
			if (accessType.getJavaAccessType() == javaAccessType) {
				return accessType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType toJavaResourceModel(XmlAccessType accessType) {
		return (accessType == null) ? null : accessType.getJavaAccessType();
	}
}
