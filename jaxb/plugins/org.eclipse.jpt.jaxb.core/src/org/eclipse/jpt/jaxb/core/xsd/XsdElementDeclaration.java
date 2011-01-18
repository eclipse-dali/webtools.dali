/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;


public class XsdElementDeclaration
		extends XsdAdapter {
	
	protected final XSDElementDeclaration xsdElementDeclaration;
	
	
	XsdElementDeclaration(XSDElementDeclaration xsdElementDeclaration) {
		super();
		this.xsdElementDeclaration = xsdElementDeclaration;
	}
	
	
	public String getName() {
		return this.xsdElementDeclaration.getName();
	}
	
	public XsdTypeDefinition getType() {
		XSDTypeDefinition xsdType = this.xsdElementDeclaration.getTypeDefinition();
		return (xsdType == null) ? null : (XsdTypeDefinition) XsdUtil.getAdapter(xsdType);
	}
}
