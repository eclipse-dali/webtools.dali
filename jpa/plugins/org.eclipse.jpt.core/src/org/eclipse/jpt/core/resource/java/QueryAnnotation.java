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

import java.util.ListIterator;
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
public interface QueryAnnotation extends JavaResourceNode
{
	String getName();	
	void setName(String name);
		String NAME_PROPERTY = "nameProperty";
	
	String getQuery();	
	void setQuery(String query);
		String QUERY_PROPERTY = "queryProperty";
	
	ListIterator<QueryHintAnnotation> hints();
	
	QueryHintAnnotation hintAt(int index);
	
	int indexOfHint(QueryHintAnnotation hint);
	
	int hintsSize();

	QueryHintAnnotation addHint(int index);
	
	void removeHint(int index);
	
	void moveHint(int targetIndex, int sourceIndex);
		String HINTS_LIST = "hintsList";

	/**
	 * Return the ITextRange for the name element. If name element
	 * does not exist return the ITextRange for the *Query annotation.
	 */
	TextRange nameTextRange(CompilationUnit astRoot);

	/**
	 * Return the ITextRange for the query element. If query element
	 * does not exist return the ITextRange for the *Query annotation.
	 */
	TextRange queryTextRange(CompilationUnit astRoot);

}
