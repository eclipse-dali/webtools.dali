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
 * A predicate that evaulates to the result of some combination of a set of
 * other predicates (e.g. the compound predicate's value may be the result
 * of ANDing the values of its child predicates together).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see org.eclipse.jpt.common.utility.internal.predicate.int_.IntPredicateTools
 */
public interface CompoundIntPredicate
	extends IntPredicate
{
	/**
	 * Return the child predicates used to calculate the compound predicate's
	 * value (e.g. the compound predicate's value may be the result
	 * of ANDing the values of these predicates together).
	 */
	IntPredicate[] getPredicates();
}
