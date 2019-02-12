/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

/**
 * 
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
public interface XmlJavaTypeAdapter
		extends JaxbContextNode {
	
	XmlJavaTypeAdapterAnnotation getAnnotation();
	
	
	// ***** value *****
	
	String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	String getValue();
	
	void setValue(String value);
	
	String getFullyQualifiedValue();
	
	
	// ***** xmlAdapter *****
	
	String XML_ADAPTER_PROPERTY = "xmlAdapter";  //$NON-NLS-1$
	
	/**
	 * Object based on specified "value"
	 */
	XmlAdapter getXmlAdapter();
	
	
	// ***** type *****
	
	String getType();
	
	String SPECIFIED_TYPE_PROPERTY = "specifiedType"; //$NON-NLS-1$
	
	String getSpecifiedType();
	
	void setSpecifiedType(String type);
	
	String DEFAULT_TYPE_PROPERTY = "defaultType"; //$NON-NLS-1$
	
	String getDefaultType();
	
	String getFullyQualifiedType();
	
	String DEFAULT_TYPE = "javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT"; //$NON-NLS-1$
}
