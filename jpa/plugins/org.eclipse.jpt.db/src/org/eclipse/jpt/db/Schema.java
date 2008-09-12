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
 * Database schema
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface Schema
	extends DatabaseObject, Comparable<Schema>
{
	/**
	 * Return the schema's container; either a catalog or a database.
	 */
	SchemaContainer getContainer();


	// ********** tables **********

	/**
	 * Return the schema's tables.
	 */
	Iterator<Table> tables();

	/**
	 * Return the number of tables the schema contains.
	 */
	int tablesSize();

	/**
	 * Return the table with specified name. The name must be an exact match
	 * of the table's name.
	 * @see #getTableForIdentifier(String)
	 */
	 Table getTableNamed(String name);

	/**
	 * Return the schema's table identifiers, sorted by name.
	 */
	Iterator<String> sortedTableIdentifiers();

	/**
	 * Return the table for the specified identifier. The identifier should
	 * be an SQL identifier (i.e. quoted when case-sensitive or containing
	 * special characters, unquoted otherwise).
	 * @see #getTableNamed(String)
	 */
	Table getTableForIdentifier(String identifier);


	// ********** sequences **********

	/**
	 * Return the schema's sequences.
	 */
	Iterator<Sequence> sequences();

	/**
	 * Return the number of sequences the schema contains.
	 */
	int sequencesSize();

	/**
	 * Return the sequence with specified name. The name must be an exact match
	 * of the sequence's name.
	 * @see #getSequenceForIdentifier(String)
	 */
	Sequence getSequenceNamed(String name);

	/**
	 * Return the schema's sequence identifers, sorted by name.
	 */
	Iterator<String> sortedSequenceIdentifiers();

	/**
	 * Return the sequence for the specified identifier. The identifier should
	 * be an SQL identifier (i.e. quoted when case-sensitive or containing
	 * special characters, unquoted otherwise).
	 * @see #getSequenceNamed(String)
	 */
	Sequence getSequenceForIdentifier(String identifier);

}
