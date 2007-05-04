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
import org.eclipse.datatools.connectivity.IManagedConnectionListener;

/**
 *  Wrap a DTP Connection
 */
public final class DTPConnectionWrapper extends Connection {
	
	final private org.eclipse.datatools.connectivity.IManagedConnection dtpConnection;
	private LocalConnectionListener connectionListener;
	
	// ********** constructors **********

	DTPConnectionWrapper( org.eclipse.datatools.connectivity.IManagedConnection dtpConnection) {
		super();
		this.dtpConnection = dtpConnection;
		this.initialize();
	}

	// ********** behavior **********
	
	protected void initialize() {
		this.connectionListener = new LocalConnectionListener();
		this.dtpConnection.addConnectionListener( this.connectionListener);
	}
	
	@Override
	protected void dispose() {
		this.dtpConnection.removeConnectionListener( this.connectionListener);
	}

	@Override
	public String getName() {

		return this.dtpConnection.getConnection().getConnectionProfile().getName();
	}
	
	@Override
	public boolean isConnected() {

		return this.dtpConnection.isConnected();
	}

	@Override
	public String getFactoryId() {
		
		return this.dtpConnection.getFactoryID();
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
		
	// ********** listeners **********

	@Override
	public void addConnectionListener( ConnectionListener listener) {
		// hook up the specified listener to our intermediate listeners
		this.connectionListener.addConnectionListener( listener);
	}

	@Override
	public void removeConnectionListener( ConnectionListener listener) {

		this.connectionListener.removeConnectionListener( listener);
	}
	
	// ********** member classes **********

	/**
	 * This listener translates and forwards IManagedConnectionListener events to ConnectionListeners.
	 */
	private class LocalConnectionListener implements IManagedConnectionListener {
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
			for (ConnectionListener listener : this.listeners) {
				listener.aboutToClose( DTPConnectionWrapper.this);
			}
		}

		public void closed( ConnectEvent event) {
			for (ConnectionListener listener : this.listeners) {
				listener.closed( DTPConnectionWrapper.this);
			}
		}

		public void modified( ConnectEvent event) {
			for (ConnectionListener listener : this.listeners) {
				listener.modified( DTPConnectionWrapper.this);
			}
		}

		public boolean okToClose( ConnectEvent event) {
			for (ConnectionListener listener : this.listeners) {
				if( !listener.okToClose( DTPConnectionWrapper.this)) {
					return false;
				}
			}
			return true;
		}
		
		public void opened( ConnectEvent event) {
			for (ConnectionListener listener : this.listeners) {
				listener.opened( DTPConnectionWrapper.this);
			}
		}
		
		void databaseChanged( Database database, int eventType) {
			for (ConnectionListener listener : this.listeners) {
				listener.databaseChanged( DTPConnectionWrapper.this, database);
			}
		}
		
		void schemaChanged( Schema schema, Database database, int eventType) {
			for (ConnectionListener listener : this.listeners) {
				listener.schemaChanged( DTPConnectionWrapper.this, schema);
			}
		}
		
		void tableChanged( Table table, Schema schema, Database database, int eventType) {
			for (ConnectionListener listener : this.listeners) {
				listener.tableChanged( DTPConnectionWrapper.this, table);
			}
		}
	}

}
