/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.DatabaseObject;

/**
 * Interface to the connection profile.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface JpaDataSource
	extends JpaNode {

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
	 * @see org.eclipse.jpt.common.utility.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener)
	 */
	String CONNECTION_PROFILE_NAME_PROPERTY = "connectionProfileName"; //$NON-NLS-1$

	/**
	 * The connection profile is null if the connection profile name is invalid.
	 */
	ConnectionProfile getConnectionProfile();

	/**
	 * ID string used when connectionProfile property is changed
	 * @see org.eclipse.jpt.common.utility.model.Model#addPropertyChangeListener(String, org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener)
	 */
	String CONNECTION_PROFILE_PROPERTY = "connectionProfile"; //$NON-NLS-1$

	/**
	 * Return whether the profile is either connected to a live database
	 * session or working off-line (i.e. it has access to meta-data).
	 */
	boolean connectionProfileIsActive();

	/**
	 * If the connection profile is active, return its database.
	 */
	Database getDatabase();

	/**
	 * Select and return the database object with the specified identifier.
	 */
	<T extends DatabaseObject> T selectDatabaseObjectForIdentifier(Iterable<T> databaseObjects, String identifier);

	/**
	 * Dispose the data source.
	 */
	void dispose();

}
