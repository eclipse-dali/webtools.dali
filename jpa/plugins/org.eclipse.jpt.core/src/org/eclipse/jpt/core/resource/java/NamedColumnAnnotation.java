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
public interface NamedColumnAnnotation extends JavaResourceNode
{
	/**
	 * Corresponds to the name element of the *Column annotation.
	 * Returns null if the name valuePair does not exist in the annotation
	 */
	String getName();
	
	/**
	 * Corresponds to the name element of the *Column annotation.
	 * Set to null to remove the name valuePair from the annotation
	 */
	void setName(String name);
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
		
	/**
	 * Corresponds to the columnDefinition element of the *Column annotation.
	 * Returns null if the columnDefinition valuePair does not exist in the annotation
	 */
	String getColumnDefinition();
	
	/**
	 * Corresponds to the columnDefinition element of the *Column annotation.
	 * Set to null to remove the columnDefinition valuePair from the annotation
	 */
	void setColumnDefinition(String columnDefinition);
		String COLUMN_DEFINITION_PROPERTY = "columnDefinition"; //$NON-NLS-1$

	/**
	 * Return the {@link TextRange} for the name element. If the name element
	 * does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);

	/**
	 * Return the {@link TextRange} for the columnDefinition element. If the columnDefinition 
	 * element does not exist return the {@link TextRange} for the *Column annotation.
	 */
	TextRange getColumnDefinitionTextRange(CompilationUnit astRoot);

	/**
	 * Return whether the specified postition touches the name element.
	 * Return false if the name element does not exist.
	 */
	boolean nameTouches(int pos, CompilationUnit astRoot);

}
