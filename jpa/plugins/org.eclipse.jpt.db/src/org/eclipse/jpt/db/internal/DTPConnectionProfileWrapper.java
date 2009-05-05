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

import com.ibm.icu.text.Collator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.datatools.connectivity.ConnectEvent;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.IManagedConnection;
import org.eclipse.datatools.connectivity.IManagedConnectionOfflineListener;
import org.eclipse.datatools.connectivity.drivers.DriverManager;
import org.eclipse.datatools.connectivity.drivers.jdbc.IJDBCDriverDefinitionConstants;
import org.eclipse.datatools.connectivity.sqm.core.connection.ConnectionInfo;
import org.eclipse.datatools.sqltools.core.DatabaseIdentifier;
import org.eclipse.datatools.sqltools.core.profile.ProfileUtil;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.DatabaseFinder;
import org.eclipse.jpt.db.DatabaseObject;
import org.eclipse.jpt.utility.internal.ListenerList;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 *  Wrap a DTP ConnectionProfile
 */
final class DTPConnectionProfileWrapper
	implements DTPDatabaseObject, ConnectionProfile
{
	// the wrapped DTP connection profile
	private final IConnectionProfile dtpConnectionProfile;

	// finder supplied by the JPA platform (determines case-sensitivity, etc.)
	private final DatabaseFinder finder;

	// callback passed to the finder
	private final DatabaseFinder.DefaultCallback databaseFinderCallback;

	// the DTP managed connection we listen to
	private final IManagedConnection dtpManagedConnection;

	// forward events from the DTP managed connection above;
	// we listen and propagate events iff we have listeners ourselves
	private final LocalConnectionListener connectionListener;

	// lazy-initialized, and deleted at disconnect
	private DTPDatabaseWrapper database;


	// ********** constants **********

	private static final String LIVE_DTP_CONNECTION_TYPE = "java.sql.Connection";  //$NON-NLS-1$

	private static final String OFFLINE_DTP_CONNECTION_TYPE = ConnectionInfo.class.getName();

	private static final String DATABASE_PRODUCT_PROP_ID = "org.eclipse.datatools.connectivity.server.version";  //$NON-NLS-1$


	// ********** constructor **********

	DTPConnectionProfileWrapper(IConnectionProfile dtpConnectionProfile, DatabaseFinder finder) {
		super();
		this.dtpConnectionProfile = dtpConnectionProfile;
		this.finder = finder;
		this.databaseFinderCallback = new DatabaseFinderCallback();
		this.dtpManagedConnection = this.buildDTPManagedConnection();
		this.connectionListener = new LocalConnectionListener();
		// don't listen to the managed connection yet
	}

	private IManagedConnection buildDTPManagedConnection() {
		String connectionType = this.dtpConnectionProfile.supportsWorkOfflineMode() ?
				OFFLINE_DTP_CONNECTION_TYPE : LIVE_DTP_CONNECTION_TYPE;
		return this.dtpConnectionProfile.getManagedConnection(connectionType);
	}


	// ********** DatabaseObject implementation **********

	public String getName() {
		return this.dtpConnectionProfile.getName();
	}

	public String getIdentifier(String javaIdentifier) {
		// connection profiles do not have "identifiers"
		throw new UnsupportedOperationException();
	}

	public String getIdentifier() {
		// connection profiles do not have "identifiers"
		throw new UnsupportedOperationException();
	}


	// ********** DTPDatabaseObject implementation **********

	public DTPConnectionProfileWrapper getConnectionProfile() {
		return this;
	}

	public synchronized DTPDatabaseWrapper getDatabase() {
		if (this.database == null) {
			this.database = this.buildDatabase();
		}
		return this.database;
	}


	// ********** ConnectionProfile implementation **********

	// ***** properties
	public String getProviderID() {
		return this.dtpConnectionProfile.getProviderId();
	}

	public String getInstanceID() {
		return this.dtpConnectionProfile.getInstanceID();
	}

	public String getDatabaseName() {
		return this.getProperty(IJDBCDriverDefinitionConstants.DATABASE_NAME_PROP_ID);
	}

	public String getDatabaseProduct() {
		return this.getProperty(DATABASE_PRODUCT_PROP_ID);
	}

	public String getDatabaseVendor() {
		return this.getProperty(IJDBCDriverDefinitionConstants.DATABASE_VENDOR_PROP_ID);
	}

	public String getDatabaseVersion() {
		return this.getProperty(IJDBCDriverDefinitionConstants.DATABASE_VERSION_PROP_ID);
	}

	public String getDriverClassName() {
		return this.getProperty(IJDBCDriverDefinitionConstants.DRIVER_CLASS_PROP_ID);
	}

	public String getURL() {
		return this.getProperty(IJDBCDriverDefinitionConstants.URL_PROP_ID);
	}

	/**
	 * Returns the user name.
	 * Allows user name composed by more than one word.
	 * If the user name contains a keyword, it returns the first word only.
	 */
	public String getUserName() {
		String userName = this.getProperty(IJDBCDriverDefinitionConstants.USERNAME_PROP_ID);
		userName = userName.trim(); 
		String[] names =  userName.split("\\s+");	//$NON-NLS-1$
		if(names.length == 3) { // 208946 handle username like "sys as sysdba" on Oracle
		    if(this.nameIsKeyword(names[1])) {
		    	return names[0];
		    }
		}
		return userName;
	}

	public String getUserPassword() {
		return this.getProperty(IJDBCDriverDefinitionConstants.PASSWORD_PROP_ID);
	}

	public String getDriverDefinitionID() {
		return this.getProperty(DRIVER_DEFINITION_PROP_ID);
	}

	public String getDriverJarList() {
		return DriverManager.getInstance().getDriverInstanceByID(this.getDriverDefinitionID()).getJarList();
	}

	public String getDriverName() {
		return DriverManager.getInstance().getDriverInstanceByID(this.getDriverDefinitionID()).getName();
	}

	// ***** connection
	public boolean isActive() {
		return this.isConnected() || this.isWorkingOffline();
	}

	public boolean isInactive() {
		return ! this.isActive();
	}

	public boolean isConnected() {
		return this.dtpManagedConnection.isConnected()
				&& ! this.dtpManagedConnection.isWorkingOffline();
	}

	public boolean isDisconnected() {
		return ! this.isConnected();
	}

	public void connect() {
		if (this.isDisconnected()) {
			this.checkStatus(this.dtpConnectionProfile.connect());
		}
	}
	
	public void disconnect() {
		this.checkStatus(this.dtpConnectionProfile.disconnect());
	}

	// ***** off-line support
	public boolean isWorkingOffline() {
		return this.dtpManagedConnection.isWorkingOffline();
	}

	public boolean supportsWorkOfflineMode() {
		return this.dtpConnectionProfile.supportsWorkOfflineMode();
	}

	public IStatus saveWorkOfflineData() {
		return this.dtpConnectionProfile.saveWorkOfflineData();
	}
	
	public boolean canWorkOffline() {
		return this.dtpConnectionProfile.canWorkOffline();
	}

	public IStatus workOffline() {
		return this.dtpConnectionProfile.workOffline();
	}
	
	// ***** listeners
	public synchronized void addConnectionListener(ConnectionListener listener) {
		if (this.hasNoListeners()) {  // first listener added
			this.startListening();
		}
		this.connectionListener.addConnectionListener(listener);
	}

	private void startListening() {
		this.dtpManagedConnection.addConnectionListener(this.connectionListener);
		if (this.database != null) {  // don't trigger database creation
			if (this.isConnected()) {  // DTP does not change when off-line
				this.database.startListening();
			}
		}
	}

	public synchronized void removeConnectionListener(ConnectionListener listener) {
		this.connectionListener.removeConnectionListener(listener);
		if (this.hasNoListeners()) {  // last listener removed
			this.stopListening();
		}
	}

	private void stopListening() {
		if (this.database != null) {  // don't trigger database creation
			if (this.isConnected()) {  // DTP does not change when off-line
				this.database.stopListening();
			}
		}
		this.dtpManagedConnection.removeConnectionListener(this.connectionListener);
	}

	boolean hasNoListeners() {
		return this.connectionListener.hasNoListeners();
	}

	boolean hasAnyListeners() {
		return this.connectionListener.hasAnyListeners();
	}


	// ********** internal methods **********

	private void checkStatus(IStatus status) {
		if (status.isOK()) {
			return;
		}
		if (status.isMultiStatus()) {
			for (IStatus child : status.getChildren()) {
				this.checkStatus(child);  // recurse, looking for the first error
			}
		}
		throw new RuntimeException(status.getMessage(), status.getException());
	}

	private DTPDatabaseWrapper buildDatabase() {
		if (this.isInactive()) {
			return null;
		}

		if (this.isWorkingOffline()) {
			ConnectionInfo connectionInfo = (ConnectionInfo) this.dtpManagedConnection.getConnection().getRawConnection();
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

	synchronized void clearDatabase() {
		if (this.database != null) {
			if (this.isConnected()) {  // DTP does not change when off-line
				this.database.stopListening();
			}
			this.database = null;
		}
	}

	/**
	 * This is called whenever we need to find a component by identifier
	 * (e.g. Table.getColumnForIdentifier(String)). We channel all the calls to here
	 * and then we delegate to the JPA platform-supplied "database finder".
	 */
	<T extends DatabaseObject> T selectDatabaseObjectForIdentifier(T[] databaseObjects, String identifier) {
		return this.finder.selectDatabaseObjectForIdentifier(databaseObjects, identifier, this.databaseFinderCallback);
	}

	/**
	 * The default "database finder" calls back to here so we can delegate to
	 * the database, which contains all the information necessary to properly
	 * match identifiers.
	 */
	<T extends DatabaseObject> T selectDatabaseObjectForIdentifier_(T[] databaseObjects, String identifier) {
		// the database should not be null here - call its internal method
		return this.database.selectDatabaseObjectForIdentifier_(databaseObjects, identifier);
	}

	void databaseChanged(DTPDatabaseWrapper db) {
		this.connectionListener.databaseChanged(db);
	}

	void catalogChanged(DTPCatalogWrapper catalog) {
		this.connectionListener.catalogChanged(catalog);
	}

	void schemaChanged(DTPSchemaWrapper schema) {
		this.connectionListener.schemaChanged(schema);
	}

	void sequenceChanged(DTPSequenceWrapper sequence) {
		this.connectionListener.sequenceChanged(sequence);
	}

	void tableChanged(DTPTableWrapper table) {
		this.connectionListener.tableChanged(table);
	}

	void columnChanged(DTPColumnWrapper column) {
		this.connectionListener.columnChanged(column);
	}

	void foreignKeyChanged(DTPForeignKeyWrapper foreignKey) {
		this.connectionListener.foreignKeyChanged(foreignKey);
	}

	private String getProperty(String propertyName) {
		return this.dtpConnectionProfile.getBaseProperties().getProperty(propertyName);
	}

	private boolean nameIsKeyword(String name) {
		return name.equalsIgnoreCase("as");  //$NON-NLS-1$
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
	class LocalConnectionListener implements IManagedConnectionOfflineListener {
		private ListenerList<ConnectionListener> listenerList = new ListenerList<ConnectionListener>(ConnectionListener.class);

		LocalConnectionListener() {
			super();
		}

		void addConnectionListener(ConnectionListener listener) {
			this.listenerList.add(listener);
		}

		void removeConnectionListener(ConnectionListener listener) {
			this.listenerList.remove(listener);
		}

		boolean hasNoListeners() {
			return this.listenerList.isEmpty();
		}

		boolean hasAnyListeners() {
			return ! this.listenerList.isEmpty();
		}


		// ********** IManagedConnectionListener implementation **********

		// off-line or inactive => live
		public void opened(ConnectEvent event) {
			// do not build the database here - it is built on-demand
			// forward event to listeners
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.opened(DTPConnectionProfileWrapper.this);
			}
		}

		/**
		 * This method is never called from the base DTP code.
		 * Perhaps DTP extenders call it....
		 * @see ManagedConnection#fireModifiedEvent(Object)
		 *     which is never called...
		 */
		public void modified(ConnectEvent event) {
			// forward event to listeners
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.modified(DTPConnectionProfileWrapper.this);
			}
		}

		public boolean okToClose(ConnectEvent event) {
			// forward event to listeners
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				if ( ! listener.okToClose(DTPConnectionProfileWrapper.this)) {
					return false;
				}
			}
			return true;
		}

		public void aboutToClose(ConnectEvent event) {
			// forward event to listeners
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.aboutToClose(DTPConnectionProfileWrapper.this);
			}
		}

		// live or off-line => inactive
		public void closed(ConnectEvent event) {
			// clear the database
			DTPConnectionProfileWrapper.this.clearDatabase();
			// forward event to listeners
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.closed(DTPConnectionProfileWrapper.this);
			}
		}


		// ********** IManagedConnectionOfflineListener implementation **********

		// live => off-line
		public boolean okToDetach(ConnectEvent event) {
			// convert the event to an "ok to close" event;
			// we are "closing" the live connection
			return this.okToClose(event);
		}
		
		// live => off-line
		public void aboutToDetach(ConnectEvent event) {
			// convert the event to a "close" event;
			// we are "closing" the live connection
			this.closed(event);
		}

		// inactive or live => off-line
		public void workingOffline(ConnectEvent event) {
			// convert the event to an "open" event;
			// we are "opening" the off-line connection
			this.opened(event);
		}

		// off-line => live
		public void aboutToAttach(ConnectEvent event) {
			// convert the event to an "close" event;
			// we are "closing" the off-line connection
			this.closed(event);
		}


		// ********** internal methods **********

		void databaseChanged(DTPDatabaseWrapper db) {
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.databaseChanged(DTPConnectionProfileWrapper.this, db);
			}
		}

		void catalogChanged(DTPCatalogWrapper catalog) {
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.catalogChanged(DTPConnectionProfileWrapper.this, catalog);
			}
		}

		void schemaChanged(DTPSchemaWrapper schema) {
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.schemaChanged(DTPConnectionProfileWrapper.this, schema);
			}
		}

		void sequenceChanged(DTPSequenceWrapper sequence) {
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.sequenceChanged(DTPConnectionProfileWrapper.this, sequence);
			}
		}

		void tableChanged(DTPTableWrapper table) {
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.tableChanged(DTPConnectionProfileWrapper.this, table);
			}
		}

		void columnChanged(DTPColumnWrapper column) {
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.columnChanged(DTPConnectionProfileWrapper.this, column);
			}
		}

		void foreignKeyChanged(DTPForeignKeyWrapper foreignKey) {
			for (ConnectionListener listener : this.listenerList.getListeners()) {
				listener.foreignKeyChanged(DTPConnectionProfileWrapper.this, foreignKey);
			}
		}

	}


	// ********** default DatabaseFinder **********

	class DatabaseFinderCallback implements DatabaseFinder.DefaultCallback {
		public <T extends DatabaseObject> T selectDatabaseObjectForIdentifier(T[] databaseObjects, String identifier) {
			// call back to the internal method
			return DTPConnectionProfileWrapper.this.selectDatabaseObjectForIdentifier_(databaseObjects, identifier);
		}
	}

}
