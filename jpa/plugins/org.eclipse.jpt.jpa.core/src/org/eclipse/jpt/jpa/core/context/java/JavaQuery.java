/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.orm.OrmQueryContainer;
import org.eclipse.jpt.jpa.core.resource.java.QueryAnnotation;

/**
 * Java named and named native queries
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 2.0
 */
public interface JavaQuery
	extends Query
{
	QueryAnnotation getQueryAnnotation();


	// ********** hints **********

	ListIterable<JavaQueryHint> getHints();

	JavaQueryHint addHint();

	JavaQueryHint addHint(int index);


	// ********** metadata conversion *********
	
	/**
	 * Add the appropriate mapping file query to the specified query
	 * container and convert it from this query.
	 */
	void convertTo(OrmQueryContainer queryContainer);

	/**
	 * Remove the query from its parent.
	 */
	void delete();
}
