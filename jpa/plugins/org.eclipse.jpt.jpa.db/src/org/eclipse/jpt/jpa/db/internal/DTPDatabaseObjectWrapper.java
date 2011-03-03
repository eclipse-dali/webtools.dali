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

import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;
import org.eclipse.datatools.connectivity.sqm.core.rte.RefreshManager;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.db.DatabaseObject;
import org.eclipse.jpt.jpa.db.internal.driver.DTPDriverAdapter;

/**
 * DTP Object Wrapper base class
 */
abstract class DTPDatabaseObjectWrapper<P extends DTPDatabaseObject>
	implements DTPDatabaseObject
{
	/** we need a way to get to the connection profile */
	final P parent;

	/** listen for the "catalog object" being refreshed */
	private final ICatalogObjectListener catalogObjectListener;


	// ********** constructor **********

	DTPDatabaseObjectWrapper(P parent) {
		super();
		this.parent = parent;
		if (this.getConnectionProfile().isConnected()) {
			// we only listen to "live" connections (as opposed to "off-line" connections);
			// and the model is rebuilt when the connection connects or disconnects
			this.catalogObjectListener = this.buildCatalogObjectListener();
			if (this.getConnectionProfile().hasAnyListeners()) {
				this.startListening();
			}
		} else {
			this.catalogObjectListener = null;
		}
	}


	// ********** names vs. identifiers **********

	/**
	 * Examples:<ul>
	 * <li>Oracle etc.<ul><code>
	 *     <li>Table(FOO) vs. "Foo" => null
	 *     <li>Table(BAR) vs. "Foo" => "BAR"
	 *     <li>Table(Foo) vs. "Foo" => "\"Foo\""
	 *     <li>Table(Bar) vs. "Foo" => "\"Bar\""
	 * </code></ul>
	 * <li>PostgreSQL etc.<ul><code>
	 *     <li>Table(foo) vs. "Foo" => null
	 *     <li>Table(bar) vs. "Foo" => "bar"
	 *     <li>Table(Foo) vs. "Foo" => "\"Foo\""
	 *     <li>Table(Bar) vs. "Foo" => "\"Bar\""
	 * </code></ul>
	 * <li>SQL Server etc.<ul><code>
	 *     <li>Table(Foo) vs. "Foo" => null
	 *     <li>Table(foo) vs. "Foo" => "foo"
	 *     <li>Table(bar) vs. "Foo" => "bar"
	 *     <li>Table(Bar) vs. "Foo" => "Bar"
	 * </code></ul>
	 * </ul>
	 */
	public String getIdentifier(String defaultName) {
		return this.getDTPDriverAdapter().convertNameToIdentifier(this.getName(), defaultName);
	}

	/**
	 * Examples:<ul>
	 * <li>Oracle etc.<ul><code>
	 *     <li>Table(FOO) => "FOO"
	 *     <li>Table(Foo) => "\"Foo\""
	 *     <li>Table(foo) => "\"foo\""
	 *     <li>Table(foo++) => "\"foo++\""
	 *     <li>Table(f"o) => "\"f\"\"o\"" (i.e. "f""o")
	 * </code></ul>
	 * <li>PostgreSQL etc.<ul><code>
	 *     <li>Table(FOO) => "\"FOO\""
	 *     <li>Table(Foo) => "\"Foo\""
	 *     <li>Table(foo) => "foo"
	 *     <li>Table(foo++) => "\"foo++\""
	 *     <li>Table(f"o) => "\"f\"\"o\"" (i.e. "f""o")
	 * </code></ul>
	 * <li>SQL Server etc.<ul><code>
	 *     <li>Table(FOO) => "FOO"
	 *     <li>Table(Foo) => "Foo"
	 *     <li>Table(foo) => "foo"
	 *     <li>Table(foo++) => "\"foo++\""
	 *     <li>Table(f"o) => "\"f\"\"o\"" (i.e. "f""o")
	 * </code></ul>
	 * <li>MySQL<ul><code>
	 *     <li>Table(FOO) => "FOO"
	 *     <li>Table(Foo) => "Foo"
	 *     <li>Table(foo) => "foo"
	 *     <li>Table(foo++) => "`foo++`"
	 *     <li>Table(f"o) => "`f\"o`" (i.e. `f"o`)
	 * </code></ul>
	 * </ul>
	 */
	public String getIdentifier() {
		return this.convertNameToIdentifier(this.getName());
	}

	String convertNameToIdentifier(String name) {
		return this.getDTPDriverAdapter().convertNameToIdentifier(name);
	}


	// ********** DTP database object listener **********

	private ICatalogObjectListener buildCatalogObjectListener() {
		return new ICatalogObjectListener() {
			public void notifyChanged(ICatalogObject dmElement, int eventType) {
				if (dmElement == DTPDatabaseObjectWrapper.this.getCatalogObject()) {
					// 'eventType' doesn't seem to be very useful, so drop it
					DTPDatabaseObjectWrapper.this.catalogObjectChanged();
				}
			}
		};
	}

	/**
	 * Typically, return the wrapped DTP database object.
	 */
	abstract ICatalogObject getCatalogObject();

	/**
	 * Typically, a subclass will override this method to
	 * call <code>super.catalogObjectChanged()</code> and
	 * notify the connection profile something has changed
	 */
	void catalogObjectChanged() {
		this.clear();
	}

	/**
	 * The DTP object has changed, clear the wrapper's state so it will be
	 * synchronized on-demand.
	 */
	abstract void clear();

	// this should only be called when the connection profile is "live" and has listeners
	void startListening() {
		this.checkListener();
		RefreshManager.getInstance().AddListener(this.getCatalogObject(), this.catalogObjectListener);
	}

	// this should only be called when the connection profile is "live" and has no listeners
	void stopListening() {
		this.checkListener();
        RefreshManager.getInstance().removeListener(this.getCatalogObject(), this.catalogObjectListener);
	}

	/**
	 * We only build {@link #catalogObjectListener} when the connection
	 * profile is "live". If we get here and it is <code>null</code>,
	 * something is wrong.
	 */
	private void checkListener() {
		if (this.catalogObjectListener == null) {
			throw new IllegalStateException("the catalog listener is null");  //$NON-NLS-1$
		}
	}


	// ********** misc **********

	public DTPConnectionProfileWrapper getConnectionProfile() {
		return this.parent.getConnectionProfile();
	}

	public DTPDatabaseWrapper getDatabase() {
		return this.parent.getDatabase();
	}

	DTPDriverAdapter getDTPDriverAdapter() {
		return this.getDatabase().getDTPDriverAdapter();
	}

	/**
	 * Convenience method.
	 */
	<T extends DatabaseObject> T selectDatabaseObjectNamed(Iterable<T> databaseObjects, String name) {
		for (T databaseObject : databaseObjects) {
			if (databaseObject.getName().equals(name)) {
				return databaseObject;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getName());
	}
}
