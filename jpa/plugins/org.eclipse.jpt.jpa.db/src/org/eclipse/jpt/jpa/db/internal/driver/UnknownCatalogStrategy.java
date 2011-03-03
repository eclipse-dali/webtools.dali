/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.driver;

import java.util.Collections;
import java.util.List;

import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.Schema;

/**
 * Catalog strategy for unknown DTP database drivers.
 * Infer the strategy from the returned catalogs and schemas.
 * 
 * @see java.sql.DatabaseMetaData#getCatalogs()
 */
class UnknownCatalogStrategy
	implements CatalogStrategy
{
	private final Database database;

	UnknownCatalogStrategy(Database database) {
		super();
		this.database = database;
	}

	/**
	 * Return <code>true</code> if the database returns a list of catalogs and
	 * that list does not contain only a "faux" catalog.
	 */
	public boolean supportsCatalogs() {
		return ! this.getCatalogs().isEmpty();
	}

	public List<Catalog> getCatalogs() {
		List<Catalog> catalogs = this.getCatalogs_();
		// if there are no catalogs, the database must hold the schemata directly
		if ((catalogs == null) || catalogs.isEmpty()) {
			return Collections.emptyList();
		}

		Catalog fauxCatalog = this.selectFauxCatalog(catalogs);
		// if we don't have a "faux" catalog, we must have "real" catalogs
		return (fauxCatalog == null) ? catalogs : Collections.<Catalog>emptyList();
	}

	public List<Schema> getSchemas() {
		List<Catalog> catalogs = this.getCatalogs_();
		// if there are no catalogs, the database must hold the schemata directly
		if ((catalogs == null) || catalogs.isEmpty()) {
			return this.getSchemas_();
		}

		Catalog fauxCatalog = this.selectFauxCatalog(catalogs);
		return (fauxCatalog != null) ? this.getSchemas(fauxCatalog) : Collections.<Schema>emptyList();
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<Schema> getSchemas(Catalog catalog) {
		return catalog.getSchemas();
	}

	/**
	 * If the specified list of catalogs contains a "faux" catalog (i.e. a
	 * single catalog with an empty string for a name), return it; otherwise
	 * return <code>null</code>.
	 */
	private Catalog selectFauxCatalog(List<Catalog> catalogs) {
		if (catalogs.size() == 1) {
			Catalog catalog = catalogs.get(0);
			if (catalog.getName().equals("")) { //$NON-NLS-1$
				return catalog;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private List<Catalog> getCatalogs_() {
		return this.database.getCatalogs();
	}

	@SuppressWarnings("unchecked")
	private List<Schema> getSchemas_() {
		return this.database.getSchemas();
	}
}
