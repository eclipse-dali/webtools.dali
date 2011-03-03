/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

/**
 * This interface allows clients of the Dali db package to control whether
 * database identifiers are to be treated as though they are delimited, which,
 * most significantly, usually means the identifiers are case-sensitive.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface DatabaseIdentifierAdapter {

	/**
	 * Return whether identifiers are to be treated as though they were
	 * delimited.
	 */
	boolean treatIdentifiersAsDelimited();


	/**
	 * This adapter simply returns <code>false</code>, which is compatible
	 * with JPA 1.0.
	 */
	final class Default implements DatabaseIdentifierAdapter {
		public static final DatabaseIdentifierAdapter INSTANCE = new Default();
		public static DatabaseIdentifierAdapter instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		public boolean treatIdentifiersAsDelimited() {
			return false;  // JPA 1.0
		}
		@Override
		public String toString() {
			return this.getClass().getDeclaringClass().getSimpleName() + '.' + this.getClass().getSimpleName();
		}
	}
}
