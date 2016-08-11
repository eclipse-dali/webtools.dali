/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.closure.InterruptibleClosure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.factory.InterruptibleFactory;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.BooleanTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.internal.transformer.int_.IntObjectTransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.IntTransformer;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Transformer} utility methods.
 */
public final class TransformerTools {

	// ********** adapters **********

	/**
	 * Adapt the specified {@link IntTransformer} to the {@link Transformer} interface.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 */
	public static <I> Transformer<I, Integer> adapt(IntTransformer<? super I> intTransformer) {
		return new IntObjectTransformerAdapter<>(intTransformer);
	}

	/**
	 * Adapt the specified {@link Closure} to the {@link Transformer} interface.
	 * The returned transformer will always return <code>null</code>.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> adapt(Closure<? super I> closure) {
		ObjectTools.assertNotNull(closure);
		return input -> {
				closure.execute(input);
				return null;
			};
	}

	/**
	 * Adapt the specified {@link InterruptibleClosure} to the
	 * {@link InterruptibleTransformer} interface.
	 * The returned transformer will always return <code>null</code>.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> InterruptibleTransformer<I, O> adapt(InterruptibleClosure<? super I> closure) {
		ObjectTools.assertNotNull(closure);
		return input -> {
				closure.execute(input);
				return null;
			};
	}

	/**
	 * Adapt the specified {@link InterruptibleCommand} to the
	 * {@link InterruptibleTransformer} interface.
	 * The returned transformer will always return <code>null</code> and its
	 * input will be ignored.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> InterruptibleTransformer<I, O> adapt(InterruptibleCommand command) {
		ObjectTools.assertNotNull(command);
		return input -> {
				command.execute();
				return null;
			};
	}

	/**
	 * Adapt the specified {@link Command} to the {@link Transformer} interface.
	 * The returned transformer will always return <code>null</code> and its
	 * input will be ignored.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> adapt(Command command) {
		ObjectTools.assertNotNull(command);
		return input -> {
				command.execute();
				return null;
			};
	}

	/**
	 * Adapt the specified {@link Factory} to the {@link Transformer} interface.
	 * The returned transformer will return the factory's output and its
	 * input will be ignored.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> adapt(Factory<? extends O> factory) {
		ObjectTools.assertNotNull(factory);
		return input -> factory.create();
	}

	/**
	 * Adapt the specified {@link InterruptibleFactory} to the
	 * {@link InterruptibleTransformer} interface.
	 * The returned transformer will return the factory's output and its
	 * input will be ignored.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> InterruptibleTransformer<I, O> adapt(InterruptibleFactory<? extends O> factory) {
		ObjectTools.assertNotNull(factory);
		return input -> factory.create();
	}

	/**
	 * Adapt the specified {@link Transformer} and appropriate input
	 * to the {@link RunnableFuture} interface.
	 * Once it has {@link Runnable#run run},
	 * the returned future will return the transformer's output,
	 * as determined by the specified input.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * (and future)
	 */
	public static <I, O> FutureTask<O> runnableFuture(InterruptibleTransformer<? super I, ? extends O> transformer, I input) {
		return new FutureTask<>(callable(transformer, input));
	}

	/**
	 * Adapt the specified {@link Transformer} and appropriate input
	 * to the {@link Callable} interface.
	 * The returned callable will return the transformer's output,
	 * as determined by the specified input.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * (and callable)
	 */
	public static <I, O> Callable<O> callable(InterruptibleTransformer<? super I, ? extends O> transformer, I input) {
		ObjectTools.assertNotNull(transformer);
		return () -> transformer.transform(input);
	}


	// ********** object to string **********

	/**
	 * Return a transformer that transforms an object into
	 * the string returned by the {@link String#valueOf(Object)} method,
	 * which is slightly different than the string returned by the
	 * {@link Object#toString()} method.
	 * <p>
	 * <strong>NB:</strong> The transformer will return the string
	 * <code>"null"</code> if it is passed a <code>null</code> input.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * 
	 * @see String#valueOf(Object)
	 * @see #objectToStringTransformer()
	 * @see #objectToStringTransformer(String)
	 * @see #objectToStringTransformer_()
	 */
	public static <I> Transformer<I, String> stringFromObjectTransformer() {
		return objectToStringTransformer(String.valueOf((Object) null));
	}

	/**
	 * Return a transformer that transforms an object into the string
	 * returned by its {@link Object#toString()} method.
	 * <p>
	 * <strong>NB:</strong> The transformer will return <code>null</code>
	 * if it is passed a <code>null</code> input.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * 
	 * @see Object#toString()
	 * @see #objectToStringTransformer(String)
	 * @see #objectToStringTransformer_()
	 * @see #stringFromObjectTransformer()
	 */
	public static <I> Transformer<I, String> objectToStringTransformer() {
		return objectToStringTransformer(null);
	}

	/**
	 * Return a transformer that transforms an object into the string
	 * returned by its {@link Object#toString()} method.
	 * The transformer will return the specified string
	 * if it is passed a <code>null</code> input.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * 
	 * @see Object#toString()
	 * @see #objectToStringTransformer()
	 * @see #objectToStringTransformer_()
	 * @see #stringFromObjectTransformer()
	 */
	public static <I> Transformer<I, String> objectToStringTransformer(String nullString) {
		return nullCheck(objectToStringTransformer_(), nullString);
	}

	/**
	 * Return a transformer that transforms a non-<code>null</code> object into
	 * the string returned by its {@link Object#toString()} method.
	 * <p>
	 * <strong>NB:</strong> The transformer will throw a
	 * {@link NullPointerException} if it is passed a <code>null</code> input.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * 
	 * @see Object#toString()
	 * @see #objectToStringTransformer()
	 * @see #objectToStringTransformer(String)
	 * @see #stringFromObjectTransformer()
	 */
	public static <I> Transformer<I, String> objectToStringTransformer_() {
		return input -> input.toString();
	}


	// ********** boolean **********

	/**
	 * Return a transformer that will transform a
	 * {@link Boolean} to its NOT value:<ul>
	 * <li>If the original {@link Boolean} is {@link Boolean#TRUE},
	 * the transformer will return {@link Boolean#FALSE}.
	 * <li>If the original {@link Boolean} is {@link Boolean#FALSE},
	 * the transformer will return {@link Boolean#TRUE}.
	 * <li>If the original {@link Boolean} is <code>null</code>,
	 * the transformer will return the specified value.
	 * </ul>
	 * @see #notBooleanTransformer()
	 */
	public static Transformer<Boolean, Boolean> notBooleanTransformer(Boolean nullBoolean) {
		return (nullBoolean == null) ? notBooleanTransformer() : nullCheck(notBooleanTransformer(), nullBoolean);
	}

	/**
	 * Return a transformer that will transform a
	 * {@link Boolean} to its NOT value:<ul>
	 * <li>If the original {@link Boolean} is {@link Boolean#TRUE},
	 * the transformer will return {@link Boolean#FALSE}.
	 * <li>If the original {@link Boolean} is {@link Boolean#FALSE},
	 * the transformer will return {@link Boolean#TRUE}.
	 * <li>If the original {@link Boolean} is <code>null</code>,
	 * the transformer will return <code>null</code>.
	 * </ul>
	 * @see #notBooleanTransformer(Boolean)
	 */
	public static Transformer<Boolean, Boolean> notBooleanTransformer() {
		return b -> (b == null) ? null : BooleanTools.not(b);
	}

	/**
	 * Return a transformer that will transform an object to a
	 * {@link Boolean}:<ul>
	 * <li>If the object is <code>null</code>,
	 * the transformer will return {@link Boolean#FALSE}.
	 * <li>If the object is <em>not</em> <code>null</code>,
	 * the transformer will return {@link Boolean#TRUE}.
	 * </ul>
	 * @see #isNullTransformer
	 */
	public static Transformer<Object, Boolean> isNotNullTransformer() {
		return IS_NOT_NULL_TRANSFORMER;
	}

	/**
	 * @see #isNotNullTransformer()
	 * @see #IS_NULL_TRANSFORMER
	 */
	public static final Transformer<Object, Boolean> IS_NOT_NULL_TRANSFORMER = adapt(PredicateTools.isNotNull());

	/**
	 * Return a transformer that will transform an object to a
	 * {@link Boolean}:<ul>
	 * <li>If the object is <code>null</code>,
	 * the transformer will return {@link Boolean#TRUE}.
	 * <li>If the object is <em>not</em> <code>null</code>,
	 * the transformer will return {@link Boolean#FALSE}.
	 * </ul>
	 * @see #isNotNullTransformer
	 */
	public static Transformer<Object, Boolean> isNullTransformer() {
		return IS_NULL_TRANSFORMER;
	}

	/**
	 * @see #isNullTransformer()
	 * @see #IS_NOT_NULL_TRANSFORMER
	 */
	public static final Transformer<Object, Boolean> IS_NULL_TRANSFORMER = adapt(PredicateTools.isNull());

	/**
	 * Return a transformer that adapts the specified {@link Predicate
	 * predicate} to the {@link Transformer transformer} interface.
	 * <p>
	 * <strong>NB:</strong> If passed a <code>null</code> <em>input</em>, the
	 * transformer will simply return a <code>null</code> <em>output</em>
	 * without forwarding the <em>input</em> to the predicate.
	 * 
	 * @param <I> input: the type of the object passed to the transformer (and
	 *   forwarded to the wrapped predicate)
	 * 
	 * @see #adapt(Predicate, Boolean)
	 * @see #adapt(Predicate)
	 */
	public static <I> Transformer<I, Boolean> adapt_(Predicate<? super I> predicate) {
		return adapt(predicate, null);
	}

	/**
	 * Return a transformer that adapts the specified {@link Predicate
	 * predicate} to the {@link Transformer transformer} interface.
	 * <p>
	 * <strong>NB:</strong> If passed a <code>null</code> <em>input</em>, the
	 * transformer will simply return the specified value
	 * without forwarding the <em>input</em> to the predicate.
	 * 
	 * @param <I> input: the type of the object passed to the transformer (and
	 *   forwarded to the wrapped predicate)
	 * 
	 * @see #adapt_(Predicate)
	 * @see #adapt(Predicate)
	 */
	public static <I> Transformer<I, Boolean> adapt(Predicate<? super I> predicate, Boolean nullBoolean) {
		return nullCheck(adapt(predicate), nullBoolean);
	}

	/**
	 * Return a transformer that adapts the specified {@link Predicate
	 * predicate} to the {@link Transformer transformer} interface.
	 * <p>
	 * <strong>NB:</strong> If passed a <code>null</code> <em>input</em>, the
	 * transformer will pass a <code>null</code> to the predicate.
	 * 
	 * @param <I> input: the type of the object passed to the transformer (and
	 *   forwarded to the wrapped predicate)
	 * 
	 * @see #adapt_(Predicate)
	 * @see #adapt(Predicate, Boolean)
	 */
	public static <I> Transformer<I, Boolean> adapt(Predicate<? super I> predicate) {
		ObjectTools.assertNotNull(predicate);
		return input -> Boolean.valueOf(predicate.evaluate(input));
	}


	// ********** string **********

	/**
	 * Return a transformer that transforms a {@link String} to the appropriate
	 * {@link Boolean}.
	 * <p>
	 * <strong>NB:</strong> The transformer will return <code>null</code>
	 * if it is passed a <code>null</code> input.
	 * 
	 * @see Boolean#valueOf(String)
	 * @see #stringToBooleanTransformer(Boolean)
	 * @see #stringToBooleanTransformer_()
	 */
	public static Transformer<String, Boolean> stringToBooleanTransformer() {
		return stringToBooleanTransformer(null);
	}

	/**
	 * Return a transformer that transforms a {@link String} to the appropriate
	 * {@link Boolean}. The transformer will return the specified value
	 * if it is passed a <code>null</code> input.
	 * 
	 * @see Boolean#valueOf(String)
	 * @see #stringToBooleanTransformer()
	 * @see #stringToBooleanTransformer_()
	 */
	public static Transformer<String, Boolean> stringToBooleanTransformer(Boolean nullBoolean) {
		return nullCheck(stringToBooleanTransformer_(), nullBoolean);
	}

	/**
	 * Return a transformer that transforms a {@link String} to the appropriate
	 * {@link Boolean}.
	 * <p>
	 * <strong>NB:</strong> The transformer will return {@link Boolean#FALSE}
	 * if it is passed a <code>null</code> input.
	 * 
	 * @see Boolean#valueOf(String)
	 * @see #stringToBooleanTransformer(Boolean)
	 * @see #stringToBooleanTransformer()
	 */
	public static Transformer<String, Boolean> stringToBooleanTransformer_() {
		return string -> Boolean.valueOf(string);
	}

	/**
	 * Return a transformer that transforms a {@link String} to the appropriate
	 * {@link Integer}.
	 * <p>
	 * <strong>NB:</strong> The transformer will return <code>null</code>
	 * if it is passed a <code>null</code> input.
	 * 
	 * @see Integer#valueOf(String)
	 * @see #stringToIntegerTransformer(Integer)
	 * @see #stringToIntegerTransformer_()
	 */
	public static Transformer<String, Integer> stringToIntegerTransformer() {
		return stringToIntegerTransformer(null);
	}

	/**
	 * Return a transformer that transforms a {@link String} to the appropriate
	 * {@link Integer}.  The transformer will return the specified value
	 * if it is passed a <code>null</code> input.
	 * 
	 * @see Integer#valueOf(String)
	 * @see #stringToIntegerTransformer()
	 * @see #stringToIntegerTransformer_()
	 */
	public static Transformer<String, Integer> stringToIntegerTransformer(Integer nullInteger) {
		return nullCheck(stringToIntegerTransformer_(), nullInteger);
	}

	/**
	 * Return a transformer that transforms a non-<code>null</code> {@link String}
	 * to the appropriate {@link Integer}.
	 * <p>
	 * <strong>NB:</strong> The transformer will throw a
	 * {@link NumberFormatException} if it is passed a <code>null</code> input.
	 * 
	 * @see Integer#valueOf(String)
	 * @see #stringToIntegerTransformer()
	 * @see #stringToIntegerTransformer(Integer)
	 */
	public static Transformer<String, Integer> stringToIntegerTransformer_() {
		return string -> Integer.valueOf(string);
	}


	// ********** collection **********

	/**
	 * Return a transformer that transforms a collection into its first element.
	 * If the collection is empty, the transformer returns <code>null</code>.
	 * 
	 * @param <E> the type of elements held by the collection
	 * 
	 * @see #collectionFirstElementTransformer_()
	 */
	public static <E> Transformer<Collection<? extends E>, E> collectionFirstElementTransformer() {
		return collection -> collection.isEmpty() ? null : collection.iterator().next();
	}

	/**
	 * Return a transformer that transforms a collection into its first element.
	 * If the collection is empty, the transformer throws a
	 * {@link java.util.NoSuchElementException}.
	 * 
	 * @param <E> the type of elements held by the collection
	 * 
	 * @see #collectionFirstElementTransformer()
	 */
	public static <E> Transformer<Collection<? extends E>, E> collectionFirstElementTransformer_() {
		return collection -> collection.iterator().next();
	}

	/**
	 * Return a transformer that transforms a collection into its last element.
	 * If the collection is empty, the transformer returns <code>null</code>.
	 * 
	 * @param <E> the type of elements held by the collection
	 * 
	 * @see #collectionLastElementTransformer_()
	 */
	public static <E> Transformer<Collection<? extends E>, E> collectionLastElementTransformer() {
		return collection -> collection.isEmpty() ? null : IterableTools.last(collection);
	}

	/**
	 * Return a transformer that transforms a collection into its last element.
	 * If the collection is empty, the transformer throws a
	 * {@link java.util.NoSuchElementException}.
	 * 
	 * @param <E> the type of elements held by the collection
	 * 
	 * @see #collectionLastElementTransformer()
	 */
	public static <E> Transformer<Collection<? extends E>, E> collectionLastElementTransformer_() {
		return collection -> IterableTools.last(collection);
	}

	/**
	 * Return a transformer that transforms a collection into its <em>single</em> element.
	 * If the collection is empty or contains more than one element,
	 * the transformer returns <code>null</code>.
	 * 
	 * @param <E> the type of elements held by the collection
	 */
	public static <E> Transformer<Collection<? extends E>, E> collectionSingleElementTransformer() {
		return collection -> (collection.size() == 1) ? collection.iterator().next() : null;
	}

	/**
	 * Return a transformer that converts a collection into a boolean
	 * that indicates whether the collection is <em>not</em> empty.
	 */
	public static Transformer<Collection<?>, Boolean> collectionIsNotEmptyTransformer() {
		return TransformerTools.COLLECTION_IS_NOT_EMPTY_TRANSFORMER;
	}

	/**
	 * Transformer that converts a collection into a boolean
	 * that indicates whether the collection is <em>not</em> empty.
	 */
	public static final Transformer<Collection<?>, Boolean> COLLECTION_IS_NOT_EMPTY_TRANSFORMER = adapt(PredicateTools.collectionIsNotEmptyPredicate());

	/**
	 * Return a transformer that converts a collection into a boolean
	 * that indicates whether the collection is empty.
	 */
	public static Transformer<Collection<?>, Boolean> collectionIsEmptyTransformer() {
		return TransformerTools.COLLECTION_IS_EMPTY_TRANSFORMER;
	}

	/**
	 * Transformer that converts a collection into a boolean
	 * that indicates whether the collection is empty.
	 */
	public static final Transformer<Collection<?>, Boolean> COLLECTION_IS_EMPTY_TRANSFORMER = adapt(PredicateTools.collectionIsEmptyPredicate());

	/**
	 * Return a transformer that converts a collection into a boolean
	 * that indicates whether the collection contains exactly one element.
	 */
	public static Transformer<Collection<?>, Boolean> collectionContainsSingleElementTransformer() {
		return TransformerTools.COLLECTION_CONTAINS_SINGLE_ELEMENT_TRANSFORMER;
	}

	/**
	 * Transformer that converts a collection into a boolean
	 * that indicates whether the collection contains exactly one element.
	 */
	public static final Transformer<Collection<?>, Boolean> COLLECTION_CONTAINS_SINGLE_ELEMENT_TRANSFORMER = adapt(PredicateTools.collectionContainsSingleElementPredicate());

	/**
	 * Return a transformer that converts a collection into a boolean
	 * that indicates whether the collection's size equals the specified size.
	 */
	public static Transformer<Collection<?>, Boolean> collectionSizeEqualsTransformer(int size) {
		return (size == 0) ? collectionIsEmptyTransformer(): (size == 1) ? collectionContainsSingleElementTransformer() : collectionSizeEqualsTransformer_(size);
	}

	private static Transformer<Collection<?>, Boolean> collectionSizeEqualsTransformer_(int size) {
		return adapt(PredicateTools.collectionSizeEqualsPredicate(size));
	}


	// ********** list **********

	/**
	 * Return a transformer that transforms a list into its last element.
	 * If the list is empty, the transformer returns <code>null</code>.
	 * 
	 * @param <E> the type of elements held by the list
	 * 
	 * @see #listLastElementTransformer_()
	 */
	public static <E> Transformer<List<? extends E>, E> listLastElementTransformer() {
		return list -> list.isEmpty() ? null : list.get(list.size() - 1);
	}

	/**
	 * Return a transformer that transforms a list into its last element.
	 * If the list is empty, the transformer throws an {@link IndexOutOfBoundsException}.
	 * 
	 * @param <E> the type of elements held by the list
	 * 
	 * @see #listLastElementTransformer()
	 */
	public static <E> Transformer<List<? extends E>, E> listLastElementTransformer_() {
		return list -> list.get(list.size() - 1);
	}


	// ********** XML **********

	/**
	 * Return a transformer that will transform a string with any XML
	 * <em>character references</em>
	 * by replacing the <em>character references</em> with the characters
	 * themselves: <code>"&amp;#x2f;" => '/'</code>
	 * 
	 * @see #xmlStringEncoder(char[])
	 * @see XMLStringDecoder
	 */
	public static Transformer<String, String> xmlStringDecoder() {
		return XMLStringDecoder.instance();
	}

	/**
	 * Return a transformer that will replace any of the XML "predefined
	 * entities" with an XML <em>character reference</em>:
	 * <code>'&' => "&amp;#x26;"</code>
	 * 
	 * @see #xmlStringDecoder()
	 * @see #xmlStringEncoder(char[])
	 * @see XMLStringEncoder
	 */
	public static Transformer<String, String> xmlStringEncoder() {
		return xmlStringEncoder(XML_PREDEFINED_ENTITIES);
	}
	public static final char[] XML_PREDEFINED_ENTITIES = new char[] {'"', '&', '\'', '<', '>'};

	/**
	 * Return a transformer that will replace any of the specified set of
	 * characters with an XML <em>character reference</em>:
	 * <code>'/' => "&amp;#x2f;"</code>
	 * 
	 * @see #xmlStringDecoder()
	 * @see #xmlStringEncoder()
	 * @see XMLStringEncoder
	 */
	public static Transformer<String, String> xmlStringEncoder(char[] chars) {
		return new XMLStringEncoder(chars);
	}


	// ********** thread local **********

	/**
	 * Return a transformer that allows the client to specify a different transformer
	 * for each thread. If there is no transformer for the current thread, the
	 * transformer will return <code>null</code>.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see ThreadLocalTransformer
	 */
	public static <I, O> ThreadLocalTransformer<I, O> threadLocalTransformer() {
		return threadLocalTransformer(nullOutputTransformer());
	}

	/**
	 * Return a transformer that allows the client to specify a different transformer
	 * for each thread. If there is no transformer for the current thread, the
	 * specified default transformer is used.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see ThreadLocalTransformer
	 */
	public static <I, O> ThreadLocalTransformer<I, O> threadLocalTransformer(Transformer<? super I, ? extends O> defaultTransformer) {
		return new ThreadLocalTransformer<>(defaultTransformer);
	}


	// ********** caching **********

	/**
	 * Return a transformer that caches the results generated by the specified transformer;
	 * the assumption being that the transformation is an expensive action.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see CachingTransformer
	 */
	public static <I, O> Transformer<I, O> cachingTransformer(Transformer<? super I, ? extends O> transformer) {
		return new CachingTransformer<>(transformer);
	}

	/**
	 * Return a transformer that caches, in a thread-safe fashion,
	 * the results generated by the specified transformer;
	 * the assumption being that the transformation is an expensive action.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see CachingInterruptibleTransformer
	 */
	public static <I, O> InterruptibleTransformer<I, O> cachingInterruptibleTransformer(Transformer<? super I, ? extends O> transformer) {
		return new CachingInterruptibleTransformer<>(transformer);
	}


	// ********** wrappers **********

	/**
	 * Wrap the specified transformer converting it
	 * into one that converts the same input object into an <em>iterator</em>
	 * of objects of the same type as the input object.
	 * 
	 * @param <I> input: the type of the object passed to the transformer; also the
	 *   type of object returned by the output iterator
	 */
	public static <I> Transformer<I, Iterator<? extends I>> toIterator(Transformer<? super I, ? extends Iterable<? extends I>> transformer) {
		ObjectTools.assertNotNull(transformer);
		return input -> transformer.transform(input).iterator();
	}

	/**
	 * Return a tranformer that checks for <code>null</code> input.
	 * If the input is <code>null</code>, the transformer will return
	 * the configured output value;
	 * otherwise, it will simply return the input.
	 * 
	 * @param <I> input: the type of the object passed to and
	 *   returned by the transformer
	 *   
	 * @see #nullCheck(Transformer)
	 * @see #nullCheck(Transformer, Object)
	 */
	public static <I> Transformer<I, I> nullCheck(I nullOutput) {
		return input -> (input != null) ? input : nullOutput;
	}

	/**
	 * Return a transformer that wraps the specified transformer and checks
	 * for <code>null</code> input before forwarding the input to the specified
	 * transformer. If the input is <code>null</code>, the transformer will
	 * return <code>null</code>.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #nullCheck(Transformer, Object)
	 * @see #nullCheck(Object)
	 */
	public static <I, O> Transformer<I, O> nullCheck(Transformer<? super I, ? extends O> transformer) {
		return nullCheck(transformer, null);
	}

	/**
	 * Return a transformer that wraps the specified transformer and checks
	 * for <code>null</code> input before forwarding the input to the specified
	 * transformer. If the input is <code>null</code>, the transformer will
	 * return the specified output value.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #nullCheck(Transformer)
	 * @see #nullCheck(Object)
	 */
	public static <I, O> Transformer<I, O> nullCheck(Transformer<? super I, ? extends O> transformer, O nullOutput) {
		ObjectTools.assertNotNull(transformer);
		return input -> (input == null) ? nullOutput : transformer.transform(input);
	}

	/**
	 * Return a transformer that wraps the specified transformer and checks
	 * for <code>null</code> input before forwarding the input to the specified
	 * transformer. If the input is <code>null</code>, the transformer will
	 * return <code>null</code>.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #nullCheck(InterruptibleTransformer, Object)
	 * @see #nullCheck(Object)
	 */
	public static <I, O> InterruptibleTransformer<I, O> nullCheck(InterruptibleTransformer<? super I, ? extends O> transformer) {
		return nullCheck(transformer, null);
	}

	/**
	 * Return a transformer that wraps the specified transformer and checks
	 * for <code>null</code> input before forwarding the input to the specified
	 * transformer. If the input is <code>null</code>, the transformer will
	 * return the specified output value.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #nullCheck(InterruptibleTransformer)
	 * @see #nullCheck(Object)
	 */
	public static <I, O> InterruptibleTransformer<I, O> nullCheck(InterruptibleTransformer<? super I, ? extends O> transformer, O nullOutput) {
		ObjectTools.assertNotNull(transformer);
		return input -> (input == null) ? nullOutput : transformer.transform(input);
	}

	/**
	 * Return a transformer that can have its wrapped transformer changed,
	 * allowing a client to change a previously-supplied transformer's
	 * behavior mid-stream.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see TransformerWrapper
	 */
	public static <I, O> TransformerWrapper<I, O> wrap(Transformer<? super I, ? extends O> transformer) {
		return new TransformerWrapper<>(transformer);
	}


	// ********** casting **********

	/**
	 * Return a transformer that simply casts the specified transformer's return type.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <X> intermediate: the type of object returned by the wrapped
	 *   transformer
	 * @param <O> output: the type of object returned by the transformer - this
	 *   is the same object returned by the wrapped transformer, simply
	 *   cast to <code>O</code>
	 *   
	 * @see #upcast(Transformer)
	 * @see #downcast(Transformer)
	 */
	@SuppressWarnings("unchecked")
	public static <I, X, O> Transformer<I, O> cast(Transformer<? super I, ? extends X> transformer) {
		ObjectTools.assertNotNull(transformer);
		return input -> (O) transformer.transform(input);
	}

	/**
	 * Return a transformer that simply downcasts the specified transformer's return type.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <X> intermediate: the type of object returned by the wrapped
	 *   transformer
	 * @param <O> output: the type of object returned by the transformer - this
	 *   is the same object returned by the wrapped transformer, simply
	 *   cast to <code>O</code>
	 * 
	 * @see #cast(Transformer)
	 * @see #upcast(Transformer)
	 */
	@SuppressWarnings("unchecked")
	public static <I, X, O extends X> Transformer<I, O> downcast(Transformer<? super I, ? extends X> transformer) {
		ObjectTools.assertNotNull(transformer);
		return input -> (O) transformer.transform(input);
	}

	/**
	 * Return a transformer that simply upcasts the specified transformer's return type.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of object returned by the transformer - this
	 *   is the same object returned by the wrapped transformer, simply
	 *   cast to <code>O</code>
	 * @param <X> intermediate: the type of object returned by the wrapped
	 *   transformer
	 * 
	 * @see #cast(Transformer)
	 * @see #downcast(Transformer)
	 */
	public static <I, O, X extends O> Transformer<I, O> upcast(Transformer<? super I, ? extends X> transformer) {
		ObjectTools.assertNotNull(transformer);
		return input -> (O) transformer.transform(input);
	}

	/**
	 * Return a transformer that simply casts the specified transformer's return type.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <X> intermediate: the type of object returned by the wrapped
	 *   transformer
	 * @param <O> output: the type of object returned by the transformer - this
	 *   is the same object returned by the wrapped transformer, simply
	 *   cast to <code>O</code>
	 *   
	 * @see #upcast(InterruptibleTransformer)
	 * @see #downcast(InterruptibleTransformer)
	 */
	@SuppressWarnings("unchecked")
	public static <I, X, O> InterruptibleTransformer<I, O> cast(InterruptibleTransformer<? super I, ? extends X> transformer) {
		ObjectTools.assertNotNull(transformer);
		return input -> (O) transformer.transform(input);
	}

	/**
	 * Return a transformer that simply downcasts the specified transformer's return type.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <X> intermediate: the type of object returned by the wrapped
	 *   transformer
	 * @param <O> output: the type of object returned by the transformer - this
	 *   is the same object returned by the wrapped transformer, simply
	 *   cast to <code>O</code>
	 * 
	 * @see #cast(InterruptibleTransformer)
	 * @see #upcast(InterruptibleTransformer)
	 */
	@SuppressWarnings("unchecked")
	public static <I, X, O extends X> InterruptibleTransformer<I, O> downcast(InterruptibleTransformer<? super I, ? extends X> transformer) {
		ObjectTools.assertNotNull(transformer);
		return input -> (O) transformer.transform(input);
	}

	/**
	 * Return a transformer that simply upcasts the specified transformer's return type.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of object returned by the transformer - this
	 *   is the same object returned by the wrapped transformer, simply
	 *   cast to <code>O</code>
	 * @param <X> intermediate: the type of object returned by the wrapped
	 *   transformer
	 * 
	 * @see #cast(InterruptibleTransformer)
	 * @see #downcast(InterruptibleTransformer)
	 */
	public static <I, O, X extends O> InterruptibleTransformer<I, O> upcast(InterruptibleTransformer<? super I, ? extends X> transformer) {
		ObjectTools.assertNotNull(transformer);
		return input -> (O) transformer.transform(input);
	}


	// ********** safe **********

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream} and <code>null</code> will be
	 * returned.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #safe(Transformer, Object)
	 * @see #safe(Transformer, ExceptionHandler)
	 * @see #safe(Transformer, ExceptionHandler, Object)
	 * @see DefaultExceptionHandler
	 */
	public static <I, O> Transformer<I, O> safe(Transformer<? super I, ? extends O> transformer) {
		return safe(transformer, DefaultExceptionHandler.instance(), null);
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream}
	 * and specified output will be returned.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #safe(Transformer)
	 * @see #safe(Transformer, ExceptionHandler)
	 * @see #safe(Transformer, ExceptionHandler, Object)
	 * @see DefaultExceptionHandler
	 */
	public static <I, O> Transformer<I, O> safe(Transformer<? super I, ? extends O> transformer, O exceptionOutput) {
		return safe(transformer, DefaultExceptionHandler.instance(), exceptionOutput);
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception will be passed to the specified exception handler
	 * and <code>null</code> will be returned.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #safe(Transformer)
	 * @see #safe(Transformer, Object)
	 * @see #safe(Transformer, ExceptionHandler, Object)
	 */
	public static <I, O> Transformer<I, O> safe(Transformer<? super I, ? extends O> transformer, ExceptionHandler exceptionHandler) {
		return safe(transformer, exceptionHandler, null);
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception will be passed to the specified exception handler
	 * and specified output will be returned.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #safe(Transformer)
	 * @see #safe(Transformer, Object)
	 * @see #safe(Transformer, ExceptionHandler)
	 * @see #safe(InterruptibleTransformer, ExceptionHandler, Object)
	 */
	public static <I, O> Transformer<I, O> safe(Transformer<? super I, ? extends O> transformer, ExceptionHandler exceptionHandler, O exceptionOutput) {
		ObjectTools.assertNotNull(transformer);
		ObjectTools.assertNotNull(exceptionHandler);
		return input -> {
				try {
					return transformer.transform(input);
				} catch (Throwable ex) {
					exceptionHandler.handleException(ex);
					return exceptionOutput;
				}
			};
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream} and <code>null</code> will be
	 * returned.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #safe(InterruptibleTransformer, Object)
	 * @see #safe(InterruptibleTransformer, ExceptionHandler)
	 * @see #safe(InterruptibleTransformer, ExceptionHandler, Object)
	 * @see DefaultExceptionHandler
	 */
	public static <I, O> InterruptibleTransformer<I, O> safe(InterruptibleTransformer<? super I, ? extends O> transformer) {
		return safe(transformer, DefaultExceptionHandler.instance(), null);
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream}
	 * and specified output will be returned.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #safe(InterruptibleTransformer)
	 * @see #safe(InterruptibleTransformer, ExceptionHandler)
	 * @see #safe(InterruptibleTransformer, ExceptionHandler, Object)
	 * @see DefaultExceptionHandler
	 */
	public static <I, O> InterruptibleTransformer<I, O> safe(InterruptibleTransformer<? super I, ? extends O> transformer, O exceptionOutput) {
		return safe(transformer, DefaultExceptionHandler.instance(), exceptionOutput);
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception will be passed to the specified exception handler
	 * and <code>null</code> will be returned.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #safe(InterruptibleTransformer)
	 * @see #safe(InterruptibleTransformer, Object)
	 * @see #safe(InterruptibleTransformer, ExceptionHandler, Object)
	 */
	public static <I, O> InterruptibleTransformer<I, O> safe(InterruptibleTransformer<? super I, ? extends O> transformer, ExceptionHandler exceptionHandler) {
		return safe(transformer, exceptionHandler, null);
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions (other than {@link InterruptedException}) thrown
	 * during the transformation. If an exception is
	 * thrown, the exception will be passed to the specified exception handler
	 * and specified output will be returned.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #safe(InterruptibleTransformer)
	 * @see #safe(InterruptibleTransformer, Object)
	 * @see #safe(InterruptibleTransformer, ExceptionHandler)
	 * @see #safe(Transformer, ExceptionHandler, Object)
	 */
	public static <I, O> InterruptibleTransformer<I, O> safe(InterruptibleTransformer<? super I, ? extends O> transformer, ExceptionHandler exceptionHandler, O exceptionOutput) {
		ObjectTools.assertNotNull(transformer);
		ObjectTools.assertNotNull(exceptionHandler);
		return input -> {
				try {
					return transformer.transform(input);
				} catch (InterruptedException ex) {
					throw ex;
				} catch (Throwable ex) {
					exceptionHandler.handleException(ex);
					return exceptionOutput;
				}
			};
	}


	// ********** pass-thru **********

	/**
	 * Return a transformer that will perform no transformation at all;
	 * it will simply return the input untransformed.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 *   (and returned by the transformer)
	 * 
	 * @see #passThruTransformer(Object)
	 */
	public static <I> Transformer<I, I> passThruTransformer() {
		return input -> input;
	}

	/**
	 * Return a transformer that will perform no transformation at all;
	 * it will simply return the input if it is not <code>null</code>.
	 * The transformer will convert a <code>null</code> input into the specified
	 * output.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 *   (and returned by the transformer)
	 * 
	 * @see #passThruTransformer()
	 */
	public static <I> Transformer<I, I> passThruTransformer(I nullOutput) {
		return nullCheck(passThruTransformer(), nullOutput);
	}


	// ********** filtering **********

	/**
	 * Return a tranformer that "filters" its input.
	 * If the input is a member of the set defined by the specified filter,
	 * the transformer simply returns the input;
	 * otherwise, the transformer will return <code>null</code>.
	 * 
	 * @param <I> input: the type of the object passed to and
	 * returned by the transformer
	 */
	public static <I> Transformer<I, I> filteringTransformer(Predicate<? super I> filter) {
		return filteringTransformer(filter, null);
	}

	/**
	 * Return a tranformer that "filters" its input.
	 * If the input is a member of the set defined by the specified filter,
	 * the transformer simply returns the input;
	 * otherwise, the transformer will return the default output.
	 * 
	 * @param <I> input: the type of the object passed to and
	 * returned by the transformer
	 */
	public static <I> Transformer<I, I> filteringTransformer(Predicate<? super I> filter, I defaultOutput) {
		ObjectTools.assertNotNull(filter);
		return input -> filter.evaluate(input) ? input : defaultOutput;
	}


	// ********** reflection **********

	/**
	 * Return a transformer that clones its input.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 *   (and returned by the transformer)
	 */
	@SuppressWarnings("unchecked")
	public static <I extends Cloneable> Transformer<I, I> cloneTransformer() {
		return input -> (I) ObjectTools.execute(input, "clone"); //$NON-NLS-1$
	}

	/**
	 * Return a transformer that transforms a {@link Class} into an instance
	 * by calling {@link Class#newInstance()}. Checked exceptions are converted
	 * to {@link RuntimeException}s.
	 * 
	 * @param <O> output: the type of the object returned by the transformer (and
	 *   the class, or superclass of the class, passed to the transformer)
	 */
	public static <O> Transformer<Class<? extends O>, O> instantiationTransformer() {
		return input -> ClassTools.newInstance(input);
	}

	/**
	 * Return a transformer that uses Java reflection to transform an object
	 * into the value of its field with the specified name.
	 * <p>
	 * <strong>NB:</strong> The actual field is determined at execution time,
	 * not construction time. As a result, the transformer can be used to emulate
	 * "duck typing".
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	@SuppressWarnings("unchecked")
	public static <I, O> Transformer<I, O> get(String fieldName) {
		return input -> (O) ObjectTools.get(input, fieldName);
	}

	/**
	 * Return a transformer that uses Java reflection to transform an object
	 * into the value returned by its zero-argument method with the specified
	 * name.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the transformer can be used to emulate
	 * "duck typing".
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> execute(String methodName) {
		return execute(methodName, ClassTools.EMPTY_ARRAY, ObjectTools.EMPTY_OBJECT_ARRAY);
	}

	/**
	 * Return a transformer that uses Java reflection to transform an object
	 * into the value returned by its single-argument method with the specified
	 * name, parameter type, and argument.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the transformer can be used to emulate
	 * "duck typing".
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> execute(String methodName, Class<?> parameterType, Object argument) {
		return execute(methodName, new Class<?>[] { parameterType }, new Object[] { argument });
	}

	/**
	 * Return a transformer that uses Java reflection to transform an object
	 * into the value returned by its method with the specified
	 * name, parameter types, and arguments.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the transformer can be used to emulate
	 * "duck typing".
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	@SuppressWarnings("unchecked")
	public static <I, O> Transformer<I, O> execute(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		ObjectTools.assertNotNull(methodName);
		ArrayTools.assertNeitherIsNorContainsNull(parameterTypes);
		ObjectTools.assertNotNull(arguments);
		return input -> (O) ObjectTools.execute(input, methodName, parameterTypes, arguments);
	}


	// ********** chain **********

	/**
	 * @see #chain(Iterable)
	 */
	public static <I, O> Transformer<I, O> chain(@SuppressWarnings("rawtypes") Transformer... transformers) {
		return chain(ArrayTools.<Transformer<?, ?>>iterable(transformers));
	}

	/**
	 * Chain the specified transformers. Pass the chain's input to the first
	 * transformer; take that transformer's output and pass it to the following
	 * transformer in the chain; etc. The output from the final transformer is
	 * returned as the chain's output.
	 * <p>
	 * <strong>NB:</strong> The transformer's generic types are for convenience only
	 * and cannot be enforced on the transformers in the chain.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <I, O> Transformer<I, O> chain(Iterable<Transformer<?, ?>> transformers) {
		ObjectTools.assertNotNull(transformers);
		return input -> {
				Object result = input;
				for (Transformer transformer : transformers) {
					result = transformer.transform(result);
				}
				return (O) result;
			};
	}

	/**
	 * @see #chain(Iterable)
	 */
	public static <I, O> InterruptibleTransformer<I, O> chain(@SuppressWarnings("rawtypes") InterruptibleTransformer... transformers) {
		return interuptibleChain(ArrayTools.<InterruptibleTransformer<?, ?>>iterable(transformers));
	}

	/**
	 * Chain the specified transformers. Pass the chain's input to the first
	 * transformer; take that transformer's output and pass it to the following
	 * transformer in the chain; etc. The output from the final transformer is
	 * returned as the chain's output.
	 * <p>
	 * <strong>NB:</strong> The transformer's generic types are for convenience only
	 * and cannot be enforced on the transformers in the chain.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <I, O> InterruptibleTransformer<I, O> interuptibleChain(Iterable<InterruptibleTransformer<?, ?>> transformers) {
		ObjectTools.assertNotNull(transformers);
		return input -> {
				Object result = input;
				for (InterruptibleTransformer transformer : transformers) {
					result = transformer.transform(result);
				}
				return (O) result;
			};
	}


	// ********** conditional **********

	/**
	 * Return a transformer that passes its input to the specified predicate to
	 * determine whether to forward the input to the specified transformer.
	 * If the predicate evaluates to <code>false</code>, the transformer returns
	 * <code>null</code>.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> conditionalTransformer(Predicate<? super I> predicate, Transformer<? super I, ? extends O> transformer) {
		return conditionalTransformer(predicate, transformer, nullOutputTransformer());
	}

	/**
	 * Return a transformer that passes its input to the specified predicate to
	 * determine which of the two specified transformers to execute.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> conditionalTransformer(Predicate<? super I> predicate, Transformer<? super I, ? extends O> trueTransformer, Transformer<? super I, ? extends O> falseTransformer) {
		ObjectTools.assertNotNull(predicate);
		ObjectTools.assertNotNull(trueTransformer);
		ObjectTools.assertNotNull(falseTransformer);
		return input -> predicate.evaluate(input) ?
				trueTransformer.transform(input) :
				falseTransformer.transform(input);
	}

	/**
	 * Return a transformer that passes its input to the specified predicate to
	 * determine which of the two specified transformers to execute.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> InterruptibleTransformer<I, O> conditionalTransformer(Predicate<? super I> predicate, InterruptibleTransformer<? super I, ? extends O> trueTransformer, InterruptibleTransformer<? super I, ? extends O> falseTransformer) {
		ObjectTools.assertNotNull(predicate);
		ObjectTools.assertNotNull(trueTransformer);
		ObjectTools.assertNotNull(falseTransformer);
		return input -> predicate.evaluate(input) ?
				trueTransformer.transform(input) :
				falseTransformer.transform(input);
	}


	// ********** switch **********

	/**
	 * @see #switchTransformer(Iterable)
	 */
	@SafeVarargs
	public static <I, O> Transformer<I, O> switchTransformer(Association<Predicate<? super I>, Transformer<? super I, ? extends O>>... transformers) {
		return switchTransformer(ArrayTools.iterable(transformers));
	}

	/**
	 * Return a transformer that loops over the specified set of
	 * predicate/transformer pairs, passing its input to each predicate to
	 * determine which of the transformers to execute. Only the first
	 * transformer whose predicate evaluates to <code>true</code> is executed,
	 * even if other, following, predicates would evaluate to <code>true</code>.
	 * If none of the predicates evaluates to <code>true</code>, the transformer
	 * returns <code>null</code>.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> switchTransformer(Iterable<Association<Predicate<? super I>, Transformer<? super I, ? extends O>>> transformers) {
		return switchTransformer(transformers, nullOutputTransformer());
	}

	/**
	 * Return a transformer that loops over the specified set of
	 * predicate/transformer pairs, passing its input to each predicate to
	 * determine which of the transformers to execute. Only the first
	 * transformer whose predicate evaluates to <code>true</code> is executed,
	 * even if other, following, predicates would evaluate to <code>true</code>.
	 * If none of the predicates evaluates to <code>true</code>, the default transformer
	 * is executed.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> switchTransformer(Iterable<Association<Predicate<? super I>, Transformer<? super I, ? extends O>>> transformers, Transformer<? super I, ? extends O> defaultTransformer) {
		IterableTools.assertNeitherIsNorContainsNull(transformers);
		ObjectTools.assertNotNull(defaultTransformer);
		return input -> {
				for (Association<Predicate<? super I>, Transformer<? super I, ? extends O>> association : transformers) {
					if (association.getKey().evaluate(input)) {
						return association.getValue().transform(input); // execute only one transformer
					}
				}
				return defaultTransformer.transform(input);
			};
	}

	/**
	 * @see #switchTransformer(Iterable)
	 */
	@SafeVarargs
	public static <I, O> InterruptibleTransformer<I, O> interruptibleSwitchTransformer(Association<Predicate<? super I>, InterruptibleTransformer<? super I, ? extends O>>... transformers) {
		return interruptibleSwitchTransformer(ArrayTools.iterable(transformers));
	}

	/**
	 * Return a transformer that loops over the specified set of
	 * predicate/transformer pairs, passing its input to each predicate to
	 * determine which of the transformers to execute. Only the first
	 * transformer whose predicate evaluates to <code>true</code> is executed,
	 * even if other, following, predicates would evaluate to <code>true</code>.
	 * If none of the predicates evaluates to <code>true</code>, the transformer
	 * returns <code>null</code>.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> InterruptibleTransformer<I, O> interruptibleSwitchTransformer(Iterable<Association<Predicate<? super I>, InterruptibleTransformer<? super I, ? extends O>>> transformers) {
		return interruptibleSwitchTransformer(transformers, nullOutputTransformer());
	}

	/**
	 * Return a transformer that loops over the specified set of
	 * predicate/transformer pairs, passing its input to each predicate to
	 * determine which of the transformers to execute. Only the first
	 * transformer whose predicate evaluates to <code>true</code> is executed,
	 * even if other, following, predicates would evaluate to <code>true</code>.
	 * If none of the predicates evaluates to <code>true</code>, the default transformer
	 * is executed.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> InterruptibleTransformer<I, O> interruptibleSwitchTransformer(Iterable<Association<Predicate<? super I>, InterruptibleTransformer<? super I, ? extends O>>> transformers, InterruptibleTransformer<? super I, ? extends O> defaultTransformer) {
		IterableTools.assertNeitherIsNorContainsNull(transformers);
		ObjectTools.assertNotNull(defaultTransformer);
		return input -> {
				for (Association<Predicate<? super I>, InterruptibleTransformer<? super I, ? extends O>> association : transformers) {
					if (association.getKey().evaluate(input)) {
						return association.getValue().transform(input); // execute only one transformer
					}
				}
				return defaultTransformer.transform(input);
			};
	}


	// ********** disabled **********

	/**
	 * Return a transformer that will throw an {@link UnsupportedOperationException exception}
	 * if {@link Transformer#transform(Object)} is called. This is useful in
	 * situations where a transformer is optional and the default transformer
	 * should not be used.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> disabledTransformer() {
		return input -> {throw new UnsupportedOperationException();};
	}


	// ********** map **********

	/**
	 * Return a transformer that will transform an input into its value
	 * in the specified map.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> mapTransformer(Map<? super I, ? extends O> map) {
		ObjectTools.assertNotNull(map);
		return input -> map.get(input);
	}


	// ********** static output **********

	/**
	 * Return a transformer that will transform every input into <code>null</code>.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #staticOutputTransformer(Object)
	 */
	public static <I, O> Transformer<I, O> nullOutputTransformer() {
		return input -> null;
	}

	/**
	 * Return a transformer that will transform any object into the specified
	 * output.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * 
	 * @see #nullOutputTransformer()
	 */
	public static <I, O> Transformer<I, O> staticOutputTransformer(O output) {
		return input -> output;
	}


	// ********** empty array **********

	public static Transformer<?, ?>[] emptyArray() {
		return EMPTY_ARRAY;
	}

	@SuppressWarnings("rawtypes")
	public static final Transformer[] EMPTY_ARRAY = new Transformer[0];


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private TransformerTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
