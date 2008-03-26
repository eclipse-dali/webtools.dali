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
import org.eclipse.datatools.connectivity.sqm.core.definition.DatabaseDefinition;

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
public interface Database extends SchemaContainer, Comparable<Database> {

	/**
	 * Return the database's name.
	 */
	String getName();

	/**
	 * Return the database's vendor.
	 */
	String getVendor();

	/**
	 * Return the database's version.
	 */
	String getVersion();

	/**
	 * Return whether the database's identifiers are case-sensitive.
	 */
	boolean isCaseSensitive();

	/**
	 * Return the database's DTP database definition.
	 */
	DatabaseDefinition getDtpDefinition();

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
	 * Return whether the database contains a catalog with the specified name,
	 * respecting the database's case-sensitivity.
	 */
	boolean containsCatalogNamed(String name);

	/**
	 * Return the catalog in the database with the specified name,
	 * respecting the database's case-sensitivity.
	 */
	Catalog catalogNamed(String name);

	/**
	 * Return the database's "default" catalog.
	 */
	Catalog getDefaultCatalog();

}
