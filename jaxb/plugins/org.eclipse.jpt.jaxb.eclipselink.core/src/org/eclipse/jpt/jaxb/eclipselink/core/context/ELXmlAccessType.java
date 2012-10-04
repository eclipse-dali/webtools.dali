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

import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType;

/**
 * MOXy extension of Access Type
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
public class ELXmlAccessType
		extends XmlAccessType {
	
	public static final ELXmlAccessType FIELD = 
			new ELXmlAccessType(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.FIELD,
					EXmlAccessType.FIELD);
	
	public static final ELXmlAccessType NONE = 
			new ELXmlAccessType(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.NONE,
					EXmlAccessType.NONE);
	
	public static final ELXmlAccessType PROPERTY = 
			new ELXmlAccessType(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PROPERTY,
					EXmlAccessType.PROPERTY);
	
	public static final ELXmlAccessType PUBLIC_MEMBER =
			new ELXmlAccessType(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PUBLIC_MEMBER,
					EXmlAccessType.PUBLIC_MEMBER);
	
	public static ELXmlAccessType[] VALUES = 
			new ELXmlAccessType[] {
					ELXmlAccessType.FIELD,
					ELXmlAccessType.NONE,
					ELXmlAccessType.PROPERTY,
					ELXmlAccessType.PUBLIC_MEMBER };
	
	
	public static org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType toJavaResourceModel(
			ELXmlAccessType accessType) {
		return (accessType != null) ? accessType.getJavaAccessType() : null;
	}
	
	public static XmlAccessType fromJavaResourceModel(
			org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType) {
		
		if (javaAccessType == null) {
			return null;
		}
		
		for (ELXmlAccessType accessType : ELXmlAccessType.VALUES) {
			if (accessType.getJavaAccessType() == javaAccessType) {
				return accessType;
			}
		}
		
		return null;
	}
	
	public static EXmlAccessType toOxmResourceModel(ELXmlAccessType accessType) {
		return (accessType != null) ? accessType.getOxmAccessType() : null;
	}
	
	public static ELXmlAccessType fromOxmResourceModel(EXmlAccessType oxmAccessType) {
		
		if (oxmAccessType == null) {
			return null;
		}
		
		for (ELXmlAccessType accessType : ELXmlAccessType.VALUES) {
			if (accessType.getOxmAccessType() == oxmAccessType) {
				return accessType;
			}
		}
		
		return null;
	}
	
	
	protected EXmlAccessType oxmAccessType;
	
	
	protected ELXmlAccessType(
			org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType,
			EXmlAccessType oxmAccessType) {
		super(javaAccessType);
		this.oxmAccessType = oxmAccessType;
	}
	
	
	public EXmlAccessType getOxmAccessType() {
		return this.oxmAccessType;
	}
}
