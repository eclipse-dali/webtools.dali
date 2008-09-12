/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.ListIterator;

import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Table
	extends JpaContextNode
{

	// ********** name **********

	/**
	 * Return the specified name if present, otherwise return the default
	 * name.
	 */
	String getName();
	String getSpecifiedName();
	void setSpecifiedName(String value);
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
	void setSpecifiedSchema(String value);
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
	void setSpecifiedCatalog(String value);
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalog"; //$NON-NLS-1$
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog"; //$NON-NLS-1$


	// ********** unique constraints **********

	<T extends UniqueConstraint> ListIterator<T> uniqueConstraints();
	int uniqueConstraintsSize();
	UniqueConstraint addUniqueConstraint(int index);
	void removeUniqueConstraint(int index);
	void removeUniqueConstraint(UniqueConstraint uniqueConstraint);
	void moveUniqueConstraint(int targetIndex, int sourceIndex);
		String UNIQUE_CONSTRAINTS_LIST = "uniqueConstraints"; //$NON-NLS-1$


	// ********** database stuff **********

	org.eclipse.jpt.db.Table getDbTable();
	Schema getDbSchema();
	Catalog getDbCatalog();
	SchemaContainer getDbSchemaContainer();

	/**
	 * Return whether the table can be resolved to a table on the database.
	 */
	boolean isResolved();

	/**
	 * Return whether the table's schema can be resolved to a schema on the
	 * database.
	 */
	boolean hasResolvedSchema();

	/**
	 * Return whether the table has a catalog and it can be resolved to a
	 * catalog on the database.
	 */
	boolean hasResolvedCatalog();

}
