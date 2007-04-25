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
	
	void catalogChanged( Catalog catalog, int eventType) {
		// do nothing
	}

	void schemaChanged( Schema schema, int eventType) {
		// do nothing
	}

	void tableChanged( Table table,  Schema schema,int eventType) {
		// do nothing
	}
	
	void refresh() {
		// do nothing
	}
	
	protected void dispose() {
		// do nothing
	}
	
	protected boolean connectionIsOnline() {
		return false;
	}

	// ********** queries **********

	public String getName() {
		return ClassTools.shortClassNameForObject( this);
	}

	public String getVendor() {
		return this.getName();
	}
	
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
	Set getCatalogs() {
		return Collections.emptySet();
	}

	// ***** schemata
	
	@Override
	Set getSchemata() {
		return Collections.emptySet();
	}
}
