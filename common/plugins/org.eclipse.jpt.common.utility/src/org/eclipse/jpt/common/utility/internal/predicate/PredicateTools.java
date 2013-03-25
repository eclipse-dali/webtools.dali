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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.predicate.CompoundPredicate;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Predicate} utility methods.
 */
public final class PredicateTools {


	// ********** simple **********

	/**
	 * Return a predicate that will always evaluate to <code>true</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> true_() {
		return True.instance();
	}

	/**
	 * Return a predicate that will always evaluate to <code>false</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> false_() {
		return False.instance();
	}

	/**
	 * Return a predicate that will evaluate whether an object is
	 * <em>not</em> <code>null</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isNotNull() {
		return IsNotNull.instance();
	}

	/**
	 * Return a predicate that will evaluate whether an object is
	 * <code>null</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isNull() {
		return IsNull.instance();
	}

	/**
	 * Return a predicate that will throw an exception when it is
	 * evaluated.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> disabledPredicate() {
		return DisabledPredicate.instance();
	}


	// ********** criterion **********

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any object {@link Object#equals(Object) equal to} the specified
	 * criterion. If the criterion is <code>null</code>, the predicate
	 * will evaluate to <code>true</code> if the variable is also
	 * <code>null</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isEqual(V criterion) {
		return new Equals<V>(criterion);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any object identical to (<code>==</code>) the specified
	 * criterion. If the criterion is <code>null</code>, the predicate
	 * will evaluate to <code>true</code> if the variable is also
	 * <code>null</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isIdentical(V criterion) {
		return new IsIdentical<V>(criterion);
	}


	// ********** AND **********

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> CompoundPredicate<V> and(Iterable<Predicate<? super V>> predicates) {
		return and(predicates.iterator());
	}

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	@SuppressWarnings("unchecked")
	public static <V> CompoundPredicate<V> and(Iterator<Predicate<? super V>> predicates) {
		return and(IteratorTools.toArray(predicates, EMPTY_ARRAY));
	}

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> CompoundPredicate<V> and(Predicate<? super V>... predicates) {
		return new AND<V>(predicates);
	}


	// ********** OR **********

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> CompoundPredicate<V> or(Iterable<Predicate<? super V>> predicates) {
		return or(predicates.iterator());
	}

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	@SuppressWarnings("unchecked")
	public static <V> CompoundPredicate<V> or(Iterator<Predicate<? super V>> predicates) {
		return or(IteratorTools.toArray(predicates, EMPTY_ARRAY));
	}

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> CompoundPredicate<V> or(Predicate<? super V>... predicates) {
		return new OR<V>(predicates);
	}


	// ********** XOR **********

	/**
	 * Return a predicate that will XOR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> CompoundPredicate<V> xor(Predicate<? super V> predicate1, Predicate<? super V> predicate2) {
		return new XOR<V>(predicate1, predicate2);
	}


	// ********** NAND **********

	/**
	 * Return a predicate that will NAND the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> nand(Iterable<Predicate<? super V>> predicates) {
		return nand(predicates.iterator());
	}

	/**
	 * Return a predicate that will NAND the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	@SuppressWarnings("unchecked")
	public static <V> Predicate<V> nand(Iterator<Predicate<? super V>> predicates) {
		return nand(IteratorTools.toArray(predicates, EMPTY_ARRAY));
	}

	/**
	 * Return a predicate that will NAND the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> nand(Predicate<? super V>... predicates) {
		return not(and(predicates));
	}


	// ********** NOR **********

	/**
	 * Return a predicate that will NOR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> nor(Iterable<Predicate<? super V>> predicates) {
		return nor(predicates.iterator());
	}

	/**
	 * Return a predicate that will NOR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	@SuppressWarnings("unchecked")
	public static <V> Predicate<V> nor(Iterator<Predicate<? super V>> predicates) {
		return nor(IteratorTools.toArray(predicates, EMPTY_ARRAY));
	}

	/**
	 * Return a predicate that will NOR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> nor(Predicate<? super V>... predicates) {
		return not(or(predicates));
	}


	// ********** XNOR **********

	/**
	 * Return a predicate that will XNOR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> xnor(Predicate<? super V> predicate1, Predicate<? super V> predicate2) {
		return not(xor(predicate1, predicate2));
	}


	// ********** NOT **********

	/**
	 * Return a predicate that will return the NOT of the value returned
	 * by the specified predicate.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> not(Predicate<? super V> predicate) {
		return new NOT<V>(predicate);
	}


	// ********** wrappers **********

	/**
	 * Return a predicate that wraps the specified predicate, allowing the
	 * wrapped predicate to be changed as necessary.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> PredicateWrapper<V> wrap(Predicate<? super V> predicate) {
		return new PredicateWrapper<V>(predicate);
	}

	/**
	 * Return a predicate that wraps the specified predicate, using the
	 * specified transformer to transform a variable before evaluating it
	 * with the wrapped predicate.
	 * @param <I> the type of objects to be evaluated by the predicate (i.e.
	 *   passed to its transformer before being forwarded to the wrapped predicate)
	 * @param <O> the type of objects output by the transformer and to be
	 *   evaluated by the wrapped predicate
	 */
	public static <I, O> Predicate<I> wrap(Predicate<? super O> predicate, Transformer<? super I, O> transformer) {
		return new TransformationPredicate<I, O>(predicate, transformer);
	}


	// ********** instanceof **********

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object that
	 * is non-<code>null</code> and an instance of the specified class.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> instanceOf(Class<? extends V> clazz) {
		return new InstanceOf<V>(clazz);
	}


	// ********** transformer **********

	/**
	 * Return a predicate that will convert the specified transformer's output
	 * value to a predicate evaluation result (i.e. <code>true</code> or
	 * <code>false</code>). If the transformer's output is <code>null</code>,
	 * the predicate will evaluate to <code>false</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> transformerPredicate(Transformer<V, Boolean> transformer) {
		return transformerPredicate(transformer, false);
	}

	/**
	 * Return a predicate that will convert the specified transformer's output
	 * value to a predicate evaluation result (i.e. <code>true</code> or
	 * <code>false</code>). If the transformer's output is <code>null</code>,
	 * the predicate will evaluate to the specified null value.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> transformerPredicate(Transformer<V, Boolean> transformer, boolean nullValue) {
		return new TransformerPredicate<V>(transformer, nullValue);
	}


	// ********** set **********

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object
	 * that is contained in the specified set.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isIn(Set<? super V> set) {
		return new SetPredicate<V>(set);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object
	 * that is <em>not</em> contained in the specified set.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isNotIn(Set<? super V> set) {
		return new ExclusionSetPredicate<V>(set);
	}


	// ********** stateful **********

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object that
	 * has not been previously evaluated by the predicate (as determined by
	 * {@link Object#equals(Object) equality}).
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
//	public static <V> UniquePredicate<V> uniqueIdentityPredicate() {
//		return uniquePredicate(new IdentityHashSet<V>());
//	}

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object that
	 * has not been previously evaluated by the predicate (as determined by
	 * {@link Object#equals(Object) equality}).
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> UniquePredicate<V> uniquePredicate() {
		return uniquePredicate(new HashSet<V>());
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object that
	 * has not been previously evaluated by the predicate (as determined by
	 * {@link Object#equals(Object) equality}).
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> UniquePredicate<V> uniquePredicate(Set<V> set) {
		return new UniquePredicate<V>(set);
	}


	// ********** reflection **********

	/**
	 * Return a predicate that evaluates to the (<code>boolean</code>) value
	 * one of the variable's fields.
	 * <p>
	 * <strong>NB:</strong> The actual field is determined at execution time,
	 * not construction time. As a result, the transformer can be used to emulate
	 * "duck typing".
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> get(String fieldName) {
		return new FieldPredicate<V>(fieldName);
	}

	/**
	 * Return a predicate that evaluates to the (<code>boolean</code>) value
	 * one of the variable's methods.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the transformer can be used to emulate
	 * "duck typing".
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> execute(String methodName) {
		return execute(methodName, ClassTools.EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Return a predicate that evaluates to the (<code>boolean</code>) value
	 * one of the variable's methods.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the transformer can be used to emulate
	 * "duck typing".
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> execute(String methodName, Class<?> parameterType, Object argument) {
		return execute(methodName, new Class<?>[] { parameterType }, new Object[] { argument });
	}

	/**
	 * Return a predicate that evaluates to the (<code>boolean</code>) value
	 * one of the variable's methods.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the transformer can be used to emulate
	 * "duck typing".
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> execute(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		return new MethodPredicate<V>(methodName, parameterTypes, arguments);
	}


	// ********** empty array **********

	@SuppressWarnings("unchecked")
	public static <V> Predicate<V>[] emptyArray() {
		return EMPTY_ARRAY;
	}

	@SuppressWarnings("rawtypes")
	public static final Predicate[] EMPTY_ARRAY = new Predicate[0];


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private PredicateTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
