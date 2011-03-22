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
import java.util.List;
import java.util.Map;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.DatabaseObject;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;

/**
 * MySQL is a bit unusual in that it does <em>not</em> fold <em>regular</em>
 * identifiers and its identifiers are <em>not</em> case-sensitive
 * (except in special cases, described below). Even <em>delimited</em> identifiers
 * are case-<em>in</em>sensitive. (Delimiters are only needed for identifiers
 * that contain special characters or are reserved words.)
 * <p>
 * MySQL handles the case-sensitivity of databases and tables according to the
 * value of the system variable <code>lower_case_table_names</code>:<ul>
 * <li>0 (Unix/Linux default): database and table names are <em>not</em> folded
 * and they are case-sensitive
 * <li>1 (Windows default): database and table names are folded to lowercase
 * and they are <em>not</em> case-sensitive
 * <li>2 (Mac OS X default): database and table names are <em>not</em> folded
 * and they are <em>not</em> case-sensitive
 * </ul>
 * These values will not work perfectly on all platforms because databases are
 * stored as directories and tables are stored as files in the underlying
 * O/S; and the actual case-sensitivity of the names will ulimately be
 * determined by the behavior of filenames on the O/S.
 * <p>
 * See <a href=http://dev.mysql.com/doc/refman/5.0/en/identifier-case-sensitivity.html>
 * Identifier Case Sensitivity</a>.
 */
class MySQL
	extends AbstractDTPDriverAdapter
{
	/** lazy-initialized; value comes from the database server */
	private int lower_case_table_names = -1;

	/** lazy-initialized; value comes from the database server */
	private Boolean ANSI_QUOTES = null;


	MySQL(Database database) {
		super(database);
	}

	/**
	 * The DTP model for MySQL has a database that contains no catalogs;
	 * but, instead, directly holds a single schema with the same name as
	 * the database. (This is hard-coded in
	 * {@link org.eclipse.datatools.enablement.mysql.catalog.MySqlCatalogDatabase#getSchemas()}.)
	 * Although you can qualify identifiers with a database name
	 * in MySQL, only the database specified at login seems to be available
	 * in the DTP model....
	 * <p>
	 * <strong>NB:</strong> In MySQL DDL, <code>SCHEMA</code> is a synonym
	 * for <code>DATABASE</code>; but the JDBC
	 * method {@link java.sql.DatabaseMetaData#getSchemas()} returns an empty list,
	 * while {@link java.sql.DatabaseMetaData#getCatalogs()} returns a list of the
	 * available databases.
	 * You can also use the JDBC method {@link java.sql.Connection#setCatalog(String)}
	 * to set the default database.
	 */
	@Override
	CatalogStrategy buildCatalogStrategy() {
		return new NoCatalogStrategy(this.database.getDTPDatabase());
	}

	/**
	 * There are some cases where MySQL folds identifiers, but using the
	 * non-folding strategy should work:<ul>
	 * <li>We only fold identifiers when
	 * converting them to names; but MySQL is case-<em>in</em>sensitive
	 * when it is folding database and table names
	 * (<code>lower_case_table_names</code>=1), so lookups will work
	 * fine without any folding anyways.
	 * <li>We only test whether a name is already folded when determining
	 * whether it must be delimited; and MySQL ignores delimiters
	 * when it comes to case-sensitivity, so we can leave any <em>regular</em>
	 * mixed-case names undelimited. (MySQL only requires delimiters for
	 * special characters and reserved words.)
	 * </ul>
	 */
	@Override
	FoldingStrategy buildFoldingStrategy() {
		return NonFoldingStrategy.instance();
	}

	@Override
	void addDefaultSchemaNamesTo(ArrayList<String> names) {
		names.add(this.database.getName());
	}


	// ********** identifiers/names **********

	/**
	 * MySQL is the only vendor that allows a digit.
	 * Although, the name cannnot be <em>all</em> digits
	 * (unless delimited).
	 */
	@Override
	boolean characterIsRegularNameStart(char c) {
		return Character.isDigit(c) || super.characterIsRegularNameStart(c);
	}

	@Override
	char[] getExtendedRegularNameStartCharacters() {
		return EXTENDED_REGULAR_NAME_START_CHARACTERS;
	}
	private static final char[] EXTENDED_REGULAR_NAME_START_CHARACTERS = new char[] { '_', '$' };

	@Override
	String delimitName(String name) {
		return StringTools.delimit(name, BACKTICK);
	}

	/**
	 * By default, MySQL delimits identifiers with backticks (<code>`</code>);
	 * but it can also be configured to use double-quotes.
	 */
	@Override
	boolean identifierIsDelimited(String identifier) {
		return StringTools.stringIsDelimited(identifier, BACKTICK)
					|| (StringTools.stringIsQuoted(identifier) && this.doubleQuoteIsIdentifierDelimiter());
	}
	private static final char BACKTICK = '`';

	/**
	 * This method should only affect whether entity
	 * generation will generate a specified table or column identifier when the
	 * default identifier is not a match. Worst case: Entity generation will
	 * generate explicit identifiers unnecessarily for columns when tables are
	 * case-sensitive. The generated annotation will be valid but the column
	 * name will be redundant.
	 */
	@Override
	boolean regularNamesMatch(String name1, String name2) {
		return this.tableNamesAreCaseSensitive() ?
				name1.equals(name2) :
				name1.equalsIgnoreCase(name2);
	}


	// ********** selecting database objects **********

	/**
	 * DTP schemata are MySQL databases(?). Of course, we only have one per
	 * connection....
	 */
	@Override
	public Schema selectSchemaForIdentifier(Iterable<Schema> schemata, String identifier) {
		return this.tableNamesAreCaseSensitive() ?
				this.selectDatabaseObjectForIdentifierRespectCase(schemata, identifier) :
				super.selectSchemaForIdentifier(schemata, identifier);
	}

	@Override
	public Table selectTableForIdentifier(Iterable<Table> tables, String identifier) {
		return this.tableNamesAreCaseSensitive() ?
				this.selectDatabaseObjectForIdentifierRespectCase(tables, identifier) :
				super.selectTableForIdentifier(tables, identifier);
	}

	private <T extends DatabaseObject> T selectDatabaseObjectForIdentifierRespectCase(Iterable<T> databaseObjects, String identifier) {
		return this.selectDatabaseObjectNamedRespectCase(databaseObjects, this.convertIdentifierToName(identifier));
	}

	/**
	 * MySQL database object names are usually case-insensitive;
	 * the exception being databases (schemata) and tables when the system
	 * variable <code>lower_case_table_names</code> is 0 (the default on
	 * UNIX/Linux). (For some odd reason, trigger names are also
	 * case-sensitive.... Fortunately, we don't care yet.)
	 */
	@Override
	<T extends DatabaseObject> T selectDatabaseObjectNamed(Iterable<T> databaseObjects, String name) {
		return this.selectDatabaseObjectNamedIgnoreCase(databaseObjects, name);
	}


	// ********** lower_case_table_names **********

	private boolean tableNamesAreCaseSensitive() {
		return this.getLowerCaseTableNames() == 0;
	}

	private int getLowerCaseTableNames() {
		if (this.lower_case_table_names == -1) {
			this.lower_case_table_names = this.buildLowerCaseTableNames();
		}
		return this.lower_case_table_names;
	}

	/**
	 * If we don't have a live connection (i.e. we are working off-line),
	 * use the current O/S default setting(?).
	 */
	private int buildLowerCaseTableNames() {
		int dbValue = this.getLowerCaseTableNamesFromDatabase();
		return (dbValue != -1) ? dbValue : this.getLowerCaseTableNamesFromOS();
	}

	/**
	 * See <a href=http://dev.mysql.com/doc/refman/5.0/en/identifier-case-sensitivity.html>
	 * Identifier Case Sensitivity</a>.
	 */
	private int getLowerCaseTableNamesFromDatabase() {
		if (this.getConnectionProfile().isDisconnected()) {
			return -1;
		}
		// the underscore is a wild character on MySQL, so we need to escape it
		List<Map<String, Object>> rows = this.execute("SHOW VARIABLES LIKE 'lower\\_case\\_table\\_names'"); //$NON-NLS-1$
		if (rows.isEmpty()) {
			return -1;
		}
		Map<String, Object> row = rows.get(0);
		if (Tools.valuesAreEqual(row.get("Variable_name"), "lower_case_table_names")) { //$NON-NLS-1$ //$NON-NLS-2$
			return Integer.valueOf((String) row.get("Value")).intValue(); //$NON-NLS-1$
		}
		return -1;
	}

	/**
	 * Make a best guess at what the setting might be:
	 * Return the default value for the current O/S (unfortunately this is the
	 * client O/S, not the MySQL Server O/S...).
	 */
	private int getLowerCaseTableNamesFromOS() {
		if (Tools.osIsMac()) {
			return 2;
		}
		if (Tools.osIsWindows()) {
			return 1;
		}
		return 0;  // Linux etc.
	}


	// ********** ANSI_QUOTES **********

	private boolean doubleQuoteIsIdentifierDelimiter() {
		return this.getANSIQuotes().booleanValue();
	}

	private Boolean getANSIQuotes() {
		if (this.ANSI_QUOTES == null) {
			this.ANSI_QUOTES = this.buildANSIQuotes();
		}
		return this.ANSI_QUOTES;
	}

	/**
	 * If we don't have a live connection (i.e. we are working off-line),
	 * return <code>FALSE</code> (the default setting).
	 */
	private Boolean buildANSIQuotes() {
		Boolean dbValue = this.getANSIQuotesFromDatabase();
		return (dbValue != null) ? dbValue : Boolean.FALSE;
	}

	/**
	 * See <a href=http://dev.mysql.com/doc/refman/5.0/en/server-sql-mode.html#sqlmode_ansi_quotes>
	 * ANSI_QUOTES</a>.
	 */
	private Boolean getANSIQuotesFromDatabase() {
		if (this.getConnectionProfile().isDisconnected()) {
			return null;
		}
		List<Map<String, Object>> rows = this.execute("SELECT @@SESSION.sql_mode"); //$NON-NLS-1$
		if (rows.isEmpty()) {
			return null;
		}
		Map<String, Object> row = rows.get(0);
		String sql_mode = (String) row.get("@@SESSION.sql_mode"); //$NON-NLS-1$
		if (sql_mode == null) {
			return null;
		}
		String[] modes = sql_mode.split(","); //$NON-NLS-1$
		return Boolean.valueOf(ArrayTools.contains(modes, "ANSI_QUOTES")); //$NON-NLS-1$
	}


	// ********** factory **********

	static class Factory implements DTPDriverAdapterFactory {
		private static final String[] VENDORS = {
				"MySql" //$NON-NLS-1$
			};
		public String[] getSupportedVendors() {
			return VENDORS;
		}
		public DTPDriverAdapter buildAdapter(Database database) {
			return new MySQL(database);
		}
	}
}
