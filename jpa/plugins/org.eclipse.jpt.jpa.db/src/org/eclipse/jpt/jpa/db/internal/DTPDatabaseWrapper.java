/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal;

import java.util.Iterator;
import java.util.List;

import org.eclipse.datatools.connectivity.sqm.core.definition.DatabaseDefinition;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.internal.core.RDBCorePlugin;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.DatabaseObject;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.db.internal.driver.DTPDriverAdapter;
import org.eclipse.jpt.jpa.db.internal.driver.DTPDriverAdapterManager;

/**
 * Wrap a DTP Database.
 * <p>
 * Catalogs vs. Schemata:<br>
 * Typically, if a DTP database does not support <em>catalogs</em>,
 * o.e.datatools.modelbase.sql.schema.Database#getCatalogs() will return a
 * single catalog without a name (actually, it's an empty string). This catalog
 * will contain all the database's schemata. We try to ignore this catalog and
 * return the schemata from the database directly.
 * <p>
 * Catalog Note 1:<br>
 * As of Jan 2009, the DTP MySQL driver is not consistent with this pattern.
 * A DTP MySQL database has <em>no</em> catalogs; it holds a single schema
 * directly, and that schema has the same name as the database. See bug 249013.
 * <p>
 * Catalog Note 2:<br>
 * As of Jan 2009, the PostgreSQL JDBC driver complicates this pattern a bit.
 * Even though PostgreSQL does not support <em>catalogs</em>, its JDBC driver
 * returns a single catalog that has the same name as the database specified
 * in the JDBC connection URL. The DTP PostgreSQL driver simply replicates this
 * behavior. Unfortunately, this catalog can be unnamed; i.e. its name is an
 * empty string....
 * <p>
 * (Yet Another) Note:<br>
 * We use <em>name</em> when dealing with the unmodified name of a database object
 * as supplied by the database itself (i.e. it is not delimited and it is always
 * case-sensitive).
 * We use <em>identifier</em> when dealing with a string representation of a database
 * object name (i.e. it may be delimited and, depending on the vendor, it may
 * be case-insensitive).
 */
final class DTPDatabaseWrapper
	extends DTPSchemaContainerWrapper<DTPConnectionProfileWrapper>
	implements Database
{
	/** the wrapped DTP database */
	private final org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase;

	/** database-specific behavior */
	private final DTPDriverAdapter dtpDriverAdapter;

	/** lazy-initialized, sorted */
	private DTPCatalogWrapper[] catalogs;


	// ********** constructor **********

	DTPDatabaseWrapper(DTPConnectionProfileWrapper connectionProfile, org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase) {
		super(connectionProfile);
		this.dtpDatabase = dtpDatabase;
		this.dtpDriverAdapter = DTPDriverAdapterManager.instance().buildAdapter(this.getVendorName(), this);
	}


	// ********** DTPDatabaseObjectWrapper implementation **********

	@Override
	ICatalogObject getCatalogObject() {
		return (ICatalogObject) this.dtpDatabase;
	}

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
	List<org.eclipse.datatools.modelbase.sql.schema.Schema> getDTPSchemas() {
		return this.dtpDriverAdapter.getDTPSchemas();
	}

	@Override
	DTPSchemaWrapper getSchema(org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		return this.getSchema_(dtpSchema);
	}

	/**
	 * This is only called from a catalog, so we know we have catalogs;
	 * i.e. the search has to descend through catalogs, then to schemata.
	 */
	DTPSchemaWrapper getSchemaFromCatalogs(org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		return this.getCatalog(dtpSchema.getCatalog()).getSchema_(dtpSchema);
	}

	/**
	 * This is only called from a schema when the database is the schema
	 * container, so we know we don't have any catalogs.
	 */
	@Override
	DTPTableWrapper getTable(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		return this.getTable_(dtpTable);
	}

	/**
	 * This is only called from a catalog, so we know we have catalogs;
	 * i.e. the search has to descend through catalogs, then to schemata.
	 */
	DTPTableWrapper getTableFromCatalogs(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		return this.getCatalog(dtpTable.getSchema().getCatalog()).getTable_(dtpTable);
	}

	/**
	 * This is only called from a schema when the database is the schema
	 * container, so we know we don't have any catalogs.
	 */
	@Override
	DTPColumnWrapper getColumn(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.getColumn_(dtpColumn);
	}

	/**
	 * This is only called from a catalog, so we know we have catalogs;
	 * i.e. the search has to descend through catalogs, then to schemata.
	 */
	DTPColumnWrapper getColumnFromCatalogs(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.getCatalog(dtpColumn.getTable().getSchema().getCatalog()).getColumn_(dtpColumn);
	}


	// ********** catalogs **********

	public boolean supportsCatalogs() {
		return this.dtpDriverAdapter.supportsCatalogs();
	}

	public Iterable<Catalog> getCatalogs() {
		return new ArrayIterable<Catalog>(this.getCatalogArray());
	}

	private Iterable<DTPCatalogWrapper> getCatalogWrappers() {
		return new ArrayIterable<DTPCatalogWrapper>(this.getCatalogArray());
	}

	private synchronized DTPCatalogWrapper[] getCatalogArray() {
		if (this.catalogs == null) {
			this.catalogs = this.buildCatalogArray();
		}
		return this.catalogs;
	}

	private DTPCatalogWrapper[] buildCatalogArray() {
		List<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs = this.getDTPCatalogs();
		DTPCatalogWrapper[] result = new DTPCatalogWrapper[dtpCatalogs.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPCatalogWrapper(this, dtpCatalogs.get(i));
		}
		return ArrayTools.sort(result, DEFAULT_COMPARATOR);
	}

	private List<org.eclipse.datatools.modelbase.sql.schema.Catalog> getDTPCatalogs() {
		return this.dtpDriverAdapter.getDTPCatalogs();
	}

	public int getCatalogsSize() {
		return this.getCatalogArray().length;
	}

	/**
	 * Return the catalog for the specified DTP catalog.
	 */
	DTPCatalogWrapper getCatalog(org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog) {
		for (DTPCatalogWrapper catalog : this.getCatalogArray()) {
			if (catalog.wraps(dtpCatalog)) {
				return catalog;
			}
		}
		throw new IllegalArgumentException("invalid DTP catalog: " + dtpCatalog);  //$NON-NLS-1$
	}

	public Iterable<String> getSortedCatalogNames() {
		// the catalogs are already sorted
		return new TransformationIterable<DatabaseObject, String>(this.getCatalogWrappers(), NAME_TRANSFORMER);
	}

	public DTPCatalogWrapper getCatalogNamed(String name) {
		return this.selectDatabaseObjectNamed(this.getCatalogWrappers(), name);
	}

	public Iterable<String> getSortedCatalogIdentifiers() {
		// the catalogs are already sorted
		return new TransformationIterable<DatabaseObject, String>(this.getCatalogWrappers(), IDENTIFIER_TRANSFORMER);
	}

	public Catalog getCatalogForIdentifier(String identifier) {
		return this.dtpDriverAdapter.selectCatalogForIdentifier(this.getCatalogs(), identifier);
	}

	public synchronized DTPCatalogWrapper getDefaultCatalog() {
		return this.getCatalogForNames(this.getDefaultCatalogNames());
	}

	private Iterable<String> getDefaultCatalogNames() {
		return this.dtpDriverAdapter.getDefaultCatalogNames();
	}

	/**
	 * Return the first catalog found.
	 */
	private DTPCatalogWrapper getCatalogForNames(Iterable<String> names) {
		for (String name : names) {
			DTPCatalogWrapper catalog = this.getCatalogNamed(name);
			if (catalog != null) {
				return catalog;
			}
		}
		return null;
	}

	/**
	 * If we find a default catalog, return its identifier;
	 * otherwise, return the last name on the list of default names.
	 * (Some databases have multiple possible default names.)
	 * Return null if the database does not support catalogs.
	 */
	public synchronized String getDefaultCatalogIdentifier() {
		Iterable<String> names = this.getDefaultCatalogNames();
		DTPCatalogWrapper catalog = this.getCatalogForNames(names);
		if (catalog != null) {
			return catalog.getIdentifier();
		}
		Iterator<String> stream = names.iterator();
		return stream.hasNext() ? this.convertNameToIdentifier(CollectionTools.last(stream)) : null;
	}


	// ********** names vs. identifiers **********

	// override to make method public since it's in the Database interface
	@Override
	public String convertNameToIdentifier(String name) {
		return super.convertNameToIdentifier(name);
	}


	// ********** misc **********

	public org.eclipse.datatools.modelbase.sql.schema.Database getDTPDatabase() {
		return this.dtpDatabase;
	}

	public String getName() {
		return this.dtpDatabase.getName();
	}

	public String getVendorName() {
		return this.dtpDatabase.getVendor();
	}

	public String getVersion() {
		return this.dtpDatabase.getVersion();
	}

	@Override
	DTPDriverAdapter getDTPDriverAdapter() {
		return this.dtpDriverAdapter;
	}

	public Table selectTableForIdentifier(Iterable<Table> tables, String identifier) {
		return this.dtpDriverAdapter.selectTableForIdentifier(tables, identifier);
	}

	// TODO add to interface? (so it can be used by AbstractDTPDriverAdapter)
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
