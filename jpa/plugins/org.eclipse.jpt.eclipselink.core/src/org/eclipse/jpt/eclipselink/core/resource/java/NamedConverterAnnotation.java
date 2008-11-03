/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface NamedConverterAnnotation extends JavaResourceNode
{
	/**
	 * Corresponds to the name element of the CustomConverter annotation.
	 * Returns null if the name element does not exist in java.
	 */
	String getName();
	
	/**
	 * Corresponds to the name element of the CustomConverter annotation.
	 * Set to null to remove the name element.
	 */
	void setName(String value);
		String NAME_PROPERTY = "nameProperty"; //$NON-NLS-1$

	/**
	 * Return the {@link TextRange} for the name element.  If the name element 
	 * does not exist return the {@link TextRange} for the CustomConverter annotation.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);
}
