/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

import java.sql.Connection;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.datatools.connectivity.drivers.jdbc.IJDBCDriverDefinitionConstants;

/**
 * Database connection profile
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ConnectionProfile
	extends DatabaseObject
{
	// ********** properties **********

	/**
	 * Return the connection profile's database.
	 * Return <code>null</code> if the connection profile is inactive.
	 */
	Database getDatabase();

	/**
	 * Return ID of the provider managing the DTP profile.
	 */
	String getProviderID();

	/**
	 * Return the connection profile's static ID.
	 */
	String getInstanceID();

	/**
	 * Return the default database name.
	 */
	String getDatabaseName();

	/**
	 * Return the database product name.
	 */
	String getDatabaseProduct();

	/**
	 * Return the database vendor.
	 */
	String getDatabaseVendor();

	/**
	 * Return the database version.
	 */
	String getDatabaseVersion();

	/**
	 * Return the driver class name.
	 */
	String getDriverClassName();

	/**
	 * Return the default connection URL.
	 */
	String getURL();

	/**
	 * Return the default user name.
	 */
	String getUserName();

	/**
	 * Return the default user password.
	 */
	String getUserPassword();

	/**
	 * Return the ID of the associated Driver definition.
	 */
	String getDriverDefinitionID();

	/**
	 * Return the jar list for the associated Driver as a 
	 * comma-delimited string.
	 */
	String getDriverJarList();

	/**
	 * Return the name of the associated Driver definition.
	 */
	String getDriverName();


	// ********** identifiers **********

	/**
	 * Return whether all identifiers are to be treated as though they were
	 * delimited. This is determined by the client-supplied database
	 * identifier adapter.
	 */
	boolean treatIdentifiersAsDelimited();


	// ********** connection **********

	/**
	 * Return whether the profile is either connected to a live database
	 * session or working off-line (i.e. it has access to meta-data).
	 * @see #isConnected()
	 * @see #isWorkingOffline()
	 */
	boolean isActive();

	/**
	 * Return whether the profile is neither connected to a live database
	 * session nor working off-line (i.e. it has access to meta-data).
	 * @see #isActive()
	 */
	boolean isInactive();

	/**
	 * Return whether the profile is connected to a live database session
	 * (i.e. the meta-data comes from the database), as opposed to working
	 * off-line.
	 * @see #isActive()
	 */
	boolean isConnected();

	/**
	 * Return whether the profile is not connected to a live database session
	 * (i.e. the meta-data comes from the database), as opposed to working
	 * off-line.
	 * @see #isConnected()
	 */
	boolean isDisconnected();

	/**
	 * Connect to the database.
	 * @see #disconnect()
	 */
	void connect();

	/**
	 * Disconnect from the database.
	 * @see #connect()
	 */
	void disconnect();

	/**
	 * Return the JDBC connection.
	 */
	Connection getJDBCConnection();


	// ********** off-line support **********

	/**
	 * Return whether the profile is working off-line (i.e. the meta-data
	 * comes from a local cache), as opposed to connected to a live
	 * database session.
	 * @see #isActive()
	 */
	boolean isWorkingOffline();

	/**
	 * Return whether the connection factories associated with the
	 * connection profile's provider support working offline.
	 */
	boolean supportsWorkOfflineMode();

	/**
	 * Save the state of the connection profile for working in an offline mode.
	 * If the connection profile does not support working in an offline mode, no
	 * exception is thrown and the method will return immediately.
	 */
	IStatus saveWorkOfflineData();

	/**
	 * Return whether the connection profile supports working offline and data
	 * has been saved for working offline.
	 */
	boolean canWorkOffline();

	/**
	 * Begin working off-line.
	 */
	IStatus workOffline();


	// ********** listeners **********

	/**
	 * Add the specified connection listener to the connection profile.
	 */
	void addConnectionListener(ConnectionListener listener);

	/**
	 * Remove the specified connection listener from the connection profile.
	 */
	void removeConnectionListener(ConnectionListener listener);


	// ********** constants **********

	String CONNECTION_PROFILE_TYPE = "org.eclipse.datatools.connectivity.db.generic.connectionProfile";  //$NON-NLS-1$
	String DRIVER_DEFINITION_PROP_ID = "org.eclipse.datatools.connectivity.driverDefinitionID";  //$NON-NLS-1$
	String DRIVER_DEFINITION_TYPE_PROP_ID = "org.eclipse.datatools.connectivity.drivers.defnType";  //$NON-NLS-1$
	String DRIVER_JAR_LIST_PROP_ID = "jarList";  //$NON-NLS-1$
	String DATABASE_SAVE_PWD_PROP_ID = IJDBCDriverDefinitionConstants.PROP_PREFIX + "savePWD";  //$NON-NLS-1$
}
