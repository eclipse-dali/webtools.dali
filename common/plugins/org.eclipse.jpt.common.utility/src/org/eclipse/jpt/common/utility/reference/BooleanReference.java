/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.reference;

import java.io.Serializable;

/**
 * Interface for a container for holding a <code>boolean</code> that cannot be
 * changed by clients.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see ModifiableBooleanReference
 */
public interface BooleanReference {

	/**
	 * Return the current <code>boolean</code> value.
	 */
	boolean getValue();

	/**
	 * Return whether the current <code>boolean</code> value is equal to the
	 * specified value.
	 */
	boolean is(boolean value);

	/**
	 * Return whether the current <code>boolean</code> value is not equal to
	 * the specified value.
	 */
	boolean isNot(boolean value);

	/**
	 * Return whether the current <code>boolean</code> value is
	 * <code>true</code>.
	 */
	boolean isTrue();

	/**
	 * Return whether the current <code>boolean</code> value is
	 * <code>false</code>.
	 */
	boolean isFalse();


	/**
	 * Convenience method.
	 */
	final class Value {
		public static BooleanReference of(boolean value) {
			return value ? True.instance() : False.instance();
		}
	}


	/**
	 * Singleton implementation of the read-only boolean reference interface
	 * whose value is always <code>true</code>.
	 */
	final class True
		implements BooleanReference, Serializable
	{
		public static final BooleanReference INSTANCE = new True();

		public static BooleanReference instance() {
			return INSTANCE;
		}

		// ensure single instance
		private True() {
			super();
		}

		public boolean getValue() {
			return true;
		}

		public boolean is(boolean value) {
			return value;
		}

		public boolean isNot(boolean value) {
			return ! value;
		}

		public boolean isTrue() {
			return true;
		}

		public boolean isFalse() {
			return false;
		}

		@Override
		public String toString() {
			return "[true]"; //$NON-NLS-1$
		}

		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}


	/**
	 * Singleton implementation of the read-only boolean reference interface
	 * whose value is always <code>false</code>.
	 */
	final class False
		implements BooleanReference, Serializable
	{
		public static final BooleanReference INSTANCE = new False();

		public static BooleanReference instance() {
			return INSTANCE;
		}

		// ensure single instance
		private False() {
			super();
		}

		public boolean getValue() {
			return false;
		}

		public boolean is(boolean value) {
			return ! value;
		}

		public boolean isNot(boolean value) {
			return value;
		}

		public boolean isTrue() {
			return false;
		}

		public boolean isFalse() {
			return true;
		}

		@Override
		public String toString() {
			return "[false]"; //$NON-NLS-1$
		}

		private static final long serialVersionUID = 1L;
		private Object readResolve() {
			// replace this object with the singleton
			return INSTANCE;
		}
	}
}
