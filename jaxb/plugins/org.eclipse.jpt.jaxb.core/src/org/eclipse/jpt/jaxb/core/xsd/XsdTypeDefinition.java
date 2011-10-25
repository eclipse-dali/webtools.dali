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
	
	public abstract Kind getKind();
	
	public abstract XsdAttributeUse getAttribute(String namespace, String name);
	
	public abstract Iterable<String> getAttributeNameProposals(String namespace, Filter<String> filter);
	
	public XsdElementDeclaration getElement(String namespace, String name) {
		return getElement(namespace, name, false);
	}
	
	public abstract XsdElementDeclaration getElement(String namespace, String name, boolean recurseChildren);
	
	public Iterable<String> getElementNameProposals(String namespace, Filter<String> filter) {
		return getElementNameProposals(namespace, filter, false);
	}
	
	public abstract Iterable getElementNameProposals(String namespace, Filter<String> filter, boolean recurseChildren);
	
	
	public enum Kind {
		
		/**
		 * An {@link XsdTypeDefinition} of SIMPLE {@link Kind} may safely be cast to an {@link XsdSimpleTypeDefinition}
		 */
		SIMPLE,
		
		/**
		 * An {@link XsdTypeDefinition} of COMPLEX {@link Kind} may safely be cast to an {@link XsdComplexTypeDefinition}
		 */
		COMPLEX;
	}
}
