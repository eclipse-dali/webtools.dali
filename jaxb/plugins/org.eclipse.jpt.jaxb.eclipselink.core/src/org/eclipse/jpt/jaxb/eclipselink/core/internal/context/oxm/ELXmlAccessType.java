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

import org.eclipse.jpt.jaxb.core.context.XmlAccessType;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAccessType;

public class ELXmlAccessType {
	
	/* not to be constructed */
	private ELXmlAccessType() {}
	
	public static EXmlAccessType toOxmResourceModel(XmlAccessType accessType) {
		if (accessType == XmlAccessType.FIELD) {
			return EXmlAccessType.FIELD;
		}
		else if (accessType == XmlAccessType.NONE) {
			return EXmlAccessType.NONE;
		}
		else if (accessType == XmlAccessType.PROPERTY) {
			return EXmlAccessType.PROPERTY;
		}
		else if (accessType == XmlAccessType.PUBLIC_MEMBER) {
			return EXmlAccessType.PUBLIC_MEMBER;
		}
		else if (accessType == null) {
			return null;
		}
		else {
			throw new IllegalArgumentException(accessType.toString());
		}
	}
	
	public static XmlAccessType fromOxmResourceModel(EXmlAccessType accessType) {
		if (accessType == EXmlAccessType.FIELD) {
			return XmlAccessType.FIELD;
		}
		else if (accessType == EXmlAccessType.NONE) {
			return XmlAccessType.NONE;
		}
		else if (accessType == EXmlAccessType.PROPERTY) {
			return XmlAccessType.PROPERTY;
		}
		else if (accessType == EXmlAccessType.PUBLIC_MEMBER) {
			return XmlAccessType.PUBLIC_MEMBER;
		}
		else if (accessType == null) {
			return null;
		}
		else {
			throw new IllegalArgumentException(accessType.toString());
		}
	}
}
