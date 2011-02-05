/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.vendor;

import java.util.Collections;
import java.util.List;

import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.Schema;

/**
 * Catalog strategy for DTP databases that build a "virtual" catalog (that has
 * no name) because the underlying JDBC driver does not return any catalogs
 * (e.g. Oracle).
 * @see java.sql.DatabaseMetaData#getCatalogs()
 */
class FauxCatalogStrategy
	implements CatalogStrategy
{
	// singleton
	private static final CatalogStrategy INSTANCE = new FauxCatalogStrategy();

	/**
	 * Return the singleton.
	 */
	static CatalogStrategy instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private FauxCatalogStrategy() {
		super();
	}

	public boolean supportsCatalogs(Database database) {
		return false;
	}

	public List<Catalog> getCatalogs(Database database) {
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	public List<Schema> getSchemas(Database database) {
		// 308947 - hack to support old IBM DTP/RDB extension for Oracle
		List<Catalog> catalogs = database.getCatalogs();
		// if there are no catalogs, the database must hold the schemata directly
		if ((catalogs == null) || catalogs.isEmpty()) {
			return database.getSchemas();
		}

		// normal logic:
		return this.getFauxCatalog(database.getCatalogs()).getSchemas();
	}

	private Catalog getFauxCatalog(List<Catalog> catalogs) {
		if (catalogs == null) {
			throw new IllegalStateException();
		}
		if (catalogs.size() != 1) {
			throw new IllegalStateException("not a single catalog: " + catalogs.size()); //$NON-NLS-1$
		}

		Catalog catalog = catalogs.get(0);
		if (catalog.getName().length() != 0) {
			throw new IllegalStateException("illegal name: " + catalog.getName()); //$NON-NLS-1$
		}
		return catalog;
	}

}
