/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.List;
import org.eclipse.jpt.common.core.utility.TextRange;

/**
 * named query
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.0
 * @since 2.0
 */
public interface NamedQuery
	extends Query
{
	Class<NamedQuery> getQueryType();

	// ********** query **********

	String QUERY_PROPERTY = "query"; //$NON-NLS-1$

	String getQuery();

	void setQuery(String query);


	/**
	 * Returns the list of {@link TextRange} of the query property, which is either a single object
	 * if the string is not split or many objects if the JPQL query is split into many strings.
	 */
	List<TextRange> getQueryTextRanges();

}
