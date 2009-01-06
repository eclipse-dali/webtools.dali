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

	public boolean supportsCatalogs(Database database) {
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Catalog> getCatalogs(Database database) {
		return database.getCatalogs();
	}

	public List<Schema> getSchemas(Database database) {
		return Collections.emptyList();
	}

}
