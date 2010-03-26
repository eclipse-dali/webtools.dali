/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

import java.io.Serializable;

/**
 * Used by various "pluggable" classes to transform objects
 * in both directions.
 * 
 * If anyone can come up with a better class name
 * and/or method name, I would love to hear it.  ~bjv
 */
public interface BidiTransformer<T1, T2> extends Transformer<T1, T2> {

	/**
	 * Return the "reverse-transformed" object.
	 * The semantics of "reverse-transform" is determined by the
	 * contract between the client and the server.
	 */
	T1 reverseTransform(T2 o);


	final class Null<S1, S2> implements BidiTransformer<S1, S2>, Serializable {
		@SuppressWarnings("rawtypes")
		public static final BidiTransformer INSTANCE = new Null();
		@SuppressWarnings("unchecked")
		public static <R1, R2> BidiTransformer<R1, R2> instance() {
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
		// simply return the object, unchanged
		@SuppressWarnings("unchecked")
		public S1 reverseTransform(S2 o) {
			return (S1) o;
		}
		@Override
		public String toString() {
			return "BidiTransformer.Null"; //$NON-NLS-1$
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

	final class Disabled<S1, S2> implements BidiTransformer<S1, S2>, Serializable {
		@SuppressWarnings("rawtypes")
		public static final BidiTransformer INSTANCE = new Disabled();
		@SuppressWarnings("unchecked")
		public static <R1, R2> BidiTransformer<R1, R2> instance() {
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
		// throw an exception
		public S1 reverseTransform(S2 o) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return "BidiTransformer.Disabled"; //$NON-NLS-1$
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

}
