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

class Sybase
	extends AbstractDTPDriverAdapter
{
	Sybase(Database database) {
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
	 * By default, Sybase identifiers are case-sensitive, even without
	 * delimiters. This can depend on the collation setting....
	 */
	//TODO query database for collation setting
	@Override
	boolean regularNamesMatch(String name1, String name2) {
		return name1.equals(name2);
	}

	/**
	 * Sybase will use the user-requested database; if that database is not
	 * found, it will default to <code>master</code>.
	 */
	@Override
	void addDefaultCatalogNamesTo(ArrayList<String> names) {
		names.add(this.database.getName());
		names.add(MASTER_CATALOG_NAME);
	}
	private static final String MASTER_CATALOG_NAME = "master";  //$NON-NLS-1$

	/**
	 * The typical default schema on Sybase for any database (catalog) is
	 * <code>dbo</code>.
	 * <br>
	 * Actually, the default schema is more like a search path:
	 * The server looks for a schema object (e.g. a table) first in the user's
	 * schema, then it looks for the schema object in the database owner's
	 * schema (<code>dbo</code>). As a result, it's really not possible to specify
	 * the "default" schema without knowing the schema object we are
	 * looking for.
	 * <br>
	 * (Note: the current 'user' is not the same thing as the current
	 * 'login' - see sp_adduser and sp_addlogin; so we probably can't
	 * use {@link org.eclipse.jpt.jpa.db.ConnectionProfile#getUserName()}.)
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
	private static final char[] EXTENDED_REGULAR_NAME_START_CHARACTERS = new char[] { '_', '@' };

	@Override
	char[] getExtendedRegularNamePartCharacters() {
		return EXTENDED_REGULAR_NAME_PART_CHARACTERS;
	}
	private static final char[] EXTENDED_REGULAR_NAME_PART_CHARACTERS = new char[] { '$', '¥', '£', '#' };

	//TODO query database for delimiter setting
	@Override
	String delimitName(String name) {
		return '[' + name + ']';
	}

	/**
	 * By default, Sybase delimits identifiers with brackets
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
				"Sybase_ASA", //$NON-NLS-1$
				"Sybase_ASE" //$NON-NLS-1$
			};
		public String[] getSupportedVendors() {
			return VENDORS;
		}
		public DTPDriverAdapter buildAdapter(Database database) {
			return new Sybase(database);
		}
	}
}
