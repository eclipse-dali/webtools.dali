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
public interface Schema extends Comparable<Schema> {

	/**
	 * Return the schema's name.
	 */
	String getName();

	/**
	 * Return the schema's tables.
	 */
	Iterator<Table> tables();

	/**
	 * Return the number of tables the schema contains.
	 */
	int tablesSize();

	/**
	 * Return the names of the schema's tables.
	 */
	Iterator<String> tableNames();

	/**
	 * Return whether the schema contains a table with the specified name,
	 * respecting the database's case-sensitivity.
	 */
	boolean containsTableNamed(String name);

	/**
	 * Return the table in the schema with the specified name,
	 * respecting the database's case-sensitivity.
	 */
	Table tableNamed(String name);

	/**
	 * Return the schema's sequences.
	 */
	Iterator<Sequence> sequences();

	/**
	 * Return the number of sequences the schema contains.
	 */
	int sequencesSize();

	/**
	 * Return the names of the schema's sequences.
	 */
	Iterator<String> sequenceNames();

	/**
	 * Return whether the schema contains a sequence with the specified name,
	 * respecting the database's case-sensitivity.
	 */
	boolean containsSequenceNamed(String name);

	/**
	 * Return the sequence in the schema with the specified name,
	 * respecting the database's case-sensitivity.
	 */
	Sequence sequenceNamed(String name);

}
