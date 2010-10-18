/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.utility.TextRange;

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
 * @version 3.0
 * @since 3.0
 */
public interface XmlNsAnnotation
		extends Annotation {
	
	String ANNOTATION_NAME = JAXB.XML_NS;
	
	/**
	 * Corresponds to the 'namespace' element of the XmlNs annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getNamespace();
		String NAMESPACE_PROPERTY = "namespace"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'namespace' element of the XmlNs annotation.
	 * Set to null to remove the element.
	 */
	void setNamespace(String namespace);
	
	/**
	 * Return the {@link TextRange} for the 'namespace' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlNs annotation.
	 */
	TextRange getNamespaceTextRange(CompilationUnit astRoot);
	
	/**
	 * Corresponds to the 'prefix' element of the XmlNs annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getPrefix();
		String PREFIX_PROPERTY = "prefix"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'prefix' element of the XmlNs annotation.
	 * Set to null to remove the element.
	 */
	void setPrefix(String prefix);
	
	/**
	 * Return the {@link TextRange} for the 'prefix' element. If the element 
	 * does not exist return the {@link TextRange} for the XmlNs annotation.
	 */
	TextRange getPrefixTextRange(CompilationUnit astRoot);
}
