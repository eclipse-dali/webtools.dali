/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.text.Collator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;

/**
 *  Wrap a DTP Catalogs
 */
final class Catalog extends DTPWrapper {
	private final Database database;
	private final org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog;
	private ICatalogObjectListener catalogListener;		//TODO listen for change
	
	// ********** constructors **********

	Catalog( Database database, org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog) {
		super();
		this.database = database;
		this.dtpCatalog = dtpCatalog;
	}

	// ********** behavior **********
	
	protected boolean connectionIsOnline() {
		return this.database.connectionIsOnline();
	}

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

	protected void dispose() {
//		this.removeCatalogObjectListener(( ICatalogObject) this.dtpCatalog, this.catalogListener);
	}
	
	boolean wraps( org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog) {
		return this.dtpCatalog == dtpCatalog;
	}
	
	// ********** queries **********

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

	synchronized Set buildSchemata() {
		Collection dtpSchemata = this.dtpCatalog.getSchemas();
		
		Set result = new HashSet( dtpSchemata.size());
		for( Iterator i = dtpSchemata.iterator(); i.hasNext(); ) {
			result.add( this.wrap(( org.eclipse.datatools.modelbase.sql.schema.Schema)i.next()));
		}
		return result;
	}
	
	private Schema wrap( org.eclipse.datatools.modelbase.sql.schema.Schema schema) {

		return new Schema( this.database, schema);
	}

	// ********** Comparable implementation **********

	public int compareTo( Object o) {
		return Collator.getInstance().compare( this.getName(), (( Catalog)o).getName());
	}

}
