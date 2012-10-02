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
public class ELJaxbXmlAccessType
		extends XmlAccessType {
	
	public static final ELJaxbXmlAccessType FIELD = 
			new ELJaxbXmlAccessType(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.FIELD,
					EXmlAccessType.FIELD);
	
	public static final ELJaxbXmlAccessType NONE = 
			new ELJaxbXmlAccessType(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.NONE,
					EXmlAccessType.NONE);
	
	public static final ELJaxbXmlAccessType PROPERTY = 
			new ELJaxbXmlAccessType(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PROPERTY,
					EXmlAccessType.PROPERTY);
	
	public static final ELJaxbXmlAccessType PUBLIC_MEMBER =
			new ELJaxbXmlAccessType(
					org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PUBLIC_MEMBER,
					EXmlAccessType.PUBLIC_MEMBER);
	
	public static ELJaxbXmlAccessType[] VALUES = 
			new ELJaxbXmlAccessType[] {
					ELJaxbXmlAccessType.FIELD,
					ELJaxbXmlAccessType.NONE,
					ELJaxbXmlAccessType.PROPERTY,
					ELJaxbXmlAccessType.PUBLIC_MEMBER };
	
	
	public static org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType toJavaResourceModel(
			ELJaxbXmlAccessType accessType) {
		return (accessType != null) ? accessType.getJavaAccessType() : null;
	}
	
	public static XmlAccessType fromJavaResourceModel(
			org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType) {
		
		if (javaAccessType == null) {
			return null;
		}
		
		for (ELJaxbXmlAccessType accessType : ELJaxbXmlAccessType.VALUES) {
			if (accessType.getJavaAccessType() == javaAccessType) {
				return accessType;
			}
		}
		
		return null;
	}
	
	public static EXmlAccessType toOxmResourceModel(ELJaxbXmlAccessType accessType) {
		return (accessType != null) ? accessType.getOxmAccessType() : null;
	}
	
	public static ELJaxbXmlAccessType fromOxmResourceModel(EXmlAccessType oxmAccessType) {
		
		if (oxmAccessType == null) {
			return null;
		}
		
		for (ELJaxbXmlAccessType accessType : ELJaxbXmlAccessType.VALUES) {
			if (accessType.getOxmAccessType() == oxmAccessType) {
				return accessType;
			}
		}
		
		return null;
	}
	
	
	protected EXmlAccessType oxmAccessType;
	
	
	protected ELJaxbXmlAccessType(
			org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType,
			EXmlAccessType oxmAccessType) {
		super(javaAccessType);
		this.oxmAccessType = oxmAccessType;
	}
	
	
	public EXmlAccessType getOxmAccessType() {
		return this.oxmAccessType;
	}
}
