/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Used by various "pluggable" classes to transform objects.
 * Transform an object of type T1 to an object of type T2.
 */
public interface Transformer<T1, T2> {

	/**
	 * Return the transformed object.
	 * The semantics of "transform" is determined by the
	 * contract between the client and the server.
	 */
	T2 transform(T1 o);


	final class Null<S1, S2> implements Transformer<S1, S2> {
		@SuppressWarnings("unchecked")
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
			return "Transformer.Null";
		}
	}

	final class Disabled<S1, S2> implements Transformer<S1, S2> {
		@SuppressWarnings("unchecked")
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
			return "Transformer.Disabled";
		}
	}

}
