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

import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.jpt.utility.internal.StringTools;

class Sybase
	extends AbstractVendor
{
	private final String dtpVendorName;

	static final Vendor ASA = new Sybase("Sybase_ASA"); //$NON-NLS-1$
	static final Vendor ASE = new Sybase("Sybase_ASE"); //$NON-NLS-1$

	static Vendor asa() {
		return ASA;
	}

	static Vendor ase() {
		return ASE;
	}

	/**
	 * Ensure only static instances.
	 */
	private Sybase(String dtpVendorName) {
		super();
		this.dtpVendorName = dtpVendorName;
	}

	@Override
	public String getDTPVendorName() {
		return this.dtpVendorName;
	}

	@Override
	CatalogStrategy getCatalogStrategy() {
		return SimpleCatalogStrategy.instance();
	}

	/**
	 * By default, Sybase identifiers are case-sensitive, even without
	 * delimiters. This can depend on the collation setting....
	 */
	@Override
	FoldingStrategy getFoldingStrategy() {
		return NonFoldingStrategy.instance();
	}

	/**
	 * The typical default schema on Sybase for any database (catalog) is
	 * 'dbo'.
	 * 
	 * Actually, the default schema is more like a search path:
	 * The server looks for a schema object (e.g table) first in the user's
	 * schema, the it look for the schema object in the database owner's
	 * schema (dbo). As a result, it's really not possible to specify
	 * the "default" schema without knowing the schema object we are
	 * looking for.
	 * 
	 * (Note: the current 'user' is not the same thing as the current
	 * 'login' - see sp_adduser and sp_addlogin; so we probably can't
	 * use ConnectionProfile#getUserName().)
	 */
	@Override
	void addDefaultSchemaIdentifiersTo(Database database, String userName, ArrayList<String> identifiers) {
		identifiers.add(DEFAULT_SCHEMA_IDENTIFIER);
	}
	private static final String DEFAULT_SCHEMA_IDENTIFIER = "dbo";  //$NON-NLS-1$

	@Override
	char[] getExtendedNormalNameStartCharacters() {
		return EXTENDED_NORMAL_NAME_START_CHARACTERS;
	}
	private static final char[] EXTENDED_NORMAL_NAME_START_CHARACTERS = new char[] { '_', '@' };

	@Override
	char[] getExtendedNormalNamePartCharacters() {
		return EXTENDED_NORMAL_NAME_PART_CHARACTERS;
	}
	private static final char[] EXTENDED_NORMAL_NAME_PART_CHARACTERS = new char[] { '$', '¥', '£', '#' };

	/**
	 * By default, Sybase delimits identifiers with brackets ([]); but it
	 * can also be configured to use double-quotes.
	 */
	@Override
	boolean identifierIsDelimited(String identifier) {
		return StringTools.stringIsBracketed(identifier)
					|| super.identifierIsDelimited(identifier);
	}

}
