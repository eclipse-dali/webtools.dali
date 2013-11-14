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
 * Catalog strategy for DTP database drivers that simply model the catalogs
 * returned by the underlying JDBC driver (e.g. Sybase).
 * 
 * @see java.sql.DatabaseMetaData#getCatalogs()
 */
class SimpleCatalogStrategy
	implements CatalogStrategy
{
	private final Database database;

	SimpleCatalogStrategy(Database database) {
		super();
		this.database = database;
	}

	@SuppressWarnings("unchecked")
	public boolean supportsCatalogs() {
		// DTP allows for optional support of catalogs in extensions
		List<Catalog> catalogs = this.database.getCatalogs();
		if ((catalogs == null) || catalogs.isEmpty()) {
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Catalog> getCatalogs() {
		List<Catalog> catalogs = this.database.getCatalogs();
		// DTP allows for optional support of catalogs in extensions
		if ((catalogs == null) || catalogs.isEmpty()) {
			return Collections.emptyList();
		}
		
		return catalogs;
	}

	@SuppressWarnings("unchecked")
	public List<Schema> getSchemas() {
		List<Catalog> catalogs = this.database.getCatalogs();
		// if there are no catalogs, the database must hold the schemata directly
		if ((catalogs == null) || catalogs.isEmpty()) {
			return this.database.getSchemas();
		}
		
		return Collections.emptyList();
	}
}
