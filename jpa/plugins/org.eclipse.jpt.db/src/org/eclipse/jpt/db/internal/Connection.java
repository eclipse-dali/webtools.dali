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
	static Connection createConnection( DTPConnectionProfileWrapper profile) {
		return ( profile == null) ? NullConnection.instance() : new DTPConnectionWrapper( profile);
	}

	Connection() {
		super();
	}

	// ********** listeners **********

	abstract void addConnectionListener( ConnectionListener listener);

	abstract void removeConnectionListener( ConnectionListener listener);


	// ********** behavior **********

	abstract void databaseChanged( Database database, int eventType);

	abstract void schemaChanged( Schema schema, Database database, int eventType);
	
	abstract void tableChanged( Table table, Schema schema, Database database, int eventType);
	

	// ********** queries **********
	
	abstract String getFactoryId();


	// ********** Comparable implementation **********

	public int compareTo(Connection connection) {
		return Collator.getInstance().compare( this.getName(), connection.getName());
	}

}
