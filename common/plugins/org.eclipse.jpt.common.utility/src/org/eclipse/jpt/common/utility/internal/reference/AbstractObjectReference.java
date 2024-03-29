/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.reference;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.reference.ObjectReference;

/**
 * Implement some of the methods in {@link ObjectReference} that can
 * be defined in terms of the other methods.
 * Subclasses need only implement<ul>
 * <li>{@link #getValue()}
 * </ul>
 * 
 * @param <V> the type of the reference's value
 */
public abstract class AbstractObjectReference<V>
	implements ObjectReference<V>
{
	protected AbstractObjectReference() {
		super();
	}

	public boolean valueEquals(Object object) {
		return ObjectTools.equals(this.getValue(), object);
	}

	public boolean valueNotEqual(Object object) {
		return ObjectTools.notEquals(this.getValue(), object);
	}

	public boolean is(Object object) {
		return this.getValue() == object;
	}

	public boolean isNot(Object object) {
		return this.getValue() != object;
	}

	public boolean isNull() {
		return this.getValue() == null;
	}

	public boolean isNotNull() {
		return this.getValue() != null;
	}

	public boolean isMemberOf(Predicate<? super V> predicate) {
		return predicate.evaluate(this.getValue());
	}

	public boolean isNotMemberOf(Predicate<? super V> predicate) {
		return ! predicate.evaluate(this.getValue());
	}


	// ********** standard methods **********

	/**
	 * Object identity is critical to object references.
	 * There is no reason for two different object references to be
	 * <em>equal</em>.
	 * 
	 * @see #valueEquals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/**
	 * @see #equals(Object)
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public String toString() {
		return '[' + String.valueOf(this.getValue()) + ']';
	}
}
