/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal.vendor;

import java.util.Collections;
import java.util.List;

import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.Schema;

/**
 * Catalog strategy for unknown DTP databases.
 * @see java.sql.DatabaseMetaData#getCatalogs()
 */
class UnknownCatalogStrategy
	implements CatalogStrategy
{
	// singleton
	private static final CatalogStrategy INSTANCE = new UnknownCatalogStrategy();

	/**
	 * Return the singleton.
	 */
	static CatalogStrategy instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private UnknownCatalogStrategy() {
		super();
	}

	@SuppressWarnings("unchecked")
	public boolean supportsCatalogs(Database database) {
		List<Catalog> catalogs = database.getCatalogs();
		if ((catalogs == null) || catalogs.isEmpty()) {
			return false;
		}

		return this.getFauxCatalog(catalogs) == null;
	}

	@SuppressWarnings("unchecked")
	public List<Catalog> getCatalogs(Database database) {
		List<Catalog> catalogs = database.getCatalogs();
		// if there are no catalogs, the database must hold the schemata directly
		if ((catalogs == null) || catalogs.isEmpty()) {
			return Collections.emptyList();
		}

		Catalog fauxCatalog = this.getFauxCatalog(catalogs);
		return (fauxCatalog == null) ? catalogs : Collections.<Catalog>emptyList();
	}

	@SuppressWarnings("unchecked")
	public List<Schema> getSchemas(Database database) {
		List<Catalog> catalogs = database.getCatalogs();
		// if there are no catalogs, the database must hold the schemata directly
		if ((catalogs == null) || catalogs.isEmpty()) {
			return database.getSchemas();
		}

		Catalog fauxCatalog = this.getFauxCatalog(catalogs);
		return (fauxCatalog != null) ? fauxCatalog.getSchemas() : Collections.emptyList();
	}

	private Catalog getFauxCatalog(List<Catalog> catalogs) {
		if (catalogs.size() == 1) {
			Catalog catalog = catalogs.get(0);
			if (catalog.getName().equals("")) { //$NON-NLS-1$
				return catalog;
			}
		}
		return null;
	}

}
