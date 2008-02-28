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
import org.eclipse.jpt.core.TextRange;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PrimaryKeyJoinColumnAnnotation extends NamedColumnAnnotation
{
	String ANNOTATION_NAME = JPA.PRIMARY_KEY_JOIN_COLUMN;

	String getReferencedColumnName();
	
	void setReferencedColumnName(String referencedColumnName);
		String REFERENCED_COLUMN_NAME_PROPERTY = "referencedColumnNameProperty";

	/**
	 * Return whether the specified postition touches the referencedColumnName element.
	 * Return false if the referencedColumnName element does not exist.
	 */
	boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the referencedColumnName element. If the referencedColumnName 
	 * element does not exist return the ITextRange for the PrimaryKeyJoinColumn annotation.
	 */
	TextRange referencedColumnNameTextRange(CompilationUnit astRoot);

}
