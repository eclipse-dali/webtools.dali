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

class MySQL
	extends AbstractVendor
{
	// singleton
	private static final Vendor INSTANCE = new MySQL();

	/**
	 * Return the singleton.
	 */
	static Vendor instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private MySQL() {
		super();
	}

	@Override
	public String getDTPVendorName() {
		return "MySql"; //$NON-NLS-1$
	}

	/**
	 * The DTP model for MySQL has a database that contains no catalogs;
	 * but, instead, directly holds a single schema with the same name as
	 * the database. (This is hard-coded in MySqlCatalogDatabase.getSchemas().)
	 * Although you can qualify identifiers with a database name
	 * in MySQL, only the database specified at login seems to be available
	 * in the DTP model....
	 * 
	 * NB: In MySQL DDL, SCHEMA is a synonym for DATABASE; but the JDBC
	 * method DatabaseMetaData.getSchemas() returns an empty list,
	 * while getCatalogs() returns a list of the available databases.
	 * You can also use the JDBC method Connection.setCatalog(String) to
	 * set the default database.
	 */
	@Override
	CatalogStrategy getCatalogStrategy() {
		return NoCatalogStrategy.instance();
	}

	/**
	 * MySQL is a bit unusual, so we force exact matches.
	 * (e.g. MySQL folds database and table names to lowercase on Windows
	 * by default; but that default can be changed by the
	 * 'lower_case_table_names' system variable. This is because databases are
	 * stored as directories and tables are stored as files in the underlying
	 * O/S; and the case-sensitivity of the names is determined by the behavior
	 * of filenames on the O/S. Then, to complicate things,
	 * none of the other identifiers, like index and column names, are folded;
	 * but they are case-insensitive, unless delimited. See
	 * http://dev.mysql.com/doc/refman/5.0/en/identifier-case-sensitivity.html.)
	 */
	@Override
	FoldingStrategy getFoldingStrategy() {
		return NonFoldingStrategy.instance();
	}

	@Override
	void addDefaultSchemaIdentifiersTo(Database database, String userName, ArrayList<String> identifiers) {
		identifiers.add(this.convertNameToIdentifier(database.getName()));  // hmmm... ~bjv
	}

	/**
	 * MySQL is the only vendor that allows a digit.
	 * Although, the name cannnot be *all* digits.
	 */
	@Override
	boolean characterIsNormalNameStart(char c) {
		return Character.isDigit(c) || super.characterIsNormalNameStart(c);
	}

	@Override
	char[] getExtendedNormalNameStartCharacters() {
		return EXTENDED_NORMAL_NAME_START_CHARACTERS;
	}
	private static final char[] EXTENDED_NORMAL_NAME_START_CHARACTERS = new char[] { '_', '$' };

	/**
	 * By default, MySQL delimits identifiers with backticks (`); but it
	 * can also be configured to use double-quotes.
	 */
	@Override
	boolean identifierIsDelimited(String identifier) {
		return StringTools.stringIsDelimited(identifier, BACKTICK)
					|| super.identifierIsDelimited(identifier);
	}
	private static final char BACKTICK = '`';

}
