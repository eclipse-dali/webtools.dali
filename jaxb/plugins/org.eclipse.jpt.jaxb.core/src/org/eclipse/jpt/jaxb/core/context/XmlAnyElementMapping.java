/*******************************************************************************
 *  Copyright (c) 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;


/**
 * Represents a JAXB xml any element mapping (@XmlAnyElement)
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
public interface XmlAnyElementMapping
		extends JaxbAttributeMapping, XmlAdaptableMapping {	
	
	// ***** lax *****
	
	boolean isLax();
	
	String SPECIFIED_LAX_PROPERTY = "specifiedLax"; //$NON-NLS-1$
	
	Boolean getSpecifiedLax();
	
	void setSpecifiedLax(Boolean specifiedLax);
	
	boolean isDefaultLax();
	
	boolean DEFAULT_LAX = false;
	
	
	// ***** value *****
	
	String getValue();
	
	String SPECIFIED_VALUE_PROPERTY = "specifiedValue"; //$NON-NLS-1$
	
	String getSpecifiedValue();
	
	void setSpecifiedValue(String value);
	
	String DEFAULT_TYPE_PROPERTY = "defaultValue"; //$NON-NLS-1$
	
	String getDefaultValue();
	
	String DEFAULT_VALUE = "javax.xml.bind.annotation.W3CDomHandler"; //$NON-NLS-1$
	
	
	// ***** XmlElementRefs *****
	
	XmlElementRefs getXmlElementRefs();
	
	
	// ***** XmlElementWrapper *****
	
	String XML_ELEMENT_WRAPPER_PROPERTY = "xmlElementWrapper"; //$NON-NLS-1$
	
	XmlElementWrapper getXmlElementWrapper();
	
	XmlElementWrapper addXmlElementWrapper();
	
	void removeXmlElementWrapper();
	
	
	// ***** XmlMixed *****
	
	String XML_MIXED_PROPERTY = "xmlMixed"; //$NON-NLS-1$
	
	XmlMixed getXmlMixed();
	
	XmlMixed addXmlMixed();
	
	void removeXmlMixed();	
}
