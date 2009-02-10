/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal;

/**
 * Used by various "pluggable" classes to transform objects
 * into strings and vice versa.
 * 
 * If anyone can come up with a better class name
 * and/or method name, I would love to hear it.  ~bjv
 */
public interface BidiStringConverter<T> extends StringConverter<T> {

	/**
	 * Convert the specified string into an object.
	 * The semantics of "convert to object" is determined by the
	 * contract between the client and the server.
	 * Typically, if the string is null, null is returned.
	 */
	T convertToObject(String s);


	final class Default<S> implements BidiStringConverter<S> {
		@SuppressWarnings("unchecked")
		public static final BidiStringConverter INSTANCE = new Default();
		@SuppressWarnings("unchecked")
		public static <R> BidiStringConverter<R> instance() {
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
		// simply return the string
		@SuppressWarnings("unchecked")
		public S convertToObject(String s) {
			return (S) s;
		}
		@Override
		public String toString() {
			return "BidiStringConverter.Default"; //$NON-NLS-1$
		}
	}

	final class Disabled<S> implements BidiStringConverter<S> {
		@SuppressWarnings("unchecked")
		public static final BidiStringConverter INSTANCE = new Disabled();
		@SuppressWarnings("unchecked")
		public static <R> BidiStringConverter<R> instance() {
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
		// throw an exception
		public S convertToObject(String s) {
			throw new UnsupportedOperationException();
		}
		@Override
		public String toString() {
			return "BidiStringConverter.Disabled"; //$NON-NLS-1$
		}
	}

	final class BooleanConverter implements BidiStringConverter<Boolean> {
		public static final BidiStringConverter<Boolean> INSTANCE = new BooleanConverter();
		public static BidiStringConverter<Boolean> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private BooleanConverter() {
			super();
		}
		/** Return "true" if the Boolean is true, otherwise return "false". */
		public String convertToString(Boolean b) {
			return (b == null) ? null : b.toString();
		}
		/** Return Boolean.TRUE if the string is "true" (case-insensitive), otherwise return Boolean.FALSE. */
		public Boolean convertToObject(String s) {
			return (s == null) ? null : Boolean.valueOf(s);
		}
		@Override
		public String toString() {
			return "BidiStringConverter.BooleanConverter"; //$NON-NLS-1$
		}
	}

	final class IntegerConverter implements BidiStringConverter<Integer> {
		public static final BidiStringConverter<Integer> INSTANCE = new IntegerConverter();
		public static BidiStringConverter<Integer> instance() {
			return INSTANCE;
		}
		// ensure single instance
		private IntegerConverter() {
			super();
		}
		/** Integer's #toString() works well. */
		public String convertToString(Integer integer) {
			return (integer == null) ? null : integer.toString();
		}
		/** Convert the string to an Integer, if possible. */
		public Integer convertToObject(String s) {
			return (s == null) ? null : Integer.valueOf(s);
		}
		@Override
		public String toString() {
			return "BidiStringConverter.IntegerConverter"; //$NON-NLS-1$
		}
	}

}
