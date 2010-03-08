/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
 * Represents a relationship mapping annotation that may have the 'mappedBy'
 * element:
 *     javax.persistence.ManyToMany
 *     javax.persistence.OneToMany
 *     javax.persistence.OneToOne
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
public interface OwnableRelationshipMappingAnnotation
	extends RelationshipMappingAnnotation
{
	/**
	 * Corresponds to the 'mappedBy' element of the annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getMappedBy();
		String MAPPED_BY_PROPERTY = "mappedBy"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'mappedBy' element of the annotation.
	 * Set to null to remove the element.
	 */
	void setMappedBy(String mappedBy);
	
	/**
	 * Return the {@link TextRange} for the 'mappedBy' element. If the element 
	 * does not exist return the {@link TextRange} for the annotation.
	 */
	TextRange getMappedByTextRange(CompilationUnit astRoot);
	
	/**
	 * Return whether the specified position touches the 'mappedBy' element.
	 * Return false if the element does not exist.
	 */
	boolean mappedByTouches(int pos, CompilationUnit astRoot);

}
