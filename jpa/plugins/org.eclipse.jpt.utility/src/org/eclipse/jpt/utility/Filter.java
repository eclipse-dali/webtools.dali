/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility;

/**
 * Used by various "pluggable" classes to filter objects.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Filter<T> {

	/**
	 * Return whether the specified object is "accepted" by the
	 * filter. The semantics of "accept" is determined by the
	 * contract between the client and the server.
	 */
	boolean accept(T o);


	final class Null<S> implements Filter<S> {
		@SuppressWarnings("unchecked")
		public static final Filter INSTANCE = new Null();
		@SuppressWarnings("unchecked")
		public static <R> Filter<R> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		// nothing is filtered - everything is accepted
		public boolean accept(S next) {
			return true;
		}
		@Override
		public String toString() {
			return "Filter.Null";
		}
	}

	final class Disabled<S> implements Filter<S> {
		@SuppressWarnings("unchecked")
		public static final Filter INSTANCE = new Disabled();
		@SuppressWarnings("unchecked")
		public static <R> Filter<R> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Disabled() {
			super();
		}
		// throw an exception
		public boolean accept(S next) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return "Filter.Disabled";
		}
	}

}
