/*******************************************************************************
 * Copyright (c) 2011, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import org.eclipse.xsd.XSDAttributeDeclaration;
import org.eclipse.xsd.XSDTypeDefinition;


public class XsdAttributeDeclaration
		extends XsdFeature<XSDAttributeDeclaration> {
	
	XsdAttributeDeclaration(XSDAttributeDeclaration xsdAttributeDeclaration) {
		super(xsdAttributeDeclaration);
	}
	
	
	@Override
	public XsdTypeDefinition getType() {
		XSDTypeDefinition xsdType = getXSDComponent().getType();
		return (xsdType == null) ? null : (XsdTypeDefinition) XsdUtil.getAdapter(xsdType);
	}
	
	@Override
	public boolean typeIsValid(XsdTypeDefinition xsdType, boolean isItemType) {
		return getType().typeIsValid(xsdType, isItemType);
	}
}
