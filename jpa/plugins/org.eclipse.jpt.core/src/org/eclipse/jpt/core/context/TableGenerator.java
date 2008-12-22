/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import java.util.ListIterator;
import org.eclipse.jpt.db.Table;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface TableGenerator
	extends Generator
{
	int DEFAULT_INITIAL_VALUE = 0;


	// ********** table **********

	/**
	 * Return the specified table if present, otherwise return the default
	 * table.
	 */
	String getTable();
	String getSpecifiedTable();
	void setSpecifiedTable(String value);
		String SPECIFIED_TABLE_PROPERTY = "specifiedTable"; //$NON-NLS-1$
	String getDefaultTable();
		String DEFAULT_TABLE_PROPERTY = "defaultTable"; //$NON-NLS-1$


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


	// ********** primary key column name **********

	/**
	 * Return the specified primary key colum name if present, otherwise return
	 * the default primary key colum name.
	 */
	String getPkColumnName();
	String getSpecifiedPkColumnName();
	void setSpecifiedPkColumnName(String value);
		String SPECIFIED_PK_COLUMN_NAME_PROPERTY = "specifiedPkColumnName"; //$NON-NLS-1$
	String getDefaultPkColumnName();
		String DEFAULT_PK_COLUMN_NAME_PROPERTY = "defaultPkColumnName"; //$NON-NLS-1$


	// ********** value column name **********

	/**
	 * Return the specified value colum name if present, otherwise return
	 * the default value colum name.
	 */
	String getValueColumnName();
	String getSpecifiedValueColumnName();
	void setSpecifiedValueColumnName(String value);
		String SPECIFIED_VALUE_COLUMN_NAME_PROPERTY = "specifiedValueColumnName"; //$NON-NLS-1$
	String getDefaultValueColumnName();
		String DEFAULT_VALUE_COLUMN_NAME_PROPERTY = "defaultValueColumnName"; //$NON-NLS-1$


	// ********** primary key column value **********

	/**
	 * Return the specified primary key colum value if present, otherwise return
	 * the default primary key colum value.
	 */
	String getPkColumnValue();
	String getSpecifiedPkColumnValue();
	void setSpecifiedPkColumnValue(String value);
		String SPECIFIED_PK_COLUMN_VALUE_PROPERTY = "specifiedPkColummValue"; //$NON-NLS-1$
	String getDefaultPkColumnValue();
		String DEFAULT_PK_COLUMN_VALUE_PROPERTY = "defaultPkColummValue"; //$NON-NLS-1$


	// ********** unique constraints **********

	<T extends UniqueConstraint> ListIterator<T> uniqueConstraints();
	int uniqueConstraintsSize();
	UniqueConstraint addUniqueConstraint(int index);
	void removeUniqueConstraint(int index);
	void removeUniqueConstraint(UniqueConstraint uniqueConstraint);
	void moveUniqueConstraint(int targetIndex, int sourceIndex);
		String UNIQUE_CONSTRAINTS_LIST = "uniqueConstraints"; //$NON-NLS-1$


	// ********** database stuff **********

	/**
	 * Return a db Table object with the specified/default table name.
	 * This can return null if no Table exists on the database with that name.
	 */
	Table getDbTable();

}
