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


public interface Basic extends JavaResource
{
	
	/**
	 * Corresponds to the fetch element of the Basic annotation.
	 * Returns null if the fetch element does not exist in java.
	 */
	FetchType getFetch();
	
	/**
	 * Corresponds to the fetch element of the Basic annotation.
	 * Set to null to remove the fetch element.
	 */
	void setFetch(FetchType fetch);
	
	/**
	 * Corresponds to the optional element of the Basic annotation.
	 * Returns null if the optional element does not exist in java.
	 */
	Boolean getOptional();
	
	/**
	 * Corresponds to the optional element of the Basic annotation.
	 * Set to null to remove the optional element.
	 */
	void setOptional(Boolean optional);

	/**
	 * Return the ITextRange for the fetch element.  If the fetch element 
	 * does not exist return the ITextRange for the Basic annotation.
	 */
	ITextRange fetchTextRange(CompilationUnit astRoot);
	
	/**
	 * Return the ITextRange for the optional element.  If the optional element 
	 * does not exist return the ITextRange for the Basic annotation.
	 */
	ITextRange optionalTextRange(CompilationUnit astRoot);

}
