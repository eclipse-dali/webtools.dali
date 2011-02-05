/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import java.io.Serializable;

/**
 * Used by various "pluggable" classes to transform objects.
 * Transform an object of type <code>T1</code> to an object of type
 * <code>T2</code>.
 * 
 * @param <T1> the type of the object passed to the transformer
 * @param <T2> the type of the object returned by the transformer
 */
public interface Transformer<T1, T2> {

	/**
	 * Return the transformed object.
	 * The semantics of "transform" is determined by the
	 * contract between the client and the server.
	 */
	T2 transform(T1 o);


	/**
	 * A "null" transformer will perform no transformation at all;
	 * it will simply return the object "untransformed".
	 */
	final class Null<S1, S2> implements Transformer<S1, S2>, Serializable {
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
		// simply return the object, unchanged
		@SuppressWarnings("unchecked")
		public S2 transform(S1 o) {
			return (S2) o;
		}
		@Override
		public String toString() {
			return "Transformer.Null"; //$NON-NLS-1$
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
	final class Disabled<S1, S2> implements Transformer<S1, S2>, Serializable {
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
		public S2 transform(S1 o) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return "Transformer.Disabled"; //$NON-NLS-1$
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

}
