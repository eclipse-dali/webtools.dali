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
 * Catalog strategy for DTP databases that simply model the catalogs returned
 * by the underlying JDBC driver (e.g. Sybase).
 * @see java.sql.DatabaseMetaData#getCatalogs()
 */
class SimpleCatalogStrategy
	implements CatalogStrategy
{
	// singleton
	private static final CatalogStrategy INSTANCE = new SimpleCatalogStrategy();

	/**
	 * Return the singleton.
	 */
	static CatalogStrategy instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private SimpleCatalogStrategy() {
		super();
	}

	@SuppressWarnings("unchecked")
	public boolean supportsCatalogs(Database database) {
		// Bug 327572: DTP allows for optional support of catalogs in extensions
		List<Catalog> catalogs = database.getCatalogs();
		if ((catalogs == null) || catalogs.isEmpty()) {
			return false;
		}
		
		// normal logic:
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Catalog> getCatalogs(Database database) {
		List<Catalog> catalogs = database.getCatalogs();
		// Bug 327572: DTP allows for optional support of catalogs in extensions
		if ((catalogs == null) || catalogs.isEmpty()) {
			return Collections.emptyList();
		}
		
		// normal logic:
		return catalogs;
	}

	@SuppressWarnings("unchecked")
	public List<Schema> getSchemas(Database database) {
		List<Catalog> catalogs = database.getCatalogs();
		// Bug 327572: DTP allows for optional support of catalogs in extensions
		// if there are no catalogs, the database must hold the schemata directly
		if ((catalogs == null) || catalogs.isEmpty()) {
			return database.getSchemas();
		}
		
		// normal logic:
		return Collections.emptyList();
	}

}
