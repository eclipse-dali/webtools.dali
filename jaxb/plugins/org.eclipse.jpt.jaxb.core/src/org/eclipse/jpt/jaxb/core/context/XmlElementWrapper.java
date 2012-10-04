/*******************************************************************************
 *  Copyright (c) 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.jaxb.core.xsd.XsdElementDeclaration;


/**
 * Represents a JAXB XML element wrapper
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface XmlElementWrapper
		extends JaxbContextNode {
	
	// ***** qname *****
	
	JaxbQName getQName();
	
	
	// ***** nillable *****
	
	boolean isNillable();
	
	String SPECIFIED_NILLABLE_PROPERTY = "specifiedNillable"; //$NON-NLS-1$
	
	Boolean getSpecifiedNillable();
	
	void setSpecifiedNillable(Boolean specifiedNillable);
	
	boolean isDefaultNillable();
	
	boolean DEFAULT_NILLABLE = false;
		
	
	// ***** required *****
	
	boolean isRequired();
	
	String SPECIFIED_REQUIRED_PROPERTY = "specifiedRequired"; //$NON-NLS-1$
	
	Boolean getSpecifiedRequired();
	
	void setSpecifiedRequired(Boolean specifiedRequired);
	
	boolean isDefaultRequired();
	
	boolean DEFAULT_REQUIRED = false;
		
		
	// ***** misc *****
	
	XsdElementDeclaration getXsdElementDeclaration();
}
