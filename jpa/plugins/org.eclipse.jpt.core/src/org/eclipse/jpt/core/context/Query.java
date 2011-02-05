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

import java.util.Arrays;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.orm.OrmQuery;

/**
 * named and named native queries
 * <p>
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
	extends JpaNamedContextNode<Query>
{
	/**
	 * TODO use this precedence until adopters protest...
	 * then move to JpaPlatform
	 */
	@SuppressWarnings("unchecked")
	Iterable<Class<? extends Query>> PRECEDENCE_TYPE_LIST = Arrays.asList(
			OrmQuery.class,
			JavaQuery.class
		);


	// ********** query **********

	String QUERY_PROPERTY = "query"; //$NON-NLS-1$

	String getQuery();

	void setQuery(String query);


	// ********** hints **********

	String HINTS_LIST = "hints"; //$NON-NLS-1$

	/**
	 * Return the query's hints.
	 */
	<T extends QueryHint> ListIterable<T> getHints();

	/**
	 * Return the number of hints.
	 */
	int getHintsSize();

	/**
	 * Add a hint to the query and return the object representing it.
	 */
	QueryHint addHint();

	/**
	 * Add a hint to the query and return the object representing it.
	 */
	QueryHint addHint(int index);

	/**
	 * Remove the hint at the index from the query.
	 */
	void removeHint(QueryHint queryHint);

	/**
	 * Remove the hint from the query.
	 */
	void removeHint(int index);

	/**
	 * Move the hint from the source index to the target index.
	 */
	void moveHint(int targetIndex, int sourceIndex);

}
