/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
 */
public interface QueryHolder extends JpaContextNode
{
	/**
	 * Return a list iterator of the named queries.
	 * This will not be null.
	 */
	<T extends NamedQuery> ListIterator<T> namedQueries();

	/**
	 * Return the number of named queries.
	 */
	int namedQueriesSize();

	/**
	 * Add a named query to the entity return the object representing it.
	 */
	NamedQuery addNamedQuery(int index);

	/**
	 * Remove the named query at the index from the entity.
	 */
	void removeNamedQuery(int index);

	/**
	 * Remove the named query at from the entity.
	 */
	void removeNamedQuery(NamedQuery namedQuery);

	/**
	 * Move the named query from the source index to the target index.
	 */
	void moveNamedQuery(int targetIndex, int sourceIndex);

	String NAMED_QUERIES_LIST = "namedQueriesList";

	/**
	 * Return a list iterator of the specified named native queries.
	 * This will not be null.
	 */
	<T extends NamedNativeQuery> ListIterator<T> namedNativeQueries();

	/**
	 * Return the number of named native queries.
	 */
	int namedNativeQueriesSize();

	/**
	 * Add a named native query to the entity return the object representing it.
	 */
	NamedNativeQuery addNamedNativeQuery(int index);

	/**
	 * Remove the named native query at the index from the entity.
	 */
	void removeNamedNativeQuery(int index);

	/**
	 * Remove the named native query at from the entity.
	 */
	void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery);

	/**
	 * Move the named native query from the source index to the target index.
	 */
	void moveNamedNativeQuery(int targetIndex, int sourceIndex);

	String NAMED_NATIVE_QUERIES_LIST = "namedNativeQueriesList";
}