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
}
