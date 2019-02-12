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

import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.reference.ModifiableIntReference;

/**
 * Implement some of the methods in {@link ModifiableIntReference} that can
 * be defined in terms of the other methods.
 * Subclasses need only implement<ul>
 * <li>{@link #getValue()}
 * <li>{@link #setValue(int)}
 * </ul>
 */
public abstract class AbstractModifiableIntReference
	extends AbstractIntReference
	implements ModifiableIntReference
{
	protected AbstractModifiableIntReference() {
		super();
	}

	public int setZero() {
		return this.setValue(0);
	}

	public int increment() {
		int v = this.getValue();
		v++;
		this.setValue(v);
		return v;
	}

	public int incrementExact() {
		int v = this.getValue();
		v = Math.incrementExact(v);
		this.setValue(v);
		return v;
	}

	public int decrement() {
		int v = this.getValue();
		v--;
		this.setValue(v);
		return v;
	}

	public int decrementExact() {
		int v = this.getValue();
		v = Math.decrementExact(v);
		this.setValue(v);
		return v;
	}

	public int halve() {
		int v = this.getValue();
		v = BitTools.half(v);
		this.setValue(v);
		return v;
	}

	public int twice() {
		int v = this.getValue();
		v = BitTools.twice(v);
		this.setValue(v);
		return v;
	}

	public int twiceExact() {
		int v = this.getValue();
		v = Math.multiplyExact(v, 2);
		this.setValue(v);
		return v;
	}

	public int abs() {
		int v = this.getValue();
		v = Math.abs(v);
		this.setValue(v);
		return v;
	}

	public int negate() {
		int v = this.getValue();
		v = -v;
		this.setValue(v);
		return v;
	}

	public int negateExact() {
		int v = this.getValue();
		v = Math.negateExact(v);
		this.setValue(v);
		return v;
	}

	public int add(int i) {
		int v = this.getValue();
		v += i;
		this.setValue(v);
		return v;
	}

	public int addExact(int i) {
		int v = this.getValue();
		v = Math.addExact(v, i);
		this.setValue(v);
		return v;
	}

	public int subtract(int i) {
		int v = this.getValue();
		v -= i;
		this.setValue(v);
		return v;
	}

	public int subtractExact(int i) {
		int v = this.getValue();
		v = Math.subtractExact(v, i);
		this.setValue(v);
		return v;
	}

	public int multiply(int i) {
		int v = this.getValue();
		v = v * i;
		this.setValue(v);
		return v;
	}

	public int multiplyExact(int i) {
		int v = this.getValue();
		v = Math.multiplyExact(v, i);
		this.setValue(v);
		return v;
	}

	public int divide(int i) {
		int v = this.getValue();
		v = v / i;
		this.setValue(v);
		return v;
	}

	public int floorDivide(int i) {
		int v = this.getValue();
		v = Math.floorDiv(v, i);
		this.setValue(v);
		return v;
	}

	public int remainder(int i) {
		int v = this.getValue();
		v = v % i;
		this.setValue(v);
		return v;
	}

	public int floorRemainder(int i) {
		int v = this.getValue();
		v = Math.floorMod(v, i);
		this.setValue(v);
		return v;
	}

	public int min(int i) {
		int v = this.getValue();
		v = Math.min(v, i);
		this.setValue(v);
		return v;
	}

	public int max(int i) {
		int v = this.getValue();
		v = Math.max(v, i);
		this.setValue(v);
		return v;
	}

	public boolean commit(int newValue, int expectedValue) {
		if (this.getValue() == expectedValue) {
			this.setValue(newValue);
			return true;
		}
		return false;
	}

	public int swap(ModifiableIntReference other) {
		if (other == this) {
			return this.getValue();
		}
		int otherValue = other.getValue();
		if (otherValue == this.getValue()) {
			return this.getValue();
		}
		other.setValue(this.getValue());
		this.setValue(otherValue);
		return otherValue;
	}
}
