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
		XsdTypeDefinition type = getType();
		if (isItemType) {
			type = (type.getKind() == XsdTypeDefinition.Kind.SIMPLE) ? 
					((XsdSimpleTypeDefinition) type).getItemType() : null;
		}
		if (type == null) {
			return false;
		}
		return type.getXSDComponent().getBadTypeDerivation(xsdType.getXSDComponent(), true, true) == null;
	}
}
