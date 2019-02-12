/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import java.util.Set;

/**
 * A predicate that will return whether the variable is <em>not</em> in a
 * client-configured set.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 */
public class ExclusionSetPredicate<V>
	extends CriterionPredicate<V, Set<? super V>>
{
	public ExclusionSetPredicate(Set<? super V> set) {
		super(set);
	}

	public boolean evaluate(V variable) {
		return ! this.criterion.contains(variable);
	}
}
