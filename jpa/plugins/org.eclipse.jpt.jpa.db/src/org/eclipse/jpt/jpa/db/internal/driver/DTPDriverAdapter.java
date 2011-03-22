/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.driver;

import java.util.List;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Sequence;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Delegate DTP driver-specific behavior to implementations of this
 * interface:<ul>
 *   <li>catalogs/schemas
 *   <li>names/identifiers
 *   <li>database object selection
 * </ul>
 * <strong>NB:</strong><br>
 * We use <em>name</em> when dealing with the unmodified name of a database
 * object as supplied by the database itself (i.e. it is not delimited and it is
 * always case-sensitive).
 * <br>
 * We use <em>identifier</em> when dealing with a string representation of a
 * database object name (i.e. it may be delimited and, depending on the database,
 * it may be case-insensitive).
 */
public interface DTPDriverAdapter {

	// ********** catalogs **********

	/**
	 * Return whether the DTP driver supports "real" catalogs (e.g. Sybase).
	 * If it does, use {@link #getDTPCatalogs()} to retrieve them;
	 * otherwise, use {@link #getDTPSchemas()} to retrieve the schemas directly.
	 */
	boolean supportsCatalogs();

	/**
	 * Return the DTP database's catalogs.
	 * This will be empty if the database does not support catalogs.
	 * @see #supportsCatalogs()
	 */
	List<org.eclipse.datatools.modelbase.sql.schema.Catalog> getDTPCatalogs();

	/**
	 * Return the DTP database's default catalog names.
	 * The first name in the list that identifies an existing catalog
	 * is <em>the</em> default.
	 * This will be empty if the database does not support catalogs.
	 */
	Iterable<String> getDefaultCatalogNames();


	// ********** schemas **********

	/**
	 * Return the DTP database's schemas.
	 * This will be empty if the database supports catalogs.
	 * @see #supportsCatalogs()
	 */
	List<org.eclipse.datatools.modelbase.sql.schema.Schema> getDTPSchemas();

	/**
	 * Return the DTP database's default schema names.
	 * The first name in the list that identifies an existing schema
	 * is <em>the</em> default.
	 */
	Iterable<String> getDefaultSchemaNames();


	// ********** names/identifiers **********

	/**
	 * Convert the specified database object name to a database-specific
	 * identifier (i.e. delimit the name as appropriate).
	 */
	String convertNameToIdentifier(String name);

	/**
	 * Convert the specified database object name to a database-specific
	 * identifier (i.e. delimit the name as appropriate).
	 * Return <code>null</code> if the resulting identifier matches the
	 * specified default name.
	 * <p>
	 * This is used by entity generation code to determine whether
	 * a generated annotation must explicitly identify a database object
	 * (e.g. a table) or the specified default adequately identifies the
	 * specified database object (taking into consideration case-sensitivity,
	 * special characters, etc.).
	 */
	String convertNameToIdentifier(String name, String defaultName);

	/**
	 * Convert the specified database object identifier to a database-specific
	 * name (i.e. stripping delimiters from and folding the identifier as
	 * appropriate).
	 */
	String convertIdentifierToName(String identifier);


	// ********** selecting database objects **********

	/**
	 * Select from the specified collection the catalog for the specified
	 * identifier.
	 */
	Catalog selectCatalogForIdentifier(Iterable<Catalog> catalogs, String identifier);

	/**
	 * Select from the specified collection the schema for the specified
	 * identifier.
	 */
	Schema selectSchemaForIdentifier(Iterable<Schema> schemata, String identifier);

	/**
	 * Select from the specified collection the table for the specified
	 * identifier.
	 */
	Table selectTableForIdentifier(Iterable<Table> tables, String identifier);

	/**
	 * Select from the specified collection the sequence for the specified
	 * identifier.
	 */
	Sequence selectSequenceForIdentifier(Iterable<Sequence> sequences, String identifier);

	/**
	 * Select from the specified collection the column for the specified
	 * identifier.
	 */
	Column selectColumnForIdentifier(Iterable<Column> columns, String identifier);
}
