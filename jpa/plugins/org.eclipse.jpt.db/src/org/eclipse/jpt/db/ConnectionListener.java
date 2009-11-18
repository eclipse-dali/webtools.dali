/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db;


/**
 * A ConnectionListener is notified of any changes to a connection.
 * 
 * @see org.eclipse.datatools.connectivity.IManagedConnectionListener
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ConnectionListener {

	public void opened(ConnectionProfile profile);
	public void modified(ConnectionProfile profile);
	public boolean okToClose(ConnectionProfile profile);
	public void aboutToClose(ConnectionProfile profile);
	public void closed(ConnectionProfile profile);

	public void databaseChanged(ConnectionProfile profile, Database database);
	public void catalogChanged(ConnectionProfile profile, Catalog catalog);
	public void schemaChanged(ConnectionProfile profile, Schema schema);
	public void sequenceChanged(ConnectionProfile profile, Sequence sequence);
	public void tableChanged(ConnectionProfile profile, Table table);
	public void columnChanged(ConnectionProfile profile, Column column);
	public void foreignKeyChanged(ConnectionProfile profile, ForeignKey foreignKey);

}
