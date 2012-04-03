/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbClassMapping;

/**
 * EclipseLink extensions to JaxbClassMapping
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface ELClassMapping
		extends JaxbClassMapping {
	
	// ***** XmlDiscriminatorNode *****
	
	String XML_DISCRIMINATOR_NODE_PROPERTY = "xmlDiscriminatorNode";  //$NON-NLS-1$
	
	ELXmlDiscriminatorNode getXmlDiscriminatorNode();
	
	ELXmlDiscriminatorNode addXmlDiscriminatorNode();
	
	void removeXmlDiscriminatorNode();
	
	
	// ***** XmlDiscriminatorValue *****
	
	String XML_DISCRIMINATOR_VALUE_PROPERTY = "xmlDiscriminatorValue";  //$NON-NLS-1$
	
	ELXmlDiscriminatorValue getXmlDiscriminatorValue();
	
	ELXmlDiscriminatorValue addXmlDiscriminatorValue();
	
	void removeXmlDiscriminatorValue();
	
	
	// ***** misc *****
	
	/**
	 * Return an {@link Iterable} of XPaths that represent the attributes annotated with XmlID or
	 * XmlKey.  (Includes those attributes that are not annotated with XmlPath - those XPaths
	 * will be calculated.)
	 */
	Iterable<String> getKeyXPaths();
}
