/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * Coalesce behavior for a schema container (i.e. database or catalog).
 */
abstract class DTPSchemaContainerWrapper
	extends DTPDatabaseObjectWrapper
	implements SchemaContainer
{
	// lazy-initialized
	private DTPSchemaWrapper[] schemata;


	// ********** constructor **********

	DTPSchemaContainerWrapper(DTPDatabaseObject parent, Object dtpObject) {
		super(parent, dtpObject);
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
	}


	// ********** abstract methods **********

	/**
	 * return the schema container's DTP schemata
	 */
	abstract List<org.eclipse.datatools.modelbase.sql.schema.Schema> getDTPSchemata();

	/**
	 * return the schema container's catalog,
	 * null if the database does not support catalogs
	 */
	abstract DTPCatalogWrapper getCatalog();

	/**
	 * return the schema for the specified DTP schema
	 */
	abstract DTPSchemaWrapper getSchema(org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema);

	/**
	 * assume the schema container (database or catalog) contains
	 * the specified schema
	 */
	DTPSchemaWrapper getSchema_(org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		for (DTPSchemaWrapper schema : this.getSchemata()) {
			if (schema.wraps(dtpSchema)) {
				return schema;
			}
		}
		throw new IllegalArgumentException("invalid DTP schema: " + dtpSchema);  //$NON-NLS-1$
	}

	/**
	 * return the table for the specified DTP table
	 */
	abstract DTPTableWrapper getTable(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable);

	/**
	 * assume the schema container contains the specified table
	 */
	DTPTableWrapper getTable_(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		return this.getSchema_(dtpTable.getSchema()).getTable_(dtpTable);
	}

	/**
	 * return the column for the specified DTP column
	 */
	abstract DTPColumnWrapper getColumn(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn);

	/**
	 * assume the schema container contains the specified column
	 */
	DTPColumnWrapper getColumn_(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.getTable_(dtpColumn.getTable()).getColumn_(dtpColumn);
	}


	// ********** schemata **********

	public Iterator<Schema> schemata() {
		return new ArrayIterator<Schema>(this.getSchemata());
	}

	Iterator<DTPSchemaWrapper> schemaWrappers() {
		return new ArrayIterator<DTPSchemaWrapper>(this.getSchemata());
	}

	synchronized DTPSchemaWrapper[] getSchemata() {
		if (this.schemata == null) {
			this.schemata = this.buildSchemata();
		}
		return this.schemata;
	}

	private DTPSchemaWrapper[] buildSchemata() {
		List<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata = this.getDTPSchemata();
		DTPSchemaWrapper[] result = new DTPSchemaWrapper[dtpSchemata.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPSchemaWrapper(this, dtpSchemata.get(i));
		}
		return result;
	}

	public int schemataSize() {
		return this.getSchemata().length;
	}

	public Iterator<String> schemaNames() {
		return new TransformationIterator<DTPSchemaWrapper, String>(this.schemaWrappers()) {
			@Override
			protected String transform(DTPSchemaWrapper schema) {
				 return schema.getName();
			}
		};
	}

	public DTPSchemaWrapper getSchemaNamed(String name) {
		return this.getDatabaseObjectNamed(this.getSchemata(), name);
	}


	// ********** listening **********

	@Override
	synchronized void startListening() {
		if (this.schemata != null) {
			this.startSchemata();
		}
		super.startListening();
	}

	private void startSchemata() {
		for (DTPSchemaWrapper schema : this.schemata) {
			schema.startListening();
		}
	}

	@Override
	synchronized void stopListening() {
		if (this.schemata != null) {
			this.stopSchemata();
		}
		super.stopListening();
	}

	private void stopSchemata() {
		for (DTPSchemaWrapper schema : this.schemata) {
			schema.stopListening();
		}
	}


	// ********** clear **********

	@Override
	synchronized void clear() {
		if (this.schemata != null) {
			this.clearSchemata();
		}
	}

	private void clearSchemata() {
		this.stopSchemata();
		for (DTPSchemaWrapper schema : this.schemata) {
			schema.clear();
		}
		this.schemata = null;
	}

}
