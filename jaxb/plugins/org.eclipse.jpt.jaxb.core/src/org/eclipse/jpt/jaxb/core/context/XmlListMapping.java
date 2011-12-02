/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

/**
 * Represents a JAXB mapping that may be annotated with XmlList
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
public interface XmlListMapping {
	
	// ***** XmlList *****
	
	boolean isXmlList();
	
	String SPECIFIED_XML_LIST_PROPERTY = "specifiedXmlList"; //$NON-NLS-1$
	
	boolean isSpecifiedXmlList();
	
	void setSpecifiedXmlList(boolean newValue);
	
	String DEFAULT_XML_LIST_PROPERTY = "defaultXmlList"; //$NON-NLS-1$
	
	boolean isDefaultXmlList();
}
