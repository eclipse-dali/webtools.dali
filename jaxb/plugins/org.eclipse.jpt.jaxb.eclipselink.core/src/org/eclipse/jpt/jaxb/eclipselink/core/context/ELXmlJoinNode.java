/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context;

import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;

/**
 * Represents an @XmlJoinNode, whether at top level or nested in @XmlJoinNodes
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.2
 */
public interface ELXmlJoinNode
		extends JaxbContextNode {
	
	// ***** xmlPath *****
	
	/**
	 * String associated with changes to the xmlPath property
	 */
	String XML_PATH_PROPERTY = "xmlPath";  ///$NON-NLS-1$
	
	/**
	 * Return the xmlPath property value.
	 * A null indicates it is not specified.
	 */
	String getXmlPath();
	
	/**
	 * Set the xmlPath property value.
	 * Null unspecifies the value.
	 */
	void setXmlPath(String value);
	
	
	// ***** referencedXmlPath *****
	
	/**
	 * String associated with changes to the referencedXmlPath property
	 */
	String REFERENCED_XML_PATH_PROPERTY = "referencedXmlPath";  ///$NON-NLS-1$
	
	/**
	 * Return the referencedXmlPath property value.
	 * A null indicates it is not specified.
	 */
	String getReferencedXmlPath();
	
	/**
	 * Set the referencedXmlPath property value.
	 * Null unspecifies the value.
	 */
	void setReferencedXmlPath(String value);
}
