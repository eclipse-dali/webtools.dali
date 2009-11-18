/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.datatools.connectivity.drivers.jdbc.IJDBCDriverDefinitionConstants;

/**
 * Database connection profile
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface ConnectionProfile extends Comparable<ConnectionProfile> {

	/**
	 * Return the connection profile's name.
	 */
	String getName();

	/**
	 * Return the connection profile's database.
	 * Return a "null" database if the connection profile is inactive.
	 */
	Database getDatabase();

	/**
	 * Return the connection profile's "default" catalog.
	 * Return null if the connection profile's database does not support
	 * catalogs.
	 */
	Catalog getDefaultCatalog();

	/**
	 * Return the connection profile's default schema.
	 * In most cases the default schema's name will match the user name.
	 * It may be null.
	 */
	Schema getDefaultSchema();

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
	String getUrl();

	/**
	 * Return the default user name.
	 */
	String getUserName();

	/**
	 * Return the default user password.
	 */
	String getUserPassword();

	/**
	 * Return the driver definition ID.
	 */
	String getDriverDefinitionID();

	/**
	 * Return the jar list for the driver instance as a 
	 * comma-delimited string.
	 */
	String getDriverJarList();

	/**
	 * Return whether the profile is either connected to a live database
	 * session or working off-line (i.e. it has access to meta-data).
	 * @see isConnected()
	 * @see isWorkingOfflin()
	 */
	boolean isActive();

	/**
	 * Return whether the profile is connected to a live database session
	 * (i.e. the meta-data comes from the database), as opposed to working
	 * off-line.
	 * @see #isActive()
	 */
	boolean isConnected();

	/**
	 * Connect to the database.
	 */
	void connect();

	/**
	 * Disconnect from the database.
	 */
	void disconnect();

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

	/**
	 * Return whether the connection profile is a "null" connection profile.
	 */
	boolean isNull();

	/**
	 * Add the specified connection listener to the connection profile.
	 */
	void addConnectionListener(ConnectionListener listener);

	/**
	 * Remove the specified connection listener from the connection profile.
	 */
	void removeConnectionListener(ConnectionListener listener);

	String CONNECTION_PROFILE_TYPE = "org.eclipse.datatools.connectivity.db.generic.connectionProfile";  //$NON-NLS-1$
	String DRIVER_DEFINITION_PROP_ID = "org.eclipse.datatools.connectivity.driverDefinitionID";  //$NON-NLS-1$
	String DRIVER_DEFINITION_TYPE_PROP_ID = "org.eclipse.datatools.connectivity.drivers.defnType";  //$NON-NLS-1$
	String DRIVER_JAR_LIST_PROP_ID = "jarList";  //$NON-NLS-1$
	String DATABASE_SAVE_PWD_PROP_ID = IJDBCDriverDefinitionConstants.PROP_PREFIX + "savePWD";  //$NON-NLS-1$

}
