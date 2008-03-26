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
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.internal.core.RDBCorePlugin;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Database;
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
	implements InternalDatabase
{
	// backpointer to parent
	private final DTPConnectionProfileWrapper connectionProfile;

	// the wrapped DTP database
	private final org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase;

	// lazy-initialized
	private DTPCatalogWrapper[] catalogs;

	// lazy-initialized
	private DTPCatalogWrapper defaultCatalog;
	private boolean defaultCatalogCalculated = false;

	// TODO allow user to configure?
	private boolean caseSensitive = false;


	private static final DTPCatalogWrapper[] EMPTY_CATALOGS = new DTPCatalogWrapper[0];


	// ********** constructor **********

	DTPDatabaseWrapper(DTPConnectionProfileWrapper connectionProfile, org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase) {
		super(connectionProfile);
		this.connectionProfile = connectionProfile;
		this.dtpDatabase = dtpDatabase;
	}


	// ********** DTPWrapper implementation **********

	@Override
	ICatalogObject getCatalogObject() {
		return (ICatalogObject) this.dtpDatabase;
	}

	@Override
	synchronized void catalogObjectChanged(int eventType) {
		super.catalogObjectChanged(eventType);
		this.getConnectionProfile().databaseChanged(this, eventType);
	}


	// ********** Database implementation **********

	@Override
	public String getName() {
		return this.dtpDatabase.getName();
	}

	public String getVendor() {
		return this.dtpDatabase.getVendor();
	}

	public String getVersion() {
		return this.dtpDatabase.getVersion();
	}

	public DatabaseDefinition getDtpDefinition() {
		return RDBCorePlugin.getDefault().getDatabaseDefinitionRegistry().getDefinition(this.dtpDatabase);
	}

	// ***** caseSensitive

	@Override
	public boolean isCaseSensitive() {
		return this.caseSensitive;
	}

	// TODO
	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	// ***** catalogs

	public boolean supportsCatalogs() {
		// if the DTP database does not have any schemata, it must have catalogs...
		List<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata = this.dtpSchemata();
		return (dtpSchemata == null) || dtpSchemata.isEmpty();
	}

	public Iterator<Catalog> catalogs() {
		return new ArrayIterator<Catalog>(this.catalogs_());
	}

	private Iterator<DTPCatalogWrapper> catalogWrappers() {
		return new ArrayIterator<DTPCatalogWrapper>(this.catalogs_());
	}

	private synchronized DTPCatalogWrapper[] catalogs_() {
		if (this.catalogs == null) {
			this.catalogs = this.buildCatalogs();
		}
		return this.catalogs;
	}

	private DTPCatalogWrapper[] buildCatalogs() {
		List<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs = this.dtpCatalogs();
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
	private List<org.eclipse.datatools.modelbase.sql.schema.Catalog> dtpCatalogs() {
		return this.dtpDatabase.getCatalogs();
	}

	public int catalogsSize() {
		return this.catalogs_().length;
	}

	public Iterator<String> catalogNames() {
		return new TransformationIterator<DTPCatalogWrapper, String>(this.catalogWrappers()) {
			@Override
			protected String transform(DTPCatalogWrapper catalog) {
				 return catalog.getName();
			}
		};
	}

	public boolean containsCatalogNamed(String name) {
		return this.catalogNamed(name) != null;
	}

	public DTPCatalogWrapper catalogNamed(String name) {
		return this.isCaseSensitive() ? this.catalogNamedCaseSensitive(name) : this.catalogNamedIgnoreCase(name);
	}
	
	private DTPCatalogWrapper catalogNamedCaseSensitive(String name) {
		for (Iterator<DTPCatalogWrapper> stream = this.catalogWrappers(); stream.hasNext(); ) {
			DTPCatalogWrapper catalog = stream.next();
			if (catalog.getName().equals(name)) {
				return catalog;
			}
		}
		return null;
	}
	
	private DTPCatalogWrapper catalogNamedIgnoreCase(String name) {
		for (Iterator<DTPCatalogWrapper> stream = this.catalogWrappers(); stream.hasNext(); ) {
			DTPCatalogWrapper catalog = stream.next();
			if (StringTools.stringsAreEqualIgnoreCase(catalog.getName(), name)) {
				return catalog;
			}
		}
		return null;
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
		String userName = this.connectionProfile.getUserName();
		for (Iterator<DTPCatalogWrapper> stream = this.catalogWrappers(); stream.hasNext(); ) {
			DTPCatalogWrapper catalog = stream.next();
			if (catalog.getName().length() == 0) {
				return catalog;  // special catalog that contains all schemata
			}
			if (catalog.getName().equals(userName)) {
				return catalog;  // user name is default catalog
			}
			if (catalog.getName().equals(this.getName())) {
				return catalog;  // special catalog with same name as DB (PostgreSQL)
			}
		}
		throw new IllegalStateException("unknown default catalog");  //$NON-NLS-1$
	}

	/**
	 * return the catalog for the specified DTP catalog
	 */
	DTPCatalogWrapper catalog(org.eclipse.datatools.modelbase.sql.schema.Catalog dtpCatalog) {
		for (Iterator<DTPCatalogWrapper> stream = this.catalogWrappers(); stream.hasNext(); ) {
			DTPCatalogWrapper catalog = stream.next();
			if (catalog.wraps(dtpCatalog)) {
				return catalog;
			}
		}
		throw new IllegalArgumentException("invalid DTP catalog: " + dtpCatalog);  //$NON-NLS-1$
	}

	// ***** schemata

	@Override
	@SuppressWarnings("unchecked")
	List<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata() {
		return this.dtpDatabase.getSchemas();
	}

	@Override
	synchronized DTPSchemaWrapper[] schemata_() {
		return (this.supportsCatalogs()) ?
			this.getDefaultCatalog().schemata_()
		:
			super.schemata_();
	}


	// ********** Comparable implementation **********

	public int compareTo(Database database) {
		return Collator.getInstance().compare(this.getName(), database.getName());
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.schema.Database database) {
		return this.dtpDatabase == database;
	}

	/**
	 * return the table for the specified DTP table
	 */
	DTPTableWrapper table(org.eclipse.datatools.modelbase.sql.tables.Table dtpTable) {
		return this.schema(dtpTable.getSchema()).table(dtpTable);
	}

	/**
	 * return the column for the specified DTP column
	 */
	DTPColumnWrapper column(org.eclipse.datatools.modelbase.sql.tables.Column dtpColumn) {
		return this.table(dtpColumn.getTable()).column(dtpColumn);
	}

	@Override
	DTPDatabaseWrapper database() {
		return this;
	}
	

	// ********** disposal **********

	// must be public because it is defined in InternalDatabase interface
	@Override
	public synchronized void dispose() {
		super.dispose();
	}

	@Override
	void dispose_() {
		this.defaultCatalogCalculated = false;
		this.defaultCatalog = null;
		this.disposeCatalogs();
		super.dispose_();
	}

	private void disposeCatalogs() {
		if (this.catalogs != null) {
			for (DTPCatalogWrapper catalog : this.catalogs) {
				catalog.dispose();
			}
			this.catalogs = null;
		}
	}

}
