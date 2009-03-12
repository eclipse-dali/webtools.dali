/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OneToOneAnnotation 
	extends OwnableRelationshipMappingAnnotation
{
	String ANNOTATION_NAME = JPA.ONE_TO_ONE;
	
	/**
	 * Corresponds to the optional element of the OneToOne annotation.
	 * Returns null if the optional element does not exist in java.
	 */
	Boolean getOptional();
	
	/**
	 * Corresponds to the optional element of the OneToOne annotation.
	 * Set to null to remove the optional element.
	 */
	void setOptional(Boolean optional);
		String OPTIONAL_PROPERTY = "optional"; //$NON-NLS-1$
	
	/**
	 * Return the {@link TextRange} for the optional element.  If the optional element 
	 * does not exist return the {@link TextRange} for the OneToOne annotation.
	 */
	TextRange getOptionalTextRange(CompilationUnit astRoot);
}
