/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.ListIterator;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface Query
	extends JpaContextNode
{
	String NAMED_QUERY="namedQuery"; //$NON-NLS-1$
	String NAMED_NATIVE_QUERY="namedNativeQuery"; //$NON-NLS-1$
	
	// **************** name ***************************************************
	
	String NAME_PROPERTY = "name"; //$NON-NLS-1$
	
	String getName();
	
	void setName(String value);
	
	
	//************************ query ***********************
	
	String QUERY_PROPERTY = "query"; //$NON-NLS-1$
	
	String getQuery();
	
	void setQuery(String value);
	
	
	//************************ hints ***********************
	
	String HINTS_LIST = "hints"; //$NON-NLS-1$
	
	/**
	 * Return a list iterator of the hints.  This will not be null.
	 */
	<T extends QueryHint> ListIterator<T> hints();
	
	/**
	 * Return the number of hints.
	 */
	int hintsSize();
	
	/**
	 * Add a hint to the query and return the object representing it.
	 */
	QueryHint addHint(int index);
	
	/**
	 * Remove the hint from the query.
	 */
	void removeHint(int index);
	
	/**
	 * Remove the hint at the index from the query.
	 */
	void removeHint(QueryHint queryHint);
	
	/**
	 * Move the hint from the source index to the target index.
	 */
	void moveHint(int targetIndex, int sourceIndex);
	
	
	// **************** validation *********************************************
	
	/**
	 * Return whether the query overrides the definition of the given query
	 * (e.g. a query defined in orm.xml overrides one defined in java).
	 */
	boolean overrides(Query query);
	
	/**
	 * Return whether the query is a duplicate of the given query.
	 * A query is not a duplicate of another query if is the same exact query,
	 * if it is a nameless query (which is an error condition), or if it overrides 
	 * or is overridden by the other query. 
	 */
	boolean duplicates(Query query);
}
