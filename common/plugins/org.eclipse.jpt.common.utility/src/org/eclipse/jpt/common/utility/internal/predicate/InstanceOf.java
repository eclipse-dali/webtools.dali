/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
	extends CriterionPredicate<V, Class<?>>
{
	public InstanceOf(Class<?> clazz) {
		super(clazz);
		if (clazz == null) {
			throw new NullPointerException();
		}
	}

	public boolean evaluate(V variable) {
		return this.criterion.isInstance(variable);
	}
}
