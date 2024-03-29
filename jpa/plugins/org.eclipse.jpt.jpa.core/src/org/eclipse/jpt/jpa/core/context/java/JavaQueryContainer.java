/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.QueryContainer;

/**
 * Java query container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 2.3
 * @since 2.3
 */
public interface JavaQueryContainer
	extends QueryContainer
{
	// ********** named queries **********

	ListIterable<JavaNamedQuery> getNamedQueries();

	JavaNamedQuery addNamedQuery();

	JavaNamedQuery addNamedQuery(int index);


	// ********** named native queries **********

	ListIterable<JavaNamedNativeQuery> getNamedNativeQueries();

	JavaNamedNativeQuery addNamedNativeQuery();

	JavaNamedNativeQuery addNamedNativeQuery(int index);

	// ********** parent adapter interface **********

	/**
	 * Parent adapter
	 */
	interface Parent
		extends JpaContextModel
	{
		/**
		 * Return the element that is annotated with queries.
		 */
		JavaResourceAnnotatedElement getResourceAnnotatedElement();
	}
}
