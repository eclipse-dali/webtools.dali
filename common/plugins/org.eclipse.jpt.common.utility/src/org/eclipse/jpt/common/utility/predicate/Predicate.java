/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.predicate;

import java.io.Serializable;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A predicate can be used to determine whether an object belongs
 * to a particular set or has a particular property (e.g.
 * {@link org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable
 * when filtering a collection of objects}).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <V> the type of objects to be evaluated
 * 
 * @see org.eclipse.jpt.common.utility.internal.predicate.PredicateTools
 */
public interface Predicate<V> {

	/**
	 * Return whether the specified object is "accepted" by the
	 * filter. The semantics of "accept" is determined by the
	 * contract between the client and the server.
	 */
	boolean evaluate(V variable);


	/**
	 * Convenience predicate implementation that evaluates any object to
	 * <code>true</code> and provides a helpful {@link #toString()}.
	 */
	class Adapter<V>
		implements Predicate<V>
	{
		public boolean evaluate(V variable) {
			return true;
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	/**
	 * Singleton predicate implementation that always evaluates to
	 * <code>true</code>.
	 */
	final class True<V>
		implements Predicate<V>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final Predicate INSTANCE = new True();
		@SuppressWarnings("unchecked")
		public static <V> Predicate<V> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private True() {
			super();
		}
		// everything is true
		public boolean evaluate(V variable) {
			return true;
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
	 * Singleton predicate implementation that always evaluates to
	 * <code>false</code>.
	 */
	final class False<V>
		implements Predicate<V>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final Predicate INSTANCE = new False();
		@SuppressWarnings("unchecked")
		public static <V> Predicate<V> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private False() {
			super();
		}
		// everything is false
		public boolean evaluate(V variable) {
			return false;
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
	 * Singleton predicate implementation that evaluates whether an object is
	 * <em>not</em> <code>null</code>.
	 */
	final class NotNull<V>
		implements Predicate<V>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final Predicate INSTANCE = new NotNull();
		@SuppressWarnings("unchecked")
		public static <V> Predicate<V> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private NotNull() {
			super();
		}
		// return whether the variable is not null
		public boolean evaluate(V variable) {
			return variable != null;
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
	 * Singleton predicate implementation that evaluates whether an object is
	 * <code>null</code>.
	 */
	final class Null<V>
		implements Predicate<V>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final Predicate INSTANCE = new Null();
		@SuppressWarnings("unchecked")
		public static <V> Predicate<V> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		// return whether the variable is null
		public boolean evaluate(V variable) {
			return variable == null;
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
	 * Singleton predicate implementation that throws an exception if invoked.
	 */
	final class Disabled<V>
		implements Predicate<V>, Serializable
	{
		@SuppressWarnings("rawtypes")
		public static final Predicate INSTANCE = new Disabled();
		@SuppressWarnings("unchecked")
		public static <V> Predicate<V> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Disabled() {
			super();
		}
		// throw an exception
		public boolean evaluate(V variable) {
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
