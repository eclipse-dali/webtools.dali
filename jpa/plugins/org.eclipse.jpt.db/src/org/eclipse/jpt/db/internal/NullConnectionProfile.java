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

	protected Connection buildConnection() {
		return NullConnection.instance();
	}

	protected Database buildDatabase() {
		return NullDatabase.instance();
	}
	
	public void connect() {
		// do nothing
	}

	public void disconnect() {
		// do nothing
	}
	
	void databaseChanged( Database database, int eventType) {
		// do nothing
	}
	
	 void catalogChanged( Catalog catalog, Database database, int eventType) {
		// do nothing
	}
	
	 void schemaChanged( Schema schema, Database database, int eventType) {
		// do nothing
	}
	
	 void tableChanged( Table table, Schema schema, Database database, int eventType) {
		// do nothing
	}

	// ********** queries **********
	
	public Connection getConnection() {
		return NullConnection.instance();
	}

	public Database getDatabase() {
		return NullDatabase.instance();
	}
	
	public String getName() {
		return ClassTools.shortClassNameForObject( this);
	}

	public String getDatabaseName() {
		return "";
	}

	public String getDatabaseProduct() {
		return "";
	}

	public String getDatabaseVendor() {
		return "";
	}

	public String getDatabaseVersion() {
		return "";
	}

	public String getUserName() {
		return "";
	}
	
	public String getInstanceId() {
		return "";
	}

	public String getProviderId() {
		return "";
	}
	
	public boolean isConnected() {
		return false;
	}
	
	boolean wraps( org.eclipse.datatools.connectivity.IConnectionProfile dtpProfile) {
		return false;
	}
	
	// ********** listeners **********

	public void addProfileListener( ProfileListener listener) {
		// do nothing
	}

	public void removeProfileListener( ProfileListener listener) {
		// do nothing
	}
	
	public void addConnectionListener( ConnectionListener listener) {
		// do nothing
	}

	public void removeConnectionListener( ConnectionListener listener) {
		// do nothing
	}

}
