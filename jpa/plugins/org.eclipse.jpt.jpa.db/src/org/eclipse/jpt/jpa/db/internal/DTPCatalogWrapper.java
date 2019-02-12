/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal;

import java.util.List;
import org.eclipse.jpt.jpa.db.Catalog;

/**
 * Wrap a DTP Catalog
 */
final class DTPCatalogWrapper
	extends DTPSchemaContainerWrapper<DTPDatabaseWrapper, org.eclipse.datatools.modelbase.sql.schema.Catalog>
	implements Catalog
{
	DTPCatalogWrapper(DTPDatabaseWrapper database, org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog) {
		super(database, dtpCatalog);
	}


	// ********** DTPDatabaseObjectWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().catalogChanged(this);
	}


	// ********** DTPSchemaContainerWrapper implementation **********

	@Override
	@SuppressWarnings("unchecked")
	List<org.eclipse.datatools.modelbase.sql.schema.Schema> getDTPSchemas() {
		return this.dtpObject.getSchemas();
	}

	@Override
	DTPSchemaWrapper getSchema(org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		// try to short-circuit the search
		return this.wraps(dtpSchema.getCatalog()) ?
						this.getSchema_(dtpSchema) :
						this.getDatabase().getSchemaFromCatalogs(dtpSchema);
	}

	@Override
	DTPTableWrapper getTable(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		// try to short-circuit the search
		return this.wraps(dtpTable.getSchema().getCatalog()) ?
						this.getTable_(dtpTable) :
						this.getDatabase().getTableFromCatalogs(dtpTable);
	}

	@Override
	DTPColumnWrapper getColumn(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		// try to short-circuit the search
		return this.wraps(dtpColumn.getTable().getSchema().getCatalog()) ?
						this.getColumn_(dtpColumn) :
						this.getDatabase().getColumnFromCatalogs(dtpColumn);
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.schema.Catalog catalog) {
		return this.dtpObject == catalog;
	}
}
