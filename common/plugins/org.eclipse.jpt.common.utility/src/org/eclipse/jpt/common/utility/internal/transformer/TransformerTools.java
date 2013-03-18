/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.util.Iterator;
import java.util.Map;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.DefaultExceptionHandler;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Transformer} utility methods.
 */
public final class TransformerTools {

	// ********** object to string **********

	/**
	 * Return a transformer that will transform an object into the string
	 * returned by its {@link Object#toString()} method. A <code>null</code>
	 * object is transformed into the string <code>"null"</code>.
	 * @param <I> input: the type of the object passed to the transformer
	 * @see ObjectToStringTransformer
	 * @see #objectToStringTransformer(String)
	 * @see #objectToStringTransformer()
	 */
	public static <I> Transformer<I, String> nonNullObjectToStringTransformer() {
		return objectToStringTransformer(String.valueOf((Object) null));
	}

	/**
	 * Return a transformer that will transform an object into the string
	 * returned by its {@link Object#toString()} method. A <code>null</code>
	 * object is transformed into the specified string.
	 * @param <I> input: the type of the object passed to the transformer
	 * @see ObjectToStringTransformer
	 * @see #objectToStringTransformer()
	 * @see #nonNullObjectToStringTransformer()
	 */
	public static <I> Transformer<I, String> objectToStringTransformer(String nullString) {
		return new ObjectToStringTransformer<I>(nullString);
	}

	/**
	 * Return a transformer that will transform an object into the string
	 * returned by its {@link Object#toString()} method. A <code>null</code>
	 * object is transformed into <code>null</code>.
	 * @param <I> input: the type of the object passed to the transformer
	 * @see ObjectToStringTransformer
	 * @see #objectToStringTransformer(String)
	 * @see #nonNullObjectToStringTransformer()
	 */
	@SuppressWarnings("unchecked")
	public static <I> Transformer<I, String> objectToStringTransformer() {
		return OBJECT_TO_STRING_TRANSFORMER;
	}
	@SuppressWarnings("rawtypes")
	private static final Transformer OBJECT_TO_STRING_TRANSFORMER = new ObjectToStringTransformer(null);


	// ********** boolean **********

	/**
	 * @see #booleanTransformer(Boolean)
	 * @see BooleanTransformer
	 */
	public static Transformer<Boolean, Boolean> booleanTransformer(boolean nullValue) {
		return BooleanTransformer.valueOf(nullValue);
	}

	/**
	 * Return a transformer that will transform a possibly-null
	 * {@link Boolean} to a non-null {@link Boolean}:<ul>
	 * <li>When the original {@link Boolean} is <em>not</em> <code>null</code>,
	 * the transformer will return it unchanged.
	 * <li>When the original {@link Boolean} is <code>null</code>,
	 * the transformer will return the specified "null value"
	 * ({@link Boolean#TRUE} or {@link Boolean#FALSE}).
	 * </ul>
	 * @see BooleanTransformer
	 */
	public static Transformer<Boolean, Boolean> booleanTransformer(Boolean nullValue) {
		return BooleanTransformer.valueOf(nullValue);
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
	 * Use a {@link #booleanTransformer(Boolean)} to specify a value for when a
	 * {@link Boolean} is <code>null</code>
	 * @see NotBooleanTransformer
	 */
	public static Transformer<Boolean, Boolean> notBooleanTransformer() {
		return NotBooleanTransformer.instance();
	}

	/**
	 * Return a transformer that will transform an object to a
	 * {@link Boolean}:<ul>
	 * <li>If the object is <code>null</code>,
	 * the transformer will return {@link Boolean#FALSE}.
	 * <li>If the object is <em>not</em> <code>null</code>,
	 * the transformer will return {@link Boolean#TRUE}.
	 * </ul>
	 * @param <I> input: the type of the object passed to the transformer
	 * @see IsNotNullTransformer
	 */
	public static <I> Transformer<I, Boolean> isNotNullTransformer() {
		return IsNotNullTransformer.<I>instance();
	}

	/**
	 * Return a transformer that will transform an object to a
	 * {@link Boolean}:<ul>
	 * <li>If the object is <code>null</code>,
	 * the transformer will return {@link Boolean#TRUE}.
	 * <li>If the object is <em>not</em> <code>null</code>,
	 * the transformer will return {@link Boolean#FALSE}.
	 * </ul>
	 * @param <I> input: the type of the object passed to the transformer
	 * @see IsNullTransformer
	 */
	public static <I> Transformer<I, Boolean> isNullTransformer() {
		return IsNullTransformer.<I>instance();
	}

	/**
	 * Return a transformer that adapts the specified predicate to the
	 * {@link Transformer} interface.
	 * @param <I> input: the type of the object passed to the transformer (and
	 *   forwarded to the wrapped predicate)
	 * @see PredicateTransformer
	 */
	public static <I> Transformer<I, Boolean> adapt(Predicate<? super I> predicate) {
		return new PredicateTransformer<I>(predicate);
	}


	// ********** string **********

	/**
	 * Return a transformer that converts a {@link String} to the appropriate
	 * {@link Boolean}.
	 * @see StringToBooleanTransformer
	 * @see Boolean#valueOf(String)
	 * @see #stringToBooleanTransformer(Boolean)
	 */
	public static Transformer<String, Boolean> stringToBooleanTransformer() {
		return stringToBooleanTransformer(null);
	}

	/**
	 * Return a transformer that converts a {@link String} to the appropriate
	 * {@link Boolean}.
	 * @see StringToBooleanTransformer
	 * @see Boolean#valueOf(String)
	 * @see #stringToBooleanTransformer()
	 */
	public static Transformer<String, Boolean> stringToBooleanTransformer(Boolean nullBoolean) {
		return new StringToBooleanTransformer(nullBoolean);
	}

	/**
	 * Return a transformer that converts a {@link String} to the appropriate
	 * {@link Integer}.
	 * @see StringToIntegerTransformer
	 * @see Integer#valueOf(String)
	 * @see #stringToIntegerTransformer(Integer)
	 */
	public static Transformer<String, Integer> stringToIntegerTransformer() {
		return stringToIntegerTransformer(null);
	}

	/**
	 * Return a transformer that converts a {@link String} to the appropriate
	 * {@link Integer}.
	 * @see StringToIntegerTransformer
	 * @see Integer#valueOf(String)
	 * @see #stringToIntegerTransformer()
	 */
	public static Transformer<String, Integer> stringToIntegerTransformer(Integer nullInteger) {
		return new StringToIntegerTransformer(nullInteger);
	}


	// ********** XML **********

	/**
	 * Return a transformer that will transform a string with any XML
	 * <em>character references</em>
	 * by replacing the <em>character references</em> with the characters
	 * themselves: <code>"&amp;#x2f;" => '/'</code>
	 * @see #xmlStringEncoder(char[])
	 * @see XMLStringDecoder
	 */
	public static Transformer<String, String> xmlStringDecoder() {
		return XMLStringDecoder.instance();
	}

	/**
	 * Return a transformer that will replace any of the specified set of
	 * characters with an XML <em>character reference</em>:
	 * <code>'/' => "&amp;#x2f;"</code>
	 * @see #xmlStringDecoder()
	 * @see XMLStringEncoder
	 */
	public static Transformer<String, String> xmlStringEncoder(char[] chars) {
		return new XMLStringEncoder(chars);
	}


	// ********** wrappers **********

	/**
	 * Wrap the specified transformer converting it
	 * into one that converts the same input object into an <em>iterator</em>
	 * of objects of the same type as the input object.
	 * 
	 * @param <I> input: the type of the object passed to the transformer; also the
	 *   type of object returned by the output iterator
	 * @see IterableTransformerWrapper
	 */
	public static <I> Transformer<I, Iterator<? extends I>> iterableTransformerWrapper(Transformer<? super I, ? extends Iterable<? extends I>> transformer) {
		return new IterableTransformerWrapper<I>(transformer);
	}

	/**
	 * Return a transformer that wraps the specified transformer and checks
	 * for <code>null</code> input before forwarding the input to the specified
	 * transformer. If the input is <code>null</code>, the transformer will
	 * return <code>null</code>.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see NullCheckTransformerWrapper
	 * @see #nullCheck(Transformer, Object)
	 */
	public static <I, O> Transformer<I, O> nullCheck(Transformer<? super I, ? extends O> transformer) {
		return nullCheck(transformer, null);
	}

	/**
	 * Return a transformer that wraps the specified transformer and checks
	 * for <code>null</code> input before forwarding the input to the specified
	 * transformer. If the input is <code>null</code>, the transformer will
	 * return the specified output value.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see NullCheckTransformerWrapper
	 * @see #nullCheck(Transformer)
	 */
	public static <I, O> Transformer<I, O> nullCheck(Transformer<? super I, ? extends O> transformer, O nullOutput) {
		return new NullCheckTransformerWrapper<I, O>(transformer, nullOutput);
	}

	/**
	 * Return a transformer that can have its wrapped transformer changed,
	 * allowing a client to change a previously-supplied transformer's
	 * behavior mid-stream.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see TransformerWrapper
	 */
	public static <I, O> Transformer<I, O> wrap(Transformer<? super I, ? extends O> transformer) {
		return new TransformerWrapper<I, O>(transformer);
	}


	// ********** casting **********

	/**
	 * Return a transformer that simply casts the specified transformer's return type.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <X> intermediate: the type of object returned by the wrapped
	 *   transformer
	 * @param <O> output: the type of object returned by the transformer - this
	 *   is the same object returned by the wrapped transformer, simply
	 *   cast to <code>O</code>
	 * @see CastingTransformerWrapper
	 * @see #upcast(Transformer)
	 * @see #downcast(Transformer)
	 */
	public static <I, X, O> Transformer<I, O> cast(Transformer<? super I, ? extends X> transformer) {
		return new CastingTransformerWrapper<I, X, O>(transformer);
	}

	/**
	 * Return a transformer that simply downcasts the specified transformer's return type.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <X> intermediate: the type of object returned by the wrapped
	 *   transformer
	 * @param <O> output: the type of object returned by the transformer - this
	 *   is the same object returned by the wrapped transformer, simply
	 *   cast to <code>O</code>
	 * @see DowncastingTransformerWrapper
	 * @see #cast(Transformer)
	 * @see #upcast(Transformer)
	 */
	public static <I, X, O extends X> Transformer<I, O> downcast(Transformer<? super I, ? extends X> transformer) {
		return new DowncastingTransformerWrapper<I, X, O>(transformer);
	}

	/**
	 * Return a transformer that simply upcasts the specified transformer's return type.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of object returned by the transformer - this
	 *   is the same object returned by the wrapped transformer, simply
	 *   cast to <code>O</code>
	 * @param <X> intermediate: the type of object returned by the wrapped
	 *   transformer
	 * @see UpcastingTransformerWrapper
	 * @see #cast(Transformer)
	 * @see #downcast(Transformer)
	 */
	public static <I, O, X extends O> Transformer<I, O> upcast(Transformer<? super I, ? extends X> transformer) {
		return new UpcastingTransformerWrapper<I, O, X>(transformer);
	}


	// ********** safe **********

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream} and <code>null</code> will be
	 * returned.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see SafeTransformer
	 * @see DefaultExceptionHandler
	 */
	public static <I, O> Transformer<I, O> protect(Transformer<? super I, ? extends O> transformer) {
		return protect(transformer, DefaultExceptionHandler.instance(), null);
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception's stack trace will be printed to {@link System#err
	 * the "standard" error output stream}
	 * and specified output will be returned.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see SafeTransformer
	 * @see DefaultExceptionHandler
	 */
	public static <I, O> Transformer<I, O> protect(Transformer<? super I, ? extends O> transformer, O exceptionOutput) {
		return protect(transformer, DefaultExceptionHandler.instance(), exceptionOutput);
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception will be passed to the specified exception handler
	 * and <code>null</code> will be returned.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see SafeTransformer
	 */
	public static <I, O> Transformer<I, O> protect(Transformer<? super I, ? extends O> transformer, ExceptionHandler exceptionHandler) {
		return protect(transformer, exceptionHandler, null);
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception will be passed to the specified exception handler
	 * and specified output will be returned.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see SafeTransformer
	 */
	public static <I, O> Transformer<I, O> protect(Transformer<? super I, ? extends O> transformer, ExceptionHandler exceptionHandler, O exceptionOutput) {
		return new SafeTransformer<I, O>(transformer, exceptionHandler, exceptionOutput);
	}


	// ********** pass-thru **********

	/**
	 * Return a transformer that will perform no transformation at all;
	 * it will simply return the input if it is not <code>null</code>.
	 * A <code>null</code> input is transformed into the specified
	 * output.
	 * 
	 * @param <I> input: the type of the object passed to the transformer
	 * @see PassThruTransformer
	 * @see #passThruTransformer()
	 */
	public static <I> Transformer<I, I> passThruTransformer(I nullOutput) {
		return new PassThruTransformer<I>(nullOutput);
	}

	/**
	 * Return a transformer that will perform no transformation at all;
	 * it will simply return the input untransformed.
	 * @param <I> input: the type of the object passed to the transformer
	 * @see PassThruTransformer
	 * @see #passThruTransformer(Object)
	 */
	@SuppressWarnings("unchecked")
	public static <I> Transformer<I, I> passThruTransformer() {
		return PASS_THRU_TRANSFORMER;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final Transformer PASS_THRU_TRANSFORMER = new PassThruTransformer(null);


	// ********** reflection **********

	/**
	 * Return a transformer that clones its input.
	 * @param <I> input: the type of the object passed to the transformer
	 * @see CloneTransformer
	 */
	public static <I extends Cloneable> Transformer<I, I> cloneTransformer() {
		return CloneTransformer.instance();
	}

	/**
	 * Return a transformer that transforms a {@link Class} into an instance
	 * by calling {@link Class#newInstance()}. Checked exceptions are converted
	 * to {@link RuntimeException}s.
	 * 
	 * @param <O> output: the type of the object returned by the transformer (and
	 *   the class, or superclass of the class, passed to the transformer)
	 * @see InstantiationTransformer
	 */
	public static <O> Transformer<Class<? extends O>, O> instantiationTransformer() {
		return InstantiationTransformer.instance();
	}

	/**
	 * Return a transformer that uses Java reflection to transform an object
	 * into the value of its field with the specified name.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see FieldTransformer
	 */
	public static <I, O> Transformer<I, O> fieldTransformer(String fieldName) {
		return new FieldTransformer<I, O>(fieldName);
	}

	/**
	 * Return a transformer that uses Java reflection to transform an object
	 * into the value returned by its zero-argument method with the specified
	 * name.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see MethodTransformer
	 */
	public static <I, O> Transformer<I, O> methodTransformer(String methodName) {
		return new MethodTransformer<I, O>(methodName);
	}


	// ********** misc **********

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
	 * @see TransformerChain
	 */
	public static <I, O> Transformer<I, O> chain(@SuppressWarnings("rawtypes") Transformer[] transformers) {
		return new TransformerChain<I, O>(transformers);
	}

	/**
	 * Return a transformer that will throw an {@link UnsupportedOperationException exception}
	 * if {@link Transformer#transform(Object)} is called. This is useful in
	 * situations where a transformer is optional and the default transformer
	 * should not be used.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see DisabledTransformer
	 */
	public static <I, O> Transformer<I, O> disabledTransformer() {
		return DisabledTransformer.instance();
	}

	/**
	 * Return a transformer that will transform an input into its value
	 * in the specified map.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see MapTransformer
	 */
	public static <I, O> Transformer<I, O> mapTransformer(Map<? super I, ? extends O> map) {
		return new MapTransformer<I, O>(map);
	}

	/**
	 * Return a transformer that will transform every input into <code>null</code>.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see NullOutputTransformer
	 */
	public static <I, O> Transformer<I, O> nullOutputTransformer() {
		return NullOutputTransformer.instance();
	}

	/**
	 * Return a transformer that will transform any object into the specified
	 * output.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see StaticOutputTransformer
	 * @see #nullOutputTransformer()
	 */
	public static <I, O> Transformer<I, O> staticOutputTransformer(O output) {
		return new StaticOutputTransformer<I, O>(output);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private TransformerTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
