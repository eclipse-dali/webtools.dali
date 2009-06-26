/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal.vendor;

import java.util.ArrayList;

import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Database;

class PostgreSQL
	extends AbstractVendor
{
	// singleton
	private static final Vendor INSTANCE = new PostgreSQL();

	/**
	 * Return the singleton.
	 */
	static Vendor instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private PostgreSQL() {
		super();
	}

	@Override
	public String getDTPVendorName() {
		return "postgres"; //$NON-NLS-1$
	}

	/**
	 * The PostgreSQL JDBC driver returns a single catalog from the call to
	 * DatabaseMetaData.getCatalogs() that has the same name as the
	 * database initially specified by the connection (in the JDBC URL).
	 * DTP uses this configuration unmodified. Unfortunately, the DTP
	 * database's name is not the same as the PostgreSQL database's
	 * name.
	 */
	@Override
	CatalogStrategy getCatalogStrategy() {
		return SimpleCatalogStrategy.instance();
	}

	@Override
	FoldingStrategy getFoldingStrategy() {
		return LowerCaseFoldingStrategy.instance();
	}

	/**
	 * The PostgreSQL database holds a single catalog that has the same name as
	 * the database.
	 */
	@Override
	void addDefaultCatalogIdentifiersTo(Database database, String userName, ArrayList<String> identifiers) {
		identifiers.add(this.buildDefaultCatalogIdentifier(database));
	}

	private String buildDefaultCatalogIdentifier(Database database) {
		return this.convertNameToIdentifier(this.buildDefaultCatalogName(database));
	}

	private String buildDefaultCatalogName(Database database) {
		return ((Catalog) database.getCatalogs().get(0)).getName();
	}

	/**
	 * PostgreSQL has a "schema search path". The default is:
	 *     "$user",public
	 * If the "$user" schema is not found, use the "public" schema.
	 */
	@Override
	void addDefaultSchemaIdentifiersTo(Database database, String userName, ArrayList<String> identifiers) {
		super.addDefaultSchemaIdentifiersTo(database, userName, identifiers);
		identifiers.add(PUBLIC_SCHEMA_IDENTIFIER);
	}
	private static final String PUBLIC_SCHEMA_IDENTIFIER = "public";  //$NON-NLS-1$

	@Override
	char[] getExtendedNormalNameStartCharacters() {
		return EXTENDED_NORMAL_NAME_START_CHARACTERS;
	}
	private static final char[] EXTENDED_NORMAL_NAME_START_CHARACTERS = new char[] { '_' };

	@Override
	char[] getExtendedNormalNamePartCharacters() {
		return EXTENDED_NORMAL_NAME_PART_CHARACTERS;
	}
	private static final char[] EXTENDED_NORMAL_NAME_PART_CHARACTERS = new char[] { '$' };

}
