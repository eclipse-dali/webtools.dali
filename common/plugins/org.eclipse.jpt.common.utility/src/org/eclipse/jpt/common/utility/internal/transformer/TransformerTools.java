/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import java.util.Iterator;
import java.util.Map;
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.exception.ExceptionHandler;
import org.eclipse.jpt.common.utility.factory.Factory;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ClassTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.exception.DefaultExceptionHandler;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Transformer} utility methods.
 */
public final class TransformerTools {

	// ********** adapters **********

	/**
	 * Adapt the specified {@link Closure} to the {@link Transformer} interface.
	 * The returned transformer will always return <code>null</code>.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> adapt(Closure<? super I> closure) {
		return new ClosureTransformer<I, O>(closure);
	}

	/**
	 * Adapt the specified {@link Command} to the {@link Transformer} interface.
	 * The returned transformer will always return <code>null</code> and its
	 * input will be ignored.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> adapt(Command command) {
		return new CommandTransformer<I, O>(command);
	}

	/**
	 * Adapt the specified {@link Factory} to the {@link Transformer} interface.
	 * The returned transformer will return the factory's output and its
	 * input will be ignored.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 */
	public static <I, O> Transformer<I, O> adapt(Factory<? extends O> factory) {
		return new FactoryTransformer<I, O>(factory);
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @see ObjectToStringTransformer
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @see ObjectToStringTransformer
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @see ObjectToStringTransformer
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @see ObjectToStringTransformer
	 * @see Object#toString()
	 * @see #objectToStringTransformer()
	 * @see #objectToStringTransformer(String)
	 * @see #stringFromObjectTransformer()
	 */
	public static <I> Transformer<I, String> objectToStringTransformer_() {
		return ObjectToStringTransformer.instance();
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
	 * @see NotBooleanTransformer
	 * @see #notBooleanTransformer()
	 */
	public static Transformer<Boolean, Boolean> notBooleanTransformer(Boolean nullBoolean) {
		return nullCheck(notBooleanTransformer(), nullBoolean);
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
	 * @see NotBooleanTransformer
	 * @see #notBooleanTransformer(Boolean)
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
	 * Return a transformer that adapts the specified {@link Predicate
	 * predicate} to the {@link Transformer transformer} interface.
	 * <p>
	 * <strong>NB:</strong> If passed a <code>null</code> <em>input</em>, the
	 * transformer will simply return a <code>null</code> <em>output</em>
	 * without forwarding the <em>input</em> to the predicate.
	 * @param <I> input: the type of the object passed to the transformer (and
	 *   forwarded to the wrapped predicate)
	 * @see PredicateTransformer
	 * @see #adapt(Predicate, Boolean)
	 * @see #adapt_(Predicate)
	 */
	public static <I> Transformer<I, Boolean> adapt(Predicate<? super I> predicate) {
		return adapt(predicate, null);
	}

	/**
	 * Return a transformer that adapts the specified {@link Predicate
	 * predicate} to the {@link Transformer transformer} interface.
	 * <p>
	 * <strong>NB:</strong> If passed a <code>null</code> <em>input</em>, the
	 * transformer will simply return the specified value
	 * without forwarding the <em>input</em> to the predicate.
	 * @param <I> input: the type of the object passed to the transformer (and
	 *   forwarded to the wrapped predicate)
	 * @see PredicateTransformer
	 * @see #adapt(Predicate)
	 * @see #adapt_(Predicate)
	 */
	public static <I> Transformer<I, Boolean> adapt(Predicate<? super I> predicate, Boolean nullBoolean) {
		return nullCheck(adapt_(predicate), nullBoolean);
	}

	/**
	 * Return a transformer that adapts the specified {@link Predicate
	 * predicate} to the {@link Transformer transformer} interface.
	 * <p>
	 * <strong>NB:</strong> If passed a <code>null</code> <em>input</em>, the
	 * transformer will pass a <code>null</code> to the predicate.
	 * @param <I> input: the type of the object passed to the transformer (and
	 *   forwarded to the wrapped predicate)
	 * @see PredicateTransformer
	 * @see #adapt(Predicate)
	 * @see #adapt(Predicate, Boolean)
	 */
	public static <I> Transformer<I, Boolean> adapt_(Predicate<? super I> predicate) {
		return new PredicateTransformer<I>(predicate);
	}


	// ********** string **********

	/**
	 * Return a transformer that transforms a {@link String} to the appropriate
	 * {@link Boolean}.
	 * <p>
	 * <strong>NB:</strong> The transformer will return <code>null</code>
	 * if it is passed a <code>null</code> input.
	 * @see StringToBooleanTransformer
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
	 * @see StringToBooleanTransformer
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
	 * @see StringToBooleanTransformer
	 * @see Boolean#valueOf(String)
	 * @see #stringToBooleanTransformer(Boolean)
	 * @see #stringToBooleanTransformer()
	 */
	public static Transformer<String, Boolean> stringToBooleanTransformer_() {
		return StringToBooleanTransformer.instance();
	}

	/**
	 * Return a transformer that transforms a {@link String} to the appropriate
	 * {@link Integer}.
	 * <p>
	 * <strong>NB:</strong> The transformer will return <code>null</code>
	 * if it is passed a <code>null</code> input.
	 * @see StringToIntegerTransformer
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
	 * @see StringToIntegerTransformer
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
	 * @see StringToIntegerTransformer
	 * @see Integer#valueOf(String)
	 * @see #stringToIntegerTransformer()
	 * @see #stringToIntegerTransformer(Integer)
	 */
	public static Transformer<String, Integer> stringToIntegerTransformer_() {
		return StringToIntegerTransformer.instance();
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
	 * Return a transformer that will replace any of the XML "predefined
	 * entities" with an XML <em>character reference</em>:
	 * <code>'&' => "&amp;#x26;"</code>
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see ThreadLocalTransformer
	 */
	public static <I, O> ThreadLocalTransformer<I, O> threadLocalTransformer() {
		return threadLocalTransformer(NullOutputTransformer.<I, O>instance());
	}

	/**
	 * Return a transformer that allows the client to specify a different transformer
	 * for each thread. If there is no transformer for the current thread, the
	 * specified default transformer is used.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see ThreadLocalTransformer
	 */
	public static <I, O> ThreadLocalTransformer<I, O> threadLocalTransformer(Transformer<? super I, ? extends O> defaultTransformer) {
		return new ThreadLocalTransformer<I, O>(defaultTransformer);
	}


	// ********** wrappers **********

	/**
	 * Wrap the specified transformer converting it
	 * into one that converts the same input object into an <em>iterator</em>
	 * of objects of the same type as the input object.
	 * @param <I> input: the type of the object passed to the transformer; also the
	 *   type of object returned by the output iterator
	 * @see IterableTransformerWrapper
	 */
	public static <I> Transformer<I, Iterator<? extends I>> toIterator(Transformer<? super I, ? extends Iterable<? extends I>> transformer) {
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
	public static <I, O> TransformerWrapper<I, O> wrap(Transformer<? super I, ? extends O> transformer) {
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
	 * @see SafeTransformerWrapper
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see SafeTransformerWrapper
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see SafeTransformerWrapper
	 */
	public static <I, O> Transformer<I, O> safe(Transformer<? super I, ? extends O> transformer, ExceptionHandler exceptionHandler) {
		return safe(transformer, exceptionHandler, null);
	}

	/**
	 * Return a transformer that wraps the specified transformer and handles
	 * any exceptions thrown during the transformation. If an exception is
	 * thrown, the exception will be passed to the specified exception handler
	 * and specified output will be returned.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see SafeTransformerWrapper
	 */
	public static <I, O> Transformer<I, O> safe(Transformer<? super I, ? extends O> transformer, ExceptionHandler exceptionHandler, O exceptionOutput) {
		return new SafeTransformerWrapper<I, O>(transformer, exceptionHandler, exceptionOutput);
	}


	// ********** pass-thru **********

	/**
	 * Return a transformer that will perform no transformation at all;
	 * it will simply return the input untransformed.
	 * @param <I> input: the type of the object passed to the transformer
	 *   (and returned by the transformer)
	 * @see PassThruTransformer
	 * @see #passThruTransformer(Object)
	 */
	public static <I> Transformer<I, I> passThruTransformer() {
		return PassThruTransformer.instance();
	}

	/**
	 * Return a transformer that will perform no transformation at all;
	 * it will simply return the input if it is not <code>null</code>.
	 * The transformer will convert a <code>null</code> input into the specified
	 * output.
	 * @param <I> input: the type of the object passed to the transformer
	 *   (and returned by the transformer)
	 * @see PassThruTransformer
	 * @see #passThruTransformer()
	 */
	@SuppressWarnings("unchecked")
	public static <I> Transformer<I, I> passThruTransformer(I nullOutput) {
		return (Transformer<I, I>) nullCheck(passThruTransformer(), nullOutput);
	}


	// ********** reflection **********

	/**
	 * Return a transformer that clones its input.
	 * @param <I> input: the type of the object passed to the transformer
	 *   (and returned by the transformer)
	 * @see CloneTransformer
	 */
	public static <I extends Cloneable> Transformer<I, I> cloneTransformer() {
		return CloneTransformer.instance();
	}

	/**
	 * Return a transformer that transforms a {@link Class} into an instance
	 * by calling {@link Class#newInstance()}. Checked exceptions are converted
	 * to {@link RuntimeException}s.
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
	 * <p>
	 * <strong>NB:</strong> The actual field is determined at execution time,
	 * not construction time. As a result, the transformer can be used to emulate
	 * "duck typing".
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see FieldTransformer
	 */
	public static <I, O> Transformer<I, O> get(String fieldName) {
		return new FieldTransformer<I, O>(fieldName);
	}

	/**
	 * Return a transformer that uses Java reflection to transform an object
	 * into the value returned by its zero-argument method with the specified
	 * name.
	 * <p>
	 * <strong>NB:</strong> The actual method is determined at execution time,
	 * not construction time. As a result, the transformer can be used to emulate
	 * "duck typing".
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see MethodTransformer
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see MethodTransformer
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see MethodTransformer
	 */
	public static <I, O> Transformer<I, O> execute(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
		return new MethodTransformer<I, O>(methodName, parameterTypes, arguments);
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see TransformerChain
	 */
	public static <I, O> Transformer<I, O> chain(Iterable<Transformer<?, ?>> transformers) {
		return new TransformerChain<I, O>(transformers);
	}


	// ********** conditional **********

	/**
	 * Return a transformer that passes its input to the specified predicate to
	 * determine whether to forward the input to the specified transformer.
	 * If the predicate evaluates to <code>false</code>, the transformer returns
	 * <code>null</code>.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see ClosureTransformer
	 */
	public static <I, O> Transformer<I, O> conditionalTransformer(Predicate<? super I> predicate, Transformer<? super I, ? extends O> transformer) {
		return conditionalTransformer(predicate, transformer, NullOutputTransformer.<I, O>instance());
	}

	/**
	 * Return a transformer that passes its input to the specified predicate to
	 * determine which of the two specified transformers to execute.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see ClosureTransformer
	 */
	public static <I, O> Transformer<I, O> conditionalTransformer(Predicate<? super I> predicate, Transformer<? super I, ? extends O> trueTransformer, Transformer<? super I, ? extends O> falseTransformer) {
		return new ConditionalTransformer<I, O>(predicate, trueTransformer, falseTransformer);
	}


	// ********** switch **********

	/**
	 * @see #switchTransformer(Iterable)
	 */
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
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see SwitchTransformer
	 */
	public static <I, O> Transformer<I, O> switchTransformer(Iterable<Association<Predicate<? super I>, Transformer<? super I, ? extends O>>> transformers) {
		return switchTransformer(transformers, NullOutputTransformer.<I, O>instance());
	}

	/**
	 * Return a transformer that loops over the specified set of
	 * predicate/transformer pairs, passing its input to each predicate to
	 * determine which of the transformers to execute. Only the first
	 * transformer whose predicate evaluates to <code>true</code> is executed,
	 * even if other, following, predicates would evaluate to <code>true</code>.
	 * If none of the predicates evaluates to <code>true</code>, the default transformer
	 * is executed.
	 * @param <I> input: the type of the object passed to the transformer
	 * @param <O> output: the type of the object returned by the transformer
	 * @see SwitchTransformer
	 */
	public static <I, O> Transformer<I, O> switchTransformer(Iterable<Association<Predicate<? super I>, Transformer<? super I, ? extends O>>> transformers, Transformer<? super I, ? extends O> defaultTransformer) {
		return new SwitchTransformer<I, O>(transformers, defaultTransformer);
	}


	// ********** disabled **********

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


	// ********** map **********

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


	// ********** static output **********

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
