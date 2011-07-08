/*******************************************************************************
 * Copyright (c) 2005, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Used by various "pluggable" classes to filter objects.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <T> the type of objects to be filtered
 */
public interface Filter<T> {

	/**
	 * Return whether the specified object is "accepted" by the
	 * filter. The semantics of "accept" is determined by the
	 * contract between the client and the server.
	 */
	boolean accept(T o);


	/**
	 * Singleton implementation of the filter interface that accepts all the
	 * objects (i.e. it does no filtering).
	 */
	final class Null<S>
		implements Filter<S>, Serializable
	{
		@SuppressWarnings("rawtypes")
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
		public boolean accept(S o) {
			return true;
		}
		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

	/**
	 * Singleton implementation of the filter interface that accepts none of the
	 * objects (i.e. it filters out all the objects).
	 */
	final class Opaque<S>
		implements Filter<S>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final Filter INSTANCE = new Opaque();
		@SuppressWarnings("unchecked")
		public static <R> Filter<R> instance() {
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
		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

	/**
	 * Singleton implementation of the filter interface that throws an exception
	 * if called.
	 */
	final class Disabled<S>
		implements Filter<S>, Serializable
	{
		@SuppressWarnings("rawtypes")
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
		public boolean accept(S o) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
