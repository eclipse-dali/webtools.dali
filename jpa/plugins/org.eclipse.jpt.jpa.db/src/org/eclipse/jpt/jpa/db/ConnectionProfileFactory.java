/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

/**
 * Database connection profile factory
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ConnectionProfileFactory {

	/**
	 * Return the names of the DTP connection profiles the factory can wrap with
	 * new connection profiles.
	 */
	Iterable<String> getConnectionProfileNames();

	/**
	 * Build and return a connection profile that wraps the DTP connection
	 * profile with the specified name.
	 * Return null if there is no DTP connection profile with the specified
	 * name.
	 * Use the specified database identifier adapter to allow clients to control how
	 * database identifiers are converted to names and vice versa.
	 */
	ConnectionProfile buildConnectionProfile(String name, DatabaseIdentifierAdapter adapter);

	/**
	 * Build and return a connection profile that wraps the DTP connection
	 * profile with the specified name.
	 * Return null if there is no DTP connection profile with the specified
	 * name.
	 * <p>
	 * Clients should use this method when a JPA platform is unavailable
	 * (e.g. during project creation). The returned connection profile will
	 * use the default conversions for identifiers and names.
	 */
	ConnectionProfile buildConnectionProfile(String name);

	/**
	 * Add a listener that will be notified of changes to the DTP
	 * connection profiles.
	 */
	void addConnectionProfileListener(ConnectionProfileListener listener);

	/**
	 * Remove the specified listener.
	 */
	void removeConnectionProfileListener(ConnectionProfileListener listener);

}
