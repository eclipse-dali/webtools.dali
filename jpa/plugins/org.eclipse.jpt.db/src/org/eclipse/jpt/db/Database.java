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
	 * Return the database's vendor.
	 */
	String getVendor();

	/**
	 * Return the database's version.
	 */
	String getVersion();

	/**
	 * Return the database's default schema.
	 * In most cases the default schema's name will match the user name.
	 * It may be null.
	 */
	Schema getDefaultSchema();


	// ********** catalogs **********

	/**
	 * Return whether the database supports catalogs.
	 */
	boolean supportsCatalogs();

	/**
	 * Return the database's catalogs.
	 */
	Iterator<Catalog> catalogs();

	/**
	 * Return the number of catalogs the database contains.
	 */
	int catalogsSize();

	/**
	 * Return the names of the database's catalogs.
	 */
	Iterator<String> catalogNames();

	/**
	 * Return the database's "default" catalog.
	 * Return null if the database does not support catalogs.
	 */
	Catalog getDefaultCatalog();

	/**
	 * Return the catalog with specified name. The name should be an SQL
	 * identifier (i.e. quoted when case-sensitive, unquoted when
	 * case-insensitive).
	 */
	Catalog getCatalogNamed(String name);


	// ********** search utility **********

	/**
	 * Return the database object from the specified list with specified name.
	 * The name should be an SQL identifier (i.e. quoted when case-sensitive,
	 * unquoted when case-insensitive).
	 */
	<T extends DatabaseObject> T getDatabaseObjectNamed(T[] databaseObjects, String name);

}
