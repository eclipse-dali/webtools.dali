/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_1.context;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.context.NamedStoredProcedureQuery2_1;

/**
 * Container for named stored procedure queries.
 * Used by entities and the <code>orm.xml</code>
 * </code>entity-mappings</code> element. 
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface QueryContainer2_1
	extends QueryContainer
{

	// ********** named stored procedure queries **********

	/**
	 * Return the container's named stored procedure queries.
	 */
	ListIterable<? extends NamedStoredProcedureQuery2_1> getNamedStoredProcedureQueries();

	String NAMED_STORED_PROCEDURE_QUERIES_LIST = "namedStoredProcedureQueries"; //$NON-NLS-1$

	/**
	 * Return the number of named stored procedure queries.
	 */
	int getNamedStoredProcedureQueriesSize();

	/**
	 * Add a new named stored procedure query to the container and return it.
	 */
	NamedStoredProcedureQuery2_1 addNamedStoredProcedureQuery();

	/**
	 * Add a new named stored procedure query to the container at the specified index
	 * and return it.
	 */
	NamedStoredProcedureQuery2_1 addNamedStoredProcedureQuery(int index);

	/**
	 * Remove from the container the named stored procedure query at the specified index.
	 */
	void removeNamedStoredProcedureQuery(int index);

	/**
	 * Remove the specified named stored procedure query from the container.
	 */
	void removeNamedStoredProcedureQuery(NamedStoredProcedureQuery2_1 namedStoredProcedureQuery);

	/**
	 * Move a named stored procedure query as specified.
	 */
	void moveNamedStoredProcedureQuery(int targetIndex, int sourceIndex);

}
