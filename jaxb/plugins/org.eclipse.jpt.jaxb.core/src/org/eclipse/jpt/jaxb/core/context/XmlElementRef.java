/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;

/**
 * Represents an @XmlElementRef, whether at top level or nested in @XmlElementRefs
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
public interface XmlElementRef
		extends JavaContextNode {
	
	// ***** annotation *****
	
	XmlElementRefAnnotation getAnnotation();
	
	
	// ***** qname *****
	
	JaxbQName getQName();
	
	
	// ***** required *****
	
	boolean isRequired();
	
	static String SPECIFIED_REQUIRED_PROPERTY = "specifiedRequired"; //$NON-NLS-1$
	
	Boolean getSpecifiedRequired();
	
	void setSpecifiedRequired(Boolean specifiedRequired);
	
	boolean isDefaultRequired();
	
	
	// ***** type *****
	
	String getType();
	
	String getFullyQualifiedType();
	
	String SPECIFIED_TYPE_PROPERTY = "specifiedType"; //$NON-NLS-1$
	
	String getSpecifiedType();
	
	void setSpecifiedType(String type);
	
	String DEFAULT_TYPE_PROPERTY = "defaultType"; //$NON-NLS-1$
	
	String getDefaultType();
	
	
	// ***** misc *****
	
	/**
	 * Return all type names that are directly referenced (for purposes of building a JAXB context)
	 */
	Iterable<String> getDirectlyReferencedTypeNames();
	
	
	// ***** validation *****
	
	TextRange getTypeTextRange(CompilationUnit astRoot);
}
