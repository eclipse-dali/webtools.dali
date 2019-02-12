/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.jdt.core.IClasspathContainer;

/**
 * Database connection profile factory
 * <p>
 * To retrieve the connection profile factory corresponding to an Eclipse workspace:
 * <pre>
 * IWorkspace workspace = ResourcesPlugin.getWorkspace();
 * ConnectionProfileFactory factory = (ConnectionProfileFactory) workspace.getAdapter(ConnectionProfileFactory.class);
 * </pre>
 * <p>
 * See <code>org.eclipse.jpt.jpa.db/plugin.xml:org.eclipse.core.runtime.adapters</code>.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ConnectionProfileFactory {

	/**
	 * Return the factory's workspace.
	 */
	IWorkspace getWorkspace();

	/**
	 * Return the names of the DTP connection profiles the factory can wrap with
	 * new connection profiles.
	 */
	Iterable<String> getConnectionProfileNames();

	/**
	 * Build and return a connection profile that wraps the DTP connection
	 * profile with the specified name.
	 * Return <code>null</code> if there is no DTP connection profile with the
	 * specified name.
	 * Use the specified database identifier adapter to allow clients to control how
	 * database identifiers are handled.
	 */
	ConnectionProfile buildConnectionProfile(String name, DatabaseIdentifierAdapter adapter);

	/**
	 * Build and return a connection profile that wraps the DTP connection
	 * profile with the specified name.
	 * Return <code>null</code> if there is no DTP connection profile with the
	 * specified name.
	 * <p>
	 * Clients should use this method when a JPA platform is unavailable
	 * (e.g. during project creation). The returned connection profile will
	 * use the default database identifier adapter.
	 */
	ConnectionProfile buildConnectionProfile(String name);

	/**
	 * Build a Java classpath container for the specified driver.
	 * @see ConnectionProfile#getDriverName()
	 */
	IClasspathContainer buildDriverClasspathContainer(String driverName);

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
