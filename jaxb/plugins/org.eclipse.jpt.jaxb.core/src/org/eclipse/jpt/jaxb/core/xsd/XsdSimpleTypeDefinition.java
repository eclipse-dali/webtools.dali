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
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.xsd.XSDSimpleTypeDefinition;


public class XsdSimpleTypeDefinition
		extends XsdTypeDefinition<XSDSimpleTypeDefinition> {
	
	XsdSimpleTypeDefinition(XSDSimpleTypeDefinition xsdSimpleTypeDefinition) {
		super(xsdSimpleTypeDefinition);
	}
	
	
	@Override
	public XsdAttributeUse getAttribute(String namespace, String name) {
		// simple types have no attributes
		return null;
	}
	
	@Override
	public Iterable<String> getAttributeNameProposals(String namespace, Filter<String> filter) {
		// simple types have no attributes
		return EmptyIterable.instance();
	}
	
	@Override
	public XsdElementDeclaration getElement(String namespace, String name) {
		// simple types have no elements
		return null;
	}
	
	@Override
	public Iterable<String> getElementNameProposals(String namespace, Filter<String> filter) {
		// simple types have no elements
		return EmptyIterable.instance();
	}
}
