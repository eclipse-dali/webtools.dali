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

	/**
	 * Corresponds to the XmlElementDecl annotation 'name' element
	 */
	String getElementName();
	void setElementName(String elementName);
		String ELEMENT_NAME_PROPERTY = "elementName"; //$NON-NLS-1$

	/**
	 * Corresponds to the XmlElementDecl annotation 'defaultValue' element
	 */
	String getDefaultValue();
	void setDefaultValue(String defaultValue);
		String DEFAULT_VALUE_PROPERTY = "defaultValue"; //$NON-NLS-1$
		String DEFAULT_DEFAULT_VALUE = "\u0000"; //$NON-NLS-1$

	/**
	 * Corresponds to the XmlElementDecl annotation 'namespace' element
	 */
	String getNamespace();
	void setNamespace(String namespace);
		String NAMESPACE_PROPERTY = "namespace"; //$NON-NLS-1$
		String DEFAULT_NAMESPACE = "##default"; //$NON-NLS-1$

	/**
	 * Corresponds to the XmlElementDecl annotation 'substitutionHeadName' element
	 */
	String getSubstitutionHeadName();
	void setSubstitutionHeadName(String substitutionHeadName);
		String SUBSTIUTION_HEAD_NAME_PROPERTY = "substitutionHeadName"; //$NON-NLS-1$
		String DEFAULT_SUBSTIUTION_HEAD_NAME = ""; //$NON-NLS-1$

	/**
	 * Corresponds to the XmlElementDecl annotation 'substitutionHeadNamespace' element
	 */
	String getSubstitutionHeadNamespace();
	void setSubstitutionHeadNamespace(String substitutionHeadNamespace);
		String SUBSTIUTION_HEAD_NAMESPACE_PROPERTY = "substitutionHeadNamespace"; //$NON-NLS-1$
		String DEFAULT_SUBSTIUTION_HEAD_NAMESPACE = "##default"; //$NON-NLS-1$


	/**
	 * Corresponds to the XmlElementDecl annotation 'scope' element
	 */
	String getScope();
	void setScope(String scope);
		String SCOPE_PROPERTY = "scope"; //$NON-NLS-1$
		String DEFAULT_SCOPE_CLASS_NAME = "javax.xml.bind.annotation.XmlElementDecl.GLOBAL"; //$NON-NLS-1$
}
