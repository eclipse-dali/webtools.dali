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
 * Schema "container" (i.e. Database or Catalog)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface SchemaContainer
	extends DatabaseObject
{

	/**
	 * Return the container's schemata.
	 */
	Iterator<Schema> schemata();

	/**
	 * Return the number of schemata in the container.
	 */
	int schemataSize();

	/**
	 * Return the schema with specified name. The name must be an exact match
	 * of the schema's name.
	 * @see #schemaNames()
	 * @see #getSchemaForIdentifier(String)
	 */
	Schema getSchemaNamed(String name);

	/**
	 * Return the container's schema identifiers, sorted by name.
	 */
	Iterator<String> sortedSchemaIdentifiers();

	/**
	 * Return the schema for the specified identifier. The identifier should
	 * be an SQL identifier (i.e. quoted when case-sensitive or containing
	 * special characters, unquoted otherwise).
	 * @see #schemaIdentifiers()
	 * @see #getSchemaNamed(String)
	 */
	Schema getSchemaForIdentifier(String identifier);

	/**
	 * Return the container's default schema, as defined by the database vendor.
	 * In most cases the default schema's name will match the user name.
	 */
	Schema getDefaultSchema();
	
	/**
	 * Return the container's "default" schema identifier.
	 * The container may or may not have a schema with a matching name.
	 * @see #getDefaultSchema()
	 */
	String getDefaultSchemaIdentifier();


}
