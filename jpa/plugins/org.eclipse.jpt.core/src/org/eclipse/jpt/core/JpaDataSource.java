/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.Iterator;

import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.DatabaseObject;
import org.eclipse.jpt.db.Schema;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaDataSource extends JpaNode {

	/**
	 * Return the data source's connection profile name.
	 * The connection profile is looked up based on this setting.
	 */
	String getConnectionProfileName();
	
	/**
	 * Set the data source's connection profile name.
	 * The connection profile is looked up based on this setting.
	 */
	void setConnectionProfileName(String connectionProfileName);
	
	/**
	 * ID string used when connectionProfileName property is changed
	 * @see org.eclipse.jpt.utility.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	public static final String CONNECTION_PROFILE_NAME_PROPERTY = "connectionProfileName"; //$NON-NLS-1$
	
	/**
	 * The data source's connection profile should never be null.
	 * If we do not have a connection, return a "null" connection profile.
	 */
	ConnectionProfile getConnectionProfile();
	
	/**
	 * ID string used when connectionProfile property is changed
	 * @see org.eclipse.jpt.utility.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.utility.model.listener.PropertyChangeListener)
	 */
	public static final String CONNECTION_PROFILE_PROPERTY = "connectionProfile"; //$NON-NLS-1$
	
	boolean connectionProfileIsActive();
	
	Database getDatabase();

	Iterator<String> catalogNames();

	Catalog getCatalogNamed(String name);
	
	Catalog getDefaultCatalog();
	
	Iterator<String> schemaNames();

	Schema getSchemaNamed(String name);

	Schema getDefaultSchema();

	<T extends DatabaseObject> T getDatabaseObjectNamed(T[] databaseObjects, String name);

	void dispose();

}
