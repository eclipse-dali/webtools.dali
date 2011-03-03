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

	public boolean supportsCatalogs() {
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Catalog> getCatalogs() {
		return this.database.getCatalogs();
	}

	public List<Schema> getSchemas() {
		return Collections.emptyList();
	}
}
