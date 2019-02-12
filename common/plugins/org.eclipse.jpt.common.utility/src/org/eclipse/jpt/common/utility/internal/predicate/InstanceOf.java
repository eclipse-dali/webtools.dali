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

/**
 * This predicate will return <code>true</code> for any object that is
 * non-<code>null</code> and an instance of the specified class.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 */
public class InstanceOf<V>
	extends CriterionPredicate<V, Class<? extends V>>
{
	public InstanceOf(Class<? extends V> clazz) {
		super(clazz);
		if (clazz == null) {
			throw new NullPointerException();
		}
	}

	public boolean evaluate(V variable) {
		return this.criterion.isInstance(variable);
	}
}
