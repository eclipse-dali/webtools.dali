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
