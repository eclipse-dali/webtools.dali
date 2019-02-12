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
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jpt.common.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Corresponds to the JAXB annotation
 * javax.xml.bind.annotation.XmlNs
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.0
 */
public interface XmlNsAnnotation
		extends NestableAnnotation {
	
	// ***** namespaceURI *****
	
	String NAMESPACE_URI_PROPERTY = "namespaceURI"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'namespaceURI' element of the XmlNs annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getNamespaceURI();
		
	/**
	 * Corresponds to the 'namespaceURI' element of the XmlNs annotation.
	 * Set to null to remove the element.
	 */
	void setNamespaceURI(String namespaceURI);
	
	/**
	 * Return the {@link TextRange} for the 'namespaceURI' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlNs annotation.
	 */
	TextRange getNamespaceURITextRange();
	
	/**
	 * Return whether the specified position touches the 'namespaceURI' element.
	 * Return false if the element does not exist.
	 */
	boolean namespaceURITouches(int pos);
	
	
	// ***** prefix *****
	
	String PREFIX_PROPERTY = "prefix"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'prefix' element of the XmlNs annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getPrefix();
		
	/**
	 * Corresponds to the 'prefix' element of the XmlNs annotation.
	 * Set to null to remove the element.
	 */
	void setPrefix(String prefix);
	
	/**
	 * Return the {@link TextRange} for the 'prefix' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlNs annotation.
	 */
	TextRange getPrefixTextRange();
	
	/**
	 * Return whether the specified position touches the 'prefix' element.
	 * Return false if the element does not exist.
	 */
	boolean prefixTouches(int pos);
}
