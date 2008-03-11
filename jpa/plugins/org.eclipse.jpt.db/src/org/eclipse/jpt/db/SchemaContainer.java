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
public interface SchemaContainer {

	/**
	 * Return the container's schemata.
	 */
	Iterator<Schema> schemata();

	/**
	 * Return the number of schemata in the container.
	 */
	int schemataSize();

	/**
	 * Return the names of the container's schemata.
	 */
	Iterator<String> schemaNames();

	/**
	 * Return whether the container contains a schema with the specified name,
	 * respecting the database's case-sensitivity.
	 */
	boolean containsSchemaNamed(String name);

	/**
	 * Return the schema in the container with the specified name,
	 * respecting the database's case-sensitivity.
	 */
	Schema schemaNamed(String name);

}
