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


public interface NamedColumn extends JavaResource
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

	/**
	 * Return the ITextRange for the name element. If the name element
	 * does not exist return the ITextRange for the *Column annotation.
	 */
	ITextRange nameTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the columnDefinition element. If the columnDefinition 
	 * element does not exist return the ITextRange for the *Column annotation.
	 */
	ITextRange columnDefinitionTextRange(CompilationUnit astRoot);

}
