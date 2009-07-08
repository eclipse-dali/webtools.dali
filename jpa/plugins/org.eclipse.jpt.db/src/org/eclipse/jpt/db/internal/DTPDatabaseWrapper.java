/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import com.ibm.icu.text.Collator;
import java.util.Iterator;
import java.util.List;

import org.eclipse.datatools.connectivity.sqm.core.definition.DatabaseDefinition;
import org.eclipse.datatools.connectivity.sqm.internal.core.RDBCorePlugin;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.DatabaseObject;
import org.eclipse.jpt.db.internal.vendor.Vendor;
import org.eclipse.jpt.db.internal.vendor.VendorRepository;
import org.eclipse.jpt.utility.internal.CollectionTools;
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
 * return the schemata from the database directly.
 * 
 * Catalog Note 1:
 * As of Jan 2009, the DTP MySQL driver is not consistent with this pattern.
 * A DTP MySQL database has *no* catalogs; it holds a single schema
 * directly, and that schema has the same name as the database. See bug 249013.
 * 
 * Catalog Note 2:
 * As of Jan 2009, the PostgreSQL JDBC driver complicates this pattern a bit.
 * Even though PostgreSQL does not support "catalogs", its JDBC driver
 * returns a single catalog that has the same name as the database specified
 * in the JDBC connection URL. The DTP PostgreSQL driver simply replicates this
 * behavior. Unfortunately, this catalog can be unnamed; i.e. its name is an
 * empty string....
 * 
 * (Yet Another) Note:
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

	// vendor-specific behavior
	private final Vendor vendor;

	// lazy-initialized, sorted
	private DTPCatalogWrapper[] catalogs;


	// ********** constructor **********

	DTPDatabaseWrapper(DTPConnectionProfileWrapper connectionProfile, org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase) {
		super(connectionProfile, dtpDatabase);
		this.dtpDatabase = dtpDatabase;
		this.vendor = VendorRepository.instance().getVendor(this.getVendorName());
	}


	// ********** DTPWrapper implementation **********

	/* TODO
	 * We might want to listen to the "virtual" catalog; but that's probably
	 * not necessary since there is no easy way for the user to refresh it
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
	List<org.eclipse.datatools.modelbase.sql.schema.Schema> getDTPSchemata() {
		return this.vendor.getSchemas(this.dtpDatabase);
	}

	@Override
	DTPSchemaWrapper getSchema(org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		return this.getSchema_(dtpSchema);
	}

	DTPSchemaWrapper getSchemaFromCatalogs(org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		return this.getCatalog(dtpSchema.getCatalog()).getSchema_(dtpSchema);
	}

	/**
	 * this is only called from a schema when the database is the schema
	 * container, so we know we don't have any catalogs
	 */
	@Override
	DTPTableWrapper getTable(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		return this.getTable_(dtpTable);
	}

	/**
	 * this is only called from a catalog, so we know we have catalogs;
	 * i.e. the search has to descend through catalogs, then to schemata
	 */
	DTPTableWrapper getTableFromCatalogs(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		return this.getCatalog(dtpTable.getSchema().getCatalog()).getTable_(dtpTable);
	}

	/**
	 * this is only called from a schema when the database is the schema
	 * container, so we know we don't have any catalogs
	 */
	@Override
	DTPColumnWrapper getColumn(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.getColumn_(dtpColumn);
	}

	/**
	 * this is only called from a catalog, so we know we have catalogs;
	 * i.e. the search has to descend through catalogs, then to schemata
	 */
	DTPColumnWrapper getColumnFromCatalogs(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.getCatalog(dtpColumn.getTable().getSchema().getCatalog()).getColumn_(dtpColumn);
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
		return this.vendor.supportsCatalogs(this.dtpDatabase);
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
		DTPCatalogWrapper[] result = new DTPCatalogWrapper[dtpCatalogs.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPCatalogWrapper(this, dtpCatalogs.get(i));
		}
		return CollectionTools.sort(result);
	}

	private List<org.eclipse.datatools.modelbase.sql.schema.Catalog> getDTPCatalogs() {
		return this.vendor.getCatalogs(this.dtpDatabase);
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

	public Iterator<String> sortedCatalogNames() {
		// the catalogs are already sorted
		return new TransformationIterator<DTPCatalogWrapper, String>(this.catalogWrappers()) {
			@Override
			protected String transform(DTPCatalogWrapper next) {
				 return next.getName();
			}
		};
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
		return this.getCatalogForIdentifiers(this.getDefaultCatalogIdentifiers());
	}

	private List<String> getDefaultCatalogIdentifiers() {
		return this.vendor.getDefaultCatalogIdentifiers(this.dtpDatabase, this.getConnectionProfile().getUserName());
	}

	/**
	 * Return the first catalog found.
	 */
	private DTPCatalogWrapper getCatalogForIdentifiers(List<String> identifiers) {
		for (String identifier : identifiers) {
			DTPCatalogWrapper catalog = this.getCatalogForIdentifier(identifier);
			if (catalog != null) {
				return catalog;
			}
		}
		return null;
	}

	// ***** schemata

	List<String> getDefaultSchemaIdentifiers() {
		return this.vendor.getDefaultSchemaIdentifiers(this.dtpDatabase, this.getConnectionProfile().getUserName());
	}


	// ********** names vs. identifiers **********

	/**
	 * Convert the specified name to an identifier. Return null if the resulting
	 * identifier matches the specified default name.
	 */
	String convertNameToIdentifier(String name, String defaultName) {
		return this.vendor.convertNameToIdentifier(name, defaultName);
	}

	/**
	 * Convert the specified name to an identifier.
	 */
	public String convertNameToIdentifier(String name) {
		return this.vendor.convertNameToIdentifier(name);
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
		return this.vendor.convertIdentifierToName(identifier);
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
	synchronized void clear() {
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
