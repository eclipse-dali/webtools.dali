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

import java.util.Collection;
import java.util.IdentityHashMap;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * This predicate evaluates to <code>true</code> if the variable (as determined
 * by identity <code>==</code>) has not previously been evaluated by the
 * predicate.
 * <p>
 * <strong>NB:</strong> Maybe it's obvious, but this predicate's behavior
 * will change over time as variables are evaluated.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 */
public class UniqueIdentityPredicate<V>
	implements Predicate<V>
{
	private final IdentityHashMap<V, Object> map = new IdentityHashMap<V, Object>();
	private static final Object PRESENT = new Object();


	public UniqueIdentityPredicate() {
		super();
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
		return this.map.put(variable, PRESENT) == null;
	}

	/**
	 * Add the specified variables to the predicate. Once added, any later
	 * evaluation of any of the specified variables will return
	 * <code>false</code>.
	 * Return whether the predicate has <em>not</em> already added or
	 * {@link #evaluate(Object) evaluated} any of the specified variables.
	 */
	public boolean addAll(Collection<? extends V> variables) {
		boolean modified = false;
		for (V variable : variables) {
			if (this.add(variable)) {
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.map);
	}
}
