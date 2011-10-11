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
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;

/**
 * Represents an @XmlElement, whether at top level or nested in @XmlElements
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
public interface XmlElement
		extends JavaContextNode {
	
	// ***** annotation *****
	
	XmlElementAnnotation getAnnotation(boolean createIfNull);
	
	
	// ***** qname *****
	
	JaxbQName getQName();
	
	
	// ***** nillable *****
	
	boolean isNillable();
	
	String SPECIFIED_NILLABLE_PROPERTY = "specifiedNillable"; //$NON-NLS-1$
	
	Boolean getSpecifiedNillable();
	
	void setSpecifiedNillable(Boolean specifiedNillable);
	
	String DEFAULT_NILLABLE_PROPERTY = "defaultNillable"; //$NON-NLS-1$
	
	boolean isDefaultNillable();
	
	
	// ***** required *****
	
	boolean isRequired();
	
	static String SPECIFIED_REQUIRED_PROPERTY = "specifiedRequired"; //$NON-NLS-1$
	
	Boolean getSpecifiedRequired();
	
	void setSpecifiedRequired(Boolean specifiedRequired);
	
	boolean isDefaultRequired();
	
	
	// ***** default value *****
	
	String DEFAULT_VALUE_PROPERTY = "defaultValue"; //$NON-NLS-1$
	
	String getDefaultValue();
	
	void setDefaultValue(String defaultValue);
	
	
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
	 * Return all directly referenced xml types, fully qualified.
	 * (Used for constructing Jaxb context)
	 */
	Iterable<String> getReferencedXmlTypeNames();
	
	
	// ***** validation *****
	
	TextRange getTypeTextRange(CompilationUnit astRoot);
}
