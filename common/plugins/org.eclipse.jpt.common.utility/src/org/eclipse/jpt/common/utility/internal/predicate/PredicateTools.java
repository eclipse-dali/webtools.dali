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

import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Predicate} utility methods.
 */
public final class PredicateTools {


	// ********** simple predicates **********

	/**
	 * Return a predicate that will always evaluate to <code>true</code>.
	 */
	public static <V> Predicate<V> truePredicate() {
		return TruePredicate.instance();
	}

	/**
	 * Return a predicate that will always evaluate to <code>false</code>.
	 */
	public static <V> Predicate<V> falsePredicate() {
		return FalsePredicate.instance();
	}

	/**
	 * Return a predicate that will evaluate whether an object is
	 * <em>not</em> <code>null</code>.
	 */
	public static <V> Predicate<V> notNullPredicate() {
		return NotNullPredicate.instance();
	}

	/**
	 * Return a predicate that will evaluate whether an object is
	 * <code>null</code>.
	 */
	public static <V> Predicate<V> nullPredicate() {
		return NullPredicate.instance();
	}

	/**
	 * Return a predicate that will throw an exception when it is
	 * evaluated.
	 */
	public static <V> Predicate<V> disabledPredicate() {
		return DisabledPredicate.instance();
	}


	// ********** criterion predicates **********

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any object {@link Object#equals(Object) equal to} the specified
	 * criterion. If the criterion is <code>null</code>, the predicate
	 * will evaluate to <code>true</code> if the variable is also
	 * <code>null</code>.
	 */
	public static <V> EqualPredicate<V> equalPredicate(V criterion) {
		return new EqualPredicate<V>(criterion);
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code>
	 * for any object identical to (<code>==</code>) the specified
	 * criterion. If the criterion is <code>null</code>, the predicate
	 * will evaluate to <code>true</code> if the variable is also
	 * <code>null</code>.
	 */
	public static <V> IdentityPredicate<V> identityPredicate(V criterion) {
		return new IdentityPredicate<V>(criterion);
	}


	// ********** compound predicates **********

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 */
	@SuppressWarnings("unchecked")
	public static <V> ANDPredicate<V> and(Iterable<Predicate<? super V>> predicates) {
		return and((Predicate<? super V>[]) IterableTools.toArray(predicates));
	}

	/**
	 * Return a predicate that will AND the results of the specified predicates.
	 */
	public static <V> ANDPredicate<V> and(Predicate<? super V>... predicates) {
		return new ANDPredicate<V>(predicates);
	}

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 */
	@SuppressWarnings("unchecked")
	public static <V> ORPredicate<V> or(Iterable<Predicate<? super V>> predicates) {
		return or((Predicate<? super V>[]) IterableTools.toArray(predicates));
	}

	/**
	 * Return a predicate that will OR the results of the specified predicates.
	 */
	public static <V> ORPredicate<V> or(Predicate<? super V>... predicates) {
		return new ORPredicate<V>(predicates);
	}

	/**
	 * Return a predicate that will XOR the results of the specified predicates.
	 */
	public static <V> XORPredicate<V> xor(Predicate<? super V> predicate1, Predicate<? super V> predicate2) {
		return new XORPredicate<V>(predicate1, predicate2);
	}

	/**
	 * Return a predicate that will NAND the results of the specified predicates.
	 */
	@SuppressWarnings("unchecked")
	public static <V> NOTPredicate<V> nand(Iterable<Predicate<? super V>> predicates) {
		return nand((Predicate<? super V>[]) IterableTools.toArray(predicates));
	}

	/**
	 * Return a predicate that will NAND the results of the specified predicates.
	 */
	public static <V> NOTPredicate<V> nand(Predicate<? super V>... predicates) {
		return not(and(predicates));
	}

	/**
	 * Return a predicate that will NOR the results of the specified predicates.
	 */
	@SuppressWarnings("unchecked")
	public static <V> NOTPredicate<V> nor(Iterable<Predicate<? super V>> predicates) {
		return nor((Predicate<? super V>[]) IterableTools.toArray(predicates));
	}

	/**
	 * Return a predicate that will NOR the results of the specified predicates.
	 */
	public static <V> NOTPredicate<V> nor(Predicate<? super V>... predicates) {
		return not(or(predicates));
	}

	/**
	 * Return a predicate that will XNOR the results of the specified predicates.
	 */
	public static <V> NOTPredicate<V> xnor(Predicate<? super V> predicate1, Predicate<? super V> predicate2) {
		return not(xor(predicate1, predicate2));
	}


	// ********** predicate wrappers **********

	/**
	 * Return a predicate that will return the NOT of the value returned
	 * by the specified predicate.
	 */
	public static <V> NOTPredicate<V> not(Predicate<? super V> predicate) {
		return new NOTPredicate<V>(predicate);
	}

	/**
	 * Return a predicate that wraps the specified predicate, allowing the
	 * wrapped predicate to be changed as necessary.
	 */
	public static <V> PredicateWrapper<V> wrap(Predicate<? super V> predicate) {
		return new PredicateWrapper<V>(predicate);
	}

	/**
	 * Return a predicate that wraps the specified predicate, using the
	 * specified transformer to transform a variable before evaluating it
	 * with the wrapped predicate.
	 */
	public static <I, O> TransformationPredicate<I, O> wrap(Predicate<? super O> predicate, Transformer<? super I, O> transformer) {
		return new TransformationPredicate<I, O>(predicate, transformer);
	}


	// ********** instanceof predicate **********

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object that
	 * is non-<code>null</code> and an instance of the specified class.
	 */
	public static <V> InstanceOfPredicate<V> instanceOfPredicate(Class<? extends V> clazz) {
		return new InstanceOfPredicate<V>(clazz);
	}


	// ********** transformer predicate **********

	/**
	 * Return a predicate that will convert the specified transformer's output
	 * value to a predicate evaluation result (i.e. <code>true</code> or
	 * <code>false</code>). If the transformer's output is <code>null</code>,
	 * the predicate will evaluate to <code>false</code>.
	 */
	public static <V> TransformerPredicate<V> transformerPredicate(Transformer<V, Boolean> transformer) {
		return transformerPredicate(transformer, false);
	}

	/**
	 * Return a predicate that will convert the specified transformer's output
	 * value to a predicate evaluation result (i.e. <code>true</code> or
	 * <code>false</code>). If the transformer's output is <code>null</code>,
	 * the predicate will evaluate to the specified null value.
	 */
	public static <V> TransformerPredicate<V> transformerPredicate(Transformer<V, Boolean> transformer, boolean nullValue) {
		return new TransformerPredicate<V>(transformer, nullValue);
	}


	// ********** stateful predicates **********

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object that
	 * has not been previously evaluated by the predicate (as determined by
	 * {@link Object#equals(Object) equality}).
	 */
	public static <V> UniquePredicate<V> uniquePredicate() {
		return new UniquePredicate<V>();
	}

	/**
	 * Return a predicate that will evaluate to <code>true</code> for any object that
	 * has not been previously evaluated by the predicate (as determined by
	 * identity <code>==</code>).
	 */
	public static <V> UniqueIdentityPredicate<V> uniqueIdentityPredicate() {
		return new UniqueIdentityPredicate<V>();
	}


	// ********** reflection predicates **********

	/**
	 * Return a predicate that evaluates to the (<code>boolean</code>) value
	 * one of the variable's fields.
	 */
	public static <V> FieldPredicate<V> fieldPredicate(String fieldName) {
		return new FieldPredicate<V>(fieldName);
	}

	/**
	 * Return a predicate that evaluates to the (<code>boolean</code>) value
	 * one of the variable's methods.
	 */
	public static <V> MethodPredicate<V> methodPredicate(String methodName) {
		return methodPredicate(methodName, ClassTools.EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Return a predicate that evaluates to the (<code>boolean</code>) value
	 * one of the variable's methods.
	 */
	public static <V> MethodPredicate<V> methodPredicate(String methodName, Class<?> parameterType, Object argument) {
		return methodPredicate(methodName, new Class<?>[] { parameterType }, new Object[] { argument });
	}

	/**
	 * Return a predicate that evaluates to the (<code>boolean</code>) value
	 * one of the variable's methods.
	 */
	public static <V> MethodPredicate<V> methodPredicate(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		return new MethodPredicate<V>(methodName, parameterTypes, arguments);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private PredicateTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
