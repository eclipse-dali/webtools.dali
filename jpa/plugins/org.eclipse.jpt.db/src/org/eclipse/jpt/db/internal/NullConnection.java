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
 *  NullConnection
 */
public final class NullConnection extends Connection {

	private static NullConnection INSTANCE;

	/**
	 * singleton support
	 */
	static synchronized Connection instance() {
		if( INSTANCE == null) {
			INSTANCE = new NullConnection();
		}
		return INSTANCE;
	}

	private NullConnection() {
		super();
	}

	// ********** behavior **********

	protected void dispose() {
		// do nothing
	}

	public String getName() {
		return ClassTools.shortClassNameForObject( this);
	}

	void databaseChanged( Database database, int eventType) {
		// do nothing
	}
	
	 void schemaChanged( Schema schema, Database database, int eventType) {
		// do nothing
	}
	
	 void tableChanged( Table table, Schema schema, Database database, int eventType) {
		// do nothing
	}
	
	public boolean isConnected() {
		return false;
	}

	public String getFactoryId() {
		return "";
	}

	public void addConnectionListener( ConnectionListener listener) {
		// do nothing
	}

	public void removeConnectionListener( ConnectionListener listener) {
		// do nothing
	}
}
