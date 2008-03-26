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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.ClassTools;

/**
 *  "null" connection profile
 */
final class NullConnectionProfile
	implements ConnectionProfile
{
	private static final String EMPTY_STRING = "";  //$NON-NLS-1$


	// ********** singleton **********

	private static final NullConnectionProfile INSTANCE = new NullConnectionProfile();

	static ConnectionProfile instance() {
		return INSTANCE;
	}

	/**
	 * 'private' to ensure singleton
	 */
	private NullConnectionProfile() {
		super();
	}


	// ********** ConnectionProfile implementation **********

	public void connect() {
		// do nothing
	}

	public void disconnect() {
		// do nothing
	}

	public IStatus saveWorkOfflineData() {
		return Status.CANCEL_STATUS;
	}

	public IStatus workOffline() {
		return Status.CANCEL_STATUS;
	}

	public Database getDatabase() {
		return NullDatabase.instance();
	}

	public String getName() {
		return ClassTools.shortClassNameForObject(this);
	}

	public String getDatabaseName() {
		return EMPTY_STRING;
	}

	public String getDatabaseProduct() {
		return EMPTY_STRING;
	}

	public String getDatabaseVendor() {
		return EMPTY_STRING;
	}

	public String getDatabaseVersion() {
		return EMPTY_STRING;
	}

	public String getUserName() {
		return EMPTY_STRING;
	}

	public String getUserPassword() {
		return EMPTY_STRING;
	}

	public String getDriverClassName() {
		return EMPTY_STRING;
	}

	public String getUrl() {
		return EMPTY_STRING;
	}

	public String getInstanceID() {
		return EMPTY_STRING;
	}

	public String getProviderID() {
		return EMPTY_STRING;
	}

	public String getDriverDefinitionID() {
		return EMPTY_STRING;
	}

	public String getDriverJarList() {
		return EMPTY_STRING;
	}

	public boolean isActive() {
		return false;
	}

	public boolean isWorkingOffline() {
		return false;
	}

	public boolean isConnected() {
		return false;
	}

	public boolean supportsWorkOfflineMode() {
		return false;
	}

	public boolean canWorkOffline() {
		return false;
	}

	public void addConnectionListener( ConnectionListener listener) {
		// do nothing
	}

	public void removeConnectionListener( ConnectionListener listener) {
		// do nothing
	}

	public Schema getDefaultSchema() {
		return null;
	}

	public Catalog getDefaultCatalog() {
		return null;
	}

	public boolean isNull() {
		return true;
	}


	// ********** Comparable implementation **********

	public int compareTo(ConnectionProfile o) {
		throw new UnsupportedOperationException("the \"null\" connection profile should not be in a sorted list");  //$NON-NLS-1$
	}


	// ********** Object overrides **********

	@Override
	public String toString() {
		return ClassTools.toStringClassNameForObject(this);
	}

}
