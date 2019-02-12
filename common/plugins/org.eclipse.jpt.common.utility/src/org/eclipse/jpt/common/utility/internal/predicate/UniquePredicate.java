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

import java.util.Collection;
import java.util.Set;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * This predicate evaluates to <code>true</code> if the variable (or its
 * {@link Object#equals(Object) equivalent}) has <em>not</em>
 * previously been evaluated by the predicate.
 * <p>
 * <strong>NB:</strong> Maybe it's obvious, but this predicate's behavior
 * will change over time as variables are evaluated.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 */
public class UniquePredicate<V>
	implements Predicate<V>
{
	private final Set<V> set;


	public UniquePredicate(Set<V> set) {
		super();
		this.set = set;
	}

	public boolean evaluate(V variable) {
		return this.add(variable);
	}

	/**
	 * Add the specified variable to the predicate. Once added, any later
	 * evaluation of the specified variable will return <code>false</code>.
	 * Return whether the predicate has <em>not</em> already added or
	 * {@link #evaluate(Object) evaluated} the specified variable.
	 */
	public boolean add(V variable) {
		return this.set.add(variable);
	}

	/**
	 * Add the specified variables to the predicate. Once added, any later
	 * evaluation of any of the specified variables will return
	 * <code>false</code>.
	 * Return whether the predicate has <em>not</em> already added or
	 * {@link #evaluate(Object) evaluated} any of the specified variables.
	 */
	public boolean addAll(Collection<? extends V> variables) {
		return this.set.addAll(variables);
	}

	/**
	 * Return the set of previously evaluated variables.
	 */
	public Set<V> getSet() {
		return this.set;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.set);
	}
}
