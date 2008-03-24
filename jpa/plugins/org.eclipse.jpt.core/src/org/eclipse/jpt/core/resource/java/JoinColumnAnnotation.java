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
public interface JoinColumnAnnotation extends AbstractColumnAnnotation
{
	String ANNOTATION_NAME = JPA.JOIN_COLUMN;

	/**
	 * Corresponds to the referencedColumnName element of the javax.persistence.JoinColumn annotation.
	 * Returns null if the referencedColumnName valuePair does not exist in the annotation
	 */
	String getReferencedColumnName();
	
	/**
	 * Corresponds to the referencedColumnName element of the javax.persistence.JoinColumn annotation.
	 * Set to null to remove the referencedColumnName valuePait from the Annotation
	 */
	void setReferencedColumnName(String referencedColumnName);
		String REFERENCED_COLUMN_NAME_PROPERTY = "referencedColumnNameProperty";
	
	/**
	 * Return the ITextRange for the referencedColumnName element. If the 
	 * referencedColumnName element does not exist return the ITextRange 
	 * for the JoinColumn annotation.
	 */
	TextRange referencedColumnNameTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified postition touches the  referencedColumnName element.
	 * Return false if the  referencedColumnName element does not exist.
	 */
	boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot);

}
