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

import org.eclipse.jpt.jaxb.core.context.java.JavaContextNode;


/**
 * Represents a namespace, name pairing
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.?
 * @since 3.? 
 */
public interface JaxbSchemaComponentRef
		extends JavaContextNode {
	
	// ***** namespace *****
	
	static String SPECIFIED_NAMESPACE_PROPERTY = "specifiedNamespace"; //$NON-NLS-1$
	
	String getNamespace();
	
	String getDefaultNamespace();
	
	String getSpecifiedNamespace();
	
	void setSpecifiedNamespace(String namespace);
	
	
	// ***** name *****
	
	static String SPECIFIED_NAME_PROPERTY = "specifiedName"; //$NON-NLS-1$
	
	String getName();
	
	String getDefaultName();
	
	String getSpecifiedName();
	
	void setSpecifiedName(String name);	
}
