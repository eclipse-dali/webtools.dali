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
	 * Given the name of a Java member and the database object to which it is
	 * mapped, build and return a string to be used as the value for the member's
	 * database object annotation's 'name' element. Return null if the member
	 * maps to the database object by default.
	 */
	String getAnnotationIdentifier(String javaIdentifier);

	/**
	 * Return a string to be used as the value for the member's
	 * database object annotation's 'name' element.
	 */
	String getAnnotationIdentifier();

	/**
	 * Return the database object's connection profile.
	 */
	ConnectionProfile getConnectionProfile();

}
