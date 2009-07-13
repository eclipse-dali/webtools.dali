/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
public interface Database extends SchemaContainer {

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
	 * Return whether the database supports catalogs. If it does, all database
	 * objects are contained by the database's catalogs; otherwise all database
	 * objects are contained by the database's schemata.
	 * 
	 * Practically speaking:
	 *     if #supportsCatalogs() returns 'true'
	 *         #catalogs() returns catalogs that contain the database's schemata
	 *         #schemata() returns an empty iterator
	 *     else
	 *         #catalogs() returns an empty iterator
	 *         #schemata() returns the database's schemata
	 * This is complicated by the presence of a "default" catalog that clients can
	 * use to allow the specification of a catalog to be optional; but clients
	 * must manage this explicitly.
	 * 
	 * @see #catalogs()
	 * @see #schemata()
	 */
	boolean supportsCatalogs();

	/**
	 * Return the database's catalogs.
	 * Return an empty iterator if the database does not support catalogs.
	 * @see #supportsCatalogs()
	 */
	Iterator<Catalog> catalogs();

	/**
	 * Return the number of catalogs the database contains.
	 * Return zero if the database does not support catalogs.
	 * @see #supportsCatalogs()
	 */
	int catalogsSize();

	/**
	 * Return the database's catalog names, sorted.
	 * Return an empty iterator if the database does not support catalogs.
	 * This is useful when the user is selecting a catalog from a read-only
	 * combo-box (e.g. in a wizard).
	 * @see #sortedCatalogIdentifiers()
	 * @see #getCatalogNamed(String)
	 */
	Iterator<String> sortedCatalogNames();

	/**
	 * Return the catalog with specified name. The name must be an exact match
	 * of the catalog's name.
	 * Return null if the database does not support catalogs.
	 * @see #supportsCatalogs()
	 * @see #sortedCatalogNames()
	 * @see #getCatalogForIdentifier(String)
	 */
	Catalog getCatalogNamed(String name);

	/**
	 * Return the database's catalog identifiers, sorted by name.
	 * Return an empty iterator if the database does not support catalogs.
	 * This is useful when the user is selecting an identifier that will be
	 * placed in a text file (e.g. in a Java annotation).
	 * @see #sortedCatalogNames()
	 * @see #getCatalogForIdentifier(String)
	 */
	Iterator<String> sortedCatalogIdentifiers();

	/**
	 * Return the catalog for the specified identifier. The identifier should
	 * be an SQL identifier (i.e. quoted when case-sensitive or containing
	 * special characters, unquoted otherwise).
	 * Return null if the database does not support catalogs.
	 * @see #supportsCatalogs()
	 * @see #sortedCatalogIdentifiers()
	 * @see #getCatalogNamed(String)
	 */
	Catalog getCatalogForIdentifier(String identifier);

	/**
	 * Return the database's "default" catalog, as defined by the database vendor.
	 * In most cases the default catalog's name will match the user name.
	 * Return null if the database does not support catalogs or if the default
	 * catalog does not exist (e.g. the database has no catalog whose name
	 * matches the user name).
	 * @see #supportsCatalogs()
	 * @see #getDefaultCatalogIdentifier()
	 */
	Catalog getDefaultCatalog();

	/**
	 * Return the database's "default" catalog identifier.
	 * The database may or may not have a catalog with a matching name.
	 * @see #supportsCatalogs()
	 * @see #getDefaultCatalog()
	 */
	String getDefaultCatalogIdentifier();


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
