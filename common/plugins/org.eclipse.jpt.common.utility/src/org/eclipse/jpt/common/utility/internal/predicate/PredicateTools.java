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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.collection.IdentityHashSet;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
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
	 * for any object {@link Object#equals(Object) equal} to the specified
	 * criterion. If the criterion is <code>null</code>, the predicate
	 * will evaluate to <code>true</code> if the variable is also
	 * <code>null</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isEqual(V criterion) {
		return (criterion == null) ? isNull() : new Equals<>(criterion);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any object <em>not</em> {@link Object#equals(Object) equal} to the specified
	 * criterion. If the criterion is <code>null</code>, the predicate
	 * will evaluate to <code>true</code> if the variable is not
	 * <code>null</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isNotEqual(V criterion) {
		return (criterion == null) ? isNotNull() : not(isEqual(criterion));
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
		return (criterion == null) ? isNull() : new IsIdentical<>(criterion);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any object that is <em>not</em> identical (<code>==</code>) the specified
	 * criterion. If the criterion is <code>null</code>, the predicate
	 * will evaluate to <code>true</code> if the variable is not
	 * <code>null</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isNotIdentical(V criterion) {
		return (criterion == null) ? isNotNull() : not(isIdentical(criterion));
	}


	// ********** AND **********

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> and(Iterable<Predicate<? super V>> predicates) {
		return and(predicates.iterator());
	}

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	@SuppressWarnings("unchecked")
	public static <V> Predicate<V> and(Iterator<Predicate<? super V>> predicates) {
		return predicates.hasNext() ? and((Predicate<? super V>[]) IteratorTools.toArray(predicates, EMPTY_ARRAY)) : true_();
	}

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	@SafeVarargs
	public static <V> Predicate<V> and(Predicate<? super V>... predicates) {
		return (predicates.length != 0) ? new AND<>(predicates) : true_();
	}


	// ********** OR **********

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> or(Iterable<Predicate<? super V>> predicates) {
		return or(predicates.iterator());
	}

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	@SuppressWarnings("unchecked")
	public static <V> Predicate<V> or(Iterator<Predicate<? super V>> predicates) {
		return predicates.hasNext() ? or((Predicate<? super V>[]) IteratorTools.toArray(predicates, EMPTY_ARRAY)) : false_();
	}

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	@SafeVarargs
	public static <V> Predicate<V> or(Predicate<? super V>... predicates) {
		return (predicates.length != 0) ? new OR<>(predicates) : false_();
	}


	// ********** XOR **********

	/**
	 * Return a predicate that will XOR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> xor(Predicate<? super V> predicate1, Predicate<? super V> predicate2) {
		return new XOR<>(predicate1, predicate2);
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
		return predicates.hasNext() ? nand((Predicate<? super V>[]) IteratorTools.toArray(predicates, EMPTY_ARRAY)) : false_();
	}

	/**
	 * Return a predicate that will NAND the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	@SafeVarargs
	public static <V> Predicate<V> nand(Predicate<? super V>... predicates) {
		return (predicates.length != 0) ? not(and(predicates)) : false_();
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
		return predicates.hasNext() ? nor((Predicate<? super V>[]) IteratorTools.toArray(predicates, EMPTY_ARRAY)) : true_();
	}

	/**
	 * Return a predicate that will NOR the results of the specified predicates.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	@SafeVarargs
	public static <V> Predicate<V> nor(Predicate<? super V>... predicates) {
		return (predicates.length != 0) ? not(or(predicates)) : true_();
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
		return new NOT<>(predicate);
	}


	// ********** wrappers **********

	/**
	 * Return a predicate that wraps the specified predicate and checks
	 * for a <code>null</code> variable before forwarding the variable to the
	 * specified predicate. If the variable is <code>null</code>, the predicate
	 * will return <code>false</code>.
	 * @param <V> the type of the object passed to the predicate
	 * @see NullCheckPredicateWrapper
	 * @see #nullCheck(Predicate, boolean)
	 */
	public static <V> Predicate<V> nullCheck(Predicate<? super V> predicate) {
		return nullCheck(predicate, false);
	}

	/**
	 * Return a predicate that wraps the specified predicate and checks
	 * for a <code>null</code> variable before forwarding the variable to the
	 * specified predicate. If the variable is <code>null</code>, the predicate
	 * will return the specified value.
	 * @param <V> the type of the object passed to the predicate
	 * @see NullCheckPredicateWrapper
	 * @see #nullCheck(Predicate)
	 */
	public static <V> Predicate<V> nullCheck(Predicate<? super V> predicate, boolean nullValue) {
		return new NullCheckPredicateWrapper<>(predicate, nullValue);
	}

	/**
	 * Return a predicate that wraps the specified predicate, allowing the
	 * wrapped predicate to be changed as necessary.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> wrap(Predicate<? super V> predicate) {
		return new PredicateWrapper<>(predicate);
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
		return new TransformationPredicate<>(predicate, transformer);
	}


	// ********** instanceof **********

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object that
	 * is non-<code>null</code> and an instance of the specified class.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> instanceOf(Class<? extends V> clazz) {
		return new InstanceOf<>(clazz);
	}


	// ********** transformer **********

	/**
	 * Return a predicate that will convert the specified transformer's output
	 * value to a predicate evaluation result (i.e. <code>true</code> or
	 * <code>false</code>). If the transformer's output is <code>null</code>,
	 * the predicate will evaluate to <code>false</code>.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> adapt(Transformer<V, Boolean> transformer) {
		return adapt(transformer, false);
	}

	/**
	 * Return a predicate that will convert the specified transformer's output
	 * value to a predicate evaluation result (i.e. <code>true</code> or
	 * <code>false</code>). If the transformer's output is <code>null</code>,
	 * the predicate will evaluate to the specified null value.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> adapt(Transformer<V, Boolean> transformer, boolean nullValue) {
		return nullCheck(adapt_(transformer), nullValue);
	}

	/**
	 * Return a predicate that will convert the specified transformer's output
	 * value to a predicate evaluation result (i.e. <code>true</code> or
	 * <code>false</code>).
	 * <strong>NB:</strong> If the transformer's output is <code>null</code>,
	 * the predicate will throw a {@link NullPointerException}.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> adapt_(Transformer<V, Boolean> transformer) {
		return new TransformerPredicate<>(transformer);
	}


	// ********** collection **********

	/**
	 * Return a predicate that returns whether a collection is <em>not</em> empty.
	 */
	public static Predicate<Collection<?>> collectionIsNotEmptyPredicate() {
		return not(collectionIsEmptyPredicate());
	}

	/**
	 * Return a predicate that returns whether a collection is empty.
	 */
	public static Predicate<Collection<?>> collectionIsEmptyPredicate() {
		return CollectionIsEmptyPredicate.instance();
	}

	/**
	 * Return a predicate that returns whether a collection contains exactly one element.
	 */
	public static Predicate<Collection<?>> collectionContainsSingleElementPredicate() {
		return COLLECTION_CONTAINS_SINGLE_ELEMENT_PREDICATE;
	}

	/**
	 * A predicate that returns whether a collection contains exactly one element.
	 */
	public static final Predicate<Collection<?>> COLLECTION_CONTAINS_SINGLE_ELEMENT_PREDICATE = collectionSizeEqualsPredicate_(1);

	/**
	 * Return a predicate that returns whether a collection's is the specified size.
	 */
	public static Predicate<Collection<?>> collectionSizeEqualsPredicate(int size) {
		return (size == 0) ? collectionIsEmptyPredicate(): (size == 1) ? collectionContainsSingleElementPredicate() : collectionSizeEqualsPredicate_(size);
	}

	private static Predicate<Collection<?>> collectionSizeEqualsPredicate_(int size) {
		return new CollectionSizeEqualsPredicate(size);
	}


	// ********** set **********

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object
	 * that is contained in the specified set.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isIn(Set<? super V> set) {
		return new SetPredicate<>(set);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object
	 * that is <em>not</em> contained in the specified set.
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> Predicate<V> isNotIn(Set<? super V> set) {
		return new ExclusionSetPredicate<>(set);
	}


	// ********** stateful **********

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object that
	 * has not been previously evaluated by the predicate (as determined by
	 * object-identity: <code>==</code>).
	 * @param <V> the type of objects to be evaluated by the predicate
	 */
	public static <V> UniquePredicate<V> uniqueIdentityPredicate() {
		return uniquePredicate(new IdentityHashSet<V>());
	}

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
		return new UniquePredicate<>(set);
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
	public static <V> FieldPredicate<V> get(String fieldName) {
		return new FieldPredicate<>(fieldName);
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
	public static <V> MethodPredicate<V> execute(String methodName) {
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
	public static <V> MethodPredicate<V> execute(String methodName, Class<?> parameterType, Object argument) {
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
	public static <V> MethodPredicate<V> execute(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		return new MethodPredicate<>(methodName, parameterTypes, arguments);
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
