/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
 * @version 3.3
 * @since 3.0
 */
public class XmlAccessType {
	
	public static final XmlAccessType FIELD = 
			new XmlAccessType(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.FIELD);
	
	public static final XmlAccessType NONE = 
			new XmlAccessType(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.NONE);
	
	public static final XmlAccessType PROPERTY = 
			new XmlAccessType(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PROPERTY);
	
	public static final XmlAccessType PUBLIC_MEMBER =
			new XmlAccessType(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType.PUBLIC_MEMBER);
	
	public static XmlAccessType[] VALUES = 
			new XmlAccessType[] {
					FIELD,
					NONE,
					PROPERTY,
					PUBLIC_MEMBER };
	
	
	public static org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType toJavaResourceModel(
			XmlAccessType accessType) {
		return (accessType != null) ? accessType.getJavaAccessType() : null;
	}
	
	public static XmlAccessType fromJavaResourceModel(
			org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType) {
		
		if (javaAccessType == null) {
			return null;
		}
		
		for (XmlAccessType accessType : XmlAccessType.VALUES) {
			if (accessType.getJavaAccessType() == javaAccessType) {
				return accessType;
			}
		}
		
		return null;
	}
	
	
	protected org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType;
	
	
	protected XmlAccessType(org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType javaAccessType) {
		this.javaAccessType = javaAccessType;
	}
	
	public org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType getJavaAccessType() {
		return this.javaAccessType;
	}
}
