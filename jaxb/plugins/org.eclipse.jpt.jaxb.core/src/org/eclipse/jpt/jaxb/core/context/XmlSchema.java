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
	extends 
		JaxbContextNode
{
	String getNamespace();

	void setNamespace(String namespace);
		/**
		 * String constant associated with changes to the namespace
		 */
		String NAMESPACE_PROPERTY = "namespace"; //$NON-NLS-1$

	/**
	 * Return the location, whether specified or defaulted.
	 * This should never return null since at least the default will be set
	 */
	String getLocation();

	/**
	 * Return the default location
	 */
	String getDefaultLocation();

		/**
		 * String constant associated with changes to the location
		 */
		String DEFAULT_LOCATION_PROPERTY = "defaultLocation"; //$NON-NLS-1$

		String DEFAULT_LOCATION = "##generate"; //$NON-NLS-1$

	String getSpecifiedLocation();

	void setSpecifiedLocation(String location);

		/**
		 * String constant associated with changes to the location
		 */
		String SPECIFIED_LOCATION_PROPERTY = "specifiedLocation"; //$NON-NLS-1$


	/**
	 * Return the attribute form default, whether specified or defaulted.
	 * This should never return null since at least the default will be set
	 */
	XmlNsForm getAttributeFormDefault();

	/**
	 * Return the default attribute form default, never null
	 */
	XmlNsForm getDefaultAttributeFormDefault();

		/**
		 * String constant associated with changes to the default attribute form default
		 */
		String DEFAULT_ATTRIBUTE_FORM_DEFAULT_PROPERTY = "defaultAttributeFormDefault"; //$NON-NLS-1$

	/**
	 * Return the specified attribute form default;
	 */
	XmlNsForm getSpecifiedAttributeFormDefault();

	/**
	 * Set the specified attribute form default.
	 */
	void setSpecifiedAttributeFormDefault(XmlNsForm newSpecifiedAttributeFormDefault);

		/**
		 * String constant associated with changes to the specified attribute form default
		 */
		String SPECIFIED_ATTRIBUTE_FROM_DEFAULT_PROPERTY = "specifiedAttributeFormDefault"; //$NON-NLS-1$


	/**
	 * Return the element form default, whether specified or defaulted.
	 * This should never return null since at least the default will be set
	 */
	XmlNsForm getElementFormDefault();

	/**
	 * Return the default element form default, never null
	 */
	XmlNsForm getDefaultElementFormDefault();

		/**
		 * String constant associated with changes to the default element form default
		 */
		String DEFAULT_ELEMENT_FORM_DEFAULT_PROPERTY = "defaultElementFormDefault"; //$NON-NLS-1$

	/**
	 * Return the specified element form default;
	 */
	XmlNsForm getSpecifiedElementFormDefault();

	/**
	 * Set the specified element form default.
	 */
	void setSpecifiedElementFormDefault(XmlNsForm newSpecifiedElementFormDefault);

		/**
		 * String constant associated with changes to the specified element form default
		 */
		String SPECIFIED_ELEMENT_FROM_DEFAULT_PROPERTY = "specifiedElementFormDefault"; //$NON-NLS-1$


	// ********** xml namespace prefixes **********

	ListIterable<XmlNs> getXmlNsPrefixes();
	int getXmlNsPrefixesSize();
	XmlNs addXmlNsPrefix(int index);
	void removeXmlNsPrefix(int index);
	void removeXmlNsPrefix(XmlNs xmlNsPrefix);
	void moveXmlNsPrefix(int targetIndex, int sourceIndex);
		String XML_NS_PREFIXES_LIST = "xmlNsPrefixes"; //$NON-NLS-1$

}
