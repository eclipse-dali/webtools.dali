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
import java.util.Vector;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.datatools.connectivity.ConnectEvent;
import org.eclipse.datatools.connectivity.IManagedConnection;
import org.eclipse.datatools.connectivity.IManagedConnectionOfflineListener;
import org.eclipse.datatools.connectivity.drivers.DriverManager;
import org.eclipse.datatools.connectivity.drivers.jdbc.IJDBCDriverDefinitionConstants;
import org.eclipse.datatools.connectivity.sqm.core.connection.ConnectionInfo;
import org.eclipse.datatools.sqltools.core.DatabaseIdentifier;
import org.eclipse.datatools.sqltools.core.profile.ProfileUtil;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;

/**
 *  Wrap a DTP ConnectionProfile
 */
final class DTPConnectionProfileWrapper
	implements ConnectionProfile, ConnectionProfileHolder
{
	// the wrapped DTP connection profile
	private final org.eclipse.datatools.connectivity.IConnectionProfile dtpConnectionProfile;

	// forward events from the DTP connection profile's managed connections
	private final LocalConnectionListener connectionListener;

	// lazy-initialized - can be a "null" database
	private InternalDatabase database;


	// ********** constants **********

	public static final String LIVE_DTP_CONNECTION_TYPE = "java.sql.Connection";  //$NON-NLS-1$

	public static final String OFFLINE_DTP_CONNECTION_TYPE = "org.eclipse.datatools.connectivity.sqm.core.connection.ConnectionInfo";  //$NON-NLS-1$

	public static final String DATABASE_PRODUCT_PROP_ID = "org.eclipse.datatools.connectivity.server.version";  //$NON-NLS-1$

	public static final String POSTGRESQL_VENDOR = "postgres";  //$NON-NLS-1$

	public static final String POSTGRESQL_DEFAULT_SCHEMA_NAME = "public";  //$NON-NLS-1$


	// ********** constructor **********

	DTPConnectionProfileWrapper(org.eclipse.datatools.connectivity.IConnectionProfile dtpConnectionProfile) {
		super();
		this.dtpConnectionProfile = dtpConnectionProfile;
		this.connectionListener = new LocalConnectionListener();
		this.getDtpLiveConnection().addConnectionListener(this.connectionListener);
		this.getDtpOfflineConnection().addConnectionListener(this.connectionListener);
	}


	// ********** ConnectionProfileHolder implementation **********

	public DTPConnectionProfileWrapper getConnectionProfile() {
		return this;
	}


	// ********** ConnectionProfile implementation **********

	public String getName() {
		return this.dtpConnectionProfile.getName();
	}

	public boolean isNull() {
		return false;
	}

	public void connect() {
		if (this.isDisconnected()) {
			this.checkStatus(this.dtpConnectionProfile.connect());
		}
	}
	
	public void disconnect() {
		this.checkStatus(this.dtpConnectionProfile.disconnect());
	}

	public boolean isActive() {
		return this.isConnected() || this.isWorkingOffline();
	}

	public boolean isInactive() {
		return ! this.isActive();
	}

	public boolean isConnected() {
		return this.getDtpLiveConnection().isConnected();
	}

	public boolean isDisconnected() {
		return ! this.isConnected();
	}

	public boolean isWorkingOffline() {
		return this.getDtpOfflineConnection().isWorkingOffline();
	}

	public boolean supportsWorkOfflineMode() {
		return this.dtpConnectionProfile.supportsWorkOfflineMode();
	}

	public boolean canWorkOffline() {
		return this.dtpConnectionProfile.canWorkOffline();
	}

	public IStatus saveWorkOfflineData() {
		return this.dtpConnectionProfile.saveWorkOfflineData();
	}
	
	public IStatus workOffline() {
		return this.dtpConnectionProfile.workOffline();
	}
	
	public String getDatabaseVendor() {
		return this.getProperty(IJDBCDriverDefinitionConstants.DATABASE_VENDOR_PROP_ID);
	}

	public String getDatabaseName() {
		return this.getProperty(IJDBCDriverDefinitionConstants.DATABASE_NAME_PROP_ID);
	}

	public String getDatabaseProduct() {
		return this.getProperty(DATABASE_PRODUCT_PROP_ID);
	}

	public String getDatabaseVersion() {
		return this.getProperty(IJDBCDriverDefinitionConstants.DATABASE_VERSION_PROP_ID);
	}

	public String getUserName() {
		return this.getProperty(IJDBCDriverDefinitionConstants.USERNAME_PROP_ID);
	}

	public String getUserPassword() {
		return this.getProperty(IJDBCDriverDefinitionConstants.PASSWORD_PROP_ID);
	}

	public synchronized InternalDatabase getDatabase() {
		if (this.database == null) {
			this.database = this.buildDatabase();
		}
		return this.database;
	}

	public DTPCatalogWrapper getDefaultCatalog() {
		return this.getDatabase().getDefaultCatalog();
	}

	public void addConnectionListener(ConnectionListener listener) {
		this.connectionListener.addConnectionListener(listener);
	}

	public void removeConnectionListener(ConnectionListener listener) {
		this.connectionListener.removeConnectionListener(listener);
	}

	public Schema getDefaultSchema() {
		return this.getDatabase().schemaNamed(this.getDefaultSchemaName());
	}

	public String getDriverClassName() {
		return this.getProperty(IJDBCDriverDefinitionConstants.DRIVER_CLASS_PROP_ID);
	}

	public String getUrl() {
		return this.getProperty(IJDBCDriverDefinitionConstants.URL_PROP_ID);
	}

	public String getInstanceID() {
		return this.dtpConnectionProfile.getInstanceID();
	}

	public String getProviderID() {
		return this.dtpConnectionProfile.getProviderId();
	}

	public String getDriverDefinitionID() {
		return this.getProperty(DRIVER_DEFINITION_PROP_ID);
	}

	public String getDriverJarList() {
		return DriverManager.getInstance().getDriverInstanceByID(this.getDriverDefinitionID()).getJarList();
	}


	// ********** internal methods **********

	IManagedConnection getDtpLiveConnection() {
		return this.dtpConnectionProfile.getManagedConnection(LIVE_DTP_CONNECTION_TYPE);
	}

	IManagedConnection getDtpOfflineConnection() {
		return this.dtpConnectionProfile.getManagedConnection(OFFLINE_DTP_CONNECTION_TYPE);
	}

	private void checkStatus(IStatus status) {
		if (status.isOK()) {
			return;
		}
		if (status.isMultiStatus()) {
			status = status.getChildren()[0];  // take the first one?
		}
		throw new RuntimeException(status.getMessage(), status.getException());
	}

	private InternalDatabase buildDatabase() {
		if (this.isInactive()) {
			return NullDatabase.instance();
		}

		if (this.isWorkingOffline()) {
			ConnectionInfo connectionInfo = (ConnectionInfo) this.getDtpOfflineConnection().getConnection().getRawConnection();
			return new DTPDatabaseWrapper(this, connectionInfo.getSharedDatabase());
		}

		// TODO see DTP bug 202306
		// pass connect=true in to ProfileUtil.getDatabase()
		// there is a bug mentioned in a comment: 
		//     "during the profile connected event notification, 
		//     IManagedConnection is connected while IConnectionProfile is not"
		// so, some hackery here to handle hackery there
		return new DTPDatabaseWrapper(this, ProfileUtil.getDatabase(new DatabaseIdentifier(this.getName(), this.getDatabaseName()), true));
	}
	
	boolean wraps(org.eclipse.datatools.connectivity.IConnectionProfile dtpCP) {
		return this.dtpConnectionProfile == dtpCP;
	}

	void databaseChanged(DTPDatabaseWrapper db, int eventType) {
		this.connectionListener.databaseChanged(db, eventType);
	}

	void catalogChanged(DTPCatalogWrapper catalog, int eventType) {
		this.connectionListener.catalogChanged(catalog, eventType);
	}

	void schemaChanged(DTPSchemaWrapper schema, int eventType) {
		this.connectionListener.schemaChanged(schema, eventType);
	}

	void sequenceChanged(DTPSequenceWrapper sequence, int eventType) {
		this.connectionListener.sequenceChanged(sequence, eventType);
	}

	void tableChanged(DTPTableWrapper table, int eventType) {
		this.connectionListener.tableChanged(table, eventType);
	}

	void columnChanged(DTPColumnWrapper column, int eventType) {
		this.connectionListener.columnChanged(column, eventType);
	}

	void foreignKeyChanged(DTPForeignKeyWrapper foreignKey, int eventType) {
		this.connectionListener.foreignKeyChanged(foreignKey, eventType);
	}

	/**
	 * private - use #getDefaultSchema() : Schema instead
	 */
	private String getDefaultSchemaName() {
		if (this.getDatabase().getVendor().equalsIgnoreCase(POSTGRESQL_VENDOR)) {
			return POSTGRESQL_DEFAULT_SCHEMA_NAME;
		}
		return this.getUserName();
	}

	private String getProperty(String propertyName) {
		return this.dtpConnectionProfile.getBaseProperties().getProperty(propertyName);
	}


	// ********** disposal **********

	synchronized void dispose() {
		this.disposeDatabase();
		this.getDtpOfflineConnection().removeConnectionListener(this.connectionListener);
		this.getDtpLiveConnection().removeConnectionListener(this.connectionListener);
	}

	synchronized void disposeDatabase() {
		if (this.database != null) {
			this.database.dispose();
			this.database = null;
		}
	}


	// ********** Comparable implementation **********

	public int compareTo(ConnectionProfile connectionProfile) {
		return Collator.getInstance().compare(this.getName(), connectionProfile.getName());
	}


	// ********** overrides **********

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getName());
	}


	// ********** DTP connection listener **********

	/**
	 * This listener translates and forwards IManagedConnectionListener and
	 * IManagedConnectionOfflineListener events to ConnectionListeners.
	 */
	private class LocalConnectionListener implements IManagedConnectionOfflineListener {
		private Vector<ConnectionListener> listeners = new Vector<ConnectionListener>();

		LocalConnectionListener() {
			super();
		}

		void addConnectionListener(ConnectionListener listener) {
			this.listeners.add(listener);
		}

		void removeConnectionListener(ConnectionListener listener) {
			this.listeners.remove(listener);
		}

		private Iterator<ConnectionListener> listeners() {
			return new CloneIterator<ConnectionListener>(this.listeners);
		}


		// ********** IManagedConnectionListener implementation **********

		public void opened(ConnectEvent event) {
			if (event.getConnection() == DTPConnectionProfileWrapper.this.getDtpLiveConnection()) {
				// clear the database so it will be rebuilt
				DTPConnectionProfileWrapper.this.disposeDatabase();
				for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
					stream.next().opened(DTPConnectionProfileWrapper.this);
				}
			}
		}

		public void modified(ConnectEvent event) {
			for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().modified(DTPConnectionProfileWrapper.this);
			}
		}

		public boolean okToClose(ConnectEvent event) {
			if (event.getConnection() == DTPConnectionProfileWrapper.this.getDtpLiveConnection()) {
				for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
					if ( ! stream.next().okToClose(DTPConnectionProfileWrapper.this)) {
						return false;
					}
				}
			}
			return true;
		}

		public void aboutToClose(ConnectEvent event) {
			if (event.getConnection() == DTPConnectionProfileWrapper.this.getDtpLiveConnection()) {
				for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
					stream.next().aboutToClose(DTPConnectionProfileWrapper.this);
				}
			}
		}

		public void closed(ConnectEvent event) {
			// clear the database so it will be rebuilt
			DTPConnectionProfileWrapper.this.disposeDatabase();
			// there is no DETACHED event, therefore closed is sent twice (i.e. by both connections)
			for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().closed(DTPConnectionProfileWrapper.this);
			}
		}


		// ********** IManagedConnectionOfflineListener implementation **********

		// live => off-line
		public boolean okToDetach(ConnectEvent event) {
			if (event.getConnection() == DTPConnectionProfileWrapper.this.getDtpOfflineConnection()) {
				for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
					if ( ! stream.next().okToClose(DTPConnectionProfileWrapper.this)) {
						return false;
					}
				}
			}
			return true;
		}
		
		// live => off-line
		public void aboutToDetach(ConnectEvent event) {
			if (event.getConnection() == DTPConnectionProfileWrapper.this.getDtpOfflineConnection()) {
				for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
					stream.next().aboutToClose(DTPConnectionProfileWrapper.this);
				}
			}
		}

		// live => off-line
		public void workingOffline(ConnectEvent event) {
			// clear the database so it will be rebuilt
			DTPConnectionProfileWrapper.this.disposeDatabase();
			for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().opened(DTPConnectionProfileWrapper.this);
			}
		}

		// off-line => live
		public void aboutToAttach(ConnectEvent event) {
			// ignore
		}


		// ********** internal methods **********

		void databaseChanged(DTPDatabaseWrapper db, int eventType) {
			for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().databaseChanged(DTPConnectionProfileWrapper.this, db);
			}
		}

		void catalogChanged(DTPCatalogWrapper catalog, int eventType) {
			for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().catalogChanged(DTPConnectionProfileWrapper.this, catalog);
			}
		}

		void schemaChanged(DTPSchemaWrapper schema, int eventType) {
			for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().schemaChanged(DTPConnectionProfileWrapper.this, schema);
			}
		}

		void sequenceChanged(DTPSequenceWrapper sequence, int eventType) {
			for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().sequenceChanged(DTPConnectionProfileWrapper.this, sequence);
			}
		}

		void tableChanged(DTPTableWrapper table, int eventType) {
			for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().tableChanged(DTPConnectionProfileWrapper.this, table);
			}
		}

		void columnChanged(DTPColumnWrapper column, int eventType) {
			for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().columnChanged(DTPConnectionProfileWrapper.this, column);
			}
		}

		void foreignKeyChanged(DTPForeignKeyWrapper foreignKey, int eventType) {
			for (Iterator<ConnectionListener> stream = this.listeners(); stream.hasNext(); ) {
				stream.next().foreignKeyChanged(DTPConnectionProfileWrapper.this, foreignKey);
			}
		}

	}

}
