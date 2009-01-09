/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Corresponds to the JPA 2.0 javax.persistence.Access annotation
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface AccessAnnotation extends JavaResourceNode
{
	
	String ANNOTATION_NAME = JPA.ACCESS;
	
	/**
	 * Corresponds to the value element of the Access annotation.
	 * Returns null if the value element does not exist in java.
	 */
	AccessType getValue();
	
	/**
	 * Corresponds to the value element of the Access annotation.
	 * Set to null to remove the value element.
	 */
	void setValue(AccessType access);
		String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	/**
	 * Return the {@link TextRange} for the value element.  If the value element 
	 * does not exist return the {@link TextRange} for the Access annotation.
	 */
	TextRange getValueTextRange(CompilationUnit astRoot);

}
