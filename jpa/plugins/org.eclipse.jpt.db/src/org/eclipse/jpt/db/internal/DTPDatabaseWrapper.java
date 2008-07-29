/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.text.Collator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.datatools.connectivity.sqm.core.definition.DatabaseDefinition;
import org.eclipse.datatools.connectivity.sqm.internal.core.RDBCorePlugin;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.DatabaseObject;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * Wrap a DTP Database.
 * Sometimes the database will directly hold schemata; but if the database
 * supports catalogs, it will not hold the schemata directly, but will delegate
 * to the "default" catalog.
 */
final class DTPDatabaseWrapper
	extends DTPSchemaContainerWrapper
	implements Database
{
	// the wrapped DTP database
	private final org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase;

	// lazy-initialized
	private DTPCatalogWrapper[] catalogs;

	// lazy-initialized - but it can be 'null' so we use a flag
	private DTPCatalogWrapper defaultCatalog;
	private boolean defaultCatalogCalculated = false;


	private static final DTPCatalogWrapper[] EMPTY_CATALOGS = new DTPCatalogWrapper[0];


	// ********** constants **********

	private static final String POSTGRESQL_PUBLIC_SCHEMA_NAME = "public";  //$NON-NLS-1$


	// ********** vendors **********

	static final String DERBY_VENDOR = "Derby";  //$NON-NLS-1$
	static final String HSQLDB_VENDOR = "HSQLDB";  //$NON-NLS-1$
	static final String DB2_UDB_I_SERIES_VENDOR = "DB2 UDB iSeries";  //$NON-NLS-1$
	static final String DB2_UDB_VENDOR = "DB2 UDB";  //$NON-NLS-1$
	static final String DB2_UDB_Z_SERIES_VENDOR = "DB2 UDB zSeries";  //$NON-NLS-1$
	static final String INFORMIX_VENDOR = "Informix";  //$NON-NLS-1$
	static final String SQL_SERVER_VENDOR = "SQL Server";  //$NON-NLS-1$
	static final String MYSQL_VENDOR = "MySql";  //$NON-NLS-1$
	static final String ORACLE_VENDOR = "Oracle";  //$NON-NLS-1$
	static final String POSTGRES_VENDOR = "postgres";  //$NON-NLS-1$
	static final String MAXDB_VENDOR = "MaxDB";  //$NON-NLS-1$
	static final String SYBASE_ASA_VENDOR = "Sybase_ASA";  //$NON-NLS-1$
	static final String SYBASE_ASE_VENDOR = "Sybase_ASE";  //$NON-NLS-1$


	// ********** constructor **********

	DTPDatabaseWrapper(DTPConnectionProfileWrapper connectionProfile, org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase) {
		super(connectionProfile, dtpDatabase);
		this.dtpDatabase = dtpDatabase;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().databaseChanged(this);
	}

	@Override
	public DTPDatabaseWrapper getDatabase() {
		return this;
	}


	// ********** DTPSchemaContainerWrapper implementation **********

	@Override
	@SuppressWarnings("unchecked")
	List<org.eclipse.datatools.modelbase.sql.schema.Schema> getDTPSchemata() {
		return this.dtpDatabase.getSchemas();
	}

	@Override
	DTPCatalogWrapper getCatalog() {
		return null;  // catalog not supported
	}

	@Override
	DTPSchemaWrapper getSchema(org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		return this.getSchema_(dtpSchema);
	}

	@Override
	DTPTableWrapper getTable(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		return this.getTable_(dtpTable);
	}

	@Override
	DTPColumnWrapper getColumn(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.getColumn_(dtpColumn);
	}


	// ********** DatabaseObject implementation **********

	public String getName() {
		return this.dtpDatabase.getName();
	}


	// ********** Database implementation **********

	public String getVendor() {
		return this.dtpDatabase.getVendor();
	}

	public String getVersion() {
		return this.dtpDatabase.getVersion();
	}

	// override to make method public since it's in the Database interface
	@Override
	public <T extends DatabaseObject> T getDatabaseObjectNamed(T[] databaseObjects, String name) {
		return super.getDatabaseObjectNamed(databaseObjects, name);
	}

	// ***** catalogs

	public boolean supportsCatalogs() {
		// if the DTP database does not have any schemata, it must have catalogs...
		List<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata = this.getDTPSchemata();
		return (dtpSchemata == null) || dtpSchemata.isEmpty();
	}

	public Iterator<Catalog> catalogs() {
		return new ArrayIterator<Catalog>(this.getCatalogs());
	}

	private synchronized DTPCatalogWrapper[] getCatalogs() {
		if (this.catalogs == null) {
			this.catalogs = this.buildCatalogs();
		}
		return this.catalogs;
	}

	private DTPCatalogWrapper[] buildCatalogs() {
		List<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs = this.getDTPCatalogs();
		if ((dtpCatalogs == null) || dtpCatalogs.isEmpty()) {
			return EMPTY_CATALOGS;
		}
		DTPCatalogWrapper[] result = new DTPCatalogWrapper[dtpCatalogs.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPCatalogWrapper(this, dtpCatalogs.get(i));
		}
		return result;
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.schema.Catalog> getDTPCatalogs() {
		return this.dtpDatabase.getCatalogs();
	}

	public int catalogsSize() {
		return this.getCatalogs().length;
	}

	public Iterator<String> catalogNames() {
		return new TransformationIterator<Catalog, String>(this.catalogs()) {
			@Override
			protected String transform(Catalog catalog) {
				 return catalog.getName();
			}
		};
	}

	public synchronized DTPCatalogWrapper getDefaultCatalog() {
		if ( ! this.defaultCatalogCalculated) {
			this.defaultCatalogCalculated = true;
			this.defaultCatalog = this.buildDefaultCatalog();
		}
		return this.defaultCatalog;
	}

	private DTPCatalogWrapper buildDefaultCatalog() {
		if ( ! this.supportsCatalogs()) {
			return null;
		}
		for (DTPCatalogWrapper catalog : this.getCatalogs()) {
			String catalogName = catalog.getName();
			if (catalogName.length() == 0) {
				return catalog;  // special catalog that contains all schemata (Derby)
			}
			if (catalogName.equals(this.getConnectionProfile().getUserName())) {
				return catalog;  // user name is default catalog
			}
			if (catalogName.equals(this.getName())) {
				return catalog;  // special catalog with same name as DB (PostgreSQL)
			}
		}
		throw new IllegalStateException("unknown default catalog");  //$NON-NLS-1$
	}

	/**
	 * return the catalog for the specified DTP catalog
	 */
	DTPCatalogWrapper getCatalog(org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog) {
		for (DTPCatalogWrapper catalog : this.getCatalogs()) {
			if (catalog.wraps(dtpCatalog)) {
				return catalog;
			}
		}
		throw new IllegalArgumentException("invalid DTP catalog: " + dtpCatalog);  //$NON-NLS-1$
	}

	public DTPCatalogWrapper getCatalogNamed(String name) {
		return this.getDatabaseObjectNamed(this.getCatalogs(), name);
	}

	// ***** schemata

	@Override
	synchronized DTPSchemaWrapper[] getSchemata() {
		DTPCatalogWrapper cat = this.getDefaultCatalog();
		return (cat == null) ? super.getSchemata() : cat.getSchemata();
	}

	public DTPSchemaWrapper getDefaultSchema() {
		DTPSchemaWrapper schema = this.getSchemaNamed(this.getConnectionProfile().getUserName());
		if (schema != null) {
			return schema;
		}
		// PostgreSQL has a "schema search path" - the default is:
		//     "$user",public
		// so if "$user" is not found, return public
		if (this.getVendor().equals(POSTGRES_VENDOR)) {
			return this.getSchemaNamed(POSTGRESQL_PUBLIC_SCHEMA_NAME);
		}
		// MySQL database has a single schema with the same name as the database
		if (this.getVendor().equals(MYSQL_VENDOR)) {
			return this.getSchemaNamed(this.getName());
		}
		return null;
	}


	// ********** identifiers **********

	/**
	 * Return the database object with the specified name. If the name is
	 * "delimited" (typically with double-quotes), it will be used without any
	 * folding. If the name is "normal" (i.e. not delimited), it will be
	 * folded to the appropriate case (typically uppercase).
	 * @see #identifierIsDelimited(String)
	 * @see #foldIdentifier(String)
	 * 
	 * Since the database has the appropriate state to compare identifiers,
	 * the connection profile delegates to here when using the default
	 * "database finder".
	 */
	// TODO convert embedded quotes?
	<T extends DatabaseObject> T getDatabaseObjectNamed_(T[] databaseObjects, String name) {
		name = this.normalizeIdentifier(name);
		for (T dbObject : databaseObjects) {
			if (dbObject.getName().equals(name)) {
				return dbObject;
			}
		}
		return null;
	}

	private String normalizeIdentifier(String identifier) {
		if (this.identifierIsDelimited(identifier)) {
			return StringTools.unwrap(identifier);
		}
		return this.foldIdentifier(identifier);
	}

	/**
	 * Return whether the specified identifier is "delimited" for the current
	 * database (typically with double-quotes).
	 */
	private boolean identifierIsDelimited(String identifier) {
		if (this.vendorAllowsQuoteDelimiters()
				&& StringTools.stringIsQuoted(identifier)) {
			return true;
		}
		if (this.vendorAllowsBracketDelimiters()
				&& StringTools.stringIsBracketed(identifier)) {
			return true;
		}
		if (this.vendorAllowsBacktickDelimiters()
				&& StringTools.stringIsDelimited(identifier, BACKTICK)) {
			return true;
		}
		return false;
	}

	/**
	 * Return whether the database allows identifiers to delimited with
	 * quotes: "FOO".
	 */
	boolean vendorAllowsQuoteDelimiters() {
		// all platforms allow identifiers to be delimited by quotes
		return true;
	}

	/**
	 * Return whether the database allows identifiers to delimited with
	 * brackets: [FOO].
	 */
	boolean vendorAllowsBracketDelimiters() {
		String vendor = this.getVendor();
		return vendor.equals(SQL_SERVER_VENDOR)
				|| vendor.equals(SYBASE_ASE_VENDOR)
				|| vendor.equals(SYBASE_ASA_VENDOR);
	}

	/**
	 * Return whether the database allows identifiers to delimited with
	 * backticks: `FOO`.
	 */
	boolean vendorAllowsBacktickDelimiters() {
		String vendor = this.getVendor();
		return vendor.equals(MYSQL_VENDOR);
	}

	private static final char BACKTICK = '`';

	/**
	 * Fold the specified identifier to the appropriate case.
	 * The SQL-92 spec says a "normal" (non-delimited) identifier should be
	 * folded to uppercase; but some databases do otherwise (e.g. PostgreSQL).
	 * 
	 * According to on-line documentation I could find:  ~bjv
	 * The following databases fold to uppercase:
	 *     Derby
	 *     Oracle
	 *     DB2
	 *     HSQLDB
	 *     MaxDB
	 * The following databases fold to lowercase:
	 *     PostgreSQL
	 *     Informix
	 *     MySQL  (sorta - depends on underlying O/S file system and env var)
	 * The following databases do not fold:
	 *     MS SQL Server (might depend on collation setting...)
	 *     Sybase (might depend on collation setting...)
	 */
	private String foldIdentifier(String identifier) {
		if (this.vendorFoldsToLowercase()) {
			return identifier.toLowerCase();
		}
		if (this.vendorFoldsToUppercase()) {
			return identifier.toUpperCase();
		}
		if (this.vendorDoesNotFold()) {
			return identifier;
		}
		throw new IllegalStateException("unknown vendor folding: " + this.getVendor()); //$NON-NLS-1$
	}

	/**
	 * Return whether the database folds non-delimited identifiers to lowercase.
	 */
	boolean vendorFoldsToLowercase() {
		String vendor = this.getVendor();
		return vendor.equals(POSTGRES_VENDOR)
				|| vendor.equals(INFORMIX_VENDOR);
	}

	/**
	 * Return whether the database does not fold non-delimited identifiers to
	 * lowercase.
	 */
	boolean vendorDoesNotFoldToLowercase() {
		return ! this.vendorFoldsToLowercase();
	}

	/**
	 * Return whether the database folds non-delimited identifiers to uppercase.
	 */
	boolean vendorFoldsToUppercase() {
		return this.vendorFolds()
				&& this.vendorDoesNotFoldToLowercase();
	}

	/**
	 * Return whether the database does not fold non-delimited identifiers to
	 * uppercase.
	 */
	boolean vendorDoesNotFoldToUppercase() {
		return ! this.vendorFoldsToUppercase();
	}

	/**
	 * Return whether the database folds non-delimited identifiers to either
	 * uppercase or lowercase.
	 */
	boolean vendorFolds() {
		return ! this.vendorDoesNotFold();
	}

	/**
	 * Return whether the database does not fold non-delimited identifiers to
	 * either uppercase or lowercase (i.e. the identifier is used unmodified).
	 * These guys are bit flaky, so we force an exact match.
	 * (e.g. MySQL folds database and table names to lowercase on Windows
	 * by default; but that default can be changed by the
	 * 'lower_case_table_names' system variable. This because databases are
	 * stored as directories and tables are stored as files in the underlying
	 * O/S, and the case-sensitivity of the names is determined by the behavior
	 * of filenames on the O/S. Then, to complicate things,
	 * none of the other identifiers, like table and column names, are folded;
	 * but they are case-insensitive, unless delimited. See
	 * http://dev.mysql.com/doc/refman/6.0/en/identifier-case-sensitivity.html.)
	 */
	boolean vendorDoesNotFold() {
		String vendor = this.getVendor();
		return vendor.equals(SQL_SERVER_VENDOR)
				|| vendor.equals(SYBASE_ASE_VENDOR)
				|| vendor.equals(SYBASE_ASA_VENDOR)
				|| vendor.equals(MYSQL_VENDOR);
	}

	/**
	 * Delimit the specified identifier in a vendor-appropriate fashion.
	 */
	String delimitIdentifier(String identifier) {
		if (this.vendorAllowsQuoteDelimiters()) {
			return StringTools.quote(identifier);
		}
		throw new IllegalStateException("unknown vendor delimiters: " + this.getVendor()); //$NON-NLS-1$
	}


	// ********** Comparable implementation **********

	public int compareTo(Database database) {
		return Collator.getInstance().compare(this.getName(), database.getName());
	}


	// ********** internal methods **********

	DatabaseDefinition getDTPDefinition() {
		return RDBCorePlugin.getDefault().getDatabaseDefinitionRegistry().getDefinition(this.dtpDatabase);
	}


	// ********** listening **********

	@Override
	synchronized void startListening() {
		if (this.catalogs != null) {
			this.startCatalogs();
		}
		super.startListening();
	}

	private void startCatalogs() {
		for (DTPCatalogWrapper catalog : this.catalogs) {
			catalog.startListening();
		}
	}

	@Override
	synchronized void stopListening() {
		if (this.catalogs != null) {
			this.stopCatalogs();
		}
		super.stopListening();
	}

	private void stopCatalogs() {
		for (DTPCatalogWrapper catalog : this.catalogs) {
			catalog.stopListening();
		}
	}


	// ********** clear **********

	@Override
	void clear() {
		this.defaultCatalogCalculated = false;
		this.defaultCatalog = null;
		if (this.catalogs != null) {
			this.clearCatalogs();
		}
		super.clear();
	}

	private void clearCatalogs() {
		this.stopCatalogs();
		for (DTPCatalogWrapper catalog : this.catalogs) {
			catalog.clear();
		}
		this.catalogs = null;
	}

}
