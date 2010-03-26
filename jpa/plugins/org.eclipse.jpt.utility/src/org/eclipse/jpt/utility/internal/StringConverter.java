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
 * into strings.
 */
public interface StringConverter<T> {

	/**
	 * Convert the specified object into a string.
	 * The semantics of "convert" is determined by the
	 * contract between the client and the server.
	 */
	String convertToString(T o);


	final class Default<S> implements StringConverter<S>, Serializable {
		@SuppressWarnings("rawtypes")
		public static final StringConverter INSTANCE = new Default();
		@SuppressWarnings("unchecked")
		public static <R> StringConverter<R> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		// simply return the object's #toString() result
		public String convertToString(S o) {
			return (o == null) ? null : o.toString();
		}
		@Override
		public String toString() {
			return "StringConverter.Default"; //$NON-NLS-1$
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

	final class Disabled<S> implements StringConverter<S>, Serializable {
		@SuppressWarnings("rawtypes")
		public static final StringConverter INSTANCE = new Disabled();
		@SuppressWarnings("unchecked")
		public static <R> StringConverter<R> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Disabled() {
			super();
		}
		// throw an exception
		public String convertToString(S o) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return "StringConverter.Disabled"; //$NON-NLS-1$
		}
		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}

}
