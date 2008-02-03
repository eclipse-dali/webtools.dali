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

import java.text.Collator;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.datatools.connectivity.sqm.core.definition.DatabaseDefinition;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 *  Database wrapper base class.
 */
public abstract class Database extends DTPWrapper implements Comparable<Database> {
	
	private boolean caseSensitive = false;  // TODO allow user to configure

	// ********** constructors **********

	static Database createDatabase( ConnectionProfile profile, org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase) {
		return ( dtpDatabase == null) ? NullDatabase.instance() : new DTPDatabaseWrapper( profile, dtpDatabase);
	}

	Database() {
		super();
	}

	// ********** behavior **********

	abstract void catalogChanged( Catalog catalog, int eventType);

	abstract void schemaChanged( Schema schema, int eventType);

	abstract void tableChanged( Table table,  Schema schema, int eventType);
	
	abstract void refresh();
	
	protected Schema wrap( org.eclipse.datatools.modelbase.sql.schema.Schema schema) {
		return new Schema( this, schema);
	}
	
	protected Catalog wrap( org.eclipse.datatools.modelbase.sql.schema.Catalog catalog) {
		return new Catalog( this, catalog);
	}
	
	// ********** queries **********

	public abstract String getVendor();
	
	public abstract String getVersion();

	/**
	 * return the column for the specified dtp column
	 */
	Column column( org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.table( dtpColumn.getTable()).column( dtpColumn);
	}
	
	/**
	 * return the table for the specified dtp table
	 */
	Table table( org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		return this.schema( dtpTable.getSchema()).table( dtpTable);
	}

	public Database database() {
		return this;
	}

	public abstract DatabaseDefinition dtpDefinition();

	// ********** Comparable implementation **********

	public int compareTo( Database database) {
		return Collator.getInstance().compare( this.getName(), database.getName());
	}

	// ***** caseSensitive

	public boolean isCaseSensitive() {
		return this.caseSensitive;
	}

	public void setCaseSensitive( boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	// ***** catalogs

	abstract Set<Catalog> getCatalogs();

	/**
	 * Returns true if this database accepts catalogs.
	 */
	public abstract boolean supportsCatalogs();
	
	/**
	 * Returns the catalog to use by default.
	 */
	public abstract String getDefaultCatalogName();
	
	public Iterator<Catalog> catalogs() {
		return this.getCatalogs().iterator();
	}

	public int catalogSize() {
		return this.getCatalogs().size();
	}

	public Iterator<String> catalogNames() {
		return new TransformationIterator<Catalog, String>( this.catalogs()) {
			@Override
			protected String transform( Catalog catalog) {
				 return catalog.getName();
			}
		};
	}

	public boolean containsCatalogNamed( String name) {
		return this.catalogNamed( name) != null;
	}

	public Catalog catalogNamed( String name) {
		return this.isCaseSensitive() ? this.catalogNamedInternal( name) : this.catalogNamedIgnoreCase( name);
	}
	
	private Catalog catalogNamedInternal( String name) {
		for ( Iterator<Catalog> stream = this.catalogs(); stream.hasNext(); ) {
			Catalog catalog = stream.next();
			if ( catalog.getName().equals( name)) {
				return catalog;
			}
		}
		return null;
	}
	
	private Catalog catalogNamedIgnoreCase( String name) {
		for ( Iterator<Catalog> stream = this.catalogs(); stream.hasNext(); ) {
			Catalog catalog = stream.next();
			if ( StringTools.stringsAreEqualIgnoreCase( catalog.getName(), name)) {
				return catalog;
			}
		}
		return null;
	}

	/**
	 * return the catalog for the specified dtp catalog
	 */
	Catalog catalog( org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog) {
		for ( Iterator<Catalog> stream = this.catalogs(); stream.hasNext(); ) {
			Catalog catalog = stream.next();
			if (catalog.wraps( dtpCatalog)) {
				return catalog;
			}
		}
		throw new IllegalArgumentException( "invalid dtp catalog: " + dtpCatalog);
	}


	// ***** schemata

	abstract Set<Schema> getSchemata();

	public Iterator<Schema> schemata() {
		return this.getSchemata().iterator();
	}

	public int schemataSize() {
		return this.getSchemata().size();
	}

	public boolean schemataContains( Column column) {
		return this.getSchemata().contains( column);
	}

	public Iterator<String> schemaNames() {
		return new TransformationIterator<Schema, String>( this.schemata()) {
			@Override
			protected String transform( Schema schema) {
				 return schema.getName();
			}
		};
	}

	public boolean containsSchemaNamed( String name) {
		return this.schemaNamed( name) != null;
	}

	public Schema schemaNamed( String name) {
		return this.isCaseSensitive() ? this.schemaNamedInternal( name) : this.schemaNamedIgnoreCase( name);
	}
	
	private Schema schemaNamedInternal( String name) {
		for ( Iterator<Schema> stream = this.schemata(); stream.hasNext(); ) {
			Schema schema = stream.next();
			if ( schema.getName().equals( name)) {
				return schema;
			}
		}
		return null;
	}
	
	private Schema schemaNamedIgnoreCase( String name) {
		for ( Iterator<Schema> stream = this.schemata(); stream.hasNext(); ) {
			Schema schema = stream.next();
			if ( StringTools.stringsAreEqualIgnoreCase( schema.getName(), name)) {
				return schema;
			}
		}
		return null;
	}

	/**
	 * return the schema for the specified dtp schema
	 */
	Schema schema( org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		for ( Iterator<Schema> stream = this.schemata(); stream.hasNext(); ) {
			Schema schema = stream.next();
			if ( schema.wraps( dtpSchema)) {
				return schema;
			}
		}
		throw new IllegalArgumentException( "invalid dtp schema: " + dtpSchema);
	}

}
