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
import org.eclipse.jpt.db.Schema;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Table extends JpaContextNode
{
	String getName();
	
	String getDefaultName();
		String DEFAULT_NAME_PROPERTY = "defaultNameProperty";

	String getSpecifiedName();
	void setSpecifiedName(String value);
		String SPECIFIED_NAME_PROPERTY = "specifiedNameProperty";

	String getCatalog();

	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalogProperty";

	String getSpecifiedCatalog();
	void setSpecifiedCatalog(String value);
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalogProperty";


	String getSchema();

	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchemaProperty";

	String getSpecifiedSchema();
	void setSpecifiedSchema(String value);
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchemaProperty";


	// **************** unique constraints **************************************

	/**
	 * Return a list iterator of the unique constraints.
	 * This will not be null.
	 */
	<T extends UniqueConstraint> ListIterator<T> uniqueConstraints();
	
	/**
	 * Return the number of unique constraints.
	 */
	int uniqueConstraintsSize();
		
	/**
	 * Add a unique constraint to the table and return the object 
	 * representing it.
	 */
	UniqueConstraint addUniqueConstraint(int index);
	
	/**
	 * Remove unique constraint at the given index from the Table
	 */
	void removeUniqueConstraint(int index);
	
	/**
	 * Remove the unique constraint from the Table
	 */
	void removeUniqueConstraint(UniqueConstraint uniqueConstraint);
	
	/**
	 * Move the unique constraint from the source index to the target index.
	 */
	void moveUniqueConstraint(int targetIndex, int sourceIndex);
		String UNIQUE_CONSTRAINTS_LIST = "uniqueConstraintsList";



	org.eclipse.jpt.db.Table getDbTable();

	Schema getDbSchema();
	
	/**
	 * Return true if this table is connected to a datasource
	 */
	boolean connectionProfileIsActive();

	/** 
	 * Return true if this table's schema can be resolved to a schema on the active connection
	 */
	boolean hasResolvedSchema();

	/** 
	 * Return true if this can be resolved to a table on the active connection
	 */
	boolean isResolved();
}
