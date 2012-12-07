/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder;

public class ELXmlAccessOrder {
	
	/* not to be constructed */
	private ELXmlAccessOrder() {}
	
	public static EXmlAccessOrder toOxmResourceModel(XmlAccessOrder accessOrder) {
		if (accessOrder == XmlAccessOrder.ALPHABETICAL) {
			return EXmlAccessOrder.ALPHABETICAL;
		}
		else if (accessOrder == XmlAccessOrder.UNDEFINED) {
			return EXmlAccessOrder.UNDEFINED;
		}
		else if (accessOrder == null) {
			return null;
		}
		else {
			throw new IllegalArgumentException(accessOrder.toString());
		}
	}
	
	public static XmlAccessOrder fromOxmResourceModel(EXmlAccessOrder accessOrder) {
		if (accessOrder == EXmlAccessOrder.ALPHABETICAL) {
			return XmlAccessOrder.ALPHABETICAL;
		}
		else if (accessOrder == EXmlAccessOrder.UNDEFINED) {
			return XmlAccessOrder.UNDEFINED;
		}
		else if (accessOrder == null) {
			return null;
		}
		else {
			throw new IllegalArgumentException(accessOrder.toString());
		}
	}
}
