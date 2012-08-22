/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to JAXB annotations with namespace and name fields
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.?
 */
public interface QNameAnnotation
		extends Annotation {
	
	/**
	 * Constant associated with changes to the 'namespace' property
	 */
	String NAMESPACE_PROPERTY = "namespace"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'namespace' element of the annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getNamespace();
	
	/**
	 * Corresponds to the 'namespace' element of the annotation.
	 * Set to null to remove the element.
	 */
	void setNamespace(String namespace);
	
	/**
	 * Return the {@link TextRange} for the 'namespace' element. If the element 
	 * does not exist return the {@link TextRange} for the annotation itself.
	 */
	TextRange getNamespaceTextRange();
	
	/**
	 * Return whether the specified position touches the 'namespace' element.
	 * Return false if the element does not exist.
	 */
	boolean namespaceTouches(int pos);
	
	/**
	 * Constant associated with changes to the 'name' property
	 */
	String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'name' element of the annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getName();
	
	/**
	 * Corresponds to the 'name' element of the annotation.
	 * Set to null to remove the element.
	 */
	void setName(String name);
	
	/**
	 * Return the {@link TextRange} for the 'name' element. If the element 
	 * does not exist return the {@link TextRange} for the annotation itself.
	 */
	TextRange getNameTextRange();
	
	/**
	 * Return whether the specified position touches the 'name' element.
	 * Return false if the element does not exist.
	 */
	boolean nameTouches(int pos);
}
