/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * 
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
public interface XmlSchema
		extends JaxbContextNode {
	
	/**
	 * Return the specified namespace or "" (default value)
	 */
	String getNamespace();
	
	/**
	 * Return the specified namespace, null if it is not specified
	 */
	String getSpecifiedNamespace();
	
	/**
	 * Set the namespace, null to unspecify
	 */
	void setSpecifiedNamespace(String namespace);
	
	/**
	 * String constant associated with changes to the specified namespace
	 */
	String SPECIFIED_NAMESPACE_PROPERTY = "specifiedNamespace"; //$NON-NLS-1$
	
	
	/**
	 * Corresponds to the XmlSchema annotation location element
	 */
	String getLocation();
	void setLocation(String location);
		String LOCATION_PROPERTY = "location"; //$NON-NLS-1$
		String DEFAULT_LOCATION = "##generate"; //$NON-NLS-1$


	/**
	 * Corresponds to the XmlSchema annotation 'attributeFormDefault' element
	 */
	XmlNsForm getAttributeFormDefault();
	void setAttributeFormDefault(XmlNsForm newAttributeFormDefault);
		String ATTRIBUTE_FROM_DEFAULT_PROPERTY = "atributeFormDefault"; //$NON-NLS-1$
		XmlNsForm DEFAULT_ATTRIBUTE_FORM_DEFAULT = XmlNsForm.UNSET;


	/**
	 * Corresponds to the XmlSchema annotation 'elementFormDefault' element
	 */
	XmlNsForm getElementFormDefault();
	void setElementFormDefault(XmlNsForm newElementFormDefault);
		String ELEMENT_FROM_DEFAULT_PROPERTY = "elementFormDefault"; //$NON-NLS-1$
		XmlNsForm DEFAULT_ELEMENT_FORM_DEFAULT = XmlNsForm.UNSET;


	// ********** xml namespace prefixes **********

	ListIterable<XmlNs> getXmlNsPrefixes();
	int getXmlNsPrefixesSize();
	XmlNs addXmlNsPrefix(int index);
	void removeXmlNsPrefix(int index);
	void removeXmlNsPrefix(XmlNs xmlNsPrefix);
	void moveXmlNsPrefix(int targetIndex, int sourceIndex);
		String XML_NS_PREFIXES_LIST = "xmlNsPrefixes"; //$NON-NLS-1$

}
