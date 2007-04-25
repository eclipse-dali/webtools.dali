/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;
import org.eclipse.emf.common.util.EList;

/**
 *  Wrap a DTP Database
 */
public final class DTPDatabaseWrapper extends Database {
	
	final private ConnectionProfile profile;
	final private org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase;
	private ICatalogObjectListener databaseListener;
	
	private Set catalogs;  // lazy-initialized
	private Set schemata;  // lazy-initialized

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
	
	protected boolean connectionIsOnline() {
		return this.profile.isConnected();
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

	void refresh() {
		this.disposeSchemata();
		this.disposeCatalogs();
		
		this.schemata = null;
		this.catalogs = null;
	}
	
	void catalogChanged( Catalog catalog, int eventType) {
		this.profile.catalogChanged( catalog, this, eventType);
		return;
	}	
		
	void schemaChanged( Schema schema, int eventType) {
		this.profile.schemaChanged( schema, this, eventType);
		return;
	}

	void tableChanged( Table table,  Schema schema, int eventType) {
		this.profile.tableChanged( table, schema, this, eventType);
		return;
	}
	
	protected void dispose() {
		this.removeCatalogObjectListener(( ICatalogObject) this.dtpDatabase, this.databaseListener);

		this.disposeSchemata();
		this.disposeCatalogs();
	}

	private void disposeSchemata() {
		if( this.schemata != null) {
			for( Iterator i = this.schemata(); i.hasNext(); ) {
				(( Schema)i.next()).dispose();
			}
		}
	}
	
	private void disposeCatalogs() {
		if( this.catalogs != null) {
			for( Iterator i = this.catalogs(); i.hasNext(); ) {
				(( Catalog)i.next()).dispose();
			}
		}
	}
	
	// ********** queries **********

	public String getName() {

		return this.dtpDatabase.getName();
	}

	public String getVendor() {
		
		return this.dtpDatabase.getVendor();
	}
	
	public String getVersion() {
		
		return this.dtpDatabase.getVersion();
	}
	
	
	// ***** schemata

	synchronized Set getSchemata() {
		if( this.schemata == null) {
			this.schemata = this.buildSchemata();
		}
		return this.schemata;
	}

	private Set buildSchemata() {
		Set result;
		if( this.supportsCatalogs()) {
			result = this.getSchemataForCatalogNamed( this.profile.getCatalogName());
		}
		else {
			EList dtpSchemata = this.dtpDatabase.getSchemas();
			result = new HashSet( dtpSchemata.size());
			for( Iterator i = dtpSchemata.iterator(); i.hasNext(); ) {
				result.add( this.wrap(( org.eclipse.datatools.modelbase.sql.schema.Schema)i.next()));
			}
		}
		return result;
	}
	
	// ***** catalogs

	public boolean supportsCatalogs() {
		EList schemata = this.dtpDatabase.getSchemas();
		return ( schemata == null || schemata.size() == 0);
	}

	@Override
	public String getDefaultCatalogName() {
		
		if( !this.supportsCatalogs()) {	// this database doesn't support catalogs
			return "";
		}
		else {
			String userName = this.profile.getUserName();
			List dtpCatalogs = this.dtpDatabase.getCatalogs();
			for( Iterator i = dtpCatalogs.iterator(); i.hasNext(); ) {
				org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog = ( org.eclipse.datatools.modelbase.sql.schema.Catalog)i.next();
				if( dtpCatalog.getName().length() == 0) {	// special catalog that contains all schemata
					return "";
				}
				else if( dtpCatalog.getName().equals( userName)) {
					return userName;		// returns user name as default catalog
				}
			}
			throw new NoSuchElementException();
		}
	}
	
	synchronized Set getCatalogs() {
		if( this.catalogs == null) {
			this.catalogs = this.buildCatalogs();
		}
		return this.catalogs;
	}

	private Set buildCatalogs() {
		
		EList dtpCatalogs = this.dtpDatabase.getCatalogs();
		if( dtpCatalogs == null) {
			return Collections.emptySet();
		}
		Set result = new HashSet( dtpCatalogs.size());
		for( Iterator i = dtpCatalogs.iterator(); i.hasNext(); ) {
			result.add( this.wrap(( org.eclipse.datatools.modelbase.sql.schema.Catalog)i.next()));
		}
		return result;
	}
	
	//TODO case insensitive search
	//
	private Set getSchemataForCatalogNamed( String catalogName) {

		Catalog catalog = this.catalogNamed( catalogName);
		return ( catalog != null) ? catalog.buildSchemata() : Collections.emptySet();
	}
}
