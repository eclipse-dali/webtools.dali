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

import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * {@link Transformer} utility methods.
 */
public final class TransformerTools {

	// ********** factory methods **********

	/**
	 * Return a transformer that converts a {@link String} to the appropriate
	 * {@link Boolean}.
	 * @see BooleanStringTransformer
	 * @see Boolean#valueOf(String)
	 */
	public static Transformer<String, Boolean> booleanStringTransformer() {
		return BooleanStringTransformer.instance();
	}

	/**
	 * Return a transformer that uses Java reflection to transform an object
	 * into the value of its field with the specified name.
	 * @see FieldTransformer
	 */
	public static <I, O> Transformer<I, O> fieldTransformer(String fieldName) {
		return new FieldTransformer<I, O>(fieldName);
	}

	/**
	 * Return a transformer that converts a {@link String} to the appropriate
	 * {@link Integer}.
	 * @see IntegerStringTransformer
	 * @see Integer#valueOf(String)
	 */
	public static Transformer<String, Integer> integerStringTransformer() {
		return IntegerStringTransformer.instance();
	}

	/**
	 * Wrap the specified transformer converting it
	 * into one that converts the same input object into an <em>iterator</em>
	 * of objects of the same type as the input object.
	 * 
	 * @param <I> input: the type of the object passed to the transformer; also the
	 *   type of object returned by the output iterator
	 */
	public static <I> IterableTransformerWrapper<I> iterableTransformerWrapper(Transformer<? super I, ? extends Iterable<? extends I>> transformer) {
		return new IterableTransformerWrapper<I>(transformer);
	}

	/**
	 * Return a transformer that simply casts the specified transformer's return type.
	 * @see LateralTransformerWrapper
	 */
	public static <I, X, O> LateralTransformerWrapper<I, X, O> lateralTransformer(Transformer<? super I, ? extends X> transformer) {
		return new LateralTransformerWrapper<I, X, O>(transformer);
	}

	/**
	 * Return a transformer that uses Java reflection to transform an object
	 * into the value returned by its zero-argument method with the specified
	 * name.
	 * @see MethodTransformer
	 */
	public static <I, O> Transformer<I, O> methodTransformer(String methodName) {
		return new MethodTransformer<I, O>(methodName);
	}

	/**
	 * @see #nonNullBooleanTransformer(Boolean)
	 * @see NonNullBooleanTransformer
	 */
	public static Transformer<Boolean, Boolean> nonNullBooleanTransformer(boolean nullValue) {
		return NonNullBooleanTransformer.valueOf(nullValue);
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
	 * @see NonNullBooleanTransformer
	 */
	public static Transformer<Boolean, Boolean> nonNullBooleanTransformer(Boolean nullValue) {
		return NonNullBooleanTransformer.valueOf(nullValue);
	}

	/**
	 * Return a transformer that will transform any object into the specified
	 * non-<code>null</code> object.
	 * @see NonNullStaticTransformer
	 */
	public static <I, O> Transformer<I, O> nonNullStaticTransformer(O output) {
		return new NonNullStaticTransformer<I, O>(output);
	}

	/**
	 * Return a transformer that will transform an object into the string
	 * returned by its {@link Object#toString()} method. A <code>null</code>
	 * object is transformed into the string <code>"null"</code>.
	 * @see NonNullStringObjectTransformer
	 */
	public static <I> Transformer<I, String> nonNullStringObjectTransformer() {
		return nonNullStringObjectTransformer(String.valueOf((Object) null));
	}

	/**
	 * Return a transformer that will transform an object into the string
	 * returned by its {@link Object#toString()} method. A <code>null</code>
	 * object is transformed into the specified
	 * non-<code>null</code> string.
	 * @see NonNullStringObjectTransformer
	 */
	public static <I> Transformer<I, String> nonNullStringObjectTransformer(String nullString) {
		return new NonNullStringObjectTransformer<I>(nullString);
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
	 * Use a {@link #nonNullBooleanTransformer(Boolean)} to specify a value for when a
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
	 * @see NotNullObjectTransformer
	 */
	public static <I> Transformer<I, Boolean> notNullObjectTransformer() {
		return NotNullObjectTransformer.<I>instance();
	}

	/**
	 * Return a transformer that will transform an object to a
	 * {@link Boolean}:<ul>
	 * <li>If the object is <code>null</code>,
	 * the transformer will return {@link Boolean#TRUE}.
	 * <li>If the object is <em>not</em> <code>null</code>,
	 * the transformer will return {@link Boolean#FALSE}.
	 * </ul>
	 * @see NullObjectTransformer
	 */
	public static <I> Transformer<I, Boolean> nullObjectTransformer() {
		return NullObjectTransformer.<I>instance();
	}

	/**
	 * Return a transformer that will transform any object into the specified
	 * object.
	 * @see StaticTransformer
	 */
	public static <I, O> Transformer<I, O> staticTransformer(O output) {
		return new StaticTransformer<I, O>(output);
	}

	/**
	 * Return a transformer that will transform an object into the string
	 * returned by its {@link Object#toString()} method. A <code>null</code>
	 * object is transformed into <code>null</code>.
	 * @see StringObjectTransformer
	 */
	public static <I> Transformer<I, String> stringObjectTransformer() {
		return StringObjectTransformer.<I>instance();
	}

	/**
	 * Return a transformer that simply casts the specified transformer's return type.
	 * @see SubTransformerWrapper
	 */
	public static <I, X, O extends X> SubTransformerWrapper<I, X, O> subTransformer(Transformer<? super I, ? extends X> transformer) {
		return new SubTransformerWrapper<I, X, O>(transformer);
	}

	/**
	 * Return a transformer that simply casts the specified transformer's return type.
	 * @see SuperTransformerWrapper
	 */
	public static <I, O, X extends O> SuperTransformerWrapper<I, O, X> superTransformer(Transformer<? super I, ? extends X> transformer) {
		return new SuperTransformerWrapper<I, O, X>(transformer);
	}

	/**
	 * Return a transformer that can have its wrapped transformer changed,
	 * allowing a client to change a previously-supplied transformer's
	 * behavior mid-stream.
	 * @see TransformerWrapper
	 */
	public static <I, O> Transformer<I, O> transformerWrapper(Transformer<? super I, ? extends O> transformer) {
		return new TransformerWrapper<I, O>(transformer);
	}

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


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private TransformerTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
