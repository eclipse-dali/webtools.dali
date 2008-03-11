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


	DTPWrapper(ConnectionProfileHolder connectionProfileHolder) {
		super();
		this.connectionProfileHolder = connectionProfileHolder;
		this.catalogObjectListener = this.buildCatalogObjectListener();
		if (this.connectionProfile().isConnected()) {
			RefreshManager.getInstance().AddListener(this.catalogObject(), this.catalogObjectListener);
		}
	}

	private ICatalogObjectListener buildCatalogObjectListener() {
		return new ICatalogObjectListener() {
			public void notifyChanged(ICatalogObject catalogObject, int eventType) {
				if (catalogObject == DTPWrapper.this.catalogObject()) {
					DTPWrapper.this.catalogObjectChanged(eventType);
				}
			}
		};
	}

	// typically, the wrapped DTP object
	abstract ICatalogObject catalogObject();

	// typically, notify the connection profile something has changed
	abstract void catalogObjectChanged(int eventType);

	public DTPConnectionProfileWrapper connectionProfile() {
		return this.connectionProfileHolder.connectionProfile();
	}

	void dispose() {
        RefreshManager.getInstance().removeListener(this.catalogObject(), this.catalogObjectListener);
	}

	// all the subclasses can implement this method
	abstract String name();

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.name());
	}

}
