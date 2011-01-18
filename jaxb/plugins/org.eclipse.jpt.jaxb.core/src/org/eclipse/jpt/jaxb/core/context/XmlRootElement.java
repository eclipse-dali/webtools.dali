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

import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;

/**
 * Represents a JAXB xml root element.  
 * (A class with either an explicit @XmlRootElement annotation)
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
public interface XmlRootElement
		extends JavaContextNode {
	
	/**************** name *****************/

	/**
	 * Return the specified name or the default type name if it is not specified
	 */
	String getName();
	
	/**
	 * Return the specified name or null if it is not specified
	 */
	String getSpecifiedName();
	
	/**
	 * Set the name, null to unspecify (use the default)
	 */
	void setSpecifiedName(String name);
	
	/**
	 * String constant associated with changes to the specified name
	 */
	String SPECIFIED_NAME_PROPERTY = "specifiedName"; //$NON-NLS-1$
	
	/**
	 * Return the default name
	 */
	String getDefaultName();


	/**************** namespace *****************/

	/**
	 * Return the specified namespace or the default namespace if it is not specified
	 */
	String getNamespace();
	
	/**
	 * Return the specified namespace or null if it is not specified
	 */
	String getSpecifiedNamespace();
	
	/**
	 * Set the namespace, null to unspecify (use the default)
	 */
	void setSpecifiedNamespace(String namespace);
	
	/**
	 * String constant associated with changes to the specified namespace
	 */
	String SPECIFIED_NAMESPACE_PROPERTY = "specifiedNamespace"; //$NON-NLS-1$
	
	/**
	 * Return the default namespace
	 */
	String getDefaultNamespace();
}
