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
import org.eclipse.jpt.core.resource.java.FetchType;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * Java resource model interface that corresponds to the Eclipselink
 * annotation org.eclipse.persistence.annotations.Transformation
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
public interface TransformationAnnotation extends JavaResourceNode
{
	
	String ANNOTATION_NAME = EclipseLinkJPA.TRANSFORMATION;
	
	/**
	 * Corresponds to the fetch element of the Transformation annotation.
	 * Returns null if the fetch element does not exist in java.
	 */
	FetchType getFetch();
	
	/**
	 * Corresponds to the fetch element of the Transformation annotation.
	 * Set to null to remove the fetch element.
	 */
	void setFetch(FetchType fetch);
		String FETCH_PROPERTY = "fetchProperty";
		
	/**
	 * Corresponds to the optional element of the Transformation annotation.
	 * Returns null if the optional element does not exist in java.
	 */
	Boolean getOptional();
	
	/**
	 * Corresponds to the optional element of the Transformation annotation.
	 * Set to null to remove the optional element.
	 */
	void setOptional(Boolean optional);
		String OPTIONAL_PROPERTY = "optionalProperty";
	
	/**
	 * Return the {@link TextRange} for the fetch element.  If the fetch element 
	 * does not exist return the {@link TextRange} for the Transformation annotation.
	 */
	TextRange getFetchTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the {@link TextRange} for the optional element.  If the optional element 
	 * does not exist return the {@link TextRange} for the Transformation annotation.
	 */
	TextRange getOptionalTextRange(CompilationUnit astRoot);

}
