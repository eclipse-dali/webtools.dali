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

/**
 * Oxm mappings that have an XmlPath
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
public interface ELXmlPathMapping {
	
	// ***** xmlPath *****
	
	/**
	 * String associated with changes to the xmlPath property
	 */
	String XML_PATH_PROPERTY = "xmlPath";  ///$NON-NLS-1$
	
	/**
	 * Return the xmlPath property value.
	 * A null indicates it is not specified.
	 */
	ELXmlPath getXmlPath();
	
	/**
	 * Add (and return) an xmlPath property value.
	 * (Specifies the property)
	 */
	ELXmlPath addXmlPath();
	
	/**
	 * Remove the xmlPath property value.
	 * (Unspecifies the property)
	 */
	void removeXmlPath();
}
