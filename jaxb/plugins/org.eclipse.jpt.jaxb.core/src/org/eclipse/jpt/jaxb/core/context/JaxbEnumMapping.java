/*******************************************************************************
 *  Copyright (c) 2011, 2013  Oracle. All rights reserved.
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

import org.eclipse.jpt.jaxb.core.xsd.XsdSimpleTypeDefinition;

/**
 * Represents mapping metadata on an enum (specified or implied).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
public interface JaxbEnumMapping
		extends JaxbTypeMapping {
	
	// ***** XmlEnum.value *****
	
	String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	/**
	 * Return the fully qualified type, specified or not
	 */
	String getValue();
	
	String SPECIFIED_VALUE_PROPERTY = "specifiedXmlEnum"; //$NON-NLS-1$
	
	/**
	 * Return the value of the XmlEnum.value element
	 */
	String getSpecifiedValue();
	
	void setSpecifiedValue(String value);
	
	/**
	 * The default value if nothing else is specified
	 */
	String DEFAULT_VALUE = "java.lang.String"; //$NON-NLS-1$
	
	
	// ***** enum constants *****
	
	String ENUM_CONSTANTS_COLLECTION = "enumConstants"; //$NON-NLS-1$
	
	Iterable<JaxbEnumConstant> getEnumConstants();
	
	int getEnumConstantsSize();
	
	
	// ***** misc *****
	
	XsdSimpleTypeDefinition getValueXsdTypeDefinition();
}
