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

import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbAttributesContainer;
import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;
import org.eclipse.jpt.jaxb.core.internal.context.AbstractJaxbContextNode;
import org.eclipse.jpt.jaxb.eclipselink.core.context.oxm.OxmJavaAttribute;

// TODO - this is a null implementation at this point, but needs to include the 
// OxmJavaType's attributes as well as any default attributes from java
// (while taking into consideration the access type imposed from the owning class mapping)
public class OxmAttributesContainer
		extends AbstractJaxbContextNode
		implements JaxbAttributesContainer {
	
	public OxmAttributesContainer(JaxbClassMapping parent) {
		super(parent);
	}
	
	
	public Iterable<OxmJavaAttribute> getAttributes() {
		return EmptyIterable.instance();
	}
	
	public int getAttributesSize() {
		return 0;
	}
}
