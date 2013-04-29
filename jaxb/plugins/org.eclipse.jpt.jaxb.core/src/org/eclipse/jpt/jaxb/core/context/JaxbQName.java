/*******************************************************************************
 *  Copyright (c) 2011, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.context;

import org.eclipse.jpt.common.core.utility.TextRange;


/**
 * Represents a namespace, name pairing
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.? 
 */
public interface JaxbQName
		extends JaxbContextNode {
	
	// ***** namespace *****
	
	static String NAMESPACE_PROPERTY = "namespace"; //$NON-NLS-1$
	
	String getNamespace();
	
	static String DEFAULT_NAMESPACE_PROPERTY = "defaultNamespace"; //$NON-NLS-1$
	
	String getDefaultNamespace();
	
	static String SPECIFIED_NAMESPACE_PROPERTY = "specifiedNamespace"; //$NON-NLS-1$
	
	String getSpecifiedNamespace();
	
	void setSpecifiedNamespace(String namespace);
	
	
	// ***** name *****
	
	static String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	String getName();
	
	static String DEFAULT_NAME_PROPERTY = "defaultName"; //$NON-NLS-1$
	
	String getDefaultName();
	
	static String SPECIFIED_NAME_PROPERTY = "specifiedName"; //$NON-NLS-1$
	
	String getSpecifiedName();
	
	void setSpecifiedName(String name);
	
	
	// ***** validation *****
	
	TextRange getNamespaceValidationTextRange();
	
	TextRange getNameValidationTextRange();
}
