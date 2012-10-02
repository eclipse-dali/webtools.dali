/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context;

import org.eclipse.jpt.jaxb.core.context.XmlAccessOrder;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessOrder;

/**
 * MOXy extension of Access Order
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public class ELJaxbXmlAccessOrder
		extends XmlAccessOrder {
	
	public static ELJaxbXmlAccessOrder ALPHABETICAL = 
			new ELJaxbXmlAccessOrder(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.ALPHABETICAL,
					EXmlAccessOrder.ALPHABETICAL);
	
	public static ELJaxbXmlAccessOrder UNDEFINED = 
			new ELJaxbXmlAccessOrder(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.UNDEFINED,
					EXmlAccessOrder.UNDEFINED);
	
	public static ELJaxbXmlAccessOrder[] VALUES = 
			new ELJaxbXmlAccessOrder[] {
					ALPHABETICAL,
					UNDEFINED };
	
	
	public static org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder toJavaResourceModel(
			ELJaxbXmlAccessOrder accessOrder) {
		return (accessOrder == null) ? null : accessOrder.getJavaAccessOrder();
	}
	
	public static ELJaxbXmlAccessOrder fromJavaResourceModel(
			org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder javaAccessOrder) {
		
		if (javaAccessOrder == null) {
			return null;
		}
		
		for (ELJaxbXmlAccessOrder accessOrder : ELJaxbXmlAccessOrder.VALUES) {
			if (accessOrder.getJavaAccessOrder() == javaAccessOrder) {
				return accessOrder;
			}
		}
		return null;
	}
	
	public static EXmlAccessOrder toOxmResourceModel(ELJaxbXmlAccessOrder accessOrder) {
		return (accessOrder != null) ? accessOrder.getOxmAccessOrder() : null;
	}
	
	public static ELJaxbXmlAccessOrder fromOxmResourceModel(EXmlAccessOrder oxmAccessOrder) {
		
		if (oxmAccessOrder == null) {
			return null;
		}
		
		for (ELJaxbXmlAccessOrder accessOrder : ELJaxbXmlAccessOrder.VALUES) {
			if (accessOrder.getOxmAccessOrder() == oxmAccessOrder) {
				return accessOrder;
			}
		}
		
		return null;
	}
	
	
	private EXmlAccessOrder oxmAccessOrder;
	
	
	protected ELJaxbXmlAccessOrder(
			org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder javaAccessOrder,
			EXmlAccessOrder oxmAccessOrder) {
		super(javaAccessOrder);
		this.oxmAccessOrder = oxmAccessOrder;
	}
	
	
	public EXmlAccessOrder getOxmAccessOrder() {
		return this.oxmAccessOrder;
	}
}
