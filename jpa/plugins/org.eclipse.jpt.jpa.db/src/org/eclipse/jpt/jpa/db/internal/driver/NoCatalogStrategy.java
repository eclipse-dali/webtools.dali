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
 * Catalog strategy for DTP database drivers that do not have catalogs
 * (e.g. MySQL; see bug 249013).
 */
class NoCatalogStrategy
	implements CatalogStrategy
{
	private final Database database;

	NoCatalogStrategy(Database database) {
		super();
		this.database = database;
	}

	public boolean supportsCatalogs() {
		return false;
	}

	public List<Catalog> getCatalogs() {
		return Collections.emptyList();
	}

	@SuppressWarnings("unchecked")
	public List<Schema> getSchemas() {
		return this.database.getSchemas();
	}
}
