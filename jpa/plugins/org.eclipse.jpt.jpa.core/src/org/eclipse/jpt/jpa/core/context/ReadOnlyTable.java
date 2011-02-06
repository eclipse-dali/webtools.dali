/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.ListIterator;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

/**
 * Read-only table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyTable
	extends JpaContextNode
{
	// ********** name **********

	/**
	 * Return the specified name if present, otherwise return the default
	 * name.
	 */
	String getName();
	String getSpecifiedName();
		String SPECIFIED_NAME_PROPERTY = "specifiedName"; //$NON-NLS-1$
	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultName"; //$NON-NLS-1$


	// ********** schema **********

	/**
	 * Return the specified schema if present, otherwise return the default
	 * schema.
	 */
	String getSchema();
	String getSpecifiedSchema();
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchema"; //$NON-NLS-1$
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchema"; //$NON-NLS-1$


	// ********** catalog **********

	/**
	 * Return the specified catalog if present, otherwise return the default
	 * catalog.
	 */
	String getCatalog();
	String getSpecifiedCatalog();
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalog"; //$NON-NLS-1$
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog"; //$NON-NLS-1$


	// ********** unique constraints **********

	ListIterator<? extends ReadOnlyUniqueConstraint> uniqueConstraints();
		String UNIQUE_CONSTRAINTS_LIST = "uniqueConstraints"; //$NON-NLS-1$
	int uniqueConstraintsSize();
	ReadOnlyUniqueConstraint getUniqueConstraint(int index);


	// ********** database **********

	/**
	 * Return the corresponding database table.
	 */
	org.eclipse.jpt.jpa.db.Table getDbTable();

	/**
	 * Return the corresponding database schema.
	 */
	Schema getDbSchema();

	/**
	 * Return the corresponding database catalog.
	 */
	Catalog getDbCatalog();

	/**
	 * Return the corresponding database schema container (catalog or database).
	 */
	SchemaContainer getDbSchemaContainer();
}
