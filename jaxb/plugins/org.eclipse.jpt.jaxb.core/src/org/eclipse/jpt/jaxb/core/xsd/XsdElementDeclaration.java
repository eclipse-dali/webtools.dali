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

import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;


public class XsdElementDeclaration
		extends XsdFeature<XSDElementDeclaration> {
	
	XsdElementDeclaration(XSDElementDeclaration xsdElementDeclaration) {
		super(xsdElementDeclaration);
	}
	
	
	public XSDElementDeclaration getXSDElementDeclaration() {
		return getXSDFeature();
	}
	
	public XsdTypeDefinition getType() {
		XSDTypeDefinition xsdType = getXSDElementDeclaration().getTypeDefinition();
		return (xsdType == null) ? null : (XsdTypeDefinition) XsdUtil.getAdapter(xsdType);
	}
	
	public XsdElementDeclaration getElement(String namespace, String name) {
		XsdTypeDefinition type = getType();
		return (type == null) ? null : type.getElement(namespace, name);
	}
	
	public Iterable<String> getElementNameProposals(String namespace, Filter<String> filter) {
		return getType().getElementNameProposals(namespace, filter);
	}
}
