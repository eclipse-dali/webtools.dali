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
import org.eclipse.jpt.utility.internal.StringTools;

/**
 *  DTP Catalog Object Wrapper base class
 */
abstract class DTPWrapper
	implements ConnectionProfileHolder
{
	// we need a way to get to the connection profile
	private final ConnectionProfileHolder connectionProfileHolder;

	// listen for the "catalog object" being refreshed
	private final ICatalogObjectListener catalogObjectListener;

	// we only listen to "live" connections
	private final boolean connectionIsLive;

	// listen for this to refresh
	final ICatalogObject catalogObject;


	DTPWrapper(ConnectionProfileHolder connectionProfileHolder, Object dtpObject) {
		super();
		this.connectionProfileHolder = connectionProfileHolder;
		this.connectionIsLive = this.getConnectionProfile().isConnected();
		if (this.connectionIsLive) {
			this.catalogObject = (ICatalogObject) dtpObject;
			this.catalogObjectListener = this.buildCatalogObjectListener();
			RefreshManager.getInstance().AddListener(this.catalogObject, this.catalogObjectListener);
		} else {
			this.catalogObject = null;
			this.catalogObjectListener = null;
		}
	}

	private ICatalogObjectListener buildCatalogObjectListener() {
		return new ICatalogObjectListener() {
			public void notifyChanged(ICatalogObject dmElement, int eventType) {
				if (dmElement == DTPWrapper.this.catalogObject) {
					DTPWrapper.this.catalogObjectChanged(eventType);
				}
			}
		};
	}

	// typically, notify the connection profile something has changed
	abstract void catalogObjectChanged(int eventType);

	public DTPConnectionProfileWrapper getConnectionProfile() {
		return this.connectionProfileHolder.getConnectionProfile();
	}

	void dispose() {
		if (this.connectionIsLive) {
	        RefreshManager.getInstance().removeListener(this.catalogObject, this.catalogObjectListener);
		}
	}

	// all the subclasses can implement this method
	abstract String getName();

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getName());
	}

}
