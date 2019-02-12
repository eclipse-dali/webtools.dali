/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDAttributeUse;


public class XsdAttributeUse
		extends XsdComponent<XSDAttributeUse> {
	
	
	XsdAttributeUse(XSDAttributeUse xsdAttributeUse) {
		super(xsdAttributeUse);
	}
	
	
	public XsdAttributeDeclaration getAttributeDeclaration() {
		XSDAttributeDeclaration xsdAttribute = getXSDComponent().getAttributeDeclaration();
		return (xsdAttribute == null) ? null : (XsdAttributeDeclaration) XsdUtil.getAdapter(xsdAttribute);
	}
}
