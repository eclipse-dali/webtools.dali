/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.utility.internal.ClassTools;

/**
 *  NullConnectionProfile
 */
public final class NullConnectionProfile extends ConnectionProfile {
	
	private static NullConnectionProfile INSTANCE;

	/**
	 * singleton support
	 */
	static synchronized ConnectionProfile instance() {
		if( INSTANCE == null) {
			INSTANCE = new NullConnectionProfile();
		}
		return INSTANCE;
	}

	private NullConnectionProfile() {
		super( null);
	}

	// ********** behavior **********

	@Override
	protected Database buildDatabase() {
		return NullDatabase.instance();
	}
	
	@Override
	public void connect() {
		// do nothing
	}

	@Override
	public void disconnect() {
		// do nothing
	}
	
	@Override
	void databaseChanged( Database database, int eventType) {
		// do nothing
	}
	
	@Override
	 void catalogChanged( Catalog catalog, Database database, int eventType) {
		// do nothing
	}
	
	@Override
	 void schemaChanged( Schema schema, Database database, int eventType) {
		// do nothing
	}
	
	@Override
	 void tableChanged( Table table, Schema schema, Database database, int eventType) {
		// do nothing
	}
	
	public IStatus saveWorkOfflineData() {
		return Status.CANCEL_STATUS;
	}
	
	public IStatus workOffline() {
		return Status.CANCEL_STATUS;
	}
	
	@Override
	boolean wraps( org.eclipse.datatools.connectivity.IConnectionProfile dtpProfile) {
		return false;
	}
	
	
	// ********** queries **********
		
	@Override
	public Database getDatabase() {
		return NullDatabase.instance();
	}
	
	@Override
	public String getName() {
		return ClassTools.shortClassNameForObject( this);
	}

	@Override
	public String getDatabaseName() {
		return "";
	}

	@Override
	public String getDatabaseProduct() {
		return "";
	}

	@Override
	public String getDatabaseVendor() {
		return "";
	}

	@Override
	public String getDatabaseVersion() {
		return "";
	}

	@Override
	public String getUserName() {
		return "";
	}

	@Override
	public String getUserPassword() {
		return "";
	}

	@Override
	public String getDefaultSchema() {
		return "";
	}
	
	@Override
	public String getDriverClass() {
		return "";
	}

	@Override
	public String getUrl() {
		return "";
	}
	
	@Override
	public String getInstanceId() {
		return "";
	}

	@Override
	public String getProviderId() {
		return "";
	}

	@Override
	public String getDriverDefinitionId() {
		return "";
	}

	@Override
	public String getDriverJarList() {
		return "";
	}
	
	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public boolean isWorkingOffline() {
		return false;
	}
	
	public boolean supportsWorkOfflineMode() {
		return false;
	}
	
	public boolean canWorkOffline() {
		return false;
	}
	
	// ********** listeners **********

	@Override
	public void addProfileListener( ProfileListener listener) {
		// do nothing
	}

	@Override
	public void removeProfileListener( ProfileListener listener) {
		// do nothing
	}
	
	@Override
	public void addConnectionListener( ConnectionListener listener) {
		// do nothing
	}

	@Override
	public void removeConnectionListener( ConnectionListener listener) {
		// do nothing
	}

}
