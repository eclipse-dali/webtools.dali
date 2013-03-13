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

import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmAttributeMapping;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaAttribute;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EXmlJavaTypeAdapter;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.Oxm;

public class OxmXmlJavaTypeAdapterXmlElementDefinition
		implements OxmAttributeMappingDefinition {
	
	public OxmXmlJavaTypeAdapterXmlElementDefinition() {
		super();
	}
	
	
	public String getKey() {
		// Hmmmmmmm ....
		return null;
	}
	
	public String getElement() {
		return Oxm.XML_JAVA_TYPE_ADAPTER;
	}
	
	public EJavaAttribute buildEJavaAttribute() {
		// Hmmmmmm ....
		return null;
	}
	
	public OxmAttributeMapping buildContextMapping(OxmJavaAttribute parent, EJavaAttribute resourceMapping) {
		return new OxmXmlJavaTypeAdapterXmlElementImpl(parent, (EXmlJavaTypeAdapter) resourceMapping);
	}
}
