/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.transformer;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Used by various "pluggable" classes to transform objects.
 * Transform an object of type <code>T1</code> to an object of type
 * <code>T2</code>.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <I> input: the type of the object passed to the transformer
 * @param <O> output: the type of the object returned by the transformer
 */
public interface Transformer<I, O> {

	/**
	 * Return the transformed object.
	 * The semantics of "transform" is determined by the
	 * contract between the client and the server.
	 */
	O transform(I input);


	/**
	 * A "non" transformer will perform no transformation at all;
	 * it will simply return the object "untransformed".
	 */
	final class Non<T>
		implements Transformer<T, T>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final Transformer INSTANCE = new Non();
		@SuppressWarnings("unchecked")
		public static <R> Transformer<R, R> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Non() {
			super();
		}
		// simply return the object, unchanged
		public T transform(T o) {
			return o;
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

	/**
	 * A "null" transformer will always return <code>null</code>.
	 */
	final class Null<I, O>
		implements Transformer<I, O>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final Transformer INSTANCE = new Null();
		@SuppressWarnings("unchecked")
		public static <R1, R2> Transformer<R1, R2> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		// simply return null
		public O transform(I o) {
			return null;
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

	/**
	 * A "disabled" transformer will throw an exception if
	 * {@link #transform(Object)} is called. This is useful in situations
	 * where a transformer is optional and the default transformer should
	 * not be used.
	 */
	final class Disabled<I, O>
		implements Transformer<I, O>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final Transformer INSTANCE = new Disabled();
		@SuppressWarnings("unchecked")
		public static <R1, R2> Transformer<R1, R2> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Disabled() {
			super();
		}
		// throw an exception
		public O transform(I o) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
