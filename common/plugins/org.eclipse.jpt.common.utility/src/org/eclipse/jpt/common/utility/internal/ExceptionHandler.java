/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
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
 * Simple interface for allowing clients to pass an exception handler to a
 * service (e.g. to log the exception). This is particularly helpful if the
 * service executes on another, possibly inaccessible, thread.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ExceptionHandler {

	/**
	 * The specified exception was thrown. Handle it appropriately.
	 */
	void handleException(Throwable t);

	/**
	 * Singleton implementation of the exception handler interface that does
	 * nothing with the exception.
	 */
	final class Null
		implements ExceptionHandler, Serializable
	{
		public static final ExceptionHandler INSTANCE = new Null();
		public static ExceptionHandler instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public void handleException(Throwable t) {
			// do nothing
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
	 * Singleton implementation of the exception handler interface that
	 * wraps the exception in a runtime exception and throws it.
	 */
	final class Runtime
		implements ExceptionHandler, Serializable
	{
		public static final ExceptionHandler INSTANCE = new Runtime();
		public static ExceptionHandler instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Runtime() {
			super();
		}
		public void handleException(Throwable t) {
			// re-throw the exception unchecked
			throw new RuntimeException(t);
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
