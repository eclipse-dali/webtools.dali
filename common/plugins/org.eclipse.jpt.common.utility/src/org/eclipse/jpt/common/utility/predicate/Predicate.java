/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.predicate;

/**
 * A predicate can be used to determine whether an object belongs
 * to a particular set or has a particular property (e.g.
 * {@link org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable
 * when filtering a collection of objects}).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <V> the type of object (variable) to be evaluated
 * 
 * @see org.eclipse.jpt.common.utility.internal.predicate.PredicateTools
 */
public interface Predicate<V> {
	/**
	 * Return whether the specified variable is a member of the set defined by
	 * the predicate. The semantics of the set is determined by the
	 * contract between the client and the server.
	 */
	boolean evaluate(V variable);
}
