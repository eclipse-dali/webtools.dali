/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMethod;

/**
 * Represents a JAXB element factory method  
 * (A method inside an object factory (@XmlRegistry) with an explicit @XmlElementDecl annotation)
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
public interface JaxbElementFactoryMethod
		extends JaxbContextNode {

	JavaResourceMethod getResourceMethod();

	String getName();

	/**
	 * Corresponds to the XmlElementDecl annotation 'name' element
	 */
	String getElementName();
	void setElementName(String elementName);
		String ELEMENT_NAME_PROPERTY = "elementName"; //$NON-NLS-1$


	//String 	defaultValue
	//String 	namespace
	//Class 	scope
	//String 	substitutionHeadName
	//String 	substitutionHeadNamespace
		
//	String getValue();
//	String getDefaultValue();
//	String getSpecifiedValue();
//	void setSpecifiedValue(String value);
//		String SPECIFIED_VALUE_PROPERTY = "specifiedValue"; //$NON-NLS-1$

}
