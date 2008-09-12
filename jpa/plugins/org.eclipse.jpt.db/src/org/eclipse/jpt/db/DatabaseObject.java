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

/**
 * Common behavior to all database objects
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface DatabaseObject {

	/**
	 * Return the database object's name.
	 */
	String getName();

	/**
	 * Return the database object's "identifier", which is the object's name
	 * modified so it can be used in an SQL statement (e.g. if the name contains
	 * special characters or is mixed case, it will be delimited, typically by
	 * double-quotes).
	 * Return null if the database object's identifier matches the specified
	 * "default name".
	 */
	String getIdentifier(String defaultName);

	/**
	 * Return the database object's "identifier", which is the object's name
	 * modified so it can be used in an SQL statement (e.g. if the name contains
	 * special characters or is mixed case, it will be delimited, typically by
	 * double-quotes).
	 */
	String getIdentifier();

	/**
	 * Return the database object's database.
	 */
	Database getDatabase();

	/**
	 * Return the database object's connection profile.
	 */
	ConnectionProfile getConnectionProfile();

}
