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
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlElementRefs;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs;

public class OxmXmlElementRefsImpl
		extends AbstractOxmAttributeMapping<EXmlElementRefs>
		implements OxmXmlElementRefs {
	
	public OxmXmlElementRefsImpl(OxmJavaAttribute parent, EXmlElementRefs eJavaAttribute) {
		super(parent, eJavaAttribute);
	}
	
	
	public String getKey() {
		return ELJaxbMappingKeys.XML_ELEMENT_REFS_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public boolean isParticleMapping() {
		return true;
	}
}
