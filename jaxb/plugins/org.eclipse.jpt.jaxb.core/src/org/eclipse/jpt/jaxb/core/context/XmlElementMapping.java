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
 * Represents a JAXB xml element mapping (@XmlElement)
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
public interface XmlElementMapping
		extends JaxbContainmentMapping {
	
	boolean isNillable();
	boolean isDefaultNillable();
		boolean DEFAULT_NILLABLE = false;
	Boolean getSpecifiedNillable();
	void setSpecifiedNillable(Boolean specifiedNillable);
		String SPECIFIED_NILLABLE_PROPERTY = "specifiedNillable"; //$NON-NLS-1$

	String getDefaultValue();
	void setDefaultValue(String defaultValue);
		String DEFAULT_VALUE_PROPERTY = "defaultValue"; //$NON-NLS-1$

	String getType();
	String getDefaultType();
	String getSpecifiedType();
	void setSpecifiedType(String type);
		String SPECIFIED_TYPE_PROPERTY = "specifiedType"; //$NON-NLS-1$


	/********** XmlElementWrapper **********/
	
	XmlElementWrapper getXmlElementWrapper();
	XmlElementWrapper addXmlElementWrapper();
	void removeXmlElementWrapper();
		String XML_ELEMENT_WRAPPER_PROPERTY = "xmlElementWrapper"; //$NON-NLS-1$
}
