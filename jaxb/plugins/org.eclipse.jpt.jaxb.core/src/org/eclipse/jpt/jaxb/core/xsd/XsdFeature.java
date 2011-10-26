/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.xsd;

import org.eclipse.xsd.XSDFeature;


public abstract class XsdFeature<A extends XSDFeature>
		extends XsdComponent<A> {
	
	protected XsdFeature(A xsdFeature) {
		super(xsdFeature);
	}
	
	
	public A getXSDFeature() {
		return getXSDComponent();
	}
	
	public String getName() {
		return getXSDFeature().getName();
	}
	
	public abstract XsdTypeDefinition getType();
}
