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
    
    public void opened( ConnectionProfile profile);
    public void modified( ConnectionProfile profile);
    public boolean okToClose( ConnectionProfile profile);
    public void aboutToClose( ConnectionProfile profile);
    public void closed( ConnectionProfile profile);

    public void databaseChanged( ConnectionProfile profile, Database database);
    public void schemaChanged( ConnectionProfile profile, Schema schema);
    public void tableChanged( ConnectionProfile profile, Table table);

}
