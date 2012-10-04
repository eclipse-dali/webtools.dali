/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.java;

import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.internal.context.java.GenericJavaXmlElementRef;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;


public class ELJavaXmlElementRef
		extends GenericJavaXmlElementRef {
	
	public ELJavaXmlElementRef(JaxbContextNode parent, GenericJavaXmlElementRef.Context context) {
		super(parent, context);
	}
	
	
	@Override
	protected boolean isTypeJAXBElement() {
		String fqType = getFullyQualifiedType();
		return fqType != null && JDTTools.typeIsSubType(getJaxbProject().getJavaProject(), getFullyQualifiedType(), JAXB.JAXB_ELEMENT);
	}
}
