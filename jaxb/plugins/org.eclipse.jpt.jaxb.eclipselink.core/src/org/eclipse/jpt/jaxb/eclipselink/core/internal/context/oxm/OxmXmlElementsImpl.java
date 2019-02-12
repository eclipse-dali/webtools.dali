/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.context.oxm;

import org.eclipse.jpt.jaxb.eclipselink.core.ELJaxbMappingKeys;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmXmlElements;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlElements;

public class OxmXmlElementsImpl
		extends AbstractOxmAttributeMapping<EXmlElements>
		implements OxmXmlElements {
	
	public OxmXmlElementsImpl(OxmJavaAttribute parent, EXmlElements eJavaAttribute) {
		super(parent, eJavaAttribute);
	}
	
	
	public String getKey() {
		return ELJaxbMappingKeys.XML_ELEMENTS_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public boolean isParticleMapping() {
		return true;
	}
}
