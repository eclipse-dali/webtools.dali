/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xsd.XSDElementDeclaration;
import org.eclipse.xsd.XSDSubstitutionGroupExclusions;
import org.eclipse.xsd.XSDTypeDefinition;

/**
 * Adds API to {@link XSDElementDeclaration}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.1
 */
public class XsdElementDeclaration
		extends XsdFeature<XSDElementDeclaration> {
	
	XsdElementDeclaration(XSDElementDeclaration xsdElementDeclaration) {
		super(xsdElementDeclaration);
	}
	
	
	public XSDElementDeclaration getXSDElementDeclaration() {
		return getXSDFeature();
	}
	
	@Override
	public XsdTypeDefinition getType() {
		XSDTypeDefinition xsdType = getXSDElementDeclaration().getTypeDefinition();
		return (xsdType == null) ? null : (XsdTypeDefinition) XsdUtil.getAdapter(xsdType);
	}
	
	@Override
	public boolean typeIsValid(XsdTypeDefinition xsdType, boolean isItemType) {
		XSDElementDeclaration xsdElement = getXSDComponent();
		boolean allowExtension = true;
		boolean allowRestriction = true;
		XSDElementDeclaration xsdSubstitutionGroupAffiliation = xsdElement.getSubstitutionGroupAffiliation();
		if (xsdSubstitutionGroupAffiliation != null) {
			EList<XSDSubstitutionGroupExclusions> xsdSubstitutionGroupExclusions 
					= xsdSubstitutionGroupAffiliation.getSubstitutionGroupExclusions();
			allowExtension = ! xsdSubstitutionGroupExclusions.contains(XSDSubstitutionGroupExclusions.EXTENSION_LITERAL);
			allowRestriction = ! xsdSubstitutionGroupExclusions.contains(XSDSubstitutionGroupExclusions.RESTRICTION_LITERAL);
		}
		return getType().typeIsValid(xsdType, isItemType, allowExtension, allowRestriction);
	}
	
	public XsdElementDeclaration getElement(String namespace, String name) {
		XsdTypeDefinition type = getType();
		return (type == null) ? null : type.getElement(namespace, name);
	}
	
	public Iterable<String> getElementNameProposals(String namespace) {
		return getType().getElementNameProposals(namespace);
	}
}
