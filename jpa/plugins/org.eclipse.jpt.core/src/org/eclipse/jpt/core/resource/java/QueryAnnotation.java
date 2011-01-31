/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.resource.java;

import java.util.ListIterator;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * Common protocol among:<ul>
 * <li><code>javax.persistence.NamedQuery</code>
 * <li><code>javax.persistence.NamedNativeQuery</code>
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface QueryAnnotation
	extends Annotation
{
	// ********** name **********

	/**
	 * Corresponds to the 'name' element of the *Query annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getName();	
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'name' element of the *Query annotation.
	 * Set to null to remove the element.
	 */
	void setName(String name);
	
	/**
	 * Return the {@link TextRange} for the 'name' element. If element
	 * does not exist return the {@link TextRange} for the *Query annotation.
	 */
	TextRange getNameTextRange(CompilationUnit astRoot);


	// ********** query **********

	/**
	 * Corresponds to the 'query' element of the *Query annotation.
	 * Return null if the element does not exist in Java.
	 */
	String getQuery();	
		String QUERY_PROPERTY = "query"; //$NON-NLS-1$

	/**
	 * Corresponds to the 'query' element of the *Query annotation.
	 * Set to null to remove the element.
	 */
	void setQuery(String query);
	
	/**
	 * Return the {@link TextRange} for the 'query' element. If element
	 * does not exist return the {@link TextRange} for the *Query annotation.
	 */
	TextRange getQueryTextRange(CompilationUnit astRoot);


	// ********** hints **********

	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 * Return an empty iterator if the element does not exist in Java.
	 */
	ListIterator<QueryHintAnnotation> hints();
		String HINTS_LIST = "hints"; //$NON-NLS-1$
	
	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	int hintsSize();

	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	QueryHintAnnotation hintAt(int index);
	
	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	int indexOfHint(QueryHintAnnotation hint);
	
	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	QueryHintAnnotation addHint(int index);
	
	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	void moveHint(int targetIndex, int sourceIndex);

	/**
	 * Corresponds to the 'hints' element of the *Query annotation.
	 */
	void removeHint(int index);
	
}
