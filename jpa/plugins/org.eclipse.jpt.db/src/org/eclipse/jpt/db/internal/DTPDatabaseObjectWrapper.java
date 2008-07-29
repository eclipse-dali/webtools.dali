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

import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;
import org.eclipse.datatools.connectivity.sqm.core.rte.RefreshManager;
import org.eclipse.jpt.db.DatabaseObject;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 *  DTP Catalog Object Wrapper base class
 */
abstract class DTPDatabaseObjectWrapper
	implements DTPDatabaseObject
{
	// we need a way to get to the connection profile
	private final DTPDatabaseObject parent;

	// listen for the "catalog object" being refreshed
	private final ICatalogObjectListener catalogObjectListener;

	// listen for this DTP catalog object to refresh
	final ICatalogObject catalogObject;


	// ********** construction/initialization **********

	DTPDatabaseObjectWrapper(DTPDatabaseObject parent, Object dtpObject) {
		super();
		this.parent = parent;
		if (this.getConnectionProfile().isConnected()) {
			// we only listen to "live" connections (as opposed to "off-line" connections)
			this.catalogObject = (ICatalogObject) dtpObject;
			this.catalogObjectListener = this.buildCatalogObjectListener();
			if (this.getConnectionProfile().hasAnyListeners()) {
				this.startListening();
			}
		} else {
			this.catalogObject = null;
			this.catalogObjectListener = null;
		}
	}

	private ICatalogObjectListener buildCatalogObjectListener() {
		return new ICatalogObjectListener() {
			public void notifyChanged(ICatalogObject dmElement, int eventType) {
				if (dmElement == DTPDatabaseObjectWrapper.this.catalogObject) {
					// 'eventType' doesn't seem to be very useful, so drop it
					DTPDatabaseObjectWrapper.this.catalogObjectChanged();
				}
			}
		};
	}

	// typically, notify the connection profile something has changed
	void catalogObjectChanged() {
		this.clear();
	}

	/**
	 * The DTP object has changed, clear the wrapper's state so it will be
	 * synchronized on-demand.
	 */
	abstract void clear();



	// ********** queries **********

	DTPDatabaseObject getParent() {
		return this.parent;
	}

	public DTPConnectionProfileWrapper getConnectionProfile() {
		return this.parent.getConnectionProfile();
	}

	public DTPDatabaseWrapper getDatabase() {
		return this.getConnectionProfile().getDatabase();
	}

	/**
	 * This is called by whenever we need to find a component by name
	 * (e.g. Table.getColumnNamed(String)). We channel all the calls to the
	 * connection profile, which then delegates to the JPA platform-
	 * supplied "database finder".
	 */
	<T extends DatabaseObject> T getDatabaseObjectNamed(T[] databaseObjects, String name) {
		return this.getConnectionProfile().getDatabaseObjectNamed(databaseObjects, name);
	}

	/**
	 * Examples:
	 * Oracle etc.
	 *     Table(FOO) vs. "Foo" => null
	 *     Table(BAR) vs. "Foo" => "BAR"
	 *     Table(Foo) vs. "Foo" => "\"Foo\""
	 *     Table(Bar) vs. "Foo" => "\"Bar\""
	 *     
	 * PostgreSQL etc.
	 *     Table(foo) vs. "Foo" => null
	 *     Table(bar) vs. "Foo" => "bar"
	 *     Table(Foo) vs. "Foo" => "\"Foo\""
	 *     Table(Bar) vs. "Foo" => "\"Bar\""
	 *     
	 * SQL Server etc.
	 *     Table(Foo) vs. "Foo" => null
	 *     Table(foo) vs. "Foo" => "foo"
	 *     Table(bar) vs. "Foo" => "bar"
	 *     Table(Bar) vs. "Foo" => "Bar"
	 */
	public String getAnnotationIdentifier(String javaIdentifier) {
		return this.getAnnotationIdentifier(javaIdentifier, this.getName());
	}

	String getAnnotationIdentifier(String javaIdentifier, String dbIdentifier) {
		if (this.getDatabase().vendorFoldsToUppercase()) {
			if (StringTools.stringIsUppercase(dbIdentifier)) {
				return dbIdentifier.equalsIgnoreCase(javaIdentifier) ? null : dbIdentifier;
			}
			return this.getDatabase().delimitIdentifier(dbIdentifier);
		}
		if (this.getDatabase().vendorFoldsToLowercase()) {
			if (StringTools.stringIsLowercase(dbIdentifier)) {
				return dbIdentifier.equalsIgnoreCase(javaIdentifier) ? null : dbIdentifier;
			}
			return this.getDatabase().delimitIdentifier(dbIdentifier);
		}
		if (this.getDatabase().vendorDoesNotFold()) {
			return dbIdentifier.equals(javaIdentifier) ? null : dbIdentifier;
		}
		throw new IllegalStateException("unknown vendor folding: " + this.getDatabase().getVendor()); //$NON-NLS-1$
	}

	/**
	 * Examples:
	 * Oracle etc.
	 *     Table(FOO) => "FOO"
	 *     Table(Foo) => "\"Foo\""
	 *     Table(foo) => "\"foo\""
	 *     
	 * PostgreSQL etc.
	 *     Table(foo) => "foo"
	 *     Table(Foo) => "\"Foo\""
	 *     Table(FOO) => "\"FOO\""
	 *     
	 * SQL Server etc.
	 *     Table(Foo) => "Foo"
	 *     Table(foo) => "foo"
	 *     Table(FOO) => "FOO"
	 */
	public String getAnnotationIdentifier() {
		String name = this.getName();
		if (this.getDatabase().vendorFoldsToUppercase()) {
			return StringTools.stringIsUppercase(name) ? name : this.getDatabase().delimitIdentifier(name);
		}
		if (this.getDatabase().vendorFoldsToLowercase()) {
			return StringTools.stringIsLowercase(name) ? name : this.getDatabase().delimitIdentifier(name);
		}
		if (this.getDatabase().vendorDoesNotFold()) {
			return name;
		}
		throw new IllegalStateException("unknown vendor folding: " + this.getDatabase().getVendor()); //$NON-NLS-1$
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getName());
	}


	// ********** listening to DTP database object **********

	// this should only be called when the connection profile is "live" and has listeners
	void startListening() {
		this.checkListener();
		RefreshManager.getInstance().AddListener(this.catalogObject, this.catalogObjectListener);
	}

	// this should only be called when the connection profile is "live" and has no listeners
	void stopListening() {
		this.checkListener();
        RefreshManager.getInstance().removeListener(this.catalogObject, this.catalogObjectListener);
	}

	private void checkListener() {
		if (this.catalogObjectListener == null) {
			throw new IllegalStateException("the catalog listener is null");  //$NON-NLS-1$
		}
	}

}
