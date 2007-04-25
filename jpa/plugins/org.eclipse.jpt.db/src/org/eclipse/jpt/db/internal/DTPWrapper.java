/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
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
 *  DataTools Wrapper base class.
 */
public abstract class DTPWrapper implements Comparable {
	
	DTPWrapper() {
		super();
	}
	
	// ********** behavior **********
	
	protected abstract void dispose();
	
	protected abstract boolean connectionIsOnline();

	protected void addCatalogObjectListener( ICatalogObject catalogObject, ICatalogObjectListener catalogObjectListener) {
		if( this.connectionIsOnline()) {
			RefreshManager.getInstance().AddListener( catalogObject, catalogObjectListener);
		}
	}

	protected void removeCatalogObjectListener( ICatalogObject catalogObject, ICatalogObjectListener catalogObjectListener) {
		if( this.connectionIsOnline()) {
	        RefreshManager.getInstance().removeListener( catalogObject, catalogObjectListener);
		}
	}

	// ********** queries **********

	public abstract String getName();
	
	public String toString() {
		return StringTools.buildToStringFor( this, this.getName());
	}	
}