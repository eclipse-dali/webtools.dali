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
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElementRefs;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.Oxm;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.OxmFactory;

public class OxmXmlElementRefsDefinition
		implements OxmAttributeMappingDefinition {
	
	public OxmXmlElementRefsDefinition() {
		super();
	}
	
	
	public String getKey() {
		return ELJaxbMappingKeys.XML_ELEMENT_REFS_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getElement() {
		return Oxm.XML_ELEMENT_REFS;
	}
	
	public EJavaAttribute buildEJavaAttribute() {
		return OxmFactory.eINSTANCE.createEXmlElementRefs();
	}
	
	public OxmAttributeMapping buildContextMapping(OxmJavaAttribute parent, EJavaAttribute resourceMapping) {
		return new OxmXmlElementRefsImpl(parent, (EXmlElementRefs) resourceMapping);
	}
}
