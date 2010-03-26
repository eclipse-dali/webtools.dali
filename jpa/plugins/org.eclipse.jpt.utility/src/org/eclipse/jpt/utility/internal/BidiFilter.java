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

import org.eclipse.jpt.utility.Filter;

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


	final class Null<S> implements BidiFilter<S>, Serializable {
		@SuppressWarnings("rawtypes")
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
		public boolean accept(S o) {
			return true;
		}
		// nothing is "reverse-filtered" - everything is accepted
		public boolean reverseAccept(S o) {
			return true;
		}
		@Override
		public String toString() {
			return "BidiFilter.Null"; //$NON-NLS-1$
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

	final class Opaque<S> implements BidiFilter<S>, Serializable {
		@SuppressWarnings("rawtypes")
		public static final BidiFilter INSTANCE = new Opaque();
		@SuppressWarnings("unchecked")
		public static <R> BidiFilter<R> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Opaque() {
			super();
		}
		// everything is filtered - nothing is accepted
		public boolean accept(S o) {
			return false;
		}
		// everything is "reverse-filtered" - nothing is accepted
		public boolean reverseAccept(S o) {
			return false;
		}
		@Override
		public String toString() {
			return "BidiFilter.Opaque"; //$NON-NLS-1$
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

	final class Disabled<S> implements BidiFilter<S>, Serializable {
		@SuppressWarnings("rawtypes")
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
		public boolean accept(S o) {
			throw new UnsupportedOperationException();
		}
		// throw an exception
		public boolean reverseAccept(S o) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return "BidiFilter.Disabled"; //$NON-NLS-1$
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

}
