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

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.datatools.connectivity.sqm.core.definition.DatabaseDefinition;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;
import org.eclipse.datatools.connectivity.sqm.internal.core.RDBCorePlugin;
import org.eclipse.emf.common.util.EList;

/**
 *  Wrap a DTP Database
 */
public final class DTPDatabaseWrapper extends Database {
	
	final ConnectionProfile profile;
	final org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase;
	private ICatalogObjectListener databaseListener;
	
	private Set<Catalog> catalogs;  // lazy-initialized
	private Set<Schema> schemata;  // lazy-initialized

	DTPDatabaseWrapper( ConnectionProfile profile, org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase) {
		super();
		this.dtpDatabase = dtpDatabase;
		this.profile = profile;
		this.initialize();
	}

	// ********** behavior **********

	private void initialize() {
		if( this.connectionIsOnline()) {
			this.databaseListener = this.buildDatabaseListener();
			this.addCatalogObjectListener(( ICatalogObject) this.dtpDatabase, this.databaseListener);
		}
	}

	@Override
	protected boolean connectionIsOnline() {
		return this.profile.connectionIsOnline();
	}
	
	private ICatalogObjectListener buildDatabaseListener() {
	   return new ICatalogObjectListener() {
		    public void notifyChanged( final ICatalogObject database, final int eventType) {     
				if( database == DTPDatabaseWrapper.this.dtpDatabase) {	
					DTPDatabaseWrapper.this.refresh();
					DTPDatabaseWrapper.this.profile.databaseChanged( DTPDatabaseWrapper.this, eventType);
				}
		    }
	    };
	}

	@Override
	void refresh() {
		this.disposeSchemata();
		this.disposeCatalogs();
		
		this.schemata = null;
		this.catalogs = null;
	}
	
	@Override
	void catalogChanged( Catalog catalog, int eventType) {
		this.profile.catalogChanged( catalog, this, eventType);
		return;
	}	
		
	@Override
	void schemaChanged( Schema schema, int eventType) {
		this.profile.schemaChanged( schema, this, eventType);
		return;
	}

	@Override
	void tableChanged( Table table,  Schema schema, int eventType) {
		this.profile.tableChanged( table, schema, this, eventType);
		return;
	}
	
	@Override
	protected void dispose() {
		this.removeCatalogObjectListener(( ICatalogObject) this.dtpDatabase, this.databaseListener);

		this.disposeSchemata();
		this.disposeCatalogs();
	}

	private void disposeSchemata() {
		if( this.schemata != null) {
			for( Iterator<Schema> stream = this.schemata(); stream.hasNext(); ) {
				stream.next().dispose();
			}
		}
	}
	
	private void disposeCatalogs() {
		if( this.catalogs != null) {
			for( Iterator<Catalog> stream = this.catalogs(); stream.hasNext(); ) {
				stream.next().dispose();
			}
		}
	}
	
	
	// ********** queries **********

	@Override
	public String getName() {

		return this.dtpDatabase.getName();
	}

	@Override
	public String getVendor() {
		
		return this.dtpDatabase.getVendor();
	}
	
	@Override
	public String getVersion() {
		
		return this.dtpDatabase.getVersion();
	}
	
	@Override
	public DatabaseDefinition dtpDefinition() {
		return RDBCorePlugin.getDefault().getDatabaseDefinitionRegistry().getDefinition(this.dtpDatabase);
	}

	
	// ***** schemata

	@Override
	synchronized Set<Schema> getSchemata() {
		if( this.schemata == null) {
			this.schemata = this.buildSchemata();
		}
		return this.schemata;
	}

	@SuppressWarnings("unchecked")
	private EList<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata() {
		return this.dtpDatabase.getSchemas();
	}

	private Set<Schema> buildSchemata() {
		Set<Schema> result;
		if( this.supportsCatalogs()) {
			result = this.getSchemataForCatalogNamed( this.profile.getCatalogName());
		}
		else {
			EList<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata = this.dtpSchemata();
			result = new HashSet<Schema>( dtpSchemata.size());
			for (org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema : dtpSchemata) {
				result.add( this.wrap(dtpSchema));
			}
		}
		return result;
	}
	
	// ***** catalogs

	@Override
	public boolean supportsCatalogs() {
		EList<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata = this.dtpSchemata();
		return ( dtpSchemata == null || dtpSchemata.size() == 0);
	}

	@SuppressWarnings("unchecked")
	private EList<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs() {
		return this.dtpDatabase.getCatalogs();
	}

	@Override
	public String getDefaultCatalogName() {
		
		if( !this.supportsCatalogs()) {	// this database doesn't support catalogs
			return "";
		}
		String userName = this.profile.getUserName();
		for (org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog : this.dtpCatalogs()) {
			if( dtpCatalog.getName().length() == 0) {	// special catalog that contains all schemata
				return "";
			}
			else if( dtpCatalog.getName().equals( userName)) {
				return userName;		// returns user name as default catalog
			}
			else if( dtpCatalog.getName().equals( this.getName())) {
				return this.getName();		 // special catalog with same name as DB (PostgreSQL)
			}
		}
		throw new NoSuchElementException();
	}
	
	@Override
	synchronized Set<Catalog> getCatalogs() {
		if( this.catalogs == null) {
			this.catalogs = this.buildCatalogs();
		}
		return this.catalogs;
	}

	private Set<Catalog> buildCatalogs() {
		
		EList<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs = this.dtpCatalogs();
		if( dtpCatalogs == null) {
			return Collections.emptySet();
		}
		Set<Catalog> result = new HashSet<Catalog>( dtpCatalogs.size());
		for (org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog : dtpCatalogs) {
			result.add( this.wrap(dtpCatalog));
		}
		return result;
	}
	
	private Set<Schema> getSchemataForCatalogNamed( String catalogName) {

		Catalog catalog = this.catalogNamed( catalogName);
		return ( catalog != null) ? catalog.buildSchemata() : Collections.<Schema>emptySet();
	}
}
