package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaType;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlElement;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElement;

public class OxmXmlElementImpl
		extends AbstractOxmJavaAttribute<EXmlElement>
		implements OxmXmlElement {
	
	public OxmXmlElementImpl(OxmJavaType parent, EXmlElement eJavaAttribute) {
		super(parent, eJavaAttribute);
	}
}
