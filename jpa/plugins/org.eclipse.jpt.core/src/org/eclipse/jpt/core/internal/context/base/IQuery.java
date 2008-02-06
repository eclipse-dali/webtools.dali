/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

import java.util.ListIterator;

public interface IQuery extends IJpaContextNode
{

	//************************ name ***********************

	String getName();
	void setName(String value);
		String NAME_PROPERTY = "nameProperty";

	//************************ query ***********************

	String getQuery();
	void setQuery(String value);
		String QUERY_PROPERTY = "queryProperty";


	//************************ hints ***********************
	/**
	 * Return a list iterator of the hints.  This will not be null.
	 */
	<T extends IQueryHint> ListIterator<T> hints();
	
	/**
	 * Return the number of hints.
	 */
	int hintsSize();
	
	/**
	 * Add a hint to the query and return the object representing it.
	 */
	IQueryHint addHint(int index);
	
	/**
	 * Remove the hint from the query.
	 */
	void removeHint(int index);
	
	/**
	 * Remove the hint at the index from the query.
	 */
	void removeHint(IQueryHint queryHint);
	
	/**
	 * Move the hint from the source index to the target index.
	 */
	void moveHint(int targetIndex, int sourceIndex);
		String HINTS_LIST = "hintsList";

}
