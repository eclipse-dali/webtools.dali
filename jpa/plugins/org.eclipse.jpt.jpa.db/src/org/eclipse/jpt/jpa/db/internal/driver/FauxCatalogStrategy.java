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
 * Catalog strategy for DTP database drivers that build a "virtual" catalog
 * (that has no name) because the underlying JDBC driver does not return any
 * catalogs (e.g. Oracle). This catalog cannot be specified by the user in
 * annotations etc.
 * 
 * @see java.sql.DatabaseMetaData#getCatalogs()
 */
class FauxCatalogStrategy
	implements CatalogStrategy
{
	private final Database database;

	FauxCatalogStrategy(Database database) {
		super();
		this.database = database;
	}

	public boolean supportsCatalogs() {
		return false;
	}

	public List<Catalog> getCatalogs() {
		return Collections.emptyList();
	}

	public List<Schema> getSchemas() {
		Catalog fauxCatalog = this.getFauxCatalog();
		@SuppressWarnings("unchecked")
		List<Schema> schemas = fauxCatalog.getSchemas();
		return schemas;
	}

	private Catalog getFauxCatalog() {
		List<Catalog> catalogs = this.getCatalogs_();
		if (catalogs == null) {
			throw new IllegalStateException("catalogs list is null"); //$NON-NLS-1$
		}
		if (catalogs.size() != 1) {
			throw new IllegalStateException("not a single catalog: " + catalogs); //$NON-NLS-1$
		}

		Catalog catalog = catalogs.get(0);
		if (catalog.getName().length() != 0) {
			throw new IllegalStateException("illegal name: " + catalog.getName()); //$NON-NLS-1$
		}
		return catalog;
	}

	@SuppressWarnings("unchecked")
	private List<Catalog> getCatalogs_() {
		return this.database.getCatalogs();
	}
}
