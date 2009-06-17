/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.AccessType;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Corresponds to the JPA 2.0 annotation
 * javax.persistence.Access
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
public interface AccessAnnotation
	extends Annotation
{
	
	String ANNOTATION_NAME = JPA.ACCESS;
	
	/**
	 * Corresponds to the 'value' element of the Access annotation.
	 * Returns null if the element does not exist in Java.
	 */
	AccessType getValue();
		String VALUE_PROPERTY = "value"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'value' element of the Access annotation.
	 * Set to null to remove the element.
	 */
	void setValue(AccessType access);
	
	/**
	 * Return the {@link TextRange} for the 'value' element.  If the element 
	 * does not exist return the {@link TextRange} for the Access annotation.
	 */
	TextRange getValueTextRange(CompilationUnit astRoot);

}
