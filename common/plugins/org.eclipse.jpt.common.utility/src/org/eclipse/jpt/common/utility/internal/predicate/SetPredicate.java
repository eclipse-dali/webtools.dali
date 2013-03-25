/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import java.util.Set;

/**
 * A predicate that will return whether the variable is in a client-configured
 * set.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 */
public class SetPredicate<V>
	extends CriterionPredicate<V, Set<? super V>>
{
	public SetPredicate(Set<? super V> set) {
		super(set);
	}

	public boolean evaluate(V variable) {
		return this.criterion.contains(variable);
	}
}
