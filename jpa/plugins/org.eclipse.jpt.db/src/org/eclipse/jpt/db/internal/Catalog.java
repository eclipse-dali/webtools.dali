/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.text.Collator;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;
import org.eclipse.emf.common.util.EList;

/**
 *  Wrap a DTP Catalogs
 */
final class Catalog extends DTPWrapper implements Comparable<Catalog> {
	private final Database database;
	final org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog;
	@SuppressWarnings("unused")
	private ICatalogObjectListener catalogListener;		//TODO listen for change
	
	// ********** constructors **********

	Catalog( Database database, org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog) {
		super();
		this.database = database;
		this.dtpCatalog = dtpCatalog;
	}

	// ********** behavior **********
	
	@Override
	protected boolean connectionIsOnline() {
		return this.database.connectionIsOnline();
	}

	@SuppressWarnings("unused")
	private ICatalogObjectListener buildCatalogListener() {
       return new ICatalogObjectListener() {
    	    public void notifyChanged( final ICatalogObject catalog, final int eventType) {     
    			if( catalog == Catalog.this.dtpCatalog) {	
//    				Catalog.this.refresh();
//    				Catalog.this.database.catalogChanged( Catalog.this, eventType);
    			}
    	    }
        };
    }

	@Override
	protected void dispose() {
//		this.removeCatalogObjectListener(( ICatalogObject) this.dtpCatalog, this.catalogListener);
	}
	
	boolean wraps( org.eclipse.datatools.modelbase.sql.schema.Catalog catalog) {
		return this.dtpCatalog == catalog;
	}
	
	// ********** queries **********

	@Override
	public String getName() {
		return this.dtpCatalog.getName();
	}
	
	boolean isCaseSensitive() {
		return this.database.isCaseSensitive();
	}

	Column column( org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.database.column(dtpColumn);
	}

	// ***** schemata

	synchronized Set<Schema> buildSchemata() {
		EList<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata = this.dtpSchemata();
		
		Set<Schema> result = new HashSet<Schema>( dtpSchemata.size());
		for (org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema : dtpSchemata) {
			result.add( this.wrap(dtpSchema));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	private EList<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata() {
		return this.dtpCatalog.getSchemas();
	}

	private Schema wrap( org.eclipse.datatools.modelbase.sql.schema.Schema schema) {

		return new Schema( this.database, schema);
	}

	// ********** Comparable implementation **********

	public int compareTo( Catalog catalog) {
		return Collator.getInstance().compare( this.getName(), catalog.getName());
	}

}
