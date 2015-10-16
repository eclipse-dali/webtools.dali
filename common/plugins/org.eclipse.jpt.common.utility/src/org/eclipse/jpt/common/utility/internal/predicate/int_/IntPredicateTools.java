/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate.int_;

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.predicate.CompoundIntPredicate;
import org.eclipse.jpt.common.utility.predicate.IntPredicate;

/**
 * {@link IntPredicate} utility methods.
 */
public class IntPredicateTools {


	// ********** simple **********

	/**
	 * Return a predicate that will always evaluate to <code>true</code>.
	 */
	public static IntPredicate true_() {
		return True.instance();
	}

	/**
	 * Return a predicate that will always evaluate to <code>false</code>.
	 */
	public static IntPredicate false_() {
		return False.instance();
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * if the variable is even.
	 */
	public static IntPredicate isEven() {
		return IsEven.instance();
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * if the variable is odd.
	 */
	public static IntPredicate isOdd() {
		return IsOdd.instance();
	}

	/**
	 * Return a predicate that will throw an exception when it is
	 * evaluated.
	 */
	public static IntPredicate disabledIntPredicate() {
		return DisabledIntPredicate.instance();
	}


	// ********** criterion **********

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable equal to the specified
	 * criterion.
	 */
	public static IntPredicate isEqual(int criterion) {
		return new Equals(criterion);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable not equal to the specified
	 * criterion.
	 */
	public static IntPredicate notEqual(int criterion) {
		return new NotEqual(criterion);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable greater than the specified criterion.
	 */
	public static IntPredicate isGreaterThan(int criterion) {
		return new IsGreaterThan(criterion);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable greater than or equal to the specified criterion.
	 */
	public static IntPredicate isGreaterThanOrEqual(int criterion) {
		return new IsGreaterThanOrEqual(criterion);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable less than the specified criterion.
	 */
	public static IntPredicate isLessThan(int criterion) {
		return new IsLessThan(criterion);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable less than or equal to the specified criterion.
	 */
	public static IntPredicate isLessThanOrEqual(int criterion) {
		return new IsLessThanOrEqual(criterion);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable that has set the specified flag.
	 */
	public static IntPredicate flagIsSet(int flag) {
		return new FlagIsSet(flag);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable that has cleared the specified flag.
	 */
	public static IntPredicate flagIsOff(int flag) {
		return new FlagIsOff(flag);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable that has set <em>only</em> the specified flag.
	 */
	public static IntPredicate onlyFlagIsSet(int flag) {
		return new OnlyFlagIsSet(flag);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable that has cleared <em>only</em> the specified flag.
	 */
	public static IntPredicate onlyFlagIsOff(int flag) {
		return new OnlyFlagIsOff(flag);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable that has set <em>any</em> of the specified flags.
	 */
	public static IntPredicate anyFlagsAreSet(int flags) {
		return new AnyFlagsAreSet(flags);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any variable that has cleared <em>any</em> of the specified flags.
	 */
	public static IntPredicate anyFlagsAreOff(int flags) {
		return new AnyFlagsAreOff(flags);
	}


	// ********** AND **********

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 */
	public static CompoundIntPredicate and(Iterable<IntPredicate> predicates) {
		return and(predicates.iterator());
	}

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 */
	public static CompoundIntPredicate and(Iterator<IntPredicate> predicates) {
		return and(IteratorTools.toArray(predicates, EMPTY_ARRAY));
	}

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 */
	public static CompoundIntPredicate and(IntPredicate... predicates) {
		return new AND(predicates);
	}


	// ********** OR **********

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 */
	public static CompoundIntPredicate or(Iterable<IntPredicate> predicates) {
		return or(predicates.iterator());
	}

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 */
	public static CompoundIntPredicate or(Iterator<IntPredicate> predicates) {
		return or(IteratorTools.toArray(predicates, EMPTY_ARRAY));
	}

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 */
	public static CompoundIntPredicate or(IntPredicate... predicates) {
		return new OR(predicates);
	}


	// ********** XOR **********

	/**
	 * Return a predicate that will XOR the results of the specified predicates.
	 */
	public static CompoundIntPredicate xor(IntPredicate predicate1, IntPredicate predicate2) {
		return new XOR(predicate1, predicate2);
	}


	// ********** NAND **********

	/**
	 * Return a predicate that will NAND the results of the specified predicates.
	 */
	public static IntPredicate nand(Iterable<IntPredicate> predicates) {
		return nand(predicates.iterator());
	}

	/**
	 * Return a predicate that will NAND the results of the specified predicates.
	 */
	public static IntPredicate nand(Iterator<IntPredicate> predicates) {
		return nand(IteratorTools.toArray(predicates, EMPTY_ARRAY));
	}

	/**
	 * Return a predicate that will NAND the results of the specified predicates.
	 */
	public static IntPredicate nand(IntPredicate... predicates) {
		return not(and(predicates));
	}


	// ********** NOR **********

	/**
	 * Return a predicate that will NOR the results of the specified predicates.
	 */
	public static IntPredicate nor(Iterable<IntPredicate> predicates) {
		return nor(predicates.iterator());
	}

	/**
	 * Return a predicate that will NOR the results of the specified predicates.
	 */
	public static IntPredicate nor(Iterator<IntPredicate> predicates) {
		return nor(IteratorTools.toArray(predicates, EMPTY_ARRAY));
	}

	/**
	 * Return a predicate that will NOR the results of the specified predicates.
	 */
	public static IntPredicate nor(IntPredicate... predicates) {
		return not(or(predicates));
	}


	// ********** XNOR **********

	/**
	 * Return a predicate that will XNOR the results of the specified predicates.
	 */
	public static IntPredicate xnor(IntPredicate predicate1, IntPredicate predicate2) {
		return not(xor(predicate1, predicate2));
	}


	// ********** NOT **********

	/**
	 * Return a predicate that will return the NOT of the value returned
	 * by the specified predicate.
	 */
	public static IntPredicate not(IntPredicate predicate) {
		return new NOT(predicate);
	}


	// ********** wrappers **********

	/**
	 * Return a predicate that wraps the specified predicate, allowing the
	 * wrapped predicate to be changed as necessary.
	 */
	public static IntPredicateWrapper wrap(IntPredicate predicate) {
		return new IntPredicateWrapper(predicate);
	}


	// ********** empty array **********

	public static IntPredicate[] emptyArray() {
		return EMPTY_ARRAY;
	}

	public static final IntPredicate[] EMPTY_ARRAY = new IntPredicate[0];


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private IntPredicateTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
