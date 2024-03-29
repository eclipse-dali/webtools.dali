/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. All rights reserved.
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

import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;

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
	
	/**
	 * Return the method name
	 */
	String getName();
	
	
	// ***** scope *****
	
	/**
	 * Corresponds to the XmlElementDecl annotation 'scope' element
	 */
	String SCOPE_PROPERTY = "scope"; //$NON-NLS-1$
	
	String getScope();
	
	void setScope(String scope);
	
	String DEFAULT_SCOPE_CLASS_NAME = "javax.xml.bind.annotation.XmlElementDecl.GLOBAL"; //$NON-NLS-1$
	
	String getFullyQualifiedScope();
	
	/**
	 * Return true if the scope is default or is specified to be XmlElementDecl.GLOBAL.
	 */
	boolean isGlobalScope();
	
	
	// ***** qName *****
	
	JaxbQName getQName();
	
	
	// ***** substitution head qName *****
	
	JaxbQName getSubstitutionHeadQName();
	
	
	// ***** default value *****
	
	/**
	 * Corresponds to the XmlElementDecl annotation 'defaultValue' element
	 */
	String DEFAULT_VALUE_PROPERTY = "defaultValue"; //$NON-NLS-1$
	
	String getDefaultValue();
	
	void setDefaultValue(String defaultValue);
	
	String DEFAULT_DEFAULT_VALUE = "\u0000"; //$NON-NLS-1$	
}
