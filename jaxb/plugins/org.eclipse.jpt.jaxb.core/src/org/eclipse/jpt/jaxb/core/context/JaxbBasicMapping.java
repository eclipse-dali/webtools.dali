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

/**
 * Represents a JAXB mapping of an attribute to a single xml attribute or element
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.0
 */
public interface JaxbBasicMapping
		extends JaxbAttributeMapping, XmlAdaptable {
	
	// ***** xml schema type *****
	
	String XML_SCHEMA_TYPE_PROPERTY = "xmlSchemaType"; //$NON-NLS-1$
	
	XmlSchemaType getXmlSchemaType();
	
	XmlSchemaType addXmlSchemaType();
	
	void removeXmlSchemaType();
	
	
	// ***** XmlList *****
	
	String XML_LIST_PROPERTY = "xmlList"; //$NON-NLS-1$
	
	XmlList getXmlList();
	
	XmlList addXmlList();
	
	void removeXmlList();
	
	
	// ***** XmlID *****
	
	String XML_ID_PROPERTY = "xmlID"; //$NON-NLS-1$
	
	XmlID getXmlID();
	
	XmlID addXmlID();
	
	void removeXmlID();
	
	
	// ***** XmlIDREF *****
	
	String XML_IDREF_PROPERTY = "xmlIDREF"; //$NON-NLS-1$
	
	XmlIDREF getXmlIDREF();
	
	XmlIDREF addXmlIDREF();
	
	void removeXmlIDREF();
	
	
	// ***** XmlAttachmentRef *****
	
	String XML_ATTACHMENT_REF_PROPERTY = "xmlAttachmentRef"; //$NON-NLS-1$
	
	XmlAttachmentRef getXmlAttachmentRef();
	
	XmlAttachmentRef addXmlAttachmentRef();
	
	void removeXmlAttachmentRef();
}
