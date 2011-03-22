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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.eclipse.datatools.connectivity.sqm.core.definition.DatabaseDefinition;
import org.eclipse.datatools.connectivity.sqm.internal.core.RDBCorePlugin;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterators.ResultSetIterator;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.DatabaseObject;
import org.eclipse.jpt.jpa.db.JptJpaDbPlugin;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Sequence;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Consolidate the behavior common to the typical DTP drivers.
 * 
 * @see Unknown
 */
abstract class AbstractDTPDriverAdapter
	implements DTPDriverAdapter
{
	/**
	 * The Dali database that wraps the corresponding DTP database.
	 */
	final Database database;

	/**
	 * DTP drivers return catalogs and schemata in a variety of ways....
	 */
	final CatalogStrategy catalogStrategy;

	/**
	 * The SQL spec says a <em>regular</em> (non-delimited) identifier should be
	 * folded to uppercase; but some databases do otherwise (e.g. Sybase).
	 */
	final FoldingStrategy foldingStrategy;


	AbstractDTPDriverAdapter(Database database) {
		super();
		this.database = database;
		this.catalogStrategy = this.buildCatalogStrategy();
		this.foldingStrategy = this.buildFoldingStrategy();
	}

	abstract CatalogStrategy buildCatalogStrategy();

	abstract FoldingStrategy buildFoldingStrategy();


	// ********** catalogs **********

	public boolean supportsCatalogs() {
		return this.catalogStrategy.supportsCatalogs();
	}

	public List<org.eclipse.datatools.modelbase.sql.schema.Catalog> getDTPCatalogs() {
		return this.catalogStrategy.getCatalogs();
	}

	/**
	 * Typically, the name of the default catalog is the user name.
	 */
	public final Iterable<String> getDefaultCatalogNames() {
		return this.supportsCatalogs() ? this.getDefaultCatalogNames_() : Collections.<String>emptyList();
	}

	final Iterable<String> getDefaultCatalogNames_() {
		ArrayList<String> names = new ArrayList<String>();
		this.addDefaultCatalogNamesTo(names);
		return names;
	}

	void addDefaultCatalogNamesTo(ArrayList<String> names) {
		names.add(this.getUserName());
	}


	// ********** schemas **********

	public List<org.eclipse.datatools.modelbase.sql.schema.Schema> getDTPSchemas() {
		try {
			return this.catalogStrategy.getSchemas();
		} catch (Exception ex) {
			throw new RuntimeException("driver adapter: " + this, ex); //$NON-NLS-1$
		}
	}

	/**
	 * Typically, the name of the default schema is the user name.
	 */
	public final Iterable<String> getDefaultSchemaNames() {
		ArrayList<String> names = new ArrayList<String>();
		this.addDefaultSchemaNamesTo(names);
		return names;
	}

	void addDefaultSchemaNamesTo(ArrayList<String> names) {
		names.add(this.getUserName());
	}


	// ********** name -> identifier **********

	public String convertNameToIdentifier(String name) {
		if (this.treatIdentifiersAsDelimited()) {
			return name;  // no delimiters necessary
		}
		if (this.nameRequiresDelimiters(name)) {
			return this.delimitName(name);
		}
		return name;
	}

	// TODO break into converting table and column names so MySQL works better
	public String convertNameToIdentifier(String name, String defaultName) {
		if (( ! this.treatIdentifiersAsDelimited()) &&  this.nameRequiresDelimiters(name)) {
			// no match possible
			return this.delimitName(name);
		}
		if (this.regularNamesMatch(name, defaultName)) {
			return null;
		}
		return name;
	}

	/**
	 * Return whether the specified database object name must be delimited
	 * when used in an SQL statement.
	 * If the name has any <em>special</em> characters (as opposed to letters,
	 * digits, and other allowed characters [e.g. underscores]), it requires
	 * delimiters.
	 * If the name is mixed case and the database folds undelimited names
	 * (to either uppercase or lowercase), it requires delimiters.
	 */
	boolean nameRequiresDelimiters(String name) {
		return (name.length() == 0)  //  an empty string must be delimited(?)
					|| this.nameIsReservedWord(name)
					|| this.nameContainsAnySpecialCharacters(name)
					|| this.nameIsNotFolded(name);
	}

	boolean nameIsReservedWord(String name) {
		return this.getDTPDefinition().isSQLKeyword(name);
	}

	// TODO make Database.getDTPDefinition() public?
	DatabaseDefinition getDTPDefinition() {
		return RDBCorePlugin.getDefault().getDatabaseDefinitionRegistry().getDefinition(this.database.getDTPDatabase());
	}

	/**
	 * Return whether the specified name contains any <em>special</em>
	 * characters that require the name to be delimited.
	 * <br>
	 * Pre-condition: the specified name is not empty
	 */
	boolean nameContainsAnySpecialCharacters(String name) {
		char[] string = name.toCharArray();
		if (this.characterIsNonRegularNameStart(string[0])) {
			return true;
		}
		for (int i = string.length; i-- > 1; ) {  // note: stop at 1
			if (this.characterIsNonRegularNamePart(string[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified character is non-<em>regular</em> for the first
	 * character of a name.
	 * Typically, databases are more restrictive about what characters can
	 * be used to <em>start</em> an identifier (as opposed to the characters
	 * allowed for the remainder of the identifier).
	 */
	boolean characterIsNonRegularNameStart(char c) {
		return ! this.characterIsRegularNameStart(c);
	}

	/**
	 * Return whether the specified character is <em>regular</em> for the first
	 * character of a name.
	 * The first character of an identifier can be:<ul>
	 * <li>a letter
	 * <li>any of the extended, database-specific, <em>regular</em> start characters
	 * </ul>
	 */
	boolean characterIsRegularNameStart(char c) {
		// all databases allow a letter
		return Character.isLetter(c)
				|| this.characterIsExtendedRegularNameStart(c);
	}

	boolean characterIsExtendedRegularNameStart(char c) {
		return this.arrayContains(this.getExtendedRegularNameStartCharacters(), c);
	}

	/**
	 * Return the <em>regular</em> characters, beyond letters, for the
	 * first character of a name.
	 * Return <code>null</code> if there are no "extended" characters.
	 */
	char[] getExtendedRegularNameStartCharacters() {
		return null;
	}

	/**
	 * Return whether the specified character is non-<em>regular</em> for the
	 * second and subsequent characters of a name.
	 */
	boolean characterIsNonRegularNamePart(char c) {
		return ! this.characterIsRegularNamePart(c);
	}

	/**
	 * Return whether the specified character is <em>regular</em> for the second and
	 * subsequent characters of a name.
	 * The second and subsequent characters of a <em>regular</em> name can be:<ul>
	 * <li>a letter
	 * <li>a digit
	 * <li>an underscore
	 * <li>any of the extended, database-specific, <em>regular</em> start characters
	 * <li>any of the extended, database-specific, <em>regular</em> part characters
	 * </ul>
	 */
	boolean characterIsRegularNamePart(char c) {
		// all databases allow a letter or digit
		return Character.isLetterOrDigit(c) ||
				(c == '_') ||
				this.characterIsExtendedRegularNameStart(c) ||
				this.characterIsExtendedRegularNamePart(c);
	}

	boolean characterIsExtendedRegularNamePart(char c) {
		return this.arrayContains(this.getExtendedRegularNamePartCharacters(), c);
	}

	/**
	 * Return the <em>regular</em> characters, beyond letters and digits and the
	 * <em>regular</em> first characters, for the second and subsequent characters
	 * of an identifier. Return <code>null</code> if there are no additional characters.
	 */
	char[] getExtendedRegularNamePartCharacters() {
		return null;
	}

	/**
	 * Return whether the specified name is not folded to the database's
	 * case, requiring it to be delimited.
	 */
	boolean nameIsNotFolded(String name) {
		return ! this.foldingStrategy.nameIsFolded(name);
	}

	/**
	 * Return whether the specified <em>regular</em> names match.
	 * Typically, <em>regular</em> identifiers are case-insensitive.
	 */
	boolean regularNamesMatch(String name1, String name2) {
		return name1.equalsIgnoreCase(name2);
	}

	/**
	 * Wrap the specified name in delimiters (typically double-quotes),
	 * converting it to an identifier.
	 */
	String delimitName(String name) {
		return StringTools.quote(name);
	}


	// ********** identifier -> name **********

	// not sure how to handle an empty string:
	// both "" and "\"\"" are converted to "" ...
	// convert "" to 'null' since empty strings must be delimited?
	public String convertIdentifierToName(String identifier) {
		if (identifier == null) {
			return null;
		}
		if (this.treatIdentifiersAsDelimited()) {
			return identifier;  // automatically delimited
		}
		if (this.identifierIsDelimited(identifier)) {
			return StringTools.undelimit(identifier);
		}
		return this.foldingStrategy.fold(identifier);
	}

	/**
	 * Return whether the specified identifier is <em>delimited</em>.
	 * The SQL-92 spec says identifiers should be delimited by
	 * double-quotes; but some databases allow otherwise (e.g. Sybase).
	 */
	boolean identifierIsDelimited(String identifier) {
		return StringTools.stringIsQuoted(identifier);
	}


	// ********** selecting database objects **********

	/**
	 * By default, convert the identifier to a name and perform a name lookup.
	 */
	public Catalog selectCatalogForIdentifier(Iterable<Catalog> catalogs, String identifier) {
		return this.selectDatabaseObjectForIdentifier(catalogs, identifier);
	}

	/**
	 * By default, convert the identifier to a name and perform a name lookup.
	 */
	public Schema selectSchemaForIdentifier(Iterable<Schema> schemata, String identifier) {
		return this.selectDatabaseObjectForIdentifier(schemata, identifier);
	}

	/**
	 * By default, convert the identifier to a name and perform a name lookup.
	 */
	public Table selectTableForIdentifier(Iterable<Table> tables, String identifier) {
		return this.selectDatabaseObjectForIdentifier(tables, identifier);
	}

	/**
	 * By default, convert the identifier to a name and perform a name lookup.
	 */
	public Sequence selectSequenceForIdentifier(Iterable<Sequence> sequences, String identifier) {
		return this.selectDatabaseObjectForIdentifier(sequences, identifier);
	}

	/**
	 * By default, convert the identifier to a name and perform a name lookup.
	 */
	public Column selectColumnForIdentifier(Iterable<Column> columns, String identifier) {
		return this.selectDatabaseObjectForIdentifier(columns, identifier);
	}

	/**
	 * By default, convert the identifier to a name and perform a name lookup.
	 */
	<T extends DatabaseObject> T selectDatabaseObjectForIdentifier(Iterable<T> databaseObjects, String identifier) {
		return this.selectDatabaseObjectNamed(databaseObjects, this.convertIdentifierToName(identifier));
	}

	/**
	 * We can tanslate identifiers on most databasee into a name we can treat as
	 * case-sensitive.
	 */
	<T extends DatabaseObject> T selectDatabaseObjectNamed(Iterable<T> databaseObjects, String name) {
		return this.selectDatabaseObjectNamedRespectCase(databaseObjects, name);
	}

	<T extends DatabaseObject> T selectDatabaseObjectNamedRespectCase(Iterable<T> databaseObjects, String name) {
		for (T databaseObject : databaseObjects) {
			if (databaseObject.getName().equals(name)) {
				return databaseObject;
			}
		}
		return null;
	}

	<T extends DatabaseObject> T selectDatabaseObjectNamedIgnoreCase(Iterable<T> databaseObjects, String name) {
		for (T databaseObject : databaseObjects) {
			if (databaseObject.getName().equalsIgnoreCase(name)) {
				return databaseObject;
			}
		}
		return null;
	}


	// ********** database queries **********

	List<Map<String, Object>> execute(String sql) {
		try {
			return this.execute_(sql);
		} catch (SQLException ex) {
			JptJpaDbPlugin.log("SQL: " + sql, ex); //$NON-NLS-1$
			return Collections.emptyList();
		}
	}

	List<Map<String, Object>> execute_(String sql) throws SQLException {
		Statement jdbcStatement = this.createJDBCStatement();
		List<Map<String, Object>> rows = Collections.emptyList();
		try {
			jdbcStatement.execute(sql);
			rows = this.buildRows(jdbcStatement.getResultSet());
		} finally {
			jdbcStatement.close();
		}
		return rows;
	}

	Statement createJDBCStatement() throws SQLException {
		return this.getConnectionProfile().getJDBCConnection().createStatement();
	}

	List<Map<String, Object>> buildRows(ResultSet resultSet) throws SQLException {
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		CollectionTools.addAll(rows, this.buildResultSetIterator(resultSet));
		return rows;
	}

	Iterator<Map<String, Object>> buildResultSetIterator(ResultSet resultSet) throws SQLException {
		return new ResultSetIterator<Map<String, Object>>(resultSet, new ListResultSetIteratorAdapter(resultSet.getMetaData()));
	}

	/**
	 * Convert each row in the result set into a map whose key is the column
	 * name and value is the column value.
	 */
	static class ListResultSetIteratorAdapter
		implements ResultSetIterator.Adapter<Map<String, Object>>
	{
		private final int columnCount;
		private final String[] columnNames;

		ListResultSetIteratorAdapter(ResultSetMetaData rsMetaData) throws SQLException {
			super();
			this.columnCount = rsMetaData.getColumnCount();
			this.columnNames = new String[this.columnCount + 1];  // leave the zero slot empty
			for (int i = 1; i <= this.columnCount; i++) {  // NB: ResultSet index/subscript is 1-based
				this.columnNames[i] = rsMetaData.getColumnName(i);
			}
		}

		public Map<String, Object> buildNext(ResultSet rs) throws SQLException {
			HashMap<String, Object> row = new HashMap<String, Object>(this.columnCount);
			for (int i = 1; i <= this.columnCount; i++) {  // NB: ResultSet index/subscript is 1-based
				row.put(this.columnNames[i], rs.getObject(i));
			}
			return row;
		}
	}


	// ********** misc **********

	String getUserName() {
		return this.convertIdentifierToName(this.getConnectionProfile().getUserName());
	}

	boolean treatIdentifiersAsDelimited() {
		return this.getConnectionProfile().treatIdentifiersAsDelimited();
	}

	ConnectionProfile getConnectionProfile() {
		return this.database.getConnectionProfile();
	}

	/**
	 * Convenience method: Add <code>null</code> check.
	 */
	boolean arrayContains(char[] array, char c) {
		return (array != null) && ArrayTools.contains(array, c);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.database);
	}
}
