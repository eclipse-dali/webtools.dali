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


/**
 *  Connection wrapper base class.
 */
public abstract class Connection extends DTPWrapper implements Comparable<Connection> {

	// ********** constructors **********

	/**
	 * Create a wrapper for the given IManagedConnection.
	 */
	static Connection createConnection( org.eclipse.datatools.connectivity.IManagedConnection dtpConnection) {
		return ( dtpConnection == null) ? NullConnection.instance() : new DTPConnectionWrapper( dtpConnection);
	}

	Connection() {
		super();
	}

	// ********** listeners **********

	public abstract void addConnectionListener( ConnectionListener listener);

	public abstract void removeConnectionListener( ConnectionListener listener);


	// ********** behavior **********

	abstract void databaseChanged( Database database, int eventType);

	abstract void schemaChanged( Schema schema, Database database, int eventType);
	
	abstract void tableChanged( Table table, Schema schema, Database database, int eventType);
	

	// ********** queries **********
	
	public abstract boolean isConnected();
	
	public abstract String getFactoryId();

	@Override
	protected boolean connectionIsOnline() {
		return this.isConnected();
	}

	// ********** Comparable implementation **********

	public int compareTo(Connection connection) {
		return Collator.getInstance().compare( this.getName(), connection.getName());
	}

}
