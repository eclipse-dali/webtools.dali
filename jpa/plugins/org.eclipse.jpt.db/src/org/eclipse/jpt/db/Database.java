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

import java.util.Iterator;

/**
 * Database
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface Database
	extends SchemaContainer, Comparable<Database>
{

	// ********** properties **********

	/**
	 * Return the name of the database's vendor.
	 */
	String getVendorName();

	/**
	 * Return the database's version.
	 */
	String getVersion();


	// ********** catalogs **********

	/**
	 * Return whether the database supports catalogs.
	 */
	boolean supportsCatalogs();

	/**
	 * Return the database's catalogs.
	 * @see #supportsCatalogs()
	 */
	Iterator<Catalog> catalogs();

	/**
	 * Return the number of catalogs the database contains.
	 * @see #supportsCatalogs()
	 */
	int catalogsSize();

	/**
	 * Return the catalog with specified name. The name must be an exact match
	 * of the catalog's name.
	 * @see #supportsCatalogs()
	 * @see #getCatalogForIdentifier(String)
	 */
	Catalog getCatalogNamed(String name);

	/**
	 * Return the database's catalog identifiers, sorted by name.
	 */
	Iterator<String> sortedCatalogIdentifiers();

	/**
	 * Return the catalog for the specified identifier. The identifier should
	 * be an SQL identifier (i.e. quoted when case-sensitive or containing
	 * special characters, unquoted otherwise).
	 * @see #supportsCatalogs()
	 * @see #getCatalogNamed(String)
	 */
	Catalog getCatalogForIdentifier(String identifier);

	/**
	 * Return the database's "default" catalog.
	 * Return null if the database does not support catalogs.
	 * @see #supportsCatalogs()
	 */
	Catalog getDefaultCatalog();


	// ********** utility methods **********

	/**
	 * Select and return from the specified list of database objects the
	 * database object identified by the specified identifier.
	 * The identifier should be an SQL identifier (i.e. delimited when
	 * non-"normal").
	 */
	<T extends DatabaseObject> T selectDatabaseObjectForIdentifier(T[] databaseObjects, String identifier);

	/**
	 * Convert the specified name to a database-appropriate SQL identifier.
	 */
	String convertNameToIdentifier(String name);

}
