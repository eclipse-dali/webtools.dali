/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.util.Collections;
import java.util.Set;
import org.eclipse.jpt.utility.internal.ClassTools;

/**
 *  NullDatabase
 */
public final class NullDatabase extends Database {

	private static NullDatabase INSTANCE;

	/**
	 * singleton support
	 */
	static synchronized Database instance() {
		if( INSTANCE == null) {
			INSTANCE = new NullDatabase();
		}
		return INSTANCE;
	}

	private NullDatabase() {
		super();
	}

	// ********** behavior **********
	
	@Override
	void catalogChanged( Catalog catalog, int eventType) {
		// do nothing
	}

	@Override
	void schemaChanged( Schema schema, int eventType) {
		// do nothing
	}

	@Override
	void tableChanged( Table table,  Schema schema,int eventType) {
		// do nothing
	}
	
	@Override
	void refresh() {
		// do nothing
	}
	
	@Override
	protected void dispose() {
		// do nothing
	}
	
	@Override
	protected boolean connectionIsOnline() {
		return false;
	}

	// ********** queries **********

	@Override
	public String getName() {
		return ClassTools.shortClassNameForObject( this);
	}

	@Override
	public String getVendor() {
		return this.getName();
	}
	
	@Override
	public String getVersion() {
		return "";
	}

	// ***** catalogs

	@Override
	public boolean supportsCatalogs() {
		return false;
	}

	@Override
	public String getDefaultCatalogName() {
		return "";
	}

	@Override
	Set<Catalog> getCatalogs() {
		return Collections.emptySet();
	}

	// ***** schemata
	
	@Override
	Set<Schema> getSchemata() {
		return Collections.emptySet();
	}
}
