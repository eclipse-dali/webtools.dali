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

import java.text.Collator;
import java.util.List;

import org.eclipse.jpt.db.Catalog;

/**
 * Wrap a DTP Catalog
 */
final class DTPCatalogWrapper
	extends DTPSchemaContainerWrapper
	implements Catalog
{
	// backpointer to parent
	private final DTPDatabaseWrapper database;

	// the wrapped DTP catalog
	private final org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog;


	// ********** constructor **********

	DTPCatalogWrapper(DTPDatabaseWrapper database, org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog) {
		super(database, dtpCatalog);
		this.database = database;
		this.dtpCatalog = dtpCatalog;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged(int eventType) {
		super.catalogObjectChanged(eventType);
		this.getConnectionProfile().catalogChanged(this, eventType);
	}


	// ********** Catalog implementation **********

	@Override
	public String getName() {
		return this.dtpCatalog.getName();
	}


	// ***** schemata

	@Override
	@SuppressWarnings("unchecked")
	List<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata() {
		return this.dtpCatalog.getSchemas();
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.schema.Catalog catalog) {
		return this.dtpCatalog == catalog;
	}

	@Override
	boolean isCaseSensitive() {
		return this.database.isCaseSensitive();
	}

	@Override
	DTPDatabaseWrapper database() {
		return this.database;
	}


	// ********** Comparable implementation **********

	public int compareTo(Catalog catalog) {
		return Collator.getInstance().compare(this.getName(), catalog.getName());
	}

}
