/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.ITextRange;


public interface OneToOne extends RelationshipMappingAnnotation
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
		String OPTIONAL_PROPERTY = "optionalProperty";
	
	/**
	 * Corresponds to the mappedBy element of the OneToOne annotation. 
	 * Returns null if the mappedBy element does not exist in java.
	 */
	String getMappedBy();
	
	/**
	 * Corresponds to the mappedBy element of the OneToOne annotation. 
	 * Set to null to remove the mappedBy element.
	 */
	void setMappedBy(String mappedBy);
		String MAPPED_BY_PROPERTY = "mappedByProperty";
	
	/**
	 * Return the ITextRange for the mappedBy element.  If the mappedBy element 
	 * does not exist return the ITextRange for the OneToOne annotation.
	 */
	ITextRange mappedByTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the optional element.  If the optional element 
	 * does not exist return the ITextRange for the OneToOne annotation.
	 */
	ITextRange optionalTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified postition touches the mappedBy element.
	 * Return false if the mappedBy element does not exist.
	 */
	boolean mappedByTouches(int pos, CompilationUnit astRoot);

}
