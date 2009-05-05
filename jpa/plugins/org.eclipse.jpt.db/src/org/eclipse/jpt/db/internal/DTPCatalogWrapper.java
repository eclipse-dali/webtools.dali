/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import com.ibm.icu.text.Collator;
import java.util.List;
import org.eclipse.jpt.db.Catalog;

/**
 * Wrap a DTP Catalog
 */
final class DTPCatalogWrapper
	extends DTPSchemaContainerWrapper
	implements Catalog
{
	// the wrapped DTP catalog
	private final org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog;


	// ********** constructor **********

	DTPCatalogWrapper(DTPDatabaseWrapper database, org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog) {
		super(database, dtpCatalog);
		this.dtpCatalog = dtpCatalog;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().catalogChanged(this);
	}


	// ********** DTPSchemaContainerWrapper implementation **********

	@Override
	@SuppressWarnings("unchecked")
	List<org.eclipse.datatools.modelbase.sql.schema.Schema> getDTPSchemata() {
		return this.dtpCatalog.getSchemas();
	}

	@Override
	DTPSchemaWrapper getSchema(org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		// try to short-circuit the search
		return this.wraps(dtpSchema.getCatalog()) ?
						this.getSchema_(dtpSchema)
					:
						this.getDatabase().getSchemaFromCatalogs(dtpSchema);
	}

	@Override
	DTPTableWrapper getTable(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		// try to short-circuit the search
		return this.wraps(dtpTable.getSchema().getCatalog()) ?
						this.getTable_(dtpTable)
					:
						this.getDatabase().getTableFromCatalogs(dtpTable);
	}

	@Override
	DTPColumnWrapper getColumn(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		// try to short-circuit the search
		return this.wraps(dtpColumn.getTable().getSchema().getCatalog()) ?
						this.getColumn_(dtpColumn)
					:
						this.getDatabase().getColumnFromCatalogs(dtpColumn);
	}


	// ********** DatabaseObject implementation **********

	public String getName() {
		return this.dtpCatalog.getName();
	}


	// ********** Comparable implementation **********

	public int compareTo(Catalog catalog) {
		return Collator.getInstance().compare(this.getName(), catalog.getName());
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.schema.Catalog catalog) {
		return this.dtpCatalog == catalog;
	}

}
