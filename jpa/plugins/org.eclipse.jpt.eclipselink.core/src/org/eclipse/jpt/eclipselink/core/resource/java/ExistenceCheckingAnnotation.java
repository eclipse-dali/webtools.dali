/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Resource model interface that represents the 
 * org.eclipse.persistence.annotations.ExistenceChecking annotation
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface ExistenceCheckingAnnotation extends JavaResourceNode
{
	
	String ANNOTATION_NAME = EclipseLinkJPA.EXISTENCE_CHECKING;
	
	/**
	 * Corresponds to the value element of the ExistenceChecking annotation.
	 * Returns null if the value element does not exist in java.
	 */
	ExistenceType getValue();
	
	/**
	 * Corresponds to the value element of the ExistenceChecking annotation.
	 * Set to null to remove the value element.
	 */
	void setValue(ExistenceType value);
		String VALUE_PROPERTY = "valueProperty"; //$NON-NLS-1$
	
		
	/**
	 * Return the {@link TextRange} for the value element.  If the value element 
	 * does not exist return the {@link TextRange} for the ExistenceChecking annotation.
	 */
	TextRange getValueTextRange(CompilationUnit astRoot);

	
}
