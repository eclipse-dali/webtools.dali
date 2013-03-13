/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlAnyAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlAnyAttribute;

public class OxmXmlAnyAttributeImpl
		extends AbstractOxmAttributeMapping<EXmlAnyAttribute>
		implements OxmXmlAnyAttribute {
	
	public OxmXmlAnyAttributeImpl(OxmJavaAttribute parent, EXmlAnyAttribute eJavaAttribute) {
		super(parent, eJavaAttribute);
	}
	
	
	public String getKey() {
		return ELJaxbMappingKeys.XML_ANY_ATTRIBUTE_ATTRIBUTE_MAPPING_KEY;
	}
}
