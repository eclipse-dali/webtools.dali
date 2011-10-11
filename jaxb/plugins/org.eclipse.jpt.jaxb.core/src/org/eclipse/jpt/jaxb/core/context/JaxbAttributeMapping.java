/*******************************************************************************
 *  Copyright (c) 2011 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;


/**
 * Represents a JAXB attribute mapping.
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
public interface JaxbAttributeMapping
		extends JavaContextNode {
	
	JaxbPersistentAttribute getPersistentAttribute();
	
	JavaResourceAttribute getJavaResourceAttribute();
	
	String getKey();
	
	
	// ***** default *****
	
	String DEFAULT_PROPERTY = "default"; //$NON-NLS-1$
	
	/**
	 * Return whether the mapping is its attribute's <em>default</em> mapping
	 * (as opposed to its <em>specified</em> mapping).
	 */
	boolean isDefault();
	
	void updateDefault();
	
	
	// ***** misc *****
	
//	/**
//	 * Return the (fully qualified) type of the attribute. 
//	 * (This is almost always the same as the base type.)
//	 */
//	String getBoundTypeName();
//	
//	/**
//	 * Return the (fully qualified) type that is to be associated with a schema type. 
//	 * (Usually the same as the base type, except when an XmlJavaTypeAdapters is used.)
//	 */
//	String getValueTypeName();
	
	/**
	 * Return all directly referenced types, fully qualified.
	 * (Used for constructing Jaxb context)
	 */
	Iterable<String> getReferencedXmlTypeNames();
}
