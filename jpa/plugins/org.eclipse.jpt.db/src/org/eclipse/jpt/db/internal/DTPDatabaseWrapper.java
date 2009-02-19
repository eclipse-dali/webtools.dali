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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.eclipse.datatools.connectivity.sqm.core.definition.DatabaseDefinition;
import org.eclipse.datatools.connectivity.sqm.internal.core.RDBCorePlugin;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.DatabaseObject;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * Wrap a DTP Database.
 * 
 * Catalogs vs. Schemata:
 * Typically, if a DTP database does not support "catalogs",
 * o.e.datatools.modelbase.sql.schema.Database#getCatalogs() will return a
 * single catalog without a name (actually, it's an empty string). This catalog
 * will contain all the database's schemata. We try to ignore this catalog and
 * return the schemata from the database directly. (Note MySQL does not seem
 * to be consistent with this pattern.)
 * 
 * Note:
 * We use "name" when dealing with the unmodified name of a database object
 * as supplied by the database itself (i.e. it is not delimited and it is always
 * case-sensitive).
 * We use "identifier" when dealing with a string representation of a database
 * object name (i.e. it may be delimited and, depending on the vendor, it may
 * be case-insensitive).
 */
final class DTPDatabaseWrapper
	extends DTPSchemaContainerWrapper
	implements Database
{
	// the wrapped DTP database
	private final org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase;

	// lazy-initialized, sorted
	private DTPCatalogWrapper[] catalogs;

	// lazy-initialized - but it can be 'null' so we use a flag
	private DTPCatalogWrapper defaultCatalog;
	private boolean defaultCatalogCalculated = false;


	private static final DTPCatalogWrapper[] EMPTY_CATALOGS = new DTPCatalogWrapper[0];


	// ********** constructor **********

	DTPDatabaseWrapper(DTPConnectionProfileWrapper connectionProfile, org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase) {
		super(connectionProfile, dtpDatabase);
		this.dtpDatabase = dtpDatabase;
	}


	// ********** DTPWrapper implementation **********

	/* TODO
	 * We might want to listen to the "virtual" catalog; but that's probably
	 * not necessary since there is not easy way for the user to refresh it
	 * (i.e. it is not displayed in the DTP UI).
	 */
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
		List<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs = this.getDTPCatalogs();
		// if there are no catalogs, the database must hold the schemata directly
		if ((dtpCatalogs == null) || dtpCatalogs.isEmpty()) {
			return this.dtpDatabase.getSchemas();
		}
		org.eclipse.datatools.modelbase.sql.schema.Catalog virtualCatalog = getVirtualCatalog(dtpCatalogs);
		return (virtualCatalog != null) ? virtualCatalog.getSchemas() : Collections.emptyList();
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

	public String getVendorName() {
		return this.dtpDatabase.getVendor();
	}

	public String getVersion() {
		return this.dtpDatabase.getVersion();
	}

	// override to make method public since it's in the Database interface
	@Override
	public <T extends DatabaseObject> T selectDatabaseObjectForIdentifier(T[] databaseObjects, String identifier) {
		return super.selectDatabaseObjectForIdentifier(databaseObjects, identifier);
	}

	// ***** catalogs

	public boolean supportsCatalogs() {
		return supportsCatalogs(this.getDTPCatalogs());
	}

	private static boolean supportsCatalogs(List<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs) {
		// if there are no catalogs, they must not be supported
		if ((dtpCatalogs == null) || dtpCatalogs.isEmpty()) {
			return false;
		}

		// if we only have a single catalog with an empty name,
		// they are not really supported either...
		return ! listContainsOnlyAVirtualCatalog(dtpCatalogs);
	}

	/**
	 * pre-condition: 'dtpCatalogs' is not null
	 */
	private static boolean listContainsOnlyAVirtualCatalog(List<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs) {
		return getVirtualCatalog(dtpCatalogs) != null;
	}

	/**
	 * pre-condition: 'dtpCatalogs' is not null
	 */
	private static org.eclipse.datatools.modelbase.sql.schema.Catalog getVirtualCatalog(List<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs) {
		if (dtpCatalogs.size() == 1) {
			org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog = dtpCatalogs.get(0);
			if (dtpCatalog.getName().equals("")) { //$NON-NLS-1$
				return dtpCatalog;
			}
		}
		return null;
	}

	public Iterator<Catalog> catalogs() {
		return new ArrayIterator<Catalog>(this.getCatalogs());
	}

	private Iterator<DTPCatalogWrapper> catalogWrappers() {
		return new ArrayIterator<DTPCatalogWrapper>(this.getCatalogs());
	}

	private synchronized DTPCatalogWrapper[] getCatalogs() {
		if (this.catalogs == null) {
			this.catalogs = this.buildCatalogs();
		}
		return this.catalogs;
	}

	private DTPCatalogWrapper[] buildCatalogs() {
		List<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs = this.getDTPCatalogs();
		if ( ! supportsCatalogs(dtpCatalogs)) {
			return EMPTY_CATALOGS;
		}
		DTPCatalogWrapper[] result = new DTPCatalogWrapper[dtpCatalogs.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPCatalogWrapper(this, dtpCatalogs.get(i));
		}
		return CollectionTools.sort(result);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<org.eclipse.datatools.modelbase.sql.schema.Catalog> getDTPCatalogs() {
		return this.dtpDatabase.getCatalogs();
	}

	public int catalogsSize() {
		return this.getCatalogs().length;
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
		return this.selectDatabaseObjectNamed(this.getCatalogs(), name);
	}

	public Iterator<String> sortedCatalogIdentifiers() {
		// the catalogs are already sorted
		return new TransformationIterator<DTPCatalogWrapper, String>(this.catalogWrappers()) {
			@Override
			protected String transform(DTPCatalogWrapper next) {
				 return next.getIdentifier();
			}
		};
	}

	public DTPCatalogWrapper getCatalogForIdentifier(String identifier) {
		return this.selectDatabaseObjectForIdentifier(this.getCatalogs(), identifier);
	}

	public synchronized DTPCatalogWrapper getDefaultCatalog() {
		if ( ! this.defaultCatalogCalculated) {
			this.defaultCatalogCalculated = true;
			this.defaultCatalog = this.buildDefaultCatalog();
		}
		return this.defaultCatalog;
	}

	private DTPCatalogWrapper buildDefaultCatalog() {
		return this.supportsCatalogs() ? this.getVendor().getDefaultCatalog(this) : null;
	}

	// ***** schemata

	@Override
	synchronized DTPSchemaWrapper[] getSchemata() {
		DTPCatalogWrapper cat = this.getDefaultCatalog();
		return (cat != null) ? cat.getSchemata() : super.getSchemata();
	}

	/**
	 * Return the specified schema container's default schema.
	 */
	DTPSchemaWrapper getDefaultSchema(DTPSchemaContainerWrapper schemaContainer) {
		return this.getVendor().getDefaultSchema(schemaContainer);
	}


	// ********** names vs. identifiers **********

	/**
	 * Convert the specified name to an identifier. Return null if the resulting
	 * identifier matches the specified default name.
	 */
	String convertNameToIdentifier(String name, String defaultName) {
		return this.getVendor().convertNameToIdentifier(name, defaultName);
	}

	/**
	 * Convert the specified name to an identifier.
	 */
	public String convertNameToIdentifier(String name) {
		return this.getVendor().convertNameToIdentifier(name);
	}

	/**
	 * Return the database object identified by the specified identifier. If
	 * the identifier is "delimited" (typically with double-quotes), it will be
	 * used without any folding. If the name is "normal" (i.e. not delimited),
	 * it will be folded to the appropriate case (typically uppercase).
	 * 
	 * Since the database has the appropriate state to compare identifiers and
	 * names, the connection profile delegates to here when using the default
	 * "database finder".
	 */
	<T extends DatabaseObject> T selectDatabaseObjectForIdentifier_(T[] databaseObjects, String identifier) {
		return this.selectDatabaseObjectNamed(databaseObjects, this.convertIdentifierToName(identifier));
	}

	/**
	 * Convert the specified identifier to a name.
	 */
	String convertIdentifierToName(String identifier) {
		return this.getVendor().convertIdentifierToName(identifier);
	}


	// ********** Comparable implementation **********

	public int compareTo(Database database) {
		return Collator.getInstance().compare(this.getName(), database.getName());
	}


	// ********** internal methods **********

	DatabaseDefinition getDTPDefinition() {
		return RDBCorePlugin.getDefault().getDatabaseDefinitionRegistry().getDefinition(this.dtpDatabase);
	}

	private Vendor getVendor() {
		return getVendor(this.getVendorName());
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


	// ********** vendors **********

	private static Vendor getVendor(String name) {
		Vendor vendor = getVendors().get(name);
		return (vendor != null) ? vendor : Default.INSTANCE;
	}

	/**
	 * keyed by vendor name
	 */
	private static HashMap<String, Vendor> Vendors;

	private static synchronized HashMap<String, Vendor> getVendors() {
		if (Vendors == null) {
			Vendors = buildVendors();
		}
		return Vendors;
	}

	private static HashMap<String, Vendor> buildVendors() {
		HashMap<String, Vendor> map = new HashMap<String, Vendor>(20);
		putVendor(map, Derby.INSTANCE);
		putVendor(map, HSQLDB.INSTANCE);
		putVendor(map, DB2.UDB);
		putVendor(map, DB2.UDB_I_SERIES);
		putVendor(map, DB2.UDB_Z_SERIES);
		putVendor(map, Informix.INSTANCE);
		putVendor(map, SQLServer.INSTANCE);
		putVendor(map, MySQL.INSTANCE);
		putVendor(map, Oracle.INSTANCE);
		putVendor(map, Postgres.INSTANCE);
		putVendor(map, MaxDB.INSTANCE);
		putVendor(map, Sybase.ASA);
		putVendor(map, Sybase.ASE);
		return map;
	}

	private static void putVendor(HashMap<String, Vendor> map, Vendor vendor) {
		String name = vendor.getName();
		if (map.put(name, vendor) != null) {
			throw new IllegalArgumentException("Duplicate vendor: " + name); //$NON-NLS-1$
		}
	}


	// ********** vendor classes **********

	/**
	 * Delegate vendor-specific behavior to implementations of this class"
	 *   - default catalog and schema
	 *   - converting names to identifiers and vice-versa
	 * 
	 * Note:
	 * We use "name" when dealing with the unmodified name of a database object
	 * as supplied by the database itself (i.e. it is not delimited and it is always
	 * case-sensitive).
	 * We use "identifier" when dealing with a string representation of a database
	 * object name (i.e. it may be delimited and, depending on the vendor, it may
	 * be case-insensitive).
	 */
	private abstract static class Vendor {

		Vendor() {
			super();
		}

		/**
		 * Return the vendor's name. This must match the name specified by the
		 * DTP connection profile.
		 */
		abstract String getName();

		/**
		 * The SQL spec says a "normal" (non-delimited) identifier should be
		 * folded to uppercase; but some databases do otherwise (e.g. Sybase).
		 */
		Folder getFolder() {
			return Folder.UPPER;
		}


		// ********** default catalog and schema **********

		/**
		 * Return whether the vendor supports catalogs.
		 */
		abstract boolean supportsCatalogs();

		DTPCatalogWrapper getDefaultCatalog(DTPDatabaseWrapper database) {
			if ( ! this.supportsCatalogs()) {
				throw new UnsupportedOperationException();
			}
			return database.getCatalogForIdentifier(this.getDefaultCatalogIdentifier(database));
		}

		/**
		 * Typically, the name of the default catalog is the user name.
		 */
		String getDefaultCatalogIdentifier(DTPDatabaseWrapper database) {
			if ( ! this.supportsCatalogs()) {
				throw new UnsupportedOperationException();
			}
			return database.getConnectionProfile().getUserName();
		}

		DTPSchemaWrapper getDefaultSchema(DTPSchemaContainerWrapper sc) {
			return sc.getSchemaForIdentifier(this.getDefaultSchemaIdentifier(sc));
		}

		/**
		 * Typically, the name of the default schema is the user name.
		 */
		String getDefaultSchemaIdentifier(DTPSchemaContainerWrapper sc) {
			return sc.getDatabase().getConnectionProfile().getUserName();
		}


		// ********** name -> identifier **********

		/**
		 * @see DTPDatabaseWrapper#convertNameToIdentifier(String, String)
		 */
		final String convertNameToIdentifier(String name, String defaultName) {
			return this.nameRequiresDelimiters(name) ? this.delimitName(name)
							: this.normalNamesMatch(name, defaultName) ? null : name;
		}

		/**
		 * @see DTPDatabaseWrapper#convertNameToIdentifier(String)
		 */
		final String convertNameToIdentifier(String name) {
			return this.nameRequiresDelimiters(name) ? this.delimitName(name) : name;
		}

		/**
		 * Return whether the specified database object name must be delimited
		 * when used in an SQL statement.
		 * If the name has any "special" characters (as opposed to letters,
		 * digits, and other allowed characters [e.g. underscores]), it requires
		 * delimiters.
		 * If the name is mixed case and the database folds undelimited names
		 * (to either uppercase or lowercase), it requires delimiters.
		 */
		final boolean nameRequiresDelimiters(String name) {
			return (name.length() == 0)  //  an empty string must be delimited(?)
						|| this.nameContainsAnySpecialCharacters(name)
						|| this.nameIsNotFolded(name);
		}

		/**
		 * Return whether the specified name contains any "special" characters
		 * that require the name to be delimited.
		 * Pre-condition: the specified name is not empty
		 */
		final boolean nameContainsAnySpecialCharacters(String name) {
			char[] string = name.toCharArray();
			if (this.characterIsSpecialNameStart(string[0])) {
				return true;
			}
			for (int i = string.length; i-- > 1; ) {  // note: stop at 1
				if (this.characterIsSpecialNamePart(string[i])) {
					return true;
				}
			}
			return false;
		}

		/**
		 * Return whether the specified character is "special" for the first
		 * character of a name.
		 * Typically, databases are more restrictive about what characters can
		 * be used to *start* an identifier (as opposed to the characters
		 * allowed for the remainder of the identifier).
		 */
		final boolean characterIsSpecialNameStart(char c) {
			return ! this.characterIsNormalNameStart(c);
		}

		/**
		 * Return whether the specified character is "normal" for the first
		 * character of a name.
		 * The first character of an identifier can be:
		 *   - a letter
		 *   - any of the other, vendor-specific, "normal" start characters
		 */
		boolean characterIsNormalNameStart(char c) {
			// all vendors allow a letter
			return Character.isLetter(c)
					|| this.characterIsNormalNameStart_(c);
		}

		private boolean characterIsNormalNameStart_(char c) {
			return arrayContains(this.getNormalNameStartCharacters(), c);
		}

		/**
		 * Return the "normal" characters, beyond letters, for the
		 * first character of a name.
		 * Return null if there are no additional characters.
		 */
		char[] getNormalNameStartCharacters() {
			return null;
		}

		/**
		 * Return whether the specified character is "special" for the second and
		 * subsequent characters of a name.
		 */
		final boolean characterIsSpecialNamePart(char c) {
			return ! this.characterIsNormalNamePart(c);
		}

		/**
		 * Return whether the specified character is "normal" for the second and
		 * subsequent characters of a name.
		 * The second and subsequent characters of a "normal" name can be:
		 *   - a letter
		 *   - a digit
		 *   - any of the other, vendor-specific, "normal" start characters
		 *   - any of the other, vendor-specific, "normal" part characters
		 */
		boolean characterIsNormalNamePart(char c) {
			// all vendors allow a letter or digit
			return Character.isLetterOrDigit(c)
					|| this.characterIsNormalNameStart_(c)
					|| this.characterIsNormalNamePart_(c);
		}

		private boolean characterIsNormalNamePart_(char c) {
			return arrayContains(this.getNormalNamePartCharacters(), c);
		}

		/**
		 * Return the "normal" characters, beyond letters and digits and the
		 * "normal" first characters, for the second and subsequent characters
		 * of an identifier. Return null if there are no additional characters.
		 */
		char[] getNormalNamePartCharacters() {
			return null;
		}

		/**
		 * Return whether the specified name is not folded to the database's
		 * case, requiring it to be delimited.
		 */
		final boolean nameIsNotFolded(String name) {
			return ! this.getFolder().stringIsFolded(name);
		}

		/**
		 * Return whether the specified "normal" names match.
		 */
		final boolean normalNamesMatch(String name1, String name2) {
			return this.normalIdentifiersAreCaseSensitive() ?
							name1.equals(name2)
						:
							name1.equalsIgnoreCase(name2);
		}

		/**
		 * Typically, "normal" identifiers are case-insensitive.
		 */
		final boolean normalIdentifiersAreCaseSensitive() {
			return this.getFolder().isCaseSensitive();
		}

		/**
		 * Wrap the specified name in delimiters (typically double-quotes),
		 * converting it to an identifier.
		 */
		String delimitName(String name) {
			return StringTools.quote(name);
		}


		// ********** identifier -> name **********

		/**
		 * @see DTPDatabaseWrapper#selectDatabaseObjectForIdentifier_(DatabaseObject[], String)
		 */
		// not sure how to handle an empty string:
		// both "" and "\"\"" are converted to "" ...
		// convert "" to 'null' since empty strings must be delimited?
		final String convertIdentifierToName(String identifier) {
			return (identifier == null) ? null :
						this.identifierIsDelimited(identifier) ?
							StringTools.undelimit(identifier)
						:
							this.getFolder().fold(identifier);
		}

		/**
		 * Return whether the specified identifier is "delimited".
		 * The SQL-92 spec says identifiers should be delimited by
		 * double-quotes; but some databases allow otherwise (e.g. Sybase).
		 */
		boolean identifierIsDelimited(String identifier) {
			return StringTools.stringIsQuoted(identifier);
		}


		// ********** misc **********

		@Override
		public String toString() {
			return this.getName();
		}

		/**
		 * static convenience method - array null check
		 */
		static boolean arrayContains(char[] array, char c) {
			return (array != null) && CollectionTools.contains(array, c);
		}

		/**
		 * Handle database-specific case-folding issues.
		 */
		enum Folder {
			UPPER {
				@Override String fold(String string) { return string.toUpperCase(); }
				@Override boolean stringIsFolded(String string) { return StringTools.stringIsUppercase(string); }
				@Override boolean isCaseSensitive() { return false; }
			},
			LOWER {
				@Override String fold(String string) { return string.toLowerCase(); }
				@Override boolean stringIsFolded(String string) { return StringTools.stringIsLowercase(string); }
				@Override boolean isCaseSensitive() { return false; }
			},
			NONE {
				@Override String fold(String string) { return string; }
				@Override boolean stringIsFolded(String string) { return true; }
				@Override boolean isCaseSensitive() { return true; }
			};
			abstract String fold(String string);
			abstract boolean stringIsFolded(String string);
			abstract boolean isCaseSensitive();
		}

	}

	private static class Default extends Vendor {
		static final Vendor INSTANCE = new Default();

		private Default() {
			super();
		}

		@Override
		String getName() {
			return "Default Vendor"; //$NON-NLS-1$
		}

		@Override
		boolean supportsCatalogs() {
			return true;  // hmmm...  ~bjv
		}

	}

	private static class Derby extends Vendor {
		static final Vendor INSTANCE = new Derby();

		private Derby() {
			super();
		}

		@Override
		String getName() {
			return "Derby"; //$NON-NLS-1$
		}

		@Override
		boolean supportsCatalogs() {
			return false;
		}

		/**
		 * The default user name on Derby is "APP".
		 */
		@Override
		String getDefaultSchemaIdentifier(DTPSchemaContainerWrapper sc) {
			String user = super.getDefaultSchemaIdentifier(sc);
			return ((user == null) || (user.length() == 0)) ?
							DEFAULT_USER_NAME
						:
							user;
		}
		private static final String DEFAULT_USER_NAME = "APP";  //$NON-NLS-1$

		@Override
		char[] getNormalNamePartCharacters() {
			return NORMAL_NAME_PART_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_PART_CHARACTERS = new char[] { '_' };

	}

	private static class HSQLDB extends Vendor {
		static final Vendor INSTANCE = new HSQLDB();

		private HSQLDB() {
			super();
		}

		@Override
		String getName() {
			return "HSQLDB"; //$NON-NLS-1$
		}

		@Override
		boolean supportsCatalogs() {
			return false;
		}

		@Override
		String getDefaultSchemaIdentifier(DTPSchemaContainerWrapper sc) {
			return PUBLIC_SCHEMA_NAME;
		}
		private static final String PUBLIC_SCHEMA_NAME = "PUBLIC";  //$NON-NLS-1$

	}

	private static class DB2 extends Vendor {
		static final Vendor UDB_I_SERIES = new DB2("DB2 UDB iSeries"); //$NON-NLS-1$
		static final Vendor UDB = new DB2("DB2 UDB"); //$NON-NLS-1$
		static final Vendor UDB_Z_SERIES = new DB2("DB2 UDB zSeries"); //$NON-NLS-1$

		private final String name;

		private DB2(String name) {
			super();
			this.name = name;
		}

		@Override
		String getName() {
			return this.name;
		}

		@Override
		boolean supportsCatalogs() {
			return false;
		}

		@Override
		char[] getNormalNamePartCharacters() {
			return NORMAL_NAME_PART_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_PART_CHARACTERS = new char[] { '_' };

	}

	private static class Informix extends Vendor {
		static final Vendor INSTANCE = new Informix();

		private Informix() {
			super();
		}

		@Override
		String getName() {
			return "Informix"; //$NON-NLS-1$
		}

		@Override
		boolean supportsCatalogs() {
			return false;
		}

		@Override
		Folder getFolder() {
			return Folder.LOWER;
		}

		@Override
		char[] getNormalNameStartCharacters() {
			return NORMAL_NAME_START_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_START_CHARACTERS = new char[] { '_' };

		@Override
		char[] getNormalNamePartCharacters() {
			return NORMAL_NAME_PART_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_PART_CHARACTERS = new char[] { '$' };

	}

	private static class SQLServer extends Vendor {
		static final Vendor INSTANCE = new SQLServer();

		private SQLServer() {
			super();
		}

		@Override
		String getName() {
			return "SQL Server"; //$NON-NLS-1$
		}

		@Override
		boolean supportsCatalogs() {
			return true;
		}

		/**
		 * The default schema on SQL Server for any database (catalog) is 'dbo'.
		 */
		@Override
		String getDefaultSchemaIdentifier(DTPSchemaContainerWrapper sc) {
			return DEFAULT_SCHEMA_NAME;
		}
		private static final String DEFAULT_SCHEMA_NAME = "dbo";  //$NON-NLS-1$

		/**
		 * By default, SQL Server identifiers are case-sensitive, even without
		 * delimiters. This can depend on the collation setting....
		 */
		@Override
		Folder getFolder() {
			return Folder.NONE;
		}

		@Override
		char[] getNormalNameStartCharacters() {
			return NORMAL_NAME_START_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_START_CHARACTERS = new char[] { '_', '@', '#' };

		@Override
		char[] getNormalNamePartCharacters() {
			return NORMAL_NAME_PART_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_PART_CHARACTERS = new char[] { '$' };

		/**
		 * By default, SQL Server delimits identifiers with brackets ([]); but it
		 * can also be configured to use double-quotes.
		 */
		@Override
		boolean identifierIsDelimited(String identifier) {
			return StringTools.stringIsBracketed(identifier)
						|| super.identifierIsDelimited(identifier);
		}

	}

	private static class MySQL extends Vendor {
		static final Vendor INSTANCE = new MySQL();

		private MySQL() {
			super();
		}

		@Override
		String getName() {
			return "MySql"; //$NON-NLS-1$
		}

		@Override
		boolean supportsCatalogs() {
			return false;
		}

		/**
		 * MySQL is a bit unusual, so we force exact matches.
		 * (e.g. MySQL folds database and table names to lowercase on Windows
		 * by default; but that default can be changed by the
		 * 'lower_case_table_names' system variable. This is because databases are
		 * stored as directories and tables are stored as files in the underlying
		 * O/S; and the case-sensitivity of the names is determined by the behavior
		 * of filenames on the O/S. Then, to complicate things,
		 * none of the other identifiers, like table and column names, are folded;
		 * but they are case-insensitive, unless delimited. See
		 * http://dev.mysql.com/doc/refman/6.0/en/identifier-case-sensitivity.html.)
		 */
		@Override
		Folder getFolder() {
			return Folder.NONE;
		}

		/**
		 * The DTP model for MySQL has a database that contains no catalogs
		 * but directly holds a single schema with the same name as the database.
		 * Although you can qualify identifiers with a database name
		 * in MySQL, only the database specified at login seems to be available
		 * in the DTP model.... 
		 * NB: In MySQL DDL, SCHEMA is a synonym for DATABASE; but the JDBC
		 * method DatabaseMetaData.getSchemas() returns an empty list,
		 * while getCatalogs() returns a list of the available databases.
		 * You can also use the JDBC method Connection.setCatalog(String) to
		 * set the default database.
		 */
		@Override
		String getDefaultSchemaIdentifier(DTPSchemaContainerWrapper sc) {
			return sc.getDatabase().getName();  // hmmm... ~bjv
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
		char[] getNormalNameStartCharacters() {
			return NORMAL_NAME_START_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_START_CHARACTERS = new char[] { '_', '$' };

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

	private static class Oracle extends Vendor {
		static final Vendor INSTANCE = new Oracle();

		private Oracle() {
			super();
		}

		@Override
		String getName() {
			return "Oracle"; //$NON-NLS-1$
		}

		@Override
		boolean supportsCatalogs() {
			return false;
		}

		@Override
		char[] getNormalNamePartCharacters() {
			return NORMAL_NAME_PART_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_PART_CHARACTERS = new char[] { '_', '$', '#' };

	}

	private static class Postgres extends Vendor {
		static final Vendor INSTANCE = new Postgres();

		private Postgres() {
			super();
		}

		@Override
		String getName() {
			return "postgres"; //$NON-NLS-1$
		}

		@Override
		Folder getFolder() {
			return Folder.LOWER;
		}

		@Override
		boolean supportsCatalogs() {
			return true;
		}

		/**
		 * PostgreSQL has a "schema search path". The default is:
		 *     "$user",public
		 * If "$user" is not found, return "public".
		 */
		@Override
		DTPSchemaWrapper getDefaultSchema(DTPSchemaContainerWrapper sc) {
			DTPSchemaWrapper userSchema = super.getDefaultSchema(sc);
			return (userSchema != null) ? userSchema : sc.getSchemaNamed(PUBLIC_SCHEMA_NAME);
		}
		private static final String PUBLIC_SCHEMA_NAME = "public";  //$NON-NLS-1$

		@Override
		char[] getNormalNameStartCharacters() {
			return NORMAL_NAME_START_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_START_CHARACTERS = new char[] { '_' };

		@Override
		char[] getNormalNamePartCharacters() {
			return NORMAL_NAME_PART_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_PART_CHARACTERS = new char[] { '$' };

	}

	private static class MaxDB extends Vendor {
		static final Vendor INSTANCE = new MaxDB();

		private MaxDB() {
			super();
		}

		@Override
		String getName() {
			return "MaxDB"; //$NON-NLS-1$
		}

		@Override
		boolean supportsCatalogs() {
			return false;
		}

		@Override
		char[] getNormalNameStartCharacters() {
			return NORMAL_NAME_START_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_START_CHARACTERS = new char[] { '#', '@', '$' };

		@Override
		char[] getNormalNamePartCharacters() {
			return NORMAL_NAME_PART_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_PART_CHARACTERS = new char[] { '_' };

	}

	private static class Sybase extends Vendor {
		static final Vendor ASA = new Sybase("Sybase_ASA"); //$NON-NLS-1$
		static final Vendor ASE = new Sybase("Sybase_ASE"); //$NON-NLS-1$

		private final String name;

		private Sybase(String name) {
			super();
			this.name = name;
		}

		@Override
		String getName() {
			return this.name;
		}

		@Override
		boolean supportsCatalogs() {
			return true;
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
		String getDefaultSchemaIdentifier(DTPSchemaContainerWrapper sc) {
			return DEFAULT_SCHEMA_NAME;
		}
		private static final String DEFAULT_SCHEMA_NAME = "dbo";  //$NON-NLS-1$

		/**
		 * By default, Sybase identifiers are case-sensitive, even without
		 * delimiters. This can depend on the collation setting....
		 */
		@Override
		Folder getFolder() {
			return Folder.NONE;
		}

		@Override
		char[] getNormalNameStartCharacters() {
			return NORMAL_NAME_START_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_START_CHARACTERS = new char[] { '_', '@' };

		@Override
		char[] getNormalNamePartCharacters() {
			return NORMAL_NAME_PART_CHARACTERS;
		}
		private static final char[] NORMAL_NAME_PART_CHARACTERS = new char[] { '$', '¥', '£', '#' };

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

}
