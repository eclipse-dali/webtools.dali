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

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * This predicate evaluates to the (<code>boolean</code>) value one of the
 * variable's fields.
 * <p>
 * <strong>NB:</strong> The actual field is determined at execution time,
 * not construction time. As a result, the transformer can be used to emulate
 * "duck typing".
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 */
public class FieldPredicate<V>
	implements Predicate<V>
{
	private final String fieldName;


	/**
	 * Construct a predicate that evaluates to the (<code>boolean</code>) value
	 * of the specified field.
	 */
	public FieldPredicate(String fieldName) {
		super();
		if (fieldName == null) {
			throw new NullPointerException();
		}
		this.fieldName = fieldName;
	}

	public boolean evaluate(V variable) {
		return this.evaluate_(variable).booleanValue();
	}

	private Boolean evaluate_(V variable) {
		return (Boolean) ObjectTools.get(variable, this.fieldName);
	}

	public String getFieldName() {
		return this.fieldName;
	}

	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof FieldPredicate<?>)) {
			return false;
		}
		FieldPredicate<?> other = (FieldPredicate<?>) o;
		return ObjectTools.equals(this.fieldName, other.fieldName);
	}

	@Override
	public int hashCode() {
		return this.fieldName.hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.fieldName);
	}
}
