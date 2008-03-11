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

import java.util.Iterator;

/**
 * Database connection profile repository
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface ConnectionProfileRepository {

	/**
	 * Return the repository's connection profiles.
	 */
	Iterator<ConnectionProfile> connectionProfiles();

	/**
	 * Return the number of connection profiles in the repository.
	 */
	int connectionProfilesSize();

	/**
	 * Return the repository's connection profile names.
	 */
	Iterator<String> connectionProfileNames();

	/**
	 * Return whether the repository contains a connection profile
	 * with the specified name.
	 */
	boolean containsConnectionProfileNamed(String name);

	/**
	 * Return the connection profile with the specified name.
	 * Return a "null" connection profile if the repository does not
	 * have a connection profile with the specified name.
	 */
	ConnectionProfile connectionProfileNamed(String name);

	/**
	 * Add a listener that will be notified of changes to the repository's
	 * connection profiles.
	 */
	void addConnectionProfileListener(ConnectionProfileListener listener);

	/**
	 * Remove the specified listener.
	 */
	void removeConnectionProfileListener(ConnectionProfileListener listener);

}
