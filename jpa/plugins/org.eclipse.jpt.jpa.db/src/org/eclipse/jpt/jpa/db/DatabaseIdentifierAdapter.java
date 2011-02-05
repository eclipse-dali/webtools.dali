/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

/**
 * This interface allows clients of the Dali db package to plug in a custom
 * strategy for converting a database identifier to a database name and vice
 * versa.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface DatabaseIdentifierAdapter {

	/**
	 * Convert the specified "identifier" to a "name".
	 */
	String convertIdentifierToName(String identifier, DefaultCallback defaultCallback);

	/**
	 * Convert the specified "name" to an "identifier".
	 */
	String convertNameToIdentifier(String name, DefaultCallback defaultCallback);

	/**
	 * The client-provided finder is passed a "default" callback that can be
	 * used if appropriate.
	 */
	interface DefaultCallback {

		/**
		 * Convert the specified "identifier" to a "name".
		 */
		String convertIdentifierToName(String identifier);

		/**
		 * Convert the specified "name" to an "identifier".
		 */
		String convertNameToIdentifier(String name);

	}

	/**
	 * This adapter simply uses the passed in default callback.
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
		// simply use the default callback
		public String convertIdentifierToName(String identifier, DefaultCallback defaultCallback) {
			return defaultCallback.convertIdentifierToName(identifier);
		}
		// simply use the default callback
		public String convertNameToIdentifier(String name, DefaultCallback defaultCallback) {
			return defaultCallback.convertNameToIdentifier(name);
		}
		@Override
		public String toString() {
			return "DatabaseIdentifierAdapter.Default"; //$NON-NLS-1$
		}
	}

}
