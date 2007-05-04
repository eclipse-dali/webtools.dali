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


/**
 * ConnectionListener integrate th DTP IManagedConnectionListener listener.
 * This class purpose is to decouple from the DTP listeners by accepting wrappers as parameter.
 * 
 * @see org.eclipse.datatools.connectivity.IManagedConnectionListener
 */
public interface ConnectionListener {
    
    public void opened( Connection connection);
    public void modified( Connection connection);
    public boolean okToClose( Connection connection);
    public void aboutToClose( Connection connection);
    public void closed( Connection connection);

    public void databaseChanged( Connection connection, Database database);
    public void schemaChanged( Connection connection, Schema schema);
    public void tableChanged( Connection connection, Table table);

}
