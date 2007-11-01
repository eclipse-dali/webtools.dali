/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Used by various "pluggable" classes to filter objects
 * in both directions.
 * 
 * If anyone can come up with a better class name
 * and/or method name, I would love to hear it.  ~bjv
 */
public interface BidiFilter<T> extends Filter<T> {

	/**
	 * Return whether the specified object is "accepted" by the
	 * "reverse" filter. What that means is determined by the client.
	 */
	boolean reverseAccept(T o);


	final class Null<S> implements BidiFilter<S> {
		@SuppressWarnings("unchecked")
		public static final BidiFilter INSTANCE = new Null();
		@SuppressWarnings("unchecked")
		public static <R> BidiFilter<R> instance() {
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
		// nothing is "reverse-filtered" - everything is accepted
		public boolean reverseAccept(S o) {
			return true;
		}
		@Override
		public String toString() {
			return "BidiFilter.Null";
		}
	}

	final class Disabled<S> implements BidiFilter<S> {
		@SuppressWarnings("unchecked")
		public static final BidiFilter INSTANCE = new Disabled();
		@SuppressWarnings("unchecked")
		public static <R> BidiFilter<R> instance() {
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
		// throw an exception
		public boolean reverseAccept(S o) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return "BidiFilter.Disabled";
		}
	}

}
