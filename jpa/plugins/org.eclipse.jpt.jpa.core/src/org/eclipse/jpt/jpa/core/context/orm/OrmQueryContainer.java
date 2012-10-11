/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.QueryContainer;

/**
 * <code>orm.xml</code> query container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.3
 */
public interface OrmQueryContainer
	extends QueryContainer
{
	// ********** named queries **********

	ListIterable<OrmNamedQuery> getNamedQueries();

	OrmNamedQuery addNamedQuery();

	OrmNamedQuery addNamedQuery(int index);


	// ********** named native queries **********

	ListIterable<OrmNamedNativeQuery> getNamedNativeQueries();

	OrmNamedNativeQuery addNamedNativeQuery();

	OrmNamedNativeQuery addNamedNativeQuery(int index);

}
