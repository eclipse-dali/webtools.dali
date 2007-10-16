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

import java.text.Collator;
import java.util.NoSuchElementException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;

/**
 *  ConnectionProfile wrapper base class.
 */
public abstract class ConnectionProfile extends DTPWrapper implements Comparable<ConnectionProfile> {
	
	private Database database; // Lazy initialized
	private String catalogName;  // Catalog used for this profile
	
	private ConnectionListener connectionListener;
	
	private ConnectionProfileRepository profileRepository;
	
	// ********** constructors **********

	static ConnectionProfile createProfile( ConnectionProfileRepository profileRepository, org.eclipse.datatools.connectivity.IConnectionProfile dtpProfile) {
		return ( dtpProfile == null) ? NullConnectionProfile.instance() : new DTPConnectionProfileWrapper( profileRepository, dtpProfile);
	}

	ConnectionProfile( ConnectionProfileRepository profileRepository) {
		super();
		this.profileRepository = profileRepository;

		this.connectionListener = buildConnectionListener();
		this.catalogName = "";
	}
	
	// ********** behavior **********

	public abstract void connect();
	
	public abstract void disconnect();
	
	protected abstract Database buildDatabase();
	
	abstract void databaseChanged( Database db, int eventType);
	
	abstract void catalogChanged( Catalog catalog, Database db, int eventType);
	
	abstract void schemaChanged( Schema schema, Database db, int eventType);
	
	abstract void tableChanged( Table table, Schema schema, Database db, int eventType);

	protected void refreshDatabase() {
		this.disposeDatabase();
    }
    
	@Override
	protected void dispose() {
		this.disengageConnectionListener();
		
		this.disposeDatabase();
	}
	
	private void disposeDatabase() {
		if( this.database != null) {
			this.getDatabase().dispose();
			this.database = null;
		}
	}
	
	/**
	 * Saves the state of the connection profile for working in an offline mode.
	 * If the connection profile does not support working in an offline mode, no
	 * exception is thrown and the method will return immediately.
	 */
	public abstract IStatus saveWorkOfflineData();
	
	public abstract IStatus workOffline();
	
	abstract boolean wraps( org.eclipse.datatools.connectivity.IConnectionProfile dtpProfile);
	

	// ********** queries **********
	
	public Database getDatabase() {
		
		if( this.database == null) {
			this.database = this.buildDatabase();
			this.setDefaultCatalogName();
		}
		return this.database;
	}
	
	public abstract String getDatabaseName();

	public abstract String getDatabaseProduct();
	
	public abstract String getDatabaseVendor();
	
	public abstract String getDatabaseVersion();
	
	public abstract String getDriverClass();
	
	public abstract String getUrl();
	
	public abstract String getUserName();
	
	public abstract String getUserPassword();
	
	public abstract String getDefaultSchema();
	
	public abstract String getInstanceId();

	public abstract String getProviderId();

	public abstract String getDriverDefinitionId();
	
	public abstract String getDriverJarList();
	
	public abstract boolean isConnected();
	
	public abstract boolean isWorkingOffline();
	
	/**
	 * @return true if connection factories associated with this
	 *				connection profile's provider support working offline.
	 */
	public abstract boolean supportsWorkOfflineMode();
	
	/**
	 * @return true if this connection profile supports working offline and data
	 *				has been saved for working offline.
	 */
	public abstract boolean canWorkOffline();

	@Override
	protected boolean connectionIsOnline() {

		return this.isConnected() && ( ! this.isWorkingOffline());
	}
	
	public boolean isNull() {
		return true;
	}
	
	ConnectionProfileRepository getProfileRepository() {
		return this.profileRepository;
	}
	
	public String getCatalogName() {
		return this.catalogName;
	}
	
	/**
	 * Set the default catalog name for this profile.
	 */
	public void setDefaultCatalogName() {
		this.setCatalogName( this.database.getDefaultCatalogName());
	}

	/**
	 * Can only set the catalog to use for this profile, when the database supports catalogs.
	 */
	public void setCatalogName( String catalogName) {
		if( this.catalogName == catalogName) {
			return;
		}
		if( this.database.supportsCatalogs()) {
			String name = ( catalogName != null) ? catalogName : this.database.getDefaultCatalogName();
			
			Catalog catalog = this.database.catalogNamed( name);
			if( catalog == null) {
				throw new NoSuchElementException();
			}
			this.catalogName = name;
			this.database.refresh();
			this.catalogChanged( catalog, this.database, ICatalogObjectListener.EventTypeEnumeration.ELEMENT_REFRESH);
		}
		else {
			this.catalogName = "";
			this.database.refresh();
		}
	}
	
	// ********** listeners **********

	abstract public void addProfileListener( ProfileListener listener);

	abstract public void removeProfileListener( ProfileListener listener);

	abstract public void addConnectionListener( ConnectionListener listener);

	abstract public void removeConnectionListener( ConnectionListener listener);

    private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {

			public void aboutToClose(ConnectionProfile profile) {
				// not interested to this event.
			}

			public void closed(ConnectionProfile profile) {
				ConnectionProfile.this.refreshDatabase();
			}

			public void modified(ConnectionProfile profile) {
				// not interested to this event.
				return;
			}

			public boolean okToClose(ConnectionProfile profile) {
				// not interested to this event.
				return true;
			}

			public void opened(ConnectionProfile profile) {
				ConnectionProfile.this.refreshDatabase();
			}

			public void databaseChanged(ConnectionProfile profile, final Database db) {
				// not interested to this event.
				return;
			}
			
			public void schemaChanged(ConnectionProfile profile, final Schema schema) {
				// not interested to this event.
				return;
			}

			public void tableChanged(ConnectionProfile profile, final Table table) {
				// not interested to this event.
				return;
			}
		};
    }
    
	protected void disengageConnectionListener() {
		this.removeConnectionListener();
	}

	protected void engageConnectionListener() {
		this.addConnectionListener();
	}

	private void addConnectionListener() {
		this.addConnectionListener( this.connectionListener);
	}
	
	private void removeConnectionListener() {
		this.removeConnectionListener( this.connectionListener);
	}
	
	// ********** Comparable implementation **********

	public int compareTo( ConnectionProfile connectionProfile) {
		return Collator.getInstance().compare( this.getName(), connectionProfile.getName());
	}

}
