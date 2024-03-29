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

/**
 * Schema container (i.e. Database or Catalog)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface SchemaContainer
	extends DatabaseObject
{
	/**
	 * Return the container's schemata.
	 */
	Iterable<Schema> getSchemata();

	/**
	 * Return the number of schemata in the container.
	 */
	int getSchemataSize();

	/**
	 * Return the container's schema names, sorted.
	 * This is useful when the user is selecting a schema from a read-only
	 * combo-box (e.g. in a wizard).
	 * @see #getSchemaNamed(String)
	 * @see #getSortedSchemaIdentifiers()
	 */
	Iterable<String> getSortedSchemaNames();

	/**
	 * Return the schema with the specified name. The name must be an exact match
	 * of the schema's name.
	 * @see #getSortedSchemaNames()
	 * @see #getSchemaForIdentifier(String)
	 */
	Schema getSchemaNamed(String name);

	/**
	 * Return the container's schema identifiers, sorted by name.
	 * This is useful when the user is selecting an identifier that will be
	 * placed in a text file (e.g. in a Java annotation).
	 * @see #getSchemaForIdentifier(String)
	 * @see #getSortedSchemaNames()
	 */
	Iterable<String> getSortedSchemaIdentifiers();

	/**
	 * Return the schema for the specified identifier. The identifier should
	 * be an SQL identifier (i.e. quoted when case-sensitive or containing
	 * special characters, unquoted otherwise).
	 * @see #getSortedSchemaIdentifiers()
	 * @see #getSchemaNamed(String)
	 */
	Schema getSchemaForIdentifier(String identifier);

	/**
	 * Return the container's default schema, as defined by the database vendor.
	 * In most cases the default schema's name will match the user name.
	 * Return <code>null</code> if the default schema does not exist (e.g. the
	 * container has no schema whose name matches the user name).
	 * @see #getDefaultSchemaIdentifier()
	 */
	Schema getDefaultSchema();

	/**
	 * Return the container's default schema identifier.
	 * The container may or may not have a schema with a matching name.
	 * @see #getDefaultSchema()
	 */
	String getDefaultSchemaIdentifier();
}
