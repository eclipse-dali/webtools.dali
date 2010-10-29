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
 * Access Order
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
public enum XmlAccessOrder {

	ALPHABETICAL(
		org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.ALPHABETICAL
		),
	UNDEFINED(
		org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.UNDEFINED
		);


	private org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder javaAccessOrder;

	XmlAccessOrder(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder javaAccessOrder) {
		if (javaAccessOrder == null) {
			throw new NullPointerException();
		}
		this.javaAccessOrder = javaAccessOrder;
	}

	public org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder getJavaAccessOrder() {
		return this.javaAccessOrder;
	}


	// ********** static methods **********

	public static XmlAccessOrder fromJavaResourceModel(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder javaAccessOrder) {
		return (javaAccessOrder == null) ? null : fromJavaResourceModel_(javaAccessOrder);
	}

	private static XmlAccessOrder fromJavaResourceModel_(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder javaAccessOrder) {
		for (XmlAccessOrder accessOrder : XmlAccessOrder.values()) {
			if (accessOrder.getJavaAccessOrder() == javaAccessOrder) {
				return accessOrder;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder toJavaResourceModel(XmlAccessOrder accessOrder) {
		return (accessOrder == null) ? null : accessOrder.getJavaAccessOrder();
	}
}
