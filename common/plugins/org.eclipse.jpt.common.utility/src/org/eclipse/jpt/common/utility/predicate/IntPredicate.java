/*******************************************************************************
 * Copyright (c) 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.predicate;

/**
 * A predicate can be used to determine whether an <code>int</code> belongs
 * to a particular set or has a particular property.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jpt.common.utility.internal.predicate.int_.IntPredicateTools
 */
public interface IntPredicate {
	/**
	 * Return whether the specified variable is a member of the set defined by
	 * the predicate. The semantics of the set is determined by the
	 * contract between the client and the server.
	 */
	boolean evaluate(int variable);
}
