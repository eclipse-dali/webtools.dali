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
 * Represents a JAXB containment mappings (xml element/attribute)
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
public interface JaxbContainmentMapping
		extends JaxbAttributeMapping, XmlAdaptable {


	String getName();
	String getDefaultName();
	String getSpecifiedName();
	void setSpecifiedName(String name);
		String SPECIFIED_NAME_PROPERTY = "specifiedName"; //$NON-NLS-1$

	boolean isRequired();
	boolean isDefaultRequired();
		boolean DEFAULT_REQUIRED = false;
	Boolean getSpecifiedRequired();
	void setSpecifiedRequired(Boolean specifiedRequired);
		String SPECIFIED_REQUIRED_PROPERTY = "specifiedRequired"; //$NON-NLS-1$

	String getNamespace();
	String getDefaultNamespace();
	String getSpecifiedNamespace();
	void setSpecifiedNamespace(String namespace);
		String SPECIFIED_NAMESPACE_PROPERTY = "specifiedNamespace"; //$NON-NLS-1$



	// ********** xml schema type ************
	/**
	 * Return whether this class has an XmlSchemaType annotation)
	 */
	boolean hasXmlSchemaType();

	/**
	 * Return the xml schema type or null.
	 */
	XmlSchemaType getXmlSchemaType();

	XmlSchemaType addXmlSchemaType();
	void removeXmlSchemaType();
		String XML_SCHEMA_TYPE = "xmlSchemaType"; //$NON-NLS-1$


	/********** XmlList **********/
	XmlList getXmlList();
	XmlList addXmlList();
	void removeXmlList();
		String XML_LIST_PROPERTY = "xmlList"; //$NON-NLS-1$


	/********** XmlID **********/
	XmlID getXmlID();
	XmlID addXmlID();
	void removeXmlID();
		String XML_ID_PROPERTY = "xmlID"; //$NON-NLS-1$

}