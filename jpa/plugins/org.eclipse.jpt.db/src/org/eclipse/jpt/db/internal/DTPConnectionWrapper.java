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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.datatools.connectivity.ConnectEvent;
import org.eclipse.datatools.connectivity.IManagedConnection;
import org.eclipse.datatools.connectivity.IManagedConnectionOfflineListener;

/**
 *  Wraps to two DTP connections (1 for live connection & 1 for offline).
 *  The connections are not cached any more, but obtained each time from the ConnectionProfile.
 */
final class DTPConnectionWrapper extends Connection {
	
	final private DTPConnectionProfileWrapper profile;
	private LocalConnectionListener connectionListener;
	
	// ********** constructors **********

	DTPConnectionWrapper( DTPConnectionProfileWrapper profile) {
		super();
		this.profile = profile;
		this.initialize();
	}

	// ********** behavior **********
	
	protected void initialize() {
		this.connectionListener = new LocalConnectionListener();
		this.getDTPConnection().addConnectionListener( this.connectionListener);
		this.getDTPOfflineConnection().addConnectionListener( this.connectionListener);
	}
	
	@Override
	protected void dispose() {
		this.getDTPConnection().removeConnectionListener( this.connectionListener);
		this.getDTPOfflineConnection().removeConnectionListener( this.connectionListener);
	}

	@Override
	void databaseChanged( Database database, int eventType) {
		
		this.connectionListener.databaseChanged( database, eventType);
	}
	
	@Override
	void schemaChanged( Schema schema, Database database, int eventType) {
		
		this.connectionListener.schemaChanged( schema, database, eventType);
	}
		
	@Override
	void tableChanged( Table table, Schema schema, Database database, int eventType) {
		
		this.connectionListener.tableChanged( table, schema, database, eventType);
	}

	// ********** queries **********

	private IManagedConnection getDTPConnection() {

		return this.profile.getDTPConnection();
	}

	private IManagedConnection getDTPOfflineConnection() {

		return this.profile.getDTPOfflineConnection();
	}
	
	@Override
	public String getName() {

		return this.getDTPConnection().getConnection().getConnectionProfile().getName();
	}

	@Override
	String getFactoryId() {
		
		return this.getDTPConnection().getFactoryID();
	}

	@Override
	protected boolean connectionIsOnline() {
		return this.profile.connectionIsOnline();
	}
		
	// ********** listeners **********

	@Override
	void addConnectionListener( ConnectionListener listener) {
		// hook up the specified listener to our intermediate listeners
		this.connectionListener.addConnectionListener( listener);
	}

	@Override
	void removeConnectionListener( ConnectionListener listener) {

		this.connectionListener.removeConnectionListener( listener);
	}
	
	// ********** member classes **********

	/**
	 * This listener translates and forwards IManagedConnectionListener events to ConnectionListeners.
	 */
	private class LocalConnectionListener implements IManagedConnectionOfflineListener {
		private Collection<ConnectionListener> listeners = new ArrayList<ConnectionListener>();

		LocalConnectionListener() {
			super();
		}

		void addConnectionListener( ConnectionListener listener) {
			this.listeners.add( listener);
		}

		void removeConnectionListener( ConnectionListener listener) {
			this.listeners.remove( listener);
		}

		// ********** behavior **********
		
		public void aboutToClose( ConnectEvent event) {
			if( event.getConnection() == DTPConnectionWrapper.this.getDTPConnection()) {
				for (ConnectionListener listener : this.listeners) {
					listener.aboutToClose( DTPConnectionWrapper.this.profile);
				}
			}
		}

		public void aboutToDetach( ConnectEvent event) {
			if( event.getConnection() == DTPConnectionWrapper.this.getDTPOfflineConnection()) {
				for (ConnectionListener listener : this.listeners) {
					listener.aboutToClose( DTPConnectionWrapper.this.profile);
				}
			}
		}

		public void closed( ConnectEvent event) {
			// There is no DETACHED event, therefore closed is sent twice (i.e. by both connections)
			for (ConnectionListener listener : this.listeners) {
				listener.closed( DTPConnectionWrapper.this.profile);
			}
		}

		public boolean okToClose( ConnectEvent event) {
			if( event.getConnection() == DTPConnectionWrapper.this.getDTPConnection()) {
				for (ConnectionListener listener : this.listeners) {
					if( !listener.okToClose( DTPConnectionWrapper.this.profile)) {
						return false;
					}
				}
			}
			return true;
		}

		public boolean okToDetach( ConnectEvent event) {
			if( event.getConnection() == DTPConnectionWrapper.this.getDTPOfflineConnection()) {
				for (ConnectionListener listener : this.listeners) {
					if( !listener.okToClose( DTPConnectionWrapper.this.profile)) {
						return false;
					}
				}
			}
			return true;
		}
		
		public void opened( ConnectEvent event) {
			if( event.getConnection() == DTPConnectionWrapper.this.getDTPConnection()) {
				for (ConnectionListener listener : this.listeners) {
					listener.opened( DTPConnectionWrapper.this.profile);
				}
			}
		}

		public void workingOffline( ConnectEvent event) {
			for (ConnectionListener listener : this.listeners) {
				listener.opened( DTPConnectionWrapper.this.profile);
			}
		}

		public void aboutToAttach( ConnectEvent event) {
			// not interested to this event.
			return;
		}

		public void modified( ConnectEvent event) {
			for (ConnectionListener listener : this.listeners) {
				listener.modified( DTPConnectionWrapper.this.profile);
			}
		}
		
		@SuppressWarnings("unused")
		void databaseChanged( Database database, int eventType) {
			for (ConnectionListener listener : this.listeners) {
				listener.databaseChanged( DTPConnectionWrapper.this.profile, database);
			}
		}

		@SuppressWarnings("unused")
		void schemaChanged( Schema schema, Database database, int eventType) {
			for (ConnectionListener listener : this.listeners) {
				listener.schemaChanged( DTPConnectionWrapper.this.profile, schema);
			}
		}

		@SuppressWarnings("unused")
		void tableChanged( Table table, Schema schema, Database database, int eventType) {
			for (ConnectionListener listener : this.listeners) {
				listener.tableChanged( DTPConnectionWrapper.this.profile, table);
			}
		}
	}

}
