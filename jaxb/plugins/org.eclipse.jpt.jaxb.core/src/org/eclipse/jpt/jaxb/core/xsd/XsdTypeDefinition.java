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

import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.xsd.XSDTypeDefinition;


public abstract class XsdTypeDefinition<A extends XSDTypeDefinition>
		extends XsdComponent<A> {
	
	protected XsdTypeDefinition(A xsdTypeDefinition) {
		super(xsdTypeDefinition);
	}
	
	
	public String getName() {
		return getXSDComponent().getName();
	}
	
	public abstract XsdAttributeUse getAttribute(String namespace, String name);
	
	public abstract Iterable<String> getAttributeNameProposals(String namespace, Filter<String> filter);
	
	public abstract XsdElementDeclaration getElement(String namespace, String name);
	
	public abstract Iterable<String> getElementNameProposals(String namespace, Filter<String> filter);
}
