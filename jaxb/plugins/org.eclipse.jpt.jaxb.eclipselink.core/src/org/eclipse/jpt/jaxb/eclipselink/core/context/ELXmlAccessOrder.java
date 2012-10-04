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
public class ELXmlAccessOrder
		extends XmlAccessOrder {
	
	public static ELXmlAccessOrder ALPHABETICAL = 
			new ELXmlAccessOrder(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.ALPHABETICAL,
					EXmlAccessOrder.ALPHABETICAL);
	
	public static ELXmlAccessOrder UNDEFINED = 
			new ELXmlAccessOrder(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder.UNDEFINED,
					EXmlAccessOrder.UNDEFINED);
	
	public static ELXmlAccessOrder[] VALUES = 
			new ELXmlAccessOrder[] {
					ALPHABETICAL,
					UNDEFINED };
	
	
	public static org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder toJavaResourceModel(
			ELXmlAccessOrder accessOrder) {
		return (accessOrder == null) ? null : accessOrder.getJavaAccessOrder();
	}
	
	public static ELXmlAccessOrder fromJavaResourceModel(
			org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder javaAccessOrder) {
		
		if (javaAccessOrder == null) {
			return null;
		}
		
		for (ELXmlAccessOrder accessOrder : ELXmlAccessOrder.VALUES) {
			if (accessOrder.getJavaAccessOrder() == javaAccessOrder) {
				return accessOrder;
			}
		}
		return null;
	}
	
	public static EXmlAccessOrder toOxmResourceModel(ELXmlAccessOrder accessOrder) {
		return (accessOrder != null) ? accessOrder.getOxmAccessOrder() : null;
	}
	
	public static ELXmlAccessOrder fromOxmResourceModel(EXmlAccessOrder oxmAccessOrder) {
		
		if (oxmAccessOrder == null) {
			return null;
		}
		
		for (ELXmlAccessOrder accessOrder : ELXmlAccessOrder.VALUES) {
			if (accessOrder.getOxmAccessOrder() == oxmAccessOrder) {
				return accessOrder;
			}
		}
		
		return null;
	}
	
	
	private EXmlAccessOrder oxmAccessOrder;
	
	
	protected ELXmlAccessOrder(
			org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder javaAccessOrder,
			EXmlAccessOrder oxmAccessOrder) {
		super(javaAccessOrder);
		this.oxmAccessOrder = oxmAccessOrder;
	}
	
	
	public EXmlAccessOrder getOxmAccessOrder() {
		return this.oxmAccessOrder;
	}
}
