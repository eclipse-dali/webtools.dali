/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Interface to the connection profile.
 * <p>
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
	extends JpaModel
{
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
	 * Select and return the table with the specified identifier.
	 * We need this unusual method because the JPA spec does not allow columns
	 * or join columns to specify a schema and/or catalog (which is only a
	 * problem when an entity has two tables from different schemata but with
	 * the same name).
	 * 
	 * @see org.eclipse.jpt.jpa.core.context.TypeMapping#resolveDbTable(String)
	 */
	Table selectTableForIdentifier(Iterable<Table> tables, String identifier);

	/**
	 * Dispose the data source.
	 */
	void dispose();
}
