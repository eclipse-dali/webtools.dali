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
 * Common interface for XmlElementMapping, XmlAttributeMapping, and XmlValueMapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.2
 * @since 3.0
 */
public interface XmlNodeMapping
		extends JaxbAttributeMapping, XmlAdaptableMapping {
	
	// ***** xml schema type *****
	
	String XML_SCHEMA_TYPE_PROPERTY = "xmlSchemaType"; //$NON-NLS-1$
	
	XmlSchemaType getXmlSchemaType();
	
	XmlSchemaType addXmlSchemaType();
	
	void removeXmlSchemaType();
	
	
	// ***** XmlList *****
	
	boolean isXmlList();
	
	String SPECIFIED_XML_LIST_PROPERTY = "specifiedXmlList"; //$NON-NLS-1$
	
	boolean isSpecifiedXmlList();
	
	void setSpecifiedXmlList(boolean newValue);
	
	String DEFAULT_XML_LIST_PROPERTY = "defaultXmlList"; //$NON-NLS-1$
	
	boolean isDefaultXmlList();
}
