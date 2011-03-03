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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.db.Database;

/**
 * Microsoft SQL Server
 */
class SQLServer
	extends AbstractDTPDriverAdapter
{
	SQLServer(Database database) {
		super(database);
	}

	@Override
	CatalogStrategy buildCatalogStrategy() {
		return new SimpleCatalogStrategy(this.database.getDTPDatabase());
	}

	@Override
	FoldingStrategy buildFoldingStrategy() {
		return NonFoldingStrategy.instance();
	}

	/**
	 * By default, SQL Server identifiers are case-sensitive, even without
	 * delimiters. This can depend on the collation setting....
	 */
	//TODO query database for collation setting
	@Override
	boolean regularNamesMatch(String name1, String name2) {
		return name1.equals(name2);
	}

	/**
	 * SQL Server will use the user-requested database; if that database is not
	 * found, it will default to <code>master</code>.
	 */
	@Override
	void addDefaultCatalogNamesTo(ArrayList<String> names) {
		names.add(this.database.getName());
		names.add(MASTER_CATALOG_IDENTIFIER);
	}
	private static final String MASTER_CATALOG_IDENTIFIER = "master";  //$NON-NLS-1$

	/**
	 * The default schema on SQL Server for any database (catalog) is
	 * <code>dbo</code>.
	 */
	@Override
	void addDefaultSchemaNamesTo(ArrayList<String> names) {
		names.add(DEFAULT_SCHEMA_NAME);
	}
	private static final String DEFAULT_SCHEMA_NAME = "dbo";  //$NON-NLS-1$

	@Override
	char[] getExtendedRegularNameStartCharacters() {
		return EXTENDED_REGULAR_NAME_START_CHARACTERS;
	}
	private static final char[] EXTENDED_REGULAR_NAME_START_CHARACTERS = new char[] { '_', '@', '#' };

	@Override
	char[] getExtendedRegularNamePartCharacters() {
		return EXTENDED_REGULAR_NAME_PART_CHARACTERS;
	}
	private static final char[] EXTENDED_REGULAR_NAME_PART_CHARACTERS = new char[] { '$' };

	//TODO query database for delimiter setting
	@Override
	String delimitName(String name) {
		return '[' + name + ']';
	}

	/**
	 * By default, SQL Server delimits identifiers with brackets
	 * (<code>[]</code>); but it
	 * can also be configured to use double-quotes.
	 */
	//TODO query database for delimiter setting
	@Override
	boolean identifierIsDelimited(String identifier) {
		return StringTools.stringIsBracketed(identifier)
					|| super.identifierIsDelimited(identifier);
	}


	// ********** factory **********

	static class Factory implements DTPDriverAdapterFactory {
		private static final String[] VENDORS = {
				"SQL Server" //$NON-NLS-1$
			};
		public String[] getSupportedVendors() {
			return VENDORS;
		}
		public DTPDriverAdapter buildAdapter(Database database) {
			return new SQLServer(database);
		}
	}
}
