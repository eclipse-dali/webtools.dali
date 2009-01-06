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
			// we only listen to "live" connections (as opposed to "off-line" connections);
			// and the model is rebuilt when the connection connects or disconnects
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
	 * This is called by whenever we need to find a component by identifier
	 * (e.g. Table.getColumnForIdentifier(String)). We channel all the calls to the
	 * connection profile, which then delegates to the JPA platform-supplied
	 * "database finder".
	 */
	<T extends DatabaseObject> T selectDatabaseObjectForIdentifier(T[] databaseObjects, String identifier) {
		return this.getConnectionProfile().selectDatabaseObjectForIdentifier(databaseObjects, identifier);
	}

	/**
	 * Convenience method.
	 */
	<T extends DatabaseObject> T selectDatabaseObjectNamed(T[] databaseObjects, String name) {
		for (T dbObject : databaseObjects) {
			if (dbObject.getName().equals(name)) {
				return dbObject;
			}
		}
		return null;
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
	public String getIdentifier(String defaultName) {
		return this.getDatabase().convertNameToIdentifier(this.getName(), defaultName);
	}

	/**
	 * Examples:
	 * Oracle etc.
	 *     Table(FOO) => "FOO"
	 *     Table(Foo) => "\"Foo\""
	 *     Table(foo) => "\"foo\""
	 *     Table(foo++) => "\"foo++\""
	 *     Table(f"o) => "\"f\"\"o++\"" (i.e. "f""o")
	 *     
	 * PostgreSQL etc.
	 *     Table(FOO) => "\"FOO\""
	 *     Table(Foo) => "\"Foo\""
	 *     Table(foo) => "foo"
	 *     Table(foo++) => "\"foo++\""
	 *     Table(f"o) => "\"f\"\"o++\"" (i.e. "f""o")
	 *     
	 * SQL Server etc.
	 *     Table(FOO) => "FOO"
	 *     Table(Foo) => "Foo"
	 *     Table(foo) => "foo"
	 *     Table(foo++) => "\"foo++\""
	 *     Table(f"o) => "\"f\"\"o++\"" (i.e. "f""o")
	 */
	public String getIdentifier() {
		return this.getDatabase().convertNameToIdentifier(this.getName());
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
