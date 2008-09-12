/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db;

/**
 * This interface allows clients of the Dali db package to plug in a custom
 * strategy for comparing an identifier to the names of a collection of
 * database objects.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface DatabaseFinder {

	/**
	 * Select and return from the specified list of database objects the
	 * database object identified by the specified identifier.
	 */
	<T extends DatabaseObject> T selectDatabaseObjectForIdentifier(T[] databaseObjects, String identifier, DefaultCallback defaultCallback);

	/**
	 * The platform-provided finder is passed a "default" callback that can be
	 * used if appropriate.
	 */
	interface DefaultCallback {

		/**
		 * Select and return from the specified list of database objects the
		 * database object identified by the specified identifier.
		 */
		<T extends DatabaseObject> T selectDatabaseObjectForIdentifier(T[] databaseObjects, String identifier);

	}

	/**
	 * This finder searches for an exact match.
	 */
	final class Simple implements DatabaseFinder {
		public static final DatabaseFinder INSTANCE = new Simple();
		public static DatabaseFinder instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Simple() {
			super();
		}
		// search for an exact match on the name
		public <T extends DatabaseObject> T selectDatabaseObjectForIdentifier(T[] databaseObjects, String identifier, DefaultCallback defaultCallback) {
			for (T databaseObject : databaseObjects) {
				if (databaseObject.getName().equals(identifier)) {
					return databaseObject;
				}
			}
			return null;
		}
		@Override
		public String toString() {
			return "DatabaseFinder.Default"; //$NON-NLS-1$
		}
	}

	/**
	 * This finder uses the passed in callback to search the list of database objects.
	 */
	final class Default implements DatabaseFinder {
		public static final DatabaseFinder INSTANCE = new Default();
		public static DatabaseFinder instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Default() {
			super();
		}
		// simply use the callback
		public <T extends DatabaseObject> T selectDatabaseObjectForIdentifier(T[] databaseObjects, String identifier, DefaultCallback defaultCallback) {
			return defaultCallback.selectDatabaseObjectForIdentifier(databaseObjects, identifier);
		}
		@Override
		public String toString() {
			return "DatabaseFinder.Default"; //$NON-NLS-1$
		}
	}

}
