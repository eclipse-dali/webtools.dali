/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.ListIterator;

/**
 * Container for named queries and/or named native queries.
 * Used by entities and the <code>orm.xml</code>
 * </code>entity-mappings</code> element. 
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface QueryContainer
	extends JpaContextNode
{
	// ********** named queries **********

	/**
	 * Return the container's named queries.
	 */
	<T extends NamedQuery> ListIterator<T> namedQueries();

	String NAMED_QUERIES_LIST = "namedQueries"; //$NON-NLS-1$

	/**
	 * Return the number of named queries.
	 */
	int namedQueriesSize();

	/**
	 * Add a new named query to the container and return it.
	 */
	NamedQuery addNamedQuery();

	/**
	 * Add a new named query to the container at the specified index
	 * and return it.
	 */
	NamedQuery addNamedQuery(int index);

	/**
	 * Remove from the container the named query at the specified index.
	 */
	void removeNamedQuery(int index);

	/**
	 * Remove the specified named query from the container.
	 */
	void removeNamedQuery(NamedQuery namedQuery);

	/**
	 * Move a named query as specified.
	 */
	void moveNamedQuery(int targetIndex, int sourceIndex);


	// ********** named native queries **********

	/**
	 * Return the container's named native queries.
	 */
	<T extends NamedNativeQuery> ListIterator<T> namedNativeQueries();

	String NAMED_NATIVE_QUERIES_LIST = "namedNativeQueries"; //$NON-NLS-1$

	/**
	 * Return the number of named native queries.
	 */
	int namedNativeQueriesSize();

	/**
	 * Add a new named native query to the container and return it.
	 */
	NamedNativeQuery addNamedNativeQuery();

	/**
	 * Add a new named native query to the container at the specified index
	 * and return it.
	 */
	NamedNativeQuery addNamedNativeQuery(int index);

	/**
	 * Remove from the container the named native query at the specified index.
	 */
	void removeNamedNativeQuery(int index);

	/**
	 * Remove the specified named native query from the container.
	 */
	void removeNamedNativeQuery(NamedNativeQuery namedNativeQuery);

	/**
	 * Move a named native query as specified.
	 */
	void moveNamedNativeQuery(int targetIndex, int sourceIndex);

}
