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


/**
 * Corresponds to the javax.persistence.NamedNativeQuery annotation
 */
public interface NamedNativeQuery extends Query
{
	/**
	 * Corresponds to the resultClass element of the NamedNativeQuery annotation.
	 * Returns null if the resultClass element does not exist in java.
	 */
	String getResultClass();
	
	/**
	 * Corresponds to the resultClass element of the NamedNativeQuery annotation.
	 * Set to null to remove the resultClass element.
	 */
	void setResultClass(String resultClass);
	
	/**
	 * Corresponds to the resultSetMapping element of the NamedNativeQuery annotation.
	 * Returns null if the resultSetMapping element does not exist in java.
	 */
	String getResultSetMapping();

	/**
	 * Corresponds to the resultSetMapping element of the NamedNativeQuery annotation.
	 * Set to null to remove the resultSetMapping element.
	 */
	void setResultSetMapping(String resultSetMapping);
	
	String getFullyQualifiedResultClass();

	/**
	 * Return the ITextRange for the resultClass element. If resultClass element
	 * does not exist return the ITextRange for the NamedNativeQuery annotation.
	 */
	ITextRange resultClassTextRange(CompilationUnit astRoot);

	
	/**
	 * Return the ITextRange for the resultSetMapping element. If resultSetMapping element
	 * does not exist return the ITextRange for the NamedNativeQuery annotation.
	 */
	ITextRange resultSetMappingTextRange(CompilationUnit astRoot);

}
