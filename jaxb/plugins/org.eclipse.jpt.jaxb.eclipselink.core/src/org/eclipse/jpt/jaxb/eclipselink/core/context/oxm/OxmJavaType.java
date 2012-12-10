/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.context.oxm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jaxb.core.context.JaxbContextNode;
import org.eclipse.jpt.jaxb.core.context.XmlAccessOrderHolder;
import org.eclipse.jpt.jaxb.core.context.XmlAccessTypeHolder;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.oxm.EJavaType;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface OxmJavaType
		extends JaxbContextNode, XmlAccessOrderHolder, XmlAccessTypeHolder {
	
	/**
	 * Resource model element
	 */
	EJavaType getEJavaType();
	
	
	// ***** name *****
	
	/**
	 * String associated with changes to the "specifiedName" property
	 */
	String SPECIFIED_NAME_PROPERTY = "specifiedName"; //$NON-NLS-1$
	
	/**
	 * Return the name specified in source
	 */
	String getSpecifiedName();
	
	/**
	 * Set the name specified in source
	 */
	void setSpecifiedName(String newName);
	
	/**
	 * String associated with changes to the "specifiedName" property
	 */
	String QUALIFIED_NAME_PROPERTY = "qualifiedName"; //$NON-NLS-1$
	
	/**
	 * Return
	 * - the name specified in source, if it is qualified or primitive
	 * - the name specified in source with the xml-bindings package name prepended
	 */
	String getQualifiedName();
	
	/**
	 * Return the name with no package qualification
	 */
	String getSimpleName();
	
	
	// ***** specified attributes *****
	
	String SPECIFIED_ATTRIBUTES_LIST = "specifiedAttributes"; //$NON-NLS-1$
	
	ListIterable<OxmJavaAttribute> getSpecifiedAttributes();
	
	int getSpecifiedAttributesSize();
}