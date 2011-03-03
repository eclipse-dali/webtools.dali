/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.driver;

import java.util.ArrayList;
import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.jpt.jpa.db.Database;

class PostgreSQL
	extends AbstractDTPDriverAdapter
{
	PostgreSQL(Database database) {
		super(database);
	}

	/**
	 * The PostgreSQL JDBC driver returns a single catalog from the call to
	 * {@link java.sql.DatabaseMetaData#getCatalogs()} that has the same name as the
	 * database initially specified by the connection (in the JDBC URL).
	 * DTP uses this configuration unmodified. Unfortunately, the DTP
	 * database's name is not the same as the PostgreSQL database's
	 * name.
	 */
	@Override
	CatalogStrategy buildCatalogStrategy() {
		return new SimpleCatalogStrategy(this.database.getDTPDatabase());
	}

	@Override
	FoldingStrategy buildFoldingStrategy() {
		return LowerCaseFoldingStrategy.instance();
	}

	/**
	 * The PostgreSQL database holds a single catalog that has the same name as
	 * the database.
	 */
	@Override
	void addDefaultCatalogNamesTo(ArrayList<String> names) {
		names.add(this.buildDefaultCatalogName());
	}

	private String buildDefaultCatalogName() {
		return ((Catalog) this.database.getDTPDatabase().getCatalogs().get(0)).getName();
	}

	/**
	 * PostgreSQL has a "schema search path". The default is:<br><code>
	 *     "$user",public
	 * </code><br>
	 * If the <code>"$user"</code> schema is not found, use the
	 * <code>"public"</code> schema.
	 */
	@Override
	void addDefaultSchemaNamesTo(ArrayList<String> names) {
		super.addDefaultSchemaNamesTo(names);
		names.add(PUBLIC_SCHEMA_NAME);
	}
	private static final String PUBLIC_SCHEMA_NAME = "public";  //$NON-NLS-1$

	@Override
	char[] getExtendedRegularNameStartCharacters() {
		return EXTENDED_REGULAR_NAME_START_CHARACTERS;
	}
	private static final char[] EXTENDED_REGULAR_NAME_START_CHARACTERS = new char[] { '_' };

	@Override
	char[] getExtendedRegularNamePartCharacters() {
		return EXTENDED_REGULAR_NAME_PART_CHARACTERS;
	}
	private static final char[] EXTENDED_REGULAR_NAME_PART_CHARACTERS = new char[] { '$' };


	// ********** factory **********

	static class Factory implements DTPDriverAdapterFactory {
		private static final String[] VENDORS = {
				"postgres" //$NON-NLS-1$
			};
		public String[] getSupportedVendors() {
			return VENDORS;
		}
		public DTPDriverAdapter buildAdapter(Database database) {
			return new PostgreSQL(database);
		}
	}
}
