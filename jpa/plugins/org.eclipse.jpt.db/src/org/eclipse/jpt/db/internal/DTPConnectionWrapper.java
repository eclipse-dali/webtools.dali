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

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
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
	
	protected void dispose() {
		this.dtpConnection.removeConnectionListener( this.connectionListener);
	}

	public String getName() {

		return this.dtpConnection.getConnection().getConnectionProfile().getName();
	}
	
	public boolean isConnected() {

		return this.dtpConnection.isConnected();
	}

	public String getFactoryId() {
		
		return this.dtpConnection.getFactoryID();
	}

	void databaseChanged( Database database, int eventType) {
		
		this.connectionListener.databaseChanged( database, eventType);
	}
	
	void schemaChanged( Schema schema, Database database, int eventType) {
		
		this.connectionListener.schemaChanged( schema, database, eventType);
	}
		
	void tableChanged( Table table, Schema schema, Database database, int eventType) {
		
		this.connectionListener.tableChanged( table, schema, database, eventType);
	}
		
	// ********** listeners **********

	public void addConnectionListener( ConnectionListener listener) {
		// hook up the specified listener to our intermediate listeners
		this.connectionListener.addConnectionListener( listener);
	}

	public void removeConnectionListener( ConnectionListener listener) {

		this.connectionListener.removeConnectionListener( listener);
	}
	
	// ********** member classes **********

	/**
	 * This listener translates and forwards IManagedConnectionListener events to ConnectionListeners.
	 */
	private class LocalConnectionListener implements IManagedConnectionListener {
		private Collection listeners = new Vector();

		void addConnectionListener( ConnectionListener listener) {
			this.listeners.add( listener);
		}

		void removeConnectionListener( ConnectionListener listener) {
			this.listeners.remove( listener);
		}

		// ********** behavior **********
		
		public void aboutToClose( ConnectEvent event) {
			for( Iterator i = listeners.iterator(); i.hasNext(); ) {
				(( ConnectionListener)i.next()).aboutToClose( DTPConnectionWrapper.this);
			}
		}

		public void closed( ConnectEvent event) {
			for( Iterator i = listeners.iterator(); i.hasNext(); ) {
				(( ConnectionListener)i.next()).closed( DTPConnectionWrapper.this);
			}
		}

		public void modified( ConnectEvent event) {
			for( Iterator i = listeners.iterator(); i.hasNext(); ) {
				(( ConnectionListener)i.next()).modified( DTPConnectionWrapper.this);
			}
		}

		public boolean okToClose( ConnectEvent event) {
			for( Iterator i = listeners.iterator(); i.hasNext(); ) {
				if( !(( ConnectionListener)i.next()).okToClose( DTPConnectionWrapper.this)) {
					return false;
				}
			}
			return true;
		}
		
		public void opened( ConnectEvent event) {
			for( Iterator i = listeners.iterator(); i.hasNext(); ) {
				(( ConnectionListener)i.next()).opened( DTPConnectionWrapper.this);
			}
		}
		
		void databaseChanged( Database database, int eventType) {
			for( Iterator i = listeners.iterator(); i.hasNext(); ) {
				(( ConnectionListener)i.next()).databaseChanged( DTPConnectionWrapper.this, database);
			}
		}
		
		void schemaChanged( Schema schema, Database database, int eventType) {
			for( Iterator i = listeners.iterator(); i.hasNext(); ) {
				(( ConnectionListener)i.next()).schemaChanged( DTPConnectionWrapper.this, schema);
			}
		}
		
		void tableChanged( Table table, Schema schema, Database database, int eventType) {
			for( Iterator i = listeners.iterator(); i.hasNext(); ) {
				(( ConnectionListener)i.next()).tableChanged( DTPConnectionWrapper.this, table);
			}
		}
	}

}
